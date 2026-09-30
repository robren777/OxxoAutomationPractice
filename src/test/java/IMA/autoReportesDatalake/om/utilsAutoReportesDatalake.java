package IMA.autoReportesDatalake.om;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import modelo.TestCase;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Descripcion:Metodos utilizados en el proyecto I24043-Automatización Reportes DataLake
 * 
 *  Lider de automatizacion: mariad1@hexaware.com
 * 
 * Prerequisitos:
 * -Tener instalado el framework ScriptXspace version 2.5
 * 
 * Insumos:
 * @author guillermo.garcia@grupotimex.onmicrosoft.com
 * @author axel.martinez@grupotimex.onmicrosoft.com
 * @date	23/10/2024
 * Fecha estimada de finalizacion de etapa de pruebas: 04/12/2024
 * 
 */
public class utilsAutoReportesDatalake {
	/**
	 * Metodo utilizado para hacer el set up de las propiedades necesarias para hacer conexion
	 * @return Properties que incluye user,password,db y schema
	 * @author Guillermo Garcia
	 * @param user Nombre del usuario
	 * @param password Contrasena del usuario
	 * @param dataBase Base de datos donde se almacenan las tablas
	 * @param schema Esquema donde se almacenan las tablas
	 * @date 23/10/2024
	 */
	public Properties getProperties(String user, String password, String dataBase, String schema) {
		Properties properties = new java.util.Properties();
		properties.put("user",user );		
		properties.put("password",password );
		properties.put("db", dataBase);
		properties.put("schema", schema);
		return properties;
	}
	
	/**
	 * Metodo utilizado para hacer el set up de las propiedades necesarias para hacer conexion
	 * @return Properties que incluye user,password,db y schema
	 * @author Guillermo Garcia
	 * @param user Nombre del usuario
	 * @param password Contrasena del usuario
	 * @param dataBase Base de datos donde se almacenan las tablas
	 * @param schema Esquema donde se almacenan las tablas
	 * @param passcode Codigo generado por duo en caso de que la cuenta tenga doble factor de auntenticacion
	 * @date 23/10/2024
	 */
	public Properties getProperties(String user, String password, String dataBase, String schema, String passcode) {
		Properties properties = new java.util.Properties();
		properties.put("user",user );
		properties.put("passcode", passcode);
		properties.put("password",password );
		properties.put("db", dataBase);
		properties.put("schema", schema);
		return properties;
	}
	
	
	/**
	 * Selecciona el tipo de esquema necesario para consultar el objeto
	 * @return Esquema que se utiliza en Snowflake o error en caso de no encontrar una opcion valida
	 * @author Guillermo Garcia
	 * @param type Es la clasificacion del objeto puede ser CATALOGO para catalogos, TRANSACTION para transaccionales, 
	 * CATALOGO-OLD para catalogos en EBS12 y TRANSACTION-OLD para transaccionales en EBS12
	 * @date 23/10/2024
	 */
	public String getSchema(String tipo) {
		String esquema;
		switch (tipo) {
		case "CATALOGO":
			esquema = "LAND_EBS_CAT";
			break;
		case "TRANSACTION":
			esquema = "LAND_EBS_TRAN";
			break;
		case "CATALOGO-OLD":
			esquema = "LAND_EBS12_0_6_CAT";
			break;
		case "TRANSACTION-OLD":
			esquema = "LAND_EBS12_0_6_TRAN";
			break;
		case "RMS":
			esquema = "LAND_RMS16_TRAN";
			break;
		case "RMS-OLD":
			esquema = "LAND_RMS10_TRAN";
			break;

		default:
			esquema = "error";
			break;
		}
		return esquema;
	}
	
	/**
	 * Genera un query para obtener la tabla de donde se genera el sinonimo 
	 * @return Query para obtener la tabla de donde se genera el synonym
	 * @author Guillermo Garcia
	 * @param owner El owner del sinonimo
	 * @param tableName El nombre del objeto
	 * @date 23/10/2024
	 */
	public String getQueryRealTable(String owner, String tableName) {		
		return String.format("SELECT * FROM ALL_SYNONYMS WHERE OWNER = '%1$s' "
							+" AND SYNONYM_NAME = '%2$s' ", owner, tableName);
	}
	
	/**
	 * Genera un query para obtener la metadata del objeto a probar en EBS
	 * @return Query para obtener la metadata del objeto a probar en EBS
	 * @author Guillermo Garcia
	 * @param owner El owner del objeto
	 * @param tableName El nombre del objeto
	 * @date 23/10/2024
	 */
	public String getQueryEBSGetMetadata(String owner, String tableName) {
		return String.format("SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, COLUMN_ID, DATA_PRECISION, DATA_SCALE, "
							+ "NULLABLE FROM ALL_TAB_COLUMNS  WHERE TABLE_NAME like '%1$s' "
							+ "AND OWNER = '%2$s'", tableName, owner);
		
	}
	
	
	/**
	 * Genera un query para obtener la metadata del objeto a probar en Snowflake
	 * @return Query para obtener la metadata del objeto a probar en Snowflake
	 * @author Guillermo Garcia
	 * @param schema El schema del objeto
	 * @param tableName El nombre del objeto
	 * @date 23/10/2024
	 */
	public String getQuerySFGetMetadata(String tableName, String schema) {
		String query = String.format("SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH AS "
				+ "DATA_LENGTH, ORDINAL_POSITION AS COLUMN_ID, IS_NULLABLE AS NULLABLE, NUMERIC_PRECISION AS DATA_PRECISION, NUMERIC_SCALE AS DATA_SCALE "
				+ "FROM INFORMATION_SCHEMA.columns "
				+ "WHERE TABLE_NAME like '%1$s' AND TABLE_SCHEMA like '%2$s'", tableName, schema);
		return query;
	}
	
	
	/**
	 * Se prueba el tipo de dato basado en lo encontrado en EBS y como debe aparecer en Snowflake
	 * @return true si el tipo de dato esta bien traducido, false si el tipo de dato no esta bien implementado
	 * @author Guillermo Garcia
	 * @param responseEBS El tipo de dato encontrado en EBS
	 * @param responseSnow El tipo de dato encontrado en Snow
	 * @param precision La precision encontrada en EBS
	 * @date 23/10/2024
	 */
	public Boolean validateDataType(String responseEBS,String responseSnow,String precision) {
		boolean flag = false;
		switch (responseEBS) {
		case "NUMBER":
			flag = ((responseSnow.equalsIgnoreCase("FLOAT"))||(responseSnow.equalsIgnoreCase("NUMBER")));
			if(precision==null) {
				flag = (responseSnow.equalsIgnoreCase("FLOAT"));	
			}
			else {
				flag = (responseSnow.equalsIgnoreCase("NUMBER"));
			}
			break;
			
		case "VARCHAR2":
			flag = (responseSnow.equalsIgnoreCase("TEXT"));
			break;
			
		case "DATE":
			flag = (responseSnow.equalsIgnoreCase("DATE"));
			break;
			
		case "CLOB":
			flag = (responseSnow.equalsIgnoreCase("TEXT"));
			break;
			
		default:
			flag = (responseSnow.equalsIgnoreCase(responseEBS));
			break;
		}
		return flag;
	}
	
	/**
	 * Se prueba la escala basado en lo encontrado en EBS y como debe aparecer en Snowflake
	 * @return true si la escala coincide, false si la escala no coincide
	 * @author Guillermo Garcia
	 * @param responseEBS La escala encontrada en EBS
	 * @param responseSnow La escala encontrada en Snow
	 * @param dataType el tipo de dato encontrado en EBS
	 * @date 23/10/2024
	 */
	public Boolean validateScale(String dataType, String responseEBS,String responseSnow) {
		boolean flag = false;
		switch (dataType) {
		case "NUMBER":
			flag = (responseSnow==null);
			break;
			
		case "VARCHAR2":
			flag = (responseSnow.equalsIgnoreCase(responseEBS));
			break;
			
		case "DATE":
			flag = (responseSnow==null);
			break;

		default:
			flag = (responseSnow.equalsIgnoreCase(responseEBS));
			break;
		}
		return flag;
	}
	
	/**
	 * Se prueba la escala basado en lo encontrado en EBS y como debe aparecer en Snowflake
	 * @return true si la escala coincide, false si la escala no coincide
	 * @author Guillermo Garcia
	 * @param responseEBS La escala encontrada en EBS
	 * @param responseSnow La escala encontrada en Snow
	 * @param dataType el tipo de dato encontrado en EBS
	 * @date 23/10/2024
	 */
	public Boolean validateDataLength(String dataType, String responseEBS,String responseSnow) {
		boolean flag = false;
		switch (dataType) {
		case "NUMBER":
			flag = (responseSnow==null);
			break;
			
		case "VARCHAR2":
			flag = (responseSnow.equalsIgnoreCase(responseEBS));
			break;
			
		case "DATE":
			flag = (responseSnow==null);
			break;

		default:
			if(!(responseSnow==null)) {
				flag = (responseSnow.equalsIgnoreCase(responseEBS));
			}
			flag = false;
			break;
		}
		return flag;
	}
	
	/**
	 * Se prueba si el campo puede aceptar valores nulos
	 * @return true si el campo acepta valores nullos , false no lo hace
	 * @author Guillermo Garcia
	 * @param responseEBS El valor de nullable en EBS
	 * @param responseSnow El valor de nullable en SnowFlake
	 * @date 23/10/2024
	 */
	public Boolean 	validateNullable(String responseEBS,String responseSnow) {
		boolean flag = false;
		switch (responseEBS) {
		case "Y":
			flag = (responseSnow.equalsIgnoreCase("YES"));
			break;
			
		case "N":
			flag = (responseSnow.equalsIgnoreCase("YES"));
			break;

		default:
			flag = (responseSnow.equalsIgnoreCase(responseEBS));
			break;
		}
		return flag;
	}
	
	/**
	 * Genera un query para consultar el total de registros en EBS
	 * @return query para consultar el total de registros en EBS
	 * @author Guillermo Garcia
	 * @param tipo El tipo de tabla a consultar si es catalogo o transaccional
	 * @param tableName El nombre del objeto a consultar
	 * @param owner El owner del objeto a consultar
	 * @param fecha la fecha a consultar
	 * @param delta el campo que funciona como delta en el caso de las transaccionales
	 * @date 23/10/2024
	 */
	public String getQueryEBSNumeroRegistros(String tipo, String tableName, String owner, String fecha, String delta) {
		String query;
		if(tipo.equalsIgnoreCase("Catalogo")) {			
			query = String.format("SELECT COUNT(*) "
					+ "FROM %1$s.%2$s" ,owner,tableName);				
		}		
		else {
			query = String.format("SELECT COUNT(*) "
					+ "FROM %1$s.%2$s "
					+ "WHERE TRUNC(%4$s) = TO_DATE('%3$s','DD/MM/YY');",owner,tableName,delta,fecha);
		}
		return query;
	}
	
	/**
	 * Genera un query para consultar el total de registros en EBS
	 * @return query para consultar el total de registros en EBS
	 * @author Guillermo Garcia
	 * @param tipo El tipo de tabla a consultar si es catalogo o transaccional
	 * @param tableName El nombre del objeto a consultar
	 * @param owner El owner del objeto a consultar
	 * @param fecha la fecha a consultar
	 * @date 23/10/2024
	 */
	public String getQueryEBSNumeroRegistros(String tipo, String tableName, String owner, String fecha) {
		String query;
			query = String.format("SELECT COUNT(*) "
					+ "FROM %1$s.%2$s" ,owner,tableName);				
		return query;
	}
	
	
	/**
	 * Genera un query para consultar el total de registros en Snow
	 * @return query para consultar el total de registros en snowflake
	 * @author Guillermo Garcia
	 * @param tipo El tipo de tabla a consultar si es catalogo o transaccional
	 * @param tableName El nombre del objeto a consultar
	 * @param delta para seleccionar el campo a consultar
	 * @param fecha la fecha a consultar
	 * @date 23/10/2024
	 */
	public String getQuerySnowNumeroRegistros(String tipo, String tableName, String fecha, String delta) {
		String query;
		if(tipo.equalsIgnoreCase("Catalogo")) {			
			query = String.format("SELECT COUNT(*) "
					+ "FROM %s" ,tableName);				
		}		
		else {
			query = String.format("SELECT COUNT(*) "
					+ "FROM %1$s "
					+ "WHERE %2$s = '%3$s'",tableName,delta,fecha);
		}
		return query;
	}
	
	
	
	/**
	 * Genera un query para consultar los registros de EBS
	 * @return query para obtener una muestra de registros en EBS
	 * @author Guillermo Garcia
	 * @param tipo El tipo de tabla a consultar si es catalogo o transaccional
	 * @param tableName El nombre del objeto a consultar
	 * @param owner El owner del objeto a consultar
	 * @param delta para seleccionar el campo a consultar
	 * @param fecha la fecha a consultar
	 * @param totalCount es el numero total de registros
	 * @date 23/10/2024
	 */
	public String getQueryEBSAllData(String tipo, String tableName, String owner, String fecha, String delta, int totalCount) {
		String query;
		
		String limit = totalCount > 100 ? "100": String.valueOf(totalCount);
		if(tipo.equalsIgnoreCase("Catalogo")) {			
			query = String.format("SELECT * "
					+ "FROM %1$s.%2$s " 
					+ "FETCH FIRST %3$s ROWS ONLY",owner,tableName,limit);				
		}		
		else {
			query = String.format("SELECT * "
					+ "FROM %1$s.%2$s "
					+ "WHERE TRUNC(%3$s) = TO_DATE('%4$s','DD/MM/YY')"
					+ "FETCH FIRST %d ROWS ONLY",owner,tableName,delta,fecha,limit);
		}
		return query;
	}
	
	/**
	 * Preparate que aqui viene uno de esos metodos que carecen de sentido pero es momento de tener 
	 * fe porque es la mejor manera de validar datos sin usar las herramientas de cloud, este metodo
	 * genera una serie de queries para que sean enviados a snowflake y con esto hacer una validacion
	 * cruzada.
	 * @return ArrayList<String> con los queries creados en base al response de ebs
	 * @author Guillermo Garcia
	 * @param responseConsultaDeRows un response de una muestra de rows obtenidas de EBS
	 * @param tableName El nombre del objeto a consultar
	 * @date 23/10/2024
	 */
	public ArrayList<String> getQueriesForDataValidation (SQLResult responseConsultaDeRows, String tableName) {
		ArrayList <String> queries = new ArrayList<>();
		List<String> errors = new ArrayList<>();		
		String inicio = String.format("SELECT * FROM %s WHERE ", tableName);
		
		for (int i = 0; i < responseConsultaDeRows.getRowCount(); i++) {
			Boolean flag = false;
			StringBuilder queryFinal = new StringBuilder();
			queryFinal.append(inicio);
		errors =responseConsultaDeRows.getColumnNames();
		for (String string : errors) {
			if(string.equalsIgnoreCase("VALIDATION_NUMBER")) {
				continue;
			}
			if(string.equalsIgnoreCase("BANK_ACCOUNT_NUM_HASH1")) {
				continue;
			}
			if(string.equalsIgnoreCase("BANK_ACCOUNT_NUM_HASH2")) {
				continue;
			}
			if(string.equalsIgnoreCase("IBAN_HASH1")) {
				continue;
			}
			if(string.equalsIgnoreCase("IBAN_HASH2")) {
				continue;
			}
			if(this.itsIgnorable(tableName, string)) {
				continue;
			}
			String valor;
			valor = responseConsultaDeRows.getData(i, string);
			string = string.equalsIgnoreCase("VAT_REGISTRATION_NUM#1") ? "VAT_REGISTRATION_NUM1" : string;
			string = string.equalsIgnoreCase("MERCHANT_NAME#1") ? "MERCHANT_NAME1" : string;
			if(!(valor==null)) {
				if(flag==true)
					queryFinal.append(" AND ");
				flag = true;
				queryFinal.append(string);
				queryFinal.append(" = ");
				queryFinal.append("'");
				queryFinal.append(valor);
				queryFinal.append("'");
			}
			else {
				if(flag==true)
					queryFinal.append(" AND ");
				flag = true;
				queryFinal.append(string);
				queryFinal.append(" IS NULL ");
			}
				
		}		
		queries.add(queryFinal.toString());
		}

		return queries;
	}
	
	
	/**
	 * Regresa el host de la DB a consultar basandose en lo que se coloque en el data provider
	 * @return El host de la DB a consultar
	 * @author Guillermo Garcia
	 * @param type puede ser CATALOGO, CATALOGO-OLD, TRANSACTION, TRANSACTION-OLD, RMS, RMS-OLD
	 * @date 23/10/2024
	 */
	public String getHost(String type) {
		String host = null;
		
		switch (type) {
		case "CATALOGO":
		case "TRANSACTION":
			host = Credentials.EBS_DB_QA.host;
			break;
			
		case "CATALOGO-OLD":
		case "TRANSACTION-OLD":
			host = Credentials.EBS_PROD_OLD.host;
			break;
			
		case "RMS":
			host = Credentials.RMS_DB_QA_ACTUAL.host;
			break;
			
		case "RMS-OLD":
			host = Credentials.RMS_DB_BD_FCRMS.host;
			break;

		default:
			host = "error";
			break;
		}
		return host;
	}
	
	/**
	 * Regresa el user de la DB a consultar basandose en lo que se coloque en el data provider
	 * @return El user de la DB a consultar
	 * @author Guillermo Garcia
	 * @param type puede ser CATALOGO, CATALOGO-OLD, TRANSACTION, TRANSACTION-OLD, RMS, RMS-OLD
	 * @date 23/10/2024
	 */
	public String getUser(String type) {		
		String user = null;
		
		switch (type) {
		case "CATALOGO":
		case "TRANSACTION":
			user = Credentials.EBS_DB_QA.user;
			break;
			
		case "CATALOGO-OLD":
		case "TRANSACTION-OLD":
			user = Credentials.EBS_PROD_OLD.user;
			break;
			
		case "RMS":
			user = Credentials.RMS_DB_QA_ACTUAL.user;
			break;
			
		case "RMS-OLD":
			user = Credentials.RMS_DB_BD_FCRMS.user;
			break;

		default:
			user = "error";
			break;
		}
		return user;
	}
	
	
	/**
	 * Regresa el password de la DB a consultar basandose en lo que se coloque en el data provider
	 * @return El password de la DB a consultar
	 * @author Guillermo Garcia
	 * @param type puede ser CATALOGO, CATALOGO-OLD, TRANSACTION, TRANSACTION-OLD, RMS, RMS-OLD
	 * @date 23/10/2024
	 */	
	public String getPassword(String type) {		
		String password = null;
		
		switch (type) {
		case "CATALOGO":
		case "TRANSACTION":
			password = Credentials.EBS_DB_QA.password;
			break;
			
		case "CATALOGO-OLD":
		case "TRANSACTION-OLD":
			password = Credentials.EBS_PROD_OLD.password;
			break;
			
		case "RMS":
			password = Credentials.RMS_DB_QA_ACTUAL.password;
			break;
			
		case "RMS-OLD":
			password = Credentials.RMS_DB_BD_FCRMS.password;
			break;

		default:
			password = "error";
			break;
		}
		return password;
	}
	

	/**
	 * Guarda los valores de una respuesta de snowflake de una forma mas facil de leer
	 * @return Un Hashmap que almacena en el key el nombre de la columna y en los values los valores del result
	 * @author Guillermo Garcia
	 * @param type puede ser CATALOGO, CATALOGO-OLD, TRANSACTION, TRANSACTION-OLD, RMS, RMS-OLD
	 * @date 23/10/2024
	 */	
	public HashMap<String,String[]> getSnowValues(ResultSet result, String typeOfTest) throws SQLException{
		HashMap<String,String[]> responseSnow = new HashMap<String, String[]>();
		if(typeOfTest.equalsIgnoreCase("Metadata")) {
			while (result.next()) {
				String[] holder= new String[6];
				holder [0] = result.getString("DATA_TYPE");								
				holder [1] = result.getString("DATA_LENGTH");
				holder [2] = result.getString("COLUMN_ID");
				holder [3] = result.getString("NULLABLE");
				holder [4] = result.getString("DATA_PRECISION");
				holder [5] = result.getString("DATA_SCALE");
						
				responseSnow.put(result.getString("COLUMN_NAME"),holder);
				}
		}
		
		return responseSnow;
	}
	
	/**
	 * Valida si es un campo especial que debe ignorarse dependiendo la tabla
	 * @return true si el valor debe ser ignorado
	 * @author Guillermo Garcia
	 * @param tableName la tabla a probar
	 * @param field el campo a probar
	 * @date 23/10/2024
	 */	
	public Boolean itsIgnorable(String tableName, String field) {
		List<String> fieldsToIgnore = new ArrayList<String>(
				Arrays.asList(	"GEOMETRY", 
								"ROW_ID",
								"ROWID",
								"BANK_ACCOUNT_NUM_HASH1",
								"BANK_ACCOUNT_NUM_HASH2",
								"DESCRIPTION")
				);
		
		List<String> tablesToIgnore = new ArrayList<String>(
				Arrays.asList(	"FND_FLEX_VALUES_VL", 
								"CE_BANKS_V", 
								"CE_BANK_BRANCHES_V",
								"HZ_LOCATIONS",
								"GL_CODE_COMBINATIONS_KFV",
								"FND_USER",
								"IBY_EXT_BANK_ACCOUNTS")
				);
		
		if(tablesToIgnore.contains(tableName)&&fieldsToIgnore.contains(field)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Realiza la conexion con snowflake
	 * @return Connection hacia el datalake de IMA en Snowflake
	 * @author Guillermo Garcia
	 * @param user Usuario valido para hacer conexion al datalake
	 * @param password Contrasena del usuario
	 * @param host Host del datalake
	 * @param schema Esquema a consultar
	 * @date 14/02/2025
	 */	
	public Connection connectToSnow(String user, String password, String host, String schema) throws ClassNotFoundException, SQLException{		
		Properties properties = this.getProperties( user,password,host,schema);	
		Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
		System.out.println("Connected to Snowflake!");
		return DriverManager.getConnection(
				"jdbc:snowflake://oxxo.south-central-us.azure.snowflakecomputing.com/",
				properties);								
	}
	
	/**
	 * Imprime columnas especificas de un queries simples
	 * @author Guillermo Garcia
	 * @param dataBase La conexion a la base de datos
	 * @param response El response del query que se quiere imprimir de forma mas clara
	 * @param numOfColumns El total de columnas que se quiere por impresion
	 * @param testCase la instancia de TestCase del framework 
	 * @date 14/02/2025
	 */	
	public void prettyPrint(SQLUtil dataBase, SQLResult response, int numOfColumns, TestCase testCase) throws SQLException {
	    int realNumOfColumns = Math.max(numOfColumns, 1);
	    String afterFromQuery = getAfterFromQuery(response.getQuery());

	    int totalColumns = response.getColumnCount();
	    realNumOfColumns = Math.min(realNumOfColumns, totalColumns); // Adjust if numOfColumns exceeds total columns
	    List<String> columns = response.getColumnNames();

	    int totalQueries = (int) Math.ceil((double) totalColumns / realNumOfColumns); // Ensure to handle any leftover columns
	    int columnCount = 0;

	    for (int i = 0; i < totalQueries; i++) {
	        String temporalQuery = buildQuery(columns, columnCount, realNumOfColumns,hasTwoFrom(response.getQuery()));
	        columnCount += realNumOfColumns;

	        // Execute query and log result
	        SQLResult temporalResponse = dataBase.executeQuery(temporalQuery + " " + afterFromQuery);
	        testCase.addBoldTextEvidenceCurrentStep("Se muestra query " + i + " de " + totalQueries);	        
	        testCase.addQueryEvidenceCurrentStep(temporalResponse);
	    }
	}


	/**
	 * Helper method to get the portion of the query after "FROM"
	 * @author Guillermo Garcia
	 * @param originalQuery el query original que se desea partir
	 * @date 14/02/2025
	 */	
	private String getAfterFromQuery(String originalQuery) {
	    // Find the index of the first occurrence of "FROM"
	    int firstFromIndex = originalQuery.indexOf("FROM");

	    if (firstFromIndex != -1) {
	        // Find the index of the second "FROM" starting from the position just after the first "FROM"
	        int secondFromIndex = originalQuery.indexOf("FROM", firstFromIndex + 1);

	        if (secondFromIndex != -1) {
	            // If the second "FROM" exists, return the substring starting from the second "FROM"
	            return originalQuery.substring(secondFromIndex).trim();
	        } else {
	            // If no second "FROM" is found, return everything from the first "FROM"
	            return originalQuery.substring(firstFromIndex).trim();
	        }
	    }

	    // Return an empty string if no "FROM" is found
	    return "";
	}


	
	/**
	 * Helper method to build the SELECT part of the query
	 * @author Guillermo Garcia
	 * @param columns Listado de las columnas del response original
	 * @param startIndex El index que lleva el orden de la columna que se esta usando
	 * @param realNumOfColumns el total de columnas que se deben imprimir por tabla
	 * @date 14/02/2025
	 */	
	private String buildQuery(List<String> columns, int startIndex, int realNumOfColumns, boolean numFrom) {
		String select = numFrom ? "SELECT * FROM (SELECT " : "SELECT ";
	    StringBuilder queryBuilder = new StringBuilder(select);
	    for (int j = 0; j < realNumOfColumns && (startIndex + j) < columns.size(); j++) {
	        if (j > 0) {
	            queryBuilder.append(", ");
	        }
	        queryBuilder.append(columns.get(startIndex + j));
	    }
	    return queryBuilder.toString();
	}
	
	/**
	 * Helper method to get the portion of the query after "FROM"
	 * @author Guillermo Garcia
	 * @param originalQuery el query original que se desea partir
	 * @date 14/02/2025
	 */	
	private boolean hasTwoFrom(String originalQuery) {
	    // Find the index of the first "FROM"
	    int firstFromIndex = originalQuery.indexOf("FROM");

	    if (firstFromIndex != -1) {
	        // Find the index of the second "FROM" starting from the position just after the first "FROM"
	        int secondFromIndex = originalQuery.indexOf("FROM", firstFromIndex + 1);

	        // If a second "FROM" exists, return true
	        return secondFromIndex != -1;
	    }

	    // Return false if no "FROM" is found
	    return false;
	}



	
}

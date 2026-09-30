package IMA.ReportesDatalake_MVP3.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import IMA.autoReportesDatalake.om.Credentials;
import modelo.TestCase;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Descripcion:Metodos utilizados en el proyecto I24043-Automatización Reportes DataLake
 * 
 *  Lider de automatizacion: mariad1@hexaware.com
 * 
 *
 * 
 * Insumos:
 * @author 
 * @author 
 * @date	
 * Fecha estimada de finalizacion de etapa de pruebas: 05/09/2025
 * 
 */


public class UtilsReportesDatalakeMVP3 {
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
	    realNumOfColumns = Math.min(realNumOfColumns, totalColumns); // Ajustar si numOfColumns excede el total de columnas

	    List<String> columns = response.getColumnNames();

	    int totalQueries = (int) Math.ceil((double) totalColumns / realNumOfColumns); // Asegúrate de manejar cualquier columna sobrante

	    int columnCount = 0;

	    for (int i = 0; i < totalQueries; i++) {
	        String temporalQuery = buildQuery(columns, columnCount, realNumOfColumns,hasTwoFrom(response.getQuery()));
	        columnCount += realNumOfColumns;

	     // Ejecutar la consulta y registrar el resultado
	        SQLResult temporalResponse = dataBase.executeQuery(temporalQuery + " " + afterFromQuery);
	        testCase.addBoldTextEvidenceCurrentStep("Se muestra query " + i + " de " + totalQueries);	        
	        testCase.addQueryEvidenceCurrentStep(temporalResponse);
	    }
	}


	/**
	 * Método auxiliar para obtener la parte del query después de la cláusula 'FROM'
	 * @author Guillermo Garcia
	 * @param originalQuery el query original que se desea partir
	 * @date 14/02/2025
	 */	
	private String getAfterFromQuery(String originalQuery) {
		// Buscar la posición (índice) de la primera aparición de la palabra "FROM" en el query
	    int firstFromIndex = originalQuery.indexOf("FROM");

	    if (firstFromIndex != -1) {
	    	// Buscar la posición (índice) de la segunda aparición de la palabra "FROM", iniciando desde justo después de la primera
	        int secondFromIndex = originalQuery.indexOf("FROM", firstFromIndex + 1);

	        if (secondFromIndex != -1) {
	        	// Si existe una segunda aparición de la palabra "FROM", devolver la subcadena que inicia desde esa segunda aparición
	            return originalQuery.substring(secondFromIndex).trim();
	        } else {
	        	// Si no se encuentra una segunda aparición de la palabra "FROM", devolver todo el contenido a partir de la primera aparición
	            return originalQuery.substring(firstFromIndex).trim();
	        }
	    }

	 // Devolver una cadena vacía en caso de no encontrarse la palabra "FROM"
	    return "";
	}


	
	/**
	 * Método auxiliar para construir la parte SELECT del query.
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
     * Método auxiliar para obtener la parte del query después de "FROM"
     * @author Guillermo Garcia
	 * @param originalQuery el query original que se desea partir
	 * @date 14/02/2025
	 */	
	private boolean hasTwoFrom(String originalQuery) {
		// Buscar el índice del primer "FROM"
	    int firstFromIndex = originalQuery.indexOf("FROM");

	    if (firstFromIndex != -1) {
	    	// Buscar el índice del segundo "FROM" comenzando desde la posición justo después del primer "FROM"
	        int secondFromIndex = originalQuery.indexOf("FROM", firstFromIndex + 1);

	     // Si existe un segundo "FROM", devolver true
	        return secondFromIndex != -1;
	    }

	 // Devolver false si no se encuentra "FROM"
	    return false;
	}
	
	/**
	 * Clase utilitaria para leer archivos Excel (.xlsx) y convertirlos en estructuras de datos manejables.
     *
     * Permite convertir el contenido de una hoja de cálculo en una lista de mapas,
     * donde cada mapa representa una fila con los nombres de las columnas como claves
     * y sus valores correspondientes.
	 * @author Joaquin Aguilar
	 * @param filePath ruta donde se localizan los archivos excel que se van a leer.
	 * @date 22/08/2025
	 */	
	
	public static class ExcelReader {
		 /**
	     * Lee un archivo Excel (.xlsx) y lo convierte en una lista de mapas clave-valor.
	     * 
	     * Cada fila del archivo se convierte en un {@code Map<String, String>},
	     * donde la clave corresponde al nombre de la columna y el valor a la celda correspondiente.
	     * 
	     * @author Joaquin Aguilar
	     * @param filePath Ruta completa del archivo Excel que se desea leer.
	     * @return Lista de mapas donde cada mapa representa una fila del Excel.
	     * @throws IOException Si ocurre un error al abrir o leer el archivo Excel.
	     * @date 22/08/2025
	     */
	    public static List<Map<String, String>> readExcelToMap(String filePath) throws IOException {
	        List<Map<String, String>> resultList = new ArrayList<>();

	        try (FileInputStream fis = new FileInputStream(filePath);
	             Workbook workbook = new XSSFWorkbook(fis)) {

	            Sheet sheet = workbook.getSheetAt(0);
	            Iterator<Row> rowIterator = sheet.iterator();

	            // Obtener encabezados
	            List<String> headers = new ArrayList<>();
	            if (rowIterator.hasNext()) {
	                Row headerRow = rowIterator.next();
	                for (Cell cell : headerRow) {
	                    headers.add(cell.getStringCellValue().trim());
	                }
	            }

	            // Leer cada fila
	            while (rowIterator.hasNext()) {
	                Row row = rowIterator.next();
	                Map<String, String> rowMap = new HashMap<>();

	                for (int i = 0; i < headers.size(); i++) {
	                    Cell cell = row.getCell(i);
	                    String cellValue = "";
	                    if (cell != null) {
	                        switch (cell.getCellType()) {
	                            case STRING:
	                                cellValue = cell.getStringCellValue();
	                                break;
	                            case NUMERIC:
	                                cellValue = String.valueOf((int) cell.getNumericCellValue());
	                                break;
	                            case BOOLEAN:
	                                cellValue = String.valueOf(cell.getBooleanCellValue());
	                                break;
	                            case FORMULA:
	                                cellValue = cell.getCellFormula();
	                                break;
	                            default:
	                                cellValue = "";
	                        }
	                    }
	                    rowMap.put(headers.get(i), cellValue.trim());
	                }

	                resultList.add(rowMap);
	            }
	        }

	        return resultList;
	    }
	}
	
	/**
     * Clase encargada de ejecutar el procedimiento almacenado (Stored Procedure)
     * relacionado con la generación del reporte de ventas (Ventas - "V") en Snowflake.
     * 
     * Utiliza autenticación mediante clave privada en formato DER para conectarse
     * al entorno Snowflake de QA, y ejecutar un SP que genera los datos requeridos
     * para validaciones automáticas de reportes.
     * 
	 * @author Joaquin Aguilar
	 * @param periodoInicial mes de inicio del trimestre consultado
	 * @param periodoFinal mes final del trimestre consultado
	 * @date 22/08/2025
	 */	

	public static class SPExecutorV {
		 /**
	     * Ejecuta el Stored Procedure encargado de generar el reporte de ventas en Snowflake.
	     * 
	     * Este método establece una conexión mediante autenticación con clave privada,
	     * prepara y ejecuta el SP definido, y retorna el estado o resultado de su ejecución.
	     * 
	     * @author Joaquin Aguilar
	     * @return {@code true} si la ejecución del SP fue exitosa, {@code false} en caso contrario.
	     * @throws Exception si ocurre un error al conectarse a Snowflake o ejecutar el SP.
	     * @date 22/08/2025
	     */

	    public static List<Map<String, Object>> ejecutarSPReporteVentas(String periodoInicial, String periodoFinal) throws Exception {
	        List<Map<String, Object>> resultadoSP = new ArrayList<>();

	        // Ruta al archivo .der (clave privada en formato PKCS#8 DER)
	        String privateKeyPath = "C:\\Central\\FrameworkIMA\\src\\test\\java\\ReportesDatalake_MVP3\\om\\private_key.der";

	        // Leer clave privada desde archivo .der
	        byte[] keyBytes = readBinaryFile(privateKeyPath);
	        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory kf = KeyFactory.getInstance("RSA");
	        PrivateKey privateKey = kf.generatePrivate(spec);

	        // Parámetros de conexión
	        String url = "jdbc:snowflake://oxxomexico-oxxoadmin.snowflakecomputing.com/";
	        Properties props = new Properties();
	        props.put("user", "USR_QA_ECLIPSE");
	        props.put("role", "RL_QA_ECLIPSE");
	        props.put("warehouse", "WH_QA_ECLIPSE");
	        props.put("db", "DBZDEV_MEX_OXXO");
	        props.put("schema", "LAND_DAS_CAT");
	        props.put("privatekey", privateKey);

	        try (Connection conn = DriverManager.getConnection(url, props)) {

	            // Ejecutar SP
	            CallableStatement stmt = conn.prepareCall("CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_VENTAS(?, ?)");
	            stmt.setString(1, periodoInicial);
	            stmt.setString(2, periodoFinal);
	            stmt.execute();
	            stmt.close();

	            // Leer resultados
	            String query = "SELECT * FROM DBZDEV_MEX_OXXO.LAND_EBS_CAT.REP_VENTAS";
	            Statement queryStmt = conn.createStatement();
	            ResultSet rs = queryStmt.executeQuery(query);

	            ResultSetMetaData meta = rs.getMetaData();
	            int columnCount = meta.getColumnCount();

	            while (rs.next()) {
	                Map<String, Object> fila = new HashMap<>();
	                for (int i = 1; i <= columnCount; i++) {
	                    String columnName = meta.getColumnName(i);
	                    Object valor = rs.getObject(i);
	                    fila.put(columnName, valor);
	                }
	                resultadoSP.add(fila);
	            }

	            rs.close();
	            queryStmt.close();

	        } catch (SQLException e) {
	            System.out.println("Error al ejecutar SP o consulta: " + e.getMessage());
	            throw e;
	        }

	        return resultadoSP;
	    }

	    /**
	     * Lee el contenido binario completo de un archivo, utilizado para cargar claves privadas (.der).
	     * 
	     * Este método se usa como apoyo para leer la clave privada que autentica la conexión
	     * hacia Snowflake mediante certificado.
	     * 
	     * @author Joaquin Aguilar
	     * @param filePath Ruta completa del archivo binario (.der o .pem) a leer.
	     * @return Arreglo de bytes que contiene el contenido binario del archivo.
	     * @throws IOException Si el archivo no puede leerse completamente o no existe.
	     * @date 22/08/2025
	     */
	    private static byte[] readBinaryFile(String filePath) throws IOException {
	        File file = new File(filePath);
	        byte[] data = new byte[(int) file.length()];
	        FileInputStream fis = new FileInputStream(file);
	        int read = fis.read(data);
	        fis.close();
	        if (read != data.length) {
	            throw new IOException("No se pudo leer completamente el archivo: " + filePath);
	        }
	        return data;
	    }
	}
	/**
	 * Clase encargada de ejecutar el procedimiento almacenado (Stored Procedure)
	 * relacionado con la generación del reporte de compras (Compras - "C") en Snowflake.
	 * 
	 * Utiliza autenticación mediante clave privada en formato DER para conectarse
	 * al entorno Snowflake de QA y ejecutar el SP correspondiente.
	 * 
	 * @author Joaquin Aguilar
     * @date 22/08/2025
     */
	public static class SPExecutorC {
		
     /**
     * Ejecuta el Stored Procedure encargado de generar el reporte de compras en Snowflake.
	 * 
	 * Establece conexión segura mediante clave privada, ejecuta el SP definido
	 * y retorna el resultado de su ejecución. 
	 * 
	 * @author Joaquin Aguilar
     * @return {@code true} si el SP se ejecutó correctamente, {@code false} en caso contrario.
	 * @throws Exception si ocurre un error durante la conexión o ejecución del SP.
	 * @date 22/08/2025
	 */

		   public static List<Map<String, Object>> ejecutarSPReporteCompras(String periodoInicial, String periodoFinal) throws Exception {
		        List<Map<String, Object>> resultadoSP = new ArrayList<>();

		        // Ruta al archivo .der (clave privada en formato PKCS#8 DER)
		        String privateKeyPath = "C:\\Central\\FrameworkIMA\\src\\test\\java\\ReportesDatalake_MVP3\\om\\private_key.der";

		        // Leer clave privada desde archivo .der
		     byte[] keyBytes = readBinaryFile(privateKeyPath);
		     PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		     KeyFactory kf = KeyFactory.getInstance("RSA");
		     PrivateKey privateKey = kf.generatePrivate(spec);

	        // Parámetros de conexión
	        String url = "jdbc:snowflake://oxxomexico-oxxoadmin.snowflakecomputing.com/";
	        Properties props = new Properties();
	        props.put("user", "USR_QA_ECLIPSE");
	        props.put("role", "RL_QA_ECLIPSE");
	        props.put("warehouse", "WH_QA_ECLIPSE");
	        props.put("db", "DBZDEV_MEX_OXXO");
	        props.put("schema", "LAND_DAS_CAT");
	        props.put("privatekey", privateKey);

	        try (Connection conn = DriverManager.getConnection(url, props)) {

	            // Ejecutar el SP
	            CallableStatement stmt = conn.prepareCall("CALL SP_REPORTE_COMPRAS(?, ?)");
	            stmt.setString(1, periodoInicial);
	            stmt.setString(2, periodoFinal);
	            stmt.execute();
	            stmt.close();

	            // Consultar el resultado del SP
	            String queryConsulta = "SELECT * FROM DBZDEV_MEX_OXXO.LAND_EBS_CAT.REP_COMPRAS";
	            Statement queryStmt = conn.createStatement();
	            ResultSet rs = queryStmt.executeQuery(queryConsulta);

	            ResultSetMetaData meta = rs.getMetaData();
	            int columnCount = meta.getColumnCount();

	            while (rs.next()) {
	                Map<String, Object> fila = new HashMap<>();
	                for (int i = 1; i <= columnCount; i++) {
	                    String columnName = meta.getColumnName(i);
	                    Object valor = rs.getObject(i);
	                    fila.put(columnName, valor);
	                }
	                resultadoSP.add(fila);
	            }

	            rs.close();
	            queryStmt.close();

	        } catch (SQLException e) {
	            System.out.println("Error al ejecutar SP o consulta: " + e.getMessage());
	            throw e;
	        }

	        return resultadoSP;
	    }
		   
	    /**
	    * Lee un archivo binario (.der o .pem) y devuelve su contenido en bytes.
	    * 
	    * Este método es utilizado para cargar la clave privada de autenticación
		* al conectarse a la base de datos Snowflake.
	    * 
        * @author Joaquin Aguilar
	    * @param filePath Ruta completa del archivo que contiene la clave privada.
	    * @return Contenido del archivo en formato binario.
	    * @throws IOException si el archivo no puede leerse completamente o no existe.
	    * @date 22/08/2025
	    */
	    private static byte[] readBinaryFile(String filePath) throws IOException {
	        File file = new File(filePath);
	        byte[] data = new byte[(int) file.length()];
	        FileInputStream fis = new FileInputStream(file);
	        int read = fis.read(data);
	        fis.close();
	        if (read != data.length) {
	            throw new IOException("No se pudo leer completamente el archivo: " + filePath);
	        }
	        return data;
	    }
	}
	
	 /**
     * Clase utilitaria para la creación de conexiones a bases de datos específicas.
     * Contiene métodos estáticos que facilitan la conexión a entornos definidos como DES.
     * Conexión para DES (Oracle).
     * 
     * @return SQLUtil instancia configurada para conectarse a DES.
     * @throws Exception si ocurre algún error al crear la conexión.
	 * @author Angel Ramos 
	 * @Date 06/10/2025 
	  */
	public static SQLUtil createDESConnection() throws Exception {
        String host = "10.184.37.102";
        String port = "1521";
        String service = "FCDASSIT";
        String fullHost = host + ":" + port + "/" + service;

        return new SQLUtil(
            fullHost,
            "UsuQAI24043",
            "Comercio.1_Oxxo.2025*"
        );
    }
		    
	  /**
	  * Clase utilitaria para establecer conexiones a la base de datos Snowflake
      * utilizando autenticación mediante clave privada.
      * 
      * Esta clase contiene métodos para crear una conexión segura a Snowflake,
      * así como una utilidad para leer archivos binarios (como claves privadas).
      *  
	  * @author Angel Ramos 
	  * @Date 02/10/2025
	  */	    
	 public static class conexionsnow {

		    // Conexión para Snowflake
		    public static Connection createSnowflakeConnection() throws Exception {
		        String privateKeyPath = "C:\\Central\\FrameworkIMA\\src\\test\\java\\ReportesDatalake_MVP3\\om\\private_key.der";

		        // Leer clave privada desde archivo
		        byte[] keyBytes = readBinaryFile(privateKeyPath);
		        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		        KeyFactory kf = KeyFactory.getInstance("RSA");
		        PrivateKey privateKey = kf.generatePrivate(spec);

		     // Configurar los parámetros de conexión a Snowflake
		        String url = "jdbc:snowflake://oxxomexico-oxxoadmin.snowflakecomputing.com/";
		        Properties props = new Properties();
		        props.put("user", "USR_QA_ECLIPSE");
		        props.put("role", "RL_QA_ECLIPSE");
		        props.put("warehouse", "WH_QA_ECLIPSE");
		        props.put("db", "DBZDEV_MEX_OXXO");
		        props.put("schema", "LAND_DAS_CAT");
		        props.put("privateKey", privateKey);
		        
		        // Crear y retornar la conexión
		        Connection conn = DriverManager.getConnection(url, props);
		        return conn;  // Devuelve la conexión establecida
		    }
		    /**
		     * Lee el contenido binario completo de un archivo especificado y lo devuelve
		     * como un arreglo de bytes.
		     * 
		     * Este método se utiliza principalmente para leer claves privadas o
		     * certificados almacenados en formato binario (.der o .pem) que se requieren
		     * para autenticación en Snowflake u otros servicios.
		     * 
		     * @author Angel Ramos
		     * @param filePath Ruta completa del archivo a leer.
		     * @return {@code byte[]} arreglo de bytes que contiene el contenido binario del archivo.
		     * @throws IOException si el archivo no puede leerse completamente o no existe.
		     * @date 02/10/2025
		     */

		 // Método alternativo para leer archivos binarios 
		    private static byte[] readBinaryFile(String filePath) throws IOException {
		        File file = new File(filePath);
		        byte[] data = new byte[(int) file.length()];
		        FileInputStream fis = new FileInputStream(file);
		        int read = fis.read(data);
		        fis.close();
		        if (read != data.length) {
		            throw new IOException("No se pudo leer completamente el archivo: " + filePath);
		        }
		        return data;
		     }
		    }
	      
	 
	 /**
	  * Ejecuta una consulta COUNT(*) sobre la tabla ORDLOC con JOIN a ORDHEAD,
	  * filtrando los últimos 3 años según CREATE_DATETIME.
	  *
	  * @param dbDES    Objeto SQLUtil con conexión activa a EBS/RMS.
	  * @param testCase Objeto TestCase para registrar evidencia.
	  * @return El número total de registros encontrados; retorna -1 en caso de error
	  * @author Angel Ramos 
	  * @Date 06/10/2025
	  */
	 public static int obtenerConteoDesdeEBSRMS(SQLUtil dbDES, TestCase testCase) {
		    String query = "SELECT COUNT(*) AS TOTAL FROM ("
		            + " SELECT /*+ PARALLEL(h 5) */"
		            + " ol.ORDER_NO,"
		            + " ol.ITEM,"
		            + " ol.LOCATION,"
		            + " ol.LOC_TYPE,"
		            + " ol.UNIT_RETAIL,"
		            + " ol.QTY_ORDERED,"
		            + " ol.QTY_PRESCALED,"
		            + " ol.QTY_RECEIVED,"
		            + " ol.LAST_RECEIVED,"
		            + " ol.LAST_ROUNDED_QTY,"
		            + " ol.LAST_GRP_ROUNDED_QTY,"
		            + " ol.QTY_CANCELLED,"
		            + " ol.CANCEL_CODE,"
		            + " ol.CANCEL_DATE,"
		            + " ol.CANCEL_ID,"
		            + " ol.ORIGINAL_REPL_QTY,"
		            + " ol.UNIT_COST,"
		            + " ol.UNIT_COST_INIT,"
		            + " ol.COST_SOURCE,"
		            + " ol.NON_SCALE_IND,"
		            + " ol.TSF_PO_LINK_NO,"
		            + " ol.ESTIMATED_INSTOCK_DATE"
		            + " FROM RMS.ORDHEAD h"
		            + " JOIN RMS.ORDLOC ol ON ol.ORDER_NO = h.ORDER_NO"
		            + " WHERE trunc(h.CREATE_DATETIME) >= TRUNC(SYSDATE) - 90"
		            + ")";

		    try {
		        SQLResult result = dbDES.executeQuery(query);
		        List<String[]> data = result.getAllData();

		        if (data == null || data.isEmpty() || data.get(0).length == 0) {
		            System.err.println("La consulta no devolvió resultados válidos.");
		            return -1;
		        }

		        int total = Integer.parseInt(data.get(0)[0]);

		        // Captura de evidencia del resultado del COUNT
		        if (testCase != null) {
		            testCase.addTextEvidenceCurrentStep("Resultado COUNT(*) de JOIN ORDLOC y ORDHEAD: " + total);
		        }

		        return total;

		    } catch (SQLException e) {
		        e.printStackTrace();
		        if (testCase != null) {
		            testCase.addTextEvidenceCurrentStep("Error SQL: " + e.getMessage());
		        }
		        return -1;
		    } catch (NumberFormatException e) {
		        System.err.println("Error al convertir el resultado a entero: " + e.getMessage());
		        if (testCase != null) {
		            testCase.addTextEvidenceCurrentStep("Error al convertir COUNT a entero: " + e.getMessage());
		        }
		        return -1;
		    }
		}

	 /**
	  * Obtiene el total de registros del día anterior en la base de datos RMS
	  * realizando un JOIN entre las tablas ORDHEAD y ORDLOC.
	  *
	  * @param obtenerRegistrodiaayrRMS instancia de  que gestiona la conexión y ejecución de queries sobre RMS.
	  * @param testCase instancia de utilizada para registrar evidencias dentro del flujo de prueba.
	  * @return número total de registros encontrados (COUNT), o -1 si ocurre algún error o la consulta no devuelve datos.
	  * @author Angel Ramos 
	  * @Date 07/11/2025
	  */
public static int obtenerRegistrodiaayrRMS(SQLUtil dbDES, TestCase testCase) {
    String query = "SELECT COUNT(*) AS TOTAL FROM ("
            + " SELECT /*+ PARALLEL(h 5) */"
            + " ol.ORDER_NO,"
            + " ol.ITEM,"
            + " ol.LOCATION,"
            + " ol.LOC_TYPE,"
            + " ol.UNIT_RETAIL,"
            + " ol.QTY_ORDERED,"
            + " ol.QTY_PRESCALED,"
            + " ol.QTY_RECEIVED,"
            + " ol.LAST_RECEIVED,"
            + " ol.LAST_ROUNDED_QTY,"
            + " ol.LAST_GRP_ROUNDED_QTY,"
            + " ol.QTY_CANCELLED,"
            + " ol.CANCEL_CODE,"
            + " ol.CANCEL_DATE,"
            + " ol.CANCEL_ID,"
            + " ol.ORIGINAL_REPL_QTY,"
            + " ol.UNIT_COST,"
            + " ol.UNIT_COST_INIT,"
            + " ol.COST_SOURCE,"
            + " ol.NON_SCALE_IND,"
            + " ol.TSF_PO_LINK_NO,"
            + " ol.ESTIMATED_INSTOCK_DATE"
            + " FROM RMS.ORDHEAD h"
            + " JOIN RMS.ORDLOC ol ON ol.ORDER_NO = h.ORDER_NO"
            + " WHERE trunc(h.CREATE_DATETIME) >= TRUNC(SYSDATE) - 1"
            + ")";

    try {
        SQLResult result = dbDES.executeQuery(query);
        List<String[]> data = result.getAllData();

        if (data == null || data.isEmpty() || data.get(0).length == 0) {
            System.err.println("La consulta no devolvió resultados válidos.");
            return -1;
        }

        int total = Integer.parseInt(data.get(0)[0]);

        // Captura de evidencia del resultado del COUNT
        if (testCase != null) {
            testCase.addTextEvidenceCurrentStep("Resultado COUNT(*) de JOIN ORDLOC y ORDHEAD: " + total);
        }

        return total;

    } catch (SQLException e) {
        e.printStackTrace();
        if (testCase != null) {
            testCase.addTextEvidenceCurrentStep("Error SQL: " + e.getMessage());
        }
        return -1;
    } catch (NumberFormatException e) {
        System.err.println("Error al convertir el resultado a entero: " + e.getMessage());
        if (testCase != null) {
            testCase.addTextEvidenceCurrentStep("Error al convertir COUNT a entero: " + e.getMessage());
        }
        return -1;
    }
}
    


/**
 * Mapea un tipo de dato de base de datos a un tipo genérico utilizado en el sistema.
 * 
 * Este método toma como entrada el nombre de un tipo de dato (por ejemplo: VARCHAR, NUMBER, DATE)
 * y devuelve una cadena genérica que representa su categoría: "STRING", "NUMBER" o "DATE".
 * 
 * Si el tipo no se reconoce, se devuelve el mismo valor original.
 * Si el parámetro es nulo, se devuelve una cadena vacía.
 *
 * @param tipo tipo de dato original proveniente de la base de datos (por ejemplo, VARCHAR, NUMBER, DATE, etc.).
 * @return tipo de dato genérico ("STRING", "NUMBER" o "DATE"), o el valor original si no coincide con ningún caso conocido.
 * @author Angel Ramos
 * @date 10/11/2025
 */
 // ================= MÉTODO AUXILIAR PARA MAPEAR TIPOS =================
public static String mapTipo(String tipo) {
	if (tipo == null)
		return "";
	tipo = tipo.toUpperCase();

	// Tipos de texto
	if (tipo.contains("CHAR") || tipo.equals("TEXT") || tipo.equals("VARCHAR2") || tipo.equals("VARCHAR")) {
		return "STRING";
	}
	// Tipos numéricos
	else if (tipo.equals("NUMBER") || tipo.equals("DECIMAL") || tipo.equals("NUMERIC") || tipo.equals("FLOAT")) {
		return "NUMBER"; // <-- FLOAT se considera equivalente a NUMBER
	}
	// Tipos de fecha
	else if (tipo.contains("DATE") || tipo.contains("TIMESTAMP")) {
		return "DATE";
	}

	return tipo; // otros tipos se comparan tal cual
}
}

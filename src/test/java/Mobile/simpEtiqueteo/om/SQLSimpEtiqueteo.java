package Mobile.simpEtiqueteo.om;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.FrameworkException;
import io.appium.java_client.AppiumDriver;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


/**
 * DESCRIPCION: Clase para metodos relacionados a consultas de BDD para proyecto Simplificacion Etiqueteo
 * 
 * @author Mario A. De Leon Rdz.
 * @date 15/octubre/2024
 */
public class SQLSimpEtiqueteo {

	AppiumDriver driver;
	TestCase testCase;
	SQLResult sqlResult;
	SQLUtil sqlUtil;
	BaseExecution baseExecution;
	
	public SQLSimpEtiqueteo (TestCase testCase, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.baseExecution = baseExecution;
		
		//this.sqlResult = sqlResult
	}	
	//XPOS variables
		public final String DB_USER_XPOS = "sa";
		public final String DB_PASSWORD_XPOS = PasswordUtil.decryptPassword("3B93B9AEAA4D85DEF36A0BDAD00DED44");

	/**
	 * Método para realizar consultas a BD SQL, proviene de frameworkXPOS.
	 * @param query - cadena de texto que contiene el query a ejecutar
	 * @param bd - variable global de bd
	 * @param user - variable global de user de bd
	 * @param password - variable global de password de bd
	 */
	public SQLResult sqlExecuteFrameworkXpos(String query, String bd, String user, String password) throws SQLException {
		//Crear conexión
		Connection conn = DriverManager.getConnection(bd, user, password);
		//Ejecutar consulta
		Statement statement = conn.createStatement();
		
		if(statement.execute(query)) {
			SQLResult res = new SQLResult(conn, query);
			return res;
		}else {
			return null;
		}	
	}
	/**
	 * Ejecuta una consulta SQL en la base de datos y registra evidencia del resultado.
	 * 
	 * Utiliza un bloque try-with-resources para gestionar automáticamente la conexión y la declaración SQL.
	 * Si la consulta se ejecuta correctamente, devuelve un objeto SQLResult y agrega evidencia al caso de prueba.
	 * 
	 * @param query     Consulta SQL a ejecutar.
	 * @param bd        URL de la base de datos.
	 * @param user      Usuario de la base de datos.
	 * @param password  Contraseña de la base de datos.
	 * @return SQLResult con el resultado de la consulta o null si no hubo resultado.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta.
	 */
	public SQLResult sqlExecute(String query, String bd, String user, String password) throws SQLException {
	    // Declarar fuera del bloque para su uso en el return
	    SQLResult res = null;

	    // try-with-resources para cerrar automáticamente Connection y Statement
	    try (Connection conn = DriverManager.getConnection(bd, user, password);
	         Statement statement = conn.createStatement()) {

	        if (statement.execute(query)) {
	            res = new SQLResult(conn, query); // Si es necesario devolver la conexión activa
	            testCase.addQueryEvidenceCurrentStep(res);
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	        throw e; // Relanzar la excepción para manejarla externamente
	    }

	    return res; // Devuelve el resultado o null si no hubo resultado
	}
	/**
	 * Ejecuta una consulta SQL en la base de datos sin registrar evidencia del resultado.
	 * 
	 * Utiliza un bloque try-with-resources para gestionar automáticamente la conexión y la declaración SQL.
	 * Si la consulta se ejecuta correctamente, devuelve un objeto SQLResult sin agregar evidencia.
	 * 
	 * @param query     Consulta SQL a ejecutar.
	 * @param bd        URL de la base de datos.
	 * @param user      Usuario de la base de datos.
	 * @param password  Contraseña de la base de datos.
	 * @return SQLResult con el resultado de la consulta o null si no hubo resultado.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta.
	 */
	public SQLResult sqlExecuteSinEv(String query, String bd, String user, String password) throws SQLException {
	    // Declarar fuera del bloque para su uso en el return
	    SQLResult res = null;

	    // try-with-resources para cerrar automáticamente Connection y Statement
	    try (Connection conn = DriverManager.getConnection(bd, user, password);
	         Statement statement = conn.createStatement()) {

	        if (statement.execute(query)) {
	            res = new SQLResult(conn, query); // Si es necesario devolver la conexión activa
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	        throw e; // Relanzar la excepción para manejarla externamente
	    }

	    return res; // Devuelve el resultado o null si no hubo resultado
	}
	/**
	 * Método para imprimir en CONSOLA todos los resultados del query 
	 * @author Mario A. De León Rdz.
	 * @date 31/octubre/2024
	 * @throws FrameworkException 
	 */
	public void imprimirResult(SQLResult resultado) throws Exception {
		try {	
			List<String[]> allData = resultado.getAllData();

	        // Convierte cada String[] en una fila tabulada y une todas las filas con saltos de línea
	        String output = allData.stream()
	            .map(row -> String.join("\t", row))  // Convierte cada arreglo String[] en una línea
	            .collect(Collectors.joining("\n"));  // Une todas las líneas con saltos de línea

	        // Imprime todo el contenido al final
	        System.out.print(output);
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	/**
	 * Método para hacer consulta ejecutar query para Actualizar fecha de planogramacion 
	 * @author Mario A. De León Rdz.
	 * @date 17/octubre/2024
	 * @throws FrameworkException 
	 */	  
	public SQLResult sqlActualizaFechaPlanograma(String dbSatelites) throws Exception {
	    try {
	        String queryActualizaFechaPlanograma = "USE XposStore " +
	                "DECLARE @fecha VARCHAR(100) " +
	                "SET @fecha = CONVERT(VARCHAR, GETDATE() - 1, 111) " +
	                "EXEC Satelites.stl.SET_PARAM @Type = 'CONFIG', " +
	                "                            @Name = 'SIMP_ETIQ_IMP_PLANOG', " +
	                "                            @Value = @fecha, " +
	                "                            @Level = 'Company' " +
	                "SELECT VALUE " +
	                "FROM SYSTEM_PARAM_VALUE " +
	                "WHERE SYSTEM_PARAM_CATALOG_ID IN ( " +
	                "    SELECT SYSTEM_PARAM_CATALOG_ID " +
	                "    FROM SYSTEM_PARAM_CATALOG " +
	                "    WHERE CODE = 'SIMP_ETIQ_IMP_PLANOG')";
	        SQLResult result = sqlExecuteSinEv(queryActualizaFechaPlanograma, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        System.out.println("Se ejecuta query ActualizaFechaPlanograma: "+result);
	        return result;
	        
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para hacer consulta ejecutar query para Actualizar fecha de planogramacion 
	 * @author Mario A. De León Rdz.
	 * @date 17/octubre/2024
	 * @throws FrameworkException 
	 */	  
	public SQLResult sqlActualizaFechaPlanogramaConEv(String dbSatelites) throws Exception {
	    try {
	        String queryActualizaFechaPlanograma = "USE XposStore " +
	                "DECLARE @fecha VARCHAR(100) " +
	                "SET @fecha = CONVERT(VARCHAR, GETDATE() - 1, 111) " +
	                "EXEC Satelites.stl.SET_PARAM @Type = 'CONFIG', " +
	                "                            @Name = 'SIMP_ETIQ_IMP_PLANOG', " +
	                "                            @Value = @fecha, " +
	                "                            @Level = 'Company' " +
	                "SELECT VALUE " +
	                "FROM SYSTEM_PARAM_VALUE " +
	                "WHERE SYSTEM_PARAM_CATALOG_ID IN ( " +
	                "    SELECT SYSTEM_PARAM_CATALOG_ID " +
	                "    FROM SYSTEM_PARAM_CATALOG " +
	                "    WHERE CODE = 'SIMP_ETIQ_IMP_PLANOG')";
	        SQLResult result = sqlExecute(queryActualizaFechaPlanograma, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        System.out.println("Se ejecuta query ActualizaFechaPlanograma: "+result);
	        return result;
	        
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para hacer consulta ejecutar query para Actualizar fecha de planogramacion 
	 * @author Mario A. De León Rdz.
	 * @date 23/enero/2025
	 * @throws Exception
	 */	  
	public SQLResult sqlActualizaFechaPlanogramaMovil(String dbUrl) throws Exception {
		try {
			//DriverManager.getDriver("jdbc:sqlserver://10.200.10.17:1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true");
			Connection conn = DriverManager.getConnection(dbUrl, DB_USER_XPOS, DB_PASSWORD_XPOS);
			String queryActualizaFechaPlanograma = "USE XposStore " +
	                "DECLARE @fecha VARCHAR(100) " +
	                "SET @fecha = CONVERT(VARCHAR, GETDATE() - 1, 111) " +
	                "EXEC Satelites.stl.SET_PARAM @Type = 'CONFIG', " +
	                "                            @Name = 'SIMP_ETIQ_IMP_PLANOG', " +
	                "                            @Value = @fecha, " +
	                "                            @Level = 'Company' " +
	                "SELECT VALUE " +
	                "FROM SYSTEM_PARAM_VALUE " +
	                "WHERE SYSTEM_PARAM_CATALOG_ID IN ( " +
	                "    SELECT SYSTEM_PARAM_CATALOG_ID " +
	                "    FROM SYSTEM_PARAM_CATALOG " +
	                "    WHERE CODE = 'SIMP_ETIQ_IMP_PLANOG')";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(queryActualizaFechaPlanograma);
			SQLResult result = new SQLResult(conn, queryActualizaFechaPlanograma);
			this.imprimirResult(result);
			return result;
		} catch (SQLException e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage() + " SQLState: " + e.getSQLState() + " ErrorCode: " + e.getErrorCode());
	        throw new Exception("Error al ejecutar el query: " + e.getMessage(), e);
	    }
	}
	
	/**
	 * Metodo para consultar LEGOS en SIV3 
	 * @param plaza Plaza del punto de venta Ej. 10MON
	 * @param tienda Tienda del punto de venta Ej. 50ADI
	 * @param serverSIV3 url del servidor
	 * @param usernameSIV3 usuario con el que se va a acceder al servidor
	 * @param passwordSIV3 contraseña del usuario
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 31/octubre/2024
	 * @throws FrameworkException 
	 */	  
	public SQLResult sqlLegosSIV3(String plaza, String tienda, String serverSIV3, String usernameSIV3, String passwordSIV3) throws Exception {
		try {	
			String query = "SELECT l.* "
					+ "FROM XXVA_SIV3.xxva_eng_legos_mv l, XXVA_SIV3.xxva_sem_sembrado s, XXVA_SIV3.xxva_sem_sembrado_mueble m "
					+ "WHERE l.legos_id  = m.legos_id "
					+ "AND      s.sembrado_id = m.sembrado_id "
					+ "AND      s.status    = 3 "
					+ "AND l.PLANOGRAMABLE='SI' "
					+ "AND      s.plaza_id  = '"+plaza+"' "
					+ "AND      s.tienda_id = '"+tienda+"' ";
			SQLUtil dbSIV3 = new SQLUtil(serverSIV3, usernameSIV3, passwordSIV3);
		    SQLResult resultSIV3 = baseExecution.executeQuery(dbSIV3, query);
		    testCase.addQueryEvidenceCurrentStep(resultSIV3);
			System.out.println("Se ejecuta query en SIV3 con exito");
			imprimirResult(resultSIV3);
			return resultSIV3;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Metodo para consultar planogramas en SIV3
	 * @param sku SKU del articulo por consultar planograma
	 * @param plaza Plaza del punto de venta Ej. 10MON
	 * @param tienda Tienda del punto de venta Ej. 50ADI
	 * @param serverSIV3 url del servidor
	 * @param usernameSIV3 usuario con el que se va a acceder al servidor
	 * @param passwordSIV3 contraseña del usuario
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 31/octubre/2024
	 * @throws FrameworkException 
	 */	  
	public SQLResult sqlPlanogramaSIV3(String sku, String plaza, String tienda, String serverSIV3, String usernameSIV3, String passwordSIV3) throws Exception {
	    try {	
	        String query = "SELECT   ROWNUM row_num, x.* "
	                + "FROM    ( "
	                + "  SELECT   REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(l.planogrupo_desc,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') planogrupo, "
	                + "           REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(l.mueble_tienda_desc,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') mueble, "
	                + "           a.charola, "
	                + "           a.ubicacion_bandeja, "
	                + "           a.num_frentes frentes, "
	                + "           a.item sku, "
	                + "           l.legos_desc lego " // Se incluye la columna lego
	                + "  FROM     XXVA_SIV3.xxva_eng_planograma_aprobado a, "
	                + "           XXVA_SIV3.xxva_eng_legos_mv l, "
	                + "           XXVA_SIV3.xxva_sem_sembrado s, "
	                + "           XXVA_SIV3.xxva_sem_sembrado_mueble m "
	                + "  WHERE    a.item IN ('" + sku + "') "
	                + "  AND      l.legos_id  = a.legos_id "
	                + "  AND      l.legos_id  = m.legos_id "
	                + "  AND      s.sembrado_id = m.sembrado_id "
	                + "  AND      s.plaza_id  = '" + plaza + "' "
	                + "  AND      s.tienda_id = '" + tienda + "' "
	                + "  AND      s.status    = 3 "
	                + "  ORDER BY l.legos_desc, a.charola, a.ubicacion_bandeja "
	                + ") x";
	        
	        SQLUtil dbSIV3 = new SQLUtil(serverSIV3, usernameSIV3, passwordSIV3);
	        SQLResult resultSIV3 = baseExecution.executeQuery(dbSIV3, query);
	        testCase.addQueryEvidenceCurrentStep(resultSIV3);
	        System.out.println("Se ejecuta query en SIV3 con exito");
	        imprimirResult(resultSIV3);
	        return resultSIV3;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Metodo para consultar planogramas de VARIOS SKUs en SIV3 
	 * @param sku SKU del articulo por consultar planograma
	 * @param plaza Plaza del punto de venta Ej. 10MON
	 * @param tienda Tienda del punto de venta Ej. 50ADI
	 * @param serverSIV3 url del servidor
	 * @param usernameSIV3 usuario con el que se va a acceder al servidor
	 * @param passwordSIV3 contraseña del usuario
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 08/noviembre/2024
	 * @throws FrameworkException 
	 */	  
	public SQLResult sqlPlanogramaSIV3(List<String> skus, String plaza, String tienda, String serverSIV3, String usernameSIV3, String passwordSIV3) throws Exception {
	    try {	
	        String skuList = skus.stream()
	                             .map(sku -> "'" + sku + "'")
	                             .collect(Collectors.joining(", "));
	                             
	        String query = "SELECT ROWNUM row_num, x.*"
	                + "  FROM    ("
	                + "  SELECT   REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (l.planogrupo_desc   ,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') planogrupo"
	                + "  ,        REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (l.mueble_tienda_desc,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') mueble"
	                + "  ,        a.charola"
	                + "  ,        a.ubicacion_bandeja"
	                + "  ,        a.num_frentes frentes"
	                + "  ,        a.item sku"
	                + "  ,        s.tienda_id tienda"
	                + "  FROM     XXVA_SIV3.xxva_eng_planograma_aprobado a"
	                + "  ,        XXVA_SIV3.xxva_eng_legos_mv l"
	                + "  ,        XXVA_SIV3.xxva_sem_sembrado s"
	                + "  ,        XXVA_SIV3.xxva_sem_sembrado_mueble m"
	                + "  WHERE    a.item IN (" + skuList + ")"
	                + "  AND      l.legos_id  = a.legos_id"
	                + "  AND      l.legos_id  = m.legos_id"
	                + "  AND      s.sembrado_id = m.sembrado_id"
	                + "  AND      s.plaza_id  = '" + plaza + "'"
	                + "  AND      s.tienda_id = '" + tienda + "'"
	                + "  AND      s.status    = 3"
	                + "  ORDER BY  a.item desc "
	                + "  ) x";
	                
	        SQLUtil dbSIV3 = new SQLUtil(serverSIV3, usernameSIV3, passwordSIV3);
	        SQLResult resultSIV3 = baseExecution.executeQuery(dbSIV3, query);
	        testCase.addQueryEvidenceCurrentStep(resultSIV3);
	        System.out.println("\nSe ejecuta query en SIV3 con éxito para múltiples SKUs\n");
	        imprimirResult(resultSIV3);
	        return resultSIV3;
	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para validar que los resultados de dos consultas sean iguales.
	 * Ojo: Revisar en ambos queries que se ordenen los resultados de la misma manera, ej. ORDER BY SKU DESC.
	 * @param resultadoConcentradora Resultado de la consulta a tablas Concentradoras.
	 * @param resultadoSIV3 Resultado de la consulta a BD SIV3.
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @date 08/noviembre/2024
	 * @throws Exception Si hay diferencias entre los resultados de ambas consultas.
	 */	  
	public void validacionConcentradoraVsSiv3(SQLResult resultadoConcentradora, SQLResult resultadoSIV3) throws Exception {
	    try {	
	        // Validar que ambos resultados tengan el mismo número de filas
	        if (resultadoConcentradora.getRowCount() != resultadoSIV3.getRowCount()) {
	            throw new Exception("Diferente número de filas entre resultados: " + 
	                                resultadoConcentradora.getRowCount() + " vs " + resultadoSIV3.getRowCount());
	        }

	        // Comparar fila por fila
	        for (int i = 0; i < resultadoConcentradora.getRowCount(); i++) {
	            String skuConcentradora = resultadoConcentradora.getData(i, "SKU");
	            String skuSIV3 = resultadoSIV3.getData(i, "SKU");
	            if (!skuConcentradora.equals(skuSIV3)) {
	                throw new Exception("Diferencia en SKU en la fila " + i + ": " + skuConcentradora + " vs " + skuSIV3);
	            }

	            String planoConcentradora = resultadoConcentradora.getData(i, "PLANOGROUP");
	            String planoSIV3 = resultadoSIV3.getData(i, "PLANOGRUPO");
	            if (!planoConcentradora.equals(planoSIV3)) {
	                throw new Exception("Diferencia en PLANOGRUPO en la fila " + i + ": " + planoConcentradora + " vs " + planoSIV3);
	            }

	            String trayConcentradora = resultadoConcentradora.getData(i, "TRAY");
	            String traySIV3 = resultadoSIV3.getData(i, "CHAROLA");
	            if (!trayConcentradora.equals(traySIV3)) {
	                throw new Exception("Diferencia en TRAY en la fila " + i + ": " + trayConcentradora + " vs " + traySIV3);
	            }

	            String locationConcentradora = resultadoConcentradora.getData(i, "LOCATION");
	            String locationSIV3 = resultadoSIV3.getData(i, "UBICACION_BANDEJA");
	            if (!locationConcentradora.equals(locationSIV3)) {
	                throw new Exception("Diferencia en LOCATION en la fila " + i + ": " + locationConcentradora + " vs " + locationSIV3);
	            }
	        }

	        System.out.println("Validación exitosa: Los resultados son iguales.");

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error en validación: " + e.getMessage());
	    }
	}

	
	/**
	 * Metodo para validar que la fecha de cambio de precio en DS50 y XPOS_STORE sea la misma
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @date 19/noviembre/2024
	 * @throws Exception 
	 */	  
	public void validarFechaDeCambioDePrecio(SQLResult fechaDS50, SQLResult fechaXposStore) throws Exception {
	    try {	
	        // Parsear las fechas a LocalDate
	        LocalDate fechaDS50Date = LocalDate.parse(fechaDS50.getData(0, "MODIFIED_DATETIME").substring(0, 10));
	        LocalDate fechaXposStoreDate = LocalDate.parse(fechaXposStore.getData(0, "LAST_PRICE_CHANGE").substring(0, 10));

	        // Comparar las fechas y lanzar excepción si no coinciden
	        if (!fechaDS50Date.equals(fechaXposStoreDate)) {
	            String errorMsg = "No coincide la fecha " + fechaDS50Date + " con " + fechaXposStoreDate;
	            testCase.addCodeEvidenceCurrentStep(errorMsg);
	            throw new Exception(errorMsg);
	        }

	        // Mensaje opcional si la validación pasa
	        System.out.println("Las fechas coinciden correctamente: " + fechaDS50Date);

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}

	/**
	 * Método para formatear precios eliminando ceros innecesarios.
	 * @param precioString Precio en formato String, e.g., "0.0000", "23.7500".
	 * @return String formateado, e.g., "0", "23.75".
	 * @author Mario A. De León Rdz.
	 *  @date 19/noviembre/2024
	 */
	public String formatPrecio(String precioString) {
	    if (precioString == null || precioString.isEmpty()) {
	        return "0"; // Valor predeterminado en caso de datos nulos o vacíos
	    }
	    // Convierte el valor a BigDecimal y redondea a un máximo de dos decimales
	    BigDecimal precio = new BigDecimal(precioString).setScale(2, RoundingMode.HALF_UP);

	    // Elimina ceros innecesarios después del punto decimal
	    return precio.stripTrailingZeros().toPlainString();
	}
	
    /**
     * Método para formatear precios eliminando ceros innecesarios,
     * pero asegurando siempre dos decimales en la salida.
     * @param precioString Precio en formato String, e.g., "0.0000", "23.7500".
     * @return String formateado con dos decimales fijos, e.g., "0.00", "23.75".
     * @author Mario A. De León Rdz.
	 * @date 19/noviembre/2024
     */
	public String formatPrecioDosDecimales(String precioString) {
	    if (precioString == null || precioString.isEmpty()) {
	        return "0"; // Valor predeterminado en caso de datos nulos o vacíos
	    }
	    // Convierte el valor a BigDecimal y mantiene dos decimales
	    BigDecimal precio = new BigDecimal(precioString).setScale(2, RoundingMode.HALF_UP);

	    return precio.toString(); // Mantiene los dos decimales
	}
	/**
	 * Método para consultar planogramas de VARIOS SKUs en SIV3 
	 * @param sku SKU del artículo por validar en RMS
	 * @return SQLResult Regresa resultado de la consulta
	 * @throws Exception 
	 */	  
	public SQLResult validarArticuloEnRMS(String sku) throws Exception {
	    try {                             
	        String query = "SELECT ITEM, STATUS, ITEM_DESC, ITEM_DESC_SECONDARY, SHORT_DESC FROM RMS.ITEM_MASTER WHERE ITEM = '" + sku + "'";
	        SQLUtil dbRMS = new SQLUtil("10.184.37.47:1521/FCMOMUAT", "nreader", PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007"));
	        SQLResult result = baseExecution.executeQuery(dbRMS, query);
	        
	        testCase.addQueryEvidenceCurrentStep(result);
	        System.out.println("Se ejecuta query en RMS con éxito para múltiples SKUs");
	        imprimirResult(result);
	        
	        // Validación manual en lugar de Assert
	        String resultadoSku = result.getData(0, "SKU");
	        if (resultadoSku == null || !resultadoSku.equals(sku)) {
	            throw new Exception("Diferencia en SKU o resultado Nulo. Esperado: " + sku + ", Obtenido: " + resultadoSku);
	        }
	        
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Metodo para buscar un SKU que se encuentre en la BD de RMS
	 * @param sku SKU del articulo por validar en RMS
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 09/diciembre/2024
	 * @throws FrameworkException 
	 */	  
	public SQLResult buscarSKULibreEnRMS() throws Exception {
	    try {	
	                             
	        String query = "SELECT * FROM "
	        		+ "RMS.ITEM_MASTER ITEM_DESC "
	        		+ "WHERE ITEM LIKE '75%' "
	        		+ "ORDER  BY ITEM DESC ";
	        SQLUtil dbRMS = new SQLUtil("10.184.37.47:1521/FCMOMUAT", "nreader", PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007"));
	        SQLResult result = baseExecution.executeQuery(dbRMS, query);
	        System.out.println("Se ejecuta query en RMS con éxito para múltiples SKUs");
	        //imprimirResult(result);
	        return result;
	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para ejecutar query para encendido de la impresora virtual
	 * @author Mario A. De León Rdz.
	 * @date 21/octubre/2024
	 * @throws FrameworkException 
	 */	   
	public void encenderImpresoraVirtual(String dbXposStore) throws Exception {
		try {	
			String queryDeEncendido = 
				    "USE [XposStore] " +
				    "SET ANSI_NULLS ON " +
				    "SET QUOTED_IDENTIFIER ON " +
				    "BEGIN TRY " +
				    "BEGIN TRANSACTION " +
				    "IF (EXISTS ( " +
				    "   SELECT 1 FROM [SYSTEM_PARAM_CATALOG] (NOLOCK) AS cat " +
				    "   INNER JOIN [SYSTEM_PARAM_VALUE] (NOLOCK) AS val ON cat.[SYSTEM_PARAM_CATALOG_ID] = val.[SYSTEM_PARAM_CATALOG_ID] " +
				    "   WHERE cat.[CODE] = 'VIRT_PRINT_ACTIVE' AND val.[VALUE] = 'FALSE' " +
				    ")) " +
				    "BEGIN " +
				    "   UPDATE val " +
				    "   SET val.[VALUE] = 'TRUE', " +
				    "       val.[MODIFIED_DATETIME] = GETDATE(), " +
				    "       val.[MODIFIED_BY_OPERATOR_ID] = 5, " +
				    "       val.[LCOUNT] = val.LCOUNT + 1 " +
				    "   FROM [SYSTEM_PARAM_CATALOG] AS cat " +
				    "   INNER JOIN [SYSTEM_PARAM_VALUE] AS val ON cat.[SYSTEM_PARAM_CATALOG_ID] = val.[SYSTEM_PARAM_CATALOG_ID] " +
				    "   WHERE cat.[CODE] = 'VIRT_PRINT_ACTIVE' " +
				    "   PRINT 'Se actualiza VIRT_PRINT_ACTIVE A TRUE' " +
				    "END " +
				    "COMMIT TRANSACTION " +
				    "END TRY " +
				    "BEGIN CATCH " +
				    "   ROLLBACK TRANSACTION " +
				    "   THROW " +
				    "END CATCH";
			String queryValidacionEncendido = 
				    "SELECT val.[VALUE] " +
				    "FROM [SYSTEM_PARAM_CATALOG] AS cat " +
				    "INNER JOIN [SYSTEM_PARAM_VALUE] AS val ON cat.[SYSTEM_PARAM_CATALOG_ID] = val.[SYSTEM_PARAM_CATALOG_ID] " +
				    "WHERE cat.[CODE] = 'VIRT_PRINT_ACTIVE'";
			//Ejecucion primer query
			sqlExecute(queryDeEncendido, dbXposStore, DB_USER_XPOS, DB_PASSWORD_XPOS);
			//Validacion
			SQLResult resultValidacionEncendido = sqlExecuteSinEv(queryValidacionEncendido, dbXposStore, DB_USER_XPOS, DB_PASSWORD_XPOS);
			String valorEncendido = resultValidacionEncendido.getData(0, 0);
			System.out.println("Impresora virtual encendida: " + valorEncendido);
			if ("FALSE".equalsIgnoreCase(valorEncendido)) {
				testCase.addCodeEvidenceCurrentStep("Error: No se pudo encender la impresora virtual");
				throw new Exception("Error: No se pudo encender la impresora virtual");
			};
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Metodo para buscar un SKU que se encuentre en la BD de RMS
	 * @param sku SKU del articulo por validar en RMS
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 06/enero/2025
	 * @throws FrameworkException 
	 */	  
	public Boolean validarSKUEnRMS(String sku) throws Exception {
	    try {	
	                             
	        String query = "SELECT * FROM "
	        		+ "RMS.ITEM_MASTER ITEM_DESC "
	        		+ "WHERE ITEM = '"+sku+"'"
	        		+ "ORDER  BY ITEM DESC ";
	        SQLUtil dbRMS = new SQLUtil("10.184.37.47:1521/FCMOMUAT", "nreader", PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007"));
	        SQLResult result = baseExecution.executeQuery(dbRMS, query);
	        System.out.println("Se ejecuta query en RMS con éxito para múltiples SKUs");
	        boolean existeEnRMS = false;
	        if (result.getRowCount()>0) {
	        	existeEnRMS = true;
	        }
	        //imprimirResult(result);
	        return existeEnRMS;
	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Metodo para buscar VARIOS SKU que se encuentre en la BD de RMS
	 * @param sku SKU del articulo por validar en RMS
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 06/enero/2025
	 * @throws FrameworkException 
	 */	  
	public String validarMultiplesSKUEnRMS(SQLResult consultaAConcentradora) throws Exception {
	    try {
			for(int i = 0; i<consultaAConcentradora.getRowCount(); i++) {
				String sku = consultaAConcentradora.getData(i, "SKU");
				if(validarSKUEnRMS(sku)) {
					System.out.println(sku);
	                return sku; // Regresa el primer SKU válido encontrado
					}
			};
			// Si no se encuentra ningún SKU válido, se retorna un string vacío
			return "";
	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Metodo para utilizar el primer SKU que se encuentre en la BD de RMS
	 * @param sku SKU del articulo por validar en RMS
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 06/enero/2025
	 * @throws FrameworkException 
	 */	  
	public SQLResult utilizarSKUEnRMS(SQLResult consultaAConcentradora, String dbSatelites) throws Exception {
	    try {
			for(int i = 0; i<consultaAConcentradora.getRowCount(); i++) {
				String sku = consultaAConcentradora.getData(i, "SKU");
				if(validarSKUEnRMS(sku)) {
					System.out.println(sku);
					String query = "SELECT SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, TRAY, LOCATION, FRONTS "
				            + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE "
				            + "SKU = '"+sku+"' ";
					 SQLResult concentradoraSKUElegido= sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS); // Regresa el primer SKU válido encontrado
					 imprimirResult(concentradoraSKUElegido);
					 return concentradoraSKUElegido;
				}
			};
			// Si no se encuentra ningún SKU válido, se retorna un string vacío
			return null;
	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Metodo para utilizar el primer SKU que se encuentre en la BD de RMS
	 * @param sku SKU del articulo por validar en RMS
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 06/enero/2025
	 * @throws FrameworkException 
	 */	  
	public SQLResult utilizarSKUEnRMS(SQLResult consultaAConcentradora, String dbSatelites, String ordenOpcional) throws Exception {
	    try {
			for(int i = 0; i<consultaAConcentradora.getRowCount(); i++) {
				String sku = consultaAConcentradora.getData(i, "SKU");
				if(validarSKUEnRMS(sku)) {
					System.out.println(sku);
					String query = "SELECT SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
				            + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE "
				            + "SKU = '"+sku+"' "+ordenOpcional+" ";
					 SQLResult concentradoraSKUElegido= sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS); // Regresa el primer SKU válido encontrado
					 imprimirResult(concentradoraSKUElegido);
					 return concentradoraSKUElegido;
				}
			};
			// Si no se encuentra ningún SKU válido, se retorna un string vacío
			return null;
	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Metodo para utilizar los primeros SKU que se encuentre en la BD de RMS segun la cantidad especificada
	 * @param sku SKU del articulo por validar en RMS
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Regresa resultado de la consulta
	 * @date 06/enero/2025
	 * @throws FrameworkException 
	 */	  
	public List<SQLResult> utilizarMultiplesSKUEnRMS(SQLResult consultaAConcentradora, String dbSatelites, String ordenOpcional, int skusSolicitados) throws Exception {
	    try {
	        int skusEncontradosEnRMS = 0;
	        List<SQLResult> resultados = new ArrayList<>();

	        for (int i = 0; i < consultaAConcentradora.getRowCount() && skusEncontradosEnRMS < skusSolicitados; i++) {
	            String sku = consultaAConcentradora.getData(i, "SKU");

	            if (validarSKUEnRMS(sku)&&skusEncontradosEnRMS<skusSolicitados) {
	                System.out.println("SKU válido encontrado: " + sku);

	                String query = "SELECT SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, TRAY, LOCATION, FRONTS "
	                        + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '" + sku + "' " + ordenOpcional;

	                SQLResult concentradoraSKUElegido = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	                imprimirResult(concentradoraSKUElegido);
	                
	                resultados.add(concentradoraSKUElegido);
	                skusEncontradosEnRMS++;
	            }
	        }
	        return resultados; // Devuelve la lista con los resultados obtenidos (puede ser vacía si no hay coincidencias)
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para consultar los primeros 10 registros de la tabla CO_LB_INTL_OX filtrados por los parámetros proporcionados.
	 * 
	 * @param statusCode Si es true, filtra por "ACTIVE_ITEM_STATUS", si es false, filtra por "INACTIVE_ITEM_STATUS".
	 * @param inventory Si es true, filtra por INVENTORY > 0; si es false, filtra por INVENTORY = 0.
	 * @param planogroup Si es true, filtra por PLANOGROUP IS NULL; si es false, filtra por PLANOGROUP IS NOT NULL.
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult consultaAConcentradoraSinEv(boolean statusCode, boolean inventory, boolean planogroup, String dbSatelites) throws Exception {
	    try {
	    	// Construcción del query SQL
	        String query = "SELECT TOP 10 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU "
	            + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE "
	            + "STATUS_CODE = '" + (statusCode ? "ACTIVE_ITEM_STATUS" : "INACTIVE_ITEM_STATUS") + "' AND "
	            + "INVENTORY " + (inventory ? ">" : "=") + " 0 AND "
	            + "PLANOGROUP " + (planogroup ? "IS NOT" : "IS") + " NULL";
	        
	        System.out.println("Se ejecuta query de tabla concentradora");
	        
	        return sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para consultar los primeros 10 registros de la tabla CO_LB_INTL_OX filtrados por los parámetros proporcionados.
	 * 
	 * @param statusCode Si es true, filtra por "ACTIVE_ITEM_STATUS", si es false, filtra por "INACTIVE_ITEM_STATUS".
	 * @param inventory Si es true, filtra por INVENTORY > 0; si es false, filtra por INVENTORY = 0.
	 * @param planogroup Si es true, filtra por PLANOGROUP IS NULL; si es false, filtra por PLANOGROUP IS NOT NULL.
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult consultaAConcentradoraTop1(boolean statusCode, boolean inventory, boolean planogroup, String dbSatelites) throws Exception {
	    try {
	    	// Construcción del query SQL
	        String query = "SELECT TOP 1 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU "
	            + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE "
	            + "STATUS_CODE = '" + (statusCode ? "ACTIVE_ITEM_STATUS" : "INACTIVE_ITEM_STATUS") + "' AND "
	            + "INVENTORY " + (inventory ? ">" : "=") + " 0 AND "
	            + "PLANOGROUP " + (planogroup ? "IS NOT" : "IS") + " NULL "
	            		+ "ORDER BY PLANOGROUP ASC ";
	        
	      
	        SQLResult result  = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        testCase.addQueryEvidenceCurrentStep(result);
	        imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para consultar 1 sku en la tabla origen
	 * 
	 * @param sku
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult consultaAOrigen(String sku, String dbSatelites) throws Exception {
	    try {
	    	// Construcción del query SQL
	    	String query = "SELECT I.ITEM_ID, V.KA_CVE_VTA SKU, P.AMOUNT PRECIO, C.NAME STATUS, SUM(KA_UDS * M.MULTIPLIER) CANTIDAD_EN_INVENTARIO "
					+ "FROM SATELITES.IO.KA_KARDX_VW V, XposStore.dbo.MOVEMENT M, XposStore.dbo.CODE CO, XposStore.dbo.ITEM I, XposStore.dbo.CODE C, XposStore.dbo.ITEM_BUSINESS_UNIT_LINK IBUL, XposStore.dbo.price P "
					+ "WHERE V.KA_CVE_MOV = M.CODE "
					+ "AND CO.CODE_ID = M.MOVEMENT_TYPE_ID "
					+ "AND CO.NAME IN ('IN_UNIT_VALUE_MOVEMENT','INITIAL_MOVEMENT','OUT_UNIT_VALUE_MOVEMENT') "
					+ "AND V.KA_CVE_VTA = I.SKU "
					+ "AND I.ITEM_STATUS_CODE_ID = C.CODE_ID "
					+ "AND IBUL.ITEM_ID = I.ITEM_ID AND IBUL.IS_ACTIVE = 1 "
					+ "AND P.ITEM_BUSINESS_UNIT_LINK_ID = IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND P.IS_ACTIVE = 1 "
					+ "AND FROM_DATE>=(SELECT MAX(FROM_DATE) FROM XposStore.dbo.price p WHERE p.ITEM_BUSINESS_UNIT_LINK_ID=IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND p.is_active = 1) "
					+ "AND i.SKU='"+sku+"' "
					+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID "
					+ "ORDER BY V.KA_CVE_VTA ASC";
	        
	        System.out.println("Se ejecuta query de tabla origen");
	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para consultar 1 sku en la tabla concentradora
	 * 
	 * @param sku
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult consultarSKUEnConcentradora(String sku, String dbSatelites) throws Exception {
	    try {
	    	// Construcción del query SQL
			String query = "SELECT TOP 1 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
					+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '"+sku+"' ";
	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para consultar multiples skus en la tabla concentradora
	 * 
	 * @param skus deben de estar añadidos en un List<String>
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult consultarSKUEnConcentradora(List<String> skus, String dbSatelites) throws Exception {
	    try {
	    	String skuList = skus.stream()
                    .map(sku -> "'" + sku + "'")
                    .collect(Collectors.joining(", "));
	    	// Construcción del query SQL
			String query = "SELECT TOP 1 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS "
					+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU IN (" + skuList + ") ";
	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para consultar los primeros 10 registros de la tabla CO_LB_INTL_OX filtrados por los parámetros proporcionados.
	 * 
	 * @param sku
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 30/enero/2025
	 */
	public SQLResult consultaAOrigen(List<String> skus, String dbSatelites) throws Exception {
	    try {
	    	String skuList = skus.stream()
                    .map(sku -> "'" + sku + "'")
                    .collect(Collectors.joining(", "));
	    	// Construcción del query SQL
	    	String query = "SELECT I.ITEM_ID, V.KA_CVE_VTA SKU, P.AMOUNT PRECIO, C.NAME STATUS, SUM(KA_UDS * M.MULTIPLIER) CANTIDAD_EN_INVENTARIO "
					+ "FROM SATELITES.IO.KA_KARDX_VW V, XposStore.dbo.MOVEMENT M, XposStore.dbo.CODE CO, XposStore.dbo.ITEM I, XposStore.dbo.CODE C, XposStore.dbo.ITEM_BUSINESS_UNIT_LINK IBUL, XposStore.dbo.price P "
					+ "WHERE V.KA_CVE_MOV = M.CODE "
					+ "AND CO.CODE_ID = M.MOVEMENT_TYPE_ID "
					+ "AND CO.NAME IN ('IN_UNIT_VALUE_MOVEMENT','INITIAL_MOVEMENT','OUT_UNIT_VALUE_MOVEMENT') "
					+ "AND V.KA_CVE_VTA = I.SKU "
					+ "AND I.ITEM_STATUS_CODE_ID = C.CODE_ID "
					+ "AND IBUL.ITEM_ID = I.ITEM_ID AND IBUL.IS_ACTIVE = 1 "
					+ "AND P.ITEM_BUSINESS_UNIT_LINK_ID = IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND P.IS_ACTIVE = 1 "
					+ "AND FROM_DATE>=(SELECT MAX(FROM_DATE) FROM XposStore.dbo.price p WHERE p.ITEM_BUSINESS_UNIT_LINK_ID=IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND p.is_active = 1) "
					+ "AND i.SKU IN (" + skuList + ") "
					+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID "
					+ "ORDER BY V.KA_CVE_VTA ASC";
	        
	        System.out.println("Se ejecuta query de tabla origen");
	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        testCase.addQueryEvidenceCurrentStep(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para consultar los primeros 10 registros de la tabla CO_LB_INTL_OX filtrados por los parámetros proporcionados.
	 * 
	 * @param sku
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult consultaKrdxUnits(String sku, String dbSatelites) throws Exception {
	    try {
	    	// Construcción del query SQL
	    	String query = "SELECT * FROM Satelites.hc.FNKRDXUNITS('"+sku+"') ";
	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Ejecuta una consulta SQL en la base de datos Satélites para obtener información  
	 * de los primeros 4 productos activos con inventario mayor a 0 y bandeja 1.  
	 *  
	 * @author Mario De Leon
	 * @date 29/enero/2025  
	 * @param dbSatelites Nombre de la base de datos a consultar  
	 * @return 
	 * @throws Exception Si ocurre un error en la ejecución de la consulta  
	 */  
	public SQLResult consultaAConcentradoraSKUsConUnaUnicaCharola(String dbSatelites) throws Exception {  
	    try {  	    
	        // Construcción del query SQL  
	        String query = "SELECT TOP 4 SKU,NAME,STATUS_CODE,TRAY,LOCATION, COUNT(*) AS Repetidos FROM [Satelites].[dbo].[CO_LB_INTL_OX] \n"
	        		+ "WHERE LEN(SKU) >= 13 AND SKU LIKE '75%' \n"
	        		+ "GROUP BY SKU, NAME, STATUS_CODE,TRAY, LOCATION \n"
	        		+ "HAVING COUNT(*) = 1 AND TRAY>1 AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' \n"
	        		+ "ORDER BY SKU ASC ";  

	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);  
	        imprimirResult(result);
			return result;
	    } catch (Exception e) {  
	    	System.err.println("Error en consultaConcentradora: " + e.getMessage());  
	        e.printStackTrace();
	        throw e;  
	    }
	}
	/**
	 * Ejecuta una consulta SQL en la base de datos Satélites para obtener información  
	 * de los primeros 4 productos activos con inventario mayor a 0 y bandeja 1.  
	 *  
	 * @author Mario de Jesus Salazar y Mario De Leon
	 * @date 29/enero/2025  
	 * @param dbSatelites Nombre de la base de datos a consultar  
	 * @return 
	 * @throws Exception Si ocurre un error en la ejecución de la consulta  
	 */  
	public SQLResult consultaAConcentradoraSKUsConMasDeUnaCharola(String dbSatelites) throws Exception {  
	    try {  	    
	        // Construcción del query SQL  
	        String query = "SELECT TOP 1 SKU,NAME,STATUS_CODE,TRAY,LOCATION, COUNT(*) AS Repetidos FROM [Satelites].[dbo].[CO_LB_INTL_OX]  \\n\"\n" +
					"\t        \t\t+ \"WHERE LEN(SKU) >= 13 \\n\"\n" +
					"\t        \t\t+ \"GROUP BY SKU, NAME, STATUS_CODE, TRAY, LOCATION \\n\"\n" +
					"\t        \t\t+ \"HAVING COUNT(*) > 1 AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' \\n\"\n" +
					"\t        \t\t+ \"ORDER BY COUNT(*) ASC ";

	        SQLResult result = sqlExecute(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);  
	        imprimirResult(result);
			return result;
	    } catch (Exception e) {  
	    	System.err.println("Error en consultaConcentradora: " + e.getMessage());  
	        e.printStackTrace();
	        throw e;  
	    }
	} 
	/**
	 * Método para consultar los primeros 10 registros de la tabla CO_LB_INTL_OX filtrados por los parámetros proporcionados.
	 * 
	 * @param statusCode Si es true, filtra por "ACTIVE_ITEM_STATUS", si es false, filtra por "INACTIVE_ITEM_STATUS".
	 * @param inventory Si es true, filtra por INVENTORY > 0; si es false, filtra por INVENTORY = 0.
	 * @param planogroup Si es true, filtra por PLANOGROUP IS NULL; si es false, filtra por PLANOGROUP IS NOT NULL.
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult conseguirSKUSparaValidarEnRMS(boolean statusCode, boolean inventory, boolean planogroup, String dbSatelites) throws Exception {
	    try {
	    	// Construcción del query SQL
	        String query = "SELECT TOP 30 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU "
	            + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE "
	            + "STATUS_CODE = '" + (statusCode ? "ACTIVE_ITEM_STATUS" : "INACTIVE_ITEM_STATUS") + "' AND "
	            + "INVENTORY " + (inventory ? ">" : "=") + " 0 AND "
	            + "PLANOGROUP " + (planogroup ? "IS NOT" : "IS") + " NULL AND "
	            + "LEN(SKU) >= 13 AND SKU LIKE '7%' ";
	        System.out.println("Se ejecuta query de tabla concentradora");
	        SQLResult result = sqlExecuteSinEv(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para consultar los primeros 10 registros de la tabla CO_LB_INTL_OX filtrados por los parámetros proporcionados.
	 * 
	 * @param statusCode Si es true, filtra por "ACTIVE_ITEM_STATUS", si es false, filtra por "INACTIVE_ITEM_STATUS".
	 * @param inventory Si es true, filtra por INVENTORY > 0; si es false, filtra por INVENTORY = 0.
	 * @param planogroup Si es true, filtra por PLANOGROUP IS NULL; si es false, filtra por PLANOGROUP IS NOT NULL.
	 * 
	 * @return SQLResult El resultado de la consulta SQL.
	 * @throws FrameworkException Si ocurre un error al ejecutar la consulta.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2025
	 */
	public SQLResult conseguirSKUSparaValidarEnRMS(boolean statusCode, boolean inventory, boolean planogroup, String dbSatelites, String filtrosAdicionales) throws Exception {
	    try {
	    	// Construcción del query SQL
	        String query = "SELECT TOP 30 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU "
	            + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE "
	            + "STATUS_CODE = '" + (statusCode ? "ACTIVE_ITEM_STATUS" : "INACTIVE_ITEM_STATUS") + "' AND "
	            + "INVENTORY " + (inventory ? ">" : "=") + " 0 AND "
	            + "PLANOGROUP " + (planogroup ? "IS NOT" : "IS") + " NULL AND "
	            + filtrosAdicionales + " AND "
	            + "LEN(SKU) >= 13 AND SKU LIKE '75%' ";
	        System.out.println("Se ejecuta query de tabla concentradora");
	        SQLResult result = sqlExecuteSinEv(query, dbSatelites, DB_USER_XPOS, DB_PASSWORD_XPOS);
	        imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
}
package EBS.AutRegistrosContables.om;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.FrameworkException;
import modelo.TestCase;
import EBS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION:Clase de utileria para manejo de proyecto Automatización Registros Contables.
 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
 * @date 20/04/2025
 */	 
public class AutRegContables {


	/**
	 * Método que consulta en la Base de Oracle si existe la factura.
	 * @param token - token que se pasa en el header
	 * @return Numero de Factura
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @date 20/04/2025
	 */	 
	public static String obtenerFacturaExcluido(HashMap<String, String> data,String Factura,String Proveedor) throws Exception {
		String Retorno;
		Retorno="";

		try {

			String ValidCuentas = "select PO_NUMBER,INVOICE_NUM,status from APPS.XXFC_AP_INVOICES_STG_ALL\r\n"
					+ "where ( status = 'I') and INVOICE_NUM="+Factura;
			/*
			 * UTILERIAS
			 */
			utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
					GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);			

			SQLResult ValidChangeExec = dbAvebqa.executeQuery(ValidCuentas);
			String cuenta = "";

			boolean ValidChangeRes = ValidChangeExec.isEmpty();
			if (!ValidChangeRes) {
				cuenta = ValidChangeExec.getData(0, "INVOICE_NUM");
				//System.out.println("Factura : " + cuenta);
				Retorno= " Factura encontrada con exito: " + Factura +  " Proveedor:" + Proveedor;
				//testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
			} else if (ValidChangeRes) {
				Retorno= " Factura No encontrada con exito: " + Factura +  " Proveedor:" + Proveedor;
			}		
		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al ejecutar la consulta o al procesar el Artículo.    -0");
		}        

		try {

			String ValidCuentas_FL = "SELECT a.ID,a.SOCIO_COMERCIAL,a.INVOICE_NUM,b.LINE_ID,b.HEADER_ID\r\n"
					+ "FROM APPS.XXFC_AP_INVOICES_STG_ALL a, APPS.XXFC_AP_INVLINES_STG_ALL b\r\n"
					+ "WHERE a.id=b.header_id and INVOICE_NUM= '"+Factura+"'";

			/*
			 * UTILERIAS
			 */
			utils.sql.SQLUtil dbAvebqa_FL = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
					GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);


			System.out.println(ValidCuentas_FL);
			SQLResult ValidChangeExec_FL = dbAvebqa_FL.executeQuery(ValidCuentas_FL);
			String cuenta_FL = "";
			System.out.println("Antes del consulta ejecuta: 1" + ValidChangeExec_FL);

			System.out.println("Antes del consulta ejecuta: 1");		

			try {
			} catch (Exception e) {
				e.printStackTrace();
			}		


			System.out.println("Antes del consulta ejecuta: 2");		

			boolean ValidChangeRes_FL = ValidChangeExec_FL.isEmpty();
			if (!ValidChangeRes_FL) {
				cuenta_FL = ValidChangeExec_FL.getData(0, "INVOICE_NUM");
				Retorno= " Factura encontrada con exito: " + cuenta_FL + " Proveedor: "+ Proveedor;
			} else if (ValidChangeRes_FL) {
				Retorno= " Factura No encontrada con exito: "+ Factura + " Proveedor: " + Proveedor;
			}

			System.out.println("Despues del consulta ejecuta: ");

		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al ejecutar la consulta o al procesar el Artículo.    0");
		}
		return Retorno;		
	}		

	/**
	 * Método que consulta en la Base de Oracle si existe la factura.
	 * @author Bernardo Alonso Mejia Ortiz
	 * @return Retorno-Numero de Factura
	 * @throws Exception 
	 * @date 22/04/2025
	 */
	public static String obtenerFacturaExcluido(String Factura,String Proveedor) throws Exception {
		String Retorno;
		Retorno="";

		try {


			String ValidCuentas = "select PO_NUMBER,INVOICE_NUM,status from APPS.XXFC_AP_INVOICES_STG_ALL\r\n"
					+ "where ( status = 'I') and INVOICE_NUM='"+ Factura.trim() +"' and PO_NUMBER= '"+ Proveedor.trim() + "' \r\n";

			utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
					GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
			SQLResult ValidChangeExec = dbAvebqa.executeQuery(ValidCuentas);
			String cuenta = "";

			boolean ValidChangeRes = ValidChangeExec.isEmpty();
			if (!ValidChangeRes) {
				cuenta = ValidChangeExec.getData(0, "INVOICE_NUM");
				Retorno= Factura;
				;
			} else if (ValidChangeRes) {
				Retorno= Factura;
			}

		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al ejecutar la consulta o al procesar el Artículo.    -0");
		}

		try {

			String ValidCuentas_FL = "SELECT a.ID,a.SOCIO_COMERCIAL,a.INVOICE_NUM,b.LINE_ID,b.HEADER_ID\r\n"
					+ "FROM APPS.XXFC_AP_INVOICES_STG_ALL a, APPS.XXFC_AP_INVLINES_STG_ALL b\r\n"
					+ "WHERE a.id=b.header_id and a.PO_NUMBER = '"+Proveedor+"' and INVOICE_NUM= '"+Factura+"' ";

			utils.sql.SQLUtil dbAvebqa_FL = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
					GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

			System.out.println(ValidCuentas_FL);
			SQLResult ValidChangeExec_FL = dbAvebqa_FL.executeQuery(ValidCuentas_FL);
			String cuenta_FL = "";

			try {
			} catch (Exception e) {

				e.printStackTrace();
			}		

			boolean ValidChangeRes_FL = ValidChangeExec_FL.isEmpty();
			if (!ValidChangeRes_FL) {
				cuenta_FL = ValidChangeExec_FL.getData(0, "INVOICE_NUM");
				Retorno=  cuenta_FL;		
			} else if (ValidChangeRes_FL) {
				Retorno= Factura;
			}

		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al ejecutar la consulta o al procesar el Artículo.    0");
		}
		return Retorno;		 
	}

	
	
	/**
	 * Obtiene el ultimo ID solicitud ejecutado
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @author juan.lopez@grupotimexico.com
	 * @return StatusConc 
	 * @date 27/04/2025
	 */
	public String obtenerStatusConcurrente (String Concurrente) throws Exception {
		try{

			String StatusConc;
			SQLResult idSolic;	
			String queryConcu = "SELECT DISTINCT t.user_concurrent_program_name,\n"
					+ "                r.REQUEST_ID,\n"
					+ "                TO_CHAR(r.ACTUAL_START_DATE, 'dd-mm-yy hh24:mi:ss') AS Started,\n"
					+ "                TO_CHAR(r.ACTUAL_COMPLETION_DATE, 'dd-mm-yy hh24:mi:ss') AS Completed,\n"
					+ "                DECODE(r.PHASE_CODE, 'C', 'Completed', 'I', 'Inactive', 'P', 'Pending', 'R', 'Running', 'NA') AS phasecode,\n"
					+ "                DECODE(r.STATUS_CODE, 'A', 'Waiting', 'B', 'Resuming', 'C', 'Normal', 'D', 'Cancelled', 'E', 'Error', \n"
					+ "                                    'F', 'Scheduled', 'G', 'Warning', 'H', 'On Hold', 'I', 'Normal', 'M', 'No Manager', \n"
					+ "                                    'Q', 'Standby', 'R', 'Normal', 'S', 'Suspended', 'T', 'Terminating', 'U', 'Disabled', \n"
					+ "                                    'W', 'Paused', 'X', 'Terminated', 'Z', 'Waiting') AS Status\n"
					+ "FROM apps.fnd_concurrent_requests r,\n"
					+ "     apps.fnd_concurrent_programs p,\n"
					+ "     apps.fnd_concurrent_programs_tl t\n"
					+ "WHERE r.CONCURRENT_PROGRAM_ID = p.CONCURRENT_PROGRAM_ID\n"
					+ "  AND r.PROGRAM_APPLICATION_ID = p.APPLICATION_ID\n"
					+ "  AND t.concurrent_program_id = r.concurrent_program_id\n"
					+ "  AND t.user_concurrent_program_name LIKE '"+Concurrente+"'\n"
					+ "ORDER BY r.REQUEST_ID DESC\n"
					+ "FETCH FIRST 1 ROWS ONLY";


			utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

			idSolic = ebsOracle.executeQuery(queryConcu);
			StatusConc = idSolic.getData(0,"PHASECODE").toString();

			System.out.println("Estatus:" + StatusConc);
			Boolean validacion = false;            
			if (idSolic.getRowCount()> 0) {				 				
				validacion = true;				 
			}			
			return StatusConc;
		}catch(Exception e) {
			System.out.println("ERROR:" );
			throw new Exception("Error: " + e.getMessage());
		}

	}

	/**
	 * Método para esperar a que termine de ejecutarse un concurrente		
	 * @throws Exception 
	 * @throws true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 30/04/2025
	 */	
	public boolean ChecarStatusConc(String Concurrente) throws Exception {    	
		try {
			int maxIntentos = 10;
			SQLResult StatusConc;

			for (int i = 0; i < maxIntentos; i++) {	        
				String Status = obtenerStatusConcurrente(Concurrente); 
				System.out.println("Status: " + Status);
				if ("Completed".equals(Status)) {
					System.out.println("El microservicio ya se ejecuto: " + Status);
					return true;
				} else {
					Thread.sleep(5000); 
					System.out.println("Aun no termina el micoservicio: " + Status + "intento" + maxIntentos);
				}

			}

			System.out.println("Se alcanzó el número máximo de intentos sin completar el proceso.");
			return false;

		}catch(Exception e) {
			System.out.println("Error de consulta .." );
			throw new Exception("Error: " + e.getMessage());
		}

	}

	/**
	 * Obtiene el estatus del microservicio01
	 * @param username usarioBD, password credenciales para acceder da la BD 		  
	 * @author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @return StatusConc
	 * @throws FrameworkException 
	 * @since 28/04/2025
	 */
	public static String obtenerStatusMicroservicio01 () throws Exception {
		try{

			String StatusConc;
			SQLResult idSolic;	
			String queryConcu = "select PO_NUMBER,INVOICE_NUM,status from APPS.XXFC_AP_INVOICES_STG_ALL\n"
					+ "where ( status = 'I')";	

			utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
			System.out.println("query:" + queryConcu);
			idSolic = ebsOracle.executeQuery(queryConcu);			
//			testCase.addQueryEvidenceCurrentStep(idSolic);
			StatusConc = idSolic.getData(0,"status").toString();

			System.out.println("Estatus:" + StatusConc);
			Boolean validacion = false;            
			if (idSolic.getRowCount()> 0) {				 				
				validacion = true;				 
			}			
			return StatusConc;
		}catch(Exception e) {
			System.out.println("ERROR:" );
			throw new Exception("Error: " + e.getMessage());
		}

	}		

	/**
	 * Método para esperar a que termine de ejecutarse un concurrente		
	 * @throws Exception 
	 * @throws true/false
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 */	
	public boolean checarStatusM01() throws Exception {    	
		try {
			int maxIntentos = 50;
			// Bucle para esperar a que se ejecute el microservicio01
			for (int i = 0; i < maxIntentos; i++) {	        
				String status = obtenerStatusMicroservicio01();
				System.out.println("Status M1: " + status);
				if (! status.equals("0") ) {
					System.out.println("El Microservicio01 se ha ejecutado: " + status);
					return true;
				} else {
					Thread.sleep(5000); 
					System.out.println("Aun no termina el Microservicio01: " + status + "intento" + maxIntentos);
				}

			}

			System.out.println("Se alcanzó el número máximo de intentos sin completar el proceso.");
			return false;

		}catch(Exception e) {
			System.out.println("Error de consulta .." );
			throw new Exception("Error: " + e.getMessage());
		}
	}				

	/**
	 * Obtiene el estatus de la ejecucion del microservicio
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @return StatusConc
	 * @throws FrameworkException 
	 * @since 28/04/2025
	 */
	public String obtenerStatusMicroservicio02 () throws Exception {
		try{

			String StatusConc;
			SQLResult idSolic;	
			StatusConc="0";
			String queryConcu = "SELECT ID,INVOICE_NUM\n"
					+ "FROM apps.XXFC_AP_RESPONSE_STG_ALL";

			utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

			idSolic = ebsOracle.executeQuery(queryConcu);	
//			testCase.addQueryEvidenceCurrentStep(idSolic);
			Boolean validacion = false;

			if (idSolic.getRowCount() > 0) {
				StatusConc = idSolic.getData(0,"ID").toString();
				System.out.println("Estatus:" + StatusConc);
				validacion = true;
			}			
			return StatusConc;
		}catch(Exception e) {
			System.out.println("Error de consulta .." );
			throw new Exception("Error: " + e.getMessage());
		}

	}		

	/**
	 * Método para esperar a que termine de ejecutarse el microservicio02		
	 * @throws Exception 
	 * @return true/false
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 */	
	public boolean ChecarStatusM02() throws Exception {    	
		try {
			int maxIntentos = 50;

			for (int i = 0; i < maxIntentos; i++) {	 
				String Status = obtenerStatusMicroservicio02();
				System.out.println("Status M2: " + Status);

				if (Status.equals("0")) {
					System.out.println("El Microservicio02 se ha ejecutado: " + Status);
					return true;
				} else {
					Thread.sleep(5000); 
					System.out.println("Aun no termina el Microservicio02: " + Status + "intento" + maxIntentos);
				}

			}

			System.out.println("Se alcanzó el número máximo de intentos sin completar el proceso.");
			return false;
		}catch(Exception e) {
			System.out.println("Error de consulta .." );
			throw new Exception("Error: " + e.getMessage());
		}

	}

	/**
	 * String donde almacena un paso grande para no afectar legibilidad del script
	 */
	private String stepFacturasWorkflow = "Tomar la captura de Facturas en el WorkFlow, usando el Appi.	---  WORKFLOW  -----	\r\n"
			+ "INVOICE	JSON TYPE,\r\n" + "numero_oc	TEXT,\r\n" + "socio_comercial	TEXT,\r\n"
			+ "nro_proveedor	TEXT,\r\n" + "fecha_factura	TEXT(DD/MM/aaaa),\r\n" + "nro_factura	TEXT(uuid),\r\n"
			+ "div_factura	TEXT,\r\n" + "importe_factura	TEXT(num decimal),\r\n" + "importe_impuesto	NUMBER,\r\n"
			+ "descripcion_servicio_contador	TEXT.";

	/**
	 * Metodo para que regrese el paso que al ser una cadena grande removeria
	 * legibilidad al script.
	 * 
	 * @return String que incluye el paso completo
	 * @author Guillermo Garcia
	 * 
	 */
	public String getStepFacturasWorkflow() {
		return this.stepFacturasWorkflow;
	}

	/**
	 * String donde almacena un paso grande para no afectar legibilidad del script
	 */
	private String stepMicroservicio = "Ejecutar el MICROSERVICIO   \"https://pre.iacostforce.mx/api/pagos_cedis/\"	\r\n"
			+ "Invoice	JAVA TYPE\r\n" + "order	String\r\n" + "provider	String\r\n" + "providerId	String\r\n"
			+ "date	LocalDate\r\n" + "invoice	String\r\n" + "currency	String\r\n" + "total	float\r\n"
			+ "taxes	float\r\n" + "description	String\r\n" + "id	int\r\n" + "createdAt	LocalDate\r\n"
			+ "createdBy	String\r\n" + "updatedAt	LocalDate\r\n" + "updatedBy	String\r\n"
			+ "loginUpdatedAt	LocalDate";

	/**
	 * Metodo para que regrese el paso que al ser una cadena grande removeria
	 * legibilidad al script.
	 * 
	 * @return String que incluye el paso completo
	 * @author Guillermo Garcia
	 * 
	 */
	public String getStepMicroservicio() {
		return this.stepMicroservicio;
	}

	/**
	 * String donde almacena un paso grande para no afectar legibilidad del script
	 */
	private String stepComparacion1 = "Comparar la informacion :   ----   WORKFLOW   -----   https://pre.iacostforce.mx/api/actualizar_status_pagos_cedis/   ----\r\n"
			+ "INVOICE	JSON TYPE	                Invoice	JAVA TYPE\r\n"
			+ "numero_oc	TEXT	                order	String\r\n"
			+ "socio_comercial	TEXT	                provider	String\r\n"
			+ "nro_proveedor	TEXT	                providerId	String\r\n"
			+ "fecha_factura	TEXT(DD/MM/aaaa)        date	        LocalDate\r\n"
			+ "nro_factura	TEXT(uuid)              invoice	String\r\n"
			+ "div_factura	TEXT	                currency     String\r\n"
			+ "importe_factura	TEXT(num decimal)       total	float\r\n"
			+ "importe_impuesto	NUMBER          taxes	float\r\n"
			+ "descripcion_servicio_contador	TEXT    description	String";

	/**
	 * Metodo para que regrese el paso que al ser una cadena grande removeria
	 * legibilidad al script.
	 * 
	 * @return String que incluye el paso completo
	 * @author Guillermo Garcia
	 * 
	 */
	public String getStepComparacion1() {
		return this.stepComparacion1;
	}

	/**
	 * Metodo que se utiliza para obtener los invoice numbers de la tabla de invoice
	 * 
	 * @param sqlResult con la tabla de invoice
	 * @return List<String> con los valores de invoice number en la tabla de invoice
	 * @author Guillermo Garcia
	 * @throws Exception
	 * @date 23/05/2025
	 */
	public List<String> getAllInvoices(SQLResult sqlResult) throws Exception {
		try {
			List<String> invoices = new ArrayList<>();
			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				invoices.add(sqlResult.getData(i, "INVOICE_NUM"));
			}
			return invoices;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	// Inicia bloque de utilidades para hacer mas legible la impresion de tablas

	/**
	 * Imprime columnas especificas de un queries simples
	 * 
	 * @author Guillermo Garcia
	 * @param dataBase     La conexion a la base de datos
	 * @param response     El response del query que se quiere imprimir de forma mas
	 *                     clara
	 * @param numOfColumns El total de columnas que se quiere por impresion
	 * @param testCase     la instancia de TestCase del framework
	 * @date 14/02/2025
	 */
	public void prettyPrint(SQLUtil dataBase, SQLResult response, int numOfColumns, TestCase testCase)
			throws SQLException {
		try {
			int realNumOfColumns = Math.max(numOfColumns, 1);
			String afterFromQuery = getAfterFromQuery(response.getQuery());

			int totalColumns = response.getColumnCount();
			realNumOfColumns = Math.min(realNumOfColumns, totalColumns); // Adjust if numOfColumns exceeds total columns
			List<String> columns = response.getColumnNames();

			int totalQueries = (int) Math.ceil((double) totalColumns / realNumOfColumns); // Ensure to handle any
																							// leftover columns
			int columnCount = 0;

			for (int i = 0; i < totalQueries; i++) {
				String temporalQuery = buildQuery(columns, columnCount, realNumOfColumns,
						hasTwoFrom(response.getQuery()));
				columnCount += realNumOfColumns;

				// Execute query and log result
				SQLResult temporalResponse = dataBase.executeQuery(temporalQuery + " " + afterFromQuery);
				testCase.addBoldTextEvidenceCurrentStep("Se muestra query " + i + " de " + totalQueries);
				testCase.addQueryEvidenceCurrentStep(temporalResponse);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	/**
	 * Helper method to get the portion of the query after "FROM"
	 * 
	 * @author Guillermo Garcia
	 * @param originalQuery el query original que se desea partir
	 * @date 14/02/2025
	 */
	private String getAfterFromQuery(String originalQuery) throws SQLException {
		try {
			// Find the index of the first occurrence of "FROM"
			int firstFromIndex = originalQuery.indexOf("FROM");

			if (firstFromIndex != -1) {
				// Find the index of the second "FROM" starting from the position just after the
				// first "FROM"
				int secondFromIndex = originalQuery.indexOf("FROM", firstFromIndex + 1);

				if (secondFromIndex != -1) {
					// If the second "FROM" exists, return the substring starting from the second
					// "FROM"
					return originalQuery.substring(secondFromIndex).trim();
				} else {
					// If no second "FROM" is found, return everything from the first "FROM"
					return originalQuery.substring(firstFromIndex).trim();
				}
			}

			// Return an empty string if no "FROM" is found
			return "";
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	/**
	 * Helper method to build the SELECT part of the query
	 * 
	 * @author Guillermo Garcia
	 * @param columns          Listado de las columnas del response original
	 * @param startIndex       El index que lleva el orden de la columna que se esta
	 *                         usando
	 * @param realNumOfColumns el total de columnas que se deben imprimir por tabla
	 * @date 14/02/2025
	 */
	private String buildQuery(List<String> columns, int startIndex, int realNumOfColumns, boolean numFrom)
			throws SQLException {
		try {
			String select = numFrom ? "SELECT * FROM (SELECT " : "SELECT ";
			StringBuilder queryBuilder = new StringBuilder(select);
			for (int j = 0; j < realNumOfColumns && (startIndex + j) < columns.size(); j++) {
				if (j > 0) {
					queryBuilder.append(", ");
				}
				queryBuilder.append(columns.get(startIndex + j));
			}
			return queryBuilder.toString();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	/**
	 * Helper method to get the portion of the query after "FROM"
	 * 
	 * @author Guillermo Garcia
	 * @param originalQuery el query original que se desea partir
	 * @date 14/02/2025
	 */
	private boolean hasTwoFrom(String originalQuery) throws SQLException {
		try {
			// Find the index of the first "FROM"
			int firstFromIndex = originalQuery.indexOf("FROM");

			if (firstFromIndex != -1) {
				// Find the index of the second "FROM" starting from the position just after the
				// first "FROM"
				int secondFromIndex = originalQuery.indexOf("FROM", firstFromIndex + 1);

				// If a second "FROM" exists, return true
				return secondFromIndex != -1;
			}

			// Return false if no "FROM" is found
			return false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	// Finaliza bloque de utilidades para mejorar la legibilidad de impresion de
	// tablas

	/**
	 * Metodo que se utiliza para obtener las lineas de la tabla de lines
	 * 
	 * @param sqlResult con la tabla de lines
	 * @return List<String[]> con los valores de: [0] CU_DISTRIBUTION [1]
	 *         DESCRIPTION [2] AMOUNT
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public List<String[]> getAllLines(SQLResult sqlResult) throws SQLException {
		try {
			List<String[]> invoices = new ArrayList<>();
			String[] lines = new String[3];
			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				lines[0] = sqlResult.getData(i, "CU_DISTRIBUTION");
				lines[1] = sqlResult.getData(i, "DESCRIPTION");
				lines[2] = sqlResult.getData(i, "AMOUNT");
				invoices.add(lines);
			}
			return invoices;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo que se utiliza para obtener los invoice numbers y las lineas del join
	 * de las tablas de lines e invoices
	 * 
	 * @param sqlResult con la consulta de join de las tablas de invoices y lines
	 * @return List<String[]> con los valores de: [0] INVOICE_NUM [1]
	 *         CU_DISTRIBUTION [2] DESCRIPTION [3] AMOUNT
	 * 
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public List<String[]> getAllLinesAndInvoices(SQLResult sqlResult) throws SQLException {
		try {
			List<String[]> invoices = new ArrayList<>();
			String[] lines = new String[4];
			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				lines[0] = sqlResult.getData(i, "INVOICE_NUM");
				lines[1] = sqlResult.getData(i, "CU_DISTRIBUTION");
				lines[2] = sqlResult.getData(i, "DESCRIPTION");
				lines[3] = sqlResult.getData(i, "AMOUNT");
				invoices.add(lines);
			}
			return invoices;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	/**
	 * Se utiliza para validar si el dia de hoy es primero, segundo o ultimo de mes
	 * 
	 * @return true si es primero,segundo o ultimo de mes, false en caso contrario
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public boolean checkDay() throws Exception {
		try {
			LocalDate fecha = LocalDate.now();
			int dia = fecha.getDayOfMonth();
			int ultimoDia = YearMonth.from(fecha).lengthOfMonth();

			if ((dia == 1) || (dia == 2) || (dia == ultimoDia)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Se utiliza para generar una lista que incluye el INVOICE_ID de las lineas con
	 * estatus ERROR
	 * 
	 * @param sqlResult del query que une la tabla de invoices y lines
	 * @return List<String> con los INVOICE_ID con estatus ERROR
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public List<String> getInvoicesWithErrors(SQLResult sqlResult) throws SQLException {
		try {
			List<String> invoices = new ArrayList<>();
			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				if (sqlResult.getData(i, "ESTATUS").equalsIgnoreCase("ERROR")) {
					invoices.add(sqlResult.getData(i, "INVOICE_NUM"));
				}
			}
			return invoices;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}

	/**
	 * Se utiliza para generar una lista que incluye el INVOICE_ID de las lineas con
	 * estatus S
	 * 
	 * @param sqlResult del query que une la tabla de invoices y lines
	 * @return List<String> con los INVOICE_ID con estatus S
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public List<String> getInvoicesWithStatus(SQLResult sqlResult) throws SQLException {
		try {
			List<String> invoices = new ArrayList<>();
			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				if (sqlResult.getData(i, "STATUS").equalsIgnoreCase("S")) {
					invoices.add(sqlResult.getData(i, "INVOICE_ID"));
				}
			}
			return invoices;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}


	
	/**
	 * Método para validar los datos de una factura
	 * 
	 * @throws Exception
	 * @return true/false
	 * @Author juan.lopez@grupotimexico.com
	 */
	public boolean validardatosfactura(String factura, String description, String proveedor, SQLResult sqlResponse) {
		try {
			String facturabd = sqlResponse.getData(0, "INVOICE_NUM");
			String numProvbd = sqlResponse.getData(0, "NRO_PROVEEDOR");
			String descriptionbd = sqlResponse.getData(0, "DESCRIPTION");

			return facturabd.equals(factura) && numProvbd.equals(proveedor) && descriptionbd.equals(description);

		} catch (Exception e) {
			System.out.println("Error al consultar la BD: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Se utiliza para generar una lista que incluye el INVOICE_NUM
	 * 
	 * @param sqlResult del query que une la tabla de invoices y lines
	 * @return List<String> con los INVOICE_NUM 
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public List<String> getInvoicesToCompare(SQLResult sqlResult) throws SQLException {
		try {
			List<String> invoices = new ArrayList<>();
			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				invoices.add(sqlResult.getData(i, "INVOICE_NUM"));
			}
			return invoices;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new SQLException("Error: " + e.getMessage());
		}
	}
	
}






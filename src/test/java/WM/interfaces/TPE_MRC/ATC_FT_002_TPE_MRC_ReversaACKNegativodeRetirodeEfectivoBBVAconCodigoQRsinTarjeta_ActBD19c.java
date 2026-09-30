package WM.interfaces.TPE_MRC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import WM.om.TPE_MRC;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import utils.webmethods.ReadRequest;

public class ATC_FT_002_TPE_MRC_ReversaACKNegativodeRetirodeEfectivoBBVAconCodigoQRsinTarjeta_ActBD19c extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_TPE_MRC_ReversaACKNegativodeRetirodeEfectivoBBVAconCodigoQRsinTarjeta_ActBD19c_test(HashMap<String, String> data) throws Exception {
	
		SQLUtil dbOXTPEQA = new SQLUtil(GlobalVariables.DB_HOST_OXTPEQA_TPEUSER, GlobalVariables.DB_USER_OXTPEQA_TPEUSER,
				GlobalVariables.DB_PASSWORD_OXTPEQA_TPEUSER);

		SQLUtil dbOXSWTDC = new SQLUtil(GlobalVariables.DB_HOST_OXSWTDC, GlobalVariables.DB_USER_OXSWTDC,
				GlobalVariables.DB_PASSWORD_OXSWTDC);
		
		SQLUtil dbOXFINDBQ = new SQLUtil(GlobalVariables.DB_HOST_OXFINDBQ_WMLOG, GlobalVariables.DB_USER_OXFINDBQ_WMLOG,
				GlobalVariables.DB_PASSWORD_OXFINDBQ_WMLOG);
		
		TPE_MRC TPE_MRCUtil = new TPE_MRC(data, testCase);
		
		/*
		 *
		 *	 Proyecto: PTI23004 - Act BD Negocio e Integración a V19c
		 *	 Caso de prueba: MTC-FT-023 TPE_MRC Reversa ACK Negativo de Retiro de Efectivo BBVA con Codigo QR sin tarjeta
		 * 
		 * @author
		 * @date 
		 * 
		 */

		
		/*
		 * Variables
		 *********************************************************************/


		
		testCase.setProject_Name("PTI23004 - Act BD Negocio e Integración a V19c");
		String folio;
		String CreationDate;

		String wmCodeFolio = data.get("wmCodeFolio");
		String wmCodeToValidateAuth = data.get("wmCodeAuth");
		String wmCodeToValidateAck = data.get("wmCodeAck");
		String ackValue = data.get("ackValue");
		String operationAck = data.get("operationAck");
		String operationAut = data.get("operationAut");
		String operationFol = data.get("operationFol");
		String application;
		String entity;
		String wmdesc;
		String wmcodeAck, wmcodeAut;

		String wmCodeRequest;
		SoftAssert softAssert = new SoftAssert();

		
		// ************************************************************************************************/
		// Paso 1
		addStep("Ejecutar el servicio de Solicitud de Folio de la interface TPE_MRC, para obtener un folio que identifique el Retiro "
		        + "de Efectivo BBVA con codigo QR sin tarjeta \n");
		System.out.println("Paso 1: ");
		String respuestaFolio = TPE_MRCUtil.generacionFolio();

		System.out.println("Esta es la respuesta de Solicitud de Folio:\n" + respuestaFolio + "\nFIN");

		folio = getFolioXml(respuestaFolio);
		System.out.println("Folio: " + folio);

		application = getAplicationXml(respuestaFolio);
		entity = getEntityXml(respuestaFolio);

		wmCodeRequest = getWmCodeXml(respuestaFolio);
		System.out.println("WMcode: " + wmCodeRequest);

		CreationDate = getCreationdate(respuestaFolio);
		System.out.println("CreationDate: " + CreationDate);

		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 100
		if(wmCodeFolio.equals(wmCodeRequest)) {    
		    testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
		            +"Actual: "+wmCodeRequest+" Esperado: "+wmCodeFolio);
		}else {
		    testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
		            +"Actual: "+wmCodeRequest+" Esperado: "+wmCodeFolio);
		    testCase.failStep("Paso 1: Se obtuvo WmCode diferente al esperado en la solicitud de folio: ");
		}
		
		softAssert.assertEquals(wmCodeRequest,wmCodeFolio ,"Paso 1: Se obtuvo WmCode diferente al esperado en la solicitud de folio: ");

		// ************************************************************************************************/
		// Paso 2
		
		addStep("Ejecutar el servicio de Solicitud de Autorización de la interface TPE_MRC, para solicitar la"
		        + " autorización del Retiro de Efectivo BBVA con Codigo QR sin tarjeta \n");

		System.out.println("Paso 2: ");

		String respuestaAut = TPE_MRCUtil.generarAutorizacion(folio,CreationDate);

		System.out.println("Esta es la respuesta de la Solicitud de Autorización\n" + respuestaAut + "\n");

		wmCodeRequest = getWmCodeXml(respuestaAut);
		System.out.println("WMcode: " + wmCodeRequest);
		wmcodeAut=wmCodeRequest;

		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 000
		if(wmCodeRequest.equals(wmCodeToValidateAuth)) {    
		    testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
		            +"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAuth);
		}else {
		    testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
		            +"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAuth);
		    testCase.failStep("Paso 2: Se obtuvo WmCode diferente al esperado en la solicitud de autorización: ");
		}
		
		softAssert.assertEquals(wmCodeRequest,wmCodeToValidateAuth,"Paso 2: Se obtuvo WmCode diferente al esperado en la solicitud de autorización: ");
		// ************************************************************************************************/
		// Paso 3
		
		addStep("Ejecutar el servicio de Solicitud de Confirmación ACK con ACK Negativo de la interface TPE_MRC, para generar una"
		        + " reversa de la transacción de Retiro de Efectivo BBVA con Codigo QR sin tarjeta \n");
		System.out.println("Paso 3: ");

		String respuestaAck = TPE_MRCUtil.generacionACK(folio, CreationDate);
		System.out.println("Esta es la respuesta de la Solicitud de ACK\n" + respuestaAck + "\n");

		folio = getFolioXml(respuestaAck);
		System.out.println("Folio: " + folio);
		wmdesc = getDescXmlwmCode(respuestaAck);

		wmCodeRequest = getWmCodeXml(respuestaAck);
		System.out.println("WMcode: " + wmCodeRequest);
		wmcodeAck=wmCodeRequest;

		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 101
		if(wmCodeRequest.equals(wmCodeToValidateAck)) {    
		    testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
		            +"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAck);
		}else {
		    testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
		            +"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAck);
		    testCase.failStep("Paso 3: Se obtuvo WmCode diferente al esperado en la solicitud de confirmación ACK: ");
		}

		softAssert.assertEquals(wmCodeRequest,wmCodeToValidateAck,"Paso 3: Se obtuvo WmCode diferente al esperado en la confirmacion ack: ");

		
		/*
		 * Queries
		 *********************************************************************/

		String query1 = "SELECT APPLICATION, ENTITY, OPERATION, MTI, FOLIO, CREATION_DATE, PLAZA, TIENDA, AMOUNT, AUTH_ID_RES, REVERSED, COUNTER, ACK, IS_NAME, WM_CODE, WM_DESC, SITE "
				+ "FROM TPEUSER.TPE_FR_TRANSACTION WHERE CREATION_DATE>=SYSDATE-1 AND APPLICATION = '" + application + "' AND ENTITY = '" + entity + "' AND FOLIO = '" + folio + "'";
		String query1_1 = "SELECT APPLICATION, ENTITY, OPERATION, MTI, FOLIO FROM TPEUSER.TPE_FR_TRANSACTION " +
                "WHERE CREATION_DATE >= SYSDATE - 1 " +
                "AND APPLICATION = '" + application + "' " +
                "AND ENTITY = '" + entity + "' " +
                "AND FOLIO = '" + folio + "'";
		String query1_2 = "SELECT CREATION_DATE, PLAZA, TIENDA, AMOUNT, AUTH_ID_RES FROM TPEUSER.TPE_FR_TRANSACTION " +
                "WHERE CREATION_DATE >= SYSDATE - 1 " +
                "AND APPLICATION = '" + application + "' " +
                "AND ENTITY = '" + entity + "' " +
                "AND FOLIO = '" + folio + "'";
		String query1_3 = "SELECT REVERSED, COUNTER, ACK, IS_NAME, WM_CODE, WM_DESC, SITE FROM TPEUSER.TPE_FR_TRANSACTION " +
                "WHERE CREATION_DATE >= SYSDATE - 1 " +
                "AND APPLICATION = '" + application + "' " +
                "AND ENTITY = '" + entity + "' " +
                "AND FOLIO = '" + folio + "'";
		 
		String query2 = "SELECT APPLICATION, ENTITY, FOLIO, MTI, PAN, AMOUNT, AUTH_ID_RES, RESP_CODE, SW_CODE, COUNTER, IS_NAME, CREATION_DATE " +
	               "FROM SWUSER.TPE_SW_TLOG " +
	               "WHERE CREATION_DATE >= TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') " +
	               "AND FOLIO = '" + folio + "'";
		String query2_1 = "SELECT APPLICATION, ENTITY, FOLIO, MTI, PAN FROM SWUSER.TPE_SW_TLOG " +
				"WHERE CREATION_DATE >= TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') " +
	               "AND FOLIO = '" + folio + "'";
		String query2_2 = "SELECT AMOUNT, AUTH_ID_RES, RESP_CODE, SW_CODE, COUNTER FROM SWUSER.TPE_SW_TLOG " +
				"WHERE CREATION_DATE >= TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') " +
	               "AND FOLIO = '" + folio + "'";
		String query2_3 = "SELECT IS_NAME, CREATION_DATE FROM SWUSER.TPE_SW_TLOG " +
				"WHERE CREATION_DATE >= TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') " +
	               "AND FOLIO = '" + folio + "'";
		
		String query3 = "SELECT ERROR_ID, FOLIO, ERROR_DATE, ERROR_CODE, TPE_TYPE, IS_NAME, DESCRIPTION, MESSAGE, ACTION FROM WMLOG.WM_LOG_ERROR_TPE WHERE ERROR_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS')  - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND (TPE_TYPE='FR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%MRC%' OR TPE_TYPE LIKE '%SW%') ORDER BY ERROR_DATE DESC";
		String query3_1 = "SELECT ERROR_ID, FOLIO, ERROR_DATE, ERROR_CODE, TPE_TYPE FROM WMLOG.WM_LOG_ERROR_TPE " +
                "WHERE ERROR_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS')  - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE " +
                "AND (TPE_TYPE='FR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%MRC%' OR TPE_TYPE LIKE '%SW%') " +
                "ORDER BY ERROR_DATE DESC";
		String query3_2 = "SELECT IS_NAME, DESCRIPTION, MESSAGE, ACTION FROM WMLOG.WM_LOG_ERROR_TPE " +
                "WHERE ERROR_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS')  - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE " +
                "AND (TPE_TYPE='FR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%MRC%' OR TPE_TYPE LIKE '%SW%') " +
                "ORDER BY ERROR_DATE DESC";
		
		String query4 = "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO, PLAZA, TIENDA, CAJA, CREATION_DATE FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS')  - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='" + application + "' AND ENTITY='" + entity + "' AND (FOLIO='" + folio + "' OR FOLIO IS NULL) ORDER BY CREATION_DATE DESC";
		String query4_1 = "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO FROM WMLOG.SECURITY_SESSION_LOG " +
                "WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS')  - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='" + application + "' AND ENTITY='" + entity + "' AND (FOLIO='" + folio + "' OR FOLIO IS NULL) ORDER BY CREATION_DATE DESC";
		String query4_2 = "SELECT PLAZA, TIENDA, CAJA, CREATION_DATE FROM WMLOG.SECURITY_SESSION_LOG " +
                "WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS')  - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='" + application + "' AND ENTITY='" + entity + "' AND (FOLIO='" + folio + "' OR FOLIO IS NULL) ORDER BY CREATION_DATE DESC";


	/*		 *********************************************************************/
		
		/*
		 * Paso 4
		 *****************************************************************************************/
		addStep("Conectarse a la Base de Datos: **OXTPEQA_tpeuser** \n");
		System.out.println("Paso 4: Conectarse a la Base de Datos: **OXTPEQA_tpeuser**\n");
		testCase.addTextEvidenceCurrentStep("Resultado esperado: La conexión con la Base de Datos OXTPEQA_tpeuser fue exitosa y responde con la leyenda: CORRECTO");
        String urloxtpeqa = "jdbc:oracle:thin:@"+GlobalVariables.DB_HOST_OXTPEQA_TPEUSER;
        String usernameoxtpeqa = GlobalVariables.DB_USER_OXTPEQA_TPEUSER;
        String passwordoxtpeqa = GlobalVariables.DB_PASSWORD_OXTPEQA_TPEUSER;

        try {
            Connection connection = DriverManager.getConnection(urloxtpeqa, usernameoxtpeqa, passwordoxtpeqa);
			System.out.println("Resultado actual: La conexión con la Base de Datos OXTPEQA_tpeuser: "+GlobalVariables.DB_HOST_OXTPEQA_TPEUSER+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
			testCase.addBoldTextEvidenceCurrentStep("Resultado actual: La conexión con la Base de Datos OXTPEQA_tpeuser: "+GlobalVariables.DB_HOST_OXTPEQA_TPEUSER+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection failed. Error message: " + e.getMessage()+"\n");
 		    testCase.addBoldTextEvidenceCurrentStep("La conexión a la base de datos OXTPEQA_tpeuser no se pudo realizar.");
 		    testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_OXTPEQA_TPEUSER);
 		    testCase.failStep("Paso 4: La conexión a la base de datos OXTPEQA_tpeuser no se pudo realizar.");
        }

        /*
		 * Paso 5
		 *****************************************************************************************/
		addStep("Ejecutar la consulta para validar que la transacción se registró correctamente en la tabla TPE_FR_TRANSACTION de la BD OXTPEQA_tpeuser \n");
		System.out.println("Paso 5: Ejecutar la consulta para validar que la transacción se registró correctamente en la tabla TPE_FR_TRANSACTION de la BD OXTPEQA_tpeuser\n");
		System.out.println(GlobalVariables.DB_HOST_OXTPEQA_TPEUSER);
		System.out.println("Query: "+query1);
		SQLResult result1 = executeQuery(dbOXTPEQA, query1);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query1);
		
		testCase.addTextEvidenceCurrentStep("Resultado Esperado: Se muestra el siguiente registro con los campos relacionados con el retiro de efectivo bbva con código QR sin tarjeta: Registro TRN03 de Retiro de Efectivo con código QR BBVA: FOLIO='" + folio + "', APPLICATION='" + application + "', ENTITY='" + entity + "', OPERATION=" + operationAck + ", MTI=200, REVERSED=2, ACK=01,"+ "WM_CODE=" + wmcodeAck + ", WM_DESC='" + wmdesc + "'");
		testCase.addTextEvidenceCurrentStep("Resultado Actual: Se muestra el siguiente registro con los campos relacionados con el retiro de efectivo bbva con codigo QR sin tarjeta: Registro TRN03 de Retiro de Efectivo con codigo QR BBVA: FOLIO=" + result1.getData(0, "FOLIO") + ", APPLICATION='" + result1.getData(0, "APPLICATION") + "', ENTITY='" + result1.getData(0, "ENTITY") + "', OPERATION=" + result1.getData(0, "OPERATION") + ", MTI=" + result1.getData(0, "MTI")+ ", REVERSED=" + result1.getData(0, "REVERSED")+ ", ACK=" + result1.getData(0, "ACK")+ ", WM_CODE=" + result1.getData(0, "WM_CODE") + ", WM_DESC=" + result1.getData(0, "WM_DESC") + "");
	    

		
		SQLResult result1_1 = executeQuery(dbOXTPEQA, query1_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result1_1);
		SQLResult result1_2 = executeQuery(dbOXTPEQA, query1_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result1_2);
		SQLResult result1_3 = executeQuery(dbOXTPEQA, query1_3);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte3:");
		testCase.addQueryEvidenceCurrentStep(result1_3);
		
		boolean stepPassed = true;

		if(!result1.isEmpty()) {
		    String[] expectedValues = {application, entity, folio, operationAck, "0200", wmcodeAck, wmdesc, ackValue, "0420"};
		    System.out.println("Valores esperados: " + Arrays.toString(expectedValues));

		    for (int i = 0; i < result1.getRowCount(); i++) {
		        String[] actualValues = {result1.getData(i, "APPLICATION"),result1.getData(i, "ENTITY"),result1.getData(i, "FOLIO"),
		            result1.getData(i, "OPERATION"),result1.getData(i, "MTI"),result1.getData(i, "WM_CODE"),result1.getData(i, "WM_DESC"),result1.getData(i, "ACK")};

		        for (String actual : actualValues) {
		            if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
		                System.out.println("El valor " + actual + " está contenido en expectedValues");
		            } else {
		                System.out.println("El valor " + actual + " no está contenido en expectedValues");
		                testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
		                stepPassed = false;
		            }
		        }
		    }

		    if (stepPassed) {
		        System.out.println("Query exitosa");
//		        testCase.addTextEvidenceCurrentStep("Resultado Actual: Se muestran los siguientes registros con los campos relacionados con el retiro de efectivo bbva con codigo QR sin tarjeta: ");
//		        testCase.addTextEvidenceCurrentStep("Retiro de Efectivo con codigo QR BBVA: FOLIO=" + folio + ", APPLICATION='" + application + "', ENTITY='" + entity + "', OPERATION=" + operationAck + ", MTI=200, REVERSED=2, ACK=" + ackValue + ", WM_CODE=" + wmcodeAck + ", WM_DESC=" + wmdesc +" ");
//		        testCase.addTextEvidenceCurrentStep ("Registro de Reversa de Retiro de Efectivo con codigo QR BBVA: FOLIO=" + folio + ", APPLICATION='" + application + "', ENTITY='" + entity + "', OPERATION=" + operationAck + ", MTI=420, REVERSED=0, ACK=" + ackValue + ", WM_CODE=" + wmcodeAck + ", WM_DESC=" + wmdesc + "");
		    } else {
		        System.out.println("El paso ha fallado");
		        testCase.failStep("El paso ha fallado");
		    }
		} else {
		    System.out.println("La consulta está vacía");
		    testCase.addTextEvidenceCurrentStep("La consulta está vacía");
		    stepPassed = false;
		}

		if (!stepPassed) {
		    System.out.println("El paso ha fallado");
		    testCase.failStep("El paso ha fallado");
		}

		
/*		 * Paso 6
		 *****************************************************************************************/
		addStep(" Establecer conexión a la BD **OXSWTDC_swuser** \n");
		System.out.println("\nPaso 6: Establecer conexión a la BD **OXSWTDC_swuser**\n");
		testCase.addTextEvidenceCurrentStep("Resultado esperado: La conexión con la Base de Datos OXSWTDC_swuser fue exitosa y responde con la leyenda: CORRECTO");
       String urloxswtdc = "jdbc:oracle:thin:@"+GlobalVariables.DB_HOST_OXSWTDC;
       String usernameoxswtdc = GlobalVariables.DB_USER_OXSWTDC;
       String passwordoxswtdc = GlobalVariables.DB_PASSWORD_OXSWTDC;
       
       try {
           Connection connection = DriverManager.getConnection(urloxswtdc, usernameoxswtdc, passwordoxswtdc);
           System.out.println("Resultado actual: La conexión con la Base de Datos OXSWTDC_swuser: "+GlobalVariables.DB_HOST_OXSWTDC+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
           testCase.addBoldTextEvidenceCurrentStep("Resultado actual: La conexión con la Base de Datos OXSWTDC_swuser: "+GlobalVariables.DB_HOST_OXSWTDC+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
           connection.close();
       } catch (SQLException e) {
           System.err.println("Connection failed. Error message: " + e.getMessage()+"\n");
		    testCase.addBoldTextEvidenceCurrentStep("La conexión a la base de datos OXSWTDC_swuser no se pudo realizar.");
		    testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_OXSWTDC);
		    testCase.failStep("Paso 6: La conexión a la base de datos OXSWTDC_swuser no se pudo realizar.");
       }

/*		 * Paso 7
		 *****************************************************************************************/
		addStep(" Ejecutar la consulta para validar la transaccion en la base de datos del **OXSWTDC_swuser** \n");
		System.out.println("Paso 7: Ejecutar la consulta para validar la transaccion en la base de datos del **OXSWTDC_swuser**\n");
		System.out.println(GlobalVariables.DB_HOST_OXSWTDC);
		System.out.println("Query: "+query2);
		SQLResult result2 = executeQuery(dbOXSWTDC, query2);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query2);
		
		
		testCase.addTextEvidenceCurrentStep("Resultado Esperado: Validar que se muestren los registros con FOLIO= "+ folio + ", MTI = 0200, RESP_CODE=00");
	    testCase.addTextEvidenceCurrentStep("Resultado Esperado: Validar que se muestren los registros con FOLIO= "+ folio + ", MTI = 0420, RESP_CODE=00");
	    testCase.addTextEvidenceCurrentStep("Resultado 	Actual: Se muestran los registros con FOLIO= "+ result2.getData(0, "FOLIO") + ", MTI = "+ result2.getData(0, "MTI") +", RESP_CODE="+ result2.getData(0, "RESP_CODE") +"");
	    testCase.addTextEvidenceCurrentStep("Resultado 	Actual: Se muestran los registros con FOLIO= "+ result2.getData(1, "FOLIO") + ", MTI = "+ result2.getData(1, "MTI") +", RESP_CODE="+ result2.getData(1, "RESP_CODE") +"");
	    

	
		
		SQLResult result2_1 = executeQuery(dbOXSWTDC, query2_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result2_1);
		SQLResult result2_2 = executeQuery(dbOXSWTDC, query2_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result2_2);
		SQLResult result2_3 = executeQuery(dbOXSWTDC, query2_3);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte3:");
		testCase.addQueryEvidenceCurrentStep(result2_3);
		boolean foundExpectedRecord = false;
		
		if(!result2.isEmpty()) {
		    for (int i = 0; i < result2.getRowCount(); i++) {
		        String folioq = result2.getData(i, "FOLIO");
		        String mtiq = result2.getData(i, "MTI");
		        String respCodeq = result2.getData(i, "RESP_CODE");

		        if (folioq.equals(folio) && mtiq.equals("0200") && respCodeq.equals("00")) {
		            System.out.println("Se encontró el registro con Folio=" + folio + ", MTI=0200, RESP_CODE=00");
		            foundExpectedRecord = true;
		        } else if (folioq.equals(folio) && mtiq.equals("0420") && respCodeq.equals("00")) {
		            System.out.println("Se encontró el registro con Folio=" + folio + ", MTI=0420, RESP_CODE=00");
		            foundExpectedRecord = true;
		        }
		    }

		    if (!foundExpectedRecord) {
		        System.out.println("No se encontró el registro esperado");
		        testCase.addTextEvidenceCurrentStep("No se encontró el registro esperado");
		        testCase.failStep("No se encontró el registro esperado");
		    } else {
		        System.out.println("Query exitosa");
		    }
		} else {
		    System.out.println("La consulta está vacía");
		    testCase.addTextEvidenceCurrentStep("La consulta está vacía");
		    testCase.addQueryEvidenceCurrentStep(result2);
		    testCase.failStep("La consulta está vacía");
		}
		
		

/*		 * Paso 8
		 *****************************************************************************************/

		addStep(" Conectarse a la Base de Datos: **OXFINDBQ _wmlog** \n");
		System.out.println("\nPaso 8: Conectarse a la Base de Datos: **OXFINDBQ _wmlog**\n");
		testCase.addTextEvidenceCurrentStep("Resultado esperado: La conexión con la Base de Datos OXFINDBQ _wmlog fue exitosa y responde con la leyenda: CORRECTO");
       String urloxfindbq = "jdbc:oracle:thin:@"+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG;
       String usernameoxfindbq = GlobalVariables.DB_USER_OXFINDBQ_WMLOG;
       String passwordoxfindbq = GlobalVariables.DB_PASSWORD_OXFINDBQ_WMLOG;
       
       try {
           Connection connection = DriverManager.getConnection(urloxfindbq, usernameoxfindbq, passwordoxfindbq);
           System.out.println("Connection successful!: "+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG+"\n");
           System.out.println("Resultado actual: La conexión con la Base de Datos OXFINDBQ_wmlog: "+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
           testCase.addBoldTextEvidenceCurrentStep("Resultado actual: La conexión con la Base de Datos OXFINDBQ_wmlog: "+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG   +"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
  
           connection.close();
       } catch (SQLException e) {
           System.err.println("Connection failed. Error message: " + e.getMessage()+"\n");
		    testCase.addBoldTextEvidenceCurrentStep("La conexión a la base de datos OXFINDBQ_wmlog no se pudo realizar.");
		    testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_OXFINDBQ_WMLOG);
		    testCase.failStep("Paso 8: La conexión a la base de datos OXFINDBQ_wmlog no se pudo realizar.");
       }
       
       
/*		 * Paso 9
		 *****************************************************************************************/
		addStep(" Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacciónara verificar que no haya generado errores \n");
		System.out.println("Paso 9: Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacciónara verificar que no haya generado errores\n");
		System.out.println(GlobalVariables.DB_HOST_OXFINDBQ_WMLOG);
		System.out.println("Query: "+query3);
		SQLResult result3 = executeQuery(dbOXFINDBQ, query3);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query3);
		
       	testCase.addTextEvidenceCurrentStep("Resultado Esperado: La consulta no muestra errores relacionados a la transaccion de Retiro de Efectivo BBVA con CodigoQR sin tarjeta");


		if(result3.isEmpty()) {
	   		System.out.println("Resultado Actual: La consulta no muestra errores.");
	       	testCase.addTextEvidenceCurrentStep("Resultado Actual: La consulta no muestra errores relacionados a la transaccion de Retiro de Efectivo BBVA con CodigoQR sin tarjeta");
//	       	testCase.addQueryEvidenceCurrentStep(result3);
	    } else {
	       	System.out.println("Resultado Actual: La consulta muestra errores");
	       	testCase.addTextEvidenceCurrentStep("Resultado Actual: La consulta muestra errores relacionados a la transaccion de Retiro de Efectivo BBVA con CodigoQR sin tarjeta");
	       	testCase.failStep("Paso 9: La consulta muestra errores relacionados a la transaccion de Retiro de Efectivo BBVA con CodigoQR sin tarjeta");
//	       	testCase.addQueryEvidenceCurrentStep(result3);
	       	
	    }
		
		
		SQLResult result3_1 = executeQuery(dbOXFINDBQ, query3_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result3_1);
		SQLResult result3_2 = executeQuery(dbOXFINDBQ, query3_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result3_2);

		

		
/*		*Paso 10 
		******************************************************************************************/
		addStep(" Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacción, para validar el registro de los servicios invocados de la interface TPE_MRC que no viajaron por el canal seguro de F5");
		System.out.println("Paso 10: Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacción, para validar el registro de los servicios invocados de la interface TPE_MRC que no viajaron por el canal seguro de F5\n");
		System.out.println(GlobalVariables.DB_HOST_OXSWTDC);
		System.out.println("Query: "+query4);
		SQLResult result4 = executeQuery(dbOXFINDBQ, query4);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query4);
        testCase.addTextEvidenceCurrentStep("Resultado Esperado: La consulta muestra las invocaciones a los servicios de la interface TPE_MRC (TRN01,TRN02, TRN03) que no viajaron por el canal seguro de F5 y que se usaron para la transacción de Retiro de efectivo BBVA con codigo QR sin tarjeta y su reversa.");

		

		boolean stepPassed1 = true;

		if(!result4.isEmpty()) {
		    String[] expectedOperations = {"TRN01", "TRN02", "TRN03"};
		    String expectedSource = "F5";

		    for (int i = 0; i < result4.getRowCount(); i++) {
		        String operation = result4.getData(i, "OPERATION");
		        String source = result4.getData(i, "SOURCE");

		        if (Arrays.asList(expectedOperations).contains(operation) && !source.equals(expectedSource)) {
		            System.out.println("La invocación al servicio " + operation + " de la interface TPE_MRC no viajó por el canal seguro de F5");
		        } else {
		            System.out.println("La invocación al servicio " + operation + " de la interface TPE_MRC viajó por el canal seguro de F5 o no es una operación esperada");
		            testCase.addTextEvidenceCurrentStep("La invocación al servicio " + operation + " de la interface TPE_MRC viajó por el canal seguro de F5 o no es una operación esperada");
		            stepPassed1 = false;
		        }
		    }

		    if (stepPassed1) {
		        System.out.println("Query exitosa");
		        testCase.addTextEvidenceCurrentStep("Resultado Actual: La consulta muestra las invocaciones a los servicios de la interface TPE_MRC (TRN01,TRN02, TRN03) que no viajaron por el canal seguro de F5 y que se usaron para la transacción de Retiro de efectivo BBVA con codigo QR sin tarjeta y su reversa.");
		    } else {
		        System.out.println("El paso ha fallado");
		        testCase.failStep("El paso ha fallado");
		    }
		} else {
		    System.out.println("La consulta está vacía");
		    testCase.addTextEvidenceCurrentStep("La consulta está vacía");
//		    testCase.addQueryEvidenceCurrentStep(result4);
		    stepPassed1 = false;
		}

		if (!stepPassed1) {
		    System.out.println("El paso ha fallado");
		    testCase.failStep("El paso ha fallado");
		}
		SQLResult result4_1 = executeQuery(dbOXFINDBQ, query4_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result4_1);
		SQLResult result4_2 = executeQuery(dbOXFINDBQ, query4_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result4_2);

	}
    public static String getFolioXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);

		return eElement.getAttribute("folio");
	}
	
	public static String getCreationdate(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);

		return eElement.getAttribute("creationDate");
	}

	public static String getWmCodeXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("wmCode").item(0);

		return eElement.getAttribute("value");
	}
	public static String getAplicationXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);

		return eElement.getAttribute("application");
	}
	public static String getEntityXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);

		return eElement.getAttribute("entity");
	}
	public static String getDescXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);

		return eElement.getAttribute("desc");
	}
	public static String getDescXmlwmCode(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("wmCode").item(0);

		return eElement.getAttribute("desc");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_TPE_MRC_ReversaACKNegativodeRetirodeEfectivoBBVAconCodigoQRsinTarjeta_ActBD19c_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Prueba de regresión para comprobar la no afectación en la funcionalidad de reversa por ACK negativo de Retiro de Efectivo BBVA con Codigo QR sin tarjeta de la interface FEMSA_TPE_MRC, al ser migrada la base de datos del SWITCH a version 19C y separada (TAE y TDC).";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Sergio Robles Ramos";
	} 	

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*Contar con acceso a las bases de datos de OXTPEQA_tpeuser, OXFINDBQ _wmlog, y OXSWTDC_swuser. \n"
				+ "*Contar con las credenciales de WM10.11 de los nuevos servers del Integration Server de QA. \n"
				+ "*Contar con ambiente de proveedor BBVA habilitardo para que responda las transacciones exitosas. ";
	}
}
package WM.interfaces.TPE_OLP;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import WM.om.TPE_OLP;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import utils.webmethods.ReadRequest;


public class ATC_FT_002_TPE_OLP_RealizarCasoCriticoPagoEnLineaDeFacturaTELCELExitoso_ActBD19c extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_TPE_OLP_RealizarCasoCriticoPagoEnLineaDeFacturaTELCELExitoso_ActBD19c_test(HashMap<String, String> data) throws Exception {
	
		/**
		 * Proyecto: PTI23004 - Act BD Negocio e Integración a V19c
		 * Caso de prueba: MTC-FT-019 TPE_OLP Realizar caso critico pago en línea de factura TELCEL exitoso
		 * @author 
		 * @date 
		 */
		
		/*
		 * Utiler�as
		 *********************************************************************/

		SQLUtil dbOXTPEQA = new SQLUtil(GlobalVariables.DB_HOST_OXTPEQA_TPEUSER, GlobalVariables.DB_USER_OXTPEQA_TPEUSER,
				GlobalVariables.DB_PASSWORD_OXTPEQA_TPEUSER);

		SQLUtil dbOXSWTDC = new SQLUtil(GlobalVariables.DB_HOST_OXSWTDC, GlobalVariables.DB_USER_OXSWTDC,
				GlobalVariables.DB_PASSWORD_OXSWTDC);
		
		SQLUtil dbOXFINDBQ = new SQLUtil(GlobalVariables.DB_HOST_OXFINDBQ_WMLOG, GlobalVariables.DB_USER_OXFINDBQ_WMLOG,
				GlobalVariables.DB_PASSWORD_OXFINDBQ_WMLOG);
		
		SQLUtil dbOXWTAE = new SQLUtil(GlobalVariables.DB_HOST_OXSWTAE, GlobalVariables.DB_USER_OXSWTAE,
				GlobalVariables.DB_PASSWORD_OXSWTAE);
		
	
		TPE_OLP TPE_OLPUtil = new TPE_OLP(data, testCase, dbOXTPEQA);
		

		/*
		 * Variables
		 *********************************************************************/
		
		testCase.setProject_Name("PTI23004 - Act BD Negocio e Integración a V19c");
		String firstFolio, secondFolio;
		String CreationDate;

		String wmCodeConsulta = data.get("wmCodeCon");
		String wmCodeFolio = data.get("wmCodeFolio");
		String wmCodeToValidateAuth = data.get("wmCodeAuth");
		String wmCodeToValidateAck = data.get("wmCodeAck");
		String wmCodeToValidateRev = data.get("wmCodeRev");
		String operationAck = data.get("operationAck");
		String operationAut = data.get("operationAut");
		String operationFol = data.get("operationFol");
		String entity,wmdesc,operation,application, mti, status;
		String wmcodeAck,wmcodeAut,trn04,accountId,idClient;

		String wmCodeRequest;
		SoftAssert softAssert = new SoftAssert();

		// ************************************************************************************************/
		// Paso 1
		
		addStep("Ejecutar el servicio de consulta de factura TELCEL de la interface TPE_OLP, para obtener la información de la factura TELCEL de un cliente");
		System.out.println("Paso 1: ");
		String respuestaConsulta = TPE_OLPUtil.ConsultaCliente();

		System.out.println("Esta es la respuesta de Solicitud de Consulta:\n" + respuestaConsulta + "\nFIN");

		firstFolio = getFolioXml(respuestaConsulta);
		System.out.println("Folio: " + firstFolio);
		
		application = getAplicationXml(respuestaConsulta);
		entity = getEntityXml(respuestaConsulta);
		operation= getOperationXml(respuestaConsulta);
		wmdesc = getDescXml(respuestaConsulta);
		mti = getOperationXml(respuestaConsulta);
		status = getOperationXml(respuestaConsulta);
		idClient = getidClientXml(respuestaConsulta);
		accountId = getaccountIdXml(respuestaConsulta);

		wmCodeRequest = getWmCodeXml(respuestaConsulta);
		System.out.println("WMcode Obtenido: " + wmCodeRequest);
		System.out.println("WMcode Esperado: " + wmCodeConsulta);
		wmcodeAut=wmCodeRequest;
		
		//CreationDate =getCreationdate(respuestaConsulta);
		//System.out.println("CreationDate: " + CreationDate);
		
		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 101
		if(wmCodeConsulta.equals(wmCodeRequest)) {	
			testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeConsulta);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeConsulta);
		    testCase.failStep("Paso 1: Se obtuvo un WmCode diferente al esperado en la solicitud de consulta de cliente de factura telcel. ");

		}

		softAssert.assertEquals(wmCodeRequest,wmCodeConsulta ,"Paso 1: Se obtuvo WmCode diferente al esperado en la solicitud de consulta de cliente de factura telcel: ");		
		
		// ************************************************************************************************/
		// Paso 2
		addStep("Ejecutar el servicio de Solicitud de Folio de la interface TPE_OLP, para obtener un folio que identifique el pago que se realizara a la factura TELCEL de un cliente");
		System.out.println("Paso 2: ");
		String respuestaFolio = TPE_OLPUtil.generacionFolio();

		System.out.println("Esta es la respuesta de Solicitud de Folio:\n" + respuestaFolio + "\nFIN");

		secondFolio = getFolioXml(respuestaFolio);
		System.out.println("Folio: " + secondFolio);

		wmCodeRequest = getWmCodeXml(respuestaFolio);
		System.out.println("WMcode: " + wmCodeRequest);
		
		CreationDate =getCreationdate(respuestaFolio);
		System.out.println("CreationDate: " + CreationDate);
		
		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 100
		if(wmCodeFolio.equals(wmCodeRequest)) {	
			testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeFolio);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeFolio);
		    testCase.failStep("Paso 2: Se obtuvo WmCode diferente al esperado en la solicitud de folio. ");

		}

		softAssert.assertEquals(wmCodeRequest,wmCodeFolio ,"Paso 2: Se obtuvo WmCode diferente al esperado en la solicitud de folio: ");


		// ************************************************************************************************/
		// Paso 3
		addStep("Ejecutar el servicio de Solicitud de Autorización de la interface TPE_OLP, para realizar el pago de una factura TELCEL de un cliente");
		System.out.println("Paso 3: ");
		
		String respuestaAut = TPE_OLPUtil.generarAutorizacion(secondFolio,CreationDate,idClient,accountId);

		System.out.println("Esta es la respuesta de la Solicitud de Autorización\n" + respuestaAut + "\n");

		wmCodeRequest = getWmCodeXml(respuestaAut);
		System.out.println("WMcode: " + wmCodeRequest);

		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 000
		if(wmCodeRequest.equals(wmCodeToValidateAuth)) {	
			testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAuth);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAuth);
		    testCase.failStep("Paso 3: Se obtuvo WmCode diferente al esperado en la solicitud de autorización. ");

		}

		softAssert.assertEquals(wmCodeRequest,wmCodeToValidateAuth,"Paso 3: Se obtuvo WmCode diferente al esperado en la solicitud de autorización: ");

		/*
		 * *****************************************************************************
		 * ****************** Paso 4
		 ***********************************************************************************************/

		addStep("Ejecutar el servicio de Solicitud de Confirmación ACK de la interface TPE_OLP, para obtener la confirmación de ACK y cerrar la transacción de pago de una factura TELCEL para un cliente");
		System.out.println("Paso 4: ");
		
		String respuestaAck = TPE_OLPUtil.generacionACK(secondFolio,idClient,accountId);
		System.out.println("Esta es la respuesta de la Solicitud de ACK\n" + respuestaAck + "\n");

		secondFolio = getFolioXml(respuestaAck);
		System.out.println("Folio: " + secondFolio);

		wmCodeRequest = getWmCodeXml(respuestaAck);
		System.out.println("WMcode: " + wmCodeRequest);
		wmcodeAck=wmCodeRequest;
		
		CreationDate =getCreationdate(respuestaFolio);
		System.out.println("CreationDate: " + CreationDate);

		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 101
		if(wmCodeRequest.equals(wmCodeToValidateAck)) {	
			testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAck);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateAck);
		    testCase.failStep("Paso 4: Se obtuvo WmCode diferente al esperado en la confirmacion ack.");

		}

		softAssert.assertEquals(wmCodeRequest,wmCodeToValidateAck,"Paso 4: Se obtuvo WmCode diferente al esperado en la confirmacion ack: ");
		//softAssert.assertAll();
		
		/*Paso 5
		 ***********************************************************************************************/

		addStep("Ejecutar el servicio de Solicitud de Reversa por Caso Critico de la interface TPE_OLP, para realizar la reversa de Pago a la factura TELCEL de un cliente");
		System.out.println("Paso 5: ");
		
		String respuestaRev = TPE_OLPUtil.solicitudReversa(secondFolio,CreationDate);
		System.out.println("Esta es la respuesta de la Solicitud de Reversa\n" + respuestaRev + "\n");

		secondFolio = getFolioXmlwmCode(respuestaRev);
		System.out.println("Folio: " + secondFolio);
		trn04 = getOperationXml(respuestaRev);

		wmCodeRequest = getWmCodeXml(respuestaRev);
		System.out.println("WMcode: " + wmCodeRequest);

		// VALIDAR QUE EL WMCODE DE LA RESPUESTA SEA IGUAL A 101
		if(wmCodeRequest.equals(wmCodeToValidateRev)) {	
			testCase.addBoldTextEvidenceCurrentStep("WmCode iguales: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateRev);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("WmCode distintos: \n"
					+"Actual: "+wmCodeRequest+" Esperado: "+wmCodeToValidateRev);	
		}

		softAssert.assertEquals(wmCodeRequest,wmCodeToValidateRev,"Paso 5: Se obtuvo WmCode diferente al esperado en la confirmacion ack: ");
//		softAssert.assertAll();	
		
		/*
		 * Queries
		 *********************************************************************/
/*
		String query1 = "SELECT * FROM TPEUSER.TPE_FR_SAF WHERE APPLICATION='OLP' AND CREATION_DATE\r\n"
				+ ">= SYSDATE-9 AND FOLIO IN('6612650056','6612650058')";
		String query2 = "SELECT * FROM TPEUSER.TPE_FR_DOCS_POS WHERE CREATION_DATE>=SYSDATE-1 and FOLIO='6612650058'";
		String query3 = "SELECT * FROM TPEUSER.TPE_FR_DOCS_POS WHERE CREATION_DATE>=SYSDATE-1 and FOLIO='6612650058'";
		String query4 = "SELECT * FROM WMLOG.WM_LOG_ERROR_TPE WHERE ERROR_DATE BETWEEN SYSDATE - INTERVAL '9' DAY - INTERVAL '15' MINUTE AND SYSDATE - INTERVAL '9' DAY + INTERVAL '15' MINUTE AND (TPE_TYPE='SE.CTR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%OLP%') ORDER BY ERROR_DATE DESC";
		String query5 = "SELECT * FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN SYSDATE\r\n"
				+ "- INTERVAL '9' DAY - INTERVAL '15' MINUTE AND SYSDATE - INTERVAL '9' DAY + INTERVAL\r\n"
				+ "'15' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' AND OPERATION IN\r\n"
				+ "('TRN03','TRN02') AND FOLIO ='6612650058' ORDER BY CREATION_DATE DESC";
		String query6 = "SELECT * FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN SYSDATE\r\n"
				+ "- INTERVAL '9' DAY - INTERVAL '15' MINUTE AND SYSDATE - INTERVAL '9' DAY + INTERVAL\r\n"
				+ "'15' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' AND OPERATION IN\r\n"
				+ "('TRN03','TRN02') AND FOLIO ='6612650058' ORDER BY CREATION_DATE DESC";
		String query7 = "SELECT * FROM SWUSER.TPE_SW_TLOG WHERE CREATION_DATE BETWEEN SYSDATE - INTERVAL\r\n"
				+ "'9' DAY - INTERVAL '15' MINUTE AND SYSDATE - INTERVAL '9' DAY + INTERVAL '15' MINUTE AND\r\n"
				+ "FOLIO = '6612650058'";
				*/
		/*		 *********************************************************************/
		
		//Query 1
		String query1 = "SELECT APPLICATION, ENTITY, OPERATION, FOLIO, CREATION_DATE, PLAZA, TIENDA, MTI, AMOUNT, TRACK2, RET_REF_NO, AUTH_ID_RES, COUNTER, ACK, WM_CODE, "
				+ "WM_DESC, IS_NAME, SITE, REVERSED, REV_COUNTER, SERVICE_ID FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION='OLP' AND CREATION_DATE >= "
				+ "TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND FOLIO IN('" + firstFolio + "','" + secondFolio +"')";
		
		//Query 1 dividida
		String query1_1 = "SELECT APPLICATION, ENTITY, OPERATION, FOLIO, CREATION_DATE FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION='OLP' AND CREATION_DATE >= "
				+ "TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND FOLIO IN('" + firstFolio + "','" + secondFolio +"')";
		
		String query1_2 = "SELECT PLAZA, TIENDA, MTI, AMOUNT, TRACK2 FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION='OLP' AND CREATION_DATE >= "
				+ "TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND FOLIO IN('" + firstFolio + "','" + secondFolio +"')";
		
		String query1_3 = "SELECT RET_REF_NO, AUTH_ID_RES, COUNTER, ACK, WM_CODE FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION='OLP' AND CREATION_DATE >= "
				+ "TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND FOLIO IN('" + firstFolio + "','" + secondFolio +"')";
		
		String query1_4 = "SELECT WM_DESC, IS_NAME, SITE, REVERSED, REV_COUNTER, SERVICE_ID FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION='OLP' AND CREATION_DATE >= "
				+ "TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND FOLIO IN('" + firstFolio + "','" + secondFolio +"')";
		
		
		//query2
		String query2	= "SELECT ID, FOLIO, PLAZA, CAJA, TIENDA, SERVICIO, CREATION_DATE_TRANS, CREATION_DATE, ATT7, ATT8, DOC_TYPE, STATUS, FECHA_TRANSACCION, "
				+ "FECHA_ADM, VALOR1, VALOR2, ATT1, ATT9 FROM TPEUSER.TPE_FR_DOCS_POS WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO='" + secondFolio +"'";
		
		//query 2 dividida
		String query2_1 = "SELECT ID, FOLIO, PLAZA, CAJA, TIENDA, SERVICIO FROM TPEUSER.TPE_FR_DOCS_POS WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO='" + secondFolio +"'";
		String query2_2 = "SELECT CREATION_DATE_TRANS, CREATION_DATE, ATT7, ATT8, DOC_TYPE FROM TPEUSER.TPE_FR_DOCS_POS WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO='" + secondFolio +"'";
		String query2_3 = "SELECT STATUS, FECHA_TRANSACCION, FECHA_ADM, VALOR1, VALOR2, ATT1, ATT9 FROM TPEUSER.TPE_FR_DOCS_POS WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO='" + secondFolio +"'"; 
		
		String query3 	= "SELECT FOLIO_TPE, CREATION_DATE, APPLICATION, ENTITY, OPERATION, MTI, STATUS, CREATION_DATE_TPE, UPDATE_DATE, COUNTER, NEXT_RETRY_DT, SITE FROM TPEUSER.TPE_FR_SAF WHERE CREATION_DATE_TPE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO_TPE='" + secondFolio + "'";
		String query3_1 = "SELECT FOLIO_TPE, CREATION_DATE, APPLICATION, ENTITY FROM TPEUSER.TPE_FR_SAF WHERE CREATION_DATE_TPE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO_TPE='" + secondFolio + "'";// No tienen relacion con la tabla de la documentacion
		String query3_2 = "SELECT OPERATION, MTI, STATUS, CREATION_DATE_TPE FROM TPEUSER.TPE_FR_SAF WHERE CREATION_DATE_TPE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO_TPE='" + secondFolio + "'";
		String query3_3 = "SELECT UPDATE_DATE, COUNTER, NEXT_RETRY_DT, SITE FROM TPEUSER.TPE_FR_SAF WHERE CREATION_DATE_TPE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO_TPE='" + secondFolio + "'";
			
		String query4 = "SELECT ERROR_ID, FOLIO, ERROR_DATE, ERROR_CODE, TPE_TYPE, IS_NAME, DESCRIPTION, MESSAGE, ACTION FROM WMLOG.WM_LOG_ERROR_TPE WHERE ERROR_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND (TPE_TYPE='SE.CTR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%OLP%' OR TPE_TYPE LIKE '%SW%') ORDER BY ERROR_DATE DESC";
		String query4_1 = "SELECT ERROR_ID, FOLIO, ERROR_DATE, ERROR_CODE FROM WMLOG.WM_LOG_ERROR_TPE WHERE ERROR_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND (TPE_TYPE='SE.CTR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%OLP%' OR TPE_TYPE LIKE '%SW%') ORDER BY ERROR_DATE DESC";
		String query4_2 = "SELECT TPE_TYPE, IS_NAME, DESCRIPTION, MESSAGE, ACTION FROM WMLOG.WM_LOG_ERROR_TPE WHERE ERROR_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND (TPE_TYPE='SE.CTR' OR TPE_TYPE LIKE '%SEC%' OR TPE_TYPE LIKE '%OLP%' OR TPE_TYPE LIKE '%SW%') ORDER BY ERROR_DATE DESC";
		
		String query5	= "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO, PLAZA, TIENDA, CAJA, CARRIER, AMOUNT, TRANS_DT, ADMIN_DATE, CREATION_DATE FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('TRN03','TRN02') and folio='"+ secondFolio + "' ORDER BY CREATION_DATE DESC";
        String query5_1 = "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('TRN03','TRN02') and folio='"+ secondFolio + "' ORDER BY CREATION_DATE DESC";
        String query5_2 = "SELECT PLAZA, TIENDA, CAJA, CARRIER, AMOUNT FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('TRN03','TRN02') and folio='"+ secondFolio + "' ORDER BY CREATION_DATE DESC";
        String query5_3 = "SELECT TRANS_DT, ADMIN_DATE, CREATION_DATE FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('TRN03','TRN02') and folio='"+ secondFolio + "' ORDER BY CREATION_DATE DESC";
        
        String query6	= "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO, PLAZA, TIENDA, CAJA, CARRIER, AMOUNT, TRANS_DT, ADMIN_DATE, CREATION_DATE FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('QRY01','TRN01') ORDER BY CREATION_DATE DESC";
        String query6_1 = "SELECT APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('QRY01','TRN01') ORDER BY CREATION_DATE DESC";
        String query6_2 = "SELECT PLAZA, TIENDA, CAJA, CARRIER, AMOUNT FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('QRY01','TRN01') ORDER BY CREATION_DATE DESC";
        String query6_3 = "SELECT TRANS_DT, ADMIN_DATE, CREATION_DATE FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND APPLICATION='OLP' AND ENTITY='TELCELPAGO' and operation in ('QRY01','TRN01') ORDER BY CREATION_DATE DESC";
        
        String query7   = "SELECT APPLICATION, ENTITY, FOLIO, MTI, PAN, AMOUNT, STAN, RESP_CODE, COUNTER, ATT3, CREATION_DATE, IS_NAME, TIENDA, CAJA  FROM SWUSER.TPE_SW_TLOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "','YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO = '" + secondFolio + "'";
		String query7_1 = "SELECT APPLICATION, ENTITY, FOLIO, MTI, PAN, AMOUNT, STAN FROM SWUSER.TPE_SW_TLOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "','YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO = '" + secondFolio + "'"; 
        String query7_2 = "SELECT RESP_CODE, COUNTER, ATT3, CREATION_DATE, IS_NAME, TIENDA, CAJA  FROM SWUSER.TPE_SW_TLOG WHERE CREATION_DATE BETWEEN TO_DATE('" + CreationDate + "','YYYYMMDDHH24MISS') - INTERVAL '2' MINUTE AND TO_DATE('" + CreationDate + "', 'YYYYMMDDHH24MISS') + INTERVAL '2' MINUTE AND FOLIO = '" + secondFolio + "'";

		/*
		 * Paso 6
		 *****************************************************************************************/
		
        addStep("Conectarse a la Base de Datos: **OXTPEQA_tpeuser**");
   		System.out.println("Paso 5: Conectarse a la Base de Datos: **OXTPEQA_tpeuser**\n");
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
   		    testCase.failStep("Paso 5: La conexión a la base de datos OXTPEQA_tpeuser no se pudo realizar.");
           }

/*
		 * Paso 7
		 *****************************************************************************************/
        System.out.println("Folio 1: " + firstFolio + ", folio 2: " + secondFolio);
        
		addStep("Ejecutar la siguiente consulta para validar que la transacción se registró correctamente en la tabla TPE_FR_TRANSACTION de la BD");
		System.out.println("Paso 7: Ejecutar la siguiente consulta para validar que la transacción se registró correctamente en la tabla TPE_FR_TRANSACTION de la BD\n");
		System.out.println(GlobalVariables.DB_HOST_OXTPEQA_TPEUSER);
		SQLResult result1 = executeQuery(dbOXTPEQA, query1);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query1);
		
		testCase.addTextEvidenceCurrentStep("Resultado esperado: Se muestra los siguientes registros con campos relacionados con la consulta:");
		testCase.addTextEvidenceCurrentStep("Registro QRY01 de Consulta de factura telcel: FOLIO= 6612650056, APPLICATION='OLP', ENTITY='TELCELPAGO', OPERATION=QRY01, MTI=200, WM_CODE=101, WM_DESC=Transacción exitosa.");
		testCase.addTextEvidenceCurrentStep("Registro TRN03 de Pago de factura telcel: FOLIO= 6612650058, APPLICATION='OLP', ENTITY='TELCELPAGO', OPERATION=TRN03, MTI=200, WM_CODE=198, WM_DESC=Transacción exitosa.");
		testCase.addTextEvidenceCurrentStep("egistro TRN03 de Pago de factura telcel: FOLIO= 6612650058, APPLICATION='OLP', ENTITY='TELCELPAGO', OPERATION=TRN03, MTI=420, WM_CODE=198, WM_DESC=Transacción exitosa.");


		
		testCase.addTextEvidenceCurrentStep("Resultado actual: Se muestra los siguientes registros con campos relacionados con la consulta,"
				+ " pago y reversa por caso critico de la factura TELCEL: \n"
				
				+ "*Registro QRY01 de Consulta de factura telcel: FOLIO=" + result1.getData(0, "FOLIO") + ", APPLICATION='" + result1.getData(0, "APPLICATION") 
				+ "', ENTITY='" + result1.getData(0, "ENTITY") + "', OPERATION=" + result1.getData(0, "OPERATION") 
				+ ", MTI=" + result1.getData(0, "MTI") + ", WM_CODE=" + result1.getData(0, "WM_CODE") + ", WM_DESC=" + result1.getData(0, "WM_DESC"));
		
		for (int i = 1; i <= 2; i++) {
		    String evidence = "*Registro TRN03 de Consulta de factura telcel: FOLIO=" + result1.getData(i, "FOLIO") 
		        + ", APPLICATION='" + result1.getData(i, "APPLICATION") 
		        + "', ENTITY='" + result1.getData(i, "ENTITY") + "', OPERATION=" + result1.getData(i, "OPERATION") 
		        + ", MTI=" + result1.getData(i, "MTI") + ", WM_CODE=" + result1.getData(i, "WM_CODE") 
		        + ", WM_DESC=" + result1.getData(i, "WM_DESC");
		    testCase.addTextEvidenceCurrentStep(evidence);
		}
		
		SQLResult result1_1 = executeQuery(dbOXTPEQA, query1_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result1_1);
		
		SQLResult result1_2 = executeQuery(dbOXTPEQA, query1_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result1_2);
		
		SQLResult result1_3 = executeQuery(dbOXTPEQA, query1_3);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte3:");
		testCase.addQueryEvidenceCurrentStep(result1_3);
		
		SQLResult result1_4 = executeQuery(dbOXTPEQA, query1_4);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte4:");
		testCase.addQueryEvidenceCurrentStep(result1_4);
		
		if(!result1.isEmpty()) {
//        	testCase.addQueryEvidenceCurrentStep(result1);
        	
            String[] expectedValues = {application, entity, operation, operationAck, firstFolio, secondFolio, "0200", "0420", wmcodeAut,"198"};
            
            System.out.println("Valores esperados: " + Arrays.toString(expectedValues));
            
            for (int i = 0; i < result1.getRowCount(); i++) {
          	   String[] actualValues = {result1.getData(i, "APPLICATION"),result1.getData(i, "ENTITY"),result1.getData(i, "OPERATION"),
                       result1.getData(i, "FOLIO"),result1.getData(i, "MTI"),result1.getData(i, "WM_CODE") };
          	   
  		        for (String actual : actualValues) {
  		            if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
  		                System.out.println("El valor " + actual + " está contenido en expectedValues");
  		            } else {
  		                System.out.println("El valor " + actual + " no está contenido en expectedValues");
  		                testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
  		             	testCase.failStep("El paso ha fallado");//

  		            }
  		        }
  		    }
    		System.out.println("Query exitosa");
        } else {
        	System.out.println("La consulta está vacía");
        	testCase.addTextEvidenceCurrentStep("La consulta está vacía");
		    testCase.failStep("El paso ha fallado");
        	
        }

/*		* Paso 8
		 *****************************************************************************************/
		addStep("Ejecutar la consulta para validar que la transacción se registró correctamente en la tabla TPE_FR_DOCS_POS de la BD OXTPEQA_tpeuser");
		System.out.println("Paso 8: Ejecutar la consulta para validar que la transacción se registró correctamente en la tabla TPE_FR_DOCS_POS de la BD OXTPEQA_tpeuser\n");
		System.out.println(GlobalVariables.DB_HOST_OXTPEQA_TPEUSER);
		System.out.println("Query: "+query2);
		SQLResult result2 = executeQuery(dbOXTPEQA, query2);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query2);
		
		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: ");
		testCase.addTextEvidenceCurrentStep("Se muestra el registro de la transacción correspondiente al pago de la factura Telcel identificado con el folio:" + secondFolio );
		testCase.addBoldTextEvidenceCurrentStep("Resultado actual: ");
		if(!result2.isEmpty()) {
			testCase.addTextEvidenceCurrentStep("Se muestra el registro de la transacción correspondiente al pago de la factura Telcel identificado con el folio:" + result2.getData(0, "FOLIO") );
		}else{
			testCase.addTextEvidenceCurrentStep("No se muestra el registro de la transacción correspondiente al pago de la factura Telcel identificado con el folio:" + secondFolio );

		}
		
		SQLResult result2_1 = executeQuery(dbOXTPEQA, query2_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result2_1);
		SQLResult result2_2 = executeQuery(dbOXTPEQA, query2_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result2_2);
		SQLResult result2_3 = executeQuery(dbOXTPEQA, query2_3);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte3:");
		testCase.addQueryEvidenceCurrentStep(result2_3);
		
		if(!result2.isEmpty()) {
//       	testCase.addQueryEvidenceCurrentStep(result2);
       	
           String[] expectedValues = {secondFolio};
           
           System.out.println("Valores esperados: " + Arrays.toString(expectedValues));
           
           for (int i = 0; i < result2.getRowCount(); i++) {
        	   String[] actualValues = {result2.getData(i, "FOLIO")};
        	   
		        for (String actual : actualValues) {
		            if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
		                System.out.println("El valor " + actual + " está contenido en expectedValues");
		            } else {
		                System.out.println("El valor " + actual + " no está contenido en expectedValues");
		                testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
		               	testCase.failStep("El paso ha fallado");//

		            }
		        }
		    }
   		System.out.println("Query exitosa");
       } else {
       	System.out.println("La consulta está vacía");
       	testCase.addTextEvidenceCurrentStep("La consulta está vacía");
       	testCase.failStep("El paso ha fallado");//
       	
       }
		
/*    * Paso 9
	     *****************************************************************************************/
		addStep("Ejecutar la consulta para validar que la Operacion TRN04 se registró correctamente en la tabla tpeuser.TPE_FR_SAF de la BD");
		System.out.println("Paso 9: Ejecutar la consulta para validar que la Operacion TRN04 se registró correctamente en la tabla tpeuser.TPE_FR_SAF de la BD\n");
		System.out.println(GlobalVariables.DB_HOST_OXTPEQA_TPEUSER);
		System.out.println("Query: "+query3);
		SQLResult result3 = executeQuery(dbOXTPEQA, query3);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query3);
		
		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: Se muestra los siguientes campos relacionados al pago de la factura TELCEL: ");
		testCase.addTextEvidenceCurrentStep("APPLICATION = " + application 
											  + "\n ENTITY = " + entity 
											  + "\n OPERATION = TRN04"  
											  + "\n ENTITY = " + entity 
											  + "\n MTI = 0420" 
											  + "\n STATUS = INICIAL");
											  
		if(!result3.isEmpty()) {
			testCase.addBoldTextEvidenceCurrentStep("Resultado actual: Se muestra los siguientes campos relacionados al pago de la factura TELCEL: ");
			testCase.addTextEvidenceCurrentStep("APPLICATION = " + result3.getData(0, "APPLICATION") 
											  + "\n ENTITY = " + result3.getData(0, "ENTITY")
											  + "\n OPERATION = " + result3.getData(0, "OPERATION")
											  + "\n ENTITY = " + result3.getData(0, "ENTITY")
											  + "\n MTI = " + result3.getData(0, "MTI")
											  + "\n STATUS = " + result3.getData(0, "STATUS"));
		} else {
			testCase.addBoldTextEvidenceCurrentStep("No se muestran los campos relacionados al pago de la factura TELCEL: ");

		}
											  
		SQLResult result3_1 = executeQuery(dbOXTPEQA, query3_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result3_1);
		SQLResult result3_2 = executeQuery(dbOXTPEQA, query3_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result3_2);
		SQLResult result3_3 = executeQuery(dbOXTPEQA, query3_3);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte3:");
		testCase.addQueryEvidenceCurrentStep(result3_3);

		if(!result3.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(result3);
			String[] expectedValues = {application, entity, trn04, "0420", "INICIAL"};
			
			System.out.println("Valores esperados: " + Arrays.toString(expectedValues));
	           
			for (int i = 0; i < 1; i++) {
	         	  String[] actualValues = {result3.getData(i, "APPLICATION"), result3.getData(i, "ENTITY"), result3.getData(i, "OPERATION"), result3.getData(i, "MTI"), result3.getData(i, "STATUS")};
	         	   
	 		        for (String actual : actualValues) {
	 		            if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
	 		                System.out.println("El valor " + actual + " está contenido en expectedValues");
	 		            } else {
	 		                System.out.println("El valor " + actual + " no está contenido en expectedValues");
	 		                testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
	 		              	testCase.failStep("El paso ha fallado");//

	 		            }
	 		        }
	 		    }
			System.out.println("Query exitosa");
		}else{
			System.out.println("La consulta está vacía");
			testCase.addTextEvidenceCurrentStep("La consulta está vacíaa");
		    testCase.failStep("El paso ha fallado");
		}

/*		 * Paso 10
		 *****************************************************************************************/
		
        addStep("Conectarse a la Base de Datos: **DB_HOST_OXFINDBQ_WMLOG**");
   		System.out.println("Paso 5: Conectarse a la Base de Datos: **DB_HOST_OXFINDBQ_WMLOG**\n");
   		testCase.addTextEvidenceCurrentStep("Resultado esperado: La conexión con la Base de Datos DB_HOST_OXFINDBQ_WMLOG fue exitosa y responde con la leyenda: CORRECTO");
           String urloxfindbq = "jdbc:oracle:thin:@"+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG;
           String usernameoxfindbq = GlobalVariables.DB_USER_OXFINDBQ_WMLOG;
           String passwordoxfindbq = GlobalVariables.DB_PASSWORD_OXFINDBQ_WMLOG;
    
           try {
               Connection connection = DriverManager.getConnection(urloxfindbq, usernameoxfindbq, passwordoxfindbq);
   			System.out.println("Resultado actual: La conexión con la Base de Datos DB_HOST_OXFINDBQ_WMLOG: "+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
               testCase.addBoldTextEvidenceCurrentStep("Resultado actual: La conexión con la Base de Datos DB_HOST_OXFINDBQ_WMLOG: "+GlobalVariables.DB_HOST_OXFINDBQ_WMLOG+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
               connection.close();
           } catch (SQLException e) {
               System.err.println("Connection failed. Error message: " + e.getMessage()+"\n");
   		    testCase.addBoldTextEvidenceCurrentStep("La conexión a la base de datos DB_HOST_OXFINDBQ_WMLOG no se pudo realizar.");
   		    testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_OXFINDBQ_WMLOG);
   		    testCase.failStep("Paso 5: La conexión a la base de datos DB_HOST_OXFINDBQ_WMLOG no se pudo realizar.");
           }
        
        
/*		 * Paso 11
		 *****************************************************************************************/
		addStep(" Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacción");
		System.out.println("Paso 11: Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacción\n");
		System.out.println(GlobalVariables.DB_HOST_OXFINDBQ_WMLOG);
		System.out.println(query4);
		SQLResult result4 = executeQuery(dbOXFINDBQ, query4);
		testCase.addBoldTextEvidenceCurrentStep("Primera Query Completa:");
		testCase.addTextEvidenceCurrentStep(query4);
		
		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: ");
    	testCase.addTextEvidenceCurrentStep("La consulta no muestra errores para el TELCELPAGO.");
		
		if(result4.isEmpty()) {
    		System.out.println("Query exitosa, La consulta no muestra errores para el TELCELPAGO.");
    		testCase.addBoldTextEvidenceCurrentStep("Resultado actual: ");
        	testCase.addTextEvidenceCurrentStep("La consulta no muestra errores para el TELCELPAGO.");

        } else {
        	System.out.println("La consulta muestra errores para el TELCELPAGO.");
        	testCase.addTextEvidenceCurrentStep("Se muestran los resultados de la query");
    	    testCase.failStep("El paso ha fallado");

        	
        }

		SQLResult result4_1 = executeQuery(dbOXFINDBQ, query4_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result4_1);
		SQLResult result4_2 = executeQuery(dbOXFINDBQ, query4_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result4_2);

		
		

		
/*		*Paso 12
 		******************************************************************************************/
		addStep(" Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacción, para validar el registro de los servicios invocados de la OLP");
		System.out.println("Paso 12: Realizar la consulta en la BD **OXFINDBQ _wmlog** del Site en la que se realizó la transacción, para validar el registro de los servicios invocados de la OLP\n");
		System.out.println(GlobalVariables.DB_HOST_OXFINDBQ_WMLOG);
		System.out.println(query5);
		SQLResult result5 = executeQuery(dbOXFINDBQ, query5);
		testCase.addBoldTextEvidenceCurrentStep("Primera Query Completa:");
		testCase.addTextEvidenceCurrentStep(query5);
		System.out.println(query6);
		SQLResult result6 = executeQuery(dbOXFINDBQ, query6);
		testCase.addBoldTextEvidenceCurrentStep("Segunda Query Completa:");
		testCase.addTextEvidenceCurrentStep(query6);

		
		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado primer query: ");
    	testCase.addTextEvidenceCurrentStep("La consulta muestra las invocaciones de los servicios invocados de la interface TPE_OLP(TRN02, TRN03) "
    			+ "para la consulta, el pago y la reversa de factura TELCEL que no viajaron por el canal seguro de F5.");
		
    	if(!result5.isEmpty()) {
    		testCase.addBoldTextEvidenceCurrentStep("Resultado actual primer query: ");
        	testCase.addTextEvidenceCurrentStep("La consulta muestra las invocaciones de los servicios invocados de la interface TPE_OLP(TRN02, TRN03) "
        			+ "para la consulta, el pago y la reversa de factura TELCEL que no viajaron por el canal seguro de F5.");
        	
            String[] expectedValues = {application, entity, secondFolio, operation, operationFol, operationAut, operationAck};
            
            System.out.println("Valores esperados: " + Arrays.toString(expectedValues));
	           
            for (int i = 0; i < result5.getRowCount(); i++) {
                String[] actualValues = {result5.getData(i, "APPLICATION"),result5.getData(i, "ENTITY"),result5.getData(i, "FOLIO"), result5.getData(i, "OPERATION")};
                
                for (String actual : actualValues) {
                    if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
                        System.out.println("El valor " + actual + " está contenido en expectedValues");
                    } else {
                        System.out.println("El valor " + actual + " no está contenido en expectedValues");
                        testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
                       	testCase.failStep("El paso ha fallado");//

                    }
                }
            }
    		System.out.println("Query exitosa");

        } else {
        	System.out.println("La consulta está vacía");
    		testCase.addBoldTextEvidenceCurrentStep("Resultado actual: ");
        	testCase.addTextEvidenceCurrentStep("La consulta está vacía");
    	    testCase.failStep("El paso ha fallado");

        }
    	
		SQLResult result5_1 = executeQuery(dbOXFINDBQ, query5_1);
		testCase.addBoldTextEvidenceCurrentStep("Primera Query Parte 1:");
		testCase.addQueryEvidenceCurrentStep(result5_1);
		SQLResult result5_2 = executeQuery(dbOXFINDBQ, query5_2);
		testCase.addBoldTextEvidenceCurrentStep("Primera Query Parte 2:");
		testCase.addQueryEvidenceCurrentStep(result5_2);
		SQLResult result5_3 = executeQuery(dbOXFINDBQ, query5_3);
		testCase.addBoldTextEvidenceCurrentStep("Primera Query Parte 3:");
		testCase.addQueryEvidenceCurrentStep(result5_3);
		

		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado segunda query: ");
    	testCase.addTextEvidenceCurrentStep("La consulta muestra las invocaciones de los servicios invocados de la interrface TPE_OLP(QRY01, TRN01) para la consulta, el pago y la reversa de factura TELCEL que no viajaron por el canal seguro de F5.");

		
		if(!result6.isEmpty()) {
    		testCase.addBoldTextEvidenceCurrentStep("Resultado actual segunda query: ");
        	testCase.addTextEvidenceCurrentStep("La consulta muestra las invocaciones de los servicios invocados de la interface TPE_OLP(QRY01, TRN01) "
        			+ "para la consulta, el pago y la reversa de factura TELCEL que no viajaron por el canal seguro de F5.");
            String[] expectedValues = {application, entity, secondFolio, operation, operationFol, operationAut, operationAck};
            
            System.out.println("Valores esperados: " + Arrays.toString(expectedValues));
	           
            for (int i = 0; i < result6.getRowCount(); i++) {
                String[] actualValues = {result6.getData(i, "APPLICATION"),result6.getData(i, "ENTITY"), result6.getData(i, "OPERATION")};
                                
                for (String actual : actualValues) {
                    if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
                        System.out.println("El valor " + actual + " está contenido en expectedValues");
                    } else {
                        System.out.println("El valor " + actual + " no está contenido en expectedValues");
                        testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
                       	testCase.failStep("El paso ha fallado");//

                    }
                }
            }
            System.out.println("Query exitosa");

        } else {
        	System.out.println("La consulta está vacía");
    		testCase.addBoldTextEvidenceCurrentStep("Resultado actual: ");
        	testCase.addTextEvidenceCurrentStep("La consulta está vacía");
    	    testCase.failStep("El paso ha fallado");

        }
		
		SQLResult result6_1 = executeQuery(dbOXFINDBQ, query6_1);
		testCase.addBoldTextEvidenceCurrentStep("Segunda Query Parte 1:");
		testCase.addQueryEvidenceCurrentStep(result6_1);
		SQLResult result6_2 = executeQuery(dbOXFINDBQ, query6_2);
		testCase.addBoldTextEvidenceCurrentStep("Segunda Query Parte 2:");
		testCase.addQueryEvidenceCurrentStep(result6_2);
		SQLResult result6_3 = executeQuery(dbOXFINDBQ, query6_3);
		testCase.addBoldTextEvidenceCurrentStep("Segunda Query Parte 3:");
		testCase.addQueryEvidenceCurrentStep(result6_3);
		
		
	
		
/*		 * Paso 13
		 *****************************************************************************************/

        addStep("Conectarse a la Base de Datos: **OXSWTAE_swuser**");
   		System.out.println("Paso 5: Conectarse a la Base de Datos: **OXSWTAE_swuser**\n");
   		testCase.addTextEvidenceCurrentStep("Resultado esperado: La conexión con la Base de Datos OXSWTAE_swuser fue exitosa y responde con la leyenda: CORRECTO");
           String urloxwtae = "jdbc:oracle:thin:@"+GlobalVariables.DB_HOST_OXSWTAE;
           String usernameoxwtae = GlobalVariables.DB_USER_OXSWTAE;
           String passwordoxwtae = GlobalVariables.DB_PASSWORD_OXSWTAE;
    
           try {
               Connection connection = DriverManager.getConnection(urloxwtae, usernameoxwtae, passwordoxwtae);
   			System.out.println("Resultado actual: La conexión con la Base de Datos OXSWTAE_swuser: "+GlobalVariables.DB_HOST_OXSWTAE+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
               testCase.addBoldTextEvidenceCurrentStep("Resultado actual: La conexión con la Base de Datos OXSWTAE_swuser: "+GlobalVariables.DB_HOST_OXSWTAE+"\n"+ "fue exitosa y responde con la leyenda: CORRECTO");
               connection.close();
           } catch (SQLException e) {
               System.err.println("Connection failed. Error message: " + e.getMessage()+"\n");
   		    testCase.addBoldTextEvidenceCurrentStep("La conexión a la base de datos OXSWTAE_swuser no se pudo realizar.");
   		    testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_OXSWTAE);
   		    testCase.failStep("Paso 5: La conexión a la base de datos OXSWTAE_swuser no se pudo realizar.");
           }
		
/*		* Paso 14
		 *****************************************************************************************/
		addStep("Ejecutar la siguiente consulta para validar la transaccion en la base de datos del *OXSWTAE_swuser*");
		System.out.println("Paso14: Ejecutar la siguiente consulta para validar la transaccion en la base de datos del *OXSWTAE_swuser*\n");
		System.out.println(GlobalVariables.DB_HOST_OXSWTAE);
		System.out.println("Query: "+query7);
		SQLResult result7 = executeQuery(dbOXWTAE , query7);
		testCase.addBoldTextEvidenceCurrentStep("Query Completa:");
		testCase.addTextEvidenceCurrentStep(query7);
		
		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado:");
		testCase.addTextEvidenceCurrentStep("Validar que se muestren los registro con folio= "+ secondFolio +", MTI=0200 (Solicitud de autorización), con el RESP_CODE=00 ");
		
		if(!result7.isEmpty()) {
	   		testCase.addBoldTextEvidenceCurrentStep("Resultado actual:");
			testCase.addTextEvidenceCurrentStep("Validar que se muestren los registro con folio= "+ result7.getData(0, "FOLIO") +", MTI= "+result7.getData(0, "MTI")+", con el RESP_CODE="+result7.getData(0, "RESP_CODE")+" ");

           String[] expectedValues = {secondFolio, "0200", "00"};
           
           System.out.println("Valores esperados: " + Arrays.toString(expectedValues));
           
           for (int i = 0; i < result7.getRowCount(); i++) {
               String[] actualValues = {result7.getData(i, "FOLIO"), result7.getData(i, "MTI"), result7.getData(i, "RESP_CODE")};
               
               for (String actual : actualValues) {
                   if (actual != null && Arrays.asList(expectedValues).contains(actual)) {
                       System.out.println("El valor " + actual + " está contenido en expectedValues");
                   } else {
                       System.out.println("El valor " + actual + " no está contenido en expectedValues");
                       testCase.addTextEvidenceCurrentStep("El valor " + actual + " no está contenido en expectedValues");
                      	testCase.failStep("El paso ha fallado");//

                   }
               }
           }
   		System.out.println("Query exitosa");
       } else {
       	System.out.println("La consulta está vacía");
   		testCase.addBoldTextEvidenceCurrentStep("Resultado actual:");
       	testCase.addTextEvidenceCurrentStep("La consulta está vacía");
	    testCase.failStep("El paso ha fallado");
       	
       }
		
		SQLResult result7_1 = executeQuery(dbOXWTAE, query7_1);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte1:");
		testCase.addQueryEvidenceCurrentStep(result7_1);
		SQLResult result7_2 = executeQuery(dbOXWTAE, query7_2);
		testCase.addBoldTextEvidenceCurrentStep("Query Parte2:");
		testCase.addQueryEvidenceCurrentStep(result7_2);
		
		

	}
	
    public static String getFolioXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);
		
		return eElement.getAttribute("folio");
	}
    public static String getAccountIdXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("service").item(0);
		
		return eElement.getAttribute("accountId");
	}
	
	public static String getFolioXmlwmCode(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("wmCode").item(0);
		
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
	public static String getOperationXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("header").item(0);

		return eElement.getAttribute("operation");
	}
	public static String getidClientXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("service").item(0);

		return eElement.getAttribute("idClient");
	}
	
    public static String getaccountIdXml(String xmlResponse) {
		Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(xmlResponse);
		Element eElement = (Element) runGetFolioRequestDoc.getElementsByTagName("service").item(0);
		
		return eElement.getAttribute("accountId");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_TPE_OLP_RealizarCasoCriticoPagoEnLineaDeFacturaTELCELExitoso_ActBD19c_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Prueba de regresión para comprobar la no afectación en la funcionalidad de reversas de pago de factura Telcel de la interface FEMSA_TPE_OLP, al ser migrada la base de datos del SWITCH a version 19C y separada (TAE y TDC).";
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
		return "*Contar con acceso a las bases de datos de OXTPEQA_tpeuser, OXSWTAEQA_swuser, OXFINDBQ _wmlog.\r\n"
				+ "*Contar con las credenciales de WM10.11 de los nuevos servers del Integration Server de QA.";
	}
}
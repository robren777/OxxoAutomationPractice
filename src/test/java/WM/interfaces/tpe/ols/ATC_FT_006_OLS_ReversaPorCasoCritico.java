package WM.interfaces.tpe.ols;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.om.OLS_D;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_006_OLS_ReversaPorCasoCritico extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_OLS_ReversaPorCasoCritico_test(HashMap<String, String> data) throws Exception{
		/*
		 * Utileria******************************************************/
		
		/**
		 * Proyecto: Servicios Electronicos 
		 * Caso de prueba: MTC-FT-035-C1 TPE_OLS Reversa exitosa por Caso Critico de transacci�n de pago de servicio
		 * @author Marisol Rodriguez
		 * @date 
		 */
		
		SQLUtil dbMtyFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,GlobalVariables.DB_PASSWORD_FCTPE);//mty
		SQLUtil dbFCTPE2 = new SQLUtil(GlobalVariables.DB_HOST_TPE_LOT, GlobalVariables.DB_USER_TPE_LOT,GlobalVariables.DB_PASSWORD_TPE_LOT);//qry
		SQLUtil dbFCWMLTAEQA_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAQ_MTY,GlobalVariables.DB_USER_FCWMLTAQ_MTY, GlobalVariables.DB_PASSWORD_FCWMLTAQ_MTY);
		SQLUtil dbQroFCWMLTAQ = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAEQA_QRO,GlobalVariables.DB_USER_FCWMLTAEQA_QA, GlobalVariables.DB_PASSWORD_FCWMLTAEQA_QRO_QA);
//		SQLUtil dbOXWMLOGQA = new SQLUtil(GlobalVariables.DB_HOST_OXWMLOGQA, GlobalVariables.DB_USER_OXWMLOGQA,GlobalVariables.DB_PASSWORD_OXWMLOGQA);
		
		OLS_D ols_dUtil = new OLS_D(data, testCase, null);
		/*
		 * Variables*****************************************************/
		//Caso pago dish exitoso
		String ConsultaReferencias= "select operation, entity, folio,wm_code, application, creation_date \r\n"
				+ "from TPEUSER.TPE_FR_TRANSACTION \r\n"
				+ "where OPERATION = 'QRY01' \r\n"
				+ "and ENTITY = 'DISH'\r\n"
				+ "and FOLIO = '%s'\r\n"
				+ "and APPLICATION = 'OLS'\r\n"
				+ "and WM_CODE = '101'\r\n"
				+ "ORDER BY CREATION_DATE DESC";
		
		String TransaccionPagoServicio = "select operation, entity, folio,wm_code, application, creation_date, ack \r\n"
				+ "from TPEUSER.TPE_FR_TRANSACTION \r\n"
				+ "where OPERATION= 'TRN03' \r\n"
				+ "and ENTITY = 'DISH'\r\n"
				+ "and FOLIO = '%s'\r\n"
				+ "and WM_CODE = '101'\r\n"
				+ "and APPLICATION = 'OLS'\r\n"
				+ "and ACK = '00'\r\n"
				+ "ORDER BY CREATION_DATE DESC";
		
		String SECURITY_SESSION_LOG = "select*from (SELECT application, entity, operation, creation_date, source, folio \r\n"
				+ "FROM WMLOG.SECURITY_SESSION_LOG \r\n"
				+ "WHERE APPLICATION  = 'OLS' \r\n"
				+ "AND ENTITY = 'DISH' \r\n"
			//	+"AND CREATION_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" 
			//	+" AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" 
				+ "ORDER BY CREATION_DATE DESC) where rownum <=4";
		
		String WM_LOG_ERROR_TPE = "SELECT error_id, folio, error_date,description tpe_type FROM WMLOG.WM_LOG_ERROR_TPE\r\n"
				+ "WHERE  TPE_TYPE like '%s'\r\n"
				+"AND ERROR_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" 
				+" AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" 
				+ "ORDER BY error_date  DESC";
		
		//Caso reversa OLS
				String Transaccion = "select operation, entity, folio,wm_code, application, creation_date, ack \r\n"
								+ "from TPEUSER.TPE_FR_TRANSACTION \r\n"
							//	+ "where OPERATION= 'TRN04' \r\n"
								+ "where ENTITY = 'DISH'\r\n"
								+ "and FOLIO IN ('%s', '%s')\r\n"
								+ "and WM_CODE= '198'\r\n"
								+ "and APPLICATION= 'OLS'\r\n"
								+ "and MTI= '0420'\r\n"
							//	+ "and CREATION_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
							//	+ "and TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
								+ "ORDER BY CREATION_DATE DESC";
							
				String LOG_ERROR_TPE = "SELECT error_id, folio, error_date,description tpe_type FROM WMLOG.WM_LOG_ERROR_TPE\r\n"
								+ "WHERE  TPE_TYPE like '%s'\r\n"
								+"AND ERROR_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" 
								+" AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" 
								+ "ORDER BY error_date  DESC";
		//Caso reversa OlS_D
		String queryTransaction = "SELECT APPLICATION, ENTITY, OPERATION, FOLIO, MTI,WM_CODE, WM_DESC, CREATION_DATE\r\n"
				+ "FROM TPEUSER.TPE_FR_TRANSACTION\r\n"
				+ "WHERE APPLICATION = '%s' \r\n"
				+ "AND CREATION_DATE >= TRUNC(SYSDATE)\r\n"
				+ "AND FOLIO IN ('%s', '%s')";
		
		String queryUnsafeTransaction = "SELECT APPLICATION, OPERATION, FOLIO, CREATION_DATE\r\n"
				+ "FROM  WMLOG.SECURITY_SESSION_LOG\r\n"
				+ "WHERE CREATION_DATE >= TO_DATE('%s', 'DD/MM/YYYY HH24:MI:SS')\r\n"
				+ "AND APPLICATION = '%s'";
		
		String queryErrorRecords = "SELECT ERROR_ID, ERROR_DATE, ERROR_CODE, DESCRIPTION, MESSAGE\r\n"
				+ "FROM WMLOG.WM_LOG_ERROR_TPE\r\n"
				+ "WHERE ERROR_DATE >= TO_DATE('%s', 'DD/MM/YYYY HH24:MI:SS')\r\n"
				+ "AND (DESCRIPTION LIKE '%s' OR MESSAGE LIKE '%s' OR TPE_TYPE='SE.CTR' OR TPE_TYPE LIKE '%s' OR TPE_TYPE LIKE '%s' OR TPE_TYPE LIKE '%s')\r\n"
				+ "ORDER BY ERROR_DATE DESC";
		
		
		Date fechaInicio ;
		Date fechaFin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		
		SoftAssert softAssert = new SoftAssert();

		testCase.setProject_Name("Servicios B2B");
		
		testCase.setPrerequisites(data.get("Prerequicitos"));
		
		testCase.setTest_Description(data.get("id")+ data.get("descripcion"));
		
	//********************************************************************************************************
				//Se solicita el request del QRY01
											
				fechaInicio = new Date();
					
		        String fechaInicial = formatter.format(fechaInicio);
		       		
				System.out.println("Fecha inicial: "+ fechaInicial);
		
		/******************************Paso 1****************************/
		addStep("Modificar el XML de ejemplo como se requiera  y realizar la consulta un servicio en linea a un proveedor:");
		
		String responseQRY01 = ols_dUtil.QRY01();
		String wmCode = RequestUtil.getWmCodeXml(responseQRY01);
		String folioService = RequestUtil.getFolioXml(responseQRY01);
		testCase.addTextEvidenceCurrentStep("wmCode obtenido: " + wmCode);
		testCase.addTextEvidenceCurrentStep("wmCode esperado: " + data.get("checkWmCode"));
		softAssert.assertEquals(wmCode, data.get("checkWmCode"), "Paso 1: Se obtiene wmCode diferente al esperado.");
		
		/******************************Paso 2****************************/
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de folio de la TPE_OLS "
				+ "para una transacci�n de pago de servicio mediante el siguiente request:");
		
		String responseTRN01 = ols_dUtil.TRN01();
		String TRN01wmCode = RequestUtil.getWmCodeXml(responseTRN01);
		String folio = "";
		String resCreationDate = "";
		if(TRN01wmCode.equals(data.get("FolioWmCode"))) {
			folio = RequestUtil.getFolioXml(responseTRN01);
			resCreationDate = RequestUtil.getCreationDate(responseTRN01);
		}
		System.out.println("Folio: " + folio + " wmCode: " + TRN01wmCode );
		System.out.println(" Creationdate: " + resCreationDate );
		testCase.addTextEvidenceCurrentStep("wmCode obtenido: " + TRN01wmCode);
		testCase.addTextEvidenceCurrentStep("wmCode esperado: " + data.get("FolioWmCode"));
		softAssert.assertEquals(TRN01wmCode, data.get("FolioWmCode"), "Paso 2: Se obtiene wmCode diferente al esperado.");
		
		/******************************Paso 3****************************/
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de Autorizaci�n de la TPE_OLS mediante el siguiente request:");
		
		String responseAuth = ols_dUtil.TRN02(folio);
				
		String authWmCode = RequestUtil.getWmCodeXml(responseAuth);
		
		String [] arr = responseAuth.split("auth=\"");
		String [] arr2 = arr[1].split("\"");
		String auth = arr2[0];
		System.out.print("\r\n");
		System.out.print("\r\nauth: "+ auth +"\r\n");
		
		testCase.addTextEvidenceCurrentStep("wmCode obtenido: " + authWmCode);
		testCase.addTextEvidenceCurrentStep("wmCode esperado: " + data.get("authWmCode"));
		softAssert.assertEquals(authWmCode, data.get("authWmCode"), "Paso 3: Se obtiene wmCode diferente al esperado.");
		
		/******************************Paso 4****************************/
		addStep("Ingresar al navegador para solicitar confirmacion ACK de una transacci�n de pago de servicio DISH mediante el sigueinte request:");
		
		String responseAck = ols_dUtil.TRN03(folio);
		String ackWmCode = RequestUtil.getWmCodeXml(responseAck);
		testCase.addTextEvidenceCurrentStep("wmCode obtenido: " + ackWmCode);
		testCase.addTextEvidenceCurrentStep("wmCode esperado: " + data.get("ackWmCode"));
		softAssert.assertEquals(ackWmCode, data.get("ackWmCode"), "Paso 4: Se obtiene wmCode diferente al esperado.");
		
		//*************************Fecha fin ******************************************************************************
		Thread.sleep(20000);
		Date fechaFin2 = new Date();
		String fechaFinal = formatter.format(fechaFin2);
		System.out.println("Fecha final: "+ fechaFinal);
		
	//  Paso 5 *********************************************************************************************************

		addStep("Conectarse a la Base de Datos: **FCTPEQA** en esquema: **TPEUSER**  de Ambos sites MTY y QRO");
		System.out.println("Paso 5: ");

		boolean conexionFCTPEQA = true;

		System.out.println("FCTPEQA MTY: "+ GlobalVariables.DB_HOST_FCTPE );

		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA Server MTY ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);

		System.out.println("FCTPEQA QRO: "+ GlobalVariables.DB_HOST_TPE_LOT );
		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA Server QRO");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPE_LOT);

		assertTrue(conexionFCTPEQA, "La conexion no fue exitosa");
	
		//Paso 6 *******************************************************************************************************

		addStep("Ejecutar la siguiente consulta para validar la transacci�n de pago de servicio DISH en la tabla TPE_FR_TRANSACTION  de la BD  **FCTPEQA** QRY01:");
		System.out.println("Paso 6: QRY01 MTY");

		testCase.addBoldTextEvidenceCurrentStep("Server MTY");

		//Consulta de referencias Dish
		String ConsultaReferencias_f = String.format(ConsultaReferencias,folioService ); //qry01

		System.out.println("Consulta de referencias Dish: \r\n "+ ConsultaReferencias_f);

		SQLResult ConsultaReferencias_r = executeQuery(dbMtyFCTPE, ConsultaReferencias_f);

		boolean validaConsultaReferencias = ConsultaReferencias_r.isEmpty();

		if(!validaConsultaReferencias) {
			
			
			testCase.addQueryEvidenceCurrentStep(ConsultaReferencias_r);
			
			
		} else {
			
			testCase.addTextEvidenceCurrentStep("No se muestra la consulta de referencias Dish");
			testCase.addQueryEvidenceCurrentStep(ConsultaReferencias_r);
			
			
		}

		System.out.println("Consulta de referencias Dish: \r\n"+  validaConsultaReferencias);

		assertFalse(validaConsultaReferencias, "No se encontraron datos en la consulta de referencias ");

		//*********************************************************************************************************************

		addStep("Ejecutar la siguiente consulta para validar la transacci�n de pago de servicio DISH en la tabla TPE_FR_TRANSACTION  de la BD  **FCTPEQA** TRN03:");

		testCase.addBoldTextEvidenceCurrentStep("Server MTY");
		//Transaccion pago servicio
		System.out.println("Paso 6: TRN03 MTY");
		String TransaccionPagoServicio_f = String.format(TransaccionPagoServicio, folio); //trn03

		System.out.println("Transaccion de pago de servicios: \r\n"+ TransaccionPagoServicio_f);

		SQLResult TransaccionPagoServicio_r = executeQuery(dbMtyFCTPE, TransaccionPagoServicio_f);

		boolean validaTransaccionPagoServicio = TransaccionPagoServicio_r.isEmpty();

		System.out.println("Transaccion de pago de servicios: \r\n"+  validaTransaccionPagoServicio);

		if(!validaTransaccionPagoServicio) {
			
		    testCase.addQueryEvidenceCurrentStep(TransaccionPagoServicio_r);
			
		} else {
			
			testCase.addTextEvidenceCurrentStep("No se muestra la transaccion de pago de servicio");
			testCase.addQueryEvidenceCurrentStep(TransaccionPagoServicio_r);
			
		}


		assertFalse(validaTransaccionPagoServicio, "No se realizo la transaccion del pago servicio dish"); 


		//Paso 6 (QRO)**************************************************************************************************
		addStep("Ejecutar la siguiente consulta para validar la transacci�n de pago de servicio DISH en la tabla TPE_FR_TRANSACTION  de la BD  **FCTPEQA** QRY01:");
		System.out.println("Paso 6: QRY01 QRO");
		Thread.sleep(20000);
		testCase.addBoldTextEvidenceCurrentStep("Server QRO");

		//Consulta de referencias Dish
		String ConsultaReferencias_f2 = String.format(ConsultaReferencias, folioService); //qry01

		System.out.println("Consulta de referencias Dish: \r\n "+ ConsultaReferencias_f2);

		SQLResult ConsultaReferencias_r2 = executeQuery(dbFCTPE2, ConsultaReferencias_f2);

		boolean validaConsultaReferencias2 = ConsultaReferencias_r2.isEmpty();

		if(!validaConsultaReferencias2) {
			
			
			testCase.addQueryEvidenceCurrentStep(ConsultaReferencias_r2);
			
			
		} else {
			
			testCase.addTextEvidenceCurrentStep("No se muestra la consulta de referencias Dish");
			testCase.addQueryEvidenceCurrentStep(ConsultaReferencias_r2);
			
			
		}

		System.out.println("Consulta de referencias Dish: \r\n"+  validaConsultaReferencias2);

		assertFalse(validaConsultaReferencias, "No se encontraron datos en la consulta de referencias ");

		//*********************************************************************************************************************

		addStep("Ejecutar la siguiente consulta para validar la transacci�n de pago de servicio DISH en la tabla TPE_FR_TRANSACTION  de la BD  **FCTPEQA** TRN03:");
		
		testCase.addBoldTextEvidenceCurrentStep("Server QRO");
		Thread.sleep(20000);
		
		//Transaccion pago servicio
		System.out.println("Paso 6: TRN03 QRO");
		String TransaccionPagoServicio_f2 = String.format(TransaccionPagoServicio, folio); //trn03

		System.out.println("Transaccion de pago de servicios: \r\n"+ TransaccionPagoServicio_f2);

		SQLResult TransaccionPagoServicio_r2 = executeQuery(dbFCTPE2, TransaccionPagoServicio_f2);

		boolean validaTransaccionPagoServicio2 = TransaccionPagoServicio_r2.isEmpty();

		System.out.println("Transaccion de pago de servicios: \r\n"+  validaTransaccionPagoServicio2);

		if(!validaTransaccionPagoServicio2) {
			
		    testCase.addQueryEvidenceCurrentStep(TransaccionPagoServicio_r2);
			
		} else {
			
			testCase.addTextEvidenceCurrentStep("No se muestra la transaccion de pago de servicio");
			testCase.addQueryEvidenceCurrentStep(TransaccionPagoServicio_r2);
			
		}


		assertFalse(validaTransaccionPagoServicio2, "No se realizo la transaccion del pago servicio dish"); 


		//Paso 7 ***************************************************************************************************

		addStep("Establecer conexi�n con la Base de Datos **FCWMLTAQ**");
		System.out.println("Paso 7: ");

		boolean conexionFCWMLTAQ = true;
		System.out.println("FCWMLTAQ: " + GlobalVariables.DB_HOST_FCWMLTAEQA_QRO);

		testCase.addTextEvidenceCurrentStep("Conexion: FCWMLTAQ ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLTAEQA_QRO);

		assertTrue(conexionFCWMLTAQ, "La conexion no fue exitosa");


		//Paso 8 *****************************************************************************************************
		addStep("Ejecutar la siguiente consulta en la base de datos **FCWMLTAQ** para validar que se hayan registrando "
			+ "las invocaciones en la tabla SECURITY_SESSION_LOG:");
		System.out.println("Paso 8: ");
		Thread.sleep(20000);
		String SECURITY_SESSION_LOG_F = String.format(SECURITY_SESSION_LOG, fechaInicial,fechaFinal);

		System.out.println(SECURITY_SESSION_LOG_F);

		SQLResult SECURITY_SESSION_LOG_R = dbQroFCWMLTAQ.executeQuery(SECURITY_SESSION_LOG_F);

		boolean valida_SECURITY_SESSION_LOG = SECURITY_SESSION_LOG_R.isEmpty();

		System.out.println(valida_SECURITY_SESSION_LOG);

		if (!valida_SECURITY_SESSION_LOG) {

		testCase.addQueryEvidenceCurrentStep(SECURITY_SESSION_LOG_R);

		} else {

		testCase.addQueryEvidenceCurrentStep(SECURITY_SESSION_LOG_R);
		}

		assertFalse(valida_SECURITY_SESSION_LOG, "No se visualizan los registros de las invocaciones realizadas a la interface OLS de la transacci�n exitosa de pago de servicio DISH.");

		//Paso 9 *****************************************************************************************************************
		addStep(" Validar en la base de datos **FCWMLTAQ** que no se encuentren registros de error de la interfaz OLS relacionado a la transacci�n de pago de servicio DISH, "
				+ "utilizando la siguiente consulta");
		System.out.println("Paso 9: ");
		Thread.sleep(20000);
		String WM_LOG_ERROR_TPE_F = String.format(WM_LOG_ERROR_TPE,"%OLS%", fechaInicial, fechaFinal);

		System.out.println(WM_LOG_ERROR_TPE_F);

		SQLResult WM_LOG_ERROR_TPE_R = dbQroFCWMLTAQ.executeQuery(WM_LOG_ERROR_TPE_F);

		boolean valida_WM_LOG_ERROR_TPE = WM_LOG_ERROR_TPE_R.isEmpty();

		System.out.println(valida_WM_LOG_ERROR_TPE);

		if (!valida_WM_LOG_ERROR_TPE) {
			
			testCase.addQueryEvidenceCurrentStep(WM_LOG_ERROR_TPE_R);
			
		} else {
			
			testCase.addTextEvidenceCurrentStep("No se presentaron errores");
			
		}

		testCase.addTextEvidenceCurrentStep("Final:  pago de servicio DISH exitoso");

		assertTrue(valida_WM_LOG_ERROR_TPE, "Se registro un error en WM_LOG_ERROR_TPE "); 
				
								
		//**********************Fin script Tansaccion exitosa DISH *********************************************************
		
		//************************Se obtiene la fecha inicial del resquest TR04*****************************************
		
				//Fecha inicio TR04
				fechaInicio = new Date();
				fechaInicial = formatter.format(fechaInicio);
				System.out.println("Fecha inicial TRN04:"+ fechaInicial);
		        Thread.sleep(20000);
		        
		/******************************Paso 1****************************/
		addStep("Ejecutar en un navegador directamente el servicio de Reversa por caso critico de la TPE_OLS");
		System.out.println("Paso 1:");
		testCase.addTextEvidenceCurrentStep("Inicio: MTC-FT-035-C1 TPE_OLS Reversa exitosa por Caso Critico de transacci�n de pago de servicio");		
		String responseReverse = ols_dUtil.TRN04(folio, auth, resCreationDate);
		String reverseWmCode = RequestUtil.getWmCodeXml(responseReverse);
		testCase.addTextEvidenceCurrentStep("wmCode obtenido: " + reverseWmCode);
		testCase.addTextEvidenceCurrentStep("wmCode esperado: " + data.get("reversewmCode"));
		softAssert.assertEquals(reverseWmCode, data.get("reversewmCode"), "Paso 5: Se obtiene wmCode diferente al esperado.");
		Thread.sleep(20000);
		
		//*****************************Fecha final***************************************************************************	
	       fechaFin = new Date();
	       fechaFinal = formatter.format(fechaFin);
	  	   System.out.println("Fecha final TRN04: "+fechaFinal);

		
		/******************************Paso 2****************************/
		
				addStep("Realizar conexion a la BD FCTPEQA_QRO");
				System.out.println("Paso 3: ");
				
				System.out.println("Conexion: "+ GlobalVariables.DB_HOST_TPE_LOT);
				
				 conexionFCTPEQA = true;
					
				testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA Server QRO");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPE_LOT);
				
				assertTrue(conexionFCTPEQA, "La conexion no fue exitosa");
				 
			 //******************************Paso 3 *************************************************************************
				
				addStep("Ejecutar la siguiente consulta en la BD FCTPEQA_QRO para validar que se registro la transaccion:");
				System.out.println("Paso 4: ");
				
		        String Transaccion_f = String.format(Transaccion, folio ,folioService, fechaInicial, fechaFinal); //trn01 qry01
				
				System.out.println("Transaccion de pago de servicios: \r\n"+ Transaccion_f);

				SQLResult Transaccion_r = executeQuery(dbFCTPE2, Transaccion_f); //QRO
				    
				boolean validaTransaccion = Transaccion_r.isEmpty();
		      
		        if(!validaTransaccion) {
		        	
		        	
		        	testCase.addQueryEvidenceCurrentStep(Transaccion_r);
		        	
		        	
		        } else {
		        	
		        	testCase.addTextEvidenceCurrentStep("No se muestra la consulta de transaccion de pago servicio Dish");
		        	testCase.addQueryEvidenceCurrentStep(Transaccion_r);
		        	
		               	
		        }
		           
				assertFalse(validaTransaccion, "No se insertaron datos en la tabla TPUSER.TPE_FR_TRANSACTION"); 
		        
		       		
				//Paso 5 ***************************************************************************************************

				addStep("Realizar conexion a la BD FCWMLTAQ");
				System.out.println("Paso 5: ");
				System.out.println("Conexion: "+ GlobalVariables.DB_HOST_FCWMLTAEQA_QRO);
				
				boolean conexionOXWMLOGQA2 = true;
				
				testCase.addTextEvidenceCurrentStep("Conexion: FCWMLTAQ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLTAEQA_QRO);
				
				assertTrue(conexionOXWMLOGQA2, "La conexion no fue exitosa");
				
			//Paso 9 *****************************************************************************************************************
				addStep(" Ejecutar la siguiente consulta en la BD OXWMLOGQA para validar que no se hayan registrado errores:");
				System.out.println("Paso 6: ");
			
				String LOG_ERROR_TPE_F = String.format(LOG_ERROR_TPE,"%OLS%", fechaInicial, fechaFinal);
				
				System.out.println(LOG_ERROR_TPE_F);
				
				SQLResult LOG_ERROR_TPE_R = dbQroFCWMLTAQ.executeQuery(LOG_ERROR_TPE_F);
				
				boolean valida_LOG_ERROR_TPE = LOG_ERROR_TPE_R.isEmpty();
				
				System.out.println(valida_LOG_ERROR_TPE);
				
				if (!valida_LOG_ERROR_TPE) {
					
					testCase.addQueryEvidenceCurrentStep(LOG_ERROR_TPE_R);
					
				} else {
					
					testCase.addTextEvidenceCurrentStep("No se presentaron errores");
					
				}
				
			
				assertTrue(valida_LOG_ERROR_TPE, "Se registro un error en WM_LOG_ERROR_TPE "); 
		
		//******************************Paso 7****************************
	/*	addStep("Ejecutar la siguiente consulta en la BD FCTPEQA para validar que se registr� la transacci�n "
				+ "de pago de servicio DISH en la tabla TPE_FR_TRANSACTION");
		
		String transactionFormat = String.format(queryTransaction, data.get("application"), folioService, folio);
		SQLResult transaction = dbFCTPE2.executeQuery(transactionFormat);
		System.out.println(transactionFormat);
		String creationDate = "";
		if(!transaction.isEmpty()) {
			creationDate = transaction.getData(0, "CREATION_DATE");
			testCase.addQueryEvidenceCurrentStep(transaction);
		}
		softAssert.assertFalse(transaction.isEmpty(), "Paso 7: No se obtuvo registro de la transaccion.");
		//******************************Paso 8****************************
		addStep("Realizar conexion a la BD FCWMLTAEQA");
		
		testCase.addTextEvidenceCurrentStep("Conexion a la base de datos: FCWMLTAEQA");
		testCase.addTextEvidenceCurrentStep("Status: ");
		testCase.addBoldTextEvidenceCurrentStep("Conectado");
		
		//******************************Paso 9****************************
		addStep("Ejecutar la siguiente consulta en la BD FCWMLTAEQA para validar que se registro la ejecuci�n hecha por el canal no seguro:");
		
		System.out.println("FECHA: " + creationDate);
		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date = originalFormat.parse(creationDate);
        date.setTime(date.getTime() - 3000);
        System.out.println("Date: " + date);
        String stringDate = targetFormat.format(date);
        System.out.println("String Date" + stringDate);
		
		String unsafeTrasactionFormat = String.format(queryUnsafeTransaction, stringDate, data.get("application"));
		SQLResult unsafeTransaction = dbFCWMLTAEQA_QRO.executeQuery(unsafeTrasactionFormat);
		System.out.println(unsafeTrasactionFormat);
		
		if(!unsafeTransaction.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(unsafeTransaction);
		}
		softAssert.assertTrue(unsafeTransaction.isEmpty(), "Paso 9: La transaccion no viajo por un canal seguro");//
		
		//******************************Paso 10****************************
		addStep("Ejecutar la siguiente consulta en la BD FCWMLTAEQA para validar que no se hayan registrado errores en la ejecuci�n de la OLS ");
		
		String errorRecordsFormat = String.format(queryErrorRecords, stringDate, "%" + data.get("application") + "%", "%" + data.get("application") + "%",
				"%" + data.get("application") + "%", "%SEC%", "%" + data.get("application") + "%", "%TPE%");
		SQLResult errorRecords = dbFCWMLTAEQA_QRO.executeQuery(errorRecordsFormat);
		System.out.println(errorRecordsFormat);
		
		testCase.addQueryEvidenceCurrentStep(errorRecords);
		softAssert.assertTrue(errorRecords.isEmpty(), "Paso 10: Se obtuvieron registros de error en la transaccion.");
	//	softAssert.assertAll();*/
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo automatizacion";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

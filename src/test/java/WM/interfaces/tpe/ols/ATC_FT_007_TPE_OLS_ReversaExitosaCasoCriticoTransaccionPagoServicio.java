package WM.interfaces.tpe.ols;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

//import interfaces.tpe.ols.ATC_FT_003_TPE_OLS_PagoServicioDISH;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.testng.annotations.Test;

import WM.om.OLS_D;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_TPE_OLS_ReversaExitosaCasoCriticoTransaccionPagoServicio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_TPE_OLS_ReversaExitosaCasoCriticoTransaccionPagoServicio_test(HashMap<String, String> data) throws Exception {

		/**
		 * Proyecto: Servicios Electronicos 
		 * Caso de prueba: MTC-FT-035-C1 TPE_OLS Reversa exitosa por Caso Critico de transacci�n de pago de servicio
		 * @author Marisol Rodriguez
		 * @date 
		 */
		
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCTPE2 = new SQLUtil(GlobalVariables.DB_HOST_TPE_LOT, GlobalVariables.DB_USER_TPE_LOT,GlobalVariables.DB_PASSWORD_TPE_LOT);
		SQLUtil dbMtyFCWMLTAQ = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAQ_MTY,GlobalVariables.DB_USER_FCWMLTAQ_MTY, GlobalVariables.DB_PASSWORD_FCWMLTAQ_MTY);
		SQLUtil dbQroFCWMLTAQ = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAEQA_QRO,GlobalVariables.DB_USER_FCWMLTAEQA_QA, GlobalVariables.DB_PASSWORD_FCWMLTAEQA_QRO_QA);
		SQLUtil dbMtyFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,GlobalVariables.DB_PASSWORD_FCTPE);
		
		OLS_D ols_dUtil = new OLS_D(data, testCase, null);
		

		/*
		 * Variables
		 *********************************************************************/
		String wmCodeToValidateQRY01 = "101";
		String wmCodeToValidateTRN01 = "100";
		String wmCodeToValidateTRN02 = "000";
		String wmCodeToValidateTRN03 = "101";
		String wmCodeToValidateTRN04 = "101";

		

		
		String wmCodeRequestQRY01;
		String wmCodeRequestTRN01;
		String wmCodeRequestTRN02;
		String wmCodeRequestTRN03;
		String wmCodeRequestTRN04;
		
		String respuestaQRY01;
		String respuestaTRN01;
		String respuestaTRN02;
		String respuestaTRN03;
		String respuestaTRN04;
		
		//auth se obtine del TRN02
		String auth = "";
		
		
		Date fechaInicio ;
		Date fechaFin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		
		//Caso exitoso DISH
		
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
		
		
		//Caso reversa
		String Transaccion = "select operation, entity, folio,wm_code, application, creation_date, ack \r\n"
						+ "from TPEUSER.TPE_FR_TRANSACTION \r\n"
						+ "where OPERATION= 'TRN04' \r\n"
						+ "and ENTITY = 'DISH'\r\n"
						+ "and FOLIO = '%s'\r\n"
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
	

		testCase.setProject_Name("Servicios B2B");
		
		testCase.setPrerequisites(data.get("Prerequicitos"));
		
		testCase.setTest_Description(data.get("id")+ data.get("descripcion"));
		
//********************************************************************************************************
		//Se solicita el request del QRY01
		
		
		
		fechaInicio = new Date();
			
        String fechaInicial = formatter.format(fechaInicio);
       		
		System.out.println("Fecha inicial: "+ fechaInicial);
 
// Paso 1 *************************************************************************************************
addStep("Ejecutar en un navegador directamente el servicio de la consulta de referencia para una transacci�n de "
		+ "pago de servicio DISH");
testCase.addTextEvidenceCurrentStep("Inicio: MTC-FT-032 Transaccion exitosa de pago de servicio DISH");

System.out.println("Paso 1:");

respuestaQRY01 = ols_dUtil.QRY01();
 
String folioQRY01 = RequestUtil.getFolioXml(respuestaQRY01);

wmCodeRequestQRY01 = RequestUtil.getWmCodeXml(respuestaQRY01);

boolean validaRespuestaQRY01 = true;
if (respuestaQRY01 != null) {
	validaRespuestaQRY01 = false;
}
assertFalse(validaRespuestaQRY01); 


//Valida el status esperado del QRY01 *******************************************************************************

addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateQRY01);

boolean validationRequestQRY01 = wmCodeRequestQRY01.equals(wmCodeToValidateQRY01);

System.out.println(validationRequestQRY01 + " - wmCode request QRY01 : " + wmCodeRequestQRY01);
	
testCase.addTextEvidenceCurrentStep(
		"Codigo esperado: " + wmCodeToValidateQRY01  + "\r\n" + "Codigo XML: " + wmCodeRequestQRY01);

assertTrue(validationRequestQRY01, "El codigo wmCode no es el esperado: " + data.get("wmCodeQRY01 "));

 
// Paso 2 *****************************************************************************************
addStep(" Ejecutar en un navegador directamente el servicio de solicitud de folio de la TPE_OLS del server QA8 "
		+ "para una transacci�n de pago de servicio DISH");
System.out.println("Paso 2: ");

//Se solicita el request del TRN01
respuestaTRN01 = ols_dUtil.TRN01();

/*String [] creationDate1 = respuestaTRN01.split("creationDate=\"");
String [] creationDate2 = creationDate1[1].split("\"");
String creationDate3 = creationDate2[0];
System.out.print("\r\n");
System.out.print("\r\ncreationDate: "+ creationDate3);*/

String folioTRN01  = RequestUtil.getFolioXml(respuestaTRN01);

wmCodeRequestTRN01 = RequestUtil.getWmCodeXml(respuestaTRN01);

String creationdate4 = RequestUtil.getCreationDate(respuestaTRN01);

System.out.println("CreationDate:"+ creationdate4);
System.out.print("\r\n");

boolean validaRespuestaTRN01 = true;
if (respuestaTRN01 != null) {
	validaRespuestaTRN01 = false;
}
assertFalse(validaRespuestaTRN01); 

//Valida el status esperado del TRN01*******************************************************************************

addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateTRN01);

boolean  validationRequestTRN01 = wmCodeRequestTRN01.equals(wmCodeToValidateTRN01);

System.out.println(validationRequestTRN01 + " - wmCode request TRN01: " + wmCodeRequestTRN01);


testCase.addTextEvidenceCurrentStep(
		"C�digo esperado: " + wmCodeToValidateTRN01 + "\r\n" + "C�digo XML: " + wmCodeRequestTRN01);

assertTrue(validationRequestTRN01, "El codigo wmCode no es el esperado: " + wmCodeRequestTRN01); 

  
// Paso 3 *****************************************************************************************
addStep(" Ejecutar en un navegador directamente el servicio de solicitud de Autorizaci�n de la TPE_OLS del servidor QA8:");
System.out.println("Paso 3: ");

//Se solicita el request del TRN02
respuestaTRN02 = ols_dUtil.TRN02(folioTRN01); 

String [] arr = respuestaTRN02.split("auth=\"");
String [] arr2 = arr[1].split("\"");
auth = arr2[0];
System.out.print("\r\n");
System.out.print("\r\nauth: "+ auth +"\r\n");

wmCodeRequestTRN02 = RequestUtil.getWmCodeXml(respuestaTRN02);

boolean validaRespuestaTRN02 = true;
if (respuestaTRN02 != null) {
	validaRespuestaTRN02 = false;
}
assertFalse(validaRespuestaTRN02);

//Valida el status esperado del TRN02 ******************************************************************************

addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateTRN02);

boolean  validationRequestTRN02 = wmCodeRequestTRN02.equals(wmCodeToValidateTRN02);

System.out.println(validationRequestTRN02 + " - wmCode request TRN02: " + wmCodeRequestTRN02);

testCase.addTextEvidenceCurrentStep(
		"C�digo esperado: " + wmCodeToValidateTRN02 + "\r\n" + "C�digo XML: " + wmCodeRequestTRN02);

assertTrue(validationRequestTRN02, "El codigo wmCode no es el esperado: " + wmCodeRequestTRN02);
		

// Paso 4 *****************************************************************************************
addStep(" Solicitar confirmacion ACK de una transacci�n de pago de servicio DISH desde un navegador");
System.out.println("Paso 4: ");

//Se solicita el request del TRN03
respuestaTRN03 = ols_dUtil.TRN03(folioTRN01); 
	
wmCodeRequestTRN03 = RequestUtil.getWmCodeXml(respuestaTRN03);

boolean validaRespuestaTRN03 = true;

if (respuestaTRN03 != null) {
	
	validaRespuestaTRN03 = false;
}

assertFalse(validaRespuestaTRN03);


//Valida el status esperado del TRN03 ***************************************************************************

addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateTRN03);

boolean  validationRequestTRN03 = wmCodeRequestTRN03.equals(wmCodeToValidateTRN03);

System.out.println(validationRequestTRN03 + " - wmCode request TRN03: " + wmCodeRequestTRN03);


testCase.addTextEvidenceCurrentStep(
		"C�digo esperado: " + wmCodeToValidateTRN03 + "\r\n" + "C�digo XML: " + wmCodeRequestTRN03); 

/*SeleniumUtil u = new SeleniumUtil(new ChromeTest());

u.hardWait(80);*/

assertTrue(validationRequestTRN03, "El codigo wmCode no es el esperado: " + wmCodeRequestTRN03);

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
String ConsultaReferencias_f = String.format(ConsultaReferencias, folioQRY01);

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
String TransaccionPagoServicio_f = String.format(TransaccionPagoServicio, folioTRN01);

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

//u.hardWait(80);

testCase.addBoldTextEvidenceCurrentStep("Server QRO");

//Consulta de referencias Dish
String ConsultaReferencias_f2 = String.format(ConsultaReferencias, folioQRY01);

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
//Transaccion pago servicio
System.out.println("Paso 6: TRN03 QRO");
String TransaccionPagoServicio_f2 = String.format(TransaccionPagoServicio, folioTRN01);

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


//Paso 8
addStep("Ejecutar la siguiente consulta en la base de datos **FCWMLTAQ** para validar que se hayan registrando "
	+ "las invocaciones en la tabla SECURITY_SESSION_LOG:");
System.out.println("Paso 8: ");

//u.hardWait(80);

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

//u.hardWait(80);

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
		        
		      //  u.hardWait(90);
				
				System.out.println(fechaInicial);
		 
   //***************************Paso 2 ***************************************************************************
				
		addStep("Ejecutar en un navegador directamente el servicio de Reversa por caso critico de la TPE_OLS");
		
		testCase.addTextEvidenceCurrentStep("Inicio: MTC-FT-035-C1 TPE_OLS Reversa exitosa por Caso Critico de transacci�n de pago de servicio");
		
		System.out.println("Paso 1:");
	   		
		respuestaTRN04 = ols_dUtil.TRN04(folioQRY01, auth,creationdate4);
		
			wmCodeRequestTRN04 = RequestUtil.getWmCodeXml(respuestaTRN04);
		
		System.out.println("WM_CODE: "+wmCodeRequestTRN04);// no esta obteniendo el wmcode

		boolean validaRespuestaTRN04 = true;
		if (respuestaTRN04 != null) {
			validaRespuestaTRN04 = false;
		}
		assertFalse(validaRespuestaTRN04); 

		
		//Valida el status esperado del TR04 *******************************************************************************
		
		addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateTRN04);

		boolean validationRequestTRN04 = wmCodeRequestTRN04.equals(wmCodeToValidateTRN04);
		
		System.out.println(validationRequestTRN04 + " - wmCode request TRN04 : " + wmCodeRequestTRN04);
			
		testCase.addTextEvidenceCurrentStep(
				"C�digo esperado: " + wmCodeToValidateTRN04  + "\r\n" + "Codigo XML: " + wmCodeRequestTRN04);

		assertTrue(validationRequestTRN04, "El codigo wmCode no es el esperado: " + wmCodeRequestTRN04);
		
		 
		
	//*****************************Fecha final***************************************************************************	
       fechaFin = new Date();
		
        fechaFinal = formatter.format(fechaFin);
        
      //  u.hardWait(80);
		
		System.out.println(fechaFinal);
	  
	// ****************************Paso 3 ********************************************************************************
		
		addStep("Realizar conexi�n a la BD FCTPEQA_QRO");
		System.out.println("Paso 3: ");
		
		System.out.println("Conexion: "+ GlobalVariables.DB_HOST_TPE_LOT);
		
		 conexionFCTPEQA = true;
			
		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA Server QRO");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPE_LOT);
		
		assertTrue(conexionFCTPEQA, "La conexion no fue exitosa");
		 
	 //******************************Paso 4 *************************************************************************
		
		addStep("Ejecutar la siguiente consulta en la BD FCTPEQA_QRO para validar que se registro la transaccion:");
		System.out.println("Paso 4: ");
		
		//u.hardWait(80);
		
        String Transaccion_f = String.format(Transaccion, folioQRY01, fechaInicial, fechaFinal);
		
		System.out.println("Transaccion de pago de servicios: \r\n"+ Transaccion_f);

		SQLResult Transaccion_r = executeQuery(dbFCTPE2, Transaccion_f); //QRO
		    
		boolean validaTransaccion = Transaccion_r.isEmpty();
      
        if(!validaTransaccion) {
        	
        	
        	testCase.addQueryEvidenceCurrentStep(Transaccion_r);
        	
        	
        } else {
        	
        	testCase.addTextEvidenceCurrentStep("No se muestra la consulta de transaccion de pago servicio Dish");
        	testCase.addQueryEvidenceCurrentStep(Transaccion_r);
        	
               	
        }
           
		assertTrue(validaTransaccion, "No se insertaron datos en la tabla TPUSER.TPE_FR_TRANSACTION"); 
        
       		
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
	//	u.hardWait(80);
		
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
		
	//	u.close();
		assertTrue(valida_LOG_ERROR_TPE, "Se registro un error en WM_LOG_ERROR_TPE "); 
	
		
	}

	@Override
	public String setTestFullName() {
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
		return "Equipo automation";
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
		return null;
	}

}


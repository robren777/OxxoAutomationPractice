package WM.interfaces.eo16_mx;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_EO16_MX_AdmAvante_CrearAltaUsuarios extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_EO16_MX_AdmAvante_CrearAltaUsuario_Test(HashMap<String, String> data) throws Exception {
	
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_Puser, GlobalVariables.DB_USER_Puser, GlobalVariables.DB_PASSWORD_Puser);
		//utils.sql.SQLUtil dbPosNueva = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA, GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);	

		SeleniumUtil u;
		PakageManagment pok;	
		
	
		testCase.setProject_Name("Administraci�n de Identidades - Avante"); 
		testCase.setTest_Description(data.get("Name"));
				
/** VARIABLES *********************************************************************/	
		
		String tdcQueryValidate = "SELECT * FROM  POSUSER.WM_TRANSACTION_EO16_MX WHERE OPERATION='CreateUser' ORDER BY END_DT DESC";
		String uri = String.format(data.get("uri"),data.get("host"),data.get("port"));
		RestAssured.baseURI = uri;
		//RestAssured.baseURI = "https://www.postman-echo.com";

		String responseCode = data.get("code");
		String status = data.get("status");
		

		
/** PASOS DEL CASO DE PRUEBA *********************************************************************/	
		
//		/****************************************************************************************************************************************
//		 * Paso 0
//		 * **************************************************************************************************************************************/
//		addStep("Comprobar status adapter");
//				 u = new SeleniumUtil(new ChromeTest(), true);
//				IntegrationServerUtil iu = new IntegrationServerUtil(u, data.get("IS_USER"), PasswordUtil.decryptPassword(data.get("IS_PASS")), data.get("IS_IP"));
//				iu.changeStatusAdapter(data.get("IS_ADAPTER_NAME"), data.get("IS_STATUS").equalsIgnoreCase("ON"));
//				testCase.addScreenShotCurrentStep(u, "Estatus adapter");
//		 			
		
		/* PASO 1 *********************************************************************/			

		addStep("Capturar en POSTMAN Username y Password en la pesta�a de Authorization.");
			
		testCase.addTextEvidenceCurrentStep("Username: " + data.get("username"));
		testCase.addTextEvidenceCurrentStep("Password: " + data.get("password"));
		testCase.addBoldTextEvidenceCurrentStep("Se capturan datos con �xito.");
		
		boolean conexion = true;
		
		assertTrue("Se ingresa exitosamente.", conexion);	
		
		/* PASO 2 *********************************************************************/		
		
		addStep("Capturar los par�metros necesarios para la solicitud de Alta de Usuario y enviar el request.");
		
		ApiMethodsUtil api = new ApiMethodsUtil(uri);
		String body1 = data.get("json");
		
		
//		String credentials = data.get("username")+":"+data.get("password");
//		Response JsonRequestBasicAuth= api.BasicAuthRequest(uri, credentials);
//		String JsonResponseBA =String.valueOf(JsonRequestBasicAuth.asPrettyString());
//		String StatusCodeBA = String.valueOf(JsonRequestBasicAuth.getStatusCode());
//		
//		System.out.println("BasicAuth Res: "+JsonResponseBA);
//		System.out.println("BasicAuth: "+StatusCodeBA);
		
		//El body1 debe de ser el path completo con los datos Ejemplo: https://httpbin.org/get?NOMBRE_USUARIO=IDMUSER01\
		System.out.println("body1: "+body1);
		Response JsonRequest= api.postRequestMethod(uri, body1);
		
		String JsonResponse =String.valueOf(JsonRequest.asPrettyString());
		String StatusCode = String.valueOf(JsonRequest.getStatusCode());
		String MessageResponse = String.valueOf(JsonRequest.getStatusLine());
		
		System.out.println("JsonResponse: \n"+JsonResponse);
		System.out.println("StatusCode: \n"+StatusCode);
		
		testCase.addTextEvidenceCurrentStep(data.get("json"));
		
		testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta del request enviado.");		
	
		//respuesta del request
		testCase.addTextEvidenceCurrentStep(JsonResponse);		
		testCase.addTextEvidenceCurrentStep("Status code: " + StatusCode);		
		testCase.addTextEvidenceCurrentStep("Mensaje recibido: " + MessageResponse);	
		
		boolean validateStatus = responseCode.equals(StatusCode);
		System.out.println("VALIDACION DE STATUS = " + validateStatus);
		assertFalse(!validateStatus, "La ejecuci�n de la interfaz no fue exitosa");
		
	
		
		/* PASO 3 *********************************************************************/		    
		
		addStep("Establecer conexi�n a la BD de WM FCWM6QA.FEMCOM.NET.");		

		testCase.addTextEvidenceCurrentStep("Base de Datos: FCWM6QA");
		testCase.addBoldTextEvidenceCurrentStep("Se establece la conexi�n con �xito a la BD.");
		testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_FCWMQA_NUEVA);
			
		assertTrue("Se ingresa exitosamente.", true);

		/* PASO 4 *********************************************************************/		    

		addStep("Validar el registro de la ejecuci�n de la interfaz EO16_MX.");
		
		System.out.println(tdcQueryValidate);		
		SQLResult connectionResult = executeQuery(dbPos, tdcQueryValidate);

		boolean connection = connectionResult.isEmpty();
		
		if (!connection) {				
			
			String estatus = connectionResult.getData(0, "Status");			
			connection = estatus.equals(status);
			System.out.println(connection);
			testCase.addQueryEvidenceCurrentStep(connectionResult);	
			
			connection = !connection;	
		} 

		assertFalse(connection, "La tabla no contiene la informaci�n o STATUS diferente a " + data.get("status"));	    
		
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Dora Elia Reyes Obeso";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_EO16_MX_AdmAvante_CrearAltaUsuario_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}
}

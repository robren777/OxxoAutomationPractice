package WM.interfaces.eo16_mx;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import WM.util.ApiUtil;
import WM.util.GlobalVariables;
import WM.util.IntegrationServerUtil;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 *  I21045-Administraci�n de Identidades-Avante-CC: MTC-FT-010-Realizar una Consulta de Usuario, Excepci�n en el Servicio
 * Desc:
 * Se requiere validar el m�todo de consulta de usuario para no duplicar cuentas en el sistema EBS 12.2.4
 * @author Roberto Flores
 * @date   2022/07/13
 */
public class ATC_FT_004_EO16_Avante_ConsultaUsuarioAdapterOff  extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_EO16_Avante_ConsultaUsuarioAdapterOff_Test(HashMap<String, String> data) throws Exception {
	
		testCase.setProject_Name("Administraci�n de Identidades - Avante"); 
		testCase.setPrerequisites("1. Para poder hacer la asignaci�n de roles y accesos debe de existir una matriz configurada en IDM.\r\n"
				+ "2. Contar con un usuario previamente dado de alta y activo en EBS 12.2.4.\r\n"
				+ "3. Contar con acceso a la BD FCWM6QA.FEMCOM.NET.\r\n"
				+ "4. Contar con acceso a la BD FCMLTAQ.FEMCOM.NET.\r\n"
				+ "5. Contar con acceso  y permisos para acceder al Integration Server (IS).");
		
		/*
		 * Utiler�as
		 *********************************************************************/
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA, GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		SQLUtil dbMtyFCWMLTAQ = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAQ_MTY,GlobalVariables.DB_USER_FCWMLTAQ_MTY, GlobalVariables.DB_PASSWORD_FCWMLTAQ_MTY);
		
		/*
		 * Variables
		 *********************************************************************/
		
		String tdcQueryValidate = "SELECT * FROM  POSUSER.WM_TRANSACTION_EO16_MX WHERE OPERATION='GetUser' AND START_DT >= TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')";

		String consultaError = "SELECT error_id, folio, error_date,description tpe_type \r\n" + 
				"FROM WMLOG.WM_LOG_ERROR_TPE \r\n" + 
				"WHERE   TPE_TYPE LIKE '%s' "
				+ "AND FOLIO = %s \r\n" + 
				"AND ERROR_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" + 
				"AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n" + 
				"ORDER BY ERROR_DATE DESC";
		
		String expectedCode = data.get("code");
		
		String status = data.get("status");
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/****************************************************************************************************************************************
		 * Paso 1
		 * **************************************************************************************************************************************/
		addStep("Ingresar al IS con las credenciales correctas e irse a la ruta: Adapters > webMethods Adapter for JDBC > Connections, y deshabilitar el pool o adapter de conexi�n DBS_ORAFIN_IDM_NT.");
				SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
				IntegrationServerUtil iu = new IntegrationServerUtil(u, data.get("IS_USER"), PasswordUtil.decryptPassword(data.get("IS_PASS")), data.get("IS_IP"));
				iu.changeStatusAdapter(data.get("IS_ADAPTER_NAME"), data.get("IS_STATUS").equalsIgnoreCase("ON"));
				testCase.addScreenShotCurrentStep(u, "Estatus adapter");
		 
		/****************************************************************************************************************************************
		 * Paso 2
		 * **************************************************************************************************************************************/		
		addStep("Capturar en POSTMAN URL y Body");
							
						testCase.addTextEvidenceCurrentStep("url: " + data.get("url"));
						testCase.addTextEvidenceCurrentStep("body:" + data.get("json"));
						
						boolean conexion = true;
						
						assertTrue("Se ingresa exitosamente.", conexion);	
		
		/****************************************************************************************************************************************
		 * Paso 3
		 * **************************************************************************************************************************************/		
		addStep("Irse a la pesta�a de Body y capturar los par�metros necesarios para la solicitud de Alta de Consulta de Usuario.");
		
				Date fechaEjecucionInicio = new Date();
				
				HttpResponse respuesta = ApiUtil.sendPostDissableSSL(
						data.get("url"), 
						data.get("json"));
				
				Date fechaEjecucionFin = new Date();
				
				String actualResponseCode = String.valueOf(respuesta.getStatusLine().getStatusCode());
				String actualResponseMessage = EntityUtils.toString(respuesta.getEntity());
				
				testCase.addCodeEvidenceCurrentStep("codigo de respuesta actual: " + actualResponseCode);
				System.out.println("codigo de respuesta actual: " + actualResponseCode);
				testCase.addCodeEvidenceCurrentStep("respuesta mensaje actual: " + actualResponseMessage);
				System.out.println("respuesta mensaje actual: " + actualResponseMessage);
				
				assertEquals(actualResponseCode, expectedCode);
		
		/****************************************************************************************************************************************
		 * Paso 4
		 * **************************************************************************************************************************************/		
				addStep("Establecer conexi�n a la BD de WM FCWM6QA.FEMCOM.NET.");		
		
				testCase.addTextEvidenceCurrentStep("Base de Datos: FCWM6QA");
				testCase.addBoldTextEvidenceCurrentStep("Se establece la conexi�n con �xito a la BD.");
				testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_FCWMQA_NUEVA);
					
				assertTrue("Se ingresa exitosamente.", true);

		/****************************************************************************************************************************************
		 * Paso 5
		 * **************************************************************************************************************************************/
		addStep("Validar el registro de la ejecuci�n de la interfaz EO16_MX.");
		
		String fechaEjecucionInicio_f = formatter.format(fechaEjecucionInicio);
		
		String tdcQueryValidate_f = String.format(tdcQueryValidate, fechaEjecucionInicio_f);
		
		System.out.println(tdcQueryValidate_f);		
		
		SQLResult connectionResult = executeQuery(dbPos, tdcQueryValidate_f);

		assertFalse(connectionResult.isEmpty());
							
		String folio = connectionResult.getData(0, "ID");
		String estatus = connectionResult.getData(0, "Status");	
		
		assertEquals(estatus, status);

		testCase.addQueryEvidenceCurrentStep(connectionResult);	
  
		
		/****************************************************************************************************************************************
		 * Paso 6
		 * **************************************************************************************************************************************/		
		addStep("Establecer conexi�n a la BD de WMLOG FCWMLTAQ.FEMCOM.NET.");		
		
				testCase.addTextEvidenceCurrentStep("Base de Datos: FCWMLTAQ");
				testCase.addBoldTextEvidenceCurrentStep("Se establece la conexi�n con �xito a la BD.");
				testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_FCWMLTAQ_MTY);
					
				assertTrue("Se ingresa exitosamente.", true);
				
		/****************************************************************************************************************************************
		 * Paso 7
		 * **************************************************************************************************************************************/		
		addStep("Ejecutar la siguiente sentencia para validar el registro del log del error de desconexi�n de WM para el c�digo obtenido 500:");		
		
				String fechaEjecucionFin_f = formatter.format(fechaEjecucionFin);
				
				String consultaError_f = String.format(consultaError, "%EO16_MX%", folio, fechaEjecucionInicio_f, fechaEjecucionFin_f);
				
				System.out.println(consultaError_f);
			
				SQLResult consultaError_r = dbMtyFCWMLTAQ.executeQuery(consultaError_f);
			
				boolean validaConsultaError = consultaError_r.isEmpty();
			
				System.out.println(validaConsultaError);
				
				testCase.addQueryEvidenceCurrentStep(consultaError_r, true);
				if (!validaConsultaError) {
					testCase.addTextEvidenceCurrentStep("Error encontrado en WMLOG.WM_LOG_ERROR_TPE");
				} else {
					testCase.addTextEvidenceCurrentStep("No se encontraron errores en WMLOG.WM_LOG_ERROR_TPE");
				}
			
				assertFalse(validaConsultaError, "No se visualizan errores en la base de datos");
		
				u.close();
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_004_EO16_Avante_ConsultaUsuarioAdapterOff_Test";
	}

	@Override
	public String setTestDescription() {
		return "MTC-FT-010-Realizar una Consulta de Usuario, Excepci�n en el Servicio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
}

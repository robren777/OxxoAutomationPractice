package WM.interfaces.Ajustes_Remesas;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagementNewVersion;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_27_Validar_error_de_con_la_BD_TPE_adaptador_apagado_WU extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_27_Validar_error_de_con_la_BD_TPE_adaptador_apagado_WU_test(HashMap<String, String> data) throws Exception {
	
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbTPE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);		
		SeleniumUtil u;
		PakageManagementNewVersion pok;	
		
		testCase.setProject_Name("I23035 Ajustes Remesas");    
				
		/** VARIABLES *********************************************************************/	

		String user = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String server = data.get("server");

		String param_tpe = "SELECT ENTITY,CODE,VALUE1,DESCRIPTION FROM TPEUSER.TPE_FR_CONFIG  WHERE application = 'TSF' AND entity IN ('WU')";
		String umbralTransmisor = "SELECT * FROM TPEUSER.TPE_CONFIG_TRANSMISOR";
		String umbralEstado = "SELECT * FROM TPEUSER.TPE_CONFIG_REMESA_ESTADO";
		String validarFallo = "SELECT RET_REF_NO,ENTITY,PLAZA,TIENDA,WM_DESC,WM_CODE,CREATION_DATE FROM TPEUSER.TPE_FR_TRANSACTION WHERE RET_REF_NO = ('8463154924')AND WM_DESC LIKE ('%Fallo%') AND CREATION_DATE  >=TRUNC(SYSDATE) AND CREATION_DATE <TRUNC(SYSDATE)+1";
		
		// PASO 1				
			addStep("Validar existan parámetros de configuración en la tabla TPE_FR_CONFIG BD TPE para TN o MGI.");		
				
				System.out.println(GlobalVariables.DB_HOST_AVEBQA);

				SQLResult paso1 = executeQuery(dbTPE, param_tpe);		
				
				boolean av1 = paso1.isEmpty();
				
				if (!av1) {
					testCase.addQueryEvidenceCurrentStep(paso1);

				} 	
				assertFalse(av1, "No se obtiene informacion de la consulta en la tabla ");

		//PASO 2	
			addStep("Validar La bandera FEMSA_TPE_TSF_FLAG_FLOW_UMBRAL debe tener el valor true.");			
				u = new SeleniumUtil(new ChromeTest(), true);
				pok = new PakageManagementNewVersion(u, testCase);
				
				System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
				String contra = "http://" + server + ":5555";
				u.get(contra);
				pok.inicarSesion(user, password);
				pok.getBanderaUmbral();
				
		// PASO 3				
			addStep("Validar existan parámetros de configuración en la tabla TPE_FR_CONFIG BD TPE para TN o MGI.");		
					
				System.out.println(GlobalVariables.DB_HOST_AVEBQA);

				SQLResult paso3 = executeQuery(dbTPE, umbralTransmisor);		
					
				boolean av2 = paso3.isEmpty();
					
				if (!av2) {
					testCase.addQueryEvidenceCurrentStep(paso3);

				} 	
				assertFalse(av2, "No se obtiene informacion de la consulta en la tabla ");		
			
	
			// PASO 3				
				addStep("Validar existan parámetros de configuración en la tabla TPE_FR_CONFIG BD TPE para TN o MGI.");		
						
					System.out.println(GlobalVariables.DB_HOST_AVEBQA);

					SQLResult paso4 = executeQuery(dbTPE, umbralEstado);		
						
					boolean av3 = paso4.isEmpty();
						
					if (!av3) {
						testCase.addQueryEvidenceCurrentStep(paso4);

					} 	
					assertFalse(av3, "No se obtiene informacion de la consulta en la tabla ");		
	
		// PASO 4 y 5
					addStep("Ejecutar servicio TPE.TSF.Pub:request de la interface TSF y \"Validar el response regrese los parámetros configurables forma-pago,  wmCode=199\" ");
					String baseURL = "http://10.184.80.20:5555";
					ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
					
						//se obtienen los datos del dataprovider
						String getPath = data.get("getPath");
						int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
						
						//Se envía como evidencia el request a ejecutar
						testCase.addCodeEvidenceCurrentStep("request:" + baseURL + getPath);
						
						//Ejecutar el request*
						Response getRespuesta = api.getRequestMethod(getPath);
						
						//Obtener el status code
						int getStatusCode = getRespuesta.getStatusCode();
						testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
						assertEquals(getStatusCode, getExpectedCode); //Validación
						
						//Obtener body de respuesta
						String getBodyResponse = getRespuesta.asPrettyString();
						testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse);
					
						//Validación de json*
						assertTrue(getBodyResponse.contains(data.get("getExpectedJSON")));
				
				// PASO 6				
				addStep("Validar la consulta sea exitosa en la tabla tpe_fr_transaction BD TPE Y WM_LOG_ERROR_TPE en la base de datos WMLOG.");		
								
					System.out.println(GlobalVariables.DB_HOST_AVEBQA);

					SQLResult paso6 = executeQuery(dbTPE, validarFallo);		
								
					boolean av4 = paso6.isEmpty();
								
					if (!av4) {
						testCase.addQueryEvidenceCurrentStep(paso6);

					} 	
					assertFalse(av4, "No se obtiene informacion de la consulta en la tabla ");				
					
					
					
	}		

			@Override
			public void beforeTest() {
				
			}

			@Override
			public String setTestDescription() {
				return "Validar no se encuentre disponible el pool de conexión AdapterConnections:TPE_TSF_NT en el IS.";
			}

			@Override
			public String setTestDesigner() {
				return "Yair Lizarraga";
			}

			@Override
			public String setTestFullName() {
				return "ATC_FT_27_Validar_error_de_con_la_BD_TPE_adaptador_apagado_WU_test";
			}

			@Override
			public String setTestInstanceID() {
				return null;
			}

			@Override
			public String setPrerequisites() {
				return  "* Ejecutar servicio TPE.TSF.Pub:request de la interface TSF para apagar la interfaz.\r\n"
						+ "* Validar el response regrese mensaje de error con wmCode <> 101.\r\n"
						+ "* Validar mensaje de error en la tabla WM_LOG_ERROR_TPE BD WMLOG TPE.\r\n"
						+ "*Tener acceso a la BD TPEQA>\r\n"
						+ "*Contar con remesas disponibles"
						+ "Tener apagado el adaptador TPE_TFS_NT";
			}
		}

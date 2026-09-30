package Mobile.simpEtiqueteo;
import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;

public class TestScript extends BaseExecution{

		@Test(dataProvider = "data-provider")
		public void TestScript_test(HashMap<String, String> data) throws Exception {
			addStep("TEST");
			
//			String serverXposStore = data.get("serverXposStore");
//			String serverSatelites = data.get("serverSatelites");
//			String serverDS50 = data.get("serverDS50");
//			String serverSIV3 = data.get("serverSIV3");
//			String usernameSIV3 = data.get("usernameSIV3");
//			String passwordSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
//			String plaza = data.get("plaza");
//			String tienda = data.get("tienda");
//			//AppiumUtil u = new AppiumUtil("C:/XPOS/XPOS.exe", false);
//			SQLSimpEtiqueteo sqlSimpEtiq = new SQLSimpEtiqueteo(testCase, this);
//			
//			/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
//			DesiredCapabilities cap = new DesiredCapabilities();
//			
//			cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
//			cap.setCapability("deviceName", data.get("deviceName"));
//			cap.setCapability("platformVersion", data.get("platformVersion"));
//			cap.setCapability("platformName", data.get("platformName"));
//			cap.setCapability("appium:appPackage", data.get("appPackageP"));
//			cap.setCapability("appium:appActivity", data.get("appActivityP"));
//			cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
//			
//			String url = "http://127.0.0.1:4723/";
//
//
//			addStep("paso1");
//			//XPOSSTORE
//			sqlSimpEtiq.encenderImpresoraVirtual(serverXposStore);
//			//SATELITES
//			sqlSimpEtiq.sqlActualizaFechaPlanogramaMovil(serverSatelites);
//			//SIV3
//			//sqlSimpEtiq.sqlPlanogramaSIV3("7501035014848", plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
//			//CSV
//			//DS50
//			testCase.addTextEvidenceCurrentStep("evidencia1");

		}

		@Override
		public String setTestFullName() {
			return "TestScript";
		}


		@Override
		public String setTestDescription() {
			return "Ejecucion test";
		}

		

		@Override
		public String setTestDesigner() {
			return "Roberto Flores";
		}

	 

		@Override
		public String setTestInstanceID() {
			return null;
		}

	 

		@Override
		public String setPrerequisites() {
			return "Prerequisitos";
		}

	 

		@Override
		public void beforeTest() {
	// TODO Auto-generated method stub
		}

	}







package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentCargaArchivos;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar que el login se realice correctamente
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-001-Acceder al portal de Sistemas de Incentivos mediante Access Control
*
* PREREQUISITOS:
*   - En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema
*   - En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente
*   - Acceso al aplicativo LaborManagment (VPN, Puertos)
*   - Usar Microsoft Edge con la última versión
*
* INSUMOS:
*   - Credenciales válidas para acceso al sistema
*
* @author Alejandro Montalvo Sillas
* @date 17/Septiembre/2024
*/
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;Login;AccesoPortal;Credenciales;Edge",
		userStoryId = 153968
		)
public class ATC_FT_003_AccederPortalIncentivosAccessControl extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_AccederPortalIncentivosAccessControl_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String mensaje = data.get("MensajeLogin");
		// String filepath = data.get("File_Path");

		/**
		 * Utilerias
		 ******************************************************************************************/
		// utils.sql.SQLUtil dbFCTPEQA = new
		// utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW,
		// GlobalVariables.DB_USER_FCTPEQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTPEQA_QAVIEW);
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);
		LaborManagment la = new LaborManagment(u, testCase);
		LaborManagmentCargaArchivos lac = new LaborManagmentCargaArchivos(u, testCase); 

		/**
		 * Inicializamos el navegador
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);

		addStep("Ingresar al portal");
		assertTrue(la.loginLaborManagment(user, password), "No se realizo correctamente el login");

		addStep("Seleccionar portal de incentivos");
		assertTrue(la.clicPortalIncentivos(), "No se pudo accesar al portal incentivos");

	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "*   - En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema\r\n"
				+ "*   - En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente\r\n"
				+ "*   - Acceso al aplicativo LaborManagment (VPN, Puertos)\r\n"
				+ "*   - Usar Microsoft Edge con la última versión";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el login se realize de manera correcta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alejandro Montalvo Sillas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_AccederPortalIncentivosAccessControl_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

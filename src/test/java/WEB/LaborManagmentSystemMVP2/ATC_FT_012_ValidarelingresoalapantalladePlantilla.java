package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentPlantilla;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Validar el ingreso a la pantalla Plantilla del Portal de Incentivos
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-012-Validar el ingreso a la pantalla Plantilla
 * 
 * PREREQUISITOS:
 * 		- Contar con acceso a la VPN
 * 
 * 
 * @author Carlos Martínez
 * @date 22-09-25
 */

@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;AutomationMVP2;I24025_Labor_Management",
		userStoryId = 147309
		)
public class ATC_FT_012_ValidarelingresoalapantalladePlantilla extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_ValidarelingresoalapantalladePlantilla_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;


		/**
		 * Utilerias
		 ******************************************************************************************/
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);
		LaborManagment la = new LaborManagment(u, testCase);
		LaborManagmentPlantilla lap = new LaborManagmentPlantilla(u, testCase);

		/**
		 * Pasos de evidencia
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);
		
		addStep("Login LaborManagment");
		la.loginLaborManagment(user, password);
		
		addStep("Ingresar al portal");
		assertTrue(la.clicPortalIncentivos(), "No se encontro el Portal de Incentivos");

		addStep("Seleccionar del menú la opción Plantilla");
		assertTrue(lap.opcionmenuplantilla(), "No cargo la pantalla de Plantilla");

		addStep("Validar que el título de la pantalla sea 'Plantilla' y elementos clave estén visibles");
		assertTrue(lap.cargapantallaplantilla(), "No cargo la pantalla de Plantilla");
	}


	@AfterMethod
	public void afterMethod() {
		// u.getDriver().quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Contar con acceso a la VPN";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el acceso a la pantalla plantilla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Carlos Martínez Ortega";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_ValidarelingresoalapantalladePlantilla_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
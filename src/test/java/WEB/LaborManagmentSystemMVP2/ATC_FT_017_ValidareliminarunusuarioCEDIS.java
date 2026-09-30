package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentPlantilla;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: 
 * - Validar el usuario tenga la opción de eliminar usuario
 * - Validar que aparezca la ventana con las opciones Si o No para eliminar un Usuario
 * - Validar que al seleccionar No el usuario no se borre
 * - Validar que al seleccionar Si el usuario se elimine y aparezca su mensaje correspondiente 
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-100-Validar el eliminar un usuarios CEDIS
 * 
 * PREREQUISITOS:
 * 		- Contar con acceso a la VPN
 * 		- Haber ejecutado el ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS
 * 		- Agregar en la columna Nombre el nombre del usuario agregado en el caso ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS
 * 
 * @author Carlos Martínez
 * @date 24-09-25
 */
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;AutomationMVP2;I24025_Labor_Management",
		userStoryId = 147309
		)
public class ATC_FT_017_ValidareliminarunusuarioCEDIS extends BaseExecution {
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_017_ValidareliminarunusuarioCEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String URL = GlobalVariables.DB_HOST_BDXXMAI_QAVIEW;
		String USER = GlobalVariables.DB_USER__BDXXMAI_QAVIEW;
		String PASSWORD = GlobalVariables.DB_PASSWORD_BDXXMAI_QAVIEW;

		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String Nombre = data.get("Nombre");
		String query = ("SELECT nombre FROM xxmai_inc.xxmai_plantilla WHERE nombre = '" + Nombre + "'");

		/**
		 * Utilerias
		 ******************************************************************************************/
		/**
		 */
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);
		LaborManagment la = new LaborManagment(u, testCase);
		LaborManagmentPlantilla lap = new LaborManagmentPlantilla(u, testCase);

		/**
		 * Pasos de Evidencias
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);
		
		addStep("Login LaborManagment");
		la.loginLaborManagment(user, password);
		
		addStep("Ingresar al portal");
		assertTrue(la.clicPortalIncentivos(), "No se encontro el Portal de Incentivos");

		addStep("Seleccionar del menú la opción Plantilla de Usuarios Cedis");
		assertTrue(lap.opcionmenuplantilla(), "No cargo la pantalla de Plantilla");

		addStep("Validar que al dar clic en eliminar en el usuario cedis a eliminar, se muestre una ventana con el mensaje ¿Desea realmente eliminar el usuario? Con lo botones \"Si\", \"No\".");
		assertTrue(lap.seleccionareliminarusuario(Nombre), "La ventana se muestra de manera correcta");

		addStep("Validar que al dar clic en la opción \"No\", no se elimine el registro y se cierre la ventana");
		assertTrue(lap.seleccionarnoeneliminar(Nombre), "Se elimino el registro cuando se dio no");

		addStep("Validar que al seleccionar \"Si\" aparezca el mensaje \"El usuario se eliminó de manera correcta\" y el usuario ya no se muestre en la pantalla");
		assertTrue(lap.seleccionarsieliminar(Nombre), "El mensaje no aparecio y no se borro el registro");

		// Ejecución de Consulta
		LaborManagmentPlantilla.SQLResultEliminar resultado = LaborManagmentPlantilla.executeQuery(query, URL, USER, PASSWORD);
		System.out.println("Consulta: " + query);

		addStep("Validar que no se encuentre el usuario eliminado");
		if (resultado.isEmpty()) {
			testCase.addTextEvidenceCurrentStep(
					"No se encontró ningún registro para el usuario eliminado. Todo correcto.");
		} else if (!resultado.isEmpty()) {
			testCase.addTextEvidenceCurrentStep("El usuario aún existe en la BD. ERROR.");
			// testCase.addQueryEvidenceCurrentStep(resultado); ("No permite realizar la
			// captura de la pantalla");
			Assert.fail("El usuario aun existe en la BD");
		}

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
		return "- Contar con acceso a la VPN\r\n"
				+ "- Haber ejecutado el ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS\r\n"
				+ "- Agregar en la columna Nombre el nombre del usuario agregado en el caso ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar funcione el botón de cargar archivo en diferentes navegadores.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Carlos Martinez Ortega";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_017_ValidareliminarunusuarioCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
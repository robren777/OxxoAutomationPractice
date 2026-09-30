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
 * DESCRIPCION: 
 * - Validar la edición de un Usuario
 * - Validar se calculen de manera correcta las semanas
 * - Validar guarde los cambios a los campos activos
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-092-Validar la edición de un usuario CEDIS
 * 
 * PRE-REQUISITOS:
 * 		- Contar con acceso a la VPN
 * 		- Haber ejecutado el ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS
 * 		- Agregar en la columna Nombre el nombre del usuario agregado en el caso ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS
 * 		- Agregar en la columna Turno del Data Provider un turno del 1 al 3.
 * 		- Agregar en la columna Tipocolaborador del Data Provider un tipo de colaborador que exista.
 * 		- Agregar en la columna Tipoincentivo del Data Provider un tipo de incentivo que exista.
 * 		- Agregar en la columna Posicion del Data Provider una posicion que exista.
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
public class ATC_FT_015_ValidarlaediciondeunusuarioCEDISTest extends BaseExecution{
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_ValidarlaediciondeunusuarioCEDISTest_test(HashMap<String, String> data) throws Exception {
		
		
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String Nombre = data.get("Nombre");
		String Turno = data.get("Turno");
		String Tipocolaborador = data.get("Tipodecolaborador");
		String Tipoincentivo = data.get("Tipodeincentivo");
		String Posicion = data.get("Posicion");		
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
		 * Pasos de evidencia:
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);
		
		addStep("Login LaborManagment");
		la.loginLaborManagment(user, password);
		
	    addStep("Ingresar al portal");
		assertTrue(la.clicPortalIncentivos(),"No se encontro el Portal de Incentivos");
		
		addStep("Seleccionar del menú la opción Plantilla");
		assertTrue(lap.opcionmenuplantilla(),"No cargo la pantalla de Plantilla");
		
		addStep("Seleccionar el botón de Editar");
		assertTrue(lap.seleccionareditarusuario(Nombre),"No se encontro el usuario");
		
		addStep("Validar se muestren todo los campos");
		assertTrue(lap.validarCamposEditar(),"No se muestra la ventana emergente de alta plantilla");
		
		addStep("Editar los campos Turno, Tipo de colaborador, Tipo de incentivo, Posición y Posición clave, con información valida y validar que al dar clic en guardar se edite la información del registro de manera correcta y se muestre el mensaje \"La información del usuario se actualizo de manera correcta\"");
	    assertTrue(lap.validareditarunusuario(Turno, Tipocolaborador, Tipoincentivo, Posicion),"No permitio editar el usuario");
	}
		
	@AfterMethod
	public void afterMethod() {
	//u.getDriver().quit();
	
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
				+ "- Agregar en la columna Nombre el nombre del usuario agregado en el caso ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS\r\n"
				+ "- Agregar en la columna Turno del Data Provider un turno del 1 al 3.\r\n"
				+ "- Agregar en la columna Tipocolaborador del Data Provider un tipo de colaborador que exista.\r\n"
				+ "- Agregar en la columna Tipoincentivo del Data Provider un tipo de incentivo que exista.\r\n"
				+ "- Agregar en la columna Posicion del Data Provider una posicion que exista.";
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
		return "ATC_FT_015_ValidarlaediciondeunusuarioCEDISTest_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
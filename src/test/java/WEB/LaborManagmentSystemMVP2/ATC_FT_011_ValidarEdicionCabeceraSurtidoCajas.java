package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentTabuladores;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar funciones de los botones de la pantalla Tabuladores
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-050-Validar que permita editar la cabecera de surtido cajas
*
* PREREQUISITOS:
*   - En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema
*   - En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente
*   - Acceso al aplicativo LaborManagment (VPN, Puertos)
*   - Usar Microsoft Edge con la última versión
*
* INSUMOS:
*   - Datos de tabuladores configurados en el sistema
*
* @author Alejandro Montalvo Sillas
* @date 25/Septiembre/2025
*/
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;ModuloTabuladores;BotoneraUI;ValidacionBD;ActualizacionDeDatos;Edge",
		userStoryId = 146928
		)
public class ATC_FT_011_ValidarEdicionCabeceraSurtidoCajas extends BaseExecution {
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_ValidarEdicionCabeceraSurtidoCajas_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");
		
		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
        String nuevoHorasProd = data.get("HorasProd"); 
        String nuevoDiasSem = data.get("DiasSem");
        String nuevoParoOperativo = data.get("ParoOperativo");
        String nuevoProduccion = data.get("Produccion");
		String defaultHorasProd = data.get("DefaultHorasProd");
		String defaultDiasSem = data.get("DefaultDiasSem");
		String defaultParoOperativo = data.get("DefaultParoOperativo");
		String defaultProduccion = data.get("DefaultProduccion");
		String URL = GlobalVariables.DB_HOST_BDXXMAI_QAVIEW;
		String USER = GlobalVariables.DB_USER__BDXXMAI_QAVIEW;
		String PASSWORD = GlobalVariables.DB_PASSWORD_BDXXMAI_QAVIEW;

		
		/**
		 * Utilerias
		 ******************************************************************************************/
		//utils.sql.SQLUtil dbFCTPEQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW, GlobalVariables.DB_USER_FCTPEQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTPEQA_QAVIEW);
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);	
		LaborManagment la = new LaborManagment(u, testCase); 
		LaborManagmentTabuladores lat = new LaborManagmentTabuladores(u, testCase);
		

		/**
		 * Inicializamos el navegador
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);
				
		addStep("Ingresar al portal");		
		assertTrue(la.loginLaborManagment(user, password),"No se realizo correctamente el login");
				
		addStep("Seleccionar portal de incentivos");
		assertTrue(la.clicPortalIncentivos(),"No se pudo accesar al portal incentivos");
		
		addStep("Seleccionar del menú la opción Carga de Archivos");
		assertTrue(lat.clicTabuladores(),"Hubo algun problema al encontrar el menu tabuladores");
								
		addStep("Verificar funcionamiento correcto de Cabecera Surtido Cajas");
		assertTrue(lat.validarActualizacionCabeceraSurtidoCajas2(nuevoHorasProd, nuevoDiasSem, nuevoParoOperativo, nuevoProduccion), "Fallo en el flujo de la Interfaz de Usuario.");
		
		addStep("Comparar datos de portal vs BD");
		assertTrue(lat.validarTabuladoresPortalVsBD(URL,USER,PASSWORD),"Hubo algun problema al validar la informacion vs BD");
		
		addStep("Se regresan los valores por default para cuando se vuelva a realizar la prueba");
		lat.actualizacionCabeceraSurtidoCajasDefault(defaultHorasProd,defaultDiasSem,defaultParoOperativo,defaultProduccion);
		
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
		return "Validar que el modulo tabuladores abra correctamente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alejandro Montalvo Sillas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_011_ValidarEdicionCabeceraSurtidoCajas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

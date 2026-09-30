package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentReportes;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: 
 * - Se ingresa a la pantalla plantilla para validar exista la opción de Reporte Información General, ademas de revisar unas cuantas 
 * validaciones mas y descargar el archivo de manera correcta
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-116-Validar se pueda generar y descargar el reporte de Información General
 *
 * PREREQUISITOS: 
 * 		- Contar con acceso a la VPN
 * 		- Se debe tener información en el año y semana seleccionados para poder descargar el Reporte
 * 		- Agregar en la columna Reporte el tipo de reporte que descargaremos
 * 		- Agregar en la columna	CEDIS el CEDIS del que descargaremos la Información
 * 		- Agregar en la columna Año el Año del que descargaremos la información
 * 		- Agregar en la columna Semana la semana de la que descargaremos la información
 * 		- Agregar en la columna Nombreesperado el nombre y tipo de archivo a descargar
 * 		- Agregar en la columna rutaBase la ruta a la que se descargara el archivo
 * 		- Agregar en la columna carpetaDescargas la carpeta en donde se descargara el archivo
 *
 * @author Carlos Martínez
 * @date 25-09-25
 */
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;Reutilizacion;Botonera Limpiar;Generar Reporte;Validaciones",
		userStoryId = 145798
		)
public class ATC_FT_021_ValidarsepuedagenerarydescargarelreportedeReporteInformacionGeneral extends BaseExecution {
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ValidarsepuedagenerarydescargarelreportedeReporteInformacionGeneral_test(HashMap<String, String> data) throws Exception {
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
		String Reporte = data.get("Tipodereporte");
		String CEDIS = data.get("CEDIS");
		String Ano = data.get("Ano");
		String Semana = data.get("Semana");
		String Nombreesperado = data.get("Nombreesperado");
		String rutaBase = data.get("rutaBase");
		String carpetaDescargas = data.get("carpetaDescargas");
		
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
		LaborManagmentReportes lar = new LaborManagmentReportes(u, testCase);

		/**
		 * Inicializamos el navegador
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);
		
		addStep("Login LaborManagment");
		la.loginLaborManagment(user, password);
		
		addStep("Ingresar al portal");
		assertTrue(la.clicPortalIncentivos(),"No se encontro el Portal de Incentivos");
				
		addStep("Seleccionar la opción del menú Generar reporte");
		assertTrue(lar.opcionmenureporte(),"No cargo la pantalla de Generar reporte");
		
        addStep("Validar que al seleccionar el tipo de reporte Información General se habiliten los campos CEDIS, Año y semana.");
		assertTrue(lar.seleccionarreporte(Reporte),"No se habilitaron los campos");
		
		addStep("Validar que no permita seleccionar mas de 2 años hacia atrás del año actual en el campo año");
		assertTrue(lar.validarOpcionesAno(), "Permitio seleccionar mas de 2 años");
		
		addStep("Validar que al llenar los 3 campos CEDIS, AÑO y Semana, se habilite el botón Descargar ");
		assertTrue(lar.habilitarelbotongenerar(CEDIS, Semana, Ano), "No se habilito el botón Descargar");
		
		addStep("Validar que al dar clic en el botón limpiar se limpien todos los campos ");
		assertTrue(lar.borrarloscampos(),"Los campos no se limpiaron");
		
		addStep("Seleccionar nuevamente la información en los campos y dar clic en generar, validar que aparezca el mensaje El archivo se ha descargado de manera correcta");
		assertTrue(lar.generararchivo(Reporte, CEDIS, Ano, Semana),"No se genero el archivo");
		
		addStep("Validar el archivo descargado sea de formato .xlsx");
		assertTrue(lar.archivosxml(Nombreesperado, rutaBase, carpetaDescargas), "El archivo no es .xlsx");
		
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
				+ "- Se debe tener información en el año y semana seleccionados para poder descargar el Reporte\r\n"
				+ "- Agregar en la columna Reporte el tipo de reporte que descargaremos\r\n"
				+ "- Agregar en la columna	CEDIS el CEDIS del que descargaremos la Información\r\n"
				+ "- Agregar en la columna Año el Año del que descargaremos la información\r\n"
				+ "- Agregar en la columna Semana la semana de la que descargaremos la información\r\n"
				+ "- Agregar en la columna Nombreesperado el nombre y tipo de archivo a descargar\r\n"
				+ "- Agregar en la columna rutaBase la ruta a la que se descargara el archivo\r\n"
				+ "- Agregar en la columna carpetaDescargas la carpeta en donde se descargara el archivo";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar se genere y descargue el reporte de manera correcta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Carlos Martínez Ortega";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_021_ValidarsepuedagenerarydescargarelreportedeReporteInformaciónGeneral_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
//import LaborManagmentSystemMVP2.archivoscsv.*;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentCargaArchivos;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar que se puedan eliminar los archivos cargados y que el sistema no presente errores durante el proceso
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-028-Validar que al dar clic en el botón "Eliminar Archivo" se elimine el archivo 
*
* PREREQUISITOS:
*   - En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema
*   - En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente
*   - Acceso al aplicativo LaborManagment (VPN, Puertos)
*   - Usar Microsoft Edge con la última versión
*
* INSUMOS:
*   - Archivos CSV previamente cargados en el sistema
*
* @author Alejandro Montalvo Sillas
* @date 17/Septiembre/2024
*/
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;GestionArchivos;Eliminacion;Estabilidad;Edge",
		userStoryId = 153968
		)
public class ATC_FT_005_ValidarEliminarArchivosCargados extends BaseExecution {
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarEliminarArchivosCargados_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");
		
		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String mensaje = data.get("MensajeLogin");
		//String filepath = data.get("File_Path"); 
		LocalDate hoy = LocalDate.now();
		String fechaFormateada = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String rutaBase = System.getProperty("user.dir") + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/";
		String[] nombresBase = { "Errores_", "Incidencias_", "Productividad_" };
		String[] rutasCSV = new String[nombresBase.length];
		
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
		LaborManagmentCargaArchivos lac = new LaborManagmentCargaArchivos(u, testCase); 
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));

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
		assertTrue(lac.clicCargaArchivos(),"No se pudo encontrar el menu carga archivos");
		
		addStep("Validar si hay archivos cargados y los elimina");
		assertTrue(lac.existenElementosColumnaNombre(true),"No se pudo encontrar elementos a eliminar");	  
		
		addStep("Validar que al darle clic al botón \"Cargar Archivos\" se muestre el cuadro \"Abrir\" y te permita seleccionar archivos .CSV de la computadora.");
		for (int i = 0; i < nombresBase.length; i++) {
		    rutasCSV[i] = rutaBase + nombresBase[i] + fechaFormateada + ".csv";
		}
		assertTrue(lac.cargarArchivosCSVUnoPorUno(rutasCSV), "Hubo algún problema con los elementos a cargar");
				
		addStep("El archivo se debe cargar en pantalla de manera correcta");
		assertTrue(lac.existenArchivosCSVEsperados(),"Hubo algun problema al detectar los archivos csv en la tabla");
		

		
		
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();

	}

	@Override
	public void beforeTest() {
		
		LaborManagment la = new LaborManagment(u, testCase); 
		
		LocalDate hoy = LocalDate.now();
		String fechaFormateada = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String rutaBase = System.getProperty("user.dir");
		String rutaTemplate = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Errores_template.csv";
		String rutaDestino = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Errores_" + fechaFormateada + ".csv";			
		String rutaTemplate2 = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Incidencias_template.csv";
		String rutaDestino2 = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Incidencias_" + fechaFormateada + ".csv";		
		String rutaTemplate3 = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Productividad_template.csv";
		String rutaDestino3 = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Productividad_" + fechaFormateada + ".csv";		
		String semana = LaborManagment.obtenerSemanaActualEspecial();
		String fecha = hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " 00:00";
		
		
		assertTrue(la.prepararArchivoErrores(rutaTemplate, rutaDestino, semana, fecha),"Hubo algún error editando el archivo errores csv");
		assertTrue(la.prepararArchivoIncidencias(rutaTemplate2, rutaDestino2, semana),"Hubo algún error editando el archivo Incidencias csv");	
		assertTrue(la.prepararArchivoProductividad(rutaTemplate3, rutaDestino3, semana, fecha),"Hubo algún error editando el archivo Productividad csv");
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
		return "Validar la correcta eliminacion de archivos cargados";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alejandro Montalvo Sillas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarEliminarArchivosCargados_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

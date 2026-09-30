package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Carga_Masiva_del_Segmento_Tienda;
import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_346_Realizar_la_carga_con_mas_columnas_de_las_validas extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_346_Realizar_la_carga_con_mas_columnas_de_las_validas_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new ChromeTest(), true); 
		Login pa = new Login(u, testCase); 
		Carga_Masiva_del_Segmento_Tienda cm = new Carga_Masiva_del_Segmento_Tienda(u, testCase);
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		Boolean validacion = false;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/

		addStep("Ingresar a la " + URL + "del portal de aplicaciones");
		
		u.get(data.get("Url")); 
		u.hardWait(3); 
		testCase.addScreenShotCurrentStep(u, "pantallaLogin");

		/**
		 *                                             PASO 2
		 ******************************************************************************************/

		addStep("Aparece la pantalla de Loggin");

		testCase.addScreenShotCurrentStep(u, "pantallaLogin");

		/**
		 *                                             PASO 3
		 ******************************************************************************************/

		addStep("Ingresar el usuario " + user + " y contrase�a " + password + " para despu�s seleccionar "
				+ "el bot�n de Continuar"); 

		pa.logginInventarioEspacios(user, password);

		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Se abre la aplicaci�n donde se muestra la pantalla de Tiendas-Femsa con la el Combo "
				+ "de Plaza y el bot�n de Continuar");
		
		testCase.addScreenShotCurrentStep(u, "pantallaTiendasFemsa");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		
		String window = pa.getScreen();
		pa.clickSeleccionarPlaza(PlazaE);
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El sistema desplegara las opciones de Distrito y Tiendas que est�n relacionadas con "
				+ "la Plaza seleccionada por el usuario."); 
		
		testCase.addScreenShotCurrentStep(u, "pantallaDistritoTiendas");

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n \"Carga Masiva del Segmento Tienda\".");
		
		pa.clickCargaMasivaSegmentoTienda();
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
	
		addStep("Sistema muestra la pantalla de \"Carga Masiva del Segmento Tienda\" con el campo de "
				+ "Archivo y los botones de \"Browse...\" y \"Cargar\"");
		
		validacion = cm.pantallaCargaMasivaSegTienda();
		assertTrue(validacion, "No se muestra la pantalla Carga Masiva del Segmento Tienda");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Usuario selecciona el bot�n de Browse para realiza la busqueda del archivo que se "
				+ "cargara de manera masiva.");
		
		cm.cargarArchivo("Prueba de Carga - 346.csv");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Usuario selecciona un archivo que cuente con un formato valido de .csv pero con m�s "
				+ "columnas al valido y lo carga.");
		
		testCase.addScreenShotCurrentStep("carga");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Sistema confirma que el archivo que se cargara cuenta con el formato de \".csv\" pero "
				+ "valida que el orden de columnas no es el siguiente:\r\n" + 
				"	-CR Plaza\r\n" + 
				"	-CR Tienda\r\n" + 
				"	-Segmento Maestro\r\n" + 
				"	-Potencial Hogar\r\n" + 
				"	-Potencial Comida\r\n" + 
				"	-Potencial Desayuno\r\n" + 
				"	-Overlay Hogar\r\n" + 
				"	-Overlay Comida\r\n" + 
				"	-Overlay Desayuno");
		
		validacion = cm.alerta();
		assertTrue(validacion, "No se puede realizar la carga");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("El sistema mostrara el mensaje de �Favor de validar los nombres de las columnas, "
				+ "as� como el orden.�.");
		
		validacion = cm.validarMensaje("La cantidad de columnas del registro no es la correcta, deben de ser 10 columnas.");
		assertTrue(validacion, "El mensaje mostrado no coincide");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("Usuario acepta le mensaje que se muestra y confirma que no se haya realizado carga alguna.");
		
		cm.clickRegresar(window);
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		fe.FormatoyEntorno();
		cm.changeWindowFormatoyEntorno();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_346_Realizar_la_carga_con_mas_columnas_de_las_validas";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_346_Realizar_la_carga_con_mas_columnas_de_las_validas_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}

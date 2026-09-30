package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Carga_Masiva_del_Segmento_Tienda;
import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_352_Cargar_Archivo_con_informacion_de_CR_Tienda_con_estatus_Notificada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_352_Cargar_Archivo_con_informacion_de_CR_Tienda_con_estatus_Notificada_test(
			HashMap<String, String> data) throws Exception {
		
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
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
		String query;
		
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
	
		addStep(" Sistema muestra la pantalla de \"Carga Masiva del Segmento Tienda\" con el campo de "
				+ "Archivo y los botones de \"Browse...\" y \"Cargar\"");
		
		validacion = cm.pantallaCargaMasivaSegTienda();
		assertTrue(validacion, "No se muestra la pantalla Carga Masiva del Segmento Tienda");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Usuario selecciona el bot�n de Browse para realiza la busqueda del archivo que se cargara "
				+ "de manera masiva.");
		
		cm.cargarArchivo("Prueba de Carga - 352.csv");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Usuario selecciona un archivo que cuente con un formato valido de .csv el cual cuenta "
				+ "con al menos un registro con un CR con estatus \"Nueva\" y lo carga.");
		
		validacion = cm.alerta();
		assertTrue(validacion, "No se puede realizar la carga");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep(" Sistema confirma que el archivo que se cargara cuenta con el formato de \".csv\" "
				+ "pero valida que al menos un registro cuenta con un CR con estatus \"Nueva\". Se "
				+ "mostrara el mensaje de �La informaci�n no puede ser actualizada, ya que algunas "
				+ "tiendas se encuentran en estatus diferente de Operativa, Reprogramada, Cancelada, "
				+ "Cerrada. Favor de validar la informaci�n.�.");
		
		u.waitForLoadPage();
		
		String cadena [] = data.get("PlazaE").split(" ");
		String CR_SUPERIOR = cadena[0];
		cadena = data.get("Tienda").split(" ");
		String CR_TIENDA = cadena[0];
		
		query = "SELECT ID_TIENDA, NOMBRE, CR_SUPERIOR, CR_TIENDA, STATUS "
				+ "FROM t_tiendas "
				+ "WHERE CR_SUPERIOR = '"  + CR_SUPERIOR + "' AND CR_TIENDA = '" + CR_TIENDA + "'";
		
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("El registro cuenta con el CR con estatus \"Notificada\"");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro");
		}
		
		validacion = cm.validarMensajeCorrecto("La informaci�n no puede ser actualizada, ya que algunas tiendas se encuentran en estatus diferente de Operativa, Reprogramada, Cancelada, Cerrada. Favor de validar la informaci�n.");
		assertTrue(validacion, "No se muestra correctamente el mensaje");
		
		/**
		 *                                             PASO 12
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
		return "ATC_FT_352_Cargar_Archivo_con_informacion_de_CR_Tienda_con_estatus_Notificada";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_352_Cargar_Archivo_con_informacion_de_CR_Tienda_con_estatus_Notificada_test";
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


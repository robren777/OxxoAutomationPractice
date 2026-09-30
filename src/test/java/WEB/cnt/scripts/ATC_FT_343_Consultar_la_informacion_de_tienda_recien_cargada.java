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

public class ATC_FT_343_Consultar_la_informacion_de_tienda_recien_cargada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_343_Consultar_la_informacion_de_tienda_recien_cargada_test(
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
		String query = "";
		
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
				+ "de manera masiva, seleccionado el archivo y lo carga.");
		
		cm.cargarArchivo("Prueba de Carga - 343.csv");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Sistema confirma que el archivo que se cargara cuente con el formato de \".csv\" y que contenga las siguientes columnas:\r\n" + 
				"	-CR Plaza\r\n" + 
				"	-CR Tienda\r\n" + 
				"	-Segmento Maestro\r\n" + 
				"	-Potencial Hogar\r\n" + 
				"	-Potencial Comida\r\n" + 
				"	-Potencial Desayuno\r\n" + 
				"	-Overlay Hogar\r\n" + 
				"	-Overlay Comida\r\n" + 
				"	-Overlay Desayuno\r\n" + 
				"    Al validar que el archivo cumple con el formato y columnas carga de manera satisfactoria.");
		
		validacion = cm.alerta();
		assertTrue(validacion, "No se puede realizar la carga");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("El sistema mostrara el mensaje de �La informaci�n fue actualizada correctamente. "
				+ "Total de registros actualizados: �N� � Donde N corresponde a la cantidad de registros que se cargaron.");
		
		u.waitForLoadPage();
		validacion = cm.validarMensajeCorrecto("La informaci�n fue actualizada correctamente. Total de registros actualizados:");
		assertTrue(validacion, "No se muestra correctamente el mensaje");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Usuario acepta le mensaje que se muestra y confirma que no se haya realizado carga alguna.");
		
		cm.clickRegresar(window);
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		fe.FormatoyEntorno();
		cm.changeWindowFormatoyEntorno();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("Se confirma que se haya mostrado la informaci�n de la tienda en base al archivo reci�n cargado.");
		
		String cadena [] = data.get("PlazaE").split(" ");
		String CR_SUPERIOR = cadena[0];
		cadena = data.get("Tienda").split(" ");
		String CR_TIENDA = cadena[0];
		String concepto_diferenciador = "";
		String segmento = "";
		String pHog = "";
		String pCom = "";
		String pDes = "";
		String oHog = "";
		String oCom = "";
		String oDes = "";
		
		
		query = "SELECT ID_TIENDA, NOMBRE, CR_SUPERIOR, CR_TIENDA, STATUS "
				+ "FROM t_tiendas "
				+ "WHERE CR_SUPERIOR = '"  + CR_SUPERIOR + "' AND CR_TIENDA = '" + CR_TIENDA + "'";
		
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (!StatusQuery) {
			String id_tienda = ExecuteQuery.getData(0, "ID_TIENDA");
			query = "SELECT * FROM TIENDAS.T_FORMATO_SEGMENTO WHERE ID_TIENDA = '" + id_tienda + "'";
			
			ExecuteQuery = executeQuery(dbFCIAS, query);
			StatusQuery = ExecuteQuery.isEmpty();
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			
			if (!StatusQuery) {
				try {
					concepto_diferenciador = ExecuteQuery.getData(0, "CONCEPTO_DIFERENCIADOR");
					segmento = ExecuteQuery.getData(0, "MAESTRO_SEGMENTO_DESC");
				}catch(Exception e) {
					System.out.print(e);
				}
				
			}
			
			query = "SELECT * FROM TIENDAS.T_FORMATO_SEGMENTO_DET WHERE ID_TIENDA = '" + id_tienda + "'";
			
			ExecuteQuery = executeQuery(dbFCIAS, query);
			StatusQuery = ExecuteQuery.isEmpty();
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			
			if (!StatusQuery) {
				try {
					pHog = ExecuteQuery.getData(0, "POTENCIAL_DESC");
					pCom = ExecuteQuery.getData(1, "POTENCIAL_DESC");
					pDes = ExecuteQuery.getData(2, "POTENCIAL_DESC");
					oHog = ExecuteQuery.getData(0, "NIVEL_OVERLAY_DESC");
					oCom = ExecuteQuery.getData(1, "NIVEL_OVERLAY_DESC");
					oDes = ExecuteQuery.getData(2, "NIVEL_OVERLAY_DESC");
				}catch(Exception e) {
					System.out.print(e);
				}
			}
			
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro");
		}
		
		validacion = cm.compararInformacion(concepto_diferenciador, segmento, pHog, pCom, pDes, oHog, oCom, oDes);
		assertTrue(validacion, "Algun(os) dato(s) del aplicativo no coincide(n) con Base de Datos");
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_343_Consultar_la_informacion_de_tienda_recien_cargada";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_343_Consultar_la_informacion_de_tienda_recien_cargada_test";
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

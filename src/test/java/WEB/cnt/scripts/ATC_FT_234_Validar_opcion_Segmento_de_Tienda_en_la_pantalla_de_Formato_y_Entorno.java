package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_234_Validar_opcion_Segmento_de_Tienda_en_la_pantalla_de_Formato_y_Entorno extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_234_Validar_opcion_Segmento_de_Tienda_en_la_pantalla_de_Formato_y_Entorno_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String diferenciador= data.get("Diferenciador");
		String msj = data.get("Alerta");


		// ------------------------------------PASO 1

		addStep("Ingresar a la '" + URL ); 
		
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep("Paso 1");
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
	

		// ------------------------------------PASO 2
		
		addStep("Ingresar el usuario "  + user + " y contrase�a para despu�s seleccionar el bot�n de Continuar");


		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");

//		//------------------------------------PASO 3
		
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
//		//------------------------------------PASO 4
		
		addStep("El usuario selecciona selecciona una tienda con estatus Nueva y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");

//		//------------------------------------PASO 5
	
		// PASO 7
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fe =  new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		fe.Framedatos();
		boolean validarFE = fe.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");

//		//      						   PASO 8
		addStep("Usuario selecciona un Concepto diferenciador que no sea Excepci�n y despu�s la opci�n de \"Segmento de Tienda\".");
		
		fe.Diferenciador(diferenciador);
		
		boolean validarBtn = fe.validarBtnAsignarActivo();
		assertTrue(validarBtn, "El sistema no habilita el boton Asignar");					
		fe.click_on_Asignar();
		
		boolean appearsmsg = fe.comprobarMensaje_onAlert(msj);
		assertTrue(appearsmsg, "No se encontro el mensaje");
		
		fe.aceptar_onAlert();
		u.hardWait(2);
		
		boolean validarPopUp = fe.validarPopUpSegmentoTienda();
		assertTrue(validarPopUp, "No se muestra el pop up Segmento de Tienda");
		
		boolean validarEPopUp = fe.validarElementosPopUpSegmentoTienda();	
		assertTrue(validarEPopUp, "No se muestran los campos del pop up Segmento de Tienda");
		
		if(validarEPopUp) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra un pop-up con el nombre de �Segmento de Tienda� el cual contar� con los campos de:\r\n" + 
					"-Segmento Maestro\r\n" + 
					"-Potencial Hogar\r\n" + 
					"-Potencial Comida\r\n" + 
					"-Potencial Desayuno\r\n" + 
					"-Overlay Hogar\r\n" + 
					"-Overlay Comida\r\n" + 
					"-Overlay Desayuno\r\n" );
		}
	


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setTestDescription() {
		return "Se valida que al seleccionar el bot�n de �Segmento de Tienda� se muestre el pop-up �Segmento de Tienda� con los siguientes campos:\r\n" + 
				"-Segmento Maestro\r\n" + 
				"-Potencial Hogar\r\n" + 
				"-Potencial Comida\r\n" + 
				"-Potencial Desayuno\r\n" + 
				"-Overlay Hogar\r\n" + 
				"-Overlay Comida\r\n" + 
				"-Overlay Desayuno\r\n" ;
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
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

package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_193_Modificar_Concepto_Diferenciador_DarkStore_Tienda_Operativa extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_193_Modificar_Concepto_Diferenciador_DarkStore_Tienda_Operativa_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String Plaza = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String diferenciador = data.get("Diferenciador");
		String bajo= "Desayuno_Bajo";
		String medio= "Desayuno_Medio";
		String alto= "Desayuno_Alto";

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a '" + URL +"' del portal de aplicaciones"); 
		
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		//u.getDriver().manage().window().maximize();
		testCase.addScreenShotCurrentStep("portal_CNT");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar el usuario "  + user + " y contrase�a para despu�s seleccionar el bot�n de Continuar");

		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		
		pa.clickSeleccionarPlaza(Plaza);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("El usuario selecciona una tienda con estatus \"Operativa\" correspondiente a Oxxo M�xico y da clic en el bot�n de Continuar.");
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		fe.Framedatos();
		boolean validarFE = fe.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		

		/*
		 *                                             PASO 6
		 ******************************************************************************************/	     			
		addStep("Usuario selecciona la opci�n de \"Dark Store\" en el combo de Concepto Diferenciador.");
		
		//fe.Framedatos();
		
		fe.Diferenciador(diferenciador);
		
		boolean validarValores = fe.ValidarCambioCampoSegmentoTiendaNA();
		
		if(validarValores) {
			testCase.addTextEvidenceCurrentStep("El Sistema cargara en Automatico el valor de NA en los campos de:" + 
					"	-Segmento Maestro" + 
					"	-Potencial_Hogar" + 
					"	-Potencial_Comida" + 
					"	-Potencial_Desayuno" + 
					"	-Overlay_Hogar" + 
					"	-Overlay_Comida" + 
					"	-Overlay_Desayuno");
		}else {
			Assert.fail("El sistena no cargo en automatico el valor NA en los campos");
		}
		
		testCase.addScreenShotCurrentStep(u, "CamposValorNA");
			
			

		/*
		 *                                             PASO 7
		 ******************************************************************************************/	     			
		addStep(" Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda");
		
	    testCase.addScreenShotCurrentStep(u, "ValoresPrecargados ");
		
		assertTrue(validarValores, "No se precargaron los valores");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Validar que al modificar el Concepto Diferenciador para una tienda con estatus �Operativa� cambiando de un Concepto Diferenciador que No es Excepci�n a �Dark Store�.\r\n" + 
				"Validando que en autom�tico se tengan las siguientes opciones y que no permita modificar dicha informaci�n:\r\n" + 
				"-Segmento Maestro: NA\r\n" + 
				"-Potencial_Hogar: NA\r\n" + 
				"-Potencial_Comida: NA\r\n" + 
				"-Potencial_Desayuno: NA\r\n" + 
				"-Overlay_Hogar: NA\r\n" + 
				"-Overlay_Comida: NA\r\n" + 
				"-Overlay_Desayuno: NA\r\n" ;
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

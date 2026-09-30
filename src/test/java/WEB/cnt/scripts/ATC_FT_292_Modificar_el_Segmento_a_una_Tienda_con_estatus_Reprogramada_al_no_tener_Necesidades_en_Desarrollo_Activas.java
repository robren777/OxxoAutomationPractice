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

public class ATC_FT_292_Modificar_el_Segmento_a_una_Tienda_con_estatus_Reprogramada_al_no_tener_Necesidades_en_Desarrollo_Activas extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_292_Modificar_el_Segmento_a_una_Tienda_con_estatus_Reprogramada_al_no_tener_Necesidades_en_Desarrollo_Activas_test(
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
		addStep("El usuario selecciona una tienda con estatus \"Reprogramada\" que corresponda a Oxxo "
				+ "M�xico y da clic en el bot�n de Continuar.");
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
	
		addStep("Sistema abre la pantalla con las opciones de Seleccionar Tienda, Datos Generales, "
				+ "Datos de Operaci�n, Formato y Entorno, Planogramaci�n, Condiciones en Propiedad y Sugerencias");
		
		testCase.addScreenShotCurrentStep(u, "pantalla");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		
		fe.FormatoyEntorno();
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes e informaci�n.");
		
		testCase.addScreenShotCurrentStep(u, "pantallaFE");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"NA\" del combo de \"Concepto Diferenciador\".");
		
		fe.selectDiferenciador("NA");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep(" El sistema habilitara el bot�n de \"Asignar\" y muestra los siguientes campos vac�os solicit�ndolos como requeridos \r\n" + 
				"    -Segmento Maestro\r\n" + 
				"	-Potencial_Hogar\r\n" + 
				"	-Potencial_Comida\r\n" + 
				"	-Potencial_Desayuno\r\n" + 
				"	-Overlay_Hogar\r\n" + 
				"	-Overlay_Comida\r\n" + 
				"	-Overlay_Desayuno");
		
		validacion = fe.validarActivacionCampos();
		assertTrue(validacion, "No se habilitaron los campos");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		
		fe.clickAsignarSegTienda();
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informaci�n, debe de guardar "
				+ "antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
		
		validacion = fe.confirmarMensajeAlerta();
		assertTrue(validacion, "El sistema no muestra el mensaje correctamente");
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		
		addStep("Usuario selecciona Aceptar");
		
		fe.confirmarAlerta();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_292_Modificar_el_Segmento_a_una_Tienda_con_estatus_Reprogramada_al_no_tener_Necesidades_en_Desarrollo_Activas";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_292_Modificar_el_Segmento_a_una_Tienda_con_estatus_Reprogramada_al_no_tener_Necesidades_en_Desarrollo_Activas_test";
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
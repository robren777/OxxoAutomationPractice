package WEB.MantenimientoTiendas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.MantenimientoTiendas.MantenimientoTiendas;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_ConsultarEncuesta extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ConsultarEncuesta_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/

		/*
		 * Variables
		 ******************************************************************************************/

		String user = data.get("user");
		String pass = data.get("password");

		String nombre_enc = data.get("nombre_encuesta");
		
		Boolean Encuesta_Activa = false;

		u = new SeleniumUtil(new MEdgeTest(), true);
		AccessControl inicio = new AccessControl(u, testCase);
		AuxMethods aux = new AuxMethods(u, testCase);
		MantenimientoTiendas mtto = new MantenimientoTiendas(u, testCase, aux);

		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites(data.get("prerequisites"));

		/**
		 *************************** PASO 1 **********************************
		 *********************************************************************
		 **/

		addStep("Ingresar a la liga 'https://fcowlq08.femcom.net/AccessControl/pages/login_form.jsf' ");

		/**
		 * Inicio del flujo
		 */
		aux.logP("1. Ingresar a Access Control");
		inicio.loginAccessControl(user, pass);
		testCase.addTextEvidenceCurrentStep("Se accedio correctamente al Portal de Access Control");

		assertTrue(mtto.IsExpansionActive());

		/**
		 *************************** PASO 2 **********************************
		 *********************************************************************
		 **/

		addStep("Dar clic en el portal de 'Checklist de Verificacion y de Recepcion'");

		aux.logS("2. Scroll hasta el apartado de 'Expansion'");
		aux.logP("3. Click en el apartado de 'Checklist de Verificacion y de Recepcion'");
		mtto.ClickChklistVerifRecept();

		assertTrue(mtto.IsChklstVerifYRecep());

		/**
		 *************************** PASO 3 **********************************
		 *********************************************************************
		 **/

		addStep("Colocar el mouse sobre la pesta�a de 'Funciones' ");

		aux.logP("4. Se selecciona el apartado de 'Funciones' ");
		mtto.SelectFunciones();
		aux.logS("Se hace click sobre la opcion 'Encuestas' ");
		mtto.ClickFEncuesta();

		testCase.addTextEvidenceCurrentStep("Se muestra pantalla definici�n de encuestas ");

		/**
		 *************************** PASO 4 **********************************
		 *********************************************************************
		 **/

		addStep("Dar click en el combobox del campo 'STATUS' ");

		mtto.clickStatus();

		testCase.addScreenShotCurrentStep(u, "ClickOnStatus");

		/**
		 *************************** PASO 5 **********************************
		 *********************************************************************
		 **/

		addStep("Selecciona la opcion 'A' y dar click en el boton 'Buscar' ");

		mtto.selectStatusA();

		testCase.addScreenShotCurrentStep(u, "OptionA");
		testCase.addTextEvidenceCurrentStep("Se muestran en la parte inferior las "
				+ "encuestas que estan activas en el portal. ");

		/**
		 *************************** PASO 6 **********************************
		 *********************************************************************
		 **/

		addStep("Validar que la encuesta creada previamente aparezca entre la lista ");

		Encuesta_Activa = mtto.EncuestaisActive(nombre_enc);

		assertTrue(Encuesta_Activa);
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Consultar si existen encuestas en la aplicaci�n para que sean programadas a un usuario.";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ConsultarEncuesta_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener acceso al porta de Checklist de Verificacion y de Recepcion";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}

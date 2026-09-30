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


public class ATC_FT_001_CreaciondeEncuesta extends BaseExecution {

	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CreaciondeEncuesta_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		

		/*
		 * Variables
		 ******************************************************************************************/
		
		
		String user = data.get("user");
		String pass = data.get("password");

		String id_enc = data.get("id_encuesta");
		String nombre_enc = data.get("nombre_encuesta");
		String version_enc = data.get("version_encuesta");
		String section_enc = data.get("seccion");
		String status_enc = data.get("status");
		
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
		
		addStep("Dar click sobre el boton 'Agregar' de la parte superior de la lista de encuestas ");
		
		mtto.ClickAgregar();
		
		testCase.addTextEvidenceCurrentStep("Se habilita un renglon en la parte inferior de la lista de encuestas");
		
		
		/**
		 *************************** PASO 5 **********************************
		 *********************************************************************
		 **/
		
		addStep("Se ingresan los datos en los campos correspondientes ");
		
		mtto.FillEncuesta(id_enc, nombre_enc, version_enc);
		
		
		/**
		 *************************** PASO 6 **********************************
		 *********************************************************************
		 **/
		
		addStep("Da clic sobre el apartado SECCIONES de la columna SECCIONES");
		
		mtto.ClickSecciones();
		
		
		/**
		 *************************** PASO 7 **********************************
		 *********************************************************************
		 **/
		
		addStep("Dar click en el boton 'Agregar' ");
		
		mtto.ClickAgregarSecccion();
		
		
		/**
		 *************************** PASO 8 **********************************
		 *********************************************************************
		 **/
		
		addStep("Ingresa los siguientes campos: Secci�n, Encuesta, Versi�n, Status ");
		
		mtto.FillNewSectionForm(nombre_enc, section_enc, version_enc, status_enc);
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Generar una encuesta con diversas preguntas para ser asignadas a usuarios en sus plazas correspondientes.";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CreaciondeEncuesta_test";
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



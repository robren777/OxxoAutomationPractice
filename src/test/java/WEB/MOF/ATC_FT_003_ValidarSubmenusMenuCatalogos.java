package WEB.MOF;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.MOF.MOF;
import modelo.BaseExecution;
import utils.selenium.MEdgeEspTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarSubmenusMenuCatalogos extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarSubmenusMenuCatalogos_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/

		/*
		 * Variables
		 ******************************************************************************************/

		String user = data.get("user");
		String pass = data.get("password");

		String plaza = data.get("plaza");
		String estado = data.get("estado");
		String ciudad = data.get("ciudad");
		String colonia = data.get("colonia");
		String calle = data.get("calle");
		String numero = data.get("numero");
		String telefono = data.get("telefono");
		
		u =  new SeleniumUtil(new MEdgeEspTest(),true);
		
		AccessControl inicio = new AccessControl(u, testCase);
		AuxMethods aux = new AuxMethods(u, testCase);
		MOF mof = new MOF(u, testCase, aux);

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

		assertTrue(mof.IsFinanzasAdministracionActive());

		/**
		 *************************** PASO 2 **********************************
		 *********************************************************************
		 **/

		addStep("Ingresar con usuario, contrasena valido y abrir la aplicacion 'Monitor de Obligaciones Fiscales' ");

		aux.logS("2. Scroll hasta el apartado de 'Finanzas y Administracion'");
		aux.logP("3. Click en el apartado de 'Monitor de Obligaciones Fiscales'");
		mof.clickPortalMonitorObligacionesFiscales(1);

		assertTrue(mof.IsMonitorObligacionesFiscalesActive());

		/**
		 *************************** PASO 3 **********************************
		 *********************************************************************
		 **/
		
		addStep("Del menu 'Catalogos' seleccionar la opcion 'Despachos' ");
		
		mof.SelectMOFCatalogos();
		mof.OpCatalogosDespachos();
		
		
		/**
		 *************************** PASO 4 **********************************
		 *********************************************************************
		 **/
		
		addStep("Editar los campos del despacho seleccionado y presionar boton Actualizar Despacho");
		
		mof.OpDespachoPlaza(plaza);
		mof.ClickOnDespachoTableResult();
		mof.OpDespachoEstado(estado);
		mof.OpDespachoCiudad(ciudad);
		u.hardWait(5);
		mof.OpDespachoColonia(colonia);
		mof.InputDespachoCalle(calle);
		mof.InputDespachoNumero(numero);
		mof.InputDespachoTelefono(telefono);
		mof.ClickBtnActualizarDespacho();
		u.hardWait(10);
		
		assertTrue(mof.IsActualizacionCompletada());
		
		/**
		 *************************** PASO 5 **********************************
		 *********************************************************************
		 **/
		
		addStep("Del menu 'Catalogos' seleccionar la opcion 'Deducciones' ");
		
		mof.SelectMOFCatalogos();
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se muestre informacion correcta en la pantalla de Despachos, Deducciones y Parametros";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarSubmenusMenuCatalogos_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Contar con la URL del aplicativo MOF. "
				+ "2.- Contar con Usuario y Contrasena validos. ";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}

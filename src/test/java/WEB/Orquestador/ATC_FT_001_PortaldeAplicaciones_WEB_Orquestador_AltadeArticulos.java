package WEB.Orquestador;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.Orquestador.Orquestador;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_001_PortaldeAplicaciones_WEB_Orquestador_AltadeArticulos extends BaseExecution {

	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PortaldeAplicaciones_WEB_Orquestador_AltadeArticulos_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		

		/*
		 * Variables
		 ******************************************************************************************/
		
		
		String user = data.get("user");
		String pass = data.get("password");
		boolean ProveedorModule = false;
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		AccessControl inicio = new AccessControl(u, testCase);
		AuxMethods aux = new AuxMethods(u, testCase);
		Orquestador orq = new Orquestador(u, testCase, aux);

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
		ProveedorModule = orq.IsProveedoresActive();
		
		assertTrue(ProveedorModule);
		
		
		/**
		 *************************** PASO 2 **********************************
		 *********************************************************************
		 **/
		
		addStep("Dar clic en el portal de Gestion de Proveedores");
		
		aux.logS("2. Scroll hasta el apartado de 'Gestion de Proveedores'");
		aux.logP("3. Click en el apartado de 'Gestion de Proveedores'");
		orq.ClickGestionProveedores();
		
		assertTrue(orq.IsGestionProveedores());
		
		
		/**
		 *************************** PASO 3 **********************************
		 *********************************************************************
		 **/
		
		addStep("El usuario selecciona la opcion 'Alta de articulos'");
		aux.logP("4. Se hace click en 'Alta Articulos' ");
		orq.clickAltaArticulos();
		aux.logS("5. Se hace click en 'Alta de Productos '");
		testCase.addTextEvidenceCurrentStep("Se muestra el contenido de la opcion 'Alta Articulos' >> 'Alta de Productos' ");
		aux.logS("6. Fin de la ejecucion");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Valida el menu principal de Orquestador asi como sus opciones de Menu";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_PortaldeAplicaciones_WEB_Orquestador_AltadeArticulos_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener acceso al porta de Gestion de Proveedores";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
	
}



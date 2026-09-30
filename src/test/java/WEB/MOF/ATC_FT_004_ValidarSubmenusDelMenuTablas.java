package WEB.MOF;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.MOF.MOF;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_ValidarSubmenusDelMenuTablas extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarSubmenusDelMenuTablas_test(HashMap<String, String> data) throws Exception {

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

		aux.logS("2. Scroll hasta el apartado de 'Finanzas'");
		aux.logP("3. Click en el apartado de 'Monitoreo de Obligaciones Fiscales'");
		mof.clickPortalMonitorObligacionesFiscales(1);

		assertTrue(mof.IsMonitorObligacionesFiscalesActive());

		/**
		 *************************** PASO 3 **********************************
		 *********************************************************************
		 **/
		
		addStep("Del menu 'Tablas' seleccionar la opcion 'Tarifas' ");
		
		mof.SelectMOFTablas();
		mof.OpTablasTarifas();
		
		
		/**
		 *************************** PASO 4 **********************************
		 *********************************************************************
		 **/
		
		addStep("Presionar el boton 'Nuevo' ");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se muestren correctamente los submenus del menu Tablas. ";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarSubmenusDelMenuTablas_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Contar con la URL del aplicativo MOF."
				+ " 2.- Contar con Usuario y Contrasena validos. ";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}

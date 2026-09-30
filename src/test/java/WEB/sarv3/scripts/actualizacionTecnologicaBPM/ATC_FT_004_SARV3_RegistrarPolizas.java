package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.MenuPrincipalSAR;
import WEB.sarv3.om.PolizaSegurosSARv3;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_SARV3_RegistrarPolizas extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_SARV3_RegistrarPolizas_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
		PolizaSegurosSARv3 ps = new PolizaSegurosSARv3(u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("Ingresar a la aplicaci�n con el usuario y contrase�a v�lidos");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Dar clic en Sistema de administraci�n de Riesgos V3");
		
		pa.clickSARv3();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Dar clic en Registrar P�liza y seleccionar Registro P�liza de Seguro.");
		
		mp.clickRegistrarPoliza();
		mp.clickRegPolizaSeguro();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Dar clic en Crear.");
		
		ps.clickCrearPoliza();
		
		/**
		 * 												PASO 6
		 ******************************************************************************************/
		
		addStep("Llenar la informaci�n solicitada y dar clic al bot�n Guardar.");
		
		validacion = ps.camposPoliza(data.get("Poliza"), data.get("Cobertura"), data.get("SMDF"), 
				data.get("Deducible"), data.get("Fecha")); 
		assertTrue(validacion, "No ingresaron los datos correctamente");
		
		ps.clickGuardarBtn();
		
		u.hardWait(5);
		
		/**
		 * 												PASO 7
		 ******************************************************************************************/
		
		addStep("Seleccionar la p�liza creada y dar clic al bot�n Ver Detalle.");
		
		validacion = ps.seleccionarPoliza(data.get("Poliza"));
		assertTrue(validacion, "No se encontro la poliza registrada");
		
		ps.clickVerDetalle();
		
		/**
		 * 												PASO 8
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Editar.");
		ps.clickEditar();
		
		/**
		 * 												PASO 9
		 ******************************************************************************************/
		
		addStep("Editar los campos deseados y dar clic al bot�n Guardar.");
		
		validacion = ps.editarCamposPoliza(data.get("Poliza2"), data.get("Cobertura2"), data.get("SMDF2"), 
				data.get("Deducible2"), data.get("Fecha2"));
		
		assertTrue(validacion, "No ingresaron los datos correctamente");
		
		ps.clickGuardarBtn();
		
		testCase.addScreenShotCurrentStep(u, "EditarPoliza");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Registrar Polizas";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_SARV3_RegistrarPolizas_Test";
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

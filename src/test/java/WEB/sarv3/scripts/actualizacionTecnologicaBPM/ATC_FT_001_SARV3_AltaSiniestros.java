package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.AltaSiniestrosSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_SARV3_AltaSiniestros extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_SARV3_AltaSiniestros_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
		AltaSiniestrosSARv3 as = new AltaSiniestrosSARv3(u, testCase);
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
		
		addStep("Dar clic en Alta de Siniestros y llenar la informaci�n solicitada.");
		
		mp.clickAltaSiniestros();
		
		u.waitForLoadPage();
		validacion = as.camposDatosGenerales(data.get("Negocio"), data.get("Plaza"), data.get("Establecimiento"),
				data.get("Siniestro"), data.get("Subtipo"), data.get("Relato"), data.get("NoAsaltantes"));
		assertTrue(validacion, "No se puede ingresar la informaci�n proporcionada");
		
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Dar clic en Grabar");

		as.clickGrabar();
		
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Dar clic en Enviar Notificaci�n");
		
		as.clickEnvNotificacion();
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Alta de Siniestros";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_SARV3_AltaSiniestros_Test";
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


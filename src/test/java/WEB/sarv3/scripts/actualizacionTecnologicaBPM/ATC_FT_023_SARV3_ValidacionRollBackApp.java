package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.Pega;
import WEB.om.Ultimus;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_023_SARV3_ValidacionRollBackApp extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_SARV3_AccessoDeUsuariosPortalPEGA_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String userPega = data.get("UserPega");
		String url = data.get("Url");
		String userUltimus = data.get("UserUltimus");
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Acceder a la url http://10.185.176.11:7011/prweb/PRWebLDAP1 o http://10.185.176.11:7011/prweb/ ");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pega p = new Pega(u, testCase);
		Ultimus ul = new Ultimus(u, testCase);
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalPega");
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("Ingresar usuario y contrase�a");
		
		p.logIn(userPega);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Dar clic sobre el bot�n \"Mostrar aplicaciones web\"");
		
		p.mouseOverNavigation();
		p.clickMisAplicaciones();		
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("No se visualiza el aplicativo <SAR>");
		
		boolean visualizaAppPega = p.existePortalSAR();
		
		assertFalse(visualizaAppPega, "Se visualiza el aplicativo");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Se ingresan usuario y contrase�a validos en Ultimus  para lograr el acceso a la herramienta");
		
		
		ul.loginUltimus(userUltimus, "");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalUltimus");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Se selecciona la pesta�a de Iniciar de Bandeja de Entrada");
		
		ul.seleccionarVista("Iniciar");	
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario da doble click sobre el aplicativo SAR ");
		
		//ul.openSivNet();
		u.hardWait(1);


		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar el log in correcto desde la plataforma PEGA al aplicativo.";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_SARV3_AccessoDeUsuariosPortalPEGA_Test";
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
		//u.getDriver().quit();
	}
}

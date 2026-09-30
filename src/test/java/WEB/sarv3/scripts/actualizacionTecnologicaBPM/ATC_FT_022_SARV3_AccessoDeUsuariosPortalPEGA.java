package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.Pega;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_022_SARV3_AccessoDeUsuariosPortalPEGA extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_SARV3_AccessoDeUsuariosPortalPEGA_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String url = data.get("Url");		
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Acceder a la url http://10.185.176.11:7011/prweb/PRWebLDAP1 o http://10.185.176.11:7011/prweb/ ");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pega p = new Pega(u, testCase);
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalPega");
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("Ingresar usuario y contrase�a");
		
		p.logIn(user);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Dar clic sobre el bot�n 'Mostrar aplicaciones web'");
		
		p.mouseOverNavigation();
		p.clickMisAplicaciones();		
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Seleccionar  el <Aplicativo >");
		
		p.clickPortalDevoluciones();
		
	    


		
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


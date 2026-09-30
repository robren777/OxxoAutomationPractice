package WorkFlows.AtributosLogisticos.ActualizacionBPM;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.AtributosLogisticos.PortalApps;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_001_FlujoFACedisInicioSesion_ActualizacionBPM extends BaseExecution {
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_FlujoFACedisInicioSesion_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("ATC_FT_Framework WorkFlows");

		/*
		 * Variables
		 */
		String user = data.get("usuario");
		String password = data.get("clave");
		//String password = PasswordUtil.decryptPassword(data.get("clave"));
		String url = data.get("url");
		
		/*
		 * Utilerias
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(url);
		PortalApps pomPortalApps = new PortalApps(u, testCase, user, password);
		addStep("Iniciar Sesion en el Portal de Aplicaciones");
		pomPortalApps.logginAtributosLogisticos();
		
	}
	

	@Override
	public void beforeTest() {
	
	}

	@Override
	public String setPrerequisites() {
		return "Ingresar los datos en el data-provider correspondiente";
	}

	@Override
	public String setTestDescription() {
		return "Inicia sesion de manera correcta en PEGA";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Luis Lopez Flores";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_FlujoFACedisInicioSesion_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
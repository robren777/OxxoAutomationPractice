package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_213_Indicadores_por_Totales_Validar_Mensajes_error_Plaza_Y_MegaPlaza_Oficina_Central extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_213_Indicadores_por_Totales_Validar_Mensajes_error_Plaza_Y_MegaPlaza_Oficina_Central_Test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String mes = data.get("mes");
		String mes2 = data.get("mes2");
		String anio = data.get("anio");
		String anio2 = data.get("anio2");
		String plaza = data.get("plaza");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiPeru mp = new MenuPrincipal_SimotiPeru(u, testCase);
		Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion;
		


		 /**
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones e Ingresar el usuario, password y dar clic en entrar"); // viene //
																											// excel
		u.get(url); 
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal(); // es para lo de la pagina no segura
		
		 /**
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		 /**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickPortalSIMOTI_PERU();
		 /**
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("El usuario despliega el menú: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores por Totales.");
		it.MAX();
		it.clickIntegridadDatos();
		u.hardWait(3);
		it.clickIndicadoresPorTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("El usuario realiza lo siguiente para el filtro que se indique:" + 
				"*Plaza: <Seleccione un valor>, el usuario No selecciona " + 
				"*Mes: mes actual por default" + 
				"*A�o: a�o actual por default" + 
				"Dar clic en [Consultar]");
		it.clickBtnConsultar();
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("El usuario Selecciona una MEGAPLAZA y da clic en [Consultar]");
		it.selectPlaza(plaza);
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("El usuario realiza lo siguiente para el filtro que se indique:" + 
				"*Plaza: <Seleccione un valor>, el usuario No selecciona un valor, si no que deja este mensaje que se muestra al inicio al entrar al reporte." + 
				"*Mes: mes actual por default" + 
				"*A�o: a�o actual por default" + 
				"Dar clic en [Consultar]");
		it.clickBtnConsultar();
		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_213_Indicadores_por_Totales_Validar_Mensajes_error_Plaza_Y_MegaPlaza_Oficina_Central";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}

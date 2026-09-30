package WEB.simoti_Chile.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Chile.om.IndicadoresPorTotales_SimotiChile;
import WEB.simoti_Chile.om.MenuPrincipal_SimotiChile;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_019_ValidarMensajesDeError_Plaza_MegaPlaza_OFICIALCENTRAL extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_ValidarMensajesDeError_Plaza_MegaPlaza_OFICIALCENTRAL_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiChile mp = new MenuPrincipal_SimotiChile(u, testCase);
		IndicadoresPorTotales_SimotiChile it = new IndicadoresPorTotales_SimotiChile (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiChile();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza lo siguiente para el filtro que se indique:\r\n" + 
				"*Plaza: <Seleccione un valor>, el usuario No selecciona \r\n" + 
				"*Mes: mes actual por default\r\n" + 
				"*A�o: a�o actual por default\r\n" + 
				"Dar clic en [Consultar]");

		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario Selecciona una MEGAPLAZA y da clic en [Consultar]");
		
		
		it.clickConsultarBtn();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario realiza lo siguiente para el filtro que se indique:\r\n" + 
				"*Plaza: <Seleccione un valor>, el usuario No selecciona un valor, si no que deja este mensaje que se muestra al inicio al entrar al reporte.\r\n" + 
				"*Mes: mes actual por default\r\n" + 
				"*A�o: a�o actual por default\r\n" + 
				"Dar clic en [Consultar]");
		
		u.getDriver().navigate().refresh();
		
		it.clickConsultarBtn();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_019_ValidarMensajesDeError_Plaza_MegaPlaza_OFICIALCENTRAL";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_ValidarMensajesDeError_Plaza_MegaPlaza_OFICIALCENTRAL_test";
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
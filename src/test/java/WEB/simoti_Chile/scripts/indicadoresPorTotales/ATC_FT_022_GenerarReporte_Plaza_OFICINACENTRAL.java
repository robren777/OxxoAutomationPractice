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

public class ATC_FT_022_GenerarReporte_Plaza_OFICINACENTRAL extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_GenerarReporte_Plaza_OFICINACENTRAL_test(HashMap<String, String> data) throws Exception {
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
		
		addStep("El usuario Selecciona una PLAZA: " + 
				"Filtro:  " + 
				"Plaza: <Seleccione un valor> " + 
				"Mes: enero " + 
				"A�o: 2010");
		
		validacion = it.selectMes("Enero");
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio("2010");
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Consultar]");
		
		it.clickConsultarBtn();
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_022_GenerarReporte_Plaza_OFICINACENTRAL";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_GenerarReporte_Plaza_OFICINACENTRAL_test";
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
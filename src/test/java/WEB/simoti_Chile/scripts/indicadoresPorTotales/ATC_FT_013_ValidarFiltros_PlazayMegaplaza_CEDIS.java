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

public class ATC_FT_013_ValidarFiltros_PlazayMegaplaza_CEDIS extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_ValidarFiltros_PlazayMegaplaza_CEDIS_test(HashMap<String, String> data) throws Exception {
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
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"Plaza, aplique lo siguiente:\r\n" + 
				"1.- Inicialmente se muestra: <Seleccione una opci�n>.\r\n" + 
				"2.- No se mostrar� la opci�n de �Todas las plazas�. \r\n" + 
				"2.- Los valores a presentar deben ser con nomenclatura: CR_PLAZA + DESC_PLAZA.\r\n" + 
				"3.- Se debe mostrar la lista y deber�n ser todas las plazas operativas del cat�logo de CNT.");

		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que la lista de Plazas que se muestran son acorde a los resultados del query.\r\n" + 
				"SELECT DISTINCT CR_PLAZA,DESC_PLAZA, MEGAPLAZA\r\n" + 
				"FROM XX_CNT_PLAZAS_TIENDAS_SIMOTI\r\n" + 
				"ORDER BY DESC_PLAZA");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"Mes, aplique lo siguiente:\r\n" + 
				"1.- Por default deber� est�r seleccionado el mes actual.\r\n" + 
				"2.- Se deber�n enlistar todos los meses del a�o.");
		
		validacion = it.validarFiltroMes();
		assertTrue(validacion, "No se muestran correctamente el filtro mes");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"A�o, aplique lo siguiente:\r\n" + 
				"1.- Se debe mostrar el a�o actual por default.\r\n" + 
				"2.- Se debe permitir consultar un a�o, \r\n" + 
				"3.- Los a�os a mostrar deber�n ser los correspondientes desde el a�o 2010 hasta la fecha actual.");
		
		validacion = it.validarFiltroAnio();
		assertTrue(validacion, "No se muestran correctamente el filtro a�o ");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mayor a mes actual> " + 
				"*A�o: <2016>");
		
		validacion = it.selectMesMayor();
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio("2016");
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores:\r\n" + 
				"*Mes: <Enero>\r\n" + 
				"*A�o: <2010> \r\n" + 
				"Da clic en [Consultar]");
		
		validacion = it.selectMes("Enero");
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio("2010");
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_013_ValidarFiltros_PlazayMegaplaza_CEDIS";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_ValidarFiltros_PlazayMegaplaza_CEDIS_test";
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

package WEB.simoti_Colombia.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.IndicadoresPorTotales_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Validar_Filtros_plaza_PLAZA extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_Filtros_plaza_PLAZA_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		IndicadoresPorTotales_SimotiColombia it = new IndicadoresPorTotales_SimotiColombia (u, testCase);
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
		
		pa.clickSimotiColombia();
		
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
				"<<<Filtro Plaza>>>");

		boolean validarPlaza = it.validarFiltroPlaza();
		assertTrue(validarPlaza, "No se muestra el filtro de plaza");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"Mes, aplique lo siguiente:\r\n" + 
				"1.- Por default deber� est�r seleccionado el mes actual.\r\n" + 
				"2.- Se deber�n enlistar todos los meses del a�o.");
		
		validacion = it.validarFiltroMes();
		assertTrue(validacion, "No se muestran correctamente los datos");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"A�o, aplique lo siguiente:\r\n" + 
				"1.- Se debe mostrar el a�o actual por default.\r\n" + 
				"2.- Se debe permitir consultar un a�o, \r\n" + 
				"3.- Los a�os a mostrar deber�n ser los correspondientes desde el a�o 2010 hasta la fecha actual.");
		
		validacion = it.validarFiltroAnio();
		assertTrue(validacion, "No se muestran correctamente los datos");
		
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mayor a mes actual>" + 
				"*A�o: <a�o actual>. El sistema aplica la validaci�n:" + 
				"'Estimado usuario no se permite la selecci�n de una fecha posterior a la actual'");
		
		if(data.get("Plaza")!=null)
		{
			validacion = it.selectPlaza(data.get("Plaza"));
			assertTrue(validacion, "No se puede seleccionar la plaza");
		}
		
		validacion = it.selectMesMayor();
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnioActual();
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
			
		it.clickConsultarBtn();
		
		String msgErrorFechaPosterior = "Estimado usuario no se permite la selecci�n de una fecha posterior a la actual.";
		mp.waitLoading();
		
		boolean validacionMsg = it.validarMensaje(msgErrorFechaPosterior);
	
		assertTrue(validacionMsg, "El sistema si permite seleccionar Fechas posteriores a la actual.");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mes>" + 
				"*A�o: <a�o>. El sistema permite consultar informaci�n desde el a�o.");
		
		if(data.get("Plaza")!=null) {
			validacion = it.selectPlaza(data.get("Plaza"));
			assertTrue(validacion, "No se puede seleccionar la plaza");
		}
		
		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		mp.waitLoading();
		
		boolean valResults = it.existenResultados();
		
		if(valResults == true) {
			validacion = true;
		}
		
		assertTrue(validacion, "El sistema no permite consultar informacion");
			
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_002_Validar_Filtros_plaza_PLAZA";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_Filtros_plaza_PLAZA_test";
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
package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.MenuPrincipalSAR;
import WEB.sarv3.om.ReportesSARv3;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_SARV3_ReportesConsultaDePasswordAjustesPOS extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_SARV3_ReportesConsultaDePasswordAjustesPOS_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		ReportesSARv3 rep = new ReportesSARv3(u, testCase);
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
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
		
		addStep("Dar clic en Reportes y seleccionar la opci�n Consulta de Password para Ajustes en POS.");
		
		mp.clickReportes();
		mp.clickConsultaPasswords();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Seleccionar los campos obligatorios y dar clic al bot�n Buscar. NOTA* Solo son v�lidos los folios con estatus \"Abierto\"");
		
		validacion = rep.camposObligatorios(data.get("Negocio"), data.get("Plaza"));
		assertTrue(validacion, "No se puede seleccionar alguna opcion proporcionada");
		
		rep.clickBuscarBtn();
		validacion = rep.validarResultados();
		assertTrue(validacion, "No se muestran resultados con los datos proporcionados");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Reportes-Consulta de Password para Ajustes en POS";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_SARV3_ReportesConsultaDePasswordAjustesPOS_Test";
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


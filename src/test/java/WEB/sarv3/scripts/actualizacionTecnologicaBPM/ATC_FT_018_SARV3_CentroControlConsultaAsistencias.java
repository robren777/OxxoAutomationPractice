package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.CentroControlSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import WEB.sarv3.om.ReportesSARv3;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_018_SARV3_CentroControlConsultaAsistencias extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_SARV3_CentroControlConsultaAsistencias_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		CentroControlSARv3 cc = new CentroControlSARv3(u, testCase);
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
		ReportesSARv3 rep = new ReportesSARv3(u, testCase);
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
		
		addStep("Dar clic en Centro de Control e ir a la opci�n de Consulta de Asistencias.");
		
		mp.clickCentroControl();
		mp.clickConsultaAsistencias();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Seleccionar un negocio y dar clic al bot�n Buscar.");
		
		validacion = cc.camposHorariosCapturar(data.get("Negocio"));
		assertTrue(validacion, "No se ingreso correctamente la informacion solicitada");
		
		cc.clickBuscarBtnCA();
		cc.esperePorFavor();
		
		validacion = cc.resultadosBusqueda();
		assertTrue(validacion, "No se muestran resultados en el grid");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Dar clic al bot�n Exportar a Excel.");
		
		rep.clickExportarNSBtn();
		
		/**
		 * 												PASO 7
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		validacion = rep.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Centro de Control-Consulta de Asistencias";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_SARV3_CentroControlConsultaAsistencias_Test";
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


package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.BusquedaSiniestroSARv3;
import WEB.sarv3.om.CatalogosSARv3;
import WEB.sarv3.om.CentroControlSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_SARV3_BusquedaSiniestrosReportes extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_SARV3_BusquedaSiniestrosReportes_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
		BusquedaSiniestroSARv3 bs = new BusquedaSiniestroSARv3(u, testCase);
		CentroControlSARv3 cc = new CentroControlSARv3(u, testCase);
		CatalogosSARv3 c = new CatalogosSARv3(u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
		
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
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
		
		addStep("Dar clic en B�squeda de Siniestros.");
		
		mp.clickBusquedaSiniestros();
		u.waitForLoadPage();
		u.hardWait(15);
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/

		addStep("Seleccionar los filtros necesarios y dar clic al bot�n Buscar");
		
		validacion = bs.camposBusquedaSiniestros(data.get("Estatus"), data.get("Negocio"), 
				data.get("FechaInicio"), data.get("FechaFin"));
		assertTrue(validacion, "No se pudo ingresar la informacion");
		
		bs.clickBuscarBtn();
		
		bs.esperePorFavor("SARv3", "EsperePorFavor");
		
		//validacion = bs.resultadoBusqueda();
		//assertTrue(validacion, "No se encontraron resultados en el grid");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Reporte Estad�stico Simple.");
		
		validacion =  bs.clickReporteEstadisticoSimpleBtn();
		assertTrue(validacion, "No se encontraron resultados en el grid");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Abrir.");
		
		validacion = c.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Reporte de Seguimiento.");
		
		bs.clickReporteSeguimientoBtn();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Abrir.");
		
		validacion = c.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Reporte de Seguimiento Segmentado.");
		
		bs.clickReporteSegSegmentadoBtn();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Abrir.");
		
		validacion = c.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Reporte de Productos.");
		
		bs.clickReporteProductosBtn();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/

		addStep("Dar clic al bot�n Abrir.");

		validacion = c.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. B�squeda de Siniestros y Reportes";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_SARV3_BusquedaSiniestrosReportes_Test";
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


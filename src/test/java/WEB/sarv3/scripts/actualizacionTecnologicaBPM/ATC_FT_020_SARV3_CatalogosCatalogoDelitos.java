package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.CatalogosSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import WEB.sarv3.om.ReportesSARv3;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_020_SARV3_CatalogosCatalogoDelitos extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_SARV3_CatalogosCatalogoDelitos_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		CatalogosSARv3 c = new CatalogosSARv3(u, testCase);
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
		
		addStep("Dar clic en Cat�logos e ir a la opci�n de Cat�logo de Delitos.");
		
		mp.clickCatalogos();
		mp.clickCatDelitos();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Nuevo.");
		
		c.clickNuevoBtn();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Llenar la informaci�n de Tipo de Delito y Descripci�n del Delito y dar clic al bot�n Guardar.");
		
		validacion = c.camposDelito(data.get("Delito"), data.get("Descripcion"));
		assertTrue(validacion, "No se ingreso correctamente la informacion");
		
		c.clickGuardarBtn();
		u.hardWait(2);
		validacion = c.validarMensaje2();
		assertTrue(validacion, "No se muestra el mensaje");
		
		
		/**
		 * 												PASO 7
		 ******************************************************************************************/
		
		addStep("Seleccionar un tipo de Delito y dar clic al Bot�n Edici�n (Lapicito)");
		
		validacion = c.buscarEnTabla3(data.get("TipoDelito"), "3", "5", "Editar");
		assertTrue(validacion, "No se encontraron resultados en el grid");
		
		
		/**
		 * 												PASO 8
		 ******************************************************************************************/
		
		addStep("Editar el Tipo de Delito, la Descripci�n del delito y dar clic al bot�n Guardar.");
		
		validacion = c.camposDelitoEditar(data.get("TipoDelitoE"), data.get("DescripcionE"));
		assertTrue(validacion, "No se ingreso correctamente la informacion");
		
		c.clickGuardarBtn();
		
		u.hardWait(2);
		validacion = c.validarMensaje2();
		assertTrue(validacion, "No se muestra el mensaje");
		
		/**
		 * 												PASO 9
		 ******************************************************************************************/
		
		addStep("Seleccionar un tipo de Delito y dar clic al Bot�n Eliminar.");
		
		//validacion = c.buscarEnTabla3(data.get("TipoDelitoE"), "3", "1", "Eliminar");
		//assertTrue(validacion, "No se encontraron resultados en el grid");
		
		c.clickEliminarBtn();
		
		u.hardWait(2);
		validacion = c.mensajeConfirmacion();
		assertTrue(validacion, "No se muestra el mensaje");
		
		/**
		 * 												PASO 10
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n SI.");
		
		c.clickSIBtn();
		
		validacion = c.mensajeEliminacion();
		assertTrue(validacion, "No se muestra el mensaje");
		
		/**
		 * 												PASO 11
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Exportar a Excel.");
		
		rep.clickExportarNSBtn();
		
		/**
		 * 												PASO 12
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = rep.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo excel");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Cat�logos-Catalogo de Empleados";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_SARV3_CatalogosCatalogoDelitos_Test";
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

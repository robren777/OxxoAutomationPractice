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

public class ATC_FT_021_SARV3_CatalogosCatalogoEmpleados extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_SARV3_CatalogosCatalogoEmpleados_Test(HashMap<String, String> data) throws Exception {
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
		
		addStep("Dar clic en Cat�logos e ir a la opci�n de Cat�logo de Empleados.");
		
		mp.clickCatalogos();
		mp.clickCatEmpleados();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Llenar la informaci�n del empleado y dar clic al bot�n Buscar.");
		
		validacion = c.camposCatalogoEmpleados(data.get("Empleado"), data.get("Negocio"));
		assertTrue(validacion, "No se ingresaron correctamente los datos");
		
		c.clickBuscarEBtn();
		u.waitForLoadPage();
		
		validacion = c.resultadosBusqueda();
		assertTrue(validacion, "No se muestran resultados en el grid");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar el usuario y dar clic al bot�n Editar.");
		
		validacion = c.buscarEnTablaEmpleados(data.get("Nombre"), data.get("Apellidos"));
		assertTrue(validacion, "No se encontro empleado con los datos proporcionados");
		
		c.clickEditarEBtn();
		
		
		/**
		 * 												PASO 7
		 ******************************************************************************************/
		
		addStep("Editar la informaci�n y dar clic al bot�n Grabar.");
		
		validacion = c.editarEmpleado(data.get("Negocio2"), data.get("Plaza"));
		assertTrue(validacion, "No se puede ingresar la informacion a editar");
		
		c.clickGrabarEBtn();
		
		u.waitForLoadPage();
		validacion = c.validarConfirmacion("Grabar");
		assertTrue(validacion, "No aparece mensaje de confirmacion");
		
		/**
		 * 												PASO 8
		 ******************************************************************************************/
		
		addStep("Seleccionar el empleado y Dar clic al bot�n Activar.");
		
		//validacion = c.buscarEnTablaEmpleados(data.get("Nombre"), data.get("Apellidos"));
		//assertTrue(validacion, "No se encontro empleado con los datos proporcionados");
		
		c.clickActivar();
		
		u.waitForLoadPage();
		u.hardWait(1);
		validacion = c.validarConfirmacion("Activar");
		assertTrue(validacion, "No aparece mensaje de confirmacion");
		
		/**
		 * 												PASO 9
		 ******************************************************************************************/
		
		addStep("Seleccionar el empleado y Dar clic al bot�n Inactivar.");
		
		//validacion = c.buscarEnTablaEmpleados(data.get("Nombre"), data.get("Apellidos"));
		//assertTrue(validacion, "No se encontro empleado con los datos proporcionados");
		
		c.clickDesactivar();
		
		u.waitForLoadPage();
		u.hardWait(1);
		validacion = c.validarConfirmacion("Inactivar");
		assertTrue(validacion, "No aparece mensaje de confirmacion");
		
		/**
		 * 												PASO 10
		 ******************************************************************************************/
		
		addStep("Seleccionar todos los usuarios y Dar clic al bot�n Exportar.");
		
		c.clickExportarBtn();
		
		/**
		 * 												PASO 11
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = c.abrirExcel();
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
		return "ATC_FT_021_SARV3_CatalogosCatalogoEmpleados_Test";
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


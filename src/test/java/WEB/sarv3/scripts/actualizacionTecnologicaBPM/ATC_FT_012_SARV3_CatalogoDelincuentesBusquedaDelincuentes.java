package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.CatalogoDelincuentesSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import WEB.sarv3.om.ReportesSARv3;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_012_SARV3_CatalogoDelincuentesBusquedaDelincuentes extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_SARV3_CatalogoDelincuentesBusquedaDelincuentes_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		CatalogoDelincuentesSARv3 cd = new CatalogoDelincuentesSARv3(u, testCase);
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
		
		addStep("Dar clic en Cat�logo de Delincuentes e ir a la opci�n de B�squeda de Delincuentes.");
		
		mp.clickCatDelincuentes();
		mp.clickBuscarDelincuente();
		u.hardWait(60);
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Llenar la informaci�n solicitada y dar clic al bot�n Buscar.");
		
		validacion = cd.camposBusquedaDelincuentes(data.get("ModusOperandi"), data.get("Zona"),
					data.get("Plaza"), data.get("Tienda"), data.get("Fecha1"), data.get("Fecha2"));
		assertTrue(validacion, "No se pueden ingresar los datos proporcionados");
		
		cd.clickBuscar();
		
		u.hardWait(30);
		validacion = cd.resultadosGrid();
		assertTrue(validacion, "No se encontraron resultados");
		
		/**
		 * 												PASO 6
		 *****************************************************************************************/
		
		addStep("Seleccionar el folio deseado y dar clic en Exportar a Excel");
	
		cd.selectFolio();
		cd.exportarExcel();
		
		/**
		 * 												PASO 7
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = rep.abrirExcel();
		assertTrue(validacion, "No se pudo abrir el archivo excel");
		
		/**
		 * 												PASO 8
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n de Ver Detalles.");
		
		cd.clickVerDetalle();
		
		u.hardWait(15);
		testCase.addScreenShotCurrentStep(u, "VerDetalle");
		
		/**
		 * 												PASO 9
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n cerrar (Tachita).");
		
		cd.clickCerrarVentana();
		
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Catalogo de Delincuentes - Busqueda de Delincuentes";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_SARV3_CatalogoDelincuentesBusquedaDelincuentes_Test";
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



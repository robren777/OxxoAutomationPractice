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

public class ATC_FT_019_SARV3_CatalogosConfiguracionSiniestrosResponsablesPP extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_SARV3_CatalogosConfiguracionSiniestrosResponsablesPP_Test(HashMap<String, String> data) throws Exception {
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
		
		addStep("Dar clic en Cat�logos e ir a la opci�n de Configuraci�n de Siniestros y Responsables de PP.");
		
		mp.clickCatalogos();
		mp.clickConfiguracion();
		
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Seleccionar un negocio, tipo de Siniestro y Subtipo de Siniestro y dar clic al bot�n Buscar.");
		
		validacion = c.camposConfigSiniestros(data.get("Negocio"), data.get("Tipo"), data.get("Subtipo"));
		assertTrue(validacion, "No se ingresaron correctamente los datos");
		
		c.clickBuscarBtn();
		
		validacion = c.validarInformacionProteccionPlaza();
		assertTrue(validacion, "No se muestra  la informaci�n Requerida en Protecci�n Patrimonial y/o no se "
				+ "habilita el filtro de Plaza.");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar una Plaza.");
		
		validacion = c.selectPlaza(data.get("Plaza"));
		assertTrue(validacion, "No se selecciono correctamente la plaza");
		
		validacion = c.validarInfDespuesDePlaza();
		assertTrue(validacion, "No se muestra correctamente la informacion");
		
		/**
		 * 												PASO 7
		 ******************************************************************************************/
		
		addStep("Ir a la secci�n de Encargados, asesores y Externos, seleccionar un asesor y dar clic al bot�n Eliminar.");
		
		validacion = c.buscarEnTabla(data.get("NombreEnc"), data.get("PaternoEnc"), 
				data.get("MaternoEnc"), "Encargados, Asesores y Externos");
		assertTrue(validacion, "No se encontro un asesor con los datos proporcionados");
		
		c.clickEliminarEncBtn();
		
		/**
		 * 												PASO 8
		 ******************************************************************************************/
		
		addStep("En la secci�n de Encargados, asesores y Externos, dar clic al bot�n Ver candidatos.");
		
		c.clickVerCandidatosEncBtn();
		
		/**
		 * 												PASO 9
		 ******************************************************************************************/
		
		addStep("Seleccionar una persona y dar clic al bot�n Agregar.");
		
		validacion = c.buscarEnTabla(data.get("NombreEncCand"), data.get("PaternoEncCand"), 
				data.get("MaternoEncCand"), "Candidatos Encargados");
		assertTrue(validacion, "No se encontro un candidato con los datos proporcionados");
		
		c.clickAgregarEncBtn();
		
		/**
		 * 												PASO 10
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar y estando nuevamente en la secci�n de la secci�n de Encargados, "
				+ "asesores y Externos, dar clic al bot�n de Ver Externos.");
		
		c.clickRegresarEncBtn();
		c.clickVerExternosEncBtn();
		
		/**
		 * 												PASO 11
		 ******************************************************************************************/
		
		addStep("Seleccionar una persona y dar clic al bot�n Agregar.");
		
		validacion = c.buscarEnTabla2(data.get("NombreEncExt"), data.get("PaternoEncExt"), "Candidatos Externos");
		assertTrue(validacion, "No se encontro un externo con los datos proporcionados");
		
		c.clickAgregarEnc2Btn();
		
		/**
		 * 												PASO 12
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar.");
		
		c.clickRegresarEnc2Btn();
		
		/**
		 * 												PASO 13
		 ******************************************************************************************/
		
		addStep("Ir a la secci�n de OS y Regionales y dar clic al bot�n Ver externos.");
		
		c.irSeccionOs();
		c.clickVerExternosOSBtn();
		
		/**
		 * 												PASO 14
		 ******************************************************************************************/
		
		addStep("Seleccionar una persona y dar clic al bot�n Agregar.");
		
		validacion = c.buscarEnTabla2(data.get("NombreOSExt"), data.get("PaternoOSExt"), "Candidatos OS");
		assertTrue(validacion, "No se encontro un externo con los datos proporcionados");
		
		c.clickAgregarOSBtn();
		
		/**
		 * 												PASO 15
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar.");
		
		c.clickRegresarOSBtn();
		
		/**
		 * 												PASO 16
		 ******************************************************************************************/
		
		addStep("Ir a la secci�n de Gerentes y Equipo B�sico y dar clic al bot�n Ver Candidatos.");
		
		c.irSeccionGerentes();
		c.clickVerCandidatosGerBtn();
		
		/**
		 * 												PASO 17
		 ******************************************************************************************/
		
		addStep("Seleccionar una persona y dar clic al bot�n Agregar.");
		
		validacion = c.buscarEnTabla(data.get("NombreGerCand"), data.get("PaternoGerCand"), 
				data.get("MaternoGerCand"), "Candidatos Gerentes");
		assertTrue(validacion, "No se encontro un candidato con los datos proporcionados");
		
		c.clickAgregarGerBtn();
		
		/**
		 * 												PASO 18
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar.");
		
		c.clickRegresarGerBtn();
		
		/**
		 * 												PASO 19
		 ******************************************************************************************/
		
		addStep("Seleccionar una persona y dar clic al bot�n Eliminar.");
		
		validacion = c.buscarEnTabla(data.get("NombreGer"), data.get("PaternoGer"), 
				data.get("MaternoGer"), "Gerentes y Equipo Basico");
		assertTrue(validacion, "No se encontro un candidato con los datos proporcionados");
		
		c.clickEliminarGerBtn();
		
		/**
		 * 												PASO 20
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Exportar a Excel.");
		
		rep.clickExportarNSBtn();
		
		/**
		 * 												PASO 21
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = rep.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo excel");
		
		/**
		 * 												PASO 22
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Grabar");
		
		c.clickGrabarBtn();
		
		validacion = c.validarMensaje();
		assertTrue(validacion, "No se muestra el mensaje");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Cat�logos-Configuracion de Siniestros y Responsables de PP";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_SARV3_CatalogosConfiguracionSiniestrosResponsablesPP_Test";
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


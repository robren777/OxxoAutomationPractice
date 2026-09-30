package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.AdmonCatalogosSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_014_SARV3_AdmonCatalogosTiposSubtiposSiniestros extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_SARV3_AdmonCatalogosTiposSubtiposSiniestros_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
		AdmonCatalogosSARv3 ac = new AdmonCatalogosSARv3(u, testCase);
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
		
		addStep("Dar clic en Adm�n. de Cat�logos e ir a la opci�n de Tipos y Subtipos de Siniestros.");
		
		mp.clickAdmonCatalogos();
		mp.clickTSSiniestro();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Llenar la informaci�n solicitada y dar clic al bot�n Buscar.");
		
		validacion = ac.camposTSSieniestros(data.get("Negocio"));
		assertTrue(validacion, "No se ingreso correctamente la informacion proporcionada");
		
		ac.clickBuscarBtn();
		
		validacion = ac.resultadosBusqueda();
		assertTrue(validacion, "No se muestran resultados en el grid");
		
		/**
		 * 												PASO 6
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Nuevo.");
	
		ac.clickNuevoBtn();
		
		/**
		 * 												PASO 7
		 *****************************************************************************************/
		
		addStep("Llenar el campo de Tipo de Siniestro y Dar clic al bot�n Guardar.");
		
		validacion = ac.camposNuevoTipoSiniestro(data.get("TipoSiniestro"));
		assertTrue(validacion, "No se ingreso correctamente el nuevo tipo de Siuniestro");
		
		ac.clickGuardarBtn();
		
		validacion = ac.validarMensajesTSS("1");
		assertTrue(validacion, "No se muestra mensaje de: \"La informaci�n fue guardada exitosamente.\"");

		/**
		 * 												PASO 8
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n de Editar (lapicito) en el tipo que se desea Editar.");
		
		validacion = ac.buscarSiniestro(data.get("TipoSiniestro"), "3", "4", "Editar");
		assertTrue(validacion, "No se encontro el Tipo de Siniestro a editar");
		
		/**
		 * 												PASO 9
		 *****************************************************************************************/
		
		addStep("Editar el nombre de Tipo de Siniestro y dar clic al bot�n Guardar.");
		
		validacion = ac.editarTipoSiniestro(data.get("TipoSiniestroE"));
		assertTrue(validacion, "No se ingreso correctamente el nuevo Tipo de siniestro");
		
		ac.clickGuardarBtnE();
		
		validacion = ac.validarMensajesTSS("1");
		assertTrue(validacion, "No se muestra el mensaje");

		/**
		 * 												PASO 10
		 *****************************************************************************************/
		
		addStep("Dar clic en el bot�n de Subtipos en el Tipo deseado.");
		
		validacion = ac.buscarSiniestro(data.get("TipoSiniestroE"), "3", "5", "");
		assertTrue(validacion, "No se encontro el Subtipo de Siniestro a editar");

		/**
		 * 												PASO 11
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Nuevo.");
		
		ac.clickNuevoBtnS();
		
		/**
		 * 												PASO 12
		 *****************************************************************************************/
		
		addStep("Llenar la informaci�n de Nombre y Descripci�n, dar clic al bot�n Guardar.");
		
		validacion = ac.camposNuevoSubtipoSiniestro(data.get("SubtipoSiniestro"), data.get("Descripcion"));
		assertTrue(validacion, "No se ingresaron correctamente los datos");
		
		ac.clickGuardarBtnS();
		
		validacion = ac.validarMensajesTSS("2");
		assertTrue(validacion, "No se muestra el mensaje");

		/**
		 * 												PASO 13
		 *****************************************************************************************/
		
		addStep("Dar clic en Edici�n (lapicito)");
		
		validacion = ac.buscarEnTabla(data.get("SubtipoSiniestro"), "3","5", "Editar");
		assertTrue(validacion, "No se encontro el Subtipo de Siniestro a editar");
		
		/**
		 * 												PASO 14
		 *****************************************************************************************/
		
		addStep("Editar el nombre de Subtipo de Siniestro y dar clic al bot�n Guardar.");
		
		validacion = ac.camposEditarSubtipoSiniestro(data.get("SubtipoSiniestroE"), data.get("DescripcionE"));
		assertTrue(validacion, "No se ingresaron correctamente los datos");
		
		ac.clickGuardarBtnSE();
		
		validacion = ac.validarMensajesTSS("2");
		assertTrue(validacion, "No se muestra el mensaje");
		
		/**
		 * 												PASO 15
		 *****************************************************************************************/
		
		addStep("Seleccionar un subtipo y dar clic al bot�n Eliminar");
		
		validacion = ac.buscarEnTabla(data.get("SubtipoSiniestroE"), "3","1", "Eliminar");
		assertTrue(validacion, "No se encontro el Subtipo de Siniestro a eliminar");
		
		ac.clickEliminarBtnS();
		
		validacion = ac.validarMensajesTSS("3");
		assertTrue(validacion, "No se muestra el mensaje Eliminar Subtipo de Siniestro, Desea eliminar "
				+ "el/los registros seleccionados? con las opciones de SI y NO");
		/**
		 * 												PASO 16
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n SI.");
		
		ac.clickSiBtnS();
		
		validacion = ac.validarMensajesTSS("4");
		assertTrue(validacion, "No se muestra el mensaje: Eliminado correctamente");
		
		/**
		 * 												PASO 17
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar.");
		
		ac.clickRegresarBtnS();
		
		/**
		 * 												PASO 18
		 *****************************************************************************************/
		
		addStep("Seleccionar un Tipo de Siniestro y dar clic al bot�n Eliminar.");
		
		validacion = ac.buscarSiniestro(data.get("TipoSiniestroE"), "3", "1", "Eliminar");
		assertTrue(validacion, "No se encontro el tipo de Siniestro a eliminar");
		
		ac.clickEliminarBtn();
		
		validacion = ac.validarMensajesTSS("5");
		assertTrue(validacion, "No se muestra el mensaje Eliminar Subtipo de Siniestro, Desea eliminar "
				+ "el/los registros seleccionados? con las opciones de SI y NO");
		
		/**
		 * 												PASO 19
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n SI.");
		
		ac.clickSIBtn();
		
		validacion = ac.validarMensajesTSS("6");
		assertTrue(validacion, "No se muestra el mensaje: Eliminado correctamente");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Adm�n. de  Cat�logos-Tipos y Subtipos de Siniestros";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_SARV3_AdmonCatalogosTiposSubtiposSiniestros_Test";
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

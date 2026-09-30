package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.AdmonCatalogosSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import WEB.sarv3.om.ReportesSARv3;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_015_SARV3_AdmonCatalogosCatalogoOtrosNegocios extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_SARV3_AdmonCatalogosCatalogoOtrosNegocios_Test(HashMap<String, String> data) throws Exception {
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
		ReportesSARv3 r = new ReportesSARv3(u, testCase);
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
		mp.clickCatOtrosNegocios();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Dar clic al bot�n Exportar a Excel.");
		
		r.clickExportarNSBtn();
		
		/**
		 * 												PASO 6
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = r.abrirExcel();
		assertTrue(validacion, "No se pudo abrir el archivo excel");
		
		/**
		 * 												PASO 7
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Nuevo.");
		
		ac.clickNuevoBtnCON();
		
		/**
		 * 												PASO 8
		 *****************************************************************************************/
		
		addStep("Llenar el campo de Nombre de Negocio y Dar clic al bot�n Guardar.");
		
		validacion = ac.camposNuevoNegocio(data.get("Nombre"));
		assertTrue(validacion, "No se ingreso correctamente la informacion");
		ac.clickGuardarBtnCON();
		validacion = ac.mensaje("Guardado");
		assertTrue(validacion, "No se guardo correctamente la informacion ingresada");
		
		/**
		 * 												PASO 9
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n de Editar (lapicito) en el Negocio que se desea Editar.");
		
		validacion = ac.buscarEnTabla(data.get("Nombre"), "3", "4", "Editar");
		assertTrue(validacion, "No se encuentra el negocio a editar");
		
		/**
		 * 												PASO 10
		 *****************************************************************************************/
		
		addStep("Editar el nombre de Negocio y dar clic al bot�n Guardar.");
		
		ac.editarNegocio(data.get("NuevoNombre"));
		ac.clickGuardarBtnCONE();
		validacion = ac.mensaje("Guardado");
		assertTrue(validacion, "No se guardo correctamente la informacion ingresada");
		
		/**
		 * 												PASO 11
		 *****************************************************************************************/
		
		addStep("Dar clic en el bot�n de Ir a Nivel 2 en el Negocio deseado.");
		
		validacion = ac.buscarEnTabla(data.get("NuevoNombre"), "3", "5", "Editar");
		assertTrue(validacion, "No se encontro el negocio " + data.get("NuevoNombre"));
		
		/**
		 * 												PASO 12
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Nuevo.");
		
		ac.clickNuevoBtnCON();
		
		/**
		 * 												PASO 13
		 *****************************************************************************************/
		
		addStep("Llenar la informaci�n de Clave y Descripci�n Plaza, dar clic al bot�n Guardar.");
		
		ac.camposNuevoNegocioNivel2(data.get("Clave"), data.get("Descripcion"));
		ac.clickGuardarBtnN2();
		validacion = ac.mensaje("Guardado");
		assertTrue(validacion, "No se guardo correctamente la informacion ingresada");
		
		/**
		 * 												PASO 14
		 *****************************************************************************************/
		
		addStep("Dar clic en Edici�n (lapicito)");
		
		validacion = ac.buscarEnTabla(data.get("Clave"), "4", "6", "Editar");
		assertTrue(validacion, "No se encontro el negocio " + data.get("Clave"));
		
		/**
		 * 												PASO 15
		 *****************************************************************************************/
		
		addStep("Editar la Clave y Descripci�n Plaza y dar clic al bot�n Guardar.");
		
		ac.editarCamposNegocioNivel2(data.get("DescripcionEditar"));
		ac.clickGuardarBtnN2E();
		validacion = ac.mensaje("Guardado");
		assertTrue(validacion, "No se guardo correctamente la informacion ingresada");
		
		
		/**
		 * 												PASO 16
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Exportar a Excel.");
		
		r.clickExportarNSBtn();
		
		
		/**
		 * 												PASO 17
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = r.abrirExcel();
		assertTrue(validacion, "No se pudo abrir el archivo excel");
		
		/**
		 * 												PASO 20
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Ir a Nivel 3.");
		
		validacion = ac.buscarEnTabla(data.get("Clave"), "4", "7", "Editar");
		assertTrue(validacion, "No se dio click en el boton Nivel 3 del negocio " + data.get("ClaveEditar"));
		
		/**
		 * 												PASO 21
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Nuevo.");
		
		ac.clickNuevoBtnCON();
		
		/**
		 * 												PASO 22
		 *****************************************************************************************/
		
		addStep("Llenar los campos obligatorios y dar clic al bot�n Guardar.");
		
		ac.camposNegocioNivel3(data.get("ClaveN3"), data.get("DescripcionN3"), data.get("Calle"), data.get("Numero"), 
				data.get("Estado"), data.get("Municipio"));
		ac.clickGuardarBtnN3();
		validacion = ac.mensaje("Guardado");
		assertTrue(validacion, "No se guardo correctamente la informacion ingresada");
		
		/**
		 * 												PASO 23
		 *****************************************************************************************/
		
		addStep("Dar clic en Editar (lapicito)");
		
		validacion = ac.buscarEnTabla(data.get("ClaveN3"), "5", "11", "Editar");
		assertTrue(validacion, "No se dio click en el boton Editar del negocio " + data.get("ClaveN3"));
		
		/**
		 * 												PASO 24
		 *****************************************************************************************/
		
		addStep("Editar los campos deseados y dar clic al bot�n Guardar.");
		
		ac.editarCamposNegocioNivel3(data.get("DescripcionN3E"), data.get("CalleE"), data.get("NumeroE"), 
				data.get("EstadoE"), data.get("MunicipioE"));
		
		ac.clickGuardarBtnN3E();
		validacion = ac.mensaje("Guardado");
		assertTrue(validacion, "No se guardo correctamente la informacion ingresada");
		
		/**
		 * 												PASO 25
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Exportar a Excel.");
		
		r.clickExportarNSBtn();
		
		/**
		 * 												PASO 26
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Abrir.");
		
		validacion = r.abrirExcel();
		assertTrue(validacion, "No se pudo abrir el archivo excel");
		
		/**
		 * 												PASO 27
		 *****************************************************************************************/
		
		addStep("Seleccionar un Negocio Nivel 3 y dar clic al bot�n Eliminar");
		
		ac.buscarEnTabla(data.get("ClaveN3"), "5", "1", "Eliminar");
		
		ac.clickEliminarBtn();
		validacion = ac.mensajeConfirmacion();
		assertTrue(validacion, "No se muestra la ventana de confirmacion");
		
		/**
		 * 												PASO 28
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n SI.");
		ac.clickSiBtn();
		u.hardWait(3);
		validacion = ac.mensaje("Eliminado");
		assertTrue(validacion, "No se elimino correctamente la informacion ingresada");
		
		/**
		 * 												PASO 29
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar.");
		
		ac.clickRegresarBtn();
		u.waitForLoadPage();
		
		/**
		 * 												PASO 18
		 *****************************************************************************************/
		
		addStep("Seleccionar un Negocio Nivel 2 y dar clic al bot�n Eliminar");
		
		validacion = ac.buscarEnTabla(data.get("Clave"), "4", "1", "Eliminar");
		assertTrue(validacion, "No se encontro el negocio " + data.get("NegocioNivel2"));
		
		ac.clickEliminarBtn();
		
		validacion = ac.mensajeConfirmacion();
		assertTrue(validacion, "No se muestra la ventana de confirmacion");
		
		/**
		 * 												PASO 19
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n SI.");
		
		ac.clickSiBtn();
		u.hardWait(3);
		validacion = ac.mensaje("Eliminado");
		assertTrue(validacion, "No se elimino correctamente la informacion ingresada");
		
		/**
		 * 												PASO 30
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Regresar.");
		
		ac.clickRegresarBtn();
		u.waitForLoadPage();

		
		/**
		 * 												PASO 31
		 *****************************************************************************************/
		
		addStep("Seleccionar un Negocio y dar clic al bot�n Eliminar.");
		
		ac.buscarEnTabla(data.get("NuevoNombre"), "3", "1", "Eliminar");
		
		ac.clickEliminarBtn();
		validacion = ac.mensajeConfirmacion();
		assertTrue(validacion, "No se muestra la ventana de confirmacion");
		
		/**
		 * 												PASO 32
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n SI.");
		
		ac.clickSiBtn();
		u.hardWait(3);
		validacion = ac.mensaje("Eliminado");
		assertTrue(validacion, "No se elimino correctamente la informacion ingresada");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Adm�n. de Cat�logos-Catalogo Otros Negocios";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_SARV3_AdmonCatalogosCatalogoOtrosNegocios_Test";
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

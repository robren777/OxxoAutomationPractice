package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.CatalogoGeneralDeImpuestos;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortal;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortalSAT;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ModeloPortalLogin;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_CodigoPostalNoValidoCGDI_HomologacionDeImpuestos extends BaseExecution {

	/**
	 * PORTAL CATALOGOS SAT/ CATALOGO GENERAL DE IMPUESTOS / CATALOGO DE ESTRUCTURA DE IMPUESTOS
	 * 
	 * DESCRIPCION: Validar que la casilla de código postal no acepte valores que no
	 * sean numéricos, una longitud máxima de 5 y si no se captura un código postal
	 * válido, no se habilite el combo de selección de estado.
	 * 
	 * PREREQUISITOS: 1. Acceso al portal SAT
	 * 2. Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 * 
	 * @author Angel Del Río
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CodigoPostalNoValidoCGDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
			throws SQLException {
		// Datos de configuración
		String portalURL = data.get("server");
		String username = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String menuPortalDeAplicaciones = data.get("menuPortalDeAplicaciones");
		String menuPortalSAT = data.get("menuPortalSAT");
		String subMenuDesplegadoPortalSAT = data.get("subMenuDesplegadoPortalSAT");
		String codigoPostalAlfa = "abcdefghijklmnñopqrstuvwxyz|°!\"#$%&/()=?¡.,:;-_´¨*+";
		String codigoPostalLargo = "123456789";

		Pattern pat = null;
		Matcher mat = null;
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Inicializar páginas
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalURL);
		MenuPortal portal = new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT = new MenuPortalSAT(u, testCase);
		CatalogoGeneralDeImpuestos catalogoGeneral = new CatalogoGeneralDeImpuestos(u, testCase);

		boolean resultValue = false;

		// PASO 1: Ingresar a la liga de acceso:
		// https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
		// Resultado Esperado: Se encuentra el mensaje: "Bienvenido a Oxxo"

		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue("Fallo en el login", login.encontrarMensajeInicio());

		// PASO 2: Capturar credenciales de acceso para iniciar sesión
		// Resultado Esperado: Se encuentra el mensaje: "Portal de Aplicaciones"

		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(username, password);
		String mensajePortal = portal.accesoMenuPortal(username, password);
		assertEquals(mensajePortal, "Portal de Aplicaciones", "No se encontró el mensaje \"Portal de Aplicaciones\"");

		// PASO 3: Seleccionar opción 'Portal Catálogos SAT'
		// Resultado Esperado: Se visualiza el menú SAT

		addStep("Seleccionar opción Portal Catálogos SAT");
		u.scroll(350);
		resultValue = portal.seleccionMenu(menuPortalDeAplicaciones, true);
		assertTrue("Error al seleccionar el Portal Catálogos SAT", resultValue);

		// PASO 4: Seleccionar el menú 'Catalogo General De Impuestos'
		// Resultado Esperado: Se selecciona el menú

		addStep("Seleccionar el menú 'Catalogo General De Impuestos'");
		resultValue = portalSAT.moverCursorSobreOpcionMenu(menuPortalSAT, true);
		assertTrue("Error al seleccionar el menú 'Catalogo General De Impuestos'", resultValue);

		// PASO 5: Seleccionar el submenú 'Informacion de impuestos a aplicar'
		// Resultado Esperado: Se visualiza la página "Catálogo de Estructura de
		// Impuestos"

		addStep("Seleccionar el submenú 'Catalogo de Estructura de Impuestos'");
		resultValue = portalSAT.seleccionOpcionDelMenuDesplegado(subMenuDesplegadoPortalSAT, true);
		assertTrue(resultValue);
		String textoCatalogoDeEstructura = catalogoGeneral.obtenerTextoDeUnElemento("textoCatalogoDeEstructura", false);
		assertEquals(textoCatalogoDeEstructura, "Catálogo de Estructura de Impuestos",
				"Error al seleccionar el submenu 'Catalogo de Estructura de Impuestos'");

		// PASO 6: Ingresar código postal con caracteres inválidos
		// Resultado Esperado: No se aceptan los caracteres

		addStep("Ingresar código postal con caracteres inválidos");
		resultValue = catalogoGeneral.llenarTextbox("textboxCodigoPostal", codigoPostalAlfa, true);
		assertTrue(resultValue);

		String textoCodigoPostal = catalogoGeneral.obtenerTextoDeUnElemento("textboxCodigoPostal", false);
		pat = Pattern.compile("^(d{0,5})$");
		mat = pat.matcher(textoCodigoPostal);
		assertTrue("El textbox acepta los caracteres: " + textoCodigoPostal, mat.find());

		// PASO 7: Ingresar código postal con longitud mayor a 9
		// Resultado Esperado: Sólo se permiten 5 caracteres

		addStep("Ingresar código postal con longitud mayor a 9");
		resultValue = catalogoGeneral.llenarTextbox("textboxCodigoPostal", codigoPostalLargo, true);
		assertTrue(resultValue);
		textoCodigoPostal = catalogoGeneral.obtenerTextoDeUnElemento("textboxCodigoPostal", false);
		assertEquals(textoCodigoPostal, codigoPostalLargo.substring(0, 5));

		addStep("Validar que el combobox Estado está deshabilitado");
		resultValue = catalogoGeneral.isEnabled("comboEstado", true);
		assertFalse("El combobox Estado está habilitado", resultValue);

		// Finaliza test case
		u.close();
	}


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso al portal SAT\n" + "Acceso a la base de datos FCPFACQA\n"
				+ "Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la casilla de código postal no acepte valores que no sean numéricos, una longitud máxima de 5 "
				+ "y si no se captura un código postal válido, no se habilite el combo de selección de estado.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CodigoPostalNoValidoCGDI_HomologacionDeImpuestos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
	
/*	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
*/
}
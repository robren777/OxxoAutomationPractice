package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.CatalogoGeneralDeImpuestos;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortal;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortalSAT;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ModeloPortalLogin;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_PaginadoDelGridCGDI_HomologacionDeImpuestos extends BaseExecution {

	/**
	 * PORTAL CATALOGOS SAT/ CATALOGO GENERAL DE IMPUESTOS / CATALOGO DE ESTRUCTURA
	 * DE IMPUESTOS
	 * 
	 * DESCRIPCION: Validar la funcionalidad del paginador del grid.
	 * 
	 * PREREQUISITOS: 1. Acceso al portal SAT 2. Acceso a la base de datos FCPFACQA
	 * 3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y
	 * XXSAT
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_PaginadoDelGridCGDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
			throws SQLException {
		String portalURL = data.get("server");
		String username = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String menuPortalDeAplicaciones = data.get("menuPortalDeAplicaciones");
		String menuPortalSAT = data.get("menuPortalSAT");
		String subMenuDesplegadoPortalSAT = data.get("subMenuDesplegadoPortalSAT");
		String codigoPostal = data.get("codigoPostal");
		String opcionEstado = data.get("opcionEstado");
		String opcionTienda = data.get("opcionTienda");
		String opcionSuperGrupo = data.get("opcionSuperGrupo");
		String opcionCategoria = data.get("opcionCategoria");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Inicializar páginas
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalURL);
		MenuPortal portal = new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT = new MenuPortalSAT(u, testCase);
		CatalogoGeneralDeImpuestos catalogoGeneral = new CatalogoGeneralDeImpuestos(u, testCase);
		String errorEnSeleccion = "No se pudo seleccionar el elemento: ";
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

		addStep("Seleccionar el submenú 'Catálogo de Estructura de Impuestos'");
		resultValue = portalSAT.seleccionOpcionDelMenuDesplegado(subMenuDesplegadoPortalSAT, true);
		assertTrue(resultValue);
		String textoCatalogoDeEstructura = catalogoGeneral.obtenerTextoDeUnElemento("textoCatalogoDeEstructura", false);
		assertEquals(textoCatalogoDeEstructura, "Catálogo de Estructura de Impuestos",
				"Error al seleccionar el submenú 'Catálogo de Estructura de Impuestos'");

		// PASO 6: Ingresar código postal
		// Resultado Esperado: Se ingresa el código postal

		addStep("Ingresar código postal");
		resultValue = catalogoGeneral.llenarTextbox("textboxCodigoPostal", codigoPostal, true);
		assertTrue(resultValue);

		addStep("Seleccionar opción del combobox Estado");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboEstado", opcionEstado, true);
		assertTrue(resultValue);

		addStep("Seleccionar opción del combobox Tienda");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboTienda", opcionTienda, true);
		assertTrue(resultValue);

		addStep("Seleccionar opción del combobox Súper Grupo");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboSuperGrupo", opcionSuperGrupo, true);
		assertTrue(resultValue);

		addStep("Seleccionar opción del combobox Categoria");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboCategoria", opcionCategoria, true);
		assertTrue(resultValue);

		addStep("Seleccionar botón Buscar");
		resultValue = catalogoGeneral.clickElemento("botonBuscar", true);
		assertTrue(resultValue);
		catalogoGeneral.esperarCargaDeDatos();

		addStep("Seleccionar lista desplegable del paginador");
		resultValue = catalogoGeneral.validarElTotalDeResultadosPaginado(true);
		assertTrue("Se muestran mas resultados de los esperados", resultValue);

		addStep("Seleccionar pagina anterior");
		String campoActual = "botonAtras";
		u.scroll(600);
		resultValue = catalogoGeneral.consultarEstatusPaginado(campoActual, true);
		assertFalse("El boton se encuentra habilitado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra deshabilitado");

		addStep("Seleccionar pagina siguiente");
		campoActual = "botonSiguiente";
		resultValue = catalogoGeneral.seleccionarElemento(campoActual, true, "");
		assertTrue(errorEnSeleccion + campoActual, resultValue);
		resultValue = catalogoGeneral.validarElTotalDeResultadosPaginado(false);
		assertTrue("Se muestran mas resultados de los esperados", resultValue);

		addStep("Volver a seleccionar pagina anterior");
		campoActual = "botonAtras";
		resultValue = catalogoGeneral.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton se encuentra deshabilitado y deberia estar habilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra habilitado");
		resultValue = catalogoGeneral.validarElTotalDeResultadosPaginado(false);
		assertTrue("Se muestran mas resultados de los esperados", resultValue);

		addStep("Seleccionar ultima pagina");
		campoActual = "botonUltimaPagina";
		resultValue = catalogoGeneral.seleccionarElemento(campoActual, false, "");
		assertTrue(errorEnSeleccion + campoActual, resultValue);
		catalogoGeneral.esperarCargaDeDatos();
		u.scroll(-600);
		resultValue = catalogoGeneral.validarElTotalDeResultadosPaginado(true);
		assertTrue("Se muestran mas resultados de los esperados", resultValue);

		addStep("Seleccionar pagina siguiente");
		campoActual = "botonSiguiente";
		u.scroll(600);
		resultValue = catalogoGeneral.consultarEstatusPaginado(campoActual, true);
		assertFalse("El boton siguiente se encuentra habilitado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("Boton siguiente deshabilitado");

		addStep("Seleccionar pagina de inicio");
		campoActual = "botonPrimerPagina";
		resultValue = catalogoGeneral.seleccionarElemento(campoActual, true, "");
		assertTrue(errorEnSeleccion + campoActual, resultValue);
		catalogoGeneral.esperarCargaDeDatos();
		campoActual = "textoPaginas";
		campoActual = catalogoGeneral.obtenerTextoDeUnElemento(campoActual, false);
		assertEquals(campoActual.substring(1, 2), "1", "El boton de pagina inicio, no funciona correctamente");
		getTestCase().addTextEvidenceCurrentStep("Pagina actual: " + campoActual.substring(1, 2));

		addStep("Seleccionar pagina anterior");
		campoActual = "botonAtras";
		u.scroll(600);
		resultValue = catalogoGeneral.consultarEstatusPaginado(campoActual, true);
		assertFalse("El boton se encuentra habilitado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra deshabilitado");

		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "Acceso al portal SAT\n" + "Acceso a la base de datos FCPFACQA\n"
				+ "Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT";

	}

	@Override
	public String setTestDescription() {

		return "Validar funcionalidad del grid";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_PaginadoDelGridCGDI_HomologacionDeImpuestos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
}
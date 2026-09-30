package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

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

public class ATC_FT_006_VentanaDeEdicionCGDI_HomologacionDeImpuestos extends BaseExecution {

	/**
	 * PORTAL CATALOGOS SAT/ CATALOGO GENERAL DE IMPUESTOS / CATALOGO DE ESTRUCTURA
	 * DE IMPUESTOS
	 * 
	 * DESCRIPCION: Validar que la información mostrada en la pantalla emergente de
	 * configuración actual coincida con la mostrada en el grid y filtros del
	 * Catálogo general de impuestos
	 * 
	 * PREREQUISITOS: 1. Acceso al portal SAT 2. Acceso a la base de datos FCPFACQA
	 * 3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y
	 * XXSAT
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_VentanaDeEdicionCGDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
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

		int numeroRegistro = catalogoGeneral.obtenerRegistroAleatorio() - 1;
		addStep("Se selecciona elemento visual de edicion del registro: " + numeroRegistro);
		String campoActual = "registro";

		u.scroll(300);
		resultValue = catalogoGeneral.seleccionarElemento(campoActual, true, String.valueOf(numeroRegistro));
		assertTrue(errorEnSeleccion + campoActual + " :" + String.valueOf(numeroRegistro), resultValue);

		addStep("Comparar informacion del Estado, se espera encontrar: " + opcionEstado);
		String inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaEstado", true);
		assertEquals(inforTexto, opcionEstado, "El estado no conincide con el esperado");

		String todoTexto[] = catalogoGeneral.obtenerSubtexto(opcionSuperGrupo);
		addStep("Comparar informacion de Super grupo, se espera encontrar: " + todoTexto[1]);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaSuperGrupo", true);
		assertEquals(inforTexto, todoTexto[1], "El super grupo no conincide con el esperado");

		String gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 7, false);
		addStep("Comparar informacion de Sub categoria, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaSubCategoria", true);
		assertEquals(inforTexto, gridInfo, "La sub categoria no conincide con la esperada");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 9, false);
		addStep("Comparar informacion de Region, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaRegion", true);
		assertEquals(inforTexto, gridInfo, "La Region no conincide con la esperada");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 10, false);
		addStep("Comparar informacion de VAT_CODE, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaVATCODE", true);
		assertEquals(inforTexto, gridInfo, "El VAT_CODE no conincide con el esperado");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 14, false);
		addStep("Comparar informacion de IEPS, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaIEPS", true);
		assertEquals(inforTexto, gridInfo, "El IEPS no conincide con el esperado");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 16, false);
		addStep("Comparar informacion de Impuesto Estatal, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaImpuestoEstatal", true);
		assertEquals(inforTexto, gridInfo, "El Impuesto Estatal no conincide con el esperado");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 19, false);
		addStep("Comparar informacion de Inicio Vigencia, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaInicioVigencia", true);
		assertEquals(inforTexto, gridInfo, "La informacion de Inicio Vigencia no conincide con la esperada");

		todoTexto = null;
		todoTexto = catalogoGeneral.obtenerSubtexto(opcionTienda);
		addStep("Comparar informacion de Super grupo, se espera encontrar: " + todoTexto[1]);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaTienda", true);
		assertEquals(inforTexto, todoTexto[1], "La tienda no conincide con la esperada");

		todoTexto = null;
		todoTexto = catalogoGeneral.obtenerSubtexto(opcionCategoria);
		addStep("Comparar informacion de Categoria, se espera encontrar: " + todoTexto[1]);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaCategoria", true);
		assertEquals(inforTexto, todoTexto[1], "La categoria no conincide con la esperada");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 8, false);
		addStep("Comparar informacion de Segmento, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaSegmento", true);
		assertEquals(inforTexto, gridInfo, "El Segmento no conincide con el esperado");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 12, false);
		addStep("Comparar informacion de Compra / Venta, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaCompraV", true);
		assertEquals(inforTexto, gridInfo, "La informacion de Compra / Venta no conincide con la esperada");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 13, false);
		addStep("Comparar informacion de IVA, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaIVA", true);
		assertEquals(inforTexto, gridInfo, "La informacion IVA no conincide con la esperada");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 15, false);
		addStep("Comparar informacion de IVA IEPS, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaIVAIEPS", true);
		assertEquals(inforTexto, gridInfo, "La informacion IVA IEPS no conincide con la esperada");

		gridInfo = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 17, false);
		addStep("Comparar informacion de IVA Impuesto Estatal, se espera encontrar: " + gridInfo);
		inforTexto = catalogoGeneral.obtenerTextoDeUnElemento("etiquetaIVAImpuestoEstatal", true);
		assertEquals(inforTexto, gridInfo, "La informacion IVA Impuesto Estatal no conincide con la esperada");

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
		return "Validar que la informacion mostrada en la pantalla emergente de configuracion actual coincida con la mostrada en el grid y filtros del Catálogo general de impuestos";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_VentanaDeEdicionCGDI_HomologacionDeImpuestos_test";
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

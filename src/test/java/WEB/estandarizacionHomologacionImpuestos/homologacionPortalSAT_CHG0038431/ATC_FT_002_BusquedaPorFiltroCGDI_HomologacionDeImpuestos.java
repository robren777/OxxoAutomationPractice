package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
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
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_BusquedaPorFiltroCGDI_HomologacionDeImpuestos extends BaseExecution {

	/**
	 * PORTAL CATALOGOS SAT/ CATALOGO GENERAL DE IMPUESTOS / CATALOGO DE ESTRUCTURA DE IMPUESTOS
	 * 
	 * DESCRIPCION: Seleccionar un registro de la búsqueda obtenida y validar que
	 * coincida con la información seleccionada en los filtros de búqueda.
	 * 
	 * PREREQUISITOS: 1. Acceso al portal SAT 2. Acceso a la base de datos FCPFACQA
	 * 3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 * 
	 * @author Angel Del Río
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_BusquedaPorFiltroCGDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
			throws SQLException {
		// Datos de configuración
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
		String bdTienda = null;
		String bdSuperGrupo = null;
		String bdCategoria = null;

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				 GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

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

		addStep("Seleccionar el submenú 'Catálogo de Estructura de Impuestos'");
		resultValue = portalSAT.seleccionOpcionDelMenuDesplegado(subMenuDesplegadoPortalSAT, true);
		assertTrue(resultValue);
		String textoCatalogoDeEstructura = catalogoGeneral.obtenerTextoDeUnElemento("textoCatalogoDeEstructura", false);
		assertEquals(textoCatalogoDeEstructura, "Catálogo de Estructura de Impuestos",
				"Error al seleccionar el submenú 'Catálogo de Estructura de Impuestos'");

		// PASO 6: Ingresar código postal
		// Resultado Esperado: Se ingresa el código postal

		addStep("Encontrar código postal '" + codigoPostal + "' en la tabla XXCFD.RMS_STATE");
		String selectCODIGOPOSTAL = "SELECT * FROM XXCFD.RMS_STATE where state in (\r\n"
				+ "Select estado from XXSAT.XXSAT_CAT_TIENDAS\r\n" + "where codigo_postal='" + codigoPostal + "')";
		utils.sql.SQLResult resultadoSQL = dbFCPFACQA.executeQuery(selectCODIGOPOSTAL);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String resultCODIGOPOSTAL = resultadoSQL.getFirstData();
		assertNotEquals(resultCODIGOPOSTAL, null, "El codigo postal capturado no es valido");

		addStep("Ingresar código postal");
		resultValue = catalogoGeneral.llenarTextbox("textboxCodigoPostal", codigoPostal, true);
		assertTrue(resultValue);

		// Validar estatus de los combobox
		resultValue = catalogoGeneral.isEnabled("comboEstado", false);
		assertTrue("El combobox Estado está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboTienda", false);
		assertFalse("El combobox Tienda está habilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboSuperGrupo", false);
		assertFalse("El combobox Súper Grupo está habilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboCategoria", false);
		assertFalse("El combobox Categoría está habilitado", resultValue);

		// PASO 7: Seleccionar opción del combobox Estado
		// Resultado Esperado: Se selecciona el estado

		addStep("Seleccionar opción del combobox Estado");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboEstado", opcionEstado, true);
		assertTrue(resultValue);

		// Validar estatus de los combobox
		resultValue = catalogoGeneral.isEnabled("comboEstado", false);
		assertTrue("El combobox Estado está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboTienda", false);
		assertTrue("El combobox Tienda está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboSuperGrupo", false);
		assertFalse("El combobox Súper Grupo está habilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboCategoria", false);
		assertFalse("El combobox Categoría está habilitado", resultValue);

		// PASO 8: Encontrar el Estado en la tabla XXCFD.RMS_STATE
		// Resultado Esperado: Se encuentra el estado en la BD

		addStep("Encontrar Estado '" + opcionEstado + "' en la tabla XXCFD.RMS_STATE");
		String selectRMS_STATE = "select * from XXCFD.RMS_STATE where description = '" + opcionEstado + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_STATE);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String bdEstado = resultadoSQL.getData(0, "description");
		assertEquals(bdEstado, opcionEstado, "No se encuentra el estado en BD");

		// PASO 9: Seleccionar opción del combobox Tienda
		// Resultado Esperado: Se selecciona la tienda

		addStep("Seleccionar opción del combobox Tienda");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboTienda", opcionTienda, true);
		assertTrue(resultValue);

		// Validar estatus de los combobox
		resultValue = catalogoGeneral.isEnabled("comboEstado", false);
		assertTrue("El combobox Estado está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboTienda", false);
		assertTrue("El combobox Tienda está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboSuperGrupo", false);
		assertTrue("El combobox Súper Grupo está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboCategoria", false);
		assertFalse("El combobox Categoría está habilitado", resultValue);

		// PASO 10: Encontrar la Tienda en la tabla XXCFD.STORE
		// Resultado Esperado: Se encuentra la tienda en la BD

		addStep("Encontrar Tienda '" + opcionTienda + "' en la tabla XXCFD.STORE");
		String tiendaArr[] = catalogoGeneral.obtenerSubtexto(opcionTienda);
		String selectSTORE = "select store, store_name, store_name10, store_city, state "
				+ "from XXCFD.STORE where store_name10 like '%" + tiendaArr[0] + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectSTORE);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		try {
			bdTienda = resultadoSQL.getData(0, "store_name");
		} catch (Exception e) {
			throw new AssertionError("No se encuentra la tienda en BD");
		}
		assertEquals(bdTienda, tiendaArr[1], "No se encuentra la tienda en BD");

		// PASO 11: Seleccionar opción del combobox Súper Grupo
		// Resultado Esperado: Se selecciona el súper grupo

		addStep("Seleccionar opción del combobox Súper Grupo");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboSuperGrupo", opcionSuperGrupo, true);
		assertTrue(resultValue);

		// Validar estatus de los combobox
		resultValue = catalogoGeneral.isEnabled("comboEstado", false);
		assertTrue("El combobox Estado está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboTienda", false);
		assertTrue("El combobox Tienda está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboSuperGrupo", false);
		assertTrue("El combobox Súper Grupo está deshabilitado", resultValue);
		resultValue = catalogoGeneral.isEnabled("comboCategoria", false);
		assertTrue("El combobox Categoría está deshabilitado", resultValue);

		// PASO 12: Encontrar el Súper Grupo en la tabla XXCFD.RMS_DIVISION
		// Resultado Esperado: Se encuentra el súper grupo en la BD

		addStep("Encontrar Súper Grupo '" + opcionSuperGrupo + "' en la tabla XXCFD.RMS_DIVISION");
		String superGrupoArr[] = catalogoGeneral.obtenerSubtexto(opcionSuperGrupo);
		String selectRMS_DIVISION = "select * from XXCFD.RMS_DIVISION where division = '" + superGrupoArr[0] + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_DIVISION);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		try {
			bdSuperGrupo = resultadoSQL.getData(0, "div_name");
		} catch (Exception e) {
			throw new AssertionError("No se encuentra el súper grupo en BD");
		}
		assertEquals(bdSuperGrupo, superGrupoArr[1], "No se encuentra el súper grupo en BD");

		// PASO 13: Seleccionar opción del combobox Categoría
		// Resultado Esperado: Se selecciona la categoría

		addStep("Seleccionar opción del combobox Categoria");
		resultValue = catalogoGeneral.seleccionarOpcionCombo("comboCategoria", opcionCategoria, true);
		assertTrue(resultValue);

		// PASO 14: Encontrar la Categoría en la tabla XXCFD.RMS_GROUPS
		// Resultado Esperado: Se encuentra la categoría en la BD

		addStep("Encontrar Categoría '" + opcionCategoria + "' en la tabla XXCFD.RMS_GROUPS");
		String categoriaArr[] = catalogoGeneral.obtenerSubtexto(opcionCategoria);
		String selectRMS_GROUPS = "select * from XXCFD.RMS_GROUPS where group_no = '" + categoriaArr[0] + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_GROUPS);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		try {
			bdCategoria = resultadoSQL.getData(0, "group_name");
		} catch (Exception e) {
			throw new AssertionError("No se encuentra la categoría en BD");
		}
		assertEquals(bdCategoria, categoriaArr[1], "No se encuentra la categoría en BD");

		// PASO 15: Seleccionar botón Buscar
		// Resultado Esperado: Se muestran los resultados de búsqueda

		addStep("Seleccionar botón Buscar");
		resultValue = catalogoGeneral.clickElemento("botonBuscar", true);
		assertTrue(resultValue);
		catalogoGeneral.esperarCargaDeDatos();

		// PASO 16: Seleccionar un registro para compararlo con los filtros de búsqueda
		// seleccionados
		// Resultado Esperado: Se valida la información del registro
		addStep("Seleccionar un registro para compararlo con los filtros de búsqueda seleccionados");
		u.scroll(600);
		int numeroRegistro = catalogoGeneral.obtenerRegistroAleatorio();
		String gridClave = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 3, true);
		assertNotEquals(gridClave, "NO EXISTEN DATOS", "Los filtros seleccionados no arrojan resultados");
		String gridDescripcion = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 4, false);
		String gridSubcategoria = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 7, false);
		String gridSegmento = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 8, false);
		String gridRegion = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 9, false);
		String gridVATCODE = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 10, false);
		String gridCompraVenta = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 12, false);
		String gridIVA = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 13, false);
		String gridIEPS = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 14, false);
		String gridIVAIEPS = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 15, false);
		String gridImpEstValor = catalogoGeneral.validarInformacionRegistro(numeroRegistro, 16, false);

		String selectITEM_MASTER = "SELECT ITEM, ITEM_DESC FROM XXCFD.ITEM_MASTER WHERE ITEM = " + gridClave;
		resultadoSQL = dbFCPFACQA.executeQuery(selectITEM_MASTER);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);

		String itemBD = resultadoSQL.getData(0, "ITEM");
		assertEquals(itemBD.replaceAll("^0+",""), gridClave.replaceAll("^0+",""), "No coincide la clave del articulo con la BD");
		assertEquals(resultadoSQL.getData(0, "ITEM_DESC").replace(" ", ""), gridDescripcion.replace(" ", ""),
				"No coincide la descripcion del articulo con la BD");

		String selectRMS_DEPS = "SELECT DEPT, DEPT_NAME, BUYER, MERCH, PROFIT_CALC_TYPE,PURCHASE_TYPE "
				+ "FROM XXCFD.RMS_DEPS WHERE DEPT_NAME= '" + gridSubcategoria + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_DEPS);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		assertFalse(resultadoSQL.isEmpty());
		
		String selectRMS_DEPS2 = "SELECT GROUP_NO, BUD_INT, BUD_MKUP, TOTAL_MARKET_AMT, MARKUP_CALC_TYPE "
				+ "FROM XXCFD.RMS_DEPS WHERE DEPT_NAME= '" + gridSubcategoria + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_DEPS2);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		assertFalse(resultadoSQL.isEmpty());
		
		String selectRMS_DEPS3 = "SELECT OTB_CALC_TYPE, MAX_AVG_COUNTER, AVG_TOLERANCE_PCT, DEPT_VAT_INCL_IND "
				+ "FROM XXCFD.RMS_DEPS WHERE DEPT_NAME= '" + gridSubcategoria + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_DEPS3);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		assertFalse(resultadoSQL.isEmpty());

		String selectRMS_CLASS = "select * from XXCFD.RMS_CLASS where CLASS_NAME = '" + gridSegmento + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_CLASS);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);

		assertEquals(resultadoSQL.getData(0, "CLASS_NAME"), gridSegmento, "No coincide el segmento con la BD");

		String selectRMS_VATREGION = "SELECT * FROM XXCFD.RMS_VAT_REGION WHERE VAT_REGION_NAME= '" + gridRegion + "'";
		utils.sql.SQLResult resultadoSQLVAT_REGION = dbFCPFACQA.executeQuery(selectRMS_VATREGION);
		testCase.addQueryEvidenceCurrentStep(resultadoSQLVAT_REGION);
		assertFalse(resultadoSQLVAT_REGION.isEmpty());

		String selectRMS_VAT_CODES = "select * from XXCFD.RMS_VAT_CODES where VAT_CODE = '" + gridVATCODE + "'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectRMS_VAT_CODES);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);

		assertEquals(resultadoSQL.getData(0, "VAT_CODE"), gridVATCODE, "No coincide la clave del artículo con la BD");
		String filtroVATCODEDESC = gridIVA + ";" + gridIEPS + ";" + gridIVAIEPS + ";" + gridImpEstValor;
		assertEquals(resultadoSQL.getData(0, "VAT_CODE_DESC"), filtroVATCODEDESC,
				"No coinciden los datos de impuestos con la BD");

		int vatRegion = Integer.valueOf(resultadoSQLVAT_REGION.getData(0, "VAT_REGION"));

		String selectCOMPRA_VENTA = "SELECT ID,ITEM, VAT_REGION, VAT_TYPE, VAT_CODE, VIGENCIA_INICIO, VIGENCIA_FIN"
				+ " FROM XXSAT.XXSAT_CAT_EST_IMPTOS WHERE ITEM='" + gridClave + "' "
				+ "AND VAT_REGION= " + vatRegion + " AND VAT_CODE= '" + gridVATCODE + "' AND ACTIVE='Y'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectCOMPRA_VENTA);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String compraVentaBD = resultadoSQL.getData(0, "VAT_TYPE");
		assertEquals(compraVentaBD, gridCompraVenta, "No coinciden los datos de impuestos con la BD");
		
		String selectCOMPRA_VENTA2 = "SELECT CREATE_USER_ID, CREATE_DATETIME, LAST_UPDATE_USER_ID, LAST_UPDATE_DATETIME, ACTIVE"
				+ " FROM XXSAT.XXSAT_CAT_EST_IMPTOS WHERE ITEM='" + gridClave + "' "
				+ "AND VAT_REGION= " + vatRegion + " AND VAT_CODE= '" + gridVATCODE + "' AND ACTIVE='Y'";
		resultadoSQL = dbFCPFACQA.executeQuery(selectCOMPRA_VENTA2);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		
		

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
		return "Seleccionar un registro de la búsqueda obtenida y validar que coincida con la información seleccionada en los filtros de búqueda.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_BusquedaPorFiltroCGDI_HomologacionDeImpuestos_test";
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

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

}
package WorkFlows.Siv;

import static org.testng.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Siv.pomSIV;
import WorkFlows.util.Siv.utilSiv_DatosBasicos;
import WorkFlows.util.Siv.utilSiv_InboxPendientes;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class Categoria_Rechazo_Abortado extends BaseExecution {
	private SeleniumUtil u;
	private pomSIV pom;
	private pomUltimus ultimus;
	private utilSiv_InboxPendientes inbox;
	private utilSiv_DatosBasicos datosBasicos;
	private String windowUltimus = "";

	@Test(dataProvider = "data-provider")

	public void ATC_Altas_Categoria(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioCategoria = data.get("usuarioCategoria");
		String usuarioCatalogo = data.get("usuarioCatalogo");
		// - Datos B�sicos -//
		String descripcionCorta = data.get("descripcionCorta");
		String descripcionLarga = data.get("descripcionLarga");
		String superGrupo = data.get("superGrupo");
		String categoria = data.get("categoria");
		String subCategoria = data.get("subCategoria");
		String segmento = data.get("segmento");
		String subSegmento = data.get("subSegmento");
		String storeOrderMultiple = data.get("storeOrderMultiple");
		String unidadMedida = data.get("unidadMedida");
		String unidadVenta = data.get("unidadVenta");
		String codigoFiscal = data.get("codigoFiscal");
		String precioSugerido = data.get("precioSugerido");
		String checkOxxo = data.get("checkOxxo");
		String checkBara = data.get("checkBara");
		String presentacion = data.get("presentacion");
		String udaPresentacion = data.get("udaPresentacion");
		String contenido = data.get("contenido");
		String unidadContenido = data.get("unidadContenido");
		String base = data.get("base");
		String peatonal = data.get("peatonal");
		String turOasis = data.get("turOasis");
		String bara = data.get("bara");
		String marcaProveedor = data.get("marcaProveedor");
		String udaMarcaProveedor = data.get("udaMarcaProveedor");
		String marcaArticulo = data.get("marcaArticulo");
		String udaMarcaArticulo = data.get("udaMarcaArticulo");
		String checkMerma = data.get("checkMerma");
		String checkCandadoVenta = data.get("checkCandadoVenta");
		String udaLibre = data.get("udaLibre");

		/// INICIALIZACI�N DE POMS ///
		u = new SeleniumUtil(new FirefoxTest(), true);
		windowUltimus = u.getDriver().getWindowHandle();
		pom = new pomSIV(u, testCase);
		ultimus = new pomUltimus(u);
		inbox = new utilSiv_InboxPendientes(u, testCase);
		datosBasicos = new utilSiv_DatosBasicos(u, testCase);
		final String BASE_PATH = System.getProperty("user.dir");

		String cb = "7690703638070";

		/// LOGUEARSE CON USUARIO CAT�LOGO ///
		addStep("Iniciar sesi�n con usuario: " + usuarioCatalogo);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesi�n en Ultimus_" + usuarioCatalogo);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL CATEGOR�A CORRESPONDIENTE ///
		addStep("Seleccionar la Categor�a e ir a Altas");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.altasCatalogos();

		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar el c�digo de barras y abrir Datos B�sicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(u, "Validar Datos B�sicos");

		/// ACEPTAR DATOS B�SICOS ///
		addStep("Rechazar los Datos B�sicos");
		inbox.rechazarInfoBasicaCatalogos();
		u.hardWait(1);
		pom.msgRechazar("Datos Basicos", "Error en la descripci�n");

		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar el c�digo de barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoAltas();

		/// ACEPTAR FUENTE DE ABASTO ///
		addStep("Rechazar la Fuente de Abasto");
		u.hardWait(1);
		inbox.checkTodosFuenteAbastoValidacion(true);
		inbox.rechazarFuenteAbastoValidacion();
		pom.msgRechazar("FA", "Costos incorrectos");
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar CB y vaciar Inbox Altas");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.vaciarInboxAltas();
		u.hardWait(2);
		pom.msgEjecucion();
		testCase.addScreenShotCurrentStep(u, "Vaciar Inbox Altas");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesi�n en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesi�n Cerrada");

		/**
		 * 
		 */

		/// LOGIN ULTIMUS ///
		addStep("Iniciar sesi�n con usuario: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesi�n en Ultimus_" + usuarioCategoria);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL MEN� INBOX PENDIENTES DE CAT�LOGOS ///
		u.hardWait(2);
		inbox.menuInboxPendientesCatalogos();
		u.hardWait(1);

		/// SELECCIONAR EL CATEGOR�A CORRESPONDIENTE E IR A RECHAZADOS ///
		addStep("Seleccionar la Categor�a e ir a Rechazados");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.rechazadosCatalogos();

		// ------------
		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar el c�digo de barras y abrir Datos B�sicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();
		u.hardWait(3);

		/// ACEPTAR RECHAZO DATOS B�SICOS ///
		addStep("Aceptar Rechazo de Datos B�sicos");
		inbox.aceptarRechazoDatosBasicos();
		u.hardWait(1);

		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar el c�digo de barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoCatalogos();

		/// ACEPTAR FUENTE DE ABASTO ///
		addStep("Aceptar Rechazo de Fuente de Abasto");
		u.hardWait(1);
		inbox.aceptarRechazoFA();
		u.hardWait(1);
		inbox.regresarFuenteAbastoValidacion();
		testCase.addScreenShotCurrentStep(u, "Item Rechazado");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesi�n en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesi�n Cerrada");
		
		addStep("Validar en la Base de Datos el estatus del C�digo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER2_XXVA_SIV3, 
					GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"+cb+"'";
			System.out.println(query_ItemStatus);
			
			queryResult= executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontrar� el c�digo de Barras");
			
		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al ejecutar el query");
		}

		/// FIN DE FLUJO ///

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ingresar los datos en el data-provider correspondiente";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Altas_Categoria";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_Altas_Categoria";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}

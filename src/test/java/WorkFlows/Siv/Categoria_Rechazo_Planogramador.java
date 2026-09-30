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
import WorkFlows.util.Siv.utilSiv_fuenteAbasto;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class Categoria_Rechazo_Planogramador extends BaseExecution {
	private SeleniumUtil u;
	private pomSIV pom;
	private pomUltimus ultimus;
	private utilSiv_InboxPendientes inbox;
	private utilSiv_DatosBasicos datosBasicos;
	private utilSiv_fuenteAbasto fuenteAbasto;
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
		fuenteAbasto = new utilSiv_fuenteAbasto(u, testCase);
		final String BASE_PATH = System.getProperty("user.dir");

		String cb = "7689305837592";

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

		/// GUARDAR DATOS B�SICOS ///
		addStep("Guardar Datos B�sicos");
		datosBasicos.siguienteCatalogos();
		u.hardWait(1);

		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar el c�digo de barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoCatalogos();

		/// GUARDAR FUENTE DE ABASTO ///
		addStep("Guardar Fuente de Abasto");
		u.hardWait(1);
		fuenteAbasto.guardarCatalogos();
		u.hardWait(1);
		inbox.regresarFuenteAbastoValidacion();
		testCase.addScreenShotCurrentStep(u, "Item Rechazado");

		/// VACIAR INBOX
		addStep("Seleccionar el c�digo de barras y vaciar inbox");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.vaciarInboxAltas();
		u.hardWait(1);
		inbox.salirInboxCatalogos();

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

		/// VALIDAR INFORMACI�N B�SICA ///
		addStep("Validar que la informaci�n sea la que se ingres�");
		u.hardWait(4);
		if (!datosBasicos.validateDescripcionCorta(descripcionCorta))
			testCase.addTextEvidenceCurrentStep("La descripci�n corta \"" + descripcionCorta + "\" no coincide");
		if (!datosBasicos.validateDescripcionLarga(descripcionLarga))
			testCase.addTextEvidenceCurrentStep("La descripci�n larga \"" + descripcionLarga + "\" no coincide");
		if (!datosBasicos.validateSuperGrupo(superGrupo))
			testCase.addTextEvidenceCurrentStep("El superGrupo \"" + superGrupo + "\" no coincide");
		if (!datosBasicos.validateCategoria(categoria))
			testCase.addTextEvidenceCurrentStep("La Categor�a \"" + categoria + "\" no coincide");
		if (!datosBasicos.validateSubCategoria(subCategoria))
			testCase.addTextEvidenceCurrentStep("La subCategor�a \"" + subCategoria + "\" no coincide");
		if (!datosBasicos.validateSegmento(segmento))
			testCase.addTextEvidenceCurrentStep("El segmento \"" + segmento + "\" no coincide");
		if (!datosBasicos.validateSubSegmento(subSegmento))
			testCase.addTextEvidenceCurrentStep("El subSegmento \"" + subSegmento + "\" no coincide");
		if (!datosBasicos.validateStoreOrderMultiple(storeOrderMultiple))
			testCase.addTextEvidenceCurrentStep("El Store Order Multiple \"" + storeOrderMultiple + "\" no coincide");
		if (!datosBasicos.validateUnidadMedida(unidadMedida))
			testCase.addTextEvidenceCurrentStep("La unidad de Medida \"" + unidadMedida + "\" no coincide");
		if (!datosBasicos.validateUnidadVenta(unidadVenta))
			testCase.addTextEvidenceCurrentStep("La unidad de venta \"" + unidadVenta + "\" no coincide");
		if (!datosBasicos.validateCodigoFiscal(codigoFiscal))
			testCase.addTextEvidenceCurrentStep("El c�gido Fiscal \"" + codigoFiscal + "\" no coincide");
		if (!datosBasicos.validatePrecioSugerido(precioSugerido))
			testCase.addTextEvidenceCurrentStep("El precio sugerido \"" + precioSugerido + "\" no coincide");
		if (!datosBasicos.validatePresentacion(udaPresentacion))
			testCase.addTextEvidenceCurrentStep("La presentaci�n \"" + udaPresentacion + "\" no coincide");
		if (!datosBasicos.validateContenido(contenido))
			testCase.addTextEvidenceCurrentStep("El contenido \"" + contenido + "\" no coincide");
		if (!datosBasicos.validateUnidadContenido(unidadContenido))
			testCase.addTextEvidenceCurrentStep("La unidad de contenido \"" + unidadContenido + "\" no coincide");
		if (!datosBasicos.validateMarcaProveedor(udaMarcaProveedor))
			testCase.addTextEvidenceCurrentStep("La marca proveedor \"" + udaMarcaProveedor + "\" no coincide");
		if (!datosBasicos.validateMarcaArticulo(udaMarcaArticulo))
			testCase.addTextEvidenceCurrentStep("La marca art�culo \"" + udaMarcaArticulo + "\" no coincide");
		if (!datosBasicos.validateMerma(checkMerma))
			testCase.addTextEvidenceCurrentStep("La merma \"" + checkMerma + "\" no coincide");
		if (!datosBasicos.validateCandadoVenta(checkCandadoVenta))
			testCase.addTextEvidenceCurrentStep("El candado venta \"" + checkCandadoVenta + "\" no coincide");
		if (!datosBasicos.validateUdaLibre(udaLibre))
			testCase.addTextEvidenceCurrentStep("La uda libre \"" + udaLibre + "\" no coincide");
		testCase.addScreenShotCurrentStep(u, "Validar Datos B�sicos");

		/// ACEPTAR DATOS B�SICOS ///
		addStep("Aceptar los Datos B�sicos");
		inbox.aceptarInfoBasicaCatalogos();
		String textModal = pom.msgModal();
		if (textModal.contains("Aceptado exitoso")) {
			pom.optionMsgModal("OK");
			u.hardWait(1);
			pom.waitLoadingModal();
		} else if (textModal.contains("Debe seleccionar")) {
			testCase.addBoldTextEvidenceCurrentStep("FALTA INFORMACI�N");
			pom.optionMsgModal("OK");
			u.hardWait(1);
			pom.waitLoadingModal();
			datosBasicos.enterMarcaProveedor(marcaProveedor, udaMarcaProveedor);
			datosBasicos.enterMarcaArticulo(marcaArticulo, udaMarcaArticulo);
			inbox.aceptarInfoBasicaCatalogos();
			pom.optionMsgModal("OK");
			//throw new AssertionError("Fall� al aceptar la informaci�n");
		} 

		/// SELECCIONAR CB EN ALTAS CAT�LOGOS ///
		addStep("Seleccionar el c�digo de barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoAltas();

		/// ACEPTAR FUENTE DE ABASTO ///
		addStep("Seleccionar todas las fuentes de abasto y aceptar");
		u.hardWait(1);
		inbox.checkTodosFuenteAbastoValidacion(true);
		inbox.aceptarFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.aceptarComentarioFuenteAbastoValidacion();
		u.hardWait(1);
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

		addStep("Validar en la Base de Datos el estatus del C�digo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"
					+ cb + "'";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
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
		//u.getDriver().quit();
	}

}

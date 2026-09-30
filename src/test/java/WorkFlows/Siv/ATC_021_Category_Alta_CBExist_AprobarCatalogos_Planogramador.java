package WorkFlows.Siv;

import static org.testng.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Siv.pomSIV;
import WorkFlows.util.Siv.utilSiv_BuscarArticulo;
import WorkFlows.util.Siv.utilSiv_Conjuntos;
import WorkFlows.util.Siv.utilSiv_DatosBasicos;
import WorkFlows.util.Siv.utilSiv_EnvioMuestraFisica;
import WorkFlows.util.Siv.utilSiv_InboxPendientes;
import WorkFlows.util.Siv.utilSiv_fuenteAbasto;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_021_Category_Alta_CBExist_AprobarCatalogos_Planogramador
		extends BaseExecution {
	private SeleniumUtil u;
	private pomSIV pom;
	private pomUltimus ultimus;
	private utilSiv_InboxPendientes inbox;
	private utilSiv_BuscarArticulo buscar;
	private utilSiv_DatosBasicos datosBasicos;
	private utilSiv_fuenteAbasto fuenteAbasto;
	private utilSiv_EnvioMuestraFisica muestraFisica;
	private utilSiv_Conjuntos conjunto;
	private String windowUltimus = "";

	@Test(dataProvider = "data-provider")

	public void ATC_021_Category_Alta_CBExist_AprobarCatalogos_Planogramador_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: SIV");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioCategoria = data.get("usuarioCategoria");
		String usuarioCatalogo = data.get("usuarioCatalogo");
		// - CB - //
		String cb = data.get("cb");
		// - Datos Basicos -//
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
		String gradosAlcohol = data.get("gradosAlcohol");
		String udaGradosAlcohol = data.get("udaGradosAlcohol");
		String diasPublicacion = data.get("diasPublicacion");
		String fechaPublicacion = data.get("fechaPublicacion");
		String base = data.get("base");
		String excluir = data.get("excluir");
		String peatonal = data.get("peatonal");
		String turOasis = data.get("turOasis");
		String mixtas = data.get("mixtas");
		String bara = data.get("bara");
		String marcaProveedor = data.get("marcaProveedor");
		String udaMarcaProveedor = data.get("udaMarcaProveedor");
		String marcaArticulo = data.get("marcaArticulo");
		String udaMarcaArticulo = data.get("udaMarcaArticulo");
		String checkMerma = data.get("checkMerma");
		String checkCandadoVenta = data.get("checkCandadoVenta");
		String udaLibre = data.get("udaLibre");

		// - Conjuntos -//
		String planogrupos = data.get("planogrupos");
		String segmentos = data.get("segmentos");
		String muebles = data.get("muebles");
		String plazas = data.get("plazas");
		String conjuntos = data.get("conjuntos");

		// - Fuente Abasto -//
		String razonSocialFuentesAbasto = data.get("razonSocial:FuentesAbasto");
		String fuenteAbastoPlazas = data.get("fuenteAbasto:Plazas");
		String arrayEach[] = data.get("vol_each").split(",");
		String arrayIner[] = data.get("vol_inner").split(",");
		String arrayCase[] = data.get("vol_case").split(",");
		String cama = data.get("camaTI");
		String altura = data.get("alturaHI");
		String costoBruto = data.get("costoBrutoUnitario");
		String objVenta = data.get("objVenta");
		String ventaMinEx = data.get("ventaMinEx");
		String CBPLU = data.get("CBPLU");
		String plaza = data.get("plaza");
		String porcentaje = data.get("porcentaje");

		// - Muestra Fisica -//
		String metodoEnvio = data.get("metodoEnvio");
		String personaQueEnvia = data.get("personaQueEnvia");
		String empresaPaqueteria = data.get("empresaPaqueteria");
		String direccionRecepcion = data.get("direccionRecepcion");
		String infoAdicional = data.get("infoAdicional");
		String telefono = data.get("telefono");
		String numGuia = data.get("numGuia");
		String fechaEnvio = data.get("fechaEnvio");
		String fechaEsperada = data.get("fechaEsperada");
		String nombreImagen = data.get("nombreImagen");

		/// INICIALIZACIÓN DE POMS ///
		u = new SeleniumUtil(new FirefoxTest(), true);
		windowUltimus = u.getDriver().getWindowHandle();
		pom = new pomSIV(u, testCase);
		ultimus = new pomUltimus(u);
		inbox = new utilSiv_InboxPendientes(u, testCase);
		buscar = new utilSiv_BuscarArticulo(u, testCase);
		datosBasicos = new utilSiv_DatosBasicos(u, testCase);
		fuenteAbasto = new utilSiv_fuenteAbasto(u, testCase);
		muestraFisica = new utilSiv_EnvioMuestraFisica(u, testCase);
		conjunto = new utilSiv_Conjuntos(u, testCase);
		final String BASE_PATH = System.getProperty("user.dir");

		/// LOGIN ULTIMUS ///
		addStep("Iniciar sesion con usuario: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCategoria);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(2);
		pom.waitLoadingModal();
		u.hardWait(2);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// AGREGAR ITEM ///
		addStep("Agregar item: buscar por codigo de barras");
		inbox.agregarItem();
		u.hardWait(2);

		// cb="";

		/// BUSCAR EL CB ///
		buscar.enterCB(cb);
		buscar.busquedaPredeterminada();
		if (buscar.seEncontraronArticulos()) {
			addStep("Seleccionar articulo y Modificar");
			buscar.seleccionarEncontrados(cb);
		} else {
			throw new AssertionError("No se encontro el articulo: " + cb);
		}

		/// ALTA ///
		buscar.Alta();
		if (pom.msgModal().contains("exitosamente")) {
			pom.optionMsgModal("OK");
		} else {
			throw new AssertionError("No se agregaron los articulos");
		}

		/// CAPTURAR DATOS ///
		addStep("Capturar los Datos Basicos para el item: " + cb);
		u.hardWait(4);
		inbox.seleccionarItemPendiente(cb);
		inbox.datosBasicosAltas();
		u.hardWait(3);
		if (pom.msgModal().contains("incompletos")) {
			pom.optionMsgModal("OK");
		}

		/// INGRESAR DATOS BaSICOS ///
		u.hardWait(4);
		datosBasicos.enterDescripcionCorta(descripcionCorta);
		datosBasicos.enterDescripcionLarga(descripcionLarga);

		datosBasicos.selectSuperGrupo(superGrupo);
		datosBasicos.selectCategoriaPlaza(categoria);
		datosBasicos.selectSubCategoria(subCategoria);
		datosBasicos.selectSegmentoPlaza(segmento);
		datosBasicos.selectSubSegmento(subSegmento);

		datosBasicos.selectStoreOrderMultiple(storeOrderMultiple);
		datosBasicos.selectUnidadMedida(unidadMedida);
		datosBasicos.selectUnidadVenta(unidadVenta);

		datosBasicos.selectCodigoFiscal(codigoFiscal);
		datosBasicos.enterPrecioSugerido(precioSugerido);
		if (checkOxxo.equalsIgnoreCase("SI")) {
			datosBasicos.selectOxxo(true);
		} else {
			datosBasicos.selectOxxo(false);
		}
		if (checkBara.equalsIgnoreCase("SI")) {
			datosBasicos.selectBara(true);
		} else {
			datosBasicos.selectBara(false);
		}

		datosBasicos.enterPresentacion(presentacion, udaPresentacion);
		datosBasicos.enterContenido(contenido);
		datosBasicos.selectUnidadContenido(unidadContenido);
		datosBasicos.enterGradosAlcohol(gradosAlcohol, udaGradosAlcohol);
		testCase.addScreenShotCurrentStep(u, "Datos ingresados parte 1");

		datosBasicos.enterBase(base);
		datosBasicos.enterExcluir(excluir);
		datosBasicos.enterPeatonal(peatonal);
		datosBasicos.enterTurOasis(turOasis);
		datosBasicos.enterMixtas(mixtas);
		if (checkBara.equalsIgnoreCase("SI")) {
			datosBasicos.enterBara(bara);
		}

		datosBasicos.enterMarcaProveedor(marcaProveedor, udaMarcaProveedor);
		datosBasicos.enterMarcaArticulo(marcaArticulo, udaMarcaArticulo);
		datosBasicos.selectMerma(checkMerma);
		datosBasicos.selectCandado(checkCandadoVenta);

		datosBasicos.enterUdaLibre(udaLibre);

		/// EXTRAS ///
		datosBasicos.selectDiasPublicacion(diasPublicacion);
		datosBasicos.selectFechaPublicacion(fechaPublicacion);

		datosBasicos.siguiente();

		pom.addDataToDataProviderFile(this.getDataProviderPath(), "Sheet1", 4, cb);
		testCase.addScreenShotCurrentStep(u, "CB en inbox");

		/// IR AL MÓDULO DE CONJUNTOS ///
		addStep("Ir a Conjuntos");
		u.hardWait(4);
		inbox.seleccionarItemPendiente(cb);
		inbox.conjuntos();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Modulo Conjuntos");

		if (pom.msgModal().contains("No se han configurado reglas de precio para Bara"))
			pom.optionMsgModal("OK");

		addStep("Seleccionar los Conjuntos");
		u.hardWait(2);
		conjunto.seleccionarOpcion("Planogrupos", planogrupos);

		conjunto.buscarSegmentos();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Segmentos", segmentos);

		conjunto.buscarMuebles();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Muebles", muebles);

		conjunto.buscarPlazas();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Plazas", plazas);

		conjunto.buscarConjuntos();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Conjuntos", conjuntos);

		testCase.addScreenShotCurrentStep(u, "Conjuntos seleccionados");

		addStep("Seleccionar Cobertura Objetivo");
		conjunto.buscar();
		conjunto.coberturaObjetivo();
		conjunto.seleccionarObjetivos();
		testCase.addScreenShotCurrentStep(u, "Cobertura Objetivos");

		addStep("Configurar Coberturas");
		conjunto.configurar();
		u.hardWait(3);
		conjunto.porcentaje();
		u.hardWait(1);

		conjunto.guardarConfiguracion();
		u.hardWait(1);

		conjunto.salir();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Coberturas OK");

		/// IR AL MÓDULO FUENTE DE ABASTO ///
		addStep("Ir a Fuente Abasto");
		u.hardWait(3);
		inbox.seleccionarItemPendiente(cb);
		inbox.fuenteAbasto();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Modulo Fuente Abasto");

		/*-----------------------------------------------------------------------------------------*/
		addStep("Seleccionar las n razon social");
		u.hardWait(1);
		fuenteAbasto.buscarFuenteAbasto();
		fuenteAbasto.seleccionarFuentesAbasto(razonSocialFuentesAbasto);
		fuenteAbasto.aceptarFuenteAbasto();

		/*-----------------------------------------------------------------------------------------*/
		addStep("Seleccionar las plazas para cada fuente de abasto");
		u.hardWait(1);
		fuenteAbasto.relacionarPlazas(fuenteAbastoPlazas);
		testCase.addScreenShotCurrentStep(u, "Crear relacion");

		/*-----------------------------------------------------------------------------------------*/

		fuenteAbasto.quitarDuplicidadRelaciones(razonSocialFuentesAbasto);
		u.hardWait(2);

		/*-----------------------------------------------------------------------------------------*/

		addStep("Configuracion Tecnica");
		fuenteAbasto.configTecnica();

		if (pom.msgModal().contains("solo se creo la relacion para un Cedis:"))
			pom.optionMsgModal("OK");

		/*-----------------------------------------------------------------------------------------*/
		for (int i = 0; i < arrayEach.length; i++) {
			pom.logM("Array Each ---> : " + arrayEach[i]);
		}
		testCase.addTextEvidenceCurrentStep("Volumentria");
		fuenteAbasto.vol_each(arrayEach);

		/*-----------------------------------------------------------------------------------------*/
		fuenteAbasto.vol_inner(arrayIner);

		/*-----------------------------------------------------------------------------------------*/
		fuenteAbasto.vol_case(arrayCase);

		/*-----------------------------------------------------------------------------------------*/
		fuenteAbasto.tarima(cama, altura);
		testCase.addScreenShotCurrentStep(u, "Volumetria");

		/*-----------------------------------------------------------------------------------------*/
		testCase.addTextEvidenceCurrentStep("Costo Bruto y Ligar Documentos");
		fuenteAbasto.abastoConfigArticulo(costoBruto);

		/*-----------------------------------------------------------------------------------------*/
		String pathCartaCompro = BASE_PATH + "\\Config\\Input\\Files\\Siv\\CARTA_COMPROMISO_OXXO.docx";
		String pathFichaTec = BASE_PATH + "\\Config\\Input\\Files\\Siv\\FICHA_TECNICA_OXXO.docx";

		fuenteAbasto.subirArchivos(pathCartaCompro, pathFichaTec);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Establecer los Parametros de Indicadores");
		fuenteAbasto.paramIndicadores(objVenta, ventaMinEx);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Calculo De Pronostico de Ventas");
		fuenteAbasto.buscaArticulo(CBPLU);
		fuenteAbasto.calPronostVenta(plaza, porcentaje);

		/// ENVIAR SELECCION ///
		addStep("Enviar Seleccion");
		u.hardWait(3);
		inbox.seleccionarItemPendiente(cb);
		u.hardWait(1);
		inbox.enviarSeleccion();
		u.hardWait(1);

		if (pom.msgModal().contains("El ciclo seleccionado para los siguientes articulos ha expirado"))
			pom.optionMsgModal("ACEPTAR");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// REVISAR ESTATUS ///

		addStep("Validar en la Base de Datos el estatus del Codigo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "SELECT item_rtk, item_transaccion_id, step_label, incidenteultimus_id FROM XXVA_SIV3.xxva_item_incident_ultimus WHERE item_RTK = '"
					+ cb + "' AND ROWNUM <= 1 ORDER BY item_transaccion_id DESC";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontraro el codigo de Barras");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

//////////////APROBACIONES ////////////

		/// LOGUEARSE CON USUARIO CATALOGO ///
		addStep("Iniciar sesion con usuario: " + usuarioCatalogo);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCatalogo);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL CATEGORIA CORRESPONDIENTE ///
		addStep("Seleccionar la Categoria e ir a Altas");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.altasCatalogos();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		addStep("Seleccionar el codigo de barras y abrir Datos Basicos");
		u.hardWait(4);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();

		/// ACEPTAR DATOS BASICOS ///
		addStep("Aceptar los Datos Basicos");
		inbox.aceptarInfoBasicaCatalogos();
		String textModal = pom.msgModal();
		if (textModal.contains("Aceptado exitoso")) {
			pom.optionMsgModal("OK");
			u.hardWait(1);
			pom.waitLoadingModal();
		} else if (textModal.contains("Debe seleccionar")) {
			testCase.addBoldTextEvidenceCurrentStep("FALTA INFORMACION");
			pom.optionMsgModal("OK");
			u.hardWait(1);
			pom.waitLoadingModal();
			datosBasicos.enterMarcaProveedor(marcaProveedor, udaMarcaProveedor);
			datosBasicos.enterMarcaArticulo(marcaArticulo, udaMarcaArticulo);
			inbox.aceptarInfoBasicaCatalogos();
			pom.optionMsgModal("OK");
		}

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		addStep("Seleccionar el codigo de barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoAltas();

		/// ACEPTAR FUENTE DE ABASTO ///
		addStep("Seleccionar todas las fuentes de abasto y aceptar");
		u.hardWait(4);
		inbox.checkTodosFuenteAbastoValidacion(true);
		inbox.aceptarFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.aceptarComentarioFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
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
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		addStep("Validar en la Base de Datos el estatus del Codigo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "SELECT item_rtk, item_transaccion_id, step_label, incidenteultimus_id FROM XXVA_SIV3.xxva_item_incident_ultimus WHERE item_RTK = '"
					+ cb + "' AND ROWNUM <= 1 ORDER BY item_transaccion_id DESC";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontraro el codigo de Barras");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
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
		return "Category: Alta BC Existente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_021_Category_Alta_CBExist_AprobarCatalogos_Planogramador";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		// u.getDriver().quit();
	}

}

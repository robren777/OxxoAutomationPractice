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

public class ATC_025_Admin_Alta_CBNvo_AprobarCategory_AprobarCatalogos_Planogramador extends BaseExecution {
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

	public void ATC_025_Admin_Alta_CBNvo_AprobarCategory_AprobarCatalogos_Planogramador_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Workflows Library: SIV");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioAdmin = data.get("usuarioAdmin");
		String usuarioDFFA = data.get("usuarioDFFA");
		String usuarioCategoria = data.get("usuarioCategoria");
		String usuarioCatalogo = data.get("usuarioCatalogo");

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

		/// INICIALIZACION DE POMS ///
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
		pom.logL("Iniciar sesion en ultimus con usuario Admin");
		addStep("Iniciar sesion con usuario perfil Admin: " + usuarioAdmin);
		ultimus.loginUltimus(usuarioAdmin, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioAdmin);

		/// ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(2);
		pom.waitLoadingModal();
		u.hardWait(2);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// CAMBIAR USUARIO ///
		pom.logL("Cambio de usuario");
		addStep("Cambiar de usuario");
		u.hardWait(2);
		inbox.changeUserAlterno(usuarioDFFA);
		pom.waitLoadingModal();

		/// AGREGAR ITEM ///
		pom.logL("Agregar Item: buscar CB");
		addStep("Agregar Item: buscar por codigo de barras");
		u.hardWait(3);
		inbox.agregarItemPlaza();
		u.hardWait(2);

		String cb;
		Boolean band;

		do {
			band = false;
			/// GENERAR CB ALEATORIO ///
			cb = pom.generacionCB();
			buscar.enterCB(cb);
			buscar.busquedaPredeterminada();
			if (buscar.seEncontraronArticulos())
				band = true;
		} while (band);

		/// CAPTURAR DATOS ///
		pom.logL("Captura de Datos Basicos");
		addStep("Capturar los Datos Basicos para el Item: " + cb);
		buscar.capturarDatos();

		/// INGRESAR DATOS BASICOS ///
		u.hardWait(4);
		datosBasicos.enterDescripcionCorta(descripcionCorta);
		datosBasicos.enterDescripcionLarga(descripcionLarga);

		datosBasicos.selectSuperGrupo(superGrupo);
		datosBasicos.selectCategoria(categoria);
		datosBasicos.selectSubCategoria(subCategoria);
		datosBasicos.selectSegmento(segmento);
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

		pom.logL("Guardar Datos Basicos");
		datosBasicos.siguiente();

		pom.logL("Guardar CB en dataprovider");
		pom.addDataToDataProviderFile(this.getDataProviderPath(), "Sheet1", 6, cb);
		testCase.addScreenShotCurrentStep(u, "CB en inbox");

		/// IR AL MODULO DE CONJUNTOS ///
		pom.logL("Seleccion de item del inbox e ir a conjuntos");
		addStep("Ir a Conjuntos");
		u.hardWait(4);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.conjuntosPlaza();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Modulo Conjuntos");

		if (pom.msgModal().contains("No se han configurado reglas de precio para Bara"))
			pom.optionMsgModal("OK");

		pom.logL("Seleccion de conjuntos");
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

		pom.logL("Seleccion de cobertura objetivo");
		addStep("Seleccionar Cobertura Objetivo");
		conjunto.buscar();
		conjunto.coberturaObjetivo();
		conjunto.seleccionarObjetivos();
		testCase.addScreenShotCurrentStep(u, "Cobertura Objetivos");

		pom.logL("Configuracion de coberturas");
		addStep("Configurar Coberturas");
		conjunto.configurar();
		u.hardWait(3);
		conjunto.porcentaje();
		u.hardWait(1);

		pom.logL("Guardar configuracion de coberturas");
		conjunto.guardarConfiguracion();
		u.hardWait(1);

		conjunto.salir();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Coberturas OK");

		/// IR AL MODULO FUENTE DE ABASTO ///
		pom.logL("Seleccion de item e ir a fuentes de abasto");
		addStep("Ir a Fuente Abasto");
		u.hardWait(3);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.fuenteAbastoPlaza();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Modulo Fuente Abasto");

		pom.logL("Seleccion de las fuentes de abasto");
		addStep("Seleccionar las n razon social");
		u.hardWait(1);
		fuenteAbasto.buscarFuenteAbasto();
		fuenteAbasto.seleccionarFuentesAbasto(razonSocialFuentesAbasto);
		fuenteAbasto.aceptarFuenteAbasto();

		pom.logL("Seleccion de las plazas por cada FA");
		addStep("Seleccionar las plazas para cada fuente de abasto");
		u.hardWait(1);
		fuenteAbasto.relacionarPlazas(fuenteAbastoPlazas);
		testCase.addScreenShotCurrentStep(u, "Crear relacion");

		pom.logL("Revisar si hay relaciones duplicadas");
		fuenteAbasto.quitarDuplicidadRelaciones(razonSocialFuentesAbasto);
		u.hardWait(2);

		pom.logL("Configuracion tecnica");
		addStep("Configuracion Tecnica");
		fuenteAbasto.configTecnica();

		if (pom.msgModal().contains("solo se creo la relacion para un Cedis:"))
			pom.optionMsgModal("OK");

		for (int i = 0; i < arrayEach.length; i++) {
			pom.logM("Array Each ---> : " + arrayEach[i]);
		}
		testCase.addTextEvidenceCurrentStep("Volumentria");
		fuenteAbasto.vol_each(arrayEach);

		fuenteAbasto.vol_inner(arrayIner);

		fuenteAbasto.vol_case(arrayCase);

		fuenteAbasto.tarima(cama, altura);
		testCase.addScreenShotCurrentStep(u, "Volumetria");

		testCase.addTextEvidenceCurrentStep("Costo Bruto y Ligar Documentos");
		fuenteAbasto.abastoConfigArticulo(costoBruto);

		pom.logL("Subir archivos");
		String pathCartaCompro = BASE_PATH + "\\Config\\Input\\Files\\Siv\\CARTA_COMPROMISO_OXXO.docx";
		String pathFichaTec = BASE_PATH + "\\Config\\Input\\Files\\Siv\\FICHA_TECNICA_OXXO.docx";

		fuenteAbasto.subirArchivos(pathCartaCompro, pathFichaTec);

		pom.logL("Parametros indicadores");
		addStep("Establecer los Parametros de Indicadores");
		fuenteAbasto.paramIndicadores(objVenta, ventaMinEx);

		pom.logL("Articulo espejo");
		addStep("Calculo De Pronostico de Ventas");
		fuenteAbasto.buscaArticulo(CBPLU);
		fuenteAbasto.calPronostVenta(plaza, porcentaje);

		/// IR AL MODULO ENVIO MUESTRA FISICA ///
		pom.logL("Seleccion de CB en inbox e ir a muestra fisica");
		addStep("Ir a Envio Muestra Fisica");
		u.hardWait(2);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.envioMuestraFisicaPlaza();
		testCase.addScreenShotCurrentStep(u, "Envio Muestra Fisica");

		addStep("Capturar Envio: " + metodoEnvio);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);
		muestraFisica.capturarNuevoEnvio();

		String pathImage = BASE_PATH + "\\Config\\Input\\Files\\Siv\\Photos\\" + nombreImagen;
		u.hardWait(4);

		switch (metodoEnvio.toUpperCase()) {
		case "ENTREGA PERSONAL":
			pom.logL("Tipo: Entrega personal");
			muestraFisica.entregaPersonal(personaQueEnvia, telefono);
			break;
		case "PAQUETERIA":
			pom.logL("Tipo: Paqueteria");
			muestraFisica.paqueteria(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional, telefono,
					numGuia, fechaEnvio, fechaEsperada);
			break;
		case "SIN MUESTRA FISICA":
			pom.logL("Tipo: Sin muestra fisica");
			muestraFisica.sinMuestraFisica();
			break;
		case "UPLOAD FOTOGRAFIA":
			pom.logL("Tipo: Upload Fotografia");
			muestraFisica.uploadFotografia(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional,
					telefono, numGuia, fechaEnvio, fechaEsperada, pathImage);
			break;
		default:
			throw new AssertionError("La opcion no es valida: " + metodoEnvio);
		}

		pom.logL("Grabar envio");
		u.hardWait(1);
		muestraFisica.grabarEnvio();

		pom.logL("Seleccion de CB en inbox y enviar muestra fisica");
		addStep("Enviar Muestra Fisica");
		u.hardWait(1);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);
		muestraFisica.enviarMuestraFisica();

		muestraFisica.salirMuestraFisica();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB status");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion en ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// ENVIO DE MUESTRA FISICA ///

		/// LOGUEARSE CON USUARIO CATALOGO ///
		pom.logL("Iniciar sesion en ultimus con usuario Catalogo");
		addStep("Iniciar sesion con usuario perfil Catalogo: " + usuarioCatalogo);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCatalogo);

		/// INICIAR Y ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL MENU CATALOGO MUESTRA FISICA ///
		pom.logL("Seleccionar el menu inbox catalogo muestra fisica");
		addStep("Seleccionar el menu: Inbox Catalogo Muestra Fisica");
		u.hardWait(1);
		inbox.menuInboxCatalogoMuestraFisica();

		/// SELECCIONAR LA CATEGORIA CORRESPONDIENTE ///
		pom.logL("Seleccionar la categoria en inbox e ir a muestra fisica");
		addStep("Seleccionar la categoria e ir a Muestra Fisica");
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.muestraFisicaCat();
		u.hardWait(1);

		/// ACEPTAR MUESTRA FISICA ///
		pom.logL("Seleccion de CB y aceptar");
		addStep("Seleccionar el Codigo de Barras y Aceptar");
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.aceptarItemEnCatalogos();

		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB aceptado");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar la ventana de SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion de ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// LOGIN ULTIMUS ///
		pom.logL("Iniciar sesion en ultimus con usuario DFFA");
		addStep("Iniciar sesion con usuario perfil DFFA: " + usuarioDFFA);
		ultimus.loginUltimus(usuarioDFFA, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioDFFA);

		/// INICIAR Y ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL CB EN LA TABLA DE PENDIENTES ///
		pom.logL("Modulos en OK");
		addStep("Validar que los modulos estan en OK");
		u.hardWait(3);
		inbox.seleccionarItemInboxCatalogos(cb);

		/// VERIFICAR COLUMNAS EN OK ///
		inbox.verificarColumnasEnOKPlaza(cb);
		testCase.addTextEvidenceCurrentStep("Los tres modulos estan en OK");

		/// ENVIAR SELECCION ///
		pom.logL("Enviar seleccion");
		addStep("Enviar Seleccion");
		inbox.enviarSeleccion();
		u.hardWait(1);

		if (pom.msgModal().contains("El ciclo seleccionado para los siguientes"))
			pom.optionMsgModal("ACEPTAR");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion en ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// REVISAR ESTATUS ///
		pom.logL("Validar estatus en la base de datos");
		addStep("Validar en la Base de Datos el estatus del Codigo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"
					+ cb + "'";
			pom.logM(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontraro el codigo de Barras");

		} catch (SQLException e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

		/**
		 * --------------------------------------------------------------- *
		 *  						APROBACIONES
		 *  -------------------------------------------------------------- *
		 */
		pom.logL("APROBACIONES");
		testCase.addBoldTextEvidenceCurrentStep("APROBACIONES");
		
		/**
		 * ------------------------- CATEGORIA --------------------------- *
		 */
		/// LOGUEARSE CON USUARIO CATEGORIA ///
		addStep("Iniciar sesion con usuario perfil Categoria: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCategoria);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// MENU INBOX MOVIMIENTOS ORDENDADOS DE CATALOGOS
		addStep("Seleccionar el menu: Inbox Movimientos Ordenados de Categoria");
		u.hardWait(1);
		inbox.menuInboxMovimientosOrdenados();

		/// SELECCIONAR EL CATEGORIA CORRESPONDIENTE ///
		addStep("Seleccionar la Categoria e ir a Altas");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.altasCatalogos();

		/// SELECCIONAR CB EN ALTAS CATEGORIA E IR A DATOS BASICOS///
		addStep("Seleccionar el Codigo de Barras y abrir Datos Basicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();

		/// SELECCIONAR CB EN ALTAS CATEGORIA E IR A DATOS BASICOS///
		addStep("Aceptar Datos Basicos");
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

		/// SELECCIONAR CB EN ALTAS CATEGORIA E IR A CONJUNTOS///
		addStep("Seleccionar el Codigo de Barras e ir a Conjuntos");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.conjuntosPlaza();
		u.hardWait(1);

		addStep("Aceptar Conjuntos");
		conjunto.AceptarAprobacion();

		/// SELECCIONAR CB EN ALTAS CATEGORIA E IR A FUENTE DE ABASTO ///
		addStep("Seleccionar el Codigo de Barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoCatalogos();

		/// ACEPTAR FUENTE DE ABASTO ///
		addStep("Aceptar Fuente de Abasto");
		fuenteAbasto.AceptarAprobacion();
		u.hardWait(1);
		fuenteAbasto.agregarComentario("OK");
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CATEGORIA ///
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

		/**
		 * ----------------------- CATALOGO ----------------------- *
		 */

		/// LOGUEARSE CON USUARIO CATALOGO ///
		addStep("Iniciar sesion con usuario perfil Catalogo: " + usuarioCatalogo);
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
		addStep("Seleccionar el Codigo de Barras y abrir Datos Basicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();

		/// ACEPTAR DATOS BASICOS ///
		addStep("Aceptar Datos Basicos");
		inbox.aceptarInfoBasicaCatalogos();
		textModal = pom.msgModal();
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
		addStep("Seleccionar el Codigo de Barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoAltas();

		addStep("Aceptar Fuente de Abastos");
		u.hardWait(1);
		inbox.checkTodosFuenteAbastoValidacion(true);
		inbox.aceptarFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.aceptarComentarioFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		addStep("Seleccionar CB y vaciar Inbox");
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
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"
					+ cb + "'";
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
		return "Admin: DFFA: Alta de un CB nuevo, el Item queda con estatus Planogramador \n En el dataprovider se puede especificar: Planogramable/No Planogramable, Cedis/Mixto/Directo, Tipo de envío";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_025_Admin_Alta_CBNvo_AprobarCategory_AprobarCatalogos_Planogramador";
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

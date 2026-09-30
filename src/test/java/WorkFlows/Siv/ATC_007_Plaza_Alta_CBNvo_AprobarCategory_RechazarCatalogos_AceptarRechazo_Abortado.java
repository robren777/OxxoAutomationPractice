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

public class ATC_007_Plaza_Alta_CBNvo_AprobarCategory_RechazarCatalogos_AceptarRechazo_Abortado extends BaseExecution {
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

	public void ATC_001_Plaza_Alta_CBNvo_AprobarCategory_AprobarCatalogos_Planogramador_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: SIV");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioPlaza = data.get("usuarioPlaza");
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
		pom.logL("Iniciar sesion ultimus Plaza");
		addStep("Iniciar sesion con usuario perfil Plaza: " + usuarioPlaza);
		ultimus.loginUltimus(usuarioPlaza, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioPlaza);

		/// INICIAR Y ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(2);
		pom.waitLoadingModal();
		u.hardWait(2);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// AGREGAR ITEM ///
		pom.logL("Agregar Item");
		addStep("Agregar item: buscar por codigo de barras");
		inbox.agregarItemPlaza();
		u.hardWait(2);

		String cb;
		Boolean band;

		do {
			band = false;
			pom.logL("Generar CB");
			cb = pom.generacionCB();
			buscar.enterCB(cb);
			buscar.busquedaPredeterminada();
			if (buscar.seEncontraronArticulos()) {
				pom.logM("CB Existente > se genera otro");
				band = true;
			}
		} while (band);

		/// CAPTURAR DATOS ///
		pom.logL("Captura de Datos Basicos");
		addStep("Capturar los Datos Basicos para el item: " + cb);
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
		try {
			datosBasicos.enterGradosAlcohol(gradosAlcohol, udaGradosAlcohol);
		} catch (Exception e) {

		}
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

		pom.logL("Guardar datos basicos");
		datosBasicos.siguiente();

		pom.logL("Insertar CB en dataprovider");
		pom.addDataToDataProviderFile(this.getDataProviderPath(), "Sheet1", 5, cb);
		testCase.addScreenShotCurrentStep(u, "CB en inbox");

		/// IR AL MODULO DE CONJUNTOS ///
		pom.logL("Seleccion de CB en inbox > conjuntos");
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

		pom.logL("Configurar cobertutas");
		addStep("Configurar Coberturas");
		conjunto.configurar();
		u.hardWait(3);
		conjunto.porcentaje();
		u.hardWait(1);

		pom.logL("Guardar configuracion de coberturas");
		conjunto.guardarConfiguracion();
		u.hardWait(1);

		pom.logL("Salir conjuntos");
		conjunto.salir();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Coberturas OK");

		/// IR AL MODULO FUENTE DE ABASTO ///
		pom.logL("Seleccion de CB en inbox > fuente abasto");
		addStep("Ir a Fuente Abasto");
		u.hardWait(3);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.fuenteAbastoPlaza();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Modulo Fuente Abasto");

		pom.logL("Seleccion de las razones sociales");
		addStep("Seleccionar las n razon social");
		u.hardWait(1);
		fuenteAbasto.buscarFuenteAbasto();
		fuenteAbasto.seleccionarFuentesAbasto(razonSocialFuentesAbasto);
		fuenteAbasto.aceptarFuenteAbasto();

		pom.logL("Seleccion de las plazas por cada fuente de abasto");
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

		if (pom.msgModal().contains("para un Cedis:"))
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

		pom.logL("Subir documentos");
		testCase.addTextEvidenceCurrentStep("Costo Bruto y Ligar Documentos");
		fuenteAbasto.abastoConfigArticulo(costoBruto);

		String pathCartaCompro = BASE_PATH + "\\Config\\Input\\Files\\Siv\\CARTA_COMPROMISO_OXXO.docx";
		String pathFichaTec = BASE_PATH + "\\Config\\Input\\Files\\Siv\\FICHA_TECNICA_OXXO.docx";

		fuenteAbasto.subirArchivos(pathCartaCompro, pathFichaTec);

		pom.logL("Parametros indicadores");
		addStep("Establecer los Parametros de Indicadores");
		fuenteAbasto.paramIndicadores(objVenta, ventaMinEx);

		pom.logL("Pronostico de ventas");
		addStep("Calculo De Pronostico de Ventas");
		fuenteAbasto.buscaArticulo(CBPLU);
		fuenteAbasto.calPronostVenta(plaza, porcentaje);

		/// IR AL MODULO ENVIO MUESTRA FISICA ///
		pom.logL("Seleccion de CB en inbox > Envio muestra fisica");
		addStep("Ir a Envio Muestra Fisica");
		u.hardWait(2);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.envioMuestraFisicaPlaza();
		testCase.addScreenShotCurrentStep(u, "Envio Muestra Fisica");

		pom.logL("Capturar envio");
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
			pom.logL("Tipo: Upload fotografia");
			muestraFisica.uploadFotografia(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional,
					telefono, numGuia, fechaEnvio, fechaEsperada, pathImage);
			break;
		default:
			throw new AssertionError("La opcion no es valida: " + metodoEnvio);
		}

		u.hardWait(1);
		pom.logL("Grabar envio");
		muestraFisica.grabarEnvio();

		pom.logL("Seleccion de CB en inbox");
		addStep("Enviar Muestra Fisica");
		u.hardWait(1);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);

		pom.logL("Enviar muestra fisica");
		muestraFisica.enviarMuestraFisica();

		pom.logL("Salir de envio de muestra fisica");
		muestraFisica.salirMuestraFisica();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB status");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion Ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/**
		 * ----------------- ENVIO DE MUESTRA FISICA ----------------- *
		 */

		/// LOGUEARSE CON USUARIO CATALOGO ///
		pom.logL("Iniciar sesion ultimus Catalogo");
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
		pom.logL("Seleccion menu: inbox catalogo Miestra Fisica");
		addStep("Seleccionar el menu: Inbox Catalogo Muestra Fisica");
		u.hardWait(1);
		inbox.menuInboxCatalogoMuestraFisica();

		/// SELECCIONAR LA CATEGORIA CORRESPONDIENTE ///
		pom.logL("Seleccion de categoria en inbox > muestra fisica");
		addStep("Seleccionar la categoria e ir a Muestra Fisica");
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.muestraFisicaCat();
		u.hardWait(1);

		/// ACEPTAR MUESTRA FISICA ///
		pom.logL("Seleccion de CB en inbox > aceptar");
		addStep("Seleccionar el Codigo de Barras y Aceptar");
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.aceptarItemEnCatalogos();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB aceptado");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// LOGIN ULTIMUS ///
		pom.logL("Iniciar sesion ultimus Plaza");
		addStep("Iniciar sesion con usuario perfil Plaza: " + usuarioPlaza);
		ultimus.loginUltimus(usuarioPlaza, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioPlaza);

		/// INICIAR Y ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL CB EN LA TABLA DE PENDIENTES ///
		pom.logL("Seleccion de CB en inbox");
		addStep("Validar que los modulos estan en OK");
		u.hardWait(3);
		inbox.seleccionarItemInboxCatalogos(cb);

		/// VERIFICAR COLUMNAS EN OK ///
		pom.logL("Validar campos en OK");
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
		pom.logL("Cerrar sesion ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// REVISAR ESTATUS ///
		pom.logL("Estatus del item en la base de datos");
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

		/**
		 * ----------------------------------------------------------------------------
		 * * * * * * * * * * * * * * * APROBACIONES * * * * * * * * * * * * * * * * * *
		 * ----------------------------------------------------------------------------
		 */
		testCase.addBoldTextEvidenceCurrentStep("< APROBACIONES >");

		/**
		 * ----------------- CATEGORY ----------------- *
		 */

		/// LOGUEARSE CON USUARIO CATEGORIA ///
		pom.logL("Iniciar sesion ultimus Categoria");
		addStep("Iniciar sesion con usuario perfil Categoria: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCategoria);

		/// INICIAR Y ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// MENU INBOX MOVIMIENTOS ORDENDADOS DE CATALOGOS
		pom.logL("Seleccion en menu: inbox movimientos ordenados de catalogos");
		addStep("Seleccionar el menu: Inbox Movimientos Ordenados de Catalogos");
		u.hardWait(1);
		inbox.menuInboxMovimientosOrdenados();

		/// SELECCIONAR EL CATEGORIA CORRESPONDIENTE ///
		pom.logL("Seleccion de categoria en inbox > Altas");
		addStep("Seleccionar la Categoria e ir a Altas");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.altasCatalogos();

		/// SELECCIONAR CB EN ALTAS CATEGORIA E IR A DATOS BASICOS///
		pom.logL("Seleccion de CB en inbox > Datos Basicos");
		addStep("Seleccionar el Codigo de Barras y abrir Datos Basicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();

		/// ACEPTAR DATOS BASICOS ///
		pom.logL("Aceptar Datos Basicos");
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
		pom.logL("Seleccion de CB en inbox > Conjuntos");
		addStep("Seleccionar el Codigo de Barras e ir a Conjuntos");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.conjuntosPlaza();
		u.hardWait(1);

		pom.logL("Aceptar Conjuntos");
		addStep("Aceptar Conjuntos");
		conjunto.AceptarAprobacion();

		/// SELECCIONAR CB EN ALTAS CATEGORIA E IR A FUENTE DE ABASTO ///
		pom.logL("Seleccion de CB en inbox > Fuente Abasto");
		addStep("Seleccionar el Codigo de Barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoCatalogos();

		/// ACEPTAR FUENTE DE ABASTO ///
		pom.logL("Aceptar FA");
		addStep("Aceptar Fuente Abasto");
		u.hardWait(4);
		inbox.checkTodosFuenteAbastoValidacionPlaza(true);
		fuenteAbasto.AceptarAprobacion();
		u.hardWait(1);
		fuenteAbasto.agregarComentario("OK");
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CATEGORIA ///
		pom.logL("Seleccion CB en inbox > Vaciar Inbox");
		addStep("Seleccionar CB y vaciar Inbox Altas");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.vaciarInboxAltas();
		u.hardWait(2);
		pom.msgEjecucion();
		testCase.addScreenShotCurrentStep(u, "Vaciar Inbox Altas");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion Ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/**
		 * ----------------- CATALOGO ----------------- *
		 */

		/// LOGUEARSE CON USUARIO CATALOGO ///
		pom.logL("Iniciar sesion ultimus catalogos");
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

		/// SELECCIONAR EL CATEGORIA CORRESPONDIENTE ///
		pom.logL("Seleccion de categoria en inbox > Altas");
		addStep("Seleccionar la Categoria e ir a Altas");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.altasCatalogos();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		pom.logL("Seleccion de CB en inbox > DB");
		addStep("Seleccionar el Codigo de Barras y abrir Datos Basicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();

		/// RECHAZAR DATOS BaSICOS ///
		pom.logL("Rechazar DB");
		addStep("Rechazar Datos Basicos");
		inbox.rechazarInfoBasicaCatalogos();
		u.hardWait(1);
		pom.msgRechazar("Datos Basicos", "Error en la descripción");

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		pom.logL("Seleccion de CB en inbox > FA");
		addStep("Seleccionar el Codigo de Barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoAltas();

		/// RECHAZAR FUENTE DE ABASTO ///
		pom.logL("Rechazar FA");
		addStep("Rechazar Fuente de Abasto");
		u.hardWait(3);
		inbox.checkTodosFuenteAbastoValidacion(true);
		inbox.rechazarFuenteAbastoValidacion();
		pom.msgRechazar("FA", "Costos incorrectos");
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		pom.logL("Seleccion de CB en inbox > Vaciar Inbox");
		addStep("Seleccionar CB y Vaciar Inbox");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.vaciarInboxAltas();
		u.hardWait(2);
		pom.msgEjecucion();
		testCase.addScreenShotCurrentStep(u, "Vaciar Inbox Altas");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/**
		 * ----------------- PLAZA ----------------- *
		 */

		/// LOGUEARSE CON USUARIO PLAZA ///
		pom.logL("Iniciar sesion ultimus Plaza");
		addStep("Iniciar sesion con usuario perfil Plaza: " + usuarioPlaza);
		ultimus.loginUltimus(usuarioPlaza, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioPlaza);

		/// INICIAR Y ABRIR SIV ///
		pom.logL("Abrir SIV");
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// MENU INBOX VALIDACION DE CATEGORY
		pom.logL("Seleccionar menu: inbox pendientes de catalogos");
		addStep("Seleccionar el menu: Inbox Pendientes de Catalogos");
		u.hardWait(2);
		inbox.menuInboxPendientesCatalogos();

		/// SELECCIONAR EL CATEGORIA CORRESPONDIENTE ///
		pom.logL("Seleccion de Categoria en inbox > Rechazados");
		addStep("Seleccionar la Categoria e ir a Rechazados");
		u.hardWait(2);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.rechazadosCatalogos();

		/// SELECCIONAR CB E IR A DATOS BASICOS ///
		pom.logL("Seleccion de CB en inbox > DB");
		addStep("Seleccionar el Codigo de Barras e ir a Datos Basicos");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		u.hardWait(2);
		if (pom.msgModal().contains("ya fue configurado")) {
			pom.optionMsgModal("OK");
		}

		/// ACEPTAR RECHAZO DATOS BASICOS ///
		pom.logL("Aceptar Rechazo DB");
		addStep("Aceptar Rechazo Datos Basicos");
		u.hardWait(1);
		datosBasicos.aceptarRechazoValidacion();
		u.hardWait(2);

		/// SELECCIONAR CB E IR A FUENTE ABASTO ///
		pom.logL("Seleccion de CB en inbox > FA");
		addStep("Seleccionar el Codigo de Barras e ir a Fuente de Abastos");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoCatalogos();
		u.hardWait(3);

		/// ACEPTAR RECHAZO FUENTE ABASTOS ///
		pom.logL("Aceptar Rechazo FA");
		addStep("Aceptar Rechazo Fuente de Abasto");
		fuenteAbasto.checkTodosFuenteAbastoValidacionPlaza(true);
		fuenteAbasto.aceptarRechazo();
		u.hardWait(1);
		fuenteAbasto.regresar();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Envio de CB");

		/// CERRAR LA VENTANA DE SIV ///
		pom.logL("Cerrar ventana SIV");
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		pom.logL("Cerrar sesion ultimus");
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		pom.logL("Estatus del item en la base de datos");
		addStep("Validar en la Base de Datos el estatus del Codigo de Barras");
		u.hardWait(1);
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

		pom.logL("FIN FLUJO");
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
		return "Plaza: Alta de CB Nuevo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_001_Plaza_Alta_CBNvo_AprobarCategory_AprobarCatalogos_Planogramador";
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

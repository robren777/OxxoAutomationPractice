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

public class Altas_Admin extends BaseExecution {
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

	public void ATC_Altas_Categoria(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioAdmin = data.get("usuarioAdmin");
		String usuarioDFFA = data.get("usuarioDFFA");
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
		String gradosAlcohol = data.get("gradosAlcohol");
		String udaGradosAlcohol = data.get("udaGradosAlcohol");
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

		// - Muestra F�sica -//
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

		/// INICIALIZACI�N DE POMS ///
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
		addStep("Iniciar sesión con usuario: " + usuarioAdmin);
		ultimus.loginUltimus(usuarioAdmin, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesión en Ultimus_" + usuarioAdmin);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(2);
		pom.waitLoadingModal();
		u.hardWait(2);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// CAMBIAR USUARIO ///
		addStep("Cambiar de usuario");
		u.hardWait(2);
		inbox.changeUserAlterno(usuarioDFFA);
		pom.waitLoadingModal();

		/// AGREGAR ITEM ///
		addStep("Agregar ítem: buscar por código de barras");
		u.hardWait(3);
		inbox.agregarItemPlaza();
		u.hardWait(2);

		String cb;
		Boolean band;

		do {
			band = false;
			/// GENERAR CB ALEATORIO ///
			cb = pom.generacionCB();
			/// BUSCAR EL CB ///
			buscar.enterCB(cb);
			testCase.addScreenShotCurrentStep(u, "CB ingresado");
			buscar.busquedaPredeterminada();
			if (buscar.seEncontraronArticulos())
				band = true;
		} while (band);

		/// CAPTURAR DATOS ///
		addStep("Capturar los Datos Básicos para el ítem: " + cb);
		buscar.capturarDatos();

		/// INGRESAR DATOS B�SICOS ///
		u.hardWait(1);
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
		if (superGrupo.equalsIgnoreCase("Cerveza")) {
			datosBasicos.enterGradosAlcohol(gradosAlcohol, udaGradosAlcohol);
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

		datosBasicos.siguiente();

		pom.addDataToDataProviderFile(this.getDataProviderPath(), "Sheet1", 6, cb);
		testCase.addScreenShotCurrentStep(u, "CB en inbox");

		/// IR AL M�DULO DE CONJUNTOS ///
		addStep("Ir a Conjuntos");
		u.hardWait(4);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.conjuntosPlaza();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Módulo Conjuntos");

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
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Coberturas OK");

		/// IR AL M�DULO FUENTE DE ABASTO ///
		addStep("Ir a Fuente Abasto");
		u.hardWait(3);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.fuenteAbastoPlaza();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Modulo Fuente Abasto");

		/*-----------------------------------------------------------------------------------------*/
		addStep("Seleccionar las n razón social");
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

		addStep("Configuración Técnica");
		fuenteAbasto.configTecnica();

		/*-----------------------------------------------------------------------------------------*/
		for (int i = 0; i < arrayEach.length; i++) {
			pom.logM("Array Each ---> : " + arrayEach[i]);
		}
		testCase.addTextEvidenceCurrentStep("Volumentría");
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
		addStep("Establecer los Parámetros de Indicadores");
		fuenteAbasto.paramIndicadores(objVenta, ventaMinEx);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Cálculo De Pronóstico de Ventas");
		fuenteAbasto.buscaArticulo(CBPLU);
		fuenteAbasto.calPronostVenta(plaza, porcentaje);

		/// IR AL M�DULO ENV�O MUESTRA F�SICA ///
		addStep("Ir a Envío Muestra Física");
		u.hardWait(2);
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.envioMuestraFisicaPlaza();
		testCase.addScreenShotCurrentStep(u, "Envío Muestra Física");

		addStep("Capturar Envío: " + metodoEnvio);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);
		muestraFisica.capturarNuevoEnvio();

		String pathImage = BASE_PATH + "\\Config\\Input\\Files\\Siv\\Photos\\" + nombreImagen;
		u.hardWait(4);

		switch (metodoEnvio.toUpperCase()) {
		case "ENTREGA PERSONAL":
			muestraFisica.entregaPersonal(personaQueEnvia, telefono);
			break;
		case "PAQUETERIA":
			muestraFisica.paqueteria(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional, telefono,
					numGuia, fechaEnvio, fechaEsperada);
			break;
		case "SIN MUESTRA FISICA":
			muestraFisica.sinMuestraFisica();
			break;
		case "UPLOAD FOTOGRAFIA":
			muestraFisica.uploadFotografia(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional,
					telefono, numGuia, fechaEnvio, fechaEsperada, pathImage);
			break;
		default:
			throw new AssertionError("La opción no es válida: " + metodoEnvio);
		}

		u.hardWait(1);
		muestraFisica.grabarEnvio();

		addStep("Enviar Muestra Física");
		u.hardWait(1);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);
		muestraFisica.enviarMuestraFisica();

		muestraFisica.salirMuestraFisica();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB status");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesión en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/**
		 * ENV�O DE MUESTRA F�SICA
		 */

		/// LOGUEARSE CON USUARIO CAT�LOGO ///
		addStep("Iniciar sesión con usuario: " + usuarioCatalogo);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCatalogo);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL MEN� CAT�LOGO MUESTRA F�SICA ///
		addStep("Seleccionar el menú Inbox Catálogo Muestra F�sica");
		u.hardWait(1);
		inbox.menuInboxCatalogoMuestraFisica();

		/// SELECCIONAR LA CATEGOR�A CORRESPONDIENTE ///
		addStep("Seleccionar la categoría e ir a Muestra Física");
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.muestraFisicaCat();
		u.hardWait(1);

		/// ACEPTAR MUESTRA FISICA ///
		addStep("Seleccionar el Código de Barras y Aceptar");
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.aceptarItemEnCatalogos();

		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB aceptado");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesión en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// LOGIN ULTIMUS ///
		addStep("Iniciar sesión con usuario: " + usuarioDFFA);
		ultimus.loginUltimus(usuarioDFFA, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioDFFA);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// SELECCIONAR EL CB EN LA TABLA DE PENDIENTES ///
		addStep("Validar que los módulos están en OK");
		u.hardWait(3);
		inbox.seleccionarItemInboxCatalogos(cb);

		/// VERIFICAR COLUMNAS EN OK ///
		inbox.verificarColumnasEnOKPlaza(cb);
		testCase.addTextEvidenceCurrentStep("Los tres módulos están en OK");

		/// ENVIAR SELECCION ///
		addStep("Envíar Selección");
		inbox.enviarSeleccion();
		u.hardWait(1);

		if (pom.msgModal().contains("El ciclo seleccionado para los siguientes articulos ha expirado"))
			pom.optionMsgModal("ACEPTAR");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesión en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// REVISAR ESTATUS ///

		addStep("Validar en la Base de Datos el estatus del Código de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"
					+ cb + "'";
			pom.logM(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontraró el código de Barras");

		} catch (SQLException e) {
			pom.logL("" + e);
			throw new AssertionError("Falló al ejecutar el query");
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
		// u.getDriver().quit();
	}

}

package WorkFlows.Variedoxxo;

import static org.testng.Assert.assertTrue;

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

public class ATC_FT_007_013_Validar_apartado_Descuentos_en_FA_Creacion_de_articulo_nuevo_en_SIV_Category_y_Catalogo extends BaseExecution {
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

	public void ATC_FT_007_013_Validar_apartado_Descuentos_en_FA_Creacion_de_articulo_nuevo_en_SIV_Category_y_Catalogo_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioCategoria = data.get("usuarioCategoria");
		String usuarioCatalogo = data.get("usuarioCatalogo");
		// - Datos Bosicos -//
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

		// paso 1
		addStep("Ingresar a la siguiente URL:http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Main.aspx");
		
		
		// paso 2
		addStep("Capturar la información de los campos:  User Id: Usuario valido" + usuarioCategoria + "Domain: comercio.fne Y presionar le boton Log On");	
		/// LOGIN ULTIMUS ///
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus " + usuarioCategoria);
		
		// paso 3
		addStep("Ir a la siguiente ruta My Views -> Views -> Iniciar" + 
				"Y seleccionar el proceso SIV Net Nucleo");
		/// INICIAR Y ABRIR SIV ///
		ultimus.openSivNet();
		u.hardWait(2);
		pom.waitLoadingModal();
		u.hardWait(2);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");
		
		
		// paso 4
		addStep("Seleccionar el boton Agregar ITEM");
		/// AGREGAR ITEM ///
		inbox.agregarItem();
		u.hardWait(2);
		
		// paso 5
		addStep("Ingresar un codigo de barras nuevo, en el campo CB PLU y presionar el boton Busqueda Predeterminada");
		
		// paso 6
		addStep("Presionar el boton Capturar Datos Basicos");
		
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
		
		// paso 7
		addStep("Del apartado Identificacion del artículo completar los siguientes campos:" +
				"- Descripción Corta" + 
				"- Descripción ");
		// paso 8
		addStep("Llenar el apartado Estructura de categoria , Propiedades de ventas e Informacion comercial");
		// paso 9
				addStep("Seleccionar en el campo PACK ITEM la opcion NO ");
		
		/// CAPTURAR DATOS ///
		addStep("Capturar los Datos Basicos para el item: " + cb);
		buscar.capturarDatos();

		/// INGRESAR DATOS BASICOS ///
		u.hardWait(10);
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
		if (checkBara.equalsIgnoreCase("NO")) {
			datosBasicos.selectBara(true);
		} else {
			datosBasicos.selectBara(false);
		}

		datosBasicos.enterPresentacion(presentacion, udaPresentacion);
		datosBasicos.enterContenido(contenido);
		datosBasicos.selectUnidadContenido(unidadContenido);

		testCase.addScreenShotCurrentStep(u, "Datos ingresados parte 1");

		datosBasicos.enterBase(base);
		datosBasicos.enterExcluir(excluir);
		datosBasicos.enterPeatonal(peatonal);
		datosBasicos.enterTurOasis(turOasis);
		datosBasicos.enterTurMixtas(mixtas);
		if (checkBara.equalsIgnoreCase("NO")) {
			datosBasicos.enterBara(bara);
		}

		datosBasicos.enterMarcaProveedor(marcaProveedor, udaMarcaProveedor);
		datosBasicos.enterMarcaArticulo(marcaArticulo, udaMarcaArticulo);
		datosBasicos.selectMerma(checkMerma);
		datosBasicos.selectCandado(checkCandadoVenta);

		datosBasicos.enterUdaLibre(udaLibre);
		
		// paso 10
		addStep("Llenar el apartado Marcas descriptivas e Informacion del contenido y Presionar el boton Siguiente");
		
		// paso 11
		addStep("Presionar el boton OK del mensaje informativo");
		datosBasicos.siguiente();

		pom.addDataToDataProviderFile(this.getDataProviderPath(), "Sheet1", 4, cb);
		testCase.addScreenShotCurrentStep(u, "CB en inbox");
		
		// paso 12
		addStep("Seleccionar el registro creado y presionar el boton Conjuntos ");

		/// IR AL MODULO DE CONJUNTOS ///

		u.hardWait(4);
		inbox.seleccionarItemPendiente(cb);
		inbox.conjuntos();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Modulo Conjuntos");

		if (pom.msgModal().contains("No se han configurado reglas de precio para Bara"))
			pom.optionMsgModal("OK");
		
		// paso 13
		addStep("Seleccionar los Planogrupos, segmenos, muebles, plazas y conjuntos del articulo");
		
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
		conjunto.seleccionarOpcion("Regiones", plazas);
		
		// paso 14
		addStep("Hacer clic en el boton Buscar");
		
		conjunto.buscarConjuntos();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Conjuntos", conjuntos);
		testCase.addScreenShotCurrentStep(u, "Conjuntos seleccionados");

		// paso 15
		addStep("Seleccionar el porcentaje de cobertura y hacer clic en el boton Configurar");
		
		addStep("Seleccionar Cobertura Objetivo");
		conjunto.buscar();
		conjunto.coberturaObjetivo();
		conjunto.seleccionarObjetivos();
		testCase.addScreenShotCurrentStep(u, "Cobertura Objetivos");
		
		// paso 16
		addStep("Seleccionar los legos para la cobertura del articulo");
		
		/// Configurar Coberturas ///
		conjunto.configurar();
		u.hardWait(3);
		conjunto.porcentaje();
		u.hardWait(1);
		
		conjunto.guardarConfiguracion();
		u.hardWait(1);
		
		// paso 17
		addStep("Hacer clic en el boton OK");
		if (pom.msgModal().contains("La información se ha guardado Correctamente."))
			pom.optionMsgModal("OK");
		
		// paso 18
		addStep("Hacer clic en el boton Salir");
		conjunto.salir();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Coberturas OK");
		
		// paso 19
		addStep("Seleccionar el registro creado y presionar el boton Fuente Abasto");
		// paso 20
		addStep("Hacer una consulta a la vista  RMS16_V.V_SUPS  en la BD de DAS");

		// paso 21
		addStep("Buscar una Fuente de Abasto Padre con la sig consulta:SELECT * FROM RMS16_V.V_SUPS WHERE SUPPLIER_PARENT IS NULL;");
		
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil DAS_PERF = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DAS_PERF,
					GlobalVariables.DB_USER_DAS_PERF, GlobalVariables.DB_PASSWORD_DAS_PERF);
			String query_ISNull = "SELECT SUPPLIER, SUP_NAME, SUP_NAME_SECONDARY, SUPPLIER_PARENT, CONTACT_NAME, CONTACT_PHONE F"
					+ "ROM RMS16_V.V_SUPS WHERE SUPPLIER_PARENT IS NULL and rownum <= 15";
			System.out.println(query_ISNull);

			queryResult = executeQuery(DAS_PERF, query_ISNull);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false;
			if (queryResult.getRowCount() > 0) 
				validacion = true;
			assertTrue(validacion, "La consulta no muestra resultados");
		

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

		// paso 22
		addStep("Dependiendo la fuente de abasto padre que se seleccione , buscar la FA hija de dicha FA Padre con la sig consulta: SELECT * FROM RMS16_V.V_SUPS WHERE SUPPLIER_PARENT='';");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil DAS_PERF = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DAS_PERF,
					GlobalVariables.DB_USER_DAS_PERF, GlobalVariables.DB_PASSWORD_DAS_PERF);
			String query_SUPPLIER_PARENT = "SELECT SUPPLIER, SUP_NAME, SUP_NAME_SECONDARY, SUPPLIER_PARENT, CONTACT_NAME, CONTACT_PHONE "
					+ "FROM RMS16_V.V_SUPS WHERE SUPPLIER_PARENT='103513'";
			System.out.println(query_SUPPLIER_PARENT);

			queryResult = executeQuery(DAS_PERF, query_SUPPLIER_PARENT);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false;
			if (queryResult.getRowCount() > 0) 
				validacion = true;
			assertTrue(validacion, "La consulta no muestra resultados");
		

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}
		
		
		
		/// IR AL MODULO FUENTE DE ABASTO ///
		u.hardWait(10);
		inbox.seleccionarItemPendiente(cb);
		inbox.fuenteAbasto();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Modulo Fuente Abasto");

		// paso 23
		addStep("Buscar la fuente de abasto por medio de la Razon Social  el ID de la FA Hija y hacer clic en el boton Busqueda Predeterminada, seleccionar la fuente de abasto y hacer clic en el boton Aceptar");

		u.hardWait(1);
		fuenteAbasto.buscarFuenteAbasto();
		fuenteAbasto.seleccionarFuentesAbasto(razonSocialFuentesAbasto);
		fuenteAbasto.aceptarFuenteAbasto();

		// paso 24
		addStep("Seleccionar la fuente de abasto y hacer clic en el boton Configurar informacion tecnica");
		u.hardWait(1);
		fuenteAbasto.relacionarPlazas(fuenteAbastoPlazas);
		testCase.addScreenShotCurrentStep(u, "Crear relacion");

		/*-----------------------------------------------------------------------------------------*/

		addStep("Configuracion Tecnica");
		fuenteAbasto.configTecnica();

		// paso 25
		addStep("Configurar la volumetria");

		// paso 26
		addStep("Agregar el costo bruto unitario");

		// paso 27
		addStep("Validar que en el campo FAP se muestre el ID de FA PADRE de la FA Hija que se selecciono (El ID de la FA Padre es el que se encuentra en el campo SUPPLIER_PARENT en la vista  RMS16_V.V_SUPS de DAS)");

		// paso 28
		addStep("Validar en el campo Descripcion se muestre la descripcion de la FA Padre que se muestra en el campo SUP_NAME de la vista RMS16_V.V_SUPS en DAS");

		// paso 29
		addStep("Validar que el boton de Descuentos Comerciales se muestre NO");
		
		// paso 30
		addStep("Hacer clic en el boton de Descuentos Comerciales");

	// paso 31
		addStep("Validar que se muestren los siguientes datos de la FA Padre:Id Fuente de Abasto Padre");

	// paso 32
		addStep("Validar que se muestre el Descuento por Distribucion % y Descuento en Linea");

	// paso 33
		addStep("Modificar descuento por descuento y hacer clic en el boton Guardar y validar ");
		
		for (int i = 0; i < arrayEach.length; i++) {
			System.out.println("Array Each ---> : " + arrayEach[i]);
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
		fuenteAbasto.abastoConfigArticulo(costoBruto);

		/*-----------------------------------------------------------------------------------------*/
		// paso 34
		addStep("Ligar documentos (Carta Compromiso y Ficha Tecnica)");
		
		// paso 35
		addStep("Hacer clic en el boton Guardar");

		// paso 36
		addStep("Hacer clic en el boton Siguiente");

		// paso 37
		addStep("Configurar la Informacion Comercial FA por Plaza");

		// paso 38
		addStep("Hacer clic en el boton Guardar");

		// paso 39
		addStep(" Hacer clic en el boton Siguiente");

		// paso 40
		addStep("Validar que se haya creado la liga de la fuente de abasto y hacer clic en el boton Salir");

		
		String pathCartaCompro = BASE_PATH + "\\Config\\Input\\Files\\Siv\\CARTA_COMPROMISO_OXXO.docx";
		String pathFichaTec = BASE_PATH + "\\Config\\Input\\Files\\Siv\\FICHA_TECNICA_OXXO.docx";

		fuenteAbasto.subirArchivos(pathCartaCompro, pathFichaTec);

		/*-----------------------------------------------------------------------------------------*/
		fuenteAbasto.paramIndicadores(objVenta, ventaMinEx);

		/*-----------------------------------------------------------------------------------------*/
		fuenteAbasto.buscaArticulo(CBPLU);
		u.hardWait(5);
		fuenteAbasto.calPronostVenta(plaza, porcentaje);
		
		// paso 41
		addStep("Validar en la tabla XXVA_ITEM_SUP_DEALS que se hayan guardado correctamente los descuentos agregados");
		
		
		
		


		// paso 42
			addStep("Seleccionar el registro creado y presionar el boton Envio Muestra Fisica");

		// paso 43
			addStep("Seleccionar el registro y hacer clic en el boton Capturar Nuevo Envio");

		// paso 44
			addStep("Seleccionar un metodo de envio y hacer clic en el boton Grabar Envio");

		// paso 45
			addStep("Seleccionar el registro y hacer clic en el boton Enviar");

		// paso 46
			addStep("Aceptar la Muestra Física con el usuario Catalogos ");

		// paso 47
			addStep("Validar con el usuario Category que se hayan completado todos los modulos del articulo y hacer clic en el boton Enviar Seleccion");

			
		/// IR AL MODULO ENVIO MUESTRA FISICA ///
		addStep("Ir a Envio Muestra Fisica");
		u.hardWait(2);
		inbox.seleccionarItemPendiente(cb);
		inbox.envioMuestraFisica();
		testCase.addScreenShotCurrentStep(u, "Envio Muestra Fisica");

		addStep("Capturar Envio: " + metodoEnvio);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);
		muestraFisica.capturarNuevoEnvio();

		String pathImage = BASE_PATH + "\\Config\\Input\\Files\\Siv\\Photos\\" + nombreImagen;
		u.hardWait(2);

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
			throw new AssertionError("La opcion no es valida: " + metodoEnvio);
		}

		u.hardWait(1);
		muestraFisica.grabarEnvio();

		addStep("Enviar Muestra Fisica");
		u.hardWait(10);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisica(cb);
		muestraFisica.enviarMuestraFisica();

		muestraFisica.salirMuestraFisica();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB status");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");
		
		/**
		 * ENVIO DE MUESTRA FISICA
		 * 
		 */
		
		
		
		// paso 48
		addStep("Iniciar sesion con el usuario Catalogos"+ usuarioCatalogo +" y seleccionar la aplicacion SIV_NET_NUCLEO");
		/// LOGUEARSE CON USUARIO CATALOGO ///
		u.hardWait(10);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCatalogo);

		
		// paso 49
		addStep("Seleccionar la categoria del articulo  y presionar el boton Altas");
		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");
		
		// paso 50
			addStep("Seleccionar el articulo , presionar el boton Datos Basicos  y hacer clic en el boton Aceptar");

		// paso 51
			addStep("Seleccionar el articulo y presionar el boton Fuente Abasto");

		// paso 52
			addStep("Hacer clic en el botón Aceptar");

		// paso 53
			addStep("Agregar comentarios y hacer clic en el boton [Aceptar]");


		// paso 54
			addStep("Validar que se muestre 1 en la opcion A de Fuente de abasto");

		// paso 55
			addStep("Seleccionar el articulo y presionar el boton Vaciar Inbox");


		
		/// SELECCIONAR EL MENU CATALOGO MUESTRA FISICA ///
		u.hardWait(10);
		inbox.menuInboxCatalogoMuestraFisica();

		/// SELECCIONAR LA CATEGORIA CORRESPONDIENTE ///
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.muestraFisicaCat();
		u.hardWait(5);

		/// ACEPTAR MUESTRA FISICA ///
		inbox.seleccionarItemInboxCatalogos(cb);
		inbox.aceptarItemEnCatalogos();

		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB aceptado");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		
	
		
		// paso 56
		addStep("Validar que el Item se encuentre en el paso planogramador SELECT * FROM XXVA_ITEM_INCIDENT_ULTIMUS WHERE ITEM_RTK =;");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil XXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "SELECT ITEM_RTK, ITEM_TRANSACCION_ID, STEP_LABEL, STATUS_STEP_ID, COMENTARIOS, USER_STEP "
					+ "FROM XXVA_ITEM_INCIDENT_ULTIMUS WHERE ITEM_RTK = '"+ cb + "'";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(XXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false;
			if (queryResult.getRowCount() > 0) 
				validacion = true;
			assertTrue(validacion, "No se encontro el codigo de Barras");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Tener Acceso a BD PIEVE con el usuario XXVA_SIV3" + 
				"2.-Tener Acceso a BD DAS" + 
				"3.-Tener Acceso a BD RMS16" + 
				"4.-Tener Acceso a BD RMS10" + 
				"5.-Tener acceso al sistema SIV.NET";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_013_Validar_apartado_Descuentos_en_FA_Creacion_de_articulo_nuevo_en_SIV_Category_y_Catalogo_Test";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernanbdez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_013_Validar_apartado_Descuentos_en_FA_Creacion_de_articulo_nuevo_en_SIV_Category_y_Catalogo_Test";
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

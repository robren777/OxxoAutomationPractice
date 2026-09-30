package EBS.folioDePago2.HU36;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.ConstruccionCSV;
import EBS.util.FolioPagoMVP2.ConstruccionXML;
import EBS.util.FolioPagoMVP2.EBSChrome;
//import EBS.util.FolioPagoMVP2.EBSPaginaInicial;

import EBS.util.FolioPagoMVP2.PortalProveedoresMVP2;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
 * PRERREQUISITOS: -Contar con un registro cargado y procesado correctamente
 * -Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx -Tener usuarios
 * para el portal -Tener Acceso a la BD en la instancia AVEBQA -Tener Acceso al
 * aplicativo de oracle EBS
 *
 * Que el proceso de validaci�n comercial permita registrar facturas con RFC,
 * Folio y Serie iguales siempre y cuando el UUID sea diferente.
 * 
 * @author jesusm3@hexaware.com
 */

public class ATC_FT_044_RFC_FolioSerieIgualesCuandoElUUIDSeaDiferente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_044_RFC_FolioSerieIgualesCuandoElUUIDSeaDiferente_test(HashMap<String, String> data)
			throws Exception {

		// UTILERIAS
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		PortalProveedoresMVP2 paginaInicial = new PortalProveedoresMVP2(u, testCase);
		PortalProveedoresMVP2 paginaInicialWEB = new PortalProveedoresMVP2(u, testCase);
		EBSPaginaInicial paginaInicialEBS = new EBSPaginaInicial(u, testCase);
		String PatternsImg = "EBS_Folio_Pago";
		EBSChrome ebs = new EBSChrome(u, testCase, PatternsImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		ConstruccionXML construccionXML = new ConstruccionXML();
		ConstruccionCSV construccionCSV = new ConstruccionCSV();
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		// Variables
		String server = data.get("server");
		String responsabilidad3 = data.get("responsabilidad3");
		String subCarpeta3 = data.get("subCarpeta3");
		String carpeta3 = data.get("carpeta3");
		String invoices = data.get("invoices");
		String concurrent = data.get("concurrent");

		String proveedor = data.get("proveedor");
		String folio_pago = data.get("folio_pago");
		String importeBruto = data.get("importeBruto");
		String importeNeto = data.get("importeNeto");
		String importeOxxo = "";
		String cuenta = data.get("cuenta");
		String path = data.get("path");
		String url = data.get("url");
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryEBS2_1 = data.get("ebs2_1");
		String queryEBS3 = data.get("ebs3");
		String queryEBS4 = data.get("ebs4");
		String queryEBS5 = data.get("ebs5");
		String queryEBS6 = data.get("ebs6");
		String queryEBS7 = data.get("ebs7");
		String queryEBS8 = data.get("ebs8");
		String pathCSV = data.get("pathCSV");
		String uuidBase = data.get("uuidBase");
		String folioBase = data.get("folioBase");
		String nombreArchivo = "";
		String decimalesExtra = data.get("decimalesExtra");
		String ebsX1 = data.get("ebsX1");
		String uuidUsado = "";
		String serieUsado = "";
		String folioUsado = "";

		// Atributos del XML
		Map<String, String> attributes = construccionXML.obtenerAtributos(path);
		// Declarar variables individuales y asignarles los valores
		String fecha = attributes.get("Fecha");
		String folio = attributes.get("Folio");
		String serie = attributes.get("Serie");
		String rfc = attributes.get("Rfc");
		String importe = attributes.get("Total");
		String tipoDeComprobante = attributes.get("TipoDeComprobante");
		String uuid = attributes.get("UUID");
		String nombreProveedor = "";
		String fechaFactura = "";
		fechaFactura = construccionXML.formatearFechaParaFactura(fecha);
		Boolean flag = false;

		String concurrente1 = data.get("concurrente1");
		String concurrente2_1 = data.get("concurrente2_1");
		String concurrente2_2 = data.get("concurrente2_2");
		String request_id = "";

		// Preparando pre-requisitos
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS2 + " '" + rfc + "'" + queryEBS2_1);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			nombreProveedor = resultadoContenedor.getData(0, 0);
		}
		resultadoContenedor = dbEBS.executeQuery(queryEBS1 + " '" + uuid + "'");
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			importeOxxo = resultadoContenedor.getData(0, 4);
		}

		// Verificaci�n de datos disponibles en base de datos
		System.out.println(
				"/////////////////////////////////////// Verificaci�n de datos disponibles en base de datos /////////////////////////////////////// ");

		// Crear ciclo hasta que encuentre un UUID disponible
		while (flag = true) {
			// Imprime el UUID que se est � probando
			System.out.println("Probando UUID: " + uuidBase);

			// Ejecuta la consulta para verificar si el UUID est � disponible
			resultadoContenedor = dbEBS.executeQuery(queryEBS5 + " '" + uuidBase + "'");

			if (!resultadoContenedor.isEmpty()) {
				// Si el UUID no est � disponible, incrementa el UUID y contin �a el ciclo
				System.out.println("UUID ocupado. Incrementando...");
				System.out.println("///////////////////////////////////////");
				uuidBase = construccionXML.incrementarUUID(uuidBase);
			} else {
				// Si el UUID est � disponible, imprime el UUID encontrado y sale del ciclo
				System.out.println("UUID disponible encontrado: " + uuidBase);
				flag = false;
				break;
			}
		}

		// Crear ciclo hasta que encuentre un folio de pago disponible
		while (flag = true) {
			// Imprime el folio de pago que se est� probando
			System.out.println("Probando folio de pago: " + folio_pago);

			// Ejecuta la consulta para verificar si el folio de pago est� disponible
			resultadoContenedor = dbEBS.executeQuery(queryEBS4 + " '" + folio_pago + "'");

			if (!resultadoContenedor.isEmpty()) {
				// Si el folio no est� disponible, incrementa el folio y contin�a el ciclo
				System.out.println("Folio de pago ocupado. Incrementando...");
				System.out.println("///////////////////////////////////////");
				folio_pago = construccionCSV.incrementar(folio_pago);
			} else {
				// Si el folio de pago est� disponible, imprime el folio de pago encontrado y
				// sale del ciclo
				System.out.println("Folio de pago disponible encontrado: " + folio_pago);
				System.out.println("///////////////////////////////////////");
				flag = false;
				break;
			}
		}

		utils.sql.SQLResult folioDisponible = dbEBS.executeQuery(queryEBS4 + " '" + folio_pago + "'");

		System.out.println("Datos para la factura:");
		System.out.println("///////////////////////////////////////");
		System.out.println("RFC: " + rfc);
		System.out.println("PROVEEDOR: " + nombreProveedor);
		System.out.println("SERIE: " + serie);
		System.out.println("FOLIO: " + folio);
		System.out.println("FOLIO_PAGO_OXXO: " + folio_pago);
		System.out.println("IMPORTE: " + importe);
		System.out.println("IMPORTE_OXXO: " + importeOxxo);
		System.out.println("FECHA_FACTURA: " + fecha);
		System.out.println("VALC_UUID: " + uuidBase);
		System.out.println("TIPO_COMPROBANTE: " + tipoDeComprobante);
		System.out.println("UUIDRELACIONADO: " + "");

		// Entrar a responsabilidad MXOXXO-APXXXXX-Super Usuario > Facturas > Entrar >
		// Facturas
		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);

		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		login.setLanguage();
		// testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();
		paginaInicialEBS.clickOnResponsabilidad2(responsabilidad3);
		u.hardWait(5);
		paginaInicialEBS.clickOnResponsabilidad2(subCarpeta3);
		u.hardWait(5);
		paginaInicialEBS.clickOnResponsabilidad2(carpeta3);
		u.hardWait(5);
		paginaInicialEBS.clickOnResponsabilidad2(invoices);
		ebs.startEBS();

		// Creaci�n de Factura en AP EBS
		p.validarImagen2("archivo");
		u.hardWait(10);
		p.nTAB(5);
		u.hardWait(10);
		// tabulador caso rfc incorrecto
		p.pegarTexto(proveedor);
		u.hardWait(5);
		p.nTAB(2);
		u.hardWait(5);
		p.pegarTexto(fechaFactura);
		p.nTAB(1);
		u.hardWait(5);
		p.pegarTexto(folio_pago);
		u.hardWait(5);
		p.nTAB(2);
		u.hardWait(5);
		p.pegarTexto(importeBruto);
		u.hardWait(5);
		p.clickImagen("2lines");
		u.hardWait(30);
		p.nTAB(1);
		u.hardWait(4);
		p.nTAB(1);
		u.hardWait(4);
		p.pegarTexto(importeNeto);
		u.hardWait(3);
		p.validarImagen("distribuciones3");
		u.hardWait(3);
		p.clickImagen("distribuciones3");
		u.hardWait(15);
		p.nTAB(2);
		u.hardWait(3);
		p.pegarTexto(importeNeto);
		u.hardWait(3);
		p.nTAB(2);
		u.hardWait(3);
		p.pegarTexto(cuenta);
		u.hardWait(3);
		p.clickImagen("saveFactura");
		u.hardWait(10);
		p.clickImagen("closeFactura");
		u.hardWait(5);
		p.clickImagen("calcularImpuesto");
		u.hardWait(5);
		p.clickImagen("acciones1");
		u.hardWait(10);
		p.clickImagen("validarFactura");
		u.hardWait(10);
		p.clickImagen("aceptarFactura");
		u.hardWait(10);
		p.clickImagen("acciones2");
		u.hardWait(10);
		p.clickImagen("crearContabilidad");
		u.hardWait(3);
		p.clickImagen("contabilizacionFinal");
		u.hardWait(10);
		p.clickImagen("aceptarFactura");
		u.hardWait(10);
		p.validarImagen("validacionFactura");
		u.hardWait(15);
		// testCase.addScreenShotCurrentStep("creacionDeFacturaExitosa");
		p.clickImagen("aceptarFacturaCreada");
		u.hardWait(15);
		p.altF4();
		u.hardWait(5);
		p.enter();
		u.hardWait(5);

		resultadoContenedor = dbEBS.executeQuery(queryEBS8 + " '" + folio_pago + "'");
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}

		String plaza = folio_pago.substring(3, 8);
		String validacionComercialPalabra = "Validacion Comercial - Correcta";

		// EBS
		String ebsUrl = data.get("server");
		String responsabilidad = data.get("responsabilidad3");

		String carpetaOtros = data.get("carpetaOtros");
		String validacionComercial = data.get("validacionComercial");
		String reporteEfectivo = data.get("reporteEfectivo");

		// VARIABLES DE EL INFORME DE REQUERIMIENTO DE EFECTIVO
		String unidadOperativa = data.get("unidadOperativa");
		String pagarvencimiento = data.get("pagarvencimiento");
		String fechaPagoDesde = data.get("fechaPagoDesde");
		String fechaPagoHasta = data.get("fechaPagoHasta");

		String incluirFacturasNoValidadas = data.get("incluirFacturasNoValidadas");
		String opcionResumen = data.get("opcionResumen");
		String totalPorProvedor = data.get("totalPorProvedor");

		// -------------------------------------------------------------------------------------------------------------------
		// TABLAS A VALIDAR

		/*
		 * UTILERIAS
		 */

		String tabla0 = "select RFC,FOLIO_PAGO,IMPORTE,FECHA_FACTURA from apps.xxfc_ap_cargacsv_all where folio_pago= "
				+ "'" + folio_pago + "' order by fecha_procesado desc";
		String tabla1 = "select RFC,FOLIO_PAGO_OXXO,IMPORTE from XXFC.XXFC_CFD_RELACION_FACTURAS where folio_pago_oxxo = "
				+ "'" + folio_pago + "' order by creation_date desc";
		String tabla2 = "select VALC_PLAZA,VALC_FOLIOPAGO from apps.xxfc_Cfdavalidar where VALC_FOLIOPAGO  = " + "'"
				+ folio_pago + "' AND VALC_PLAZA = " + "'" + plaza + "'";
		String tabla3 = "select VALN_ESTATUS  from apps.xxfc_cfdavalidar where VALC_FOLIOPAGO = " + "'" + folio_pago
				+ "'";
		String tablaUid = "select VALC_RFC,VALN_FOLIO,VALC_UUID from apps.xxfc_Cfdavalidar where valc_uuid = " + "'"
				+ uuid + "' order by VALD_FECHARECEPCION desc";

		////////////// PASOS DEL CASO DE PRUEBA /////////////////////////
		// PASO 1
		addStep("Buscar un registro con un uuid, folio y serie procesados anteriormente");

		resultadoContenedor = dbEBS.executeQuery(ebsX1);
		testCase.addBoldTextEvidenceCurrentStep("Registro utilizado anteriormente: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			uuidUsado = resultadoContenedor.getData(0, 2);
			serieUsado = resultadoContenedor.getData(0, 3);
			folioUsado = resultadoContenedor.getData(0, 4);
			flag = true;
		}
		// assertTrue(flag, "El registro existe en la BD");
		construccionXML.modificarXMLString2(path, uuidBase, folioUsado, serieUsado);

		System.out.println(
				"/////////////////////////////////////// Final de Verificaci�n de datos disponibles en base de datos /////////////////////////////////////// ");
		attributes = construccionXML.obtenerAtributos(path);
		fecha = attributes.get("Fecha");
		folio = attributes.get("Folio");
		serie = attributes.get("Serie");
		rfc = attributes.get("Rfc");
		importe = attributes.get("Total");
		tipoDeComprobante = attributes.get("TipoDeComprobante");
		uuid = attributes.get("UUID");

		System.out.println("Datos para el .csv:");
		System.out.println("///////////////////////////////////////");
		System.out.println("RFC: " + rfc);
		System.out.println("PROVEEDOR: " + nombreProveedor);
		System.out.println("SERIE: " + serie);
		System.out.println("FOLIO: " + folio);
		System.out.println("FOLIO_PAGO_OXXO: " + folio_pago);
		System.out.println("IMPORTE: " + importe);
		System.out.println("IMPORTE_OXXO: " + importeOxxo);
		System.out.println("FECHA_FACTURA: " + fecha);
		System.out.println("VALC_UUID: " + uuid);
		System.out.println("TIPO_COMPROBANTE: " + tipoDeComprobante);
		System.out.println("UUIDRELACIONADO: " + "");
		fechaFactura = construccionXML.formatearFechaParaFactura(fecha);
		System.out.println("fecha factura: " + fechaFactura);
		nombreArchivo = "CFD" + folio_pago + ".csv";

		System.out.println("path: " + path);
		System.out.println("url: " + url);
		flag = construccionXML.sendXmlPath(path, url);

		// testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		assertTrue(flag, "El registro existe en la BD");
		testCase.addBoldTextEvidenceCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep(" ");

		// Este muestra cfd a validar cuando se acaban de recibir
		resultadoContenedor = dbEBS.executeQuery(queryEBS7 + " '" + uuid + "'");
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}

		testCase.addBoldTextEvidenceCurrentStep("Registro a utilizar: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		assertTrue(flag, "El registro existe en la BD");
		testCase.addBoldTextEvidenceCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep(" ");

		// PASO 2
		addStep("Generar un nuevo archivo de csv usando �nicamente folio y serie del registro encontrado previamente");
		flag = construccionCSV.generarArchivoCsv(rfc, nombreProveedor, serie, folio, folio_pago, importe, importeOxxo,
				fecha, uuid, tipoDeComprobante, "", pathCSV);
		assertTrue(flag, "Se cre� exitosamente el .csv");

		testCase.addBoldTextEvidenceCurrentStep("Folio disponible: " + folio_pago);
		testCase.addQueryEvidenceCurrentStep(folioDisponible);

		testCase.addBoldTextEvidenceCurrentStep("Nombre del archivo csv creado: " + folio_pago + ".csv");

		// Agregar los datos como evidencia
		testCase.addBoldTextEvidenceCurrentStep("Datos para el .csv:");
		testCase.addBoldTextEvidenceCurrentStep("///////////////////////////////////////");
		testCase.addBoldTextEvidenceCurrentStep("RFC: " + rfc);
		testCase.addBoldTextEvidenceCurrentStep("PROVEEDOR: " + nombreProveedor);
		testCase.addBoldTextEvidenceCurrentStep("SERIE: " + serie);
		testCase.addBoldTextEvidenceCurrentStep("FOLIO: " + folio);
		testCase.addBoldTextEvidenceCurrentStep("FOLIO_PAGO_OXXO: " + folio_pago);
		testCase.addBoldTextEvidenceCurrentStep("IMPORTE: " + importe);
		testCase.addBoldTextEvidenceCurrentStep("IMPORTE_OXXO: " + importeOxxo);
		testCase.addBoldTextEvidenceCurrentStep("FECHA_FACTURA: " + fecha);
		testCase.addBoldTextEvidenceCurrentStep("VALC_UUID: " + uuid);
		testCase.addBoldTextEvidenceCurrentStep("TIPO_COMPROBANTE: " + tipoDeComprobante);
		testCase.addBoldTextEvidenceCurrentStep("UUIDRELACIONADO: " + "");

		// Paso 3
		addStep("Ingresar a Control de Acceso: (https://pruebasweb.oxxo.com/AuthFemcoProx )");
		paginaInicialWEB.loginPortalProveedores(GlobalVariables.getPDPQAUsr, GlobalVariables.getPDPQAPwd);
		// PASO 4
		addStep("Ingresar a Gestion de Proveedores");
		testCase.addScreenShotCurrentStep("Gestion de Proveedores");
		u.hardWait(5);
		p.validarImagen2("gestionProveedores");
		u.hardWait(10);
		p.clickImagen("gestionProveedores");
		// u.getDriver().navigate().back();
		u.waitForLoadPage();
		// PASO 5
		addStep("Ingresar a Menu Finanzas");
		paginaInicialWEB.finanzas();
		// PASO 6
		addStep("Ingresar a la Carga de Archivo Relacional");
		paginaInicialWEB.linkAchivoRelacional();
		// PASO 7
		addStep("Adjuntar archivo relacional y presionar el boton  cargar");
		System.out.println("Nombre del archivo: " + nombreArchivo);
		long startTime = System.nanoTime();
		paginaInicialWEB.cargarArchivoRelacional(nombreArchivo, pathCSV);

		u.hardWait(15);

		flag = true;
		while (flag) {
			// Validar si la imagen "ejecutando" est � presente
			flag = p.validarImagenBoolean("procesando");

			if (flag) {
				p.clickImagen("procesando");
				u.hardWait(15); // Esperar 15 segundos
			} else {
				// Si la imagen ya no est � presente, salir del ciclo
				System.out.println("Subida terminada");
			}
		}
		//

		u.hardWait(5);
		testCase.addScreenShotCurrentStep(" ");

		// PASO 8
		addStep("Visualizar que el archivo se haya subido y haya llegado a la interfaz FEMSA_EO17_MX con el query:"
				+ "select * from apps.xxfc_ap_cargacsv_all where folio_pago='Numero de factura' order by fecha_procesado desc");
		SQLResult consulta0 = dbEBS.executeQuery(tabla0);
		String ITEMCONSULTA0 = consulta0.getData(0, "FOLIO_PAGO");

		testCase.addQueryEvidenceCurrentStep(consulta0);
		assertEquals(ITEMCONSULTA0, folio_pago);

		// PASO 9
		addStep("Como el archivo se debera de procesar correctamente, debera de llegar a EBS  verificamos con el query:"
				+ "select * from XXFC.XXFC_CFD_RELACION_FACTURAS where folio_pago_oxxo = 'Numero de factura' order by creation_date desc");
		SQLResult consulta = dbEBS.executeQuery(tabla1);
		String ITEMCONSULTA = consulta.getData(0, "FOLIO_PAGO_OXXO");

		testCase.addQueryEvidenceCurrentStep(consulta);
		assertEquals(ITEMCONSULTA, folio_pago);
		// testCase.addTextEvidenceCurrentStep("validar que los campos VALC_PLAZA y
		// VALC_FOLIOPAGO se actualizaron en la tabla apps.xxfc_Cfdavalidar");

		SQLResult consulta2 = dbEBS.executeQuery(tabla2);
		String ITEMCONSULTA2 = consulta2.getData(0, "VALC_FOLIOPAGO");

		// testCase.addQueryEvidenceCurrentStep(consulta2);
		assertEquals(ITEMCONSULTA2, folio_pago);

		// addStep("Verificar que el archivo se proceso correctamente");
		paginaInicialWEB.clickActualizarArchivo();
		assertTrue(paginaInicial.validarMesajeCorrectoArchivo(), "Pantalla validada");

		// Detener el contador de tiempo
		long endTime = System.nanoTime();

		// Calcular el tiempo transcurrido en milisegundos
		double elapsedTimeMillis = (endTime - startTime) / 1_000_000.0;

		// Convertir el tiempo a minutos y segundos
		long minutes = (long) (elapsedTimeMillis / 60000); // Dividir por 60000 para obtener minutos
		double seconds = (elapsedTimeMillis % 60000) / 1000.0; // Obtener el resto y convertir a segundos

		// Formatear el tiempo
		String formattedTime = String.format("%d minuto%s y %.3f segundo%s", minutes, (minutes != 1 ? "s" : ""),
				seconds, (seconds != 1.0 ? "s" : ""));

		// Imprimir y guardar el tiempo en una variable
		System.out.println("Tiempo total de procesamiento: " + formattedTime);
		String tiempoProcesamiento = formattedTime;
		u.hardWait(6);
		testCase.addScreenShotCurrentStep("Pantalla validada correctamente");
		// testCase.addBoldTextEvidenceCurrentStep("Tiempo de procesamiento de archivo
		// csv: " + tiempoProcesamiento);

		// Tiempo del concurrente
		resultadoContenedor = dbEBS.executeQuery(concurrente1 + "'" + folio_pago + "'");
		if (!resultadoContenedor.isEmpty()) {
			request_id = resultadoContenedor.getData(0, 0);
			flag = true;
		}
		// testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		// Este muestra cfd a validar cuando se acaban de recibir
		testCase.addBoldTextEvidenceCurrentStep(concurrente2_1 + request_id + concurrente2_2);
		System.out.println("Query armado: ");
		System.out.println(concurrente2_1 + "'" + request_id + "'" + concurrente2_2);
		resultadoContenedor = dbEBS.executeQuery(concurrente2_1 + "'" + request_id + "'" + concurrente2_2);

		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		testCase.addBoldTextEvidenceCurrentStep("Tiempo de procesamiento de XXFC-Validaciones Folio de Pago 2.0");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		u.hardWait(6);

		// addStep("Ingresar a EBS QA Iniciar session Click en conectese");
		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		login.setLanguage();
		// testCase.addScreenShotCurrentStep("Ingreso de credenciales 2");
		login.clickLogIn();

		// PASO 10
		addStep("Ingresar a la responsabilidad: MXOXXO-APXXXXX-Super-Usuario Ingresar a la carpeta: Otros Click en el enlace: Concurrent");
		// paginaInicialEBS.clickOnResponsabilidad(responsabilidad);
		paginaInicialEBS.clickOnResponsabilidad(carpetaOtros);
		paginaInicialEBS.clickOnResponsabilidad(concurrent);
		ebs.startEBS();
		p.validarImagen2("archivo");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		ebs.clickEjecutarNuevaSolicitud();

		// PASO 11
		addStep("Ingresar: XXFC - CFD Validacion Comercial, Click en ejecutar y en visualizar que haya terminado");
		ebs.enterText(validacionComercial);
		testCase.addScreenShotCurrentStep("Validacion Comercial 1");
		u.hardWait(5);
		ebs.clickEjecutar();
		ebs.nEnter(1);
		startTime = System.nanoTime();
		u.hardWait(12);
		ebs.clickNo();
		testCase.addScreenShotCurrentStep("Validacion Comercial 2");
		ebs.clickEncontrar();
		testCase.addScreenShotCurrentStep("Validacion Comercial 3");

		u.quit();
		u.hardWait(12);
		p.clickImagen("encontrar_solicitudes");
		u.hardWait(15);
		p.clickImagen("solicitud_especifica");
		u.hardWait(5);
		p.clickImagen("nombre_concurrente");
		u.hardWait(5);
		p.pegarTexto(validacionComercial);
		u.hardWait(5);
		p.clickImagen("encontrar_concurrente");
		u.hardWait(15);

		flag = true;
		while (flag) {
			// Validar si la imagen "ejecutando" est � presente
			flag = p.validarImagenBoolean("normal");

			if (flag) {
				// Si la imagen sigue presente, realizar clic en "refresh" y esperar
				p.clickImagen("refresh");
				u.hardWait(15); // Esperar 15 segundos
			} else {
				// Si la imagen ya no est � presente, salir del ciclo
				System.out.println("Concurrente terminado");
				// Detener el contador de tiempo
				endTime = System.nanoTime();

				// Calcular el tiempo transcurrido en milisegundos
				elapsedTimeMillis = (endTime - startTime) / 1_000_000.0;

				// Convertir el tiempo a minutos y segundos
				minutes = (long) (elapsedTimeMillis / 60000); // Dividir por 60000 para obtener minutos
				seconds = (elapsedTimeMillis % 60000) / 1000.0; // Obtener el resto y convertir a segundos

				// Formatear el tiempo
				formattedTime = String.format("%d minuto%s y %.3f segundo%s", minutes, (minutes != 1 ? "s" : ""),
						seconds, (seconds != 1.0 ? "s" : ""));
			}
		}
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(5);

		// PASO 12
		addStep("Ingresar a la base de datos  AVEBQA consultar: select VALN_STATUS  from aapps.xxfc_cfdavalidar where VALC_FOLIOPAGO=''");
		SQLResult consulta3 = dbEBS.executeQuery(tabla3);
		String ITEMCONSULTA3 = consulta3.getData(0, "VALN_ESTATUS");
		testCase.addQueryEvidenceCurrentStep(consulta3);
		assertEquals(ITEMCONSULTA3, "1");
		u.hardWait(5);

		// PASO 13
		addStep("Ingresar: XXFC-Informe de Requerimientos de Efectivo Detallado Click en ejecutar Click en visualizar Output");
		ebs.vers();
		u.hardWait(15);
		ebs.clickEjecutarNuevaSolicitud();
		u.hardWait(15);
		ebs.enterText(reporteEfectivo);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(5);
		ebs.clickEjecutar();
		u.hardWait(7);
		paginaInicialEBS.clickOnResponsabilidad(responsabilidad);

		p.validarImagen2("parametros");

		ebs.camposReporteEfectivo(unidadOperativa, pagarvencimiento, fechaPagoDesde, fechaPagoHasta, fechaFactura,
				incluirFacturasNoValidadas, opcionResumen, totalPorProvedor);
		u.hardWait(7);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(7);
		ebs.clickAceptarParametros();
		u.hardWait(18);
		ebs.clickEjecutar();
		u.hardWait(10);
		ebs.clickNo();
		u.hardWait(10);
		// testCase.addScreenShotCurrentStep(" ");
		ebs.clickEncontrar();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		while (ebs.validatePattern("estadoNormalVerde") || ebs.validatePattern("estadoStandbyVerde")) {
			u.hardWait(15);
			if (ebs.validatePattern("ocRefrescarDatos")) {
				ebs.clickPattern("ocRefrescarDatos", 10);
			}
		}

		assertTrue(ebs.validatePattern("faseFinalizadoPrimeraFila") && ebs.validatePattern("estadoNormalPrimeraFila"));
		// testCase.addScreenShotCurrentStep(" ");
		u.hardWait(5);
		// testCase.addScreenShotCurrentStep(" ");
		System.out.println("Antes de ver output");
		ebs.clickVisualizarOutput();
		System.out.println("Despu�s de ver output");
		System.out.println("Abriendo excel...");
		// u.hardWait(10);
		p.validarImagen2("advertencia");
		p.doubleClickImagen("advertencia");
		u.hardWait(5);
		p.validarImagen2("insecureFile");
		u.hardWait(1);
		p.clickImagen("insecureFile");
		System.out.println("Excel descargado");
		u.hardWait(40);
		System.out.println("Esperamos 40 segundos, ejecutamos el archivo descargado");
		paginaInicialEBS.AbrirUltimoArchivoDescargadoExcel();
		System.out.println("Excel abierto");

		ebs.AbrirBuscarFolioExcel(folio_pago);
		testCase.addScreenShotCurrentStep(" ");
		System.out.println("mnt1");
		ebs.altF4();
		testCase.addScreenShotCurrentStep("Validacion final");
		assertTrue(paginaInicialEBS.ValidacionComercialFolio(folio_pago, validacionComercialPalabra));
		System.out.println("mnt2");
		ebs.altF4();
		System.out.println("mnt3");
		u.hardWait(3);
		p.clickImagen("closeChrome");
		System.out.println("mnt4");
		u.hardWait(3);
		p.altF4();
		System.out.println("mnt5");
		u.hardWait(5);
		p.enter();
		System.out.println("mnt6");
		u.hardWait(5);
		p.clickImagen("aceptarCerrar2");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_044_RFC_FolioSerieIgualesCuandoElUUIDSeaDiferente_test";
	}

	@Override
	public String setTestDescription() {
		return "Que el proceso de validaci�n comercial permita registrar facturas con RFC, "
				+ "Folio y Serie iguales siempre y cuando el UUID sea diferente.";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con un registro cargado y procesado correctamente\r\n"
				+ "Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx \r\n" + "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n" + "Tener Acceso al aplicativo de oracle EBS";
	}

	@Override
	public void beforeTest() {

	}

}
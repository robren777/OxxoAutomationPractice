package EBS.folioDePago2.HU21;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.PatternUtil;
import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.ClaseLogin;
import EBS.util.FolioPagoMVP2.ConstruccionCSV;
import EBS.util.FolioPagoMVP2.ConstruccionXML;
import EBS.util.FolioPagoMVP2.EBSPaginaInicial;
import EBS.util.FolioPagoMVP2.PortalProveedoresMVP2;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
 * 
 * DESCRIPCION:
 * Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  en el campo serie contenga un registro de 25 caracteres exactos y este se procese de manera exitosa.
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_007_GenerarCargaArchivoCampoSerieTenga25CaracteresExactos
 * 
 * PRERREQUISITOS: 
 * Solicitar a Diverza que registre en su backend un numero de Serie que contenga 25 caracteres  exactos y nos confirme su carga pasando el UUID.
 * Tener Acceso a https://portalespublicosqa.oxxo.com/AccesssControl/pages/login_form.jsf
 * Tener usuarios para el portal
 * Tener Acceso a la BD en la instancia AVEBQA
 * Tener Acceso al aplicativo de Oracle EBS";
 *
 * INSUMOS
 * Factura contabilizada
 * CSV con informacion de la factura
 * 
 * @author ricardor4@hexaware.com
 * @date 10/Marzo/2025
 */
public class ATC_FT_007_GenerarCargaArchivoCampoSerieTenga25CaracteresExactos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_GenerarCargaArchivoCampoSerieTenga25CaracteresExactos_test(HashMap<String, String> data) throws Exception {


		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil uinsumos = new SeleniumUtil(new IETest(), true);
		PortalProveedoresMVP2 paginaInicial = new PortalProveedoresMVP2(uinsumos, testCase);
		EBSPaginaInicial paginaInicialEBS = new EBSPaginaInicial(uinsumos, testCase);
		MetodosSikuli p = new MetodosSikuli(sk, uinsumos, pathimg, ListImage, ImgPatter, patterns, Fol);
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
		String pathCSV = data.get("pathCSV");
		String uuidBase = data.get("uuidBase");
		String folioBase = data.get("folioBase");

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
		Boolean flag = false;
		

		// PASO 1
		addStep("Validar que se muestre el dato de serie con 25 caracteres exactos del UUID con el query:"
				+ " select * from apps.xxfc_cfdavalidar where valc_uuid IN ('Numero de UUID de xml')");
		
		
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

		
		//Verificaci�n de datos disponibles en base de datos
		System.out.println("/////////////////////////////////////// Verificaci�n de datos disponibles en base de datos /////////////////////////////////////// "); 
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
		
		// Crear ciclo hasta que encuentre un folio disponible
		while (flag = true) {
		    // Imprime el folio que se est� probando
		    System.out.println("Probando folio: " + folioBase);

		    // Ejecuta la consulta para verificar si el folio est� disponible
		    resultadoContenedor = dbEBS.executeQuery(queryEBS6 + " '" + folioBase + "'");

		    if (!resultadoContenedor.isEmpty()) {
		        // Si el folio no est� disponible, incrementa el folio y contin�a el ciclo
		        System.out.println("Folio ocupado. Incrementando...");
		        System.out.println("///////////////////////////////////////");
		        folioBase = construccionXML.incrementarFolio(folioBase);
		    } else {
		        // Si el folio est� disponible, imprime el folio encontrado y sale del ciclo
		        System.out.println("Folio disponible encontrado: " + folioBase);
		        System.out.println("///////////////////////////////////////");
		        flag = false;
		        break;
		    }
		}

		construccionXML.modificarXMLString(path, uuidBase, folioBase);
		
		// Crear ciclo hasta que encuentre un folio de pago disponible
		while (flag = true) {
			// Imprime el folio de pago que se est � probando
			System.out.println("Probando folio de pago: " + folio_pago);

			// Ejecuta la consulta para verificar si el folio de pago est� disponible
			resultadoContenedor = dbEBS.executeQuery(queryEBS4 + " '" + folio_pago + "'");

			if (!resultadoContenedor.isEmpty()) {
				// Si el folio no est � disponible, incrementa el folio y contin �a el ciclo
				System.out.println("Folio de pago ocupado. Incrementando...");
				System.out.println("///////////////////////////////////////");
				folio_pago = construccionCSV.incrementar(folio_pago);
			} else {
				// Si el folio de pago est� disponible, imprime el folio de pago encontrado y sale del ciclo
				System.out.println("Folio de pago disponible encontrado: " + folio_pago);
				System.out.println("///////////////////////////////////////");
				flag = false;
				break;
			}
		}
		
		System.out.println("/////////////////////////////////////// Final de Verificacion de datos disponibles en base de datos /////////////////////////////////////// "); 

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

		// Ejemplo de uso
		flag = construccionCSV.generarArchivoCsv(rfc, nombreProveedor, serie, folio, folio_pago, importe, importeOxxo,
				fecha, uuid, tipoDeComprobante, "", pathCSV);
		assertTrue(flag, "Se cre� exitosamente el .csv");
		
		System.out.println("path: "+path);
		System.out.println("url: "+url);
		flag = construccionXML.sendXmlPath(path, url);
		

		// Entrar a responsabilidad MXOXXO-APXXXXX-Super Usuario > Facturas > Entrar >
		// Facturas
		EBSLogin login = new EBSLogin(uinsumos, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);
		
		
		login.navigate(server);
		login.setCredentials();
		uinsumos.hardWait(5);
		login.setLanguage();
		login.clickLogIn();
		paginaInicialEBS.clickOnResponsabilidad(responsabilidad3);
		uinsumos.hardWait(5);
		paginaInicialEBS.clickOnResponsabilidad(subCarpeta3);
		uinsumos.hardWait(5);
		paginaInicialEBS.clickOnResponsabilidad(carpeta3);
		uinsumos.hardWait(5);
		paginaInicialEBS.clickOnResponsabilidad(invoices);
		uinsumos.hardWait(10);
		uinsumos.hardWait(10);
		p.accepApp();
		uinsumos.hardWait(80);

		// Creaci�n de Factura en AP EBS
		p.nTAB(5);
		uinsumos.hardWait(10);
		//tabulador caso rfc incorrecto
		p.pegarTexto(proveedor);
		uinsumos.hardWait(5);
		p.nTAB(2);
		uinsumos.hardWait(5);
		p.pegarTexto(fechaFactura);
		p.nTAB(1);
		uinsumos.hardWait(5);
		
		// verificar que no existe folio de pago
		testCase.addBoldTextEvidenceCurrentStep("verificar que el folio pago no existe en la base de datos");
		String tablafolio = "select invoice_num from apps.ap_invoices_all where invoice_num = "+"'"+folio_pago+"'";
		SQLResult consultafolio = dbEBS.executeQuery(tablafolio);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consultafolio);
		
		
		p.pegarTexto(folio_pago);
		uinsumos.hardWait(5);
		p.nTAB(2);
		uinsumos.hardWait(5);
		p.pegarTexto(importeBruto);
		uinsumos.hardWait(5);
		p.clickImagen("2lines");
		uinsumos.hardWait(10);
		p.nTAB(2);
		p.pegarTexto(importeNeto);
		uinsumos.hardWait(3);
		p.clickImagen("distribuciones");
		uinsumos.hardWait(15);
		p.nTAB(2);
		uinsumos.hardWait(3);
		p.pegarTexto(importeNeto);
		uinsumos.hardWait(3);
		p.nTAB(2);
		uinsumos.hardWait(3);
		p.pegarTexto(cuenta);
		uinsumos.hardWait(3);
		p.clickImagen("saveFactura");
		uinsumos.hardWait(10);
		p.clickImagen("closeFactura");
		uinsumos.hardWait(5);
		p.clickImagen("calcularImpuesto");
		uinsumos.hardWait(5);
		p.clickImagen("acciones1");
		uinsumos.hardWait(10);
		p.clickImagen("validarFactura");
		uinsumos.hardWait(10);
		p.clickImagen("aceptarFactura");
		uinsumos.hardWait(10);
		p.clickImagen("acciones2");
		uinsumos.hardWait(10);
		p.clickImagen("crearContabilidad");
		uinsumos.hardWait(3);
		p.clickImagen("contabilizacionFinal");
		uinsumos.hardWait(10);
		p.clickImagen("aceptarFactura");
		uinsumos.hardWait(10);
		p.validarImagen("validacionFactura");
		uinsumos.hardWait(3);
		testCase.addScreenShotCurrentStep("creacionDeFacturaExitosa");
		p.clickImagen("aceptarFacturaCreada");
		uinsumos.hardWait(5);
		p.altF4();
		uinsumos.hardWait(5);
		p.enter();

		
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		/*
		 * VARIABLES
		 */
		
		String usuario = GlobalVariables.USUARIOPORTALPROVEEDORES;
		String contrasena = GlobalVariables.CONTRASENAPORTALPROVEEDORES;
		
		
		String nombreArchivo =  "CFD" + folio_pago + ".csv";
		String plaza = folio_pago.substring(3, 8);
		String validacionComercialPalabra = "Validacion Comercial - Correcta";
		
		// EBS
		String ebsUrl = data.get("server");
		String ebsUser = GlobalVariables.getRicardoHexEbsUsr;
		String ebsPass = GlobalVariables.getRicardoHexEbsPwd;
		
		
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
		
		 //-------------------------------------------------------------------------------------------------------------------
		//TABLAS A VALIDAR 
		
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PortalProveedoresMVP2 paginaInicialWEB = new PortalProveedoresMVP2(u, testCase);
		uinsumos.getDriver().quit();
		String tabla0 =  "select RFC,FOLIO_PAGO,IMPORTE,FECHA_FACTURA from apps.xxfc_ap_cargacsv_all where folio_pago= "+"'"+folio_pago+"' order by fecha_procesado desc";
		String tabla1 = "select RFC,FOLIO_PAGO_OXXO,IMPORTE from XXFC.XXFC_CFD_RELACION_FACTURAS where folio_pago_oxxo = "+"'"+folio_pago+"' order by creation_date desc";
		String tabla2 = "select VALC_PLAZA,VALC_FOLIOPAGO from apps.xxfc_Cfdavalidar where VALC_FOLIOPAGO  = "+"'"+folio_pago+"' AND VALC_PLAZA = "+"'"+plaza+"'";
		String tabla3 = "select VALN_ESTATUS  from apps.xxfc_cfdavalidar where VALC_FOLIOPAGO = "+"'"+folio_pago+"'";
		String tablaUid = "select VALC_SERIE,VALC_RFC,VALN_FOLIO,VALC_UUID from apps.xxfc_Cfdavalidar where valc_uuid = "+"'"+uuid+"' order by VALD_FECHARECEPCION desc";

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		SQLResult consultauid = dbEBS.executeQuery(tablaUid);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consultauid);
		assertNotNull(consultauid, "Error: El resultado de la consulta a la base de datos es nulo.");
		// PASO 2
		addStep("Generar el archivo de csv que contenga 25 caracteres exactos en el campo serie");
		assert nombreArchivo != null && !nombreArchivo.isEmpty() : "Error: El nombre del archivo CSV esta vacío o es nulo.";
		testCase.addBoldTextEvidenceCurrentStep("Nombre del archivo csv creado: " +nombreArchivo);
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
		// PASO 3
		addStep("Ingresar a Access Control: (https://pruebasweb.oxxo.com/AuthFemcoProx)");
		paginaInicialWEB.loginPortalProveedores(usuario, contrasena);
		assertNotNull(tabla1, "Error: El resultado de la consulta a la base de datos es nulo.");
		System.out.println(tabla1);
		// PASO 4
		addStep("Ingresar a Gestion de  Proveedores");
		boolean isButtonPresent = paginaInicialWEB.gestionDeProveedores();
		assertTrue(isButtonPresent, "No se encontro xphat");
		u.waitForLoadPage();
		// PASO 5
		addStep("Ingresar a Menu Finanzas");
		boolean isButtonPresent2 = paginaInicialWEB.finanzas();
		assertTrue(isButtonPresent2, "No se encontro xphat");
		// PASO 6
		addStep("Ingresar a la Carga de Archivo Relacional");
		boolean isButtonPresent3 = paginaInicialWEB.linkAchivoRelacional();
		assertTrue(isButtonPresent3, "No se encontro xphat");
		// PASO 7
		addStep("Adjuntar archivo relacional y presionar el boton  cargar");
		testCase.addBoldTextEvidenceCurrentStep("tiempo de carga de archivo " + paginaInicialWEB.subirArchivoRelacional(nombreArchivo,pathCSV) +" Segundos");
	
		String tabla1tiempo = "SELECT REQUEST_ID,FOLIO,FOLIO_PAGO,IMPORTE,FECHA_PROCESADO FROM apps.xxfc_ap_cargacsv_all where folio_Pago = "+"'"+folio_pago+"' order by fecha_procesado desc";
		String tabla0tiempo =  "select request_id FROM apps.xxfc_ap_cargacsv_all where folio_Pago = "+"'"+folio_pago+"' order by fecha_procesado desc";
		SQLResult consulta0tiempo = dbEBS.executeQuery(tabla0tiempo);
		String request_idtiempo = consulta0tiempo.getData(0, "REQUEST_ID");
		String tabla3tiempo = "SELECT " +
	               "    fcpt.user_concurrent_program_name AS Concurrente, " +
	               "    to_char(fcr.actual_start_date, 'dd/mm/yy HH24:MI:SS') AS Inicio, " +
	               "    to_char(fcr.actual_completion_date, 'dd/mm/yy HH24:MI:SS') AS Fin, " +
	               "    round(((fcr.actual_completion_date - fcr.actual_start_date) * 24 * 60), 2) AS Minutos, " +
	               "    round((((fcr.actual_completion_date - fcr.actual_start_date) * 24 * 60) / 60), 2) AS Horas, " +
	               "    fcr.completion_text AS Status " +
	               "FROM " +
	               "    apps.fnd_concurrent_requests fcr, " +
	               "    apps.fnd_concurrent_programs_tl fcpt, " +
	               "    apps.fnd_user fu " +
	               "WHERE " +
	               "    fcr.concurrent_program_id = fcpt.concurrent_program_id " +
	               "    AND fcr.requested_by = fu.user_id " +
	               "    AND fcpt.language = 'ESA' " +
	               "    AND request_id = "+"'"+request_idtiempo+"'" + 
	               " ORDER BY request_id DESC";
		
		testCase.addBoldTextEvidenceCurrentStep("Informacion sobre el archivo procesado");
		SQLResult consultapricipal = dbEBS.executeQuery(tabla1tiempo);
		testCase.addQueryEvidenceCurrentStep(consultapricipal,false);
	
		
		testCase.addBoldTextEvidenceCurrentStep("Informacion de tiempo de concurrente");
		SQLResult consultasecundaria = dbEBS.executeQuery(tabla3tiempo);
		testCase.addQueryEvidenceCurrentStep(consultasecundaria,false);
		assertNotNull(consultasecundaria, "Error: El resultado de la consulta a la base de datos es nulo.");
		
		// PASO 8
		addStep("Visualizar que el archivo se haya subido y haya llegado a la interfaz FEMSA_EO17_MX con el query:"
				+ "select * from apps.xxfc_ap_cargacsv_all where folio_pago='Numero de factura' order by fecha_procesado desc");
		SQLResult consulta0 = dbEBS.executeQuery(tabla0);
		String ITEMCONSULTA0 = consulta0.getData(0, "FOLIO_PAGO");
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta0);
		assertEquals(ITEMCONSULTA0,folio_pago);
	
		
		// PASO 9
		addStep("Como el archivo se debera de procesar correctamente, debera de llegar a EBS  verificamos con el query:"
				+ "select * from XXFC.XXFC_CFD_RELACION_FACTURAS where folio_pago_oxxo = 'Numero de factura' order by creation_date desc");
		SQLResult consulta = dbEBS.executeQuery(tabla1);
		String ITEMCONSULTA = consulta.getData(0, "FOLIO_PAGO_OXXO");
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta);
		assertEquals(ITEMCONSULTA,folio_pago);
		testCase.addTextEvidenceCurrentStep("validar que los campos VALC_PLAZA y VALC_FOLIOPAGO se actualizaron en la tabla apps.xxfc_Cfdavalidar");		
		
		
		SQLResult consulta2 = dbEBS.executeQuery(tabla2);
		String ITEMCONSULTA2 = consulta2.getData(0, "VALC_FOLIOPAGO");
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta2);
		assertEquals(ITEMCONSULTA2,folio_pago);
		
		//PASO 10
		addStep("Verificar que el archivo se proceso correctamente");
		paginaInicialWEB.clickActualizarArchivo();
		assertTrue(paginaInicial.validarMesajeCorrectoArchivo(), "Pantalla validada");
		u.hardWait(6);
		
		
		// PASO 11 
		addStep("Ingresar a EBS QA Iniciar session Click en conectese");
		SeleniumUtil uebs = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(uebs, testCase, ebsUrl, ebsUser, ebsPass);
		EBSPaginaInicial paginaInicialebs = new EBSPaginaInicial(uebs, testCase);
		PatternUtil patutil = new PatternUtil(sk, uebs, pathimg, ListImage, ImgPatter, patterns, Fol);
		inicio.login();
		u.quit();
		
		// PASO 12 
		addStep("Ingresar a la responsabilidad: MXOXXO-APXXXXX-Super-Usuario Ingresar a la carpeta: Otros Click en el enlace: Concurrent");
		paginaInicialebs.clickOnResponsabilidad(responsabilidad);
		paginaInicialebs.clickOnCarpeta(carpetaOtros);
		paginaInicialebs.clickRunConcurrent();
 		uebs.hardWait(10);
 		patutil.runApplet();
		uebs.hardWait(60);
		testCase.addScreenShotCurrentStep(" ");
		patutil.clickEjecutarNuevaSolicitud();
		
		//PASO 13
		addStep("Ingresar: XXFC - CFD Validacion Comercial Click en ejecutar Click en visualizar Output");
		paginaInicialebs.clickOnResponsabilidad(responsabilidad);
		
		
		patutil.enterText(validacionComercial);
		testCase.addScreenShotCurrentStep(" ");
	    uebs.hardWait(5);
	    patutil.clickEjecutar();
	    patutil.nEnter(1);
	    uebs.hardWait(12);
	    patutil.clickNo();
	    testCase.addScreenShotCurrentStep(" ");
	    patutil.clickEncontrar();
	    testCase.addScreenShotCurrentStep(" ");
	  		while(patutil.validatePattern("estadoNormalVerde") || patutil.validatePattern("estadoStandbyVerde")) {
	  			u.hardWait(15);
	  			if(patutil.validatePattern("ocRefrescarDatos")) {
	  				patutil.clickPattern("ocRefrescarDatos",10);
	  			}
	  		}
	  		    
	  		assertTrue(patutil.validatePattern("faseFinalizadoPrimeraFila") && patutil.validatePattern("estadoNormalPrimeraFila"));	
		testCase.addScreenShotCurrentStep(" ");
	  	u.hardWait(5);
	    
		//PASO 14
		addStep("Ingresar a la base de datos  AVEBQA consultar: select VALN_STATUS  from aapps.xxfc_cfdavalidar where VALC_FOLIOPAGO=''");
		SQLResult consulta3 = dbEBS.executeQuery(tabla3);
		String ITEMCONSULTA3 = consulta3.getData(0, "VALN_ESTATUS");
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta3);
		assertEquals(ITEMCONSULTA3,"1");
		u.hardWait(5);
		//PASO 15
		addStep("Ingresar: XXFC-Informe de Requerimientos de Efectivo Detallado Click en ejecutar Click en visualizar Output");
		patutil.vers();
	    uebs.hardWait(15);
	    patutil.clickEjecutarNuevaSolicitud();
	    uebs.hardWait(15);
	    patutil.enterText(reporteEfectivo);
		testCase.addScreenShotCurrentStep(" ");
		uebs.hardWait(5);
		patutil.clickEjecutar();
		uebs.hardWait(7);
		paginaInicialebs.clickOnResponsabilidad(responsabilidad);
		
		String consultaTerms = "select invoice_num, terms_date, invoice_amount, source, description from apps.ap_invoices_all where invoice_num = "+"'"+folio_pago+"' FETCH FIRST 1 ROW ONLY";
		SQLResult CONSULTARESULTTERMS = dbEBS.executeQuery(consultaTerms);
		
		String fechaPagoTerms = CONSULTARESULTTERMS.getData(0, "terms_date");
	
		   // Formato que coincide con la fecha proporcionada
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        // Parsear la fecha al objeto Date
        Date fecha1 = formatoEntrada.parse(fechaPagoTerms);

        // Formato deseado para la salida
        SimpleDateFormat formatoDeseado = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        // Formatear la fecha y convertirla a mayúsculas
        String fechaFormateada = formatoDeseado.format(fecha1).toUpperCase();

        // Imprimir el resultado
        System.out.println("Fecha con formato terms: " + fechaFormateada);
        u.hardWait(4);
		
		patutil.camposReporteEfectivo(unidadOperativa, pagarvencimiento, fechaPagoDesde, fechaPagoHasta, fechaFormateada, incluirFacturasNoValidadas, opcionResumen, totalPorProvedor);
		testCase.addScreenShotCurrentStep(" ");
		patutil.clickAceptarParametros();
		u.hardWait(8);
		patutil.clickEjecutar();
		uebs.hardWait(10);
		patutil.clickNo();
	    testCase.addScreenShotCurrentStep(" ");
	    patutil.clickEncontrar();
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep(" ");
  		while(patutil.validatePattern("estadoNormalVerde") || patutil.validatePattern("estadoStandbyVerde")) {
  			u.hardWait(15);
  			if(patutil.validatePattern("ocRefrescarDatos")) {
  				patutil.clickPattern("ocRefrescarDatos",10);
  			}
  		}
  		    
  		assertTrue(patutil.validatePattern("faseFinalizadoPrimeraFila") && patutil.validatePattern("estadoNormalPrimeraFila"));	
	    testCase.addScreenShotCurrentStep(" ");
	    uebs.hardWait(5);
	    testCase.addScreenShotCurrentStep(" ");
	    patutil.clickVisualizarOutput();
	    uebs.hardWait(40);
	    paginaInicialebs.AbrirUltimoArchivoDescargadoExcel();
	   
	    patutil.AbrirBuscarFolioExcel(folio_pago);
	    testCase.addScreenShotCurrentStep(" ");
		assertTrue(paginaInicialebs.ValidacionComercialFolio(folio_pago,validacionComercialPalabra));
		patutil.altF4();
		uebs.getDriver().quit();

	
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_GenerarCargaArchivoCampoSerieTenga25CaracteresExactos_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  en el campo serie contenga un registro de 25 caracteres exactos y este se procese de manera exitosa.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Solicitar a Diverza que registre en su backend un numero de Serie que contenga 25 caracteres  exactos y nos confirme su carga pasando el UUID.\r\n"
				+ "Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener Acceso al aplicativo de Oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}
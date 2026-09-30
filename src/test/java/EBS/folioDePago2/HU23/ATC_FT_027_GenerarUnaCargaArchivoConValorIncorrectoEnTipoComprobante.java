package EBS.folioDePago2.HU23;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
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
 * Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv   contenga valores diferentes en el archivo csv en el campo tipo de comprobante, de los cargados en el xml
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_027_GenerarUnaCargaArchivoConValorIncorrectoEnTipoComprobante
 * 
 * PRERREQUISITOS: 
 * Contar con archivo relacional
 * Tener previamente un Folio de Pago para que sea posible el procesamiento
 * Tener Acceso a https://portalespublicosqa.oxxo.com/AccesssControl/pages/login_form.jsf
 * Tener usuarios para el portal
 * Tener Acceso a la BD en la instancia AVEBQA
 * Tener Acceso al aplicativo de oracle EBS
 *
 * INSUMOS:
 * Factura contabilizada
 * XML con valor incorrecto en tipo de comprobante
 * CSV con datos de la factura y valor incorrecto en tipo de comprobante
 * 
 * @author ricardor4@hexaware.com
 * @date 13/Marzo/2025
 */
public class ATC_FT_027_GenerarUnaCargaArchivoConValorIncorrectoEnTipoComprobante extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_027_GenerarUnaCargaArchivoConValorIncorrectoEnTipoComprobante_test(HashMap<String, String> data) throws Exception {
	
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
		addStep("Ingresar a Control de Acceso: (https://pruebasweb.oxxo.com/AuthFemcoProx)");
				
		
		
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
		String tp = "E";
		
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
				fecha, uuid, tp, "", pathCSV);
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
		String tabla0 =  "select MSG_ERROR,FOLIO_PAGO,FECHA_FACTURA from apps.xxfc_ap_cargacsv_all where folio_pago= "+"'"+folio_pago+"' order by fecha_procesado desc";
		String tablaUid = "select VALC_RFC,VALC_TIPOCOMPROBANTE,VALC_UUID from apps.xxfc_Cfdavalidar where valc_uuid = "+"'"+uuid+"' order by VALD_FECHARECEPCION desc";
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		SQLResult consultauid = dbEBS.executeQuery(tablaUid);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consultauid);
		paginaInicial.loginPortalProveedores(usuario, contrasena);
		assertNotNull(consultauid, "Error: El resultado de la consulta a la base de datos es nulo.");
		// PASO 2
		addStep("Ingresar a Gestion de Proveedores");
		boolean isButtonPresent = paginaInicialWEB.gestionDeProveedores();
		assertTrue(isButtonPresent, "No se encontro xphat");
		u.waitForLoadPage();
		// PASO 3
		addStep("Ingresar a Menu Finanzas");
		boolean isButtonPresent2 = paginaInicialWEB.finanzas();
		assertTrue(isButtonPresent2, "No se encontro xphat");
		// PASO 4
		addStep("Ingresar a la Carga de Archivo Relacional");
		boolean isButtonPresent3 = paginaInicialWEB.linkAchivoRelacional();
		assertTrue(isButtonPresent3, "No se encontro xphat");
		// PASO 5
		addStep("Adjuntar archivo relacional y presionar el boton  cargar");
		testCase.addBoldTextEvidenceCurrentStep("tiempo de carga de archivo " + paginaInicialWEB.subirArchivoRelacional(nombreArchivo,pathCSV) +" Segundos");
	
		testCase.addBoldTextEvidenceCurrentStep("Nombre del archivo a procesar: ");
		testCase.addBoldTextEvidenceCurrentStep(nombreArchivo);
		assertNotNull(nombreArchivo, "Archivo nulo");
		
		testCase.addBoldTextEvidenceCurrentStep("XML ENVIADO:");
		String xmlContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
		testCase.addCodeEvidenceCurrentStep(xmlContent);
		testCase.addBoldTextEvidenceCurrentStep("Valor tipo comprobante diferente en el archivo:  " +tp);
		
		// PASO 6
		addStep("Visualizar que el archivo subio con error con el query:"
				+ "select MSG_ERROR,FOLIO_PAGO,FECHA_FACTURA from apps.xxfc_ap_cargacsv_all where folio_pago='Numero de factura' order by fecha_procesado desc");
		SQLResult consulta0 = dbEBS.executeQuery(tabla0);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta0);
		assertNotNull(consulta0, "El resultado de la consulta es nulo");
		// PASO 7
		addStep("Verificar que el archivo no se haya procesado correctamente");
		paginaInicialWEB.clickActualizarArchivo();
		assertTrue(paginaInicialWEB.validarMesajeErrorTipoRelacion(nombreArchivo));
		paginaInicialEBS.AbrirUltimoArchivoDescargadoExcel();
		testCase.addScreenShotCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.getDriver().quit();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_027_GenerarUnaCargaArchivoConValorIncorrectoEnTipoComprobante_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar una carga de archivo relacional en el portal de proveedores, donde el documento csv contenga valores diferentes en el campo tipo de comprobante, de los cargados en el xml";
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
		return "Contar con archivo relacional\r\n"
				+ "Tener previamente un Folio de Pago para que sea posible el procesamiento\r\n"
				+ "Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener Acceso al aplicativo de oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}
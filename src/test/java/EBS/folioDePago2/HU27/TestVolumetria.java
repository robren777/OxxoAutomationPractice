package EBS.folioDePago2.HU27;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.ConstruccionCSV;
import EBS.util.FolioPagoMVP2.ConstruccionXML;
import EBS.util.FolioPagoMVP2.EBSChrome;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.FolioPagoMVP2.PathsXML;
import EBS.util.FolioPagoMVP2.PortalProveedoresMVP2;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class TestVolumetria extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void TestVolumetria_test(HashMap<String, String> data) throws Exception {
		addStep("Llamando paths");

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
		String importeOxxo = importeBruto;
		String cuenta = data.get("cuenta");
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
		String fechaXML = data.get("fechaFactura");

		// Variables base para UUID y folio
		String uuidPivote = uuidBase;
		String folioPivote = folioBase;

		// Parte de los paths dinámicos/////////////////
		// Crear una instancia de PathsXML con la cantidad de paths necesarios
		PathsXML pathsXML = new PathsXML(5000);
		String path1 = pathsXML.getPath(1);
		// String path4367 = pathsXML.getPath(4367);
		//////////////////////////////////////////////

		// Atributos del XML
		Map<String, String> attributes2 = construccionXML.obtenerAtributos(path1);
		// Declarar variables individuales y asignarles los valores
		String fecha = attributes2.get("Fecha");
		String folio = attributes2.get("Folio");
		String serie = attributes2.get("Serie");
		String rfc = attributes2.get("Rfc");
		String importe = attributes2.get("Total");
		String tipoDeComprobante = attributes2.get("TipoDeComprobante");
		String uuid = attributes2.get("UUID");
		String nombreProveedor = "";
		String fechaFactura = construccionXML.formatearFechaParaFactura(fecha);
		String uuidRelacional = "";
		Boolean flag = false;

		String consultaTerms = "select invoice_num, terms_date, invoice_amount, source, description from apps.ap_invoices_all where invoice_num = "
				+ "'" + folio_pago + "' FETCH FIRST 1 ROW ONLY";
		SQLResult CONSULTARESULTTERMS = dbEBS.executeQuery(consultaTerms);

		String fechaPagoTerms = CONSULTARESULTTERMS.getData(0, "terms_date");

		// Formato que coincide con la fecha proporcionada
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

		// Parsear la fecha al objeto Date
		Date fecha1 = formatoEntrada.parse(fechaPagoTerms);

		// Formato deseado para la salida
		SimpleDateFormat formatoDeseado = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

		// Formatear la fecha y convertirla a may�sculas
		String fechaFormateada = formatoDeseado.format(fecha1).toUpperCase();

		// Imprimir el resultado
		System.out.println("Fecha con formato terms: " + fechaFormateada);
		u.hardWait(4);

		// Preparando pre-requisitos
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS2 + " '" + rfc + "'" + queryEBS2_1);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			nombreProveedor = resultadoContenedor.getData(0, 0);
		}

		// Quitar comentario cuando se requiera buscar otros 5000 uuids y folios
		// disponibles
		// (Tarda casi 2 horas en ejecutar este proceso.)
		// Iterar sobre los paths generados dinámicamente
		for (int i = 1; i <= pathsXML.getAllPaths().size(); i++) {
			String currentPath = pathsXML.getPath(i); // Obtener el path actual
			String currentUUID = uuidPivote; // Inicia desde el pivote actual
			String currentFolio = folioPivote; // Inicia desde el pivote actual

			// Buscar un UUID disponible
			while (true) {
				System.out.println("Probando UUID para el archivo " + currentPath + ": " + currentUUID);

				// Ejecuta la consulta para verificar si el UUID está disponible
				resultadoContenedor = dbEBS.executeQuery(queryEBS5 + " '" + currentUUID + "'");

				if (!resultadoContenedor.isEmpty()) {
					// Si el UUID no está disponible, incrementa el UUID y continúa el ciclo
					System.out.println("UUID ocupado. Incrementando...");
					System.out.println("///////////////////////////////////////");
					currentUUID = construccionXML.incrementarUUID(currentUUID);
				} else {
					// Si el UUID está disponible, imprime el UUID encontrado y sale del ciclo
					System.out
							.println("UUID disponible encontrado para el archivo " + currentPath + ": " + currentUUID);
					break;
				}
			}

			// Buscar un folio disponible
			while (true) {
				System.out.println("Probando folio para el archivo " + currentPath + ": " + currentFolio);

				// Ejecuta la consulta para verificar si el folio está disponible
				resultadoContenedor = dbEBS.executeQuery(queryEBS6 + " '" + currentFolio + "'");

				if (!resultadoContenedor.isEmpty()) {
					// Si el folio no está disponible, incrementa el folio y continúa el ciclo
					System.out.println("Folio ocupado. Incrementando...");
					System.out.println("///////////////////////////////////////");
					currentFolio = construccionXML.incrementarFolio(currentFolio);
				} else {
					// Si el folio está disponible, imprime el folio encontrado y sale del ciclo
					System.out.println(
							"Folio disponible encontrado para el archivo " + currentPath + ": " + currentFolio);
					System.out.println("///////////////////////////////////////");
					break;
				}
			}
			//construccionXML.actualizarMesEnFecha(currentPath, fechaXML);

			// Modificar el archivo XML con el UUID y folio encontrados
			construccionXML.modificarXMLString(currentPath, currentUUID, currentFolio);

			// Actualizar los pivotes para el siguiente archivo
			uuidPivote = construccionXML.incrementarUUID(currentUUID);
			folioPivote = construccionXML.incrementarFolio(currentFolio);
		}

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
				// Si el folio de pago est� disponible, imprime el folio de pago encontrado y
				// sale del ciclo
				System.out.println("Folio de pago disponible encontrado: " + folio_pago);
				System.out.println("///////////////////////////////////////");
				flag = false;
				break;
			}
		}

		utils.sql.SQLResult folioDisponible = dbEBS.executeQuery(queryEBS4 + " '" + folio_pago + "'");

		nombreArchivo = "CFD" + folio_pago + ".csv";

		// Iterar sobre todos los paths y enviarlos
		// Descomentar estas lineas para enviar los xml a la femsa_ws cuando se tengan
		// listos
		// (El proceso tarda 4 horas aprox)
		// for (int i = 1; i <= pathsXML.getAllPaths().size(); i++) {
		for (int i = 1; i <= 5000; i++) {
			String currentPath = pathsXML.getPath(i); // Obtener el path actual

			// Enviar el XML usando el m étodo sendXmlPath
			flag = construccionXML.sendXmlPath(currentPath, url);

			// Verificar el estado de env ío y registrar el resultado
			if (flag) {
				System.out.println("XML enviado exitosamente: " + currentPath);
			} else {
				System.out.println("Error al enviar XML: " + currentPath);
			}
		}

		// Proceso para armar el map y tomar los atributos de cada xml (no tarda nada)
		// Mapa para almacenar los atributos de cada archivo XML, identificados por su
		// índice
		Map<Integer, Map<String, String>> archivosMap = new HashMap<>();

		// Iterar sobre los paths de los archivos XML
		for (int i = 1; i <= pathsXML.getAllPaths().size(); i++) {
			String path = pathsXML.getPath(i); // Obtener el path actual

			// Obtener los atributos del archivo actual
			Map<String, String> attributes = construccionXML.obtenerAtributos(path);

			// Guardar los atributos en el mapa utilizando el índice como clave
			archivosMap.put(i, attributes);
		}

		// Ejemplo: Acceder a los datos de cada archivo XML
		for (Map.Entry<Integer, Map<String, String>> entry : archivosMap.entrySet()) {
			int archivoIndex = entry.getKey(); // Indice del archivo
			Map<String, String> attributes = entry.getValue(); // Atributos del archivo

			// Acceder a los valores de las claves
			fecha = attributes.get("Fecha");
			folio = attributes.get("Folio");
			serie = attributes.get("Serie");
			rfc = attributes.get("Rfc");
			importe = attributes.get("Total");
			tipoDeComprobante = attributes.get("TipoDeComprobante");
			uuid = attributes.get("UUID");

			// Imprimir los valores
			System.out.println("Archivo XML " + archivoIndex + ":");
			System.out.println("Fecha: " + fecha);
			System.out.println("Folio: " + folio);
			System.out.println("Serie: " + serie);
			System.out.println("RFC: " + rfc);
			System.out.println("Importe: " + importe);
			System.out.println("Tipo de Comprobante: " + tipoDeComprobante);
			System.out.println("UUID: " + uuid);
			System.out.println("///////////////////////////////////////");
		}

		// Iterar sobre el mapa y generar el archivo CSV
		for (Map.Entry<Integer, Map<String, String>> entry : archivosMap.entrySet()) {
			Map<String, String> attributes = entry.getValue();

			// Obtener los valores de los atributos
			rfc = attributes.get("Rfc");
			serie = attributes.get("Serie");
			folio = attributes.get("Folio");
			fecha = attributes.get("Fecha");
			uuid = attributes.get("UUID");
			tipoDeComprobante = attributes.get("TipoDeComprobante");
			uuidRelacional = " "; // Puede estar vacío

			// Obtener el importe y modificarlo según el tipo de comprobante
			double importe1 = Double.parseDouble(attributes.get("Total"));
			if ("E".equalsIgnoreCase(tipoDeComprobante)) {
				importe1 = -importe1; // Hacer el importe negativo si el tipo es "E"
			} else if ("I".equalsIgnoreCase(tipoDeComprobante)) {
				importe1 = Math.abs(importe1); // Asegurarse de que el importe sea positivo si el tipo es "I"
			}

			// Generar una línea en el archivo CSV
			flag = construccionCSV.generarArchivoCsvVariasLineas(rfc, nombreProveedor, serie, folio, folio_pago,
					String.valueOf(importe1), // Convertir el importe a String
					importeOxxo, fecha, uuid, tipoDeComprobante, uuidRelacional, pathCSV);

			// Verificar si la línea fue agregada exitosamente
			if (flag) {
				System.out.println("Se agregó exitosamente una línea al archivo CSV para el UUID: " + uuid);
			} else {
				System.err.println("Error al agregar una línea al archivo CSV para el UUID: " + uuid);
			}
		}

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
		testCase.addScreenShotCurrentStep("creacionDeFacturaExitosa2");
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
		testCase.addScreenShotCurrentStep("creacionDeFacturaExitosa3");
		u.hardWait(10);
		p.clickImagen("crearContabilidad");
		u.hardWait(3);
		p.clickImagen("contabilizacionFinal");
		u.hardWait(10);
		p.clickImagen("aceptarFactura");
		u.hardWait(10);
		p.validarImagen("validacionFactura");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("creacionDeFacturaExitosa4");
		p.clickImagen("aceptarFacturaCreada");
		u.hardWait(15);
		p.altF4();
		u.hardWait(5);
		p.enter();
		u.hardWait(5);

	}


	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

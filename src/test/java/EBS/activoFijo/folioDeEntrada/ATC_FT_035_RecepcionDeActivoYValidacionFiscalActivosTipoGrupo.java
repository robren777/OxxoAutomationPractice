package EBS.activoFijo.folioDeEntrada;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.EBSDeskApp.pomSimetrik;
import EBS.util.FolioPagoMVP2.EBSChrome;
import EBS.util.ServiceNow.ServiceNow;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Se incluye proceso de folio de Entrada para una OC en pesos, llegada de información a tabla intermedia, envío a buzones de integración y despues a Snow para su validación fiscal y regreso a EBS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_035_Recepción de activo y Validación fiscal_ Activos tipo Grupo
* 
* PREREQUISITOS:
* 	- Contar con una OC a recibir relacionada a una factura que esté registrada en Diverza con artículos de tipo Grupo
* 
* INSUMOS:
* 	- El mismo script los genera basado en el archivo OxxoAutomation\Config\Input\Files\Activo Fijo.xlsx
* 
* @author Jesus Moreno
* @date 16/Febrero/2026
*/
public class ATC_FT_035_RecepcionDeActivoYValidacionFiscalActivosTipoGrupo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_035_RecepcionDeActivoYValidacionFiscalActivosTipoGrupo_test(HashMap<String, String> data)
			throws Exception {

		// UTILERIAS
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		EBSPaginaInicial paginaInicialEBS = new EBSPaginaInicial(u, testCase);
		String PatternsImg = "EBS_Folio_Pago";
		EBSChrome ebs = new EBSChrome(u, testCase, PatternsImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		ServiceNow serviceNow = new ServiceNow(u, testCase);
		ServiceNow paginaInicialWEB = new ServiceNow(u, testCase);
		pomSimetrik pom = new pomSimetrik(u, testCase);

		// Variables
		String server = data.get("server");
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String subCarpeta = data.get("subCarpeta");
		String responsabilidad1 = data.get("responsabilidad1");
		String carpeta1 = data.get("carpeta1");
		String subCarpeta1 = data.get("subCarpeta1");
		String subCarpeta2 = data.get("subCarpeta2");
		String subCarpeta3 = data.get("subCarpeta3");
		String logoutEBS = data.get("logoutEBS");
		String OC = "";
		String invoiceOC = data.get("invoiceOC");
		String concurrente = data.get("concurrente");
		String queryEBS1 = data.get("queryEBS1");
		String queryEBS2 = data.get("queryEBS2");
		String queryEBS3 = data.get("queryEBS3");
		String queryEBS4 = data.get("queryEBS4");
		String queryEBS5 = data.get("queryEBS5");
		String queryEBS6 = data.get("queryEBS6");
		String queryEBS7 = data.get("queryEBS7");
		String queryEBS8 = data.get("queryEBS8");
		String queryEBS9 = data.get("queryEBS9");
		String queryEBS9_1 = data.get("queryEBS9_1");
		String queryEBS9_2 = data.get("queryEBS9_2");
		String queryEBS9_3 = data.get("queryEBS9_3");
		String queryEBS9_4 = data.get("queryEBS9_4");
		String queryEBS9_5 = data.get("queryEBS9_5");
		String queryEBS9_6 = data.get("queryEBS9_6");
		String queryEBS9_7 = data.get("queryEBS9_7");
		String queryEBS10 = data.get("queryEBS10");
		String queryEBS10_1 = data.get("queryEBS10_1");
		String queryEBS10_2 = data.get("queryEBS10_2");
		String queryEBS10_3 = data.get("queryEBS10_3");
		String queryEBS10_4 = data.get("queryEBS10_4");
		String queryEBS10_5 = data.get("queryEBS10_5");
		String queryEBS10_6 = data.get("queryEBS10_6");
		String queryEBS10_7 = data.get("queryEBS10_7");
		String queryEBS10_8 = data.get("queryEBS10_8");
		String queryEBS10_9 = data.get("queryEBS10_9");
		String deleteEBS3 = data.get("deleteEBS3");
		String deleteEBS4 = data.get("deleteEBS4");
		String deleteEBS5 = data.get("deleteEBS5");
		String updateEBS6 = data.get("updateEBS6");
		String updateEBS7 = data.get("updateEBS7");
		String commit = data.get("commit");
		String busqueda = data.get("busqueda");
		String unidadOperativa = data.get("unidadOperativa");
		String tipoOC = data.get("tipoOC");
		String tipoDivisa = data.get("tipoDivisa");
		String orgEnvio = data.get("orgEnvio");
		String rangoInf = data.get("rangoInf");
		String rangoSup = data.get("rangoSup");
		String pathFacturas = data.get("path");
		String articulo = "";
		String tipoDeUso = "";
		String articulosBloqueados = "32520";
		String registros = "";
		int update = 0;
		
		
		String ruta = data.get("ruta");
		String ruta2 = data.get("ruta2");
		String docName = "";
		boolean existeArchivo = false;

		String ruta3 = data.get("ruta3");
		String ruta4 = data.get("ruta4");
		
		String opcion1 = data.get("opcion1");
		String opcion2 = data.get("opcion2");
		String opcion3 = data.get("opcion3");
		String articulos = data.get("articulos");
		String tipoArticulo = data.get("tipoArticulo");

	

		utils.sql.SQLUtil dbEBSActFijo = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA_ACTFIJO,
				GlobalVariables.DB_USER_AVEBQA_ACTFIJO, GlobalVariables.DB_PASSWORD_AVEBQA_ACTFIJO);

		Boolean flag = false;


		addStep("Entrar al módulo de MXOXXO-IN17FAA-Admon_ACT_FIJO_OS > Transacciones > Recepcion > View Receiving Transactions");

		serviceNow.extraerDatosExcel(pathFacturas, 2);

		int intento = 1;
		Map<String, String> datos = null;
		String factura = null; // Declarar fuera para usar después
		String receiptNum = null;
		String kitGrupo = null;

		while (true) {
			datos = serviceNow.extraerDatosExcel(pathFacturas, intento - 1);

			receiptNum = datos.get("Receipt Num");
			factura = datos.get("Factura Diverza");
			kitGrupo = datos.get("KIT/Grupo");

			if ("N/A".equalsIgnoreCase(kitGrupo)) {
				existeArchivo = pom.validarCSVArticulosIndividuales(GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta,
						receiptNum, kitGrupo);
				existeArchivo = pom.validarCSVArticulosIndividuales(GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta2,
						receiptNum, kitGrupo);
			} else {
				System.out.println("Intento " + intento + ": Factura " + factura
						+ " ignorada (no es artículo individual, KIT/Grupo = " + kitGrupo + ")");
				intento++;
				continue;
			}

			if (existeArchivo) {
				System.out.println("Intento " + intento + ": Factura " + factura
						+ " NO candidata (archivo SFTP encontrado para Receipt Num " + receiptNum + ")");
				intento++;
			} else {
				System.out.println("Intento " + intento + ": Factura " + factura
						+ " CANDIDATA (archivo SFTP NO encontrado para Receipt Num " + receiptNum + ")");
				break;
			}
		}

		// Aquí tienes los datos válidos en 'datos'
		System.out.println("Datos seleccionados para usar:");
		datos.forEach((clave, valor) -> System.out.println(clave + ": " + valor));

		// PASOS DE VERIFICACIÓN EN TABLA INTERMEDIA

		// Verificación de XXFC.XXFA_SN_DATA_DETAILS
		System.out.println("=========== Validando XXFC.XXFA_SN_DATA_DETAILS ============");

		System.out.println(queryEBS3 + " '" + factura + "'");
		utils.sql.SQLResult resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS3 + " '" + factura + "'");
		registros = resultadoContenedor.getData(0, 0);
		System.out.println("Registros: " + registros);
		if (!"0".equals(registros)) {
			System.out.println("Factura registrada, eliminando registro de XXFC.XXFA_SN_DATA_DETAILS");
			update = dbEBSActFijo.executeUpdate(deleteEBS3 + " '" + factura + "'");
		}
		// testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		// Verificación de XXFC.XXFA_SN_FE_DATA_DETAILS

		System.out.println("=========== Validando XXFC.XXFA_SN_FE_DATA_DETAILS ============");
		System.out.println(queryEBS4 + " '" + factura + "'");
		resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS4 + " '" + factura + "'");
		registros = resultadoContenedor.getData(0, 0);
		System.out.println("Registros: " + registros);
		if (!"0".equals(registros)) {
			System.out.println("Factura registrada, eliminando registro de XXFC.XXFA_SN_FE_DATA_DETAILS");
			update = dbEBSActFijo.executeUpdate(deleteEBS4 + " '" + factura + "'");
		}
		// testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		// Verificación de xxfc.xxfa_sn_control
		System.out.println("=========== Validando xxfc.xxfa_sn_control ============");
		System.out.println(queryEBS5 + " '" + factura + "')");
		resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS5 + " '" + factura + "')");
		registros = resultadoContenedor.getData(0, 0);
		System.out.println("Registros: " + registros);
		if (!"0".equals(registros)) {
			System.out.println("Factura registrada, eliminando registro de xxfc.xxfa_sn_control");
			update = dbEBSActFijo.executeUpdate(queryEBS5 + " '" + factura + "')");
		}

		// Verificación de po.rcv_shipment_headers
		System.out.println("=========== Validando po.rcv_shipment_headers ============");
		resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS6 + " '" + factura + "'");
		registros = resultadoContenedor.getData(0, 0);
		System.out.println("Registros: " + registros);
		System.out.println(queryEBS6 + " '" + factura + "'");
		if (!"0".equals(registros)) {
			System.out.println("Factura registrada, eliminando registro de po.rcv_shipment_headers");
			update = dbEBSActFijo.executeUpdate(updateEBS6 + " '" + factura + "'");
		}

		// Verificación de ap.AP_INVOICES_ALL
		System.out.println("=========== Validando ap.AP_INVOICES_ALL ============");
		System.out.println(queryEBS7 + " '" + factura + "'");
		resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS7 + " '" + factura + "'");
		registros = resultadoContenedor.getData(0, 0);
		System.out.println("Registros: " + registros);

		if (!"0".equals(registros)) {
			System.out.println("Factura registrada, eliminando registro de ap.AP_INVOICES_ALL");
			update = dbEBSActFijo.executeUpdate(updateEBS7 + " '" + factura + "'");
		}

		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.get5032838EbsUsr,
				GlobalVariables.get5032838HexEbsPwd);
		login.navigate(server);
		login.setCredentials();
		login.setLanguage();
		login.clickLogIn();

		p.validarImagen2("carpetaEBS");
		p.presionarESC();

		paginaInicialEBS.clickOnResponsabilidad2(responsabilidad1);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(carpeta1);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(subCarpeta1);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(subCarpeta3);
		ebs.startEBS3();
		p.validarImagen2("orgs");
		testCase.addScreenShotCurrentStep("Paso 1");
		p.enter();

		// Paso 2. Ingresar el folio de entrada en el campo de Recepción y clic en
		// Encontrar
		addStep("Ingresar el folio de entrada en el campo de Recepción y clic en Encontrar");

		p.validarImagen2("transaccionesDeRecepcion");
		p.nTAB(2);
		p.pegarTexto(receiptNum);
		p.validarImagen2("Paso 2");
		testCase.addScreenShotCurrentStep("paso 2");
		p.clickImagen("encontrarRecepitNum");

		// Paso 3. Clic en la opción de la parte superior "Herramientas" -> Carga de
		// Factura
		addStep("Clic en la opción de la parte superior \"Herramientas\" -> Carga de Factura");

		p.clickImagen("herramientas");
		testCase.addScreenShotCurrentStep("Paso 3");
		p.clickImagen("cargaFactura");

		// Paso 4. Ingresar el número de factura, la cual seguirá la nomenclatura
		// "serie-factura". Dichos datos se tomarán de la factura de Diverza. Posterior,
		// click en Aceptar.
		addStep("Ingresar el número de factura, la cual seguirá la nomenclatura \"serie-factura\". Dichos datos se tomarán de la factura de Diverza. Posterior, click en Aceptar.");
		p.validarImagen2("parametrosFactura");
		p.pegarTexto(factura);
		testCase.addBoldTextEvidenceCurrentStep("Datos de prueba:");
		testCase.addBoldTextEvidenceCurrentStep("Folio de Entrada: " + receiptNum);
		testCase.addBoldTextEvidenceCurrentStep("Factura: " + factura);
		testCase.addBoldTextEvidenceCurrentStep("Tipo de artículo: " + tipoArticulo);
		testCase.addBoldTextEvidenceCurrentStep("Artículos: " + articulos);
		testCase.addScreenShotCurrentStep("Paso 4");
		p.enter();

		// Paso 5. Click en Ejecutar
		addStep("Click en Ejecutar");
		testCase.addScreenShotCurrentStep("Paso 5");
		p.clickImagen("ejecutarUpdateFactura");
		p.nTAB(1);
		p.enter();

		// Paso 6. Validar en el output que la factura se ligue correctamente a la orden
		// de compra y al folio de entrada.
		addStep("Validar en el output que la factura se ligue correctamente a la orden de compra y al folio de entrada.");
		p.clickImagen("verConcurrentes");
		p.clickImagen("solicitudesConcurrentes");
		p.validarImagen2("encontrarSolicitudes2");
		p.clickImagen("encontrarSolicitud3");
		p.validarImagen2("refrescoAutomatico");

		flag = true;
		while (flag) {

			flag = p.validarImagenBoolean("normal");

			if (flag) {
				p.clickImagen("refresh2");
				u.hardWait(5);
			} else {
				System.out.println("Concurrente terminado");
			}
		}

		p.clickImagen("encontrar_solicitudes");
		p.validarImagen2("solicitud_especifica");
		p.clickImagen("solicitud_especifica");
		p.validarImagen2("nombre_concurrente");
		p.clickImagen("nombre_concurrente");
		u.hardWait(1);
		p.pegarTexto(concurrente);
		p.validarImagen2("encontrar_concurrente");
		p.clickImagen("encontrar_concurrente");
		p.validarImagen2("solicitudes2");
		p.validarImagen2("refrescoAutomatico");

		int intentos = 0;
		boolean logoPresente = false;

		while (intentos < 10 && !logoPresente) {
			p.clickImagen("visualizarOutput2");
			u.hardWait(10);

			logoPresente = p.validarImagenBoolean("logoOxxo");

			if (logoPresente) {
				testCase.addScreenShotCurrentStep("Paso 6");
				p.altF4();
				p.validarImagen2("archivo2");
			} else {
				p.altF4();
				intentos++;
			}
		}

		if (!logoPresente) {
			// Tras 10 intentos sin éxito, tomar screenshot y continuar
			testCase.addScreenShotCurrentStep("Paso 6");
		}
		p.validarImagen2("archivo2");

		// Paso 7. Visualizar en la tabla intermedia y tabla de validación el registro
		// de los datos de la orden de compra recibida.
		addStep("Visualizar en la tabla intermedia y tabla de validación el registro de los datos de la orden de compra recibida.");

		// Verificación de XXFC.XXFA_SN_DATA_DETAILS
		System.out.println("=========== Validando XXFC.XXFA_SN_DATA_DETAILS ============");

		System.out.println(queryEBS3 + " '" + factura + "'");

		// Ejecutar query que cuenta registros
		resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS3 + " '" + factura + "'");
		registros = resultadoContenedor.getData(0, 0);
		System.out.println("Registros: " + registros);

		int count = Integer.parseInt(registros);

		if (count > 3) {
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		} else if (count > 0) {
			System.out.println("Muchos registros en XXFC.XXFA_SN_DATA_DETAILS, imprimiendo el count");

			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS8 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			// Ejecutar el update para eliminar registros
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS8 + " '" + factura + "'");
		}

		// Paso 8. Ejecutar el job XXFA_EBS_SN_OUT_BIN y validar que los archivos CSV de
		// entrada y validación hayan sido creados correctamente, así como su contenido
		addStep("Ejecutar el job XXFA_EBS_SN_OUT_BIN y validar que los archivos CSV de entrada y validación hayan sido creados correctamente, así como su contenido");

		// Validar que se cree el CSV

		// Definición de tiempo máximo de espera en milisegundos (15 minutos)
		long tiempoMaxEspera = 15 * 60 * 1000;

		// Variables para controlar el tiempo de espera
		long inicioEspera, tiempoTranscurrido;

		// Primera ruta
		System.out.println("Iniciando validación del archivo CSV en la primera ruta: " + ruta);
		inicioEspera = System.currentTimeMillis();
		boolean archivoExiste = pom.validarCSVArticulosIndividuales(GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta,
				receiptNum, kitGrupo);

		while (!archivoExiste && (System.currentTimeMillis() - inicioEspera) < tiempoMaxEspera) {
			System.out.println("Archivo no encontrado en ruta 1, refrescando pantalla y esperando...");
			p.clickImagen("refresh2");
			u.hardWait(15);
			archivoExiste = pom.validarCSVArticulosIndividuales(GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta,
					receiptNum, kitGrupo);
		}

		if (archivoExiste) {
			System.out.println("Archivo encontrado en ruta 1. Procediendo a validar en la segunda ruta: " + ruta2);
			inicioEspera = System.currentTimeMillis();
			archivoExiste = pom.validarCSVArticulosIndividuales(GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta2,
					receiptNum, kitGrupo);

			while (!archivoExiste && (System.currentTimeMillis() - inicioEspera) < tiempoMaxEspera) {
				System.out.println("Archivo no encontrado en ruta 2, refrescando pantalla y esperando...");
				p.clickImagen("refresh2");
				u.hardWait(15);
				archivoExiste = pom.validarCSVArticulosIndividuales(GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta2,
						receiptNum, kitGrupo);
			}

			if (archivoExiste) {
				System.out.println("Archivo encontrado en ruta 2. Proceso completado.");
			} else {
				System.out.println("Tiempo agotado esperando archivo en ruta 2.");
			}

		} else {
			System.out.println("Tiempo agotado esperando archivo en ruta 1.");
		}
		docName = "ebs_sn_validacion_fiscal_" + receiptNum + ".csv";
		testCase.addBoldTextEvidenceCurrentStep("Archivo encontrado en repositorios: " + docName);

		p.validarImagen2("refrescoAutomatico");
		p.altF4();
		p.enter();

		// PASOS DE SNOW
		// Paso 9. Una vez transcurridos los 15 minutos, validar en Service Now que
		// el/los archivos CSV se hayan recibido correctamente

		addStep("Una vez transcurridos los 15 minutos, validar en Service Now que el/los archivos CSV se hayan recibido correctamente");
		paginaInicialWEB.loginServiceNow(GlobalVariables.USER_ServiceNowQA, GlobalVariables.PASSWORD_ServiceNowQA);
		paginaInicialWEB.datosIntegracion(opcion2);
		paginaInicialWEB.filtrarArchivosEBS("Nombre de archivo", "contiene", receiptNum);
		paginaInicialWEB.esperarRegistros();
		paginaInicialWEB.validarEstadoRegistroProcesado(receiptNum);
		testCase.addScreenShotCurrentStep("archivosCSV");

		// Paso 10. Realizar busqueda de factura en Diverza en la pantalla de Folios de
		// Factura y visualizar que se haya insertado correctamente en Service Now
		addStep("Realizar busqueda de factura en Diverza en la pantalla de Folios de Factura y visualizar que se haya insertado correctamente en Service Now");
		paginaInicialWEB.datosIntegracion(opcion3);
		paginaInicialWEB.filtrarFacturas("Folio", "es", factura);
		paginaInicialWEB.esperarRegistros();
		testCase.addScreenShotCurrentStep("pantallaFacturas");

		// Paso 11. Validar que se ligue el asset correspondiente en la pantalla Datos
		// Integración EBSs
		addStep("Validar que se ligue el asset correspondiente en la pantalla Datos Integración EBSs");
		assertTrue(paginaInicialWEB.datosIntegracion(opcion1));
		assertTrue(paginaInicialWEB.filtrarRegistro("Folio de Entrada", "es", receiptNum)); // pendientre
		assertTrue(paginaInicialWEB.esperarRegistros());
		assertTrue(paginaInicialWEB.validarEstadoFacturaMAF(receiptNum));
		testCase.addScreenShotCurrentStep("pantallaAssets1");
		paginaInicialWEB.abrirPrimerRegistroDeTabla();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("pantallaAssets1");
		paginaInicialWEB.scrollearHastaFondoFormularioMAF();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("pantallaAssets2");
		assertTrue(paginaInicialWEB.clickBotonFactura());
		paginaInicialWEB.scrollearHastaFondoFormularioMAF();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("pantallaAssets3");

		// PASOS DE LECTURA DE EXCEL Y VERIFICACIÓN DE NO DUPLICIDAD EN TABLA INTERMEDIA
		// Y DE VALIDACIÓN (PENDIENTE ...)
		// Paso 12. Validar que se genere el archivo de validación fiscal y este sea
		// depositado en el repositorio incoming del SFTP, validar la información
		// correctamente insertada en el csv y los registros actualizados en base de
		// datos

		addStep("Validar que se genere el archivo de validación fiscal y este sea depositado en el repositorio incoming del SFTP, validar la información correctamente insertada en el csv y los registros actualizados en base de datos");
		
		System.out.println("Etapa 1");
		assertTrue(paginaInicialWEB.esperarArchivoSnowFE(u.getDriver(), GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta3,
				receiptNum), "Se encontró el archivo en el repositorio");
		System.out.println("Etapa 2");
		assertTrue(paginaInicialWEB.esperarArchivoSnowFE(u.getDriver(), GlobalVariables.SFTP_ACT_FIJO_HOST, GlobalVariables.SFTP_ACT_FIJO_PORT, GlobalVariables.SFTP_ACT_FIJO_USER, GlobalVariables.SFTP_ACT_FIJO_PWD, ruta4,
				receiptNum), "Se encontró el archivo en el repositorio");

		// Verificación de XXFC.XXFA_SN_DATA_DETAILS
		System.out.println("=========== Validando XXFC.XXFA_SN_DATA_DETAILS ============");

		dbEBSActFijo = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA_ACTFIJO,
				GlobalVariables.DB_USER_AVEBQA_ACTFIJO, GlobalVariables.DB_PASSWORD_AVEBQA_ACTFIJO);

		if (count > 3) {
			System.out.println("Muchos registros en XXFC.XXFA_SN_DATA_DETAILS, imprimiendo el count");

			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_1 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_2 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_3 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_4 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_5 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_6 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS9_7 + " '" + factura + "') WHERE ROWNUM <= 3");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		} else if (count > 0) {
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_1 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_2 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_3 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_4 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_5 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_6 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_7 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_8 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			resultadoContenedor = dbEBSActFijo.executeQuery(queryEBS10_9 + " '" + factura + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		}
		u.close();
		u.quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\r\n"
				+ "Contar con una OC a recibir relacionada a una factura que esté registrada en Diverza con artículos de tipo Grupo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_035_RecepcionDeActivoYValidacionFiscalActivosTipoGrupo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se incluye proceso de folio de Entrada para una OC en pesos, llegada de información a tabla intermedia, envío a buzones de integración y despues a Snow para su validación fiscal y regreso a EBS";
	}

}
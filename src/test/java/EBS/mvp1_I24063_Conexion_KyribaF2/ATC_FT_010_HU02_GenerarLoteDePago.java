package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.mvp1_Kyriba_F2.pom.pomKyribaF2;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCI�N: V"Validar que se pueda generar un lote de pago con facturas en USD.
-Validar estatus de factura: Pagada"
.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_001_HU02_CuentaParaPagosUSD 
 * 
 * PREREQUISITOS: -"-Contar con acceso a EBS
-Contar con un perfil para pago en USD a trav�s de Kyriba
-Contar con facturas pendientes de pago en USD
-Contar con cuentas bancarias para pago en USD
-Contar con proveedores configurados para pago en USD
-Contar con la plantilla configurada para pagos USD"

 *
 *
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 3/03/2025
 */

public class ATC_FT_010_HU02_GenerarLoteDePago extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_HU02_GenerarLoteDePago_test(HashMap<String, String> data) throws Exception {

		/*
		 * InternetExplorerOptions options = new InternetExplorerOptions();
		 * options.introduceFlakinessByIgnoringSecurityDomains();
		 */
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getGAGUAYOHexEbsUsr;
		String password = GlobalVariables.getGAGUAYOHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String Subcarpeta = data.get("Subcarpeta");
		String campo = data.get("campo");
		String fechainicio = data.get("fechainicio");
		String fechafin = data.get("fechafin");
		String receptorPago = data.get("PROVEEDOR");
		String NombreLotedePago = data.get("NOMBRELOTEDEPAGO");
		String plantilla = data.get("PLANTILLA");
		String taxIVA = data.get("TAXIVA");
		String FACTURA1 = data.get("FACTURA1");
		String FACTURA2 = data.get("FACTURA2");
		String FACTURA3 = data.get("FACTURA3");
		String FACTURA4 = data.get("FACTURA4");
		String FACTURA5 = data.get("FACTURA5");

		String Estatus1 = data.get("STATUS1");
		String Estatus2 = data.get("STATUS2");
		String Estatus3 = data.get("STATUS3");
		String Estatus4 = data.get("STATUS4");
		/*
		 * TASA-0%IVA UTILERIAS
		 */
		// Configurar las opciones de IE
		InternetExplorerOptions options = new InternetExplorerOptions();

		// Forzar el uso de CreateProcess API
		options.setCapability("ie.forceCreateProcessApi", true);

		// Agregar par�metros de l�nea de comandos
		options.setCapability("ie.browserCommandLineSwitches", "-noframemerging -private");

		// Otras configuraciones importantes
		options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		pomKyribaF2 kyriba = new pomKyribaF2(u, testCase, data);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Espa�ol --> Ingresar usuario y contrase�a --> Clic en Con�ctese\r\n"
				+ "");

		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("save");
		inicio.login();
		p.popUpAuthenticacion();

		// PASO 2
		addStep("Seleccionar MXOXXO-APXXXXX-Super Usuario --> Pagos -->Entrar --> Gerente de Pagos\r\n" + "");

		paginaInicial.clickOnResponsabilidad(responsabilidad);
		kyriba.MXOXXOAPXXXXXSuperUsuarioPagosEntrarGerenteDePagos();

		// PASO 3
		addStep("Seleccionar la opci�n: \"Ejecutar solicitud de Proceso de Pago �nica\"");

		kyriba.MXOXXOAPXXXXXEjecutarSolicitudProcesodePago();

		// PASO 4
		addStep("Capturar el nombre de Lote de Pago utilizando el CR de plaza como inicio, seguido de las Iniciales de su nombre, "
				+ "por ultimo la fecha de aplicaci�n del pago\r\n" + "");
		kyriba.capturarNombreDeLoteDePago(NombreLotedePago);

		// PASO 5
		addStep("Seleccionar la plantilla\r\n" + "");
		kyriba.seleccionarPlantilla(plantilla);

		// PASO 6
		addStep("Ingresar los criterios de selecci�n de pago programado:\r\n" + "-Fecha L�mite de Pago\r\n"
				+ "-Tipo de Proveedor (Opcional)\r\n" + "-Receptor Pago (Opcional)\r\n" + "");

		kyriba.loteDePago_SeleccionPagoProgramado(fechainicio, receptorPago);

		// PASO 7
		addStep("Dar clic en Ejecutar\r\n" + "");
		kyriba.loteDePago_click_Ejecutar();

		assertTrue(kyriba.validar_ejecucion(NombreLotedePago), "Fallo al Ejecutar el lote de pago");

		// PASO 8
		addStep("Realizar la b�squeda con el nombre del lote de pago y/o Fecha de Pago --> dar clic en el bot�n Ir\r\n");

		kyriba.buscarSolicitudDeLoteDePago(NombreLotedePago);

		assertTrue(kyriba.loteDepagoEncontrado(NombreLotedePago), "No se encontro el Lote de Pago");

		// PASO 9
		addStep("Dar clic en Refrescar Estado\r\n" + "");

		assertTrue(kyriba.RefrescarEstado(Estatus1), "Fallo al Ejecutar el lote de pago");
		// PASO 10
		addStep("Clic en el bot�n de \"Iniciar Acci�n\" --> Revisar las facturas que se seleccionaron para pagar \r\n"
				+ "");
		kyriba.loteDepagoFacturasSeleccionadas();

		// PASO 11
		addStep("Dar clic en el bot�n de Ejecutar");

		kyriba.EjecutarPago();
		assertTrue(kyriba.validar_ejecucion(NombreLotedePago), "Fallo al Ejecutar el lote de pago");
		// PASO 12
		addStep("Dar clic en Refrescar estado para continuar con el proceso hasta que aparezca el estado de Revisi�n de pago propuesto pendiente\r\n"
				+ "");
		assertTrue(kyriba.RefrescarEstado(Estatus2), "Fallo al Ejecutar el lote de pago");
		// PASO 13
		addStep("Dar clic en el bot�n Iniciar Acci�n \r\n" + "");

		assertTrue(kyriba.IniciarAccion(), "Fallo al dar click en iniciar Accion");

		// PASO 14
		addStep("Valida la informaci�n --> En el campo Acciones seleccionar la opci�n \"Ejecutar proceso de pago\" --> Dar clic bot�n \"Ir\"\r\n"
				+ "");

		assertTrue(kyriba.EjecutarProcesoDePago(), "Fallo al Ejecutar el Proceso de pago");
		assertTrue(kyriba.RefrescarEstado(Estatus3), "Fallo al Ejecutar el lote de pago");
		// PASO 15
		addStep("En la columna \"Detalles\" seleccionar el link \"Mostrar\"\r\n" + "");

		kyriba.Clickdetalles();
		// PASO 16
		addStep("Dar clic en el bot�n \"Efectuar Acci�n\" \r\n" + "");
		kyriba.IniciarAccion();

		// PASO 17
		addStep("Dar clic en Continuar --> Aplicar\r\n" + "");

		kyriba.clickContinuar();

		// PASO 18
		addStep("Dar clic en Refrescar Estado\r\n" + "");
		kyriba.click_BTN_IR();
		assertTrue(kyriba.RefrescarEstado(Estatus4), "Fallo al Ejecutar el lote de pago");
		// PASO 19
		addStep("Consultar las facturas pagadas --> Seleccionar la responsabilidad de MXOXXO-APXXXXX-Super Usuario --> "
				+ "Seleccionar Facturas --> Entrar --> Facturas\r\n" + "");
		kyriba.click_BTN_Home();
		u.hardWait(2);
		// paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickOnCarpeta(Subcarpeta);
		paginaInicial.clickEntrarFacturas();
		p.accepApp();

		// PASO 20
		addStep("Dar clic en buscar --> ingresar los par�metros de b�squeda --> Clic en Encontrar\r\n" + "");
		assertTrue(p.pantallaGenerarFactura(), "La Pantalla Generar Factura No se Ejecuto Correctamente");
		testCase.addScreenShotCurrentStep("windowGenerarFactura");
		assertTrue(kyriba.BusquedaFactura(FACTURA1),"No se Encontro la Factura "+FACTURA1);
		testCase.addScreenShotCurrentStep("camposF");

		// PASO 21
		addStep("Validar que el campo Importe Pagado sea distinto de cero \r\n" + "");

		assertFalse(kyriba.ValidarPago(), "El importe no se pago");

		// PASO 22
		addStep("Seleccionar la pesta�a \"Visualiza Pagos\" \r\n" + "");
		assertTrue(kyriba.VisualizarPagos(), "nose visualiza la pantalla \"Visualizar Pagos\" ");
		// PASO 23
		addStep("Ingresar a la base de datos de EBS \r\n" + "");

		SQLUtil dbEBS = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		String coneccion = "SELECT SYS_CONTEXT('USERENV', 'DB_NAME') FROM DUAL";
		testCase.addTextEvidenceCurrentStep("Conexion a la base de datos EBS exitosa");
		SQLResult Connect = dbEBS.executeQuery(coneccion);

		testCase.addTextEvidenceCurrentStep("Conexion a la base de datos EBS exitosa");
		testCase.addQueryEvidenceCurrentStep(Connect);
		
		// PASO 24
		addStep("Localizar n�meros de pago\r\n" + "");
		String query = "SELECT \r\n" + "  k.TRANSACTION_CODE AS TRANSACTION_CODE1,\r\n" + "  a.CHECK_NUMBER,\r\n"
				+ "  c.INVOICE_NUM,\r\n" + "  c.INVOICE_AMOUNT,\r\n" + "  k.TRD_PRTY_NAME    AS TRD_PRTY_NAME1,\r\n"
				+ "  k.DEBIT_ACCOUNT    AS DEBIT_ACCOUNT1\r\n" + "FROM XXFC.XXFC_AP_KYRIBA_PAGOS k\r\n"
				+ "INNER JOIN apps.ap_checks_all a\r\n" + "ON k.REFERENCE_NUM = a.CHECK_NUMBER\r\n"
				+ "INNER JOIN apps.ap_invoice_payments_all n\r\n" + "ON a.CHECK_ID = n.CHECK_ID\r\n"
				+ "INNER JOIN apps.ap_invoices_all c\r\n" + "ON n.INVOICE_ID      = c.INVOICE_ID\r\n"
				+ "WHERE c.INVOICE_NUM IN ('" + FACTURA1 + "' ,'" + FACTURA2 + "','" + FACTURA3 + "','" + FACTURA5
				+ "')";
		SQLResult result = dbEBS.executeQuery(query);
		testCase.addQueryEvidenceCurrentStep(result);
		assertTrue(result.getAllData().size() > 0); // Verificar que se localizan n�meros de pago
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_HU02_GenerarLoteDePago";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se pueda generar un lote de pago con facturas en USD.\r\n"
				+ "-Validar estatus de factura: Pagada\r\n" + "";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_010_HU02_GenerarLoteDePago";
	}

	@Override
	public String setPrerequisites() {
		return "-\"-Contar con acceso a EBS\r\n"
				+ "-Contar con un perfil para pago en USD a trav�s de Kyriba\r\n"
				+ "-Contar con facturas pendientes de pago en USD\r\n"
				+ "-Contar con cuentas bancarias para pago en USD\r\n"
				+ "-Contar con proveedores configurados para pago en USD\r\n"
				+ "-Contar con la plantilla configurada para pagos USD\"\r\n"
				+ "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
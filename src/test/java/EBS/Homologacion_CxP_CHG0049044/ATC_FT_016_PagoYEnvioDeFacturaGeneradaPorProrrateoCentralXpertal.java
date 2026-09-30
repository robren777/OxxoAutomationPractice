package EBS.Homologacion_CxP_CHG0049044;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import EBS.util.ControlM_HomologacionCxP;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Realizar el pago de una factura generada mediante el proceso de 
 * XXFC-Proceso Facturas Xpertal Prorrateo Central y hacer el envio del pago hacia el portal
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_016_PagoYEnvioDeFacturaGeneradaPorProrrateoCentralXpertal
 * 
 * PREREQUISITOS: 
 * Tener acceso a aplicativo EBS
 * Tener acceso a la BD de AVEBQA
 *	Verificar que existan facturas de prorrateo generadas del dia
 *
 * INSUMOS (datos o información que necesita estar preparada o generada para
 * poder ejecutar el script automatizado):
-  -Haber corrido previamente el ATC_FT_006_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Mayor_a_30
   -Tener el job runPagos_OE14MX en released

 * @author Jesus Alberto Moreno Delgado
 * @date 09/01/2025
 */
public class ATC_FT_016_PagoYEnvioDeFacturaGeneradaPorProrrateoCentralXpertal extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_PagoYEnvioDeFacturaGeneradaPorProrrateoCentralXpertal_test(HashMap<String, String> data)
			throws Exception {

		// Inicia Selenium y Sikuli despuÃ©s de todas las validaciones
		SeleniumUtil u = new SeleniumUtil(new IETest());

		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		ControlM_HomologacionCxP pom = new ControlM_HomologacionCxP(u, testCase);

		boolean result;

		String server = data.get("server");
		String queryEBS0 = data.get("ebs0");
		String queryEBS1 = data.get("ebs1");
		boolean flag;
		String responsabilidad3 = data.get("responsabilidad3");
		String subCarpeta3 = data.get("subCarpeta3");
		String carpeta3 = data.get("carpeta3");
		String invoices = data.get("invoices");
		String path = data.get("path");
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryEBS4 = data.get("ebs4");
		String queryEBS5 = data.get("ebs5");
		String queryEBS6 = data.get("ebs6");
		String queryEBS7 = data.get("ebs7");

		String unidadOperativa = data.get("unidadOperativa");
		String rfc = data.get("rfc");
		String cuentaBancaria = data.get("cuentaBancaria");
		String paymentProcessProfile = data.get("paymentProcessProfile");

		String responsabilidad5 = data.get("responsabilidad5");
		String subCarpeta5 = data.get("subcarpeta5");
		String carpeta5 = data.get("carpeta5");
		String concurrenteNombre = data.get("concurrenteNombre");

		String compania = data.get("compania");
		String crDePlaza = data.get("crDePlaza");
		String tipoDeTransaccion = data.get("tipoDeTransaccion");
		String output = data.get("output");
		String fechaDePagoTransaccion = data.get("fechaDePagoTransaccion");
		String job = data.get("job");

		String invoice_num = null;
		String id_documento_pago = null;
		String log = null;

		// Paso 1. Ingresar a la sección de MXOXXO-APXXXXX-Super Usuario > Facturas
		// >Ingresar>Facturas
		addStep("Ingresar a la sección de MXOXXO-APXXXXX-Super Usuario > Facturas > Ingresar > Facturas");
		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);

		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		login.clickLogIn();
		paginaInicial.clickOnResponsabilidad(responsabilidad3);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta3);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta3);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(invoices);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");

		// Extraer invoice_num del registro de referencia
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
		JsonObject entity = jsonObject.getAsJsonObject("Entity");
		invoice_num = entity.get("INVOICE_NUM").getAsString();
		System.out.println("INVOICE_NUM: " + invoice_num);

		// Paso 2. Ingresa el numero de la factura en el campo Number y da clic en Find
		addStep("Ingresa el numero de la factura en el campo Number y da clic en Find");
		p.clickImagen("find_factura_final");
		u.hardWait(10);
		p.clickImagen("number_final");
		u.hardWait(5);
		p.enviarTexto(invoice_num);
		u.hardWait(5);
		p.clickImagen("find_final");
		u.hardWait(7);
		testCase.addScreenShotCurrentStep("factura_final");

		// Paso 3. Verificar que la factura este validada
		addStep("Verificar que la factura este validada");
		p.clickImagen("validada");
		u.hardWait(7);
		testCase.addScreenShotCurrentStep("validacion");

		// Paso 4. Ingresar a la sección MXOXXO-APXXXXX-SUPER USUARIO/PAYMENTS
		addStep("Ingresar a la sección MXOXXO-APXXXXX-SUPER USUARIO/PAYMENTS");
		p.clickImagen("close_validacion");
		u.hardWait(10);
		p.clickImagen("payments");
		u.hardWait(10);
		p.clickImagen("open2");
		u.hardWait(15);
		p.clickImagen("open2");
		u.hardWait(15);
		p.clickImagen("open2");
		u.hardWait(60);
		testCase.addScreenShotCurrentStep("payments");

		// Paso 5. Ingresar unidad operativa,numero de proveedor(RFC),Fecha de pago,
		// cuenta bancaria, método de pago, payment process profile
		addStep("Ingresar unidad operativa,numero de proveedor(RFC),Fecha de pago, cuenta bancaria, método de pago, payment process profile");

		p.nTAB(1);
		u.hardWait(5);
		p.enviarTexto(unidadOperativa);
		u.hardWait(5);
		p.nTAB(1);
		u.hardWait(5);
		p.enviarTexto(rfc);
		u.hardWait(5);
		p.nTAB(4);
		u.hardWait(5);
		p.clickImagen("date");
		u.hardWait(15);
		p.enter();
		u.hardWait(15);
		p.enviarTexto(cuentaBancaria);
		u.hardWait(5);
		p.nTAB(1);
		u.hardWait(5);
		p.enter();
		u.hardWait(5);
		p.nTAB(1);
		u.hardWait(5);
		p.enviarTexto(paymentProcessProfile);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("payments2");
		p.nTAB(1);
		u.hardWait(5);

		// Paso 6. Click Ingresar/Ajustar facturas, Ingresar numero de factura a pagar y
		// Click en el disco amarillo(guardar)
		addStep("Click Ingresar/Ajustar facturas, Ingresar numero de factura a pagar y Click en el disco amarillo(guardar)	");
		p.clickImagen("enterAdjustInvoices");
		u.hardWait(10);
		p.enviarTexto(invoice_num);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("payments3");
		p.clickImagen("guardar_yellow");
		u.hardWait(20);
		p.enter();
		u.hardWait(40);
		p.cerrarApplicacion2();

		// Paso 7. Consultar el pago mediante el siguiente query:
		// SELECT
		// INVOICE_ID,LAST_UPDATE_DATE,VENDOR_ID,INVOICE_NUM,INVOICE_AMOUNT,AMOUNT_PAID,INVOICE_TYPE_LOOKUP_CODE,AMOUNT_APPLICABLE_TO_DISCOUNT,PAY_GROUP_LOOKUP_CODE,ORG_ID
		// FROM APPS.AP_INVOICES_ALL WHERE INVOICE_NUM IN('Num_factura')
		addStep("Consultar el pago mediante el siguiente query:\r\n"
				+ "SELECT INVOICE_ID,LAST_UPDATE_DATE,VENDOR_ID,INVOICE_NUM,INVOICE_AMOUNT,AMOUNT_PAID,INVOICE_TYPE_LOOKUP_CODE,AMOUNT_APPLICABLE_TO_DISCOUNT,PAY_GROUP_LOOKUP_CODE,ORG_ID\r\n"
				+ "FROM APPS.AP_INVOICES_ALL WHERE INVOICE_NUM IN('Num_factura')\r\n" + "");

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS0 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.AP_INVOICES_ALL: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS1 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.AP_INVOICES_ALL: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 8. Ingresa a la sección
		// MX0XXX0-APXXXXX-CONSULTAS_FISCAL_OS>USUARIO>Concurrent
		addStep("Ingresa a la sección MXOXXXO-APXXXXX-CONSULTAS_FISCAL_OS> Otro > Ejecutar solicitud > USUARIO > Concurrent");
		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		login.clickLogIn();
		paginaInicial.clickOnResponsabilidad(responsabilidad5);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta5);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta5);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);

		p.clickImagen("cancel_inicio");
		u.hardWait(10);
		p.clickImagen("view");
		u.hardWait(5);
		p.clickImagen("requestsUltimo");
		u.hardWait(30);
		testCase.addScreenShotCurrentStep("concurrente fiscal");

		// Paso 9. Ejecutar el concurrente XXFC - Extracción de transacciones de AP para
		// portal de proveedores ; con la compañía 00099, las plazas, Con el catalogo de
		// Pagos y con fecha de ejecución del día de pago
		addStep("Ejecutar el concurrente XXFC - Extracción de transacciones de AP para portal de proveedores ; con la compañía 00099, las plazas, Con el catalogo de Pagos  y con fecha de ejecución del día de pago");

		// Enviar concurrente
		p.clickImagen("submit_a_new_request");
		u.hardWait(10);
		p.pegarTexto(concurrenteNombre);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);
		p.enviarTexto(compania);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);
		p.enviarTexto(crDePlaza);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);
		p.enviarTexto(tipoDeTransaccion);
		p.nTAB(1);
		u.hardWait(10);
		p.enviarTexto(output);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);
		p.enviarTexto(fechaDePagoTransaccion);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);

		p.clickImagen("ok_concurrente_transacciones");

		u.hardWait(10);
		testCase.addScreenShotCurrentStep("concurrente fiscal2");
		p.clickImagen("submit");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("requestConcurrenteTransaccion3");
		p.clickImagen("submitNo");
		u.hardWait(10);

		p.clickImagen("specific_requests");
		p.nTAB(2);
		u.hardWait(10);
		p.pegarTexto(concurrenteNombre);
		u.hardWait(10);
		p.clickImagen("find_concurrente");
		u.hardWait(20);
		p.esperarConcurrenteEnRunning();
		p.clickImagen("view_output");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("reporte concurrente transaccion 4");

		u.hardWait(5);
		log = p.copiarYObtenerTextoDePantalla();
		u.hardWait(10);

		// Extraer id_documento_pago
		id_documento_pago = p.extractIdDocumentoPago(log);
		System.out.println("id_documento_pago: " + id_documento_pago);
		u.hardWait(10);

		// Paso 10. Valida la información del pago en las tablas APPS.XXFC_XP_PAGOS_AP y
		// APPS.XXFC_XP_PAGOS_LINEAS_AP
		addStep("Valida la información del pago en las tablas APPS.XXFC_XP_PAGOS_AP y  APPS.XXFC_XP_PAGOS_LINEAS_AP	");

		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + " '" + id_documento_pago + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.XXFC_XP_PAGOS_AP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS3 + " '" + id_documento_pago + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.XXFC_XP_PAGOS_LINEAS_AP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS4 + " '" + id_documento_pago + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.XXFC_XP_PAGOS_LINEAS_AP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 11. Ejecutar la interface OE14_MX.Pub:runPagos para el envio de pagos
		// hacia el portal
		addStep("Ejecutar la interface OE14_MX.Pub:runPagos para el envio de pagos hacia el portal");
		result = pom.getControlMChrome(GlobalVariables.CONTROL_M_USER_JesusMoreno, GlobalVariables.CONTROL_M_PWD_JesusMoreno,
				GlobalVariables.CONTROL_M_SERVER);
		assertTrue(result, "Fallo al iniciar sesion en CTRL-M");
		u.hardWait(15);
		p.clickImagen("monitoring");
		//u.click(By.xpath("//a[@href='Monitoring' and text()='Monitoring']"));
		u.hardWait(15);
		p.buscarJob(job);
		p.clickDerecho("os");
		u.hardWait(15);
		p.clickImagen("run_options");
		u.hardWait(5);
		p.clickImagen("run_now");
		u.hardWait(5);
		p.enter();
		testCase.addScreenShotCurrentStep("Control M Interface");
		u.hardWait(160);

		// Comprobacion de cambio de status a 'E'
		resultadoContenedor = dbEBS.executeQuery(queryEBS5 + " '" + id_documento_pago + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.XXFC_XP_PAGOS_AP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS6 + " '" + id_documento_pago + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.XXFC_XP_PAGOS_LINEAS_AP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS7 + " '" + id_documento_pago + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla APPS.XXFC_XP_PAGOS_LINEAS_AP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}
		
		//Cerrar ventanas existentes
		p.altF4();
		u.hardWait(2);
		p.altF4();
		u.hardWait(2);
		p.altF4();
		u.hardWait(10);
		p.enter();
		p.altF4();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "* Tener acceso a BD AVEBQA" + "*Tener acceso a Control M";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Realizar el pago de una factura generada mediante el proceso de XXFC-Proceso Facturas Xpertal Prorrateo Central y hacer el envio del pago hacia el portal";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_ProcesamientoDeOrdenesDeProrrateoConYSinError";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

package EBS.Homologacion_CxP_CHG0049044;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Generar mediante el proceso de XXFC-Proceso Facturas Xpertal
 * Prorrateo Central la factura para una plaza con un cr menor a 30 en su
 * combinacion contable y validar su distribucion hasta el nivel plaza
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_005_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Menor_a_30
 * 
 * PREREQUISITOS: Tener acceso a EBS Tener acceso a la BD de AVEBQA
 *
 * INSUMOS (datos o información que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Modificar el JSON (Ubicado en la
 * carpeta Files) de la siguiente manera: Cambiar el campo INVOICE_NUM de
 * Entity, agregandole una unidad a la secuencia Cambiar el campo UUID de uuids,
 * agregandole una unidad a la secuencia y modificando la fecha al final del
 * campo Cambiar el campo INVOICE_NUM de uuids, agregandole una unidad a la
 * secuencia Ajustar el campo FECHA_EMISION_XML de uuids, modificando su valor a
 * la fecha en la que se esté ejecutando el caso de prueba Ajustar el campo
 * FECHA_RECEPCION_XML de uuids, modificando su valor a la fecha en la que se
 * esté ejecutando el caso de prueba
 * 
 * @author Jesus Alberto Moreno Delgado
 * @date 09/01/2025
 */
public class ATC_FT_005_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Menor_a_30 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Menor_a_30_test(HashMap<String, String> data)
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

		// Concurrentes a ejecutar
		String prorrateo = data.get("prorrateo");
		String facturas = data.get("facturas");

		String server = data.get("server");
		String queryEBS1 = data.get("ebs1");
		boolean flag;
		String url = data.get("url");
		String responsabilidad2 = data.get("responsabilidad2");
		String responsabilidad3 = data.get("responsabilidad3");
		String subCarpeta = data.get("subCarpeta");
		String subCarpeta2 = data.get("subCarpeta2");
		String subCarpeta3 = data.get("subCarpeta3");
		String carpeta = data.get("carpeta");
		String carpeta2 = data.get("carpeta2");
		String carpeta3 = data.get("carpeta3");
		String ejecutarSolicitud = data.get("ejecutarSolicitud");
		String invoices = data.get("invoices");
		String path = data.get("path");
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryEBS4 = data.get("ebs4");
		String queryEBS5 = data.get("ebs5");
		String queryEBS6 = data.get("ebs6");
		String queryEBS7 = data.get("ebs7");

		String combinacionContable = null;
		String invoice_num = null;

		// Paso 1. Ingresar a la BD de AVEBQA e ingresar a la tabla
		// XXFC_XP_CARGO_CENTRAL_HEADERS
		// y valida que la información a procesar tenga el wm_status=O
		addStep("Ingresar a la BD de AVEBQA e ingresar a la tabla XXFC_XP_CARGO_CENTRAL_HEADERS "
				+ "y valida que la información a procesar tenga el wm_status=O");

		// Lectura y envío de json a interfaz EO13
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
		JsonObject entity = jsonObject.getAsJsonObject("Entity");
		JsonArray lines = entity.getAsJsonArray("lines");

		if (lines.size() > 0) {
			JsonObject firstLine = lines.get(0).getAsJsonObject();
			combinacionContable = firstLine.get("COMBINACION_CONTABLE").getAsString();
			System.out.println("Combinación Contable: " + combinacionContable);
		}

		// Extraer el valor de "INVOICE_NUM" desde el objeto "entity"
		invoice_num = entity.get("INVOICE_NUM").getAsString();
		System.out.println("INVOICE_NUM: " + invoice_num);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
			httpPost.setEntity(requestEntity);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			flag = (statusCode == 200);
			assertTrue(flag, "La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
		} catch (IOException e) {
			e.printStackTrace();
		}

		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);

		testCase.addBoldTextEvidenceCurrentStep("Datos del json");
		testCase.addBoldTextEvidenceCurrentStep("invoice_num: " + invoice_num);
		testCase.addBoldTextEvidenceCurrentStep("Combinación Contable: " + combinacionContable);

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		// Validaciones en las tablas EBS
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS1 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 2. Ingresar a la tabla XXFC_XP_CARGO_CENTRAL_LINES y
		// valida que el tercer segmento de la combinación contable tenga un CR menor a
		// 30
		addStep("Ingresar a la tabla XXFC_XP_CARGO_CENTRAL_LINES y valida que el tercer "
				+ "segmento de la combinación contable tenga un CR menor a 30");
		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_LINES: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		if (!resultadoContenedor.isEmpty()) {

			// Divide la combinación contable en segmentos
			String[] segmentos = combinacionContable.split("\\.");

			// Asegura de que hay al menos tres segmentos antes de acceder al tercero
			if (segmentos.length >= 3) {
				String tercerSegmento = segmentos[2];

				// Extraer los primeros caracteres del tercer segmento y convertirlos a número
				int prefijo = Integer.parseInt(tercerSegmento.substring(0, 2));

				// Validar que el prefijo sea menor a 30
				if (prefijo < 30) {
					flag = true;
					assertTrue(flag, "El registro existe en la BD y el tercer segmento tiene un CR menor a 30");
				} else {
					flag = false;
					assertTrue(flag, "El registro existe en la BD pero el tercer segmento no tiene un CR menor a 30");
				}
			} else {
				flag = false;
				assertTrue(flag, "La combinación contable no tiene suficientes segmentos");
			}
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 3. Validar que la información a procesar tenga un uuid en la tabla
		// XXFC_XP_RELACION_UUIDS_TMP
		addStep("Validar que la información a procesar tenga un uuid en la tabla XXFC_XP_RELACION_UUIDS_TMP");
		resultadoContenedor = dbEBS.executeQuery(queryEBS3 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_RELACION_UUIDS_TMP con UUID");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 4. Ingresar al portal de EBS entrando al siguiente link:
		// fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
		addStep("Ingresar al portal de EBS entrando al siguiente link: fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
		login.navigate(server);
		testCase.addScreenShotCurrentStep("Acceso al portal");

		// Paso 5. Ingresar el User Name y Password correspondientes del Usuario y dar
		// clic en el botón de Login
		addStep("Ingresar el User Name y Password correspondientes del Usuario y dar clic en el botón de Login");
		login.setCredentials();
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();

		// Paso 6. Ingresar a la responsabilidad MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS > AP Usuario > Otro > Ejecutar solicitud
		addStep("Ingresar a la responsabilidad MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS > AP Usuario > Otro > Ejecutar solicitud");
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta);
		testCase.addScreenShotCurrentStep("save");
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(ejecutarSolicitud);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);
		p.clickImagen("cancel_inicio");
		u.hardWait(10);
		p.clickImagen("view");
		u.hardWait(5);
		p.clickImagen("requestsModificado");
		u.hardWait(30);

		// Paso 7. Seleccionar "Single Request", buscar el nombre del concurrente
		// (XXFC-Proceso Facturas Xpertal Prorrateo Central) y click en submit
		addStep("Seleccionar \"Single Request\", buscar el nombre del concurrente "
				+ "(XXFC-Proceso Facturas Xpertal Prorrateo Central) y click en submit");

		// Enviar concurrente
		p.clickImagen("submit_a_new_request");
		u.hardWait(10);
		p.enviarTexto(prorrateo);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(5);
		p.clickImagen("submit");
		u.hardWait(5);
		p.clickImagen("ok_advertencia2");
		u.hardWait(5);
		u.hardWait(45);

		testCase.addScreenShotCurrentStep("requestConcurrente");
		p.clickImagen("submitNo");

		// Paso 8. Seleccionar View > Requests y seleccionar "Find"
		addStep("Seleccionar View > Requests y seleccionar \"Find\"");
		u.hardWait(10);
		p.clickImagen("find_concurrente");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("Concurrentes");
		u.hardWait(15);

		// Paso 9. Una vez terminada la ejecución, visualizar el log del concurrente
		// XXFC-Proceso Facturas Xpertal Prorrateo Central
		addStep("Una vez terminada la ejecución, visualizar el log del concurrente XXFC-Proceso Facturas Xpertal Prorrateo Central");
		p.clickImagen("find_requests");
		u.hardWait(10);
		p.clickImagen("specific_requests");
		p.nTAB(2);
		u.hardWait(10);
		p.enviarTexto(prorrateo);
		u.hardWait(3);
		p.clickImagen("find_concurrente");
		u.hardWait(60);
		p.clickImagen("viewLog");
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("reporte");
		u.scroll();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("reporte2");
		u.hardWait(10);

		// Paso 10. Validar el Output: Importacion Facturas Prorrateo Central (Payables
		// Open Interface Import)
		addStep("Validar el Output: Importacion Facturas Prorrateo Central (Payables Open Interface Import)");
		p.switchWindowAltTab();
		u.hardWait(5);
		p.clickImagen("find_requests");
		u.hardWait(5);
		p.nTAB(2);
		u.hardWait(10);
		p.enviarTexto(facturas);
		u.hardWait(3);
		p.clickImagen("find_concurrente");
		u.hardWait(30);
		p.clickImagen("view_output");
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("Importacion Facturas Prorrateo Central");
		u.hardWait(10);
		// p.switchWindowAltTab();

		// Paso 11. : Validar que el estado del registro este en ‘G’ , WM_STATUS=’L’ y
        //		se llene el campo fecha de pago(fecha emisión xml + terms) de la tabla
		//		XXFC_XP_CARGO_CENTRAL_HEADERS
		addStep("Validar que el estado del registro este en ‘G’ , WM_STATUS=’L’ y\r\n"
				+ "se llene el campo fecha de pago(fecha emisión xml + terms) de la tabla\r\n"
				+ "XXFC_XP_CARGO_CENTRAL_HEADERS");

		resultadoContenedor = dbEBS.executeQuery(queryEBS4 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 12. Validar que el registro de la tabla UUID temporal sea eliminado y
		// sea copiado a la tabla Historica
		addStep("Validar que el registro de la tabla UUID temporal sea eliminado "
				+ "y sea copiado a la tabla Histórica");
		// Ejecutar el query con el nuevo_invoice_num
		resultadoContenedor = dbEBS.executeQuery(queryEBS5 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_RELACION_UUIDS_TMP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

		// Cambiar la validación: ahora es correcto si no encuentra registros
		if (resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro no existe en la BD, lo cual es correcto.");
		} else {
			flag = false;
			assertTrue(flag, "El registro existe en la BD, lo cual es incorrecto.");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS6 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla histórica: XXFC.XXFC_XP_RELACION_UUIDS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 13. Validar que el registro sea insertado en la tabla
		// XXFC.XXFC_DISTRIBUCION_FACTURAS
		addStep("Validar que el registro sea insertado en la tabla XXFC.XXFC_DISTRIBUCION_FACTURAS");

		resultadoContenedor = dbEBS.executeQuery(queryEBS7 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("XXFC.XXFC_DISTRIBUCION_FACTURAS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		p.cerrarApplicacion3();
		u.hardWait(10);

		// Paso 14. Ingresar al la sección de MXOOXXO-AP17ADM-Usuario_OS_ADTIVO_OS
		// >Prorrateo Central> Prorrateo Central
		addStep("Ingresar al la sección de MXOOXXO-AP17ADM-Usuario_OS_ADTIVO_OS >Prorrateo Central> Prorrateo Central");

		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();
		//paginaInicial.clickOnResponsabilidad(responsabilidad2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta2);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep("Prorrateo Central");

		// Paso 15. Click F11
		addStep("Click F11");
		p.f11(1);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("F11");

		// Paso 16. Click F11
		addStep("Ingresa el numero de Factura generado y da clic en Crtl +F11");
		p.clickImagen("f11_factura");
		p.enviarTexto(invoice_num);
		u.hardWait(10);
		p.ctrlF11();
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("ctrlF11");

		// Paso 17. Selecciona el campo Cia dentro de la sección de Compañías y dar clic
		// en Distribuir Compañía
		addStep("Selecciona el campo Cia dentro de la sección de Compañías y dar clic en Distribuir Compañía");
		p.clickImagen("cia");
		u.hardWait(5);
		p.clickImagen("distribuirCompania");
		u.hardWait(15);
		p.clickImagen("plazas");
		testCase.addScreenShotCurrentStep("distribucion");
		p.cerrarApplicacion2();

		addStep("Ingresar a la responsabilidad\r\n" + "	MXOXXO-APXXXXX-Super Usuario>Invoices>Entry"
				+ "	ingresar el nuevo numero de factura y click en find");

		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();
		paginaInicial.clickOnResponsabilidad(responsabilidad3);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta3);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta3);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(invoices);
		u.hardWait(10);
		// p.clickImagen("ok_advertencia3");
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);

		p.clickImagen("find_factura_final");
		u.hardWait(10);
		p.clickImagen("number_final");
		u.hardWait(5);
		p.enviarTexto(invoice_num);
		u.hardWait(5);
		p.clickImagen("find_final");
		u.hardWait(7);
		testCase.addScreenShotCurrentStep("factura_final");
		p.clickImagen("2lines");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("factura_final2");

		u.getDriver().quit();
		p.cerrarApplicacion2();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\"* Tener acceso a EBS\r\n" + "*Tener acceso a las BD de AVEBQA\"\r\n" + "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Generar mediante el proceso de XXFC-Proceso Facturas Xpertal Prorrateo Central la factura para una plaza con un cr menor a 30 en su combinacion contable y validar su distribucion hasta el nivel plaza";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Menor_a_30";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

package EBS.Homologacion_CxP_CHG0049044;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

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
 * DESCRIPCI�N: Ejecutar el proceso de XXFC-Proceso Facturas Xpertal Prorrateo
 * Central para validar la generaci�n de facturas de prorrateo aun cuando se
 * encuentre un error en el proceso
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_013_ProcesamientoDeOrdenesDeProrrateoConYSinError
 * 
 * PREREQUISITOS: Tener acceso a EBS Tener acceso a la BD de AVEBQA
 *
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Modificar los JSON (Ubicados en la
 * carpeta Files) de la siguiente manera: - Cambiar con la misma especificaci�n
 * del ATC_FT_005_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Menor_a_30
 * cada uno de los 3 archivos, haciendo una secuencia de 3 entre cada ejecuci�n
 * Ejemplo: Archivo 1. JM01 Archivo 2. JM02 Archivo 3. JM03 - Ajustar la celda
 * "mes" en el DataProvider dependiendo en qu� mes se est� corriendo el TC
 * 
 * @author Jesus Alberto Moreno Delgado
 * @date 09/01/2025
 */
public class ATC_FT_013_ProcesamientoDeOrdenesDeProrrateoConYSinError extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_ProcesamientoDeOrdenesDeProrrateoConYSinError_test(HashMap<String, String> data)
			throws Exception {

		// Inicia Selenium y Sikuli despu�s de todas las validaciones
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

		boolean flag;
		String url = data.get("url");
		String path1 = data.get("path1");
		String path2 = data.get("path2");
		String path3 = data.get("path3");
		String queryEBS11 = data.get("ebs11");
		String queryEBS12 = data.get("ebs12");
		String queryEBS13 = data.get("ebs13");
		String queryEBS14 = data.get("ebs14");
		String server = data.get("server");

		String responsabilidad = data.get("responsabilidad");
		String subCarpeta = data.get("subCarpeta");
		String carpeta = data.get("carpeta");
		String ejecutarSolicitud = data.get("ejecutarSolicitud");

		String prorrateo = data.get("prorrateo");
		String facturas = data.get("facturas");

		// Preparacion de insumos antes de ejecutar los pasos
		// path 1: Un json con estructura correcta
		// path 2: Un json con estructura correcta, pero vendor_site_id incorrecto
		// path 3: Un json con estructura correcta
		String[] paths = { path1, path2, path3 };
		List<String> invoiceNumbers = new ArrayList<>();

		for (String path : paths) {
			try {
				// Leer y enviar JSON
				String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
				JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
				JsonObject entity = jsonObject.getAsJsonObject("Entity");

				// Extraer el valor de "INVOICE_NUM" desde el objeto "entity"
				String invoiceNum = entity.get("INVOICE_NUM").getAsString();
				invoiceNumbers.add(invoiceNum);
				System.out.println("INVOICE_NUM: " + invoiceNum);

				try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
					HttpPost httpPost = new HttpPost(url);
					StringEntity requestEntity = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
					httpPost.setEntity(requestEntity);
					HttpResponse response = httpClient.execute(httpPost);
					int statusCode = response.getStatusLine().getStatusCode();
					flag = (statusCode == 200);
					assert flag : "La solicitud se ha enviado correctamente. C�digo de estado: " + statusCode;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		// Paso 1. Ingresar a la BD de AVEBQA e ingresar a la tabla
		// XXFC_XP_CARGO_CENTRAL_HEADERS y validar que la informaci�n a procesar con y
		// sin error de datos tenga el wm_status=O
		addStep("Ingresar a la BD de AVEBQA e ingresar a la tabla XXFC_XP_CARGO_CENTRAL_HEADERS y "
				+ "validar que la informaci�n a procesar con y sin error de datos tenga el wm_status=O");
		String invoiceNums = String.join("', '", invoiceNumbers);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS11 + "('" + invoiceNums + "')");
		testCase.addBoldTextEvidenceCurrentStep("Registro con error: " + invoiceNumbers.get(1));
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC.XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 2. Ingresar a la tabla XXFC_XP_CARGO_CENTRAL_LINES y validar la
		// informaci�n de las ordenes a nivel linea
		addStep("Ingresar a la tabla XXFC_XP_CARGO_CENTRAL_LINES y validar la informaci�n de las ordenes a nivel linea ");
		resultadoContenedor = dbEBS.executeQuery(queryEBS12 + "('" + invoiceNums + "')");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC.XXFC_XP_CARGO_CENTRAL_LINES: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 3. Validar que la informaci�n a procesar tenga un uuid en la tabla
		// XXFC_XP_RELACION_UUIDS_TMP
		addStep("Validar que la informaci�n a procesar tenga un uuid en la tabla XXFC_XP_RELACION_UUIDS_TMP");
		resultadoContenedor = dbEBS.executeQuery(queryEBS13 + "('" + invoiceNums + "')");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC.XXFC_XP_RELACION_UUIDS_TMP: ");
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

		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);
		addStep("Ingresar al portal de EBS entrando al siguiente link: fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
		login.navigate(server);
		testCase.addScreenShotCurrentStep("Acceso al portal");

		// Paso 5. Ingresar el User Name y Password correspondientes del Usuario y dar
		// clic en el bot�n de Login
		addStep("Ingresar el User Name y Password correspondientes del Usuario y dar clic en el bot�n de Login");
		login.setCredentials();
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();

		// Paso 6. Ingresar a la responsabilidad MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS >
		// AP Usuario > Otro > Ejecutar solicitud
		addStep("Ingresar a la responsabilidad MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS > AP Usuario > Otro > Ejecutar solicitud");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
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

		// Paso 9. Una vez terminada la ejecuci�n, visualizar el log del concurrente
		// XXFC-Proceso Facturas Xpertal Prorrateo Central
		addStep("Una vez terminada la ejecuci�n, visualizar el log del concurrente XXFC-Proceso Facturas Xpertal Prorrateo Central");
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
		p.cerrarApplicacion3();
		u.getDriver().quit();

		// Paso 11.Ingresar a la tabla XXFC_XP_CARGO_CENTRAL_HEADERS y valida que el
		// campo de las facturas sin error est�n estado = G y WM_STATUS = L y las
		// facturas con error est�n en estado = P y WM_STATUS = L y el campo eror_msg
		// con los detalles del error
		addStep("Ingresar a la tabla XXFC_XP_CARGO_CENTRAL_HEADERS y valida que el campo de las facturas sin error est�n "
				+ "estado = G y WM_STATUS = L y las facturas con error est�n en estado = P y WM_STATUS = L y el campo eror_msg con los detalles del error");

		resultadoContenedor = dbEBS.executeQuery(queryEBS14 + "('" + invoiceNums + "')");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\"* Tener acceso a EBS\r\n" + "*Tener acceso a las BD de AVEBQA\"\r\n"
				+ "Tener registros con status 'O' con y sin errores\"\r\n";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecutar el proceso de XXFC-Proceso Facturas Xpertal Prorrateo Central para validar la generaci�n de facturas de prorrateo aun cuando se encuentre un error en el proceso";
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

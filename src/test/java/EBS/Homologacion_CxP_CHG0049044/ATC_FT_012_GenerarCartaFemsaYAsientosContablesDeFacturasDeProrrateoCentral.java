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
 * DESCRIPCIÓN: Generar la carta FEMSA y asientos contables de todas las facturas generadas en el día para prorrateo central xpertal
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_012_GenerarCartaFemsaYAsientosContablesDeFacturasDeProrrateoCentral
 * 
 * PREREQUISITOS: 
 * Tener acceso a aplicativo EBS
 * Tener acceso a la BD de AVEBQA
 *	Verificar que existan facturas de prorrateo generadas del dia
 *
 * INSUMOS (datos o información que necesita estar preparada o generada para
 * poder ejecutar el script automatizado):
-  -Haber corrido previamente el ATC_FT_006_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Mayor_a_30

 * @author Jesus Alberto Moreno Delgado
 * @date 09/01/2025
 */
public class ATC_FT_012_GenerarCartaFemsaYAsientosContablesDeFacturasDeProrrateoCentral extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_GenerarCartaFemsaYAsientosContablesDeFacturasDeProrrateoCentral_test(
			HashMap<String, String> data) throws Exception {

		// Inicia Selenium y Sikuli después de todas las validaciones
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


		String server = data.get("server");
		boolean flag;
		String url = data.get("url");
		String responsabilidad2 = data.get("responsabilidad2");
		String subCarpeta2 = data.get("subCarpeta2");
		String carpeta2 = data.get("carpeta2");
		String path = data.get("path");
		String queryEBS8 = data.get("ebs8");
		String cartasProrrateo = data.get("cartasProrrateo");
		String generacionPasivos = data.get("generacionPasivos");
		String mes = data.get("mes");
		String queryEBS9 = data.get("ebs9");
		String queryEBS10 = data.get("ebs10");
		String responsabilidad4 = data.get("responsabilidad4");
		String subCarpeta4 = data.get("subCarpeta4");
		String carpeta4 = data.get("carpeta4");

		String combinacionContable = null;
		String invoice_num = null;

		String log = null;
		String currentRequestId = null;
		String batchName = null;
		String pie = null;
		String cartaFemsaId = null;

		// Lectura y envío de json a interfaz EO13
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
//				testCase.addBoldTextEvidenceCurrentStep("JSON Utilizado en la prueba: ");
//				testCase.addBoldTextEvidenceCurrentStep(jsonContent);

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
			System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
		} catch (IOException e) {
			e.printStackTrace();
		}


		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		
		// Paso 1.Verificar que existan facturas de prorrateo generadas del dia
		addStep("Verificar que existan facturas de prorrateo generadas del dia");
		utils.sql.SQLResult  resultadoContenedor = dbEBS.executeQuery(queryEBS8);
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC.XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 2. Ingresar al portal de EBS entrando al siguiente link:
		// fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
		addStep("Ingresar al portal de EBS entrando al siguiente link: fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
		login.navigate(server);
		testCase.addScreenShotCurrentStep("Acceso al portal2");

		// Paso 3. Ingresar el User Name y Password correspondientes del Usuario y dar
		// clic en el botón de Login
		addStep("Ingresar el User Name y Password correspondientes del Usuario y dar clic en el botón de Login");
		login.setCredentials();
		testCase.addScreenShotCurrentStep("Ingreso de credenciales2");
		login.clickLogIn();

		// Paso 4. Ingresar al correspondiente MXOOXXO-AP17ADM-Usuario_OS_ADTIVO_OS
		// >Prorrateo Central> Prorrateo Central
		addStep("Ingresar al correspondiente MXOOXXO-AP17ADM-Usuario_OS_ADTIVO_OS >Prorrateo Central> Prorrateo Central");
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta2);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep("Prorrateo Central");

		// Paso 5. Click en view, Seleccionar "Request", buscar el nombre del
		// concurrente XXFC-Generacion Cartas Interempresa Prorrateo Central y dar clic
		// en submit
		addStep("Click en view, Seleccionar \"Request\", buscar el nombre del concurrente XXFC-Generacion Cartas Interempresa Prorrateo Central y dar clic en submit");
		p.clickImagen("viewProrrateo");
		u.hardWait(5);
		p.clickImagen("requestsProrrateo");
		u.hardWait(30);

		// Enviar concurrente
		p.clickImagen("submit_a_new_request");
		u.hardWait(10);
		p.enviarTexto(cartasProrrateo);
		u.hardWait(3);
		p.nTAB(1);
		u.hardWait(10);
		p.clickImagen("submit");
		u.hardWait(5);
		// p.buscarAdvertencia2();
		p.clickImagen("ok_advertencia2");
		u.hardWait(5);
		u.hardWait(45);
		testCase.addScreenShotCurrentStep("requestConcurrenteProrrateo");
		p.clickImagen("submitNo");

		// Paso 6. Click en no, seleccionar All my Requests y seleccionar "Find"
		addStep("Click en no, seleccionar All my Requests y seleccionar \"Find\"");
		u.hardWait(10);
		p.clickImagen("find_concurrente");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("ConcurrentesProrrateo");
		u.hardWait(15);

		// Paso 7. Una vez terminada la ejecución, visualizar el log del concurrente
		// XXFC- Generación cartas interempresa prorrateo central
		addStep("Una vez terminada la ejecución, visualizar el log del concurrente XXFC- Generación cartas interempresa prorrateo central");
		p.clickImagen("find_requests");
		u.hardWait(10);
		p.clickImagen("specific_requests");
		p.nTAB(2);
		u.hardWait(10);
		p.enviarTexto(cartasProrrateo);
		u.hardWait(3);
		p.clickImagen("find_concurrente");
		u.hardWait(30);
		p.clickImagen("viewLog");
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("reporteProrrateo");
		u.scroll();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("reporteProrrateo2");
		u.hardWait(10);

		// Paso 8. Validar el Output: GL - ERP - Generacion Pasivos (Journal Import)
		addStep("Validar el Output: GL - ERP - Generacion Pasivos (Journal Import)");
		p.switchWindowAltTab();
		u.hardWait(5);
		p.clickImagen("find_requests");
		u.hardWait(5);
		p.nTAB(2);
		u.hardWait(10);
		p.enviarTexto(generacionPasivos);
		u.hardWait(3);
		p.clickImagen("find_concurrente");
		u.hardWait(30);
		p.clickImagen("view_output");
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("Generacion Pasivos Prorrateo Central");
		u.hardWait(5);
		log = p.copiarYObtenerTextoDePantalla();
		u.hardWait(10);

		// Extraer Concurrent Request ID
		currentRequestId = p.extractConcurrentRequestId(log);
		// Extraer Batch Name
		batchName = p.extractBatchName(log, mes);
		// Extraer PIE
		pie = p.extractPIE(batchName);

		System.out.println("Batch Name: " + batchName);
		System.out.println("PIE: " + pie);

		// Paso 9. Validar que se generen datos de las facturas en las tablas
		// XXFC_CARTAS_FEMSA y XXFC_CARTAS_FEMSA_LINEAS
		addStep("Validar que se generen datos de las facturas en las tablas XXFC_CARTAS_FEMSA y XXFC_CARTAS_FEMSA_LINEAS");
		resultadoContenedor = dbEBS.executeQuery(queryEBS9 + " '" + pie + "'");
		testCase.addBoldTextEvidenceCurrentStep("XXFC_CARTAS_FEMSA: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
			cartaFemsaId = resultadoContenedor.getData(0, 0);
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS10 + " '" + cartaFemsaId + "'");
		testCase.addBoldTextEvidenceCurrentStep("XXFC_CARTAS_FEMSA_LINEAS: ");
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

		// Paso 10. Ingresar a la responsabilidad MXOXXO-GLXXXXX-Super
		// Usuario>Journals>Enter ingresar el Batch del output + ResquestID y click en
		// find
		addStep("Ingresar a la responsabilidad MXOXXO-GLXXXXX-Super Usuario>Journals>Enter ingresar el Batch del output + ResquestID y click en find");

		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();
		paginaInicial.clickOnResponsabilidad(responsabilidad4);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta4);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta4);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);

		p.enviarTexto(batchName + " " + currentRequestId);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Journals1");
		p.clickImagen("find_batch");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Journals2");
		p.clickImagen("reviewJournal");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Journals3");
		u.hardWait(5);

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
		return "\"* Tener acceso a EBS\r\n" + "*Tener acceso a las BD de AVEBQA\"\r\n"
				+ "Verificar que existan facturas de prorrateo generadas del dia\"\r\n";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Generar la carta FEMSA y asientos contables de todas las facturas generadas en el día para prorrateo central xpertal";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_GenerarCartaFemsaYAsientosContablesDeFacturasDeProrrateoCentral";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

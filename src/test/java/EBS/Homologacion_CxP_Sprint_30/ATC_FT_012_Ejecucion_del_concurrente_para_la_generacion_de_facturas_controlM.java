package EBS.Homologacion_CxP_Sprint_30;

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
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.CTRL_M_UTIL;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Ejecutar mediante control M el JOB XXFCAPXPCRGCNTRLS_BIN_MEX  que activa el concurrente  XXFC-Proceso de Cargo Central  para la generacion de facturas en pantallas de cargo central central y AP
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_012_Ejecucion_del_concurrente_para_la_generacion_de_facturas_controlM
*  
* PREREQUISITOS:
*	Tener acceso a las BD de AVEBQA
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - NA
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_012_Ejecucion_del_concurrente_para_la_generacion_de_facturas_controlM extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_Ejecucion_del_concurrente_para_la_generacion_de_facturas_controlM_test(
			HashMap<String, String> data) throws Exception {

		// Inicia Selenium y Sikuli después de todas las validaciones
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());

		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		//Jobs
		JSONObject obj = new JSONObject(data.get("job"));

		// Registro verificado de factura existente
		String invoice_num = data.get("invoice_num");

		String server = data.get("server");
		String queryEBS1 = data.get("ebs1");
		boolean flag;
		String url = data.get("url");
		String responsabilidad = data.get("responsabilidad");
		String responsabilidad2 = data.get("responsabilidad2");
		String subCarpeta = data.get("subCarpeta");
		String subCarpeta2 = data.get("subCarpeta2");
		String carpeta = data.get("carpeta");
		String carpeta2 = data.get("carpeta2");
		String invoices = data.get("invoices");
		String path = data.get("path");
		String headerIdFromEBS7 = null;
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryEBS4 = data.get("ebs4");
		String queryEBS5 = data.get("ebs5");
		String queryEBS6 = data.get("ebs6");
		String queryEBS7 = data.get("ebs7");
		String queryEBS8 = data.get("ebs8");
		String queryEBS9 = data.get("ebs9");
		String queryEBS10 = data.get("ebs10");
		String queryEBS11 = data.get("ebs11");
		String rfc = data.get("rfc");
		String idHeaderFromEBS1 = null;

		String carta_femsa_query = data.get("carta_femsa_query");
		String invoice_id_query = data.get("invoice_id_query");
		String carta_femsa_id = null;
		String invoice_id = null;

		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);
		
		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
        ControlM controlM = new ControlM(u, testCase, obj);

		// Paso 1. Validar que la información de xpertal llegue a la tablas staggin:
		// XXFC_XP_CARGO_CENTRAL_HEADERS y XXFC_XP_CARGO_CENTRAL_LINES
		addStep("Validar que la información de xpertal llegue a la tablas staggin: "
				+ "XXFC_XP_CARGO_CENTRAL_HEADERS y XXFC_XP_CARGO_CENTRAL_LINES");
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		testCase.addBoldTextEvidenceCurrentStep("JSON Utilizado en la prueba: ");
		testCase.addBoldTextEvidenceCurrentStep(jsonContent);

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

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		// Validaciones en las tablas EBS
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS1 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
			idHeaderFromEBS1 = resultadoContenedor.getData(0, 2);
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_LINES: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 2. Validar que la información del XML de xpertal llegue a la tabla UUID
		// XXFC_XP_RELACION_UUIDS_TMP
		addStep("Validar que la información del XML de xpertal llegue a la tabla UUID XXFC_XP_RELACION_UUIDS_TMP");
		resultadoContenedor = dbEBS.executeQuery(queryEBS3 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_RELACION_UUIDS_TMP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// PASOS DE CONTROL M
		
		//Paso 3. Ingresar a Control M
		
		CTRL_M_UTIL job = new CTRL_M_UTIL(u, testCase); 
		job.getControlM();
		
		
		//ControlM cm = new ControlM(u, testCase, obj);
		ControlM control = new ControlM(u, testCase, obj);
		try {
			control.searchJob();
			System.out.println("try block");
		} catch (Exception e) {
			By msg = By.cssSelector(
					"body > div.popover.ng-isolate-scope.bottom.start-viewpoints.fade.in.bottom-left > div.popover-inner > div > div > div.rightArea > a");

			u.waitWebElement(msg, 50, 1);
			u.click(msg);

			u.hardWait(2);

			By planning = By.cssSelector("#WcmDomainTabHeader > div > a");
			u.waitWebElement(planning, 120, 1);
			u.clear(planning);
			u.hardWait(2);

			control.searchJob();
			System.out.println("catch block");
		}

		String resultadoEjecucion = control.executeJob();
		u.hardWait(5);
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " + output);
		testCase.addTextEvidenceCurrentStep("Output: " + output);
		control.closeViewpoint();

		if (resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition")) {
			//return true;
		} else {
			//return false;
		}
		
		///////////////////////

		// Paso 8. Validar que el estado del registro este en â€˜Gâ€™ ,
		// WM_STATUS=O y se llene el campo fecha de pago(fecha creación factura +
		// terms)
		// de la tabla XXFC_XP_CARGO_CENTRAL_HEADERS
		addStep("Validar que el estado del registro este en â€˜Gâ€™ , WM_STATUS=O y se llene "
				+ "el campo fecha de pago(fecha creación factura + terms) de la tabla "
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

		// Paso 9. Validar que el registro de la tabla UUID temporal sea eliminado y
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

		// Paso 10. Validar que los registros de las tablas staggin
		// xpertal(XXFC_XP_CARGO_CENTRAL_HEADERS y XXFC_XP_CARGO_CENTRAL_Lines)
		// sean copiadas a las tablas históricas de cargos centrales
		addStep(" Validar que los registros de las tablas staggin xpertal(XXFC_XP_CARGO_CENTRAL_HEADERS "
				+ "y XXFC_XP_CARGO_CENTRAL_Lines) sean copiadas a las tablas históricas de cargos centrales");

		resultadoContenedor = dbEBS.executeQuery(queryEBS7 + " '" + invoice_num + "'");
		testCase.addBoldTextEvidenceCurrentStep("XXFC.XXFC_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
			headerIdFromEBS7 = resultadoContenedor.getData(0, 0);
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS8 + " '" + headerIdFromEBS7 + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla Historica XXFC_CARGO_CENTRAL_LINES: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 11. Validar que se generen datos de la factura en las tablas
		// XXFC_CARTAS_FEMSA y XXFC_CARTAS_FEMSA_LINEAS (Buscar por carta_femsa_id)
		addStep(" Validar que se generen datos de la factura en las tablas "
				+ "XXFC_CARTAS_FEMSA y XXFC_CARTAS_FEMSA_LINEAS (Buscar por carta_femsa_id)");

		resultadoContenedor = dbEBS.executeQuery(carta_femsa_query + " '" + invoice_num + "'");
		carta_femsa_id = resultadoContenedor.getData(0, 0);

		resultadoContenedor = dbEBS.executeQuery(queryEBS9 + " '" + carta_femsa_id + "'");
		testCase.addBoldTextEvidenceCurrentStep("xxfc.xxfc_cartas_femsa: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		resultadoContenedor = dbEBS.executeQuery(queryEBS10 + " '" + carta_femsa_id + "'");
		testCase.addBoldTextEvidenceCurrentStep("xxfc.xxfc_cartas_femsa_lineas: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 12. Validar que se insertaron los datos en la tabla AP.AP_INVOICES_ALL
		// (Buscar por invoice_id)
		addStep("Validar que se insertaron los datos en la tabla AP.AP_INVOICES_ALL (Buscar por invoice_id)");

		resultadoContenedor = dbEBS.executeQuery(invoice_id_query + " '" + invoice_num + "'");
		invoice_id = resultadoContenedor.getData(0, 0);

		resultadoContenedor = dbEBS.executeQuery(queryEBS11 + " '" + invoice_id + "'");
		testCase.addBoldTextEvidenceCurrentStep("Tabla AP.AP_INVOICES_ALL: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		//Paso 13. Ingresar a EBS e ir a la responsabilidad 
		//MXOXXO-APXXXXX-Super Usuario>Invoices>Entry ingresar el numero de factura y click en find
		addStep("Ingresar a EBS e ir a la responsabilidad MXOXXO-APXXXXX-Super Usuario>Invoices>Entry ingresar el numero de factura y click en find");

		login.navigate(server);
		login.setCredentials();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(carpeta2);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(invoices);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);

		p.clickImagen("find_factura_final");
		u.hardWait(10);
		p.clickImagen("supplier_number_final");
		u.hardWait(8);
		p.enviarTexto(rfc);
		u.hardWait(5);
		p.clickImagen("number_final");
		u.hardWait(5);
		p.enviarTexto(invoice_num);
		u.hardWait(5);
		p.clickImagen("find_final");
		u.hardWait(7);
		testCase.addScreenShotCurrentStep("factura_final");
		u.hardWait(3);
		p.cerrarApplicacion2();
		
		//Paso 14. Ingresar a la responsabilidad 
		//MXOXXO-CC17FAA-Usuario_ACT_FIJO_OS >Cargos centrales>Captura Cargos centrales, 
		//ingresar el numero de factura y click en find
		
		addStep("Ingresar a la responsabilidad "
				+ "MXOXXO-CC17FAA-Usuario_ACT_FIJO_OS >Cargos centrales>Captura Cargos centrales, "
				+ "ingresar el numero de factura y click en find\r\n"
				+ "");
		login.navigate(server);
		login.setCredentials();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(subCarpeta);
		
		paginaInicial.clickOnResponsabilidad(carpeta);
		u.hardWait(10);
		p.accepApp();
		u.hardWait(60);
		p.clickImagen("cancel_inicio");
		u.hardWait(10);
		
		p.clickImagen("open_cargos_centrales");
		u.hardWait(5);
		p.clickImagen("open_cargos_centrales");
		u.hardWait(20);

		p.clickImagen("find_factura");
		u.hardWait(6);
		p.clickImagen("supplier_number");
		u.hardWait(5);
		p.enviarTexto(rfc);
		u.hardWait(3);
		p.clickImagen("number");
		u.hardWait(3);
		p.enviarTexto(invoice_num);
		u.hardWait(3);
		p.clickImagen("find_factura_ci");
		testCase.addScreenShotCurrentStep("facturaSuperUsuario");
		u.hardWait(5);
		u.getDriver().quit();
		p.cerrarApplicacion();
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
		return "Ejecutar mediante control M el JOB XXFCAPXPCRGCNTRLS_BIN_MEX  que activa el concurrente  XXFC-Proceso de Cargo Central  para la generacion de facturas en pantallas de cargo central central y AP";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_Ejecucion_del_concurrente_para_la_generacion_de_facturas_controlM_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

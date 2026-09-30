package WM.interfaces.EO13_MX;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;

public class ATC_FT_008_Recibir_facturas_de_cargos_centrales_repetidas_con_wm_status_P extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_Recibir_facturas_de_cargos_centrales_repetidas_con_wm_status_P_test(
			HashMap<String, String> data) throws Exception {

		
		
		addStep("Validar que la información en las tablas de EBS tiene wm_status=’P’ (XXFC_XP_CARGO_CENTRAL_HEADERS)");

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningún campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryEBS1.isEmpty() || !queryEBS2.isEmpty() || !queryEBS3.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

		
		//Validar que la información en las tablas de EBS tiene wm_status=’P’ (XXFC_XP_CARGO_CENTRAL_HEADERS)
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		addStep("Ejecutar el JSON usado anteriormente de la interfaz EO13MX_CCC en POSTMAN (del registro existente con WM_STATUS = ‘P’");

		
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		testCase.addBoldTextEvidenceCurrentStep("JSON Utilizado en la prueba: ");
		testCase.addBoldTextEvidenceCurrentStep(jsonContent);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
			httpPost.setEntity(requestEntity);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			// Leer el contenido de la respuesta
		    String responseContent = EntityUtils.toString(response.getEntity());
			
			flag = false;
			if (statusCode == 200) {
				flag = true;
				assertTrue(flag, "La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
				System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
				System.out.println("Respuesta: " + responseContent);
				testCase.addBoldTextEvidenceCurrentStep(responseContent);
			} else {
				System.out.println("Se produjo un error al enviar la solicitud. Código de estado: " + statusCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addStep("Validar en la tabla WMLOG.WM_LOG_RUN el campo status con valor 'S' en WMLOG");

		// queryWMLOG
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2, GlobalVariables.DB_USER_FCWMLQAv2,
				GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryWMLOG);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			wm_run_id = resultadoContenedor.getData(0, 0);
			assertTrue(flag, "El registro existe en la BD");
		}

		addStep("Validar que la información se mantenga en las tablas de EBS con WM_STATUS= ‘P’ (XXFC_XP_CARGO_CENTRAL_HEADERS)");

		// queryEBS1
		dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		resultadoContenedor = dbEBS.executeQuery(queryEBS1 + wm_run_id);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		String idHeaderFromEBS1 = null;
		if (!resultadoContenedor.isEmpty()) {
			idHeaderFromEBS1 = resultadoContenedor.getData(0, 1);
		}

		// queryEBS2

		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + idHeaderFromEBS1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

		// queryEBS3

		resultadoContenedor = dbEBS.executeQuery(queryEBS3);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso a BD de AVEBQA y FCWMLQA, contar con al menos un registro en la tabla de EBS XXFC_XP_CARGO_CENTRAL_HEADERS con wm_status = 'P'";
	}

	@Override
	public String setTestDescription() {
		return "Al ejecutar la interface recepción de cargos centrales y el valor WM_STATUS de la tabla XXFC_XP_CARGO_CENTRAL_HEADERS sea igual a \"P\" se registre error en la ejecución\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_Recibir_facturas_de_cargos_centrales_repetidas_con_wm_status_P";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
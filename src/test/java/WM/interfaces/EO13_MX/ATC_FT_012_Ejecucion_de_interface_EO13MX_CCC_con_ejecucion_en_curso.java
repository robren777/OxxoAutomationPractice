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
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;

public class ATC_FT_012_Ejecucion_de_interface_EO13MX_CCC_con_ejecucion_en_curso extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_Ejecucion_de_interface_EO13MX_CCC_con_ejecucion_en_curso_test(HashMap<String, String> data)
			throws Exception {

		addStep("Verificar que haya registros con status 'E' a la par");

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryEBS1 = data.get("ebs1");
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningún campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryEBS1.isEmpty()) {
			flag = true;
			assertTrue(flag, "Los registros en el Data Provider estan completos");
		}
		
		
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		utils.sql.SQLResult resultadoContenedor = dbWM.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

		addStep("Ejecutar servicios de las interfaces EO13MX_CCC");
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		testCase.addBoldTextEvidenceCurrentStep("JSON Utilizado en la prueba: ");
		testCase.addBoldTextEvidenceCurrentStep(jsonContent);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
			httpPost.setEntity(requestEntity);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			flag = false;
			if (statusCode == 200) {
				flag = true;
				assertTrue(flag, "La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
				System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
			} else {
				System.out.println("Se produjo un error al enviar la solicitud. Código de estado: " + statusCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

		addStep("Consultar la tabla WMLOG.wm_log_run en WMLOG\r\n"
				+ "");

		// queryWMLOG
		dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2, GlobalVariables.DB_USER_FCWMLQAv2,
				GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryWMLOG);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			wm_run_id = resultadoContenedor.getData(0, 0);
			assertTrue(flag, "El registro existe en la BD");
		}

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		addStep("\" Validar que la información se inserte en las tablas de EBS con wm_status=’O’\r\n"
				+ "XXFC_XP_CARGO_CENTRAL_HEADERS\"\r\n"
				+ "");

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
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso a BD de AVEBQA, FCWMLQ con registros con estatus 'E' en la tabla";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al ejecutar la interfaz y ya se encuentre otra ejecución en curso permita esa segunda ejecución  \r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Recibir_facturas_de_cargos_centrales_con_UUIDS_relacionados";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

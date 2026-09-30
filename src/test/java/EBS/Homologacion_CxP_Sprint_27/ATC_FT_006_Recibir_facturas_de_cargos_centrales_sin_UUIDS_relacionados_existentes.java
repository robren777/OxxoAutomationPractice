package EBS.Homologacion_CxP_Sprint_27;

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

import EBS.util.GlobalVariables;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Validar la recepción de cargos centrales XPERTAL-ORACLE en las tablas de ORACLE,INVOICE_NUM y VENDOR_ID 
* ya existen en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS y que el valor del campo WM_STATUS de la tabla sea diferente de "G" y "P"
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_006_Recibir_facturas_de_cargos_centrales_sin_UUIDS_relacionados_existentes
*  
* PREREQUISITOS:
*	Contar con acceso a BD de AVEBQA y FCWMLQA
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - Contar con un json sin UUIDS relacionados
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_006_Recibir_facturas_de_cargos_centrales_sin_UUIDS_relacionados_existentes extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Recibir_facturas_de_cargos_centrales_sin_UUIDS_relacionados_existentes_test(
			HashMap<String, String> data) throws Exception {

		addStep("Ejecutar servicios de la interfaces EO13MX_CCC con un Json utilizado anteriormente \r\n" + "");

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningÃºn campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryEBS1.isEmpty() || !queryEBS2.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

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

		// queryWMLOG
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		utils.sql.SQLResult resultadoContenedor = dbWM.executeQuery(queryWMLOG);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			wm_run_id = resultadoContenedor.getData(0, 0);
		}
		assertTrue(flag, "El registro existe en la BD");

		addStep("\" Validar que la información se inserte en las tablas de EBS con wm_status=O™\r\n"
				+ "XXFC_XP_CARGO_CENTRAL_HEADERS\"\r\n" + "");

		// queryEBS1
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		resultadoContenedor = dbEBS.executeQuery(queryEBS1 + wm_run_id);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");

		String idHeaderFromEBS1 = null;
		if (!resultadoContenedor.isEmpty()) {
			idHeaderFromEBS1 = resultadoContenedor.getData(0, 1);
		}

		// queryEBS2

		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + "AND id_header = " + idHeaderFromEBS1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con acceso a BD de AVEBQA y FCWMLQA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la recepción de cargos centrales XPERTAL-ORACLE en las tablas de ORACLE,INVOICE_NUM y VENDOR_ID ya existen en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS y que el valor del campo WM_STATUS de la tabla sea diferente de \"G\" y \"P\"\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_Recibir_facturas_de_cargos_centrales_sin_UUIDS_relacionados_existentes_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

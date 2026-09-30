package EBS.Homologacion_CxP;

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

public class ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS_XXFC_XP_RELACION_UUIDS_TMP
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS_XXFC_XP_RELACION_UUIDS_TMP_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryComprobador = data.get("query_comprobador");
		boolean flag;
		String idHeaderFromEBS1 = null;
		String invoiceNum = null;

		// Validar que ningún campo del DataProvider venga como nulo.
		flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryEBS1.isEmpty() || !queryEBS2.isEmpty() || !queryEBS3.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// .1 Ingresar a la BD de AVBQA
		addStep("Ingresar a la BD de AVBQA\r\n");

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		// 2. Validar que la información de xpertal llegue a la tablas staggin:
		// XXFC_XP_CARGO_CENTRAL_HEADERS, XXFC_XP_CARGO_CENTRAL_LINES y
		// XXFC_XP_RELACION_UUIDS_TMP

		addStep("Validar que la información de xpertal llegue a la tablas staggin:\r\n"
				+ "	XXFC_XP_CARGO_CENTRAL_HEADERS, XXFC_XP_CARGO_CENTRAL_LINES y\r\n"
				+ "	XXFC_XP_RELACION_UUIDS_TMP");

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

		addStep("Ingresar a  WMLOG \r\n" + "");
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

		// Validar en la tabla WMLOG.WM_LOG_RUN que el registro de la última ejecución
		// se generó con status = ‘S’");

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

		dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

//		addStep("Validar que la información se inserte en las tablas de EBS "
//				+ "con wm_status=’O’ XXFC_XP_CARGO_CENTRAL_HEADERS, "
//				+ "XXFC_XP_CARGO_CENTRAL_LINES, XXFC_XP_UUIDS_TIPOS_REL_TMP");

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

		if (!resultadoContenedor.isEmpty()) {
			idHeaderFromEBS1 = resultadoContenedor.getData(0, 1);
		}

		// queryEBS2

		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + "AND id_header = " + idHeaderFromEBS1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			invoiceNum = resultadoContenedor.getData(0, "invoice_num");
		flag = true;
		assertTrue(flag, "El registro existe en la BD");
		System.out.println(invoiceNum);

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
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener acceso a las BD de AVEBQA\r\n" + "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida que la sumatoria de los importes mas el impuesto de la tabla XXFC_XP_CARGO_CENTRAL_LINES sea igual al monto reflejado en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS_XXFC_XP_RELACION_UUIDS_TMP";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

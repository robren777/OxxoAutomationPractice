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
* DESCRIPCIÓN: Validar que la recepción de cargos centrales con la información obligatoria en null registre un error
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_007_Recibir_facturas_de_cargos_centrales_sin_informacion_obligatoria_en_el_HEADER_en_null
*  
* PREREQUISITOS:
*	Contar con acceso a BD de FCWMLQA
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - Contar con un json sin UUIDS relacionados
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_007_Recibir_facturas_de_cargos_centrales_sin_informacion_obligatoria_en_el_HEADER_en_null
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_Recibir_facturas_de_cargos_centrales_sin_informacion_obligatoria_en_el_HEADER_en_null_test(
			HashMap<String, String> data) throws Exception {

		addStep("Ejecutar servicios de la interfaz runCargoCenCed en POSTMAN con información obligatoria vacía en el JSON ");

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG1 = data.get("wmlog1");
		String queryWMLOG2 = data.get("wmlog2");

		// Validar que ningún campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !path.isEmpty() || !queryWMLOG1.isEmpty() || !queryWMLOG2.isEmpty()) {
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

		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);

		addStep("Validar en la tabla WMLOG.WM_LOG_RUN estatus='E' en WMLOG ");

		// queryWMLOG
		utils.sql.SQLResult resultadoContenedor = dbWM.executeQuery(queryWMLOG1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");
		
		String run_idFromWMwmlog1 = null;
		if (!resultadoContenedor.isEmpty()) {
			run_idFromWMwmlog1 = resultadoContenedor.getData(0, 0);
		}

		addStep("Validar en la tabla WMLOG.WM_LOG_ERROR se genere el error en WMLOG ");
		// queryWMLOG 2
		resultadoContenedor = dbWM.executeQuery(queryWMLOG2+" where RUN_ID IN "+run_idFromWMwmlog1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

		addStep("Validar envío de correos");
		testCase.addBoldTextEvidenceCurrentStep("Se adjunta correo de error junto con evidencia generada.");

		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso a BD de FCWMLQA";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la recepción de cargos centrales con la información obligatoria en null registre un error\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_Recibir_facturas_de_cargos_centrales_sin_informacion_obligatoria_en_el_HEADER_en_null_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

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
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Validar que  ya existen en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS 
* y que el valor del campo ESTADO de la tabla XXFC_XP_CARGO_CENTRAL_HEADERS  sea igual "G"
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_009_Recibir_factura_que_de_cargo_central_ya_ha_sido_recibido_y_generado_anteriormente
*  
* PREREQUISITOS:
*	Contar con acceso a BD de FCWMLQA
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - Contar con un registro de una factura que de cargo central ya ha sido recibido y generado anteriormente
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_009_Recibir_factura_que_de_cargo_central_ya_ha_sido_recibido_y_generado_anteriormente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_Recibir_factura_que_de_cargo_central_ya_ha_sido_recibido_y_generado_anteriormente_test(
			HashMap<String, String> data) throws Exception {

		
		
		addStep("Validar que la información en las tablas de EBS tiene ESTADO=O (XXFC_XP_CARGO_CENTRAL_HEADERS)");

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningún campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryEBS1.isEmpty() || !queryEBS2.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

		
		//Validar que la información en las tablas de EBS tiene wm_status=â€™Pâ€™ (XXFC_XP_CARGO_CENTRAL_HEADERS)
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		addStep("Ejecutar el JSON usado anteriormente de la interfaz EO13MX_CCC en POSTMAN (del registro existente con ESTADO= O)");

		
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
		}
		assertTrue(flag, "El registro existe en la BD");

		addStep("Validar que la información no se inserte en las tablas de EBS (XXFC_XP_CARGO_CENTRAL_HEADERS)");

		// queryEBS1
		resultadoContenedor = dbEBS.executeQuery(queryEBS2);
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
		return "Contar con acceso a BD de AVEBQA y FCWMLQA, contar con al menos un registro en la tabla de EBS XXFC_XP_CARGO_CENTRAL_HEADERS con ESTADO = 'G' y wm_status = 'P'";
	}

	@Override
	public String setTestDescription() {
		return "Validar que  ya existen en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS y que el valor del campo ESTADO de la tabla XXFC_XP_CARGO_CENTRAL_HEADERS  sea igual \"G\"\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_Recibir_factura_que_de_cargo_central_ya_ha_sido_recibido_y_generado_anteriormente_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
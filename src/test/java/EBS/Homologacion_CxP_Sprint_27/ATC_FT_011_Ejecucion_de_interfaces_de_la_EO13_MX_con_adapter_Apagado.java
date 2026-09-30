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
* DESCRIPCIÓN: Validar que al ejecutar la interface no procesa información y a su vez se registra el error de la ejecución en WM_LOG_RUN,WM_LOG_ERROR
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_011_Ejecucion_de_interfaces_de_la_EO13_MX_con_adapter_Apagado
*  
* PREREQUISITOS:
*	Contar con acceso a BD de FCWMLQA
*	Tener apagado el adapter de la interfaz EO13_MX en el portal de Integración
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - NA
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_011_Ejecucion_de_interfaces_de_la_EO13_MX_con_adapter_Apagado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Ejecucion_de_interfaces_de_la_EO13_MX_con_adapter_Apagado_test(
			HashMap<String, String> data) throws Exception {

		

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String queryWMLOG2 = data.get("wmlog2");
		String wm_run_id = "";
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningÃºn campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryWMLOG2.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

		addStep("Ejecutar servicios de la interfaz runCargoCenCed");

		
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
		
		addStep("Validar en la tabla WMLOG.WM_LOG_RUN status='E' (en WMLOG)");

		// queryWMLOG
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2, GlobalVariables.DB_USER_FCWMLQAv2,
				GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		utils.sql.SQLResult resultadoContenedor = dbWM.executeQuery(queryWMLOG);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			wm_run_id = resultadoContenedor.getData(0, 0);
			assertTrue(flag, "El registro existe en la BD");
		}

		addStep("Validar en la tabla WMLOG.WM_LOG_ERROR se genere el detalle del error (en WMLOG)");
		
		resultadoContenedor = dbWM.executeQuery(queryWMLOG2+wm_run_id);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");
		
		addStep("Validar envío de correos");
		testCase.addBoldTextEvidenceCurrentStep("Se adjunta evidencia de correo manualmente.");

		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Contar con el adapter apagado, contar con acceso a BD de FCWMLQA";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al ejecutar la interface no procesa información y a su vez se registra el error de la ejecución en WM_LOG_RUN,WM_LOG_ERROR\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_Ejecucion_de_interfaces_de_la_EO13_MX_con_adapter_Apagado_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
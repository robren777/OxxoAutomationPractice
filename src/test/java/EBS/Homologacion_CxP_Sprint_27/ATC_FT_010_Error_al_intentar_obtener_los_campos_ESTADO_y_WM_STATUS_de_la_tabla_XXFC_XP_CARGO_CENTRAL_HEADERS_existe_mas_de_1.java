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
* DESCRIPCIÓN: Validar que al tratar de recibir una factura que tiene 2 o mas registros con el mismo vendor_id e invoice_num regrese un error
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_010_Error_al_intentar_obtener_los_campos_ESTADO_y_WM_STATUS_de_la_tabla_XXFC_XP_CARGO_CENTRAL_HEADERS_existe_mas_de_1
*  
* PREREQUISITOS:
*	Contar con acceso a BD de FCWMLQA
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - Contar con un registro de una factura que tenga el mismo vendor_id e invoice_num que el json que se va a enviar.
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_010_Error_al_intentar_obtener_los_campos_ESTADO_y_WM_STATUS_de_la_tabla_XXFC_XP_CARGO_CENTRAL_HEADERS_existe_mas_de_1
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_Error_al_intentar_obtener_los_campos_ESTADO_y_WM_STATUS_de_la_tabla_XXFC_XP_CARGO_CENTRAL_HEADERS_existe_mas_de_1_test(
			HashMap<String, String> data) throws Exception {

		// Inicializar las variables trayendo la información del Data Provider.
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryWMLOG2 = data.get("wmlog2");
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningún campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryWMLOG2.isEmpty() || !queryEBS2.isEmpty() || !queryEBS3.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

		// Paso 1
		// Validar la información en las tablas de EBS ya este existente, Se solicita
		// por ticket insert de un registro repetido directo a la tabla para simular el
		// escenario.
		addStep("Validar la información en las tablas de EBS ya este existente, Se solicita por ticket insert de un registro repetido directo a la tabla para simular el escenario.");

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");
		
		//Paso 2
		addStep("Ejecutar servicios de la interfaces EO13MX_CCC  \r\n" + "");

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

		//Paso 3.
		addStep("Validar en la tabla WMLOG.WM_LOG_RUN  status='E' en WMLOG\r\n" + "");

		// queryWMLOG
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryWMLOG);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			wm_run_id = resultadoContenedor.getData(0, 0);
		}
		assertTrue(flag, "El registro existe en la BD");

		//Paso 4
		addStep("Validar en la tabla  WMLOG.WM_LOG_ERROR  se  genere el error en WMLOG\r\n" + "");

		// Query wmlog2
		resultadoContenedor = dbWM.executeQuery(queryWMLOG2 + wm_run_id);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");

		// PASO 5
		//Se adjunta evidencia manualmente
		addStep("Validar envÃ­o de correos");
		testCase.addBoldTextEvidenceCurrentStep("Paso manual, se adjunta evidencia de email generado");
		
		//Paso 6.
		addStep("Validar que la información no se inserte en las tablas de EBS (XXFC_XP_CARGO_CENTRAL_HEADERS)");
		resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");

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
		return "Validar que al tratar de recibir una factura que tiene 2 o mas registros con el mismo vendor_id e invoice_num regrese un error\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_Error_al_intentar_obtener_los_campos_ESTADO_y_WM_STATUS_de_la_tabla_XXFC_XP_CARGO_CENTRAL_HEADERS_existe_mas_de_1_registro_en_la_tabla_con_el_mismo_vendor_id_e_invoice_num_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

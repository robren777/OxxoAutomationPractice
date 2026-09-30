package WEB.evolucionDEI.scripts;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import modelo.BaseExecution;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;

/**
* DESCRIPCIÓN: Validar que el Portal de Workflow tenga la conexión a las tablas staging que se crearán en EBS, para recibir la información necesaria para crear la Factura y la informacion se guarde correctamente
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_001_13_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS
* 
* PREREQUISITOS:
* 	"1.- Contar con acceso a la BD de EBS"
*	"2.- Tener Insumos en el bus de Workflow en espera de la ejecucion del primer Microservicio"
*	"3.- Ejecutar el script un momento antes de que el microservicio entre en accion(cada 5 o 15 minutos, depende de la configuracion)";	
* 
* INSUMOS:
* 	-Se requiere tener insumos en workflow
* 
* @author bernardo.mejia@grupotimex.onmicrosoft.com
* @date 08/Mayo/2025
*/

public class ATC_FT_013_ElTokenInvOTiemDeVidaExpirado extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	
	public void ATC_FT_013_ElTokenInvOTiemDeVidaExpirado_test(HashMap<String, String> data)
			throws Exception {
		
		/** Se valida que el Cliente reciba un código de respuesta 401 cuando el token es inválido o el tiempo de vida haya expirado.
		 * @param RestAssured
		 * @author Bernardo Alonso Mejia Ortiz
		 */	
		
		/****************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");
 
		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
		
		// Se llama al OM
		Response postTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));		
		
		// Validar status		
		int postTokenStatusCode = postTokenResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + postTokenStatusCode);
		System.out.println("Status token: " + postTokenStatusCode);
		assertEquals(postTokenStatusCode, postExpectedCode, "Error al obtener token");
 
		String postTokenBodyResponse = postTokenResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + postTokenBodyResponse);
		System.out.println("Respuesta completa token: " + postTokenBodyResponse);
 
		// Extraemos el access_token del JSON
		JSONObject postJson = new JSONObject(postTokenBodyResponse);
		String token = postJson.getString("access_token");
		assertNotNull(token, "No se encontró access_token en la respuesta");
		testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
 		
		
			/***************************** PASO 3: Obtener datos en petición GET ****************************/
			addStep("Ejecutar petición GET retrieve para validar que se actualizó ESTATUS_GRAL en la capa correcta");
			System.out.println("=== Ejecutando petición GET con reintentos ===");
	 
			// Parametrización de URL
			//String baseGetUrl = data.get("getPath");
			// Variables de control para retry
			int maxReintentos = 3;
			
			// Ejecutamos el GET usando el OM
			Response getResponse = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token, postJson, data, maxReintentos);
			System.out.println("Token usado en GET: " + token);
			System.out.println("ApiKey usada en GET: " + data.get("apiKey"));
			int status = getResponse.getStatusCode();			
			
			// Validación final después de reintentos
			testCase.addCodeEvidenceCurrentStep("statusCode: " + status);
			System.out.println("Status: " + status);
			int getStatusCode = getResponse.getStatusCode();
			assertEquals(status, getStatusCode, "El GET no devolvió 200 después de " + maxReintentos + " intentos");
	 
			// Guardar y mostrar body completo
			String getBodyResponse = getResponse.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getBodyResponse);
			System.out.println("Respuesta: " + getBodyResponse);
	        
			if (status != 401) {
				// Parsear JSON y validar datos
				JSONObject getJsonResponse = new JSONObject(getBodyResponse);
				JSONArray features = getJsonResponse.optJSONArray("features");
				assertNotNull(features, "El JSON no contiene 'features'");
				assertTrue("El arreglo 'features' está vacío",features.length() > 0 );
				JSONObject firstFeature = features.getJSONObject(0);
				JSONObject attributes = firstFeature.optJSONObject("attributes");
				assertNotNull(attributes, "El objeto 'attributes' no existe en el JSON");
			}	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_ElTokenInvOTiemDeVidaExpirado";
	}

	@Override
	public String setTestDescription() {
		return "Se valida que el Cliente reciba un código de respuesta 401 cuando el token es inválido o el tiempo de vida haya expirado.";
	}

	@Override
	public String setTestDesigner() {
		return "bernardo.mejia@grupotimex.onmicrosoft.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Abrir Postman\r\n"
		+ "		2.- Se coloca el Token que ya expiro  y el JSON con 1 peticiones desde postman.\n";		
	}

	@Override
	public void beforeTest() {

	}
}

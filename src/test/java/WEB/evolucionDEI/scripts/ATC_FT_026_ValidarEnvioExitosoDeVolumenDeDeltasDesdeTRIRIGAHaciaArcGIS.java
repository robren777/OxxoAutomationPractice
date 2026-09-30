package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Verificar que el sistema sea capaz de procesar correctamente un envío masivo de 2000 registros (Deltas) desde TRIRIGA hacia ArcGIS
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-026 Validación del envío exitoso de 2000 Deltas desde TRIRIGA hacia ArcGIS
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 26/09/2025
 */

public class ATC_FT_026_ValidarEnvioExitosoDeVolumenDeDeltasDesdeTRIRIGAHaciaArcGIS extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis",
//	userStoryId = 226345   //)
	  // 🔹 Declaras la variable base aquí:
	
  	@Test(dataProvider = "data-provider")
	public void ATC_FT_026_ValidarEnvioExitosoDeVolumenDeDeltasDesdeTRIRIGAHaciaArcGIS_test(HashMap<String, String> data) throws Exception {

		/** Ejecutar petición Put exitosamente con un volumen alto de deltas
		 * @param RestAssured
		 * @author Melba Solís 	 */

		/***************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");

		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
		Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth")); // Se llama al OM

		// Validar status
		int getTokenStatusCode = getTokenResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + getTokenStatusCode);
		System.out.println("Status token: " + getTokenStatusCode);
		assertEquals(getTokenStatusCode, postExpectedCode, "Error al obtener token");

		// Ver respuesta completa
		String getTokenBodyResponse = getTokenResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getTokenBodyResponse);
		System.out.println("Respuesta completa token: " + getTokenBodyResponse);

		// Extraer el token del JSON
		JSONObject postJson = new JSONObject(getTokenBodyResponse);
		String token = postJson.optString("access_token", null);
		assertNotNull(token, "No se encontró access_token en la respuesta");
		testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
		System.out.println("Token extraído: " + token);

		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		 addStep("Leyendo archivo con registros JSON");
	
		// Obtiene la ruta base del proyecto (funciona en local y en pipeline)
		String basePath = System.getProperty("user.dir");

		// Une la ruta base con la relativa del JSON usando Paths.get (corrige los separadores automáticamente)
		Path jsonPath = Paths.get(basePath, data.get("Json"));

		System.out.println("Ruta completa: " + jsonPath.toAbsolutePath());

		// Lee el contenido del JSON
		String jsonBody = new String(Files.readAllBytes(jsonPath));
		
		 // Medir tiempo de ejecución del PUT
		 long inicio = System.currentTimeMillis();
		 
		 JSONArray jsonArray = new JSONArray(jsonBody);
		 int totalRegistros = jsonArray.length();
		 assertTrue(totalRegistros > 0, "El archivo JSON no contiene registros.");
		 testCase.addTextEvidenceCurrentStep("Total de registros a enviar: " + totalRegistros);

		 // Cargar JSON en el mapa
		 data.put("Json", jsonBody);
		 
		 addStep("Ejecutando PUT hacia endpoint syncPotentialStores...");
		 Response respuesta = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		 int statusCode = respuesta.getStatusCode();
		 testCase.addTextEvidenceCurrentStep("Status code obtenido: " + statusCode);
		 assertEquals(statusCode, 200, "El status code no es 200");

		 addStep("Validación final: carga masiva procesada correctamente");

		// Validación de éxito general
		 String body = respuesta.asString();
		 if (body.contains("\"success\"")) {
		     try {
		         JSONObject jsonResp = new JSONObject(body);
		         JSONArray results = jsonResp.optJSONArray("updateResults");
		         if (results != null && results.length() > 0) {
		             boolean success = results.optJSONObject(0).optBoolean("success", false);
		             testCase.addTextEvidenceCurrentStep("Resultado 'success' en la respuesta: " + success);
		             assertTrue(success, "El campo 'success' es falso.");
		         } else {
		             testCase.addTextEvidenceCurrentStep("No se encontró el arreglo 'updateResults' en la respuesta.");
		         }
		     } catch (Exception e) {
		         testCase.addTextEvidenceCurrentStep("No se pudo parsear el campo 'success' del body.");
		     }
		 } else {
		     testCase.addTextEvidenceCurrentStep("No se encontró el campo 'success' en la respuesta (validación omitida).");
		 }
		 
		 long fin = System.currentTimeMillis();
		 long duracion = fin - inicio; // en milisegundos
		 double duracionSegundos = duracion / 1000.0;

		 testCase.addTextEvidenceCurrentStep("Tiempo de ejecución del PUT: " + duracionSegundos + " segundos");
		 
		 //Valida que los registros se hayan actualizado
		 String bodyput = respuesta.asString();
		 JSONObject jsonResp = new JSONObject(bodyput);
		 JSONArray results = jsonResp.optJSONArray("updateResults");

		 assertNotNull(results, "No se encontró el arreglo 'updateResults' en la respuesta.");
		 int totalActualizados = 0;

		 for (int i = 0; i < results.length(); i++) {
		     JSONObject result = results.optJSONObject(i);
		     boolean success = result.optBoolean("success", false);
		     if (success) totalActualizados++;
		 }

		 testCase.addTextEvidenceCurrentStep("Registros actualizados correctamente: " + totalActualizados + " de " + totalRegistros);
		 assertEquals(totalActualizados, totalRegistros, "No todos los registros se actualizaron correctamente.");

		/*************************************PASO: 4 
		* REGRESAR EL ESTATUS A SUS VALORES ORIGINAL
		*******************************************/
		// Generar Token por segunda ocasión
		Response getTokenResponse2 = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));

		// Validar status
		int getTokenStatusCode2 = getTokenResponse2.getStatusCode();
		System.out.println("Status token 2: " + getTokenStatusCode2);
		assertEquals(getTokenStatusCode2, postExpectedCode, "Error al obtener segundo token");

		// Ver respuesta completa
		String getTokenBodyResponse2 = getTokenResponse2.asPrettyString();
		System.out.println("Respuesta completa token 2: " + getTokenBodyResponse2);

		// Extraer el token del JSON
		JSONObject postJson2 = new JSONObject(getTokenBodyResponse2);
		String token2 = postJson2.optString("access_token", null);
		assertNotNull(token2, "No se encontró access_token en la segunda respuesta");
		System.out.println("Token 2 extraído: " + token2);
		
		// ===== SEGUNDA EJECUCIÓN =====
		// Cambias el archivo JSON para la segunda ejecución
		basePath = System.getProperty("user.dir");
		
		// Une la ruta base con la relativa del JSON usando Paths.get (corrige los separadores automáticamente)
		Path jsonPath2 = Paths.get(basePath, data.get("Json2"));
		System.out.println("Ruta completa: " + jsonPath2.toAbsolutePath());

		String jsonBody2 = new String(Files.readAllBytes(jsonPath));;
		
		long inicio2 = System.currentTimeMillis();

		JSONArray jsonArray2 = new JSONArray(jsonBody2);
		int totalRegistros2 = jsonArray2.length();
		assertTrue(totalRegistros2 > 0, "El archivo JSON no contiene registros (2).");

		data.put("Json2", jsonBody2);

		//addStep("Ejecutando PUT hacia endpoint syncPotentialStores (segunda ejecución)...");
		Response respuesta2 = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		int statusCode2 = respuesta2.getStatusCode();
		assertEquals(statusCode2, 200, "El status code no es 200 (2).");

		String body2 = respuesta2.asString();
		JSONObject jsonResp2 = new JSONObject(body2);
		JSONArray results2 = jsonResp2.optJSONArray("updateResults");

		assertNotNull(results2, "No se encontró el arreglo 'updateResults' en la respuesta (2).");
		int totalActualizados2 = 0;
		for (int i = 0; i < results2.length(); i++) {
		    JSONObject result = results2.optJSONObject(i);
		    boolean success = result.optBoolean("success", false);
		    if (success) totalActualizados2++;
		}

		assertEquals(totalActualizados2, totalRegistros2, "No todos los registros se actualizaron correctamente (2).");

		long fin2 = System.currentTimeMillis();
		double duracionSegundos2 = (fin2 - inicio2) / 1000.0;
		//testCase.addTextEvidenceCurrentStep("Tiempo de ejecución del PUT (2): " + duracionSegundos2 + " segundos");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con la herramienta postman \r\n" 
				+ "2. Contar con las credenciales correspondientes a la colección \r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_026_ValidarEnvioExitosoDeVolumenDeDeltasDesdeTRIRIGAHaciaArcGIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Verificar que el sistema sea capaz de procesar correctamente un envío masivo de 2000 registros (Deltas) desde TRIRIGA hacia ArcGIS";
	}
}

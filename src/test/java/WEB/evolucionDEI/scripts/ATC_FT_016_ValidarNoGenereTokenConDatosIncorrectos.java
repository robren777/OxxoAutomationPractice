package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**DESCRIPCIÓN: Validar que, al realizar la solicitud para obtener el token en
 * Postman utilizando credenciales incorrectas, el sistema no genere el token y
 * responda con el código de error correspondiente.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-016 Validar no genere
 * token con datos incorrectos
 * 
 * PREREQUISITOS: URL y Credenciales inválidas
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 24/09/2025      */ 

public class ATC_FT_016_ValidarNoGenereTokenConDatosIncorrectos extends BaseExecution {

//@AzureTestInfo(
//			areaPath = "I24073-Evolución DEI",
//			iterationPath = "I24073-Evolución DEI",
//			tags = "Token;Error",
//			userStoryId = 226345   //)

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_016_ValidarNoGenereTokenConDatosIncorrectos_test(HashMap<String, String> data) throws Exception {

		/** Llamar a la Petición que genera el Token con datos incorrectos
		 * @param RestAssured
		 * @author Melba Solís 	 */

		/***************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token con credenciales inválidas");

		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));

		// Ahora usamos solo el header con Basic Auth
		Response postTokenResponse = RestAssured
			.given()
				.relaxedHTTPSValidation()
				.header("Authorization", "Basic " + data.get("basicAuth"))
			.when()
			.post(data.get("postPath"));

		int postTokenStatusCode = postTokenResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + postTokenStatusCode);
		assertEquals(postTokenStatusCode, postExpectedCode, "No se observa error al obtener el token y no es lo esperado");
		System.out.println("Estatus: " + postTokenStatusCode);
		
		String postTokenBodyResponse = postTokenResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + postTokenBodyResponse);
		assertTrue(postTokenBodyResponse == null || postTokenBodyResponse.isBlank(), 
				"El body de la respuesta no está vacío como se esperaba");
		System.out.println("Respuesta obtenida: " + postTokenBodyResponse);
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con la herramienta postman y la URL \r\n"
				+ "2. Contar con las credenciales inválidas correspondientes a la colección \r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarNoGenereTokenConDatosIncorrectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Validar que, al realizar la solicitud para obtener el token en Postman utilizando credenciales incorrectas el sistema no genere el token y responda con el código de error correspondiente";
	}
}

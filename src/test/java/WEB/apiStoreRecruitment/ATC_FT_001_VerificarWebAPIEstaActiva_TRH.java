//******************************************************//

// Verificar que la API Web est� activa
// EMI004 Verificar que la API Web est� activa

//******************************************************//

/** Verifica que el endpoint de la API Web regresa una respuesta
	 * @author Luis Ramirez
	 */

package WEB.apiStoreRecruitment;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_FT_001_VerificarWebAPIEstaActiva_TRH extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	
	
	public void ATC_FT_001_VerificarWebAPIEstaActiva_TRH_test(HashMap<String, String> data)throws Exception {
				
		String baseURL = "https://fcportalqa.femcom.net:8443";
		
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);	
		
		addStep("Hacer el send del request");		
		//Obtener datos del data provider
		String getPath = data.get("getPath");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		
		//Se env�a como evidencia el request a ejecutar
		testCase.addCodeEvidenceCurrentStep("request:"+ baseURL + getPath);
		
		//Ejecuta request
		//Response getRespuesta = api.getRequestMethod(getPath);
		RestAssured.baseURI = baseURL;
		Response getRespuesta = RestAssured
		.given()
			.header("Authorization","NoAuth")
			.relaxedHTTPSValidation() //no buscar certificado SSL
			.contentType(ContentType.JSON) //tipo de api
		.when()
			.get(getPath);
		
		//Obtener body de respuesta
		String getBodyResponse = getRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse);
				
		//Validaci�n de JSON
		JSONObject getJson = new JSONObject(getBodyResponse);
		testCase.addTextEvidenceCurrentStep("body response: "+ getJson);

		//Obtener el status code
		addStep("Validar que la informacion sea devuelta por el m�todo");
		int getStatusCode = getRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode:" + getStatusCode);
		assertEquals(getStatusCode, getExpectedCode); //Validacion	
				
	}
	

@Override
public String setTestFullName() {
	return "ATC_FT_001_VerificarWebAPIEstaActiva_TRH_test";
}

@Override
public String setTestDescription() {
	return "Verificar que la API Web est� activa";
}

@Override
public String setTestDesigner() {
	return "Luis Ramirez";
}

@Override
public String setTestInstanceID() {
	return null;
}

@Override
public String setPrerequisites() {
	return null;
}

@Override
public void beforeTest() {

}
}

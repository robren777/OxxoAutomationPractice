//******************************************************//

// Verificar response de Web API contiene todos los campos requeridos
// EMI005 Verificar response de Web API contiene todos los campos requeridos

//******************************************************//

/** Verifica que respuesta de la API StoreRecruitment contiene todos los campos requeridos
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



public class ATC_FT_002_VerificarResponsedeWebAPIContieneCamposRequeridos_TRH extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
public void ATC_FT_002_VerificarResponsedeWebAPIContieneCamposRequeridos_TRH_test(HashMap<String, String> data)throws Exception{
	

		String baseURL = "https://fcportaldes.femcom.net:8443";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
		addStep("Hacer el Send del request");
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
		/*String locationId = getJson.getString("locationId");
		testCase.addTextEvidenceCurrentStep("locationId:" + locationId);
		assertEquals(locationId, "35265");*/
		
		//Obtener el status code
		addStep("Validar que la informacion sea devuelta por el m�todo");
		int getStatusCode = getRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode:" + getStatusCode);
		assertEquals(getStatusCode, getExpectedCode); //Validacion	
		
		
	}
@Override
public String setTestFullName() {
	return "ATC_FT_002_VerificarResponsedeWebAPIContieneCamposRequeridos_TRH_test";
}

@Override
public String setTestDescription() {
	return "Verificar response de Web API contiene todos los campos requeridos";
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

}}
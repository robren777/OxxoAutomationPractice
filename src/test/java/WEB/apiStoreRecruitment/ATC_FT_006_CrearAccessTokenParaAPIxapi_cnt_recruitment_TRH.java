//******************************************************//

// Crear access token para API xapi-cnt-recruitment
// EMI011 Crear access token para API xapi-cnt-recruitment

//******************************************************//

/** Crear access token para API xapi-cnt-recruitment 
	 * @author Luis Ramirez
	 */

package WEB.apiStoreRecruitment;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_FT_006_CrearAccessTokenParaAPIxapi_cnt_recruitment_TRH extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_CrearAccessTokenParaAPIxapi_cnt_recruitment_TRH_test(HashMap<String, String> data)throws Exception{
		
		//String baseURL = "http://api-dev.oxxo.io";
		String baseURL = "http://api-stg.oxxo.io";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		//api.BasicAuthRequest(baseURL, "grant_type: client_credentials");
		
		addStep("Hacer el send del request");	
		//Obtener datos del data provider
		String getPath = data.get("getPath");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		String getAuthType = data.get("getAuthType");
		
        HttpGet httpGet = new HttpGet(baseURL+getPath);
        httpGet.setHeader("Authorization", getAuthType);		
		
		//Se env�a como evidencia el request a ejecutar
		testCase.addCodeEvidenceCurrentStep("request:"+ baseURL + getPath);

		//Ejecuta request
		Response getRespuesta = api.getRequestMethod(getPath);
		
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
	return "ATC_FT_006_CrearAccessTokenParaAPIxapi_cnt_recruitment_TRH_test";
}

@Override
public String setTestDescription() {
	return "Crear access token para API xapi-cnt-recruitment";
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
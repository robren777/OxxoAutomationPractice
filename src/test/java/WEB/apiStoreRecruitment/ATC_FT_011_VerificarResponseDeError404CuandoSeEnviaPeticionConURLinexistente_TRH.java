//******************************************************//

// Verificar response de error 400 cuando se env�a una petici�n con una URL inexistente
// EMI017 Verificar response de error 400 cuando se env�a una petici�n con una URL inexistente

//******************************************************//

/** Verifica que respuesta de error de la API xapi-cnt-recruitment cuando se env�a una petici�n con una URL inexistente
	 * @author Luis Ramirez
	 */

package WEB.apiStoreRecruitment;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;


public class ATC_FT_011_VerificarResponseDeError404CuandoSeEnviaPeticionConURLinexistente_TRH extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_VerificarResponseDeError404CuandoSeEnviaPeticionConURLinexistente_TRH_test(HashMap<String, String> data)throws Exception {
		
		//String baseURL = "https://api-dev.oxxo.io";
		String baseURL = "https://api-stg.oxxo.io";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
		addStep("Hacer el send del request");	
		//Obtener datos del data provider
		String getPath = data.get("getPath");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		
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
	return "ATC_FT_011_VerificarResponseDeError404CuandoSeEnviaPeticionConURLinexistente_TRH_test";
}

@Override
public String setTestDescription() {
	return "Verifica que respuesta de error de la API xapi-cnt-recruitment cuando se env�a una petici�n con una URL inexistente";
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



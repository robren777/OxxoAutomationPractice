//******************************************************//

// Verificar response de error 401 cuando el token es inv�lido o expirado
// EMI015 Verificar response de error 401 cuando el token es inv�lido o expirado

//******************************************************//

/** Verifica response de error 401 cuando el token es inv�lido o expirado
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

public class ATC_FT_009_VerificarResponseDeError401CuandoTokenEsInvalido_o_Expirado_TRH extends BaseExecution{
		
		
		@Test(dataProvider = "data-provider")
		public void ATC_FT_009_VerificarResponseDeError401CuandoTokenEsInvalido_o_Expirado_TRH_test(HashMap<String, String> data)throws Exception{
			// headers para generar token
			String RestAPIAuth		= "Basic ZTZiZTc3NmQtMWNjYy00MzhlLWJlZjMtZTdkYTNlY2NmMGNhOjYyMmY3M2JkLTIyM2UtNDhmZi04MjM1LWQ3MWY3MWE3MTkwMw==";
			
			// headers para llamada al API Platform
			String xAPIAuth			= "Bearer";	
			String TrackingId 		= "278e783e-7683-405c-bf7e-e20c6cdd00de";
			String ChannelId 		= "WEB";
			String CountryCode 		= "MX";
			String Language 		= "SPA";
			String UserAgent 		= "android/10";
			String xGatewayAPIKey 	= "278e783e-7683-405c-bf7e-e20c6cdd00de";
			String Accept 			= "application/json";
			String ContentType 		= "application/json";
			
			//******************************************************//
			// Run
			
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
	return "ATC_FT_009_VerificarResponseDeError401CuandoTokenEsInvalido_o_Expirado_TRH_test";
}

@Override
public String setTestDescription() {
	return "Verificar response de error 401 cuando el token es inv�lido o expirado";
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
//******************************************************//

// Verificar response de error 429 cuando se excede numero de peticiones permitidas
// EMI020 Verificar response de error 429 cuando se excede numero de peticiones permitidas

//******************************************************//

/** Verificar response de error 429 cuando se excede numero de peticiones permitidas
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

public class ATC_FT_013_VerificarResponseDeError429CuandoSeExcedeNumeroDePeticionesPermitidas_TRH extends BaseExecution{
@Test(dataProvider = "data-provider")
	public void ATC_FT_013_VerificarResponseDeError429CuandoSeExcedeNumeroDePeticionesPermitidas_TRH_test(HashMap<String, String> data)throws Exception{
		
	String baseURL = "https://api-dev.oxxo.io";
	ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
	
	addStep("Datos de Data Provider");
	//Obtener datos del data provider
	String getPath = data.get("getPath");
	int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
	
	//Se env�a como evidencia el request a ejecutar
	testCase.addCodeEvidenceCurrentStep("request:"+ baseURL + getPath);

	//Ejecuta request
	addStep("Send del request");
	Response getRespuesta = api.getRequestMethod(getPath);
	
	//Obtener body de respuesta
	String getBodyResponse = getRespuesta.asPrettyString();
	testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse);
			
	//Validaci�n de JSON
	JSONObject getJson = new JSONObject(getBodyResponse);
	testCase.addTextEvidenceCurrentStep("body response: "+ getJson);

	//Obtener el status code
	addStep("Respuesta obtenida");
	int getStatusCode = getRespuesta.getStatusCode();
	testCase.addCodeEvidenceCurrentStep("statusCode:" + getStatusCode);
	assertEquals(getStatusCode, getExpectedCode); //Validacion	
	}


@Override
public String setTestFullName() {
	return "ATC_FT_013_VerificarResponseDeError429CuandoSeExcedeNumeroDePeticionesPermitidas_TRH_test";
}

@Override
public String setTestDescription() {
	return "EMI020 Verificar response de error 429 cuando se excede numero de peticiones permitidas";
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

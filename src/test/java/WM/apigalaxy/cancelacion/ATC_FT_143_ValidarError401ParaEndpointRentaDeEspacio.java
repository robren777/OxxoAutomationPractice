package WM.apigalaxy.cancelacion;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 401 frozandolo para los dos endpoints de la
 * API
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-143
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7000
 * 
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_143_ValidarError401ParaEndpointRentaDeEspacio extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_143_ValidarError401ParaEndpointRentaDeEspacio_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String jsonEnviadoCancel = "json7019Cancel.txt";
	// se usa como prerequisito
	/***************************************
	 * PASO 1 Hacer una llamada tipo POST
	 ***************************************/
	addStep("Hacer una llamada tipo POST", "Debe regresar un 401");
	Response response = util.putPromotionalRentsNoToken(jsonEnviadoCancel);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 401, "Se valida el estatus code");

	/***************************************
	 * PASO 2 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:{code:X401000,message:The specified token is invalid or has expired.}");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	assertEquals(response.jsonPath().getString("code"), "X401000", "Validacion del codigo");
	assertEquals(response.jsonPath().getString("message"), "[ Token specified is invalid or has expired.]",
		"Validacion del message");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\nLa API funcionando\nAcceso a snowflake\nUn set de datos que no esté en la DB";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 401 frozandolo para los dos endpoints de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_143_ValidarError401ParaEndpointRentaDeEspacio";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
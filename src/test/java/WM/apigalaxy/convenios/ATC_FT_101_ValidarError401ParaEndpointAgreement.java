package WM.apigalaxy.convenios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error forzandolo para el endpoint de la API
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-101-Validar error 401
 * para endpoint agreement
 *
 * PREREQUISITOS: - Acceso a la API y la API funcionando - JSON con POST ID MKT
 * 721 - Acceso a Snowflake - Acceso a DB Convenios
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_101_ValidarError401ParaEndpointAgreement extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_101_ValidarError401ParaEndpointAgreement_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String jsonCambio = "json706Cambio.txt";

	/***************************************
	 * PASO 1 Hacer una llamada tipo DELETE con los siguinetes valores: periodsId =
	 * 70
	 ***************************************/
	addStep("Hacer una llamada tipo DELETE con los siguinetes valores:periodsId = 70", "Debe regresar un 401");
	Response response = util.postAgreementsNoToken(jsonCambio);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 401, "Se valida el estatus code");

	/***************************************
	 * PASO 2 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:code: X401000,message:[ Token specified is invalid or has expired.]");
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep("Para el envio se obtiene el code " + realCode);
	testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	assertEquals(realCode, "X401000", "Code erroneo ");
	assertEquals(response.jsonPath().getString("message"), "[ Token specified is invalid or has expired.]",
		"Message erroneo ");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API y la API funcionando\\nJSON con POST ID MKT 721\\nAcceso a Snowflake\\nAcceso a DB Convenios";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error forzandolo para el endpoint de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_101_ValidarError401ParaEndpointAgreement";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

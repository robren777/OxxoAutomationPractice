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
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-100-Validar error 400
 * para endpoint agreement
 *
 * PREREQUISITOS: - Acceso a la API y la API funcionando - JSON con POST ID MKT
 * 720 - Acceso a Snowflake - Acceso a DB Convenios
 *
 * INSUMOS: -
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_100_ValidarError400ParaEndpointAgreement extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_100_ValidarError400ParaEndpointAgreement_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String jsonCambio = "json706Cambio.txt";

	/***************************************
	 * PASO 1 Utilizando el token generar un segundo token desde el endpoint de
	 * OAuth2
	 ***************************************/
	addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2",
		"Debes utilizar un api token");
	testCase.addCodeEvidenceCurrentStep("Se utiliza");

	/***************************************
	 * PASO 2 Utilizar el token generado como header para las siguiente accion.
	 ***************************************/
	addStep("Utilizar el token generado como header para las siguiente accion.", "Se debe utilizar un OAuth2");
	testCase.addCodeEvidenceCurrentStep("Se utiliza");

	/***************************************
	 * PASO 3 Hacer una llamada tipo DELETE con los siguinetes valores: periodsId =
	 * a
	 ***************************************/
	addStep("Hacer una llamada tipo post con los siguinetes valores:periodsId = a", "Debe regresar un 400");
	Response response = util.postAgreementsError(jsonCambio);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 400, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:code:X400000,message:The client has issued a malformed or illegal request.");
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep("Para el envio se obtiene el code " + realCode);
	testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	assertEquals(realCode, "X400000", "Code erroneo ");
	assertEquals(response.jsonPath().getString("message"), "The client has issued a malformed or illegal request.",
		"Message erroneo ");
	assertEquals(response.getStatusCode(), 400, "Se valida codigo de error");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API y la API funcionando\\nJSON con POST ID MKT 720\\nAcceso a Snowflake\\nAcceso a DB Convenios";
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
	return "ATC_FT_100_ValidarError400ParaEndpointAgreement";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

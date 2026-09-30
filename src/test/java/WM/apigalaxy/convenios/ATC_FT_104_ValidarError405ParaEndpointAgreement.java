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
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-104-Validar error 405
 * para endpoint agreement
 *
 * PREREQUISITOS: - Acceso a la API y la API funcionando - JSON con POST ID MKT
 * 724 - Acceso a Snowflake - Acceso a DB Convenios
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_104_ValidarError405ParaEndpointAgreement extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_104_ValidarError405ParaEndpointAgreement_test(HashMap<String, String> data) throws Exception {

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
	 * PASO 3 Hacer una llamada tipo PUT
	 ***************************************/
	addStep("Hacer una llamada tipo PUT", "Debe regresar un 405");
	Response response = util.putAgreements(jsonCambio);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 405, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:code: X405000,message: Method not allowed : *");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	assertEquals(response.jsonPath().getString("code"), "X405000", "Validacion del codigo");
	assertEquals(response.jsonPath().getString("message"), "Method not allowed: PUT.", "Validacion del message");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API y la API funcionando\\nJSON con POST ID MKT 724\\nAcceso a Snowflake\\nAcceso a DB Convenios";
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
	return "ATC_FT_104_ValidarError405ParaEndpointAgreement";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

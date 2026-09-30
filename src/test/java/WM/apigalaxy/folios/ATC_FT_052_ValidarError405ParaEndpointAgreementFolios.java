package WM.apigalaxy.folios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 405 forzandolo para el endpoints de la
 * APIforzandolo para el endpoint de la API
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-052-Validar error 405
 * para endpoint agreement/folios
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando
 *
 * INSUMOS: -
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_052_ValidarError405ParaEndpointAgreementFolios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_052_ValidarError405ParaEndpointAgreementFolios_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);

	/***************************************
	 * PASO 1 Utilizando el token generar un segundo token desde el endpoint de
	 * OAuth2
	 ***************************************/
	addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2",
		"Debes utilizar un api token");

	testCase.addCodeEvidenceCurrentStep("Se genera el token");
	// Este paso se ejecuta de manera automatica en el metodo de llamada de api

	/***************************************
	 * PASO 2 Utilizar el token generado como header para las siguiente accion
	 ***************************************/
	addStep("Utilizar el token generado como header para las siguiente accion.", "Se debe utilizar un OAuth2");
	testCase.addCodeEvidenceCurrentStep("Se utiliza el OAuth2");
	// Este paso se ejecuta de manera automatica en el metodo de llamada de api

	/***************************************
	 * PASO 3 Hacer una llamada tipo POST con los siguinetes valores:
	 * https://api-dev.oxxo.io/exp-marketplace-space-leasing/api/v1/catalog/stores?page=1&pageSize=1&updateDate=2025-01-31
	 ***************************************/
	addStep("Hacer una llamada tipo POST con los siguinetes valores:https://api-dev.oxxo.io/exp-marketplace-space-leasing/api/v1/catalog/stores?page=1&pageSize=1&updateDate=2025-01-31",
		"Debe regresar un 404");
	Response response = util.deleteAgreementFolios(1, 1, 1, "a", "a");
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 405, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:codeX405000 message Method not allowed : *");
	ErrorResponse errorBodyStores = response.as(ErrorResponse.class);
	assertEquals(errorBodyStores.getCode(), "X405000",
		"Validar estatus code de endpoint healtCheck en el response body");
	assertEquals(errorBodyStores.getMessage().matches("Method not allowed: DELETE."), true,
		"Validar message error de endpoint healtCheck en el response body");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + response.asPrettyString());

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 405 forzandolo para el endpoints de la APIforzandolo para el endpoint de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_052_ValidarError405ParaEndpointAgreementFolios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 404 al forzar la consulta del endpoint
 * stores-spaces de la API, utilizando un token OAuth2 generado desde un api
 * token y verificando que el response regrese el código X404000 con el mensaje
 * de recurso no encontrado.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-060-Validar error 404
 * para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 *
 * @author guillermo garcia
 * @date 26/05/2026
 */
public class ATC_FT_060_ValidarError404ParaEndpointStoresSpaces extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_060_ValidarError404ParaEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {
	/*****************************
	 * Variables Data Provider
	 **************************************/
	String updatedDate = data.get("updatedDate");
	String cursor = data.get("cursor");
	String limit = data.get("limit");

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	SoftAssert softAssert = new SoftAssert();
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
	 * PASO 3 Hacer una llamada tipo GET con los siguientes valores
	 ***************************************/
	addStep("Hacer una llamada tipo GET con los siguinetes valores:\r\n"
		+ "Hacer una llamada tipo GET con los siguinetes valores: " + util.getTokenUrl()
		+ "/catalog/stores-spaces?cursor=1&limit=1&updateDate=2025-01-31", "Debe regresar un 404");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStoresSpacesNotFound(cursor, limit, updatedDate);
	softAssert.assertEquals(stores.getStatusCode(), 404, "Validar estatus code de endpoint stores spaces");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta", "El response debe ser: {\r\n" + "    \"code\": \"X404000\",\r\n"
		+ "    \"message\": \"Resource * not found.\"\r\n" + "}");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodyStores.getCode(), "X404000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertTrue(errorBodyStores.getMessage().matches("Resource .* not found\\."),
		"Validar message error de endpoint healtCheck en el response body");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 404 al forzar la consulta del endpoint stores-spaces de la API, "
		+ "utilizando un token OAuth2 generado desde un api token y verificando que el response "
		+ "regrese el código X404000 con el mensaje de recurso no encontrado.";
    }

    @Override
    public String setTestDesigner() {
	return "guillermo garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_060_ValidarError404ParaEndpointStoresSpaces";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }
}

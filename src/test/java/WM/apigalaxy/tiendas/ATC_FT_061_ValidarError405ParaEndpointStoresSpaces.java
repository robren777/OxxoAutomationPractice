package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 405 forzándolo para el endpoint de stores
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-061-Validar error 405
 * para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API, La API funcionando
 *
 * INSUMOS:
 *
 * @date 18/05/2026
 */
public class ATC_FT_061_ValidarError405ParaEndpointStoresSpaces extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_061_ValidarError405ParaEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {

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

	testCase.addCodeEvidenceCurrentStep(
		"=====================Se valida el endpoint stores========================================");
	/***************************************
	 * PASO 3 Hacer una llamada tipo POST con los siguientes valores
	 ***************************************/
	addStep("Hacer una llamada tipo PUT con los siguientes valores: " + util.getTokenUrl()
		+ "/catalog/stores-spaces?cursor=1&limit=1&updateDate=2025-01-31", "Debe regresar un 405");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.putStoresSpaces(cursor, limit, updatedDate);
	softAssert.assertEquals(stores.getStatusCode(), 405, "Validar estatus code del endpoint");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser: { \"code\": \"X405000\", \"message\": \"Method not allowed : *\" }");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodyStores.getCode(), "X405000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertTrue(errorBodyStores.getMessage().matches("Method not allowed : .* "),
		"Validar message error de endpoint en el response body");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_061_ValidarError405ParaEndpointStoresSpaces";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida el error 405 forzandolo para el endpoints de la APIforzandolo para el endpoint de la API\r\n"
		+ "";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

    @Override
    public String setPrerequisites() {
	// Mismo que PREREQUISITOS en documentación
	return "Acceso a la API, La API funcionando";
    }

    @Override
    public void beforeTest() {

    }
}
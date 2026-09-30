package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 400 forzándolo para el endpoint stores spaces
 * de la API.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-057-Validar error 400
 * para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 *
 * @author Guillermo Garcia
 * @date 25/05/2026
 */
public class ATC_FT_057_ValidarError400ParaEndpointStoresSpaces extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_057_ValidarError400ParaEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {

	/*****************************
	 * Variables Data Provider
	 **************************************/
	String updatedDate = data.get("updatedDate");
	String limit = data.get("limit");
	String cursor = data.get("cursor");

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
	addStep("Hacer una llamada tipo GET con los siguinetes valores: cursor = a, limit = 1, updateDate = "
		+ updatedDate, "Debe regresar un 400");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response spacesStores = util.getStoresSpaces(limit, cursor, updatedDate);
	testCase.addCodeEvidenceCurrentStep(
		"Los valores enviados son limit: " + limit + " ,cursor: " + cursor + " ,updatedDate: " + updatedDate);
	softAssert.assertEquals(spacesStores.getStatusCode(), 400, "Validar estatus code de endpoint spacesStores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + spacesStores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser: { \"code\": \"X400000\", \"message\": \"The client has issued a malformed or illegal request.\" }");
	ErrorResponse errorBodySpacesStores = spacesStores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodySpacesStores.getCode(), "X400000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertEquals(errorBodySpacesStores.getMessage(),
		"The client has issued a malformed or illegal request.",
		"Validar message error de endpoint stores spaces en el response body");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + spacesStores.asPrettyString());

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	// Mismo que PREREQUISITOS en documentación
	return "Acceso a la API\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida el error 400 forzándolo para el endpoint stores spaces de la API.";
    }

    @Override
    public String setTestFullName() {
	// Mismo nombre de la clase
	return "ATC_FT_057_ValidarError400EndpointStoresSpaces";
    }

    @Override
    public String setTestInstanceID() {
	// Dejar null
	return null;
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

}

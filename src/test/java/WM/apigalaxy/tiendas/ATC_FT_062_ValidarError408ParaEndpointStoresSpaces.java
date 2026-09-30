package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 408 forzándolo para el endpoint stores-spaces
 * de la API mediante el uso de un token OAuth2 generado a partir de un API
 * token, verificando que la respuesta corresponda al código X403000.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-062-Validar error 408
 * para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 * 
 * @author guillermo garcia
 * @date 25/05/2026
 */
public class ATC_FT_062_ValidarError408ParaEndpointStoresSpaces extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_062_ValidarError408ParaEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {
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
	/**
	 * ******************************************************************************
	 * Paso 1
	 * ********************************************************************************
	 */
	addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2",
		"Debes utilizar un api token");
	testCase.addCodeEvidenceCurrentStep("Se genera el token");
	// Este paso se ejecuta de manera automatica en el metodo de llamada de api

	/**
	 * ******************************************************************************
	 * Paso 2
	 * ********************************************************************************
	 */
	addStep("Utilizar el token generado como header para las siguiente accion.", "Se debe utilizar un OAuth2");
	testCase.addCodeEvidenceCurrentStep("Se utiliza el OAuth2");
	// Este paso se ejecuta de manera automatica en el metodo de llamada de api

	/**
	 * ******************************************************************************
	 * Paso 3
	 * ********************************************************************************
	 */
	addStep("Hacer una llamada tipo GET con los siguinetes valores: " + util.getTokenUrl()
		+ "catalog/stores-spaces?cursor=1&limit=1&updateDate=2025-01-31\r\n"
		+ "Pero forzando un tiempo de respuesta largo", "Debe regresar un 408");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStoresSpaces(cursor, limit, updatedDate);
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());
	softAssert.assertEquals(stores.getStatusCode(), 408, "Validar estatus code de endpoint stores");

	/**
	 * ******************************************************************************
	 * Paso 4
	 * ********************************************************************************
	 */
	addStep("Validar el response de la consulta",
		"El response debe ser:\r\n" + "\r\n" + "{\r\n" + "    \"code\": \"X408000\",\r\n"
			+ "    \"message\": \"Timeout exceeded when fetching data from the database.\"\r\n" + "}");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());
	softAssert.assertEquals(errorBodyStores.getCode(), "X408000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertEquals(errorBodyStores.getMessage(), "Timeout exceeded when fetching data from the database.");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "1.-Accesos validos para la API.\r\n" + "2.-La API activa y funcionando."
		+ "3.-Solicitar al equipo de soporte que desactive la aplicacion .";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 408 forzandolo para el endpoints de la APIforzandolo para el endpoint de la API\r\n"
		+ "";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_062_ValidarError408ParaEndpointStoresSpaces";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
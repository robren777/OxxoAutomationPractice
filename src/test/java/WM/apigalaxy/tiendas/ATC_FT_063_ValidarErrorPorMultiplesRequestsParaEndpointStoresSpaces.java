package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error por multiples requests forzandolo para el
 * endpoints de la APIforzandolo para el endpoint de la API NOMBRE CASOS DE
 * PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-063-Validar error por multiples
 * requests para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API, La API funcionando
 *
 * INSUMOS:
 *
 * @date 18/05/2026
 */
public class ATC_FT_063_ValidarErrorPorMultiplesRequestsParaEndpointStoresSpaces extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_063_ValidarErrorPorMultiplesRequestsParaEndpointStoresSpaces_test(HashMap<String, String> data)
	    throws Exception {

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
	addStep("Hacer una llamada 900 veces tipo GET con los siguientes valores: cursor = 1, limit = 1, updateDate = <Valor obtenido en datos de prueba, formato YYYY-MM-DD>",
		"Debes bloquear la api");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStoresSpacesNoToken(cursor, limit, updatedDate);
	if (!(stores.getStatusCode() == 403)) {
	    util.multipleRequestMarketplace("spaces");
	    stores = util.getStoresSpacesNoToken(cursor, limit, updatedDate);
	}
	Assert.assertEquals(stores.getStatusCode(), 403, "Validar estatus code de endpoint stores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response obtenido", "Debe ser Access deny for multiples request");
	Assert.assertEquals(stores.getBody().asString(), "Access deny for multiples request",
		"Validar message error de endpoint stores en el response body");
	testCase.addCodeEvidenceCurrentStep(
		"El valor encontrado en el codigo es: " + stores.getBody().asPrettyString());

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_063_ValidarErrorPorMultiplesRequestsParaEndpointStoresSpaces";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida el error por multiples requests forzandolo para el endpoints de la APIforzandolo para el endpoint de la API\r\n"
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
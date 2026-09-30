package WM.apigalaxy.cancelacion;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 429 frozandolo para los dos endpoints de la
 * API
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-147-Validar error por
 * multiples requests para endpoint renta de espacio
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_147_ValidarErrorPorMultiplesRequestsParaEndpointRentaDeEspacio extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = false)
    public void ATC_FT_147_ValidarErrorPorMultiplesRequestsParaEndpointRentaDeEspacio_test(HashMap<String, String> data)
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
	 * PASO 3 Hacer una llamada 900 veces tipo PUT
	 ***************************************/
	addStep("Hacer una llamada 900 veces tipo PUT", "Debes bloquear la api");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getBaseUrlIma());
	Response stores = util.postPromotionalRents("");
	if (!(stores.getStatusCode() == 403)) {
	    util.multipleRequestMarketplace("rent");
	    stores = util.postPromotionalRents("");
	}
	Assert.assertEquals(stores.getStatusCode(), 403, "Validar estatus code de endpoint stores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response obtenido
	 ***************************************/
	addStep("Validar el response obtenido", "Debe ser Access deny for multiples request Debe regresar un 403");
	Assert.assertEquals(stores.getBody().asString(), "Access deny for multiples request",
		"Validar message error de endpoint stores en el response body");
	testCase.addCodeEvidenceCurrentStep(
		"El valor encontrado en el codigo es: " + stores.getBody().asPrettyString());

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
	return "Se valida el error 429 frozandolo para los dos endpoints de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_147_ValidarErrorPorMultiplesRequestsParaEndpointRentaDeEspacio";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
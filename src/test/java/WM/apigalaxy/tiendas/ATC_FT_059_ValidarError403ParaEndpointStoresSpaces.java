package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 403 forzándolo para el endpoint stores-spaces
 * de la API mediante el uso de un token OAuth2 generado a partir de un API
 * token, verificando que la respuesta corresponda al código X403000.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-058-Validar error 401
 * para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 *
 * @author guillermo garcia
 * @date 25/05/2026
 */
public class ATC_FT_059_ValidarError403ParaEndpointStoresSpaces extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_059_ValidarError403ParaEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {
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
		+ "/catalog/123/stores-spaces?cursor=1&limit=1&updateDate=2025-01-31", "Debe regresar un 403");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStoresSpaces(cursor, limit, updatedDate);
	softAssert.assertEquals(stores.getStatusCode(), 403, "Validar estatus code de endpoint stores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/**
	 * ******************************************************************************
	 * Paso 4
	 * ********************************************************************************
	 */
	addStep("Validar el response de la consulta",
		"El response debe ser: {\"code\":\"X403000\",\"message\":\"Application has been identified but it is currently suspended. Please contact the API Gateway administrator for further details.\"}");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodyStores.getCode(), "X403000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertEquals(errorBodyStores.getMessage(),
		"Application has been identified but it is currently suspended. Please contact the API Gateway administrator for further details.");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());

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
	return "Se valida el error 403 forzándolo para el endpoint stores-spaces de la API mediante el uso de un token OAuth2 generado a partir de un API token, verificando que la respuesta corresponda al código X403000.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_059_ValidarError403ParaEndpointStoresSpaces";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

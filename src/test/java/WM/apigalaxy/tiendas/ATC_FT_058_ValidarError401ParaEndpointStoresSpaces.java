package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 401 forzándolo para el endpoint stores spaces
 * de la API.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-058-Validar error 401
 * para endpoint stores spaces
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 *
 * @author Guillermo Garcia
 * @date 26/05/2026
 */
public class ATC_FT_058_ValidarError401ParaEndpointStoresSpaces extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_058_ValidarError401ParaEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {

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
	 * ***************************************************************************
	 * Paso 1
	 * ****************************************************************************
	 */
	addStep("Hacer una llamada tipo GET con los siguientes valores: cursor = 1, limit = 1, updateDate = '"
		+ updatedDate + "'", "Debe regresar un 401");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStoresSpacesNoToken(cursor, limit, updatedDate);
	Assert.assertEquals(stores.getStatusCode(), 401, "Validar estatus code de endpoint stores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/**
	 * ***************************************************************************
	 * Paso 2
	 * ****************************************************************************
	 */
	addStep("Validar el response de la consulta",
		"El response debe ser: {\"code\":\"X401000\",\"message\":\"The specified token is invalid or has expired.\"}");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodyStores.getCode(), "X401000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertEquals(errorBodyStores.getMessage(), "The specified token is invalid or has expired.",
		"Validar message error de endpoint healtCheck en el response body");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());
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
	return "Se valida el error 401 forzándolo para el endpoint stores spaces de la API.";
    }

    @Override
    public String setTestDesigner() {
	// Mismo que @author
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	// Mismo nombre de la clase
	return "ATC_FT_058_ValidarError401ParaEndpointStoresSpaces";
    }

    @Override
    public String setTestInstanceID() {
	// Dejar null
	return null;
    }
}

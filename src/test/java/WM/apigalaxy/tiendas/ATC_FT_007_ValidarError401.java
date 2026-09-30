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
 * DESCRIPCIÓN: Se valida el error 401 forzándolo para el endpoint de stores
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-007-Validar error 401
 * para endpoint tiendas
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 * 
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_007_ValidarError401 extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_007_ValidarError401_test(HashMap<String, String> data) throws Exception {

	/*****************************
	 * Variables Data Provider
	 **************************************/
	String updatedDate = data.get("updatedDate");
	String page = data.get("page");
	String pageSize = data.get("pageSize");

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	SoftAssert softAssert = new SoftAssert();

	/***************************************
	 * PASO 3 Hacer una llamada tipo GET con los siguientes valores
	 ***************************************/
	addStep("Hacer una llamada tipo GET con los siguientes valores: page = a, limit = 1, updateDate = <Valor obtenido en datos de prueba, formato YYYY-MM-DD>",
		"Debe regresar un 401");
	testCase.addCodeEvidenceCurrentStep(
		"=====================Se valida el endpoint stores========================================");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStoresNoToken(page, pageSize, updatedDate);
	Assert.assertEquals(stores.getStatusCode(), 401, "Validar estatus code de endpoint stores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser: { \"code\": \"X401000\", \"message\": \"The specified token is invalid or has expired.\" }");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodyStores.getCode(), "X401000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertEquals(errorBodyStores.getMessage(), "The specified token is invalid or has expired.",
		"Validar message error de endpoint healtCheck en el response body");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_007_ValidarError401";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 401 forzándolo para el endpoint de stores";
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
	return "Acceso a la API\nLa API funcionando";
    }

    @Override
    public void beforeTest() {

    }
}

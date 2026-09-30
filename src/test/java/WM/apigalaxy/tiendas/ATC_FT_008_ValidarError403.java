package WM.apigalaxy.tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.apigalaxy.models.ErrorResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 403 forzándolo para el endpoint de tiendas.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-008-Validar error 403
 * para endpoint tiendas
 *
 * PREREQUISITOS: Acceso a la API, La API funcionando
 *
 * INSUMOS:
 * 
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_008_ValidarError403 extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_008_ValidarError403_test(HashMap<String, String> data) throws Exception {
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
	 * PASO 3 Hacer una llamada tipo GET con los siguientes valores
	 ***************************************/
	addStep("Hacer una llamada tipo GET con los siguientes valores: page = a, limit = 1, updateDate = <Valor obtenido en datos de prueba, formato YYYY-MM-DD>",
		"Debe regresar un 403");
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	Response stores = util.getStores(page, pageSize, updatedDate);
	softAssert.assertEquals(stores.getStatusCode(), 403, "Validar estatus code de endpoint stores");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.getStatusCode());

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta", "El response debe ser:\r\n" + "{\r\n"
		+ "    \"code\": \"X403000\",\r\n"
		+ "    \"message\": \"Application has been identified but it is currently suspended. Please contact the API Gateway administrator for further details.\"\r\n"
		+ "}");
	ErrorResponse errorBodyStores = stores.as(ErrorResponse.class);
	softAssert.assertEquals(errorBodyStores.getCode(), "X403000",
		"Validar estatus code de endpoint healtCheck en el response body");
	softAssert.assertEquals(errorBodyStores.getMessage(),
		"Application has been identified but it is currently suspended. Please contact the API Gateway administrator for further details.");
	testCase.addCodeEvidenceCurrentStep("El valor encontrado en el codigo es: " + stores.asPrettyString());

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_008_ValidarError403";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida el error 403 forzándolo para el endpoint de tiendas.";
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
	return "1.-Accesos validos para la API.\r\n" + "2.-La API activa y funcionando."
		+ "3.-Solicitar al equipo de soporte que desactive la aplicacion .";

    }

    @Override
    public void beforeTest() {

    }
}
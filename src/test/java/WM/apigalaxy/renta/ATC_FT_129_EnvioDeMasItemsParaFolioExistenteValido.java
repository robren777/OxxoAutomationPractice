package WM.apigalaxy.renta;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se envian los mismos datos de la cabecera de un registro
 * previamente creado con mas items y se valida que No se generen nuevos
 * registros y la llamada sea rechazada
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC-FT-129_EnvioDeMasItemsParaFolioExistenteValido
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7006
 * 
 * @author Guillermo Garcia
 * @date 19/05/2026
 */
public class ATC_FT_129_EnvioDeMasItemsParaFolioExistenteValido extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_129_EnvioDeMasItemsParaFolioExistenteValido_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "7000";
	String jsonEnviado = "json7000.txt";
	// se usa como prerequisito
	/***************************************
	 * PASO 1 Utilizando el token generar un segundo token desde el endpoint de
	 * OAuth2
	 ***************************************/
	addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2",
		"Debes utilizar un api token");
	testCase.addCodeEvidenceCurrentStep("Se genera el token con exito");
	// Este paso ocurre dentro de las utils en el metodo que hace la llamada a la
	// api con el proposito que no expire el token por llamada

	/***************************************
	 * PASO 2 Utilizar el token generado como header para las siguiente accion.
	 ***************************************/
	addStep("Utilizar el token generado como header para las siguiente accion.", "Se debe utilizar un OAuth2");
	testCase.addCodeEvidenceCurrentStep("Se utiliza el token");
	// Se agregan estos dos pasos para que los stakeholders esten comodos sabiendo
	// que se utiliza seguridad en la API
	 
	/***************************************
	 * PASO 3 En snowflake enviar el siguiente querie: SELECT COUNT(1) from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7006;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7000;",
		"Debe regresar 1");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieRentCount(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 7000 son: " + responseQuerieOne.get(0).get("COUNT(1)"));

	/***************************************
	 * PASO 4 Enviar el post valido 7006
	 ***************************************/
	addStep("Enviar el post valido 7006", "Debe retornar status 400");
	Response response = util.postPromotionalRents(jsonEnviado);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 400, "Se valida el estatus code");

	/***************************************
	 * PASO 5 Validar del reponse el key code
	 ***************************************/
	addStep("Validar del reponse el key code", "Debe ser \"X400000\"");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key code: " + response.jsonPath().getString("code"));
	assertEquals(response.jsonPath().getString("code"), "X400000", "Se valida el key code");

	/***************************************
	 * PASO 6 Validar del response el key message
	 ***************************************/
	addStep("Validar del response el key message",
		"Debe ser: \"The client has issued a malformed or illegal request.\"");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	assertEquals(response.jsonPath().getString("message"), "The client has issued a malformed or illegal request.",
		"Se valida el key message");

	/***************************************
	 * PASO 7 Validar del response el key details
	 ***************************************/
	addStep("Validar del response el key details", "Debe ser: Un array");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key details que contiene estos elementos: "
			+ response.jsonPath().getMap("details").size());
	assertEquals(response.jsonPath().getMap("details").isEmpty(), false, "Se valida el key details");

	/***************************************
	 * PASO 8 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser failed");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.status: "
		+ response.jsonPath().getString("details.status"));
	assertEquals(response.jsonPath().getString("details.status"), "rejected", "Se valida el key details.status");

	/***************************************
	 * PASO 9 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser true");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.hasError: "
		+ response.jsonPath().getBoolean("details.hasErrors"));
	assertEquals(response.jsonPath().getBoolean("details.hasErrors"), true, "Se valida el key details.hasError");

	/***************************************
	 * PASO 10 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.batchId: "
		+ response.jsonPath().getString("details.batchId"));
	assertEquals(response.getBody().jsonPath().get("details.batchId") instanceof String, true,
		"Se valida el key details.batchId");

	/***************************************
	 * PASO 11 Validar del response el key message
	 ***************************************/
	addStep("Validar del response el key message",
		"Debe ser: \"Operación rechazada. Algunos registros ya existen en el sistema.\"");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.message: "
		+ response.jsonPath().getString("details.message"));
	assertEquals(response.jsonPath().getString("details.message"),
		"Operación rechazada. Algunos registros ya existen en el sistema.", "Se valida el key details.message");

	/***************************************
	 * PASO 12 Validar del response el key errors
	 ***************************************/
	addStep("Validar del response el key errors",
		"Debe ser: El valor repetido con folio, year. Periodo, providerName y providerId");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.errors: "
		+ response.jsonPath().getString("details.errors[0]"));
	assertEquals(response.jsonPath().getString("details.errors[0]"),
		"Folio 7000 Año 2026 Periodo P1 ProviderName COCA-COLAs ProviderId 0002",
		"Se valida el key details.errors");

	/***************************************
	 * PASO 13 En snowflake enviar el siguiente querie: SELECT COUNT(1) from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7006;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7006;",
		"Debe regresar 1");
	responseQuerieOne = util.executeQuerySnowflake(util.querieRentCount(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 7000 son: " + responseQuerieOne.get(0).get("COUNT(1)"));
	// querie para borrar el record
	util.executeQuerySnowflake(util.querieRentDelete(testId));

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
	return "Se envian los mismos datos de la cabecera de un registro previamente creado con mas items y se valida que No se generen nuevos registros y la llamada sea rechazada";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_129_EnvioDeMasItemsParaFolioExistenteValido";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
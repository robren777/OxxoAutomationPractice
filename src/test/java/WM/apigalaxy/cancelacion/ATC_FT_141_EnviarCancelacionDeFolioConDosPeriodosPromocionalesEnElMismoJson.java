package WM.apigalaxy.cancelacion;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se intenta enviar una cancelacion a folio existente con items de
 * diferentes periodos, debe lograrse.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-141
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7018
 * 
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_141_EnviarCancelacionDeFolioConDosPeriodosPromocionalesEnElMismoJson extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_141_EnviarCancelacionDeFolioConDosPeriodosPromocionalesEnElMismoJson_test(
	    HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "7018";
	String jsonEnviado = "json7018.txt";
	String jsonEnviadoCancel = "json7018Cancel.txt";
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
	util.executeQuerySnowflake(util.querieRentDelete(testId));
	util.postPromotionalRents(jsonEnviado);

	/***************************************
	 * PASO 3 En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS,
	 * items from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL
	 * WHERE RENT_FOLIO = 7014;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS, items from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7014;",
		"Debe regresar ACTIVO");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 7018 son: " + responseQuerieOne.get(0).get("RENT_FOLIO_STATUS"));

	/***************************************
	 * PASO 4 Enviar el put valido 7018
	 ***************************************/
	addStep("Enviar el put valido 7018", "Debe retornar status 200");
	Response response = util.putPromotionalRents(jsonEnviadoCancel);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 5 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser success");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + response.jsonPath().getString("status"));
	assertEquals(response.jsonPath().getString("status"), "success", "Se valida el key estatus");

	/***************************************
	 * PASO 6 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser false");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key hasErrors: " + response.jsonPath().getBoolean("hasErrors"));
	assertEquals(response.jsonPath().getBoolean("hasErrors"), false, "Se valida el key hasErrors");

	/***************************************
	 * PASO 7 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key batchId: " + response.jsonPath().getString("batchId"));
	assertEquals(response.getBody().jsonPath().get("batchId") instanceof String, true, "Se valida el key batchId");

	/***************************************
	 * PASO 8 Validar del response el key message
	 ***************************************/
	addStep("Validar del response el key message",
		"Debe ser \"Los estados de renta y subpaquetes se han actualizado correctamente.\"");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	assertEquals(response.jsonPath().getString("message"),
		"Los estados de renta y subpaquetes se han actualizado correctamente.", "Se valida el key estatus");

	/***************************************
	 * PASO 9 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ response.jsonPath().getMap("summary").size());
	assertEquals(response.jsonPath().getMap("summary").isEmpty(), false, "Se valida el key summary");

	/***************************************
	 * PASO 10 Validar del response el key summary.totalProcessed
	 ***************************************/
	addStep("Validar del response el key summary.totalProcessed", "Debe ser 2");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.totalProcessed: "
		+ response.jsonPath().getInt("summary.totalProcessed"));
	assertEquals(response.jsonPath().getInt("summary.totalProcessed"), 2,
		"Se valida el key summary.totalProcessed");

	/***************************************
	 * PASO 11 Validar del response el key summary.updatedFolios
	 ***************************************/
	addStep("Validar del response el key summary.updatedFolios ", "Debe ser un array con el valor 7018,7018");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el primer key summary.updatedFolios: "
		+ response.jsonPath().getInt("summary.updatedFolios[0]"));
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el segundo key summary.updatedFolios: "
		+ response.jsonPath().getInt("summary.updatedFolios[1]"));
	assertEquals(response.jsonPath().getInt("summary.updatedFolios[0]"), 7018,
		"Se valida el key summary.updatedFolios");
	assertEquals(response.jsonPath().getInt("summary.updatedFolios[1]"), 7018,
		"Se valida el key summary.updatedFolios");

	/***************************************
	 * PASO 12 En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7018;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7018;",
		"Debe regresar Cancelado");
	responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El estatus del id 7018 es: " + responseQuerieOne.get(0).get("RENT_FOLIO_STATUS"));
	assertEquals(responseQuerieOne.get(0).get("RENT_FOLIO_STATUS"), "Cancelado",
		"Se valida estatus del rent folio");

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
	return "Se intenta enviar una cancelacion a folio existente con items de diferentes periodos, debe lograrse";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_141_EnviarCancelacionDeFolioConDosPeriodosPromocionalesEnElMismoJson";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
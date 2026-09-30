package WM.apigalaxy.cancelacion;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se intenta enviar una canclacion de todos los items existentes
 * en la DB sin enviar cancelacion en el folio, debe cancelar todos los items y
 * mantener el folio activo.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-140
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7017
 * 
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_140_EnviarCancelacionATodosLosItemsSinCancelarElFolio extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_140_EnviarCancelacionATodosLosItemsSinCancelarElFolio_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "7016";
	String jsonEnviado = "json7016.txt";
	String jsonEnviadoCancel = "json7016Cancel.txt";
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
	List<Map<String, Object>> responseQuerieOne = new ArrayList<>();
	responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 7016 son: " + responseQuerieOne.get(0).get("RENT_FOLIO_STATUS"));

	/***************************************
	 * PASO 4 Validar el valor de items
	 ***************************************/
	addStep("Validar el valor de items", "Debe tener dos registros con \"subPackageStatus\": \"Activo\",");
	Object itemsObj = responseQuerieOne.get(0).get("ITEMS");
	ObjectMapper mapper = new ObjectMapper();
	List<Map<String, Object>> items;
	if (itemsObj instanceof String) {
	    items = mapper.readValue((String) itemsObj, new TypeReference<List<Map<String, Object>>>() {
	    });
	} else {
	    items = mapper.convertValue(itemsObj, new TypeReference<List<Map<String, Object>>>() {
	    });
	}
	String status1 = (String) items.get(0).get("subPackageStatus");
	String status2 = (String) items.get(1).get("subPackageStatus");
	testCase.addCodeEvidenceCurrentStep(
		"En los registros con el id 7016 se encuentran estos dos subPackageStatus: estatus 1 " + status1
			+ " estatus 2 " + status2);

	/***************************************
	 * PASO 5 Enviar el put valido 7014
	 ***************************************/
	addStep("Enviar el put valido 7014", "Debe retornar status 200");
	Response response = util.putPromotionalRents(jsonEnviadoCancel);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 6 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser success");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + response.jsonPath().getString("status"));
	assertEquals(response.jsonPath().getString("status"), "success", "Se valida el key estatus");

	/***************************************
	 * PASO 7 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser false");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key hasErrors: " + response.jsonPath().getBoolean("hasErrors"));
	assertEquals(response.jsonPath().getBoolean("hasErrors"), false, "Se valida el key hasErrors");

	/***************************************
	 * PASO 8 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key batchId: " + response.jsonPath().getString("batchId"));
	assertEquals(response.getBody().jsonPath().get("batchId") instanceof String, true, "Se valida el key batchId");

	/***************************************
	 * PASO 9 Validar del response el key message
	 ***************************************/
	addStep("Validar del response el key message",
		"Debe ser \"Los estados de renta y subpaquetes se han actualizado correctamente.\"");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	assertEquals(response.jsonPath().getString("message"),
		"Los estados de renta y subpaquetes se han actualizado correctamente.", "Se valida el key estatus");

	/***************************************
	 * PASO 10 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ response.jsonPath().getMap("summary").size());
	assertEquals(response.jsonPath().getMap("summary").isEmpty(), false, "Se valida el key summary");

	/***************************************
	 * PASO 11 Validar del response el key summary.totalProcessed
	 ***************************************/
	addStep("Validar del response el key summary.totalProcessed", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.totalProcessed: "
		+ response.jsonPath().getInt("summary.totalProcessed"));
	assertEquals(response.jsonPath().getInt("summary.totalProcessed"), 1,
		"Se valida el key summary.totalProcessed");

	/***************************************
	 * PASO 12 Validar del response el key summary.updatedFolios
	 ***************************************/
	addStep("Validar del response el key summary.updatedFolios ", "Debe ser un array con el valor 7014");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.updatedFolios: "
		+ response.jsonPath().getInt("summary.updatedFolios[0]"));
	assertEquals(response.jsonPath().getInt("summary.updatedFolios[0]"), 7016,
		"Se valida el key summary.updatedFolios");

	/***************************************
	 * PASO 13 En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS,
	 * items from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL
	 * WHERE RENT_FOLIO = 7014;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS, items from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7014;",
		"Debe regresar ACTIVO");
	responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El estatus del id 7014 es: " + responseQuerieOne.get(0).get("RENT_FOLIO_STATUS"));
	assertEquals(responseQuerieOne.get(0).get("RENT_FOLIO_STATUS"), "Activo", "Se valida estatus del rent folio");

	/***************************************
	 * PASO 14 Validar el valor de items
	 ***************************************/
	addStep("Validar el valor de items", "Debe tener dos registros con \"subPackageStatus\": \"Cancelado\",");
	itemsObj = responseQuerieOne.get(0).get("ITEMS");
	if (itemsObj instanceof String) {
	    items = mapper.readValue((String) itemsObj, new TypeReference<List<Map<String, Object>>>() {
	    });
	} else {
	    items = mapper.convertValue(itemsObj, new TypeReference<List<Map<String, Object>>>() {
	    });
	}
	status1 = (String) items.get(0).get("subPackageStatus");
	status2 = (String) items.get(1).get("subPackageStatus");
	testCase.addCodeEvidenceCurrentStep("El estatus del primer subPackageStatus es: " + status1);
	assertEquals(status1, "Cancelado", "Se valida estatus del primer subPackageStatus");
	testCase.addCodeEvidenceCurrentStep("El estatus del segundo subPackageStatus es: " + status2);
	assertEquals(status2, "Cancelado", "Se valida estatus del segundo subPackageStatus");
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
	return "Se intenta enviar una canclacion de todos los items existentes en la DB sin enviar cancelacion en el folio, debe cancelar todos los items y mantener el folio activo.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_140_EnviarCancelacionATodosLosItemsSinCancelarElFolio";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
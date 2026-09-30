package WM.apigalaxy.calendarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.apigalaxy.models.ComparisonResult;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN:Es la validacion del flujo esperado de la api, se elimina un
 * registro existente en la db y se envia el registro con el mismo id pero
 * diferente informacion, se valida que en la db solo exista un set de datos
 * para ese id y que sean los ultimos enviados NOMBRE CASOS DE PRUEBA MANUAL QUE
 * SE ABARCAN: MTC-FT-080-Eliminar un registro y crear el mismo registro sin
 * cambios
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando
 *
 * INSUMOS: post datos validos 79 y post datos validos 79 v2
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_080_EliminarUnRegistroYCrearElMismoRegistroSinCambios extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_080_EliminarUnRegistroYCrearElMismoRegistroSinCambios_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	String testId = "79";
	String jsonEnviado = "json79.txt";
	// se usa como prerequisito
	util.deletePromotionalCalendars(testId);

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
	 * PASO 3 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 79;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 79;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 70 son: " + responseQuerieOne.size());
	/***************************************
	 * PASO 4 Enviar el post datos validos 79
	 ***************************************/
	addStep("Enviar el post datos validos 79", "Debe retornar status 200");
	Response response = util.postPromotionalCalendars(jsonEnviado);
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
	 * PASO 8 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ response.jsonPath().getMap("summary").size());
	assertEquals(response.jsonPath().getMap("summary").isEmpty(), false, "Se valida el key summary");

	/***************************************
	 * PASO 9 Validar del response el key summary.total
	 ***************************************/
	addStep("Validar del response el key summary.total", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary.tota: " + response.jsonPath().get("summary.tota"));
	assertEquals(response.jsonPath().getInt("summary.total"), 1, "Se valida el key summary.tota");

	/***************************************
	 * PASO 10 Validar del response el key summary.processedId
	 ***************************************/
	addStep("Validar del response el key summary.processedId", "Debe ser 70");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processedId: "
		+ response.jsonPath().get("summary.processedId"));
	assertEquals(response.jsonPath().getInt("summary.processedId"), Integer.parseInt(testId),
		"Se valida el key summary.processedId");

	/***************************************
	 * PASO 11 En snowflake enviar el siguiente querie: SELECT * FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 79;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 79;",
		"Debe regresar 1 registro");
	List<Map<String, Object>> responseQuerieData = util.executeQuerySnowflake(util.querieCalendarData(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieData.size());
	assertEquals(responseQuerieData.size(), 1, "Se valida el tamano del response de snowflake");

	/***************************************
	 * PASO 12 Hacer comparacion cruzada del json enviado contra los registros de la
	 * tabla
	 ***************************************/
	addStep("Hacer comparacion cruzada del json enviado contra los registros de la tabla",
		"Deben ser los mismos datos");
	ComparisonResult resultado = util.comparePromotionalCalendar(jsonEnviado, responseQuerieData);
	for (String line : resultado.getDetails()) {
	    testCase.addCodeEvidenceCurrentStep(line);
	}
	assertTrue(resultado.isSuccess(), "validacion de comparativa");

	/***************************************
	 * PASO 13 Enviar el Delete con el param periodsId = 79
	 ***************************************/
	addStep("Enviar el Delete con el param periodsId = 79", "Debe retornar status 200");
	Response responseDel = util.deletePromotionalCalendars(testId);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + responseDel.getStatusCode());
	assertEquals(responseDel.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 14 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser success");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + responseDel.jsonPath().getString("status"));
	assertEquals(responseDel.jsonPath().getString("status"), "success", "Se valida el key estatus");

	/***************************************
	 * PASO 15 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser false");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key hasErrors: "
		+ responseDel.jsonPath().getBoolean("hasErrors"));
	assertEquals(responseDel.jsonPath().getBoolean("hasErrors"), false, "Se valida el key hasErrors");

	/***************************************
	 * PASO 16 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key batchId: " + responseDel.jsonPath().getString("batchId"));
	assertEquals(responseDel.getBody().jsonPath().get("batchId") instanceof String, true,
		"Se valida el key batchId");

	/***************************************
	 * PASO 17 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ responseDel.jsonPath().getMap("summary").size());
	assertEquals(responseDel.jsonPath().getMap("summary").isEmpty(), false, "Se valida el key summary");

	/***************************************
	 * PASO 18 Validar del response el key summary.total
	 ***************************************/
	addStep("Validar del response el key summary.total", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.tota: "
		+ responseDel.jsonPath().get("summary.tota"));
	assertEquals(responseDel.jsonPath().getInt("summary.total"), 1, "Se valida el key summary.tota");

	/***************************************
	 * PASO 19 Validar del response el key summary.processed
	 ***************************************/
	addStep("Validar del response el key summary.processed", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processed: "
		+ responseDel.jsonPath().get("summary.processed"));
	assertEquals(responseDel.jsonPath().getInt("summary.processed"), 1, "Se valida el key summary.processed");

	/***************************************
	 * PASO 20 Validar del response el key summary.failed
	 ***************************************/
	addStep("Validar del response el key summary.failed", "Debe ser 0");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.failed: "
		+ responseDel.jsonPath().get("summary.failed"));
	assertEquals(responseDel.jsonPath().getInt("summary.failed"), 0, "Se valida el key summary.failed");

	/***************************************
	 * PASO 21 Validar del response el key errors
	 ***************************************/
	addStep("Validar del response el key errors", "Debe ser un array vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key errors que contiene estos elementos: "
			+ responseDel.jsonPath().getList("errors").size());
	assertEquals(responseDel.jsonPath().getList("errors").isEmpty(), true, "Se valida el key errors");

	/***************************************
	 * PASO 22 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 79;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 79;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieDataFinal = util.executeQuerySnowflake(util.querieCalendarData(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieDataFinal.size());
	assertEquals(responseQuerieDataFinal.size(), 0, "Se valida el tamano del response de snowflake");

	/***************************************
	 * PASO 23 Enviar el post datos validos 79
	 ***************************************/
	addStep("Enviar el post datos validos 79", "Debe retornar status 200");
	Response responseCambios = util.postPromotionalCalendars(jsonEnviado);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + responseCambios.getStatusCode());
	assertEquals(responseCambios.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 24 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser success");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + responseCambios.jsonPath().getString("status"));
	assertEquals(responseCambios.jsonPath().getString("status"), "success", "Se valida el key estatus");

	/***************************************
	 * PASO 25 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser false");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key hasErrors: "
		+ responseCambios.jsonPath().getBoolean("hasErrors"));
	assertEquals(responseCambios.jsonPath().getBoolean("hasErrors"), false, "Se valida el key hasErrors");

	/***************************************
	 * PASO 26 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key batchId: "
		+ responseCambios.jsonPath().getString("batchId"));
	assertEquals(responseCambios.getBody().jsonPath().get("batchId") instanceof String, true,
		"Se valida el key batchId");

	/***************************************
	 * PASO 27 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ responseCambios.jsonPath().getMap("summary").size());
	assertEquals(responseCambios.jsonPath().getMap("summary").isEmpty(), false, "Se valida el key summary");

	/***************************************
	 * PASO 28 Validar del response el key summary.total
	 ***************************************/
	addStep("Validar del response el key summary.total", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.tota: "
		+ responseCambios.jsonPath().get("summary.tota"));
	assertEquals(responseCambios.jsonPath().getInt("summary.total"), 1, "Se valida el key summary.tota");

	/***************************************
	 * PASO 29 Validar del response el key summary.processedId
	 ***************************************/
	addStep("Validar del response el key summary.processedId", "Debe ser 79");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processedId: "
		+ responseCambios.jsonPath().get("summary.processedId"));
	assertEquals(responseCambios.jsonPath().getInt("summary.processedId"), Integer.parseInt(testId),
		"Se valida el key summary.processedId");

	/***************************************
	 * PASO 30 En snowflake enviar el siguiente querie: SELECT * FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 79;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 79;",
		"Debe regresar 1 registro");
	List<Map<String, Object>> responseQuerieDataCambio = util
		.executeQuerySnowflake(util.querieCalendarData(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieDataCambio.size());
	assertEquals(responseQuerieDataCambio.size(), 1, "Se valida el tamano del response de snowflake");

	/***************************************
	 * PASO 31 Hacer comparacion cruzada del json enviado contra los registros de la
	 * tabla
	 ***************************************/
	addStep("Hacer comparacion cruzada del json enviado contra los registros de la tabla",
		"Deben ser los mismos datos");
	ComparisonResult resultadoCambio = util.comparePromotionalCalendar(jsonEnviado, responseQuerieDataCambio);
	for (String line : resultadoCambio.getDetails()) {
	    testCase.addCodeEvidenceCurrentStep(line);
	}
	assertTrue(resultadoCambio.isSuccess(), "validacion de comparativa");

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_080_EliminarUnRegistroYCrearElMismoRegistroSinCambios";
    }

    @Override
    public String setTestDescription() {
	return "Es la validacion del flujo esperado de la api, se elimina un registro existente en la db y se envia el registro con el mismo id pero diferente informacion, se valida que en la db solo exista un set de datos para ese id y que sean los ultimos enviados\r\n"
		+ "";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestInstanceID() {
	return "1";
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\r\n" + "La API funcionando\r\n" + "Acceso a snowflake\r\n"
		+ "Un set de datos que no este en la DB\r\n" + "";

    }

    @Override
    public void beforeTest() {

    }
}

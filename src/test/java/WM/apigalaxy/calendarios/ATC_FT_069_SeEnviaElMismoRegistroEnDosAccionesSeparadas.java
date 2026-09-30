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
 * DESCRIPCIÓN:Se envia un post con todos los datos correctos, se valida su
 * creacion en tabla y se vuelve a enviar, se deben conservar ambos registros en
 * tabla. NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-069-Se envia el
 * mismo registro en dos acciones separadas
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando Acceso a snowflake Un set
 * de datos que no este en la DB
 *
 * INSUMOS: Post valido 77
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_069_SeEnviaElMismoRegistroEnDosAccionesSeparadas extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_069_SeEnviaElMismoRegistroEnDosAccionesSeparadas_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	String testId = "78";
	String jsonEnviado = "json78.txt";
	final String STATUS = "status";
	final String HAS_ERRORS = "hasErrors";
	final String BATCH_ID = "batchId";
	final String SUMMARY = "summary";
	final String SUMMARY_PROCESSED_ID = "summary.processedId";
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
	 * 78;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 78;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 78 son: " + responseQuerieOne.size());
	assertEquals(responseQuerieOne.size(), 0);
	
	/***************************************
	 * PASO 4 Enviar el post datos validos 78
	 ***************************************/
	addStep("Enviar el post datos validos 78", "Debe retornar status 200");
	Response response = util.postPromotionalCalendars(jsonEnviado);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 5 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser success");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + response.jsonPath().getString(STATUS));
	assertEquals(response.jsonPath().getString(STATUS), "success", "Se valida el key estatus");

	/***************************************
	 * PASO 6 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser false");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key hasErrors: " + response.jsonPath().getBoolean(HAS_ERRORS));
	assertEquals(response.jsonPath().getBoolean(HAS_ERRORS), false, "Se valida el key hasErrors");

	/***************************************
	 * PASO 7 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key batchId: " + response.jsonPath().getString(BATCH_ID));
	assertEquals(response.getBody().jsonPath().get(BATCH_ID) instanceof String, true, "Se valida el key batchId");

	/***************************************
	 * PASO 8 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ response.jsonPath().getMap(SUMMARY).size());
	assertEquals(response.jsonPath().getMap(SUMMARY).isEmpty(), false, "Se valida el key summary");

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
	addStep("Validar del response el key summary.processedId", "Debe ser 78");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processedId: "
		+ response.jsonPath().get(SUMMARY_PROCESSED_ID));
	assertEquals(response.jsonPath().getInt(SUMMARY_PROCESSED_ID), Integer.parseInt(testId),
		"Se valida el key summary.processedId");

	/***************************************
	 * PASO 11 En snowflake enviar el siguiente querie: SELECT * FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 78;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 78;",
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
	 * PASO 4 Enviar el post datos validos 78
	 ***************************************/
	addStep("Enviar el post datos validos 78", "Debe retornar status 200");
	response = util.postPromotionalCalendars(jsonEnviado);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 5 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key status", "Debe ser success");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + response.jsonPath().getString(STATUS));
	assertEquals(response.jsonPath().getString(STATUS), "success", "Se valida el key estatus");

	/***************************************
	 * PASO 6 Validar del response el key hasError
	 ***************************************/
	addStep("Validar del response el key hasError", "Debe ser false");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key hasErrors: " + response.jsonPath().getBoolean(HAS_ERRORS));
	assertEquals(response.jsonPath().getBoolean(HAS_ERRORS), false, "Se valida el key hasErrors");

	/***************************************
	 * PASO 7 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key batchId: " + response.jsonPath().getString(BATCH_ID));
	assertEquals(response.getBody().jsonPath().get(BATCH_ID) instanceof String, true, "Se valida el key batchId");

	/***************************************
	 * PASO 8 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary que contiene estos elementos: "
			+ response.jsonPath().getMap(SUMMARY).size());
	assertEquals(response.jsonPath().getMap(SUMMARY).isEmpty(), false, "Se valida el key summary");

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
	addStep("Validar del response el key summary.processedId", "Debe ser 78");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processedId: "
		+ response.jsonPath().get(SUMMARY_PROCESSED_ID));
	assertEquals(response.jsonPath().getInt(SUMMARY_PROCESSED_ID), Integer.parseInt(testId),
		"Se valida el key summary.processedId");

	/***************************************
	 * PASO 11 En snowflake enviar el siguiente querie: SELECT * FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 78;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 78;",
		"Debe regresar 1 registro");
	responseQuerieData = util.executeQuerySnowflake(util.querieCalendarData(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieData.size());
	assertEquals(responseQuerieData.size(), 1, "Se valida el tamano del response de snowflake");

	/***************************************
	 * PASO 12 Hacer comparacion cruzada del json enviado contra los registros de la
	 * tabla
	 ***************************************/
	addStep("Hacer comparacion cruzada del json enviado contra los registros de la tabla",
		"Deben ser los mismos datos");
	resultado = util.comparePromotionalCalendar(jsonEnviado, responseQuerieData);
	for (String line : resultado.getDetails()) {
	    testCase.addCodeEvidenceCurrentStep(line);
	}
	assertTrue(resultado.isSuccess(), "validacion de comparativa");

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_069_SeEnviaElMismoRegistroEnDosAccionesSeparadas";
    }

    @Override
    public String setTestDescription() {
	return "Se envia un post con todos los datos correctos, se valida su creacion en tabla y se vuelve a enviar, se deben conservar ambos registros en tabla.\r\n"
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

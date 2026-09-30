package WM.apigalaxy.calendarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN:Se envian dos registros con diferentes periodsId y se valida el
 * response con error y se valida que no se genere ninguno de los dos registros
 * en la DB. NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-068-Envio de
 * dos diferentes periodsId en un solo request
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando Acceso a snowflake Un set
 * de datos que no este en la DB
 *
 * INSUMOS: Post valido 7576
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_068_EnvioDeDosDiferentesPeriodsidEnUnSoloRequest extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_068_EnvioDeDosDiferentesPeriodsidEnUnSoloRequest_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	String testId = "75";
	String testIdB = "76";
	String jsonEnviado = "json75.txt";
	final String EXPECTED_RESULT = "Debe regresar 0";
	// se usa como prerequisito
	util.deletePromotionalCalendars(testId);
	util.deletePromotionalCalendars(testIdB);
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
	 * 75;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 75;",
		EXPECTED_RESULT);
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 75 son: " + responseQuerieOne.size());
	assertEquals(responseQuerieOne.size(), 0);
	
	/***************************************
	 * PASO 4 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 76;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 76;",
		EXPECTED_RESULT);
	List<Map<String, Object>> responseQuerieTwo = util.executeQuerySnowflake(util.querieCalendarCount(testIdB));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 76 son: " + responseQuerieTwo.size());
	assertEquals( responseQuerieTwo.size(), 0, "Se valida el estatus code");
	
	/***************************************
	 * PASO 5 Enviar el Post datos duplicados 7576
	 ***************************************/
	addStep("Enviar el Post datos duplicados 7576", "Debe retornar status 400");
	Response response = util.postPromotionalCalendars(jsonEnviado);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 400, "Se valida el estatus code");

	/***************************************
	 * PASO 6 Validar del response el key code
	 ***************************************/
	addStep("Validar del response el key code", "Debe ser X400000");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key code: " + response.jsonPath().getString("code"));
	assertEquals(response.jsonPath().getString("code"), "X400000", "Se valida el key code");

	/***************************************
	 * PASO 7 Validar del response el key message
	 ***************************************/
	addStep("Validar del response el key message",
		"Debe ser  The client has issued a malformed or illegal request.");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	assertEquals(response.jsonPath().getString("message"), "The client has issued a malformed or illegal request.",
		"Se valida el key message");

	/***************************************
	 * PASO 8 Validar del response el key details
	 ***************************************/
	addStep("Validar del response el key details", "Debe ser un json array");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key details que contiene estos elementos: "
			+ response.jsonPath().get("details"));
	assertNotNull(response.jsonPath().get("details"), "Se valida el key details");

	/***************************************
	 * PASO 9 Validar del response el key details.status
	 ***************************************/
	addStep("Validar del response el key details.status", "Debe ser failed");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key hasErrors: "
		+ response.jsonPath().getString("details.status"));
	assertEquals(response.jsonPath().getString("details.status"), "failed", "Se valida el key details.status");

	/***************************************
	 * PASO 10 Validar del response el key details.batchId
	 ***************************************/
	addStep("Validar del response el key details.batchId", "Debe ser un valor de tipo texto");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.batchId: "
		+ response.jsonPath().getString("details.batchId"));
	assertEquals(response.getBody().jsonPath().get("details.batchId") instanceof String, true,
		"Se valida el key details.batchId");

	/***************************************
	 * PASO 11 Validar del response el key details.hasErrors
	 ***************************************/
	addStep("Validar del response el key details.hasErrors", "Debe ser true");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key  details.hasErrors: "
		+ response.jsonPath().getBoolean("details.hasErrors"));
	assertEquals(response.jsonPath().getBoolean("details.hasErrors"), true, "Se valida el key  details.hasErrors");

	/***************************************
	 * PASO 12 Validar del response el key details.message
	 ***************************************/
	addStep("Validar del response el key details.message",
		"Debe ser Se encontraron inconsistencias en la matriz. No se realizó ninguna operación.");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.message: "
		+ response.jsonPath().getString("details.message"));
	assertEquals(response.jsonPath().getString("details.message"),
		"Se encontraron inconsistencias en la matriz. No se realizó ninguna operación.",
		"Se valida el key details.message");

	/***************************************
	 * PASO 13 Validar del response el key details.errors
	 ***************************************/
	addStep("Validar del response el key details.errors", "Debe ser un array de json arrays");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.errors: "
		+ response.jsonPath().getList("details.errors").size());
	assertEquals(response.jsonPath().getList("details.errors").isEmpty(), false,
		"Se encontraron inconsistencias en la matriz. No se realizó ninguna operación.");

	/***************************************
	 * PASO 15 Validar el registro details.errors con index 2
	 ***************************************/
	addStep("Validar  el registro details.errors con index 1",
		"El valor index debe ser 1, el valor periodsId debe ser 76, el valor reason debe ser Inconsistencia de ID: Se esperaba 75 pero se recibió 76");
	assertEquals(response.jsonPath().getInt("details.errors[0].index"),1);
	assertEquals(Integer.parseInt(testIdB), response.jsonPath().getInt("details.errors[0].periodsId"));
	assertEquals(response.jsonPath().getString("details.errors[0].reason"),"Inconsistencia de ID: Se esperaba 75 pero se recibió 76");

	/***************************************
	 * PASO 16 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 75;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 75;",
		EXPECTED_RESULT);
	List<Map<String, Object>> responseQueriePostOne = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 75 son: " + responseQueriePostOne.size());
	assertEquals(responseQueriePostOne.size(),0);
	
	/***************************************
	 * PASO 17 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 76;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 76;",
		EXPECTED_RESULT);
	List<Map<String, Object>> responseQueriePostTwo  = util.executeQuerySnowflake(util.querieCalendarCount(testIdB));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 76 son: " + responseQueriePostTwo.size());
	assertEquals(responseQueriePostTwo.size(),0);

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_068_EnvioDeDosDiferentesPeriodsidEnUnSoloRequest";
    }

    @Override
    public String setTestDescription() {
	return "Se envian dos registros con diferentes periodsId y se valida el response con error y se valida que no se genere ninguno de los dos registros en la DB.";
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

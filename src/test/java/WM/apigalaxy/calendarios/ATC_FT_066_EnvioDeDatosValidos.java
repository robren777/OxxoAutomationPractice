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
 * DESCRIPCIÓN:Se envia un set de datos generados para probar cada campo en base
 * a su tipo de dato, en esta prueba se envian los escenarios validos para cada
 * tipo de dato, el endpoint valida por registro por lo que no se ve la
 * necesidad de hacer una prueba aislada por cada campo, se valida el response y
 * se valida que se generen los regsitros en la db. NOMBRE CASOS DE PRUEBA
 * MANUAL QUE SE ABARCAN: MTC-FT-066-Envio de datos validos
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando Acceso a snowflake Un set
 * de datos que no este en la DB
 *
 * INSUMOS: Post valido 73
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_066_EnvioDeDatosValidos extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_066_EnvioDeDatosValidos_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "73";
	String jsonEnviado = "post valido 73(ValidData).txt";
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
	 * 73;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 73;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 73 son: " + responseQuerieOne.size());
	assertEquals(responseQuerieOne.size(), 0);
	
	/***************************************
	 * PASO 4 Enviar el post valido 73
	 ***************************************/
	addStep("Enviar el post valido 73", "Debe retornar status 200");
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
	addStep("Validar del response el key summary.total", "Debe ser 3");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key summary.tota: " + response.jsonPath().get("summary.tota"));
	assertEquals(response.jsonPath().getInt("summary.total"), 3, "Se valida el key summary.total");

	/***************************************
	 * PASO 10 Validar del response el key summary.processedId
	 ***************************************/
	addStep("Validar del response el key summary.processedId", "Debe ser 73");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processedId: "
		+ response.jsonPath().get("summary.processedId"));
	assertEquals(response.jsonPath().getInt("summary.processedId"), Integer.parseInt(testId),
		"Se valida el key summary.processedId");

	/***************************************
	 * PASO 11 En snowflake enviar el siguiente querie: SELECT * FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 71;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 71;",
		"Debe regresar xx registros");
	List<Map<String, Object>> responseQuerieData = util.executeQuerySnowflake(util.querieCalendarData(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieData.size());
	assertEquals(responseQuerieData.size(), 3, "Se valida el tamano del response de snowflake");

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

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_066_EnvioDeDatosValidos";
    }

    @Override
    public String setTestDescription() {
	return "Se envia un set de datos generados para probar cada campo en base a su tipo de dato, en esta prueba se envian los escenarios validos para cada tipo de dato, el endpoint valida por registro por lo que no se ve la necesidad de hacer una prueba aislada por cada campo, se valida el response y se valida que se generen los regsitros en la db.\r\n"
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

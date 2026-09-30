package WM.apigalaxy.renta;

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
 * DESCRIPCIÓN: happy path, se envian headers correctos, se envian valores
 * aptos, se valida que se crea el registro en la db, se valida el response, se
 * valida que la informacion enviada sea la misma en la db NOMBRE CASOS DE
 * PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-123-Envio de un registro para endpoint
 * de renta espacios
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7000
 * 
 * @author Guillermo Garcia
 * @date 19/05/2026
 */
public class ATC_FT_123_EnvioDeUnRegistroParaEndpointDeRentaEspacios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_123_EnvioDeUnRegistroParaEndpointDeRentaEspacios_test(HashMap<String, String> data)
	    throws Exception {
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
	 * RENT_FOLIO = 7000;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7000;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieRentCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 7000 son: " + responseQuerieOne.size());

	/***************************************
	 * PASO 4 Enviar el post valido 7000
	 ***************************************/
	addStep("Enviar el post valido 7000", "Debe retornar status 200");
	Response response = util.postPromotionalRents(jsonEnviado);
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
	 * PASO 10 En snowflake enviar el siguiente querie: SELECT * from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7000;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT * from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7000;",
		"Debe regresar 1 registros");
	List<Map<String, Object>> responseQuerieData = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieData.size());
	assertEquals(responseQuerieData.size(), 1, "Se valida el tamano del response de snowflake");

	/***************************************
	 * PASO 11 Hacer comparacion cruzada del json enviado contra los registros de la
	 * tabla
	 ***************************************/
	addStep("Hacer comparacion cruzada del json enviado contra los registros de la tabla",
		"Deben ser los mismos datos");
	ComparisonResult resultado = util.comparePromotionalRents(jsonEnviado, responseQuerieData);
	assertTrue(resultado.isSuccess(), "validacion de comparativa");
	// querie para borrar el record
	util.executeQuerySnowflake(util.querieRentDelete(testId));
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "\"Acceso a la API\r\n" + "La API funcionando\r\n" + "Acceso a snowflake\r\n"
		+ "Un set de datos que no este en la DB\r\n" + "\"\r\n" + "";
    }

    @Override
    public String setTestDescription() {
	return "happy path, se envian headers correctos, se envian valores aptos, se valida que se crea el registro en la db, se valida el response, se valida que la informacion enviada sea la misma en la db\r\n"
		+ "";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_123_EnvioDeUnRegistroParaEndpointDeRentaEspacios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
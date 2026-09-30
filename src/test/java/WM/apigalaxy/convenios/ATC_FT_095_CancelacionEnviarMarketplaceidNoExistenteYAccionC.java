package WM.apigalaxy.convenios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se envia el post con la accion cancelar y con un marketplaceId
 * no existente, por lo que deberia generar error, se valida response y que no
 * se genere el registro en la db
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-095-cancelacion enviar
 * marketplaceId no existente y accion C
 *
 * PREREQUISITOS: - Acceso a la API y la API funcionando - JSON con POST ID MKT
 * 715 - Acceso a Snowflake - Acceso a DB Convenios
 *
 * INSUMOS: -
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_095_CancelacionEnviarMarketplaceidNoExistenteYAccionC extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_095_CancelacionEnviarMarketplaceidNoExistenteYAccionC_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "707";
	String jsonCambio = "json707Cancel.txt";

	/***************************************
	 * PASO 1 Hacer conexion a snowflake
	 ***************************************/
	addStep("Hacer conexion a snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion.");
	// Ya se refactorizo un mtodo para hacer conexion directa
	// querie para borrar el record como precondicion pero el before no funciona
	// bien en el framework
	util.executeQuerySnowflake(util.querieConvenioDelete(testId));

	/***************************************
	 * PASO 2 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
	 * ID_MARKETPLACE = '707';
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE = '707';",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioCount(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 707 son: " + responseQuerieOne.get(0).get("COUNT(1)"));
	assertEquals(responseQuerieOne.get(0).get("COUNT(1)"), 9);

	/***************************************
	 * PASO 3 Obtener el token de la api
	 ***************************************/
	addStep("Obtener el token de la api", "Debe poder ser obtenido y utiizado en la siguiente paso");
	testCase.addCodeEvidenceCurrentStep("Se obtiene y sera utilizado");
	// Este paso se usa en el util del response

	/***************************************
	 * PASO 4 Enviar el archivo 707
	 ***************************************/
	addStep("Enviar el archivo 707", "Debe dar un status 400");
	Response response = util.postAgreements(jsonCambio);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 400, "Se valida el estatus code");

	/***************************************
	 * PASO 5 Validar del response el key status
	 ***************************************/
	addStep("Validar del response el key details.status", "Debe ser failed");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key status: " + response.jsonPath().getString("details.status"));
	assertEquals(response.jsonPath().getString("details.status"), "failed", "Se valida el key details.estatus");

	/***************************************
	 * PASO 6 Validar del response el key hasErrors
	 ***************************************/
	addStep("Validar del response el key details.hasErrors", "Debe ser true");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key hasErrors: "
		+ response.jsonPath().getBoolean("details.hasErrors"));
	assertEquals(response.jsonPath().getBoolean("details.hasErrors"), true, "Se valida el key details.hasErrors");

	/***************************************
	 * PASO 7 Validar del response el key batchId
	 ***************************************/
	addStep("Validar del response el key details.batchId", "Debe ser un valor tipo texto");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key batchId: "
		+ response.jsonPath().getString("details.batchId"));
	assertEquals(response.getBody().jsonPath().get("details.batchId") instanceof String, true,
		"Se valida el key batchId");

	/***************************************
	 * PASO 8 Validar del response el key summary
	 ***************************************/
	addStep("Validar del response el key details.summary", "Debe ser un json array no vacio");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key details.summary que contiene estos elementos: "
			+ response.jsonPath().getMap("details.summary").size());
	assertEquals(response.jsonPath().getMap("details.summary").isEmpty(), false, "Se valida el key summary");

	/***************************************
	 * PASO 9 Validar del response el key summary.total
	 ***************************************/
	addStep("Validar del response el key details.summary.total", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.summary.total: "
		+ response.jsonPath().getInt("details.summary.total"));
	assertEquals(response.jsonPath().getInt("details.summary.total"), 1, "Se valida el key details.summary.total");

	/***************************************
	 * PASO 10 Validar del response el key summary.processed
	 ***************************************/
	addStep("Validar del response el key summary.processed", "Debe ser 0");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.summary.processed: "
		+ response.jsonPath().getInt("details.summary.processed"));
	assertEquals(response.jsonPath().getInt("details.summary.processed"), 0,
		"Se valida el key details.summary.processed");

	/***************************************
	 * PASO 11 Validar del response el key summary.failed
	 ***************************************/
	addStep("Validar del response el key summary.failed", "Debe ser 1");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.summary.failed: "
		+ response.jsonPath().getInt("details.summary.failed"));
	assertEquals(response.jsonPath().getInt("details.summary.failed"), 1,
		"Se valida el key details.summary.failed");

	/***************************************
	 * PASO 12 Validar el code
	 ***************************************/
	addStep("Validar el code", "Debe ser X400000");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key code: " + response.jsonPath().getString("code"));
	assertEquals(response.jsonPath().getString("code"), "X400000", "Se valida el key code");

	/***************************************
	 * PASO 13 Validar el message
	 ***************************************/
	addStep("Validar el message", "Debe ser The client has issued a malformed or illegal request.");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	assertEquals(response.jsonPath().getString("message"), "The client has issued a malformed or illegal request.",
		"Se valida el key message");

	/***************************************
	 * PASO 14 Validar el details.errors
	 ***************************************/
	addStep("Validar el details.errors", "Debe ser un array que no esta vacio");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.errors: "
		+ response.jsonPath().getList("details.errors"));
	assertEquals(response.jsonPath().getList("details.errors").isEmpty(), false, "Se valida el key details.errors");

	/***************************************
	 * PASO 15 Validar el details.errors.marketplaceId
	 ***************************************/
	addStep("Validar el details.errors.marketplaceId", "Debe ser 707");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key details.errors[0].marketplaceId: "
			+ response.jsonPath().getInt("details.errors[0].marketplaceId"));
	assertEquals(response.jsonPath().getInt("details.errors[0].marketplaceId"), 707,
		"Se valida el key details.errors[0].marketplaceId");

	/***************************************
	 * PASO 16 Validar el details.errors.reason
	 ***************************************/
	addStep("Validar el details.errors.reason",
		"Debe ser No se puede procesar la acción 'C' porque el registro '707' no existe en el sistema.");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key details.errors[0].reason: "
		+ response.jsonPath().getString("details.errors[0].reason"));
	assertEquals(response.jsonPath().getString("details.errors[0].reason"),
		"No se puede procesar la acción 'C' porque el registro '707' no existe en el sistema.",
		"Se valida el key details.errors[0].reason");

	/***************************************
	 * PASO 17 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
	 * ID_MARKETPLACE = '715';
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE = '707';",
		"Debe regresar 0");
	responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioCount(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 707 son: " + responseQuerieOne.get(0).get("COUNT(1)"));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API y la API funcionando\\nJSON con POST ID MKT 715\\nAcceso a Snowflake\\nAcceso a DB Convenios";
    }

    @Override
    public String setTestDescription() {
	return "Se envia el post con la accion cancelar y con un marketplaceId no existente,  por lo que deberia generar error, se valida response y que no se genere el registro en la db";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_095_CancelacionEnviarMarketplaceidNoExistenteYAccionC";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

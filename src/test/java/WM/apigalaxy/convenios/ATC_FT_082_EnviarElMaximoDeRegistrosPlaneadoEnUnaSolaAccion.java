package WM.apigalaxy.convenios;

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
 * DESCRIPCIÓN: Se envian 1000 registros validos en una sola accion.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-082-Enviar el maximo de
 * registros planeado en una sola accion
 *
 * PREREQUISITOS: - Acceso a la API y la API funcionando - JSON con POST ID MKT
 * 702 - Acceso a Snowflake - Acceso a DB Convenios
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia
 *
 * @date 29/05/2026
 */
public class ATC_FT_082_EnviarElMaximoDeRegistrosPlaneadoEnUnaSolaAccion extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_082_EnviarElMaximoDeRegistrosPlaneadoEnUnaSolaAccion_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String jsonEnviado = "post1k.json";
	final String COUNT = "COUNT(1)";
	final String DATE = "2026-06-12";
	/***************************************
	 * PASO 1 Hacer conexion a snowflake
	 ***************************************/
	addStep("Hacer conexion a snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion.");
	
	/***************************************
	 * PASO 2 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
	 * ID_MARKETPLACE = '702';
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE = '702';",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util
		.executeQuerySnowflake(util.querieConvenioCountMil(DATE));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 701 son: " + responseQuerieOne.get(0).get(COUNT));
	assertEquals(responseQuerieOne.get(0).get(COUNT), 0);

	/***************************************
	 * PASO 3 Obtener el token de la api
	 ***************************************/
	addStep("Obtener el token de la api", "Debe poder ser obtenido y utiizado en la siguiente paso");
	testCase.addCodeEvidenceCurrentStep("Se obtiene y sera utilizado");
//Este paso se usa en el util del response

	/***************************************
	 * PASO 4 Enviar el archivo 702
	 ***************************************/
	addStep("Enviar el archivo 702", "Debe dar un status 200");
	Response response = util.postAgreements(jsonEnviado);
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
	 * PASO 6 Validar del response el key hasErrors
	 ***************************************/
	addStep("Validar del response el key hasErrors", "Debe ser false");
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
	addStep("Validar del response el key message", "Debe ser \"Los registros se han guardado correctamente.\"");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	assertEquals(response.jsonPath().getString("message"), "Los registros se han guardado correctamente.",
		"Se valida el key message");

	/***************************************
	 * PASO 9 Validar del response el key summary.total
	 ***************************************/
	addStep("Validar del response el key summary.total", "Debe ser 1000");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.total: "
		+ response.jsonPath().getInt("summary.total"));
	assertEquals(response.jsonPath().getInt("summary.total"), 1000, "Se valida el key summary.total");

	/***************************************
	 * PASO 10 Validar del response el key summary.processed
	 ***************************************/
	addStep("Validar del response el key summary.processed", "Debe ser 1000");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.processed: "
		+ response.jsonPath().getInt("summary.processed"));
	assertEquals(response.jsonPath().getInt("summary.processed"), 1000, "Se valida el key summary.processed");

	/***************************************
	 * PASO 11 Validar del response el key summary.failed
	 ***************************************/
	addStep("Validar del response el key summary.failed", "Debe ser 0");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el siguiente valor en el key summary.failed: "
		+ response.jsonPath().getInt("summary.failed"));
	assertEquals(response.jsonPath().getInt("summary.failed"), 0, "Se valida el key summary.failed");

	/***************************************
	 * PASO 12 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
	 * ID_MARKETPLACE = '702';
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE = '702';",
		"Debe regresar 1000 registros");
	responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioCountMil(DATE));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 701 son: " + responseQuerieOne.get(0).get(COUNT));

	/***************************************
	 * PASO 13 Hacer comparacion cruzada del json enviado contra los registros de la
	 * tabla
	 ***************************************/
	addStep("Hacer comparacion cruzada del json enviado contra los registros de la tabla",
		"Deben ser los mismos datos");
	responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioMil(DATE));
	ComparisonResult resultado = util.compareAgreements(jsonEnviado, responseQuerieOne);
	assertTrue(resultado.isSuccess(), "validacion de comparativa");
//querie para borrar el record
	util.executeQuerySnowflake(util.querieConvenioDeleteMil("2026-06-08"));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API y la API funcionando\\nJSON con POST ID MKT 702\\nAcceso a Snowflake\\nAcceso a DB Convenios";
    }

    @Override
    public String setTestDescription() {
	return "Se envian 1000 registros validos en una sola accion.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_082_EnviarElMaximoDeRegistrosPlaneadoEnUnaSolaAccion";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

package WM.apigalaxy.folios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se validan los datos en la tabla de convenios en snowflake
 * contra los registros presentados en la API.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-042-Validar datos input
 * vs salida endpoint agreement/folios
 *
 * PREREQUISITOS: - Acceso a la API - Acceso a Snowflake - Informacion Valida en
 * las tablas de Snowflake - La API funcionando
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia -
 *
 * @date 29/05/2026
 */
public class ATC_FT_042_ValidarDatosInputVsSalidaEndpointAgreementFolios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_042_ValidarDatosInputVsSalidaEndpointAgreementFolios_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String query = "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS where id_marketplace = 708 and Accion_convenio = 'C';";
	String queryData = "SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS where id_marketplace = 708 and Accion_convenio = 'C';";

	/***************************************
	 * PASO 1 Hacer conexion a Snowflake
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "Se puede hacer conexion a snowflake");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion.");

	/***************************************
	 * PASO 2 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS;
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS;",
		"Almacenar la respuesta");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(query);
	int cuenta = ((Long) responseQuerieOne.get(0).get("COUNT(1)")).intValue();
	testCase.addCodeEvidenceCurrentStep("El total de regsitros son: " + cuenta);

	addStep("Ejecutar el siguiente querie:SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS where id_marketplace = 708 and Accion_convenio = 'C';",
		"Almacenar la respuesta");
	List<Map<String, Object>> responseQuerieTodo = util.executeQuerySnowflake(queryData);
	testCase.addCodeEvidenceCurrentStep("El total de regsitros son: " + responseQuerieTodo.size());

	/***************************************
	 * PASO 3 comparar la respuesta del querie del count contra el metadata de la
	 * api, totalItems
	 ***************************************/
	addStep("comparar la respuesta del querie del count contra el metadata de la api, totalItems",
		"Deben ser los mismos");
	Response response = util.getAgreementFolios(708, 1, 1, "1998-08-06", "true");
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key totalItems: " + response.jsonPath().getInt("totalItems"));
	assertEquals(response.jsonPath().getInt("totalItems"), cuenta, "Se valida el key totalItems");

	/***************************************
	 * PASO 4 Comparar la respuesta del querie de todos los registros, realizando
	 * una llamada a la api
	 ***************************************/
	addStep("Comparar la respuesta del querie de todos los registros, realizando una llamada a la api",
		"Deben ser los mismos");
	assertTrue(util.validateAgreementAgainstSnowflake(response, responseQuerieTodo.get(0)));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\\nAcceso a Snowflake\\nInformacion Valida en las tablas de Snowflake\\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Se validan los datos en la tabla de convenios en snowflake contra los registros presentados en la API.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_042_ValidarDatosInputVsSalidaEndpointAgreementFolios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

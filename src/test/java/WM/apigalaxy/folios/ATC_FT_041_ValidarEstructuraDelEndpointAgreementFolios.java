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
 * DESCRIPCIÓN: Happy path para validar la estructura del json response y la
 * seguridad de la api
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-041-Validar estructura
 * del endpoint agreement/folios
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia -
 *
 * @date 29/05/2026
 */
public class ATC_FT_041_ValidarEstructuraDelEndpointAgreementFolios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_041_ValidarEstructuraDelEndpointAgreementFolios_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String query = "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS;";
	final String TEXTO = "texto";
	final String EXPECTED = "Debe ser texto";
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

	/***************************************
	 * PASO 3 Realizar una nueva consulta con los siguientes datos: page = 1,
	 * pageSize = 1, createdDate = 1998-08-06
	 * https://api-dev.oxxo.io/exp-marketplace-space-leasing/api/v1/agreement/folios?page=1&pageSize=1&createdDate=1998-08-06
	 ***************************************/
	addStep("Realizar una nueva consulta con los siguientes datos:page = 1,pageSize = 1,createdDate = 1998-08-06https://api-dev.oxxo.io/exp-marketplace-space-leasing/api/v1/agreement/folios?page=1&pageSize=1&createdDate=1998-08-06",
		"Debe poder enviarse la consulta");
	Response response = util.getAgreementFolios(0, 1, 1, "1998-08-06", "No enviar");

	/***************************************
	 * PASO 4 Validar response
	 ***************************************/
	addStep("Validar response", "Debe tener un codigo 200");
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 5 Comparar valor page
	 ***************************************/
	addStep("Comparar valor page", "Debe ser el mismo enviado en querie page");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key page: " + response.jsonPath().getInt("page"));
	assertEquals(response.jsonPath().getInt("page"), 1, "Se valida el key page");

	/***************************************
	 * PASO 6 Comparar valor pageSize
	 ***************************************/
	addStep("Comparar valor pageSize", "Debe ser el mismo enviado en querie pageSize");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key pageSize: " + response.jsonPath().getInt("pageSize"));
	assertEquals(response.jsonPath().getInt("pageSize"), 1, "Se valida el key pageSize");

	/***************************************
	 * PASO 7 comparar valor totalItems
	 ***************************************/
	addStep("comparar valor totalItems", "Debe ser el mismo obtenido en el paso dos");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key totalItems: " + response.jsonPath().getInt("totalItems"));
	assertEquals(response.jsonPath().getInt("totalItems"), cuenta, "Se valida el key totalItems");

	/***************************************
	 * PASO 8 comparar valor totalPages
	 ***************************************/
	addStep("comparar valor totalPages", "Debe ser el mismo que totalItems");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key totalPages: " + response.jsonPath().getInt("totalPages"));
	assertEquals(response.jsonPath().getInt("totalPages"), cuenta, "Se valida el key totalPages");

	/***************************************
	 * PASO 9 comparar valor data
	 ***************************************/
	addStep("comparar valor data", "debe ser un array de json donde cada json debe contener:");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key data: " + response.jsonPath().getList("data"));
	assertEquals(response.jsonPath().getList("data").isEmpty(), false, "Se valida el key data");

	/***************************************
	 * PASO 10 Validar el campo marketplaceId
	 ***************************************/
	addStep("Validar el campo marketplaceId", "Debe ser numerico");
	assertTrue(
		util.validatorTypeJson(response.jsonPath().get("data[0].actionAgreement"), TEXTO, "actionAgreement"));

	/***************************************
	 * PASO 11 Validar el campo actionAgreement
	 ***************************************/
	addStep("Validar el campo actionAgreement", EXPECTED);
	assertTrue(
		util.validatorTypeJson(response.jsonPath().get("data[0].actionAgreement"), TEXTO, "actionAgreement"));

	/***************************************
	 * PASO 12 Validar el campo folioAgreement
	 ***************************************/
	addStep("Validar el campo folioAgreement", EXPECTED);
	assertTrue(
		util.validatorTypeJson(response.jsonPath().get("data[0].folioAgreement"), TEXTO, "folioAgreement"));

	/***************************************
	 * PASO 13 Validar el campo statusAgreement
	 ***************************************/
	addStep("Validar el campo statusAgreement", EXPECTED);
	assertTrue(
		util.validatorTypeJson(response.jsonPath().get("data[0].statusAgreement"), TEXTO, "statusAgreement"));

	/***************************************
	 * PASO 14 Validar el campo creationDate
	 ***************************************/
	addStep("Validar el campo creationDate", EXPECTED);
	assertTrue(util.validatorTypeJson(response.jsonPath().get("data[0].creationDate"), TEXTO, "creationDate"));

	/***************************************
	 * PASO 15 Validar el campo cancellationMarketplaceId
	 ***************************************/
	addStep("Validar el campo cancellationMarketplaceId", "Debe ser numerico");
	assertTrue(util.validatorTypeJson(response.jsonPath().get("data[0].cancellationMarketplaceId"), TEXTO,
		"cancellationMarketplaceId"));

	/***************************************
	 * PASO 16 Validar el campo sendedFile
	 ***************************************/
	addStep("Validar el campo sendedFile", EXPECTED);
	assertTrue(util.validatorTypeJson(response.jsonPath().get("data[0].sendedFile"), TEXTO, "sendedFile"));

	/***************************************
	 * PASO 17 Validar el campo errors
	 ***************************************/
	addStep("Validar el campo errors", EXPECTED);
	assertTrue(util.validatorTypeJson(response.jsonPath().get("data[0].errors"), TEXTO, "errors"));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Happy path para validar la estructura del json response y la seguridad de la api";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_041_ValidarEstructuraDelEndpointAgreementFolios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

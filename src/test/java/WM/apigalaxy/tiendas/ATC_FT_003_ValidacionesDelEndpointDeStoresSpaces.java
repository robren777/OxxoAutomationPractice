package WM.apigalaxy.tiendas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import WM.apigalaxy.models.StoresSpacesResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Happy path para updatedDate, cursor y limit. Se valida formato
 * de response.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-003-Validaciones del
 * endpoint de storesSpaces
 *
 * PREREQUISITOS: Acceso a la API, Acceso a Snowflake, Información Válida en las
 * tablas de Snowflake, La API funcionando
 *
 * INSUMOS: updateDate, busca el valor más nuevo de cambios de fecha en la db de
 * Snowflake
 *
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
//@AzureTestInfo(areaPath = "I22122-Reingeniería%20de%20Promociones", iterationPath = "I22122-Reingeniería de Promociones\\6 SPRINTS MVP6 - APIGalaxy\\Sprint 2 - Q1", tags = "WM", userStoryId = 275548)
public class ATC_FT_003_ValidacionesDelEndpointDeStoresSpaces extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_003_ValidacionesDelEndpointDeStoresSpaces_test(HashMap<String, String> data) throws Exception {
	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	Connection connection = null;
	String query = "SELECT count(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA;";
	int count = 0;
	final String ESPECTED_NUMBER = "Debe ser numerico";
	final String EXPECTED_STRING = "Debe ser un string";
	/***************************************
	 * PASO 1 Hacer conexion a Snowflake
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "Se debe poder hacer conexion");
	try {
	    connection = util.getSnowflakeConnection();
	    if (connection.isValid(10))
		testCase.addCodeEvidenceCurrentStep("Conexion a SnowFlake arroja estatus OK");

	    /***************************************
	     * PASO 2 Ejecutar el siguiente querie: select * from
	     * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA;
	     ***************************************/
	    addStep("Ejecutar el siguiente querie: SELECT count(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA;",
		    "Validar la respuesta y almacenar los datos.");
	    // Se genera un statement para ejecutar las consultas
	    Statement stat = connection.createStatement();
	    // Se manda una consulta y se guarda en un resultSet
	    ResultSet result = stat.executeQuery(query);
	    if (result.next()) {
		count = result.getInt("COUNT(1)");
		testCase.addCodeEvidenceCurrentStep("El valor encontrado en el count es: " + count);
	    }
	    // Manejo de errores especifico
	} catch (SQLException e) {
	    System.out.println("No se pudo cerrar la conexión: " + e.getMessage());
	} finally {
	    // cierre de conexion
	    if (connection != null) {
		try {
		    connection.close();
		} catch (SQLException e) {
		    System.out.println("No se pudo cerrar la conexión: " + e.getMessage());
		}
	    }
	}
	/***************************************
	 * PASO 3 Realizar una nueva consulta con los siguientes datos: page = 1, limit
	 * = 1, updateDate = <Valor obtenido en datos de prueba, formato YYYY-MM-DD>
	 * https://api-dev.oxxo.io/exp-marketplace-space-leasing/api/v1/catalog/stores?page=1&limit=1&updateDate=2025-01-31
	 ***************************************/
	addStep("Realizar una nueva consulta con los siguientes datos:\r\n" + "cursor = 1,\r\n" + "limit = 1,\r\n"
		+ "updateDate = <Valor obtenido en datos de prueba, formato YYYY-MM-DD>\r\n" + "",
		"Debe poder enviarse la consulta");
	int cursor = 1;
	int limit = 1;
	String updatedDate = "1998-08-06";
	Response response = util.getStoresSpaces(cursor, limit, updatedDate);
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	testCase.addCodeEvidenceCurrentStep("Se envia el get con los valores: cursor " + cursor + " ,limit " + limit
		+ " ,updatedDate " + updatedDate);

	/***************************************
	 * PASO 4 Validar response
	 ***************************************/
	addStep("Validar response", "Debe ser un codigo 200");
	testCase.addCodeEvidenceCurrentStep("todo");
	int statusCode = response.getStatusCode();
	Assert.assertEquals(statusCode, 200, "Se valida el status code, debe ser 200");
	testCase.addCodeEvidenceCurrentStep("El valor de estatus encontrado es: " + statusCode);

	// Se mapea el response en tipo de dato
	StoresSpacesResponse storesSpacesResponse = response.as(StoresSpacesResponse.class);

	/***************************************
	 * PASO 5 Comparar valor page
	 ***************************************/
	addStep("Comparar valor limit", "Debe ser el mismo enviado en el param limit");
	int realLimit = storesSpacesResponse.getLimit();
	Assert.assertEquals(realLimit, limit, "Se valida el valor de limit");
	testCase.addCodeEvidenceCurrentStep("El valor de limit encontrado es: " + realLimit);

	/***************************************
	 * PASO 6 Comparar valor pageSize
	 ***************************************/
	addStep("Comparar valor returnedItems", "Debe ser 1 ");
	int realReturnedItems = storesSpacesResponse.getReturnedItems();
	Assert.assertEquals(realReturnedItems, 1, "Se valida el valor de returnedItems");
	testCase.addCodeEvidenceCurrentStep("El valor de returnedItems encontrado es:" + realReturnedItems);

	/***************************************
	 * PASO 7 comparar valor totalItems
	 ***************************************/
	addStep("Validar el dato hasMore ", "Debe ser true");
	Boolean realHasMore = storesSpacesResponse.isHasMore();
	Assert.assertEquals(realHasMore, true, "Se valida el valor de hasMore");
	testCase.addCodeEvidenceCurrentStep("El valor real de hasMore encontrado es: " + realHasMore);

	/***************************************
	 * PASO 8 comparar valor totalPages
	 ***************************************/
	addStep("Validar el valor nextCursor", "Debe ser 2");
	int realNextCursor = storesSpacesResponse.getNextCursor();
	Assert.assertEquals(realNextCursor, 2, "Se valida el valor de nextCursor");
	testCase.addCodeEvidenceCurrentStep("El valor real de tnextCursor encontrado es: " + realNextCursor);

	/***************************************
	 * PASO 9 comparar valor storesSpaces
	 ***************************************/
	addStep("comparar valor storesSpaces",
		"debe ser un array de json donde cada json debe contener los campos indicados");
	Assert.assertTrue(storesSpacesResponse.getStoresSpaces() instanceof List, "storesSpaces no es un array");
	testCase.addCodeEvidenceCurrentStep("Se valida que storesSpaces sea un array de JSON");

	List<Map<String, Object>> storesSpacesList = response.jsonPath().getList("storesSpaces");
	/***************************************
	 * PASO 10 Validar el campo retekId
	 ***************************************/
	for (int i = 0; i < storesSpacesList.size(); i++) {
	    Map<String, Object> storeSpace = storesSpacesList.get(i);

	    addStep("Validar el campo retekId", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("retekId") instanceof Number, "retekId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo retekId es un campo de tipo numerico");

	    addStep("Validar el campo spaceId", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("spaceId") instanceof Number, "spaceId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo spaceId es un campo de tipo numerico");

	    addStep("Validar el campo spaceName", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("spaceName") instanceof String, "spaceName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo spaceName es un campo de tipo string");

	    addStep("Validar el campo needDesc", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("needDesc") instanceof String, "needDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo needDesc es un campo de tipo string");

	    addStep("Validar el campo spaceType", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("spaceType") instanceof String, "spaceType no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo spaceType es un campo de tipo string");

	    addStep("Validar el campo chargeType", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("chargeType") instanceof String, "chargeType no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo chargeType es un campo de tipo string");

	    addStep("Validar el campo regionName", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("regionName") instanceof String, "regionName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo regionName es un campo de tipo string");

	    addStep("Validar el campo plazaName", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("plazaName") instanceof String, "plazaName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo plazaName es un campo de tipo string");

	    addStep("Validar el campo masterSegDesc", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("masterSegDesc") instanceof String, "masterSegDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo masterSegDesc es un campo de tipo string");

	    addStep("Validar el campo masterSegId", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("masterSegId") instanceof Number, "masterSegId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo masterSegId es un campo de tipo numerico");

	    addStep("Validar el campo potentialDesc", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("potentialDesc") instanceof String, "potentialDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo potentialDesc es un campo de tipo string");

	    addStep("Validar el campo alcoholPermit", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("alcoholPermit") instanceof String, "alcoholPermit no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo alcoholPermit es un campo de tipo string");

	    addStep("Validar el campo packageId", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("packageId") instanceof Number, "packageId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo packageId es un campo de tipo numerico");

	    addStep("Validar el campo packageDesc", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("packageDesc") instanceof String, "packageDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo packageDesc es un campo de tipo string");

	    addStep("Validar el campo subpackId", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("subpackId") instanceof Number, "subpackId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo subpackId es un campo de tipo numerico");

	    addStep("Validar el campo subpackDesc", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("subpackDesc") instanceof String, "subpackDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo subpackDesc es un campo de tipo string");

	    addStep("Validar el campo compositeId", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("compositeId") instanceof String, "compositeId no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo compositeId es un campo de tipo string");

	    addStep("Validar el campo slots", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("slots") instanceof Number, "slots no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo slots es un campo de tipo numerico");

	    addStep("Validar el campo orderId", ESPECTED_NUMBER);
	    Assert.assertTrue(storeSpace.get("orderId") instanceof Number, "orderId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo orderId es un campo de tipo numerico");

	    addStep("Validar el campo deltaType", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("deltaType") instanceof String, "deltaType no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo deltaType es un campo de tipo string");

	    addStep("Validar el campo createdDate", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("createdDate") instanceof String, "createdDate no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo createdDate es un campo de tipo string");

	    addStep("Validar el campo updatedDate", EXPECTED_STRING);
	    Assert.assertTrue(storeSpace.get("updatedDate") instanceof String, "updatedDate no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo updatedDate es un campo de tipo string");
	}
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_003_ValidacionesDelEndpointDeStoresSpaces";
    }

    @Override
    public String setTestDescription() {
	return "Happy path para updatedDate,page y limit\r\n" + "Se valida formato de response\r\n"
		+ "Se validan todas las paginas, 3 respuestas por pagina\r\n";
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
	return "Acceso a la API\r\n" + "Acceso a Snowflake\r\n" + "Informacion Valida en las tablas de Snowflake\r\n"
		+ "La API funcionando";

    }

    @Override
    public void beforeTest() {

    }
}

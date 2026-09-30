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

import WM.apigalaxy.models.StoresResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Validaciones del endpoint de stores en el happy path para
 * updatedDate, page y limit. Se valida el formato de response y las respuestas.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-002-Validaciones del
 * endpoint de stores
 *
 * PREREQUISITOS: updateDate, busca el valor mas nuevo de cambios de fecha en la
 * db de snowflake
 *
 * INSUMOS:
 * 
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_002_ValidacionesDelEndpointDeStores extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_002_ValidacionesDelEndpointDeStores_test(HashMap<String, String> data) throws Exception {
	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	Connection connection = null;
	String query = "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS;";
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
	     * PASO 2 Ejecutar el siguiente querie: SELECT * from
	     * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS;
	     ***************************************/
	    addStep("Ejecutar el siguiente querie: SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS;",
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
	 * PASO 3 Realizar una nueva consulta con los datos page=1, limit=1,
	 * updateDate=YYYY-MM-DD
	 ***************************************/
	addStep("Realizar una nueva consulta con los datos page=2, limit=1, updateDate=YYYY-MM-DD",
		"Debe poder enviarse la consulta");
	int page = 1;
	int limit = 1;
	String updatedDate = "1998-08-06";
	Response response = util.getStores(page, limit, updatedDate);
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getBaseUrl());
	testCase.addCodeEvidenceCurrentStep(
		"Se envia el get con los valores: page " + page + " ,limit " + limit + " ,updatedDate " + updatedDate);

	/***************************************
	 * PASO 4 Validar response
	 ***************************************/
	addStep("Validar response", "Debe tener un codigo 200");
	int statusCode = response.getStatusCode();
	Assert.assertEquals(statusCode, 200, "Se valida el status code, debe ser 200");
	testCase.addCodeEvidenceCurrentStep("El valor de estatus encontrado es: " + statusCode);

	// Se mapea el response en tipo de dato
	StoresResponse storesResponse = response.as(StoresResponse.class);

	/***************************************
	 * PASO 5 Comparar valor page
	 ***************************************/
	addStep("Comparar valor page", "Debe ser el mismo enviado en querie page");
	int realPage = storesResponse.getPage();
	Assert.assertEquals(realPage, page, "Se valida el valor de Page");
	testCase.addCodeEvidenceCurrentStep("El valor de Page encontrado es: " + realPage);

	/***************************************
	 * PASO 6 Comparar valor pageSize
	 ***************************************/
	addStep("Comparar valor pageSize", "Debe ser el mismo enviado en querie limit");
	int realPageSize = storesResponse.getPageSize();
	Assert.assertEquals(realPageSize, limit, "Se valida el valor de pageSize");
	testCase.addCodeEvidenceCurrentStep("El valor de pageSize encontrado es:" + realPageSize);

	/***************************************
	 * PASO 7 Comparar valor totalItems
	 ***************************************/
	addStep("Comparar valor totalItems", "Debe ser el mismo obtenido en el paso dos");
	int realTotalItems = storesResponse.getTotalItems();
	Assert.assertEquals(realTotalItems, count, "Se valida el valor de total items");
	testCase.addCodeEvidenceCurrentStep("El valor real de TotalItems encontrado es: " + realTotalItems);

	/***************************************
	 * PASO 8 Comparar valor totalPages
	 ***************************************/
	addStep("Comparar valor totalPages", "Debe ser el mismo que totalItems");
	int realTotalPages = storesResponse.getTotalPages();
	Assert.assertEquals(realTotalPages, count, "Se valida el valor de total items");
	testCase.addCodeEvidenceCurrentStep("El valor real de total Pages encontrado es: " + realTotalPages);

	/***************************************
	 * PASO 9 Comparar valor stores
	 ***************************************/
	addStep("Comparar valor stores", "Debe ser un array de json con los campos requeridos");
	Assert.assertTrue(storesResponse.getStores() instanceof List, "Se valida que Stores sea un array");
	testCase.addCodeEvidenceCurrentStep("El valor stores es un array");

	List<Map<String, Object>> storesList = response.jsonPath().getList("stores");

	// Recorrer cada store
	for (int i = 0; i < storesList.size(); i++) {
	    Map<String, Object> store = storesList.get(i);

	    /***************************************
	     * PASO 10 Validar el campo retekId
	     ***************************************/
	    addStep("Validar el campo retekId", ESPECTED_NUMBER);
	    Assert.assertTrue(store.get("retekId") instanceof Number, "retekId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo retekId es un campo de tipo numerico");

	    /***************************************
	     * PASO 11 Validar el campo storeCode
	     ***************************************/
	    addStep("Validar el campo storeCode", EXPECTED_STRING);
	    Assert.assertTrue(store.get("storeCode") instanceof String, "storeCode no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo storeCode es un campo de tipo string");

	    /***************************************
	     * PASO 12 Validar el campo storeName
	     ***************************************/
	    addStep("Validar el campo storeName", EXPECTED_STRING);
	    Assert.assertTrue(store.get("storeName") instanceof String, "storeName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo storeName es un campo de tipo string");

	    /***************************************
	     * PASO 13 Validar el campo regionCode
	     ***************************************/
	    addStep("Validar el campo regionCode", EXPECTED_STRING);
	    Assert.assertTrue(store.get("regionCode") instanceof String, "regionCode no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo regionCode es un campo de tipo string");

	    /***************************************
	     * PASO 14 Validar el campo regionName
	     ***************************************/
	    addStep("Validar el campo regionName", EXPECTED_STRING);
	    Assert.assertTrue(store.get("regionName") instanceof String, "regionName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo regionName es un campo de tipo string");

	    /***************************************
	     * PASO 15 Validar el campo zoneName
	     ***************************************/
	    addStep("Validar el campo zoneName", EXPECTED_STRING);
	    Assert.assertTrue(store.get("zoneName") instanceof String, "zoneName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo zoneName es un campo de tipo string");

	    /***************************************
	     * PASO 16 Validar el campo plazaCode
	     ***************************************/
	    addStep("Validar el campo plazaCode", EXPECTED_STRING);
	    Assert.assertTrue(store.get("plazaCode") instanceof String, "plazaCode no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo plazaCode es un campo de tipo string");

	    /***************************************
	     * PASO 17 Validar el campo plazaName
	     ***************************************/
	    addStep("Validar el campo plazaName", EXPECTED_STRING);
	    Assert.assertTrue(store.get("plazaName") instanceof String, "plazaName no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo plazaName es un campo de tipo string");

	    /***************************************
	     * PASO 18 Validar el campo municipalityDel
	     ***************************************/
	    addStep("Validar el campo municipalityDel", EXPECTED_STRING);
	    Assert.assertTrue(store.get("municipalityDel") instanceof String, "municipalityDel no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo municipalityDel es un campo de tipo string");

	    /***************************************
	     * PASO 19 Validar el campo ownGasStation
	     ***************************************/
	    addStep("Validar el campo ownGasStation", EXPECTED_STRING);
	    Assert.assertTrue(store.get("ownGasStation") instanceof String, "ownGasStation no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo ownGasStation es un campo de tipo String");

	    /***************************************
	     * PASO 20 Validar el campo gasStationCode
	     ***************************************/
	    addStep("Validar el campo gasStationCode", EXPECTED_STRING);
	    Assert.assertTrue(store.get("gasStationCode") instanceof String, "gasStationCode no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo gasStationCode es un campo de tipo String");

	    /***************************************
	     * PASO 21 Validar el campo masterSegId
	     ***************************************/
	    addStep("Validar el campo masterSegId", ESPECTED_NUMBER);
	    Assert.assertTrue(store.get("masterSegId") instanceof Number, "masterSegId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo masterSegId es un campo de tipo numerico");

	    /***************************************
	     * PASO 22 Validar el campo masterSegDesc
	     ***************************************/
	    addStep("Validar el campo masterSegDesc", EXPECTED_STRING);
	    Assert.assertTrue(store.get("masterSegDesc") instanceof String, "masterSegDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo masterSegDesc es un campo de tipo string");

	    /***************************************
	     * PASO 23 Validar el campo potentialDesc
	     ***************************************/
	    addStep("Validar el campo potentialDesc", EXPECTED_STRING);
	    Assert.assertTrue(store.get("potentialDesc") instanceof String, "potentialDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo potentialDesc es un campo de tipo string");

	    /***************************************
	     * PASO 24 Validar el campo storeTypeSin
	     ***************************************/
	    addStep("Validar el campo storeTypeSin", EXPECTED_STRING);
	    Assert.assertTrue(store.get("storeTypeSin") instanceof String, "storeTypeSin no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo storeTypeSin es un campo de tipo String");

	    /***************************************
	     * PASO 25 Validar el campo alcoholPermit
	     ***************************************/
	    addStep("Validar el campo alcoholPermit", EXPECTED_STRING);
	    Assert.assertTrue(store.get("alcoholPermit") instanceof String, "alcoholPermit no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo alcoholPermit es un campo de tipo string");

	    /***************************************
	     * PASO 26 Validar el campo bottlerDesc
	     ***************************************/
	    addStep("Validar el campo bottlerDesc", EXPECTED_STRING);
	    Assert.assertTrue(store.get("bottlerDesc") == null || store.get("bottlerDesc") instanceof String,
		    "bottlerDesc no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo bottlerDesc es un campo de tipo string");

	    /***************************************
	     * PASO 27 Validar el campo orderId
	     ***************************************/
	    addStep("Validar el campo orderId", ESPECTED_NUMBER);
	    Assert.assertTrue(store.get("orderId") instanceof Number, "orderId no es numerico");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo orderId es un campo de tipo numerico");

	    /***************************************
	     * PASO 28 Validar el campo deltaType
	     ***************************************/
	    addStep("Validar el campo deltaType", EXPECTED_STRING);
	    Assert.assertTrue(store.get("deltaType") instanceof String, "deltaType no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo deltaType es un campo de tipo string");

	    /***************************************
	     * PASO 29 Validar el campo createdDate
	     ***************************************/
	    addStep("Validar el campo createdDate", EXPECTED_STRING);
	    Assert.assertTrue(store.get("createdDate") instanceof String, "createdDate no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo createdDate es un campo de tipo string");

	    /***************************************
	     * PASO 30 Validar el campo updatedDate
	     ***************************************/
	    addStep("Validar el campo updatedDate", EXPECTED_STRING);
	    Assert.assertTrue(store.get("updatedDate") instanceof String, "updatedDate no es String");
	    testCase.addCodeEvidenceCurrentStep("Se valida el campo updatedDate es un campo de tipo string");
	}

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_002_ValidacionesDelEndpointDeStores";
    }

    @Override
    public String setTestDescription() {
	return "Happy path para updatedDate,page y limit\r\n" + "Se valida formato de response\r\n"
		+ "Se validan todas las paginas, 3 respuestas por pagina\r\n" + "";
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

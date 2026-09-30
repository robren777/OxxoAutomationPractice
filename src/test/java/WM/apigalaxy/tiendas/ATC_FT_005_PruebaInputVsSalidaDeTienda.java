package WM.apigalaxy.tiendas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import WM.apigalaxy.models.Store;
import WM.apigalaxy.models.StoresResponse;
import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Validación de la entrada y salida de datos de tienda mediante la
 * comparación de registros en Snowflake y la API.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-005-Prueba Input vs
 * Salida de tienda
 *
 * PREREQUISITOS: - Acceso a la API - Acceso a Snowflake - Información válida en
 * las tablas de Snowflake - La API funcionando
 *
 * INSUMOS: - updateDate, busca el valor más nuevo de cambios de fecha en la db
 * de Snowflake
 * 
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_005_PruebaInputVsSalidaDeTienda extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_005_PruebaInputVsSalidaDeTienda_test(HashMap<String, String> data) throws Exception {

	/*****************************
	 * Variables Data Provider
	 **************************************/

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, this.testCase);
	Connection connection = null;
	String queryCount = "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS where DATE(LAST_UPDATED_DATE) = '2026-04-22';";
	String queryMuestra = "SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS "
		+ "WHERE DATE(LAST_UPDATED_DATE) = '2026-04-22' AND MOD(ABS(HASH(ORDER_ID)), 100) = 1;";
	String queryLimites = "WITH Ordenados AS (SELECT *, ROW_NUMBER() OVER (ORDER BY ORDER_ID ASC) as posicion, COUNT(*) OVER () as total_filas"
		+ " FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS WHERE DATE(LAST_UPDATED_DATE) = '2026-04-22')"
		+ " SELECT * FROM Ordenados WHERE posicion = 1 OR posicion = total_filas OR posicion = FLOOR(total_filas / 2);";

	int count = 0;
	List<Map<String, Object>> responseMuestra = new ArrayList<>();
	List<Map<String, Object>> responseLimites = new ArrayList<>();
	int page = 1;
	int pageSize = 1;
	String updatedDate = "2026-04-22";
	
	/***************************************
	 * PASO 1 Hacer conexion a Snowflake
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "Se debe poder hacer conexion");
	try {
	    connection = util.getSnowflakeConnection();
	    if (connection.isValid(10))
		testCase.addCodeEvidenceCurrentStep("Conexion a SnowFlake arroja estatus OK");

	    /***************************************
	     * PASO 2 Mandar querie que obtenga el total de registros que tuvieron un cambio
	     * y deben ser enviados en la api
	     ***************************************/
	    addStep("Mandar querie que obtenga el total de registros que tuvieron un cambio y deben ser enviados en la api",
		    "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_179_RDP_MARKETPLACE_TIENDAS;");
	    // Se genera un statement para ejecutar las consultas
	    Statement stat = connection.createStatement();
	    // Se manda una consulta y se guarda en un resultSet
	    ResultSet result = stat.executeQuery(queryCount);
	    if (result.next()) {
		count = result.getInt("COUNT(1)");
		testCase.addCodeEvidenceCurrentStep("El valor encontrado en el count es: " + count);
	    }

	    /***************************************
	     * PASO 3 Mandar el querie para obtener una muestra del 0.1% del total.
	     ***************************************/
	    addStep("Mandar el querie para obtener una muestra del 0.1% del total.",
		    "SELECT * FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA WHERE DATE(LAST_UPDATED_DATE) = '<fecha>' AND MOD(ABS(HASH(ORDER_ID)), 1000) = 1;");
	    ResultSet resultMuestra = stat.executeQuery(queryMuestra);
	    responseMuestra = util.getListFromSnowResponse(resultMuestra);
	    System.out.println("La muestra es de : " + responseMuestra.size());
	    testCase.addCodeEvidenceCurrentStep("El valor total de la muestra es: " + responseMuestra.size());

	    /***************************************
	     * PASO 4 Mandar el querie para obtener los tress registros de limites.
	     ***************************************/
	    addStep("Mandar el querie para obtener los tress registros de limites.",
		    "WITH Ordenados AS (SELECT *, ROW_NUMBER() OVER (ORDER BY ORDER_ID ASC) as posicion, COUNT(*) OVER () as total_filas FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA WHERE DATE(LAST_UPDATED_DATE) = '<fecha>') SELECT * FROM Ordenados WHERE posicion = 1 OR posicion = total_filas OR posicion = FLOOR(total_filas / 2);");
	    ResultSet resultLimites = stat.executeQuery(queryLimites);
	    responseLimites = util.getListFromSnowResponse(resultLimites);
	    testCase.addCodeEvidenceCurrentStep("El valor total de los limites es: " + responseLimites.size());

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
	 * PASO 5 comparar la respuesta del querie del count contra el metadata de la
	 * api, totalItems
	 ***************************************/
	addStep("comparar la respuesta del querie del count contra el metadata de la api, totalItems",
		"Deben ser los mismos que el paso 5");
	Response response = util.getStores(page, pageSize, updatedDate);
	testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
	testCase.addCodeEvidenceCurrentStep("Se envia el get con los valores: page " + page + " ,pageSize " + pageSize
		+ " ,updatedDate " + updatedDate);
	int statusCode = response.getStatusCode();
	Assert.assertEquals(statusCode, 200, "Se valida el status code, debe ser 200");
	testCase.addCodeEvidenceCurrentStep("El valor de estatus encontrado es: " + statusCode);

	// Se mapea el response en tipo de dato
	StoresResponse storesResponse = response.as(StoresResponse.class);

	int realTotalItems = storesResponse.getTotalItems();
	Assert.assertEquals(realTotalItems, count, "Se valida el valor de total items");
	testCase.addCodeEvidenceCurrentStep("El valor real de TotalItems encontrado es: " + realTotalItems);

	/***************************************
	 * PASO 6 Comparar la respuesta del querie de la muestra, realizando una llamada
	 * con los querie params donde el limit =1 y page = <ORDER_NO>
	 ***************************************/
	addStep("Comparar la respuesta del querie de la muestra, realizando una llamada con los querie params donde el limit =1 y page = <ORDER_NO>",
		"Deben ser los mismos que el 6");
	int orderId;
	Store actual;
	for (Map<String, Object> fila : responseMuestra) {
	    orderId = Integer.parseInt(fila.get("ORDER_ID").toString());
	    testCase.addBoldTextEvidenceCurrentStep(
		    "Enviando page: " + orderId + " ,pageSize: " + pageSize + " ,updatedDate: " + updatedDate);
	    response = util.getStores(orderId, pageSize, updatedDate);
	    System.out.println("Procesando Order ID: " + orderId);
	    testCase.addBoldTextEvidenceCurrentStep("Validando  Order ID: " + orderId);
	    storesResponse = response.as(StoresResponse.class);
	    actual = storesResponse.getStores().get(0);
	    util.validateStore(actual, fila, orderId);
	}

	/***************************************
	 * PASO 7 Comparar la respuesta del quierie de los limites, realizando la
	 * llamada con los querie params donde el limit =1 y page = <ORDER_NO>
	 ***************************************/
	addStep("Comparar la respuesta del quierie de los limites, realizando la llamada con los querie params donde el limit =1 y page = <ORDER_NO>",
		"Deben ser los mismos");
	testCase.addCodeEvidenceCurrentStep("Se empieza validacion de limites");
	for (Map<String, Object> fila : responseLimites) {
	    orderId = Integer.parseInt(fila.get("ORDER_ID").toString());
	    testCase.addBoldTextEvidenceCurrentStep(
		    "Enviando page: " + orderId + " ,pageSize: " + pageSize + " ,updatedDate: " + updatedDate);
	    response = util.getStores(orderId, pageSize, updatedDate);
	    System.out.println("Procesando Order ID: " + orderId);
	    testCase.addBoldTextEvidenceCurrentStep("Validando  Order ID: " + orderId);
	    storesResponse = response.as(StoresResponse.class);
	    actual = storesResponse.getStores().get(0);
	    util.validateStore(actual, fila, orderId);
	}

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_005_PruebaInputVsSalidaDeTienda";
    }

    @Override
    public String setTestDescription() {
	return "Validación de la entrada y salida de datos de tienda mediante la comparación de registros en Snowflake y la API.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API, Acceso a Snowflake, Información válida en las tablas de Snowflake, La API funcionando";
    }

    @Override
    public void beforeTest() {

    }
}

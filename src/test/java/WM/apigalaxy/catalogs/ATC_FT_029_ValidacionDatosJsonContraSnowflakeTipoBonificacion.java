package WM.apigalaxy.catalogs;

import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import WM.apigalaxy.models.ApiRequest;
import WM.util.ApiGalaxyUtils;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida que el JSON contenga los valores adecuados basados en
 * la información de Snowflake.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-029-Validacion de datos
 * json contra snow de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_TIPO_BONIFICACION
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Acceso a Snowflake -Ejecutar el
 * pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Tipo_Bonificacion -Guardar el
 * JSON de la actividad Post_Registros (Seguir el manual)
 *
 * INSUMOS: Querie: select FLEX_VALUE, DESCRIPTION from
 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_XXFC_IC_TIPO_BONIFICACION; Endpoint:
 * {{base_url}}/rest/all/V1/oxxo/ebs-tipo-bonificacion/create
 *
 * @author Guillermo Garcia
 * @date 19/05/2026
 */
public class ATC_FT_029_ValidacionDatosJsonContraSnowflakeTipoBonificacion extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_029_ValidacionDatosJsonContraSnowflakeTipoBonificacion_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Variables Data Provider
	 **************************************/

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	String json = data.get("urlJson");
	String currentDir = System.getProperty("user.dir");
	String urlJson = currentDir + json;
	String querie = data.get("querie");
	String endpoint = data.get("endpoint");
	String primaryKey = data.get("primaryKey").toUpperCase();
	String bodyKey = data.get("bodyKey");
	Connection connection = null;
	List<Map<String, Object>> response = new ArrayList<>();
	int i = 1;
	/***************************************
	 * PASO 1
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "La conexion debe realizarse con exito");
	try {
	    connection = util.getSnowflakeConnection();
	    if (connection.isValid(10))
		testCase.addCodeEvidenceCurrentStep("Conexion a SnowFlake arroja estatus OK");

	    /***************************************
	     * PASO 2
	     ***************************************/
	    addStep("Ejecutar el querie y almacenar los datos", "El querie debe poder ser ejecutado con exito");
	    // Se genera un statement para ejecutar las consultas
	    Statement stat = connection.createStatement();
	    // Se manda una consulta y se guarda en un resultSet
	    ResultSet result = stat.executeQuery(querie);
	    response = util.getListFromSnowResponse(result);
	} catch (SQLException e) {
	    System.out.println("Error connecting to Snowflake");
	    e.printStackTrace();
	} finally {
	    // cierre de conexion
	    if (connection != null) {
		try {
		    connection.close();
		} catch (SQLException e) {
		    System.out.println("Error closing connection");
		    e.printStackTrace();
		}
	    }
	}

	/***************************************
	 * PASO 3
	 ***************************************/
	addStep("Validar el Json creado", "Debe poder ser abierto.");
	ObjectMapper mapper = new ObjectMapper();
	ApiRequest request = mapper.readValue(Paths.get(urlJson).toFile(), ApiRequest.class);
	testCase.addCodeEvidenceCurrentStep("Se mapea el json encontrado. ");

	/***************************************
	 * PASO 4
	 ***************************************/
	addStep("Validar el valor del key method", "Debe ser POST");
	assertTrue(request.getMethod().equals("POST"), "Se valida  el valor del key method");
	testCase.addCodeEvidenceCurrentStep("Se encuentran  : " + request.getMethod());

	/***************************************
	 * PASO 5
	 ***************************************/
	addStep("Validar el valor del key url", "Debe ser el endpoint " + endpoint);
	assertTrue(request.getUrl().equals(endpoint), "Se valida el valor del key url");
	testCase.addCodeEvidenceCurrentStep("Se encuentran  : " + request.getUrl());

	/***************************************
	 * PASO 6
	 ***************************************/
	addStep("Extraer el valor del key body", "Debe poder ser extraido");
	assertTrue(!request.getBody().isNull(), "Se valida el valor del key body");
	testCase.addCodeEvidenceCurrentStep("Se logro extraer el body");

	/***************************************
	 * PASO 7
	 ***************************************/
	addStep("Extraer el array de json", "Debe poder ser extraido");
	assertTrue(!request.getBody().get(bodyKey).isNull(), "Se valida el valor del key body");
	testCase.addCodeEvidenceCurrentStep("Se logro extraer " + bodyKey);

	/***************************************
	 * PASO 8
	 ***************************************/
	addStep("Comparar el tamano del array y del response de snow", "Deben ser los mismo");
	assertTrue(request.getBody().get(bodyKey).size() == response.size(),
		"Se valida el tamano del array y del response de snow");
	testCase.addCodeEvidenceCurrentStep("Se encuentran en el json : " + request.getBody().get(bodyKey).size()
		+ " y en el response : " + response.size());

	/***************************************
	 * PASO 9
	 ***************************************/
	addStep("Comparar los datos del array y del response", "Deben ser los mismos");
	List<Map<String, Object>> jsonList = mapper.convertValue(request.getBody().get(bodyKey),
		new TypeReference<List<Map<String, Object>>>() {
		});
	List<Map<String, Object>> normalizedJsonList = util.normalizeJsonListKeys(jsonList);
	Map<String, Map<String, Object>> jsonIndex = util.jsonIndexer(normalizedJsonList, primaryKey);

	for (Map<String, Object> dbRow : response) {
	    String pkValue = dbRow.get(primaryKey).toString();
	    Map<String, Object> jsonRow = jsonIndex.get(pkValue);
	    if (jsonRow == null) {
		assertTrue(false, "No se encunetra el registro" + pkValue);
		throw new RuntimeException("Registro con PK " + pkValue + " no encontrado en JSON");
	    }
	    testCase.addBoldTextEvidenceCurrentStep("Se valida el resitro: " + i++);
	    for (String column : dbRow.keySet()) {
		Object dbValue = dbRow.get(column);
		Object jsonValue = jsonRow.get(column);
		String dbStr = dbValue != null ? dbValue.toString() : "";
		String jsonStr = jsonValue != null ? jsonValue.toString() : "";
		testCase.addCodeEvidenceCurrentStep("Se valida columna: " + column);
		testCase.addCodeEvidenceCurrentStep("|| Valor encontrado en snowflake: " + dbStr);
		testCase.addCodeEvidenceCurrentStep("|| Valor encontrado en json:      " + jsonStr);
		if (!dbStr.equals(jsonStr)) {
		    assertTrue(false, "Diferencia en " + pkValue);
		    throw new RuntimeException("Diferencia encontrada -> PK: " + pkValue + " Columna: " + column
			    + " DB: " + dbStr + " JSON: " + jsonStr);
		}
	    }
	}

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a Azure DataFactory, Acceso a Snowflake, Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Tipo_Bonificacion, Guardar el JSON de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public String setTestDescription() {
	return "Se valida que el JSON contenga los valores adecuados basados en la información de Snowflake.";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_029_ValidacionDatosJsonContraSnowflakeTipoBonificacion";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

}
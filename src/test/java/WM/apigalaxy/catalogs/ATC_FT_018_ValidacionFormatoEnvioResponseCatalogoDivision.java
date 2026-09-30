package WM.apigalaxy.catalogs;

import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WM.apigalaxy.models.ApiRequest;
import WM.util.ApiGalaxyUtils;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida que el json que se envia tenga el formato correcto, a
 * su vez se valida que el pipeline ejecute sin errores y el json de respuesta
 * sea el adecuado
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-018-Validacion de
 * formato de envio y response de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_XXCO_DIVISION
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Ejecutar el pipeline
 * 260_PL_DEALSESPACIORENTA_SNFLK2REST_Division -Guardar el json de la actividad
 * Post_Registros
 *
 * INSUMOS:
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_018_ValidacionFormatoEnvioResponseCatalogoDivision extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_018_ValidacionFormatoEnvioResponseCatalogoDivision_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Variables Data Provider
	 **************************************/

	/*****************************
	 * Instancias
	 **************************************************/
	String json = data.get("urlJson");
	String currentDir = System.getProperty("user.dir");
	String urlJson = currentDir + json;
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	final String DIVISION = "division";
	final String DIVISIONS = "divisions";
	final String DIV_NAME = "div_name";
	final String ID_SUPERGRUPO = "id_supergrupo";

	/***************************************
	 * PASO 1 Validar el Json creado
	 ***************************************/
	addStep("Validar el Json creado", "Debe poder ser abierto.");
	ObjectMapper mapper = new ObjectMapper();
	ApiRequest request = mapper.readValue(Paths.get(urlJson).toFile(), ApiRequest.class);
	testCase.addCodeEvidenceCurrentStep("Se mapea el json encontrado. ");

	/***************************************
	 * PASO 2 Validar los keys del json
	 ***************************************/
	addStep("Validar los keys del json",
		"Deben ser 4:\r\n" + "-method\r\n" + "-headers\r\n" + "-url\r\n" + "-body");
	assertTrue(!request.getHeaders().isEmpty(), "Se valida que tenga Headers");
	assertTrue(((!request.getMethod().isBlank()) && (!request.getMethod().isEmpty())),
		"Se valida que tenga Method");
	assertTrue(((!request.getUrl().isBlank()) && (!request.getUrl().isEmpty())), "Se valida que tenga URL");
	assertTrue(((!request.getBody().isNull()) && (!request.getBody().isEmpty())), "Se valida que tenga Body");
	testCase.addCodeEvidenceCurrentStep("Se valida que tenga los 4 headers y ninguno es null");

	/***************************************
	 * PASO 3 Validar el valor body
	 ***************************************/
	addStep("Validar el valor body", "Debe ser solo 1 key:\r\n" + "-divisions");
	assertTrue(request.getBody().size() == 1, "Se valida que sea 1 el tamano del body");
	testCase.addCodeEvidenceCurrentStep("El tamano del body es: " + request.getBody().size());
	assertTrue(request.getBody().has(DIVISIONS), "Se valida que tenga el valor de divisions");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de divisions");

	/***************************************
	 * PASO 4 Validar el campo collectionMethod del body
	 ***************************************/
	addStep("Validar el campo division del body", "Debe tener un array de json");
	assertTrue(request.getBody().get(DIVISIONS).isArray(), "Se valida que el tipo de dato es array");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de divisions");

	/***************************************
	 * PASO 5 Validar el primer json que se encuentre
	 ***************************************/
	addStep("Validar el primer json que se encuentre", "Deben ser solo 3 keys");
	JsonNode jason = request.getBody().get(DIVISIONS);
	JsonNode first = util.findFirstWithoutNulls(jason);
	assertTrue(first.size() == 3, "Se valida el tamano del array");
	testCase.addCodeEvidenceCurrentStep("Se encuentran un total de : " + first.size() + " keys");

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo division", "Debe ser un Number");
	if (first.has(DIVISION)) {
	    assertTrue(first.get(DIVISION).isNumber(), "Se valida que division es Number");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es division es :" + first.get(DIVISION).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de divisions");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra divisions");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo div_name", "Debe ser un  String");
	if (first.has(DIV_NAME)) {
	    assertTrue(first.get(DIV_NAME).isTextual(), "Se valida que div_name es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es div_name es :" + first.get(DIV_NAME).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de div_name");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra div_name");
	}

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo id_supergrupo", "Debe ser un Number");
	if (first.has(ID_SUPERGRUPO)) {
	    assertTrue(first.get(ID_SUPERGRUPO).isNumber(), "Se valida que id_supergrupo es Number");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es id_supergrupo es :" + first.get(ID_SUPERGRUPO).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de id_supergrupo");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra id_supergrupo");
	}

    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "-Acceso a Azure DataFactory\n-Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Division\n-Guardar el json de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public String setTestDescription() {
	return "Se valida que el json que se envia tenga el formato correcto, a su vez se valida que el pipeline ejecute sin errores y el json de respuesta sea el adecuado";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_018_ValidacionFormatoEnvioResponseCatalogoDivision";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
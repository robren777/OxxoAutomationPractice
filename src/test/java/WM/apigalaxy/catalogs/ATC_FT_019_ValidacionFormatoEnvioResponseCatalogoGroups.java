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
 * DESCRIPCIÓN: Se valida que el JSON enviado tenga el formato correcto,
 * asegurando que el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Groups ejecute
 * sin errores y que el JSON de respuesta sea el adecuado.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-019-Validacion de
 * formato de envio y response de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_XXCO_GROUPS
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Ejecutar el pipeline
 * 260_PL_DEALSESPACIORENTA_SNFLK2REST_Groups -Guardar el JSON de la actividad
 * Post_Registros
 *
 * INSUMOS:
 * 
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_019_ValidacionFormatoEnvioResponseCatalogoGroups extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_019_ValidacionFormatoEnvioResponseCatalogoGroups_test(HashMap<String, String> data)
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
	final String GROUPS = "groups";
	final String GROUP_NO = "group_no";
	final String DIVISION = "division";
	final String GROUP_NAME = "group_name";
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
	addStep("Validar el valor body", "Debe ser solo 1 key:\r\n" + "-groups");
	assertTrue(request.getBody().size() == 1, "Se valida que sea 1 el tamano del body");
	testCase.addCodeEvidenceCurrentStep("El tamano del body es: " + request.getBody().size());
	assertTrue(request.getBody().has(GROUPS), "Se valida que tenga el valor de group");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de groups");

	/***************************************
	 * PASO 4 Validar el campo collectionMethod del body
	 ***************************************/
	addStep("Validar el campo group del body", "Debe tener un array de json");
	assertTrue(request.getBody().get(GROUPS).isArray(), "Se valida que el tipo de dato es array");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de groups");

	/***************************************
	 * PASO 5 Validar el primer json que se encuentre
	 ***************************************/
	addStep("Validar el primer json que se encuentre", "Deben ser solo 3 keys");
	JsonNode jason = request.getBody().get(GROUPS);
	JsonNode first = util.findFirstWithoutNulls(jason);
	assertTrue(first.size() == 3, "Se valida el tamano del array");
	testCase.addCodeEvidenceCurrentStep("Se encuentran un total de : " + first.size() + " keys");

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo group_no", "Debe ser un Number");
	if (first.has(GROUP_NO)) {
	    assertTrue(first.get(GROUP_NO).isNumber(), "Se valida que group_no es Number");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es group_no es :" + first.get(GROUP_NO).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de group_no");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra group_no");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo group_name", "Debe ser un  String");
	if (first.has(GROUP_NAME)) {
	    assertTrue(first.get(GROUP_NAME).isTextual(), "Se valida que group_name es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es group_name es :" + first.get(GROUP_NAME).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de group_name");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra group_name");
	}

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo division", "Debe ser un Number");
	if (first.has(DIVISION)) {
	    assertTrue(first.get(DIVISION).isNumber(), "Se valida que division es Number");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es division es :" + first.get(DIVISION).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de division");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra division");
	}

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	// Mismo que PREREQUISITOS en documentación
	return "-Acceso a Azure DataFactory\n-Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Groups\n-Guardar el JSON de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida que el JSON enviado tenga el formato correcto, asegurando que el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Groups ejecute sin errores y que el JSON de respuesta sea el adecuado.";
    }

    @Override
    public String setTestFullName() {
	// Mismo nombre de la clase
	return "ATC_FT_019_ValidacionFormatoEnvioResponseCatalogoGroups";
    }

    @Override
    public String setTestInstanceID() {
	// Dejar null
	return null;
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

}
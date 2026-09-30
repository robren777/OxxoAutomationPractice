package WM.apigalaxy.catalogs;

import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Map;

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
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-012-Validacion de
 * formato de envio y response de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_FORMAS_COBRO
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Ejecutar el pipeline
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_Formas_Cobro -Guardar el json de la
 * actividad Post_Registros
 *
 * INSUMOS:
 * 
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_012_ValidacionFormatoEnvioResponseCatalogoFormasCobro extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_012_ValidacionFormatoEnvioResponseCatalogoFormasCobro_test(Map<String, String> data)
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
	String COLLECTION_METHOD = "collectionMethod";
	String FLEX_VALUE = "flex_value";
	String DESCRIPTION = "description";

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
	addStep("Validar el valor body", "Debe ser solo 1 key:\r\n" + "-collectionMethod");
	assertTrue(request.getBody().size() == 1, "Se valida que sea 1 el tamano del body");
	testCase.addCodeEvidenceCurrentStep("El tamano del body es: " + request.getBody().size());
	assertTrue(request.getBody().has(COLLECTION_METHOD), "Se valida que tenga el valor de collectionMethod");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de collectionMethod");

	/***************************************
	 * PASO 4 Validar el campo collectionMethod del body
	 ***************************************/
	addStep("Validar el campo collectionMethod del body", "Debe tener un array de json");
	assertTrue(request.getBody().get(COLLECTION_METHOD).isArray(), "Se valida que el tipo de dato es array");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de collectionMethod");

	/***************************************
	 * PASO 5 Validar el primer json que se encuentre
	 ***************************************/
	addStep("Validar el primer json que se encuentre", "Deben ser solo dos keys");
	JsonNode jason = request.getBody().get(COLLECTION_METHOD);
	JsonNode first = util.findFirstWithoutNulls(jason);
	assertTrue(first.size() == 2, "Se valida el tamano del array");
	testCase.addCodeEvidenceCurrentStep("Se encuentran un total de : " + first.size() + " keys");

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo flex_value", "Debe ser un String");
	if (first.has(FLEX_VALUE)) {
	    assertTrue(first.get(FLEX_VALUE).isTextual(), "Se valida que flex_value es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es flex_value es :" + first.get(FLEX_VALUE).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de flex_value");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra flex_value");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo description", "Debe ser un  String");
	if (first.has(DESCRIPTION)) {
	    assertTrue(first.get(DESCRIPTION).isTextual(), "Se valida que description es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es description es :" + first.get(DESCRIPTION).getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de description");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra description");
	}

    }

    @Override
    public String setTestFullName() {
	// Mismo nombre de la clase
	return "ATC_FT_012_ValidacionFormatoEnvioResponseCatalogoFormasCobro";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida que el json que se envia tenga el formato correcto, a su vez se valida que el pipeline ejecute sin errores y el json de respuesta sea el adecuado";
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
	// Mismo que PREREQUISITOS en documentación
	return "Acceso a Azure DataFactory, Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2TREST_Formas_Cobro, Guardar el json de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public void beforeTest() {

    }
}
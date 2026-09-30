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
 * DESCRIPCIÓN: Se valida que el JSON enviado tenga el formato correcto, el
 * pipeline ejecute sin errores, y el JSON de respuesta sea adecuado.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-021-Validacion de
 * formato de envio y response de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_NEGOCIOS_PLAZAS_V
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Ejecutar el pipeline
 * 260_PL_DEALSESPACIORENTA_SNFLK2REST_Negocios_Plazas -Guardar el JSON de la
 * actividad Post_Registros
 *
 * INSUMOS:
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_021_ValidacionFormatoEnvioResponseCatalogoNegociosPlazasV extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_021_ValidacionFormatoEnvioResponseCatalogoNegociosPlazasV_test(HashMap<String, String> data)
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
	addStep("Validar el valor body", "Debe ser solo 1 key:\r\n" + "-bussinesPlaces");
	assertTrue(request.getBody().size() == 1, "Se valida que sea 1 el tamano del body");
	testCase.addCodeEvidenceCurrentStep("El tamano del body es: " + request.getBody().size());
	assertTrue(request.getBody().has("bussinesPlaces"), "Se valida que tenga el valor de bussinesPlaces");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de bussinesPlaces");

	/***************************************
	 * PASO 4 Validar el campo collectionMethod del body
	 ***************************************/
	addStep("Validar el campo bussinesPlaces del body", "Debe tener un array de json");
	assertTrue(request.getBody().get("bussinesPlaces").isArray(), "Se valida que el tipo de dato es array");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de bussinesPlaces");

	/***************************************
	 * PASO 5 Validar el primer json que se encuentre
	 ***************************************/
	addStep("Validar el primer json que se encuentre", "Deben ser solo 4 keys");
	JsonNode jason = request.getBody().get("bussinesPlaces");
	JsonNode first = util.findFirstWithoutNulls(jason);
	assertTrue(first.size() == 4, "Se valida el tamano del array");
	testCase.addCodeEvidenceCurrentStep("Se encuentran un total de : " + first.size() + " keys");

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo cr_plaza", "Debe ser un  String");
	if (first.has("cr_plaza")) {
	    assertTrue(first.get("cr_plaza").isTextual(), "Se valida que cr_plaza es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es cr_plaza es :" + first.get("cr_plaza").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de cr_plaza");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra cr_plaza");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo descripcion", "Debe ser un  String");
	if (first.has("descripcion")) {
	    assertTrue(first.get("descripcion").isTextual(), "Se valida que descripcion es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es descripcion es :" + first.get("descripcion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de descripcion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra descripcion");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo cr_distrito", "Debe ser un  String");
	if (first.has("cr_distrito")) {
	    assertTrue(first.get("cr_distrito").isTextual(), "Se valida que cr_distrito es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es cr_distrito es :" + first.get("cr_distrito").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de cr_distrito");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra cr_distrito");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo shuttle", "Debe ser un  String");
	if (first.has("shuttle")) {
	    assertTrue(first.get("shuttle").isTextual(), "Se valida que shuttle es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es shuttle es :" + first.get("shuttle").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de shuttle");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra shuttle");
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
	return "-Acceso a Azure DataFactory\n"
		+ "-Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Negocios_Plazas\n"
		+ "-Guardar el JSON de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public String setTestDescription() {
	return "Se valida que el JSON enviado tenga el formato correcto, el pipeline ejecute sin errores, y el JSON de respuesta sea adecuado.";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_021_ValidacionFormatoEnvioResponseCatalogoNegociosPlazasV";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
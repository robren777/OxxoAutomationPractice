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
 * DESCRIPCIÓN: Se valida que el json enviado tenga el formato correcto, se
 * verifica la ejecución del pipeline sin errores y se valida el json de
 * respuesta.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-024-Validacion de
 * formato de envio y response de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_TIPOS_APORTACION
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Ejecutar el pipeline
 * 260_PL_DEALSESPACIORENTA_SNFLK2REST_Tipos_Aportacion -Guardar el json de la
 * actividad Post_Registros
 *
 * INSUMOS:
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_024_ValidacionFormatoEnvioResponseCatalogoTiposAportacion extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_024_ValidacionFormatoEnvioResponseCatalogoTiposAportacion_test(HashMap<String, String> data)
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
	addStep("Validar el valor body", "Debe ser solo 1 key:\r\n" + "-typesOfAportation");
	assertTrue(request.getBody().size() == 1, "Se valida que sea 1 el tamano del body");
	testCase.addCodeEvidenceCurrentStep("El tamano del body es: " + request.getBody().size());
	assertTrue(request.getBody().has("typesOfAportation"), "Se valida que tenga el valor de typesOfAportation");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de typesOfAportation");

	/***************************************
	 * PASO 4 Validar el campo collectionMethod del body
	 ***************************************/
	addStep("Validar el campo typesOfAportation del body", "Debe tener un array de json");
	assertTrue(request.getBody().get("typesOfAportation").isArray(), "Se valida que el tipo de dato es array");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de typesOfAportation");

	/***************************************
	 * PASO 5 Validar el primer json que se encuentre
	 ***************************************/
	addStep("Validar el primer json que se encuentre", "Deben ser solo 4 keys");
	JsonNode jason = request.getBody().get("typesOfAportation");
	JsonNode first = util.findFirstWithoutNulls(jason);
	assertTrue(first.size() == 4, "Se valida el tamano del array");
	testCase.addCodeEvidenceCurrentStep("Se encuentran un total de : " + first.size() + " keys");

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo id_tipo_aportacion", "Debe ser un Number");
	if (first.has("id_tipo_aportacion")) {
	    assertTrue(first.get("id_tipo_aportacion").isNumber(), "Se valida que id_tipo_aportacion es Number");
	    testCase.addCodeEvidenceCurrentStep("El tipo de dato que es id_tipo_aportacion es :"
		    + first.get("id_tipo_aportacion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de id_tipo_aportacion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra id_tipo_aportacion");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo tipo_aportacion", "Debe ser un  String");
	if (first.has("tipo_aportacion")) {
	    assertTrue(first.get("tipo_aportacion").isTextual(), "Se valida que tipo_aportacion es string");
	    testCase.addCodeEvidenceCurrentStep("El tipo de dato que es tipo_aportacion es :"
		    + first.get("tipo_aportacion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de tipo_aportacion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra tipo_aportacion");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo aportacion", "Debe ser un  String");
	if (first.has("aportacion")) {
	    assertTrue(first.get("aportacion").isTextual(), "Se valida que aportacion es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es aportacion es :" + first.get("aportacion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de aportacion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra aportacion");
	}

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo id_tipo_bonificacion", "Debe ser un Number");
	if (first.has("id_tipo_bonificacion")) {
	    assertTrue(first.get("id_tipo_bonificacion").isNumber(), "Se valida que id_tipo_bonificacion es Number");
	    testCase.addCodeEvidenceCurrentStep("El tipo de dato que es id_tipo_bonificacion es :"
		    + first.get("id_tipo_bonificacion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de id_tipo_bonificacion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra id_tipo_bonificacion");
	}

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a Azure DataFactory, Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Tipos_Aportacion, Guardar el json de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public String setTestDescription() {
	return "Se valida que el json enviado tenga el formato correcto, se verifica la ejecución del pipeline sin errores y se valida el json de respuesta.";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_024_ValidacionFormatoEnvioResponseCatalogoTiposAportacion";
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
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
 * DESCRIPCIÓN: Se valida que el JSON enviado tenga el formato correcto, se
 * verifica la ejecución del pipeline sin errores y que el JSON de respuesta sea
 * adecuado.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-023-Validacion de
 * formato de envio y response de catalogo OUT_
 * 260_PL_DEALSESPACIORENTA_SNFLK2TREST_TIPOS_BONIFICACION
 *
 * PREREQUISITOS: -Acceso a Azure DataFactory -Ejecutar el pipeline
 * 260_PL_DEALSESPACIORENTA_SNFLK2REST_Tipos_Bonificacion -Guardar el JSON de la
 * actividad Post_Registros
 *
 * INSUMOS:
 * @author Guillermo Garcia
 * @date 18/05/2026
 */
public class ATC_FT_023_ValidacionFormatoEnvioResponseTiposBonificacion extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_023_ValidacionFormatoEnvioResponseTiposBonificacion_test(HashMap<String, String> data)
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
	addStep("Validar el valor body", "Debe ser solo 1 key:\r\n" + "-bonusTypesBonification");
	assertTrue(request.getBody().size() == 1, "Se valida que sea 1 el tamano del body");
	testCase.addCodeEvidenceCurrentStep("El tamano del body es: " + request.getBody().size());
	assertTrue(request.getBody().has("bonusTypesBonification"),
		"Se valida que tenga el valor de bonusTypesBonification");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de bonusTypesBonification");

	/***************************************
	 * PASO 4 Validar el campo collectionMethod del body
	 ***************************************/
	addStep("Validar el campo bonusTypesBonification del body", "Debe tener un array de json");
	assertTrue(request.getBody().get("bonusTypesBonification").isArray(), "Se valida que el tipo de dato es array");
	testCase.addCodeEvidenceCurrentStep("Se encuentra el valor de bonusTypesBonification");

	/***************************************
	 * PASO 5 Validar el primer json que se encuentre
	 ***************************************/
	addStep("Validar el primer json que se encuentre", "Deben ser solo 6 keys");
	JsonNode jason = request.getBody().get("bonusTypesBonification");
	JsonNode first = util.findFirstWithoutNulls(jason);
	assertTrue(first.size() == 6, "Se valida el tamano del array");
	testCase.addCodeEvidenceCurrentStep("Se encuentran un total de : " + first.size() + " keys");

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
	addStep("validar el campo concepto_factura", "Debe ser un  String");
	if (first.has("concepto_factura")) {
	    assertTrue(first.get("concepto_factura").isTextual(), "Se valida que concepto_factura es string");
	    testCase.addCodeEvidenceCurrentStep("El tipo de dato que es concepto_factura es :"
		    + first.get("concepto_factura").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de concepto_factura");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra concepto_factura");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo concepto", "Debe ser un  String");
	if (first.has("concepto")) {
	    assertTrue(first.get("concepto").isTextual(), "Se valida que concepto es string");
	    testCase.addCodeEvidenceCurrentStep(
		    "El tipo de dato que es concepto es :" + first.get("concepto").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de concepto");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra concepto");
	}

	/***************************************
	 * PASO 6 validar el campo flex_value
	 ***************************************/
	addStep("validar el campo id_tipo_negociacion", "Debe ser un Number");
	if (first.has("id_tipo_negociacion")) {
	    assertTrue(first.get("id_tipo_negociacion").isNumber(), "Se valida que id_tipo_negociacion es Number");
	    testCase.addCodeEvidenceCurrentStep("El tipo de dato que es id_tipo_negociacion es :"
		    + first.get("id_tipo_negociacion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de id_tipo_negociacion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra id_tipo_negociacion");
	}

	/***************************************
	 * PASO 7 Vvalidar el campo description
	 ***************************************/
	addStep("validar el campo tipo_bonificacion", "Debe ser un  String");
	if (first.has("tipo_bonificacion")) {
	    assertTrue(first.get("tipo_bonificacion").isTextual(), "Se valida que tipo_bonificacion es string");
	    testCase.addCodeEvidenceCurrentStep("El tipo de dato que es tipo_bonificacion es :"
		    + first.get("tipo_bonificacion").getClass().getSimpleName());
	} else {
	    assertTrue(false, "No se encuentra la key de tipo_bonificacion");
	    testCase.addCodeEvidenceCurrentStep("No se encuentra tipo_bonificacion");
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
	// Mismo que PREREQUISITOS en documentación
	return "-Acceso a Azure DataFactory\n-Ejecutar el pipeline 260_PL_DEALSESPACIORENTA_SNFLK2REST_Tipos_Bonificacion\n-Guardar el JSON de la actividad Post_Registros Seguir el documento(Prerequisitos prueba catalogos.pdf)";
    }

    @Override
    public String setTestDescription() {
	// Mismo que DESCRIPCIÓN en documentación
	return "Se valida que el JSON enviado tenga el formato correcto, se verifica la ejecución del pipeline sin errores y que el JSON de respuesta sea adecuado.";
    }

    @Override
    public String setTestFullName() {
	// Mismo nombre de la clase
	return "ATC_FT_023_ValidacionFormatoEnvioResponseTiposBonificacion";
    }

    @Override
    public String setTestInstanceID() {
	// Dejar null
	return null;
    }
}
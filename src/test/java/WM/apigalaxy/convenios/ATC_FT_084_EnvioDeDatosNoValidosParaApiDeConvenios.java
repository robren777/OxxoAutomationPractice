package WM.apigalaxy.convenios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se envia un set de datos generados para probar cada campo en
 * base a su tipo de dato, en esta prueba se envian los escenarios no validos
 * para cada tipo de dato, el endpoint valida por registro por lo que no se ve
 * la necesidad de hacer una prueba aislada por cada campo, en esta validacion
 * se incluye la falta de envio de keys en algunos registros y el envio de
 * valores con null, se valida el response y se valida que no se genere ni un
 * registro en la db
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-084-Envio de datos no
 * validos para api de convenios
 *
 * PREREQUISITOS: - Acceso a la API y la API funcionando - JSON con POST ID MKT
 * 704 - Acceso a Snowflake - Acceso a DB Convenios
 *
 * @author Guillermo garcia
 *
 * @date 29/05/2026
 */
public class ATC_FT_084_EnvioDeDatosNoValidosParaApiDeConvenios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_084_EnvioDeDatosNoValidosParaApiDeConvenios_test(HashMap<String, String> data) throws Exception {
	String EXPECTED_RESULT = "Debe dar un status 400 y el response debe ser: code:X400000, message:The client has issued a malformed or illegal request.";
	String archivoValidaciones = "inputNoValido.csv";
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "704";
	final String COUNT = "COUNT(1)";
	/***************************************
	 * PASO 1 Hacer conexion a snowflake
	 ***************************************/
	addStep("Hacer conexion a snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion");
	util.executeQuerySnowflake(util.querieConvenioDelete(testId));

	/***************************************
	 * PASO 2 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
	 * ID_MARKETPLACE = '704';
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE = '704';",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioCount(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 704 son: " + responseQuerieOne.get(0).get(COUNT));
	assertEquals(responseQuerieOne.get(0).get(COUNT), 0);

	/***************************************
	 * PASO 3 Obtener el token de la api
	 ***************************************/
	addStep("Obtener el token de la api", "Debe poder ser obtenido y utiizado en la siguiente paso");
	testCase.addCodeEvidenceCurrentStep("Se obtiene y sera utilizado");
	// Este paso se usa en el util del response

	List<String[]> rows = util.readCsvModifications(archivoValidaciones, "agreements");

	for (String[] row : rows) {
	    String field = row[0];
	    String value = row[1];
	    Object parsedValue = util.parseValue(value);

	    addStep("Enviar un response valido modificando el campo " + field + " con el valor " + parsedValue,
		    EXPECTED_RESULT);
	    util.validateAgreementlError(field, parsedValue, "400");
	}

	/***************************************
	 * PASO 126 Ejecutar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
	 * ID_MARKETPLACE = '704';
	 ***************************************/
	addStep("Ejecutar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE ID_MARKETPLACE = '704';",
		"Debe regresar 0");
	responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioCount(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El total de regsitros con el id 704 son: " + responseQuerieOne.get(0).get(COUNT));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API y la API funcionando\\nJSON con POST ID MKT 704\\nAcceso a Snowflake\\nAcceso a DB Convenios";
    }

    @Override
    public String setTestDescription() {
	return "Se envia un set de datos generados para probar cada campo en base a su tipo de dato, en esta prueba se envian los escenarios no validos para cada tipo de dato, el endpoint valida por registro por lo que no se ve la necesidad de hacer una prueba aislada por cada campo, en esta validacion se incluye la falta de envio de keys en algunos registros y el envio de valores con null, se valida el response y se valida que no se genere ni un registro en la db";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_084_EnvioDeDatosNoValidosParaApiDeConvenios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

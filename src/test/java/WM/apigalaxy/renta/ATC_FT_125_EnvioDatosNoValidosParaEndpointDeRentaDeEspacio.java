package WM.apigalaxy.renta;

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
 * registro en la db NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC-FT-125_EnvioDatosNoValidosParaEndpointDeRentaDeEspacio
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7002
 * 
 * @author Guillermo Garcia
 * @date 19/05/2026
 */
public class ATC_FT_125_EnvioDatosNoValidosParaEndpointDeRentaDeEspacio extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_125_EnvioDatosNoValidosParaEndpointDeRentaDeEspacio_test(HashMap<String, String> data)
	    throws Exception {
	final String VALOR_MESSAGE =" con el valor ";
	final String ENVIAR_MESSAGE = "Enviar un response valido modificando el campo ";
	final String EXPECTED_SEGURIDAD = "Debe dar un status 400 y el response debe ser code:X400000 message:The client has issued a malformed or illegal request";
	final String EXPECTED_COMMON = "The operation was denied, if the problem persists contact your administrator or try again later.";
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "7002";
	String archivoValidaciones = "input.csv";

	/***************************************
	 * PASO 1 Hacer conexion a snowflake
	 ***************************************/
	addStep("Hacer conexion a snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion");
	// precondiciones
	util.executeQuerySnowflake(util.querieRentDelete(testId));
	/***************************************
	 * PASO 2 En snowflake enviar el siguiente querie: SELECT COUNT(1) from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7002;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7002;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 7002 son: " + responseQuerieOne.size());

	/***************************************
	 * PASO 3 Obtener el token de la api
	 ***************************************/
	addStep("Obtener el token de la api", "Debe poder ser obtenido y utiizado en la siguiente paso");
	testCase.addCodeEvidenceCurrentStep("Se genera el token con exito");
	// Este paso ocurre dentro de las utils en el metodo que hace la llamada a la
	// api con el proposito que no expire el token por llamada

	/***************************************
	 * PASO 4 Enviar un response valido modificando el campo rentFolio con el valor
	 * null
	 ***************************************/
	// Al ser demasiados pasos se decide automatizar esto con un csv
	List<String[]> rows = util.readCsvModifications(archivoValidaciones);

	for (String[] row : rows) {
	    String field = row[0];
	    String value = row[1];
	    Object parsedValue = util.parseValue(value);

	    addStep(ENVIAR_MESSAGE + field + VALOR_MESSAGE + parsedValue,
		    EXPECTED_COMMON);
	    System.out.println(
		    ENVIAR_MESSAGE + field + VALOR_MESSAGE + parsedValue);
	    util.validatePromotionalRentalError(field, parsedValue, "400");
	}

	// validaciones que no dan 400
	String field = "items[0].oxxoCategories";
	String value = "<script>alert('xss')</script>";
	//
	addStep(ENVIAR_MESSAGE + field + VALOR_MESSAGE + value,
		EXPECTED_SEGURIDAD);
	System.out.println(ENVIAR_MESSAGE + field + VALOR_MESSAGE + value);
	util.validatePromotionalRentalError(field, value, "seguridad");

	field = "items[0].subPackageId";
	value = "'OR 1=1 --";
	addStep(ENVIAR_MESSAGE + field + VALOR_MESSAGE + value,
		EXPECTED_SEGURIDAD);
	System.out.println(ENVIAR_MESSAGE + field + VALOR_MESSAGE + value);
	util.validatePromotionalRentalError(field, value, "seguridad");

	/***************************************
	 * PASO 103 En snowflake enviar el siguiente querie: SELECT COUNT(1) from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7002;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7002;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieData = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente numero de registros: " + responseQuerieData.size());
	assertEquals(responseQuerieData.size(), 0, "Se valida el tamano del response de snowflake");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\nLa API funcionando\nAcceso a snowflake\nUn set de datos que no esté en la DB";
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
	return "ATC_FT_125_EnvioDatosNoValidosParaEndpointDeRentaDeEspacio";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
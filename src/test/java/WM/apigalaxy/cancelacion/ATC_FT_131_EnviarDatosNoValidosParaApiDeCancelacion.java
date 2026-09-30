package WM.apigalaxy.cancelacion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC-FT-131_EnviarDatosNoValidosParaApiDeCancelacion
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS: - post valido 7000
 * 
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_131_EnviarDatosNoValidosParaApiDeCancelacion extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_131_EnviarDatosNoValidosParaApiDeCancelacion_test(HashMap<String, String> data)
	    throws Exception {
	final String EXPECTED_COMMON = "The operation was denied, if the problem persists contact your administrator or try again later.";
	final String RENT_FOLIO_STATUS = "RENT_FOLIO_STATUS";
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "7007";
	String archivoValidaciones = "inputCancel.csv";

	/***************************************
	 * PASO 1 Hacer conexion a snowflake
	 ***************************************/
	addStep("Hacer conexion a snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion");

	/***************************************
	 * PASO 2 En snowflake enviar el siguiente querie: SELECT RENT_FOLIO_STATUS from
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7008;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7008;",
		"Debe regresar ACTIVO");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El estatus del id 7007 es: " + responseQuerieOne.get(0).get(RENT_FOLIO_STATUS));
	assertEquals(responseQuerieOne.get(0).get(RENT_FOLIO_STATUS), "Activo", "Se valida estatus del rent folio");

	/***************************************
	 * PASO 3 Obtener el token de la api
	 ***************************************/
	addStep("Obtener el token de la api", "Debe poder ser obtenido y utiizado en la siguiente paso");
	testCase.addCodeEvidenceCurrentStep("Se genera el token con exito");
	// Este paso ocurre dentro de las utils en el metodo que hace la llamada a la
	// api con el proposito que no expire el token por llamada

	List<String[]> rows = util.readCsvModifications(archivoValidaciones);

	for (String[] row : rows) {
	    String field = row[0];
	    String value = row[1];
	    Object parsedValue = util.parseValue(value);

	    addStep("Enviar un response valido modificando el campo " + field + " con el valor " + parsedValue,
		    EXPECTED_COMMON);
	    assertTrue(util.validatePromotionalRentalCancelacionError(field, parsedValue, "400"));
	}
	/***************************************
	 * PASO 53 En snowflake enviar el siguiente querie: SELECT RENT_FOLIO_STATUS
	 * from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE
	 * RENT_FOLIO = 7008;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT RENT_FOLIO_STATUS from DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_260_PROMOTIONAL_RENTAL WHERE RENT_FOLIO = 7008;",
		"Debe regresar ACTIVO");
	responseQuerieOne = util.executeQuerySnowflake(util.querieRent(testId));
	testCase.addCodeEvidenceCurrentStep(
		"El estatus del id 7007 es: " + responseQuerieOne.get(0).get(RENT_FOLIO_STATUS));
	assertEquals(responseQuerieOne.get(0).get(RENT_FOLIO_STATUS), "Activo", "Se valida estatus del rent folio");

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
	return "ATC_FT_131_EnviarDatosNoValidosParaApiDeCancelacion";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
package WM.apigalaxy.folios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se busca en la DB un registro que cuente como cancelacion de
 * convenio sin reemplazo y se busca en la API por medio de busqueda de id, se
 * comparan los valores de la db en contra de la api.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-046-Validacion
 * cancelacion de convenio sin reemplazo
 *
 * PREREQUISITOS: - Acceso a la API - Acceso a Snowflake - Informacion Valida en
 * las tablas de Snowflake - La API funcionando
 *
 * INSUMOS: - Querie: SELECT * FROM
 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS WHERE
 * ID_MARKETPLACE IN ( SELECT ID_MARKETPLACE FROM
 * DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS GROUP BY
 * ID_MARKETPLACE HAVING COUNT(*) > 1 ) AND ACCION_CONVENIO = 'C';
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_046_ValidacionCancelacionDeConvenioSinReemplazo extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_046_ValidacionCancelacionDeConvenioSinReemplazo_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String query = "SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS;";

	/***************************************
	 * PASO 1 Hacer conexion a Snowflake
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion.");
	// Ya se refactorizo un mtodo para hacer conexion directa

	/*************************************
	 * PASO 2 Ejecutar querie para encontrar un id que respete el escenario
	 ***************************************/
	addStep("Ejecutar querie para encontrar un id que respete el escenario", "Almacenar la respuesta");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieConvenioCount(query));
	testCase.addCodeEvidenceCurrentStep("El total de registros son: " + responseQuerieOne.get(0).get("COUNT(1)"));

	/***************************************
	 * PASO 3 Realizar la siguiente consulta
	 * <urlBase>/agreement/folios?marketplaceId=<id obtenido en el querie>
	 ***************************************/
	addStep("Realizar la siguiente consulta <urlBase>/agreement/folios?marketplaceId=<id obtenido en el querie>",
		"Almacenar la respuesta");
	Response response = util.getAgreementFolios(0, 0, 0, "1998-01-01", "True");
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 200, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Comparar ambas respuestas
	 ***************************************/
	addStep("Comparar ambas respuestas", "Deben contener los mismos valores");
	assertTrue(util.validateAgreementAgainstSnowflake(response, responseQuerieOne.get(0)));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\\nAcceso a Snowflake\\nInformacion Valida en las tablas de Snowflake\\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Se busca en la DB un registro que cuente como cancelacion de convenio sin reemplazo y se busca en la API por medio de busqueda de id, se comparan los valores de la db en contra de la api.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_046_ValidacionCancelacionDeConvenioSinReemplazo";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}

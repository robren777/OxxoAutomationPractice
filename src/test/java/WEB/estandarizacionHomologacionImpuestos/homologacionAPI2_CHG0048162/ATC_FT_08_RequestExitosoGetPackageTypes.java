package WEB.estandarizacionHomologacionImpuestos.homologacionAPI2_CHG0048162;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

/**
* DESCRIPCIÓN: Validar que el endpoint getPackageTypes responda con 
* el catálogo de tipos de envase.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* - No Aplica
* 
* PREREQUISITOS:
* - API creada
* - Acceso a la base de datos FCPFACQA
* 
* INSUMOS:
* - No Aplica
* 
* @author Jorge Hernandez Sanchez
* @date 19/Agosto/2024
*/

public class ATC_FT_08_RequestExitosoGetPackageTypes extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_08_RequestExitosoGetPackageTypes_test(HashMap<String, String> data) throws Exception{
        utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
                                                             GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

        String uri = String.format(data.get("uri"));

        RestAssured.baseURI = uri;
        ApiMethodsUtil api = new ApiMethodsUtil(uri);

        //Paso 1
        addStep("Realizar request GET al endpoint getPackageTypes");
        testCase.addCodeEvidenceCurrentStep("Request: " + uri);
        System.out.println("Request: " + uri);
        Response jsonRequest = api.getRequestMethod(uri);

        String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
        String statusCode = String.valueOf(jsonRequest.getStatusCode());

        testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
        System.out.println("Respuesta JSON: \n" + jsonResponse);
        testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
        System.out.println("Codigo de respuesta: " + statusCode);

        assertEquals(statusCode, "200", "El código de respuesta no es 200");

        //Validacion de formato de respuesta
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                assertTrue(json.has("code"), "El formato de respuesta no contiene el campo: code");
                assertTrue(json.has("message"), "El formato de respuesta no contiene el campo: message");
                assertTrue(json.has("packageType"), "El formato de respuesta no contiene el campo: packageType");
                assertTrue(json.has("description"), "El formato de respuesta no contiene el campo: description");
            } catch (Exception e) {
                throw new AssertionError("Error al obtener los datos de la respuesta");
            }
        }

        //Paso 2
        addStep("Validar que los datos obtenidos coincidan con la BD");

        String selectPackageTypes = "select ID_CAT_TIPO_ENVASE, TIPO_ENVASE, DESCRIPCION, ACTIVE, IEPS from XXSAT.XXSAT_CAT_TIPO_ENVASE";

        try {
            utils.sql.SQLResult sqlResult = dbFCPFACQA.executeQuery(selectPackageTypes);
            testCase.addQueryEvidenceCurrentStep(sqlResult);
            for (int i = 0; i < sqlResult.getRowCount(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String packageTypeDB = sqlResult.getData(i, "TIPO_ENVASE");
                String descriptionDB = sqlResult.getData(i, "DESCRIPCION");
                assertEquals(json.get("packageType"), packageTypeDB, "El valor de la tabla no es el mismo que el obtenido en la API");
                assertEquals(json.get("description"), descriptionDB, "El valor de la tabla no es el mismo que el obtenido en la API");
            }
        } catch (Exception e) {
            throw new AssertionError("Error al consultar la BD FCPFACQA");
        }

        dbFCPFACQA.close();
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_08_RequestFallidoGetSatCodeShcp_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que el endpoint getPackageTypes responda con el catálogo de tipos de envase.";
    }

    @Override
    public String setTestDesigner() {
        return "Angel Del Rio";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public String setPrerequisites() {
        return "1. API creada 2. Acceso a la base de datos FCPFACQA.";
    }

    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
    }
}
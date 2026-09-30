package WEB.estandarizacionHomologacionImpuestos.homologacionAPI2_CHG0048162;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

/**
* DESCRIPCIÓN: Validar que al enviar un request con parámetros fuera de rango 
* el endpoint getIeps responda con el mensaje de error esperado.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* - No Aplica
* 
* PREREQUISITOS:
* - API creada
* 
* INSUMOS:
* - No Aplica
* 
* @author Isaac Emmanuel García García
* @date 19/Agosto/2024
*/

public class ATC_FT_05_RequestFallidoGetIeps extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_05_RequestFallidoGetIeps_test(HashMap<String, String> data) throws Exception {

        String uri = String.format(data.get("uri"));
        String value = data.get("value");
        String catalog = data.get("catalog");

        RestAssured.baseURI = uri;
        ApiMethodsUtil api = new ApiMethodsUtil(uri);

        // Paso 1
        addStep("Realizar request GET al endpoint getIeps con datos no válidos");
        testCase.addCodeEvidenceCurrentStep("Request: " + uri + value + "/" + catalog);
        System.out.println("Request: " + uri + value + "/" + catalog);
        Response jsonRequest = api.getRequestMethod(uri + value + "/" + catalog);

        String response = jsonRequest.getBody().asString();
        String statusCode = String.valueOf(jsonRequest.getStatusCode());

        testCase.addCodeEvidenceCurrentStep("Respuesta: \n" + response);
        System.out.println("Response: \n" + response);
        testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
        System.out.println("StatusCode: " + statusCode);

        // Validar que se obtiene el código 200
        assertEquals(statusCode, "200", "El código de respuesta no es 200");

        // Validar el mensaje de error
        try {
            // Verificar que la respuesta contiene el mensaje esperado
            assertEquals(response.trim(), "IEPS not found", "El mensaje de error no es el esperado");
        } catch (Exception e) {
            throw new AssertionError("Error al obtener los datos de la respuesta: " + e.getMessage());
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_05_RequestFallidoGetIeps_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al enviar un request con parámetros fuera de rango el endpoint getIeps responda con el mensaje de error esperado.";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac Emmanuel García García";
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
        // Implement any setup needed before test execution
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
    }
}
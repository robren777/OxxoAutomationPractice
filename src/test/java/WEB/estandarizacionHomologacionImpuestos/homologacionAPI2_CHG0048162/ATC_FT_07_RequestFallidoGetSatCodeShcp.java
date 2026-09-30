package WEB.estandarizacionHomologacionImpuestos.homologacionAPI2_CHG0048162;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

/**
* DESCRIPCIÓN: Validar que al enviar un request con un código SAT inválido 
* el endpoint getSatCodeShcp responda con el mensaje de error esperado.
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
* @author Jorge Hernandez Sanchez
* @date 19/Agosto/2024
*/

public class ATC_FT_07_RequestFallidoGetSatCodeShcp extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_07_RequestFallidoGetSatCodeShcp_test(HashMap<String, String> data) throws Exception {
        String uri = String.format(data.get("uri"));
        String sc = data.get("sc");

        RestAssured.baseURI = uri;
        ApiMethodsUtil api = new ApiMethodsUtil(uri);

        //Paso 1
        addStep("Realizar request GET al endpoint getSatCodeShcps");
        testCase.addCodeEvidenceCurrentStep("Request: " + uri + sc);
        System.out.println("Request: " + uri + sc);
        Response jsonRequest = api.getRequestMethod(uri + sc);

        String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
        String statusCode = String.valueOf(jsonRequest.getStatusCode());

        testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
        System.out.println("JsonResponse: \n" + jsonResponse);
        testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
        System.out.println("StatusCode: " + statusCode);

        assertEquals(statusCode, "200", "El código de respuesta no es 200");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        try {
            String message = jsonNode.get("message").asText();
            testCase.addCodeEvidenceCurrentStep("message: " + message);
            assertEquals(message, "SAT Code not found", "No coincide el Mensaje de error");
            System.out.println("Message: " + message);

        } catch (Exception e) {
            throw new AssertionError("Error al obtener los datos de la respuesta");
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_07_RequestFallidoGetSatCodeShcp_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al enviar un request con un código SAT inválido el endpoint getSatCodeShcp responda con el mensaje de error esperado.";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez Sanchez";
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
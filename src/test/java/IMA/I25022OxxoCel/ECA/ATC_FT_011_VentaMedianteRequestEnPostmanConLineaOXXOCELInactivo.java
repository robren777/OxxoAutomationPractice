package IMA.I25022OxxoCel.ECA;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import IMA.OM_API.OxxoCelECA;

/**
 * DESCRIPCION: Validar que al enviar un member_id valido pero con línea oxxocel inactivo, en el request desde Postman, la petición se procese correctamente y el sistema responda únicamente con un status 200 OK.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_011-Venta mediante request en Postman con linea OXXOCEL inactivo
 *
 * PREREQUISITOS: - 1.Endpoint: https://challenger-testing.mioxxo-dev.io/api/v1/campaign/mini-campaigns/collects/active
					2.Contar con un MemberId con línea OXXOCEL inactivo
 * INSUMOS: - N/A
 *
 * @author Hugo Andres Palacios Reina
 * @date 04/marzo/2026
 */

public class ATC_FT_011_VentaMedianteRequestEnPostmanConLineaOXXOCELInactivo extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_011_VentaMedianteRequestEnPostmanConLineaOXXOCELInactivo_test(HashMap<String, String> data) throws Exception {

        testCase.setProject_Name("I25022 - OXXOCEL");

        String httpCode = data.get("httpCode");
        int codeEsperado = Integer.parseInt(httpCode);

        //columnas del excel
        String bodyRequest = data.get("jSon");
        String urlEndpoint = data.get("urlEndpoint");
        String validaHeaders = data.get("validaH");

        addStep("Validar que se muestre el resultado del JSON con el estado success y que el campo data se presente vacío::\r\n"
        		+ "{\r\n"
        		+ "    \"status\": \"success\",\r\n"
        		+ "    \"data\": {}\r\n"
        		+ "}\r\n"
        		+ " y que el código de estado de la petición indique " + codeEsperado);

        OxxoCelECA eca = new OxxoCelECA(testCase);

        Map<String, String> headers =
                eca.obtenerHeaders(validaHeaders);

        String response =
                eca.realizarPeticionHTTP(
                        urlEndpoint,
                        codeEsperado,
                        bodyRequest,
                        headers);

        JSONObject json = new JSONObject(response);

        testCase.addTextEvidenceCurrentStep(
                json.toString(4));
        
    }

    @Override
    public void beforeTest() {}

    @Override
    public String setPrerequisites() {
        return "Contar con un MemberId con línea OXXOCEL inactivo: WISJAHI";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al enviar un member_id valido pero con línea oxxocel inactivo, en el request desde Postman, la petición se procese correctamente y el sistema responda únicamente con un status 200 OK";
    }

    @Override
    public String setTestDesigner() {
        return "Hugo Andrés Palacios Reina";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_011_VentaMedianteRequestEnPostmanConLineaOXXOCELInactivo";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
package IMA.I25022OxxoCel.ECA;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import IMA.OM_API.OxxoCelECA;

/**
 * DESCRIPCION: Validar que al enviar una venta que contenga productos que participen en dos o más campañas, en el request desde Postman, la petición se procese correctamente y el sistema responda con un status 200 OK
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_058-Realizar una venta mediante request en Postman con articulos participantes para dos o más promociones activas diferentes
 *
 * PREREQUISITOS: - 1.Endpoint: https://challenger-testing.mioxxo-dev.io/api/v1/campaign/mini-campaigns/collects/active
					2.SKU para campaña 1
					3.SKU para campaña 2
 * INSUMOS: - N/A
 *
 * @author Hugo Andres Palacios Reina
 * @date 09/marzo/2026
 */

public class ATC_FT_058_RealizarUnaVentaMedianteRequestEnPostmanConArticulosParticipantesParaDosOMasPromocionesActivasDiferentes extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_058_RealizarUnaVentaMedianteRequestEnPostmanConArticulosParticipantesParaDosOMasPromocionesActivasDiferentes_test(HashMap<String, String> data) throws Exception {

        testCase.setProject_Name("I25022 - OXXOCEL");

        String httpCode = data.get("httpCode");
        int codeEsperado = Integer.parseInt(httpCode);

        //columnas del excel
        String bodyRequest = data.get("jSon");
        String urlEndpoint = data.get("urlEndpoint");
        String validaHeaders = data.get("validaH");

        addStep("Validar que se muestre el resultado del JSON con el estado \"success\", un resumen del ticket en data de las promociones aplicadas correctamente, y que el código de estado de la petición indique: " + codeEsperado);
        
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
        return "1.Contar con un MemberId válido: Z5UE1S5\r\n"
        		+ "2.SKUs no excluidos: 7500810011126:$32, 7502294221800:$16\r\n"
        		+ "3.SKU excluido: 7501045300085:$16\r\n"
        		+ "4.Monto minimo \"50.00\"";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al enviar una venta que contenga productos que participen en dos o más campañas, en el request desde Postman, la petición se procese correctamente y el sistema responda con un status 200 OK";
    }

    @Override
    public String setTestDesigner() {
        return "Hugo Andrés Palacios Reina";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_058_RealizarUnaVentaMedianteRequestEnPostmanConArticulosParticipantesParaDosOMasPromocionesActivasDiferentes";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
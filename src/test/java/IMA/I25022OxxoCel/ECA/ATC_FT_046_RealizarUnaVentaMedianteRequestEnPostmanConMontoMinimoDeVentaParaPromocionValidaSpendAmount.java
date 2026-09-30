package IMA.I25022OxxoCel.ECA;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import IMA.OM_API.OxxoCelECA;

/**
 * DESCRIPCION: Validar que al enviar una venta con SKU's que sumen un monto minimo de venta para una campaña Spend Amount, en el request desde Postman, la petición se procese correctamente y el sistema responda con un status 200 OK
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_046-Realizar una venta mediante request en Postman con monto minimo de venta para promocion válida "Spend Amount"
 *
 * PREREQUISITOS: - 1.Endpoint: https://challenger-testing.mioxxo-dev.io/api/v1/campaign/mini-campaigns/collects/active
					2.SKU no excluidos
					3.Monto minimo "0.00"
 * INSUMOS: - N/A
 *
 * @author Hugo Andres Palacios Reina
 * @date 09/marzo/2026
 */

public class ATC_FT_046_RealizarUnaVentaMedianteRequestEnPostmanConMontoMinimoDeVentaParaPromocionValidaSpendAmount extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_046_RealizarUnaVentaMedianteRequestEnPostmanConMontoMinimoDeVentaParaPromocionValidaSpendAmount_test(HashMap<String, String> data) throws Exception {

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
        return "1.Contar con un MemberId válido: ABZENDP\r\n"
        		+ "2.SKU: 7500810011126, 7502294221800\r\n"
        		+ "3.SKU no excluidos\r\n"
        		+ "4.Monto minimo \"50.00\"";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al enviar una venta con SKU's que sumen un monto minimo de venta para una campaña Spend Amount, en el request desde Postman, la petición se procese correctamente y el sistema responda con un status 200 OK";
    }

    @Override
    public String setTestDesigner() {
        return "Hugo Andrés Palacios Reina";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_046_RealizarUnaVentaMedianteRequestEnPostmanConMontoMinimoDeVentaParaPromocionValidaSpendAmount";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
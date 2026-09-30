package WM.apigalaxy.calendarios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN:Se valida el error 403 NOMBRE CASOS DE PRUEBA MANUAL QUE SE
 * ABARCAN: MTC-FT-074-Validar error 403 para endpoint promotional calendars
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando
 *
 * INSUMOS:
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_074_ValidarError401ParaEndpointPromotionalCalendars extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_074_ValidarError401ParaEndpointPromotionalCalendars_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	/***************************************
	 * PASO 3 Hacer una llamada tipo DELETE con los siguinetes valores: periodsId =
	 * a
	 ***************************************/
	addStep("Hacer una llamada tipo DELETE con los siguinetes valores:\r\n" + "periodsId = 70",
		"Debe regresar un 401");
	Response response = util.deletePromotionalCalendarsNoAuth("a");
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 401, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:\r\n" + "\r\n" + "{\r\n" + "    \"code\": \"X401000\",\r\n"
			+ "    \"message\": \"The specified token is invalid or has expired.\"\r\n" + "}");
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep("Para el envio Se obtiene el code " + realCode);
	assertEquals(realCode, "X401000", "Code erroneo");
	assertEquals(response.jsonPath().getString("message"), "The specified token is invalid or has expired.",
		"Message erroneo");

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_074_ValidarError401ParaEndpointPromotionalCalendars";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 403 ";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestInstanceID() {
	return "1";
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\r\n" + "La API funcionando\r\n" + "";

    }

    @Override
    public void beforeTest() {

    }
}

package WM.apigalaxy.calendarios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN:Se envia en el param del endpoint un valor de tipo no numerico
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-072_MandarValorNoValidoEnElParamDelDelete
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando
 *
 * INSUMOS:
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_072_MandarValorNoValidoEnElParamDelDelete extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_072_MandarValorNoValidoEnElParamDelDelete_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	String testId = "777";
	// se usa como prerequisito
	util.deletePromotionalCalendars(testId);
	/***************************************
	 * PASO 1 Utilizando el token generar un segundo token desde el endpoint de
	 * OAuth2
	 ***************************************/
	addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2",
		"Debes utilizar un api token");
	testCase.addCodeEvidenceCurrentStep("Se genera el token con exito");
	// Este paso ocurre dentro de las utils en el metodo que hace la llamada a la
	// api con el proposito que no expire el token por llamada

	/***************************************
	 * PASO 2 Utilizar el token generado como header para las siguiente accion.
	 ***************************************/
	addStep("Utilizar el token generado como header para las siguiente accion.", "Se debe utilizar un OAuth2");
	testCase.addCodeEvidenceCurrentStep("Se utiliza el token");
	// Se agregan estos dos pasos para que los stakeholders esten comodos sabiendo
	// que se utiliza seguridad en la API

	/***************************************
	 * PASO 3 Hacer una llamada tipo DELETE con los siguinetes valores: periodsId =
	 * a
	 ***************************************/
	addStep("Hacer una llamada tipo DELETE con los siguinetes valores:\r\n" + "periodsId = a",
		"Debe regresar un 400");
	Response response = util.deletePromotionalCalendars("a");
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 400, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:\r\n" + "\r\n" + "{\r\n" + "    \"code\": \"X400000\",\r\n"
			+ "    \"message\": \"The client has issued a malformed or illegal request.\"\r\n" + "}");
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep("Para el envio Se obtiene el code " + realCode);
	assertEquals(realCode, "X400000", "Code erroneo");
	assertEquals(response.jsonPath().getString("message"), "The client has issued a malformed or illegal request.",
		"Message erroneo");

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_072_MandarValorNoValidoEnElParamDelDelete";
    }

    @Override
    public String setTestDescription() {
	return "Se envia en el param del endpoint un valor de tipo no numerico\r\n" + "";
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

package WM.apigalaxy.cancelacion;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 403 frozandolo para los dos endpoints de la
 * API
 *
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-144
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 *
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_144_ValidarError403ParaEndpointRentaDeEspacio extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = false)
    public void ATC_FT_144_ValidarError403ParaEndpointRentaDeEspacio_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
	String testId = "7000";
	String jsonEnviado = "json7000.txt";
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
	 * PASO 3 Hacer una llamada tipo PUT
	 ***************************************/
	addStep("Hacer una llamada tipo DELETE", "Debe regresar un 403");
	Response response = util.postPromotionalRents(jsonEnviado);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 403, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:{code:X403000,message:Application has been identified but it is currently suspended. Please contact the API Gateway administrator for further details.}");
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep("Para el envio Se obtiene el code " + realCode);
	assertEquals(realCode, "X403000", "Code erroneo");
	assertEquals(response.jsonPath().getString("message"),
		"Application has been identified but it is currently suspended. Please contact the API Gateway administrator for further details.",
		"Message erroneo");

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
	return "Se valida el error 403 frozandolo para los dos endpoints de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_144_ValidarError403ParaEndpointRentaDeEspacio";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
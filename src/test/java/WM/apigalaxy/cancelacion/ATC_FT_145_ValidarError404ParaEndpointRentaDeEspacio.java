package WM.apigalaxy.cancelacion;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 404 frozandolo para los dos endpoints de la
 * API
 *
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-145
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando - Acceso a snowflake -
 * Un set de datos que no esté en la DB
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia
 * @date 19/05/2026
 */
public class ATC_FT_145_ValidarError404ParaEndpointRentaDeEspacio extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_145_ValidarError404ParaEndpointRentaDeEspacio_test(HashMap<String, String> data)
	    throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String jsonEnviadoCancel = "json7019Cancel.txt";
	// se usa como prerequisito

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
	 * PASO 3 Hacer una llamada tipo GET con los siguinetes valores:
	 * https://api-dev.oxxo.io/exp-ima-space-leasing/api/v1/promotional-rents/ss
	 ***************************************/
	addStep("Hacer una llamada tipo POST con los siguinetes valores:https://api-stg.oxxo.io/exp-ima-space-leasing/api/v1/promotional-rents/ss",
		"Debe regresar un 404");
	Response response = util.putPromotionalRentsNotFound(jsonEnviadoCancel);
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 404, "Se valida el estatus code");

	/***************************************
	 * PASO 4 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta",
		"El response debe ser:{code:X404000,message:Resource * not found.}");
	testCase.addCodeEvidenceCurrentStep(
		"Se encuentra el siguiente valor en el key message: " + response.jsonPath().getString("message"));
	testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	assertEquals(response.jsonPath().getString("code"), "X404000", "Validacion del codigo");
	assertEquals(response.jsonPath().getString("message"), "[ Resource /promotional-rents/holaCrayola not found.]",
		"Validacion del message");
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
	return "Se valida el error 404 frozandolo para los dos endpoints de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_145_ValidarError404ParaEndpointRentaDeEspacio";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
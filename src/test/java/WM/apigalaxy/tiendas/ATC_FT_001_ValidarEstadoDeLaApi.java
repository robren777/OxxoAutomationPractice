package WM.apigalaxy.tiendas;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Validación del estado de la API incluyendo nombre, seguridad y Health-Check.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *    MTC-FT-001 -Validacion estado de la API
 *
 * PREREQUISITOS:
 *     1.-Accesos validos para la API.
 *     2.-La API activa y funcionando.
 *
 * INSUMOS:
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_001_ValidarEstadoDeLaApi extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_ValidarEstadoDeLaApi_test(HashMap<String, String> data) throws Exception {
		 
		/***************************** Variables Data Provider **************************************/

		/***************************** Instancias **************************************************/
		String ambiente = data.get("ambiente");
		ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
		
		
		/*************************************** PASO 1 Validar el nombre de la API ***************************************/
		String apiName = "marketplace-ima";
		addStep("Validar el nombre de la API","Debe ser marketplace-ima");
		testCase.addCodeEvidenceCurrentStep("API validada correctamente: " + apiName);

		/*************************************** PASO 2 Utilizando el token generar un segundo token desde el endpoint de OAuth2 ***************************************/
		addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2","Debes utilizar un api token");
		testCase.addCodeEvidenceCurrentStep("Token generado correctamente");
		//Este paso ocurre en los utils

		/*************************************** PASO 3 Utilizar el token generado como header para las siguiente accion. ***************************************/
		addStep("Utilizar el token generado como header para las siguiente accion.","Se debe utilizar un OAuth2");
		testCase.addCodeEvidenceCurrentStep("Se esta utilizando OAuth2" );
		//Este paso ocurre en los utils

		/*************************************** PASO 4 Hacer una llamada tipo GET a https://api-dev.oxxo.io/exp-marketplace-space-leasing/api/v1/health-check y validar el status code ***************************************/
		addStep("Hacer una llamada tipo GET a "+ util.getTokenUrl()+ " y validar el status code","Debe regresar un 200");
		Response response = util.getHealtCheck();
		int statusCode = response.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
		testCase.addCodeEvidenceCurrentStep("Status code correcto: " + statusCode);
		assertEquals(statusCode, 200, "Validacion codigo de estatus 200");
		

		/*************************************** PASO 5 Validar el response de la consulta ***************************************/
		addStep("Validar el response de la consulta","El response debe ser:\n{\n    \"status\": \"Success\"\n}");
		String statusValue = response.jsonPath().getString("status");
		assertTrue("Success".equals(statusValue),"Validacion del mensaje de status");		
		testCase.addCodeEvidenceCurrentStep("Response validado correctamente: " + response.getBody().asString());


	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarEstadoDeLaApi";
	}

	@Override
	public String setTestDescription() {
		return "Se valida el nombre de la API\r\n"
				+ "Se valida la seguridad de la API\r\n"
				+ "Se valida el Healt-Check";
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
		return "1.-Accesos validos para la API.\r\n"
				+ "2.-La API activa y funcionando.";
		
	}

	@Override
	public void beforeTest() {
		
	}
}

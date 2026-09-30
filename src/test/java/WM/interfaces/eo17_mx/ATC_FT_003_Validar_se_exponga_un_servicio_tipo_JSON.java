package WM.interfaces.eo17_mx;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

/**
 * Requisito mandatorios para el Nombre (Java): -No incluir acentos, espacios en
 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
 * exceder de 100 caracteres -El nombre del caso de prueba debe ser igual que el
 * que se usa en Test, solo que debe ser precedido por la siglas o nombre del
 * proyecto al que pertenece. -Cuando se trata de la primera clase para un caso
 * de prueba no lleva ningun identificador de proyecto. [Tipo ejecucion]_[Tipo
 * Caso de prueba]_[Numero de Caso]_[Nombre caso de prueba]_[Proyecto] El tipo
 * de ejecucion siempre debe ser ATC para los casos de prueba automatizados. Ej:
 * ATC_FT_001_TestExample_Project (cuando es para un proyecto) ATC - Automated
 * Test Case FT - Functional Test 001 - Test case Nombre - TestExample Project -
 * Proyecto para el que se desarrollo el caso de prueba
 *
 * La clase siempre debe estar extendida de "BaseExecution", para usar los
 * metodos y herramientas del Framework de Oxxo
 */

public class ATC_FT_003_Validar_se_exponga_un_servicio_tipo_JSON extends BaseExecution{

/**
 * Descripcion del test:
 * Validar que se realicen peticiones. Se realizan las consultas get y post para comprobar la existencia del servicio de petición 
 * tipo JSON; se realiza la petición con Json con los limites maximos de longitud y sin el campo uuid relacionado.
 * 
 * Pre-requisitos:
 * 1- Contar con la herramienta que permita la carga Json
 * 2- Contar con el ambiente QA
 *
 * Insumos:
 * 1- Archivo Json 
 */
/**
*  Con la sintaxix @Test del TestNG, se indica que el metodo que esta a
* continuacion es un Test.
* 
* Requisito mandatorios para el Nombre (Test): -No incluir acentos, espacios en blanco
* ni caracteres especiales en el nombre -La longitud del nombre no debe exceder de
* 100 caracteres -El nombre del caso de prueba debe ser unico. [Tipoejecucion]_[Tipo Caso de 
* prueba]_[Numero de Caso]_[Nombre caso de prueba]_Test El tipo de ejecucion siempre 
* debe ser ATC para los casos de prueba automatizados. Ej: ATC_FT_001_TestExample_Test ATC - Automated Test
* Case FT - Functional Test 001 - Test case Nombre - TestExample
* 
* Se manda como parametro el Data Provider: este es un archivo que esta alojado
* en Config/Input/DataProviders/TestExample/ATC_FT_001_TestExample.xlsx
*/
	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_003_Validar_se_exponga_un_servicio_tipo_JSON_test (HashMap<String, String> data) throws Exception {
		
		/** Abrir la pagina web donde se carga el JSON
		 * @param ApiMethodsUtil
		 * @author Melba Solis
		 */
		String baseURL = "https://qa-foliodepago-eo17mx.femcom.net:1812";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
		/*************************************** PASO 1 GET ***************************************/
		addStep("Ejecutar petición GET"); 
				
		//se obtienen los datos del dataprovider
		String getPath = data.get("getPath");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
			
		//Se envía como evidencia el request a ejecutar
		testCase.addCodeEvidenceCurrentStep("request:" + baseURL + getPath);
		
		//Ejecutar el request*
		//Response getRespuesta = api.getRequestMethod(getPath);
			
		RestAssured.baseURI = baseURL;
		Response getRespuesta = RestAssured
		.given()
			//.header("Authorization","NoAuth")
			.relaxedHTTPSValidation() //no buscar certificado SSL
			.contentType(ContentType.JSON) //tipo de api
		.when()
			.get("/restv2/cargaRelacional/id_carga/1");
			
		//Obtener el status code
		int getStatusCode = getRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
		assertEquals(getStatusCode, getExpectedCode); //Validación
			
		//Obtener body de respuesta
		String getBodyResponse = getRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse);
			
		System.out.print(getBodyResponse);
			
		//Validación de json*
		JSONObject getJson = new JSONObject(getBodyResponse);
		JSONObject dataObj = getJson.getJSONObject("DATA"); 
		String estatusString = dataObj.getString("ESTATUS");
		testCase.addTextEvidenceCurrentStep("ESTATUS:" + estatusString);
		assertEquals(estatusString, "E");
			
		/*************************************** PASO 2 POST ***************************************/
		//La petición se realiza con un JSON teniendo los limites maximos de longitud y sin los campos serie y uuid relacionado
		addStep("Ejecutar petición POST");
						
		//se obtienen los datos del dataprovider
		String postPath = data.get("postPath");
		String postBody = data.get("postBody");
		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
			
		System.out.print(postExpectedCode); 
						
		//Se envía como evidencia el request a ejecutar
		testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath);
		testCase.addCodeEvidenceCurrentStep("body:" + postBody);
			
		//Ejecutar el request*
		//Response postRespuesta = api.postRequestMethod(postPath, postBody);
			
		//Sección que se coloca para evitar que te pida permisos/token
		RestAssured.baseURI = baseURL;
		RestAssured.baseURI = baseURL;
		Response postRespuesta = RestAssured
			.given()
			//.header("Authorization","NoAuth")
			.relaxedHTTPSValidation() //no buscar certificado SSL
			.contentType(ContentType.JSON) //tipo de api
			.body(postBody)
				.when()
			.post("/restv2/cargaRelacional");
							
		//Obtener el status code
		int postStatusCode = postRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode);
		assertEquals(postStatusCode, postExpectedCode); //Validación
			
		//Obtener body de respuesta
		String postBodyResponse = postRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse);
		
		System.out.print(postBodyResponse); 

		//Validación de json*
		JSONObject postJson = new JSONObject(postBodyResponse);
		JSONObject campoResponse = postJson.getJSONObject("response"); 
		String wmMessageString = campoResponse.getString("wm_message");
		testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessageString);
		assertEquals(wmMessageString, "Se realizo correctamente la carga relacional en EBS");
	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Validar_se_exponga_un_servicio_tipo_JSON_test";
	}

	@Override
	public String setTestDescription() {
		return "Se realizan las consultas get y post para comprobar la existencia del servicio de petición tipo JSON, se realiza la petición con Json con los limites maximos de longitud y sin el campo uuid relacionado";
	}

	@Override
	public String setTestDesigner() {
		return "Melba Solís Castillo";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con la herramienta que permita la carga Json, contar con el ambiente QA";
	}

	@Override
	public void beforeTest() {
	}
}

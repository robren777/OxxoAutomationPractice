package WM.interfaces.eo17_mx;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Requisito mandatorios para el Nombre  (Java):  -No incluir acentos, espacios en
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

public class ATC_FT_005_Validar_mensaje_de_error_peticion_JSON extends BaseExecution{
	
/**
 * Descripcion del test:
 * Validar los mensajes de error que se pueden generar en peticiones Get y Post
 * 
 * Pre-requisitos:
 * 1- Contar con la herramienta que permita la carga Json
 * 2- Contar con el ambiente QA
 * 
 * Insumos:
 * 1- Tener acceso a la Base de Datos AVEBQA (para poder consultar los registros y poder hacer peticiones get que puedan genrar el error(ejemplo un registro no existente))
 * 2- Archivo Json que no cumpla con los parametros 
 * 		1.1- Json que contenga formatos distintos en campos Importe y Fecha
 * 		1.2- Json que exceda las longitudes en los campos
 * 		1.3- Diversos (11) Json en el cual debera faltar c/u de los campos en diferentes Json
 * 		1.4- Diversos (12)Json en el cual debera tener el campo nulo/vacio en c/u de los campos en diferentes Json 
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
	public void ATC_FT_005_Validar_mensaje_de_error_peticion_JSON_test(HashMap<String, String> data) throws Exception {
		
	/** Abrir la pagina web donde se carga el JSON
	 * @param ApiMethodsUtil
	 * @author Melba Solis
	 */
		
	String baseURL = "https://qa-foliodepago-eo17mx.femcom.net:1812";
	ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
	/*************************************** PASO 1 GET ***************************************/
	//Se realiza consulta Get con id_carga no existente en la BD
	addStep("Ejecutar petición GET con resultado de error");
		
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
		.get("/restv2/cargaRelacional/id_carga/2000");
			
	//Obtener el status code
	int getStatusCode = getRespuesta.getStatusCode();
	testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
	assertEquals(getStatusCode, getExpectedCode); //Validación
			
	//Obtener body de respuesta
	String getBodyResponse = getRespuesta.asPrettyString();
	testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse); 
							
	/*************************************** PASO 2 GET ***************************************/
	/*//Continua el proceso de validación de EBS con el id_carga
	addStep("Ejecutar petición GET con resultado de error");
	
	//se obtienen los datos del dataprovider
	String getPath2 = data.get("getPath2");
	int getExpectedCode2 = Integer.parseInt(data.get("getExpectedCode2"));
				
	//Se envía como evidencia el request a ejecutar
	testCase.addCodeEvidenceCurrentStep("request:" + baseURL + getPath2);
				
	//Ejecutar el request*
	//Response getRespuesta = api.getRequestMethod(getPath);
	RestAssured.baseURI = baseURL;
	Response getRespuesta2 = RestAssured
	.given()
		//.header("Authorization","NoAuth")
		.relaxedHTTPSValidation() //no buscar certificado SSL
		.contentType(ContentType.JSON) //tipo de api
	.when()
		.get("/restv2/cargaRelacional/id_carga/6292");
				
	//Obtener el status code
	int getStatusCode2 = getRespuesta2.getStatusCode();
	testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode2);
	assertEquals(getStatusCode2, getExpectedCode2); //Validación
				
	//Obtener body de respuesta
	String getBodyResponse2 = getRespuesta2.asPrettyString();
	testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse2); 
				
	/*************************************** PASO 3 GET ***************************************/
	//Error en la validación del id_carga para consultar el estatus de la carga relacional //Error que se genera al colocar valores en formato diferente al esperado en id_carga
	addStep("Ejecutar petición GET con resultado de error");
				
	//se obtienen los datos del dataprovider
	String getPath3 = data.get("getPath3");
	int getExpectedCode3 = Integer.parseInt(data.get("getExpectedCode3"));
		
	//Se envía como evidencia el request a ejecutar
	testCase.addCodeEvidenceCurrentStep("request:" + baseURL + getPath3);
					
	//Ejecutar el request*
	//Response getRespuesta = api.getRequestMethod(getPath);
	RestAssured.baseURI = baseURL;
	Response getRespuesta3 = RestAssured
	.given()
		//.header("Authorization","NoAuth")
		.relaxedHTTPSValidation() //no buscar certificado SSL
		.contentType(ContentType.JSON) //tipo de api
	.when()
		.get("/restv2/cargaRelacional/id_carga/2gr");
					
	//Obtener el status code
	int getStatusCode3 = getRespuesta3.getStatusCode();
	testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode3);
	assertEquals(getStatusCode3, getExpectedCode3); //Validación
					
	//Obtener body de respuesta
	String getBodyResponse3 = getRespuesta3.asPrettyString();
	testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse3); 
					
	/*************************************** POST 1 ***************************************/
			//Carga JSON con formatos incorrectos en importe y fecha//
			addStep("Ejecutar petición POST - Json con campos Importes y Fecha con formatos incorrectos"); 
			
			//se obtienen los datos del dataprovider
			String postPath = data.get("postPath");
			String postBody = data.get("postBody");
			int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
			
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody);
						
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
			
			//Validación de json*
			JSONObject postJson = new JSONObject(postBodyResponse);
			JSONObject campoResponse = postJson.getJSONObject("response"); 
			String wmMessageString = campoResponse.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessageString);
			assertEquals(wmMessageString, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: string a does not match pattern ^([+-]?[0-9]{1,14}(\\.[0-9]{1,2})?)$ - #/properties/DATA/items/properties/IMPORTE; [ISC.0164.0030] JSON schema validation failed: string 15 enero del 2023 does not match pattern ^([0-2][0-9]|3[0-1])(/)(0[1-9]|1[0-2])\\2(\\d{4})$ - #/properties/DATA/items/properties/FECHA_FACTURA");
			

/*postBody2 - Carga JSON con campos que exceden la longitud máxima permitida en cada campo.*/
			addStep("Ejecutar petición POST - Json con campos que exceden la longitud máxima permitida");
			
			//se obtienen los datos del dataprovider
			String postPath2 = data.get("postPath2");
			String postBody2 = data.get("postBody2");
			int postExpectedCode2 = Integer.parseInt(data.get("postExpectedCode2"));
			
			System.out.print(postExpectedCode2); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request2:" + baseURL + postPath2);
			testCase.addCodeEvidenceCurrentStep("body2:" + postBody2);
			
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta2 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody2)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode2 = postRespuesta2.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode2: " + postStatusCode2);
			assertEquals(postStatusCode2, postExpectedCode2); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse2 = postRespuesta2.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse2:" + postBodyResponse2);
			
			System.out.print(postBodyResponse2); 

			//Validación de json*
			JSONObject postJson2 = new JSONObject(postBodyResponse2);
			JSONObject campoResponse2 = postJson2.getJSONObject("response"); 
			String wmMessage2String = campoResponse2.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage2String);
			assertEquals(wmMessage2String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: expected maxLength: 150, actual: 159 - #/properties/DATA/items/properties/PROVEEDOR; [ISC.0164.0030] JSON schema validation failed: string 1234567891032321112.66 does not match pattern ^([+-]?[0-9]{1,14}(\\.[0-9]{1,2})?)$ - #/properties/DATA/items/properties/IMPORTE_OXXO; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 18, actual: 28 - #/properties/DATA/items/properties/ID_CARGA; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 70, actual: 128 - #/properties/DATA/items/properties/FOLIO; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 6, actual: 18 - #/properties/DATA/items/properties/TIPO_COMPROBANTE; [ISC.0164.0030] JSON schema validation failed: string 1234567893232101112.44 does not match pattern ^([+-]?[0-9]{1,14}(\\.[0-9]{1,2})?)$ - #/properties/DATA/items/properties/IMPORTE; [ISC.0164.0030] JSON schema validation failed: string 15/01/20233 does not match pattern ^([0-2][0-9]|3[0-1])(/)(0[1-9]|1[0-2])\\2(\\d{4})$ - #/properties/DATA/items/properties/FECHA_FACTURA; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 45, actual: 50 - #/properties/DATA/items/properties/UUID; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 70, actual: 76 - #/properties/DATA/items/properties/FOLIO_PAGO; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 15, actual: 18 - #/properties/DATA/items/properties/RFC; [ISC.0164.0030] JSON schema validation failed: expected maxLength: 10, actual: 16 - #/properties/DATA/items/properties/SERIE");

//postBody3	- RFC - Carga JSON con campos obligatorios faltantes uno por uno y verifica que se devuelva un mensaje de error apropiado para cada campo faltante.
/************************************* POST *************************************/
			addStep("Ejecutar petición POST - Json con campo RFC faltante");
			
			//se obtienen los datos del dataprovider
			String postPath3 = data.get("postPath3");
			String postBody3 = data.get("postBody3");
			int postExpectedCode3 = Integer.parseInt(data.get("postExpectedCode3"));
			
			System.out.print(postExpectedCode3); 
						
			//Se env�a como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request3:" + baseURL + postPath3);
			testCase.addCodeEvidenceCurrentStep("body3:" + postBody3);
			
			//Secci�n que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta3 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody3)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode3 = postRespuesta3.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode3);
			assertEquals(postStatusCode3, postExpectedCode3); //Validaci�n
			
			//Obtener body de respuesta
			String postBodyResponse3 = postRespuesta3.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse3);
			
			System.out.print(postBodyResponse3); 

			//Validaci�n de json*
			JSONObject postJson3 = new JSONObject(postBodyResponse3);
			JSONObject campoResponse3 = postJson3.getJSONObject("response"); 
			String wmMessage3String = campoResponse3.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage3String);
			assertEquals(wmMessage3String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [RFC] not found - #/properties/DATA/items");

//postBody4 - PROVEEDOR//
/************************************* POST *************************************/
			addStep("Ejecutar petición POST - Json con campo PROVEEDOR faltante");
			
			//se obtienen los datos del dataprovider
			String postPath4 = data.get("postPath4");
			String postBody4 = data.get("postBody4");
			int postExpectedCode4 = Integer.parseInt(data.get("postExpectedCode4"));
			
			System.out.print(postExpectedCode4); 
						
			//Se env�a como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request4:" + baseURL + postPath4);
			testCase.addCodeEvidenceCurrentStep("body4:" + postBody4);
						
			//Secci�n que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta4 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody4)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode4 = postRespuesta4.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode4);
			assertEquals(postStatusCode4, postExpectedCode4); //Validaci�n
			
			//Obtener body de respuesta
			String postBodyResponse4 = postRespuesta4.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse4);
			
			System.out.print(postBodyResponse4); 

			//Validaci�n de json*
			JSONObject postJson4 = new JSONObject(postBodyResponse4);
			JSONObject campoResponse4 = postJson4.getJSONObject("response"); 
			String wmMessage4String = campoResponse4.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage4String);
			assertEquals(wmMessage4String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [PROVEEDOR] not found - #/properties/DATA/items");

//postBody6 - FOLIO//
			addStep("Ejecutar petición POST - Json con campo FOLIO faltante");
			
			//se obtienen los datos del dataprovider
			String postPath6 = data.get("postPath6");
			String postBody6 = data.get("postBody6");
			int postExpectedCode6 = Integer.parseInt(data.get("postExpectedCode6"));
			
			System.out.print(postExpectedCode6); 
						
			//Se env�a como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath6);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody6);
						
			//Secci�n que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta6 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody6)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode6 = postRespuesta6.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode6);
			assertEquals(postStatusCode6, postExpectedCode6); //Validaci�n
			
			//Obtener body de respuesta
			String postBodyResponse6 = postRespuesta6.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse6);
			
			System.out.print(postBodyResponse6); 

			//Validaci�n de json*
			JSONObject postJson6 = new JSONObject(postBodyResponse6);
			JSONObject campoResponse6 = postJson6.getJSONObject("response"); 
			String wmMessage6String = campoResponse6.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage6String);
			assertEquals(wmMessage6String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [FOLIO] not found - #/properties/DATA/items");

//postBody7 - FOLIO PAGO//
			addStep("Ejecutar petición POST - Json con campo FOLIO PAGO faltante");
			
			//se obtienen los datos del dataprovider
			String postPath7 = data.get("postPath7");
			String postBody7 = data.get("postBody7");
			int postExpectedCode7 = Integer.parseInt(data.get("postExpectedCode7"));
			
			System.out.print(postExpectedCode7); 
						
			//Se env�a como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath7);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody7);
						
			//Secci�n que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta7 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody7)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode7 = postRespuesta7.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode7);
			assertEquals(postStatusCode7, postExpectedCode7); //Validaci�n
			
			//Obtener body de respuesta
			String postBodyResponse7 = postRespuesta7.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse7);
			
			System.out.print(postBodyResponse7); 

			//Validaci�n de json*
			JSONObject postJson7 = new JSONObject(postBodyResponse7);
			JSONObject campoResponse7 = postJson7.getJSONObject("response"); 
			String wmMessage7String = campoResponse7.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage7String);
			assertEquals(wmMessage7String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [FOLIO_PAGO] not found - #/properties/DATA/items");

//postBody8 - IMPORTE//
			addStep("Ejecutar petición POST - Json con campo IMPORTE faltante");
			
			//se obtienen los datos del dataprovider
			String postPath8 = data.get("postPath8");
			String postBody8 = data.get("postBody8");
			int postExpectedCode8 = Integer.parseInt(data.get("postExpectedCode8"));
			
			System.out.print(postExpectedCode8); 
						
			//Se env�a como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath8);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody8);
						
			//Secci�n que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta8 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody8)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode8 = postRespuesta8.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode8);
			assertEquals(postStatusCode8, postExpectedCode8); //Validaci�n
			
			//Obtener body de respuesta
			String postBodyResponse8 = postRespuesta8.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse8);
			
			System.out.print(postBodyResponse8); 

			//Validaci�n de json*
			JSONObject postJson8 = new JSONObject(postBodyResponse8);
			JSONObject campoResponse8 = postJson8.getJSONObject("response"); 
			String wmMessage8String = campoResponse8.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage8String);
			assertEquals(wmMessage8String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [IMPORTE] not found - #/properties/DATA/items");

//postBody9 - IMPORTE OXXO//
			addStep("Ejecutar petición POST - Json con campo IMPORTE OXXO faltante");
			
			//se obtienen los datos del dataprovider
			String postPath9 = data.get("postPath9");
			String postBody9 = data.get("postBody9");
			int postExpectedCode9 = Integer.parseInt(data.get("postExpectedCode9"));
			
			System.out.print(postExpectedCode9); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath9);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody9);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta9 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody9)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode9 = postRespuesta9.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode9);
			assertEquals(postStatusCode9, postExpectedCode9); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse9 = postRespuesta9.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse9);
			
			System.out.print(postBodyResponse9); 

			//Validación de json*
			JSONObject postJson9 = new JSONObject(postBodyResponse9);
			JSONObject campoResponse9 = postJson9.getJSONObject("response"); 
			String wmMessage9String = campoResponse9.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage9String);
			assertEquals(wmMessage9String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [IMPORTE_OXXO] not found - #/properties/DATA/items");

//postBody10 - FECHA FACTURA//
			addStep("Ejecutar petición POST - Json con campo FECHA FACTURA faltante");
			
			//se obtienen los datos del dataprovider
			String postPath10 = data.get("postPath10");
			String postBody10 = data.get("postBody10");
			int postExpectedCode10 = Integer.parseInt(data.get("postExpectedCode10"));
			
			System.out.print(postExpectedCode10); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath10);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody10);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta10 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody10)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode10 = postRespuesta10.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode10);
			assertEquals(postStatusCode10, postExpectedCode10); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse10 = postRespuesta10.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse10);
			
			System.out.print(postBodyResponse10); 

			//Validación de json*
			JSONObject postJson10 = new JSONObject(postBodyResponse10);
			JSONObject campoResponse10 = postJson10.getJSONObject("response");
			String wmMessage10String = campoResponse10.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage10String);
			assertEquals(wmMessage10String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [FECHA_FACTURA] not found - #/properties/DATA/items");

//postBody11 - UUID/			
			addStep("Ejecutar petición POST - Json con campo UUID faltante");
			
			//se obtienen los datos del dataprovider
			String postPath11 = data.get("postPath11");
			String postBody11 = data.get("postBody11");
			int postExpectedCode11 = Integer.parseInt(data.get("postExpectedCode11"));
			
			System.out.print(postExpectedCode11); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath11);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody11);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta11 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody11)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode11 = postRespuesta11.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode11);
			assertEquals(postStatusCode11, postExpectedCode11); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse11 = postRespuesta11.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse11);
			
			System.out.print(postBodyResponse11); 

			//Validación de json*
			JSONObject postJson11 = new JSONObject(postBodyResponse11);
			JSONObject campoResponse11 = postJson11.getJSONObject("response"); 
			String wmMessage11String = campoResponse11.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage11String);
			assertEquals(wmMessage11String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [UUID] not found - #/properties/DATA/items");

//postBody12 - TIPO COMPROBANTE//
			addStep("Ejecutar petición POST - Json con campo TIPO COMPROBANTE faltante");
			
			//se obtienen los datos del dataprovider
			String postPath12 = data.get("postPath12");
			String postBody12 = data.get("postBody12");
			int postExpectedCode12 = Integer.parseInt(data.get("postExpectedCode12"));
			
			System.out.print(postExpectedCode12); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath12);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody12);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta12 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody12)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode12 = postRespuesta12.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode12);
			assertEquals(postStatusCode12, postExpectedCode12); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse12 = postRespuesta12.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse12);
			
			System.out.print(postBodyResponse12); 

			//Validación de json*
			JSONObject postJson12 = new JSONObject(postBodyResponse12);
			JSONObject campoResponse12 = postJson12.getJSONObject("response"); 
			String wmMessage12String = campoResponse12.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage12String);
			assertEquals(wmMessage12String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [TIPO_COMPROBANTE] not found - #/properties/DATA/items");

//postBody13 - ID CARGA//
			addStep("Ejecutar petición POST - Json con campo ID CARGA faltante");
			
			//se obtienen los datos del dataprovider
			String postPath13 = data.get("postPath13");
			String postBody13 = data.get("postBody13");
			int postExpectedCode13 = Integer.parseInt(data.get("postExpectedCode13"));
			
			System.out.print(postExpectedCode13); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath13);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody13);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta13 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody13)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode13 = postRespuesta13.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode13);
			assertEquals(postStatusCode13, postExpectedCode13); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse13 = postRespuesta13.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse13);
			
			System.out.print(postBodyResponse13); 

			//Validación de json*
			JSONObject postJson13 = new JSONObject(postBodyResponse13);
			JSONObject campoResponse13 = postJson13.getJSONObject("response"); 
			String wmMessage13String = campoResponse13.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage13String);
			assertEquals(wmMessage13String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: required key [ID_CARGA] not found - #/properties/DATA/items");

//postBody14 - RFC - Carga JSON con valores nulos/vacios en campos y verifica que se devuelva un mensaje de error adecuado
			addStep("Ejecutar petición POST - Json con campo RFC nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath14 = data.get("postPath14");
			String postBody14 = data.get("postBody14");
			int postExpectedCode14 = Integer.parseInt(data.get("postExpectedCode14"));
			
			System.out.print(postExpectedCode14); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath14);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody14);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta14 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody14)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode14 = postRespuesta14.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode14);
			assertEquals(postStatusCode14, postExpectedCode14); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse14 = postRespuesta14.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse14);
			
			System.out.print(postBodyResponse14); 

			//Validación de json*
			JSONObject postJson14 = new JSONObject(postBodyResponse14);
			JSONObject campoResponse14 = postJson14.getJSONObject("response"); 
			String wmMessage14String = campoResponse14.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage14String);
			assertEquals(wmMessage14String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: expected minLength: 1, actual: 0 - #/properties/DATA/items/properties/RFC");

//postBody15 - PROVEEDOR//
			addStep("Ejecutar petición POST - Json con campo PROVEEDOR nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath15 = data.get("postPath15");
			String postBody15 = data.get("postBody15");
			int postExpectedCode15 = Integer.parseInt(data.get("postExpectedCode15"));
			
			System.out.print(postExpectedCode15); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath15);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody15);
			
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta15 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody15)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode15 = postRespuesta15.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode15);
			assertEquals(postStatusCode15, postExpectedCode15); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse15 = postRespuesta15.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse15);
			
			System.out.print(postBodyResponse15); 

			//Validación de json*
			JSONObject postJson15 = new JSONObject(postBodyResponse15);
			JSONObject campoResponse15 = postJson15.getJSONObject("response"); 
			String wmMessage15String = campoResponse15.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage15String);
			assertEquals(wmMessage15String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: expected minLength: 1, actual: 0 - #/properties/DATA/items/properties/PROVEEDOR");

//postBody17 - FOLIO//
			addStep("Ejecutar petición POST - Json con campo FOLIO nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath17 = data.get("postPath17");
			String postBody17 = data.get("postBody17");
			int postExpectedCode17 = Integer.parseInt(data.get("postExpectedCode17"));
			
			System.out.print(postExpectedCode17); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath17);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody17);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta17 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody17)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode17 = postRespuesta17.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode17);
			assertEquals(postStatusCode17, postExpectedCode17); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse17 = postRespuesta17.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse17);
			
			System.out.print(postBodyResponse17); 

			//Validación de json*
			JSONObject postJson17 = new JSONObject(postBodyResponse17);
			JSONObject campoResponse17 = postJson17.getJSONObject("response"); 
			String wmMessage17String = campoResponse17.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage17String);
			assertEquals(wmMessage17String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: expected minLength: 1, actual: 0 - #/properties/DATA/items/properties/FOLIO");

//postBody18 - FOLIO PAGO//
			addStep("Ejecutar petición POST - Json con campo FOLIO PAGO nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath18 = data.get("postPath18");
			String postBody18 = data.get("postBody18");
			int postExpectedCode18 = Integer.parseInt(data.get("postExpectedCode18"));
			
			System.out.print(postExpectedCode18); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath18);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody18);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta18 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody18)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode18 = postRespuesta18.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode18);
			assertEquals(postStatusCode18, postExpectedCode18); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse18 = postRespuesta18.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse18);
			
			System.out.print(postBodyResponse18); 

			//Validación de json*
			JSONObject postJson18 = new JSONObject(postBodyResponse18);
			JSONObject campoResponse18 = postJson18.getJSONObject("response"); 
			String wmMessage18String = campoResponse18.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage18String);
			assertEquals(wmMessage18String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: expected minLength: 1, actual: 0 - #/properties/DATA/items/properties/FOLIO_PAGO");

//postBody19 - IMPORTE//
			addStep("Ejecutar peticón POST - Json con campo IMPORTE nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath19 = data.get("postPath19");
			String postBody19 = data.get("postBody19");
			int postExpectedCode19 = Integer.parseInt(data.get("postExpectedCode19"));
			
			System.out.print(postExpectedCode19); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath19);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody19);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta19 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody19)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode19 = postRespuesta19.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode19);
			assertEquals(postStatusCode19, postExpectedCode19); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse19 = postRespuesta19.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse19);
			
			System.out.print(postBodyResponse19); 

			//Validación de json*
			JSONObject postJson19 = new JSONObject(postBodyResponse19);
			JSONObject campoResponse19 = postJson19.getJSONObject("response"); 
			String wmMessage19String = campoResponse19.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage19String);
			assertEquals(wmMessage19String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: string  does not match pattern ^([+-]?[0-9]{1,14}(\\.[0-9]{1,2})?)$ - #/properties/DATA/items/properties/IMPORTE");

//postBody20 - IMPORTE OXXO//
			addStep("Ejecutar petición POST - Json con campo IMPORTE OXXO nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath20 = data.get("postPath20");
			String postBody20 = data.get("postBody20");
			int postExpectedCode20 = Integer.parseInt(data.get("postExpectedCode20"));
			
			System.out.print(postExpectedCode20); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath20);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody20);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta20 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody20)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode20 = postRespuesta20.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode20);
			assertEquals(postStatusCode20, postExpectedCode20); //Validaci�n
			
			//Obtener body de respuesta
			String postBodyResponse20 = postRespuesta20.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse20);
			
			System.out.print(postBodyResponse20); 

			//Validación de json*
			JSONObject postJson20 = new JSONObject(postBodyResponse20);
			JSONObject campoResponse20 = postJson20.getJSONObject("response"); 
			String wmMessage20String = campoResponse20.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage20String);
			assertEquals(wmMessage20String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: string  does not match pattern ^([+-]?[0-9]{1,14}(\\.[0-9]{1,2})?)$ - #/properties/DATA/items/properties/IMPORTE_OXXO");

//postBody21 - FECHA FACTURA//
			addStep("ejecutar petición POST - Json con campo FECHA FACTURA nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath21 = data.get("postPath21");
			String postBody21 = data.get("postBody21");
			int postExpectedCode21 = Integer.parseInt(data.get("postExpectedCode21"));
			
			System.out.print(postExpectedCode21); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath21);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody21);
			
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta21 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody21)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode21 = postRespuesta21.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode21);
			assertEquals(postStatusCode21, postExpectedCode21); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse21 = postRespuesta21.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse21);
			
			System.out.print(postBodyResponse21); 

			//Validación de json*
			JSONObject postJson21 = new JSONObject(postBodyResponse21);
			JSONObject campoResponse21 = postJson21.getJSONObject("response"); 
			String wmMessage21String = campoResponse21.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage21String);
			assertEquals(wmMessage21String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: string  does not match pattern ^([0-2][0-9]|3[0-1])(/)(0[1-9]|1[0-2])\\2(\\d{4})$ - #/properties/DATA/items/properties/FECHA_FACTURA");

//postBody22 - UUID//
			addStep("Ejecutar petición POST - Json con campo UUID nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath22 = data.get("postPath22");
			String postBody22 = data.get("postBody22");
			int postExpectedCode22 = Integer.parseInt(data.get("postExpectedCode22"));
			
			System.out.print(postExpectedCode22); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath22);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody22);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta22 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody22)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode22 = postRespuesta22.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode22);
			assertEquals(postStatusCode22, postExpectedCode22); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse22 = postRespuesta22.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse22);
			
			System.out.print(postBodyResponse22); 

			//Validación de json*
			JSONObject postJson22 = new JSONObject(postBodyResponse22);
			JSONObject campoResponse22 = postJson22.getJSONObject("response"); 
			String wmMessage22String = campoResponse22.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage22String);
			assertEquals(wmMessage22String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: expected minLength: 1, actual: 0 - #/properties/DATA/items/properties/UUID");
			
//postBody23 - TIPO COMPROBANTE//
			addStep("ejecutar petición POST - Json con campo TIPO COMPROBANTE nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath23 = data.get("postPath23");
			String postBody23 = data.get("postBody23");
			int postExpectedCode23 = Integer.parseInt(data.get("postExpectedCode23"));
			
			System.out.print(postExpectedCode23); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath23);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody23);
						
			//Secciín que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta23 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody23)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode23 = postRespuesta23.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode23);
			assertEquals(postStatusCode23, postExpectedCode23); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse23 = postRespuesta23.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse23);
			
			System.out.print(postBodyResponse23); 

			//Validación de json*
			/*JSONObject postJson23 = new JSONObject(postBodyResponse23);
			JSONObject campoResponse23 = postJson23.getJSONObject("response"); 
			String wmMessage23String = campoResponse23.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage23String);
			assertEquals(wmMessage23String, "Error al realizar la carga relacional en EBS: Error en procedimiento XXFC_AP_CFDVALFACTURAS_PKG.obt_cargarelacional_prc - CURSOR cur_cargacsv: ORA-06512: en \"APPS.XXFC_AP_CFDVALFACTURAS_PKG\", línea 98\n-1400: ORA-01400: no se puede realizar una inserci�n NULL en (\"XXFC\".\"XXFC_AP_CARGACSV_ALL\".\"TIPO_COMPROBANTE\")");*/
					
//postBody25 - ID CARGA//
			addStep("Ejecutar petición POST - Json con campo ID CARGA nulo/vacío");
			
			//se obtienen los datos del dataprovider
			String postPath25 = data.get("postPath25");
			String postBody25 = data.get("postBody25");
			int postExpectedCode25 = Integer.parseInt(data.get("postExpectedCode25"));
			
			System.out.print(postExpectedCode25); 
						
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath25);
			testCase.addCodeEvidenceCurrentStep("body:" + postBody25);
						
			//Sección que se coloca para evitar que te pida permisos/token
			RestAssured.baseURI = baseURL;
			RestAssured.baseURI = baseURL;
			Response postRespuesta25 = RestAssured
			.given()
				//.header("Authorization","NoAuth")
				.relaxedHTTPSValidation() //no buscar certificado SSL
				.contentType(ContentType.JSON) //tipo de api
				.body(postBody25)
					.when()
				.post("/restv2/cargaRelacional");
							
			//Obtener el status code
			int postStatusCode25 = postRespuesta25.getStatusCode();
			testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode25);
			assertEquals(postStatusCode25, postExpectedCode25); //Validación
			
			//Obtener body de respuesta
			String postBodyResponse25 = postRespuesta25.asPrettyString();
			testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse25);
			
			System.out.print(postBodyResponse25); 

			//Validación de json*
			JSONObject postJson25 = new JSONObject(postBodyResponse25);
			JSONObject campoResponse25 = postJson25.getJSONObject("response"); 
			String wmMessage25String = campoResponse25.getString("wm_message");
			testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessage25String);
			assertEquals(wmMessage25String, "Error en la validación del JSON para la carga relacional:  [ISC.0164.0030] JSON schema validation failed: string  does not match pattern ^[0-9]+$ - #/properties/DATA/items/properties/ID_CARGA");
		
		/** Abrir la pagina web donde se carga el JSON
		 * @param SQLUtil 
		 */
		  //Utileria// Se considera una utileria la BD //
		//****************** Llamar a la Base de Datos **********************/
			  
			 SQLUtil DBFCWMLQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA3, GlobalVariables.DB_USER_FCWMLQA3, GlobalVariables.DB_PASSWORD_FCWMLQA3);
			  
			  // Variables
		//********************************************************************/
		//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
		//testCase.setTest_Description(data.get("id") + data.get("description"));

		//testcase va a mandar los prerequisitos 
		testCase.setPrerequisites("Que se haya realizado una consulta con error y se guarden en la tabla correspondiente");
		//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

		System.out.println(

		"*************** PASOS 3 ******************************************");
		//add = añadir // step = paso //STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
		//añadir un paso // El paso cosiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
		addStep("Ingresar a la BD de FCMLQA para validar la consulta get y post realizada y validar el mensaje de error de la tabla WMLOG.WM_LOG_ERROR_TPE en la columna DESCRIPTION");
		String consulta_FCWMLQA = data.get("consulta_mensajes_error"); 
		System.out.println("Tu consulta es: " + consulta_FCWMLQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO

		SQLResult ExecuteQuery = executeQuery(DBFCWMLQA, consulta_FCWMLQA); // execute = ejecutar Query=consulta (DBFCMLQA, consulta_FCMLQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)

		boolean conexiondbFwmlqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
		conexiondbFwmlqa  = ExecuteQuery.isEmpty();

		testCase.addTextEvidenceCurrentStep("Conexion: FCWMLQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

		assertFalse(conexiondbFwmlqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
				
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_Validar_mensaje_de_error_peticion_JSON_test";
	}

	@Override
	public String setTestDescription() {
		return "Se carga un archivo tipo JSON con errores en la herrmienta postman, se realiza una consulta get y post con valores que provoquen los mensajes de error correspondientes";
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
		return "El servicio de peticion debe estar creado";
	}

	@Override
	public void beforeTest() {
	}

}
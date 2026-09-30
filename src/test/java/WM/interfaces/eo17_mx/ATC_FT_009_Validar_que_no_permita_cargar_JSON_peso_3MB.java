package WM.interfaces.eo17_mx;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import config.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.TextDocumentUtil;

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
public class ATC_FT_009_Validar_que_no_permita_cargar_JSON_peso_3MB extends BaseExecution{

/**
 * Descripcion del test:
 * Validar que al cargar un Json con peso mayor a 3MB no permita la carga
 * 
 * Pre-requisitos:
 * 1- Contar con la herramienta que permita la carga Json
 * 2- Contar con el ambiente QA
 *
 * Insumos:
 * 1- Archivo Json que exceda el peso permitido "3MB"
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
	public void ATC_FT_009_Validar_que_no_permita_cargar_JSON_peso_3MB_test(HashMap<String, String> data) throws Exception {
			
		/** Abrir la pagina web donde se carga el JSON
		 * @param ApiMethodsUtil
		 * @author Melba Solis
		 */		
		// Abrir la pagina web donde se carga el JSON
		String baseURL = "https://qa-foliodepago-eo17mx.femcom.net:1812";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
			
		/*************************************** PASO 1 y 2 ***************************************/
		addStep("Ejecutar petición POST con JSON de peso mayor a 3MB y validar que no permita el viaje");
	
		TextDocumentUtil tu = new TextDocumentUtil();
		        String filepath = data.get("postBody");
		        filepath = Constants.BASE_PATH + "\\" + filepath;
		        String postBody = tu.readLines(filepath);
			System.out.print(filepath);
						
		//System.out.print(postExpectedCode); 
		String postPath = data.get("postPath");
		// Se envía como evidencia el request a ejecutar
		//testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath); 
		//testCase.addCodeEvidenceCurrentStep("body:" + baseURL + postBody); //Se comenta esta parte para que no salga en la evidencia dado que por el peso del archivo no se carga en test o la evidencia en octane y del mismo modo el pdf se genera con error por lo que no se permite visualizar
		/*Metodo para agregar a la evidencia la referencia a un archivo
		se debe pasar la ruta del archivo y un string que se mostrara como el link de referencia al archivo*/
		//testCase.addDocumentEvidence(filepath, "JSON del post body");
		testCase.addDocumentEvidence("/../../../" + data.get("postBody"), "testJSON3MB");
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
		//assertEquals(postStatusCode, postExpectedCode); //Validación
				
		//Obtener body de respuesta
		String postBodyResponse = postRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse);
		
		addStep("Codigo de error:");
		//Validación de json*
		JSONObject postJson = new JSONObject(postBodyResponse);
		JSONObject campoResponse = postJson.getJSONObject("response"); 
		String wmCodeString = campoResponse.getString("wm_code");
		testCase.addTextEvidenceCurrentStep("CODIGO DE ERROR:" + wmCodeString);
		assertEquals(wmCodeString, "110");
				
		}

		@Override
		public String setTestFullName() {
			return "ATC_FT_009_Validar_que_no_permita_cargar_JSON_peso_3MB_test";
		}

		@Override
		public String setTestDescription() {
			return "Se carga un archivo tipo JSON con peso mayor a 3MB en la herrmienta postman y no se carga correctamente";
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

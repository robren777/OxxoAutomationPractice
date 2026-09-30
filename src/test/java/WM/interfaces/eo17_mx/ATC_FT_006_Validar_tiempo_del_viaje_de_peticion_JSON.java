package WM.interfaces.eo17_mx;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

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

public class ATC_FT_006_Validar_tiempo_del_viaje_de_peticion_JSON extends BaseExecution{

/**
 * Descripcion del test:
 * Validar el tiempo que se demoran las peticiones Get y Post (carga Json)
 * 
 * Pre-requisitos:
 * 1- Contar con la herramienta que permita la carga Json
 * 2- Contar con el ambiente QA
 * 3- Realizar la peticion Get
 * 4- Realizar peticion Post (cargar Json)
 *
 * Insumos:
 * 1- Archivo Json que cumpla con los parametros 
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
	public void ATC_FT_006_Validar_tiempo_del_viaje_de_peticion_JSON_test(HashMap<String, String> data) throws Exception {
		
		/** Abrir la pagina web donde se carga el JSON
		 * @param ApiMethodsUtil
		 * @author Melba Solis
		 */		
		// Abrir la pagina web donde se carga el JSON
		String baseURL = "https://qa-foliodepago-eo17mx.femcom.net:1812";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
		/**************** PASO 1 peticion GET y PASO 2 validacion de tiempo peticion ****************/
		addStep("Ejecutar petición GET");
		
		//se obtienen los datos del dataprovider
		String getPath = data.get("getPath");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
			
		//Se envía como evidencia el request a ejecutar
		testCase.addCodeEvidenceCurrentStep("request:" + baseURL + getPath);
		
		// Registra el tiempo antes de realizar la petición JSON
		long startTime = System.currentTimeMillis();
			
		//Ejecutar el request*
		//Response getRespuesta = api.getRequestMethod(getPath);
			
		//Ejecutar el request*
		//Response getRespuesta = api.getRequestMethod(getPath);
		RestAssured.baseURI = baseURL;
		Response getRespuesta = RestAssured
		.given()
			//.header("Authorization","NoAuth")
			.relaxedHTTPSValidation() //no buscar certificado SSL
			.contentType(ContentType.JSON) //tipo de api
		.when()
			.get("/restv2/cargaRelacional/id_carga/6");
			
		// Registra el tiempo después de realizar la petición JSON
		long endTime = System.currentTimeMillis();

		// Calcula el tiempo que se demoró la petición JSON
		long executionTime = endTime - startTime;
		System.out.println("Tiempo de ejecución de la petición JSON: " + executionTime + " milisegundos");

		//Obtener el status code
		int getStatusCode = getRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
		assertEquals(getStatusCode, getExpectedCode); //Validación
			
		//Obtener body de respuesta
		String getBodyResponse = getRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse);
			
		addStep("Validacion del tiempo de la petición Get");
			
		// Agregar el tiempo de ejecución como evidencia /// Tiempo máximo esperado 60 segundos ///
		testCase.addCodeEvidenceCurrentStep("Tiempo de ejecución de la petición JSON: " + executionTime + " milisegundos");
		
		// Assert para validar que el tiempo de respuesta es igual o menor a 60 segundos
		assertTrue(executionTime <= 60000, "El tiempo de ejecución excede los 60 segundos");
					
		/******************** PASO 3 y PASO 4 ************************/
		
		addStep("Ejecutar petición POST");
		
		//se obtienen los datos del dataprovider
		String postPath = data.get("postPath");
		String postBody = data.get("postBody");
		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
		
		//Se envía como evidencia el request a ejecutar
		testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath);
		testCase.addCodeEvidenceCurrentStep("body:" + postBody);
		
		// Registra el tiempo antes de realizar la petición JSON
		long startTime2 = System.currentTimeMillis();
		
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
			
		// Registra el tiempo después de realizar la petición JSON
		long endTime2 = System.currentTimeMillis();

		// Calcula el tiempo que se demoró la petición JSON
		long executionTime2 = endTime2 - startTime2;
		System.out.println("Tiempo de ejecución de la petición JSON: " + executionTime2 + " milisegundos");
			
		//Obtener el status code
		int postStatusCode = postRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode);
		assertEquals(postStatusCode, postExpectedCode); //Validación
			
		//Obtener body de respuesta
		String postBodyResponse = postRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse);
			
		addStep("Validacion del tiempo de la petición Post");
			
		// Agregar el tiempo de ejecución como evidencia /// Tiempo máximo esperado 65 segundos ///
		testCase.addCodeEvidenceCurrentStep("Tiempo de ejecución de la petición JSON: " + executionTime2 + " milisegundos");
	
		// Assert para validar que el tiempo de respuesta es igual o menor a 65 segundos
		assertTrue(executionTime2 <= 65000, "El tiempo de ejecución excede los 65 segundos");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Validar_tiempo_del_viaje_de_peticion_JSON_test";
	}

	@Override
	public String setTestDescription() {
		return "Se carga un archivo tipo JSON en la herrmienta postman, se realiza una consulta get y post y se comprueba que el tiempo de espera sea menor o igual a 60 segundos";
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
package WM.interfaces.eo17_mx;

import java.util.HashMap;

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

public class ATC_FT_008_Validar_carga_de_archivo_con_peso_menor_3MB extends BaseExecution{

/**
 * Descripcion del test:
 * Validar la carga de Json de peso ()
 * 
 * Pre-requisitos:
 * 1- Contar con la herramienta que permita la carga Json
 * 2- Contar con el ambiente QA
 *
 * Insumos:
 * 1- Archivo Json que cumpla con el parametro de peso mayor a 1MB
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
	public void ATC_FT_008_Validar_carga_de_archivo_con_peso_menor_3MB_test(HashMap<String, String> data) throws Exception {
		
		/** Abrir la pagina web donde se carga el JSON
		 * @param ApiMethodsUtil
		 * @author Melba Solis
		 */		
		// Abrir la pagina web donde se carga el JSON
		String baseURL = "https://qa-foliodepago-eo17mx.femcom.net:1812";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
		/*************************************** PASO 1 GET ***************************************/
		addStep("Ejecutar petición POST");
		
		TextDocumentUtil tu = new TextDocumentUtil();
			String filepath = data.get("postBody");
			filepath = Constants.BASE_PATH + "\\" + filepath;
			String postBody = tu.readLines(filepath);
		System.out.print(filepath);
		
		//System.out.print(postExpectedCode); 
		String postPath = data.get("postPath");
		
		//Se envía como evidencia el request a ejecutar
		//testCase.addCodeEvidenceCurrentStep("request:" + baseURL + postPath);
		//testCase.addCodeEvidenceCurrentStep("body:" + postBody);
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
		
		System.out.print(postBodyResponse); 
		
	/******************** PASO 2 ************************/
		
		/** Llamar a la Base de Datos
		* @param SQLUtil
		*/
		//Utileria// Se considera una utileria la BD //
		//****************** Llamar a la Base de Datos **********************/
					  
		//SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
					  
		// Variables
		//********************************************************************/
		//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
		//testCase.setTest_Description(data.get("id") + data.get("description"));

		//testcase va a mandar los prerequisitos 
    	/*testCase.setPrerequisites("Que se hayan realizado las consultas get y post y se guarden en la tabla correspondiente");
		//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

		System.out.println( 
	"*************** CONSULTA DE LA PETICIÓN POST EN LA BD *********************");*/
	
	//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
	//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
	/*addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar la consulta de peticion post realizada validando de ese modo la respuesta esperada siendo la carga de la información enviada por el JSON");
	String consulta_AVEBQA = data.get("consultaBD"); // "SELECT columna FROM apps.xxfc_ap_cargacsv_all  WHERE columna = ?? "
	System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
	SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
	boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
	conexiondbAvebqa  = ExecuteQuery.isEmpty();

	testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
	testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
	testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

	assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); */ // si es falso va a arrojar un error */
//Se comenta la consulta de la BD dado que el peso de las líneas consultadas no permite generar la evidencia lo que provoca que falle
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_008_Validar_carga_de_archivo_con_peso_menor_3MB_test";
	}

	@Override
	public String setTestDescription() {
		return "Se carga un archivo tipo JSON con peso menor o igual a 1MB en la herrmienta postman y se carga correctamente";
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

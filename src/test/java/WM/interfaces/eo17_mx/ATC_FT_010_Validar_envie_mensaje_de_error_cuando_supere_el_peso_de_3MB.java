package WM.interfaces.eo17_mx;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import config.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.TextDocumentUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

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

public class ATC_FT_010_Validar_envie_mensaje_de_error_cuando_supere_el_peso_de_3MB extends BaseExecution {
	
/**
 * Descripcion del test:
 * Validar que al cargar un Json con peso mayor a 3MB no permita la carga y salga el mensaje correspondiente 
 * 
 * Pre-requisitos:
 * 1- Contar con la herramienta que permita la carga Json
 * 2- Contar con el ambiente QA
 *
 * Insumos:
 * 1- Archivo Json que exceda el peso permitido "3MB"
 */
	/**
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
	public void ATC_FT_010_Validar_envie_mensaje_de_error_cuando_supere_el_peso_de_3MB_test(HashMap<String, String> data) throws Exception {
	
		/**
		 * Abrir la pagina web donde se carga el JSON
	     * @param ApiMethodsUtil
	     * @author Melba Solis
		 */
		// Abrir la pagina web donde se carga el JSON
		String baseURL = "https://qa-foliodepago-eo17mx.femcom.net:1812";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		
		/************************ PASO 1 POST  *****************************/
	addStep("Ejecutar petición POST");

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
	
	// Ejecutar el request*
	// Response postRespuesta = api.postRequestMethod(postPath, postBody);

	// Sección que se coloca para evitar que te pida permisos/token
	RestAssured.baseURI = baseURL;
	RestAssured.baseURI = baseURL;
	Response postRespuesta = RestAssured.given()
			// .header("Authorization","NoAuth")
			.relaxedHTTPSValidation() // no buscar certificado SSL
			.contentType(ContentType.JSON) // tipo de api
			.body(postBody).when().post("/restv2/cargaRelacional");

	// Obtener el status code
	int postStatusCode = postRespuesta.getStatusCode();
	testCase.addCodeEvidenceCurrentStep("statusCode: " + postStatusCode);
	//assertEquals(postStatusCode, postExpectedCode); // Validación

	// Obtener body de respuesta
	String postBodyResponse = postRespuesta.asPrettyString();
	testCase.addCodeEvidenceCurrentStep("bodyResponse:" + postBodyResponse);
	
	/*************************** PASO 2 ********************************/
	addStep("Mensaje de error:");
	//Validación de json*
	JSONObject postJson = new JSONObject(postBodyResponse);
	JSONObject campoResponse = postJson.getJSONObject("response"); 
	String wmMessageString = campoResponse.getString("wm_message");
	testCase.addTextEvidenceCurrentStep("MENSAJE:" + wmMessageString);
	assertEquals(wmMessageString, "Error el archivo de carga relacional en formato JSON supera 3.00 MB");

	/*************************** PASO 3 ********************************/
	/**
	 * Abrir la pagina web donde se carga el JSON
	 * @param SQLUtil
	 */
	// Utileria// Se considera una utileria la BD //
	// ****************** Llamar a la Base de Datos **********************/

	SQLUtil DBFCWMLQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA3, GlobalVariables.DB_USER_FCWMLQA3,GlobalVariables.DB_PASSWORD_FCWMLQA3);

	// Variables
	// testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
	// testCase.setTest_Description(data.get("id") + data.get("description"));

	// testcase va a mandar los prerequisitos
	testCase.setPrerequisites("Que se haya realizado una consulta con error y se guarden en la tabla correspondiente");
	// Para obtener el id y la descripcion necesito tener primero la BD (la tabla) // si falla va a aparecer el mensaje que coloque y si no muestra nada, significa // que esta bien y arrojara el id y la descripcion

	System.out.println(

	"*************** PASOS 4 ******************************************");
	// add = añadir // step = paso //STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde //
	// añadir un paso // El paso cosiste en que la cadena consultara la tabla AVEBQA
	// y seleccionara al dueño nombre de la tabla desde todas las tablas
	addStep("Ingresar a la BD de FCMLQA y Realizar la siguiente consulta para validar el mensaje de error: Error el archivo de carga relacional en formato JSON supera 1MB");
	String consulta_FCWMLQA = data.get("consulta_mensaje_error"); 
	System.out.println("Tu consulta es: " + consulta_FCWMLQA); // IMPRIMIR LA CONSULTA DE LA BD + RESULTADO

	SQLResult ExecuteQuery = executeQuery(DBFCWMLQA, consulta_FCWMLQA); // execute = ejecutar Query = consulta 	// (DBFCMLQA, consulta_FCMLQA) ejecutar consulta de la BD que es igual al escript // validar tabla CFDAVALIDAR (va a buscar la tabla)

	boolean conexiondbFwmlqa = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
	conexiondbFwmlqa = ExecuteQuery.isEmpty();

	testCase.addTextEvidenceCurrentStep("Conexion: FCWMLQA ");
	testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA);
	testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

	assertFalse(conexiondbFwmlqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_Validar_envie_mensaje_de_error_cuando_supere_el_peso_de_3MB_test";
	}

	@Override
	public String setTestDescription() {
		return "Se carga un archivo tipo JSON con peso mayor a 3MB en la herrmienta postman, no se carga correctamente y envia un mensaje de error";
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
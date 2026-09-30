package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import modelo.BaseExecution;
import WEB.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import static org.junit.Assert.assertTrue;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;


/**
* DESCRIPCIÓN: Validar que el Portal de Workflow tenga la conexión a las tablas staging que se crearán en EBS, para recibir la información necesaria para crear la Factura y la informacion se guarde correctamente
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_001_8_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS
* 
* PREREQUISITOS:
* 	"1.- Contar con acceso a la BD de EBS"
*	"2.- Tener Insumos en el bus de Workflow en espera de la ejecucion del primer Microservicio"
*	"3.- Ejecutar el script un momento antes de que el microservicio entre en accion(cada 5 o 15 minutos, depende de la configuracion)";	
* 
* INSUMOS:
* 	-Se requiere tener insumos en workflow
* 
* @author bernardo.mejia@grupotimex.onmicrosoft.com
* @date 08/Mayo/2025
*/

public class ATC_FT_008_ValActRegDuplique extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	
	public void ATC_FT_008_ValActRegDuplique_test(HashMap<String, String> data)
			throws Exception {
		
		/**
		 * Validar que cuando hagamos alguna actualización a cualquiera de los campos de la TABLA solo se haga la ACTUALIZACION a ese campo. No debe de haber más de 2 registros con ese ID TIENDA en la BD de ArcGis.
		 * @param RestAssured
		 * @author Bernardo Alonso Mejia Ortiz
		 */
 
	       /***************************** PASO 1: Obtener el token ****************************/
			addStep("Ejecutar petición POST para obtener el token");
		 
				//String baseTokenURL = data.get("postPath");
				int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));

				// Se llama al OM
				Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));
				
				// Validar status
				int getTokenStatusCode = getTokenResponse.getStatusCode();
				testCase.addCodeEvidenceCurrentStep("statusCode: " + getTokenStatusCode);
				System.out.println("Status token: " + getTokenStatusCode);
				assertEquals(getTokenStatusCode, getExpectedCode, "Error al obtener token");
		 
				// Ver respuesta completa
				String getTokenBodyResponse = getTokenResponse.asPrettyString();
				testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getTokenBodyResponse);
				System.out.println("Respuesta completa token: " + getTokenBodyResponse);
		 
				// Extraemos el access_token del JSON
				JSONObject getJson = new JSONObject(getTokenBodyResponse);
				String token = getJson.optString("access_token", null);
				assertNotNull(token, "No se encontró access_token en la respuesta");
				testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
				System.out.println("Token extraído: " + token);
				
				/***************************** PASO 2: Obtener datos en petición GET ****************************/
				addStep("Ejecutar petición GET retrieve para validar que se actualizó ESTATUS_GRAL en la capa correcta");
				System.out.println("=== Ejecutando petición GET con reintentos ===");
		 
				// Parametrización de URL
				//String baseGetUrl = data.get("getPath");
		 
				// Variables de control para retry
				int maxReintentos = 3;
				
				// Ejecutamos el GET usando el OM
				Response getResponse = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token, getJson, data, maxReintentos);
				System.out.println("Token usado en GET: " + token);
				System.out.println("ApiKey usada en GET: " + data.get("apiKey"));
				int status = getResponse.getStatusCode();			
				
				// Validación final después de reintentos
				testCase.addCodeEvidenceCurrentStep("statusCode: " + status);
				System.out.println("Status: " + status);
				int getStatusCode = getResponse.getStatusCode();
				assertEquals(status, getStatusCode, "El GET no devolvió 200 después de " + maxReintentos + " intentos");
		 
				// Guardar y mostrar body completo
				String getBodyResponse = getResponse.asPrettyString();
				testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getBodyResponse);
				System.out.println("Respuesta: " + getBodyResponse);
		 
				// Parsear JSON y validar datos
				JSONObject getJsonResponse = new JSONObject(getBodyResponse);
				JSONArray features = getJsonResponse.optJSONArray("features");
				assertNotNull(features, "El JSON no contiene 'features'");
				assertTrue("El arreglo 'features' está vacío",features.length() > 0 );

				JSONObject firstFeature = features.getJSONObject(0);
				JSONObject attributes = firstFeature.optJSONObject("attributes");
				assertNotNull(attributes, "El objeto 'attributes' no existe en el JSON");
				
				/***************** PASO 3: Realizar la coneción a la BD de ArcGis ****************/
				/* ***************** Llamar a la Base de Datos 01 **********************/
					  
				SQLUtil dbFcgisqn = new SQLUtil(GlobalVariables.DB_HOST_FCGISQN, GlobalVariables.DB_USER_FCGISQN, GlobalVariables.DB_PASSWORD_FCGISQN);
				System.out.println("*************** PASOS 1 y 2 *****************");
				addStep("Ingresar a la BD de ARCGIS y Realizar la siguiente consulta para validar la existencia de la tabla XXGISOWNER.EXP_PUNTOS_POTENCIALES_V2");
				String consulta_FCGISQN = data.get("ConsultaBDArcgis");
				System.out.println("Tu consulta es: " + consulta_FCGISQN); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery = executeQuery(dbFcgisqn, consulta_FCGISQN); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn  = ExecuteQuery.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
				assertFalse(conexiondbFcgisqn, "La conexion no fue exitosa"); // si es falso va a arrojar un error 

				/****************** Llamar a la Base de Datos 02 **********************/
				System.out.println("*************** PASOS 3 *****************");
				consulta_FCGISQN = data.get("ConsultaBDArcgis1");			
				System.out.println("Tu consulta es: " + consulta_FCGISQN); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery1 = executeQuery(dbFcgisqn, consulta_FCGISQN); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn1  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn1  = ExecuteQuery1.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery1);
				assertFalse(conexiondbFcgisqn1, "La conexion no fue exitosa"); // si es falso va a arrojar un error
				
				/****************** Llamar a la Base de Datos 03 **********************/
				System.out.println("*************** PASOS 4 *****************");
				 consulta_FCGISQN = data.get("ConsultaBDArcgis2");
				System.out.println("Tu consulta es: " + consulta_FCGISQN); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery2 = executeQuery(dbFcgisqn, consulta_FCGISQN); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn2  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn2  = ExecuteQuery2.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				assertFalse(conexiondbFcgisqn2, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
				
				/****************** Llamar a la Base de Datos 04 **********************/
				System.out.println("*************** PASOS 5 *****************");
				String consulta_FCGISQN3 = data.get("ConsultaBDArcgis3");
				System.out.println("Tu consulta es: " + consulta_FCGISQN3); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery3 = executeQuery(dbFcgisqn, consulta_FCGISQN3); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn3  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn3  = ExecuteQuery3.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
				assertFalse(conexiondbFcgisqn3, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
				
				/****************** Llamar a la Base de Datos 05 **********************/
				System.out.println("*************** PASOS 6 *****************");
				String consulta_FCGISQN4 = data.get("ConsultaBDArcgis4");
				System.out.println("Tu consulta es: " + consulta_FCGISQN4); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery4 = executeQuery(dbFcgisqn, consulta_FCGISQN4); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn4  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn4  = ExecuteQuery4.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
				assertFalse(conexiondbFcgisqn4, "La conexion no fue exitosa"); // si es falso va a arrojar un error 

				/****************** Llamar a la Base de Datos 06 **********************/
				System.out.println("*************** PASOS 7 *****************");
				String consulta_FCGISQN5 = data.get("ConsultaBDArcgis5");
				System.out.println("Tu consulta es: " + consulta_FCGISQN5); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery6 = executeQuery(dbFcgisqn, consulta_FCGISQN5); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn6  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn6  = ExecuteQuery6.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
				assertFalse(conexiondbFcgisqn6, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
				
				/****************** Llamar a la Base de Datos 07 **********************/
				System.out.println("*************** PASOS 8 *****************");
				addStep("Ingresar a la BD de ARCGIS y Realizar la siguiente consulta para validar la existencia de la tabla XXGISOWNER.EXP_PUNTOS_POTENCIALES_V2");
				String consulta_FCGISQN7 = data.get("ConsultaBDArcgis7");
				System.out.println("Tu consulta es: " + consulta_FCGISQN7); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
				SQLResult ExecuteQuery7 = executeQuery(dbFcgisqn, consulta_FCGISQN7); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
				boolean conexiondbFcgisqn7  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
				conexiondbFcgisqn7  = ExecuteQuery7.isEmpty();
				testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
				assertFalse(conexiondbFcgisqn7, "La conexion no fue exitosa"); // si es falso va a arrojar un error 

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_ValActRegDuplique";
	}

	@Override
	public String setTestDescription() {
		return "Validar que cuando hagamos alguna actualización a cualquiera de los campos de la TABLA solo se haga la ACTUALIZACION a ese campo. No debe de haber más de 2 registros con ese ID TIENDA en la BD de ArcGis.";
	}

	@Override
	public String setTestDesigner() {
		return "bernardo.mejia@grupotimex.onmicrosoft.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Abrir Postman\r\n"
		+ "		2.- Ejecutar collection\r\n"
		+ "		3.- Conectarse a ArcGits. \r\n"				
		+ "		4.- Ejecutar query de consulta\n";		
	}

	@Override
	public void beforeTest() {

	}
}

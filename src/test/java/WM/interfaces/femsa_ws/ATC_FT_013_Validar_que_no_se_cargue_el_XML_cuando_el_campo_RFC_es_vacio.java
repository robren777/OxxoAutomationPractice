package WM.interfaces.femsa_ws;

	import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.FDP2Soap;
import WM.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

	public class ATC_FT_013_Validar_que_no_se_cargue_el_XML_cuando_el_campo_RFC_es_vacio extends BaseExecution{
		
		/**
		* Descripcion del test:
		 * Validar que no se cargue el XML recortado en la tabla XXFC.XXFC_CFDAVALIDAR de la BD AVEBQA 
		 * cuando el campo RFC es omitido. 
		 * 
		 * Pre-requisitos:
		 * 1- Acceder a la BD AVEBQA
		 *
		 * Insumos:
		 * 1- Tener acceso a la Base de Datos AVEBQA 
		 */	
		
		private FDP2Soap FDP2WM;
		
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT013_Validar_que_no_se_cargue_el_XML_cuando_el_campo_RFC_es_vacio_test (HashMap<String, String> data) throws Exception {
			
		/** Llamar a la Base de Datos
		 * @param RestAssured
		 * @param SQLUtil
		 * @author Melba Solís
		 */	
		
		// Instanciar FDP2Soap y llamar al método assertSoapResponse
	    FDP2WM = new FDP2Soap(testCase);
	        
	        /************************** PASO 1 CARGA XML **************************/
			//Se carga XML 
			addStep("Carga de XML");
							
			//se obtienen los datos del dataprovider
			String baseUrl = data.get("baseUrl");
			String xmlBody = data.get("xmlBody");
			int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
				
			System.out.print(postExpectedCode); 
					
			//Se envía como evidencia el request a ejecutar
			testCase.addCodeEvidenceCurrentStep("request:" + baseUrl);
			testCase.addCodeEvidenceCurrentStep("bodyXML:" + xmlBody);   
			
			//Response respuesta; 
		
			Response response = RestAssured.given()
	                   .baseUri(baseUrl)
	                   .relaxedHTTPSValidation()
	                   .contentType(ContentType.XML)
	                   .body(xmlBody)
	                   .when()
	                   .post();
	       
	       System.out.println(response.getBody().asString());
	       
	       addStep("Estatus y mensajes de respuesta arrojados");
	       
	       //response.then().statusCode(200);
	       assertEquals(response.getStatusCode(), 200); //Validación
	       
	       testCase.addCodeEvidenceCurrentStep("bodyEstatusResponse:" + response.getStatusCode());
	       testCase.addCodeEvidenceCurrentStep("bodyResponse:" + response.getBody().asString());
	       // Validar el contenido de la respuesta SOAP
	       
	       String responseBody = response.getBody().asString();
	       assertSoapResponse(responseBody);   
	       
	       addStep("Validar mensaje arrojado con responseMessage y returnCode");
	       FDP2WM.assertSoapResponse2(responseBody);
	       
	       //Utileria// Se considera una utileria la BD //
	       SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
	      	  
	       System.out.println(
	      				
	      "*************** PASOS 4 **********************");
	      		
	      		//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
	      		//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
	      		addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar la carga del xml, que no se haya insertado en la tabla XXFC.XXFC_CFDAVALIDAR");
	      		String consulta_AVEBQA = data.get("consulta"); // ""
	      		System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
	      		
	      		SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
	      		
	      		boolean conexiondbAvebqa  = false; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
	      		conexiondbAvebqa  = ExecuteQuery.isEmpty();

	      		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
	      		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
	      		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

	      		assertTrue(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error
	    
	}

	  	private void assertSoapResponse(String responseBody) {
		// TODO Auto-generated method stub
		}
		@Override
		public String setPrerequisites() {
			// TODO Auto-generated method stub
			return "Contar con la herramienta que permita la carga del XML, contar con el ambiente QA";
		}
		@Override
		public String setTestDescription() {
			// TODO Auto-generated method stub
			return "Se realiza la carga de XML recortado con el campo RFC con valores vacios y se valida que no se haya cargado a la tabla xxfc_cfdavalidar en la BD  AVEBQA.";
		}
		@Override
		public String setTestDesigner() {
			// TODO Auto-generated method stub
			return "Melba Solís Castillo";
		}
		@Override
		public String setTestFullName() {
			// TODO Auto-generated method stub
			return "ATC_FT013_Validar_que_no_se_cargue_el_XML_cuando_el_campo_RFC_es_vacio_test";
		}
		@Override
		public String setTestInstanceID() {
			// TODO Auto-generated method stub
			return null;
		} 
		 @Override
			public void beforeTest() {
				// TODO Auto-generated method stub
			}
	  }

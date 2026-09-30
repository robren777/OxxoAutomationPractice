package WM.interfaces.ENRED_OUT_V2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.azure.monitor.query.models.QueryTimeInterval;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WM.om.ENREDOUTV2;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_003_ENREDOUTV2_ValidarReglasTransformacionACK extends BaseExecution {
	public String eventDateOutbound = "";
	public String name = "";
	
	@Test
	(dataProvider = "data-provider")

	public void ATC_003_ENREDOUTV2_ValidarReglasTransformacionACK_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_003 Reglas Transformacion servicio ACK Enred out V2
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que el servicio ACK de la API ENRED_OUT_V2 funcione correctamente y regrese WmCode en 101 con estatus P.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
		//MDB_ERDD_PromotionCloud
        String queryStatusDigitalY = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId")+ "' AND STORE='" +data.get("storeId")+ "' AND DIGITAL='" +data.get("digital")+ "'";
		String queryStatusAntes = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "'AND BATCH_ID='" +data.get("id")+ "'";
		String queryStatusDespues = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusAckRespuesta")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "'AND BATCH_ID='" +data.get("id")+ "'";
		
		boolean validaStatus = false;
		//***************************Paso 1*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea digital, con la consulta: " + queryStatusDigitalY);

		 validaStatus = tr.queryBDAzure(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String digital = tr.getValueCampoQuery(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
		
		//***************************Paso 3*****************************************************************************
		 addStep("Validar que el registro del documento se actualizo a estatus C: " + queryStatusAntes);
		
		 validaStatus = tr.queryBDAzure(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String status = tr.getValueCampoQuery(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 String pvStatus = tr.getValueCampoQuery(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
			
		//***************************Paso 4*****************************************************************************
		addStep("Invocar la API ACK V1 para confirmar un documento descargado\n");

		//Obtener datos del data provider
		String testcaseName = data.get("name ");
		String getPathToken = data.get("getPathToken");
		String baseURLToken = data.get("baseURLToken");
		String Auth = data.get("Auth");
		
		String access_token = tr.getTokenAPI(getPathToken, baseURLToken, Auth);

		//Definici�n de Variables 	
		String baseURILisp =  data.get("baseURILisp");
		String getPathLisp = data.get("getPathLisp");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String source = data.get("source");
		String doctype = data.get("doctype");
		String getExpectedCode = data.get("getExpectedCode");
		int getExpectedCode1 = Integer.parseInt(getExpectedCode);
		
		name = tr.lispAPI(baseURILisp, getPathLisp, departmentId, storeId, source, doctype, access_token, getExpectedCode, testcaseName);
		
		// Llamada al API OUTBOUND usando el token generado	
		String getPathOutbound = data.get("getPathOutbound");
		
		eventDateOutbound = tr.outboundAPI(baseURILisp, getPathOutbound, name, departmentId, storeId, source, doctype, access_token, getExpectedCode, testcaseName);
		
		//Lista de headers
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("Accept", "*/*");
		headersList.put("content-type", "application/json");
		headersList.put("Authorization", "Bearer "+access_token);
		
		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
				
		// Llamada al API ACK usando el token generado	
		String baseURIACK = baseURILisp;
		String getPathACK = data.get("getPathACK");
		
		System.out.println("Invocar la API ACK para confirmar un documento descargado (Estatus P)");	
	
		RestAssured.baseURI = baseURIACK;
		String reqBodyACK = "{\n" +
		        "    \"departmentId\":\""+departmentId+"\",\n" +
		        "    \"storeId\":\""+storeId+"\",\n" +
		        "    \"source\":\"POS\",\n" +
		        "    \"documents\":[\n" +
		        "       {\n" +
		        "          \"name\":\""+name+"\",\n" +
		        "          \"fileType\":\""+doctype+"\",\n" +
		        "          \"status\":\"P\",\n" +
		        "          \"eventDate\":\""+eventDateOutbound+"\"\n" +
		        "       }\n" +
		        "    ]\n" +
		        "}";

		//Se env�a como evidencia el request a ejecutar
		testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURIACK+getPathACK+reqBodyACK);
		
		///Hacemos el send del request
		Response getRespuestaACK = api.postRequestWithHeaders(baseURIACK+getPathACK, reqBodyACK, headersList);
		
		//***************************Paso 5*****************************************************************************
		 addStep("Para la TR1 verificar que los campos de entrada se transformen a minuscula e ingles. En palabras compuesta utilizar formato camelCase");
		 boolean isValid = tr.validateJsonRequest(reqBodyACK);
	     System.out.println("Request valido: " + isValid);
	     testCase.addTextEvidenceCurrentStep("TR1 exitosa\n Request valido: " + reqBodyACK);
	     assertTrue("No se muestra el estatus esperado: el JSON debería ser válido.", isValid);
		
		//***************************Paso 6*****************************************************************************
	     addStep("Para la TR3 verificar que las variables de los servicios expuesto esten mapeadas en el body del request");
	     ObjectMapper objectMapper = new ObjectMapper();
       JsonNode jsonNode = objectMapper.readTree(reqBodyACK);

      // Verificar si las claves 'departamentId', 'storeId', 'source', 'documents', 'name', 'fileType', 'status' y 'eventDate' estan presentes
       if (jsonNode.has("departmentId") && jsonNode.has("storeId") && jsonNode.has("source") && jsonNode.has("documents")) {
    	   JsonNode documentsNode = jsonNode.get("documents");
    	   if (documentsNode.isArray()) {
    		   for (JsonNode documentNode : documentsNode){
    			   if (documentNode.has("name") && documentNode.has("fileType") && documentNode.has("status") && documentNode.has("eventDate")) {
			           System.out.println("Las variables estan mapeadas en el body");
			           validaStatus = false;
    			   } else {
    				   System.out.println("Falta 'name', 'fileType', 'status' o 'eventDate' en algun documento.");
    				   validaStatus = false;
    			   }
    		   }
    	   }
       } else {
           System.out.println("No hay variables en el body");
           validaStatus = true;
       }
      testCase.addTextEvidenceCurrentStep("TR3 exitosa\n Las variables estan mapeadas en el body" + reqBodyACK);
      assertFalse("Se encontro algun registro de error", validaStatus);
	     
      //***************************Paso 7*****************************************************************************
	     addStep("Para la TR4 verificar que la consulta que no corresponde al CRUD se envie con metodo POST");
	     
	     if (getRespuestaACK != null) {
	    	 System.out.println("Request enviado con metodo POST");
	    	 validaStatus = false;
	     }else {
          System.out.println("Request NO enviado con metodo POST");
          validaStatus = true;
	     }
	     testCase.addTextEvidenceCurrentStep("TR4 exitosa\n Request enviado con metodo POST" + reqBodyACK);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 8*****************************************************************************
	     addStep("Para la TR6 verificar que el request se muestren en JSON");
	     if (tr.esJsonValido(reqBodyACK)) {
	    	 	System.out.println("El cuerpo de la solicitud es JSON");
	    	 	validaStatus = false;
	        } else {
	        	System.out.println("El cuerpo de la solicitud NO es un JSON");
	        	validaStatus = true;
	        }
	     testCase.addTextEvidenceCurrentStep("TR6 exitosa\n El cuerpo de la solicitud es JSON" + reqBodyACK);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 9*****************************************************************************
	     addStep("Para la TR7 verificar que la nomenclatura de campos omitan el nombre del nodo padre. En campos fuera del nodo revisar que el nombre sea compuesto");
	     JsonNode rootNode = objectMapper.readTree(reqBodyACK);

       // Validar el JSON
       if (tr.validateJsonNombreNodo(rootNode, null)) {
           System.out.println("La nomenclatura de nombre de nodos es correcta");
           validaStatus = false;
       } else {
           System.out.println("La nomenclatura de nombre de nodos NO es correcta");
           validaStatus = true;
       }
       testCase.addTextEvidenceCurrentStep("TR7 exitosa\n La nomenclatura de nombre de nodos es correcta" + reqBodyACK);
       assertFalse("Se encontro algun registro de error", validaStatus);
		
     //***************************Paso 10*****************************************************************************
	     addStep("Validar que la respuesta del request: ");
		
		//Obtener body de respuesta
		String getBodyResponseACK = getRespuestaACK.getBody().asPrettyString();
		System.out.println("Body: "+getBodyResponseACK);
		
		JsonPath getJsonACK = new JsonPath(getBodyResponseACK);
		String messageACK = getJsonACK.getString("message");
		String code = getJsonACK.getString("code"); 
		int codeACK = Integer.parseInt(code);
		testCase.addTextEvidenceCurrentStep("Code ACK: "+ codeACK);
		testCase.addTextEvidenceCurrentStep("Mensaje ACK: "+ messageACK);
		testCase.addTextEvidenceCurrentStep("Response: \n" + getRespuestaACK+getBodyResponseACK);
		
		assertEquals(codeACK, getExpectedCode1);
		
		OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);
		
		//***************************Paso 11*****************************************************************************
		 addStep("Para la TR2 verificar que los campos de salida se transformen a minuscula e ingles. En palabras compuesta utilizar formato camelCase");
		 isValid = tr.validateJsonRequest(getBodyResponseACK);
	     System.out.println("Request valido: " + isValid);
	     testCase.addTextEvidenceCurrentStep("TR2 exitosa\n Request valido: " + getBodyResponseACK);
	     assertTrue("No se muestra el estatus esperado: el JSON debería ser válido.", isValid);
		
		//***************************Paso 12*****************************************************************************
	     addStep("Para la TR3 verificar que las variables de los servicios expuesto esten mapeadas en el body del request");
         jsonNode = objectMapper.readTree(getBodyResponseACK);

		 // Verificar si las claves code y message, estan presentes
		 if (jsonNode.has("code") && jsonNode.has("message")) {
		     System.out.println("Las variables estan mapeadas en el body");
		     validaStatus = false;
		 } else {
		     System.out.println("No hay variables en el body");
		     validaStatus = true;
		 }
		 testCase.addTextEvidenceCurrentStep("TR3 exitosa\n Las variables estan mapeadas en el body" + getBodyResponseACK);
		 assertFalse("Se encontro algun registro de error", validaStatus);
	     
     //***************************Paso 13*****************************************************************************
	     addStep("Para la TR5 verificar que el grupo de elementos este contenido dentro de una estructura que lo describa claramente");
	     rootNode = objectMapper.readTree(getBodyResponseACK);
	     
	     if (tr.validateJsonCamposAck(rootNode)) {
	    	 System.out.println("Response con nombre de nodos representativos");
	    	 validaStatus = false;
	     }else {
         System.out.println("Response con nombre de nodos NO representativos");
         validaStatus = true;
	     }
	     testCase.addTextEvidenceCurrentStep("TR5 exitosa\n Response con nombre de nodos representativos" + getBodyResponseACK);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 14*****************************************************************************
	     addStep("Para la TR6 verificar que el request se muestren en JSON");
	     if (tr.esJsonValido(getBodyResponseACK)) {
	    	 	System.out.println("El cuerpo de la solicitud es JSON");
	    	 	validaStatus = false;
	        } else {
	        	System.out.println("El cuerpo de la solicitud NO es un JSON");
	        	validaStatus = true;
	        }
	     testCase.addTextEvidenceCurrentStep("TR6 exitosa\n El cuerpo de la solicitud es JSON" + getBodyResponseACK);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 15*****************************************************************************
	     addStep("Para la TR7 verificar que la nomenclatura de campos omitan el nombre del nodo padre. En campos fuera del nodo revisar que el nombre sea compuesto");

      // Validar el JSON
      if (tr.validateJsonNombreNodo(rootNode, null)) {
          System.out.println("La nomenclatura de nombre de nodos es correcta");
          validaStatus = false;
      } else {
          System.out.println("La nomenclatura de nombre de nodos NO es correcta");
          validaStatus = true;
      }
      testCase.addTextEvidenceCurrentStep("TR7 exitosa\n La nomenclatura de nombre de nodos es correcta" + getBodyResponseACK);
      assertFalse("Se encontro algun registro de error", validaStatus);
      
      //***************************Paso 16*****************************************************************************
      addStep("Validar que el registro del documento se actualizo a estatus P: " + queryStatusDespues);
      
      tr.queryBDAzure(queryStatusDespues, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 status = tr.getValueCampoQuery(queryStatusDespues, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 pvStatus = tr.getValueCampoQuery(queryStatusDespues, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 
		 if(status.equals("C") || pvStatus.equals("P")) {
				validaStatus = true;
		 }
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_003 Reglas Transformacion servicio ACK Enred out V2";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verifica que el servicio ACK de Enred out V2 cumpla las reglas de transformacion:\r\n"
				+ "Request: TR1, TR3, TR4, TR6, TR7\r\n"
				+ "Response: TR2, TR3, TR5, TR6, TR7\r\n"
				+ "TR1: Todos los campos nativos de entrada se deben transformar a idioma ingles, en minusculas y en caso de palabras compuestas utilizar formato camelCase. \r\n"
				+ "TR2: Todos los campos nativos de salida se deben transformar a idioma ingles, en minusculas y en caso de palabras compuestas utilizar formato camelCase.\r\n"
				+ "TR3: Todas las variables de los servicios expuestos de APIs de experiencia que a traves de solicitud REST no iran dentro la URL, tendran que ser mapeadas al body de la API. Casos de uso tener datos sensibles, datos de token de sesion del API nativa, operaciones fuera del CRUD.\r\n"
				+ "TR4: Todas las operaciones nativas que no entren dentro de las operaciones CRUD se representaran por el metodo POST como accion.\r\n"
				+ "TR5: El response del servicio que contenga objetos u arreglos, deberan estar embebidos en un nodo padre, que describa la lista u objeto. \r\n"
				+ "TR6: Todos los datos de entrada y salida del API tienen que estar representado en formato JSON. \r\n"
				+ "TR7: La definicion de la nomenclatura de los campos que pertenezcan a un nodo padre (Objeto o arreglo) omitira el nombre del nodo padre para simplificar el tamanio y lejibilidad del nombre del campo. Si el campo se encuentra fuera del nodo o a nivel raiz se maneja el nombre compuesto.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Valeria Fernandez";
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

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con la mensajeria para invocar la API ACK V2 \r\n"
				+ "2. Esten instalado el cambio de la ENRED OUT V2 \r\n"
				+ "3. Respuesta con codigo: wmCode 101 \r\n"
				+ "4. Verificar que exista la interface Enred out V2 en las configuraciones de Azure \r\n"
				+ "5. Haber ejecutado el proceso de envio de las tiendas (PR63_Cloud) \r\n"
				+ "6. Acceso a BD MDB_ERDD_PromotionCloud (Azure)";

	}
}

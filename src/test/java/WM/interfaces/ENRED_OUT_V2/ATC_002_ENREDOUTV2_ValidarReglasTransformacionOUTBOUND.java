package WM.interfaces.ENRED_OUT_V2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

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

public class ATC_002_ENREDOUTV2_ValidarReglasTransformacionOUTBOUND extends BaseExecution {
	public String eventDateOutbound = "";
	public String name = "";
	
	@Test
	(dataProvider = "data-provider")

	public void ATC_002_ENREDOUTV2_ValidarReglasTransformacionOUTBOUND_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_002 Reglas Transformacion servicio OUTBOUND Enred out V2
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que el servicio OUTBOUND de la API ENRED_OUT_V2 funcione correctamente y regrese WmCode en 101.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
      //MDB_ERDD_PromotionCloud
        String queryStatusDigitalY = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId")+ "' AND STORE='" +data.get("storeId")+ "' AND DIGITAL='" +data.get("digital")+ "'";
		String queryStatusAntes = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "'AND BATCH_ID='" +data.get("id")+ "'";
		
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
		addStep("Invocar la API Outbound con JSON");
			
		//Invocar la API Outbound con JSON
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
		String getExpectedCode1 = data.get("getExpectedCode");
		
		name = tr.lispAPI(baseURILisp, getPathLisp, departmentId, storeId, source, doctype, access_token, getExpectedCode1, testcaseName);

		System.out.println(name);
		//Lista de headers
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("Accept", "*/*");
		headersList.put("content-type", "application/json");
		headersList.put("Authorization", "Bearer "+access_token);
		
		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
		
		// Llamada al API OUTBOUND usando el token generado	
		String baseURIOutbound = baseURILisp;
		String getPathOutbound = data.get("getPathOutbound");
				
		//Invocar la API Outbound con JSON
		
		RestAssured.baseURI = baseURIOutbound;	
		String reqBodyOutbound = "{"
				+ "\"departmentId\":\""+departmentId+"\","
				+ "\"storeId\":\""+storeId+"\","
				+ "\"source\":\""+source+"\","
				+ "\"documents\":["
						+ "{"
							+ "\"name\": \""+name+"\","
							+ "\"fileType\": \""+doctype+"\""
						+ "}"
					+ "]"
			+ "}";

		//Se env�a como evidencia el request a ejecutar
		testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURIOutbound+getPathOutbound+reqBodyOutbound);
		
		//Hacemos el send del request
		Response getRespuesta3 = api.postRequestWithHeaders(baseURIOutbound+getPathOutbound, reqBodyOutbound, headersList);
		
		//***************************Paso 5*****************************************************************************
		 addStep("Para la TR1 verificar que los campos de entrada se transformen a minuscula e ingles. En palabras compuesta utilizar formato camelCase");
		 boolean isValid = tr.validateJsonRequest(reqBodyOutbound);
	     System.out.println("Request valido: " + isValid);
	     testCase.addTextEvidenceCurrentStep("TR1 exitosa\n Request valido: " + reqBodyOutbound);
	     assertTrue("No se muestra el estatus esperado: el JSON debería ser válido.", isValid);
		
		//***************************Paso 6*****************************************************************************
	     addStep("Para la TR3 verificar que las variables de los servicios expuesto esten mapeadas en el body del request");
	     ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(reqBodyOutbound);

       // Verificar si las claves 'departamentId', 'storeId', 'source', 'documents', 'name' y 'fileType' estan presentes
       if (jsonNode.has("departmentId") && jsonNode.has("storeId") && jsonNode.has("source") && jsonNode.has("documents")) {
    	   JsonNode documentsNode = jsonNode.get("documents");
    	   if (documentsNode.isArray()) {
    		   for (JsonNode documentNode : documentsNode){
    			   if (documentNode.has("name") && documentNode.has("fileType")) {
			           System.out.println("Las variables estan mapeadas en el body");
			           validaStatus = false;
    			   } else {
    				   System.out.println("Falta 'name' o 'fileType' en algun documento.");
    				   validaStatus = false;
    			   }
    		   }
    	   }
       } else {
           System.out.println("No hay variables en el body");
           validaStatus = true;
       }
       testCase.addTextEvidenceCurrentStep("TR3 exitosa\n Las variables estan mapeadas en el body" + reqBodyOutbound);
       assertFalse("Se encontro algun registro de error", validaStatus);
	     
       //***************************Paso 7*****************************************************************************
	     addStep("Para la TR4 verificar que la consulta que no corresponde al CRUD se envie con metodo POST");
	     
	     if (getRespuesta3 != null) {
	    	 System.out.println("Request enviado con metodo POST");
	    	 validaStatus = false;
	     }else {
           System.out.println("Request NO enviado con metodo POST");
           validaStatus = true;
	     }
	     testCase.addTextEvidenceCurrentStep("TR4 exitosa\n Request enviado con metodo POST" + reqBodyOutbound);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 8*****************************************************************************
	     addStep("Para la TR6 verificar que el request se muestren en JSON");
	     if (tr.esJsonValido(reqBodyOutbound)) {
	    	 	System.out.println("El cuerpo de la solicitud es JSON");
	    	 	validaStatus = false;
	        } else {
	        	System.out.println("El cuerpo de la solicitud NO es un JSON");
	        	validaStatus = true;
	        }
	     testCase.addTextEvidenceCurrentStep("TR6 exitosa\n El cuerpo de la solicitud es JSON" + reqBodyOutbound);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 9*****************************************************************************
	     addStep("Para la TR7 verificar que la nomenclatura de campos omitan el nombre del nodo padre. En campos fuera del nodo revisar que el nombre sea compuesto");
	     JsonNode rootNode = objectMapper.readTree(reqBodyOutbound);

        // Validar el JSON
        if (tr.validateJsonNombreNodo(rootNode, null)) {
            System.out.println("La nomenclatura de nombre de nodos es correcta");
            validaStatus = false;
        } else {
            System.out.println("La nomenclatura de nombre de nodos NO es correcta");
            validaStatus = true;
        }
        testCase.addTextEvidenceCurrentStep("TR7 exitosa\n La nomenclatura de nombre de nodos es correcta" + reqBodyOutbound);
        assertFalse("Se encontro algun registro de error", validaStatus);
		
      //***************************Paso 10*****************************************************************************
	     addStep("Validar que la respuesta del request: ");
	     
		//Obtener body de respuesta
		String getBodyResponse3 = getRespuesta3.getBody().asPrettyString();
			
		System.out.println("Body: "+getBodyResponse3);
		
		JsonPath getJson3 = new JsonPath(getBodyResponse3);
		String documentsOutbound = getJson3.getString("documents.name");
		
		//VALIDACION CODE
		int codeResp = Integer.parseInt(getJson3.getString("code"));
		int getExpectedCode = Integer.parseInt(getExpectedCode1);
		assertEquals(codeResp, getExpectedCode);
		
		// Obtener la lista de documentos OUTBOUND
		java.util.List <Map<String, Object>> documentsListOutbound = getJson3.getList("documents");
		System.out.println("documentsListOutbound: "+documentsListOutbound);
		
		// Obtener el NOMBRE del documento OUTBOUND
		name = (String) documentsListOutbound.get(0).get("name");
		System.out.println("Name del documento Outbound: " + name);
		
		// Obtener el FILETYPE del documento OUTBOUND
		String fileTypeOutbound = (String) documentsListOutbound.get(0).get("fileType");
		System.out.println("fileType del documento Outbound: " + fileTypeOutbound);
		
		// Obtener el STATUS del documento OUTBOUND
		String statusOutbound = (String) documentsListOutbound.get(0).get("status");
		System.out.println("Status del documento Outbound: " + statusOutbound);
		
		// Setear EVENTDATE del documento OUTBOUND
        LocalDateTime now = LocalDateTime.now(); // Obtener la fecha y hora actual
        DateTimeFormatter formatoEventDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Formateador con la sintaxis del eventDate
		String eventDateOutbound = (String) now.format(formatoEventDate);
		System.out.println("eventDate del documento Outbound: " + eventDateOutbound);
		
		testCase.addTextEvidenceCurrentStep("Code: " +codeResp);
		testCase.addTextEvidenceCurrentStep("Response: \n"+ getRespuesta3+getBodyResponse3);
		
		//***************************Paso 11*****************************************************************************
		 addStep("Para la TR2 verificar que los campos de salida se transformen a minuscula e ingles. En palabras compuesta utilizar formato camelCase");
		 isValid = tr.validateJsonRequest(getBodyResponse3);
	     System.out.println("Request valido: " + isValid);
	     testCase.addTextEvidenceCurrentStep("TR2 exitosa\n Request valido: " + getBodyResponse3);
	     assertTrue("No se muestra el estatus esperado: el JSON debería ser válido.", isValid);
		
		//***************************Paso 12*****************************************************************************
	     addStep("Para la TR3 verificar que las variables de los servicios expuesto esten mapeadas en el body del request");
       jsonNode = objectMapper.readTree(getBodyResponse3);

      // Verificar si las claves code, message, documents, mimeType, name, fileType, status y data estan presentes
       if (jsonNode.has("code") && jsonNode.has("message") && jsonNode.has("documents")) {
    	   JsonNode documentsNode = jsonNode.get("documents");
    	   if (documentsNode.isArray()) {
    		   for (JsonNode documentNode : documentsNode){
    			   if (documentNode.has("mimeType") && documentNode.has("name") && documentNode.has("fileType") && documentNode.has("status") && documentNode.has("data")) {
			           System.out.println("Las variables estan mapeadas en el body");
			           validaStatus = false;
    			   } else {
    				   System.out.println("Falta 'name' o 'fileType' en algun documento.");
    				   validaStatus = false;
    			   }
    		   }
    	   }
       } else {
           System.out.println("No hay variables en el body");
           validaStatus = true;
       }
       testCase.addTextEvidenceCurrentStep("TR3 exitosa\n Las variables estan mapeadas en el body" + reqBodyOutbound);
       assertFalse("Se encontro algun registro de error", validaStatus);
	     
      //***************************Paso 13*****************************************************************************
	     addStep("Para la TR5 verificar que el grupo de elementos este contenido dentro de una estructura que lo describa claramente");
	     rootNode = objectMapper.readTree(getBodyResponse3);
	     
	     if (tr.validateJsonCamposOutbound(rootNode)) {
	    	 System.out.println("Response con nombre de nodos representativos");
	    	 validaStatus = false;
	     }else {
          System.out.println("Response con nombre de nodos NO representativos");
          validaStatus = true;
	     }
	     testCase.addTextEvidenceCurrentStep("TR5 exitosa\n Response con nombre de nodos representativos" + getBodyResponse3);
	     assertFalse("Se encontro algun registro de error", validaStatus);
	     
	   //***************************Paso 14*****************************************************************************
	     addStep("Para la TR6 verificar que el request se muestren en JSON");
	     if (tr.esJsonValido(getBodyResponse3)) {
	    	 	System.out.println("El cuerpo de la solicitud es JSON");
	    	 	validaStatus = false;
	        } else {
	        	System.out.println("El cuerpo de la solicitud NO es un JSON");
	        	validaStatus = true;
	        }
	     testCase.addTextEvidenceCurrentStep("TR6 exitosa\n El cuerpo de la solicitud es JSON" + getBodyResponse3);
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
       testCase.addTextEvidenceCurrentStep("TR7 exitosa\n La nomenclatura de nombre de nodos es correcta" + getBodyResponse3);
       assertFalse("Se encontro algun registro de error", validaStatus);
		
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_002 Reglas Transformacion servicio OUTBOUND Enred out V2";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verifica que el servicio OUTBOUND de Enred out V2 cumpla las reglas de transformacion:\r\n"
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
		return "1. Contar con la mensajeria para invocar la API OUTBOUND V2 \r\n"
				+ "2. Esten instalado el cambio de la ENRED OUT V2 \r\n"
				+ "3. Respuesta con codigo: wmCode 101 \r\n"
				+ "4. Verificar que exista la interface Enred out V2 en las configuraciones de Azure \r\n"
				+ "5. Haber ejecutado el proceso de envio de las tiendas (PR63_Cloud) \r\n"
				+ "6. Acceso a BD MDB_ERDD_PromotionCloud (Azure)";

	}
}

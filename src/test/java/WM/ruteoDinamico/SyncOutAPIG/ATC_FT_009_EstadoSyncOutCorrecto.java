package WM.ruteoDinamico.SyncOutAPIG;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
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
 * PREREQUISITOS: 1.-Contar con las credenciales necesarias para la creaci�n del token (ClientId, Client Secret), estas cambian por ambiente
2.-Contar con la url de consumo para la generaci�n de OAUTH
3.-Contar con la url de consumo para la api rdm-routes
4.- Existencia de las tablas: WM_LOG_RUN, WM_LOG_ERROR_TPE, XXFC_DATOS_SYNCOUT, XXFC_DATOS_SYNCOUT_OPERACION
5.- Existencia de los campos: pru_idruta, IDRUTA
 * Los archivos json, son proporcionados por Oxxo 
 * DESCRIPCION: Validar que el estado del sync out sea 0 al enviar varias ordenes
 * @author jesus.b.jimenez@accenture.com
 */

public class ATC_FT_009_EstadoSyncOutCorrecto extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_EstadoSyncOutCorrecto_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String postPathToken = data.get("postPathToken");
		String postPathAPI = data.get("postPathAPI");
		String postBody = data.get("postBody");
		String baseURL = data.get("baseUrl");
		String consultaIdRutaIdJornada = data.get("queryIDRutaIDJornada");
		String consultaNumTransf = data.get("queryNumTransf");
		String consultaEdoSyncOut = data.get("queryEdoSyncOut");
		String postExpCode = data.get("postExpectedCode");
		String expBodyRes = data.get("expectedBodyResponse");
		String apiUserAuth = GlobalVariables.APIG_USER_RD;
		String apiPassAuth = GlobalVariables.APIG_PASSWORD_RD;
		String apiKey = GlobalVariables.APIG_APIKEY_RD;
		ArrayList<String> tsfNoList = new ArrayList<String>();
		String postBodyResponse;
		String message;
		String cveEdoSyncOut;
		String idRuta = "";
		String idJornada = "";
		String tsfNo;
		String accessToken;
		int postExpectedCode = Integer.parseInt(postExpCode);
		int postStatusCode;
		boolean transaction1, transaction2, transaction3;
		HashMap<String,String> headersListApi = new HashMap<>();
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDQA, GlobalVariables.DB_USER_RDM_RD,
				GlobalVariables.DB_PASSWORD_RDM_RDQA);
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		Response postRespuesta;
		JSONObject postjson;
		SQLResult result1, result2, result3;
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		
		// EJECUTAR CONSULTA PARA OBTENER DATOS PARA INSERTAR EN EL JSON A CONSUMIR DEL SERVICIO DE APIG
		addStep("Se toma el IDRUTA, IDJORNADA y TSF_NO para agregar en los parametros routeId, dayId y orderDocumentReference"
				+ " del JSON a consumir del servicio de API Galaxy");
		
		testCase.addCodeEvidenceCurrentStep(consultaIdRutaIdJornada);
		result1 = dbRdm.executeQuery(consultaIdRutaIdJornada);
		
		testCase.addCodeEvidenceCurrentStep(consultaNumTransf);
		result2 = dbRdm.executeQuery(consultaNumTransf);
		
		transaction1 = result1.isEmpty();
		transaction2 = result2.isEmpty();
		if (!transaction1 && !transaction2) {
			idRuta = result1.getData(0, "IDRUTA");
			idJornada = result1.getData(0, "IDJORNADA");
			testCase.addBoldTextEvidenceCurrentStep("IDRUTA (routeId en cabecera del JSON): ");
			testCase.addCodeEvidenceCurrentStep("\t" + idRuta);
			testCase.addBoldTextEvidenceCurrentStep("IDJORNADA (dayId en cabecera del JSON): ");
			testCase.addCodeEvidenceCurrentStep("\t" + idJornada);
			testCase.addBoldTextEvidenceCurrentStep("TSF_NO (orderDocumentReference en order object del JSON): ");
			for (int i = 0; i < 5; i++) {
				tsfNo = result2.getData(i, "TSF_NO");
				testCase.addCodeEvidenceCurrentStep("\t" + tsfNo);
				tsfNoList.add(tsfNo);
			}
			postBody = String.format(postBody, idRuta, idJornada, tsfNoList.get(0), tsfNoList.get(1), tsfNoList.get(2), 
					tsfNoList.get(3), tsfNoList.get(4));
		}
		
		addStep("Ejecutar token");
		System.out.println("PASO 1");
		
		testCase.addBoldTextEvidenceCurrentStep("- request:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + postPathToken);
		
		//GENERAR TOKEN
		
		RestAssured.baseURI = baseURL;
		
		postRespuesta = api.BasicAuthRequest(baseURL + postPathToken, apiUserAuth + ":" + apiPassAuth);
		
		postStatusCode = postRespuesta.getStatusCode();
		testCase.addBoldTextEvidenceCurrentStep("- statusCode (token):\n");
		testCase.addCodeEvidenceCurrentStep("\t" + postStatusCode);
		
		postBodyResponse = postRespuesta.asPrettyString();
		testCase.addBoldTextEvidenceCurrentStep("- bodyResponse (token):\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyResponse);
		
		postjson = new JSONObject(postBodyResponse);
		accessToken = postjson.getString("access_token");
		testCase.addBoldTextEvidenceCurrentStep("- Access token:\n");
		testCase.addCodeEvidenceCurrentStep("\t" + accessToken);

		
		addStep("Ejecutar servicio con datos en formato json con informaci�n de 5 ordenes con datos correctos");
		System.out.println("PASO 2");
		
		testCase.addBoldTextEvidenceCurrentStep("- request:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + postPathAPI);
		testCase.addBoldTextEvidenceCurrentStep("- body:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBody);
		
		//LISTA DE HEADERS
		
		headersListApi.put("Accept", "*/*");
		headersListApi.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
		headersListApi.put("Channel-Id", "WEB");
		headersListApi.put("Country-Code", "MX");
		headersListApi.put("Language", "SPA");
		headersListApi.put("Content-Type", "application/json");
		headersListApi.put("x-Gateway-APIKey", apiKey);
		headersListApi.put("Authorization", "Bearer " + accessToken);
		
		RestAssured.baseURI = baseURL;
		postRespuesta = RestAssured
		.given()
			.headers(headersListApi)
			.relaxedHTTPSValidation()
			.contentType(ContentType.JSON)
			.body(postBody)
		.when()
			.post(postPathAPI);
		
		postStatusCode = postRespuesta.getStatusCode();
		testCase.addBoldTextEvidenceCurrentStep("- statusCode:\n");
		testCase.addCodeEvidenceCurrentStep("\t" + postStatusCode);
		
		assertEquals(postStatusCode, postExpectedCode);
		
		postBodyResponse = postRespuesta.asPrettyString();
		testCase.addBoldTextEvidenceCurrentStep("- bodyResponse:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyResponse);
		
		postjson = new JSONObject(postBodyResponse);
		message = postjson.getString("message");
		testCase.addBoldTextEvidenceCurrentStep("- message:\n");
		testCase.addCodeEvidenceCurrentStep("\t" + message);
		
		assertEquals(message, expBodyRes);
		
		addStep("Validando el Estado del SyncOut despu�s de correr el servicio de API Galaxy");
		
		consultaEdoSyncOut = String.format(consultaEdoSyncOut, idRuta, idJornada);
		testCase.addCodeEvidenceCurrentStep(consultaEdoSyncOut);
		result3 = dbRdm.executeQuery(consultaEdoSyncOut);
		
		transaction3 = result3.isEmpty();
		if (!transaction3) {
			cveEdoSyncOut = result3.getData(0, "CVE_SYNCOUT_ESTADO");
			assertEquals(cveEdoSyncOut, "0");
			testCase.addBoldTextEvidenceCurrentStep("Clave de Estado del SyncOut: ");
			testCase.addCodeEvidenceCurrentStep("\t" + cveEdoSyncOut);
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.-Contar con las credenciales necesarias para la creaci�n del token (ClientId, Client Secret), estas cambian por ambiente\r\n"
				+ "2.-Contar con la url de consumo para la generaci�n de OAUTH\r\n"
				+ "3.-Contar con la url de consumo para la api rdm-routes\r\n"
				+ "4.- Existencia de las tablas: WM_LOG_RUN, WM_LOG_ERROR_TPE, XXFC_DATOS_SYNCOUT, XXFC_DATOS_SYNCOUT_OPERACION";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el estado del sync out sea 0 al enviar varias ordenes";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "jesus.b.jimenez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_EstadoSyncOutCorrecto_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
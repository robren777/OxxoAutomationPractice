package WM.ruteoDinamico.SyncOutWM;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * PREREQUISITOS: Existencia de las tablas:
					WM_LOG_RUN
					WM_LOG_ERROR_TPE
					XXFC_DATOS_SYNCOUT 
					XXFC_DATOS_SYNCOUT_OPERACION
 * 
 * 
 * DESCRIPCION: Validar que el estado del sync out sea -1 al enviar varias ordenes con error
 * @author cristhian.m.gonzalez@accenture.com
 */

@AzureTestInfo(
		areaPath = "I22074_RuteoDinamico",
		iterationPath = "I22074_RuteoDinamico\\SP8", 
		tags = "WM;Automatizacion",
		userStoryId = 25487 
)

public class ATC_FT_005_ValidarEstadoSyncOutIncorrecto extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarEstadoSyncOutIncorrecto_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String postPath = data.get("postPath");
		String postBody = data.get("postBody");
		String baseURL = data.get("baseUrl");
		String consultaIdRutaIdJornada = data.get("queryIDRutaIDJornada");
		String consultaNumTransf = data.get("queryNumTransf");
		String consultaEdoSyncOut = data.get("queryEdoSyncOut");
		String postExpCode = data.get("postExpectedCode");
		String expBodyRes = data.get("expectedBodyResponse");
		//String wmUserAuth = GlobalVariables.WM_USER_RD;
		//String wmPassAuth = GlobalVariables.WM_PASSWORD_RD;
		ArrayList<String> tsfNoList = new ArrayList<String>();
		String idRuta = "";
		String idJornada = "";
		String tsfNo;
		String postBodyResponse;
		String message;
		String cveEdoSyncOut;
		int postExpectedCode = Integer.parseInt(postExpCode);
		int postStatusCode;
		boolean transaction1, transaction2, transaction3;
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDQA, GlobalVariables.DB_USER_RDM_RD,
				GlobalVariables.DB_PASSWORD_RDM_RDQA);
		Response postRespuesta;
		JSONObject postjson;
		SQLResult result1, result2, result3;
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		
		// EJECUTAR CONSULTA PARA OBTENER DATOS PARA INSERTAR EN EL JSON A CONSUMIR DE LA INTERFAZ WM
		addStep("Se toma el IDRUTA, IDJORNADA y TSF_NO para agregar en los parametros routeId, dayId y orderDocumentReference"
				+ " del JSON a consumir de la interfaz de WebMethod");
		
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
			for (int i = 0; i < 4; i++) {
				tsfNo = result2.getData(i, "TSF_NO");
				testCase.addCodeEvidenceCurrentStep("\t" + tsfNo);
				tsfNoList.add(tsfNo);
			}
			postBody = String.format(postBody, idRuta, idJornada, tsfNoList.get(0), tsfNoList.get(1), tsfNoList.get(2), tsfNoList.get(3));
		}
		
		addStep("Ejecutar servicio con datos en formato json con informaci�n de 5 ordenes con la �ltima orden con el dato "
				+ "del par�metro orderDocumentReference incorrecto ");
		System.out.println("PASO 1");
		
		testCase.addBoldTextEvidenceCurrentStep("- request:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + postPath);
		testCase.addBoldTextEvidenceCurrentStep("- body:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBody);
		
		RestAssured.baseURI = baseURL;
		postRespuesta = RestAssured
		.given()
			//.auth().basic(wmUserAuth, wmPassAuth)
			.relaxedHTTPSValidation()
			.contentType(ContentType.JSON)
			.body(postBody)
		.when()
			.post(postPath);
		
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
		
		// EJECUTAR CONSULTA PARA OBTENER EL ESTADO DEL SYNCOUT DESPUES DE EJECUTAR SERVICIO DE LA INTERFAZ WM
		addStep("Validando el Estado del SyncOut despu�s de correr la interfaz de WebMethod");
		
		consultaEdoSyncOut = String.format(consultaEdoSyncOut, idRuta, idJornada);
		testCase.addCodeEvidenceCurrentStep(consultaEdoSyncOut);
		result3 = dbRdm.executeQuery(consultaEdoSyncOut);
		
		transaction3 = result3.isEmpty();
		if (!transaction3) {
			cveEdoSyncOut = result3.getData(0, "CVE_SYNCOUT_ESTADO");
			assertEquals(cveEdoSyncOut, "-1");
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
		return "Existencia de las tablas:\r\n"
				+ "	WM_LOG_RUN\r\n"
				+ "	WM_LOG_ERROR_TPE\r\n"
				+ "	XXFC_DATOS_SYNCOUT \r\n"
				+ "	XXFC_DATOS_SYNCOUT_OPERACION";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el estado del sync out sea -1 al enviar varias ordenes con error";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarEstadoSyncOutIncorrecto_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

package WM.ruteoDinamico.SyncOutWM;

import static org.testng.Assert.assertEquals;

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
 * DESCRIPCION: Validar sync out arroje error de Web Methods al enviar dato incorrecto en el par�metro branch (numero de cedis)"
 * @author cristhian.m.gonzalez@accenture.com
 */

@AzureTestInfo(
		areaPath = "I22074_RuteoDinamico",
		iterationPath = "I22074_RuteoDinamico\\SP8", 
		tags = "WM;Automatizacion",
		userStoryId = 25487 
)

public class ATC_FT_002_ValidarErrorWM extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarErrorWM_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String postPath = data.get("postPath");
		String postBody = data.get("postBody");
		String baseURL = data.get("baseUrl");
		String consultaParamJSON = data.get("query");
		String postExpCode = data.get("postExpectedCode");
		String expBodyRes = data.get("expectedBodyResponse").replaceAll("\"", "");
		//String wmUserAuth = GlobalVariables.WM_USER_RD;
		//String wmPassAuth = GlobalVariables.WM_PASSWORD_RD;
		String postBodyResponse;
		String message;
		String idRuta;
		String idJornada;
		int postExpectedCode = Integer.parseInt(postExpCode);
		int postStatusCode;
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDQA, GlobalVariables.DB_USER_RDM_RD,
				GlobalVariables.DB_PASSWORD_RDM_RDQA);
		Response postRespuesta;
		JSONObject postjson;
		SQLResult result;
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		
		// EJECUTAR CONSULTA PARA OBTENER DATOS PARA INSERTAR EN EL JSON A CONSUMIR DE LA INTERFAZ WM
		addStep("Se toma el IDRUTA y IDJORNADA para agregar en los parametros routeId y dayId del JSON a consumir de la interfaz de WebMethod");
		
		testCase.addCodeEvidenceCurrentStep(consultaParamJSON);
		result = dbRdm.executeQuery(consultaParamJSON);
		
		boolean transaction = result.isEmpty();
		if (!transaction) {
			idRuta = result.getData(0, "IDRUTA");
			idJornada = result.getData(0, "IDJORNADA");
			testCase.addBoldTextEvidenceCurrentStep("IDRUTA (routeId en cabecera del JSON): ");
			testCase.addCodeEvidenceCurrentStep("\t" + idRuta);
			testCase.addBoldTextEvidenceCurrentStep("IDJORNADA (dayId en cabecera del JSON): ");
			testCase.addCodeEvidenceCurrentStep("\t" + idJornada);
			postBody = String.format(postBody, idRuta, idJornada);
		}
		
		addStep("Ejecutar servicio con datos en formato json con informaci�n con dato incorrecto en el par�metro branch");
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
		return "Validar sync out arroje error de Web Methods"
				+ " al enviar dato incorrecto en el par�metro"
				+ " branch (numero de cedis)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarErrorWM_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

package WM.interfaces.pe6;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import WM.util.ApiUtil;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import utils.webmethods.ReadRequest;

public class ATC_FT_026_PE6_ValidaPruebasVolumenRendimiento extends BaseExecution{
	@Test(dataProvider = "data-provider")

	public void ATC_FT_026_PE6_ValidaPruebasVolumenRendimiento_test(HashMap<String, String> data)
			throws Exception {

		SQLUtil dbFCTDCQA = new SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QRO, GlobalVariables.DB_USER_FCTDCQA_QRO,
				GlobalVariables.DB_PASSWORD_FCTDCQA_QRO);
		SQLUtil dbFCSWQA = new SQLUtil(GlobalVariables.DB_HOST_FCSWQA_QRO, GlobalVariables.DB_USER_FCSWQA_QRO,
				GlobalVariables.DB_PASSWORD_FCSWQA_QRO);

		/********************************
		 * VARIABLES *
		 ********************************/
		

		String ValidTranss= "SELECT FOLIO,CREATION_DATE,PLAZA,TIENDA,INTEGRIDAD,WM_CODE  "
				+ "FROM TPEUSER.TDC_TRANSACTION "
				+ "WHERE CREATION_DATE >=TRUNC(SYSDATE) "
				+ "and plaza= '"+data.get("plaza")+"'  "
				+ "AND FOLIO = '%s' "
				+ "AND rownum >=1 ORDER BY CREATION_DATE DESC";
		
		String ValidSWLog= "Select FOLIO,PLAZA,TIENDA,APPLICATION,ENTITY,CREATION_DATE"
				+ " from swuser.TPE_SW_TLOG "
				+ "WHERE  CREATION_DATE  >=TRUNC(SYSDATE) "
				+ "AND APPLICATION='TDC' "
				+ "AND FOLIO= '%s' "
				+ "AND rownum >=1 ORDER BY CREATION_DATE DESC";
		
		String ValidTranssAck= "SELECT FOLIO,CREATION_DATE,PLAZA,TIENDA,ack_code  "
				+ "FROM TPEUSER.TDC_TRANSACTION "
				+ "WHERE CREATION_DATE >=TRUNC(SYSDATE) "
				+ "and plaza= '"+data.get("plaza")+"'  "
				+ "AND FOLIO = '%s' "
				+ "AND ack_code='00' "
				+ "AND rownum >=1 ORDER BY CREATION_DATE DESC";
		
		String ValidSWLogAck= "Select FOLIO,PLAZA,TIENDA,APPLICATION,ENTITY,CREATION_DATE,SW_CODE"
				+ " from swuser.TPE_SW_TLOG "
				+ "WHERE  CREATION_DATE >=TRUNC(SYSDATE) "
				+ "AND APPLICATION='TDC' "
				+ "AND FOLIO= '%s' "
				+ "AND SW_CODE='00' "
				+ "AND rownum >=1 ORDER BY CREATION_DATE DESC";

		testCase.setTest_Description(data.get("Name"));

		String folio = "";
		String creationDate = "";
		String wmCodeFolio = "";
		String wmCodeAut = "";
		String uri = data.get("uri");
		String adDate = "";
		
		String idAut = data.get("idAut");
		String Json_Volt = data.get("Json_Volt");
		String serverVoltage = data.get("serverVoltage");
		String Tarjeta = data.get("Tarjeta");
		String Monto = data.get("Monto");
		String GetAut = data.get("GetAut");
		String entryMode = data.get("entryMode");
		String track2 = data.get("track2");
		String promType = data.get("promType");
		String operator = data.get("operator");
		String accountNo = data.get("accountNo");
		String accountType = data.get("accountType");
		String bankld = data.get("bankld");
		String purchaseType = data.get("purchaseType");
		String EncrCard = data.get("EncrCard");

		ApiMethodsUtil api = new ApiMethodsUtil(uri);

		// Valores WMcode esperados
		String expectedWMCodeGetFolio = data.get("WMCodeGetFolio");
		String expectedWMCodeGetAuth = data.get("WMCodeGetAuth");
		String expectedWMCodeGetAck = data.get("WMCodeGetAck");
		String WMCodeVoltage=data.get("WMCodeVoltage");

		System.out.println(data.get("Name")+" - "+data.get("Banco"));
		// Paso 1 *********************************************************

		addStep("Solicitar un folio , invocando el servicio runGetFolio:");

		String ResponseGetFolio = api.getRequestMethod(data.get("GetFolio")).asPrettyString();
		System.out.println(ResponseGetFolio);

		boolean ValidResGetfolio = ResponseGetFolio.isEmpty();
		boolean ValidWmCode = false;
		if (!ValidResGetfolio) {

			Document runGetFolioRequestDoc = ReadRequest.convertStringToXMLDocument(ResponseGetFolio);
			folio = runGetFolioRequestDoc.getElementsByTagName("folio").item(0).getTextContent();
			System.out.println("Folio: " + folio);

			creationDate = runGetFolioRequestDoc.getElementsByTagName("creationDate").item(0).getTextContent();
			System.out.println("creationDate: " + creationDate);

			wmCodeFolio = runGetFolioRequestDoc.getElementsByTagName("wmCode").item(0).getTextContent();
			System.out.println("wmCodeFolio: " + wmCodeFolio);

			ValidWmCode = wmCodeFolio.equals(expectedWMCodeGetFolio);
			
			testCase.addTextEvidenceCurrentStep("Request: \n"+ data.get("GetFolio"));
			testCase.addTextEvidenceCurrentStep("Response: \n"+ResponseGetFolio);

			assertTrue(ValidWmCode, "el wmCodeFolio no es: " + expectedWMCodeGetFolio);


		}

	
			
		/*
		 * FORMAR DATO INTEGRIDAD*********************************************************
		 */
		String Fecha1 = creationDate.substring(4);
		System.out.println("DateIntegridad: "+Fecha1);
		adDate = creationDate.substring(0,8);	
		System.out.println("adDate: "+adDate);
		String Integridad = Tarjeta+Fecha1+folio+Monto;
			
		/*
		 * ENCRIPTAR CAMPO INTEGRIDADA****************************************************
		 */
		boolean Status =false;
		if(idAut.equals("true")) {
			addStep("Encriptar dato Integridad");
			String JsonFormat = String.format(Json_Volt, Integridad);
			System.out.println(JsonFormat);
			HashMap<String, String> Headers = new HashMap<String, String>();
			Headers.put("Content-Type", "application/json");
			Headers.put("Authorization", data.get("Authorization"));
			
			testCase.addTextEvidenceCurrentStep("Se envia request a server voltage:\n"+ serverVoltage);
			testCase.addTextEvidenceCurrentStep(JsonFormat);
			
			HttpResponse RequestEncript = ApiUtil.sendPostDissableSSL(serverVoltage, JsonFormat, Headers);
			String StatusCode = String.valueOf(RequestEncript.getStatusLine().getStatusCode());
			System.out.println("Status RequestEncript: "+ StatusCode);
			
			testCase.addTextEvidenceCurrentStep("Status Code: "+ StatusCode);
			
			Status = StatusCode.equals(WMCodeVoltage);
			if (Status){
			JSONObject JsonIntegridad = new JSONObject(EntityUtils.toString(RequestEncript.getEntity()));
			System.out.println("JsonIntegridad: "+ JsonIntegridad);

			JSONArray IntegridadArray = JsonIntegridad.getJSONArray("data");
			System.out.println("IntegridadArray: "+ IntegridadArray);
			String Data = IntegridadArray.toString().substring(2);
			Integridad=Data.substring(0,Data.length()-2);
		
			testCase.addTextEvidenceCurrentStep("Se encripto correctamente:\n"+ JsonIntegridad);
			testCase.addTextEvidenceCurrentStep("Integridad: "+ Integridad);
			}else {
				testCase.addTextEvidenceCurrentStep("No se obtuvo campo integridad encriptado");
				System.out.println("Status Code incorrecto: "+ StatusCode);
			}
		}	
		
		System.out.println("Integridad: "+ Integridad);
		assertTrue(Status, "el wmCodeFolio no es: " + WMCodeVoltage);

		// ************************* Paso 2 ******************************
		// ***************************************************************
		addStep("Solicitud de autorizaci�n " + data.get("Caso"));

		String FormatGetAut = String.format(GetAut, folio, creationDate, creationDate, adDate,EncrCard,entryMode,track2,promType,Monto,operator,
				accountNo,accountType,bankld,purchaseType,Integridad,idAut);
		System.out.println(FormatGetAut);

		String ResponseGetAuth = api.getRequestMethod(FormatGetAut).asPrettyString();
		System.out.println(ResponseGetAuth);

		boolean ValidResGetAut = ResponseGetAuth.isEmpty();
		ValidWmCode = false;

		if (!ValidResGetAut) {

			Document runGetAutRequestDoc = ReadRequest.convertStringToXMLDocument(ResponseGetAuth);
			wmCodeAut = runGetAutRequestDoc.getElementsByTagName("wmCode").item(0).getTextContent();
			System.out.println("wmCodeAut: " + wmCodeAut);

			ValidWmCode = wmCodeAut.equals(expectedWMCodeGetAuth);
		}

		testCase.addTextEvidenceCurrentStep("Request GetAut: \n"+ FormatGetAut);
		testCase.addTextEvidenceCurrentStep("Response: \n"+ResponseGetAuth);

		assertTrue(ValidWmCode, "el wmCode no es el esperado : " + expectedWMCodeGetAuth);

		// ************************* Paso 3 ******************************
		// ***************************************************************
		
		addStep("Valida que se registra la informaci�n de la transacci�n");
		System.out.println(GlobalVariables.DB_HOST_FCTDCQA_QRO);

		String formatValidTranss = String.format(ValidTranss, folio);
		System.out.println(formatValidTranss);

		SQLResult ExecuteValidTranss = dbFCTDCQA.executeQuery(formatValidTranss);

		boolean ValidTranssBoolean = ExecuteValidTranss.isEmpty();

		System.out.println(ValidTranssBoolean);

		if (!ValidTranssBoolean) {
			
			testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado:");
			testCase.addBoldTextEvidenceCurrentStep(
					"Se muestra registro relacionado con la transacci�n.");
			
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteValidTranss);
		assertFalse(ValidTranssBoolean, "No hay registros en la tabla TDC_TRANSACTION ");
		
		// ************************* Paso 4 ******************************
		// ***************************************************************
		
		addStep("Valida que se registra la informaci�n de la transacci�n  en la tabla swuser.TPE_SW_TLOG WHERE");
		System.out.println(GlobalVariables.DB_HOST_FCSWQA_QRO);

		String ValidSWLogTranss = String.format(ValidSWLog, folio);
		System.out.println(ValidSWLogTranss);

		SQLResult ExecuteValidSWLogs = dbFCSWQA.executeQuery(ValidSWLogTranss);

		boolean ValidSWLogBoolean = ExecuteValidSWLogs.isEmpty();

		System.out.println(ValidSWLogBoolean);

		if (!ValidSWLogBoolean) {
			
			testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado:");
			testCase.addBoldTextEvidenceCurrentStep(
					"Se muestra registro relacionado con la transacci�n.");
			
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteValidSWLogs);
		assertFalse(ValidSWLogBoolean, "No hay registros en la tabla swuser.TPE_SW_TLOG WHERE ");
		
		
		// ************************* Paso 5 ******************************
		// ***************************************************************
		addStep("Solicitud de ack " + data.get("Caso"));
		
		String FormatGetAutAck = String.format(data.get("Getack"), folio, creationDate);
		System.out.println(FormatGetAutAck);

		String ResponseGetAuthAck = api.getRequestMethod(FormatGetAutAck).asPrettyString();
		System.out.println(ResponseGetAuthAck);

		boolean ValidResGetAutAck = ResponseGetAuthAck.isEmpty();
		ValidWmCode = false;

		if (!ValidResGetAutAck) {

			Document runGetAutRequestDoc = ReadRequest.convertStringToXMLDocument(ResponseGetAuthAck);
			wmCodeAut = runGetAutRequestDoc.getElementsByTagName("wmCode").item(0).getTextContent();
			System.out.println("wmCodeAut: " + wmCodeAut);

			ValidWmCode = wmCodeAut.equals(expectedWMCodeGetAck);
		}

		testCase.addTextEvidenceCurrentStep("Request GetAutAck: \n"+ FormatGetAutAck);
		testCase.addTextEvidenceCurrentStep("Response: \n"+ResponseGetAuthAck);

		assertTrue(ValidWmCode, "el wmCode no es el esperado: " + expectedWMCodeGetAck);
		
		
		// ************************* Paso 6 ******************************
		// ***************************************************************	
		
		addStep("Valida que se registra la informaci�n code_ack = 00.");
		System.out.println(GlobalVariables.DB_HOST_FCTDCQA_QRO);

		String formatValidTranssAck = String.format(ValidTranssAck, folio);
		System.out.println(formatValidTranssAck);

		SQLResult ExecuteValidTranssAck = dbFCTDCQA.executeQuery(formatValidTranssAck);

		boolean ValidTranssAckBoolean = ExecuteValidTranssAck.isEmpty();

		System.out.println(ValidTranssAckBoolean);

		if (!ValidTranssAckBoolean) {
			
			testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado:");
			testCase.addBoldTextEvidenceCurrentStep(
					"Se muestra registro relacionado con la transacci�n y code_ack = 00.");
			
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteValidTranssAck);
		assertFalse(ValidTranssAckBoolean, "No hay registros en la tabla TDC_TRANSACTION ");
		
		// ************************* Paso 7 ******************************
		// ***************************************************************

		addStep("Valida que se registra la informaci�n de la transacci�n  en la tabla swuser.TPE_SW_TLOG WHERE con SW_CODE='00'");
		System.out.println(GlobalVariables.DB_HOST_FCSWQA_QRO);

		String ValidSWLogTranssAck = String.format(ValidSWLogAck, folio);
		System.out.println(ValidSWLogTranssAck);

		SQLResult ExecuteValidSWLogsAck = dbFCSWQA.executeQuery(ValidSWLogTranssAck);

		boolean ValidSWLogAckBoolean = ExecuteValidSWLogsAck.isEmpty();

		System.out.println(ValidSWLogAckBoolean);

		if (!ValidSWLogAckBoolean) {
			
			testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado:");
			testCase.addBoldTextEvidenceCurrentStep(
					"Se muestra registro relacionado con la transacci�n.");
			
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteValidSWLogsAck);
		assertFalse(ValidSWLogAckBoolean, "No hay registros en la tabla swuser.TPE_SW_TLOG WHERE con  SW_CODE='00'");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*Contar con un simulador de proveedor de corresponsal�as que responda las transacciones de forma exitosa.\r\n"
				+ "*Contar con acceso a las base de datos de FCTDCQA_MTY, FCTDCQA_QRO, FCWMLTAEQA_MTY y FCWMLTAEQA_QRO.,FCSWQA.FEMCOM.NET\r\n"
				+ "*Contar con las credenciales de WM10.5 de los nuevos servers del Integration Server de QA.\r\n"
				+ "*Contar con acceso al Software AG para el debug de los servicios";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_026_PE6_ValidaPruebasVolumenRendimiento_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
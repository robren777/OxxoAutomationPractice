package WM.interfaces.pe6;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.webmethods.ReadRequest;

public class ATC_FT_013_PE6_ValidaErroresCampoIntegridad extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_013_PE6_ValidaErroresCampoIntegridad_test(HashMap<String, String> data) throws Exception {

		/********************************
		 * VARIABLES *
		 ********************************/

		testCase.setTest_Description(data.get("Name"));
		

		String folio = "";
		String creationDate = "";
		String wmCodeFolio = "";
		String wmCodeAut = "";
		String uri = data.get("uri");
		String adDate = "";
		String DateIntegridad="";
		
		String GetAut = data.get("GetAut");
		String cardNo = data.get("cardNo");
		String entryMode = data.get("entryMode");
		String track2 = data.get("track2");
		String promType = data.get("promType");
		String amount = data.get("amount");
		String operator = data.get("operator");
		String accountNo = data.get("accountNo");
		String accountType = data.get("accountType");
		String bankld = data.get("bankld");
		String purchaseType = data.get("purchaseType");
		String integridad = data.get("integridad");
		String idAut = data.get("Aut");
		String EncrCard = data.get("EncrCard");
		
		ApiMethodsUtil api = new ApiMethodsUtil(uri);

		// Valores WMcode esperados
		String expectedWMCodeGetFolio = data.get("WMCodeGetFolio");
		String expectedWMCodeGetAuth = data.get("WMCodeGetAuth");
		System.out.println(data.get("Name")+" - "+data.get("Banco"));
		// Paso 1 *********************************************************			
		
		addStep("Solicitar un folio , invocando el servicio runGetFolio:");
		testCase.addBoldTextEvidenceCurrentStep("Request: \n"+ data.get("GetFolio"));
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
			testCase.addTextEvidenceCurrentStep("Response: \n"+ResponseGetFolio);

			assertTrue(ValidWmCode, "el wmCodeFolio no es: " + expectedWMCodeGetFolio);

		}

	
		// ************************* Paso 2
		// ***************************************************************
		addStep("Solicitud de autorizaci�n con el campo de Integridad con " + data.get("Caso"));
		DateIntegridad = creationDate.substring(4);
		System.out.println("DateIntegridad: "+DateIntegridad);
		adDate = creationDate.substring(0,8);	
		System.out.println("adDate: "+adDate);
				
		String FormatGetAut = String.format(GetAut, folio, creationDate, creationDate, adDate,EncrCard,entryMode,track2,promType,amount,operator,
				accountNo,accountType,bankld,purchaseType,integridad,idAut);
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

		assertTrue(ValidWmCode, "el wmCode no es el esperado con error: " + expectedWMCodeGetAuth);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

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
		return "ATC_FT_013_PE6_ValidaErroresCampoIntegridad_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*Contar con un simulador de proveedor de corresponsal�as que responda las transacciones de forma exitosa.\r\n"
				+ "*Contar con acceso a las base de datos de FCTDCQA_MTY, FCTDCQA_QRO, FCWMLTAEQA_MTY y FCWMLTAEQA_QRO.,FCSWQA.FEMCOM.NET\r\n"
				+ "*Contar con las credenciales de WM10.5 de los nuevos servers del Integration Server de QA.\r\n"
				+ "*Contar con acceso al Software AG para el debug de los servicios";
	}
}

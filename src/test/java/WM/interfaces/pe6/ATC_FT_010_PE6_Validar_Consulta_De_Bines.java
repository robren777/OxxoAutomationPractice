package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE6;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_FT_010_PE6_Validar_Consulta_De_Bines extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_PE6_Validar_Consulta_De_Bines_test (HashMap <String, String> data) throws Exception {
		/*
		 * Utilerias**************************************************************/
		
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		/*
		 * Variables***************************************************************/
		
		String wmCodeToValidate = data.get("wmCodeToValidate");
		
		String params = pe6Params.runGetQueryParams();
		/************************************Paso 1*********************************/
		addStep(data.get("name"));
		String uri = data.get("uri") + params;
		System.out.println(uri);
		ApiMethodsUtil api = new ApiMethodsUtil(uri);
		testCase.setTest_Description(data.get("testDescription"));
		Response requestQuery = api.getRequestMethod(uri);
		
		String wmCode = "";
		if ( requestQuery.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyQuery = requestQuery.getBody().asPrettyString();
			System.out.println(bodyQuery);
			
			wmCode = getSimpleDataXml(bodyQuery, "wmCode");
			System.out.println("WmCode: " + wmCode);
			
			//Agregar request a evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uri);
			//Agregar Response a evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestQuery.getStatusLine());
			testCase.addTextEvidenceCurrentStep(bodyQuery);
			
			assertEquals(wmCode, wmCodeToValidate, "Se obtiene wmCode diferente al esperado");
		}
			assertEquals(requestQuery.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		
	}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_PE6_Validar_Consulta_De_Bines_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido.";
	}

}

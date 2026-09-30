package WM.interfaces.femsa_ws;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.FDP2Soap;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;

public class ATC_FT_001_Validar_carga_xml_correcto extends BaseExecution{
	
	/**
	* Descripcion del test:
	 * Validar que se cargue el XML recortado en la tabla XXFC.XXFC_CFDAVALIDAR de la BD AVEBQA 
	 * 
	 * Pre-requisitos:
	 * 1- Acceder a la BD AVEBQA
	 *
	 * Insumos:
	 * 1- Tener acceso a la Base de Datos AVEBQA 
	 */	
	
	private FDP2Soap FDP2WM;
	
@Test(dataProvider = "data-provider", enabled = true)
public void ATC_FT_001_Validar_carga_xml_correcto_test (HashMap<String, String> data) throws Exception {
		
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
       
       addStep("Estatus y Mensajes de respuesta arrojados");
       
       //response.then().statusCode(200);
       assertEquals(response.getStatusCode(), 200); //Validación
       
       testCase.addCodeEvidenceCurrentStep("bodyEstatusResponse:" + response.getStatusCode());
       testCase.addCodeEvidenceCurrentStep("bodyResponse:" + response.getBody().asString());
       // Validar el contenido de la respuesta SOAP
       
       String responseBody = response.getBody().asString();
       assertSoapResponse(responseBody);       
    
       addStep("Validar mensaje arrojado con responseMessage y returnCode");
       FDP2WM.assertSoapResponse(responseBody);
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
		return "Se realiza la carga de XML";
	}
	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solís Castillo";
	}
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Validar_carga_xml_correct_test";
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

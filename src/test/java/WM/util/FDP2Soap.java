package WM.util;

import static org.testng.Assert.assertEquals;

import modelo.TestCase;


public class FDP2Soap {
	
	private static TestCase testCase;

public FDP2Soap(TestCase testCase) {
	super();
	this.testCase = testCase;
	
}

/** Validar mensaje de respuesta xml
 * @param assertSoapResponse
 * @author Melba Solís
 */	

public void assertSoapResponse(String responseBody) {
	
	  	// Validar el mensaje de respuesta
	    String responseMessage = responseBody
	        .split("<responseMessage xsi:type=\"xsd:string\">")[1]
	        .split("</responseMessage>")[0];
	    assertEquals(responseMessage, "", "El mensaje de respuesta no es el esperado");
	    testCase.addCodeEvidenceCurrentStep("bodyResponse:" + responseMessage);
	  	
	  	// Validar el código de retorno
	    String returnCode = responseBody
	         .split("<returnCode xsi:type=\"xsd:string\">")[1]
	         .split("</returnCode>")[0];
	    assertEquals(returnCode, "0", "El código de retorno no es el esperado");
	    testCase.addCodeEvidenceCurrentStep("bodyResponse:" + returnCode);
	    	
	    }
	
public void assertSoapResponse2(String responseBody) {
    	
   	// Validar el código de retorno
    String returnCode = responseBody
         .split("<returnCode xsi:type=\"xsd:string\">")[1]
         .split("</returnCode>")[0];
    assertEquals(returnCode, "1", "El código de retorno no es el esperado");
    testCase.addCodeEvidenceCurrentStep("bodyResponse:" + returnCode);
}
	}

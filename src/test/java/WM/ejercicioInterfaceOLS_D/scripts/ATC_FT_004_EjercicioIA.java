package WM.ejercicioInterfaceOLS_D.scripts;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/*********************************************************************************
* Inicialización/Declaración de utilerías
**********************************************************************************/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import modelo.BaseExecution;

public class ATC_FT_004_EjercicioIA extends BaseExecution {
 
	/**
	 * Se crea una instancia de SeleniumUtil, este es el objeto con el que se
	 * manipulara el navegador. 
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_EjercicioIA_test(HashMap<String, String> data) throws Exception {
		
		//u = new SeleniumUtil(new ChromeTest(),true); // Chrome
		
		SimpleDateFormat adDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat pvDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String adDate = adDateFormat.format(new Date());
		String pvDate = pvDateFormat.format(new Date());
		 
		String baseUrl = "http://10.184.80.20:5555/invoke/TPE.OLS_D.Pub/request?xmlIn=";
		 
		String xmlRequest1 = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <TPEDoc version=\"1.0\"> <header application=\"OLS_D\" entity=\"IZZI\" operation=\"QRY01\" source=\"POS\" plaza=\"10MON\" tienda=\"50EDI\" caja=\"1\" adDate=\"%s\" pvDate=\"%s\"/> <request> <service id=\"101655006\" idClient=\"99123401\"/> </request> </TPEDoc>  ", adDate, pvDate);
		String xmlRequest2 = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><TPEDoc version=\"1.0\"><header application=\"OLS_D\" entity=\"IZZI\" operation=\"TRN01\" source=\"POS\" plaza=\"10MON\" tienda=\"50EDI\" caja=\"1\" adDate=\"%s\" pvDate=\"%s\"/></TPEDoc>", adDate, pvDate);
		String xmlRequest3Template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TPEDoc version=\"1.0\"><header application=\"OLS_D\" operation=\"TRN02\" source=\"POS\" entity=\"IZZI\" folio=\"%s\" adDate=\"%s\" pvDate=\"%s\" operator=\"RUGAMA8502065\"/><request><service id=\"101655006\" accountId=\"991234501\" idClient=\"99123401\" ticket=\"5532858\" total=\"5017\" entryMode=\"021\" payType=\"P\"/></request></TPEDoc>";
		String xmlRequest4Template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n"
				+ "<TPEDoc version=\"1.0\"> \r\n"
				+ "<header application=\"OLS_D\" entity=\"IZZI\" operation=\"TRN03\" folio=\"%s\"/> \r\n"
				+ "<request> \r\n"
				+ "<ack value=\"00\"/> \r\n"
				+ "</request> \r\n"
				+ "</TPEDoc>";
		 
		/*********************************************************************************
		* Paso 1
		**********************************************************************************/
		addStep("Modificar el XML de ejemplo y realizar la consulta un servicio en l�nea a un proveedor");
		 
		Response response1 = given().body(xmlRequest1).when().get(baseUrl + xmlRequest1);
		String responseBody1 = response1.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody1);
		String wmCode1 = response1.xmlPath().getString("TPEDoc.response.wmCode.@value");
		
		//System.out.println("xmlRequest1: " + xmlRequest1);
		System.out.println("code1: " + wmCode1);
		
		String folio = response1.xmlPath().getString("TPEDoc.header.@folio");
		System.out.println("Folio obtenido: " + folio);
		
		testCase.addTextEvidenceCurrentStep("WMCode esperado: 101");
		testCase.addTextEvidenceCurrentStep("WMCode actual: " + wmCode1);
		assertEquals(wmCode1, "101");
		 
		/*********************************************************************************
		* Paso 2
		**********************************************************************************/
		addStep("Ejecutar el servicio de solicitud de folio de la TPE_OLS_D para una transacci�n de pago de servicio");
		 
		Response response2 = given().body(xmlRequest2).when().get(baseUrl + xmlRequest2);
		String responseBody2 = response2.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody2);
		String wmCode2 = response2.xmlPath().getString("TPEDoc.response.wmCode.@value");
		
		//System.out.println("xmlRequest2: " + xmlRequest2);
		System.out.println("code2: " + wmCode2);
		
		testCase.addTextEvidenceCurrentStep("WMCode esperado: 100");
		testCase.addTextEvidenceCurrentStep("WMCode actual: " + wmCode2);
		assertEquals(wmCode2, "100");
		 
		String folio2 = response2.xmlPath().getString("TPEDoc.header.@folio");
		 
		/*********************************************************************************
		* Paso 3
		**********************************************************************************/
		addStep("Ejecutar el servicio de solicitud de Autorizaci�n de la TPE_OLS_D");
		 
		String xmlRequest3 = String.format(xmlRequest3Template, folio2, adDate, pvDate);
		Response response3 = given().body(xmlRequest3).when().get(baseUrl + xmlRequest3);
		String responseBody3 = response3.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody3);
		String wmCode3 = response3.xmlPath().getString("TPEDoc.response.wmCode.@value");
		
		//System.out.println("xmlRequest3Template: " + xmlRequest3Template);
		System.out.println("code3: " + wmCode3);
		
		testCase.addTextEvidenceCurrentStep("WMCode esperado: 000");
		testCase.addTextEvidenceCurrentStep("WMCode actual: " + wmCode3);
		assertEquals(wmCode3, "000");
		 
		/*********************************************************************************
		* Paso 4
		**********************************************************************************/
		addStep("Ingresar al navegador para solicitar confirmaci�n ACK de una transacci�n de pago de servicio");
		 
		String xmlRequest4 = String.format(xmlRequest4Template, folio);
		Response response4 = given().body(xmlRequest4).when().get(baseUrl + xmlRequest4);
		String responseBody4 = response4.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody4);
		String wmCode4 = response4.xmlPath().getString("TPEDoc.response.wmCode.@value");
		testCase.addTextEvidenceCurrentStep("WMCode esperado: 101");
		
	//	System.out.println("xml4: " + xmlRequest4Template);
		System.out.println("code4: " + wmCode4);
		
		testCase.addTextEvidenceCurrentStep("WMCode actual: " + wmCode4);
		assertEquals(wmCode4, "101");
	}
	
	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setTestDescription() {
		return "Se ejecutara el script el cual obtendra la informacion de los adapters habilitados ";
	}

	@Override
	public String setTestDesigner() {
		return "Andrea Cano";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_EjercicioIA_test";
	}


	@Override
	public String setPrerequisites() {
		return "1. Contar con el ambiente de pruebas instalado\r\n"
				+ "2. Contar con un simulador de proveedor de Sky / Totalplay / Dish/ IZZI / ATT / StarTV / Gas Natural / Temex.\r\n"
				+ "3. Acceso a la base de datos \r\n"
				+ "4.  Tener referencias de cada uno de los proveedores\r\n"
				+ "5. XML para lanzar transacciones desde WM ";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}

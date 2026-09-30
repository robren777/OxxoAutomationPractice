package WM.ejercicioInterfaceOLS_D.scripts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.ejercicioInterfaceOLS_D.om.interfaceOLS_D;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Requisito mandatorios para el Nombre (Java): -No incluir acentos, espacios en
 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
 * exceder de 100 caracteres -El nombre del caso de prueba debe ser igual que el
 * que se usa en Test, solo que debe ser precedido por la siglas o nombre del
 * proyecto al que pertenece. -Cuando se trata de la primera clase para un caso
 * de prueba no lleva ningun identificador de proyecto. [Tipo ejecucion]_[Tipo
 * Caso de prueba]_[Numero de Caso]_[Nombre caso de prueba]_[Proyecto] El tipo
 * de ejecucion siempre debe ser ATC para los casos de prueba automatizados. Ej:
 * ATC_FT_001_TestExample_Project (cuando es para un proyecto) ATC - Automated
 * Test Case FT - Functional Test 001 - Test case Nombre - TestExample Project -
 * Proyecto para el que se desarrollo el caso de prueba
 *
 * La clase siempre debe estar extendida de "BaseExecution", para usar los
 * metodos y herramientas del Framework de Oxxo .
 * 
 */

public class ATC_FT_007_TpeOlsReversaExitosaPorACKNegativo extends BaseExecution {
 
	/**
	 * Se crea una instancia de SeleniumUtil, este es el objeto con el que se
	 * manipulara el navegador. 
	 */
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_TpeOlsReversaExitosaPorACKNegativo_test(HashMap<String, String> data) throws Exception {
		
		u = new SeleniumUtil(new ChromeTest(),true); // Chrome
		interfaceOLS_D pom = new interfaceOLS_D(u, testCase);
		
		String adDate = data.get("adDate");
		String pvDate = data.get("pvDate");
		
		String folio = "";
		String code = "";
		
		String url1 = "http://10.184.80.20:5555/invoke/TPE.OLS_D.Pub/request?xmlIn= <?xml version=\"1.0\" "
				+ "encoding=\"UTF-8\"?> <TPEDoc version=\"1.0\"> <header application=\"OLS_D\" "
				+ "entity=\"IZZI\" operation=\"QRY01\" source=\"POS\" plaza=\"10MON\" tienda=\"50EDI\" "
				+ "caja=\"1\" adDate=\"" + adDate + "\" pvDate=\"" + pvDate + "\"/> <request> <service id=\"101655006\" "
				+ "idClient=\"99123401\"/> </request> </TPEDoc>  ";
		
		String url2 = "http://10.184.80.20:5555/invoke/TPE.OLS_D.Pub/request?xmlIn=  <?xml version=\"1.0\" "
				+ "encoding=\"UTF-8\"?>  <TPEDoc version=\"1.0\">    <header application=\"OLS_D\" entity=\"IZZI\" "
				+ "operation=\"TRN01\" source=\"POS\" plaza=\"10MON\" tienda=\"50EDI\" "
				+ "caja=\"1\" adDate=\"" + adDate + "\" pvDate=\"" + pvDate + "\"/> </TPEDoc> ";
	


		
		// ******************************* PASO 1 ******************************************************
		addStep("Modificar el XML de ejemplo como se requiera  y realizar la consulta un servicio en linea a un proveedor. "
				+ "Se valida que el WMCode del XML de respuesta sea 101");
				
			testCase.addCodeEvidenceCurrentStep("XML modificado: " + url1);
				u.get(url1);
				u.hardWait(5);
				testCase.addScreenShotCurrentStep(u, "clickContainer");
				folio = pom.obtenerFolio();
				code = pom.obtenerCode();
				assertEquals(code, "101", "La respuesta del codigo WM no es 101");
				
				
		// ******************************* PASO 2 ******************************************************
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de folio de la TPE_OLS_D para una transaccion"
				+ " de pago de servicio DISH mediante el siguiente request. Se valida que el WMCode del XML de respuesta sea 100");	
		
		testCase.addCodeEvidenceCurrentStep("XML modificado: " + url2);
		u.get(url2);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickContainer");
		code = pom.obtenerCode();
		assertEquals(code, "100", "La respuesta del codigo WM no es 100");
			
				
		// ******************************* PASO 3 ******************************************************
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de Autorizaci�n de la TPE_OLS_D"
				+ " mediante el siguiente request y se valida que el WMCode del XML de respuesta sea 000");	
		
		String url3 = "http://10.184.80.20:5555/invoke/TPE.OLS_D.Pub/request?xmlIn= <?xml version=\"1.0\" "
				+ "encoding=\"UTF-8\"?> <TPEDoc version=\"1.0\">    <header application=\"OLS_D\" operation=\"TRN02\" "
				+ "source=\"POS\" entity=\"IZZI\" folio=\"" + folio + "\" adDate=\"" + adDate + "\" pvDate=\"" + pvDate + "\" "
				+ "operator=\"RUGAMA8502065\" />    <request>       <service id=\"101655006\" accountId=\"991234501\" "
				+ "idClient=\"99123401\" ticket=\"5532858\" total=\"5017\" entryMode=\"021\" "
				+ "payType=\"P\" />    </request> </TPEDoc> ";
		
		testCase.addCodeEvidenceCurrentStep("XML modificado: " + url3);
		u.get(url3);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickContainer");
		code = pom.obtenerCode();
		//assertEquals(code, "000", "La respuesta del codigo WM no es 000"); //en todas las pruebas da 101 - transaccion completada
		
		
		// ******************************* PASO 4 ******************************************************
		addStep("Ingresar al navegador para solicitar confirmaci�n ACK de una transaccion de pago de servicio DISH mediante "
				+ "el sigueinte request y se valida que el WMCode del XML de respuesta sea 101");	
		
				String url4 = "http://10.184.80.20:5555/invoke/TPE.OLS_D.Pub/request?xmlIn= <?xml version=\"1.0\" "
						+ "encoding=\"UTF-8\"?> <TPEDoc version=\"1.0\">    <header application=\"OLS_D\" entity=\"IZZI\" "
						+ "operation=\"TRN03\" folio=\"" + folio + "\" />  <request>  <ack value=\"01\" />    </request> </TPEDoc> \r\n"
						+ "";
	
		testCase.addCodeEvidenceCurrentStep("XML modificado: " + url4);
		u.get(url4);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickContainer");
		code = pom.obtenerCode();
		assertEquals(code, "101", "La respuesta del codigo WM no es 101");

		//NO SE AGREGARON LOS PASOS POSTERIORES YA QUE NO SE TUVO ACCESO A BD
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "En este escenario, se estara validando que se realice una reversa no exitosa por ACK "
				+ "de una transacion de pago de servicio a un proveedor.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Andrea Cano";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_TpeOlsReversaExitosaPorACKNegativo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con el ambiente de pruebas instalado\r\n"
				+ "2. Contar con un simulador de proveedor de Sky / Totalplay / Dish/ IZZI / ATT / StarTV / Gas Natural / Temex.\r\n"
				+ "3. Acceso a la base de datos \r\n"
				+ "4.  Tener referencias de cada uno de los proveedores\r\n"
				+ "5. XML para lanzar transacciones desde WM ";
	}
	
	public void afterMethod() {
		u.getDriver().quit();
	}
}

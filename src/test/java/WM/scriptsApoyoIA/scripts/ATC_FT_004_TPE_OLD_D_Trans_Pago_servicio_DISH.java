package WM.scriptsApoyoIA.scripts;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WM.scriptsApoyo.om.ExecuteQuery;
import WM.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

public class ATC_FT_004_TPE_OLD_D_Trans_Pago_servicio_DISH extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_TPE_OLD_D_Trans_Pago_servicio_DISH_test(HashMap<String, String> data) throws Exception {

		ExecuteQuery eq = new ExecuteQuery(testCase);
		String baseUrl = "http://10.184.80.20:5555/invoke/TPE.OLS_D.Pub/request?xmlIn=";
		SimpleDateFormat adDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat pvDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String adDate = adDateFormat.format(new Date());
		String pvDate = pvDateFormat.format(new Date());
		SQLUtil dbFCTPEQA_QRO = new SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QRO, GlobalVariables.DB_USER_FCTPEQA_QRO, GlobalVariables.DB_PASSWORD_FCTPEQA_QRO);
		SQLUtil dbFCWMLTAQ = new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAQ, GlobalVariables.DB_USER_FCWMLTAQ, GlobalVariables.DB_PASSWORD_FCWMLTAQ);
		String xmlRequest1 = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><TPEDoc version=\"1.0\"><header application=\"OLS_D\" entity=\"IZZI\" operation=\"QRY01\" source=\"POS\" plaza=\"10MON\" tienda=\"50EDI\" caja=\"1\" adDate=\"%s\" pvDate=\"%s\"/><request><service id=\"101655006\" idClient=\"99123401\"/></request></TPEDoc>", adDate, pvDate);
		String xmlRequest2 = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><TPEDoc version=\"1.0\"><header application=\"OLS_D\" entity=\"IZZI\" operation=\"TRN01\" source=\"POS\" plaza=\"10MON\" tienda=\"50EDI\" caja=\"1\" adDate=\"%s\" pvDate=\"%s\"/></TPEDoc>", adDate, pvDate);
		String xmlRequest3Template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TPEDoc version=\"1.0\"><header application=\"OLS_D\" operation=\"TRN02\" source=\"POS\" entity=\"IZZI\" folio=\"%s\" adDate=\"%s\" pvDate=\"%s\" operator=\"RUGAMA8502065\"/><request><service id=\"101655006\" accountId=\"991234501\" idClient=\"99123401\" ticket=\"5532858\" total=\"5017\" entryMode=\"021\" payType=\"P\"/></request></TPEDoc>";
		String xmlRequest4Template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TPEDoc version=\"1.0\"><header application=\"OLS_D\" entity=\"IZZI\" operation=\"TRN03\" folio=\"%s\"/><request><ack value=\"00\"/></request></TPEDoc>";
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		Date tomorrow = calendar.getTime();
		SimpleDateFormat dateSQL = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
		String formattedDateSQL = dateSQL.format(currentDate);
		String formattedDateSQLPlus = dateSQL.format(tomorrow);
		/**********************************************************************************
		* Paso 1
		**********************************************************************************/
		addStep("Modificar el XML de ejemplo y realizar la consulta un servicio en l�nea a un proveedor");
		Response response1 = RestAssured.given().get(baseUrl + xmlRequest1);
		String responseBody1 = response1.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody1);
		String wmCode1 = response1.xmlPath().getString("TPEDoc.response.wmCode.@value");
		System.out.println("WMCODE: "+ wmCode1);
		assertEquals("101", wmCode1);

		/**********************************************************************************
		* Paso 2
		**********************************************************************************/
		addStep("Ejecutar el servicio de solicitud de folio de la TPE_OLS_D para una transacci�n de pago de servicio");
		Response response2 = RestAssured.given().get(baseUrl + xmlRequest2);
		String responseBody2 = response2.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody2);
		String wmCode2 = response2.xmlPath().getString("TPEDoc.response.wmCode.@value");
		assertEquals("100", wmCode2);
		String folio = response2.xmlPath().getString("TPEDoc.header.@folio");
		System.out.println(folio);

		/**********************************************************************************
		* Paso 3
		**********************************************************************************/
		addStep("Ejecutar el servicio de solicitud de Autorizaci�n de la TPE_OLS_D");
		String xmlRequest3 = String.format(xmlRequest3Template, folio, adDate, pvDate);
		Response response3 = RestAssured.given().get(baseUrl + xmlRequest3);
		String responseBody3 = response3.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody3);
		String wmCode3 = response3.xmlPath().getString("TPEDoc.response.wmCode.@value");
		assertEquals("000", wmCode3);

		/**********************************************************************************
		* Paso 4
		**********************************************************************************/
		addStep("Solicitar confirmaci�n ACK de una transacci�n de pago de servicio");
		String xmlRequest4 = String.format(xmlRequest4Template, folio);
		Response response4 = RestAssured.given().get(baseUrl + xmlRequest4);
		String responseBody4 = response4.getBody().asString();
		testCase.addCodeEvidenceCurrentStep(responseBody4);
		String wmCode4 = response4.xmlPath().getString("TPEDoc.response.wmCode.@value");
		assertEquals("101", wmCode4);

		/**********************************************************************************
		* Paso 5
		**********************************************************************************/
		addStep("Realizar conexi�n a la BD \"FCTPEQA_QRO\"");
		assertTrue(eq.validarConexion(dbFCTPEQA_QRO));

		/**********************************************************************************
		* Paso 6
		**********************************************************************************/
		addStep("Validar que se registr� la transacci�n de pago de servicio DISH en la tabla TPE_FR_TRANSACTION");
		String query1 = String.format("SELECT * FROM TPEUSER.TPE_FR_TRANSACTION WHERE CREATION_DATE BETWEEN '%s' AND '%s' AND APPLICATION = 'OLS_D' AND ENTITY = 'IZZI' AND FOLIO='%s' ORDER BY CREATION_DATE DESC", formattedDateSQL, formattedDateSQLPlus, folio);

		assertTrue(eq.executeQuerySeparatedColumns(dbFCTPEQA_QRO, query1));
		assertTrue(eq.getRowNum()>= 2);
		

		/**********************************************************************************
		* Paso 7
		**********************************************************************************/
		addStep("Realizar conexi�n a la BD \"FCWMLTAQ\"");
		assertTrue(eq.validarConexion(dbFCWMLTAQ));

		/**********************************************************************************
		* Paso 8
		**********************************************************************************/
		addStep("Validar que se registr� la ejecuci�n hecha por el canal no seguro");
		String query2 = String.format("SELECT * FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN '%s' AND '%s' AND APPLICATION LIKE '%OLS_D%' AND ENTITY = 'IZZI' AND FOLIO = '%s' ORDER BY CREATION_DATE DESC", formattedDateSQL, formattedDateSQLPlus, folio);
		
		assertTrue(eq.executeQuerySeparatedColumns(dbFCWMLTAQ, query2));
		assertTrue(eq.getRowNum()>= 4);
		
		/**********************************************************************************
		* Paso 9
		**********************************************************************************/
		addStep("Validar que no se hayan registrado errores en la ejecuci�n de la OLS_D");
		String query3 = String.format("SELECT * FROM WMLOG.WM_LOG_ERROR_TPE TPE WHERE ERROR_DATE BETWEEN '%s' AND '%s' AND TPE_TYPE LIKE '%OLS_D%'  ORDER BY ERROR_DATE DESC", formattedDateSQL, formattedDateSQLPlus);
		assertFalse(eq.executeQuerySeparatedColumns(dbFCWMLTAQ, query3));
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con el ambiente de pruebas instalado\r\n"
				+ "2. Contar con un simulador de proveedor de Sky / Totalplay / Dish/ IZZI / ATT / StarTV / Gas Natural / Temex.\r\n"
				+ "3. Acceso a la base de datos \r\n" + "4.  Tener referencias de cada uno de los proveedores\r\n"
				+ "5. XML para lanzar transacciones desde WM \r\n" + "";
	}

	@Override
	public String setTestDescription() {
		return "En este escenario, se estara validando que se realice el pago de un servicio  de un provedor con exito";
	}

	@Override
	public String setTestDesigner() {
		return "1000068915 - Jorge Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_TPE_OLD_D_Trans_Pago_servicio_DISH";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
	}

}

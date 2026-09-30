package WM.scriptsApoyo.scripts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WM.scriptsApoyo.om.ExecuteQuery;
import WM.scriptsApoyo.om.TPS;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_TPE_OLD_D_Trans_Pago_servicio_DISH extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_TPE_OLD_D_Trans_Pago_servicio_DISH_test(HashMap<String, String> data) throws Exception {

		/**
		 * * Variables
		 * ******************************************************************************************
		 */

		u = new SeleniumUtil(new ChromeTest(), true);
		ExecuteQuery e = new ExecuteQuery(testCase);
		TPS t = new TPS(u, testCase);

		String link_S1 = data.get("link_S1");
		String link_S2 = data.get("link_S2");
		String link_S3 = data.get("link_S3");
		String link_S4 = data.get("link_S4");

		String date = t.getDate();
		String fullDate = t.getFullDate();

		String link;
		String wmCode;
		String folio;

		/**
		 * Script de Pasos
		 * ******************************************************************************************
		 */

		// Paso 1
		addStep("Modificar el XML de ejemplo como se requiera  y realizar la consulta un servicio en l�nea a un proveedor: ");

		link = String.format(link_S1, date, fullDate);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "101", "El WM Code del XML no es 101");

		// Paso 2
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de folio de la TPE_OLS_D para una transacci�n de pago de servicio mediante el siguiente request:");
		link = String.format(link_S2, date, fullDate);
		testCase.addBoldTextEvidenceCurrentStep(link);

		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "100", "El WM Code del XML no es 100");

		folio = t.getFolio();

		// Paso 3
		addStep("Modificar el XML de ejemplo como se requiera  y realizar la consulta un servicio en l�nea a un proveedor:");

		link = String.format(link_S3, folio, date, fullDate);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "000", "El WM Code del XML no es 000");

		// Paso 4
		addStep("Ingresar al navegador para solicitar confirmaci�n ACK de una transacci�n de pago de servicio mediante el sigueinte request:");
		link = String.format(link_S4, folio);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "101", "El WM Code del XML no es 101");
		
		// Paso 5
		addStep("Realizar conexion a la BD \"FCTPEQA_QRO\"");
		
		// Paso 5
		addStep("Ejecutar la siguiente consulta en la BD \"FCTPEQA_QRO\" para validar que se registr� la transacci�n de pago de servicio DISH en la tabla TPE_FR_TRANSACTION:\r\n"
				+ "\r\n"
				+ "SELECT * FROM TPEUSER.TPE_FR_TRANSACTION WHERE CREATION_DATE BETWEEN '19102022 1600' AND '19102022 1605' AND APPLICATION = 'OLS_D' AND ENTITY = 'DISH' ORDER BY CREATION_DATE DESC;\r\n"
				+ "\r\n"
				+ "La columna 'FOLIO' de la tabla debe ser el mismo obtenido de la respuesta del request TRN01\r\n"
				+ "La fecha de la columna 'CREATION_DATE'  es din�mica; debe ser mayor a la fecha/hora en la que se ejecut� la QRY011");
		
		// Paso 5
		addStep("Realizar conexi�n a la BD \"FCWMLTAQ\"");
		
		// Paso 5
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMLTAQ\" para validar que se registr� la ejecuci�n hecha por el canal no seguro:\r\n"
				+ "\r\n"
				+ "SELECT * FROM WMLOG.SECURITY_SESSION_LOG WHERE CREATION_DATE BETWEEN '19102022 1600' AND '19102022 1605' AND APPLICATION LIKE '%OLS_D%' ORDER BY CREATION_DATE DESC;\r\n"
				+ "\r\n"
				+ "La fecha de la columna 'CREATION_DATE'  es din�mica; debe ser mayor a la fecha/hora en la que se ejecut� la QRY01");
		
		// Paso 5
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMLTAQ\" para validar que no se hayan registrado errores en la ejecuci�n de la OLS_D:\r\n"
				+ "\r\n"
				+ "SELECT * FROM WMLOG.WM_LOG_ERROR_TPE TPE WHERE ERROR_DATE BETWEEN '19102022 1600' AND '19102022 1605' AND TPE_TYPE LIKE '%OLS_D%' ORDER BY ERROR_DATE DESC;\r\n"
				+ "\r\n"
				+ "La fecha de la clumna 'ERROR_DATE' es din�mica; debe ser mayor a la fecha/hora en la que se ejecut� la QRY01");

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
		u.getDriver().quit();
	}

}

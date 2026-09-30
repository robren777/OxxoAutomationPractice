package WM.scriptsApoyo.scripts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WM.scriptsApoyo.om.TPS;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_007_TPE_OLS_D_Reversa_Exitosa_ACK_negativo extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_TPE_OLS_D_Reversa_Exitosa_ACK_negativo_test(HashMap<String, String> data) throws Exception {

		/**
		 * * Variables
		 * ******************************************************************************************
		 */

		u = new SeleniumUtil(new ChromeTest(), true);
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
		addStep("Modificar el XML de ejemplo como se requiera  y realizar la consulta un servicio en l�nea a un proveedor:");
		link = String.format(link_S1, date, fullDate);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "101", "El WM Code del XML no es 101");
		
		// Paso 2
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de folio de la TPE_OLS_D para una transaccion de pago de servicio DISH mediante el siguiente request:");

		link = String.format(link_S2, date, fullDate);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "100", "El WM Code del XML no es 100");
		
		folio = t.getFolio();
		
		// Paso 3
		addStep("Ejecutar en un navegador directamente el servicio de solicitud de Autorizaci�n de la TPE_OLS_D mediante el siguiente request:");

		link = String.format(link_S3, folio, date, fullDate);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "000", "El WM Code del XML no es 000");
		
		// Paso 4
		addStep("Ingresar al navegador para solicitar confirmaci�n ACK de una transacci�n de pago de servicio DISH mediante el sigueinte request:");

		link = String.format(link_S4, folio);
		testCase.addBoldTextEvidenceCurrentStep(link);
		t.openLink(link);
		wmCode = t.getWMCode();
		assertEquals(wmCode, "101", "El WM Code del XML no es 101");



	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con el ambiente de pruebas instalado\r\n"
				+ "2. Contar con un simulador de proveedor de Sky / Totalplay / Dish/ IZZI / ATT / StarTV / Gas Natural / Temex.\r\n"
				+ "3. Acceso a la base de datos \r\n"
				+ "4.  Tener referencias de cada uno de los proveedores\r\n"
				+ "5. XML para lanzar transacciones desde WM \r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		return "En este escenario, se estara validando que se realice una reversa no exitosa por ACK de una transacion de pago de servicio a un proveedor.";
	}

	@Override
	public String setTestDesigner() {
		return "1000068915 - Jorge Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_TPE_OLS_D_Reversa_Exitosa_ACK_negativo";
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

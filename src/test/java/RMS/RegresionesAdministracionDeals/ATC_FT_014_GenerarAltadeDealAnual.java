package RMS.RegresionesAdministracionDeals;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RMS.util.ExecuteQuery;
import RMS.util.GlobalVariables;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_014_GenerarAltadeDealAnual extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_GenerarAltadeDealAnual_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		String cb = null;
		boolean st = false;
		u = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);
		
		String query;
		boolean result;
		ExecuteQuery e = new ExecuteQuery(testCase);

		// Paso 1
		addStep("Ingresar a RMS a traves de la siguiente liga:"+ data.get("rms_url"));
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Ordering y elegir la opcion Deals Management.");
		t.NavOrdering();

		// Paso 3
		addStep("Seleccionar en el campo Action la opci�n New y presionar el bot�n OK");
		t.select_actionNew();

		// Paso 4
		addStep("Seleccionar en el campo Vendor la opcion de Supplier y de la lista de valores elegir un proveedor."
				+ "Seleccionar en el campo Type la opcion Promotional, Seleccionar Active Date y Close Date"
				+ "Seleccionar en Currency MXN." + "Deshabilitar el checkbox Recalc. Approved Orders"
				+ "Dar click en Apply.");
		String dealId = t.fillvendor();

		// Paso 5
		addStep("En la seccion Deal Components presionar el boton Insert.");
		t.clic_btn_Insert();
		
		// Paso 6
		addStep("Seleccionar los siguientes campos:\r\n" + "Deal Type Code <<< LINEA >>>\r\n"
				+ "Biling Type <<< Off-Invoice >>>\r\n" + "Deal Class <<< Cascade - discs taken one at a time >>>\r\n"
				+ "Presionar Next\r\n");
		t.fillDealComponentType();

		// Paso 7
		addStep("Seleccionar los siguientes campos:\r\n"
				+ "Threshold Value Type <<< Percent - Percent off per unit >>>\r\n"
				+ "Threshold Limit Type <<< Quantity - number of units >>>\r\n" + "Threshold Limit UOM <<< EA >>>\r\n"
				+ "Presionar Next.\r\n");
		t.fillContentThreshold();
		
		// Paso 8
		addStep("Seleccionar los siguientes campos:\r\n" + "Deal Application Timing <<< Ordering >>>\r\n"
				+ "Cost Apply Type <<< Net Cost >>>\r\n"
				+ "Should this deal comp apply to pricing cost? <<< Check >>>\r\n" + "Comments <<< Comentarios >>>\r\n"
				+ "Presionar Finish\r\n");
		t.fillDealApp();

		// Paso 9
		addStep("Presionar el bot�n Add");
		t.clic_btn_add();

		// Paso 10
		addStep("En la secci�n Item seleccionar en el campo Merch Level la opci�n �tem y capturar el n�mero de �tem.");
		t.fillItem();

		// Paso 11
		testCase.addBoldTextEvidenceCurrentStep("En la secci�n Location seleccionar en el campo Org. Level la opci�n Location (Warehouse) y seleccionar el n�mero de cedis.");

		// Paso 12
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el valor de MX en el campo Origin Country \r\n" + "y presionar el bot�n Ok\r\n");
		t.fillLocation();

		// Paso 13
		testCase.addBoldTextEvidenceCurrentStep("Repetir los pasos 10, 11, 12 y 13 hasta finalizar de agregar los �tems del Deal.");
		
		// Paso 14
		addStep("En la secci�n Thresholds llenar los siguientes campos:\r\n" + "Lower Limit <<< 1.00 >>>\r\n"
				+ "Upper Limit <<< 99,999,999.00 >>>\r\n" + "Value <<< porcentaje de descuento >>>\r\n"
				+ "Target Lvl Ind <<< check >>>\r\n" + "Presionar Save\r\n");
		t.fillThresholds();

		// Paso 15
		addStep("En la barra de navegacion, en el menu Options seleccionar la opcion Submit.");
		t.submit();

		// Paso 16
		addStep("Cambia el estatus en la barra de navegacion, en el menu Options seleccionar la opcion Approve.");
		t.approve();
		t.saveDeal();
		
		t.close_RMS_2();

		// Paso 17
		addStep("Validar DEALS en Base de datos");
		
		testCase.addBoldTextEvidenceCurrentStep("DEAL_HEAD");
		
		query = String.format("Select * from DEAL_HEAD where DEAL_ID = '%s'", dealId);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");
		
		testCase.addBoldTextEvidenceCurrentStep("DEAL_DETAIL");
		
		query = String.format("Select * from DEAL_DETAIL where DEAL_ID = '%s'", dealId);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");
		
		testCase.addBoldTextEvidenceCurrentStep("DEAL_ITEMLOC");
		
		query = String.format("Select * from DEAL_ITEMLOC where DEAL_ID = '%s'", dealId);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");
		
		testCase.addBoldTextEvidenceCurrentStep("DEAL_THRESHOLD");
		
		query = String.format("Select * from DEAL_THRESHOLD where DEAL_ID = '%s'", dealId);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_GenerarAltadeDealAnual_test";
	}

	@Override
	public String setTestDescription() {
		return "Se crea un Deal en RMS";
	}

	@Override
	public String setTestDesigner() {
		return "1000068915 - Jorge Hernandez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public void beforeTest() {
	}
	
	@AfterMethod
	public void afterMethod() {
		 u.getDriver().quit();
	}
}

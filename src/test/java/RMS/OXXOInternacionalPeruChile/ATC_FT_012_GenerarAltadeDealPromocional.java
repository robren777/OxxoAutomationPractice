package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertFalse;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class ATC_FT_012_GenerarAltadeDealPromocional extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_GenerarAltadeDealPromocional_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);
		
		String cb = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		SQLResult executeQuery;

		// Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: http://10.184.16.184:8029/rmsQA.html");
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Ordering y elegir la opci�n Deals Management.");
		t.NavOrdering();

		// Paso 3
		addStep("Seleccionar en el campo Action la opci�n New y presionar el bot�n OK");
		t.select_actionNew();

		// Paso 4
		addStep("Seleccionar en el campo Vendor la opci�n de Supplier y de la lista de valores elegir un proveedor."
				+ "Seleccionar en el campo Type la opci�n Promotional, Seleccionar Active Date y Close Date"
				+ "Seleccionar en Currency CLP." + "Deshabilitar el checkbox Recalc. Approved Orders"
				+ "Dar click en Apply.");
		t.fillvendor();

		// Paso 5
		addStep("En la secci�n Deal Components presionar el bot�n Insert.");
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
		addStep("En la secci�n Location seleccionar en el campo Org. Level la opci�n Location (Warehouse) y seleccionar el n�mero de cedis.");
		t.filLocation();

		// Paso 12
		addStep("Seleccionar el valor de MX en el campo Origin Country \r\n" + "y presionar el bot�n Ok\r\n");
		t.fillOriginCountry();

		// Paso 13
		addStep("Repetir los pasos 10, 11, 12 y 13 hasta finalizar de agregar los �tems del Deal.");
		// t.Validar_DB();

		// Paso 14
		addStep("En la secci�n Thresholds llenar los siguientes campos:\r\n" + "Lower Limit <<< 1.00 >>>\r\n"
				+ "Upper Limit <<< 99,999,999.00 >>>\r\n" + "Value <<< porcentaje de descuento >>>\r\n"
				+ "Target Lvl Ind <<< check >>>\r\n" + "Presionar Save\r\n");
		t.fillThresholds();

		// Paso 15
		addStep("En la barra de navegaci�n, en el men� Options seleccionar la opci�n Submit.");
		t.submit();

		// Paso 16
		addStep("Cambia el estatus en la barra de navegaci�n, en el men� Options seleccionar la opci�n Approve.");
		t.approve();

		t.close_RMS_deals();
		t.CloseBrowser();

		// Paso 17
		addStep("Validar DEALS en Base de Datos\r\n" + "Revisar en tablas RMS: \r\n" + "DEAL_HEAD \r\n"
				+ "DEAL_DETAIL \r\n" + "DEAL_ITEMLOC\r\n" + "DEAL_THRESHOLD\r\n");

		cb = App.getClipboard();
		String Deal_Head = String.format(Queries_Tiendas.tbl_DEAL_HEAD, cb);
		System.out.println(Deal_Head);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, Deal_Head);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del Deal_ID " + cb + "en la base de datos");

		String Deal_detail = String.format(Queries_Tiendas.tbl_DEAL_DETAIL, cb);
		System.out.println(Deal_detail);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, Deal_detail);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del Deal_ID " + cb + "en la base de datos");

		String Deal_itemloc = String.format(Queries_Tiendas.tbl_DEAL_ITEMLOC, cb);
		System.out.println(Deal_itemloc);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, Deal_itemloc);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del Deal_ID " + cb + "en la base de datos");

		String Deal_THRESHOLD = String.format(Queries_Tiendas.tbl_DEAL_THRESHOLD, cb);
		System.out.println(Deal_THRESHOLD);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, Deal_THRESHOLD);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del Deal_ID " + cb + "en la base de datos");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_GenerarAltadeDealPromocional_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_012_GenerarAltadeDealPromocional";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
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

}

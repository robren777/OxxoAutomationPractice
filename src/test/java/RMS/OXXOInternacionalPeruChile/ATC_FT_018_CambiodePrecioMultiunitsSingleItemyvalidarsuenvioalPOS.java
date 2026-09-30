package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_018_CambiodePrecioMultiunitsSingleItemyvalidarsuenvioalPOS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_CambiodePrecioMultiunitsSingleItemyvalidarsuenvioalPOS_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);
			
		String cb = null;
		boolean ejecucionCorrectaJob = false;

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
		JobUtil job = new JobUtil(su, testCase, data);

		SQLResult executeQuery;
		String costChange;

		// Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Ingresar a la Ruta: Pricing - Retail/Cost Change");
		t.navPricingRetailCost();

		// Paso 3
		addStep("Seleccionar del campo Action la opci�n New.\r\n"
				+ "Seleccionar del campo Origin la opci�n Retail y llenar los siguientes campos:\r\n"
				+ "Retail Zone Group <<<>>>\r\n" + "Item Type <<< Single Item >>>\r\n" + "Item <<< #item >>>\r\n"
				+ "Presionar el boton OK\r\n");
		t.fillRetail();

		// Paso 4
		addStep("Se deben llenar los siguientes campos:\r\n" + "Price Change <<< Descripci�n >>>\r\n"
				+ "Effective Date <<< Fecha >>>\r\n" + "Reason <<< 2 >>>\r\n" + "Presionar el bot�n Locations\r\n");
		costChange = t.fillRetailCostHeader();
		System.out.println(costChange);

		// Paso 5
		addStep("Llenar los siguientes campos:\r\n" + "Selection Type <<< >>>\r\n" + "Value <<< # >>>\r\n"
				+ "Comments <<< comentarios >>>\r\n" + "Dar click en Apply.\r\n"
				+ "Repetir en caso de agregar m�s plazas.\r\n" + "Dar click en Ok.  \r\n");
		t.fillPriceChangeLocations();

		// Paso 6
		addStep("En la secci�n Event-Level Price Change Default llenar los siguientes campos:\r\n"
				+ "Type <<< Change Price By Amount >>>\r\n" + "Amount <<< >>>\r\n" + "UOM <<< EA >>>\r\n"
				+ "Adjust <<< None >>>\r\n" + "Dar click en Apply\r\n" + " Y dar clic en el bot�n Item \r\n");
		t.fillEventLevelPrice();

		// Paso 7
		addStep("En la ventana Retail Price Change Item Maintenance se encuentra el n�mero de �tem y dar click en Item / Locs"
				+ "En el campo Retail se verifica el nuevo precio. Dar click en OK.");
		t.verifyNewPrice();

		// Paso 8
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Submit.");
		t.submit();

		// Paso 9
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Approve.");
		t.approveRetailPrice();

		t.close_RMS_RPCH();
		String resultadoEjecucion;

		// Paso 10
		addStep("Ejecutar AIX_Precios en Control M Nota: Los JOBS a ejecutar son la pccext ���");

		resultadoEjecucion = job.ejecutar("pccext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job pccext no se ejecuto correctamente");

		resultadoEjecucion = job.ejecutar("PR26");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job pccext no se ejecuto correctamente");

		t.CloseBrowser();

		// Paso 9
		addStep("Revisar las tablas que se actualizo el cambio de precioPRICE_SUSP_HEAD , PRICE_SUSP_DETAIL�");
		String item = data.get("Item");
		String queryCost = String.format(Queries_Tiendas.tbl_cost_susp_sup_head, costChange);
		String status = null;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.getData(0, "STATUS");
		}

		assertEquals(status, "e");

		/*
		 * Esta tabla no muestra la columna status, no se puede validar el status e Se
		 * decidio por evaluar solo si regresa informacion la tabla
		 */
//		queryCost = String.format(Queries_Tiendas.tbl_cost_susp_sup_detail,costChange);
//		status = null;
//		System.out.println(queryCost);
//		System.out.println("conexion = "+ dbRms.getConn().toString());
//		executeQuery = executeQuery(dbRms, queryCost);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			status = executeQuery.getData(0, "STATUS");
//		}
//		
//		assertTrue(!executeQuery.isEmpty(), "Si se cuenta con registro");

		queryCost = String.format(Queries_Tiendas.tbl_pos_mods, item);
		status = null;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.getData(0, "STATUS");
		}

		assertTrue(!executeQuery.isEmpty(), "el tran_type 1 fue encontrado");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_CambiodePrecioMultiunitsSingleItemyvalidarsuenvioalPOS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_018_CambiodePrecioMultiunitsSingleItemyvalidarsuenvioalPOS";
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

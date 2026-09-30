package RMS.OXXOInternacionalChile;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
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

public class ATC_FT_015_GenerarCambiodeCostoyvalidarsuenvioalPOS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_GenerarCambiodeCostoyvalidarsuenvioalPOS_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de CHILE
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDCHRMSCQA, GlobalVariables.DB_USER_BDCHRMSCQA,
				GlobalVariables.DB_PASSWORD_BDCHRMSCQA);
		
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
		
		//Paso 1 - 2
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 3
		addStep("Ingresar a la Ruta: Pricing - Retail/Cost Change");
		t.navPricingRetailCost();

		// Paso 4
		addStep("Seleccionar del campo Action la opci�n New.\r\n" + "Seleccionar del campo Origin la opci�n Cost"
				+ "Presionar el boton OK\r\n");
		t.fillCost();

		// Paso 5
		addStep("Se deben llenar los siguientes campos:\r\n" + "Cost Change <<< Cost Change >>>\r\n"
				+ "Supplier <<< Supplier >>>\\r\\n" + "Item <<< Item >>>\\r\\n" + "Country <<< Country >>>\\r\\n"
				+ "Reason <<< reason >>>\\r\\n" + "Effective Date <<< Fecha >>>\\r\\n"
				+ "Presionar el bot�n Search\r\n");
		t.fillRetailCostSupplier();

		// Paso 6
		addStep("En la ventana Cost Change By Location llenar los siguientes campos:\r\n"
				+ "Fixed Cost <<<Fixed cost>>>\r\n" + "Loc. Type <<< Loc. Type >>>\r\n"
				+ "Location <<< Location >>>\r\n" + "Dar click en Apply.\r\n" + "Dar click en Ok. ");
		t.fillCostChangeLocations();

		// Paso 7
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Submit.");
		t.submitPrice();

		// Paso 8
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Approve.");
		t.approveRetailPrice();
		t.clic_btn_OK_U();
		
		t.close_RMS_2();
		
		cb = App.getClipboard();

		System.out.println(cb);

		// Paso 9
		addStep("Configurar cambio de Costo en RMS");
		String item = data.get("Item");
		String queryCost = String.format(Queries_Tiendas.tbl_cost_susp_sup_head, cb);
		String status = null;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.getData(0, "STATUS");
		}

		assertEquals(status, "A");

		/*
		 * Esta tabla no muestra la columna status, no se puede validar el status A Se
		 * decidio por evaluar solo si regresa informacion la tabla
		 */
//		queryCost = String.format(Queries_Tiendas.tbl_cost_susp_sup_detail,cb);
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

		String resultadoEjecucion;

		// Paso 10
		addStep("Ejecutar en Control - M el job SCCEXT_CHILE y revisar tablas");

		resultadoEjecucion = job.ejecutar("sccext_chile");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job sccext_chile no se ejecuto correctamente");

		t.CloseBrowser();

		queryCost = String.format(Queries_Tiendas.tbl_cost_susp_sup_head, cb);
		status = null;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.getData(0, "STATUS");
		}

		assertEquals(status, "E");

		/*
		 * Esta tabla no muestra la columna status, no se puede validar el status E Se
		 * decidio por evaluar solo si regresa informacion la tabla
		 */

//		queryCost = String.format(Queries_Tiendas.tbl_cost_susp_sup_detail,cb);
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
		return "ATC_FT_015_GenerarCambiodeCostoyvalidarsuenv�oalPOS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_015_GenerarCambiodeCostoyvalidarsuenv�oalPOS";
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

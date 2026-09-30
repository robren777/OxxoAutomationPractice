package RMS.RegresionCostos;

import static org.testng.Assert.assertEquals;
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
import RMS.util.PageObject.Costos;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_GenerarCambiodeCosto_NivelItem_SupplierFuentedeAbastoDirecto extends BaseExecution {

	SeleniumUtil u;

	@SuppressWarnings("unused")
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_GenerarCambiodeCosto_NivelItem_SupplierFuentedeAbastoDirecto_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * Datos para ingresar a la base de datos RMS
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		String cb = null;
		boolean ejecucionCorrectaJob = false;
		// String ResultadoEjecucion = null;
		boolean st = false;
		u = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Costos c = new Costos(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,
				testCase);

		String item = data.get("item");
		String supplier = data.get("supplier");
		String loc = data.get("location");

		ExecuteQuery e = new ExecuteQuery(testCase);
		String costChange;
		String query;
		String location = data.get("location");
		String newCost = data.get("newCost");
		boolean result;

		// Paso 1 - 2
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url"));
		testCase.addBoldTextEvidenceCurrentStep("Ingresar a RMS capturar campos User >>Password>>");
		c.loginRMS();

		// Paso 3
		addStep("Seleccionar el menu Pricing y elegir la opci�n Retail/Cost Change.");
		c.navPricingRetailCost();

		// Paso 4
		addStep("Seleccionar del campo Action la opci�n New. Seleccionar del campo Origin la opci�n Cost Presionar el boton OK");
		c.fillRetail();

		// Paso 5
		addStep("Se deben llenar los siguientes campos: " + "Cost Change >>" + "Supplier >>" + "Item >>" + "Country >> "
				+ "Reason >>" + "Effective Date >>");
		result = c.fillRetailCostChange();
		assertTrue(result, "Ya se tiene un cambio de precio con el mismo item, se debe seleccionar otro item");

		String costChangeId = c.getCostChange();

		// Paso 6
		addStep("En la fila donde se encuentra el proveedor en la columna New colocar el nuevo costo.");
		c.fillRetailNewCost(newCost);

		// Paso 7
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Submit.");
		c.submitPrice();

		// Paso 8
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Approve.");
		c.approveRetailPrice();

		c.clicBtnOkU();
		c.close_RMS_2();

		c.CloseBrowser();

		// Paso 9
		addStep("Configurar cambio de Costo en RMS" + "Revisar tablas en RMS con estatus A:	" + "COST_SUSP_SUP_HEAD"
				+ "COST_SUSP_SUP_DETAIL_LOC");
		testCase.addBoldTextEvidenceCurrentStep("COST_SUSP_SUP_HEAD");

		query = String.format("SELECT * FROM COST_SUSP_SUP_HEAD where COST_CHANGE = '%s'", costChangeId);

		result = e.executeQuerySeparatedColumns(dbRms, query);
		assertTrue(result, "No se ha encontrado item");

		String status = e.getData(dbRms, query, 0, "STATUS");
		assertEquals(status, "A");

		/*
		 * Esta tabla no muestra la columna STATUS por lo que no se puede validar que se
		 * encuentre en "A"
		 */
		testCase.addBoldTextEvidenceCurrentStep("COST_SUSP_SUP_DETAIL");
		query = String.format("SELECT * FROM COST_SUSP_SUP_DETAIL where COST_CHANGE = '%s'",
				costChangeId);
		result = e.executeQuerySeparatedColumns(dbRms, query);
		assertTrue(result, "No se han encontrado registros");

		// Paso 10
		addStep("Ejecutar en Control - M el job AIX_SCCEXT                                                                                                                            ");

		u = new SeleniumUtil(new ChromeTest(), true);
		c = new Costos(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,
				testCase);

		c.getControlM();
		testCase.addScreenShotCurrentStep("Ingreso a control-M");

		testCase.addBoldTextEvidenceCurrentStep("JOB: xxfc_pre_fem_cost_change_siv");
		boolean resultadoEjecucion = c.ejecutarJob(("xxfc_pre_fem_cost_change_siv"));
		assertTrue(resultadoEjecucion, "El JOB xxfc_pre_fem_cost_change_siv no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: fem_cost_change_siv");
		resultadoEjecucion = c.ejecutarJob(("fem_cost_change_siv"));
		assertTrue(resultadoEjecucion, "El JOB fem_cost_change_siv no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: cost_change_new_store");
		resultadoEjecucion = c.ejecutarJob(("cost_change_new_store"));
		assertTrue(resultadoEjecucion, "El JOB cost_change_new_store no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: rule_check");
		resultadoEjecucion = c.ejecutarJob(("rule_check"));
		assertTrue(resultadoEjecucion, "El JOB rule_check no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: sccext");
		resultadoEjecucion = c.ejecutarJob(("sccext"));
		assertTrue(resultadoEjecucion, "El JOB sccext no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: COST_SUSP_SUP_HEAD");
		query = String.format("SELECT * FROM COST_SUSP_SUP_HEAD where COST_CHANGE = '%s'", costChangeId);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado item");

		status = e.getData(dbRms, query, 0, "STATUS");
		assertEquals(status, "E");

		/*
		 * Esta tabla no muestra la columna STATUS por lo que no se puede validar que se
		 * encuentre en "E"
		 */
		testCase.addBoldTextEvidenceCurrentStep("COST_SUSP_SUP_DETAIL");
		query = String.format("SELECT * FROM COST_SUSP_SUP_DETAIL where COST_CHANGE = '%s'",
				costChangeId);
		result = e.executeQuerySeparatedColumns(dbRms, query);
		assertTrue(result, "No se han encontrado registros");

		testCase.addBoldTextEvidenceCurrentStep("ITEM_SUPP_COUNTRY");
		query = String.format("select * from ITEM_SUPP_COUNTRY where item = '%s' and supplier = '%s'", item, supplier);
		result = e.executeQuerySeparatedColumns(dbRms, query);
		assertTrue(result, "No se han encontrado registros");

		testCase.addBoldTextEvidenceCurrentStep("ITEM_SUPP_COUNTRY_LOC");
		query = String.format(
				"select * from ITEM_SUPP_COUNTRY_LOC where item = '%s' and supplier = '%s' and loc = '%s'", item,
				supplier, location);
		result = e.executeQuerySeparatedColumns(dbRms, query);
		assertTrue(result, "No se han encontrado registros");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_GenerarCambiodeCosto_NivelItem_SupplierFuentedeAbastoDirecto_test";
	}

	@Override
	public String setTestDescription() {
		return "Realiza cambio de costo para todos los items de una fuente de Abasto";
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

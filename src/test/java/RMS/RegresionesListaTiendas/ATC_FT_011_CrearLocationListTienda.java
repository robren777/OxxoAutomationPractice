package RMS.RegresionesListaTiendas;

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

public class ATC_FT_011_CrearLocationListTienda extends BaseExecution {
	
	SeleniumUtil su;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_CrearLocationListTienda_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		String cb = null;
		boolean st = false;
		su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st,
				data, testCase);
		
		String query;
		boolean result;
		ExecuteQuery e = new ExecuteQuery(testCase);
		
		String locationType = data.get("Element");

		// Paso 1
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Control y elegir la opcion Location List.");
		t.navLocList();

		// Paso 3
		addStep("En la opcion Action, seleccionar New y presionar OK.");
		t.clicActionNew();

		// Paso 4
		addStep("El sistema arroja el numero de Location list, se llenan los campos Description y Comments y se da click en el checkbox Batch Rebuild Ind�");
		String listNumber = t.fillBox_LL();

		// Paso 5
		addStep("En el menu Options seleccionar la opcion Criteria");
		t.criteria();

		// Paso 6
		addStep("Llenar los siguientes campos:\r\n" + "  Location Type >>\r\n" + "  ( >>\r\n" + "  Element >>\r\n"
				+ "  Comparison >>\r\n" + "  Value >>\r\n" + "  ) >>\r\n" + "  Logic Operation >>\r\n"
				+ "  Al registrar la �ltima plaza el campo Logic Operation se deja en blanco.\r\n"
				+ "  Al finalizar dar click en OK.");
		t.Fillcriteria(locationType);

		// Paso 7
		addStep("En el menu Options seleccionar la opci�n Rebuild Location List y dar click en OK.");
		t.rebuild();
		t.clicBtnOk_RebuildLocationList();
		
		// Paso 8
		addStep("En la ventana Location List Head, en el campo Total Locations se muestra la cantidad de Tiendas agregadas a la Location List. Dar click en OK para finalizar el proceso.");
		t.valNoLocations();
		
		t.clic_btn_OK();

		// Paso 9
		addStep("Consultar en base de Datos de RMS la tabla� loc_list_head� y loc_list_detail");
		testCase.addBoldTextEvidenceCurrentStep("loc_list_head");
		
		query = String.format("Select * from loc_list_head where loc_list = '%s'", listNumber);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");
		
		testCase.addBoldTextEvidenceCurrentStep("loc_list_detail");
		query = String.format("Select * from loc_list_detail where loc_list = '%s'", listNumber);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");


		// Paso 10
		addStep("Consultar en base de Datos de RMS la tabla location_list_criteria");
		query = String.format("Select * from loc_list_criteria where loc_list = '%s'", listNumber);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_CrearLocationListTienda_test";
	}

	@Override
	public String setTestDescription() {
		return "Crear una Location List para la Tienda 515";
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
	
	@AfterMethod
	public void afterMethod() {
		 su.getDriver().quit();
	}


}

package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertEquals;

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

public class ATC_FT_009_CrearLocationListTienda extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_CrearLocationListTienda_test(HashMap<String, String> data) throws Exception {
		
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
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Control y elegir la opcion Location List.");
		t.navLocList();

		// Paso 3
		addStep("En la opci�n Action, seleccionar New y presionar OK.");
		t.clicActionNew();

		// Paso 4
		addStep("El sistema arroja el n�mero de Location list, se llenan los campos Description y Comments y se da click en el checkbox Batch Rebuild Ind�");
		t.fillBox_LL();

		// Paso 5
		addStep("En el men� Options seleccionar la opci�n Criteria");
		t.criteria();

		// Paso 6
		addStep("Llenar los siguientes campos:\r\n" + "  Location Type >>\r\n" + "  ( >>\r\n" + "  Element >>\r\n"
				+ "  Comparison >>\r\n" + "  Value >>\r\n" + "  ) >>\r\n" + "  Logic Operation >>\r\n"
				+ "  Al registrar la �ltima plaza el campo Logic Operation se deja en blanco.\r\n"
				+ "  Al finalizar dar click en OK.");
		t.Fillcriteria(pathImg);

		// Paso 7
		addStep("En el men� Options seleccionar la opci�n Rebuild Location List y dar click en OK.");
		t.rebuild();

		// Paso 8
		addStep("En la ventana Location List Head, en el campo Total Locations se muestra la cantidad de Tiendas agregadas a la Location List. Dar click en OK para finalizar el proceso.");
		t.clic_btn_OK();

		t.close_RMS_lls();
		t.CloseBrowser();

		// Paso 9
		addStep("Consultar en base de Datos de RMS la tabla� loc_list_head� y loc_list_detail");
		cb = App.getClipboard();
		System.out.println(cb);
		// String tienda = data.get("Store");
		String queryTienda = String.format(Queries_Tiendas.loc_list_head, cb);
		System.out.println(queryTienda);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryTienda);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			assertEquals(executeQuery.getData(0, "LOC_LIST"), cb);
		}

//		else {
//			assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del location list " + cb +"en la base de datos");
//		}

		String queryTienda1 = String.format(Queries_Tiendas.loc_list_detail, cb);
		System.out.println(queryTienda1);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryTienda1);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			assertEquals(executeQuery.getData(0, "LOC_LIST"), cb);
		}
//		else {
//			assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del location list " + cb +"en la base de datos");
//		}

		// Paso 10
		addStep("Consultar en base de Datos de RMS la tabla location_list_criteria");
		String queryTienda2 = String.format(Queries_Tiendas.loc_list_criteria, cb);
		System.out.println(queryTienda2);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryTienda2);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			assertEquals(executeQuery.getData(0, "LOC_LIST"), cb);
		}
//		else {
//			assertFalse(executeQuery.isEmpty(), "no se encontr� el numero del location list " + cb +"en la base de datos");
//		}

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_CrearLocationListTienda_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_009_CrearLocationListTienda";
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

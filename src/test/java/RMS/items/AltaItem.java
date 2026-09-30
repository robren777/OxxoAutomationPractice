package RMS.items;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.QueriesItems;
import RMS.util.PageObject.Items;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class AltaItem extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AltaItem(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();	
		Items i = new Items(sk, su, new Robot(),
				pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, null, testCase);

		JobUtil job = new JobUtil(null, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Ingresar a RMS");
		System.out.println("PASO 1"); 
		
		i.loginRMS();
		
		
		System.out.println("PASO 1");

		// i.loginRMS();

		// PASO 2
		addStep("Seleccionar el folder items, elegir la opcion items y dar clic en el bot�n open");
		System.out.println("PASO 2");

		// i.menuItemItems();

		// PASO 3
		addStep("Seleccionar en el campo Action la opci�n New item.");
		System.out.println("PASO 3");

		// i.altaItem();

		// PASO 4
		addStep("Capturar o seleccionar la opci�n correspondiente de los siguientes campos: "
				+ "item Type, item, Description, Short Desc, Tran Level, Merchandise, Forecastable, "
				+ "Catch Weight, Constant Dimensions, Retail Zone, Cost Zone, Selling Unit Retail, "
				+ "Suggested Retail, Store Order Multiple, Standard UOM, " + "Dar clic en Groc Attributes");
		System.out.println("PASO 4");
		i.capturarInfoNuevoItem();
		
		
		// PASO 5
		addStep("Validar que la informaci�n del item se vea reflejada en las tablas item_master, "
				+ "item_loc, item_supplier, item_supp_country y item_supp_country_dim "
				+ "dentro de la Base de Datos.");
		System.out.println("PASO 5");

		// Valida ITEM_MASTER
		String item = data.get("item");

		String itemMaster = String.format(QueriesItems.ITEM_MASTER_ITEM, item);
		System.out.println(itemMaster);

		SQLResult executeQuery = executeQuery(dbRms, itemMaster);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_MASTER");

		// Valida ITEM_LOC
		String itemLoc = String.format(QueriesItems.ITEM_LOC_ITEM, item);
		System.out.println(itemLoc);

		executeQuery = executeQuery(dbRms, itemLoc);
		String store = "";

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			store = executeQuery.getData(0, "LOC");
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_LOC");

		// Valida ITEM_SUPPLIER
		String itemSupp = String.format(QueriesItems.ITEM_SUPPLIER_ITEM, item);
		System.out.println(itemSupp);

		executeQuery = executeQuery(dbRms, itemSupp);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_SUPPLIER");

		// Valida ITEM_SUPP_COUNTRY
		String itemSuppCountry = String.format(QueriesItems.ITEM_SUPP_COUNTRY_ITEM, item);
		System.out.println(itemSuppCountry);

		executeQuery = executeQuery(dbRms, itemSuppCountry);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_SUPP_COUNTRY");

		// Valida ITEM_SUPP_COUNTRY_DIM
		String itemSuppCountryDim = String.format(QueriesItems.ITEM_SUPP_COUNTRY_DIM_ITEM, item);
		System.out.println(itemSuppCountryDim);

		executeQuery = executeQuery(dbRms, itemSuppCountryDim);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_SUPP_COUNTRY_DIM");

		// PASO 6
		addStep("Validar que se haya insertado el alta del art�culo receta en la tabla POS_MODS " + "con tran type 1");
		System.out.println("PASO 6");

		String posMods = String.format(QueriesItems.POS_MODS_TRAN_1, item);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_SUPP_COUNTRY_DIM");

		// PASO 7
		addStep("Ejecutar la cadena de jobs del grupo AIX_PRECIOS_Y_PROMOCIONES hasta el "
				+ "job XXFC_PROCESA_POSDNLD_ITM en Control-M");
		System.out.println("PASO 7");

		String resultadoEjecucion = job.ejecutar("");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job  no se ejecuto correctamente");

		// PASO 8
		addStep("Validar que se haya generado el Batch_id de la ejecuci�n en la tabla " + "WMUSER.POS_ITM_PRM_HEAD. ");
		System.out.println("PASO 8");

		String posItem = String.format(QueriesItems.WM_POS_ITM_PRM_HEAD, store);
		System.out.println(posItem);

		executeQuery = executeQuery(dbRms, posItem);
		String loadBatchId = "";

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			loadBatchId = executeQuery.getData(0, "LOAD_BATCH_ID");
		}

		assertFalse(statusQuery, "No se encontraron registros con LOC = " + store + " en WMUSER.POS_ITM_PRM_HEAD");

		// PASO 9
		addStep("Validar en la tabla WMUSER.FEM_POS_MODS_STG que se encuentre el registro del alta del "
				+ "art�culo� que se proces� dentro del Batch_id de la ejecuci�n.");
		System.out.println("PASO 9");

		String modsStg = String.format(QueriesItems.WM_FEM_POS_MODS_STG, item, loadBatchId);
		System.out.println(modsStg);

		executeQuery = executeQuery(dbRms, modsStg);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " y LOAD_BATCH_ID = " + loadBatchId
				+ "en WMUSER.FEM_POS_MODS_STG");

		// PASO 10
		addStep("Ejecutar el Job runITM en Control-M");
		System.out.println("PASO 10");

		resultadoEjecucion = job.ejecutar("runITM");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job runITM no se ejecuto correctamente");

		// PASO 11
		addStep("Validar en la tabla WMUSER.POS_ITM_PRM_HEAD, dentro del Batch_id de la ejecuci�n, "
				+ "que se haya generado el archivo ITM");
		System.out.println("PASO 11");

		posItem = String.format(QueriesItems.WM_POS_ITM_PRM_HEAD, store);
		System.out.println(posItem);

		executeQuery = executeQuery(dbRms, posItem);
		String itm = "";

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			itm = executeQuery.getData(0, "WM_TARGET_ITM");
		}

		assertFalse(statusQuery, "No se encontraron registros con LOC = " + store + " en WMUSER.POS_ITM_PRM_HEAD");

		// PASO 12
		addStep("Validar que se encuentre el archivo ITM en la tabla POSUSER.POS_OUTBOUND_DOCS");
		System.out.println("PASO 12");

		String posUser = String.format(QueriesItems.POS_OUTBOUND_DOCS_ITM, itm);
		System.out.println(posUser);

		executeQuery = executeQuery(dbRms, posUser);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con DOC_NAME = " + itm + " en POSUSER.POS_OUTBOUND_DOCS");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Alta de art�culo nuevo." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: "
				+ "-CASOS QUE CUMPLE: " + "MTC-FT-005 Alta Item - VIRTUA - EAN13 - IVA 16 - Interior, "
				+ "MTC-FT-001 Alta Item - MREGUL - PLU - IVA0 - Interior, "
				+ "MTC-FT-002 Alta Item - MREGUL - EAN13 - IVA08 - Frontera, "
				+ "MTC-FT-003 Alta Item - MREGUL - UPC-A - IVA16 - Bara, "
				+ "MTC-FT-004 Alta Item - MREGUL - EAN8 - IVA16 - Interior.";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_AltaItem";
	}

	@Override

	public String setTestInstanceID() {
		return null;
	}
}
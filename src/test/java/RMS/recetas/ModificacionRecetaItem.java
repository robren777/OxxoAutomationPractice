package RMS.recetas;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.QueriesRecetas;
import RMS.util.PageObject.Recetas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ModificacionRecetaItem extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Modificacion_Receta(HashMap<String, String> data) throws Exception {

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
		Recetas r = new Recetas(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, data,
				testCase);
		JobUtil job = new JobUtil(null, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Ingresar a RMS");
		System.out.println("PASO 1");

		r.loginRMS();

		// PASO 2
		addStep("Seleccionar el menu items y elegir la opci�n FEMSA Packitem Maintenance.");
		System.out.println("PASO 2");

		r.menuItemsFemsaPackItem();

		// PASO 3
		addStep("En el campo \"Pack No\" capturamos el PLU de la receta, nos mostrara todos los insumos de esta.");
		System.out.println("PASO 3");

		r.capturarPackNo();

		// PASO 4
		addStep("Para Cambiar el factor nos posicionamos en el rengl�n del insumo� a modificar, "
				+ "en Quantity modificamos el factor de consumo y damos click en Apply. "
				+ "Cuando terminemos de aplicar los cambios solicitados damos click en OK.");
		System.out.println("PASO 4");

		String[] itemInfo = r.modificarReceta();

		String item = itemInfo[0];
		String quantity = itemInfo[1];

		su.close();

		// PASO 5
		addStep("Revisar ITEM en tablas de RMS: " + "PACKITEM, ITEM_LOC, ITEM_MASTER");
		System.out.println("PASO 5");

		String packNo = data.get("pack_no");

		// Valida PACKITEM
		String packItem = String.format(QueriesRecetas.PACKITEM_PACK_ITEM_QUANTITY, packNo, item, quantity);
		System.out.println(packItem);

		SQLResult executeQuery = executeQuery(dbRms, packItem);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PACK_NO = " + packNo + " ITEM = " + item + " y con "
				+ "el nuevo valor QUANTITY = " + quantity + " en PACKITEM");

		// Valida ITEM_LOC
		String store = data.get("store");
		String itemLoc = String.format(QueriesRecetas.ITEM_LOC_ITEM_STORE_STATUS_A, item, store);
		System.out.println(itemLoc);

		executeQuery = executeQuery(dbRms, itemLoc);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PACK_NO = ITEM = " + item + " y " + "LOC = " + store + " en ITEM_LOC");

		// Valida ITEM_MASTER
		String itemMaster = String.format(QueriesRecetas.ITEM_MASTER_ITEM, item);
		System.out.println(itemMaster);

		executeQuery = executeQuery(dbRms, itemMaster);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " en ITEM_MASTER");

		// PASO 6
		addStep("Ejecutar runPR26(PR26) en Control M");
		System.out.println("PASO 6");

		String resultadoEjecucion = job.ejecutar("PR26");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job PR26 no se ejecuto correctamente");

		// PASO 7
		addStep("Ejecutar PR35");
		System.out.println("PASO 7");

		resultadoEjecucion = job.ejecutar("PR35");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job PR35 no se ejecuto correctamente");

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
		return "Construido. Modificacion de receta." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: "
				+ "-CASOS QUE CUMPLE: " + "MTC-FT-003 Modificaci�n de Receta - MREGUL - PLU - IVA16 - Interior, "
				+ "MTC-FT-004 Modificaci�n de Receta - MREGUL - PLU - IVA08 - Frontera.";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Modificacion_Receta";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

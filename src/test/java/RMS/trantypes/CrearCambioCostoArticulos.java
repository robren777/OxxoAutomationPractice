package RMS.trantypes;

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
import RMS.util.QueriesTranTypes;
import RMS.util.PageObject.TranTypes;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CrearCambioCostoArticulos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Crear_Cambio_Precio_Articulos(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		TranTypes t = new TranTypes(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms,
				data, testCase);
		JobUtil job = new JobUtil(u, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Seleccionar art�culo y tienda que cumpla con las caracter�sticas necesarias "
				+ "para que se generen los tran_type");
		System.out.println("PASO 1");

		String store = data.get("store");
		String item = data.get("item");

		String itemSuppCountry = String.format(QueriesTranTypes.ITEM_LOC_ITEM_SUPP_COUNTRY, store, item);
		System.out.println(itemSuppCountry);

		SQLResult executeQuery = executeQuery(dbRms, itemSuppCountry);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No existen registros con LOC = " + store + " e ITEM = " + item);

		// PASO 2
		addStep("Ingresar a la aplicacion RMS");
		System.out.println("PASO 2");

		t.loginRMS();

		// PASO 3
		addStep("Seleccionar en el men� Pricing>Retail/Cost Change");
		System.out.println("PASO 3");

		t.menuPricingRetail();

		// PASO 4
		addStep("Seleccionar las opciones Action = New, Origin=Cost y clic en OK.");
		System.out.println("PASO 4");

		t.costoNuevo();

		// PASO 5
		addStep("Se deben llenar los siguientes campos: " + "Cost Change >> Supplier >> Item >> Country >> "
				+ "Reason >> Effective Date >> Dar click en Search " + "Presionar el bot�n Locations");
		System.out.println("PASO 5");

		String costChangeId = t.cambiarCosto();

		// PASO 6
		addStep("Presentar y aprobar el cambio de costo.");
		System.out.println("PASO 6");

		t.submitCambio();
		t.aprobarCambio();

		// PASO 3
		addStep("Se valida que el cambio de costo se vea reflejado en la tabla COST_SUSP_SUP_HEAD en status A.");
		System.out.println("PASO 3");

		String costChangeStatusA = String.format(QueriesTranTypes.COST_SUSP_SUP_HEAD_STATUS_A, "31166592");
		System.out.println(costChangeStatusA);

		executeQuery = executeQuery(dbRms, costChangeStatusA);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con COST_CHANGE = " + "31166592"
				+ " y STATUS = 'A' en COST_SUSP_SUP_HEAD");

		// PASO 4
		addStep("Ejecutar en Control - M el job SCCEXT.");
		System.out.println("PASO 4");

		String resultadoEjecucion = job.ejecutar("SCCEXT");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job SCCEXT no se ejecuto correctamente");

		// PASO 5
		addStep("Se valida que en la tabla COST_SUSP_SUP_HEAD cambi� el status a E");
		System.out.println("PASO 5");

		String costChangeStatusE = String.format(QueriesTranTypes.COST_SUSP_SUP_HEAD_STATUS_E, costChangeId);
		System.out.println(costChangeStatusE);

		executeQuery = executeQuery(dbRms, costChangeStatusE);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con COST_CHANGE = " + costChangeId
				+ " y STATUS = 'E' en COST_SUSP_SUP_HEAD");

		// PASO 6
		addStep("Se valida que en la tabla POS_MODS se hayan insertado los tran_type.");
		System.out.println("PASO 6");

		String posMods = String.format(QueriesTranTypes.POS_MODS_TRAN_TYPE_90_ITEM_STORE, item, store);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " y TRAN_TYPE = 90 en POS_MODS");
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
		return "Construido. Crear cambios de precio para art�culos de M�xico."
				+ "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: " + "-CASOS QUE CUMPLE: " + "";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Crear_Cambio_Precio_Articulos";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

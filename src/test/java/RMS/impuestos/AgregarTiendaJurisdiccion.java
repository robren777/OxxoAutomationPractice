package RMS.impuestos;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.QueriesImpuestos;
import RMS.util.PageObject.Impuestos;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class AgregarTiendaJurisdiccion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Agregar_Tienda_A_Jurisdiccion(HashMap<String, String> data) throws Exception {

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
		Impuestos i = new Impuestos(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms,
				data, testCase);
		JobUtil job = new JobUtil(null, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Contar con una tienda que cumpla los siguientes criterios: " + "No este agregada a la jurisdicci�n.");
		System.out.println("PASO 1");

		String store = data.get("store");
		String state = data.get("state");

		String geoCodeStore = String.format(QueriesImpuestos.GEOCODE_STORE, state, store);
		System.out.println(geoCodeStore);

		SQLResult executeQuery = executeQuery(dbRms, geoCodeStore);

		boolean statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "La tienda " + store + " esta agregada a la jurisdiccion " + state);

		// PASO 2
		addStep("Revisar en la base de datos que la tienda este ligada a al menos un " + "articulo de la jurisdiccion");
		System.out.println("PASO 2");

		// OBTENER TAX_JURISDICTION_ID
		String taxCodeQuery = String.format(QueriesImpuestos.GEOCODE_TXCDE, state);
		System.out.println(taxCodeQuery);

		String taxId = executeQuery(dbRms, taxCodeQuery).getData(0, "TAX_JURISDICTION_ID");

		// ITEM_LOC
		String productTax = String.format(QueriesImpuestos.ITEM_LOC_PRODUCT_TAX_STORE, taxId, store);
		System.out.println(productTax);

		executeQuery = executeQuery(dbRms, productTax);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "La tienda " + store + " no tiene tiendas ligadas " + "en la jurisdiccion " + state
				+ "(" + taxId + ")");

		// PASO 3
		addStep("Revisar en la tabla pos_mods que tenga tran_type 1");
		System.out.println("PASO 3");

		String posMods = String.format(QueriesImpuestos.POS_MODS_TRAN_TYPE_1);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con TRAN_TYPE = 1 en POS_MODS");

		// PASO 4
		addStep("Consultar la jurisdiccion donde se agregara la tienda nueva.");
		System.out.println("PASO 4");

		String taxRatesGeocode = String.format(QueriesImpuestos.TAX_RATES_GEOCODE, taxId);
		System.out.println(taxRatesGeocode);

		executeQuery = executeQuery(dbRms, taxRatesGeocode);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con TAX_JURISDICTION_ID = " + taxId);

		// PASO 5
		addStep("Agregar una tienda nueva a la jurisdiccion en RMS.");
		System.out.println("PASO 5");

		i.loginRMS();
		i.agregarTiendaJurisdiccion();

		// PASO 6
		addStep("Consultar la nueva tienda en la jurisdiccion.");
		System.out.println("PASO 6");

		taxRatesGeocode = String.format(QueriesImpuestos.GEOCODE_STORE, state, store);
		System.out.println(taxRatesGeocode);

		executeQuery = executeQuery(dbRms, taxRatesGeocode);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con STATE_GEOCODE_ID = " + state + " Y STORE = " + store
				+ " en GEOCODE_STORE");

		// PASO 7
		addStep("Consultar en la tabla XXFC_POS_MODS_TEMP el impuesto sobre los art�culos " + "de la tienda nueva.");
		System.out.println("PASO 7");

		String xxfcPosMods = QueriesImpuestos.XXFC_POS_MODS_TEMP_TRAN_1;
		System.out.println(xxfcPosMods);

		executeQuery = executeQuery(dbRms, xxfcPosMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con TRAN_TYPE = 1 EN XXFC_POS_MODS_TEMP");

		// PASO 8
		addStep("Ejecutar el job FEM_POSDNLD_PRE");
		System.out.println("PASO 8");

		String resultadoEjecucion = job.ejecutar("FEM_POSDNLD_PRE");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job FEM_POSDNLD_PRE no se ejecuto correctamente");

		// PASO 9
		addStep("Consultar la tabla xxfc_pos_mods_temp");
		System.out.println("PASO 9");

		xxfcPosMods = QueriesImpuestos.XXFC_POS_MODS_TEMP_TRAN;
		System.out.println(xxfcPosMods);

		executeQuery = executeQuery(dbRms, xxfcPosMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en XXFC_POS_MODS_TEMP");

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
		return "Construido. Agregar una tienda a una jurisdiccion. " + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET "
				+ "-JOBS: FEM_POSDNLD_PRE";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Agregar_Tienda_A_Jurisdiccion";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

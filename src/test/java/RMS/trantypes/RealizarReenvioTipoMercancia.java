package RMS.trantypes;

import static org.testng.Assert.assertFalse;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.QueriesTranTypes;
import RMS.util.PageObject.TranTypes;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class RealizarReenvioTipoMercancia extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Realiza_Reenvio_Tipo_Mercancia(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

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

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Seleccionar art�culo y tienda que cumpla con las caracter�sticas necesarias "
				+ "para que se generen los tran_type");
		System.out.println("PASO 1");

		String store = data.get("store");
		String merchType = data.get("merch_type");
		String item = "";

		String consignacion = String.format(QueriesTranTypes.ITEM_LOC_CONSIGNACION, merchType, store);
		System.out.println(consignacion);

		SQLResult executeQuery = executeQuery(dbRms, consignacion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			item = executeQuery.getData(0, "ITEM");
		}

		assertFalse(statusQuery,
				"No existen articulos que cumplan con las caracteristicas para " + "generar los Tran Type");

		System.out.println("Item seleccionado: " + item);
		testCase.addTextEvidenceCurrentStep("Item seleccionado: " + item);

		// PASO 2
		addStep("Ingresar a RMS");
		System.out.println("PASO 2");

		t.loginRMS();

		// PASO 3
		addStep("Entrar en el apartado Control/XXREO_Control de Operaciones/ " + "XXREO-Reenviar Cat�logos por Tienda");
		System.out.println("PASO 3");

		t.menuControlReenviar();

		// PASO 4
		addStep("Seleccionar la tienda, el �tem y la opci�n reenviar " + "tipo mercanc�a y se da clic en Reenviar.");
		System.out.println("PASO 4");

		// LLEGAR A ESA PANTALLA

		// PASO 5
		addStep("Se valida en tabla pos_mods los movimientos insertados para el art�culo-tienda.");
		System.out.println("PASO 6");

		String tranType = data.get("tran_type");

		String posMods = String.format(QueriesTranTypes.POS_MODS_ITEM_STORE_TRAN_TYPE, item, store, tranType);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No existen registros con ITEM = " + item + ", STORE = " + store + " y TRAN_TYPE = "
				+ tranType + " en POS_MODS");

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
		return "Construido. Reenviar tran_type 93 tipo de mercanc�a por pantalla."
				+ "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: " + "-CASOS QUE CUMPLE: ";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Realiza_Reenvio_Tipo_Mercancia";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

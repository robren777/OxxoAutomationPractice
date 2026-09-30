package RMS.trantypes;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

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

public class AltaArticuloTipoMercancia extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Alta_Articulo_Tipo_Mercancia(HashMap<String, String> data) throws Exception {

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
		addStep("Seleccionar art�culo que cumpla con las caracter�sticas necesarias para que se generen los tran_type. "
				+ "Seleccionar tienda a la que no est� ligada el art�culo.");
		System.out.println("PASO 1");

		String merchType = data.get("merch_type");
		String store = data.get("store");

		String itemLocStore = String.format(QueriesTranTypes.ITEM_LOC_CONSIGNACION, merchType, store);
		System.out.println(itemLocStore);

		SQLResult executeQuery = executeQuery(dbRms, itemLocStore);
		String item = null;

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			item = executeQuery.getData(0, "ITEM");
		}

		assertNotNull(item, "No existe ITEM con MERCH_TYPE = " + merchType + " " + "NO ligado a LOC = " + store);

		testCase.addTextEvidenceCurrentStep("Item seleccionado: " + item);
		System.out.println("Item seleccionado: " + item);

		// PASO 2
		addStep("Obtener una tienda que no este ligada al articulo");
		System.out.println("PASO 2");

		String locItem = String.format(QueriesTranTypes.STORE_NOT_ITEM, item);
		System.out.println(locItem);

		executeQuery = executeQuery(dbRms, locItem);
		store = null;

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			store = executeQuery.getData(0, "STORE");
		}

		assertNotNull(store, "No existe STORE que no este ligada al ITEM " + item);

		testCase.addTextEvidenceCurrentStep("Store seleccionada: " + store);
		System.out.println("Store seleccionada: " + store);

		// PASO 3
		addStep("Ingresar a RMS");
		System.out.println("PASO 3");

		t.loginRMS();

		// PASO 4
		addStep("Seleccionar el menu items y elegir la opcion items");
		System.out.println("PASO 4");

		t.menuItemItems();

		// PASO 5
		addStep("Seleccionar en el campo Action la opci�n Edit item. "
				+ "En el campo Item, capturar el n�mero de �tem. " + "Presionar OK.");
		System.out.println("PASO 5");

		t.editItem(item);

		// PASO 6
		addStep("Seleccionar el men� Locations para editar art�culo que se le ligar� una tienda nueva. "
				+ "Se busca la tienda y se valida que a�n no tiene ligado el art�culo");
		System.out.println("PASO 6");

		t.locations(store);

		// PASO 7
		addStep("Se liga el art�culo a la tienda nueva");
		System.out.println("PASO 7");

		t.ligarArticulo(store);

		// PASO 8
		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el art�culo-tienda");
		System.out.println("PASO 8");

		String posMods = String.format(QueriesTranTypes.POS_MODS_TRAN_TYPE_93, item);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con TRAN_TYPE = 93 e ITEM = " + item + " en POS_MODS");
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
		return "Construido. Alta de liga de art�culo con tipo de mercanc�a consumo interno. "
				+ "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: " + "-CASOS QUE CUMPLE: "
				+ "MTC-FT-010 Generar Alta de Art�culo con Tipo de Mercanc�a CINTER - Tran_Type 1,93, "
				+ "MTC-FT-011 Generar Alta de Art�culo con Tipo de Mercanc�a VIRTUA - Tran_Type 1,93.";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Alta_Articulo_Tipo_Mercancia";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

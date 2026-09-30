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

public class RealizarReenvioCambioPrecio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_Realiza_Reenvio_Cambio_Precio(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA, GlobalVariables.DB_USER_BDRMSCQA,
				GlobalVariables.DB_PASSWORD_BDRMSCQA);

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

		// PRECIO NORMAL
		String group = data.get("group_id");
		String zone = data.get("zone_id");
		String store = data.get("store");
		String item = data.get("item");

		String itemZonePrice = String.format(QueriesTranTypes.ITEM_ZONE_PRICE, group, zone, store, item);
		System.out.println(itemZonePrice);

		SQLResult executeQuery = executeQuery(dbRms, itemZonePrice);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No existen registros con ZONE_GROUP_ID = " + group + ", ZONE_ID = " + zone
				+ ", LOC = " + store + " E ITEM = " + item + " en ITEM_ZONE_PRICE");

//		// MULTIUNIT
//		String groupMu = data.get("group_id_mu");
//		String zoneMu = data.get("zone_id_mu");
//		String storeMu = data.get("store_mu");
//		String itemMu = data.get("item_mu");
//		
//		String itemZonePriceMu = String.format(QueriesTranTypes.ITEM_ZONE_PRICE_MU, groupMu, zoneMu, 
//				storeMu, itemMu);
//		System.out.println(itemZonePriceMu);
//
//		executeQuery = executeQuery(dbRms, itemZonePriceMu);
//	
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);			
//		}
//
//		assertFalse(statusQuery, "No existen registros con ZONE_GROUP_ID = " + groupMu + ", ZONE_ID = " + zoneMu
//				+ ", LOC = " + storeMu + " E ITEM = " + itemMu + " en ITEM_ZONE_PRICE");

		// PASO 2
		addStep("Ingresar a RMS");
		System.out.println("PASO 2");

		t.loginRMS();

		// PASO 3
		addStep("Entrar en el apartado Control/XXREO_Control de Operaciones/ " + "XXREO-Reenviar Cat�logos por Tienda");
		System.out.println("PASO 3");

		t.menuControlReenviar();

		// PASO 4
		addStep("PRECIO NORMAL: Seleccionar la tienda, el art�culo y la opci�n reenviar cambio de precio. Clic en reenviar");
		System.out.println("PASO 4");

		// LLEGAR A ESA PANTALLA
		t.formularioReenviar();

		su.close();
		// PASO 5
		addStep("Se valida en tabla POS_MODS y XXFC.XXFC_POS_MODS_TEMS_COL los movimientos insertados para el art�culo-tienda.");
		System.out.println("PASO 5");

		String posMods = String.format(QueriesTranTypes.POS_MODS, item, store);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No existen registros con ITEM = " + item + ", STORE = " + store + " en POS_MODS");

		posMods = String.format(QueriesTranTypes.XXFC_XXFC_POS_MODS_TEMP_COL, item, store);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No existen registros con ITEM = " + item + ", STORE = " + store + " en POS_MODS");

//		// PASO 7
//		addStep("PRECIO MULTIUNIT: Seleccionar la tienda, el art�culo y la opci�n reenviar cambio de precio. "
//				+ "Clic en reenviar");
//		System.out.println("PASO 7");
//		t.loginRMS();
//		t.menuControlReenviar();
//		
//		// LLEGAR
//		
//		
//		// PASO 8
//		addStep("Se valida en tabla pos_mods los movimientos insertados para el art�culo-tienda.");
//		System.out.println("PASO 8");
//		
//		String tranTypeMu = data.get("tran_type_mu");
//		
//		posMods = String.format(QueriesTranTypes.POS_MODS_ITEM_STORE_TRAN_TYPE, itemMu, storeMu, tranTypeMu);
//		System.out.println(posMods);
//
//		executeQuery = executeQuery(dbRms, posMods);
//
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No existen registros con ITEM = " + itemMu + ", STORE = " + storeMu
//				+ " y TRAN_TYPE = " + tranTypeMu + " en POS_MODS");
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
		return "Construido. Reenviar Candado por pantalla ." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: "
				+ "-CASOS QUE CUMPLE: ";

	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_Realiza_Reenvio_Cambio_Precio";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

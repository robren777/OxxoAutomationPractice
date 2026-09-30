package RMS.RegresionesArticulos;

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

public class ATC_FT_030_Modif_Campo_InStoreMarketBasket_ArtIculoDeValor_TranType99 extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_030_Modif_Campo_InStoreMarketBasket_ArtIculoDeValor_TranType99_test(HashMap<String, String> data)
			throws Exception {
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		String cb = null;
		boolean st = false;
		u = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);

		ExecuteQuery e = new ExecuteQuery(testCase);
		boolean result;
		
		String query;
		String item = data.get("item");
		String store = data.get("groupValue");
		String ISBM = data.get("ISBM");
		String ISMBActual;
		
		// Paso 1
		addStep("Seleccionar articulo que cumpla con las caracteristicas necesarias para que se generen los tran_type.");

		query = String.format("SELECT IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS FROM ITEM_LOC IL, ITEM_SUPP_COUNTRY_LOC ISCL WHERE\r\n"
				+ "IL.ITEM = ISCL.ITEM AND\r\n"
				+ "IL.LOC = ISCL.LOC AND\r\n"
				+ "IL.STATUS = 'A' AND IL.LOC = '%s' AND IL.ITEM = '%s'", store, item);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		//Paso 2
		addStep("Revisar el valor del campo in_store_market_basket en tabla item_loc_traits.");
		
		query = String.format("Select * from ITEM_LOC_TRAITS Where ITEM = '%s' and LOC = '%s'", item, store);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		ISMBActual = e.getData(dbRms, query, 0, "IN_STORE_MARKET_BASKET");
		
		testCase.addBoldTextEvidenceCurrentStep("Valor Actual del Campo IN_STORE_MARKET_BASKET: " + ISMBActual);
		
		// Paso 3
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		t.Login_En_RMS();

		// Paso 4
		addStep("Seleccionar el menu items y elegir la opcion items");
		t.navItems();

		// Paso 5
		addStep("Seleccionar en el campo Action la opcion Edit item.\r\n"
				+ "En el campo Item, capturar el n�mero de �tem.\r\n"
				+ "Presionar OK");

		t.EditItemVirtual(item);

		// Paso 6
		addStep("Seleccionar el menu Locations, seleccionar la tienda, seleccionar la opciun Item/Single Loc Traits.");
		t.NavLocations();
		t.NavItemSingleLocTraits(store);

		// Paso 7
		addStep("Seleccionar el valor de In Store Market Basket y guardar el cambio.");
		t.fillItemLocationTraits();

		t.clic_btn_OKItems();
		t.close_RMS_2();
		
		// Paso 8
		addStep("Se valida en tabla pos_mods los movimientos insertados para el articulo-tienda.");

		query = String.format("select * from POS_MODS where ITEM = '%s' AND STORE = '%s' and TRAN_TYPE in ('63', '99')", item, store);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store);
		
		// Paso 9
		addStep("Se revisa el valor actualizado en la tabla");

		query = String.format("Select * from ITEM_LOC_TRAITS Where ITEM = '%s' and LOC = '%s' and IN_STORE_MARKET_BASKET = '%s'", item, store, ISBM);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_030_Modif_Campo_InStoreMarketBasket_ArtIculoDeValor_TranType99_test";
	}

	@Override
	public String setTestDescription() {
		return "Modificar en pantalla itemloctrait el valor del campo in store market basket";
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

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

public class ATC_FT_027_Modificacion_en_Descripcion_Corta_y_Larga_de_Articulo_Existente_Tran_Type_1012 extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_027_Modificacion_en_Descripcion_Corta_y_Larga_de_Articulo_Existente_Tran_Type_1012_test(HashMap<String, String> data) throws Exception {

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
		
		String item = data.get("item");
		String store = data.get("store");
		String query;
		
		String description = data.get("description");
		
		System.out.println("Item a usar: " + item);
		System.out.println("Store a usar: " + store);

		// Paso 2
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		t.Login_En_RMS();

		// Paso 3
		addStep("Seleccionar el menu items y elegir la opcion items");
		t.navItems();

		// Paso 4
		addStep("Seleccionar en el campo Action la opcion Edit item.\r\n"
				+ "En el campo Item, capturar el n�mero de �tem.\r\n"
				+ "Presionar OK");

		t.EditItemVirtual(item);

		// Paso 5
		addStep("Seleccionar el menu Locations para editar articulo que se le ligara una tienda nueva.");
		t.NavLocations();
		
		// Paso 5
		addStep("Se modifica la descripcion corta y larga");
		t.modifyDesc(store);
		
		t.clic_btn_OKItems();
		t.close_RMS_2();
		
		// Paso 8
		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el articulo-tienda.");
		
		query = String.format("select * from POS_MODS where ITEM = '%s' AND STORE = '%s' and ITEM_LONG_DESC = '%s'", item, store, description);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store + " con la descripcion: " + description);
		

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_027_Modificacion_en_Descripcion_Corta_y_Larga_de_Articulo_Existente_Tran_Type_1012_test";
	}

	@Override
	public String setTestDescription() {
		return "Modificacion de Descripcion Larga de Articulo Existente";
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

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

public class ATC_FT_018_Generar_Alta_de_Articulo_con_Giftcard_Tran_Type_181 extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_Generar_Alta_de_Articulo_con_Giftcard_Tran_Type_181_test(
			HashMap<String, String> data) throws Exception {

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

		boolean validar;
		ExecuteQuery e = new ExecuteQuery(testCase);
		boolean result;
		
		String item;
		String store = null;
		String query;
		
		// Paso 1
		addStep("Seleccionar articulo que cumpla con las caracteristicas necesarias para que se generen los tran_type. Seleccionar tienda a la que no este ligada el art�culo.");

		query = "select il.loc,uil.item,uil.uda_id,uil.uda_value,uv.uda_value_desc, il.local_item_desc from uda_item_lov uil, uda_values uv, item_loc il WHERE\r\n"
				+ "uv.uda_id = uil.uda_id and\r\n"
				+ "uv.uda_value = uil.uda_value and\r\n"
				+ "uv.uda_id = 13940 and\r\n"
				+ "uil.item = il.item and\r\n"
				+ "il.status = 'A' "
				+ "and rownum <= '10'";
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Item Seleccionado");
		
		query = "select uil.item from uda_item_lov uil, uda_values uv, item_loc il WHERE\r\n"
				+ "uv.uda_id = uil.uda_id and\r\n"
				+ "uv.uda_value = uil.uda_value and\r\n"
				+ "uv.uda_id = 13940 and\r\n"
				+ "uil.item = il.item and\r\n"
				+ "il.status = 'A'"
				+ "and rownum <= '1'";
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		item = e.getData(dbRms, query, 0, "ITEM");
		
		testCase.addBoldTextEvidenceCurrentStep("Buscar tienda que no este ligada al articulo");
		query = String.format("select * from store where store_close_date is null and store not in (select loc from item_loc where item = '%s') and rownum <= '10'", item);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado Tiendas que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Tienda seleccionada");
		
		query = String.format("select STORE from store where store_close_date is null and store not in (select loc from item_loc where item = '%s') and rownum <= '1'", item);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		store = e.getData(dbRms, query, 0, "STORE");
		
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

		// Paso 6
		addStep("Se busca la tienda y se valida que aun no tiene ligado el art�culo.");
		validar = t.ValRecordsItemLocation(store);
		assertTrue(validar, "La tienda ya tiene ligado el articulo.");
		
		// Paso 7
		addStep("Se liga el articulo a la tienda nueva.");
		t.fillNuevaTienda(store);
		
		t.clic_btn_OKItems();
		t.close_RMS_2();
		
		// Paso 8
		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el articulo-tienda.");
		
		query = String.format("select * from POS_MODS where ITEM = '%s' AND STORE = '%s'", item, store);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store);

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_Generar_Alta_de_Articulo_con_Giftcard_Tran_Type_181_test";
	}

	@Override
	public String setTestDescription() {
		return "Alta de liga de articulo Giftcard";
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

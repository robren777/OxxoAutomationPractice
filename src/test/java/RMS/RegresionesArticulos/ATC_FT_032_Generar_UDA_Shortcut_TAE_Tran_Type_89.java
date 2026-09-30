package RMS.RegresionesArticulos;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.ExecuteQuery;
import RMS.util.GlobalVariables;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_032_Generar_UDA_Shortcut_TAE_Tran_Type_89 extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_032_Generar_UDA_Shortcut_TAE_Tran_Type_89_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries	
		String cb = null;
		boolean st = false;
		u = new SeleniumUtil(new IETest());
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
		
		boolean result;

		ExecuteQuery e = new ExecuteQuery(testCase);
		String store = data.get("store");

		String item = null;
		String id_UDA = "9545";
		String text_UDA = "TEST";

		// Paso 1
		addStep("Seleccionar articulo que cumpla con las caracteristicas necesarias para que se generen los tran_type.");
		String query = String.format("select IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS from item_loc il, item_supp_country_loc iscl where\r\n"
				+ "il.item = iscl.item and\r\n"
				+ "il.loc = iscl.loc and\r\n"
				+ "il.primary_supp = iscl.supplier and\r\n"
				+ "il.status = 'A' and il.loc = '%s' and il.item in (select item from uda_item_ff where uda_id = '9545') and rownum <='10'", store);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Consulta en Tabla xxfc_fem_consignacion");
		query = String.format("select * from xxfc_fem_consignacion where merch_type = 'VIRTUA' and item not in (select item from uda_item_ff where uda_id = '%s') and loc in ('%s') and rownum <= 10", id_UDA, store);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Consulta en Tabla uda_item_ff");
		query = String.format("select * from uda_item_ff where uda_id != '%s' and ITEM not in (select ITEM from uda_item_ff where uda_id = '%s') and rownum <= 10", id_UDA, id_UDA);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Item Seleccionado: ");
		query = String.format("select * from uda_item_ff where uda_id != '%s' and ITEM not in (select ITEM from uda_item_ff where uda_id = '%s') and rownum <= 1", id_UDA, id_UDA);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");
		
		item = e.getData(dbRms, query, 0, "ITEM");
		
		System.out.println("Item a usar: " + item);
		
		testCase.addBoldTextEvidenceCurrentStep("Consulta en Tabla loc_trait");
		query = String.format("select * from loc_traits where loc_trait = '%s'", id_UDA);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros con el UDA: " + id_UDA);
		
		testCase.addBoldTextEvidenceCurrentStep("Consulta en Tabla loc_traits_matrix");
		query = String.format("select * from loc_traits_matrix where loc_trait = '%s'", id_UDA);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros con el UDA: " + id_UDA);

		// Paso 2
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 3
		addStep("Seleccionar el menu items y elegir la opcion �tems");
		t.navItems();

		// Paso 4
		addStep("Seleccionar en el campo Action la opcion Edit item.\r\n"
				+ "En el campo Item, capturar el n�mero de �tem.\r\n" + "Presionar OK\r\n");
		t.EditItemVirtualMod(item);

		// Paso 5
		addStep("Editar articulo, clic en la opcion User Defined Attributes para acceder a la pantalla item UDA.\r\n"
				+ "Se a�ade el UDA "+ id_UDA);
		t.navEditUDA();
		t.editUDA(id_UDA, text_UDA);
		t.clic_btn_OKItems();
		t.close_RMS_2();
		
		/*
		 * Este paso se deja comentado debido a que la tabla POS_MODS no presenta registros con el item y 
		 * con el TRAN_TYPE del proceso (05/16/24)
		 */
		
//		// Paso 6
//		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el art�culo-tienda.");
//		query = String.format("Select * from POS_MODS WHERE ITEM = '%s' and TRAN_TYPE IN (89)", item);
//		result = e.executeQuerySeparatedColumns(dbRms,query);
//		assertTrue(result, "No se han encontrado registros");

		// Paso 7
		addStep("Se valida en tabla uda_item_ff que haya sido agregado el art�culo.");

		query = String.format("Select * FROM UDA_ITEM_FF WHERE ITEM = '%s' AND UDA_ID = '%s'", item, id_UDA);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros");
		
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_032_Generar_UDA_Shortcut_TAE_Tran_Type_89_test";
	}

	@Override
	public String setTestDescription() {
		return "Por pantalla itemuda generar el tran_type FEMSA Articulos TAE - Shortcut TAE";
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
	
	public void afterMethod() {
		u.getDriver().quit();
	}

}

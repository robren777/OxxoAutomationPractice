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

public class ATC_FT_031_Activar_Articulo_Existente_a_Nivel_Tienda_Tran_Type_25 extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_031_Activar_Articulo_Existente_a_Nivel_Tienda_Tran_Type_25_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
//		String promotion = data.get("promotion"); // id del registro que vamos a buscar en las tablas 	
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
		String item = null;
		boolean result;

		ExecuteQuery e = new ExecuteQuery(testCase);
		String store = data.get("store");
		String status = data.get("Status");
		
		// Paso 1
		addStep("Seleccionar articulo que cumpla con las caracter�sticas necesarias para que se generen los tran_type.");	
		String query = String.format("SELECT * FROM ITEM_LOC WHERE STATUS = 'I' AND LOC = '%s' AND rownum <= 10", store);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Item Seleccionado: ");
		query = String.format("SELECT ITEM FROM ITEM_LOC WHERE STATUS = 'I' AND LOC = '%s' AND rownum <= 1", store);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");
		
		item = e.getData(dbRms, query, 0, "ITEM");
		
		System.out.println("Item a usar: " + item);
		System.out.println("Store a usar: " + store);

		//Paso 2
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 3
		addStep("Seleccionar el folder items, elegir la opcion items y dar clic en el bot�n open");
		t.navItems();

		// Paso 4
		addStep("Seleccionar en el campo Action la opcion Edit item.\r\n"
				+ "En el campo Item, capturar el n�mero de �tem.\r\n"
				+ "Presionar OK\r\n");
		t.EditItemVirtualMod(item);

		// Paso 5
		addStep("Seleccionar el menu Locations, filtrar la tienda 1610");
		t.NavLocations();
		t.selectLocation(store);

		// Paso 6
		addStep("Modificar el estatus a Activo.");
		t.modStatus(status);
		
		t.clic_btn_OKItems();
		t.close_RMS_2();

		// Paso 7
		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el articulo-tienda.");
		query = String.format(
				"select * from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "25");
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store);
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_031_Activar_Articulo_Existente_a_Nivel_Tienda_Tran_Type_25_test";
	}

	@Override
	public String setTestDescription() {
		return "Modificar estatus de liga articulo-tienda en pantalla itemloc de inactivo a activo";
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

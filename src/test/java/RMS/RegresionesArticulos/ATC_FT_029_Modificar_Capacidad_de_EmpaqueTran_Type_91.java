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

public class ATC_FT_029_Modificar_Capacidad_de_EmpaqueTran_Type_91 extends BaseExecution {

	SeleniumUtil u; 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_029_Modificar_Capacidad_de_EmpaqueTran_Type_91_test(HashMap<String, String> data) throws Exception {

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
		String store = data.get("groupValue");
		String SOMI = data.get("SOMI");
		String query;
		
		
		System.out.println("Item a usar: " + item);
		System.out.println("Store a usar: " + store);


		// Paso 1 -2
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 3
		addStep("Seleccionar el menu items y elegir la opcion items");
		t.navItems();

		// Paso 4
		addStep("Seleccionar en el campo Action la opci�n Edit item.\r\n"
				+ "En el campo Item, capturar el n�mero de �tem.\r\n" + "Presionar OK");
		t.EditItemVirtual(item);

		// Paso 5
		addStep("Seleccionar el menu Locations para editar art�culo.");
				addStep("En la pantalla itemloc, seleccionar la tienda. Validar la descripcion corta y larga");
		t.NavLocations();
		t.modifyStoreOrderMultiple(store);

		t.clic_btn_OKItems();
		t.close_RMS_2();
		
		// Paso 6
		addStep("Se revisa que se haya modificado el valor del campo en la tabla itemloc.");

		query = String.format("SELECT IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS, IL.STORE_ORD_MULT FROM ITEM_LOC IL, ITEM_SUPP_COUNTRY_LOC ISCL WHERE\r\n"
				+ "IL.ITEM = ISCL.ITEM AND\r\n"
				+ "IL.LOC = ISCL.LOC AND\r\n"
				+ "IL.PRIMARY_SUPP = ISCL.SUPPLIER AND\r\n"
				+ "IL.STATUS = 'A' AND IL.LOC = '%s' AND IL.ITEM = '%s'", store, item, SOMI);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store);

		// Paso 7
		addStep("Se valida en tabla pos_mods los movimientos insertados para el art�culo-tienda.");

		query = String.format("select * from POS_MODS where ITEM = '%s' AND STORE = '%s'", item, store);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se encontro informacion en la tabla POS_MODS con el item: " + item + " y la tienda: " + store);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_029_Modificar_Capacidad_de_EmpaqueTran_Type_91_test";
	}

	@Override
	public String setTestDescription() {
		return "Modificar en pantalla itemloc el valor del campo store order multiple";
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

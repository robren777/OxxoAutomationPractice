package RMS.RegresionesArticulos;

import static org.testng.Assert.assertFalse;
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

public class ATC_FT_034_Generar_Alta_de_LInea_de_Surtido_Tran_Type_97 extends BaseExecution {

	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_034_Generar_Alta_de_LInea_de_Surtido_Tran_Type_97_test(HashMap<String, String> data)
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
		
		String loc = data.get("ValueStore");
		String suppline = data.get("suppline");
		String warehouse = data.get("warehouse");
		String unidades = data.get("unidades");
		String borrar = data.get("borrar");
		String item = "";
		
		String query;

		// Paso 1
		addStep("Seleccionar articulo y cedis que cumpla con las caracteristicas necesarias para que se generen los tran_type.");
		query = String.format("SELECT IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS FROM ITEM_LOC IL, ITEM_SUPP_COUNTRY_LOC ISCL, XXFC_SUPPLY_LINE_DETAIL SL\r\n"
				+ "WHERE IL.ITEM = ISCL.ITEM \r\n"
				+ "AND ISCL.ITEM = SL.ITEM \r\n"
				+ "AND IL.LOC = ISCL.LOC \r\n"
				+ "AND IL.PRIMARY_SUPP = ISCL.SUPPLIER \r\n"
				+ "AND IL.STATUS = 'A' AND IL.LOC = '%s' and IL.ITEM not in (SELECT ITEM FROM XXFC_SUPPLY_LINE_DETAIL WHERE WH = '%s' and SUPPLY_LINE = '%s') and rownum <= '10'",
				loc,warehouse, suppline);
		
		boolean result = e.ejecutarConsulta(dbRms, query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");

		testCase.addBoldTextEvidenceCurrentStep("Item Seleccionado: ");
		query = String.format("SELECT IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS FROM ITEM_LOC IL, ITEM_SUPP_COUNTRY_LOC ISCL, XXFC_SUPPLY_LINE_DETAIL SL\r\n"
				+ "WHERE IL.ITEM = ISCL.ITEM \r\n"
				+ "AND ISCL.ITEM = SL.ITEM \r\n"
				+ "AND IL.LOC = ISCL.LOC \r\n"
				+ "AND IL.PRIMARY_SUPP = ISCL.SUPPLIER \r\n"
				+ "AND IL.STATUS = 'A' AND IL.LOC = '%s' and IL.ITEM not in (SELECT ITEM FROM XXFC_SUPPLY_LINE_DETAIL WHERE WH = '%s' and SUPPLY_LINE = '%s') and rownum <= '1'",
				loc,warehouse, suppline);
		
		result = e.ejecutarConsulta(dbRms, query);
		assertTrue(result, "No se han encontrado ITEMS que cumplan con el requisito");

		item = e.getData(dbRms, query, 0, "ITEM");

		// Paso 2
		addStep("Ingresar a RMS y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 3
		addStep("Seleccionar el menu Inventory>XXFC-JIT>XXFC-suppline");
		t.navXXFCSuppline();

		// Paso 4
		addStep("Se valida que no exista la l�nea de surtido en la tabla XXFC_SUPPLY_LINE_DETAIL para el art�culo.");
		query = String.format("SELECT * FROM XXFC_SUPPLY_LINE_DETAIL WHERE WH = '%s' and ITEM = '%s'", warehouse, item);
		
		result = e.ejecutarConsulta(dbRms, query);
		assertFalse(result, "Si se encuentran registros en la tabla con el item: " + item);

		// Paso 5
		addStep("Se edita la linea de surtido en la pantalla xxfcsuppline y se agrega el art�culo. Clic en Add item.");
		t.fillEditXXFCSuppline(suppline, item, unidades, borrar);

		t.close_RMS_Menu();
		
		// Paso 6
		addStep("Se valida que se haya insertado la l�nea de surtido en la tabla XXFC_SUPPLY_LINE_DETAIL.");
		query = String.format("SELECT * FROM XXFC_SUPPLY_LINE_DETAIL WHERE WH = '%s' and ITEM = '%s'", warehouse, item);
		
		result = e.ejecutarConsulta(dbRms, query);
		assertTrue(result, "No se encuentran registros en la tabla con el item: " + item);

		// Paso 7
		addStep("Se valida que se haya insertado la informaci�n en la tabla POS_MODS para todas las tiendas relacionadas al articulo.");
		query = String.format("Select * from POS_MODS WHERE ITEM = '%s'", item);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros con item: " + item);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_034_Generar_Alta_de_LInea_de_Surtido_Tran_Type_97_test";
	}

	@Override
	public String setTestDescription() {
		return "Por pantalla Supply Line Maintenance alta de l�nea de surtido.";
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

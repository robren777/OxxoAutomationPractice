package RMS.RegresionesListaArticulos;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_009_CrearListadeArticulos extends BaseExecution {
	SeleniumUtil su;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_CrearListadeArticulos_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		String cb = null;
		boolean st = false;
		su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st,
				data, testCase);

		// Paso 1
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu items y elegir la opcion item List.");
		t.navItemList();

		// Paso 3
		addStep("En la opcion Action, seleccionar New y presionar OK.");
		t.NewItem();

		// Paso 4
		addStep("El sistema arroja el numero de item list y se llenan los campos Description y Comments.");
		t.fillBox();
		
		//Paso 5
		addStep("En el menu Options seleccionar la opcion select Items.");
		t.clicselecItem();
		
		// Paso 6
		addStep("Dar click en el boton Add, capturar el numero de item a anadir y repetir el proceso hasta terminar de anadir todos los items que conforman la lista. Al finalizar dar click en OK.");
		int itemsNo = t.addItems();
	
		// Paso 7
		addStep("En la ventana Item List Header, en el campo Total Items se muestra la cantidad de items agregados a la lista. Dar click en OK para finalizar el proceso.");
		int totalItems = t.valNoItems();
		assertEquals(itemsNo, totalItems, "No se muestra el numero de ITEMS agregados");
		
		t.clicBtnOKItem();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_CrearListadeArticulos_test";
	}

	@Override
	public String setTestDescription() {
		return "Crear una lista de articulos.";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
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
		 su.getDriver().quit();
	}


}

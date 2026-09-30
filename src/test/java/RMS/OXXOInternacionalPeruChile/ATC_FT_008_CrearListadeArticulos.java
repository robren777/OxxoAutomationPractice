package RMS.OXXOInternacionalPeruChile;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class ATC_FT_008_CrearListadeArticulos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_CrearListadeArticulos_test(HashMap<String, String> data) throws Exception {
		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);
		
		String cb = null;

		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();

		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data, testCase);
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu items y elegir la opcion item List.");
		t.navItemList();

		// Paso 3
		addStep("En la opci�n Action, seleccionar New y presionar OK.");
		t.NewItem();

		// Paso 4
		addStep("El sistema arroja el n�mero de item list y se llenan los campos Description y Comments.");
		t.fillBox();
		
		//Paso 5
		addStep("En el men� Options seleccionar la opci�n select Items.");
		t.clicselecItem();
	
		//Paso 6
		addStep("Dar click en el bot�n Add, capturar el n�mero de �tem a a�adir y repetir el proceso hasta terminar de a�adir todos los �tems que conforman la lista. Al finalizar dar click en OK.");
		t.addItems();

		//Paso 7
		addStep("En la ventana Item List Header, en el campo Total Items se muestra la cantidad de �tems agregados a la lista. Dar click en OK para finalizar el proceso.");
		t.clicBtnOKItem();
		t.CloseBrowser();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_CrearListadeArticulos_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_008_CrearListadeArticulos";
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

}

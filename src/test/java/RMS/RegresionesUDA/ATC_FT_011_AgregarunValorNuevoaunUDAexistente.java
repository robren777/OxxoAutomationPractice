package RMS.RegresionesUDA;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.PageObject.Tiendas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_011_AgregarunValorNuevoaunUDAexistente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_AgregarunValorNuevoaunUDAexistente_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
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
		Tiendas t = new Tiendas(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st,
				data, testCase);

		// Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("\"Seleccionar el menu Items, seleccionar el submen� User Defined Atributes y elegir la opcion Edit.");
		t.navUDA();

		// Paso 3
		addStep("Seleccionar el UDA al que se le agregar� el valor nuevo. Dar click en el bot�n Add Value.");
		t.selectUDA();

		// Paso 4
		addStep("Se captura el valor nuevo. Presionar OK.");
		t.AddUDAValue();
		t.clic_btn_OK();
		
		t.CloseBrowser();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_AgregarunValorNuevoaunUDAexistente_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_011_AgregarunValorNuevoaunUDAexistente";
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

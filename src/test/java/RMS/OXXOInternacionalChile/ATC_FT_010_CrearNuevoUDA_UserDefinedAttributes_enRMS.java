package RMS.OXXOInternacionalChile;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_010_CrearNuevoUDA_UserDefinedAttributes_enRMS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_CrearNuevoUDA_UserDefinedAttributes_enRMS_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de CHILE
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDCHRMSCQA, GlobalVariables.DB_USER_BDCHRMSCQA,
				GlobalVariables.DB_PASSWORD_BDCHRMSCQA);
		
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
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Items, seleccionar el submen� User Defined Atributes y elegir la opcion Edit.");
		t.navUDA();

		// Paso 3
		addStep("Dar click en el bot�n Add UDA.	");
		t.clic_btn_addUDA();

		// Paso 4
		addStep("Se llenan los siguientes campos:\r\n" + "  UDA Description >>\r\n" + "  Dialog >>\r\n"
				+ "  Display Type >>\r\n" + "  Data Type >>\r\n" + "  Data Length >>\r\n" + "  Single Value >>\r\n"
				+ "  Presionar Add Value\"");
		t.fillUDA();

		// Paso 5
		addStep("Se agregan los valores de acuerdo a las caracter�sticas del UDA creado. Dar click a OK.");

		t.clic_btn_OK();	
		
		t.close_RMS_Menu();
		t.CloseBrowser();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_CrearNuevoUDA_UserDefinedAttributes_enRMS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_010_CrearNuevoUDA_UserDefinedAttributes_enRMS";
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

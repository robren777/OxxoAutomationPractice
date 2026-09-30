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

public class ATC_FT_019_EliminarZonadePrecios extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_EliminarZonadePrecios_test(HashMap<String, String> data) throws Exception {
		
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

		Tiendas_Jorge t = new Tiendas_Jorge (sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data, testCase);
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso 1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Abrir la carpeta ra�z llamada Control");
		t.OpenControl();
		//Paso 3
		addStep("Escoger la carpeta de �Setup� del men� principal");
		t.Open_Setup();

		//Paso 4
		addStep("Seleccionar Price Zone Group del men� principal");
		t.OpenPriceZoneGroup();

		// Paso 5
		addStep("Selecciona la opci�n Edit en Price Zone Group");
		t.clicEditZG();
		
		//Paso 6
		addStep("Seleccionar el Group Zone");
		t.SelectGZ();
	
		//Paso 7

		addStep("Doble clic al bot�n Zones");
		t.DoubleClicBTNZones();
//		
//		
//		//Paso 8
//		addStep("Se selecciona la Zona ID origen de las tiendas a cambiar");
//		t.select_stores();	
//		
//		//Paso 9
//		addStep("Se agrega la New Zona ID para las tiendas a cambiar");
//		t.Validar_DB();		
//		
//		//Paso 10
//		addStep("Se valida que ya se realiz� el cambio de zonas de precio a las tiendas");
//		t.Validar_DB();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_EliminarZonadePrecios_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_019_EliminarZonadePrecios";
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

package RMS.RegresionesAdministracionProveedores;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.PageObject.Tiendas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_004_ModificarDirecciondeProveedorenRMS extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ModificarDirecciondeProveedorenRMS_test(HashMap<String, String> data) throws Exception {

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
		Tiendas t = new Tiendas(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st,
				data, testCase);

		// Paso 1
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Control y elegir la opci�n Supplier y despu3s la opci�n Maintenance.");
		t.NavMaintenance();
	
		// Paso 3
		addStep("Seleccionar en el campo Action la opci�n Edit y seleccionar el proveedor y dar click en el bot�n OK");
		t.selectActionEditDir();

		// Paso 4
		addStep("En la barra de navegaci�n, en el men� Options seleccionar la opci�n Address");
		t.clicAddress();
		
		// Paso 5
		addStep("En el campo Address Type seleccionar la opcion 01 (Business) y modificar los siguientes campos: address, city, state, country, name, pone, fax, email.");
		t.editAddress01();
		t.clic_btn_OK_dir();

		// Paso 6
		addStep("En el campo Address Type seleccionar la opci�n 04 (Order) y modificar los siguientes campos: address, city, state, country, name, pone, fax, email");
		t.clicAddress();
		t.editAddress02();
		t.clic_btn_OK_dir();
		
		// Paso 7
		addStep("En el campo Address Type seleccionar la opci�n 03 (Returns) y modificar los siguientes campos: address, city, state, country, name, pone, fax, email");
		t.clicAddress();
		t.editAddress03();		
		t.clic_btn_OK_dir();
		
		// Paso 8
		addStep("Dar click en Ok para finalizar el proceso.");
		t.clic_btn_OK_Close();
		t.close_RMS_2();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ModificarDirecciondeProveedorenRMS_test";
	}

	@Override
	public String setTestDescription() {
		return "Se realiza una modificacion a las direcciones de un proveedor en RMS ";
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

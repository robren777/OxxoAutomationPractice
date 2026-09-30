package RMS.RegresionesJerarquiaOrganizacional;

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

public class ATC_FT_003_ActualizarJerarquiaOrganizativadeTiendaenRMS extends BaseExecution {
	SeleniumUtil su;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ActualizarJerarquiaOrganizativadeTiendaenRMS_test(HashMap<String, String> data)
			throws Exception {

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
		System.out.println(dbRms.getHost());
		
		ExecuteQuery e = new ExecuteQuery(testCase);
		String store = data.get("Store");
		String VAT_Region = data.get("VAT_Region");
		String Area = data.get("Area");
		
		// Paso 1
		addStep("Ingresar a RMS a traves de la siguiente liga:" + data.get("rms_url"));
		t.OpenBrowser();

		// Paso 2
		addStep("Capturar campos de login");
		t.Login_En_RMS();
		
		// Paso 3
		addStep("Seleccionar el folder Action, elegir la opcion Organizational Hierarchy y dar clic en el boton open");
		t.Org_Herarky();

		// Paso 4
		addStep("Seleccionar la opcion Store dentro de la jerarquia organizacional.");
		t.OpenStore();

		// Paso 5
		addStep("En la seccion Filter, capturar el numero de tienda en el campo store y dar clic en la tecla tab para que nos arroje el nombre de la tienda y dar clic en el boton Apply filter.");
		t.CapturaTienta();
		
		// Paso 6
		addStep("Dar clic en el boton Edit");
		t.ClicBtnEdit();

		// Paso 7
		addStep("Modificar VAT Region ("+VAT_Region+") y area ("+Area+") y darle OK");
		t.ModifVATRegion();
		t.close_OrgHierarchy();
		t.close_RMS_Menu();

		// Paso 8
		addStep("Accesar a base de datos de RMS Validar en la tabla Store que se hayan actualizado los campos VAT_REGION y DISTRICT.");
		String query = String.format("select * from store where store = '%s' and VAT_REGION = '%s' and DISTRICT = '%s'", store,VAT_Region,Area);
		boolean result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado registro en la tabla");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ActualizarJerarquiaOrganizativadeTiendaenRMS_test";
	}

	@Override
	public String setTestDescription() {
		return "Actualizar la Jerarquia Organizativa de tienda en RMS";
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
		 su.getDriver().quit();
	}

}

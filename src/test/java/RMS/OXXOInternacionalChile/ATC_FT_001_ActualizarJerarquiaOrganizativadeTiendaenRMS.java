package RMS.OXXOInternacionalChile;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_ActualizarJerarquiaOrganizativadeTiendaenRMS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ActualizarJerarquiaOrganizativadeTiendaenRMS_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de CHILE
		 */

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDCHRMSCQA, GlobalVariables.DB_USER_BDCHRMSCQA,
				GlobalVariables.DB_PASSWORD_BDCHRMSCQA);

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
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data, testCase);
		SQLResult executeQuery;
		System.out.println(dbRms.getHost());

		addStep("Ingresar a RMS a trav�s de la siguiente liga:" + data.get("rms_url"));
		addStep("Capturar campos de login");
		t.Login_En_RMS();
		
		//Paso 3
		addStep("Seleccionar el folder Action, elegir la opcion Organizational Hierarchy y dar clic en el bot�n open");
		t.Org_Herarky();

		// Paso 4
		addStep("Seleccionar la opci�n Store dentro de la jerarqu�a organizacional.");
		t.OpenStore();

//		
		// Paso 5
		addStep("En la secci�n Filter, capturar el n�mero de tienda en el campo store y dar clic en la tecla tab para que nos arroje el nombre de la tienda y dar clic en el bot�n Apply filter.");
		t.CapturaTienta();
//		
		// Paso 6
		addStep("Dar clic en el bot�n Edit");
		t.ClicBtnEdit();
//		
//		
		// Paso 7
		addStep("Modificar VAT Region (2) y �rea (81) y darle OK");
		t.ModifVATRegion();
		t.close_RMS();
		t.CloseBrowser();

		// Paso 8
		addStep("Accesar a base de datos de RMS Validar en la tabla Store que se hayan actualizado los campos VAT_REGION y DISTRICT.");
		String tienda = data.get("Store");
		String queryTienda = String.format(Queries_Tiendas.Tbl_Store, tienda);
		System.out.println(queryTienda);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryTienda);
		if (executeQuery.getData(0, "VAT_REGION").equals(data.get("VAT_Region"))) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertEquals(executeQuery.getData(0, "VAT_REGION"), data.get("VAT_Region"));

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ActualizarJerarquiaOrganizativadeTiendaenRMS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_001_ActualizarJerarquiaOrganizativadeTiendaenRMS";
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

package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class ATC_FT_020_CrearNuevaZonadePrecio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_CrearNuevaZonadePrecio_test(HashMap<String, String> data) throws Exception {

		
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

		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		SQLResult executeQuery;
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");		

		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Control, y dentro Setup y la opcion Price Zone Group y presiona new ");
		t.NavOPZ();

		// Paso 3
		addStep("Dar clic al boton add y captura la siguiente informaci�n:\r\n"
				+ ".Zone Group  -----  Pricing Level ----- Description   -----  Like Zone Group ------- y dar clic al boton zones\r\n");
		t.clicbtnaddNZP();

		// Paso 4
		addStep("Dar clic al bot�n Ok");
		t.clicbtnOkUL();
		t.clic_btn_OK();

		t.close_RMS();
		t.CloseBrowser();

		// Paso 5
		addStep("Se consultan las tablas de RMS price_zone_group_store");
		cb = App.getClipboard();

		int cbi = Integer.parseInt(cb);
		cbi = cbi + 1;
		cb = cbi + "";

		String PZGS = String.format(Queries_Tiendas.price_zone_group_store, cb);
		System.out.println(PZGS);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, PZGS);
		boolean stts = executeQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(executeQuery);
		System.out.println(stts);
		assertTrue(stts, "se encontr� el IDZone " + cb);

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_CrearNuevaZonadePrecio_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_020_CrearNuevaZonadePrecio";
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

package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertFalse;

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

public class ATC_FT_047_Crearpromocionincompletaentienda extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_047_Crearpromocionincompletaentienda_test(HashMap<String, String> data) throws Exception {

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
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);

		SQLResult executeQuery;

		// Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Para crear la Promoci�n dirigirse a la opci�n Pricing del men� Retek. "
				+ "Seleccionar e ingresar a la opci�n Promotions.");
		t.NavPricingPromotions();

		// Paso 3
		addStep("Seleccionar del campo Action la opci�n New y presionar el bot�n OK"
				+ "Copiar el ID de la promocion nueva");
		t.getPromoIncompleta();

		// Paso 3
		addStep("Cerrar el formulario sin llenar los campos de datos");
		t.close_RMS_3();
		t.CloseBrowser();

		// Paso 4
		addStep("Ingresar a la BD de RMS RMS_CHILE." + "Validar que no se haya creado la promocion");

		cb = App.getClipboard();

		System.out.println(cb);

		String promotion = App.getClipboard();
		String query = String.format(Queries_Tiendas.tbl_promhead, promotion);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(!executeQuery.isEmpty(), "La promocion no se genero");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_047_Crearpromocionincompletaentienda_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_047_Crearpromocionincompletaentienda";
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

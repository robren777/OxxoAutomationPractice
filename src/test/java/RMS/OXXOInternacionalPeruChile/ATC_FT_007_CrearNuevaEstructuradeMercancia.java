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

public class ATC_FT_007_CrearNuevaEstructuradeMercancia extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_CrearNuevaEstructuradeMercancia_test(HashMap<String, String> data) throws Exception {
		
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
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Action y elegir la opcion Merchandise Hierarchy y dar clic en open.");
		t.MerchaHerar();

		// Paso 3
		addStep("En el campo Compania, seleccionar de la lista una compa�ia dando doble click sobre lo opci�n elegida.");
		t.selectCompania();

		// Paso 4
		addStep("En el campo Supergrupo, seleccionar una opci�n de la lista y dar click en el campo Categoria.");
		t.CapSuperGrupo();

		// Paso 5
		addStep("En el campo Categoria, seleccionar de la lista una categor�a dando doble click sobre la opci�n elegida.");
		t.CapCatego();

		// Paso 6
		addStep("En el campo Subcategoria, seleccionar de la lista una subcategor�a dando doble click sobre la opci�n elegida.");
		t.CapsubCatego();

		// Paso 7
		addStep("En la opci�n Segmento, abrir la lista de opciones en el campo Subcategor�a y seleccionar una opci�n. De la lista de opciones que despliega, elegir el segmento deseado dando doble click sobre el segmento elegido.");
		t.Capsegmento();

		// Paso 8
		addStep("En el campo Subsegmento, dar click en el bot�n New");
		t.clicBtnNew();

		// Paso 9
		addStep("Dar click en el bot�n Add y a�adir un nuevo Subsegmento y presionar el bot�n Ok.");
		t.addcatego();

		t.close_RMS();
		t.CloseBrowser();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_CrearNuevaEstructuradeMercancia_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_007_CrearNuevaEstructuradeMercancia";
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

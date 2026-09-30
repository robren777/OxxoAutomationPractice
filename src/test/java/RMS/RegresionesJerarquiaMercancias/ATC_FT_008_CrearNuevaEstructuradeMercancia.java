package RMS.RegresionesJerarquiaMercancias;

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

public class ATC_FT_008_CrearNuevaEstructuradeMercancia extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_CrearNuevaEstructuradeMercancia_test(HashMap<String, String> data) throws Exception {

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

		String superGrupo = data.get("SuperGrupo");
		String categoria = data.get("Categoria");
		String subcategoria = data.get("Subcategoria");
		String segmento = data.get("Segmento");
		String segmentoDesc = data.get("SegmentoDesc");
		
		// Paso 1
		addStep("Ingresar a RMShttp://10.184.16.184:8029/rmsQA.html y capturar campos User >>Password>>");
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
		t.CapSuperGrupo(superGrupo);
	
		// Paso 5
		addStep("En el campo Categoria, seleccionar de la lista una categor�a dando doble click sobre la opci�n elegida.");
		t.CapCatego(categoria);
	
		// Paso 6
		addStep("En el campo Subcategoria, seleccionar de la lista una subcategor�a dando doble click sobre la opci�n elegida.");
		t.CapsubCatego(subcategoria);

		// Paso 7
		addStep("En la opci�n Segmento, abrir la lista de opciones en el campo Subcategor�a y seleccionar una opci�n. De la lista de opciones que despliega, elegir el segmento deseado dando doble click sobre el segmento elegido.");
		t.Capsegmento(subcategoria, segmento);
		
		// Paso 8
		addStep("En el campo Subsegmento, dar click en el bot�n New");
		t.clicBtnNew(subcategoria, segmento);

		// Paso 9
		addStep("Dar click en el bot�n Add y a�adir un nuevo Subsegmento y presionar el bot�n Ok.");
		t.addcatego(segmentoDesc);
		t.CloseBrowser();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_CrearNuevaEstructuradeMercancia_test";
	}

	@Override
	public String setTestDescription() {
		return "Creaci�n de una nueva estructura de Mercancia";
	}

	@Override
	public String setTestDesigner() {
		return "1000068916 - Jorge Hernandez";
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

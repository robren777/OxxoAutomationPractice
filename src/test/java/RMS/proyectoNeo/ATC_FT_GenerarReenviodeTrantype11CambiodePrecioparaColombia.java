package RMS.proyectoNeo;

import static org.testng.Assert.assertFalse;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.QueriesTranTypes;
import RMS.util.PageObject.TranTypes;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class ATC_FT_GenerarReenviodeTrantype11CambiodePrecioparaColombia extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_GenerarReenviodeTrantype11CambiodePrecioparaColombia_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();		
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);	
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();	
		TranTypes t = new TranTypes(sk, su, new Robot(),
				pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, data, testCase);
		
		SQLResult executeQuery;
		Boolean statusQuery = false;
		System.out.println(dbRms.getHost());		
		
		
		//PASO 1
		addStep("Ingresar a RMS");
		System.out.println("PASO 1"); 
		
		t.loginRMS();
		
		
		// PASO 2
		addStep("Entrar en el apartado Control/XXREO_Control de Operaciones/ "
				+ "XXREO-Reenviar Cat�logos por Tienda");
		System.out.println("PASO 2");
		t.menuControlReenviar();
		
		
		// PASO 3
		addStep("Se revisa la descripci�n corta que se reenviara");
		System.out.println("PASO 3");
		t.formularioReenviar();
		
		su.close();
		
		// PASO 5
		addStep("Se valida en tabla POS_MODS y XXFC.XXFC_POS_MODS_TEMS_COL los movimientos insertados para el art�culo-tienda.");
		System.out.println("PASO 5");
		String item = data.get("item");
		String store = data.get("store");
		
		String posMods = String.format(QueriesTranTypes.POS_MODS, item, store);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No existen registros con ITEM = " + item + ", STORE = " + store
				+ " en POS_MODS");
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_GenerarReenviodeTrantype11CambiodePrecioparaColombia_test";	
	}
	
	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_GenerarReenviodeTrantype11CambiodePrecioparaColombia";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

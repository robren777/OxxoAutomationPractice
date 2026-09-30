package RMS.test;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.PromoEjecutor;
import RMS.util.PageObject.Promociones;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class MTC_FT_001_Crear_Promocion_Simple_ItemList_Fixed_Amount extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Crear_Promocion_Simple_ItemList_Fixed_Amount(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
//		String promotion = data.get("promotion"); // id del registro que vamos a buscar en las tablas 	
		String cb = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Promociones p = new Promociones(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms,
				st, data);
		JobUtil job = new JobUtil(su, testCase, data);

		PromoEjecutor pe = new PromoEjecutor(p);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Configurar una promoci�n simple item list de tipo fixed amount para M�xico.");
		System.out.println("PASO 1");
		// validar la tabla vdate, tiene que estar con fecha de hoy
		// ejecutas IT_setup.sh
		// ejecutas dtesys

//		job.ejecutar("IT_setup.sh");
//		job.ejecutar("dtesys");
//		p.Login_En_RMS();
//		p.Crear_Promocion();
//		p.Configurar_Promocion_Simple_Item_List(); // falta programar las fechas para que ingrese en el RMS fecha inicio y fecha fin con la misma fecha de hoy.
//		p.Submit_Promocion();
//		job.ejecutar("rr_setup.sh_2");
//		p.Validar_DB();
//		p.Aprovar_Promocion();
//		job.ejecutar("rr_setup.sh_2");
		pe.EjecutarPromoSimple();
		String resultadoEjecucion = "A";
		AssertJUnit.assertEquals(resultadoEjecucion, "A");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Crear_Promocion_Simple_ItemList_Fixed_Amount";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ejecuci�n de prueba";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "RMS";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}

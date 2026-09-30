package RMS.test;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.PromocionesComplejas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.pageObject.Monitoring;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class JobTest extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void JobTest(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		// SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		PromocionesComplejas p = new PromocionesComplejas(sk, null, new Robot(), pathimg, ListImage, ImgPatter,
				patterns, Fol, pathImg, dbRms, data, testCase);
		JobUtil job = new JobUtil(u, testCase, data);

		System.out.println(dbRms.getHost());

		addStep("EJECUTAR JOB");

		Monitoring monitoring = new Monitoring(u);
		By hold = By.xpath(
				" //*[@id=\"slsDomainTabset\"]/div/div[4]/tab-content/service-viewpoint/div/div[1]/div[1]/div[3]/ul/li[1]/a/span[2]");

		String ejecutar = "rr_setup.sh_2";

		String resultadoEjecucion = job.ejecutar(ejecutar);
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		System.out.println("RESULTADO: " + resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job " + ejecutar + " no se ejecuto correctamente");

		ejecutar = "runprm";

		resultadoEjecucion = job.ejecutar(ejecutar);
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		System.out.println("RESULTADO: " + resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job " + ejecutar + " no se ejecuto correctamente");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "JobPrueba";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

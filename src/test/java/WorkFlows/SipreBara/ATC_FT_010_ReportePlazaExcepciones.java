package WorkFlows.SipreBara;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_inicioSesionUltimusPega;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userPlaza;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_010_ReportePlazaExcepciones extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_010_ReportePlazaExcepciones_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Workflows Library: Sipre Bara");

		/**
		 * Variables
		 * 
		 */
		String userPega = data.get("userUltimus");
		String userSipre = GlobalVariables.SIPRE_BARA_USER;
		String psSipre = GlobalVariables.SIPRE_BARA_PS;

		/**
		 * 
		 * Utilerías
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url_ultimus"));

		pom_inicioSesionUltimusPega inicioSesion = new pom_inicioSesionUltimusPega(u, testCase, userPega);

		pom_metodosSipreBara_userPlaza pom_userPlaza = new pom_metodosSipreBara_userPlaza(u, testCase);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Plaza");
		inicioSesion.inicioSesion("ULTIMUS", userSipre, psSipre);
		u.hardWait(2);
		/*-----------------------------------------------------------------------------------------*/

		addStep("Generar Reporte Excepciones");

		pom_userPlaza.generarReporte("Exepciones");

	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que el Reporte de Excepciones se exporte correctamente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_ReportePlazaExcepciones_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

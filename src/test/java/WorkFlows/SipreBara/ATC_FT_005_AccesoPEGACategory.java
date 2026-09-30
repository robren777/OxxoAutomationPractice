package WorkFlows.SipreBara;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.SipreBara.pom_inicioSesionUltimusPega;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_AccesoPEGACategory extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_AccesoPEGACategory_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Workflows Library: Sipre Bara");

		/**
		 * Variables
		 * 
		 */
		String userPega = data.get("userPega");

		/**
		 * 
		 * Utiler�as
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url"));

		pom_inicioSesionUltimusPega inicioSesion = new pom_inicioSesionUltimusPega(u, testCase, userPega);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Pega con usuario Categoria");
		inicioSesion.inicioSesion("PEGA_Cat_Plaza", userPega, "");
		u.hardWait(2);

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
		return "Construido. Validar que se pueda ingresar a la aplicaci�n de SIPRE-BARA para el perfil Category";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_AccesoPEGACategory_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

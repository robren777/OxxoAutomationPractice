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

public class ATC_FT_040_GeneraCambioPrecioRegular extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_040_GeneraCambioPrecioRegular_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Workflows Library: Sipre Bara");

		/**
		 * Variables
		 * 
		 */
		String userPega = data.get("userUltimus");
		//String userCat = data.get("userCat");
		String userSipre = GlobalVariables.SIPRE_BARA_USER;
		String psSipre = GlobalVariables.SIPRE_BARA_PS;
		String status_PE = data.get("status_PE");
		String zona_PE = data.get("zona_PE");
		String grupo_PE = data.get("grupo_PE");
		String categoria_PE = data.get("Categ_PE");

		/**
		 * 
		 * Utilerías
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url_ultimus"));

		pom_inicioSesionUltimusPega inicioSesion = new pom_inicioSesionUltimusPega(u, testCase, userPega);
		pom_metodosSipreBara_userPlaza pom_userPlaza = new pom_metodosSipreBara_userPlaza(u, testCase);

		addStep("Inicia Sesion en Ultimus con usuario Plaza");
		inicioSesion.inicioSesion("ULTIMUS", userSipre, psSipre);
		u.hardWait(2);

		addStep("Generar Excepción");

		pom_userPlaza.puntosEjecucion();

		addStep("Seleccionar los siguientes filtros:\r\n" + "1. Region (la configurada en palza)\r\n"
				+ "2. Plaza Precios: Opción\r\n" + "3. Estatus: PENDIENTES PUNTOS DE EJECUCIÓN\r\n"
				+ "4.Zona de Precio:Opción\r\n" + "5. Supergrupo: Opción");

		pom_userPlaza.selectFiltrosPE(status_PE, zona_PE, grupo_PE, categoria_PE);

		addStep("Seleccionar un item");

		String item = pom_userPlaza.selectArtiPE(data.get("nuevoPrecio"), data.get("item"));
		System.out.println("Item ----> " + item);

		addStep("Generar cambio de precio");
		pom_userPlaza.cambioPrecio(data.get("dia"), data.get("item"));

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
		return "Terminado. Validar que al realizar un cambio de PE desde el usuario plaza, se mande la información a RMS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_040_GeneraCambioPrecioRegular_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

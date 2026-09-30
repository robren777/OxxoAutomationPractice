package WorkFlows.SipreBara;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_inicioSesionUltimusPega;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userCategoria;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userPlaza;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_048_CargaInputCategoriasZonasExcluir_2 extends BaseExecution {
	SeleniumUtil u;
	SeleniumUtil k;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_048_CargaInputCategoriasZonasExcluir_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Workflows Library: Sipre Bara");

		/**
		 * Variables
		 * 
		 */
		String userPega = data.get("userUltimus");
		String userCat = data.get("userCat");
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

		addStep("En el cuadrante 4, de Punto de Ejecuión Precio, Capturar en el campo"
				+ "Punto de Ejecución un valor que sobrepse el valor máximo de Punto de Ejecución que nos muestra el cuadrante 3");

		pom_userPlaza.aplicaPE(data.get("nuevoPrecio"));

		addStep("Validar que aparezca el Folio de  en la columna Folio de Excepción");
		pom_userPlaza.solicitudExcepcion();

		k = new SeleniumUtil(new FirefoxTest(), true);
		k.get(data.get("url_ultimus"));

		pom_inicioSesionUltimusPega inicioSesionCat = new pom_inicioSesionUltimusPega(k, testCase, userCat);
		pom_metodosSipreBara_userCategoria pom_userCate = new pom_metodosSipreBara_userCategoria(k, testCase);

		k.hardWait(2);

		addStep("Inicia Sesion en Ultimus con usuario Categoria");
		inicioSesionCat.inicioSesion("ULTIMUS_SIGUIENTE", userSipre, psSipre);
		k.hardWait(2);

		addStep("Seleccionar el menú Respuesta a Excepción");
		pom_userCate.rechazaExcepcion(item);
		k.hardWait(2);
	}

	@AfterMethod
	public void afterMethod() {
		k.getDriver().quit();
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
		return "Terminado. Validar que se rechaze una Excepción de precio fuera de rango de bandas permitidad por el usuario category";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_048_CargaInputCategoriasZonasExcluir_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

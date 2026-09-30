package RDM.cambiosMasivosPmo.hu01;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PRERREQUISITOS: 1. Edge" + "2.-Tener acceso a la aplicacion
 *
 * Filtros requeridos para extraer información de las rutas y tiendas ya
 * existentes en POM son 1.- Plaza 2.- Tipo distribucion 3.- Día generacion 4.-
 * Día entrega 5.- Ruta
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_003_Validarquemuestrelainformaciondelosdiasderutaenorden extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Validarquemuestrelainformaciondelosdiasderutaenorden_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		/*
		 * UTILERIAS
		 */
		SikuliUtil sk = new SikuliUtil();
		SeleniumUtil u = new SeleniumUtil(new IETest());
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a la liga http://10.180.49.86:8071");
		System.out.println("PASO 1");
		// PASO 2
		addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas" + "Capturar:" + "- Usuario" + "- Contraseña"
				+ "Y dar clic al boton de Logon");
		System.out.println("PASO 2");
		c.logInCambiosMasivos();
		// PASO 3
		addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Cambios_Masivos ");
		System.out.println("PASO 3");
		c.clickPmoCambiosMasivos();
		// PASO 4
		addStep("Validar los filtros para extraer la informacion de las rutas y tiendas ya existentes");
		assertTrue(c.validarListaValoresDeFiltros(), "Filtros Validados correctamente");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Edge" + "2.-Tener acceso a la aplicacion";
	}

	@Override
	public String setTestDescription() {
		return "Filtros requeridos para extraer información de las rutas y tiendas ya existentes en POM son:"
				+ "1.- Plaza" + "2.- Tipo distribucion" + "3.- Día generacion" + "4.- Día entrega" + "5.- Ruta";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Validarquemuestrelainformaciondelosdiasderutaenorden_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
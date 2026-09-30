package RDM.cambiosMasivosPmo.hu01;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
 * PRERREQUISITOS: 1. Edge" + "2.-Tener acceso a la aplicacion
 *
 * Filtros requeridos para extraer información de las rutas y tiendas ya
 * existentes en POM son 1.- Plaza 2.- Tipo distribucion 3.- Día generacion 4.-
 * Día entrega 5.- Ruta
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_005_ValidaciondebusquedaenlatabladePMOtienda extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidaciondebusquedaenlatabladePMOtienda_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		String plaza = data.get("plaza");
		String ruta = data.get("ruta");
		String consulta = "SELECT ROUTE,DEST_ID,H_FIN_VENT_ENTREGA,H_EST_ENTREGA FROM RDM100.XXFC_PMO_TIENDA WHERE DEST_ID = 12341 AND ROUTE = 'BTEP602'";
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
		utils.sql.SQLUtil RDM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRD71CU,
				GlobalVariables.DB_USER_FCRD71CU, GlobalVariables.DB_PASSWORD_FCRD71CU);

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
		addStep("El sistema mostrara los filtros requeridos para extraer informacion de las rutas y tiendas ya existentes de la tabla POM_Route"
				+ "1.- Plaza" + "2.- Tipo distribucion" + "3.- Día generacion" + "4.- Día entrega" + "5.- Ruta");
		c.buscarPorPlazaRuta(plaza, ruta);
		u.hardWait(4);
		System.out.println(consulta);
		SQLResult c1 = RDM.executeQuery(consulta);
		testCase.addQueryEvidenceCurrentStep(c1);
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
		return "ATC_FT_005_ValidaciondebusquedaenlatabladePMOtienda_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
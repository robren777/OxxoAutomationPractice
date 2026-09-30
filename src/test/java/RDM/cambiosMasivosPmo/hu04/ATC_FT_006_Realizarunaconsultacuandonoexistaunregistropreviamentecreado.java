package RDM.cambiosMasivosPmo.hu04;

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
 * PRERREQUISITOS: Contar con la liga de acceso a RDM Contar con credenciales
 * para RDM NOTA: No tener registros guardados
 *
 * "Realizar una prueba de consulta cuando no existan previamente registros generados
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_006_Realizarunaconsultacuandonoexistaunregistropreviamentecreado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Realizarunaconsultacuandonoexistaunregistropreviamentecreado_test(
			HashMap<String, String> data) throws Exception {

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
		addStep("Dar clic al menu: Support Functions > Reports > PMO_Config_Ruta_Ciclo");
		System.out.println("PASO 3");
		c.clickPmoConfigRutaCiclo();
		// PASO 4
		addStep("No tener registros previamente creados");
		c.nEnter(1);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(6);
		// PASO 5
		addStep("Dar clic al boton Consultar");
		assertTrue(c.validarAlertaSinRegistos(), "Pantalla validada");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM" + "- Contar con credenciales para RDM"
				+ " NOTA: No tener registros guardados ";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba de consulta cuando no existan previamente registros generados";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Realizarunaconsultacuandonoexistaunregistropreviamentecreado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
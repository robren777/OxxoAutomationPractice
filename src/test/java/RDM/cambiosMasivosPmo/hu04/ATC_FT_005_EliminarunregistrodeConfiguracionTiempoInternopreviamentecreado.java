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
 * para RDM NOTA: EJECUTAR PRIMERO CASO
 * ATC_FT_002_AgregarunanuevaConfiguraciondeTiempoInterno
 *
 * Realizar una prueba que nos permita visualizar correctamente la baja de
 * Configuracion Tiempo Interno previamente creado
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_005_EliminarunregistrodeConfiguracionTiempoInternopreviamentecreado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_EliminarunregistrodeConfiguracionTiempoInternopreviamentecreado_test(
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
		addStep("Dar clic al boton Consultar");
		c.nEnter(1);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(6);
		assertTrue(c.validarConsultaTiempoGuardado(), "Pantalla validada");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 5
		addStep("Dar clic al boton Eliminar");
		c.clickEliminarConfiguracion();
		testCase.addScreenShotCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM" + "- Contar con credenciales para RDM"
				+ " NOTA: EJECUTAR PRIMERO CASO ATC_FT_002_AgregarunanuevaConfiguraciondeTiempoInterno";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba que nos permita visualizar correctamente la baja de Configuracion Tiempo Interno previamente creado";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_EliminarunregistrodeConfiguracionTiempoInternopreviamentecreado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
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
 * para RDM
 *
 * Realizar una prueba que nos permita validar que no haya campos nulos (sin
 * capturar)
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_012_ValidarObligatoriedadDeLosCampos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_ValidarObligatoriedadDeLosCampos_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */

		String distribucion = data.get("distribucion");
		String ruta = data.get("ruta");
		String plaza = data.get("plaza");
		String cilco = data.get("cilco");
		String produccion = data.get("produccion");
		String carga = data.get("carga");
		String verificacion = data.get("verificacion");
		String jit = data.get("jit");
		String envio = data.get("envio");

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
		assertTrue(c.validarPantallaConfiguracionTiempoInterno(), "Pantalla validada");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 4
		addStep("No capturar los campos:" + "- Tipo de Distribución (Vacio)" + "- Tipo Ruta (Vacio)" + "- Plaza (Vacio)"
				+ "- Tiempo Cilco (Vacio)" + "- Producción (Vacio)" + "- Verificación (Vacio)" + "- Carga (Vacio)"
				+ "- Corte Jit (Vacio)" + "- Corte Envio (Vacio)");
		c.agregarCambiosMasivos(distribucion, ruta, plaza, cilco, produccion, carga, verificacion, jit, envio);
		c.nEnter(1);
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.validarTipoDistribucionVacio(), "Pantalla validada");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM" + "- Contar con credenciales para RDM";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba que nos permita validar que no haya campos nulos (sin capturar)";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_ValidarObligatoriedadDeLosCampos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
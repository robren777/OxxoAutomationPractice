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
 * Realizar una prueba donde nos permita validar que los campos: Produccion,
 * Verificacion, Carga. Corte Jit, Corte Envio. Estos sean mayor a 1 segundo y
 * menos a 24 horas
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_015_ValidarLimitesCamposProduccionVerificacionCargaCorteJitCorteEnvio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_ValidarLimitesCamposProduccionVerificacionCargaCorteJitCorteEnvio_test(
			HashMap<String, String> data) throws Exception {

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
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.validarPantallaConfiguracionTiempoInterno(), "Pantalla validada");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 4
		addStep("Dar clic al boton de Agregar y Capturar los campos:" + "- Tipo de Distribución (P)"
				+ "- Tipo Ruta (LOCAL)" + "- Plaza (10GUD)" + "- Tiempo Cilco (2)" + "- Producción (00:00:00)"
				+ "- Verificación (00:00:00)" + "- Carga (00:00:00)" + "- Corte Jit (00:00:00)"
				+ "- Corte Envio (00:00:00)");
		c.agregarCambiosMasivos(distribucion, ruta, plaza, cilco, produccion, carga, verificacion, jit, envio);
		c.nEnter(1);
		testCase.addScreenShotCurrentStep(" ");
		assertTrue(c.validarVerificacionCero(), "Pantalla validada");
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
		return "Realizar una prueba donde nos permita validar que los campos: Produccion, Verificacion, Carga. Corte Jit, Corte Envio. Estos sean mayor a 1 segundo y menos a 24 horas";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_ValidarLimitesCamposProduccionVerificacionCargaCorteJitCorteEnvio_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
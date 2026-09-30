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

/**
 * PRERREQUISITOS: 1. Edge" + "2.-Tener acceso a la aplicacion
 *
 * La información de los dias de la ruta, debera ser mostrada en el siguiente
 * orden 1.-Días de generacion 2.-Días de Entrega 3.-Dias de Programacion
 * 4.-Días de Produccion 5.-Días de Verificacion 6.-Días de Carga 7.-Dia de
 * salida 8.-Dia de salida PT 9.-Orden en los que esten el resto de los campos.
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_006_Validaciondemuestradeinformacionordenadamente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Validaciondemuestradeinformacionordenadamente_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		String plaza = data.get("plaza");

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
		addStep("Validar que la informacion se muestre en orden");
		c.buscarPorPlaza(plaza);
		u.hardWait(4);
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
		return "La información de los dias de la ruta, debera ser mostrada en el siguiente orden"
				+ "1.-Días de generacion" + "2.-Días de Entrega" + "3.-Dias de Programacion" + "4.-Días de Produccion"
				+ "5.-Días de Verificacion" + "6.-Días de Carga" + "7.-Dia de salida" + "8.-Dia de salida PT"
				+ "9.-Orden en los que esten el resto de los campos.";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Validaciondemuestradeinformacionordenadamente_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
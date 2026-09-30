package EBS.nexxo.cho52084072;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_AplicarRenegociacionMasivaTipoAdendumconresultadocorrectoauncontratomigradoconNEXXO extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_AplicarRenegociacionMasivaTipoAdendumconresultadocorrectoauncontratomigradoconNEXXO_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String descripcion = data.get("descripcion");
		String categoria = data.get("categoria");
	
		/*
		 * UTILERIAS
		 */

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084072
		 */

		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickAdmonOSACTFIJOOSinterno();
		paginaInicial.clickCargaMasivaRenegociaciones();
		p.runApplet();
		u.hardWait(3);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(descripcion);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		p.ejecutarBotonAnexos();
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(4);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(categoria);
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_AplicarRenegociacionMasivaTipoAdendumconresultadocorrectoauncontratomigradoconNEXXO_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el proceso de Carga Masiva de Renegociaciones Tipo Adendum finalice sin error al utilizar un contrato Migrado por Nexxo. Pre-requisistos: 1.- Contar con acceso a la responsabilidad MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS"
				+ "2.- Previamente haber ejecutado el proceso XXFC PN Reclasificacion de Contratos y Direcciones NEXXO CP005"
				+ "3.- Contar con el archivo .txt que se procesará; este se genera a traves del archivo PLANTILLA_RENEGOCIACIONES.xlsx"
		        + "Nota: este caso no puede automatizarce debido a los procesos variables que tienen sus pasos";
		
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Contar con acceso a la responsabilidad MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS"
				+ "2.- Previamente haber ejecutado el proceso XXFC PN Reclasificacion de Contratos y Direcciones NEXXO CP005"
				+ "3.- Contar con el archivo .txt que se procesará; este se genera a traves del archivo PLANTILLA_RENEGOCIACIONES.xlsx"
		        + "Nota: este caso no puede automatizarce debido a los procesos variables que tienen sus pasos";
	}

	@Override
	public void beforeTest() {
		
	}

}
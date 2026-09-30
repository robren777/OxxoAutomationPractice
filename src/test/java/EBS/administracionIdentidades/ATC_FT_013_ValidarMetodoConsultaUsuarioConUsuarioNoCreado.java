package EBS.administracionIdentidades;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_013_ValidarMetodoConsultaUsuarioConUsuarioNoCreado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_013_ValidarMetodoConsultaUsuarioConUsuarioNoCreado_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = data.get("password");
		String ebsPattern = data.get("ebsPattern")+"\\EBS";
		String mensajeError = data.get("mensajeError");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath(ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
		 */

		// PASO 1
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
		addStep("Se envia mensaje de error");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Error login");
		u.hardWait(5);
		assertEquals(mensajeError, paginaInicial.getTextMensajeError());
		u.hardWait(3);
		u.close();

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Nombre de usuario: (Número de empleado). Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
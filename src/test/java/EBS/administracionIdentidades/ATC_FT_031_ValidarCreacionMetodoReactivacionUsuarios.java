package EBS.administracionIdentidades;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_031_ValidarCreacionMetodoReactivacionUsuarios extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_031_ValidarCreacionMetodoReactivacionUsuarios_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String usuarios = data.get("carpeta1");
		String usuario = data.get("usuario");
		String ebsPattern = data.get("ebsPattern")+"\\EBS";

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
		addStep("Ingresar a la responsabilidad 'MXOXXO-HELP-DESK'desde el usuario existente: 6006756");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Administracion de Almacén -> Otros -> Solicitudes -> Requests");
		paginaInicial.clickOnCarpeta(usuarios);
		paginaInicial.clickRunUsuariosHD();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(10);
		// PASO 7
		addStep("Ingresar el Nombre de Usuario:IDMUSER01");
		patternUtil.f11();
		patternUtil.enterText(usuario);
		u.hardWait(5);
		// PASO 8
		addStep("Seleccionar la tecla ctrl + F11");
		patternUtil.ctrlF11();
		patternUtil.validatePattern("usuarioAlta");
		assertTrue(patternUtil.validatePattern("usuarioAlta"));
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "entrada:Nombre de usuario: (Número de empleado)·Contraseña·Nombre·Correo·Responsabilidades (Arreglo de perfiles)·Fecha efectiva de asignación de responsabilidades·Fecha efectiva de remoción de responsabilidades. Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM con su actualización cómo fallida";
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
		return "Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM con su actualización cómo fallida";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
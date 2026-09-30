package EBS.administracionIdentidades;

import static org.testng.Assert.assertEquals;
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

public class ATC_FT_016_ValidarMetodoActualizarDatosUsuariosFallido extends BaseExecution{
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_016_ValidarMetodoActualizarDatosUsuariosFallido_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String usuario = data.get("usuario");
		String ebsPattern = data.get("ebsPattern")+"\\EBS";
		String responsabilidad2 = data.get("responsabilidad2");
		String rfc = data.get("RFC");
		String curp = data.get("CURP");


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
		addStep("Ingresar a la responsabilidad 'MXOXXO-HELP-DESK'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: MXOXXO-HELP-DESK -> Usuarios");
		paginaInicial.clickRunUsuariosHD();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(10);
		// PASO 7
		addStep("Ingresar el Nombre de Usuario:75681455");
		patternUtil.f11();
		u.hardWait(3);
		patternUtil.enterText(usuario);
		u.hardWait(5);
		// PASO 8
		addStep("Seleccionar la tecla ctrl + F11");
		patternUtil.ctrlF11();
		u.hardWait(3);
		// PASO 9
		patternUtil.keyAlt("a");
		u.hardWait(10);
		patternUtil.clickImage("responsabilidadesIndirectas");
		// PASO 10
		addStep("Atributos seguridad");
		u.hardWait(10);
		patternUtil.clickImage("atributosSeguridad");
		patternUtil.validatePattern("atributosSeguridad");
		assertTrue(patternUtil.validatePattern("atributosSeguridad"));
		// PASO 11
		addStep("Ir a la responsabilidad: MXOXXO-HRXXXXX-Configurador");
		patternUtil.closeWindowInsideApplet();
		patternUtil.keyAlt("a");
		patternUtil.keyAlt("s");
		u.hardWait(10);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(10);
		patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(3);
		patternUtil.enterText(responsabilidad2);
		u.hardWait(3);
		patternUtil.keyAlt("b");
		u.hardWait(3);
		// PASO 12
		addStep("Ir a la opción Personas/Ingresar y Mantener e ingresar el número de empleado");
		patternUtil.keyAlt("b");
		patternUtil.keyAlt("b");
		patternUtil.nTAB(3);
		patternUtil.enterText(usuario);
		// PASO 13
		addStep("Seleccionar la opción 'Encontrar'.");
		patternUtil.keyAlt("n");
		u.hardWait(8);
		// PASO 14
		addStep("Ir a opción: Detalles adicionales personales.");
		patternUtil.clickImage("detallesAdicionalesP");
		u.hardWait(3);
		assertEquals(rfc, patternUtil.textCopy());
		patternUtil.nTAB(1);
		assertEquals(curp, patternUtil.textCopy());
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
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
		return "Los campos de entrada para la creación del usuario serian los siguientes:Nombre de usuario: (Número de empleado)·Contraseña·Nombre·Correo·Responsabilidades (Arreglo de perfiles)·Fecha efectiva de asignación de responsabilidades·Fecha efectiva de remoción de responsabilidades. Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM con su actualización cómo fallida";
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
package EBS.administracionIdentidades;

import static org.testng.Assert.assertFalse;
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
import utils.sql.SQLResult;

public class ATC_FT_061_ValidarBajaModuloCompras extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_061_ValidarBajaModuloCompras_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String tipoEvento = data.get("tipoEvento");
		String responsabilidad = data.get("responsabilidad");
		String usuario = data.get("usuario");
		String ebsPattern = data.get("ebsPattern")+"\\EBS";

		String consulta1 = "select * from apps.xxfc_idm_movtos_all where TIPO_EVENTO = 'INACTIVAR_USR_PRC' and NOMBRE_USUARIO = '75681455'";

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

		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
		 */

		// PASO 1
		addStep("Ir a la BD del servidor AVEBQA");
		u.hardWait(3);
		System.out.println(consulta1);
		// PASO 2
		addStep("select * from apps.xxfc_idm_movtos_all where NOMBRE_USUARIO = '98767896' and TIPO_EVENTO = 'CAMBIO_RESPO_PRC' and IDM_USR_ID = '458'");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "TIPO_EVENTO");
		boolean validar = atributo.equals(tipoEvento);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 3
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 4
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 5
		addStep("Ingresar usuario y contrasena.");
		// PASO 6
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 7
		addStep("Ingresar a la responsabilidad 'MXOXXO-HELP-DESK'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 8
		addStep("Seleccionar: MXOXXO-HELP-DESK -> Usuarios");
		paginaInicial.clickRunUsuariosHD();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(10);
		// PASO 9
		addStep("Ingresar el Nombre de Usuario:75681455");
		patternUtil.f11();
		u.hardWait(3);
		patternUtil.enterText(usuario);
		u.hardWait(5);
		// PASO 10
		addStep("Seleccionar la tecla ctrl + F11");
		patternUtil.ctrlF11();
		u.hardWait(3);
		// PASO 11
		u.hardWait(10);
		patternUtil.clickImage("responsabilidadesIndirectas");
		// PASO 12
		addStep("Atributos seguridad");
		u.hardWait(10);
		patternUtil.clickImage("atributosSeguridad");
		patternUtil.validatePattern("atributosSeguridad");
		assertTrue(patternUtil.validatePattern("atributosSeguridad"));
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
		return "La búsqueda se realizará por el Nombre de usuario -Nombre de usuario: (Número de empleado). Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM";
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
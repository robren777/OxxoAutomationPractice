package EBS.moduloSaldoDiario;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_EjecutarProcesoCierreAutomaticoSaldoDiario extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_EjecutarProcesoCierreAutomaticoSaldoDiario_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String otros = data.get("carpeta1");
		String concurrente = data.get("concurrente");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);
		GenericMethods action = new GenericMethods(u);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: SALDO DIARIO
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Otros --> Solicitudes");
		paginaInicial.clickOnCarpeta(otros);
		paginaInicial.clickRunSolicitudesGLSuperUsuario();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(25);
		// PASO 7
		addStep("Ingresar a la siguiente ruta: Ver/solicitudes/Ejecutar Solicitud");
		patternUtil.keyAlt("n");
		u.hardWait(3);
		// PASO 8
		addStep("Ejecutar proceso: XXFC-Cierre Automatico Saldo Diario (Nuevo)");
		patternUtil.typeAndTab(concurrente);
		u.hardWait(3);
		// PASO 9
		addStep("Ingresa los valores de los parámetros de ejecución: Plaza, Tienda");
		patternUtil.typeAndTab(plaza);
		u.hardWait(3);
		patternUtil.enterText(tienda);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 10 
		addStep("Clic en el botón EJECUTAR");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 11
		addStep("Validación de la salida de proceso: Arrastre de saldo Diario Definitivo");
		u.hardWait(5);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		u.hardWait(3);
		patternUtil.clickVisualizarOutput();
		u.hardWait(10);
		assertEquals(p.getText(plaza),plaza);
		u.hardWait(3);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(5);
		patternUtil.clickOracle();
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "La salida de estos procesos mostrarán el IVA del impuesto estatal de la fuente. Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/";
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
		return "Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
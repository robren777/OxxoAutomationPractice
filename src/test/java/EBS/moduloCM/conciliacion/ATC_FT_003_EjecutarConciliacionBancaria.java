package EBS.moduloCM.conciliacion;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
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

public class ATC_FT_003_EjecutarConciliacionBancaria extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_EjecutarConciliacionBancaria_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String bankStatements = data.get("carpeta1");
		String numeroCuenta = data.get("numeroCuenta");
		String numeroEstadoCuenta = data.get("numeroEstadoCuenta");
		String fechaContable = data.get("fechaContable");
		String estado = data.get("estado");

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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: PRE Y CONCILIACIÓN
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-CMXXXXX-Super Usuario'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Bank Statements --> Bank Statement Interface");
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView();", paginaInicial.carpetaBankStatementsCMSuperUsuarioElement);
		paginaInicial.clickOnCarpeta(bankStatements);
		paginaInicial.clickRunBankStatementInterfaceCMSuperUsuario();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(25);
		// PASO 7
		addStep("Capturar el estado de cuenta a buscar/clic botón Buscar");
		patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(3);
		patternUtil.enterText(numeroCuenta);
		u.hardWait(1);
		patternUtil.keyAlt("b");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 8
		addStep("Ingresar a la siguiente liga: Herramienta/Conciliación automática");
		patternUtil.keyAlt("h");
		u.hardWait(5);
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.keyAlt("y");
		u.hardWait(1);
		patternUtil.nTAB(7);
		u.hardWait(3);
		patternUtil.enterText(fechaContable);
		u.hardWait(8);
		// PASO 9
		addStep("Ejecutar conciliación");
		patternUtil.clickImage("ejecutar1");
		u.hardWait(3);
		patternUtil.clickAceptarParametros();
		u.hardWait(3);
		patternUtil.clickAceptarParametros();
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		u.hardWait(1);
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		assertEquals(numeroEstadoCuenta, patternUtil.textCopy());
		u.hardWait(3);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(5);
		patternUtil.clickOracle();
		u.hardWait(3);
		// PASO 10
		addStep("Ingresar a la siguiente ruta: MXXXXX-CEXXXXX-Super Usuario/Estados Bancarios/Estados Bancarios y Conciliación");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.nDOWN(10);
		patternUtil.keyAlt("b");
		patternUtil.nDOWN(1);
		patternUtil.keyAlt("b");
		u.hardWait(8);
		// PASO 11
		addStep("Capturar los parámetros:");
		patternUtil.nTAB(1);
		u.hardWait(1);
		patternUtil.enterText(fechaContable);
		u.hardWait(1);
		patternUtil.nTAB(1);
		u.hardWait(1);
		patternUtil.typeAndTab(numeroEstadoCuenta);
		u.hardWait(8);
		patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(3);
		patternUtil.enterText(fechaContable);
		u.hardWait(3);
		patternUtil.keyAlt("b");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 12
		addStep("Clic en el botón ENCONTRAR");
		patternUtil.keyAlt("c");
		u.hardWait(3);
		// PASO 13
		addStep("Seleccionar estado Bancario/Clic en el botón REVISAR");
		patternUtil.keyAlt("i");
		u.hardWait(3);
		// PASO 14
		addStep("Clic en el botón LINEAS");
		patternUtil.keyAlt("l");
		u.hardWait(3);
		patternUtil.nTAB(8);
		u.hardWait(10);
		assertEquals(estado, patternUtil.textCopy());
		u.hardWait(5);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(5);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(5);
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
		return "";
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
		return "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
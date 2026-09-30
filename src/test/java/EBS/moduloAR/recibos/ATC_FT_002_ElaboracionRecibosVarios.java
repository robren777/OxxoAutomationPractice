package EBS.moduloAR.recibos;

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

public class ATC_FT_002_ElaboracionRecibosVarios extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ElaboracionRecibosVarios_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String metodoRecibo = data.get("metodoRecibo");
        String numeroRecibo = data.get("numeroRecibo");
        String importe = data.get("importe");
        String tipoRecibo = data.get("tipoRecibo");
        String actividad = data.get("actividad");
		
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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: COMPROBANTES ELECTRÓNICOS
		 */
		
		// PASO 1
		addStep("Ingresar EBS Peru");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		// PASO 2
		addStep("Ingresar responsabilidad MXOXXO-AR10GTO-RECIBOS--> Recibos");
		paginaInicial.clickOnResponsabilidad(responsabilidad);	
		paginaInicial.clickrunRecibosAR10GTORecibos();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("runApplet");
		patternUtil.runApplet2();
		u.hardWait(33);
		testCase.addScreenShotCurrentStep("runApplet");
		// PASO 3
		addStep("Ingresar los parámetros correspondientes");
		patternUtil.enterText(metodoRecibo);
		u.hardWait(3);
		patternUtil.nTAB(2);
		u.hardWait(3);
		patternUtil.enterText(numeroRecibo);
		u.hardWait(3);
		patternUtil.nTAB(3);
		u.hardWait(3);
		patternUtil.enterText(importe);
		u.hardWait(5);
		patternUtil.nTAB(2);
		u.hardWait(5);
		patternUtil.typeAndTab(tipoRecibo);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("parametros");
		// PASO 4
		addStep("Ingresar actividad");
		patternUtil.clickImage("actividad");
		testCase.addScreenShotCurrentStep("actividad");
		u.hardWait(3);
		patternUtil.typeAndTab(actividad);
		testCase.addScreenShotCurrentStep("actividad");
		u.hardWait(3);
		
		// PASO 5
		addStep("Dar click en Distribuciones");
		patternUtil.keyAlt("s");
		testCase.addTextEvidenceCurrentStep("Distrinuciones");
		u.hardWait(10);
		patternUtil.clickImage("aceptarBoton");
		testCase.addScreenShotCurrentStep("aceptarBoton");
		u.hardWait(3);
		//PASO 6
		addStep("Cerrar ventanas");
		patternUtil.closeWindowInsideApplet();
		testCase.addScreenShotCurrentStep("closeWindowInsideApplet");
		patternUtil.closeActiveWindow();
		testCase.addScreenShotCurrentStep("closeActiveWindow");
		patternUtil.closeWindow();
		testCase.addScreenShotCurrentStep("closeWindow");
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
		return "Elaboración manual Recbios Varios";
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
		return "Elaboración manual Recbios Varios";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
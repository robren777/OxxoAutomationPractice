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

public class ATC_FT_001_ElaboracionRecibosStandard extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ElaboracionRecibosStandard_test(HashMap<String, String> data) throws Exception {
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
        String nombre = data.get("nombre");
        String aplicarA = data.get("applicarA");
		
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
		u.hardWait(5);
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
		u.hardWait(1);
		patternUtil.nTAB(2);
		u.hardWait(1);
		patternUtil.enterText(numeroRecibo);
		u.hardWait(1);
		patternUtil.nTAB(3);
		u.hardWait(1);
		patternUtil.enterText(importe);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("parametros");
		// PASO 4
		addStep("Ingresar nombre");
		patternUtil.clickImage("nombreRecibos");
		u.hardWait(3);
		patternUtil.typeAndTab(nombre);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("nombreRecibos");
		// PASO 5
		addStep("Aplicar a: 13187 y oprime tecla tab");
		patternUtil.keyAlt("l");
		u.hardWait(15);
		patternUtil.enterText(aplicarA);
		testCase.addScreenShotCurrentStep("aplicarA");
		patternUtil.nTAB(1);
		testCase.addScreenShotCurrentStep("ntab");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("wait");
		//PASO 6
		addStep("Click en grabar o disco amarillo");
		patternUtil.clickImage("carpeta");
		testCase.addScreenShotCurrentStep("clickImage");
		u.hardWait(15);
		patternUtil.closeWindowInsideApplet();
		testCase.addScreenShotCurrentStep("closeWindowInsideApplet");
		u.hardWait(10);
		patternUtil.closeActiveWindow();
		testCase.addScreenShotCurrentStep("closeActiveWindow");
		u.hardWait(5);
		patternUtil.clickImage("si");
		u.hardWait(5);
		patternUtil.clickImage("aceptar");
		patternUtil.closeWindow();
		testCase.addScreenShotCurrentStep("closeWindow");
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		testCase.addScreenShotCurrentStep("ventanaConfirmarSalir");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Elaboración manual Recbios Standard";
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
		return "Elaboración manual Recbios Standard";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
package EBS.avante.cho52068895;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarConcurrenteXXFCPaso1 extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarConcurrenteXXFCPaso1_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String almacen = data.get("carpeta1");
		String otros = data.get("carpeta2");
		String solicitudes = data.get("carpeta3");
		String requests = data.get("carpeta4");
		String campo = data.get("campo");
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
		// File Fol = new File(pathImg);
		File Folder = new File(pathImg);
		GenericMethods action = new GenericMethods(u);

		// MetodosSikuli p = new MetodosSikuli(sk, u , pathimg, ListImage, ImgPatter,
		// patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52068895
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Administracion de Almacén -> Otros -> Solicitudes -> Requests");
		paginaInicial.clickOnCarpeta(almacen);
		paginaInicial.clickOnCarpeta(otros);
		paginaInicial.clickOnCarpeta(solicitudes);
		paginaInicial.clickOnCarpeta(requests);
		paginaInicial.clickRunWmCoEqpFrm();
		u.hardWait(10);
		patternUtil.runApplet();
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Ingresar en el campo Nombre 'XXFC - Preparar Reservas Inventario Fisico (Paso 1)' ");
		patternUtil.typeAndTab(campo);
		// PASO 9
		addStep("Validar que exista el concurrente 'XXFC - Preparar Reservas Inventario Fisico (Paso 1)'");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.keyAlt("d");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarConcurrenteXXFCPaso1_test";
	}

	@Override
	public String setTestDescription() {
		return "Descripcion y Prerequisitos";
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
		return "Validar que exista el concurrente 'XXFC - Preparar Reservas Inventario Fisico (Paso 1)' dentro de la responsabilidad: 'MXOXXO-WM17FAA-COORDINADOR EQP FRM' Acceso a la responsabilidad: 'MXOXXO-WM17FAA-COORDINADOR EQP FRM' ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}

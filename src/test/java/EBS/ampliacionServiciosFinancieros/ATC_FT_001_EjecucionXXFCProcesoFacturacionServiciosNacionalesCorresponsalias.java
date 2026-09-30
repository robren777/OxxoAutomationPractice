package EBS.ampliacionServiciosFinancieros;

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

public class ATC_FT_001_EjecucionXXFCProcesoFacturacionServiciosNacionalesCorresponsalias extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_EjecucionXXFCProcesoFacturacionServiciosNacionalesCorresponsalias_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String campo = data.get("campo");
		String fechainicio = data.get("fechainicio");
		String fechafin = data.get("fechafin");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();

		// PASO 5													
		addStep("Seleccionar cualquiera de las responsabilidades: 1.MXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS "
				+ "2-MXOXXO_GL17ADM_SERVICIOS ");
		u.hardWait(5);
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Dar clic en el menu: Informes: Solicitar/Standard");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickcarpetaSolicitar();
		paginaInicial.clickrunStandard();
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("runApplet");

		// PASO 7
		addStep("Seleccionar Solicitud unica y Aceptar");
		p.inicio();
		testCase.addScreenShotCurrentStep("inicio");

		// PASO 8
		addStep("Posicionarse sobre campo Nombre y oprimir …");
		p.ingresarCampo(campo);
		p.ingresarFechas(fechainicio, fechafin);
		testCase.addScreenShotCurrentStep("datos");
		p.ejecutar();
		testCase.addScreenShotCurrentStep("ejecutar");
		
		// PASO 9
		addStep("Sobre campo Buscar ingresar XXFC- Proceso de Facturacion Servicios Nacionales Corresponsalias "
				+ "Oprimir Buscar");
		p.otraSolicitud();
		testCase.addScreenShotCurrentStep("otraSolicitud");
		u.hardWait(3);
		p.vers();
		testCase.addScreenShotCurrentStep("vers");
		p.encontrar();
		testCase.addScreenShotCurrentStep("encontrar");
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
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con la responsabilidad registrada" + "Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se muestre concurrente existente XXFC-Proceso de Facturacion Servicios Nacionales Corresponsalias."
				+ "Prerequisitos: Contar con la responsabilidad registrada"
				+ "Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_EjecucionXXFCProcesoFacturacionServiciosNacionalesCorresponsalias_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

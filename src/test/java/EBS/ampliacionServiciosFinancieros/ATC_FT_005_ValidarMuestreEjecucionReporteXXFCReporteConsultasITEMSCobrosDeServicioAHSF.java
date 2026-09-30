package EBS.ampliacionServiciosFinancieros;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;



public class ATC_FT_005_ValidarMuestreEjecucionReporteXXFCReporteConsultasITEMSCobrosDeServicioAHSF extends BaseExecution {

	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarMuestreEjecucionReporteXXFCReporteConsultasITEMSCobrosDeServicioAHSF_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String subcarpeta = data.get("subcarpeta");
		String ebsPattern = data.get("ebsPattern");
		String nombreSolicitud = data.get("nombre");
		
		

		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath(ebsPattern)+"\\EBS";
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Seleccionar la responsabilidad de " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Dar clic en el menu: Informes: Solicitar/Standard");
		paginaInicial.clickOnInformesSolicitudStandard();
		u.hardWait(5);
		patternUtil.runApplet();
		u.hardWait(20);
		// PASO 7
		addStep("Seleccionar Solicitud unica y presionamos Aceptar");
		patternUtil.solicitudUnica();
		u.hardWait(10);
		// PASO 8
		addStep("Ingresamos los parametros");
		patternUtil.typeAndTab(nombreSolicitud);
		
				
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con la responsabilidad registrada "
				+ "Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se muestre el concurrente para ejecutar Reporte de Consultas ITEMS Cobros de Servicios."
				+ "Prerequisitos: Contar con la responsabilidad registrada "
				+ "Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarMuestreEjecucionReporteXXFCReporteConsultasITEMSCobrosDeServicioAHSF";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}


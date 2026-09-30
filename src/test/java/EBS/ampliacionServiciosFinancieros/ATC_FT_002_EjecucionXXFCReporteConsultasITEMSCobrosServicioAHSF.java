package EBS.ampliacionServiciosFinancieros;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_EjecucionXXFCReporteConsultasITEMSCobrosServicioAHSF extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_EjecucionXXFCReporteConsultasITEMSCobrosServicioAHSF_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
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
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS instancia México");
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
		addStep("Seleccionar la responsabilidad de MXOXXO-GLXXXXX-CONSULTAS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Dar clic en el menu: Informes: Solicitar/Standard");
		paginaInicial.clickOnCarpeta(carpeta);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con la responsabilidad registrada. Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se muestre el concurrente para ejecutar Reporte de Consultas ITEMS Cobros de Servicios."
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
		return "ATC_FT_002_EjecucionXXFCReporteConsultasITEMSCobrosServicioAHSF_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

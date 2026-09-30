package EBS.cajasInteligentes.cho52071605;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Validarcargaaddenda extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validarcargaaddenda_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta1 = data.get("carpeta1");
		String ruta = data.get("archivo");
		String cantidad1 = data.get("cantidad1");
		String precio1 = data.get("precio1");
		String cantidad2 = data.get("cantidad2");
		String precio2 = data.get("precio2");
		String cantidad3 = data.get("cantidad3");
		String precio3 = data.get("precio3");
		String total = data.get("total");
		double imp1 = Integer.parseInt(cantidad1) * Double.parseDouble(precio1);
		double imp2 = Integer.parseInt(cantidad2) * Double.parseDouble(precio2);
		int imp3 = Integer.parseInt(cantidad3) * Integer.parseInt(precio3);
		double total1 = imp1 + imp2 + imp3;

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
		CargaDeArchivosTool cargarrutas = new CargaDeArchivosTool(u, testCase);
		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52071605
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-EAM10VCZ-EQUIPO-MANTO'");
		testCase.addScreenShotCurrentStep(u, "RESPONSABILIDAD MXOXXO-EAM10VCZ-EQUIPO-MANTO");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Pedidos de Trabajo -> 'XXFC - Carga de Gastos Addenda'");
		paginaInicial.clickOnCarpeta(carpeta1);
		paginaInicial.cargaaddenda();
		// PASO 7
		addStep("Dar clic en el botón de Browse... y  elegir el archivo xml de addendas y dar clic en Open'");
		testCase.addScreenShotCurrentStep(u, "Documento de Addenda");
		paginaInicial.seleccionaraddenda(cargarrutas.seleccionarArchivoSinSendkeys(ruta));
		// PASO 8
		addStep("Clic en Aplicar.");
		paginaInicial.aplicaraddenda();
		paginaInicial.home();
		// PASO 9
		addStep("Dar clic en Página Inicial -> Pedidos de Trabajo -> Pedidos de Trabajo -> Ver(x) -> Solicitudes -> Encontrar.");
		paginaInicial.regresarpedidos();
		p.runApplet();
		p.closeWindow1();
		p.vers();
		p.encontrar();
		// PASO 10
		addStep("Dar clic en 'Visualizar Output'");
		p.visualizarOutput();
		// PASO 11
		addStep("Validar se muestre 'Cantidad * Precio Unitario = Importe'.");
		p.appletMaximize();
		assertEquals(p.getTextDelNavegador("procesoadd", String.valueOf(total1)), total);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		p.c();
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validarcargaaddenda_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar archivo TXT y validar que el cálculo de importe sea correcto. Pre-requisitos: 1.- Tener acceso a la responsabilidad 'MXOXXO-EAM10VCZ-EQUIPO-MANTO'";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Tener acceso a la responsabilidad 'MXOXXO-EAM10VCZ-EQUIPO-MANTO'";
	}

	@Override
	public void beforeTest() {

	}
}
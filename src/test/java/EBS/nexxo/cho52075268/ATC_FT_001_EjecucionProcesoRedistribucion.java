package EBS.nexxo.cho52075268;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_EjecucionProcesoRedistribucion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_EjecucionProcesoRedistribucion_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");
		String ruta = data.get("archivo");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		CargaDeArchivosTool cargarrutas = new CargaDeArchivosTool(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52075268
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'");
		testCase.addScreenShotCurrentStep(u, "RESPONSABILIDAD 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickcargaarchivosr();
		testCase.addScreenShotCurrentStep(u, "Seleccionar: Carga de Archivos");
		// PASO 7
		addStep("Seleccionar el Archivo a Cargar, dar clic en 'Browse…' y seleccionar el archivo (.txt)");
		u.sendKeys(paginaInicial.cajadebusqueda(), campo);
		// PASO 8
		addStep("Seleccionar el Proceso 'Migracion de Proveedores'.");
		cargarArchivos.seleccionaraddenda(cargarrutas.seleccionarArchivoSinSendkeys(ruta));
		paginaInicial.clicklupabusqueda();
		// creamos una instancia de la pantalla primigenia
		String originalWindow = u.getDriver().getWindowHandle();
		// Check we don't have other windows open already
		assert u.getDriver().getWindowHandles().size() == 1;
		// esperamos
		u.hardWait(3);
		// encontramos las ventanas hijas
		for (String windowHandle : u.getDriver().getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				u.getDriver().switchTo().window(windowHandle);
				break;
			}
		}
		// esperamos a que termine de cargar
		WebElement frame = u.getDriver().findElement(paginaInicial.newframe());
		u.waitForLoadPage();
		u.getDriver().switchTo().frame(frame);
		cargarArchivos.clickIr();
		// PASO 8
		addStep("Seleccionar el Proceso 'Migracion de Proveedores'.");
		testCase.addScreenShotCurrentStep(u, "Seleccionar el Proceso 'Migracion de Proveedores'.");
		cargarArchivos.clickCreacionResponsabilidad();
		u.getDriver().switchTo().window(originalWindow);
		// PASO 9
		addStep("Dar clic en el botón 'Cargar'.");
		testCase.addScreenShotCurrentStep(u, "Dar clic en el botón 'Cargar'.");
		cargarArchivos.clickCargar();
		paginaInicial.home();
		u.waitForLoadPage();
		paginaInicial.verSolicitudApplets();
		p.runApplet();
		p.encontrar();
		// PASO 10
		addStep("Validar el Concurrente Finalice Normal.");
		testCase.addScreenShotCurrentStep(u, "Validar el Concurrente Finalice Normal.");
		p.visualizaRegistro();
		// PASO 11
		addStep("Dar clic en Visualizar Registro.");
		testCase.addScreenShotCurrentStep(u, "Dar clic en Visualizar Registro.");
		p.cerrarNavegador();
		p.cerrarResp("a");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_EjecucionProcesoRedistribucion_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la Migracion de Proveedores con Archivo de Carga '.txt'. Pre-requisitos: 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'."
				+ "2.- Contar con el archivo para procesar la carga en formato '.txt'.";
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
		return "1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'."
				+ "2.- Contar con el archivo para procesar la carga en formato '.txt'.";
	}

	@Override
	public void beforeTest() {

	}
}
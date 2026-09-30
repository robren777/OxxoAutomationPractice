package EBS.cartaPorte.escenario4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_Validargeneracionxmlarticulo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CP04_Validargeneracionxmlarticulo_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String ruta = "C:\\Users\\ricardo.palacios\\Downloads\\20211111090356.req";
		String responsabilidad2 = data.get("responsabilidad2");
		String carpeta2 = data.get("carpeta2");
		String campo = data.get("campo");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: esenario4
		 */

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
		addStep("Ingresar a la responsabilidad 'MXOXXO-POXXXXX-PLANTILLA DE REQUISICIONES'");
		testCase.addScreenShotCurrentStep(u, "MXOXXO-POXXXXX-PLANTILLA DE REQUISICIONES");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		testCase.addScreenShotCurrentStep(u, "Seleccionar: Carga de Archivos");
		// PASO 7
		addStep("Dar clic en el boton de 'Browse…' para buscar el archivo a cargar con la informacion de las responsabilidades.");
		cargarArchivos.seleccionaraddenda(ruta);
		cargarArchivos.seleccionarproceso(campo);
		cargarArchivos.clicklupabusqueda();
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
		WebElement frame = u.getDriver().findElement(cargarArchivos.newframe());
		u.waitForLoadPage();
		u.getDriver().switchTo().frame(frame);
		// PASO 8
		addStep("Seleccionar el archivo 'Creacion Responsabilidades.txt'.");
		// PASO 9
		addStep("Dar clic en el boton de Lupa y seleccionar 'Ir'");
		cargarArchivos.clickIr();
		// PASO 10
		addStep("Dar clic en el Proceso 'Creacion de responsabilidad'");
		cargarArchivos.clickCreacionResponsabilidad();
		u.getDriver().switchTo().window(originalWindow);
		// PASO 11
		addStep("Dar clic en el botón 'Cargar'");
		cargarArchivos.clickCargar();
		// PASO 12
		addStep("Validar el proceso dentro del menú: Ver(x) -> Solicitudes -> Encontrar.");
		cargarArchivos.home();
		u.waitForLoadPage();
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		paginaInicial.clickOnCarpeta(carpeta2);
		paginaInicial.clickVerSolicitudes();
		p.runApplet();
		p.encontrar();
		// PASO 13
		addStep("Dar clic en el boton de 'Visualizar Output'");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		p.cerrarconteclas();
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CP04_Validargeneracionxmlarticulo_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que no se genere XML para Carta Porte por dato ORIGEN 'Pre-requisitos' Contar con: "
				+ "*Excel con un llenado incorrecto en campo Articulo";
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
		return "Contar con: " + "*Excel con un llenado incorrecto en campo Articulo";
	}

	@Override
	public void beforeTest() {

	}
}
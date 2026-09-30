package EBS.cartaPorte.cho52098816;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_007_EjecutarelreportedeRelacionamientodeUUIDSconfacturassinXML extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_EjecutarelreportedeRelacionamientodeUUIDSconfacturassinXML_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES.
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");
		String archivo = data.get("archivo");
		String navegador = data.get("navegador");
        String dato = data.get("dato");
        String fechainicio = data.get("fechainicio");
        String fechafin = data.get("fechafin");
		
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
		CargaDeArchivosTool cargarrutas = new CargaDeArchivosTool(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52098816
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-AP10MON-USUARIO-FACTURA'");
		testCase.addScreenShotCurrentStep(u, "RESPONSABILIDAD 'MXOXXO-AP10MON-USUARIO-FACTURA'.");
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
		cargarrutas.selectFileCurrentProject(archivo);
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
		addStep("Seleccionar el Proceso 'Importa Facturas Traslado de Valores'.");
		testCase.addScreenShotCurrentStep(u, "Seleccionar el Proceso 'Importa Facturas Traslado de Valores'.");
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
		p.visualizarOutput();
		// PASO 11
		p.cerrarNavegador();
		p.navegador();
		p.buscarResponsabilidad(navegador);
		p.vers();
		p.runApplet();
		p.ejecutarNuevaSolicitud();
		p.buscarArchivoTab(dato);
		p.parametrosFactura(fechainicio,fechafin);
		p.ejecutarBoton();
		p.ejecutarBotonNo();
		p.encontrar();
		p.verificarEstado("normalVerde", 6000000);
		p.visualizarOutput();
		u.hardWait(2);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		p.cerrarNavegador();
		p.cerrarResp("a"); testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_EjecutarelreportedeRelacionamientodeUUIDSconfacturassinXML_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar el reporte de relacionamiento de UUIDS muestra el numero de factura actualizado"
				+ "Pre-requisitos: Haber cargado facturas sin XML.";
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
		return "Pre-requisitos: Haber cargado facturas sin XML.";
	}

	@Override
	public void beforeTest() {

	}
}

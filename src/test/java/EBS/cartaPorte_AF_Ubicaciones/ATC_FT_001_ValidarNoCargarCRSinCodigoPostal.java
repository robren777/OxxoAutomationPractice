package EBS.cartaPorte_AF_Ubicaciones;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarNoCargarCRSinCodigoPostal extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarNoCargarCRSinCodigoPostal_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String carga = data.get("campo");
		String ebsPattern = "EBS\\"+data.get("ebsPattern");
		String filepath = data.get("filepath");
		String text = data.get("text");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);
		CargaDeArchivosTool archivosTool = new CargaDeArchivosTool(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath(ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
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
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickplantillasCartaPorte();
		testCase.addScreenShotCurrentStep(u, "Seleccion de carga de archivos");
		// PASO 7
		addStep("Seleccionar 'Migracion de proveedores'  dentro del campo Proceso.");
		u.hardWait(3);
		archivosTool.selectFileCurrentProject(filepath);
		u.hardWait(3);
		u.sendKeys(paginaInicial.cajadebusqueda(), carga);
		u.hardWait(3);
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
		// PASO 8
		addStep("Cargamos el archivo .Txt, dar clic en el boton 'Browse…'.");
		cargarArchivos.clickIr();
		// PASO 9
		addStep("Dar clic en el botón 'Cargar'.");
		cargarArchivos.clickCreacionResponsabilidad();
		u.getDriver().switchTo().window(originalWindow);
		cargarArchivos.clickCargar();
		u.hardWait(3);
		// PASO 10
		addStep("Validar el concurrente finalice normal.");
		paginaInicial.home();
		u.waitForLoadPage();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickrunStockLocatorsCoFrm();
		u.hardWait(14);
		patternUtil.appletMaximize();
		u.hardWait(14);
		patternUtil.closeActiveWindow();
		u.hardWait(3);
		patternUtil.closeActiveWindow();
		u.hardWait(10);
		// PASO 11
		addStep("Dirigirse al menú Ver(x) -> Solicitudes -> Encontrar.");
		patternUtil.encontrarSolicitudes();
		patternUtil.keyAlt("e");
		u.hardWait(5);
		// PASO 12
		addStep("Dar clic en Visualizar Output.");
		patternUtil.keyAlt("l");
		u.hardWait(5);
		patternUtil.windowMaximize();
		u.hardWait(10);
		assertEquals(text, p.getText(text));
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar que no se permita carga de CR sin Codigo Postal";
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
		return "Validar que no se permita carga de CR sin Codigo Postal";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
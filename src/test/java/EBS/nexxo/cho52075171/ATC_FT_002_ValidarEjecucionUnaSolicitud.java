package EBS.nexxo.cho52075171;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_002_ValidarEjecucionUnaSolicitud extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ValidarEjecucionUnaSolicitud_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");
		String ruta = data.get("archivo");
		String responsabilidad2 = data.get("responsabilidad2");
		String carpeta2 = data.get("carpeta2");
		String navegador = data.get("navegador");
		String ns = data.get("ns");
		String cambio = data.get("cambio");
		String at2 = "10VCZ";

		String consulta1 = "select requisition_header_id , segment1  , attribute1  , attribute2  , attribute3  , attribute4  , attribute5  , attribute6  , creation_date\r\n"
				+ "from   apps.po_requisition_headers_all\r\n" + "where segment1 = '208391'";
		String consulta2 = "select requisition_header_id   , requisition_line_id   , attribute2 , attribute3  , attribute4\r\n"
				+ "from   apps.po_requisition_lines_all\r\n" + "where  requisition_header_id = 1707407";
		String consulta3 = "select requisition_header_id, segment1  , attribute1  , attribute2  , attribute3  , attribute4  , attribute5  , attribute6 , requisition_header_id2\r\n"
				+ "from   apps.xxfc_po_requisition_headers\r\n" + "where  requisition_header_id2 = 1707407";
		String consulta4 = "select requisition_header_id   , requisition_line_id   , attribute2 , attribute3  , attribute4\r\n"
				+ "from   apps.xxfc_po_requisition_lines\r\n" + "where  requisition_header_id = 3222735";

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
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52075171
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.scroll();
		testCase.addScreenShotCurrentStep(u,
				"Ingresamos en la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'");
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickcargaarchivosr();
		testCase.addScreenShotCurrentStep(u, "Seleccion de carga de archivos");
		// PASO 7
		addStep("Seleccionamos 'Migracion de Solicitudes de Inversion'  dentro del campo Proceso.");

		u.sendKeys(paginaInicial.cajadebusqueda(), campo);
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

		// PASO 8
		addStep("Cargamos el archivo .CSV, dar clic en el boton 'Browse…'.");
		cargarArchivos.clickIr();
		// PASO 9
		addStep("Dar clic en el botón 'Cargar'.");
		cargarArchivos.clickCreacionResponsabilidad();
		u.getDriver().switchTo().window(originalWindow);
		cargarArchivos.clickCargar();

		// PASO 10
		addStep("Validar el concurrente finalice normal.");
		paginaInicial.home();
		u.waitForLoadPage();
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		paginaInicial.clickOnCarpeta(carpeta2);
		paginaInicial.clickVerSolicitudes();
		p.runApplet();
		p.encontrar();
		u.hardWait(3);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.CTRL);
		p.navegador();
		p.buscarResponsabilidad(navegador);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyUp("a");
		p.abrirSolicitudesInternas();
		p.EscribirNumeroSolicitud(ns);
		// PASO 11
		addStep("Validar la Solicitud de Inversion con el cambio de Poza Rica a Veracruz.");
		p.validarCambio();
		p.compararCelda(cambio);
		// PASO 12
		addStep("Validar la Solicitud de Inversion con el cambio de Poza Rica a Veracruz.");
		System.out.println(consulta1);
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "attribute6");
		boolean validar = atributo.equals(cambio);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		System.out.println(consulta2);
		SQLResult validat2 = EBSAVANTE.executeQuery(consulta2);
		String atributo2 = validat2.getData(0, "attribute2");
		boolean validar2 = atributo2.equals(at2);
		System.out.print(atributo2);
		assertFalse(!validar2, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		System.out.println(consulta3);
		SQLResult validat3 = EBSAVANTE.executeQuery(consulta3);
		String atributo3 = validat3.getData(0, "attribute6");
		boolean validar3 = atributo3.equals(cambio);
		System.out.print(atributo3);
		assertFalse(!validar3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		System.out.println(consulta4);
		SQLResult validat4 = EBSAVANTE.executeQuery(consulta4);
		String atributo4 = validat4.getData(0, "attribute2");
		boolean validar4 = atributo4.equals(at2);
		System.out.print(atributo4);
		assertFalse(!validar4, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		p.cerrarSolicitudEntera();
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarEjecucionUnaSolicitud_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la Migracion de Solicitudes de Inversion con archivo de carga '.CSV'  con una Solicitud de Inversion. Pre-requisitos: 1.- Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'.\r\n"
				+ "2.- Contar con el archivo para procesar la carga en formato '.CSV'.";
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
		return "1.- Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'.\r\n"
				+ "2.- Contar con el archivo para procesar la carga en formato '.CSV'.";
	}

	@Override
	public void beforeTest() {

	}
}

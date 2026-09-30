package EBS.cajasInteligentes.cho52083734;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_002_ValidarEjecucionSolicitudesInversionValTij extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ValidarEjecucionSolicitudesInversionValTij_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String responsabilidad2 = data.get("responsabilidad2");
		String carga = data.get("carpeta1");
		String solicitudes = data.get("carpeta2");
		String archivo = data.get("archivo");
		String migracion = data.get("campo");
		String ns = data.get("ns");
		String cambio = data.get("cambio");
		String ebsPattern = "EBS\\"+data.get("ebsPattern");
		String filepath = data.get("filepath");
		String at2 = "10TIJ";
		String at5 = "01TIJ";

		String consulta1 = "select requisition_header_id , segment1  , attribute1  , attribute2  , attribute3  , attribute4  , attribute5  , attribute6  , creation_date\r\n"
				+ "from   apps.po_requisition_headers_all\r\n" + "where requisition_header_id = 1617753";
		String consulta2 = "select requisition_header_id   , requisition_line_id   , attribute2 , attribute3\r\n"
				+ "from   apps.po_requisition_lines_all\r\n" + "where  requisition_header_id = 1617753";
		String consulta3 = "select requisition_header_id, segment1  , attribute1  , attribute2  , attribute3  , attribute4  , attribute5  , attribute6 , requisition_header_id2\r\n"
				+ "from   apps.xxfc_po_requisition_headers\r\n" + "where  requisition_header_id2 = 1617753";
		String consulta4 = "select requisition_header_id   , requisition_line_id   , attribute2 , attribute3\r\n"
				+ "from   apps.xxfc_po_requisition_lines\r\n"
				+ "where  requisition_header_id in(select requisition_header_id from apps.xxfc_po_requisition_headers\r\n"
				+ "where requisition_header_id2 = 1617753)";

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
		GenericMethods action = new GenericMethods(u);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: 52083734
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
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickcargaarchivosr();
		testCase.addScreenShotCurrentStep(u, "Seleccion de carga de archivos");
		// PASO 7
		addStep("Seleccionar 'Migracion de Solicitudes de Inversion'  dentro del campo Proceso.");
		u.hardWait(3);
		archivosTool.selectFileCurrentProject(filepath);
		u.hardWait(3);
		u.sendKeys(paginaInicial.cajadebusqueda(), migracion);
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
		addStep("Cargamos el archivo .CSV, dar clic en el boton 'Browse…'.");
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
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		paginaInicial.clickOnCarpeta(solicitudes);
		paginaInicial.clickResumenSolicitud();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(5);
		p.EscribirNumeroSolicitudConEnter(ns);
		u.hardWait(8);
		// PASO 11
		addStep("Validar la Solicitud de Inversion con el cambio de Monterrey a Tijuana.");
		p.compararCelda(cambio);
		u.hardWait(5);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.close();
		u.hardWait(3);
		// PASO 12
		addStep("Validar la Solicitud de Inversion con el cambio de Monterrey a Tijuana.");
		System.out.println(consulta1);
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "attribute6");
		boolean validar = atributo.equals(cambio);
		String atributo6 = validat.getData(0, "attribute5");
		boolean validar6 = atributo6.equals(at5);
		System.out.print(atributo);
		System.out.print(atributo6);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		assertFalse(!validar6, "La respuesta no tiene el el resultado esperado.");
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
		String atributo3 = "";
		boolean validar3 = atributo3.equals("");
		System.out.print(atributo3);
		assertFalse(!validar3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		System.out.println(consulta4);
		SQLResult validat4 = EBSAVANTE.executeQuery(consulta4);
		String atributo4 = "";
		boolean validar4 = atributo4.equals("");
		System.out.print(atributo4);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		EBSAVANTE.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarEjecucionSolicitudesInversionValTij_test";
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
		return "Validar Se asigne el Estado Financiero(Attribute5) en tablas naturales de PO, cuando en tablas naturales de PO tenga lleno el attribute5 y no existan registros en las tablas Custom. Permita la finalización del proceso sin Errores. 1.- Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar' 2.- Contar con el archivo para procesar la carga en formato '.CSV'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
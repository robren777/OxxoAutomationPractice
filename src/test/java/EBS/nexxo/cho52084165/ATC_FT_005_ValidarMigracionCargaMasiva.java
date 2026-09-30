package EBS.nexxo.cho52084165;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSCuentasAPagar;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_005_ValidarMigracionCargaMasiva extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_ValidarMigracionCargaMasiva_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String migracion = data.get("campo");
		String reportes = data.get("carpeta1");
		String text = data.get("text");
		String ebsPattern = data.get("ebsPattern");
		String filepath = data.get("filepath");
		String segment = data.get("segment");
		String vendor = data.get("vendor");
		String update_date = data.get("update_date");
		String short_code = data.get("short_code");

		String consulta1 = "SELECT sup.segment1 , sup.vendor_id , sit.vendor_site_id , sit.vendor_site_code , sit.creation_date , sit.last_update_date , sit.org_id , uo.short_code\r\n"
				+ "FROM   apps.ap_suppliers sup , apps.ap_supplier_sites_all sit , apps.hr_operating_units uo\r\n"
				+ "WHERE  sup.vendor_id = 68060\r\n" + "AND    sup.vendor_id = sit.vendor_id\r\n"
				+ "AND    sit.org_id = uo.organization_id\r\n" + "AND    sit.org_id = 179";

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSEntryProveedores proveedores = new EBSEntryProveedores(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);
		EBSCuentasAPagar cuentasapagar = new EBSCuentasAPagar(u, testCase);
		CargaDeArchivosTool archivosTool = new CargaDeArchivosTool(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
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
		paginaInicial.clickCargaArchivosAdmonProOpCaOs();
		testCase.addScreenShotCurrentStep(u, "Seleccion de carga de archivos");
		// PASO 7
		addStep("Seleccionar 'Migracion de proveedores'  dentro del campo Proceso.");
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
		addStep("Cargamos el archivo .Txt, dar clic en el boton 'Browse…'.");
		cargarArchivos.clickIr();
		// PASO 9
		addStep("Dar clic en el botón 'Cargar'.");
		cargarArchivos.clickCreacionResponsabilidad();
		u.getDriver().switchTo().window(originalWindow);
		long start = System.currentTimeMillis();
		cargarArchivos.clickCargar();
		long fin = System.currentTimeMillis();
		double tiempototal = ((fin - start) / 1000);
		System.out.println("Execution time: " + tiempototal + "segundos");
		boolean validar = true;
		if (tiempototal >= 300) {
			validar = false;
		}
		if (!validar) {
			System.out.println("Tiempo excedido de los 5 min");
		}
		assertTrue(validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 10
		addStep("Validar el concurrente finalice normal.");
		paginaInicial.home();
		u.waitForLoadPage();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(reportes);
		paginaInicial.clickVerSolicitudesAdmonProOpCaOs();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(10);
		patternUtil.keyAlt("e");
		patternUtil.keyAlt("l");
		u.hardWait(5);
		// PASO 11
		addStep("Validar la migración del Proveedor tipo 'Empleado' correctamente");
		assertEquals(patternUtil.getTextDelNavegador("cargam", text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.hardWait(3);
		patternUtil.closeWindow();
		// PASO 12
		addStep("Validar la Solicitud de Inversion con el cambio de Chihuahua a Tijuana.");
		System.out.println(consulta1);
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "segment1");
		boolean check = atributo.equals(segment);
		System.out.print(atributo);
		assertFalse(!check, "La respuesta no tiene el resultado esperado.");
		String atributo2 = validat.getData(0, "VENDOR_SITE_CODE");
		boolean check2 = atributo2.contentEquals(vendor);
		System.out.print(atributo2);
		assertFalse(!check2, "La respuesta no tiene el resultado esperado.");
		String atributo3 = validat.getData(0, "LAST_UPDATE_DATE");
		boolean check3 = atributo3.contentEquals(update_date);
		System.out.print(atributo3);
		assertFalse(!check3, "La respuesta no tiene el resultado esperado.");
		String atributo4 = validat.getData(0, "SHORT_CODE");
		boolean check4 = atributo4.contentEquals(short_code);
		System.out.print(atributo4);
		assertFalse(!check4, "La respuesta no tiene el resultado esperado.");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarMigracionCargaMasiva_test";
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
		return "Validar el tiempo de ejecución del proceso con un volumen de 100 proveedores tipo Empleado. 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'. 2.- Contar con el archivo para procesar la carga en formato '.txt'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
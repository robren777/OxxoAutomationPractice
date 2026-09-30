package EBS.nexxo.cho52084165;

import static org.testng.Assert.assertEquals;

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
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSCuentasAPagar;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_007_ValidarMigracionEmpleadoComisionista extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_ValidarMigracionEmpleadoComisionista_test(HashMap<String, String> data) throws Exception {
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
		String carpeta2 = data.get("carpeta2");
		String numprov = data.get("numeroprov");
		String numprov2 = data.get("numeroprov2");
		String nombresucursal = data.get("nombresucursal");
		String unidadoperativa = data.get("unidadoperativa");
		String direccionenvio = data.get("direccionenvio");
		String direccionfacturacion = data.get("direccionfacturacion");
		String nombresucursal2 = data.get("nombresucursal2");
		String unidadoperativa2 = data.get("unidadoperativa2");
		String direccionenvio2 = data.get("direccionenvio2");
		String direccionfacturacion2 = data.get("direccionfacturacion2");
		String tipo = data.get("tipo");
		String tipo2 = data.get("tipo2");

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

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
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
		cargarArchivos.clickCargar();
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
		u.hardWait(13);
		patternUtil.keyAlt("e");
		patternUtil.keyAlt("l");
		u.hardWait(5);
		// PASO 11
		addStep("Validar la migración del Proveedor tipo 'Empleado y Comisionista' correctamente");
		assertEquals(patternUtil.getTextDelNavegador("rfcec", text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.hardWait(3);
		// PASO 12
		addStep("Validar la información del sitio creado");
		paginaInicial.clickOnCarpeta(carpeta2);
		paginaInicial.clickOnEntryAmonProOpCaOs();
		u.sendKeys(proveedores.numeroProveedor(), numprov);
		proveedores.clickIr();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Suppliers"));
		assertEquals(unidadoperativa, cuentasapagar.getTextOption("Unidad Operativa Suppliers"));
		assertEquals(direccionenvio, cuentasapagar.getTextField("Direccion Envio"));
		assertEquals(direccionfacturacion, cuentasapagar.getTextField("Direccion Facturacion"));
		proveedores.clickOrganization();
		assertEquals(tipo, cuentasapagar.getTextTipoProveedor("Comisionistas"));
		cuentasapagar.home();
		paginaInicial.clickOnEntryAmonProOpCaOs();
		u.sendKeys(proveedores.numeroProveedor(), numprov2);
		proveedores.clickIr();
		assertEquals(nombresucursal2, cuentasapagar.getTextNombreSucursal6("Sucursal Suppliers 2"));
		assertEquals(unidadoperativa2, cuentasapagar.getTextOption("Unidad Operativa Suppliers 2"));
		assertEquals(direccionenvio2, cuentasapagar.getTextField("Direccion Envio 2"));
		assertEquals(direccionfacturacion2, cuentasapagar.getTextField("Direccion Facturacion 2"));
		proveedores.clickOrganization();
		assertEquals(tipo2, cuentasapagar.getTextTipo());
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		patternUtil.closeWindow();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ValidarMigracionEmpleadoComisionista_test";
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
		return "Validar la correcta ejecución con proveedor tipo empleado y un proveedor comisionista finalice sin errores. 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS' 2.- Contar con el archivo para procesar la carga en formato '.txt'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}

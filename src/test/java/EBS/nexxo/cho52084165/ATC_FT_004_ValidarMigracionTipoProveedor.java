package EBS.nexxo.cho52084165;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSCuentasAPagar;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_ValidarMigracionTipoProveedor extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ValidarMigracionTipoProveedor_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String migracion = data.get("campo");
		String reportes = data.get("carpeta1");
		String carpeta2 = data.get("carpeta2");
		String numprov = data.get("numeroprov");
		String tipo = data.get("tipo");
		String unidadoperativa = data.get("unidadoperativa");
		String nombresucursal = data.get("nombresucursal");
		String numero = data.get("numero");
		String banco = data.get("banco");
		String cuentapasivo = data.get("cuentapasivo");
		String calcularimpuesto = data.get("calcularimpuesto");
		String reglaredondeo = data.get("reglaredondeo");
		String prioridadpago = data.get("prioridadpago");
		String grupopago = data.get("grupopago");
		String terminos = data.get("terminos");
		String fechaplazo = data.get("fechaplazo");
		String fechapago = data.get("fechapago");
		String text = data.get("text");
		String proposito = data.get("proposito");
		String clasificacionimpuestos = data.get("clasificacionimpuestos");
		String definirvalores = data.get("definirvalores");
		String descontarcargo = data.get("descontarcargo");
		String unidadoperativa2 = data.get("unidadoperativa2");
		String ebsPattern = data.get("ebsPattern");
		String filepath = data.get("filepath");

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
		cargarArchivos.clickCargar();
		u.hardWait(3);
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
		assertEquals(patternUtil.getTextDelNavegador("rfcprove", text), text);
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
		proveedores.clickOrganization();
		assertEquals(tipo, cuentasapagar.getTextTipoProveedor("Proveedor Mercancia"));
		proveedores.clickTaxtsdetails();
		u.scroll();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Tax Details 2"));
		assertEquals(unidadoperativa, cuentasapagar.getTextNombreSucursal6("Unidad Operativa Tax Details"));
		proveedores.clickAdressBook();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Address Book 2"));
		assertEquals(proposito, cuentasapagar.getTextProposito("Proposito Addres Book 3"));
		proveedores.clickBankingDetails();
		u.hardWait(3);
		cuentasapagar.seleccionarNivelAsignacion("Sucursal");
		u.sendKeys(cuentasapagar.lupaNivel(), unidadoperativa2);
		u.hardWait(3);
		cuentasapagar.clicklupabusqueda();
		String originalWindow2 = u.getDriver().getWindowHandle();
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
		WebElement frame2 = u.getDriver().findElement(cuentasapagar.newframe());
		u.waitForLoadPage();
		u.getDriver().switchTo().frame(frame2);
		cuentasapagar.clickSeleccionRapida();
		u.getDriver().switchTo().window(originalWindow);
		cuentasapagar.clickBotonIr();
		assertEquals(numero, cuentasapagar.getTextNumero("Numero cuenta Banking Details 2"));
		assertEquals(banco, cuentasapagar.getTextNombreBanco("Nombre Banco Banking Details 2"));
		proveedores.clickAccounting();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Accounting"));
		assertEquals(unidadoperativa, cuentasapagar.getTextOption("Unidad Operativa Accounting"));
		assertEquals(cuentapasivo, cuentasapagar.getTextCuentaPasivo3("Cuenta Pasivo Accounting 2"));
		proveedores.clickTaxtReporting();
		cuentasapagar.clickOnActualizarImpuesto("Actualizar Impuesto 3");
		assertEquals(unidadoperativa, cuentasapagar.getTextOption("Unidad Operativa Tax Details Actualizar"));
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal5());
		assertEquals(calcularimpuesto, cuentasapagar.getDefaultOptionSelect("Calcular Impuesto"));
		assertEquals(reglaredondeo, cuentasapagar.getDefaultOptionSelect("Regla de Redondeo"));
		assertEquals(definirvalores, cuentasapagar.getDefaultOptionSelect("Definir Valores de Factura"));
		assertEquals(clasificacionimpuestos, cuentasapagar.getTextField("Clasificacion de Impuestos"));
		cuentasapagar.clickBotonCancelar();
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.waitForLoadPage();
		proveedores.clickinvoicemanagement();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Invoice Management"));
		assertEquals(unidadoperativa, cuentasapagar.getTextOption("Unidad Operativa Invoice Management"));
		cuentasapagar.clickBotonPagos();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Invoice Management 2"));
		assertEquals(unidadoperativa, cuentasapagar.getTextOption("Unidad Operativa Invoice Management 2"));
		assertEquals(prioridadpago, cuentasapagar.getTextField("Prioridad Pago Invoice Management"));
		assertEquals(grupopago, cuentasapagar.getDefaultOptionSelect("Grupo de Pago 2"));
		assertEquals(descontarcargo, cuentasapagar.getDefaultOptionSelect("Descontar Cargo Bancario"));
		cuentasapagar.clickBotonTerminos();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal6("Sucursal Invoice Management 2"));
		assertEquals(unidadoperativa, cuentasapagar.getTextOption("Unidad Operativa Invoice Management 2"));
		assertEquals(terminos, cuentasapagar.getTextField("Prioridad Pago Invoice Management-Terminos"));
		assertEquals(fechaplazo, cuentasapagar.getDefaultOptionSelect("Base de Fecha Plazo 2"));
		assertEquals(fechapago, cuentasapagar.getDefaultOptionSelect("Base de Fecha de Pago 2"));
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		patternUtil.closeWindow();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarMigracionTipoProveedor_test";
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
		return "Validar Proveedor tipo Prestadores de Servicio con más de un sitio \\\"10POZ\\\"(origen) y sin existencia de un sitio\\\"10VCZ\\\"(sitio a crear) . 1.- Acceso a la responsabilidad \'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS\'. 2.- Contar con el archivo para procesar la carga en formato '.txt'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
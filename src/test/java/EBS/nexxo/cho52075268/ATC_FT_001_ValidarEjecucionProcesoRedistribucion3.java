package EBS.nexxo.cho52075268;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCuentasAPagar;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarEjecucionProcesoRedistribucion3 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarEjecucionProcesoRedistribucion3_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String numprov = data.get("numeroprov");
		String nombresucursal = data.get("nombresucursal");
		String detallesnumero = data.get("detallesnumero");
		String nombrebanco = data.get("nombrebanco");
		String numerocuentapasivo = data.get("cuentapasivo");
		String numerocuentapasivo2 = data.get("cuentapasivo2");
		String clasificacionimpuestos = data.get("clasificacionimpuestos");
		String prioridaddepago = data.get("prioridaddepago");
		String terminos = data.get("terminos");
		String unidadoperativa2 = data.get("unidadoperativa2");
		String nombresucursal2 = data.get("nombresucursal2");
		String terminos2 = data.get("terminos2");
		String detallesnumero2 = data.get("detallesnumero2");
		String nombrebanco2 = data.get("nombrebanco2");
		String prioridaddepago2 = data.get("prioridaddepago2");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSEntryProveedores proveedores = new EBSEntryProveedores(u, testCase);
		EBSCuentasAPagar cuentasapagar = new EBSCuentasAPagar(u, testCase);
		SikuliUtil sk = new SikuliUtil();

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
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickEntry();
		u.sendKeys(proveedores.numeroProveedor(), numprov);
		testCase.addScreenShotCurrentStep(u, "Buscar Numero De Proveedor");
		proveedores.clickIr();
		proveedores.clickOrganization();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Organizacion");
		proveedores.clickTaxtsdetails();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Tax Deatils.");
		u.scroll();
		assertEquals(nombresucursal2, cuentasapagar.getTextNombreSucursal1());
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursalSegundaFila());
		proveedores.clickAdressBook();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Address Book");
		cuentasapagar.clickAdministrarSucursales();
		u.waitForLoadPage();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal());
		cuentasapagar.clickBotonCancelar();
		proveedores.clickBankingDetails();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Banking Details");
		u.sendKeys(cuentasapagar.nivelasignacion(), "Sucursal");
		u.sendKeys(cuentasapagar.lupaNivel(), unidadoperativa2);
		cuentasapagar.clickLupaBakingDetails();
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
		cuentasapagar.clickUnidadOperativaTabla();
		cuentasapagar.clickSeleccionar();
		u.getDriver().switchTo().window(originalWindow);
		cuentasapagar.clickBotonIr();
		assertEquals(detallesnumero, cuentasapagar.getDetallesNumero());
		assertEquals(nombrebanco, cuentasapagar.getNombreBanco());
		assertEquals(detallesnumero2, cuentasapagar.getDetallesNumero2());
		assertEquals(nombrebanco2, cuentasapagar.getNombreBanco2());
		proveedores.clickAccounting();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Accounting");
		assertEquals(nombresucursal2, cuentasapagar.getTextNombreSucursal2());
		assertEquals(numerocuentapasivo, cuentasapagar.getTextCuentaPasivo());
		assertEquals(numerocuentapasivo2, cuentasapagar.getTextCuentaPasivo2());
		proveedores.clickTaxtReporting();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Tax Reporting");
		u.scroll();
		cuentasapagar.clickActualizarImpuesto();
		u.waitForLoadPage();
		assertEquals(nombresucursal2, cuentasapagar.getTextNombreSucursal3());
		assertEquals(clasificacionimpuestos, cuentasapagar.getTextClasificacionImpuestos());
		cuentasapagar.clickBotonCancelar();
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.waitForLoadPage();
		proveedores.clickinvoicemanagement();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Invoice Management Facturacion");
		u.scroll();
		assertEquals(nombresucursal2, cuentasapagar.getTextNombresucursalFacturacion());
		assertEquals(nombresucursal, cuentasapagar.getTextSucursalPagos());
		cuentasapagar.clickBotonPagos();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Invoice Management Pagos");
		u.scroll();
		assertEquals(nombresucursal2, cuentasapagar.getTextNombreSucursalPagos());
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursalPagosComparacion2());
		assertEquals(prioridaddepago, cuentasapagar.getTextPrioridadPago());
		assertEquals(prioridaddepago2, cuentasapagar.getTextPrioridadPago2());
		u.scroll();
		cuentasapagar.clickBotonTerminos();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Invoice Management Terminos");
		u.scroll();
		assertEquals(nombresucursal2, cuentasapagar.getTextNombreSucursalPagos());
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursalPagosComparacion2());
		assertEquals(terminos2, cuentasapagar.getTextTerminos());
		assertEquals(terminos, cuentasapagar.getTerminosCajadetexto());

		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarEjecucionProcesoRedistribucion3_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar Proveedor tipo Cobro de Servicios con un solo site de '10POZ'. Pre-requisitos: 1.- Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'."
				+ "2.- Haber ejecutado el CP004.";
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
		return "1.- Acceso a la responsabilidad 'MXOXXO-POXXXXX-Super Usuario Configurar'."
				+ "2.- Haber ejecutado el CP004.";
	}

	@Override
	public void beforeTest() {

	}
}

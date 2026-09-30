package EBS.nexxo.cho52075268;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

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

public class ATC_FT_001_ValidarEjecucionProcesoRedistribucion2 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarEjecucionProcesoRedistribucion2_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String numprov = data.get("numeroprov");
		String unidadoperativa = data.get("unidadoperativa");
		String nombresucursal = data.get("nombresucursal");
		String detallesnumero = data.get("detallesnumero");
		String nombrebanco = data.get("nombrebanco");
		String numerocuentapasivo = data.get("cuentapasivo");
		String clasificacionimpuestos = data.get("clasificacionimpuestos");
		String prioridaddepago = data.get("prioridaddepago");
		String terminos = data.get("terminos");

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
		assertEquals(unidadoperativa, cuentasapagar.getTextUnidadOperativa());
		proveedores.clickAdressBook();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Address Book");
		cuentasapagar.clickAdministrarSucursales();
		u.waitForLoadPage();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal());
		cuentasapagar.clickBotonCancelar();
		proveedores.clickBankingDetails();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Banking Details");
		u.sendKeys(cuentasapagar.nivelasignacion(), "Sucursal");
		u.sendKeys(cuentasapagar.lupaNivel(), unidadoperativa);
		cuentasapagar.clickBotonIr();
		assertEquals(detallesnumero, cuentasapagar.getDetallesNumero());
		assertEquals(nombrebanco, cuentasapagar.getNombreBanco());
		proveedores.clickAccounting();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Accounting");
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal2());
		assertEquals(numerocuentapasivo, cuentasapagar.getTextCuentaPasivo());
		proveedores.clickTaxtReporting();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Tax Reporting");
		u.scroll();
		cuentasapagar.clickActualizarImpuesto();
		u.waitForLoadPage();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursal3());
		assertEquals(clasificacionimpuestos, cuentasapagar.getTextClasificacionImpuestos());
		cuentasapagar.clickBotonCancelar();
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.waitForLoadPage();
		proveedores.clickinvoicemanagement();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Invoice Management Facturacion");
		cuentasapagar.clickBotonPagos();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Invoice Management Pagos");
		u.scroll();
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursalPagos());
		assertEquals(prioridaddepago, cuentasapagar.getTextPrioridadPago());
		u.scroll();
		cuentasapagar.clickBotonTerminos();
		testCase.addScreenShotCurrentStep(u, "Dar clic en Invoice Management Terminos");
		assertEquals(nombresucursal, cuentasapagar.getTextNombreSucursalPagos());
		assertEquals(terminos, cuentasapagar.getTextTerminos());
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarEjecucionProcesoRedistribucion2_test";
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

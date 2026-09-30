package EBS.cajasInteligentes.cho52068368;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.DetallesProveedorCreadoPom;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.TaxDetailsPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.TaxReportingPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_CONSULTAS.BuscarProveedoresPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_CONSULTAS.OrganizationPOM;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidacionPlantillaProveedoresMercanciaFrontera extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidacionPlantillaProveedoresMercanciaFrontera_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String campoNumProveedor = data.get("campo");
		String numProveedor = data.get("numeroproveedor");
		String tipo = data.get("tipo");
		String persona = data.get("persona");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		BuscarProveedoresPOM buscarProveedores = new BuscarProveedoresPOM(u, testCase);
		DetallesProveedorCreadoPom detallesProv = new DetallesProveedorCreadoPom(u, testCase);
		OrganizationPOM organization = new OrganizationPOM(u, testCase);
		TaxDetailsPOM taxDetails = new TaxDetailsPOM(u, testCase);
		TaxReportingPOM taxTeporting = new TaxReportingPOM(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Consulta Proveedores.");
		paginaInicial.clickOnCarpeta(carpeta);

		// PASO 7
		addStep("Ingresar en el apartado Numero de proveedor el RFC(PRUE901212ZZ1) a consultar y dar clic en 'Ir'");
		buscarProveedores.enterValue(numProveedor, campoNumProveedor);
		buscarProveedores.clickBtnIr();

		// PASO 8
		addStep("Validar informacion en el apartado 'Organization'");
		detallesProv.clickOrganization();
		assertEquals(organization.getTipoText(), tipo);
		assertEquals(organization.getPersonaText(), persona);
		testCase.addScreenShotCurrentStep(u, "ValidateTipoYPersona");

		// PASO 9
		addStep("Ingresar en el apartado 'Tax Detail' y validar que al dar clic en 'Actualizar Impuesto de Transaccion' se muestre correcta la informacion.");
		detallesProv.clickTaxDetails();
		taxDetails.clickActualizarImpuestoTrans();
		testCase.addScreenShotCurrentStep(u, "ValidarInformacion1");

		// PASO 10
		addStep("Ingresar en el apartado 'Tax and Reporting' y validar que al dar clic en 'Actualizar Impuesto de Transaccion' se muestre correcta la informacion.");
		taxDetails.clickCancelar();
		detallesProv.clickTaxReporting();
		taxTeporting.actualizarImpuestoTrans();
		testCase.addScreenShotCurrentStep(u, "ValidarInformacion2");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidacionPlantillaProveedoresMercanciaFrontera_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validacion de plantilla proveedores Tipo de proveedor 'Mercancia(F)-Frontera'."
				+ "Prerequisitos: 1.- Tener asignada la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Haber creado proveedor desde PANP(Matriz de Casos de Prueba CHO 52068368 PANP P1.xlsx).";
	}

	@Override
	public String setTestDesigner() {
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Tener asignada la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Haber creado proveedor desde PANP(Matriz de Casos de Prueba CHO 52068368 PANP P1.xlsx).";
	}

	@Override
	public void beforeTest() {

	}
}

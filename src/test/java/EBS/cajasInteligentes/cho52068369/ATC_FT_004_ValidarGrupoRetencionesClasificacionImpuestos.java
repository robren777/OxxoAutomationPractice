package EBS.cajasInteligentes.cho52068369;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.tools.MXOXXO_APXXXXX_ADMON_PROVEEDORES_tools.CrearProveedoresTool;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.ActualizarOrganizacionPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.AddressBookPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.CrearAdressBookPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.CrearProveedor;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.DetallesProveedorCreadoPom;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.TaxDetailsPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.TaxReportingPOM;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_ValidarGrupoRetencionesClasificacionImpuestos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarGrupoRetencionesClasificacionImpuestos_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String nombreOrganizacion = data.get("nombreOrganizacion");
		String persona = data.get("persona");
		String numProveedor = data.get("numProveedor");
		String tipoOrganizacion = data.get("tipoOrganizacion");
		String calleNumero = data.get("calleNumero");
		String nombreDomicilio = data.get("nombreDomicilio");
		String labelGRIP = data.get("labelGRIP");
		String clasificacionImpuestos = data.get("clasificacionImpuestos");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSEntryProveedores entryProveedores = new EBSEntryProveedores(u, testCase);
		CrearProveedoresTool crearProvTool = new CrearProveedoresTool(u, testCase);
		CrearProveedor crearProveedor = new CrearProveedor(u, testCase);
		DetallesProveedorCreadoPom detallesProv = new DetallesProveedorCreadoPom(u, testCase);
		ActualizarOrganizacionPOM actualizarOrg = new ActualizarOrganizacionPOM(u, testCase);
		AddressBookPOM addressBook = new AddressBookPOM(u, testCase);
		CrearAdressBookPOM crearAddressBook = new CrearAdressBookPOM(u, testCase);
		TaxDetailsPOM taxDetails = new TaxDetailsPOM(u, testCase);
		TaxReportingPOM taxRep = new TaxReportingPOM(u, testCase);

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
		addStep("Dar clic en el boton de 'Conectarse'.");
		inicio.login();

		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Seleccionar Proveedores: Entry.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickOnEntryAP();

		// PASO 7
		addStep("Seleccionar Opcion: Crear Proveedor.");
		entryProveedores.clickCrearProveedor();

		// PASO 8
		addStep("Llenar los datos obligatorios marcados con (*) y validar el campo 'Persona' se encuentre en los requeridos.");
		crearProvTool.llenarCamposObligatorios(nombreOrganizacion, persona, numProveedor);
		crearProveedor.clickAplicar();

		// PASO 9
		addStep("Ingresar en 'Organization'");
		detallesProv.clickOrganization();

		// PASO 10
		addStep("Capturar el campo Tipo y dar clic en Grabar");
		actualizarOrg.sendTipo(tipoOrganizacion);
		actualizarOrg.clickGrabar();

		// PASO 11
		addStep("Ingresar en 'Address Book'");
		detallesProv.clickAddressBook();

		// PASO 12

		addStep("Dar clic en Crear y  capturar los datos para el Domicilio");
		addressBook.clickCrear();
		crearAddressBook.sendCalleNumero(calleNumero);
		crearAddressBook.sendNombreDomicilio(nombreDomicilio);
		crearAddressBook.clickContinuar();
		crearAddressBook.seleccionarSucursal();
		crearAddressBook.clickAplicar();

		// PASO 13

		addStep("Ingresar en 'Tax Details' y activar checkbox 'Autorizar Retencion de Impuesto'");
		detallesProv.clickTaxDetails();
		taxDetails.markRetencionImpuesto();
		Assert.assertEquals(taxDetails.getTextGrupoRetImpPago(), labelGRIP);
		taxDetails.clickGrabar();

		// PASO 14
		addStep("Ingresar en 'Tax and Reporting', validar la correcta asignacion del Codigo de Impuestos");
		detallesProv.clickTaxReporting();
		taxRep.actualizarImpuestoTrans();
		Assert.assertEquals(taxRep.getTextClasImpuestos(), clasificacionImpuestos);
		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES'.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la asignacion en automatico de 'Grupo de Retenciones de Impuestos de Pago' y "
				+ "'Clasificacion de Impuestos' - Tasa Interior para todos proveedores Tipo Persona Fisica con Tasa Interior."
				+ "Prerequisitos: Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarGrupoRetencionesClasificacionImpuestos_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

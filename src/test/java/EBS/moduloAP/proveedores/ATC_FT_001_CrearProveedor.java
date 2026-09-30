package EBS.moduloAP.proveedores;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSCuentasAPagar;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.AccountingPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.ActualizarOrganizacionPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.ContactDirectory;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.CrearAdressBookPOM;
import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.CrearProveedor;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_CrearProveedor extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_CrearProveedor_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String carpeta1 = data.get("carpeta1");
		String tipoProveedor = data.get("tipoProveedor");
		String nombreOrganizacion = data.get("nombreOrganizacion");
		String numeroProveedor = data.get("numeroProveedor");
		String persona = data.get("persona");
		String mensajeConfirmacion = data.get("mensajeConfirmacion");
		String calleNumero = data.get("calleNumero");
		String nombreDomicilio = data.get("nombreDomicilio");
		String compra = data.get("opcion1");
		String pago = data.get("pago");
		String contactDirectory = data.get("contactDirectory");
		String primerNombre = data.get("primerNombre");
		String apellido = data.get("apellido");
		String cargo = data.get("cargo");
		String email = data.get("email");
		String anticipo = data.get("anticipo");
		String terminos = data.get("terminos");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSEntryProveedores proveedores = new EBSEntryProveedores(u, testCase);
		CrearProveedor crearProveedor = new CrearProveedor(u, testCase);
		EBSCuentasAPagar cuentasApagar = new EBSCuentasAPagar(u, testCase);
		ActualizarOrganizacionPOM organizacion = new ActualizarOrganizacionPOM(u, testCase);
		CrearAdressBookPOM crearDomicilio = new CrearAdressBookPOM(u, testCase);
		ContactDirectory contact = new ContactDirectory(u, testCase);
		AccountingPOM accounting = new AccountingPOM(u, testCase);

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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CREAR PROVEEDOR
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-AP15IXM-Admon Proveedores'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(carpeta1);
		paginaInicial.clickformularioEntryAP15AdmonProveedores();
		u.waitForLoadPage();
		// PASO 6
		addStep("Entramos a la opción de CREAR PROVEEDOR");
		proveedores.clickCrearProveedor();
		u.waitForLoadPage();
		// PASO 7
		addStep("Se da Alta un Proveedor tipo PRESTADOR DE SERVICIOS");
		crearProveedor.seleccionarTipoProveedor(tipoProveedor);
		u.hardWait(3);
		// PASO 8
		addStep("Ingresamos el Nombre de la Organización , número de proveedor y ID de Contribuyente");
		crearProveedor.sendNombreOrganizacion(nombreOrganizacion);
		u.hardWait(1);
		crearProveedor.sendNumProveedore(numeroProveedor);
		u.hardWait(1);
		u.sendKeys(crearProveedor.cajaDeBusqueda(), persona);
		u.hardWait(3);
		crearProveedor.clickLupaBusqueda();
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
		WebElement frame = u.getDriver().findElement(crearProveedor.newFrame());
		u.waitForLoadPage();
		u.getDriver().switchTo().frame(frame);
		crearProveedor.clickIr();
		crearProveedor.clickSeleccionRapida();
		u.getDriver().switchTo().window(originalWindow);
		u.hardWait(3);
		crearProveedor.sendIDContribuyente(numeroProveedor);
		// PASO 9
		addStep("Se da click en APLICAR");
		crearProveedor.clickAplicar();
		// PASO 10
		addStep("Entramos a la opción de ORGANIZACIÓN y seleccionamos el Tipo de Proveedor en este caso PRESTADORES DE SERVICIO y llenamos la información adicional");
		u.hardWait(3);
		proveedores.clickOrganization();
		organizacion.sendTipo(tipoProveedor);
		organizacion.clickGrabar();
		u.hardWait(3);
		assertEquals(mensajeConfirmacion, organizacion.getTextConfirmacion());
		u.hardWait(3);
		// PASO 11
		addStep("Entramos a la opción de LIBRO DE DIRECCIONES y damos click en CREAR");
		proveedores.clickAdressBook();
		crearDomicilio.clickCrear();
		crearDomicilio.sendCalleNumero(calleNumero);
		crearDomicilio.sendNombreDomicilio(nombreDomicilio);
		crearDomicilio.seleccionarOpcionCheckbox(compra);
		crearDomicilio.seleccionarOpcionCheckbox(pago);
		u.hardWait(3);
		crearDomicilio.clickContinuar();
		crearDomicilio.clickSeleccionarOpcion();
		crearDomicilio.clickAplicar();
		u.hardWait(3);
		assertEquals(mensajeConfirmacion, organizacion.getTextConfirmacion());
		u.hardWait(3);
		// PASO 12
		addStep("Entramos a la opción de DIRECTORIO DE CONTACTOS para crear el contacto de Proveedor, damos un click en CREAR");
		proveedores.clickOnOpcionesSuppliers(contactDirectory);
		contact.clickCrear();
		u.waitForLoadPage();
		contact.sendInformacion("primer nombre", primerNombre);
		contact.sendInformacion("apellido", apellido);
		contact.sendInformacion("cargo", cargo);
		contact.sendInformacion("correo electronico", email);
		contact.clickAplicar();
		u.hardWait(3);
		contact.clickAplicar();
		u.hardWait(3);
		assertEquals(mensajeConfirmacion, organizacion.getTextConfirmacion());
		u.hardWait(3);
		// PASO 13
		addStep("Entramos a la opción de Contabilidad para crear la cuenta de Pasivo, Anticipos, Documentos por Pagar y Juegos de Distribución");
		proveedores.clickAccounting();
		accounting.clickAnticipo();
		assertEquals(anticipo, accounting.getTextField("Anticipo"));
		proveedores.clickTaxtReporting();
		u.waitForLoadPage();
		cuentasApagar.clickOnActualizarImpuesto("Actualizar Impuesto 3");
		u.hardWait(3);
		cuentasApagar.clickBotonCancelar();
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.waitForLoadPage();
		// PASO 14
		addStep("Entramos a DETALLES DE IMPUESTOS, retención de impuestos");
		proveedores.clickTaxtsdetails();
		cuentasApagar.clickGeneral("autorizar retención");
		u.hardWait(3);
		u.scroll();
		u.hardWait(3);
		cuentasApagar.clickGeneral("autorizar retención 2");
		u.hardWait(2);
		cuentasApagar.clickGeneral("botón grabar");
		u.hardWait(3);
		assertEquals(mensajeConfirmacion, organizacion.getTextConfirmacion());
		u.hardWait(3);
		// PASO 15
		addStep("Administración de Facturas configuramos: Prioridad de Pago, Grupo de Pago, Términos de Pago, etc.");
		proveedores.clickinvoicemanagement();
		cuentasApagar.clickBotonPagos();
		u.hardWait(3);
		cuentasApagar.clickBotonTerminos();
		u.hardWait(3);
		cuentasApagar.sendInformacion("ingresar términos", terminos);
		cuentasApagar.clickGeneral("botón grabar invoice m");
		u.hardWait(3);
		assertEquals(mensajeConfirmacion, organizacion.getTextConfirmacion());
		u.hardWait(3);
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return null;
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
		return "Validar la Migración de Proveedor tipo Empleado con Archivo de Carga '.txt' . 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS' 2.- Contar con el archivo para procesar la carga en formato '.txt'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
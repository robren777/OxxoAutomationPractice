package EBS.util.pageObjectModel;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSCuentasAPagar {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSCuentasAPagar(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By tabla = By.xpath("//table//table//table//table[@class='xcg']");
	By unidadoperativa = By.xpath("//span[@id='N74:OuName1:0']");
	By administrarsucursales = By.xpath("//a[@id='N32:mngSites:0']//img[@title='Administrar Sucursal']");
	By nombresucursal = By.xpath("//table//table//table//table[@class='xcg']//input[@id='N20:siteName1:0']");
	By botoncancelar = By.xpath("//tbody//button[@title = 'Cancelar']");
	By nivelasignacion = By.xpath("//select[@id='usesList']");
	By botonir = By.xpath("//button[@id='goButton']");
	By lupanivel = By.xpath("//input[@id='sitesList']");
	By lupabakingdetails = By.xpath("//img[@title='Buscar: sitesList']");
	By clickunidadoperativatabla = By.xpath(
			"/html[1]/body[1]/div[3]/span[1]/div[1]/div[2]/form[1]/table[2]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[3]/span[1]/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[3]/td[1]");
	By clickSeleccionar = By.xpath("//button[normalize-space()='Seleccionar']");
	By ns1 = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[4]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/span[2]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]");
	By ns2 = By.xpath("//span[@id='N74:SiteName1:1']");
	By detallesnumero2 = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[2]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/span[1]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[2]/td[2]");
	By nombrebanco2 = By.xpath("//span[@id='PayeeBankAccountAssignmentsRN:BankName1:1']");
	By nombresucursalcomparacion2 = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[4]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]/span[1]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[2]/td[1]");
	By prioridaddepago2 = By.xpath("//div[1]/div[3]/table/tbody/tr[2]/td[4]/input");
	By nombresucursalfacturacion = By.xpath("//span[@id='InvoiceAdvTblRN:siteName:0']");
	By nombresucursalfacturacion2 = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[4]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]/span[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[3]/td[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]");
	By terminoscajadetexto = By.xpath("//input[@id='N45:siteTerms:1']");
	By sucursalpagos = By.xpath("//*[@id='InvoiceAdvTblRN:Content']/tbody/tr[2]/td[1]");
	By detallesnumero = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[2]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/span[1]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[2]");
	By nombrebanco = By.xpath("//div/span[1]/div/div/div[1]/div[3]/table/tbody/tr/td[5]/span");
	By nombresucursal2 = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[2]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]/span[1]/div[1]/div[1]/div[1]/div[2]/table[1]/tbody[1]/tr[2]/td[1]");
	By cuentapasivo = By.xpath("//input[@id='N28:LiabilityFlex_COMBINATION:0']");
	By actualizarimpuesto = By.xpath("//a[@id='N48:updTranTax:0']//img[@title='Actualizar Impuesto de Transacción']");
	By cuentapasivo2 = By.xpath("//input[@id='N28:LiabilityFlex_COMBINATION:1']");
	By nombresucursal3 = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/table[2]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[5]/span[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[3]/td[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]");
	By clasificacionimpuestos = By.xpath("//input[@id='N3:VatCode:0']");
	By pagos = By.xpath("//label[normalize-space()='Pago']");
	By terminospagos = By.xpath("//label[normalize-space()='Términos']");
	By nombresucursalpagos = By.xpath(
			"/html[1]/body[1]/div[3]/form[1]/span[2]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/div[2]/table[4]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]/span[1]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]");
	By prioridaddepago = By.xpath("//input[@id='N36:PayPriority1:0']");
	By terminosterminos = By.xpath("//input[@id='N45:siteTerms:0']");
	By empleado = By.xpath("//table/tbody/tr[6]/td[3]/span/span[text() = 'Empleado']");
	By nombresucursal4 = By.xpath("//tr/td/div/div/span[2]/div/div/div[1]/div[3]/table/tbody/tr[2]/td[1]/span");
	By unidadoperativa2 = By.xpath(
			"//tr/td/div/div/span[2]/div/div/div[1]/div[3]/table/tbody/tr[2]/td[2]/span[text() = 'PL_VERACRUZ_CCO_UO']");
	By actualizarimpuesto2 = By.xpath("//table/tbody/tr[2]/td[4]/a/img[@title='Actualizar Impuesto de Transacción']");
	By tipo = By.xpath("//table/tbody/tr[6]/td[3]/span/span[text() = 'Empleado']");
	By unidadoperativa3 = By.xpath("//tbody/tr[3]/td[2]/span[text() = 'PL_VERACRUZ_CCO_UO']");
	By unidadoperativa4 = By.xpath("//tbody/tr/td[1]//span[text() = 'PL_VERACRUZ_CCO_UO']");
	By nombresucursal5 = By.xpath("//tbody/tr/td[1]//span[text() = '10VCZ']");
	By calcularimpuesto = By.xpath("//select[@title='Calcular Impuesto']");
	By reglaredondeo = By.xpath("//table/tbody/tr/td[6]/select");
	By unidadoperativa5 = By.xpath("//table/tbody/tr[2]/td[2]/span[text() = 'PL_VERACRUZ_CCO_UO']");
	By prioridadpago = By.xpath("//table/tbody/tr[2]/td[4]/input");
	By grupopago = By.xpath("//table/tbody/tr[2]/td[5]/select");
	By fechaplazo = By.xpath("//table/tbody/tr[2]/td[4]/select");
	By fechapago = By.xpath("//table/tbody/tr[2]/td[5]/select");
	By numero = By.xpath("//span[1]/div/div/div[1]/div[3]/table/tbody/tr/td[2]/a");
	By terminos1 = By.xpath(" //table/tbody/tr[2]/td[3]/div/span/input");
	By cobrosservicio = By.xpath("//tr[6]/td[3]/div/span/input");
	By actualizarimpuesto3 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[4]/a/img");
	By actualizarimpuesto4 = By.xpath("//div[1]/div[3]/table/tbody/tr[2]/td[4]/a/img");
	By terminos2 = By.xpath("//div[1]/div[3]/table/tbody/tr[2]/td[3]/div/span/input");
	By pagoaddress = By.xpath("//input[@id='N11:Pay:1']");
	By terminos = By.xpath(" //table/tbody/tr[2]/td[3]/div/span/input");
	By nombresucursal6 = By.xpath("//td//span[@id='N11:siteName1:1']");
	By unidadoperativa6 = By.xpath("//td//span[@id='N11:OUName1:1']");
	By administrarsucursales2 = By.xpath("//a[@id='N32:mngSites:1']//img[@title='Administrar Sucursal']");
	By botonidentificacion = By.xpath("//table/tbody/tr/td[5]/a/label");
	By sucursalalterna = By.xpath("//input[@id='N38:siteAltName:0']");
	By numeroproveedor = By.xpath("//tbody/tr/td[4]/table/tbody/tr[2]/td[3]/span");
	By nombresucursal7 = By.xpath("//td//input[@id='N38:siteName3:0']");
	By unidadoperativa7 = By.xpath("//div[1]/div[3]/table/tbody/tr/td[2]/span");
	By prestadoresservicio = By.xpath("//tr[6]/td[3]/div/span/input");
	By cajabusquedasucursal = By.xpath("//input[@id='posSiteName']");
	By botonir2 = By.xpath("//td//button[@id='btnGo']");
	By nombresucursal8 = By.xpath("//span[text()='10VCZ-PS']");
	By nombresucursal9 = By.xpath("//span[text()='10VCZ']");
	By unidadoperativa8 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[2]/span");
	By unidadoperativa9 = By.xpath("//div[1]/div[3]/table/tbody/tr[10]/td[2]/span");
	By gruporetencion = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[5]/div/span/input");
	By gruporetencion2 = By.xpath("//div[1]/div[3]/table/tbody/tr[10]/td[5]/div/span/input");
	By cajabusquedasucursal2 = By.xpath("//input[@id='stName']");
	By botonir3 = By.xpath("//td//button[@id='GoBtn']");
	By proposito = By.xpath("//div[1]/div[3]/table/tbody/tr[1]/td[6]/span");
	By proposito2 = By.xpath("//div[1]/div[3]/table/tbody/tr[6]/td[6]/span");
	By nombresucursal10 = By.xpath("//div[1]/div[3]/table/tbody/tr[1]/td[2]/span");
	By nombresucursal11 = By.xpath("//span[text() = '10VCZ-PS']");
	By proveedormercancia = By.xpath("//tbody/tr[6]/td[3]/div/span/input");
	By nombresucursal12 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[1]/span");
	By unidadoperativa10 = By.xpath("//div[3]/table/tbody/tr[3]/td[2]/span");
	By administrarsucursales3 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[9]/a/img");
	By nombresucursal13 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[2]/span");
	By proposito3 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[6]/span");
	By numero2 = By.xpath("//div[1]/div[3]/table/tbody/tr/td[2]/a");
	By nombrebanco3 = By.xpath("//div[1]/div[3]/table/tbody/tr/td[5]/span");
	By nombresucursal14 = By.xpath("//div[1]/div[2]/table/tbody/tr[4]/td[1]/span");
	By unidadoperativa11 = By.xpath("//div[1]/div[2]/table/tbody/tr[4]/td[2]/span");
	By cuentapasivo3 = By.xpath("//div[1]/div[2]/table/tbody/tr[4]/td[3]/input");
	By definirvalores = By.xpath("//div[1]/table/tbody/tr/td[7]/select");
	By clasificacionimpuestos2 = By.xpath("//div[1]/table/tbody/tr/td[8]/div/span/input");
	By nombresucursal15 = By.xpath("//div[1]/table/tbody/tr[3]/td[1]/span");
	By unidadoperativa12 = By.xpath("//tr[3]/td/div[1]/table/tbody/tr[3]/td[2]/span");
	By descontarcargo = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[6]/select");
	By grupopago2 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[5]/select");
	By lupa = By.xpath("//td[2]/table/tbody/tr/td[3]/div/span/a/img");
	By seleccionrapida = By.xpath("//div[1]/table/tbody/tr[3]/td[2]/a/img");
	By newFrame = By.xpath("//frame[@title='Content']");
	By unidadoperativa13 = By.xpath("//table/tbody/tr[3]/td/div[1]/table/tbody/tr/td[1]/span/span");
	By nombresucursal16 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[1]/span");
	By unidadoperativa14 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[2]/span");
	By fechaplazo2 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[4]/select");
	By fechapago2 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[5]/select");
	By prioridadpago2 = By.xpath("//div[1]/div[3]/table/tbody/tr[3]/td[4]/input");
	By prioridadpago3 = By.xpath("//div[3]/table/tbody/tr[3]/td[3]/div/span/input");
	By nombresucursal17 = By.xpath("//span[text()='10POZ']");
	By cuentapasivo4 = By.xpath("//div[1]/div[2]/table/tbody/tr[2]/td[3]/input");
	By tipo3 = By.xpath("//td[2]/table/tbody/tr[6]/td[3]/div/span/input");
	By home = By.xpath("//img[@title='Home']");
	By nombresucursal18 = By.xpath("//tr[2]//div[3]//tr[2]/td[1]//span[text()='10VCZ']");
	By unidadoperativa15 = By.xpath("//div[3]//tr[2]/td[1]//span[text()='PL_VERACRUZ_CCO_UO']");
	By direccionenvio = By.xpath("//span[1]/div/div/div[1]/div[3]/table/tbody/tr[2]/td[3]/div/span/input");
	By direccionfacturacion = By.xpath("//span[1]/div/div/div[1]/div[3]/table/tbody/tr[2]/td[4]/div/span/input");
	By nombresucursal19 = By.xpath("//div[1]/div[3]/table/tbody/tr[2]/td[1]/span[text()='Oficina']");
	By unidadoperativa16 = By.xpath("//tr[2]/td[2]/span[text() = 'PL_VERACRUZ_CCO_UO']");
	By direccionenvio2 = By.xpath("//div[1]/div[3]/table/tbody/tr[2]/td[3]/div/span/input");
	By direccionfacturacion2 = By.xpath("//div[1]/div[3]/table/tbody/tr[2]/td[4]/div/span/input");
	By crearProveedor = By.xpath("//tbody//button[@title = 'Crear Proveedor']");
	By autorizarRetencion = By.xpath("//input[@id= 'allwTaxWthld']");
	By autorizarRetencion2 = By.xpath("//input[@id = 'N74:AllowTaxHold:0']");
	By btnGrabar = By.xpath("//button[@id='saveBtn_uixr']");
	By btnGrabarInvoiceM = By.xpath("//button[@id='saveBtn']");
	By ingresarTerminos = By.xpath("//div[3]/table/tbody/tr/td[3]/div/span/input[@title = 'Términos']");
	
	
	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return unidadoperativa
	 */
	public String getTextUnidadOperativa() {

		u.highLight(unidadoperativa);
		return u.getText(unidadoperativa);
	}

	/**
	 * envio parametro de nombre sucursal
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursal1() {

		u.highLight(ns1);
		return u.getText(ns1);
	}

	/**
	 * envio parametro de nombre sucursal segunda fila
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursalSegundaFila() {

		u.highLight(ns2);
		return u.getText(ns2);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursal() {

		u.highLight(nombresucursal);
		return u.getDriver().findElement(nombresucursal).getAttribute("Value");
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextCuentaPasivo() {

		u.highLight(cuentapasivo);
		return u.getDriver().findElement(cuentapasivo).getAttribute("Value");
	}

	public By cuentap(String Cuentap) {
		switch (Cuentap) {
		case "Cuenta Pasivo Accounting":
			return cuentapasivo2;
		case "Cuenta Pasivo Accounting 2":
			return cuentapasivo3;
		case "Cuenta Pasivo Accounting 3":
			return cuentapasivo4;
		}
		return null;
	}

	public String getTextCuentaPasivo3(String cuentap) {

		By obj = cuentap(cuentap);
		u.highLight(obj);
		return u.getDriver().findElement(obj).getAttribute("Value");
	}

	public String getTextCuentaPasivo2() {

		u.highLight(cuentapasivo2);
		return u.getDriver().findElement(cuentapasivo2).getAttribute("Value");
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursal2() {

		u.highLight(nombresucursal2);
		return u.getText(nombresucursal2);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextSucursalPagos() {

		u.highLight(sucursalpagos);
		return u.getText(sucursalpagos);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombresucursalFacturacion() {

		u.highLight(nombresucursalfacturacion);
		return u.getText(nombresucursalfacturacion);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombresucursalFacturacion2() {

		u.highLight(nombresucursalfacturacion2);
		return u.getText(nombresucursalfacturacion2);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursal3() {

		u.highLight(nombresucursal3);
		return u.getText(nombresucursal3);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursalPagos() {

		u.highLight(nombresucursalpagos);
		return u.getText(nombresucursalpagos);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextNombreSucursalPagosComparacion2() {

		u.highLight(nombresucursalcomparacion2);
		return u.getText(nombresucursalcomparacion2);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextClasificacionImpuestos() {

		u.highLight(clasificacionimpuestos);
		return u.getDriver().findElement(clasificacionimpuestos).getAttribute("Value");

	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextPrioridadPago() {

		u.highLight(prioridaddepago);
		return u.getDriver().findElement(prioridaddepago).getAttribute("Value");
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextPrioridadPago2() {

		u.highLight(prioridaddepago2);
		return u.getDriver().findElement(prioridaddepago2).getAttribute("Value");
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextTerminos() {

		u.highLight(terminosterminos);
		return u.getDriver().findElement(terminosterminos).getAttribute("Value");
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTerminosCajadetexto() {

		u.highLight(terminoscajadetexto);
		return u.getDriver().findElement(terminoscajadetexto).getAttribute("Value");
	}

	/**
	 * Hace un remarcado y click en administrar sucursales
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickAdministrarSucursales() {

		u.highLight(administrarsucursales);
		u.click(administrarsucursales);
	}

	/**
	 * Hace un remarcado y click en actualizar impuesto
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickActualizarImpuesto() {

		u.highLight(actualizarimpuesto);
		u.click(actualizarimpuesto);
	}

	/**
	 * Hace un remarcado y click ir segunda ventana de unidad operativa
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickSeleccionar() {

		u.highLight(clickSeleccionar);
		u.click(clickSeleccionar);
	}

	/**
	 * Hace un remarcado y click en la lupa de baking details
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickUnidadOperativaTabla() {

		u.highLight(clickunidadoperativatabla);
		u.click(clickunidadoperativatabla);
	}

	/**
	 * Hace un remarcado y click en la seleccion de la tabla de unidades operativas
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickLupaBakingDetails() {

		u.highLight(lupabakingdetails);
		u.click(lupabakingdetails);
	}

	/**
	 * Hace un remarcado y click en boton ir
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickBotonIr() {

		u.highLight(botonir);
		u.click(botonir);
	}

	/**
	 * Hace un remarcado y click en boton pagos
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickBotonPagos() {

		u.highLight(pagos);
		u.click(pagos);
	}

	/**
	 * Hace un remarcado y click en boton terminos
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickBotonTerminos() {

		u.highLight(terminospagos);
		u.click(terminospagos);
	}

	/**
	 * Hace un remarcado y click en boton cancelar
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickBotonCancelar() {

		u.highLight(botoncancelar);
		u.click(botoncancelar);
	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public By nivelasignacion() {

		return nivelasignacion;
	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public By lupaNivel() {

		return lupanivel;
	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return detallesnumero
	 */
	public String getDetallesNumero() {

		u.highLight(detallesnumero);
		return u.getText(detallesnumero);
	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return detallesnumero2
	 */
	public String getDetallesNumero2() {

		u.highLight(detallesnumero2);
		return u.getText(detallesnumero2);
	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getNombreBanco() {

		u.highLight(nombrebanco);
		return u.getText(nombrebanco);

	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getNombreBanco2() {

		u.highLight(nombrebanco2);
		return u.getText(nombrebanco2);

	}

	/**
	 * Método que selecciona el campo del Banco a validar
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Banco
	 * @return
	 */
	public By banco(String Banco) {
		switch (Banco) {
		case "Nombre Banco Banking Details":
			return nombrebanco2;
		case "Nombre Banco Banking Details 2":
			return nombrebanco3;
		}
		return null;
	}

	/**
	 * Método que toma el texto del campo de Banco para la información que se esta
	 * validando
	 * 
	 * @author JessicaR@hexaware.com
	 * @param banco
	 * @return
	 */
	public String getTextNombreBanco(String banco) {

		By obj = banco(banco);
		u.highLight(obj);
		return u.getText(obj);

	}

	/**
	 * Hace un remarcado al tipo y toma la información que se muestra en la pantalla
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public String getTextTipo() {

		u.highLight(empleado);
		return u.getText(empleado);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextNombreSucursal4() {

		u.highLight(nombresucursal4);
		return u.getText(nombresucursal4);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextUnidadOperativa2() {

		u.highLight(unidadoperativa2);
		return u.getDriver().findElement(unidadoperativa2).getAttribute("Value");
	}

	/**
	 * Hace un remarcado y click en actualizar impuesto
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickActualizarImpuesto2() {

		u.highLight(actualizarimpuesto2);
		u.click(actualizarimpuesto2);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextUnidadOperativa3() {

		u.highLight(unidadoperativa3);
		return u.getText(unidadoperativa3);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextUnidadOperativa4() {

		u.highLight(unidadoperativa4);
		return u.getDriver().findElement(unidadoperativa4).getAttribute("Value");

	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextNombreSucursal5() {

		u.highLight(nombresucursal5);
		return u.getText(nombresucursal5);
	}

	/**
	 * Metodo utilizado para checar cual opcion se va a comparar
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Option
	 * @return Locator de la opcion elegida
	 */
	public By option(String Option) {
		switch (Option) {
		case "Calcular Impuesto":
			return calcularimpuesto;
		case "Regla de Redondeo":
			return reglaredondeo;
		case "Grupo de Pago":
			return grupopago;
		case "Base de Fecha de Plazo":
			return fechaplazo;
		case "Base de Fecha de Pago":
			return fechapago;
		case "Definir Valores de Factura":
			return definirvalores;
		case "Descontar Cargo Bancario":
			return descontarcargo;
		case "Grupo de Pago 2":
			return grupopago2;
		case "Base de Fecha Plazo 2":
			return fechaplazo2;
		case "Base de Fecha de Pago 2":
			return fechapago2;
		}
		return null;
	}

	/**
	 * Selecciona el texto de la primera opción que se muestra en la lista
	 * desplegable
	 * 
	 * @author JessicaR@hexaware.com
	 * @param option
	 * @return defaultItem
	 */
	public String getDefaultOptionSelect(String option) {

		By obj = option(option);
		Select select = new Select(u.getDriver().findElement(obj));
		WebElement options = select.getFirstSelectedOption();
		String defaultItem = options.getText();
		u.highLight(obj);
		System.out.println(defaultItem);
		return defaultItem;
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextUnidadOperativa5() {

		u.highLight(unidadoperativa5);
		return u.getText(unidadoperativa5);
	}

	/**
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextPrioridadPago3() {

		u.highLight(prioridadpago);
		return u.getDriver().findElement(prioridadpago).getAttribute("Value");
	}

	/**
	 * 
	 * envio parametro de caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return By
	 */
	public String getTextTerminos2() {

		u.highLight(terminos);
		return u.getDriver().findElement(terminos).getAttribute("Value");
	}

	public By numerocuenta(String NumeroCuenta) {
		switch (NumeroCuenta) {
		case "Numero cuenta Banking Details":
			return numero;
		case "Numero cuenta Banking Details 2":
			return numero2;
		}
		return null;
	}

	/**
	 * Método para obtener el texto del numero de cuenta del banco se la información
	 * que se esta validando
	 * 
	 * @author JessicaR@hexaware.com
	 * @param numerocuenta
	 * @return
	 */
	public String getTextNumero(String numerocuenta) {

		By obj = numerocuenta(numerocuenta);
		u.highLight(obj);
		return u.getText(obj);
	}

	/**
	 * selecciona la opción del dropdown
	 * 
	 * @author JessicaR@hexaware.com
	 * @param x valor seleccionado del dropdown
	 */
	public void seleccionarNivelAsignacion(String x) {
		try {
			Select nivel = new Select(u.getDriver().findElement(nivelasignacion));
			nivel.selectByVisibleText(x);
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}

	/**
	 * Método que selecciona el texto del campo Tipo
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Tipo
	 * @return
	 */

	public By tipo(String Tipo) {
		switch (Tipo) {
		case "Cobros de Servicio":
			return cobrosservicio;
		case "Prestadores de Servicio":
			return prestadoresservicio;
		case "Proveedor Mercancia":
			return proveedormercancia;
		case "Comisionistas":
			return tipo3;
		}
		return null;
	}

	/**
	 * Selecciona el texto que viene en el tipo de proveedor que se esta validando
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */

	public String getTextTipoProveedor(String tipo) {

		By obj = tipo(tipo);
		u.highLight(obj);
		return u.getDriver().findElement(obj).getAttribute("Value");
	}

	/**
	 * Metodo utilizado para seleccionar el proveedor al que se le va a actualizar
	 * la información
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Imagen
	 * @return
	 */
	public By imagen(String Imagen) {
		switch (Imagen) {
		case "Actualizar Impuesto 3":
			return actualizarimpuesto3;
		case "Actualizar Impuesto 4":
			return actualizarimpuesto4;
		}
		return null;
	}

	/**
	 * Da un click en la imagen del lápiz para actualizar la información del
	 * impuesto
	 * 
	 * @author JessicaR@hexaware.com
	 * @param imagen
	 */
	public void clickOnActualizarImpuesto(String imagen) {
		try {
			By obj = imagen(imagen);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
	}

	/**
	 * Método para obtener el texto del campo términos
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Terminos
	 * @return Un WebElement dependiendo del texto que recibe
	 */
	public By terminos(String Terminos) {
		switch (Terminos) {
		case "Términos Cobros de Servicio":
			return terminos2;
		case "Términos Empleado":
			return terminos1;
		}
		return null;
	}

	/**
	 * Método que toma el texto del campo términos
	 * 
	 * @author JessicaR@hexaware.com
	 * @param terminos
	 * @return Value
	 */
	public String getTextTerminos2(String terminos) {

		By obj = terminos(terminos);
		u.highLight(obj);
		return u.getDriver().findElement(obj).getAttribute("Value");
	}

	/**
	 * Método para obtener el texto del campo Sucursal
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Sucursal
	 * @return Sucursal locator
	 */
	public By sucursal(String Sucursal) {
		switch (Sucursal) {
		case "Sucursal Tax Details":
			return nombresucursal4;
		case "Sucursal Address Book":
			return nombresucursal6;
		case "Sucursal Actualizar Tax Details":
			return nombresucursal8;
		case "Sucursal Actualizar Tax Details 2":
			return nombresucursal9;
		case "Sucursal Actualizar Address Book":
			return nombresucursal10;
		case "Sucursal Actualizar Address Book 2":
			return nombresucursal11;
		case "Sucursal Tax Details 2":
			return nombresucursal12;
		case "Sucursal Address Book 2":
			return nombresucursal13;
		case "Sucursal Accounting":
			return nombresucursal14;
		case "Sucursal Invoice Management":
			return nombresucursal15;
		case "Unidad Operativa Tax Details":
			return unidadoperativa10;
		case "Sucursal Invoice Management 2":
			return nombresucursal16;
		case "Sucursal Accountig 2":
			return nombresucursal17;
		case "Sucursal Suppliers":
			return nombresucursal18;
		case "Sucursal Suppliers 2":
			return nombresucursal19;
		}
		return null;
	}

	/**
	 * Método para obtener el texto de la Sucursal
	 * 
	 * @author JessicaR@hexaware.com
	 * @param sucursal
	 * @return
	 */
	public String getTextNombreSucursal6(String sucursal) {

		By obj = sucursal(sucursal);
		u.highLight(obj);
		return u.getText(obj);
	}

	/**
	 * Método para validar si el checkbox esta seleccionado
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public boolean getCheckBoxValue() {

		u.highLight(pagoaddress);
		return u.getDriver().findElement(pagoaddress).isSelected();
	}

	/**
	 * Método para seleccionar la Unidad Operativa
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Unidad
	 * @return
	 */
	public By unidad(String Unidad) {
		switch (Unidad) {
		case "Unidad Operativa Address Book":
			return unidadoperativa6;
		case "Unidad Operativa Identificacion":
			return unidadoperativa7;
		case "Unidad Operativa Tax Details":
			return unidadoperativa8;
		case "Unidad Operativa Tax Details 2":
			return unidadoperativa9;
		case "Unidad Operativa Tax Details 3":
			return unidadoperativa10;
		}
		return null;
	}

	/**
	 * Método para obtener el texto de una Unidad Operativa
	 * 
	 * @author JessicaR@hexaware.com
	 * @param unidad
	 * @return
	 */
	public String getTextUnidadOperativa6(String unidad) {

		By obj = unidad(unidad);
		u.highLight(obj);
		return u.getDriver().findElement(obj).getAttribute("Value");
	}

	/**
	 * Método para dar click en la imagen de Administrar Sucursales
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Formulario
	 * @return
	 */
	public By formulario(String Formulario) {
		switch (Formulario) {
		case "Administrar Sucursales Address Book":
			return administrarsucursales2;
		case "Administrar Sucursales Addres Book 2":
			return administrarsucursales3;
		}
		return null;
	}

	/**
	 * Método para dar click en la opción de Administrar Sucursales
	 * 
	 * @author JessicaR@hexaware.com
	 * @param formulario
	 */
	public void clickOnAdministrarSucursales(String formulario) {
		try {
			By obj = formulario(formulario);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
	}

	/**
	 * Método que le da click al boton de identificación
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickBotonIdentificacion() {

		u.highLight(botonidentificacion);
		u.click(botonidentificacion);
	}

	/**
	 * Método para obtener el texto de la Sucursal Alterna
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public String getTextSucursalAlterna() {

		u.highLight(sucursalalterna);
		return u.getDriver().findElement(sucursalalterna).getAttribute("Value");
	}

	/**
	 * Método para obtener el texto del número de Proveedor
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public String getTextNumeroProveedor() {

		u.highLight(numeroproveedor);
		return u.getText(numeroproveedor);
	}

	/**
	 * Método para obtener el nombre de la sucursal
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public String getTextNombreSucursal7() {

		u.highLight(nombresucursal7);
		return u.getDriver().findElement(nombresucursal7).getAttribute("Value");
	}

	/**
	 * Método para localizar la caja de busqueda solicitada en la prueba
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Busqueda
	 * @return
	 */
	public By busqueda(String Busqueda) {
		switch (Busqueda) {
		case "Caja de busqueda Sucursal Tax Details":
			return cajabusquedasucursal;
		case "Caja de busqueda Sucursal Address Book":
			return cajabusquedasucursal2;
		}
		return null;
	}

	/**
	 * Método para el envio de parametro en la caja de texto
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public By cajaDeBusquedaSucursal(String busqueda) {

		By obj = busqueda(busqueda);
		u.highLight(obj);
		return obj;
	}

	/**
	 * Metodo para seleccionar el boton ir dependiendo de la pestaña en la que se
	 * encuentre
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Boton
	 * @return
	 */
	public By boton(String Boton) {
		switch (Boton) {
		case "Boton ir Tax Details":
			return botonir2;
		case "Boton ir Addres Book":
			return botonir3;
		}
		return null;
	}

	/**
	 * Método para darle click al boton de ir después de realizar una busqueda
	 * específica
	 * 
	 * @author JessicaR@hexaware.com
	 * @param boton
	 */
	public void clickOnBotonIr(String boton) {
		try {
			By obj = boton(boton);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
	}

	/**
	 * Método que localiza el campo de texto que queremos comparar del Grupo de
	 * Retencion
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Retencion
	 * @return Retenciones
	 */
	public By empty(String Retencion) {
		switch (Retencion) {
		case "Grupo de Retencion Tax Details":
			return gruporetencion;
		case "Grupo de Retencion Tax Details 2":
			return gruporetencion2;
		}
		return null;
	}

	/**
	 * Método que toma el texto que viene en el campo de Grupo de Retención
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public Boolean getEmptyFields(String empty) {

		By obj = empty(empty);
		u.highLight(obj);
		if (u.getText(obj).isEmpty()) {
			System.out.println("El campo se muestra vacío");
			return true;
		} else
			System.out.println("El campo se muestra con información");
		return false;

	}

	/**
	 * Método para seleccionar el texto del Propósito que se desea comparar
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Proposito
	 * @return
	 */
	public By proposito(String Proposito) {
		switch (Proposito) {
		case "Proposito Address Book":
			return proposito;
		case "Proposito Address Book 2":
			return proposito2;
		case "Proposito Addres Book 3":
			return proposito3;
		}
		return null;
	}

	/**
	 * Método para obtener el texto del campo de Propósito
	 * 
	 * @author JessicaR@hexaware.com
	 * @param proposito
	 * @return
	 */
	public String getTextProposito(String proposito) {

		By obj = proposito(proposito);
		u.highLight(obj);
		return u.getText(obj);
	}

	/**
	 * Método para seleccionar la celda específica a validar
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Field
	 * @return
	 */
	public By field(String Field) {
		switch (Field) {
		case "Clasificacion de Impuestos":
			return clasificacionimpuestos2;
		case "Prioridad Pago Invoice Management":
			return prioridadpago2;
		case "Prioridad Pago Invoice Management-Terminos":
			return prioridadpago3;
		case "Direccion Envio":
			return direccionenvio;
		case "Direccion Facturacion":
			return direccionfacturacion;
		case "Direccion Envio 2":
			return direccionenvio2;
		case "Direccion Facturacion 2":
			return direccionfacturacion2;
		}
		return null;
	}

	/**
	 * Método que obtiene el texto de las celdas a comparar
	 * 
	 * @author JessicaR@hexaware.com
	 * @param field
	 * @return
	 */
	public String getTextField(String field) {

		By obj = field(field);
		u.highLight(obj);
		return u.getDriver().findElement(obj).getAttribute("Value");

	}

	public By textopt(String Textopt) {
		switch (Textopt) {
		case "Unidad Operativa Accounting":
			return unidadoperativa11;
		case "Unidad Operativa Tax Details Actualizar":
			return unidadoperativa13;
		case "Unidad Operativa Invoice Management":
			return unidadoperativa12;
		case "Unidad Operativa Invoice Management 2":
			return unidadoperativa14;
		case "Unidad Operativa Suppliers":
			return unidadoperativa15;
		case "Unidad Operativa Suppliers 2":
			return unidadoperativa16;
		}
		return null;
	}

	public String getTextOption(String textopt) {

		By obj = textopt(textopt);
		u.highLight(obj);
		return u.getText(obj);
	}

	/**
	 * Método que hace un remarcado y da click en la imagen de lupa
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clicklupabusqueda() {
		u.highLight(lupa);
		u.click(lupa);
	}

	/**
	 * Método que hace un remarcado y selecciona la impagen de selección rápida
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickSeleccionRapida() {
		u.highLight(seleccionrapida);
		u.click(seleccionrapida);
	}

	/**
	 * Método que envía los parámetros del frame
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public By newframe() {
		return newFrame;
	}

	/**
	 * Método que te regresa a la página principal
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void home() {
		u.highLight(home);
		u.click(home);
	}
	
	public By boton2(String Boton2) {
		switch (Boton2.toLowerCase(Locale.ROOT)) {
		case "autorizar retención":
			return autorizarRetencion;
		case "autorizar retención 2":
			return autorizarRetencion2;
		case "botón grabar":
			return btnGrabar;
		case "botón grabar invoice m":
			return btnGrabarInvoiceM;
		}

		return null;
	}
	
	public void clickGeneral(String boton2) {
        By obj = boton2(boton2);
        u.highLight(obj);
        u.getDriver().findElement(obj).click();
    }
	
	public By campo(String Campo) {
		switch (Campo.toLowerCase(Locale.ROOT)) {
		case "ingresar términos":
			return ingresarTerminos;
		}

		return null;
	}
	
	
	public void sendInformacion(String campo, String informacion) {
        By obj = campo(campo);
        u.highLight(obj);
        u.clear(obj);
        u.hardWait(3);
        u.getDriver().findElement(obj).sendKeys(informacion);
    }
}

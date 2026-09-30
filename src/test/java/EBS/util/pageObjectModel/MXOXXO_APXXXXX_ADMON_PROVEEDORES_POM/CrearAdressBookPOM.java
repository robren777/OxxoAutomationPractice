package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CrearAdressBookPOM {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public CrearAdressBookPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By pais = By.xpath("//input[@id='HzFlexCountry']");
	By calleNum = By.xpath("//table[@id='AddressStyleRN']//tr[@id='HzAddressStyleFlex1__xc_']//td/input");
	By nombreDom = By.xpath("//table[@id='AddressStyleRN']//tr[@id='hzPartySiteName__xc_']//td/input");
	By continuar = By.xpath("//button[@id='nextBtn']");
	By btnAplicar = By.xpath("//button[@id='applyBtn']");
	By seleccionarSucursal = By.xpath("//tbody/tr[@class='xfn']/td/span/input[@title='Seleccionar']");
	By btnCrear = By.xpath("//button[@id='Create']");
	By checkCompra = By.xpath("//input[@title='Compra']");
	By checkPago = By.xpath("//input[@title='Pago']");
	By seleccionarOpcion = By.xpath("//input[@name='N10:selected:0']");

	public void sendCalleNumero(String calleNumero) {
		u.highLight(calleNum);
		u.sendKeys(calleNum, calleNumero);
	}

	public void sendNombreDomicilio(String nombreDomicilio) {
		u.highLight(nombreDom);
		u.sendKeys(nombreDom, nombreDomicilio);
	}

	public void clickContinuar() {
		u.highLight(continuar);
		u.click(continuar);
		u.waitForLoadPage();
	}

	public void seleccionarSucursal() {
		u.highLight(seleccionarSucursal);
		u.click(seleccionarSucursal);
	}

	public void clickAplicar() {
		u.highLight(btnAplicar);
		u.click(btnAplicar);
		u.waitForLoadPage();

	}

	/**
	 * Da click y hace un remarcado en el botón de Crear y espera a que la página se
	 * termine de cargar
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickCrear() {
		u.highLight(btnCrear);
		u.click(btnCrear);
		u.waitForLoadPage();
	}

	/**
	 * Selecciona la opción que elegimos del checkbox
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Proposito
	 * @return
	 */
	public By proposito(String Proposito) {
		switch (Proposito) {
		case "Compra":
			return checkCompra;
		case "Pago":
			return checkPago;
		}
		return null;
	}

	/**
	 * Método que selecciona la opción del checkbox que queremos
	 * 
	 * @author JessicaR@hexaware.com
	 * @param proposito (es la opción que seleccionamos)
	 */
	public void seleccionarOpcionCheckbox(String proposito) {
		try {
			By obj = proposito(proposito);
			u.highLight(obj);
			u.getDriver().findElement(obj).click();

		} catch (Exception e) {
			logger.error("Error opción no encontrada " + e);
			System.out.println("Error opción no encontrada " + e);
		}
	}

	/**
	 * Da cick en el checkbox que se muestra en la pantalla
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickSeleccionarOpcion() {
		u.highLight(seleccionarOpcion);
		u.click(seleccionarOpcion);
	}

}

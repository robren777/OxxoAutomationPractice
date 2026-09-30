package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSEntryProveedores {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSEntryProveedores(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By btnCrearProveedor = By.xpath("//button[@id='supCreatBtn']");
	By btnNombreOrganizacion = By.xpath("//input[@id='organization_name']");
	By numeroProveedor = By.xpath("//input[@id='SearchSuppNum']");
	By ir = By.xpath("//button[@id='GoButton']");
	By organization = By.xpath("//a[@id='POS_HT_SP_B_ORG']");
	By taxtdetails = By.xpath("//a[@id='POS_HT_SP_B_TAX_DT']");
	By addressbook = By.xpath("//a[@id='POS_HT_SP_B_ADDR_BK']");
	By bankingdetails = By.xpath("//a[@id='POS_SBD_BUYER_MAIN']");
	By accounting = By.xpath("//a[@id='POS_HT_SP_B_ACCT']");
	By taxtreporting = By.xpath("//a[@id='POS_HT_SP_B_TAX_RPT']");
	By invoicemanagement = By.xpath("//a[@id='POS_HT_SP_B_INV_MNG']");
	By proveedor = By.xpath("//table/tbody/tr[2]/td[8]/a/img");
	By contactDirectory = By.xpath("//a[@id='POS_HT_SP_B_CONT_DIR']");

	/**
	 * Da click en el boton Crear Proveedor
	 * 
	 * @author edwin.ramirez@neoris.com
	 */
	public void clickCrearProveedor() {
		u.click(btnCrearProveedor);
		u.waitForLoadPage();
	}

	/**
	 * envio parametro numero de proveedor
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public By numeroProveedor() {
		return numeroProveedor;
	}

	/**
	 * click en boton ir
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickIr() {
		u.click(ir);
		u.waitForLoadPage();
	}

	/**
	 * click en boton organization
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickOrganization() {
		u.click(organization);
		u.waitForLoadPage();
	}

	/**
	 * click en boton taxtdetails
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickTaxtsdetails() {
		u.click(taxtdetails);
		u.waitForLoadPage();
	}

	/**
	 * click en boton addressbook
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickAdressBook() {
		u.click(addressbook);
		u.waitForLoadPage();
	}

	/**
	 * click en boton bankingdetails
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickBankingDetails() {
		u.click(bankingdetails);
		u.waitForLoadPage();
	}

	/**
	 * click en boton accounting
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickAccounting() {
		u.click(accounting);
		u.waitForLoadPage();
	}

	/**
	 * click en boton taxtreporting
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickTaxtReporting() {
		u.click(taxtreporting);
		u.waitForLoadPage();
	}

	/**
	 * click en boton invoicemanagement
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickinvoicemanagement() {
		u.click(invoicemanagement);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y click en actualizar el proveedor
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickActualizarProveedor() {

		u.highLight(proveedor);
		u.click(proveedor);
		u.waitForLoadPage();
	}

	/**
	 * Método utiliado para seleccionar el Supplier de la página de proveedores
	 * 
	 * @author JessicaR@hexaware.com
	 * @param Supplier
	 * @return
	 */
	public By supplier(String Supplier) {
		switch (Supplier) {
		case "Address Book":
			return addressbook;
		case "Contact Directory":
			return contactDirectory;
		}
		return null;
	}

	/**
	 * Método utilizado para dar click en cualquiera de las opciones que se muestran
	 * en la paágina de proveedores en las opciones de Suppliers
	 * 
	 * @author JessicaR@hexaware.com
	 * @param supplier
	 */
	public void clickOnOpcionesSuppliers(String supplier) {
		try {
			By obj = supplier(supplier);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Supplier no encontrado " + e);
			System.out.println("Error Supplier no encontrado " + e);
		}
	}
}

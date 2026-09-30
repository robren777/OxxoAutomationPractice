package EBS.util.pageObjectModel.MXOXXO_APXXXXX_CONSULTAS;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class BuscarProveedoresPOM {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JesusH@hexaware.com
	 */
	public BuscarProveedoresPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By nomProveedore = By.xpath("//input[@id='SearchSuppName']");
	By numProveedor = By.xpath("//input[@id='SearchSuppNum']");
	By idContribuyente = By.xpath("SearchTaxPayerId");
	By numRegistroImpositivo = By.xpath("//input[@id='SearchTaxRegNum']");
	By numDUNS = By.xpath("//input[@id='SearchDUNS']");
	By numEmpleadp = By.xpath("//input[@id='SearchEmpNum']");
	By btnIr = By.xpath("//button[@id='GoButton']");
	By btnLimpiar = By.xpath("//button[@id='ClearButton']");

	public By campoALLenar(String campo) {
		switch (campo) {
		case "nombre de proveedor":
			return nomProveedore;
		case "numero de proveedor":
			return numProveedor;
		}
		return null;
	}

	public void enterValue(String value, String campo) {
		u.waitForLoadPage();
		try {
			By obj = campoALLenar(campo);
			u.highLight(obj);
			u.sendKeys(obj, value);
		} catch (Exception e) {
			logger.error("Error " + e);
			System.out.println("Error " + e);
		}
	}

	public void clickBtnIr() {
		u.highLight(btnIr);
		u.click(btnIr);
	}

}

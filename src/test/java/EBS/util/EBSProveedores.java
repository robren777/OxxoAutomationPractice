package EBS.util;

import org.openqa.selenium.By;

import utils.selenium.SeleniumUtil;

public class EBSProveedores {

	SeleniumUtil u;

	public EBSProveedores(SeleniumUtil u) {
		super();
		this.u = u;
	}

	By btnCrearProveedor = By.xpath("//button[@id='supCreatBtn']");

	public void clickCrearProveedor() {
		u.waitForLoadPage();
		u.click(btnCrearProveedor);
	}
}

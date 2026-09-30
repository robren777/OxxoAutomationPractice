
/**
 *	Clase para automatizar el apartado Surtido
 *
 * @author ricardo.palacios
 */

package RDM.util;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Surtido extends BaseRDM {

	public Surtido(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);

	}

	/**
	 * En el menu da click en Inventory Management del menu principal
	 * 
	 * @author ricardo.palacios
	 */
	public void clickInventoryManagement() {
		try {
			sk.screen.wait(imgPattern.get("inventoryManagement")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en XXFC Confirm Paper Pick to Pallet
	 * 
	 * @author ricardo.palacios
	 */
	public void clickXxfcConfirmPaper() {
		try {
			sk.screen.wait(imgPattern.get("XxfcConfirmPaperPickPallet")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en confirmAll
	 * 
	 * @author ricardo.palacios
	 */
	public void clickConfirmAll() {
		try {
			sk.screen.wait(imgPattern.get("confirmAll")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en save surtido
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSaveSurtido() {
		try {
			sk.screen.wait(imgPattern.get("saveSurtido")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en palomita
	 * 
	 * @author ricardo.palacios
	 */
	public void clickCheckAprovacionSurtido() {
		try {
			sk.screen.wait(imgPattern.get("checkAprovacionSurtido")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en Pick Confirmation
	 * 
	 * @author ricardo.palacios
	 */
	public void clickPickConfirmation() {
		try {
			sk.screen.wait(imgPattern.get("pickConfirmation")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
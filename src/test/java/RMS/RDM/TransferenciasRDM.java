/**
 *Clase para automatizar el modulo de merma y devoluciones de RDM
 *
 * @author guerrero.vargas
 */

package RMS.RDM;

import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class TransferenciasRDM extends BaseRDM {

	HashMap<String, String> data;
	public TransferenciasRDM(SeleniumUtil u, TestCase testCase, HashMap<String, String> data) {
		super(u, testCase, data);
		this.data = data;
	}
	
	
	/**
	 * Navega al modulo XXFC-Mod. Programaci�n p Ruta.
	 * 
	 * @author 1000068915
	 */
	public void navXXFCModProgramacionPRuta() {
		try {
			wait(2);
			sk.getScreen().hover(imgPattern.get("distributionPlanning"));
			sk.getScreen().click();
			wait(1);
			
			sk.getScreen().hover(imgPattern.get("xxfcProgramacionyBalanceo"));
			sk.getScreen().click();
			wait(1);
			
			sk.getScreen().hover(imgPattern.get("xxfcModProgramacionpRuta"));
			screenshot();
			sk.getScreen().click();
			wait(1);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Navega al modulo XXFC-Mod. Programaci�n p Ruta.
	 * 
	 * @author 1000068915
	 */
	public void fillXXFCPRogramacionSurtidoRoute(String date) {
		try {
			String distro = App.getClipboard();
			
			
			wait(2);
			sk.getScreen().wait(imgPattern.get("f_Inicial"), 15).right(50).doubleClick();
			sk.getScreen().wait(imgPattern.get("f_Inicial"), 15).right(50).doubleClick();
			wait(1);

			sk.getScreen().type(date);

			wait(2);
			sk.getScreen().wait(imgPattern.get("distro"), 15).right(30).click();
			wait(1);
			
			sk.getScreen().type(distro);
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("btn_execute"), 15).click();
			sk.getScreen().click();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * En el menu da click en Inventory Management
	 * 
	 * @author guerrero.vargas
	 */
	public void clickInventoryManagement() {
		try {
			sk.getScreen().hover(imgPattern.get("inventoryManagement"));
			wait(1);
			screenshot();
			sk.getScreen().click();
			
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * En el menu da click en Inventory Management > XxfcModuloConteoCiclico
	 * 
	 * @author 1000068915
	 */
	public void clickXxfcModuloConteoCiclico() {
		try {
			sk.getScreen().hover(imgPattern.get("XxfcModuloConteoCiclico"));
			wait(1);
			screenshot();
			sk.getScreen().click();
			
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * En el menu da click en Inventory Management > XxfcModuloConteoCiclico > XxfcArticulosCriticos
	 * 
	 * @author 1000068915
	 */
	public void clickXxfcArticulosCriticos() {
		try {
			sk.getScreen().hover(imgPattern.get("XxfcArticulosCriticos"));
			wait(1);
			screenshot();
			sk.getScreen().click();
			
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Se ingresa informacion en los filtros para comprobar que funcionen y que se genera
	 * informacion en la tabla
	 * 
	 * @author 1000068915
	 */
	public void validarFiltrosTablaArticulosCriticos() {
		wait(10);
		try {
			sk.getScreen().type(data.get("Fecha"));
			wait(1);
			sk.getScreen().type(Key.TAB);
			wait(1);
			
			sk.getScreen().type(data.get("Articulo"));
			wait(1);
			sk.getScreen().type(Key.TAB);
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("executeQuery")).click();
			wait(5);
			screenshot();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
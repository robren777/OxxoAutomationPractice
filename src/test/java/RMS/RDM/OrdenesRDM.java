/**
 *Clase para automatizar el modulo de merma y devoluciones de RDM
 *
 * @author guerrero.vargas
 */

package RMS.RDM;

import java.util.HashMap;

import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class OrdenesRDM extends BaseRDM {

	HashMap<String, String> data;
	public OrdenesRDM(SeleniumUtil u, TestCase testCase, HashMap<String, String> data) {
		super(u, testCase, data);
		this.data = data;
	}
	
	
	/**
	 * Navega al modulo Appoiment Schedule
	 * 
	 * @author 1000068915
	 */
	public void navAppointmentSchedule() {
		try {
			wait(2);
			sk.getScreen().hover(imgPattern.get("appointments"));
			sk.getScreen().click();
			wait(1);
			
			sk.getScreen().hover(imgPattern.get("appointmentsSchedule"));
			sk.getScreen().click();
			wait(3);
			screenshot();
			wait(2);	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Navega al modulo Receiving Labels
	 * 
	 * @author 1000068915
	 */
	public void navReceivingLabels() {
		try {
			wait(2);
			sk.getScreen().hover(imgPattern.get("receiving_allocation"));
			sk.getScreen().click();
			wait(1);
			
			sk.getScreen().hover(imgPattern.get("receiving_labels"));
			sk.getScreen().click();
			wait(3);
			screenshot();
			wait(2);	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Crea una cita
	 * 
	 * @author 1000068915
	 */
	public void crearCita() {
		try {
			wait(2);
			sk.getScreen().wait(imgPattern.get("btn_createRecord"), 15).click();
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("carrier"), 15).right(30).click();
			wait(1);
			sk.getScreen().write(data.get("carrier"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("trailer_id"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("door"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("type"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("duration"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("ASN"));
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("total_hours"), 15).right(30).click();
			wait(1);
			sk.getScreen().write(data.get("total_hours"));
			wait(1);
			
			if (data.get("NSC") == "Y") {
				sk.getScreen().find(imgPattern.get("nsc")).right(10).click();
			}
			wait(1);
			
			if (data.get("QA") == "Y") {
				sk.getScreen().find(imgPattern.get("qa")).right(10).click();
			}
			
			wait(1);
			if (data.get("VA") == "Y") {
				sk.getScreen().find(imgPattern.get("va")).right(10).click();
			}
			
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("save"), 15).click();
			
			if (sk.getScreen().exists(imgPattern.get("same_door"), 10) != null) {
				sk.getScreen().type(Key.ENTER);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Vuelve al menu principal
	 * 
	 * @author 1000068915
	 */
	public void backToMenu() {
		try {
			wait(2);
			sk.getScreen().wait(imgPattern.get("exit"), 15).click();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	
	/**
	 * Busca la Orden de compra
	 * 
	 * @author 1000068915
	 */
	public void findOC() {
		try {
			wait(2);
			sk.getScreen().wait(imgPattern.get("ubicacion"), 15).right(10).click();
			wait(1);
			sk.getScreen().write(data.get("ubicacion"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("execute"), 15).click();
			wait(5);
			
			sk.getScreen().wait(imgPattern.get("confirm_alert"), 15);
			wait(1);
			
			sk.getScreen().type(Key.ENTER);
			wait(2);
			
			sk.getScreen().wait(imgPattern.get("print_screen"), 15);
			wait(1);
			
			
			
			
			
			
			
			
			
			
			sk.getScreen().write(data.get("trailer_id"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("door"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("type"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("duration"));
			wait(1);
			
			sk.getScreen().write(Key.TAB);
			wait(1);
			sk.getScreen().write(data.get("ASN"));
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("execute"), 15).right(30).click();
			wait(1);
			sk.getScreen().write(data.get("total_hours"));
			wait(1);
			
			if (data.get("NSC") == "Y") {
				sk.getScreen().find(imgPattern.get("nsc")).right(10).click();
			}
			wait(1);
			
			if (data.get("QA") == "Y") {
				sk.getScreen().find(imgPattern.get("qa")).right(10).click();
			}
			
			wait(1);
			if (data.get("VA") == "Y") {
				sk.getScreen().find(imgPattern.get("va")).right(10).click();
			}
			
			wait(1);
			
			sk.getScreen().wait(imgPattern.get("save"), 15).click();
			
			if (sk.getScreen().exists(imgPattern.get("same_door"), 10) != null) {
				sk.getScreen().type(Key.ENTER);
			}
			
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
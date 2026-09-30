/**
 *	Clase para automatizar el apartado de Recibo a proveedor
 *
 * @author ricardo.palacios
 */

package RDM.util;

import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ReciboProveedor extends BaseRDM {

	public ReciboProveedor(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
	}

	/**
	 * En el menu da click en receivingAllocation
	 * 
	 * @author ricardo.palacios
	 */
	public void clickReceivingAllocation() {

		try {

			sk.screen.wait(imgPattern.get("receivingAllocation")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en receivingLabels
	 * 
	 * @author ricardo.palacios
	 */
	public void clickReceivingLabels() {

		try {

			sk.screen.wait(imgPattern.get("receivingLabels")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * click en el boton CreateRecord
	 * 
	 * @author ricardo.palacios
	 */
	public void clickCreateRecord() {

		try {

			sk.screen.wait(imgPattern.get("createRecord")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Receiving > Reports > Receiving Receipts
	 * 
	 * @author ricardo.palacios
	 */
	public void clickReceivingReceipts() {

		try {

			sk.screen.wait(imgPattern.get("receiving")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("reports")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("receivingReceipt")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Receiving > Reports > Receiving Register
	 * 
	 * @author ricardo.palacios
	 */
	public void clickReceivingRegister() {

		try {

			sk.screen.wait(imgPattern.get("receiving")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("reports")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("receivingRegister")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Support Functions > Processing/ Returns Setup > Reprint/ Null Labels
	 * 
	 * @author ricardo.palacios
	 */
	public void clickNullLabels() {

		try {

			sk.screen.wait(imgPattern.get("supportFunctions")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("processingReturnSetup")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("reprintNullLabels")).click();
			wait(3);
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en receiving
	 * 
	 * @author ricardo.palacios
	 */
	public void clickReceiving() {

		try {

			sk.screen.wait(imgPattern.get("receiving")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Appointments > Appointment Schedule
	 * 
	 * @author ricardo.palacios
	 */
	public void clickAppointmentSchedule() {

		try {

			sk.screen.wait(imgPattern.get("appointments")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("appointmentSchedule")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en boton null
	 * 
	 * @author ricardo.palacios
	 */
	public void clickNull() {

		try {

			sk.screen.wait(imgPattern.get("null")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en boton imprimir cita
	 * 
	 * @author ricardo.palacios
	 */
	public void clickImprimirCita() {

		try {

			sk.screen.wait(imgPattern.get("imprimirCita")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en save destination
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSave() {

		try {

			sk.screen.wait(imgPattern.get("savereciboProveedor")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en save
	 * 
	 * @author ricardo.palacios
	 */
	public void clickS2() {

		try {

			sk.screen.wait(imgPattern.get("s2")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
    
	/**
	 * Cambiar fecha de el calendario con tab antes
	 * 
	 * @param fecha
	 * @author ricardo.palacios
	 */
	public void cambiarDateConTab(String fecha) {

		this.nTAB(1);
		wait(3);
		sk.screen.type(Key.DELETE);
		wait(3);
		sk.screen.type(fecha);
	}
	
	/**
	 * Cambiar ubicacion
	 * 
	 * @param fecha
	 * @author ricardo.palacios
	 */
	public void cambiarUbicacionConTab(String ubicacion) {

		wait(3);
		sk.screen.type(ubicacion);
		wait(3);
		
	
	}
	
	/**
	 * Cambiar fecha de el calendario
	 * 
	 * @param fecha
	 * @author ricardo.palacios
	 */
	public void cambiarDate(String fecha) {

		wait(3);
		sk.screen.type(Key.DELETE);
		wait(3);
		sk.screen.type(fecha);
	}
    
	/**
	 * Click en aprovacion de execute
	 * 
	 * @author ricardo.palacios
	 */
	public void clickAprovacion() {

		try {

			sk.screen.wait(imgPattern.get("checkAprovacion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Click en imprimir 
	 * 
	 * @author ricardo.palacios
	 */
	public void clickPrint() {

		try {

			sk.screen.wait(imgPattern.get("print")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

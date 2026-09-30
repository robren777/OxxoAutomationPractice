/**
 *	Clase para automatizar el apartado de Intercedis
 *
 * @author ricardo.palacios
 */

package RDM.util;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Intercedis extends BaseRDM {

	public Intercedis(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
	}

	
	/**
	 * click en el boton CreateRecord
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickCreateRecord() throws FindFailed {

			sk.screen.wait(imgPattern.get("createRecord")).click();
			wait(3);

	}
	
	/**
	 * click en Distribution Planning / XXFC Programacion y balanceo / XXFC-Mod Programacion p Ruta
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionPlaningXxfcModpRuta() {

		try {

			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("xxxProgramacionBalanceo")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("xxfcModProgramacionpRuta")).click();
			wait(3);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ingresar status y distro
	 * 
	 * @author ricardo.palacios
	 */
	public void ingresarStatusDistro(String status, String distro) {

		try {
 
			wait(3);
			sk.screen.wait(imgPattern.get("Distrointercedisuno")).offset(90, 0).click();
			this.nBackspace(20);
			wait(3);
			sk.screen.type(distro);
			wait(3);
			sk.screen.wait(imgPattern.get("statusIntercedis")).offset(90, 0).click();
			this.nBackspace(20);
			wait(3);
			sk.screen.wait(imgPattern.get("statusIntercedis")).offset(90, 0).click();
			this.nBackspace(20);
			sk.screen.type(status);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Appointments > Appointment Schedule
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickAppointmentSchedule() throws FindFailed {

			wait(5);
			sk.screen.wait(imgPattern.get("appointments")).click();
			wait(5);
			sk.screen.wait(imgPattern.get("appointmentSchedule")).click();
			wait(15);
	
	}
	
	/**
	 * Ingresar datos en las opciones fecha inicial, fecha final, T distrib, distro
	 * 
	 * @author ricardo.palacios
	 */
	public void ingresarParametros(String fechaInicial,String fechaFinal,String tDistrib,String distro) {

		try {

			sk.screen.wait(imgPattern.get("fechaInicial")).offset(130, 0).click();
			wait(3);
			sk.screen.type(fechaInicial);
			wait(3);
			sk.screen.wait(imgPattern.get("fechaFinal")).offset(130, 0).click();
			wait(3);
			sk.screen.type(fechaFinal);
			wait(3);
			sk.screen.wait(imgPattern.get("tDistrib")).offset(130, 0).click();
			wait(3);
			sk.screen.type(tDistrib);
			wait(3);
			sk.screen.wait(imgPattern.get("distro")).offset(130, 0).click();
			wait(3);
			sk.screen.type(distro);
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Receiving / XXFC Cross Dock Receiving
	 * 
	 * @author ricardo.palacios
	 */
	public void clickReceivingXxfcCrossDockReceiving() {

		try {

			sk.screen.wait(imgPattern.get("receiving")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("xxfcCrossDockReceiving")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En titulo start
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickStartImagen() throws FindFailed {

			sk.screen.wait(imgPattern.get("StartIntercedis"), 15).click();
			wait(3);
	}
	
	/**
	 * En el menu da click en boton detail
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDetail() {

		try {

			sk.screen.wait(imgPattern.get("detail")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en check
	 * 
	 * @author ricardo.palacios
	 */
	public void clickEjecutarCheck() {

		try {

			sk.screen.wait(imgPattern.get("checkIntercedisAprovacion")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en boton ejecutar
	 * 
	 * @author ricardo.palacios
	 */
	public void clickEjecutar() {

		try {

			sk.screen.wait(imgPattern.get("executeQuery")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en boton sellect all
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSelectAll() {

		try {

			sk.screen.wait(imgPattern.get("selectAll")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Click en XXFC-Wave Status
	 * 
	 * @author ricardo.palacios
	 */
	public void clickWaveStatus() {

		try {

			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("reports")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("XxfcWaveStatusDistroIntercedis")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en boton balancear
	 * 
	 * @author ricardo.palacios
	 */
	public void clickBalancear() {

		try {

			sk.screen.wait(imgPattern.get("balancear")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en item detail
	 * 
	 * @author ricardo.palacios
	 */
	public void clickItemDetail() {

		try {

			sk.screen.wait(imgPattern.get("itemDetail")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el boton confirm Record
	 * 
	 * @author ricardo.palacios
	 */
	public void clickConfirmRecord() {

		try {

			sk.screen.wait(imgPattern.get("confirmRecord")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el boton pint Labels
	 * 
	 * @author ricardo.palacios
	 */
	public void clickPrintLabels() {

		try {

			sk.screen.wait(imgPattern.get("printLabels")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En la ventana emergente tipo de reporte y escribir el tipo
	 * 
	 * @param tipo
	 * @author ricardo.palacios
	 */
	public void clickTipoReporte(String tipo) {

		try {

			sk.screen.wait(imgPattern.get("tipoReporte")).click();
			sk.screen.type(tipo);
			wait(3);
			sk.screen.wait(imgPattern.get("acceptarintercedis")).click();
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Close APPT
	 * 
	 * @author ricardo.palacios
	 */
	public void clickCloseAppt() {

		try {

			sk.screen.wait(imgPattern.get("closeAppt")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en summary Intercedis
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSummaryIntercedis() {

		try {

			sk.screen.wait(imgPattern.get("summaryIntercedis")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en save Intercedis
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickSaveIntercedis() throws FindFailed {

			sk.screen.wait(imgPattern.get("SaveIntercedis")).click();
			wait(3);
	}
    
	public void clickSaveIntercedis2() throws FindFailed {

		sk.screen.wait(imgPattern.get("SaveIntercedis2")).click();
		wait(3);
	}
	
	public void clickSave() throws FindFailed {

		sk.screen.wait(imgPattern.get("save")).click();
		wait(3);

}

	
	/**
	 * Cambiar fecha de el calendario
	 * 
	 * @param fecha
	 * @author ricardo.palacios
	 */
	public String cambiarDate(String fecha) {

		wait(3);
		//sk.screen.type(Key.DELETE);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		String fechaCitaNueva = "";
		fechaCitaNueva = App.getClipboard();
		sk.screen.type(Key.TAB);
		wait(3);
		//sk.screen.type(fecha);
		return fechaCitaNueva;
	}
	
	/**
	 * Minimiza la ventana del navegador
	 * 
	 */
	public void minimizarWindow() {

		try {
			
			if (sk.screen.exists(imgPattern.get("min"), 0) != null) {
				sk.screen.wait(imgPattern.get("min")).click();
			} else {
				sk.screen.wait(imgPattern.get("minOsc")).click();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

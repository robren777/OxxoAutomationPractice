/**
 *Clase para automatizar el modulo de merma y devoluciones de RDM
 *
 * @author guerrero.vargas
 */

package RDM.util;

import static org.testng.Assert.assertTrue;

import java.awt.HeadlessException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;

import org.sikuli.script.Button;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.Assert;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class MermaYDev extends BaseRDM {

	public MermaYDev(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
	}

	/**
	 * En el menu da click en Appointments > Appointment Schedule
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuAppointmentSchedule() {
		try {
			sk.screen.wait(imgPattern.get("appointments")).click();
			sk.screen.hover(imgPattern.get("appointmentSchedule"));
			screenshot();
			sk.screen.click();
			
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * En el menu da click en Appointments > Appointment Schedule
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuAppointmentNscAppointmentDetail() {
		try {
			sk.screen.wait(imgPattern.get("appointments")).click();
			wait(5);
			sk.screen.hover(imgPattern.get("nscAppointmentDetail"));
			screenshot();
			sk.screen.click();
			
			wait(15);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * En el menu da click en Inventory Management > Inventory Inquiry by Location
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuInventoryMngIventoryLocation() {
		try {
			sk.screen.wait(imgPattern.get("inventoryManagement")).click();
			sk.screen.hover(imgPattern.get("inventoryByLocation"));
			screenshot();
			sk.screen.click();
			
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * En el menu da click en Inventory Management > Inventory Inquiry by Location
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuReceivingXxfcAsnNscReceiving() {
		try {
			//Da clic en modulo receiving
			sk.screen.wait(imgPattern.get("receiving")).click();
			//Espera a que se muestre el modulo xxfc asn nsc receiving y da clic
			sk.screen.hover(imgPattern.get("xxfcAsnNscReceiving"));
			screenshot();
			sk.screen.click();
			
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	/**
	 * En el menu da click en Inventory Management > Return Vendor
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuInventoryMngReturnVendor() {
		try {
			wait(6);
			sk.screen.wait(imgPattern.get("inventoryManagement")).click();
			wait(6);
			sk.screen.hover(imgPattern.get("returnToVendor"));
			screenshot();
			sk.screen.click();
			
			wait(5);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Ingresa la informacion de la cita requerida para recibir el ASN
	 * 
	 * @param noCita 
	 * @param noAsn
	 * @param noItem Id del item que se va a recibir
	 * @param unidadesRcvd Unidades que se van a recibir
	 * @param opcion Opcion por la que se recibe
	 * @param unrcCode Codigo por el cual no se puede recibir completamente la mercancia
	 * @return String[] con el Id del item y la cantidad a recibir
	 * @author guerrero.vargas 
	 */
	public String[] ingresarInfo(String noCita, String noAsn, String noItem, String unidadesRcvd, 
			String opcion, String unrcCode) {
		String info []= new String[2];
		numLockOff();
		
		String itemId = "";
		String appt = "";
		
		try {
			screenshot();
			
			sk.screen.type(noCita);
			sk.screen.type(Keys.TAB);
			/*
			sk.screen.type(noAsn);
			sk.screen.type(Keys.TAB);
			
			sk.screen.type(noItem);
			sk.screen.type(Keys.TAB);
			*/
			wait(10);
			sk.screen.wait(imgPattern.get("executeQueryAsn")).click();	
			screenshot();
			
			if (sk.screen.exists(imgPattern.get("criticalAlertReceiving")) != null) {
				return null;
			}
			
			sk.screen.wait(imgPattern.get("itemId")).offset(50, 20).click();
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			wait(5);
			copyPaste();
			info[0] = copyPaste();
			System.out.println(info[0]);
			wait(3);			
			
			sk.screen.wait(imgPattern.get("appt")).offset(15, 20).click();
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			wait(3);
			info[1]= copyPaste();
			System.out.println(info[1]);
			wait(3);
			
			sk.screen.type(Keys.TAB);
			sk.screen.type(unidadesRcvd);
			wait(1);
			screenshot();
			sk.screen.type(Keys.TAB);
			
			wait(5);
			// Si aparece el mensaje de cantidad invalida regresa null
			if (sk.screen.exists(imgPattern.get("quantityInvalid"), 0) != null) {
				screenshot();
				wait(1);
				
				return null;
			}
			if (sk.screen.exists(imgPattern.get("green"), 0) != null) {
				sk.screen.wait(imgPattern.get(opcion)).click();
			} else {
				wait(5);
				sk.screen.wait(imgPattern.get("unrcDesplegable")).click();
				wait(1);
				sk.screen.type(unrcCode);
				sk.screen.wait(imgPattern.get("unrcCodeOk")).click();
				sk.screen.wait(imgPattern.get(opcion)).click();				
				
			}
			
			screenshot();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return info;
	}
	
	/**
	 * En la pantalla XXFC ASN NSC receiving coloca el numero de cita 
	 * 
	 * @param noCita numero de cita a colocar
	 * @author guerrero.vargas
	 */
	public void ingresarNoCita(String noCita) {		
		try {
			sk.screen.type(noCita);
			sk.screen.type(Keys.TAB);
			
			sk.screen.wait(imgPattern.get("executeQueryAsn")).click();
			wait(1);
			screenshot();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}	
		
	}
	
	/**
	 * coloca el numero de contenedor que se recibira
	 * 
	 * @param containerId Id del contenedor que se va a recibir
	 * @author guerrero.vargas
	 */
	public void itemRecibido(String containerId) {
		try {
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("container"));
			sk.screen.type(containerId);
			sk.screen.type(Keys.TAB);
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("containerOk")).click();
			
			wait(5);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	 }
	
	/**
	 * Revisa que un item ya no aparezca en la tabla debido a que se resibio completamente
	 * 
	 * @param item Numero de item que se va a buscar
	 * @return true si el item ya no aparece en la tabla, de lo contrario false
	 * @author guerrero.vargas
	 */
	public boolean recibidoCompletamente(String item) {
		try {
			numLockOff();
	
			sk.screen.wait(imgPattern.get("itemId")).offset(50, 20).click();
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			String itemId = copyPaste();
			wait(1);
		
			
			if (!itemId.equals(item)) {
				return true;
			}
			screenshot();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Si se recibio solo una cantidad de items checa que se muestre en la tabla los items 
	 * restantes
	 * 
	 * @param nuevaQty nueva cantidad de items que deben aparecer
	 * @return true si aparecen los items restantes, de lo contrario false
	 */
	public boolean recibidoIncompletamente(String nuevaQty) {
		try {
			numLockOff();

			sk.screen.wait(imgPattern.get("appt")).offset(15, 20).click();
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			String appt = copyPaste();
			wait(1);
		
			
			if (appt.equals(nuevaQty)) {
				return true;
			}
			screenshot();
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		
		return false;
	}
	
	/**
	 * Recibe item mediante Adjust
	 * 
	 *  @author guerrero.vargas
	 */
	public void recibirPorAdjust() {
		try {
			wait(1);
			screenshot();
			sk.screen.wait(imgPattern.get("reasonCodeOk")).click();
			
			wait(3);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Cierra la cita y si hay renglones sin UNRC CODE los coloca debido a que es requerimiento
	 * para cerrar la cita
	 *
	 *@param unrcCode codigo que colocara en el apartado UNRC CODE
	 *@return true si se cerro la cita, false de lo contrario
	 *@author guerrero.vargas 
	 */
	public boolean closeAppt(String unrcCode, String sealNbr) {
		try {
			sk.screen.wait(imgPattern.get("closeAppt")).click();
			
			wait(1);
			screenshot();
			
			if (sk.screen.exists(imgPattern.get("notHaveUnrc")) != null) {
				sk.screen.wait(imgPattern.get("notHaveUnrcYes")).click();
				
				while (sk.screen.exists(imgPattern.get("unrcDesplegable2"), 0) != null) {
					sk.screen.wait(imgPattern.get("unrcDesplegable2")).offset(65, 0).click();
					wait(1);
					sk.screen.type(unrcCode);
					sk.screen.wait(imgPattern.get("unrcCodeOk")).click();
					wait(1);
				}
				
				if (sk.screen.exists(imgPattern.get("unrcDesplegableBlue"), 0) != null) {
					sk.screen.wait(imgPattern.get("unrcDesplegableBlue")).offset(55, 0).click();
					wait(1);
					sk.screen.type(unrcCode);
					sk.screen.wait(imgPattern.get("unrcCodeOk")).click();
					wait(1);
				}
				
				screenshot();
				wait(1);
				sk.screen.wait(imgPattern.get("closeAppt")).click();
				wait(1);
				screenshot();
			}
			
			sk.screen.wait(imgPattern.get("unreceivedYes")).click();
			wait(1);
			screenshot();
			
			sk.screen.type(sealNbr);
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("sealNbrOk")).click();
			wait(1);
			screenshot();
			
			return sk.screen.exists(imgPattern.get("statusUnrc")) != null;
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Llena los campos para generar una cita para devolucion
	 * 
	 * @param carrier Id del transportista
	 * @param trailerId Id del trailer
	 * @param door Id de la puerta
	 * @param type Tipo de cita
	 * @param duration Duracion de la cita
	 * @return String con la fecha de la nueva cita creada
	 * @author guerrero.vargas
	 */
	public String llenarCitaDevolucion(String fechaCita, String horaCita, String carrier, String trailerId, 
			String door, String type, String duration, String asn) {
		ArrayList<String> infoCita = new ArrayList<>();
		//infoCita.add(fechaCita + " " + horaCita);
		infoCita.add(carrier);
		infoCita.add(trailerId);
		infoCita.add(door);
		infoCita.add(type);
		infoCita.add(duration);
		infoCita.add(asn);
				
		String fechaCitaNueva = "";
		
		try {
			sk.screen.wait(imgPattern.get("createRecord")).click();
			wait(3);
			
			fechaCitaNueva = copyPaste(); // copia la fecha de la cita nueva
			sk.screen.type(Keys.TAB);
			for (String row : infoCita) {
				sk.screen.type(row);
				sk.screen.type(Keys.TAB);
				wait(1);

			}
			
			sk.screen.type(Keys.SPACE);
			
			sk.screen.hover(imgPattern.get("save"));
			screenshot();
			sk.screen.click();
			
			wait(2);
			
			// Si se empalma con otra cita, regresa null
			if (sk.screen.exists(imgPattern.get("appointmentOverlap"), 0) != null) {
				wait(1);
				screenshot();
				sk.screen.wait(imgPattern.get("appointmentOverlapNo")).click();
				
				return null;
			}
			
			// si se coloco un numero de puerta invalido, regresa null
			if (sk.screen.exists(imgPattern.get("invalidDoor"), 0) != null) {
				wait(1);
				screenshot();
				sk.screen.wait(imgPattern.get("invalidDoorYes")).click();
				
				return null;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return fechaCitaNueva;
	}
	
	/**
	 * Recorre la tabla de la pantalla appointment schedule para buscar la cita nueva
	 * 
	 * @param fechaCitaActual fecha en la cual se creo la cita 
	 * @return String[] con el numero de cita y el status
	 * @author guerrero.vargas
	 */
	public String[] verificarCitaProgramada(String fechaCitaNueva) {
		numLockOff();
				
		sk.screen.type(Keys.PAGE_DOWN);
		wait(1);
		screenshot();
		sk.screen.type(Keys.END, KeyModifier.SHIFT);
		
		String fechaCita = copyPaste();
		System.out.println("fechaCita: " + fechaCita);
		
		// si no encuentra fecha con FechaCitaNueva, regresa null
		if (!fechaCita.equals(fechaCitaNueva)) {
			return null;
		}
		
		// si encuentra cita, obtiene el numero de cita y el status y lo regresa
		nTAB(2);
		wait(1);
		sk.screen.type(Keys.END, KeyModifier.SHIFT);
		String noCita = copyPaste(); 
		System.out.println("noCita: " + noCita);
		
		nTAB(7);
		wait(1);
		sk.screen.type(Keys.END, KeyModifier.SHIFT);		
		String status = copyPaste(); 
		System.out.println("status: " + status);
		
		screenshot();
		
		return new String[] {noCita, status};
	}

	/**
	 * Coloca la fecha en el campo "AppointmentDate"
	 * 
	 * @param fecha string de la fecha a colocar en "Appointment Date"
	 * @author guerrero.vargas
	 */
	public boolean colocarFechaAppointmentDate(String fecha) {
		sk.screen.type(fecha);
		sk.screen.type(Keys.TAB);
		wait(1);
		
		if (sk.screen.exists(imgPattern.get("dateFormatError"), 0) != null ||
				sk.screen.exists(imgPattern.get("workingTimeError")) != null) {
			screenshot();
			return false;
		}
		
		screenshot();		
		wait(1);
		
		return true;
	}
	
	/**
	 * Colocal el numero de cita al que se le va a agregar el ASN
	 * 
	 * @param noCita Id de la cita a la que se le va a agregar ASN
	 * @author guerrero.vargas
	 */
	public void colocarNumeroCitaAgregar(String noCita) {
		try {
			sk.screen.type(noCita);
			screenshot();
			sk.screen.type(Keys.TAB);
			wait(1);
			closeCritical();
			
			sk.screen.wait(imgPattern.get("addMultipleAsn")).click();
			wait(1);
			screenshot();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Coloca el numero de cita al que se le va a eliminar el ASN
	 * 
	 * @param noCita numero de cita al que se le va a eliminar el ASN
	 * @author guerrero.vargas
	 */
	public void colocarNumeroCitaEliminar(String noCita) {
		sk.screen.type(noCita);
		screenshot();
		sk.screen.type(Keys.TAB);
		closeCritical();
		
	}
	
	/**
	 * Recorre la tabla de la pantalla NSC Appointment Detail buscando un ASN
	 * 
	 * @param asn numero de ASN a buscar
	 * @return true si encuentra el ASN, de lo contrario false
	 * @author guerrero.vargas
	 */
	public boolean buscarAsn(String asn) {
		numLockOff();
				
		while (true) {
			App.setClipboard("");
			
			sk.screen.type(Keys.END);
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			String asnAgregado = copyPaste();
			
			if (asnAgregado.equals(asn)) {
				return true;			
			}			
			
			sk.screen.type(Key.DOWN);
				
			if (sk.screen.exists(imgPattern.get("lastRecordAsn"), 0) != null) {
				return false;
			}
			
		}	
	
	}
	
	/**
	 * Recorre la tabla de la pantalla NSC Appointment Detail buscando un ASN
	 * 
	 * @param asn numero de ASN a buscar
	 * @return true si no encuentra el ASN, de lo contrario false
	 * @author guerrero.vargas
	 */
	public boolean validarEliminacionAsn(String asn) {
		numLockOff();
				
		while (true) {
			App.setClipboard("");
			
			sk.screen.type(Keys.END);
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			String asnAgregado = copyPaste();
			
			if (asnAgregado.equals(asn)) {
				return false;			
			}			
			
			sk.screen.type(Key.DOWN);
				
			if (sk.screen.exists(imgPattern.get("lastRecordAsn"), 0) != null) {
				return true;
			}
			
		}	
	
	}
	
	/**
	 * Elimina un ASN seleccionado
	 * 
	 * @return true si no salio mensaje de error al eliminar, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean eliminarAsn() {
		try {
			sk.screen.wait(imgPattern.get("deleteRecord")).click();
			
			wait(1);
			screenshot();
			sk.screen.wait(imgPattern.get("confirmAlertAsn")).click();
			wait(1);
			sk.screen.wait(imgPattern.get("yesEliminarAsn")).click();
			
			screenshot();
			
			wait(1);
			
			// si sale mensaje de error es que no se pudo eliminar la cita y regresa null
			if (sk.screen.exists(imgPattern.get("criticalAlertDmsError")) != null) {
				screenshot();
				return false;
			}
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Coloca el asn que se agregarar a una cita
	 * 
	 * @param asns asns que se van a agregar
	 * @author guerrero.vargas
	 */
	public boolean llenarInfoAsn(String[] asns) {		
		try {
			sk.screen.wait(imgPattern.get("search")).click();
			sk.screen.hover(imgPattern.get("asn"));
			screenshot();
			sk.screen.click();
			
			wait(1);
			
			for (String asn : asns) {
				sk.screen.type(asn);
				sk.screen.type(Keys.DOWN);
			}
			
			screenshot();
			sk.screen.wait(imgPattern.get("runQuery")).click();
			wait(1);
			screenshot();
			
			if (sk.screen.exists(imgPattern.get("asnNotFound"), 0) != null) {
				wait(1);
				return false;
			}
			
			sk.screen.wait(imgPattern.get("selectAll")).click();
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("saveAsn")).click();
			wait(1);
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return true;

	}
	
	/**
	 * Coloca tiendas que se van a agregar a una cita de devolucion
	 * 
	 * @param tiendas numero de tiendas que se van a agregar
	 * @return ArrayList con los asns pertenecientes a las tiendas agregadas
	 * @author guerrero.vargas
	 */
	public ArrayList<String> llenarInfoTiendas(String[] tiendas) {		
		ArrayList<String> asns = new ArrayList<>();
		
		try {
			sk.screen.wait(imgPattern.get("search")).click();
			sk.screen.hover(imgPattern.get("store"));
			screenshot();
			sk.screen.click();
			
			wait(1);
			
			// coloca las tiendas
			for (String tienda : tiendas) {
				sk.screen.type(tienda);
				sk.screen.type(Keys.TAB);
				sk.screen.type(Keys.DOWN);
			}
			
			screenshot();
			sk.screen.wait(imgPattern.get("runQuery")).click();
			wait(1);
			screenshot();
			
			// si no encuentra asn a una tienda regresa null
			if (sk.screen.exists(imgPattern.get("notDataFoundAsnStore")) != null) {
				return null;
			}
			
			String actualAsn = copyPaste();
			String oldAsn = "";
			
			// agrega los asn al arraylist
			while (true) {
				oldAsn = actualAsn;
				asns.add(actualAsn);
				sk.screen.type(Keys.DOWN);
				
				actualAsn = copyPaste();
				
				if (actualAsn.equals(oldAsn)) {
					break;
				}	
			}
			
			sk.screen.wait(imgPattern.get("selectAll")).click();
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("saveAsn")).click();
			wait(1);
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return asns;
		
	}
	
	/**
	 * recorre la tabla en la pantalla NSC Appointment Detail para validar si se agregaron las ASN
	 *  
	 * @param asns numeros de asns a validar
	 * @return true si encuentra los asns agregados, false de lo contrario
	 * @author guerrero.vargas
	 * 
	 */
	public boolean validarAsnsAgregados(String[] asns) {
		numLockOff();
		
		screenshot();
		
		boolean todosAgregados = false;
		List<String> listaAsns = new ArrayList<>(Arrays.asList(asns));
		
		while (true) {
			App.setClipboard("");
			
			sk.screen.type(Keys.END);
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			String asnAgregado = copyPaste();
			
			if (listaAsns.contains(asnAgregado)) {
				listaAsns.remove(asnAgregado);
			}
			
			if (listaAsns.size() == 0) {
				todosAgregados = true;
				break;
			}
			
			sk.screen.type(Keys.DOWN);
			
			if (sk.screen.exists(imgPattern.get("statusLastRecord"), 0) != null) {
				return false;
			}
		}
		
		return todosAgregados;
		
	}
	
	/**
	 * Valida que se hayan agregado las tiendas
	 * 
	 * @param tiendas Numero de tiendas que se van a validas
	 * @return true si se agregaron las tiendas, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarTiendasAgregadas(String[] tiendas) {
		numLockOff();
		
		screenshot();
		
		boolean todasAgregadas = false;
		List<String> listaTiendas = new ArrayList<>(Arrays.asList(tiendas));
		
		while (true) {
			App.setClipboard("");
			
			sk.screen.type(Keys.END, KeyModifier.SHIFT);
			String tiendaAgregada = copyPaste();
			
			if (tiendaAgregada.equals("")) {
				sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
				tiendaAgregada = copyPaste();
				
				if (tiendaAgregada.equals("")) {
					break;
				}
			}
			
			if (listaTiendas.contains(tiendaAgregada)) {
				listaTiendas.remove(tiendaAgregada);
			}
			
			if (listaTiendas.size() == 0) {
				todasAgregadas = true;
				break;
			}
			
			try {
				sk.screen.wait(imgPattern.get("downArrow")).click();
			} catch (FindFailed e) {
				System.out.println(e.getMessage());
			}
		}
		
		wait(2);
		
		return todasAgregadas;
		
	}
	
	/**
	 * Ingresa el location al que se le va a validar el status de los contenedores
	 *
	 * @param locationId numero de location al que se le va a validar los contenedores
	 * @author guerrero.vargas
	 */
	public void ingresarMuelleDev(String locationId) {
		screenshot();
		
		sk.screen.type(locationId);
		screenshot();
		sk.screen.type(Keys.TAB);
	
	}
	
	/**
	 * Valida si al colocar el location id muestra el critical no data found in search
	 * 
	 * @return true si encuentra  el critical, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarMuelle() {		
		wait(1);
		
		if (sk.screen.exists(imgPattern.get("criticalAlertMuelle")) == null) {
			return false;
		}
		
		screenshot();
		
		try {
			sk.screen.wait(imgPattern.get("criticalAlertMuelle"));
			wait(1);
			sk.screen.wait(imgPattern.get("yesMuelle")).click();
			
			wait(2);
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * En caso de que location id regrese info valida que la info regresada se encuentre en status T
	 *
	 * @return true si la info se encuentra en status T, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarStatusT() {		
		Pattern lastRecord = imgPattern.get("statusLastRecord");
		
		numLockOff();
		
		try {
			sk.screen.wait(imgPattern.get("locationStatus")).offset(0, 10).doubleClick();
			
			// recorre la tabla en busca de status T, si hay algo distinto a T regresa null
			while (true) {
				sk.screen.type(Keys.END, KeyModifier.SHIFT);
				String status = copyPaste();
				
				if (!status.equals("T")) {
					return false;
				}
				
				sk.screen.type(Keys.DOWN);				
				
				if (sk.screen.exists(lastRecord, 0) != null) {
					return false;
				}
				
			}
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Cierra ventana de error al colocar fecha en el campo "Appointment Date"
	 * 
	 * @author guerrero.vargas
	 */
	public void closeCritical() {
		try {
			if (sk.screen.exists(imgPattern.get("notDataFound"), 1.0) != null) {
				screenshot();
				sk.screen.wait(imgPattern.get("notDataFoundYes")).click();
				return;
			}
			
			if (sk.screen.exists(imgPattern.get("notDataFoundDev"), 1.0) != null) {
				screenshot();
				sk.screen.wait(imgPattern.get("notDataFoundDevYes")).click();
				return;
			}
			
			if (sk.screen.exists(imgPattern.get("critical"), 1.0) != null) {
				screenshot();
				sk.screen.wait(imgPattern.get("yes")).click();
				return;
			}
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Click en print y en save para generar el reporte del reprint
	 
	 * @return String url del pop up que se genera al crear el reporte
	 * @author guerrero.vargas
	 */
	public String generarArchivo() {
		String currentUrl = null;
		
		try {
			wait(1);
			screenshot();
			sk.screen.wait(imgPattern.get("saveReprints")).click();
			
			wait(5);
			
			// si se creo el pop up y la url contiene el String "reprints" 
			// consideramos que la reimpresion se creo y devolvemos la url para validaciones
			boolean reimpresion = mostroReimpresion();			
			if (reimpresion) {
				return getDriver().getCurrentUrl();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return currentUrl; // devuelve null, el reporte fallo
	}
	
	/**
	 * En el menu da click en Support Functions > Processing / returns / setup >
	 * Reprint null labels
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuSupportProcessingReprint() {
		try {
			sk.screen.wait(imgPattern.get("supportFunctions")).click();
			sk.screen.wait(imgPattern.get("processingReturnsSetup")).click();
			sk.screen.hover(imgPattern.get("reprintNullLabels"));
			screenshot();
			sk.screen.click();
			
			wait(2);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Coloca el numero de etiquetas que quieres reimprimir 
	 * 
	 * @param Cantidad de etiquetas a reimprimir
	 * @author guerrero.vargas
	 */
	public void reprintEtiquetas(String cantidadEtiquetas) {
		try {
			screenshot();
			
			nTAB(4);
			wait(1);
			sk.screen.type(cantidadEtiquetas);
			sk.screen.hover(imgPattern.get("reprint"));
			screenshot();
			sk.screen.click();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Maneja el cambio de ventana al pop up
	 * 
	 * @return true si la url del pop up contiene el String "reprints", de lo contrario false
	 * @author guerrero.vargas
	 */
	private boolean mostroReimpresion() {
		Set<String> handles = getDriver().getWindowHandles(); // obtiene las ventanas	  
		
		handles.remove(handles.iterator().next()); // elimina la ventana principal
		
		// cambia al pop up (lastHandle = ultima ventana creada)
		String lastHandle = handles.iterator().next(); 
		getDriver().switchTo().window(lastHandle);
    
   		getDriver().manage().window().maximize(); // maximiza
   		wait(3);
   		
   		// si la url no contiene el String "reprints" consideramos
   		// que la creacion de la reimpresion fallo
   		if (!getDriver().getCurrentUrl().contains("reprints")) {
   			return false;
   		}
   		
   		screenshot();
   		
   		return true;
	}
	
	
	/**
	 * Coloca la info necesaria para realizar devolucion de mercancia a un proveedor
	 * 
	 * @param containerId Id del contenedor
	 * @param vendorNbr Id del vendor
	 * @param auth 
	 * @param month
	 * @param storagePallets
	 * @param destructionPallets
	 * @author guerrero.vargas
	 */
	public void llenarInfoReturnToVendor(String containerId, String vendorNbr, String auth, 
			String month, String storagePallets, String destructionPallets) {
		try {
			wait(1);
			screenshot();
			wait(1);
			
			sk.screen.type(containerId);
			sk.screen.type(Keys.TAB);
			screenshot();
		
			sk.screen.wait(imgPattern.get("vendorNbrList")).click();
			wait(5);
			screenshot();
			
			if(sk.screen.exists(imgPattern.get("find")) != null) {
				sk.screen.wait(imgPattern.get("find")).click();
				sk.screen.type(vendorNbr);
				wait(5);
				sk.screen.type(Keys.ENTER);
				wait(5);
				screenshot();	
			}			
			wait(3);			
			sk.screen.wait(imgPattern.get("vendorOk")).click();
			wait(3);
			screenshot();
			
			sk.screen.type(auth);
			screenshot();
			sk.screen.type(Keys.TAB);
			
			sk.screen.type(month);
			screenshot();
			sk.screen.type(Keys.TAB);
			
			//if (sk.screen.exists(imgPattern.get("storagePallets")) == null) {
				
			//} 
			
			//sk.screen.wait(imgPattern.get("storagePallets")).offset(70, 0).click();
			//sk.screen.type(storagePallets);
			
			
			
			sk.screen.wait(imgPattern.get("destructionPallets")).offset(70, 0).click();
			sk.screen.type(destructionPallets);
			
			wait(3);
			screenshot();
			
			sk.screen.wait(imgPattern.get("rtv")).click();		
            wait(6);
            screenshot();
			
			sk.screen.wait(imgPattern.get("confirmRtvYes")).click();
			screenshot();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	/**
	 * Ventana Destination
	 * 
	 * @author guerrero.vargas
	 */
	public void descargarXxfcRtvAdvice() {
		try {
			wait(7);
			
			
			if (sk.screen.exists(imgPattern.get("XxfcRtvAdviceSetup")) != null) {
				sk.screen.wait(imgPattern.get("destination")).click();
				wait(5);
				screenshot();
				
				sk.screen.wait(imgPattern.get("destinationSaveBtn")).click();
				wait(35);				
				screenshot();
				
				sk.screen.wait(imgPattern.get("EdgeOpen")).click();
				wait(20);				
				screenshot();
				
			} else {
				Assert.fail("No se muestra ventana XxfcRtvAdviceSetup");
			}		
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getRtvId(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.click();
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		//System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		//String findResult = ""; // Lo que regresaermos
        String [] parts = textFound.split(" ");
        ArrayList<String> al = new ArrayList<String>();
        
        
        boolean exist = false;
        String rtv = "";
        int contador = 0;
        for(int i = 0; i < parts.length-1; i++) {
        	if(parts[i].contains(find)) {
        		al.add(parts[i+2]);
        		System.out.println("String buscado: "+parts[i]); 
        		System.out.println("RTV ID: "+parts[i+2]); 
        		rtv = parts[i+2];
        		exist = true;
        		contador++;
        	}
        }
                
		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		wait(3);
		screenshot();
		return rtv; // Si lo encuentra lo regresa

	}
	
	
	/**
	 * En el menu da click en Receiving > XXFC Devoluciones > XXFC Acreditaciones
	 * Para el proceso de devoluciones y no acreditaciones de reingeneria
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public void clickMenuReceivingXxfcAcreditaciones() {
		try {
			//Clic en receiving
			sk.screen.wait(imgPattern.get("receiving")).click();
			//Clic en modulo xxfcDevoluciones
			sk.screen.hover(imgPattern.get("xxfcDevoluciones"));
			sk.screen.click();
			//Clic en modulo xxfcAcreditaciones
			sk.screen.hover(imgPattern.get("xxfcAcreditaciones"));
			screenshot();
			sk.screen.click();
			
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * En el menu da click en Receiving > XXFC Devoluciones > XXFC Acreditaciones All In One
	 * Para el proceso de devoluciones y no acreditaciones de reingeneria All In One
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public void clickMenuReceivingXxfcAcreditacionesAllInOne() {
		try {
			//Clic en receiving
			sk.screen.wait(imgPattern.get("receiving")).click();
			//Clic en modulo xxfcDevoluciones
			sk.screen.hover(imgPattern.get("xxfcDevoluciones"));
			sk.screen.click();
			//Clic en modulo xxfcAcreditaciones All In One
			sk.screen.hover(imgPattern.get("xxfcAcreditacionesAIO"));
			screenshot();
			sk.screen.click();
			
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Ingresa la informacion de APPT NBR para cargar el listado de las devoluciones a no acreditar
	 * En la pantalla ASN NSC Receiving
	 * 
	 * @param apptNbr Dato identificador de la cita para cargar el listado de las devoluciones 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public void ingresarApptNbrCargaDevoluciones(String apptNbr) {
		
		try {
			//Una vez ingreses a la pantalla ASN NSC Receiving se ingresa directamente el ApptNbr
			sk.screen.type(apptNbr);
			//Se da clic en la palomita roja para realizar la busqueda
			sk.screen.wait(imgPattern.get("executeQueryAsn")).click();
			wait(2);
			screenshot();
			
			//Si existe la pantalla de error significa que no esta encontrando datos con ese apptnbr
			if (sk.screen.exists(imgPattern.get("criticalAlertReceiving")) != null) {
			System.out.println("No existen datos con este apptNbr");	
			}									
			
		}
		catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}	
	
	/**
	 * Realiza la carga del ASN ligado con la Ruta 
	 * (Cuando solamente se tiene 1 ruta generada y esta dentro del rango de fecha)
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public void clickLoadAsnXxfcAcreditacionesReingeneria() {
		try {
			//Clic en confirmar carga ASN pendientes de acreditar
			sk.screen.wait(imgPattern.get("confirmLoadAsn")).click();
			wait(1);
			//Clic en si cargar ASN pendientes de acreditar
			sk.screen.wait(imgPattern.get("yesLoadAsn")).click();
			wait(1);
			//Clic para buscar rutas habilitadas
			sk.screen.wait(imgPattern.get("cargaRuta")).offset(93, -24).click();
			wait(15);
			screenshot();
			//Clic para confirmar la unica ruta disponible
			sk.screen.wait(imgPattern.get("rutasPendientes")).offset(37, 120).click();
			//Clic para hacer la carga de los articulos
			sk.screen.wait(imgPattern.get("openRuta")).click();
			wait(5);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("No existen ASN por cargar o Rutas Habilitadas "
					+ "dentro del rango de la fecha actual");
		}
	}
	
	
	/**
	 * Realiza la carga del ASN ligado con la Ruta 
	 * (Cuando solamente se tiene 1 ruta generada y esta dentro del rango de fecha)
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public void clickLoadAsnXxfcAcreditacionesReingeneriaAio() {
		try {
			//Clic en confirmar carga ASN pendientes de acreditar
			sk.screen.wait(imgPattern.get("confirmLoadAsn")).click();
			wait(1);
			//Clic en si cargar ASN pendientes de acreditar
			sk.screen.wait(imgPattern.get("yesLoadAsn")).click();
			wait(1);
			//Clic para buscar rutas habilitadas
			sk.screen.wait(imgPattern.get("cargaRutaAIO")).offset(93, -24).click();
			wait(15);
			screenshot();
			//Clic para confirmar la unica ruta disponible
			sk.screen.wait(imgPattern.get("rutasPendientesAio")).offset(37, 160).click();
			//Clic para hacer la carga de los articulos
			sk.screen.wait(imgPattern.get("openRutaAIO")).click();
			wait(5);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("No existen ASN por cargar o Rutas Habilitadas "
					+ "dentro del rango de la fecha actual");
		}
	}
	
	/**
	 * Valida el listado de los 14 nuevos motivos de no acreditacion se valida en
	 * pantalla Receiving > xxfc devoluciones > xxfc acreditaciones All In One(reingeneria)
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public boolean validar14NuevosMotivosNoAcreditacionReingeneriaAIO() {
	    // Lista de motivos a validar
	    String[] motivos = { "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };

	    String[] screenmotivos = { "17a", "18a", "19a", "20a", "21a", "22a", "23a", "24a", "25a", "26a", "27a", "28a",
	            "29a", "30a" };
	    boolean allFound = true;

	    try {
	        // Selecciona el check para poder modificar y seleccionar los motivos de no
	        // acreditacion
	        sk.screen.wait(imgPattern.get("checkAsnAIO")).offset(-165, -3).click();
	        screenshot();
	        // Deslizarse con el scroll hacia la derecha
	        sk.screen.wait(imgPattern.get("scrollBarHorizontalAcreditacionesAioRight")).offset(900, 0).click();
	        screenshot();
	        wait(3);

	        for (int index = 0; index < motivos.length; index++) {
	            String motivo = motivos[index];
	            String screenmotivo = screenmotivos[index];
	            boolean found = false;

	            // Intentar encontrar el motivo en la región visible
	            for (int i = 0; i < 2; i++) { // Hasta 2 intentos de validacion
	                // Da clic en el scrollbar hacia la derecha

	                sk.screen.wait(imgPattern.get("rcvdAcreditacionesAIO")).offset(-80, 20).click();
	                
	                // Espera y hace clic en el desplegable UNRC
	                sk.screen.exists(imgPattern.get("unrcDesplegableDataAcreditacionesAIO")).click();
	                wait(2);
	                // Detectar el área de la ventana
	                if (sk.screen.exists(imgPattern.get("ValidUrncCodesWindowAio")) != null) {

	                    // Verifica si existe el input "find" y realiza acciones
	                    if (sk.screen.exists(imgPattern.get("findAio")) != null) {
	                        sk.screen.wait(imgPattern.get("findAio")).click();
	                        sk.screen.type(motivo);
	                        wait(5);
	                        sk.screen.type(Keys.ENTER);
	                        wait(5);
	                        screenshot();
	                        wait(3);
	                    }
	                    if (sk.screen.exists(imgPattern.get(screenmotivo), 5) != null) {
	                        System.out.println("Motivo de no acreditacion encontrado: " + motivo);
	                        sk.screen.type(Keys.ESC);
	                        found = true;
	                        break;
	                    }
	                } else {
	                    System.out.println(
	                            "No se detectó la ventana 'Valid URNC Codes'. Verifica la imagen o la aplicación.");
	                }
	            }

	            if (!found) {
	                System.out.println("Motivo NO encontrado: " + motivo);
	                allFound = false;
	            }
	        }

	    } catch (FindFailed e) {
	        e.printStackTrace();
	        allFound = false;
	    }

	    return allFound;
	}
	
	/**
	 * Valida el listado de los 16 motivos originales de no acreditacion se valida en
	 * pantalla Receiving > xxfc devoluciones > xxfc acreditaciones (reingeneria)
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public boolean validar16MotivosOriginalesNoAcreditacionReingeneriaAIO() {
	    // Lista de motivos a validar
	    String[] motivos = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };
	    String[] screenmotivos = { "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a", "11a", "12a",
	            "13a", "14a", "15a", "16a" };
	    boolean allFound = true;

	    try {
	        // Selecciona el check para poder modificar y seleccionar los motivos de no
	        // acreditacion
	        sk.screen.wait(imgPattern.get("checkAsnAIO")).offset(-165, -3).click();
	        screenshot();
	        
	        // Deslizarse con el scroll hacia la derecha
	        sk.screen.wait(imgPattern.get("scrollBarHorizontalAcreditacionesAioRight")).offset(900, 0).click();
	        screenshot();
	        wait(3);

	        for (int index = 0; index < motivos.length; index++) {
	            String motivo = motivos[index];
	            String screenmotivo = screenmotivos[index];
	            boolean found = false;

	            // Intentar encontrar el motivo en la región visible
	            for (int i = 0; i < 2; i++) { // Hasta 2 intentos de validacion
	                // Da clic en el scrollbar hacia la derecha

	                sk.screen.wait(imgPattern.get("rcvdAcreditacionesAIO")).offset(-80, 20).click();
	                // Espera y hace clic en el desplegable UNRC
	                sk.screen.exists(imgPattern.get("unrcDesplegableDataAcreditacionesAIO")).click();
	                wait(2);
	                // Detectar el área de la ventana
	                if (sk.screen.exists(imgPattern.get("ValidUrncCodesWindowAio")) != null) {

	                    // Verifica si existe el input "find" y realiza acciones
	                    if (sk.screen.exists(imgPattern.get("findAio")) != null) {
	                        sk.screen.wait(imgPattern.get("findAio")).click();
	                        sk.screen.type(motivo);
	                        wait(5);
	                        sk.screen.type(Keys.ENTER);
	                        wait(5);
	                        screenshot();
	                        wait(3);
	                    }
	                    if (sk.screen.exists(imgPattern.get(screenmotivo), 5) != null) {
	                        System.out.println("Motivo de no acreditacion encontrado: " + motivo);
	                        sk.screen.type(Keys.ESC);
	                        found = true;
	                        break;
	                    }
	                } else {
	                    System.out.println(
	                            "No se detectó la ventana 'Valid URNC Codes'. Verifica la imagen o la aplicación.");
	                }
	            }

	            if (!found) {
	                System.out.println("Motivo NO encontrado: " + motivo);
	                allFound = false;
	            }
	        }

	    } catch (FindFailed e) {
	        e.printStackTrace();
	        allFound = false;
	    }

	    return allFound;
	}
	
	/**
	 * Valida el listado de los 14 nuevos motivos de no acreditacion se valida en
	 * pantalla Receiving > xxfc devoluciones > xxfc acreditaciones (reingeneria)
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public boolean validar14NuevosMotivosNoAcreditacionReingeneria() {
	    // Lista de motivos a validar
	    String[] motivos = { "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };
	    boolean allFound = true;

	    try {
	        // Selecciona el check para poder modificar y seleccionar los motivos de no
	        // acreditacion
	        sk.screen.wait(imgPattern.get("checkAsn")).offset(-103, 3).click();
	        screenshot();
	        
	        // Deslizarse con el scroll hacia la derecha
	        sk.screen.wait(imgPattern.get("scrollBarHorizontalAcreditacionesRight")).offset(900, 0).click();
	        wait(3);
	        screenshot();

	        for (String motivo : motivos) {
	            boolean found = false;

	            // Intentar encontrar el motivo en la región visible
	            for (int i = 0; i < 2; i++) { // Hasta 2 intentos de validacion
	                // Da clic en el scrollbar hacia la derecha
	                sk.screen.wait(imgPattern.get("rcvdAcreditaciones")).offset(-27, 17).click();
	                // Espera y hace clic en el desplegable UNRC
	                sk.screen.exists(imgPattern.get("unrcDesplegableDataAcreditaciones")).click();
	                wait(2);
	                // Detectar el área de la ventana
	                if (sk.screen.exists(imgPattern.get("Valid_URNC_Codes_Window")) != null) {

	                    // Verifica si existe el input "find" y realiza acciones
	                    if (sk.screen.exists(imgPattern.get("find")) != null) {
	                        sk.screen.wait(imgPattern.get("find")).click();
	                        sk.screen.type(motivo);
	                        wait(5);
	                        sk.screen.type(Keys.ENTER);
	                        wait(5);
	                        screenshot();
	                        wait(3);
	                    }
	                    if (sk.screen.exists(imgPattern.get(motivo), 5) != null) {
	                        System.out.println("Motivo de no acreditacion encontrado: " + motivo);
	                        sk.screen.type(Keys.ESC);
	                        found = true;
	                        break;
	                    }
	                } else {
	                    System.out.println(
	                            "No se detectó la ventana 'Valid URNC Codes'. Verifica la imagen o la aplicación.");
	                }
	            }

	            if (!found) {
	                System.out.println("Motivo NO encontrado: " + motivo);
	                allFound = false;
	            }
	        }

	    } catch (FindFailed e) {
	        e.printStackTrace();
	        allFound = false;
	    }

	    return allFound;
	}
	
	/**
	 * Valida el listado de los 16 motivos originales de no acreditacion se valida en
	 * pantalla Receiving > xxfc devoluciones > xxfc acreditaciones (reingeneria)
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public boolean validar16MotivosOriginalesNoAcreditacionReingeneria() {
	    // Lista de motivos a validar
	    String[] motivos = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };
	    boolean allFound = true;

	    try {
	        // Selecciona el check para poder modificar y seleccionar los motivos de no
	        // acreditacion
	        sk.screen.wait(imgPattern.get("checkAsn")).offset(-103, 3).click();
	        
	        // Deslizarse con el scroll hacia la derecha
	        sk.screen.wait(imgPattern.get("scrollBarHorizontalAcreditacionesRight")).offset(900, 0).click();
	        wait(3);

	        for (String motivo : motivos) {
	            boolean found = false;

	            // Intentar encontrar el motivo en la región visible
	            for (int i = 0; i < 2; i++) { // Hasta 2 intentos de validacion
	                // Da clic en el scrollbar hacia la derecha
	                sk.screen.wait(imgPattern.get("rcvdAcreditaciones")).offset(-27, 17).click();
	                // Espera y hace clic en el desplegable UNRC
	                sk.screen.exists(imgPattern.get("unrcDesplegableDataAcreditaciones")).click();
	                wait(2);
	                // Detectar el área de la ventana
	                if (sk.screen.exists(imgPattern.get("Valid_URNC_Codes_Window")) != null) {

	                    // Verifica si existe el input "find" y realiza acciones
	                    if (sk.screen.exists(imgPattern.get("find")) != null) {
	                        sk.screen.wait(imgPattern.get("find")).click();
	                        sk.screen.type(motivo);
	                        wait(5);
	                        sk.screen.type(Keys.ENTER);
	                        wait(5);
	                        screenshot();
	                        wait(3);
	                    }
	                    if (sk.screen.exists(imgPattern.get(motivo), 5) != null) {
	                        System.out.println("Motivo de no acreditacion encontrado: " + motivo);
	                        sk.screen.type(Keys.ESC);
	                        found = true;
	                        break;
	                    }
	                } else {
	                    System.out.println(
	                            "No se detectó la ventana 'Valid URNC Codes'. Verifica la imagen o la aplicación.");
	                }
	            }

	            if (!found) {
	                System.out.println("Motivo NO encontrado: " + motivo);
	                allFound = false;
	            }
	        }

	    } catch (FindFailed e) {
	        e.printStackTrace();
	        allFound = false;
	    }

	    return allFound;
	}
	
	/**
	 * Valida el listado de los 14 nuevos motivos de no acreditacion, se valida en
	 * pantalla Receiving > xxfc ASN NSC Receiving
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public boolean validar14NuevosMotivosNoAcreditacion() {

	    // Lista de motivos a validar
	    String[] motivos = { "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };
	    boolean allFound = true;

	    try {

	        // Iterar sobre cada motivo
	        for (String motivo : motivos) {
	            boolean found = false;

	            // Intentar encontrar el motivo en la región visible
	            for (int i = 0; i < 2; i++) { // Hasta 2 intentos de validacion

	                sk.screen.wait(imgPattern.get("rcvd")).offset(0, 9).click();
	                // Espera y hace clic en el desplegable UNRC
	                sk.screen.exists(imgPattern.get("unrcDesplegableData2")).click();
	                wait(2);
	                // Detectar el área de la ventana
	                if (sk.screen.exists(imgPattern.get("Valid_URNC_Codes_Window")) != null) {

	                    // Verifica si existe el input "find" y realiza acciones
	                    if (sk.screen.exists(imgPattern.get("find")) != null) {
	                        sk.screen.wait(imgPattern.get("find")).click();
	                        sk.screen.type(motivo);
	                        wait(5);
	                        sk.screen.type(Keys.ENTER);
	                        wait(5);
	                        screenshot();
	                        wait(3);
	                    }
	                    // Si detecta la existencia del motivo ingresado imprime el mensaje
	                    if (sk.screen.exists(imgPattern.get(motivo), 5) != null) {
	                        System.out.println("Motivo de no acreditacion encontrado: " + motivo);
	                        sk.screen.type(Keys.ESC);
	                        found = true;
	                        break;
	                    }
	                } else {
	                    System.out.println(
	                            "No se detectó la ventana 'Valid URNC Codes'. Verifica la imagen o la aplicación.");
	                }
	            }

	            if (!found) {
	                System.out.println("Motivo NO encontrado: " + motivo);
	                allFound = false;
	            }
	        }

	    } catch (FindFailed e) {
	        e.printStackTrace();
	        allFound = false;
	    }

	    return allFound;
	}
	
	
	/**
	 * Valida el listado de los 16 Motivos originales de no acreditacion, se valida
	 * en pantalla Receiving > xxfc ASN NSC Receiving
	 * 
	 * @author Alejandro.Montalvo
	 * @date 15/01/2025
	 */
	public boolean validar16MotivosOriginalesNoAcreditacion() {

	    // Lista de motivos a validar
	    String[] motivos = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };
	    boolean allFound = true;

	    try {

	        // Iterar sobre cada motivo
	        for (String motivo : motivos) {
	            boolean found = false;

	            // Intentar encontrar el motivo en la región visible
	            for (int i = 0; i < 2; i++) { // Hasta 2 intentos de validacion

	                sk.screen.wait(imgPattern.get("rcvd")).offset(0, 9).click();
	                // Espera y hace clic en el desplegable UNRC
	                sk.screen.exists(imgPattern.get("unrcDesplegableData2")).click();
	                wait(2);
	                // Detectar el área de la ventana
	                if (sk.screen.exists(imgPattern.get("Valid_URNC_Codes_Window")) != null) {

	                    // Verifica si existe el input "find" y realiza acciones
	                    if (sk.screen.exists(imgPattern.get("find")) != null) {
	                        sk.screen.wait(imgPattern.get("find")).click();
	                        sk.screen.type(motivo);
	                        wait(5);
	                        sk.screen.type(Keys.ENTER);
	                        wait(5);
	                        screenshot();
	                        wait(3);
	                    }
	                    if (sk.screen.exists(imgPattern.get(motivo), 5) != null) {
	                        System.out.println("Motivo de no acreditacion encontrado: " + motivo);
	                        sk.screen.type(Keys.ESC);
	                        found = true;
	                        break;
	                    }
	                } else {
	                    System.out.println(
	                            "No se detectó la ventana 'Valid URNC Codes'. Verifica la imagen o la aplicación.");
	                }
	            }

	            if (!found) {
	                System.out.println("Motivo NO encontrado: " + motivo);
	                allFound = false;
	            }
	        }

	    } catch (FindFailed e) {
	        e.printStackTrace();
	        allFound = false;
	    }

	    return allFound;
	}
	
	/**
	 * Valida cada uno de los ID de los 16 motivos originales de no acreditación.
	 * @author Alejandro.Montalvo
	 * @param dbRdm Instancia de SQLUtil para ejecutar consultas en la base de datos.
	 * @return Un mapa con los IDs y sus resultados de consulta.
	 * @date 15/01/2025
	 */
	public Map<Integer, SQLResult> validarId16MotivosOriginalesAcreditacion(SQLUtil dbRdm) {
	    Map<Integer, SQLResult> resultados = new HashMap<>();
	    try {
	        for (int id = 1; id <= 16; id++) {
	            String query = "SELECT unrc_code FROM rdm100.xxfc_unrc_reason_codes WHERE unrc_code = '" + id + "'";
	            SQLResult rs = dbRdm.executeQuery(query);
	            resultados.put(id, rs);
	            if (!rs.isEmpty()) {
	                System.out.println("ID " + id + " existe en la tabla rdm100.xxfc_unrc_reason_codes.");
	            } else {
	                System.out.println("ID " + id + " no encontrado en la tabla rdm100.xxfc_unrc_reason_codes.");
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultados;
	}
	
	/**
	 * Valida cada uno de los ID de los 14 nuevos motivos de no acreditación.
	 * @author Alejandro.Montalvo
	 * @param dbRdm Instancia de SQLUtil para ejecutar consultas en la base de datos.
	 * @return Un mapa con los IDs y sus resultados de consulta.
	 * @date 15/01/2025
	 */
	public Map<Integer, SQLResult> validarId14NuevosMotivosAcreditacion(SQLUtil dbRdm) {
	    Map<Integer, SQLResult> resultados = new HashMap<>();
	    try {
	        for (int id = 17; id <= 30; id++) {
	            String query = "SELECT unrc_code FROM rdm100.xxfc_unrc_reason_codes WHERE unrc_code = '" + id + "'";
	            SQLResult rs = dbRdm.executeQuery(query);
	            resultados.put(id, rs);
	            if (!rs.isEmpty()) {
	                System.out.println("ID " + id + " existe en la tabla rdm100.xxfc_unrc_reason_codes.");
	            } else {
	                System.out.println("ID " + id + " no encontrado en la tabla rdm100.xxfc_unrc_reason_codes.");
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultados;
	}
	
	
	/**
	 * Valida cada uno de los ID de los 16 motivos originales de no acreditación.
	 * @author Alejandro.Montalvo
	 * @param dbRdm Instancia de SQLUtil para ejecutar consultas en la base de datos.
	 * @return Un mapa con los IDs y sus resultados de consulta.
	 * @date 15/01/2025
	 */
	public Map<Integer, SQLResult> validar16MotivosOriginalesAcreditacion(SQLUtil dbRdm, String[] motivosEsperados) {
	    Map<Integer, SQLResult> resultados = new HashMap<>();

	    try {
	        for (int id = 1; id <= 16; id++) {
	            String query = "SELECT unrc_desc FROM rdm100.xxfc_unrc_reason_codes WHERE unrc_code = '" + id + "'";
	            SQLResult rs = dbRdm.executeQuery(query);
	            resultados.put(id, rs);
	            if (!rs.isEmpty()) {
	                String motivoObtenido = rs.getData(0, 0);
	                String motivoEsperado = motivosEsperados[id - 1];
	                System.out.println("Validando ID " + id + ": " + motivoObtenido);
	                if (!motivoObtenido.equals(motivoEsperado)) {
	                    System.out.println("El motivo para ID " + id + " no coincide con el esperado.");
	                    return resultados;
	                }
	            } else {
	                System.out.println("ID " + id + " no encontrado en la tabla rdm100.xxfc_unrc_reason_codes.");
	                return resultados;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultados;
	}
	
	/**
	 * Valida cada uno de los ID de los 14 nuevos motivos de no acreditación.
	 * @author Alejandro.Montalvo
	 * @param dbRdm Instancia de SQLUtil para ejecutar consultas en la base de datos.
	 * @return Un mapa con los IDs y sus resultados de consulta.
	 * @date 15/01/2025
	 */
	public Map<Integer, SQLResult> validar14NuevosMotivosAcreditacion(SQLUtil dbRdm, String[] motivosEsperados) {
	    Map<Integer, SQLResult> resultados = new HashMap<>();

	    try {
	        for (int i = 0; i < motivosEsperados.length; i++) {
	            int id = 17 + i;
	            String query = "SELECT unrc_desc FROM rdm100.xxfc_unrc_reason_codes WHERE unrc_code = '" + id + "'";
	            SQLResult rs = dbRdm.executeQuery(query);
	            resultados.put(id, rs);
	            if (!rs.isEmpty()) {
	                String motivoObtenido = rs.getData(0, 0);
	                String motivoEsperado = motivosEsperados[i];
	                System.out.println("Validando ID " + id + ": " + motivoObtenido);
	                if (!motivoObtenido.equals(motivoEsperado)) {
	                    System.out.println("El motivo para ID " + id + " no coincide con el esperado.");
	                    return resultados;
	                }
	            } else {
	                System.out.println("ID " + id + " no encontrado en la tabla rdm100.xxfc_unrc_reason_codes.");
	                return resultados;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultados;
	}
}

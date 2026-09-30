/**
 *	Clase para automatizar el modulo de citas de RDM
 *
 * @author guerrero.vargas
 */

package RDM.util;

import java.util.ArrayList;
import java.util.Set;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Citas extends BaseRDM {

	public Citas(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
	}

	/**
	 * En el menu da click en Appointments > Appointment Schedule
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuAppointmentSchedule() throws FindFailed {
		wait(5);
		sk.screen.wait(imgPattern.get("appointments")).click();
		sk.screen.hover(imgPattern.get("appointmentSchedule"));
		screenshot();
		sk.screen.click();
			
		wait(2);
	}

	/**
	 * Llena los campos con la informacion necesaria para generar una cita
	 * 
	 * @param carrier Id del tranportista
	 * @param trailerId  Id del trailer
	 * @param door Identificador de la puerta
	 * @param type Tipo de cita
	 * @param duration	Duracion de la cita 
	 * @throws FindFailed al no encontrar alguna imagen especificada
	 * @return String con la fecha de la cita creada o null si ocurrio un error
	 * @author guerrero.vargas
	 */
	public String llenarCita(String fechaCita, String horaCita, String carrier, String trailerId, 
			String door, String type, String duration) throws FindFailed {
		ArrayList<String> infoCita = new ArrayList<>();
		//infoCita.add(fechaCita + " " + horaCita);
		infoCita.add(carrier);
		infoCita.add(trailerId);
		infoCita.add(door);
		infoCita.add(type);
		infoCita.add(duration);
				
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
			
			sk.screen.hover(imgPattern.get("save"));
			screenshot();
			sk.screen.click();
			
			wait(2);
			
			// En caso de que la cita se sobreponga a una existente, cancela su creacion
			// y regresa null
			if (sk.screen.exists(imgPattern.get("appointmentOverlap")) != null) {
				wait(1);
				screenshot();
				sk.screen.wait(imgPattern.get("appointmentOverlapNo")).click();
				
				return null;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return fechaCitaNueva;
	}
	
	/**
	 * En la ventana appointment schedule recorre la tabla donde se muestran las citas creadas y busca 
	 * si existe una con fechaCitaActual
	 *  
	 * @param fechaCitaActual Fecha de la cita que se busca
	 * @return String[] con el numero de cita y el status, null si no encuentra cita
	 * con fechaCitaNueva
	 * @author guerrero.vargas
	 */
	public String[] verificarCitaProgramada(String fechaCitaNueva) {
		
		// debe estar desactivado NUM_LOCK para poder hacer la combinacion SHIFT + END
		// en las celdas de la tabla de citas
		wait(5);
		numLockOff();
				
		// selecciona la fecha de la celda
		sk.screen.type(Keys.PAGE_DOWN);
		wait(1);
		screenshot();
		sk.screen.type(Keys.END, KeyModifier.SHIFT);
		
		String fechaCita = copyPaste(); // copia el valor seleccionado 
		System.out.println("fechaCita: " + fechaCita);
		
		// null si la fecha no es igual fechaCitaNueva (no se creo la cita)
		if (!fechaCita.equals(fechaCitaNueva)) {
			return null;
		}
		
		// si se creo la cita nueva busca y copia el numero de cita
		nTAB(2);
		wait(1);
		sk.screen.type(Keys.END, KeyModifier.SHIFT);
		String noCita = copyPaste(); 
		System.out.println("noCita: " + noCita);
		
		// y el status de la cita
		nTAB(7);
		wait(5);
		sk.screen.type(Keys.END, KeyModifier.SHIFT);		
		String status = copyPaste(); 
		System.out.println("status: " + status);
		
		screenshot();
		
		return new String[] {noCita, status};
	}
	
	/**
	 * Llena una cita que ya este programada
	 * 
	 * @param fechaCita 
	 * @param tipoCita
	 * @param carrier Id del Transportista
	 * @param trailerId Id del trailer
	 * @param proveedor Id del proveedor al que se le asigno la cita
	 * @author guerrero.vargas
	 */
	public void llenarCitaProgramada(String fechaCita, String tipoCita, String carrier, 
			String trailerId, String proveedor) {		
		try {
			screenshot();
			sk.screen.type(fechaCita);
			sk.screen.type(Keys.TAB);
			sk.screen.type(tipoCita);
			sk.screen.type(Keys.TAB);
			sk.screen.type(carrier);
			sk.screen.type(Keys.TAB);				
			sk.screen.type(trailerId);
			sk.screen.type(Keys.TAB);
			
			sk.screen.wait(imgPattern.get("checkProveedor")).click();
			wait(1);
			sk.screen.type(Keys.TAB, Keys.SHIFT);			
			sk.screen.type(proveedor);
			sk.screen.type(Keys.TAB);
			
			screenshot();
			
			sk.screen.wait(imgPattern.get("executeQuery")).click();
			
			wait(2);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Coloca la fecha en el campo "AppointmentDate"
	 * 
	 * @param fecha la coloca en el campo "Appointment Date"
	 * @return true si no aparecen mensajes de error al colocar la fecha, false de lo contrario  
	 * @author guerrero.vargas
	 */
	public boolean colocarFechaAppointmentDate(String fecha) {
		sk.screen.type(fecha);
		sk.screen.type(Keys.TAB);
		wait(5);
		
		if (sk.screen.exists(imgPattern.get("dateFormatError"), 0) != null ||
				sk.screen.exists(imgPattern.get("workingTimeError"), 0) != null) {
			screenshot();
			return false;
		}
		
		screenshot();		
		wait(1);
		
		return true;
	}
	
	/**
	 * Cierra ventanas de error como "Not Data Found" o "Critical"
	 * 
	 * @author guerrero.vargas
	 */
	public void closeCritical() {
		try {
			if (sk.screen.exists(imgPattern.get("notDataFound")) != null) {
				screenshot();
				sk.screen.wait(imgPattern.get("notDataFoundYes")).click();
				return;
			}
			
			if (sk.screen.exists(imgPattern.get("critical")) != null) {
				screenshot();
				sk.screen.wait(imgPattern.get("yes")).click();
				return;
			}
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Click en print y save para generar un reporte de citas
	 *
	 * @return String Url del pop up que se crea al generar el reporte de citas
	 * @author guerrero.vargas
	 */
	public String generarArchivo() {
		String currentUrl = null;
		
		try {
			wait(5);
			sk.screen.wait(imgPattern.get("print")).click();
			screenshot();
			wait(5);
			sk.screen.wait(imgPattern.get("saveReporte")).click();
			
			wait(240);
			
			// si se creo el pop up y la url contiene el String "appointment schedule" 
			// consideramos que el reporte se creo y devolvemos la url para validaciones
			boolean reporte = mostroReporte();			
			if (reporte) {
				return getDriver().getCurrentUrl();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return currentUrl; // devuelve null, el reporte fallo
	}
	
	/**
	 * En el menu da click en Appointments > XXFC Appointment Schedule
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuAppointmentxxfcAppointment() {
		try {
			sk.screen.wait(imgPattern.get("appointments")).click();
			sk.screen.hover(imgPattern.get("xxfcAppointmentSchedule"));
			screenshot();
			sk.screen.click();
			
			wait(2);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Coloca la informacion de la cita que se va reprogramar
	 * 
	 * @param proveedor numero de proveedor de la cita que se va a reprogramar
	 * @param fechaCita fecha de la cita que se va a programar
	 * @param noCita numero de cita que se va a programar
	 * @return string con el tiempo de descarga de la cita, null en caso de error
	 * @author guerrero.vargas
	 */
	public String reprogCitaInfoActual(String proveedor, String fechaCita, String noCita) {
		String tiempoDeDescarga = "";
		screenshot();
		
		try {
			sk.screen.wait(imgPattern.get("reprogCita")).click();
			wait(1);
			screenshot();
			
			sk.screen.type(proveedor);
			sk.screen.type(Keys.TAB);
			wait(1);
			
			// Si se muestra una imagen indicando que el proveedor colocado on existe regresa null
			if (sk.screen.exists(imgPattern.get("numeroProveedorNoExiste")) != null) {
				return null;
			}
			
			sk.screen.wait(imgPattern.get("fechaCita")).offset(130, 0).click();
			nBackspace(12);
			sk.screen.type(fechaCita);
			sk.screen.type(Keys.TAB);
			wait(1);
			
			sk.screen.type(noCita);
			sk.screen.type(Keys.TAB);
			wait(1);
			
			// si al colocar la cita no encuentra info regresa null
			if (sk.screen.exists(imgPattern.get("noDataFoundCitaReprog")) != null) {
				return null;
			}
			
			screenshot();
			
			sk.screen.wait(imgPattern.get("tiempoDeDescarga")).offset(100, 0).doubleClick();
			tiempoDeDescarga = copyPaste();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return tiempoDeDescarga;
	}
	
	/**
	 * Coloca la informacion para reprogramar una cita
	 * 
	 * @param fechaPropuesta Fecha a la que se quiere programar la cita
	 * @param motivo 
	 * @param ubicaciones
	 * @param muelle
	 * @return true si se logra reprogramar la cita, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean reprogCitaInfoPropuesta(String fechaPropuesta, String motivo, String ubicaciones,
			String muelle) {		
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("enterQuery")).click();
			sk.screen.wait(imgPattern.get("dosMenus")).click();
			sk.screen.wait(imgPattern.get("cancel")).click();
			wait(5);
			
			sk.screen.type(fechaPropuesta);
			sk.screen.type(Keys.TAB);
			
			if (sk.screen.exists(imgPattern.get("criticalFechasAnteriores")) != null) {
				return false;
			}			
			
			sk.screen.type(motivo);
			wait(1);
			screenshot();
			sk.screen.type(Keys.TAB);
						
			sk.screen.type(Keys.TAB, KeyModifier.SHIFT);
			
			sk.screen.type(ubicaciones);
			sk.screen.type(Keys.TAB);
			
			sk.screen.type(muelle);
			sk.screen.type(Keys.TAB);
			
			sk.screen.wait(imgPattern.get("executeQueryMuelle")).click();
			
			wait(1);
			
			screenshot();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Coloca un numero de cita para su cancelacion
	 * 
	 * @author guerrero.vargas
	 */
	public void cancelarCita(String noCita) {
		try {
			sk.screen.wait(imgPattern.get("reprogCita")).click();
			sk.screen.wait(imgPattern.get("citaText")).click();
			sk.screen.type(noCita);
			sk.screen.type(Keys.TAB);
			
			//sk.screen.wait(imgPattern.get("cancelarCita")).click(); FALTA IMAGEN

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Selecciona una cita fija a la que se reprogramara la cita a proveedor
	 * 
	 * @param horaCitaFija hora a la que se va a reprogramar
	 * @return true si encuentra cita fija con la hora proporcionada, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean seleccionarCitaFija(String horaCitaFija) {
		// debe estar desactivado NUM_LOCK para poder hacer la combinacion SHIFT + END
		// en las celdas de la tabla de citas
		numLockOff();
		
		String hora = ""; 
		String horaAnterior = "";
		
		// busca una cita con horaCitaFija, si no la encuentra regresa false
		while (true) {
			sk.screen.type(Keys.END, KeyModifier.SHIFT);
			wait(1);
			hora = copyPaste();

			if (hora.equals(horaCitaFija)) {
				nTAB(3);
				sk.screen.type(Keys.END, KeyModifier.SHIFT);
				String text = copyPaste();
				
				if (text.equals("Cita Fija")) {
					screenshot();
					return true;
					
				} else {
					nShiftTab(3);
				}
			}
			
			sk.screen.type(Keys.DOWN);
			if (hora.equals(horaAnterior)) {
				if (sk.screen.exists(imgPattern.get("lastRecord"), 0) != null) {
					return false;
				}
				
			} else {
				horaAnterior = hora;
			}
			
		}
		
	}
	
	/**
	 * Reprograma la cita a proveedor a una cita fija
	 * 
	 * @return true si se reprogramo con exito, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean reprogramarCita() {		
		try {
			sk.screen.wait(imgPattern.get("citaFija")).doubleClick();
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("programarCitaSave")).click();
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("reprogramarCitaYes")).click();
			
			boolean programo = sk.screen.exists(imgPattern.get("reprogramoExito")) != null;
			
			sk.screen.wait(imgPattern.get("exitoOk")).click();
			
			return programo;
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public String reprogramarMasCita() {
		String horaFin = null;
		
		try {
		/*	sk.screen.wait(imgPattern.get("citaFija")).doubleClick();
			wait(1);
			screenshot();
			
			if (Key.isLockOn(Key.C_NUM_LOCK)) {
				sk.screen.type(Keys.NUM_LOCK);
			}
			
			nTAB(3);
			
			sk.screen.type(Keys.END, KeyModifier.SHIFT);
			
			horaFin = copyPaste();
			
			sk.screen.wait(imgPattern.get("programarMasCitaSave")).click();
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("reprogramarCitaYes")).click();
		
			sk.screen.wait(imgPattern.get("exitoOk")).click();
		
			screenshot();
			*/
			//sk.screen.wait(imgPattern.get("programarMasCitaExitCancel")).click();
			//screenshot();
			
			sk.screen.wait(imgPattern.get("yesCritical")).click();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return horaFin;
	}

	/**
	 * En el menu da click en Appointments > Appointments Detail
	 * 
	 * @author guerrero.vargas
	 */
	public void clickMenuAppointmentsAppointmentDetail() {
		try {
			wait(10);
			sk.screen.wait(imgPattern.get("appointments")).click();
			wait(5);
			sk.screen.hover(imgPattern.get("appointmentDetail"));
			screenshot();
			sk.screen.click();
			
			wait(2);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Agrega la orden de compra a una cita
	 * 
	 * @param noCita el numero de cita al que se le va a agregar la orden
	 * @param noOrden orden que se va a agregar a la cita
	 * @param bulk valor que se colocara en el campo bulk al agregar la orden
	 * @author guerrero.vargas
	 */
	public void agregarOrdenDeCompra(String noCita, String noOrden, String bulk) throws FindFailed {
		wait(3);
		screenshot();
			
		sk.screen.type(noCita);
		screenshot();
		sk.screen.type(Keys.TAB);
		wait(10);
		screenshot();
		closeCritical();
			
		sk.screen.wait(imgPattern.get("addByPo")).click();
		wait(5);
		screenshot();
			
		sk.screen.type(noOrden);
		sk.screen.type(Keys.TAB);
		sk.screen.type(bulk);			
		screenshot();
			
		sk.screen.wait(imgPattern.get("savePo")).click(); 
		
		wait(5);
		
	}
	
	/**
	 * Coloca el numero de cita para abrir su detalle
	 * 
	 * @param noCita 
	 * @author guerrero.vargas
	 */
	public void abrirAppointmentDetail(String noCita) {
		wait(3);
		screenshot();
		
		sk.screen.type(noCita);
		screenshot();
		sk.screen.type(Keys.TAB);
		wait(1);
		screenshot();
		closeCritical();
	}
	
	/**
	 * Agrega una orden de compra a una cita
	 * 
	 * @return true si agrega la orden de compra con exito, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean agregarOrdenesDeCompra(String noOrden, String bulk) {
		try {
			sk.screen.wait(imgPattern.get("addByPo")).click();
			wait(3);
			screenshot();
			
			sk.screen.type(noOrden);
			sk.screen.type(Keys.TAB);
			sk.screen.type(bulk);			
			screenshot();
			
			sk.screen.wait(imgPattern.get("savePo")).click(); 
			
			wait(10);
			
			screenshot();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Valida que una cita se encuentre en status OPEN en la ventana Appointment Detail
	 * 
	 * @param cita Numero de cita que se va a validar
	 * @return
	 */	
	public boolean validarStatusOpen(String cita) {
		sk.screen.type(cita);
		sk.screen.type(Keys.TAB);
		wait(1);
		
		screenshot();
		
		return sk.screen.exists(imgPattern.get("statusOpen"), 0) != null;
	}
	
	/**
	 * Recorre la tabla de la pantalla appointment detail y busca la orden de compra agregada
	 *
	 * @param noOrdenEsperada numero de orden de compra que se quiere validar
	 * @return true si encuentra la orden de compra, false de lo contrario
	 * @author guerrero.vargas  
	 */
	public boolean validarOrdenDeCompraAgregada(String noOrdenEsperada) {
		numLockOff();
				
		wait(1);
		screenshot();
				
		String po = null;
		while (true) {
			wait(1);
			sk.screen.type(Keys.END, KeyModifier.SHIFT);
			po = copyPaste();
			
			if (po.equals("")) {
				return false;
			}
			
			if (po.equals(noOrdenEsperada)) {
				screenshot();
				return true;
			}
			
			sk.screen.type(Keys.DOWN);	
		}
	}
	
	/**
	 * Recorre la tabla de la pantalla appointment detail y busca las ordenes de compra agregadas
	 * 
	 * @param noOrdenes arreglo de numeros de ordenes de compra a validar
	 * @return true si las ordenes son encontradas, false de lo contrario
	 * @author guerrero.vargas 
	 */
	public boolean validarOrdenesDeCompraAgregadas(String[] noOrdenes) {
		Pattern lastRecord = imgPattern.get("lastRecord");
		
		numLockOff();
		
		wait(1);
		screenshot();
				
		boolean[] ordenesEncontradas = new boolean[noOrdenes.length];
		
		String po = null;
		while (true) {
			sk.screen.type(Keys.END);
			sk.screen.type(Keys.HOME, KeyModifier.SHIFT);
			
			po = copyPaste();
			
			if (po.equals("")) {
				break;
			}
			
			for (int i = 0; i < noOrdenes.length; i++) {
				if (po.equals(noOrdenes[i])) {
					//screenshot();
					ordenesEncontradas[i] = true;
				}
			}
			
			sk.screen.type(Keys.DOWN);
			
			if(sk.screen.exists(lastRecord, 0) != null) {
				break;
			}
						
		}
		
		for (int i = 0; i < ordenesEncontradas.length; i++) {
			if (!ordenesEncontradas[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Selecciona el tiempo de descarga de la orden de compra igual a noOrden
	 * 
	 * @param noOrden Numero de orden que se quiere seleccionar
	 * @return String con tiempo de descarga, null de lo contrario
	 * @author guerrero.vargas
	 */
	public String seleccionarOrdenDeCompra(String noOrden) {
		String tiempoDescarga = null;
		screenshot();
		
		try {
			sk.screen.findText(noOrden).doubleClick();
			wait(1);
			sk.screen.type(Keys.TAB, Keys.SHIFT);
			wait(1);
			sk.screen.type(Keys.SPACE);
			wait(1);
			
			screenshot();
			
			nTAB(7);
			
			tiempoDescarga = copyPaste();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return tiempoDescarga;
	}
	
	/**
	 * Coloca la ubicacion y el muelle en los que se encuentra la cita fija creada
	 * 
	 * @param ubicacion Id de la ubicacion
	 * @param muelle Id del muelle
	 * @author guerrero.vargas
	 */
	public void llenarUbicacionMuelleCitaFija(String ubicacion, String muelle) {
		try {
			sk.screen.wait(imgPattern.get("ubicacion")).offset(100, 0).doubleClick();
			Mouse.at().click();
			sk.screen.type(ubicacion);
			
			sk.screen.type(Keys.TAB);
			sk.screen.type(muelle);
			
			screenshot();
			
			sk.screen.type(Keys.TAB);
			
			wait(1);
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Maneja el cambio de ventana al pop up que se crea cuando generas un reporte por citas
	 * 
	 * @return true si la url del pop up contiene el String "appointment_schedule", de lo contrario false
	 * @author guerrero.vargas
	 */
	private boolean mostroReporte() {
		Set<String> handles = getDriver().getWindowHandles(); // obtiene las ventanas	  
		
		handles.remove(handles.iterator().next()); // elimina la ventana principal
		
		// cambia al pop up (lastHandle = ultima ventana creada)
		String lastHandle = handles.iterator().next(); 
		getDriver().switchTo().window(lastHandle);
    
   		getDriver().manage().window().maximize(); // maximiza
   		wait(5);
   		
   		// si la url no contiene el String "appointment_schedule" consideramos
   		// que la creacion del reporte fallo
   		if (!getDriver().getCurrentUrl().contains("appointment_schedule")) {
   			return false;
   		}
   		
   		screenshot();
   		
   		return true;
	}
	
	/**
	 * Borra la fecha default de un campo
	 * 
	 * @author guerrero.vargas
	 */
	public void borrarFecha() {
		nBackspace(15);
	}
	
}

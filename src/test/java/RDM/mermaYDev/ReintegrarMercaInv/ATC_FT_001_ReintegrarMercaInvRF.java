package RDM.mermaYDev.ReintegrarMercaInv;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import RDM.util.RadioFrecuencia;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ReintegrarMercaInvRF extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ReintegrarMercaInvRF_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String host = data.get("host");
		String puerto = data.get("puerto");
		String usuarioTera = data.get("usuarioTera");
		String passTera = data.get("passTera");
		String usuarioRf = data.get("usuarioRf");
		String passRf = data.get("passRf");
		String cita = data.get("cita");
		String door = data.get("door");
		String trailerId = data.get("trailerId");
		String seal = data.get("seal");
		String fecha = data.get("fecha");
		String contenedor = data.get("container");
		
		/*
		 * UTILERIAS
		 */	
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RadioFrecuencia rf = new RadioFrecuencia(testCase);
		Citas c = new Citas(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM (RF). "
				+ "Presionar el boton Login ");
		System.out.println("PASO 1"); 

		rf.abrir(host, puerto, usuarioTera, passTera, usuarioRf, passRf);		
		
		// PASO 2
		addStep("Seleccionar del submenu la opcion INVENT MGMT "
				+ "Ingresar a la opcion MANUAL REPLEN ");
		System.out.println("PASO 2");
		
		rf.seleccionarInventMgmtManualReplen();
		
		// PASO 3
		addStep("Escanear en el campo CONTAINER ID el numero de contenedor a reingresar "
				+ "al inventario. "
				+ "Seleccionar articulo y presionar F4. "
				+ "Seleccionar el pivote y presionar F4. ");
		System.out.println("PASO 3");
		
		rf.ingresarContenedorReintegro(contenedor);
		
		/*
		// PASO 4
		addStep("Validar que la cita haya cambiado de estatus en la pantalla Appointment Schedule");
		System.out.println("PASO 4");
		
		c.logIn();
		c.clickMenuAppointmentsAppointmentDetail();
		
		boolean statusOpen = c.validarStatusOpen(cita);
		
		assertTrue(statusOpen, "La cita: " + cita + " no existe o no tiene status OPEN");
		
		u.close();*/
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "Tener una cita activa. Agregar una orden de compra. Imprimir etiquetas.";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se validara la apertura de una cita por medio de la Radio Frecuencia, "
				+ "cambiando su estatus a Open";
	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ReintegrarMercaInvRF_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

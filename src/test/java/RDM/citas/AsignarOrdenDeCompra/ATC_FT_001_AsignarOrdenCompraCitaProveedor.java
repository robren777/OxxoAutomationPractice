package RDM.citas.AsignarOrdenDeCompra;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_AsignarOrdenCompraCitaProveedor extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AsignarOrdenCompraCitaProveedor_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String noOrden = data.get("noOrden");
		String bulk = data.get("bulk");
		
		/*
		 * UTILERIAS
		 */	
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Citas c = new Citas(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM. "
				+ "Presionar el boton \"Login\"");
		System.out.println("PASO 1"); 
		
		c.logIn();		
		
		// PASO 2
		addStep("Dar click en la opcion \"Appointments\" del menu principal. "
				+ "Dar click en la opcion \"Appointment Detail\"");
		System.out.println("PASO 2");
		
		c.clickMenuAppointmentsAppointmentDetail();
				
		// PASO 3
		addStep("Teclear el no. de cita programada en el campo APPT NBR y presionar enter. "
				+ "Presionar el boton \"YES\". "
				+ "Presionar el boton \"Add By PO\" para agregar la orden de compra. "
				+ "Ingresar la orden de compra a ligar en el campo \"PO\"."
				+ "Ingresar en el campo \"Bulk\" "
				+ "la letra: Y, para que se imprima una etiqueta por contenedor (tarima). "
				+ "Dar click en el boton \"Save\".");
		System.out.println("PASO 3");
		
		c.agregarOrdenDeCompra(noCita, noOrden, bulk);
		
		// PASO 4
		addStep("Verificar que en la pantalla \"Appointment Detail\" "
				+ "se despliegue la informacion de la orden de compra ligada con la cita.");
		System.out.println("PASO 4");
	
		boolean mismoPo = c.validarOrdenDeCompraAgregada(noOrden);
				
		assertTrue(mismoPo, "No se agrego la orden de compra: " + noOrden);
		
		u.close();		
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "1) Tener una cita vigente con status SCHD"
				+ "2) Tener orden de compra actual para agregar a la cita";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe asignar una orden de compra "
				+ "a una cita de Proveedor";	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_AsignarOrdenCompraCitaProveedor_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

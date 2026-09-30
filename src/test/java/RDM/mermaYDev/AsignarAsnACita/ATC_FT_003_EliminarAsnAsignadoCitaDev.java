package RDM.mermaYDev.AsignarAsnACita;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_EliminarAsnAsignadoCitaDev extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_EliminarAsnAsignadoCitaDev_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String asn = data.get("asn");
		
		/*
		 * UTILERIAS
		 */	
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM. "
				+ "Presionar el boton \"Login\"");
		System.out.println("PASO 1"); 
		
		m.logIn();		
		
		// PASO 2
		addStep("Dar click en la opcion \"Appointments\" del menu principal."
				+ "Dar click en la opcion \"NSC Appointment Detail\"");
		System.out.println("PASO 2");
		
		m.clickMenuAppointmentNscAppointmentDetail();
		
		// PASO 3
		addStep("Teclear el no. de cita programada en el campo APPT NBR y presionar enter.");
		System.out.println("PASO 3");
		
		m.colocarNumeroCitaEliminar(noCita);
		
		// PASO 4
		addStep("Posicionarse en la linea y seleccionar el ASN que se requiere eliminar. "
				+ "Presionar el boton \"Delete Record\" para eliminar el registro. "
				+ "Presionar el boton \"YES\" para confirmar que se requiere eliminar el ASN.");
		System.out.println("PASO 4");
		
		boolean encontrado = m.buscarAsn(asn);
		
		assertTrue(encontrado, "El ASN: " + asn + " no fue encontrado");
		
		boolean seElimino = m.eliminarAsn();
		
		assertTrue(seElimino, "El ASN: " + asn + " no se elimino");
		
		// PASO 5
		addStep("Verificar en la pantalla\"NSC Appointment Detail\" que el ASN se elimino.");
		System.out.println("PASO 5");
		
		boolean validarEliminacion = m.validarEliminacionAsn(asn);
		
		assertTrue(validarEliminacion, "El ASN: " + asn + " no se elimino correctamente");
		
		u.close();
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "1) Tener cita de devoluciones activa "
				+ "2) Tener ASN agregados a la cita ";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe asignar una "
				+ "Notificacion Avanzada de Embarque (ASN) a una cita de recibo devoluciones "
				+ "utilizando la opcion: Store";	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_EliminarAsnAsignadoCitaDev_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

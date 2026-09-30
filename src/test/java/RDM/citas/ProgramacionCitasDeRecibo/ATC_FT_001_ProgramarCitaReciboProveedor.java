package RDM.citas.ProgramacionCitasDeRecibo;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ProgramarCitaReciboProveedor extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ProgramarCitaReciboProveedor_Test(HashMap<String, String> data) throws Exception {
		
		/*
		 * VARIABLES
		 */
		String fechaCita = data.get("fechaCita");
		String horaCita = data.get("horaCita");
		String carrier = data.get("carrier");
		String trailerId = data.get("trailerId");
		String door = data.get("door");
		String type = data.get("type");
		String duration = data.get("duration");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Citas c = new Citas(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
	
		c.logIn();
		
		// PASO 2
		addStep("Dar click en la opcion \"Appointments\" del menu principal. "
				+ "Dar click en la opcion \"Appointment Schedule\"");
		System.out.println("PASO 2");
		
		c.clickMenuAppointmentSchedule();
		
		// PASO 3
		addStep("Seleccionar el boton de lista de valores del campo \"Appointment date\". "
				+ "Seleccionar la fecha requerida.");
		System.out.println("PASO 3");	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String fecha = sdf.format(new Date()).toUpperCase();
		
		boolean fechaCorrecta = c.colocarFechaAppointmentDate(fecha);
		u.hardWait(3);
		c.closeCritical();
		
		assertTrue(fechaCorrecta, "La fecha proporcionada no es valida");
		
				
		// PASO 4
		addStep("Presionar el boton \"Create Record\". "
				+ "Ingresar la informacion requerida en los siguientes campos: "
				+ "-Carrier, Trailer id, Door, Type: L, Duration "
				+ "NOTA: los muelles MR01, MR02� son muelles de recibo proveedor. "
				+ "El tipo de carga que se utiliza actualmente es Live (L)");
		System.out.println("PASO 4");

		String fechaCitaNueva = c.llenarCita(fechaCita, horaCita, carrier, trailerId, door, type, duration);
				
		assertNotNull(fechaCitaNueva, "Error al crear la cita (quiza hay overlap con otra)");
		
		// PASO 5
		addStep("Verificar que en la pantalla \"Appointment Schedule\" se muestre la cita que se "
				+ "programo con la informacion correcta y se encuentre con el estatus \"SCHD\".");
		System.out.println("PASO 5");
				
		String[] citaCreada = c.verificarCitaProgramada(fechaCitaNueva);
		
		assertNotNull(citaCreada, "Hubo un error en la creacion de la cita.");
		
		String noCita = citaCreada[0];
		String status = citaCreada[1];
		
		System.out.println("-"+status+"-SCHD-");
		boolean statusValido = true;
		if (!status.equals("SCHD")) {
			statusValido = false;
		}
		
		assertTrue(statusValido, "La CITA: " + noCita + " no tiene STATUS = SCHD");
		
		testCase.addBoldTextEvidenceCurrentStep("La CITA: " + noCita + " se creo con STATUS: " + status);
		
		u.close();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {		
		return "1) Colocar la fecha de la cita. "
				+ "2) La fecha debe estar activa en la tabla WORKING_DAYS";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe programar una cita para "
				+ "recibo proveedor (NON ASN-NON NSC).";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ProgramarCitaReciboProveedor_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

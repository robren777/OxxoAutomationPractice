package RDM.mermaYDev.ProgramacionCitasDev;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_ProgramarCitaReciboDevolucion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ProgramarCitaReciboDevolucion_Test(HashMap<String, String> data) throws Exception {

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
		String asn = data.get("asn");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
			
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("Dar click en la opcion \"Appointments\" del menu principal. "
				+ "Dar click en la opcion \"Appointment Schedule\"");
		System.out.println("PASO 2");
		
		m.clickMenuAppointmentSchedule();
		
		// PASO 3
		addStep("Seleccionar el boton de lista de valores del campo \"Appointment date\". "
				+ "Seleccionar la fecha requerida.");
		System.out.println("PASO 3");
		
		boolean fechaCorrecta = m.colocarFechaAppointmentDate(fechaCita);
		m.closeCritical();
		
		assertTrue(fechaCorrecta, "La fecha proporcionada no es valida");
				
		// PASO 4
		addStep("Presionar el boton \"Create Record\". "
				+ "Presionar el boton \"Lista de valores\" del campo \"Carrier\"."
				+ "Seleccionar el transporte requerido y presionar \"OK\"."
				+ "Presionar el boton \"Lista de valores\" del campo \"Trailer\"."
				+ "Seleccionar el camion requerido y presionar \"OK\"."
				+ "Presionar el boton \"Lista de valores\" del campo \"Door\"."
				+ "\"Seleccionar el muelle requerido y presionar \"\"OK\"\"."
				+ "NOTA: Los muelles RD01, RD02� son muelles de recibo Devoluciones.\""
				+ "Presionar el boton \"Lista de valores\" del campo \"Type\"."
				+ "Seleccionar la opcion: L (Load type) y presionar \"OK\"."
				+ "Capturar el tiempo estimado que durara la cita en el campo \"Duration\"."
				+ "En el campo \"ASN\" capturar la letra: \"Y\", ya que para esta cita se requiere una Notificacion de embarque."
				+ "Seleccionar la opcion \"NSC\""
				+ "Presionar el boton \"Save\".");
		System.out.println("PASO 4");

		String fechaCitaNueva = m.llenarCitaDevolucion(fechaCita, horaCita, carrier, trailerId, door, type, duration, asn);
		
		assertNotNull(fechaCitaNueva, "Error al crear la cita (quiza hay overlap con otra)");
		
		// PASO 5
		addStep("Verificar que en la pantalla \"Appointment Schedule\" se muestre la cita que se "
				+ "programo.");
		System.out.println("PASO 5");
		
		String[] citaCreada = m.verificarCitaProgramada(fechaCitaNueva);
		
		assertNotNull(citaCreada, "Hubo un error en la creacion de la cita.");
		
		String noCita = citaCreada[0];
		String status = citaCreada[1];

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
		return "Construido. Mediante el sistema RDM se debe programar una cita de recibo "
				+ "Devolucion (ASN-NSC).";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ProgramarCitaReciboDevolucion_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

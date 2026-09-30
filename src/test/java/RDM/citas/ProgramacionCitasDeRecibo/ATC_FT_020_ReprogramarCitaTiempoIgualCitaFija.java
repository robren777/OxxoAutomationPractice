package RDM.citas.ProgramacionCitasDeRecibo;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_020_ReprogramarCitaTiempoIgualCitaFija extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_ReprogCitaTiempoIgualCitaFija_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String proveedor = data.get("proveedor");
		String fechaCita = data.get("fechaCita");
		String noCita = data.get("noCita");
		String fechaPropuesta = data.get("fechaPropuesta");
		String motivo = data.get("motivo");
		String ubicaciones = data.get("ubicaciones");
		String muelle = data.get("muelle");
		String horaCitaFija = data.get("horaCitaFija");
	
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
		addStep("Acceda a la ruta: /Appointments/XXFC-Appointments Schedule/");
		System.out.println("PASO 2");
		
		c.clickMenuAppointmentxxfcAppointment();
		
		// PASO 3
		addStep("Dar clic en el boton REPROG. CITA. "
				+ "Dar clic en la lista de valores Proveedor. "
				+ "Seleccionar el proveedor deseado y dar clic en el boton OK. "
				+ "Dar clic en la lista de valores del filtro fecha cita. "
				+ "Seleccionar una fecha en el calendario y dar clic en el boton de OK. "
				+ "Dar clic en la lista de valores del filtro cita. "
				+ "Seleccionar una cita con tiempo de descarga igual a la "
				+ "cita fija y dar clic en el boton de OK.");
		System.out.println("PASO 3");
		
		String tiempoDescarga = c.reprogCitaInfoActual(proveedor, fechaCita, noCita);
		c.closeCritical();
		
		assertNotNull(tiempoDescarga, "Hubo un problema al colocar la info de la cita");
		
		// PASO 4
		addStep("En la seccion PROG. PROPUESTA dar clic en la lista de valores del filtro fecha cita. "
				+ "Seleccionar la nueva fecha de cita en el calendario y dar clic en el boton de OK. "
				+ "Dar clic en la lista de valores del filtro motivo. "
				+ "Seleccionar un motivo y dar clic en el boton de OK. "
				+ "Dar clic en el boton Execute Query. ");
		System.out.println("PASO 4");

		boolean infoReprog = c.reprogCitaInfoPropuesta(fechaPropuesta, motivo, ubicaciones, muelle);
		
		assertTrue(infoReprog, "No se coloco la informacion de reprogramacion");
		
		// PASO 5
		addStep("El usuario selecciona el horario que se encuentre disponible para citas fijas "
				+ "(marcados con rojo) y da doble clic. "
				+ "Dar clic en el boton de save. "
				+ "El usuario da clic en el boton YES.");
		System.out.println("PASO 5");
		
		boolean citaFija = c.seleccionarCitaFija(horaCitaFija);
		
		assertTrue(citaFija, "No se encontro una cita fija disponible a las " + horaCitaFija 
				+ " para el proveedor: " + proveedor);
		
		boolean reprogramarCita = c.reprogramarCita();
		
		assertTrue(reprogramarCita, "No se reprogramo la cita: " + noCita);
		
		u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1) Tener una cita vigente "
				+ "2) Agregarle una orden de compra a la cita "
				+ "3) Tener una cita fija para el proveedor";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se valida que cuando se reprograme una cita, "
				+ "cuyo tiempo de descarga es igual al de la cita fija, "
				+ "deberia de permitir reprogramar dicha cita. ";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_ReprogCitaTiempoIgualCitaFija_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

package RDM.citas.ProgramacionCitasDeRecibo;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_022_ProgramarDosCitasHorariosConsecuentes extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_ProgramarDosCitasHorariosConsecuentes_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fechaCita = data.get("fechaCita");
		String tipoCita = data.get("tipoCita");
		String carrier = data.get("carrier");
		String trailerId = data.get("trailerId");
		String proveedor = data.get("proveedor");
	
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Citas c = new Citas(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
			
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		c.logIn();
		
		// PASO 2
		addStep("Acceda a la ruta: /Appointments/XXFC-Appointments Schedule/");
		System.out.println("PASO 2");
		
		c.clickMenuAppointmentxxfcAppointment();
		
		// PASO 3
		addStep("Dar clic en el icono Enter Query (lampara). "
				+ "Dar clic en la lista de valores del filtro fecha. "
				+ "Seleccionar una fecha en el calendario y dar clic en el boton de OK. "
				+ "Seleccionar el tipo de cita deseado y dar clic en el boton OK. "
				+ "Dar clic en la lista de valores tipo de cita. "
				+ "El campo Carrier y el boton de lista de valores aparecen deshabilitados. "
				+ "Dar clic en la lista de valores trailer. "
				+ "Seleccionar el trailer deseado y dar clic en el boton OK. "
				+ "Seleccionar Checkbox del campo Proveedor "
				+ "Seleccionar el proveedor deseado y dar clic en el boton OK. "
				+ "Dar clic en el boton Execute Query. ");
		System.out.println("PASO 3");
		
		c.llenarCitaProgramada(fechaCita, tipoCita, carrier, trailerId, proveedor);
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se valida que cuando se tenga una cita fija con un horario "
				+ "en donde solo quepa una cita, el sistema permita agendar otra cita "
				+ "en el horario siguiente ";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_ProgramarDosCitasHorariosConsecuentes_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

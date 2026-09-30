package RDM.citas.GeneracionReporteCitas;

import static org.testng.Assert.assertNotNull;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_GeneracionReporteProgramacionCitas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_GeneracionReporteProgramacionCita_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fecha = data.get("fechaReporte");

		/*
		 * UTILERIAS
		 */
		/*SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);*/

		SeleniumUtil u = new SeleniumUtil(new IETest());
		Citas c = new Citas(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		//System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		c.logIn();
		
		// PASO 2
		addStep("Dar click en la opcion \"Appointments\" del menu principal. "
				+ "Dar click en la opcion \"Appointment Schedule\"");
		System.out.println("PASO 2");
		
		c.clickMenuAppointmentSchedule();
		
		
		// PASO 3
		addStep("Seleccionar la fecha en que se programo la(s) cita(s)");
		System.out.println("PASO 3");
		
		c.colocarFechaAppointmentDate(fecha);
		
		// PASO 4
		addStep("Presionar el boton \"Print\". Elegir la pestana \"Destination\". "
				+ "Dar click en el boton \"Save\" para guardar la informacion.");
		System.out.println("PASO 4");
		
		String urlPopUp = c.generarArchivo();
		assertNotNull(urlPopUp, "No se mostro el reporte de programacion de citas");
		
		u.close();				
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Permitir ventanas emergentes en Internet Explorer";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe generar el reporte de programacion de citas.";

	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_GeneracionReporteProgramacionCita_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

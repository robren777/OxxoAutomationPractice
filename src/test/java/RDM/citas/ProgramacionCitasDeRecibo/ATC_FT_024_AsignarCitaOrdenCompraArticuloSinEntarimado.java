package RDM.citas.ProgramacionCitasDeRecibo;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_024_AsignarCitaOrdenCompraArticuloSinEntarimado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_024_AsignarCitaOrdenCompraArticuloSinEntarimado_Test(HashMap<String, String> data) throws Exception {

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
		addStep("Se capturar los campos siguientes: "
				+ "Fecha Cita, Tipo cita, Carrier, Trailer y proveedor y se da clic en execute");
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
		return "Construido. Validar que se considere el tiempo de descarga recalculado "
				+ "al momento de editar el detalle de articulos en la Orden de Compra "
				+ "al seleccionar un solo articulo para incluirse en la cita a generar "
				+ "sin seleccionar la opcion de Entarimado ";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_024_AsignarCitaOrdenCompraArticuloSinEntarimado_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

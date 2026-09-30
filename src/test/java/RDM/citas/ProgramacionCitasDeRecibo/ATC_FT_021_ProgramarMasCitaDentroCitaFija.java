package RDM.citas.ProgramacionCitasDeRecibo;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_021_ProgramarMasCitaDentroCitaFija extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ProgramarMasCitaDentroCitaFija_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fechaCita = data.get("fechaCita");
		String tipoCita = data.get("tipoCita");
		String carrier = data.get("carrier");
		String trailerId = data.get("trailerId");
		String proveedor = data.get("proveedor");
		String noOrden = data.get("noOrden");
		String noOrden2 = data.get("noOrden2");
		String ubicacion = data.get("ubicacion");
		String muelle = data.get("muelle");
		String horaCitaFija = data.get("horaCitaFija");
		String horaCitaFija2 = data.get("horaCitaFija2");
			
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
		
		// PASO 4
		addStep("El usuario da clic en el checkbox de la(s) orden(es) de compra que desea programar "
				+ "la cita. (Validar que el tiempo de descarga sea menor al tiempo de la Cita Fija) ");
		System.out.println("PASO 4");		
		
		String tiempo = c.seleccionarOrdenDeCompra(noOrden);
		
		assertNotNull(tiempo, "No se encontro la orden de compra " + noOrden);
		
		// PASO 5
		addStep("Dar clic en la lista de valores Ubicacion. "
				+ "Seleccionar la ubicacion deseada y dar clic en el boton OK."
				+ "Dar clic en la lista de valores Muelle."
				+ "Seleccionar el muelle deseado y dar clic en el boton OK. "
				+ "Dar clic en el boton Execute Query que est al lado del campo Ubicacion"
				+ "Seleccionar la ubicacion deseada y dar clic en el boton OK. "
				+ "Dar clic en la lista de valores Muelle. "
				+ "Seleccionar el muelle deseado y dar clic en el boton OK. "
				+ "Dar clic en el boton Execute Query que est al lado del campo Ubicacion. ");
		System.out.println("PASO 5");
		
		c.llenarUbicacionMuelleCitaFija(ubicacion, muelle);
		
		
		// PASO 6
		addStep("El usuario selecciona el horario que se encuentre disponible para citas fijas "
				+ "(marcados con rojo) y da doble clic"
				+ "Dar clic en el boton de save. "
				+ "El usuario da clic en el boton YES. "
				+ "El usuario presiona el boton CANCEL."); 
		System.out.println("PASO 6");
		
		boolean citaEncontrada = c.seleccionarCitaFija(horaCitaFija);
		
		assertTrue(citaEncontrada, "No se encontro cita fija en horario: " + horaCitaFija);
		
		// PASO 7
		addStep("El usuario selecciona el horario que se encuentre disponible para citas fijas "
				+ "(marcados con rojo) y da doble clic. "
				+ "Dar clic en el boton de save. "
				+ "El usuario da clic en el boton YES. "
				+ "El usuario presiona el boton CANCEL.");
		System.out.println("PASO 7");
		
		String horaFin = c.reprogramarMasCita();
			
		assertNotNull(horaFin, "No se asigno la PO " + noOrden + " a la cita fija."); 
		
		// PASO 8

		addStep("Agendar una nueva Cita, Seleccionar una fecha en el calendario y dar clic en el boton de OK. "
				+ "Seleccionar el tipo de cita deseado y dar clic en el boton OK. "
				+ "El campo Carrier y el boton de lista de valores aparecen deshabilitados. "
				+ "Dar clic en la lista de valores triler. "
				+ "Seleccionar el triler deseado y dar clic en el boton OK. "
				+ "Seleccionar Checkbox del campo Proveedor "
				+ "Seleccionar el proveedor deseado y dar clic en el boton OK. "
				+ "Dar clic en el boton Execute Query. ");
		System.out.println("PASO 8");
		
		c.borrarFecha();
		
		c.llenarCitaProgramada(fechaCita, tipoCita, carrier, trailerId, proveedor);
		
		// PASO 9
		addStep("El usuario da clic en el checkbox de la(s) orden(es) de compra que desea programar "
				+ "la cita. (Validar que el tiempo de descarga sea menor al tiempo restante de la "
				+ "Cita Fija");
		System.out.println("PASO 9");
		
		tiempo = c.seleccionarOrdenDeCompra(noOrden2);
		
		assertNotNull(tiempo, "No se encontro la orden de compra " + noOrden2);
		
		// PASO 10
		addStep("Dar clic en la lista de valores Ubicacion. "
				+ "Seleccionar la ubicacion deseada y dar clic en el boton OK. "
				+ "Dar clic en la lista de valores Muelle. "
				+ "Seleccionar el muelle en donde se programo la cita anterior y dar clic en el boton OK. "
				+ "Dar clic en el boton Execute Query que est al lado del campo Ubicacion.");
		System.out.println("PASO 10");
		
		c.llenarUbicacionMuelleCitaFija(ubicacion, muelle);
		
		// PASO 11
		addStep("El usuario selecciona el horario restante disponible de la cita fija (marcados con rojo) y da doble clic. "
				+ "Dar clic en el boton de save. "
				+ "El usuario da clic en el boton YES. "
				+ "El usuario presiona el boton CANCEL. ");
		System.out.println("PASO 11");
		
		citaEncontrada = c.seleccionarCitaFija(horaCitaFija2);
		
		assertTrue(citaEncontrada, "No se encontro cita fija en horario: " + horaCitaFija2);
		
		// PASO 12
		addStep("Validar que el sistema muestre los horarios restantes de la cita fija");
		System.out.println("PASO 12");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Tener cita fija activa. "
				+ "Tener 2 ordenes de compra activas para proveedor "
				+ "(mismo proveedor que la cita fija) sin asignar";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se valida que se permita programar mas de una cita en el espacio "
				+ "de la cita fija y se muestre los horarios restantes de la cita fija ";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_021_ProgramarMasCitaDentroCitaFija_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

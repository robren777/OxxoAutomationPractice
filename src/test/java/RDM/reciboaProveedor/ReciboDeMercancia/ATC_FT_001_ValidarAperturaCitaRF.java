package RDM.reciboaProveedor.ReciboDeMercancia;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Citas;
import RDM.util.RadioFrecuencia;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarAperturaCitaRF extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarAperturaCitaRF_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String host = data.get("host");
		String puerto = data.get("puerto");
		String usuarioTera = GlobalVariables.USER_TERA;
		String passTera = PasswordUtil.decryptPassword(GlobalVariables.PASS_TERA);
		String usuarioRf = GlobalVariables.USER_RF;
		String passRf = PasswordUtil.decryptPassword(GlobalVariables.PASS_RF);
		String cita = data.get("cita");
		String door = data.get("door");
		String trailerId = data.get("trailerId");
		String seal = data.get("seal");
		
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
		addStep("Ingresar a Radio Frecuencia (TeraTerm) y conectar.");
		System.out.println("PASO 1"); 

		rf.abrir(host, puerto, usuarioTera, passTera, usuarioRf, passRf);		
		
		// PASO 2
		addStep("Dar clic en la opcion RECEIVING presionar Enter y "
				+ "nuevamente el bot�n enter en la opci�n RECEIVING ");
		System.out.println("PASO 2");
		
		rf.seleccionarReceivingReceiving();
			
		// PASO 3
		addStep("Capturar la siguiente informacion en los siguientes campos: "
				+ "- APPT NBR: Numero de cita, "
				+ "- DOOR: Numero de muelle programado, "
				+ "- TRAILER ID: Numero de identificacion del camion, "
				+ "- SEAL NBR: Numero de usuario recibidor");
		System.out.println("PASO 3");
		
		rf.capturarCita(cita, door, trailerId, seal);
		rf.closeAltF4();
		
		// PASO 4
		addStep("Validar que la cita haya cambiado de estatus en la pantalla Appointment Schedule");
		System.out.println("PASO 4");
		
		c.logIn();
		c.clickMenuAppointmentsAppointmentDetail();
		
		boolean statusOpen = c.validarStatusOpen(cita);
		
		assertTrue(statusOpen, "La cita: " + cita + " no existe o no tiene status OPEN");
		
		u.close();
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "1) Tener una cita actual con status SCHD"
				+ "2) Agregar una orden de compra a la cita"
				+ "3) Imprimir etiquetas de contenedores";
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
		return "ATC_FT_001_ValidarAperturaCitaRF_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

package RDM.mermaYDev.AsignarAsnACita;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_AsignarAsnACitaDevStore extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_AsignarAsnACitaDevStore_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String[] destIds = data.get("destId").replace(" ", "").split(",");
		
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
		addStep("Teclear el no. de cita programada en el campo APPT NBR y presionar enter. "
				+ "Presionar el boton \"YES\". "
				+ "Presionar el boton \"Add Multiple ASN\" para ligar la informacion.");
		System.out.println("PASO 3");
		
		m.colocarNumeroCitaAgregar(noCita);
		
		// PASO 4
		addStep("Posicionar el cursor en el campo \"Search\" y seleccionar la opcion STORE. "
				+ "Posicionarse sobre el campo STORE y presionar el bot�n \"Lista de valores\" "
				+ "Seleccionar la STORE requerida y presionar \"OK\". "
				+ "Si se requiere agregar mas STORES repetir pasos del 9 al 10, de lo contrario presionar el boton \"Run Query\". "
				+ "\"Seleccionar las casillas de las devoluciones a ligar.\r\n"
				+ "NOTA: Si se desea seleccionar todas las devoluciones se puede dar click a la opcion \"\"SELECT ALL\"\".\" "
				+ "Presionar el boton \"Save\".");
		System.out.println("PASO 4");
		
		ArrayList<String> asnList = m.llenarInfoTiendas(destIds);
		String[] asns = null;
		if (asnList != null) {
			asns = new String[asnList.size()];
			asns = asnList.toArray(asns);
		}
		
		assertNotNull(asns, "Ocurrio un error al agregar el Asn: " + Arrays.toString(asns));
	
		// PASO 5
		addStep("Verificar que la informacion que se selecciono "
				+ "coincida con la que se muestra en la pantalla \"NSC Appointment Detail\".");
		System.out.println("PASO 5");
		
		
		boolean todoSeAgrego = m.validarAsnsAgregados(asns);
		
		assertTrue(todoSeAgrego, "Los ASN no se agregaron correctamente");
		
		u.close();
		
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "Tener cita para devoluciones activa";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe asignar una "
				+ "Notificacion Avanzada de Embarque (ASN) a una cita de recibo devoluciones "
				+ "utilizando la opcion: ASN";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_AsignarAsnACitaDevStore_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

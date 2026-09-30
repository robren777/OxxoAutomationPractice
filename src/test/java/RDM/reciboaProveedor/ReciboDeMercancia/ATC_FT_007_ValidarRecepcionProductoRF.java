package RDM.reciboaProveedor.ReciboDeMercancia;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RadioFrecuencia;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.sql.SQLUtil;

public class ATC_FT_007_ValidarRecepcionProductoRF extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarRecepcionProductoRF_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String host = data.get("host");
		String puerto = data.get("puerto");
		String usuarioTera = data.get("usuarioTera");
		String passTera = data.get("passTera");
		String usuarioRf = data.get("usuarioRf");
		String passRf = data.get("passRf");
		String cita = data.get("cita");
		String door = data.get("door");
		String trailerId = data.get("trailerId");
		String seal = data.get("seal");
		/*
		 * Si se quiere ingresar algo en especifico usar estas variables
		 * y poner los valores correspondientes en el data provider
		 */
		//String contenedor = data.get("container");
		//String cantidad = data.get("cantidad");
		//String item = data.get("item");
		
		/*
		 * UTILERIAS
		 */	
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		RadioFrecuencia rf = new RadioFrecuencia(testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Ingresar a RF  y conectar");
		System.out.println("PASO 1"); 

		rf.abrir(host, puerto, usuarioTera, passTera, usuarioRf, passRf);		
		
		// PASO 2
		addStep("Seleccionar la opcion 2 y presionar la tecla [Enter]\r\n"
				+ "Teclear [Enter] "
				+ "Ingresar usuario y contrasena que se ingresaron en el paso 2 y teclear [Enter] "
				+ "Seleccionar la opcion XXFC - RECEIVING y teclear [Enter] "
				+ "Seleccionar la opcion STAY IN CEDIS y teclear [Enter]");
		System.out.println("PASO 2");
		
		rf.seleccionarXxfcReceivingStay(); 
		
		// PASO 3
		addStep("Capturar la siguiente informacion en los siguientes campos: "
				+ "- APPT NBR: Numero de cita, "
				+ "- DOOR: Numero de muelle programado, "
				+ "- TRAILER ID: Numero de identificacion del camion, "
				+ "- SEAL NBR: Numero de usuario recibidor "
				+ "Presionar la tecla F4");
		System.out.println("PASO 3");
		
		boolean etiquetas = rf.capturarCitaStay(cita);
		
		assertTrue(etiquetas, "La cita " + cita + " no es valida");
		
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "Tener una cita actual con status SCHD, agregarle una orden de compra "
				+ "e imprimir etiquetas";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se validara la recepcion del producto por medio de la Radiofrecuencia";
	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ValidarRecepcionProductoRF_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

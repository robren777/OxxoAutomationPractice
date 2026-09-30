package RDM.reciboaProveedor.ReciboDeMercancia;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RadioFrecuencia;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_006_ValidarCierreCitaMercanciaIncompletaRF extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarCierreCitaMercanciaIncompletaRF_Test(HashMap<String, String> data) throws Exception {
			
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
		addStep("Ingresar a Radio Frecuencia (TeraTerm) y conectar.");
		System.out.println("PASO 1"); 

		rf.abrir(host, puerto, usuarioTera, passTera, usuarioRf, passRf);		
		
		// PASO 2
		addStep("Ingresar opcion 2 y dar clic en enter. "
				+ "Ingresar el User ID y password. "
				+ "Dar click en Enter "
				+ "Dar click en la opcion XXFC-RECEIVING");
		System.out.println("PASO 2");
		
		rf.seleccionarReceivingReceiving(); 
		
		// PASO 3
		addStep("Capturar la siguiente informacion en los siguientes campos: "
				+ "- APPT NBR: Numero de cita, "
				+ "- DOOR: Numero de muelle programado, "
				+ "- TRAILER ID: Numero de identificacion del camion, "
				+ "- SEAL NBR: Numero de usuario recibidor"
				+ " Presionar la tecla F4");
		System.out.println("PASO 3");
		
		boolean etiquetas = rf.capturarCita(cita, door, trailerId, seal);
		
		assertTrue(etiquetas, "No se recibio la mercancia porque no se imprimeron las etiquetas");
			
		// PASO 4
		addStep("Posicionarse en el campo  e ingresar el numero de contenedor a recibir "
				+ "Presionar la tecla [Enter] "
				+ "Confirmar la cantidad del contenedor y presionar la tecla [Enter] "
				+ "Presionar la tecla F4");
		System.out.println("PASO 4");
		
		/*
		 * Obtiene el CONTAINER_ID de la tabla CONTAINER
		 * si se quiere recibir algo especifico comentar de aqui ->
		 */
		String containerIdQuery = String.format("SELECT CONTAINER_ID FROM CONTAINER "
				+ "WHERE APPT_NBR = '%s' "
				+ "AND CONTAINER_STATUS != 'I'", cita);
		System.out.println(containerIdQuery);

		SQLResult executeQuery = executeQuery(dbRdm, containerIdQuery);
		String contenedor = null;
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			contenedor = executeQuery.getData(0, "CONTAINER_ID");
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con APPT_NBR = " + cita + " y "
				+ "CONTAINTER_STATUS != 'I' en CONTAINER");
		
		// Obtiene el ITEM_ID y CONTAINER_QTY a recibir de la tabla CONTAINER_ITEM
		String itemQtyQuery = String.format("SELECT ITEM_ID, CONTAINER_QTY FROM CONTAINER_ITEM "
				+ "WHERE CONTAINER_ID = '%s' ", contenedor);
		System.out.println(itemQtyQuery);

		executeQuery = executeQuery(dbRdm, itemQtyQuery);
		String item = null;
		String cantidad = null;
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			item = executeQuery.getData(0, "ITEM_ID");
			cantidad = executeQuery.getData(0, "CONTAINER_QTY");
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con CONTAINER_ID = " + contenedor
				+ " en CONTAINER_ITEM");
		// <- a aqui 
		
		System.out.println(contenedor);
		System.out.println(cantidad);
		System.out.println(item);
		boolean permiteSeguirIngresando = rf.ingresarContenedor(contenedor, cantidad, item);
		
		assertTrue(permiteSeguirIngresando, "La terminal no permitio seguir ingresando mercancia");
		
		// PASO 5
		addStep("Presionar la tecla F4 "
				+ "Presionar la tecla F4 "
				+ "Posicionarse en el campo  y teclear la letra Y "
				+ "Presionar la tecla F4 "
				+ "Presionar la tecla F6 ");
		System.out.println("PASO 5");
		
		// PASO 6
		addStep("Crear otra cita para la mercancia faltante");
		System.out.println("PASO 6");
		
		
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
		return "Construido. Se validara cerrar una cita en radio Frecuencia cuando no se "
				+ "recibe completamente la mercancia en fisico";
	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidarCierreCitaMercanciaIncompletaRF_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

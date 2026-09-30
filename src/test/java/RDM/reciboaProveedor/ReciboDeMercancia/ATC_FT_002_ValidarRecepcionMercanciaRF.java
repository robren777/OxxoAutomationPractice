package RDM.reciboaProveedor.ReciboDeMercancia;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RadioFrecuencia;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_ValidarRecepcionMercanciaRF extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarRecepcionMercanciaRF_Test(HashMap<String, String> data) throws Exception {
			
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
				+ "Dar click en la opcion RECEIVING");
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
		
		rf.capturarCita(cita, door, trailerId, seal);
		
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
		return "Construido. Se validara la recepcion de mercancia por contenedor por medio de "
				+ "Radio Frecuencia";
	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarRecepcionMercanciaRF";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

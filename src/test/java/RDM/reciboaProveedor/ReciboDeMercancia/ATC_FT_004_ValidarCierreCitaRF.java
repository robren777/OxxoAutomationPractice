package RDM.reciboaProveedor.ReciboDeMercancia;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import RDM.util.RadioFrecuencia;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_ValidarCierreCitaRF extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarCierreCitaRF_Test(HashMap<String, String> data) throws Exception {
			
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
		String bestBeforeDate = data.get("bestBeforeDate");				
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
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		List<String[]> allData = executeQuery.getAllData();
		List<String> contenedores = new ArrayList<>();
		
		for (String[] rowContenedores : allData) {
			contenedores.add(rowContenedores[0]);
		}

		assertFalse(statusQuery, "No se encontraron registros con APPT_NBR = " + cita + " y "
				+ "CONTAINTER_STATUS != 'I' en CONTAINER");
		
		for (int i = 0; i < 2; i++) {
			boolean permiteSeguirIngresando = rf.ingresarContenedorBest(contenedores.get(i), bestBeforeDate);
			
			assertTrue(permiteSeguirIngresando, "La terminal no permitio seguir ingresando mercancia");
		}
		
		
		// PASO 5
		addStep("Presionar la tecla F4 "
				+ "Posicionarse en el campo  con la tecla N "
				+ "Presionar la tecla F4 "
				+ "Presionar la tecla F3");
		System.out.println("PASO 5");
		
		rf.lock();
		
		// PASO 6
		addStep("Validar en la pantalla Appt Schedule o Appt Detail que la cita "
				+ "cambio al estatus UNRC");
		System.out.println("PASO 6"); 
		
		
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "1) Tener la configuracion del teclaso en ENG US"
				+ "2) Tener una cita actual con status SCHD "
				+ "3) Agregar una orden de compra a la cita "
				+ "4) Imprimir etiquetas de contenedores ";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se validara que se cierre una cita utilizando Radio Frecuencia";	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarCierreCitaRF_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

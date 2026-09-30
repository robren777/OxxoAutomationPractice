package RDM.ruteoDinamico.consultaPedidosCambioEdo;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator - Acceso al aplicativo RDM
 * (VPN, Puertos) - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar que se pueda seleccionar todos los registros al dar Clic al bot�n
 * 				"Selec. Todos" y que se puedan enviar al dar Clic al bot�n "Volver a Enviar"
 * 
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_003_SeleccionarEnviarTodo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_SeleccionarEnviarTodo_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		//String url = data.get("server");
		String url = GlobalVariables.urlRDQA;
		String scriptSimularError = data.get("query");
		String fCreaIni = "";
		String fCreaFin = "";
		String actualizacionRegistrosTodos = "actualizacionRegistrosTodosQA";
		String btnYes = "yesRD";
		String numeroTransferencia = "";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, url);
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDQA, GlobalVariables.DB_USER_RDM_RD,
				GlobalVariables.DB_PASSWORD_RDM_RDQA);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
			
		// PASO 1
		addStep("Acceder al enlace http://fcret3.femcom.net:8036/rdmDES36.html por Microsoft Edge");
		System.out.println("PASO 1");

		rd.logIn();

		// PASO 2
		addStep("Seguir la ruta dando Clic en Distribution Planning -> XXFC-Programacion y Balanceo -> XXFC-Ruteo Dinamico");
		System.out.println("PASO 2");

		rd.clickMenuDistPlanProgramBalanceoRuteoDinamico();

		// PASO 3
		addStep("Clic en el bot�n \"Ver TSF Unigis\"");
		System.out.println("PASO 3");

		rd.clickVerTSFUnigis();
		
		// PASO 4
		addStep("Establecer en los campos F. Crea Ini y F. Crea Fin fechas v�lidas");
		System.out.println("PASO 4");
		
		fCreaIni = rd.obtenerFechaAnteriorConFormato(-90);
		fCreaFin = rd.obtenerFechaActualConFormato();
		rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);

		// PASO 5
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");

		rd.clickExecute();
		
		// EJECUTAR SCRIPT DE SIMULACI�N PARA CAMBIAR UN REGISTRO A ESTADO ERRONEO
		addStep("Se toma el numero de transferencia del primer registro para cambiarlo a un estado err�neo");
		
		numeroTransferencia = rd.numeroTransferenciaMedianteFila(1);
		scriptSimularError = String.format(scriptSimularError, numeroTransferencia);
		testCase.addCodeEvidenceCurrentStep(scriptSimularError);
		
		int resultado = dbRdm.executeUpdate(scriptSimularError);
		boolean status = false;
		
		if (resultado > 0) {
			status = true; 
			testCase.addBoldTextEvidenceCurrentStep("Resultado de la ejecuci�n del script: " + String.valueOf(resultado));	
		}

		assertTrue(status, "No se realiz� el cambio del registro");
		
		addStep("Limpiar la tabla y ejecutar de nuevo para ver el cambio reflejado");
		rd.clickClear();
		rd.clickExecute();
		
		// PASO 6
		addStep("Clic en el bot�n \"Selec. Todos\"");
		System.out.println("PASO 6");
		
		rd.clickSeleccionarTodos();
		
		// PASO 7
		addStep("Clic en el bot�n \"Volver a Enviar\"");
		System.out.println("PASO 7");
		
		rd.clickVolverEnviar();
		assertTrue(rd.validarMensajeCuadroDialogo(actualizacionRegistrosTodos, btnYes), "No se visualiz� el cuadro de mensaje");
		
		// CERRAR APLICATIVO RDM
		System.out.println("Cerrando el aplicativo de RDM");

		u.quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Tener instalado el JInitiator "
				+ "- Acceso al aplicativo RDM (VPN, Puertos)"
				+ "- Usar Microsoft Edge en modo Internet Explorer";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se pueda seleccionar todos los registros"
				+ " al dar Clic al bot�n \"Selec. Todos\" y que se puedan"
				+ " enviar al dar Clic al bot�n \"Volver a Enviar\"";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_SeleccionarEnviarTodo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

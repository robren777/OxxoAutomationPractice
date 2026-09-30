package RDM.conectaFase2;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution; import RDM.util.GlobalVariables;import RDM.util.Conecta.SFTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_008_ValidacionHistoricoSieteDias extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_ValidacionHistoricoSieteDias_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * VARIABLES
		 */
		// vars rutas SFTP
		String EN_PROCESO = "/u26/reple/bin/export/conecta/archivos/EN_PROCESO";
		String ERROR = "/u26/reple/bin/export/conecta/archivos/ERROR";
		String LOG = "/u26/reple/bin/export/conecta/archivos/LOG";
		String PENDIENTES = "/u26/reple/bin/export/conecta/archivos/PENDIENTES";
		String PROCESADOS = "/u26/reple/bin/export/conecta/archivos/PROCESADOS";				
		String RECIBIDOS = "/u26/reple/bin/export/conecta/archivos/RECIBIDOS";
		
		// vars CONTROL-M
		String controlMUser = GlobalVariables.USER_G_CONTROL_M;
		String controlMPass = GlobalVariables.PASS_G_CONTROL_M;
		String controlMServer = GlobalVariables.serverControlM;
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCREPQA, 
				GlobalVariables.DB_USER_FCREPQA,
				GlobalVariables.DB_PASSWORD_FCREPQA);
		SFTPUtil sftp = new SFTPUtil();
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Ingresar a la base de datos de satelite de REPLE");
		System.out.println("PASO 1");
		
		testCase.addTextEvidenceCurrentStep("Conectado a la BD: " + dbConecta.getHost());	
		
		// PASO 2
		addStep("Validar que la informacion que se encuentre sea mayor a 7 dias "
				+ "en las siguientes tablas: "
				+ "XXFC_CONECTA_FORECAST_HDR, "
				+ "XXFC_CONECTA_ARCHIVO_LOG, "
				+ "XXFC_CONECTA_FORECAST_DTL, "
				+ "XXFC_CONECTA_FORECAST_LOAD.");
		System.out.println("PASO 2");
		
		// XXFC_CONECTA_FORECAST_HDR
		String forecastHdr = String.format("SELECT ID_ARCHIVO_CARGADO, ARCHIVO_TEAMCORE, "
				+ "FECHA_CREACION "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE FECHA_CREACION < (SYSDATE - 7)");
		System.out.println(forecastHdr);

		SQLResult executeQuery = executeQuery(dbConecta, forecastHdr);
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "FECHA_CREACION < (SYSDATE - 7)");
		
		// XXFC_CONECTA_ARCHIVO_LOG
		String archivoLog = String.format("SELECT ID_ARCHIVO_CARGADO, ESTATUS, FECHA_CREACION "
				+ "FROM RMSREPLE.XXFC_CONECTA_ARCHIVO_LOG "
				+ "WHERE FECHA_CREACION < (SYSDATE - 7)");
		System.out.println(archivoLog);

		executeQuery = executeQuery(dbConecta, archivoLog);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_ARCHIVO_LOG con "
				+ "FECHA_CREACION < (SYSDATE - 7)");
		
		// XXFC_CONECTA_FORECAST_DTL
		String forecastDtl = String.format("SELECT ID_ARCHIVO_CARGADO, "
				+ "NUM_REGISTRO, CLIENTE, FECHA_ULTIMO_CAMBIO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
				+ "WHERE FECHA_ULTIMO_CAMBIO < (SYSDATE - 7)");
		System.out.println(forecastDtl);

		executeQuery = executeQuery(dbConecta, forecastDtl);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
				+ "FECHA_ULTIMO_CAMBIO < (SYSDATE - 7)");
		
		// XXFC_CONECTA_FORECAST_LOAD
		String forecastLoad = String.format("SELECT ID_ARCHIVO_CARGADO, "
				+ "NUM_REGISTRO, CLIENTE, FECHA_CARGA "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
				+ "WHERE FECHA_CARGA < (SYSDATE - 7)");
		System.out.println(forecastLoad);

		executeQuery = executeQuery(dbConecta, forecastLoad);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
				+ "FECHA_CARGA < (SYSDATE - 7)");
		
		// PASO 3
		addStep("Validar que en las siguientes rutas se muestre informacion mayor a 7 dias "
			    + "/u26/reple/bin/export/conecta/archivos/EN_PROCESO "
			    + "/u26/reple/bin/export/conecta/archivos/ERROR "
			    + "/u26/reple/bin/export/conecta/archivos/LOG "
			    + "/u26/reple/bin/export/conecta/archivos/PENDIENTES "
			    + "/u26/reple/bin/export/conecta/archivos/PROCESADOS "
			    + "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		System.out.println("PASO 3");
		
		sftp.conectar();
		
		// /u26/reple/bin/export/conecta/archivos/EN_PROCESO
		HashMap<String, String> enProceso = sftp.infoMayorA7Dias(EN_PROCESO);
		
		assertNotNull(enProceso, "No hay archivos en la ruta: " + EN_PROCESO);
		assertFalse(enProceso.isEmpty(), "No hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + EN_PROCESO);
		
		testCase.addBoldTextEvidenceCurrentStep("Los siguientes archivos tienen fecha mayor a 7 dias "
				+ "en la ruta " + EN_PROCESO + ": ");
		
		for (Map.Entry<String, String> map : enProceso.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/ERROR
		HashMap<String, String> error = sftp.infoMayorA7Dias(ERROR);
		
		assertNotNull(error, "No hay archivos en la ruta: " + ERROR);
		assertFalse(error.isEmpty(), "No hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + ERROR);
		
		testCase.addBoldTextEvidenceCurrentStep("Los siguientes archivos tienen fecha mayor a 7 dias "
				+ "en la ruta " + ERROR + ": ");
		
		for (Map.Entry<String, String> map : error.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/LOG
		HashMap<String, String> log = sftp.infoMayorA7Dias(LOG);
		
		assertNotNull(log, "No hay archivos en la ruta: " + LOG);
		assertFalse(log.isEmpty(), "No hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + LOG);
		
		testCase.addBoldTextEvidenceCurrentStep("Los siguientes archivos tienen fecha mayor a 7 dias "
				+ "en la ruta " + LOG + ": ");
		
		for (Map.Entry<String, String> map : log.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/PENDIENTES
		HashMap<String, String> pendientes = sftp.infoMayorA7Dias(PENDIENTES);
		
		assertNotNull(pendientes, "No hay archivos en la ruta: " + PENDIENTES);
		assertFalse(pendientes.isEmpty(), "No hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PENDIENTES);
		
		testCase.addBoldTextEvidenceCurrentStep("Los siguientes archivos tienen fecha mayor a 7 dias "
				+ "en la ruta " + PENDIENTES + ": ");
		
		for (Map.Entry<String, String> map : pendientes.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/PROCESADOS
		HashMap<String, String> procesados = sftp.infoMayorA7Dias(PROCESADOS);
		
		assertNotNull(procesados, "No hay archivos en la ruta: " + PROCESADOS);
		assertFalse(procesados.isEmpty(), "No hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PROCESADOS);
		
		testCase.addBoldTextEvidenceCurrentStep("Los siguientes archivos tienen fecha mayor a 7 dias "
				+ "en la ruta " + PROCESADOS + ": ");
		
		for (Map.Entry<String, String> map : procesados.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/RECIBIDOS
		HashMap<String, String> recibidos = sftp.infoMayorA7Dias(RECIBIDOS);
		
		assertNotNull(recibidos, "No hay archivos en la ruta: " + RECIBIDOS);
		assertFalse(recibidos.isEmpty(), "No hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + RECIBIDOS);
		
		testCase.addBoldTextEvidenceCurrentStep("Los siguientes archivos tienen fecha mayor a 7 dias "
				+ "en la ruta " + RECIBIDOS + ": ");
		
		for (Map.Entry<String, String> map : recibidos.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		sftp.desconectar();
		
		// PASO 4
		addStep("Ejecutar el job xxfc_conecta_depurar");
		System.out.println("PASO 4");
		
		JSONObject obj = new JSONObject(data.get("xxfc_conecta_depurar"));
		Control_mInicio cm = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm.logOn();

		ControlM control = new ControlM(u, testCase, obj); 
		control.searchJob(); // busca el job
		
		String resultadoEjecucion = control.executeJob(); 

		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"), 
				"El job xxfc_conecta_depurar no se ejecuto correctamente");	
		
		u.close();
		
		// PASO 5
		addStep("Validar que en las tablas no se muestre informacion mayor a 7 dias "
				+ "XXFC_CONECTA_FORECAST_HDR, "
				+ "XXFC_CONECTA_ARCHIVO_LOG, "
				+ "XXFC_CONECTA_FORECAST_DTL, "
				+ "XXFC_CONECTA_FORECAST_LOAD.");
		System.out.println("PASO 5");
		
		// XXFC_CONECTA_FORECAST_HDR
		System.out.println(forecastHdr);
		
		executeQuery = executeQuery(dbConecta, forecastHdr);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "FECHA_CREACION < (SYSDATE - 7)");
		
		// XXFC_CONECTA_ARCHIVO_LOG
		System.out.println(archivoLog);

		executeQuery = executeQuery(dbConecta, archivoLog);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_ARCHIVO_LOG con "
				+ "FECHA_CREACION < (SYSDATE - 7)");
		
		// XXFC_CONECTA_FORECAST_DTL
		System.out.println(forecastDtl);

		executeQuery = executeQuery(dbConecta, forecastDtl);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
				+ "FECHA_ULTIMO_CAMBIO < (SYSDATE - 7)");
		
		// XXFC_CONECTA_FORECAST_LOAD
		System.out.println(forecastLoad);

		executeQuery = executeQuery(dbConecta, forecastLoad);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
				+ "FECHA_CARGA < (SYSDATE - 7)");
		
		// PASO 6
		addStep("Validar que en las siguientes rutas no se muestre informacion mayor a 7 dias "
			    + "/u26/reple/bin/export/conecta/archivos/EN_PROCESO "
			    + "/u26/reple/bin/export/conecta/archivos/ERROR "
			    + "/u26/reple/bin/export/conecta/archivos/LOG "
			    + "/u26/reple/bin/export/conecta/archivos/PENDIENTES "
			    + "/u26/reple/bin/export/conecta/archivos/PROCESADOS "
			    + "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		System.out.println("PASO 6");
		
		sftp.conectar();
		
		// /u26/reple/bin/export/conecta/archivos/EN_PROCESO
		enProceso = sftp.infoMayorA7Dias(EN_PROCESO);
		
		assertNotNull(enProceso, "No hay archivos en la ruta: " + EN_PROCESO);
		assertTrue(enProceso.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + EN_PROCESO);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + EN_PROCESO);
		
		for (Map.Entry<String, String> map : enProceso.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/ERROR
		error = sftp.infoMayorA7Dias(ERROR);
		
		assertNotNull(error, "No hay archivos en la ruta: " + ERROR);
		assertTrue(error.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + ERROR);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + ERROR);
		
		// /u26/reple/bin/export/conecta/archivos/LOG
		log = sftp.infoMayorA7Dias(LOG);
		
		assertNotNull(log, "No hay archivos en la ruta: " + LOG);
		assertTrue(log.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + LOG);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + LOG);
	
		// /u26/reple/bin/export/conecta/archivos/PENDIENTES
		pendientes = sftp.infoMayorA7Dias(PENDIENTES);
		
		assertNotNull(pendientes, "No hay archivos en la ruta: " + PENDIENTES);
		assertTrue(pendientes.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PENDIENTES);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + PENDIENTES);
	
		// /u26/reple/bin/export/conecta/archivos/PROCESADOS
		procesados = sftp.infoMayorA7Dias(PROCESADOS);
		
		assertNotNull(procesados, "No hay archivos en la ruta: " + PROCESADOS);
		assertTrue(procesados.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PROCESADOS);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + PROCESADOS);
		
		// /u26/reple/bin/export/conecta/archivos/RECIBIDOS
		recibidos = sftp.infoMayorA7Dias(RECIBIDOS);
		
		assertNotNull(recibidos, "No hay archivos en la ruta: " + RECIBIDOS);
		assertTrue(recibidos.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + RECIBIDOS);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + RECIBIDOS);
		
		sftp.desconectar();        
	}
	
	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar que el archivo VentasInvDiario sea cargado a la base de datos "
				+ "de la tabla correspondiente al historico de siete dias";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_ValidacionHistoricoSieteDias_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
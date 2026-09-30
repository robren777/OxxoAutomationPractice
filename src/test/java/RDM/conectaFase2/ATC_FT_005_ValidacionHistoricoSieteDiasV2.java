package RDM.conectaFase2;

import static org.testng.Assert.assertFalse;
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

public class ATC_FT_005_ValidacionHistoricoSieteDiasV2 extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidacionHistoricoSieteDiasV2_test(HashMap<String, String> data) throws Exception {
		
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
		addStep("Revisar en la tabla XXFC_CONECTA_FORECAST_HDR que archivos han excedido el tiempo de "
				+ "retencion");
		System.out.println("PASO 1");
		
		String archivos = "SELECT ID_ARCHIVO_CARGADO, ARCHIVO_TEAMCORE, ID_ESTATUS_PROCESO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE NVL(FECHA_CARGA, FECHA_CREACION) < SYSDATE - ("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION FROM RMSREPLE.XXFC_CONECTA_CONFIG_PMT "
				+ "WHERE PARAMETRO_CONFIGURADO IN ('TIEMPO_RETENCION'))";
		System.out.println(archivos);

		SQLResult executeQuery = executeQuery(dbConecta, archivos);
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);	
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_HDR "
				+ "que hayan excedido el tiempo de retencion");	
		
		// PASO 2
		addStep("Ejecutar el job xxfc_conecta_depurar");
		System.out.println("PASO 2");
		
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
		
		// PASO 3
		addStep("Validar que en las siguientes rutas no se muestre informacion mayor a 7 dias "
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
		
		assertTrue(enProceso.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + EN_PROCESO);
		
		for (Map.Entry<String, String> map : enProceso.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/ERROR
		HashMap<String, String> error = sftp.infoMayorA7Dias(ERROR);
		
		assertTrue(error.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + ERROR);
		
		// /u26/reple/bin/export/conecta/archivos/LOG
		HashMap<String, String> log = sftp.infoMayorA7Dias(LOG);
		
		assertTrue(log.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + LOG);
	
		// /u26/reple/bin/export/conecta/archivos/PENDIENTES
		HashMap<String, String> pendientes = sftp.infoMayorA7Dias(PENDIENTES);
		
		assertTrue(pendientes.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PENDIENTES);
	
		// /u26/reple/bin/export/conecta/archivos/PROCESADOS
		HashMap<String, String> procesados = sftp.infoMayorA7Dias(PROCESADOS);
		
		assertTrue(procesados.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PROCESADOS);
		
		// /u26/reple/bin/export/conecta/archivos/RECIBIDOS
		HashMap<String, String> recibidos = sftp.infoMayorA7Dias(RECIBIDOS);
		
		assertTrue(recibidos.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + RECIBIDOS);
		
		testCase.addTextEvidenceCurrentStep("No se encontraron archivos en las rutas: " 
				+ "/u26/reple/bin/export/conecta/archivos/EN_PROCESO\n"
				+ "/u26/reple/bin/export/conecta/archivos/ERROR\n"
				+ "/u26/reple/bin/export/conecta/archivos/LOG\n"
				+ "/u26/reple/bin/export/conecta/archivos/PENDIENTES\n"
				+ "/u26/reple/bin/export/conecta/archivos/PROCESADOS\n"
				+ "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		
		// PASO 4
		addStep("Revisar que no haya datos del archivo en cuestion en las tablas: "
				+ "XXFC_CONECTA_ARCHIVO_LOG    XXFC_CONECTA_FORECAST_LOAD "
				+ "XXFC_CONECTA_FORECAST_DTL   XXFC_CONECTA_FORECAST_HDR");
		System.out.println("PASO 4");
		
		// XXFC_CONECTA_ARCHIVO_LOG
		String archivoLog = "SELECT ID_ARCHIVO_CARGADO, ESTATUS, CODIGO_ERROR_VALIDACION "
				+ "FROM RMSREPLE.XXFC_CONECTA_ARCHIVO_LOG "
				+ "WHERE ID_ARCHIVO_CARGADO IN (SELECT ID_ARCHIVO_CARGADO FROM ( "
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION FROM RMSREPLE.XXFC_CONECTA_CONFIG_PMT "
				+ "WHERE PARAMETRO_CONFIGURADO IN ('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN RMSREPLE.XXFC_CONECTA_FORECAST_HDR ON NVL(FECHA_CARGA,FECHA_CREACION) < "
				+ "TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(archivoLog);

		executeQuery = executeQuery(dbConecta, archivoLog);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_ARCHIVO_LOG con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// XXFC_CONECTA_FORECAST_LOAD
		String forecastLoad = "SELECT ID_ARCHIVO_CARGADO, FECHA_CARGA "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
				+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO FROM("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION "
				+ "FROM RMSREPLE.XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_CONFIGURADO IN"
				+ "('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "ON NVL(FECHA_CARGA, FECHA_CREACION) < TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(forecastLoad);

		executeQuery = executeQuery(dbConecta, forecastLoad);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// XXFC_CONECTA_FORECAST_DTL
		String forecastDtl = "SELECT ID_ARCHIVO_CARGADO,FECHA_ULTIMO_CAMBIO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
				+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO FROM("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION "
				+ "FROM RMSREPLE.XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_CONFIGURADO IN"
				+ "('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "ON NVL(FECHA_CARGA, FECHA_CREACION) < TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(forecastDtl);

		executeQuery = executeQuery(dbConecta, forecastDtl);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// XXFC_CONECTA_FORECAST_HDR
		String forecastHdr = "SELECT ID_ARCHIVO_CARGADO, ARCHIVO_TEAMCORE, ID_ESTATUS_PROCESO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO FROM("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION "
				+ "FROM RMSREPLE.XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_CONFIGURADO IN"
				+ "('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "ON NVL(FECHA_CARGA, FECHA_CREACION) < TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(forecastHdr);
		
		executeQuery = executeQuery(dbConecta, forecastHdr);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
	}
	
	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "Se debe tener archivos con fecha de creacion mayor a 7 dias"
				+ " Una vez ejecutada la prueba esos archivos desapareceran";
	}

	@Override
	public String setTestDescription() {
		return "Depuracion historica de tablas y archivos de acuerdo a lo configurado";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidacionHistoricoSieteDiasV2_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
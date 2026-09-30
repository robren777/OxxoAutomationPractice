package RDM.conectaFase2;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import modelo.BaseExecution; import RDM.util.GlobalVariables;import RDM.util.Conecta.SFTPUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_006_ValidacionConsultaRepleV2 extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidacionConsultaReplev2_test(HashMap<String, String> data) throws Exception {
		
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
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCREPQA, 
				GlobalVariables.DB_USER_FCREPQA,
				GlobalVariables.DB_PASSWORD_FCREPQA);
		SFTPUtil sftp = new SFTPUtil();
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Ingresar a la base de datos de satelite de REPLE");
		System.out.println("PASO 1");
		
		testCase.addTextEvidenceCurrentStep("Conectado a la BD: " + dbConecta.getHost());	
		
		// PASO 2
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_FORECAST_HDR no muestra informacion mayor a 7 dias.");
		System.out.println("PASO 2");
		
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
		
		SQLResult executeQuery = executeQuery(dbConecta, forecastHdr);
		
		boolean statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// PASO 3
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_ARCHIVO_LOG no muestra informacion mayor a 7 dias.");
		System.out.println("PASO 3");
		
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
		
		// PASO 4
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_FORECAST_DTL no muestra informacion mayor a 7 dias");
		System.out.println("PASO 4");
		
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
		
		// PASO 5
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_FORECAST_LOAD no muestra informacion mayor a 7 dias");
		System.out.println("PASO 5");
		
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
		HashMap<String, String> enProceso = sftp.infoMayorA7Dias(EN_PROCESO);
		
		assertNotNull(enProceso, "No hay archivos en la ruta: " + EN_PROCESO);
		assertTrue(enProceso.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + EN_PROCESO);
		
		for (Map.Entry<String, String> map : enProceso.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/ERROR
		HashMap<String, String>  error = sftp.infoMayorA7Dias(ERROR);
		
		assertTrue(error.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + ERROR);
	
		// /u26/reple/bin/export/conecta/archivos/LOG
		HashMap<String, String>  log = sftp.infoMayorA7Dias(LOG);
		
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
		
		testCase.addTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a TIEMPO_RETENCION en:\n" 
				+ "/u26/reple/bin/export/conecta/archivos/EN_PROCESO\n"
				+ "/u26/reple/bin/export/conecta/archivos/ERROR\n"
				+ "/u26/reple/bin/export/conecta/archivos/LOG\n"
				+ "/u26/reple/bin/export/conecta/archivos/PENDIENTES\n"
				+ "/u26/reple/bin/export/conecta/archivos/PROCESADOS\n"
				+ "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		
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
		return "Validar que se pueda acceder a la informacion del repositorio "
				+ "historico Reple";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidacionConsultaRepleV2_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
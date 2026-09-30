package RDM.conectaFase2;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import modelo.BaseExecution; import RDM.util.GlobalVariables;import RDM.util.Conecta.SFTPUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_009_ValidacionConsultaReple extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ValidacionConsultaReple_test(HashMap<String, String> data) throws Exception {
		
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
		
		String forecastHdr = String.format("SELECT ID_ARCHIVO_CARGADO, ARCHIVO_TEAMCORE, "
				+ "FECHA_CREACION "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE FECHA_CREACION < (SYSDATE - 7)");
		System.out.println(forecastHdr);

		SQLResult executeQuery = executeQuery(dbConecta, forecastHdr);
		
		boolean statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "FECHA_CREACION < (SYSDATE - 7)");
		
		// PASO 3
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_ARCHIVO_LOG no muestra informacion mayor a 7 dias.");
		System.out.println("PASO 3");
		
		String archivoLog = String.format("SELECT ID_ARCHIVO_CARGADO, ESTATUS, FECHA_CREACION "
				+ "FROM RMSREPLE.XXFC_CONECTA_ARCHIVO_LOG "
				+ "WHERE FECHA_CREACION < (SYSDATE - 7)");
		System.out.println(archivoLog);

		executeQuery = executeQuery(dbConecta, archivoLog);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_ARCHIVO_LOG con "
				+ "FECHA_CREACION < (SYSDATE - 7)");
		
		// PASO 4
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_FORECAST_DTL no muestra informacion mayor a 7 dias");
		System.out.println("PASO 4");
		
		String forecastDtl = String.format("SELECT ID_ARCHIVO_CARGADO, "
				+ "NUM_REGISTRO, CLIENTE, FECHA_ULTIMO_CAMBIO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
				+ "WHERE FECHA_ULTIMO_CAMBIO < (SYSDATE - 7)");
		System.out.println(forecastDtl);

		executeQuery = executeQuery(dbConecta, forecastDtl);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
				+ "FECHA_ULTIMO_CAMBIO < (SYSDATE - 7)");
		
		// PASO 5
		addStep("Validar que la informacion que se muestra en la tabla "
				+ "XXFC_CONECTA_FORECAST_LOAD no muestra informacion mayor a 7 dias");
		System.out.println("PASO 5");
		
		String forecastLoad = String.format("SELECT ID_ARCHIVO_CARGADO, "
				+ "NUM_REGISTRO, CLIENTE, FECHA_CARGA "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
				+ "WHERE FECHA_CARGA < (SYSDATE - 7)");
		System.out.println(forecastLoad);

		executeQuery = executeQuery(dbConecta, forecastLoad);
		
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
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
		HashMap<String, String> enProceso = sftp.infoMayorA7Dias(EN_PROCESO);
		
		assertNotNull(enProceso, "No hay archivos en la ruta: " + EN_PROCESO);
		assertTrue(enProceso.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + EN_PROCESO);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + EN_PROCESO);
		
		for (Map.Entry<String, String> map : enProceso.entrySet()) {
			testCase.addTextEvidenceCurrentStep(map.getKey() + ": " + map.getValue());
		}
		
		// /u26/reple/bin/export/conecta/archivos/ERROR
		HashMap<String, String>  error = sftp.infoMayorA7Dias(ERROR);
		
		assertNotNull(error, "No hay archivos en la ruta: " + ERROR);
		assertTrue(error.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + ERROR);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + ERROR);
		
		// /u26/reple/bin/export/conecta/archivos/LOG
		HashMap<String, String>  log = sftp.infoMayorA7Dias(LOG);
		
		assertNotNull(log, "No hay archivos en la ruta: " + LOG);
		assertTrue(log.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
			+ "en la ruta: " + LOG);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + LOG);
	
		// /u26/reple/bin/export/conecta/archivos/PENDIENTES
		HashMap<String, String> pendientes = sftp.infoMayorA7Dias(PENDIENTES);
		
		assertNotNull(pendientes, "No hay archivos en la ruta: " + PENDIENTES);
		assertTrue(pendientes.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PENDIENTES);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + PENDIENTES);
	
		// /u26/reple/bin/export/conecta/archivos/PROCESADOS
		HashMap<String, String> procesados = sftp.infoMayorA7Dias(PROCESADOS);
		
		assertNotNull(procesados, "No hay archivos en la ruta: " + PROCESADOS);
		assertTrue(procesados.isEmpty(), "Hay archivos con fecha de modificacion mayor a 7 dias "
				+ "en la ruta: " + PROCESADOS);
		
		testCase.addBoldTextEvidenceCurrentStep("No se encontraron archivos con fecha mayor a 7 dias "
				+ "en la ruta " + PROCESADOS);
		
		// /u26/reple/bin/export/conecta/archivos/RECIBIDOS
		HashMap<String, String> recibidos = sftp.infoMayorA7Dias(RECIBIDOS);
		
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
		return "Validar que se pueda acceder a la informacion del repositorio "
				+ "historico Reple";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_ValidacionConsultaReple_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
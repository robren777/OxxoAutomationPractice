package RDM.conectaFinal;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution; import RDM.util.GlobalVariables;import RDM.util.Conecta.SFTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_019_DepuracionHistoricoTablas extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_DepuracionHistoricoTablas_test(HashMap<String, String> data) throws Exception {
		
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
		
		String archivos = "SELECT * FROM XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE NVL(FECHA_CARGA, FECHA_CREACION) < SYSDATE - ("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION FROM XXFC_CONECTA_CONFIG_PMT "
				+ "WHERE PARAMETRO_CONFIGURADO IN ('TIEMPO_RETENCION'))";
		System.out.println(archivos);

		SQLResult executeQuery = executeQuery(dbConecta, archivos);
		String archivo = "";
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);	
			archivo = executeQuery.getData(0, "ARCHIVO_TEAMCORE");
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
		boolean existeArchivo = sftp.existeArchivoEnSFtp(EN_PROCESO, archivo);
		
		assertFalse(existeArchivo, "El archivo " + archivo + " si existe en la ruta " 
				+ EN_PROCESO);
		
		// /u26/reple/bin/export/conecta/archivos/ERROR
		existeArchivo = sftp.existeArchivoEnSFtp(ERROR, archivo);
		
		assertFalse(existeArchivo, "El archivo " + archivo + " si existe en la ruta " 
				+ ERROR);
		
		// /u26/reple/bin/export/conecta/archivos/LOG
		String filenameLog = archivo.split(".")[0] + ".log";
		existeArchivo = sftp.existeArchivoEnSFtp(LOG, filenameLog);
		
		assertFalse(existeArchivo, "El archivo " + filenameLog + " si existe en la ruta " 
				+ LOG);
	
		// /u26/reple/bin/export/conecta/archivos/PENDIENTES
		existeArchivo = sftp.existeArchivoEnSFtp(PENDIENTES, archivo);
		
		assertFalse(existeArchivo, "El archivo " + archivo + " si existe en la ruta " 
				+ PENDIENTES);
	
		// /u26/reple/bin/export/conecta/archivos/PROCESADOS
		existeArchivo = sftp.existeArchivoEnSFtp(PROCESADOS, archivo);
		
		assertFalse(existeArchivo, "El archivo " + archivo + " si existe en la ruta " 
				+ PROCESADOS);
		
		// /u26/reple/bin/export/conecta/archivos/RECIBIDOS
		existeArchivo = sftp.existeArchivoEnSFtp(RECIBIDOS, archivo);
		
		assertFalse(existeArchivo, "El archivo " + archivo + " si existe en la ruta " 
				+ PROCESADOS);
		
		sftp.desconectar();   
		
		testCase.addTextEvidenceCurrentStep("No se encontro el archivo: " + archivo + "en las "
				+ "rutas: " 
				+ "/u26/reple/bin/export/conecta/archivos/EN_PROCESO "
				+ "/u26/reple/bin/export/conecta/archivos/ERROR "
				+ "/u26/reple/bin/export/conecta/archivos/LOG "
				+ "/u26/reple/bin/export/conecta/archivos/PENDIENTES "
				+ "/u26/reple/bin/export/conecta/archivos/PROCESADOS "
				+ "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		
		// PASO 4
		addStep("Revisar que no haya datos del archivo en cuestion en las tablas: "
				+ "XXFC_CONECTA_ARCHIVO_LOG    XXFC_CONECTA_FORECAST_LOAD "
				+ "XXFC_CONECTA_FORECAST_DTL   XXFC_CONECTA_FORECAST_HDR");
		System.out.println("PASO 4");
		
		// XXFC_CONECTA_ARCHIVO_LOG
		String archivoLog = " SELECT * FROM XXFC_CONECTA_ARCHIVO_LOG "
				+ "WHERE ID_ARCHIVO_CARGADO IN (SELECT ID_ARCHIVO_CARGADO FROM ( "
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION FROM XXFC_CONECTA_CONFIG_PMT "
				+ "WHERE PARAMETRO_CONFIGURADO IN ('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN XXFC_CONECTA_FORECAST_HDR ON NVL(FECHA_CARGA,FECHA_CREACION) < "
				+ "TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(archivoLog);

		executeQuery = executeQuery(dbConecta, archivoLog);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_ARCHIVO_LOG con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// XXFC_CONECTA_FORECAST_LOAD
		String forecastLoad = "SELECT *  FROM XXFC_CONECTA_FORECAST_LOAD "
				+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO FROM("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION "
				+ "FROM XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_CONFIGURADO IN"
				+ "('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN XXFC_CONECTA_FORECAST_HDR "
				+ "ON NVL(FECHA_CARGA, FECHA_CREACION) < TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(forecastLoad);

		executeQuery = executeQuery(dbConecta, forecastLoad);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// XXFC_CONECTA_FORECAST_DTL
		String forecastDtl = "SELECT * FROM XXFC_CONECTA_FORECAST_DTL "
				+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO FROM("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION "
				+ "FROM XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_CONFIGURADO IN"
				+ "('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN XXFC_CONECTA_FORECAST_HDR "
				+ "ON NVL(FECHA_CARGA, FECHA_CREACION) < TRUNC(SYSDATE - TIEMPO_RETENCION))";
		System.out.println(forecastDtl);

		executeQuery = executeQuery(dbConecta, forecastDtl);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
				+ "FECHA_CREACION mayor a TIEMPO_RETENCION");
		
		// XXFC_CONECTA_FORECAST_HDR
		String forecastHdr = "SELECT * FROM XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO FROM("
				+ "SELECT MIN(PARAMETRO_VALOR) TIEMPO_RETENCION "
				+ "FROM XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_CONFIGURADO IN"
				+ "('TIEMPO_RETENCION') AND ACTIVO = 1) TIEMPO_RETENCION "
				+ "INNER JOIN XXFC_CONECTA_FORECAST_HDR "
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
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Depuracion historica de tablas y archivos de acuerdo a lo configurado con los "
				+ "parametros TIEMPO_RETENCION y TIEMPO_RETENCION_ARCHIVOS";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_DepuracionHistoricoTablas";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
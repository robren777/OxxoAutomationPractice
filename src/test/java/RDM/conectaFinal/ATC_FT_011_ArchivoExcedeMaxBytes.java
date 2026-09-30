package RDM.conectaFinal;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import config.Constants;
import modelo.BaseExecution; import RDM.util.GlobalVariables;import RDM.util.Conecta.SFTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_011_ArchivoExcedeMaxBytes extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_ArchivoExcedeMaxBytes_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * VARIABLES
		 */
		// nombre del archivo
		String filename = data.get("filename");
		
		// vars ftp
		String pathRecibidos = data.get("pathRecibidos");
		String pathProcesados = data.get("pathProcesados");
		String pathLog = data.get("pathLog");
		String pathError = data.get("pathError");
		String pathPendientes = data.get("pathPendientes");

		// var local
		String pathDestinoLocal = Constants.DATA_PROVIDER_PATH + "TeamCoreFiles";
	
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
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		SFTPUtil sftp = new SFTPUtil();
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Copiar el archivo al folder /u26/reple/bin/export/conecta/archivos/RECIBIDOS/");
		System.out.println("PASO 1");
		
		sftp.conectar();
		
		sftp.copiarArchivoLocalAFTP(filename, pathDestinoLocal, pathRecibidos);
		boolean existeArchivo = sftp.existeArchivoEnSFtp(pathRecibidos, filename);
		
		assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta " + pathRecibidos);
		
		testCase.addTextEvidenceCurrentStep(pathRecibidos + "\ncontiene el archivo:\n" + filename);
			
		// PASO 2
		addStep("Ejecutar el proceso xxfc_conecta_procesar.sh");
		System.out.println("PASO 2");
		
		JSONObject obj = new JSONObject(data.get("xxfc_conecta_procesar"));
		Control_mInicio cm = new Control_mInicio(u, controlMUser, controlMPass);
		
		u.get(controlMServer);
		u.waitForLoadPage();		
		cm.logOn();

		ControlM control = new ControlM(u, testCase, obj); 
		control.searchJob(); // busca el job
		
		String resultadoEjecucion = control.executeJob(); 

		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"), 
				"El job xxfc_conecta_procesar no se ejecuto correctamente");	
		
		u.close();
		
		// PASO 3
		addStep("Revisar en la tabla XXFC_CONECTA_FORECAST_HDR que el archivo se haya cargado");
		System.out.println("PASO 3");
		
		String forecastHdr = String.format("SELECT * FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ARCHIVO_TEAMCORE = '%s'", filename);
		System.out.println(forecastHdr);

		SQLResult executeQuery = executeQuery(dbConecta, forecastHdr);
		String idArchivoCargado = "";
		String status = "";
		String lineasArchivo = "";
		String idCargaInicial = "";
		String idCargaFinal = "";
		String fechaVisita = "";
		String erroresEncontrados = ""; 
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			idArchivoCargado = executeQuery.getData(0, "ID_ARCHIVO_CARGADO");
			lineasArchivo = executeQuery.getData(0, "LINEAS_ARCHIVO");
			idCargaInicial = executeQuery.getData(0, "ID_CARGA_INICIAL");
			idCargaInicial = executeQuery.getData(0, "FECHA_VISITA");
			erroresEncontrados = executeQuery.getData(0, "ERRORES_ENCONTRADOS");
			
			String idProceso = executeQuery.getData(0, "ID_ESTATUS_PROCESO");
			String obtenerStatus = String.format("SELECT PARAMETRO_CONFIGURADO FROM "
					+ "RMSREPLE.XXFC_CONECTA_CONFIG_PMT WHERE PARAMETRO_VALOR = '%s'",  idProceso);
			System.out.println(obtenerStatus);
			
			executeQuery = executeQuery(dbConecta, obtenerStatus);
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				status = executeQuery.getData(0, "PARAMETRO_CONFIGURADO");
				testCase.addTextEvidenceCurrentStep("El archivo se proceso con el status " + status);
			}
			
			assertFalse(statusQuery, "Hubo un error al intentar obtener el status del archivo: " 
					+ filename);
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "ARCHIVO_TEAMCORE = " + filename);
		
		// QUERY PARA MOSTRAR ANTERIOR CONSULTA EN EL REPORTE
		String forecastHdrMin = String.format("SELECT ID_ARCHIVO_CARGADO, "
				+ "ARCHIVO_TEAMCORE, ID_ESTATUS_PROCESO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ARCHIVO_TEAMCORE = '%s'", filename);
		System.out.println(forecastHdrMin);
		
		executeQuery = executeQuery(dbConecta, forecastHdrMin);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}		
		
		assertFalse(statusQuery, "Hubo un error al obtener informacion de XXFC_CONECTA_FORECAST_HDR");
		
		// longitud de bytes
		addStep("Si la LONGITUD_BYTES no excede el maximo en bytes configurado, entonces la columna "
				+ "LINEAS_ARCHIVO debe indicar cuantos renglones contiene el archivo");
		System.out.println("PASO LONGITUD_BYTES");
		
		String obtenerMaxBytes = "SELECT PARAMETRO_CONFIGURADO, PARAMETRO_VALOR "
				+ "FROM RMSREPLE.XXFC_CONECTA_CONFIG_PMT "
				+ "WHERE PARAMETRO_CONFIGURADO LIKE 'ARCH%MAX%BYTES'";
		System.out.println(obtenerMaxBytes);
		
		String maxBytes = "";
		executeQuery = executeQuery(dbConecta, obtenerMaxBytes);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			maxBytes = executeQuery.getData(0, "PARAMETRO_VALOR");
		}
		
		assertFalse(statusQuery, "Hubo un error al intentar obtener el numero maximo de bytes de "
				+ "RMSREPLE.XXFC_CONECTA_CONFIG_PMT");
		
		String obtenerLongitudBytes = String.format("SELECT LONGITUD_BYTES "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ARCHIVO_TEAMCORE = '%s'", filename);
		System.out.println(obtenerLongitudBytes);
		
		executeQuery = executeQuery(dbConecta, obtenerLongitudBytes);
		String longitudBytes = "";
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			longitudBytes = executeQuery.getData(0, "LONGITUD_BYTES");
		}
		
		assertFalse(statusQuery, "Hubo un error al intentar obtener LONGITUD_BYTES de "
				+ "RMSREPLE.XXFC_CONECTA_FORECAST_HDR");
		
		String obtenerLineasArchivo = String.format("SELECT LINEAS_ARCHIVO "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ARCHIVO_TEAMCORE = '%s'", filename);
		System.out.println(obtenerLineasArchivo);
		
		executeQuery = executeQuery(dbConecta, obtenerLineasArchivo);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			lineasArchivo = executeQuery.getData(0, "LINEAS_ARCHIVO");
		}
		
		assertFalse(statusQuery, "Hubo un error al intentar obtener LINEAS_ARCHIVO de "
				+ "RMSREPLE.XXFC_CONECTA_FORECAST_HDR");
		
		if (Long.valueOf(longitudBytes) < Long.valueOf(maxBytes)) {
			testCase.addTextEvidenceCurrentStep("El numero de bytes " + longitudBytes + " es menor "
					+ "a la longitud maxima de bytes " + maxBytes);
			testCase.addTextEvidenceCurrentStep("La columna LINEAS_ARCHIVO tiene el valor " + lineasArchivo);
		} else {
			testCase.addTextEvidenceCurrentStep("El numero de bytes (" + longitudBytes + ") es mayor "
					+ "a la longitud maxima de bytes (" + maxBytes + ")");
			testCase.addTextEvidenceCurrentStep("La columna LINEAS_ARCHIVO tiene el valor " + lineasArchivo);
		}
		
		System.out.println("Id archivo cargado: " + idArchivoCargado + "\nStatus: " + status);
		
		// BIFURCACION ENTRE ARCHIVO PROCESADO CON EXITO O ERROR
		if (status.equals("ARCH_EST_ERROR") || status.equals("ARCH_EST_PENDIENTE")) {
			// PASO 4
			addStep("Si el estatus termino en ARCH_EST_ERROR o ARCH_EST_PENDIENTE");
			System.out.println("PASO 4");
			
			addStep("Si el archivo paso por varios intentos de reprocesos esta informacion se vera "
					+ "reflejada");
			
			String intentos = String.format("SELECT CONTADOR_INTENTOS, FECHA_PRIMERO_INTENTO, "
					+ "FECHA_ULTIMO_INTENTO FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
					+ "WHERE ARCHIVO_TEAMCORE = '%s'", filename);
			System.out.println(intentos);
			
			executeQuery = executeQuery(dbConecta, intentos);
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}
			
			addStep("El campo ERRORES_ENCONTRADOS debe ser igual a los renglones encontrados en "
					+ "XXFC_CONECTA_ARCHIVO_LOG");
			// Mostrar ERRORES_ENCONTRADOS en el reporte
			String erroresEncontradosQuery = String.format("SELECT ERRORES_ENCONTRADOS "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
					+ "WHERE ARCHIVO_TEAMCORE = '%s'", filename);
			System.out.println(erroresEncontradosQuery);
			
			executeQuery = executeQuery(dbConecta, erroresEncontradosQuery);
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}
			
			assertFalse(statusQuery, "Hubo un error al obtener ERRORES_ENCONTRADOS "
					+ "de XXFC_CONECTA_FORECAST_HDR");
			
			String erroresLog = String.format("SELECT COUNT(1) "
					+ "FROM RMSREPLE.XXFC_CONECTA_ARCHIVO_LOG "
					+ "WHERE ID_ARCHIVO_CARGADO IN (SELECT ID_ARCHIVO_CARGADO "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
					+ "WHERE ARCHIVO_TEAMCORE = '%s')", filename);
			System.out.println(erroresLog);
			
			executeQuery = executeQuery(dbConecta, erroresLog);
			String countErrores = "";
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
				countErrores = executeQuery.getData(0, "COUNT(1)");
			}
			
			assertTrue(erroresEncontrados.equals(countErrores), "El valor ERRORES_ENCONTRADOS "
					+ "es distinto a COUNT(1) de XXFC_CONECTA_ARCHIVO_LOG");
			
			addStep("Revisar en el detalle de los errores presentados");
			
			String detalleErrores = String.format("SELECT ID_ARCHIVO_CARGADO,"
					+ "ESTATUS, CODIGO_ERROR_VALIDACION FROM RMSREPLE.XXFC_CONECTA_ARCHIVO_LOG "
					+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE ARCHIVO_TEAMCORE = '%s') "
					+ "AND ROWNUM <= 10", filename);
			System.out.println(detalleErrores);
			
			executeQuery = executeQuery(dbConecta, detalleErrores);
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}
			
			assertFalse(statusQuery, "No se encontraron registros en la tabla XXFC_CONECTA_ARCHIVO_LOG "
					+ "con ID_ARCHIVO_CARGADO = " + idArchivoCargado);
			
			addStep("Revisar en la tabla XXFC_CONECTA_FORECAST_LOAD los registros que aparentemente no "
					+ "tuvieron errores de tipo de dato");
			
			String regsSinError = String.format("SELECT NUM_REGISTRO, CLIENTE, HOLDING, CADENA "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
					+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
					+ "WHERE ARCHIVO_TEAMCORE = '%s')", filename);
			System.out.println(regsSinError);
			
			executeQuery = executeQuery(dbConecta, regsSinError);
			
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			
		} else if (status.equals("ARCH_EST_PROCESADO") || status.equals("ARCH_EST_COMPLETO")) {
			// PASO 5
			addStep("Si el estatus termino en ARCH_EST_PROCESADO o ARCH_EST_COMPLETO entonces: ");
			System.out.println("PASO 5");
			
			// Registros cargados y lineas archivo
			addStep("El campo REGISTROS_CARGADOS + 1 debe ser igual a LINEAS_ARCHIVO");
			
			String registroLineas = String.format("SELECT REGISTROS_CARGADOS, LINEAS_ARCHIVOS "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE ARCHIVO_TEAMCORE = '%s'", filename);
			System.out.println(registroLineas);
			
			executeQuery = executeQuery(dbConecta, registroLineas);
			String registrosCargados = "";
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
				registrosCargados = executeQuery.getData(0, "REGISTROS_CARGADOS");
			}
			
			boolean sonIguales = (Long.valueOf(registrosCargados) + 1) == Long.valueOf(lineasArchivo); 
			
			assertTrue(sonIguales, "El valor de REGISTROS_CARGADOS (" + registrosCargados + ") es distinto "
					+ "al valor de LINEAS_ARCHIVO (" + lineasArchivo + ")");

			// Errores encontrados
			addStep("El campo ERRORES_ENCONTRADOS debe ser igual a 0");
			String errores = String.format("SELECT ERRORES_ENCONTRADOS "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE ARCHIVO_TEAMCORE = '%s'", filename);
			System.out.println(errores);
			
			executeQuery = executeQuery(dbConecta, errores);
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}
			
			assertTrue(Long.valueOf(erroresEncontrados) == 0, "ERRORES_ENCONTRADOS (" 
					+ erroresEncontrados+ ") es distinto a 0");
			
			// CAMPOS IGUALES
			addStep("Los campos REGISTROS_CARGADOS, ID_CARGA_INICIAL, ID_CARGA_FINAL y "
					+ "FECHA_VISITA debe ser igual a "
					+ "los campos REGISTROS_CARGADOS, ID_CARGA_INICIAL, ID_CARGA_FINAL y FECHA_VISITA debe ser igual a: "
					+ "SELECT COUNT(1), MIN(ID_CARGA), MAX(ID_CARGA), MIN(FECHA_VISITA) "
					+ "FROM XXFC_CONECTA_FORECAST_DTL "
					+ "WHERE ID_ARCHIVO_CARGADO IN("
					+ "SELECT ID_ARCHIVO_CARGADO FROM XXFC_CONECTA_FORECAST_HDR "
					+ "WHERE ARCHIVO_TEAMCORE = '[archivo]')");
			
			String campos = String.format("SELECT COUNT(1), MIN(ID_CARGA), MAX(ID_CARGA), "
					+ "MIN(FECHA_VISITA) FROM RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
					+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE ARCHIVO_TEAMCORE = '%s')", filename);
			System.out.println(campos);
			
			executeQuery = executeQuery(dbConecta, campos);
			String registrosCargadosDtl = "";
			String idCargaInicialDtl = "";
			String idCargaFinalDtl = "";
			String fechaVisitaDtl = "";
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
				registrosCargadosDtl = executeQuery.getData(0, "REGISTROS_CARGADOS");
				idCargaInicialDtl = executeQuery.getData(0, "ID_CARGA_INICIAL");
				idCargaFinalDtl = executeQuery.getData(0, "ID_CARGA_FINAL");
				fechaVisitaDtl = executeQuery.getData(0, "FECHA_VISITA");
			}

			assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
					+ "ID_ARCHIVO_CARGADO = " + idArchivoCargado);
			
			assertTrue(registrosCargados.equals(registrosCargadosDtl), 
					"El valor REGISTROS_CARGADOS " + registrosCargados + " de la tabla "
							+ "XXFC_CONECTA_FORECAST_HDR es distinto a REGISTROS_CARGADOS " + registrosCargadosDtl
							+ " de la tabla XXFC_CONECTA_FORECAST_DTL");
			
			assertTrue(idCargaInicial.equals(idCargaInicialDtl), 
					"El valor ID_CARGA_INICIAL " + idCargaInicial + " de la tabla "
							+ "XXFC_CONECTA_FORECAST_HDR es distinto a ID_CARGA_INICIAL " + idCargaInicial
							+ " de la tabla XXFC_CONECTA_FORECAST_DTL");
			
			assertTrue(idCargaFinal.equals(idCargaFinalDtl), 
					"El valor ID_CARGA_FINAL " + idCargaFinal + " de la tabla "
							+ "XXFC_CONECTA_FORECAST_HDR es distinto a ID_CARGA_FINAL " + idCargaFinal
							+ " de la tabla XXFC_CONECTA_FORECAST_DTL");
			
			assertTrue(fechaVisita.equals(fechaVisitaDtl), 
					"El valor FECHA_VISITA " + fechaVisita + " de la tabla "
							+ "XXFC_CONECTA_FORECAST_HDR es distinto a FECHA_VISITA " + fechaVisitaDtl
							+ " de la tabla XXFC_CONECTA_FORECAST_DTL");
			
			// Registros en XXFC_CONECTA_FORECAST_DTL
			addStep("En XXFC_CONECTA_FORECAST_DTL estaran los registros cargados exitosamente");
			
			String registros = String.format("SELECT * FROM RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
					+ "WHERE ID_ARCHIVO_CARGADO IN(SELECT ID_ARCHIVO_CARGADO "
					+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE ARCHIVO_TEAMCORE = '%s')", filename);
			System.out.println(registros);
			
			executeQuery = executeQuery(dbConecta, registros);
			
			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}

			assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_DTL con "
					+ "ID_ARCHIVO_CARGADO = " + idArchivoCargado);
		}
		
		// BIFURCACION ENTRE CARPETAS POR ARCHIVOS SATISFACTORIOS O ERROR
		if (status.equals("ARCH_EST_PROCESADO") || status.equals("ARCH_EST_COMPLETO")) {
			// PASO 6
			addStep("Si el archivo fue procesado exitosamente (estatus ARCH_EST_PROCESADO o "
					+ "ARCH_EST_COMPLETO) Verificar en la carpeta "
					+ "/u26/reple/bin/export/conecta/archivos/PROCESADOS que este el archivo "
					+ "si fue procesado con exito");
			System.out.println("PASO 6");
			
			existeArchivo = sftp.existeArchivoEnSFtp(pathProcesados, filename);
			
			assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta " 
			+ pathProcesados);
			
			testCase.addTextEvidenceCurrentStep(pathProcesados + "\ncontiene el archivo:\n" + filename);
			
			addStep("Verificar en la carpeta /u26/reple/bin/export/conecta/archivos/LOG que este un "
					+ "archivo con el mismo nombre + la fecha de ejecucion + la extension .log y con un "
					+ "texto similar a FechaHora 'Archivo procesado exitosamente, Registros leidos: NNNNN' "
					+ "donde NNNNN es el valor de LINEAS_ARCHIVO");
			
			String filenameLog = filename.split(".")[0] + ".log";
			existeArchivo = sftp.existeArchivoEnSFtp(pathLog, filenameLog);
			
			assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta: " 
			+ pathLog);		
			
			testCase.addTextEvidenceCurrentStep(pathLog + "\ncontiene el archivo:\n" + filename);
			
		} else if (status.equals("ARCH_EST_ERROR") || status.equals("ARCH_EST_PENDIENTE")) {
			addStep("Si el archivo NO fue procesado exitosamente (estatus ARCH_EST_ERROR o "
					+ "ARCH_EST_PENDIENTE) Verificar que en la carpeta "
					+ "/u26/reple/bin/export/conecta/archivos/ERROR este un archivo con el "
					+ "mismo nombre + la fecha de ejecucion + la extension .err y un texto "
					+ "similar a FechaHora");
			
			String fileErr = sftp.existeArchivoErr(pathError, filename);			
			
			assertNotNull(fileErr, "No existe el archivo .err de: " + filename + " en la ruta: " 
					+ pathError);
			
			testCase.addTextEvidenceCurrentStep(pathError + "\ncontiene el archivo:\n" + fileErr);
			
			if (status.equals("ARCH_EST_ERROR")) {
				existeArchivo = sftp.existeArchivoEnSFtp(pathError, filename);			
				
				assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta: " 
						+ pathError);
				
				testCase.addTextEvidenceCurrentStep(pathError + "\ncontiene el archivo:\n" + filename);
			}
			
			if (status.equals("ARCH_EST_PENDIENTE")) {
				existeArchivo = sftp.existeArchivoEnSFtp(pathPendientes, filename);			
				
				assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta: "
						+ pathPendientes);
				
				testCase.addTextEvidenceCurrentStep(pathPendientes + "\ncontiene el archivo:\n" + filename);
			}
			
			sftp.desconectar();
		}
	}
	
	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Se debe rechazar el archivo excede el tamano en bytes "
				+ "segun parametro ARCHIVO_MAXIMO_EN_BYTES";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_ArchivoExcedeMaxBytes_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
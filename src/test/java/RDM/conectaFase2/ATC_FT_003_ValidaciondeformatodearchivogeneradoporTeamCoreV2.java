package RDM.conectaFase2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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

public class ATC_FT_003_ValidaciondeformatodearchivogeneradoporTeamCoreV2 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidaciondeformatodearchivogeneradoporTeamCoreV2_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		// nombre del archivo
		String filename = data.get("filename");
		String longitudBytes = String.format("SELECT LINEAS_ARCHIVO FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE "
		        + "ARCHIVO_TEAMCORE = '%s' ", filename);
		String registrosErrores = String.format("select COUNT(1) "
				+ "from RMSREPLE.xxfc_conecta_archivo_log "
				+ "where id_archivo_cargado in (select id_archivo_cargado from RMSREPLE.XXFC_CONECTA_FORECAST_HDR where ARCHIVO_TEAMCORE = '%s') ", filename);
		String detalleDeErrores = String.format("select ID_ARCHIVO_CARGADO,ESTATUS,CODIGO_ERROR_VALIDACION from RMSREPLE.xxfc_conecta_archivo_log "
				+ "where id_archivo_cargado in (select id_archivo_cargado from RMSREPLE.XXFC_CONECTA_FORECAST_HDR where ARCHIVO_TEAMCORE = '%s')AND ROWNUM <= 10 ", filename);
		String numeroDeRegistros = String.format("select COUNT(1) "
				+ "from RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
				+ "where id_archivo_cargado in (select id_archivo_cargado from RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "where ARCHIVO_TEAMCORE = '%s') ", filename);
		String detalleDeRegistros = String.format("select NUM_REGISTRO,CLIENTE,HOLDING,CADENA,CODIGO_DE_LOCAL from RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
				+ "where id_archivo_cargado in (select id_archivo_cargado from RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "where ARCHIVO_TEAMCORE = '%s') ", filename);
		String cargaDtl = String.format("select NUM_REGISTRO,CLIENTE,HOLDING,CADENA,CODIGO_DE_LOCAL from RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
				+ "where id_archivo_cargado in (select id_archivo_cargado from RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "where archivo_teamcore = '%s') ", filename);
		String forecastLoad = String.format("select ARCHIVO_TEAMCORE,ID_ESTATUS_PROCESO,CONTADOR_INTENTOS "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ARCHIVO_TEAMCORE = '%s' ", filename);
		// vars ftp
		String pathDestinoFtp = data.get("pathDestino");
		String pathDestinoFtpSinErrores = data.get("pathDestinoFtpSinErrores");
		String pathError = data.get("pathError");
		String pathPendientes = data.get("pathPendientes");
		String pathLog = data.get("pathLog");
		
		// var local
		String pathDestinoLocal = Constants.DATA_PROVIDER_PATH + "TeamCoreFiles";
	
		// vars CONTROL-M
		String controlMUser = GlobalVariables.usuarioRicardoControlM;
		String controlMPass = GlobalVariables.contrasenaRicardoControlM;
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
		addStep("Subir archivo csv con columnas incompletas a la ruta "
				+ "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		System.out.println("PASO 1");
		
		sftp.conectar();
		
		sftp.copiarArchivoLocalAFTP(filename, pathDestinoLocal, pathDestinoFtp);
		boolean existeArchivo = sftp.existeArchivoEnSFtp(pathDestinoFtp, filename);
		assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta del "
		+ "SFTP: " + pathDestinoFtp);
		
		// PASO 2
		addStep("Ejecutar el siguiente job: xxfc_conecta_procesar.sh");
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
		
		// COMIENZAN LAS VALIDACIONES DE ARCHIVOS CON ERRORES Y ARCHIVOS SIN ERRORES
		
		String obtenerStatus = String.format("SELECT ID_ARCHIVO_CARGADO,ID_ESTATUS_PROCESO,CONTADOR_INTENTOS,CODIGO_ERROR_VALIDACION "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE ARCHIVO_TEAMCORE = '%s'", filename);
		SQLResult comienzoValidacion = dbConecta.executeQuery(obtenerStatus);
		String IdEstatusProceso = comienzoValidacion.getData(0, "ID_ESTATUS_PROCESO");
		System.out.print("el estatus de el archivo es: "+IdEstatusProceso);
		
		// CONSULTAS IF PRIMERA PARTE
		
		String hdrComparaciones = String.format("SELECT * FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR WHERE "
		        + "ARCHIVO_TEAMCORE = '%s' ", filename);
		
		// VALIDACION ARCHIVO SIN ERRORES 
		
		if(IdEstatusProceso.equals("40") || IdEstatusProceso.equals("50"))
		{
			
			// VALIDACIONES
			// PASO 3
			addStep("Validar se muestre cargado el csv en la tabla "
					+ "RMSREPLE.XXFC_CONECTA_FORECAST_LOAD");
			System.out.println("PASO 4");
			System.out.println(forecastLoad);
			SQLResult executeQuery = dbConecta.executeQuery(forecastLoad);
			boolean statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}
			assertTrue(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
					+ "WHERE ARCHIVO_TEAMCORE = " + filename);
			System.out.println(executeQuery);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

			addStep("el campo REGISTROS_CARGADOS + 1 debe ser igual a LINEAS_ARCHIVO");
			System.out.println("PASO 3");
			SQLResult hdrComparaciones1 = dbConecta.executeQuery(hdrComparaciones);
			testCase.addQueryEvidenceCurrentStep(hdrComparaciones1);
			String lineasArchivo2 = hdrComparaciones1.getData(0, "LINEAS_ARCHIVO");
			Long l = Long.parseLong(lineasArchivo2);
			String registrosCargados2 = hdrComparaciones1.getData(0, "REGISTROS_CARGADOS");
			Long r = Long.parseLong(registrosCargados2)+1;
			assertEquals(l,r);
			testCase.addTextEvidenceCurrentStep("Validacion exitosa el campo REGISTROS_CARGADOS + 1 es igual a LINEAS_ARCHIVO");
			
			addStep("el campo ERRORES_ENCONTRADOS debe ser igual a 0");
			System.out.println("PASO 4");
			String errorresEncontrados2 = hdrComparaciones1.getData(0, "ERRORES_ENCONTRADOS");
			assertEquals(errorresEncontrados2,"0");
			testCase.addTextEvidenceCurrentStep("Validacion exitosa el campo ERRORES_ENCONTRADOS es igual a 0");
			
			addStep("los campos REGISTROS_CARGADOS,ID_CARGA_INICIAL, ID_CARGA_FINAL y FECHA_VISITA debe ser igual a "
					+ "select COUNT(1),MIN(ID_CARGA),MAX(ID_CARGA),MIN(FECHA_VISITA) "
					+ "from XXFC_CONECTA_FORECAST_DTL"
					+ "where id_archivo_cargado in (\r\n"
					+ "select id_archivo_cargado\r\n"
					+ "from XXFC_CONECTA_FORECAST_HDR\r\n"
					+ "where archivo_teamcore='[archivo]')");
			System.out.println("PASO 5");
			String validacionAtributos1 = String.format("select COUNT(1),MIN(ID_CARGA),MAX(ID_CARGA),MIN(FECHA_VISITA) "
					+ "from RMSREPLE.XXFC_CONECTA_FORECAST_DTL "
					+ "where id_archivo_cargado in (select id_archivo_cargado from RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
					+ "where archivo_teamcore = '%s') ", filename);
			SQLResult validacionatributos11 = dbConecta.executeQuery(validacionAtributos1);
			testCase.addQueryEvidenceCurrentStep(validacionatributos11);
			String registrosCargados1 = validacionatributos11.getData(0, "COUNT(1)");
			String cargainicial1 = validacionatributos11.getData(0, "MIN(ID_CARGA)");
			String cargaFinal1 = validacionatributos11.getData(0, "MAX(ID_CARGA)");
			String fechaVisita1 = validacionatributos11.getData(0, "MIN(FECHA_VISITA)");
			// igual a --
			String validacionAtributos2 = String.format("select REGISTROS_CARGADOS,ID_CARGA_INICIAL,ID_CARGA_FINAL,FECHA_VISITA "
					+ "from RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
					+ "where archivo_teamcore = '%s') ", filename);
			SQLResult validacionatributos12 = dbConecta.executeQuery(validacionAtributos2);
			String registrosCargados = validacionatributos12.getData(0, "REGISTROS_CARGADOS");
			String cargainicial = validacionatributos12.getData(0, "ID_CARGA_INICIAL");
			String cargaFinal = validacionatributos12.getData(0, "ID_CARGA_FINAL");
			String fechaVisita = validacionatributos12.getData(0, "FECHA_VISITA");
			assertEquals(registrosCargados1,registrosCargados);
			assertEquals(cargainicial1,cargainicial);
			assertEquals(cargaFinal1,cargaFinal);
			assertEquals(fechaVisita1,fechaVisita);
			testCase.addTextEvidenceCurrentStep("Validacion exitosa los campos son iguales entre ambas tablas");
			System.out.print("Validar Infromacion de Tabla XXFC_CONECTA_FORECAST_DTL");
			System.out.println(cargaDtl);
			SQLResult validat467 = dbConecta.executeQuery(cargaDtl);
			boolean valida467 = validat467.isEmpty();
			if (!valida467) {
				testCase.addQueryEvidenceCurrentStep(validat467);
				System.out.println(valida467);
				testCase.addTextEvidenceCurrentStep("Se encontraron registros en la consulta ya que no contiene errores");
			}
			
			System.out.print("Detalle de registros");
			System.out.println(detalleDeRegistros);
			SQLResult validat5 = dbConecta.executeQuery(detalleDeRegistros);
			boolean valida5 = validat5.isEmpty();
			if (!valida5) {
				testCase.addQueryEvidenceCurrentStep(validat5);
				System.out.println(valida5);
				testCase.addTextEvidenceCurrentStep("Se encuentran registros en la consulta ya que no contiene errores");
			}
			
			// PASO 5
			addStep("Validar que se muestre el csv en la ruta de procesados "
					+ "u26/reple/bin/export/conecta/archivos/PROCESADOS");
			System.out.println("PASO 5");
			
			existeArchivo = sftp.existeArchivoEnSFtp(pathDestinoFtpSinErrores, filename);
			
			assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta del "
					+ "SFTP: " + pathDestinoFtpSinErrores);
			
			// PASO 6
			addStep("Validar que se muestre el csv en la ruta de log "
					+ "u26/reple/bin/export/conecta/archivos/LOG");
			System.out.println("PASO 6");
			
			existeArchivo = sftp.existeArchivoEnSFtp(pathLog, filename+".log");
			
			assertTrue(existeArchivo, "No existe el archivo: " + filename+".log" + " en la ruta del "
					+ "SFTP: " + pathLog);
			
			testCase.addTextEvidenceCurrentStep("Se valido correctamente la informacion para un archivo completo o procesado");
		}
		
		// VALIDACION ARCHIVO CON ERRORES
		
		else if (IdEstatusProceso.equals("30") || IdEstatusProceso.equals("15"))
		{
			
			// VALIDACIONES
			// PASO 4
			addStep("Validar que no se muestre cargado el csv en la tabla "
					+ "RMSREPLE.XXFC_CONECTA_FORECAST_LOAD");
			System.out.println("PASO 4");
			System.out.println(forecastLoad);
			SQLResult executeQuery = dbConecta.executeQuery(forecastLoad);
			boolean statusQuery = executeQuery.isEmpty();
			if (statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);
			}
			assertFalse(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
					+ "WHERE ARCHIVO_TEAMCORE = " + filename);
			System.out.println(executeQuery);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
			
			System.out.print("Longitud de bytes");
			System.out.println(longitudBytes);
			SQLResult validat1 = dbConecta.executeQuery(longitudBytes);
			boolean valida1 = validat1.isEmpty();
			if (!valida1) {
				testCase.addQueryEvidenceCurrentStep(validat1);
			}
			assertFalse("No se encuentra la informacion ", valida1);
			System.out.println(valida1);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
			
			System.out.print("Registros de errores");
			System.out.println(registrosErrores);
			SQLResult validat2 = dbConecta.executeQuery(registrosErrores);
			boolean valida2 = validat2.isEmpty();
			if (!valida2) {
				testCase.addQueryEvidenceCurrentStep(validat2);
			}
			assertFalse("No se encuentra la informacion ", valida2);
			System.out.println(valida2);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
			
			System.out.print("Detalle de errores");
			System.out.println(detalleDeErrores);
			SQLResult validat3 = dbConecta.executeQuery(detalleDeErrores);
			boolean valida3 = validat3.isEmpty();
			if (!valida3) {
				testCase.addQueryEvidenceCurrentStep(validat3);
			}
			assertFalse("No se encuentra la informacion ", valida3);
			System.out.println(valida3);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
			
			System.out.print("Numero de registros");
			System.out.println(numeroDeRegistros);
			SQLResult validat4 = dbConecta.executeQuery(numeroDeRegistros);
			boolean valida4 = validat4.isEmpty();
			if (!valida4) {
				testCase.addQueryEvidenceCurrentStep(validat4);
			}
			assertFalse("No se encuentra la informacion ", valida4);
			System.out.println(valida4);
			testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
			
			System.out.print("Validar Infromacion de Tabla XXFC_CONECTA_FORECAST_DTL");
			System.out.println(cargaDtl);
			SQLResult validat46 = dbConecta.executeQuery(cargaDtl);
			boolean valida46 = validat46.isEmpty();
			if (valida46) {
				testCase.addQueryEvidenceCurrentStep(validat46);
				System.out.println(valida46);
				testCase.addTextEvidenceCurrentStep("No se encuentran registros en la consulta ya que contiene errores");
			}
			
			System.out.print("Detalle de registros");
			System.out.println(detalleDeRegistros);
			SQLResult validat5 = dbConecta.executeQuery(detalleDeRegistros);
			boolean valida5 = validat5.isEmpty();
			if (valida5) {
				testCase.addQueryEvidenceCurrentStep(validat5);
				System.out.println(valida5);
				testCase.addTextEvidenceCurrentStep("No se encuentran registros en la consulta ya que contiene errores");
			}
			
			
			if(IdEstatusProceso.equals("30")) {
				
				// PASO 4
				addStep("Validar que se muestre el csv en la ruta de errores "
						+ "/u26/reple/bin/export/conecta/archivos/ERROR");
				System.out.println("PASO 4");
				existeArchivo = sftp.existeArchivoEnSFtp(pathError, filename);
				assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta del "
						+ "SFTP: " + pathError);
				testCase.addTextEvidenceCurrentStep("El archivo "+filename+" Existe en: "+pathError);
				
				// PASO 5
				addStep("Validar que se muestre el csv en la ruta de errores "
						+ "/u26/reple/bin/export/conecta/archivos/ERROR");
				System.out.print("PASO 5");
				String fileErr = sftp.existeArchivoErr(pathError, filename);
				assertNotNull(fileErr, "No existe el archivo .err de: " + filename + " en la ruta del "
						+ "SFTP: " + pathError);
				testCase.addTextEvidenceCurrentStep("El archivo "+fileErr+" Existe en: "+pathError);
				testCase.addTextEvidenceCurrentStep("Se valido correctamente la informacion para un archivo con errores");
				
			}
			
			else if(IdEstatusProceso.equals("15")) {
				
				// PASO 4
				addStep("Validar que se muestre el csv en la ruta de errores "
						+ "/u26/reple/bin/export/conecta/archivos/ERROR");
				System.out.print("PASO 5");
				String fileErr = sftp.existeArchivoErr(pathError, filename);
				assertNotNull(fileErr, "No existe el archivo .err de: " + filename + " en la ruta del "
						+ "SFTP: " + pathError);
				testCase.addTextEvidenceCurrentStep("El archivo "+fileErr+" Existe en: "+pathError);
				testCase.addTextEvidenceCurrentStep("Se valido correctamente la informacion para un archivo con errores");
				
				// PASO 5
				addStep("Validar que se muestre el csv en la ruta de pendientes "
						+ "/u26/reple/bin/export/conecta/archivos/PENDIENTES");
				System.out.println("PASO 4");
				existeArchivo = sftp.existeArchivoEnSFtp(pathPendientes, filename);
				assertTrue(existeArchivo, "No existe el archivo: " + filename + " en la ruta del "
						+ "SFTP: " + pathPendientes);
				testCase.addTextEvidenceCurrentStep("El archivo "+filename+" Existe en: "+pathPendientes);
				
			}
			
		}

		sftp.desconectar();
		u.close();
	
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Crear un archivo cero bytes";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar el formato del archivo generado";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidaciondeformatodearchivogeneradoporTeamCoreV2_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
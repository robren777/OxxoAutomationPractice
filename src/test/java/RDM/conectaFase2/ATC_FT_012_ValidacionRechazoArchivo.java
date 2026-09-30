package RDM.conectaFase2;

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

public class ATC_FT_012_ValidacionRechazoArchivo extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_ValidacionRechazoArchivo_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * VARIABLES
		 */
		// nombre del archivo
		String nombreArchivo = data.get("nombreArchivo");
		
		// vars ftp
		String pathRecibidosFtp = data.get("pathRecibidos");
		String pathErrorFtp = data.get("pathError");
		
		// var local
		String pathLocal = Constants.DATA_PROVIDER_PATH + "TeamCoreFiles";
	
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
		addStep("Subir archivo csv con errores a la ruta "
				+ "/u26/reple/bin/export/conecta/archivos/RECIBIDOS");
		System.out.println("PASO 1");
		
		sftp.conectar();
		
		sftp.copiarArchivoLocalAFTP(nombreArchivo, pathLocal, pathRecibidosFtp);
		boolean existeArchivo = sftp.existeArchivoEnSFtp(pathRecibidosFtp, nombreArchivo);
		
		assertTrue(existeArchivo, "No existe el archivo: " + nombreArchivo + " en la ruta del "
				+ "SFTP: " + pathRecibidosFtp);
		
		// PASO 2
		addStep("Ejecutar el siguiente job: xxfc_conecta_procesar.sh");
		System.out.println("PASO 2");
		
		JSONObject obj = new JSONObject(data.get("xxfc_conecta_procesar.sh"));
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
		addStep("Validar que se inserte la informacion en la tabla XXFC_CONECTA_FORECAST_HDR "
				+ "con ID_ESTATUS_PROCESADO con valor de 30");
		System.out.println("PASO 3");
		
		String forecastHdr = String.format("SELECT * FROM RMSREPLE.XXFC_CONECTA_FORECAST_HDR "
				+ "WHERE ARCHIVO_TEAMCORE = '%s' "
				+ "AND ID_ESTATUS_PROCESO = 30", nombreArchivo);
		System.out.println(forecastHdr);

		SQLResult executeQuery = executeQuery(dbConecta, forecastHdr);
		String idArchivoCargado = "";
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			idArchivoCargado = executeQuery.getData(0, "ID_ARCHIVO_CARGADO");
			
		}

		assertFalse(statusQuery, "No se encontraron registros en: XXFC_CONECTA_FORECAST_HDR con "
				+ "ARCHIVO_TEAMCORE = " + nombreArchivo + " "
				+ "ID_ESTATUS_PROCESADO = 30");
		
		// PASO 4
		addStep("Validar que no se muestre cargado el csv en la tabla "
				+ "RMSREPLE.XXFC_CONECTA_FORECAST_LOAD");
		System.out.println("PASO 4");
		
		String forecastLoad = String.format("SELECT ID_ARCHIVO_CARGADO, NUM_REGISTRO, CLIENTE "
				+ "FROM RMSREPLE.XXFC_CONECTA_FORECAST_LOAD "
				+ "WHERE ID_ARCHIVO_CARGADO = %s", idArchivoCargado);
		System.out.println(forecastLoad);

		executeQuery = executeQuery(dbConecta, forecastLoad);

		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "Se encontraron registros en: XXFC_CONECTA_FORECAST_LOAD con "
				+ "ID_ARCHIVO_CARGADO = " + idArchivoCargado);
		
		// PASO 5
		addStep("Validar que se muestre el csv en la ruta de errores "
				+ "/u26/reple/bin/export/conecta/archivos/ERROR");
		System.out.println("PASO 5");
		
		existeArchivo = sftp.existeArchivoEnSFtp(pathErrorFtp, nombreArchivo);
		
		assertTrue(existeArchivo, "No existe el archivo: " + nombreArchivo + " en la ruta del "
				+ "SFTP: " + pathErrorFtp);
		
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
		return "Validar que al no cumplir con las reglas el archivo sea rechazado "
				+ "y se envie notficacion, el archivo sera rechazado al no cumplir con "
				+ "las reglas definidas o si la fecha de visita ya existe en la "
				+ "tabla de detalle";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_ValidacionRechazoArchivo_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}

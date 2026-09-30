package WM.EvolucionRetiroSinTarjeta.HU09;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_Ejecucion_de_Job_ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005 extends BaseExecution {
	/**
	 * Ejecucion de job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005
	 * Desc:
	 * Validar ejecucion de job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005 desde portal Control-M
	 * @author Miguel Escamilla
	 * @date 25/01/2024
	 **/
	SeleniumUtil u = null;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Ejecucion_de_Job_ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005(HashMap<String, String> data) throws Exception {

		//BD FCODSQA
		SQLUtil dbFCODSQA = new SQLUtil(GlobalVariables.DB_HOST_FCODSQA, GlobalVariables.DB_USER_FCODSQA,
				GlobalVariables.DB_PASSWORD_FCODSQA);

		System.out.println(
				"*****************************************PASO 1************************************************");
		addStep("Ejecutar el job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		u.get(data.get("server"));

		//espera de carga de pagina y login
		u.waitForLoadPage();
		u.hardWait(20);
		u.waitForLoadPage();
		cm.logOn();

		//objeto json para pasar los datos para encontrar el job de control-M a utilizar
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job = new ControlM(u, testCase, obj);
		u.hardWait(15);
		boolean flag = job.searchJob();
        //validar que el job se encontro
		assertTrue(flag);

		u.hardWait(45);
		//ejecucion del job y obtencion de resultados de la ejecucion en control-M
		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());

		u.hardWait(10);
		//resultado esperado para la finalizacion de ejecucion del job
		assertEquals(resultado1, "Ended OK");

		u.hardWait(3);

		// cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo
		job.closeViewpoint();
		u.hardWait(15);
		addStep("Se ejecuta de manera exitosa el job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005");

		u.close();

		System.out.println(
				"*****************************************PASO 2************************************************");

		String consulta_dbFCODSQA = "Select * from ( SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE FROM WM.TPE_FR_TRANSACTION ORDER BY CREATION_DATE DESC ) WHERE ROWNUM <= 5";
		System.out.println("Consulta: " + consulta_dbFCODSQA);
		SQLResult Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, consulta_dbFCODSQA);

		boolean conexionFCODSQA = true;
		conexionFCODSQA = Execute_dbFCODSQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCODSQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCODSQA);

		addStep("Se ingresa a la Base de datos FCODSQA para validar la información de la tabla TPE_FR_TRANSACTION");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		assertFalse(conexionFCODSQA, "La conexion no fue exitosa");

		if (!conexionFCODSQA){
			System.out.println("Conexion con la base de datos FCODSQA se realizo de manera exitosa");
		}

		System.out.println("Se imprime una muestra de datos:");
		List<String> columnNames = Execute_dbFCODSQA_Query.getColumnNames();
		Map<String, String> datos = Execute_dbFCODSQA_Query.getRowMap(1);

		columnNames.forEach( x -> { System.out.println(x + ": " + datos.get(x)); } );
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "El ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005 debe estar instalado y en estus 'on Hold' para su ejecucion en QA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecucion de Job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Miguel Angel Escamilla Reyes";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "003-Ejecucion de Job ODS_MAP_WM2ODS_TPEFR_TRANSACTION_005.sh";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

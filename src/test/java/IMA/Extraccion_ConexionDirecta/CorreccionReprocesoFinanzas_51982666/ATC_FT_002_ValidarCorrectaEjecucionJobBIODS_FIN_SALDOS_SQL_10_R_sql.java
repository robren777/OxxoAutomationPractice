package IMA.Extraccion_ConexionDirecta.CorreccionReprocesoFinanzas_51982666;

import modelo.BaseExecution;
import IMA.util.GlobalVariables;
import IMA.util.IMATools;

import org.json.JSONObject;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

import java.util.HashMap;

public class ATC_FT_002_ValidarCorrectaEjecucionJobBIODS_FIN_SALDOS_SQL_10_R_sql extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarCorrectaEjecucionJobBIODS_FIN_SALDOS_SQL_10_R_sql_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIQN = new SQLUtil(GlobalVariables.DB_HOST_BIQN, GlobalVariables.DB_USER_BIQN,GlobalVariables.DB_PASSWORD_BIQN);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "Select * from biodsfin.controlcarga WHERE ID_ODS = 1";
		
		/*************************************** PASO 1 ***************************************/	
		addStep("Verificar que se muestra la fecha en Periodo");

		System.out.println(GlobalVariables.DB_HOST_BIQN);
		System.out.println("\n" + Query);
		 
		SQLResult ExecuteQuery = executeQuery(dbBIQN, Query);
		
		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla biodsfin.controlcarga.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}			
		assertFalse(StatusQuery, "No se muestran los registros en la tabla biodsfin.controlcarga.");
		
		
		//esta ejecucion de query es extra para obtener solo el periodo y hacer la comparacion
			
		/*
		 * SQLResult ExecuteComparacion= executeQuery(dbBIQN,ObtenerPeriodo); boolean
		 * StatusQuery4 = ExecuteComparacion.isEmpty(); if (!StatusQuery4) {
		 * System.out.println("Ejecutando query para hacer la comparacion de registros"
		 * ); } else {
		 * System.out.println("No se pudo hacer la comparacion de los registros"); }
		 */

		/*************************************** PASO 2 ***************************************/	
		
		  addStep("Acceder a Control M ");
		  
			  Control_mInicio cm = new Control_mInicio(u, data.get("user"),data.get("ps"));
			  u.get(data.get("server")); u.hardWait(40); u.waitForLoadPage(); cm.logOn();
			 
		/*************************************** PASO 3 ***************************************/	
			
				  addStep("Ejecuci�n del job BIODS_FIN_SALDOS_SQL_10_R.sql");
				  
					  JSONObject obj = new JSONObject(data.get("jobs")); 
					  ControlM job = new ControlM(u, testCase, obj); 
					  boolean flag = job.searchJob(); 
					  assertTrue(flag);
					  
					  String resultado = job.executeJob();
					  System.out.println("Resultado de la ejecucion -> " + resultado);
					  System.out.println ("Valor de output :" +job.getOutput());
					  assertEquals(resultado, "Wait Condition");
					  
					  u.hardWait(3); job.closeViewpoint(); u.close();
					 
				
		/*************************************** PASO 4 ***************************************/	
			addStep("Verfificar que la fecha en el campo PERIODO haya incrementado en un mes "
					+ "a la fecha que tenia el paso 1.");
			IMATools ima = new IMATools(u,testCase);	
			
			System.out.println(GlobalVariables.DB_HOST_BIQN);
			System.out.println("\n" + Query);
			
			SQLResult ExecuteQuery2 = executeQuery(dbBIQN, Query);
			
			boolean StatusQuery2 = ExecuteQuery2.isEmpty();
			System.out.println(StatusQuery2);
			
			if (!StatusQuery2) {
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				
				System.out.println("Ejecutando query para hacer la comparacion de registros");
				String a= "periodo";
				ima.compararCampoTablas(ExecuteQuery, a, 0, ExecuteQuery2, a, 0);
			} else {
				testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla biodsfin.controlcarga.");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				System.out.println("No se pudo hacer la comparacion de los registros");
			}			
			assertFalse(StatusQuery2, "No se muestran los registros en la tabla biodsfin.controlcarga.");
			assertTrue(StatusQuery2,"Se encuentran los mismos registros, no fueron modificados despues de la ejecucion del job");
			
			
			//esta ejecucion de query es extra para obtener solo el periodo y hacer la comparacion
			/*
			 * SQLResult ExecuteComparacion2= executeQuery(dbBIQN,ObtenerPeriodo); boolean
			 * StatusQuery3 = ExecuteComparacion2.isEmpty(); if (!StatusQuery3) {
			 * System.out.println("Ejecutando query para hacer la comparacion de registros"
			 * ); //ima.compararCampoTablas(ExecuteComparacion, ExecuteComparacion2);
			 * ima.compararCampoTablas(ExecuteQuery, "PERIODO", 1, ExecuteQuery, "PERIODO",
			 * 1); } else {
			 * System.out.println("No se pudo hacer la comparacion de los registros"); }
			 * assertTrue(
			 * StatusQuery3,"Se encuentran los mismos registros, no fueron modificados despues de la ejecucion del job"
			 * );
			 */
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarCorrectaEjecucionJobBIODS_FIN_SALDOS_SQL_10_R_sql_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "MTC_FT-001_CP002_Validar correcta ejecuci�n del job BIODS_FIN_SALDOS_SQL_10_R.sql";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Team IMA";
	}

	@Override
	public String setTestInstanceID() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
// TODO Auto-generated method stub
	}

	@Override
	public void baseAfterTest() {
		u.close();
	}
}
package IMA.MigracionServiciosCashInOut.Scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import IMA.MigracionServiciosCashInOut.om.jobUtil;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**
* DESCRIPCIÓN: Realiza la ejecucion de los jobs necesarios para cargar informacion
* al reporte de analisis de margen.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-038- Validar la ejecucion de jobs para la generacion de reporte Analisis de Margen
* 
* PREREQUISITOS:
* 	- Acceso a MicroStrategy
* 	-Correr la interfaz WO01
* 	-Contar con informacion en la tabla xxfc_mpc_gl_tpc

* 
* INSUMOS:
* 	
* @author Michelle Ceja
* @date 25/Noviembre/2024
*/
public class ATC_FT_038_ValidarEjecucionJobsReporteAnalisisMargen extends BaseExecution{
	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_038_ValidarEjecucionJobsReporteAnalisisMargen_test(HashMap<String, String> data) throws SQLException, FindFailed {
		testCase.setProject_Name("I24053 - Migración servicios Cash-in y Cash-out");

		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		JSONObject array;
		boolean flag;
		ControlM job;
	
		/*************************************** PASO 1 ***************************************/	
		
		        addStep("Acceder a Control M ");
			  
				Control_mInicio cm = new Control_mInicio(u, data.get("user"),PasswordUtil.decryptPassword(data.get("ps")));
				u.get(data.get("server"));
				u.hardWait(40); 
				u.waitForLoadPage();
				cm.logOn();
				testCase.addScreenShotCurrentStep("");

		/*************************************** PASO 2 ***************************************/	
	
				  addStep("Validar la ejecución correcta del Job BI_MAP_VIEW2FACT_119_RST_040");
				  
				  array = new JSONObject(data.get("BI_MAP_VIEW2FACT_119_RST_040")); 
				   job = new ControlM(u, testCase, array); 
				   flag = job.searchJob(); 
				testCase.addScreenShotCurrentStep("");

				  assertTrue(flag);
				  String resultado = job.executeJob();
				  testCase.addScreenShotCurrentStep("");
				  
				  u.hardWait(4);

				  System.out.println("Resultado de la ejecucion -> " + resultado);
				  System.out.println ("Valor de output :" +job.getOutput());
				  assertEquals(resultado, "Ended OK");
				  testCase.addScreenShotCurrentStep("");

				  u.hardWait(3); 
				  jobUtil jobU = new jobUtil(u, testCase);
				  job.closeViewpoint();
				  jobU.selectViewPoint();
				  
				  
				 
		/*************************************** PASO 3 **************************************/

				  addStep("Validar la ejecución correcta del Job BI_MAP_FACT2FACT_SERV_RST_SEM_050");
				  array = new JSONObject(data.get("BI_MAP_FACT2FACT_SERV_RST_SEM_050")); 
				  job = new ControlM(u, testCase, array); 
				  flag = job.searchJob();
				  testCase.addScreenShotCurrentStep("");

				  assertTrue(flag);
				  resultado = job.executeJob();
				  testCase.addScreenShotCurrentStep("");

				  u.hardWait(4);

				  System.out.println("Resultado de la ejecucion -> " + resultado);
				  System.out.println ("Valor de output :" +job.getOutput());
				  testCase.addScreenShotCurrentStep("");

				  assertEquals(resultado, "Ended OK");
				  
				  job.closeViewpoint();
				  jobU.selectViewPoint();
				 
				/*************************************** PASO 4 **************************************/
	  
				  addStep("Validar la ejecución correcta del Job BI_MAP_FACT2FACT_SERV_RST_MES_060");
				  array = new JSONObject(data.get("BI_MAP_FACT2FACT_SERV_RST_MES_060")); 
				  job = new ControlM(u, testCase, array); 
				  flag = job.searchJob();
				  testCase.addScreenShotCurrentStep("");

				  assertTrue(flag);
				  resultado = job.executeJob();
				  testCase.addScreenShotCurrentStep("");

				  u.hardWait(4);

				  System.out.println("Resultado de la ejecucion -> " + resultado);
				  System.out.println ("Valor de output :" +job.getOutput());
				  testCase.addScreenShotCurrentStep("");

				  assertEquals(resultado, "Ended OK");

					
					u.close();
		
			  
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener habilitada la interfase WO01. Que ya existan transacciones ejecutadas en la tabla xxfc_mpc_gl_tpc";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se ejecuten correctamente los jobs BI_MAP_VIEW2FACT_119_RST_040, BI_MAP_FACT2FACT_SERV_RST_SEM_050 y BI_MAP_FACT2FACT_SERV_RST_MES_060";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_038_ValidarEjecucionJobsReporteAnalisisMargen_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

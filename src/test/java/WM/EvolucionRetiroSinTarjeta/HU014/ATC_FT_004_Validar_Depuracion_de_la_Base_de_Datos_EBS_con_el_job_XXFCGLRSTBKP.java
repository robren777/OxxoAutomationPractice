package WM.EvolucionRetiroSinTarjeta.HU014;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
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

public class ATC_FT_004_Validar_Depuracion_de_la_Base_de_Datos_EBS_con_el_job_XXFCGLRSTBKP extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Validar_Depuracion_de_la_Base_de_Datos_EBS_con_el_job_XXFCGLRSTBKP_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Job XXFCGLRSTBKP Folder TQA_EBS_MEX
				 * Desc:
				 * Validar Depuracion de la Base de Datos EBS con el job XXFCGLRSTBKP.sh
				 * @author Mónica Soto
				 * @date 27/02/2024
				 **/

				/*
				 * Utilerias
				 *********************************************************************/
				//BD AVEBQA
				SQLUtil AVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
											GlobalVariables.DB_PASSWORD_AVEBQA);
				/*
				 * Variables
				 ******************************************************************************************/
		
				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("Que exista la tabla FND_LOOKUP_VALUES_VL en la bd AVEBQA");
				
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				String consulta_AVEBQA = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE,-29)";
				System.out.println("Consulta: " + consulta_AVEBQA);
		
				SQLResult ExecuteQuery = executeQuery(AVEBQA, consulta_AVEBQA);
		
				boolean conexiondbAVEBQA  = true;
				conexiondbAVEBQA  = ExecuteQuery.isEmpty();
		
				addStep("Se realiza la conexión a la BD de AVEBQA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Contabilizar el total de registros a depurar en la tabla XXFC_MPC_GL_TPC despues de 29 meses");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
				
				String consulta_AVEBQA_hist = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC_HIST WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE,-26)";
				System.out.println("Consulta: " + consulta_AVEBQA_hist);
				SQLResult ExecuteQuery_hist = executeQuery(AVEBQA, consulta_AVEBQA_hist);
		
				boolean conexiondbAVEBQA_hist  = true;
				conexiondbAVEBQA_hist  = ExecuteQuery_hist.isEmpty();
				
				testCase.addTextEvidenceCurrentStep("Se contabiliza cuantos registros tiene la tabla histórica para ese mes.");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery_hist);

				assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
				assertFalse(conexiondbAVEBQA_hist, "La conexion no fue exitosa");				
				
				System.out.println(
						"*****************************************PASO 3 y 4************************************************");
				
				addStep("Ejecutar el job XXFCGLRSTBKP");
								
				SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
				String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
				Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
				u.get(data.get("server"));

				u.waitForLoadPage();
				u.waitForLoadPage();
				cm.logOn();

				JSONObject obj = new JSONObject(data.get("job")); 
				ControlM job= new ControlM(u, testCase, obj); 
				boolean flag = job.searchJob(); 
				
				assertTrue(flag);
						
				String resultado1 = job.executeJob();
				System.out.println("Resultado de la ejecucion -> " + resultado1);
				System.out.println ("Valor de output :" +job.getOutput());

				u.hardWait(5);
				assertEquals(resultado1, "Ended OK");
						
				job.closeViewpoint(); 
				addStep("Se ejecuta de manera exitosa el job XXFCGLRSTBKP");
				
				u.quit();
				
				System.out.println(
						"*****************************************PASO 5 y 6************************************************");
				String consulta_AVEBQA2 = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE,-29)";
				System.out.println("Consulta: " + consulta_AVEBQA2);
		
				SQLResult ExecuteQuery2 = executeQuery(AVEBQA, consulta_AVEBQA2);
		
				boolean conexiondbAVEBQA2  = true;
				conexiondbAVEBQA2  = ExecuteQuery2.isEmpty();
		
				addStep("Se realiza la conexión a la BD de AVEBQA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Contabilizar el total de registros a depurar en la tabla XXFC_MPC_GL_TPC despues de 29 meses, el cual ahora despues de la depuracion debe dar como resultado '0'");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				
				String consulta_AVEBQA_hist2 = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC_HIST WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE,-26)";
				System.out.println("Consulta: " + consulta_AVEBQA_hist2);
				SQLResult ExecuteQuery_hist2 = executeQuery(AVEBQA, consulta_AVEBQA_hist2);
		
				boolean conexiondbAVEBQA_hist2  = true;
				conexiondbAVEBQA_hist2  = ExecuteQuery_hist2.isEmpty();
				
				testCase.addTextEvidenceCurrentStep("Se valida que haya cambiado el valor dependiendo de los registros depurados.");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery_hist2);

				assertFalse(conexiondbAVEBQA2, "La conexion no fue exitosa");
				assertFalse(conexiondbAVEBQA_hist2, "La conexion no fue exitosa");
				
		}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestDescription() {
	
	return "Validar Depuracion de la Base de Datos EBS con el job XXFCGLRSTBKP.sh";
	}
	
	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Validar_Depuracion_de_la_Base_de_Datos_EBS_con_el_job_XXFCGLRSTBKP_test";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override
	public String setPrerequisites() {
		return "La tabla XXFC_MPC_GL_TPC debe estar creada en la BD AVEBQA\r\n";
	}
	
	@AfterMethod
	public void afterMethod() {
	
	}
}

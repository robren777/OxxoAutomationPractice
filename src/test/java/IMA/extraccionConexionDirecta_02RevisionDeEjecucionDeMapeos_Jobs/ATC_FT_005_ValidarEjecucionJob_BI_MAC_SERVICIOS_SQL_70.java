package IMA.extraccionConexionDirecta_02RevisionDeEjecucionDeMapeos_Jobs;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_005_ValidarEjecucionJob_BI_MAC_SERVICIOS_SQL_70 extends BaseExecution {
	  @Test(dataProvider = "data-provider")
	  public void ATC_005_ValidarEjecucionJob_BI_MAC_SERVICIOS_SQL_70_test(HashMap<String, String> data) throws Exception {
			SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
			utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
			
			JSONObject obj = new JSONObject(data.get("job"));

			/* ************** PASO 1 *********************************************************************/	 
			addStep("Paso 1: Ejecutar Job en Ctrl M");
			Control_mInicio CM = new Control_mInicio(u, data.get("ctrl_user"), PasswordUtil.decryptPassword(data.get("ctrl_ps")));

			addStep("Login");
			u.get(data.get("ctrl_url"));
			u.hardWait(20);
			u.waitForLoadPage();		
			CM.logOn(); // login
			testCase.addScreenShotCurrentStep(u, "Pagina Login");

			addStep("Inicio de job ");
			ControlM ctrlMJob = new ControlM(u, testCase, obj); // si es uno por uno, debes instanciar esta clase
			testCase.addScreenShotCurrentStep(u, "ctrl");
			boolean flag = ctrlMJob.searchJob();
			assertTrue(flag);

			testCase.addScreenShotCurrentStep(u, "Buscar job");

			String resultadoEjecucion = ctrlMJob.executeJob(); // ejecuta el job

			System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
			System.out.println("Output: " + ctrlMJob.getOutput());
			
			Boolean casoPasado = true;
			if(resultadoEjecucion.equals("Failure")){
				casoPasado = false;
			}
			
			assertTrue(casoPasado, "El job fall�");
			u.close();		
			
			
			/******************* validacion bd *************************************************/	
			System.out.println("Empieza query");
			String Query = "SELECT * FROM BIODSMKT.BITACORA_CARGA";
				/*	+ "where tema_id = 18" biodsmkt.bitacora_carga
					+ "and proceso = '07 MAP_TBL2ODS_SERVICIOS'"
					+ "and INICIO_FIN in ('FIN')"
					+ "and trunc(TIEMPO) = trunc(sysdate);";*/

			/**************** PASO 2 ***********************************************************/	
					
			addStep("Por medio de la siguiente consulta se verifican los datos");
			
			System.out.println(Query);		
			SQLResult connectionResult = executeQuery(dbPos, Query);

			boolean connection = false;
			if(!connectionResult.isEmpty())
				connection = true;
			
			if (!connection) {				
				testCase.addQueryEvidenceCurrentStep(connectionResult);					
			} 

			assertTrue(connection, "La tabla no contiene informaci�n.");
	  		
			
	}

		

		@Override
		public String setTestFullName() {
			// TODO Auto-generated method stub
			return "ATC_005_ValidarEjecucionJob_BI_MAC_SERVICIOS_SQL_70_test";
		}

		@Override
		public String setTestDescription() {
			// TODO Auto-generated method stub
			return "Validar la ejecucion del Job BI_MAC_SERVICIOS_SQL_70.sql";
		}

		@Override
		public String setTestDesigner() {
			// TODO Auto-generated method stub
			return "EQUIPO AUTOMATIZACION";
		}

		@Override
		public String setTestInstanceID() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void beforeTest() {
			// TODO Auto-generated method stub
		}

		@Override
		public String setPrerequisites() {
			// TODO Auto-generated method stub
			return null;
		}


	}


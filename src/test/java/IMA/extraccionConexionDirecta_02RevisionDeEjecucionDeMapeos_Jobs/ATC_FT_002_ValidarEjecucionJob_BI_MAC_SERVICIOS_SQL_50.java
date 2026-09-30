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

public class ATC_FT_002_ValidarEjecucionJob_BI_MAC_SERVICIOS_SQL_50 extends BaseExecution{
	 @Test(dataProvider = "data-provider")
  public void ATC_002_ValidarEjecucionJob_BI_MAC_SERVICIOS_SQL_50_test(HashMap<String, String> data) throws Exception {
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

		
		/* ****************** validacion bd *********************************************WHERE ROWNUM <=10****/ 	
		String Query = "select * from biodsmkt.bitacora_carga "
				 + " where tema_id = 18"
				+ " and proceso = '07 MAP_TBL2ODS_SERVICIOS'"
				+ " and INICIO_FIN in ('FIN')"
				+ " and trunc(TIEMPO) = trunc(sysdate)";
		
		String Query2 = "select * from biodsmkt.bitacora_carga "
				 + " and tema_id = 18"
				+ " and proceso ='07 MAP_ODS2FCT_SERVICIOS'"
				+ " and INICIO_FIN in ('INICIO')"
				+ " and trunc(TIEMPO) = trunc(sysdate)";

		/* ************** PASO 2 *********************************************************************/	
				
		addStep("Paso 2: Por medio de la siguiente consulta se verifican los datos query 1 ");
		
		System.out.println(Query);		
		SQLResult connectionResult = executeQuery(dbPos, Query);

		boolean connection = false;
		if(!connectionResult.isEmpty())
			connection = true;
		System.out.println("connection true");		
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);	
		} 
		
		/* ******************** PASO 3 ********************************************************/
		
		addStep("Paso 3: Por medio de la siguiente consulta se verifican los datos query 2 ");
		
		System.out.println(Query);		
		SQLResult connectionResult2 = executeQuery(dbPos, Query2);

		boolean connection2 = false;
		if(!connectionResult2.isEmpty())
			connection2 = true;
		System.out.println("connection 2 true");		
		
		if (!connection2) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult2);	
		} 
		
		assertTrue(connection2, "La tabla 2 no contiene informaci�n.");
		
}

	

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_002_ValidarEjecucionJob_BI_MAC_SERVICIOS_SQL_50_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el ejecucion del Job BI_MAC_SERVICIOS_SQL_50.sql del grupo TQABI_MAD_SERVICIOS";
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
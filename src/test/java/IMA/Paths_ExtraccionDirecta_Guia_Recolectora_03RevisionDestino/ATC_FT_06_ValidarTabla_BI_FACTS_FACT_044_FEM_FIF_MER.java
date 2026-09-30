package IMA.Paths_ExtraccionDirecta_Guia_Recolectora_03RevisionDestino;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_06_ValidarTabla_BI_FACTS_FACT_044_FEM_FIF_MER extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_06_ValidarTabla_BI_FACTS_FACT_044_FEM_FIF_MER_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT * FROM BI_FACTS.VM_044_FEM_FIF_MER";
		String queryStep2 = "SELECT * FROM RMS.TMP_FEM_FIF_STG_MER@ODS";
		String queryStep3 = "SELECT * FROM BI_FACTS.FACT_044_FEM_FIF_MER";
		String queryStep5 = "SELECT * FROM BI_FACTS.VM_044_FEM_FIF_MER";
		String queryStep6 = "SELECT * FROM BI_FACTS.FACT_044_FEM_FIF_MER";
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se valida la existencia de la VM siguiente.\n"
				+ " \n"
				+ "SELECT * FROM BI_FACTS.VM_044_FEM_FIF_MER");
		System.out.println(queryStep1);
		SQLResult Res = executeQuery(rms_mex, queryStep1);

		boolean connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 2
		addStep("Se validan los datos con los cuales ser� refrescada la vista del paso anterior, mediante la siguiente consulta.\n"
				+ " \n"
				+ "SELECT *\n"
				+ "FROM RMS.TMP_FEM_FIF_STG_MER@ODS;");
		System.out.println(queryStep2);
		Res = executeQuery(rms_mex, queryStep2);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");		
		
		// Paso 3
		addStep("Se verifica la existencia de la tabla destino y los datos que pueda contener\n"
				+ " \n"
				+ "SELECT * FROM BI_FACTS.FACT_044_FEM_FIF_MER");
		System.out.println(queryStep2);
		Res = executeQuery(rms_mex, queryStep2);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");		
		
		// Paso 4
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se verifica en la herramienta Control-M la correcta ejecuci�n del Job\n"
				+ " \n"
				+ "Job: BI_044_MAP_VM2FACT_FEM_FIF_MER_010\n"
				+ "Grupo: TQAEDW_044_GUIA_RECTORA");
		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.USER_CTRL_M_JH, GlobalVariables.PASSWORD_CTRL_M_JH);

		u.get(data.get("ctrl_url"));
		u.hardWait(20);
		u.waitForLoadPage();
		CM.logOn();

		ControlM control = new ControlM(u, testCase, obj);
		control.searchJob();
		String resultado = control.executeJob();

		System.out.println("Resultado de la ejecuci�n: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		u.close();		
		
		// Paso 5
		addStep("Se verifica la VM y los datos deben ser los mostrados en el paso 2\n"
				+ " \n"
				+ "SELECT * FROM BI_FACTS.VM_044_FEM_FIF_MER");
		System.out.println(queryStep5);
		Res = executeQuery(rms_mex, queryStep5);

		connection = false;		
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 6
		addStep("Se verifica la tabla destino, que contendr� los mismos datos que la VM\n"
				+ " \n"
				+ "SELECT * FROM BI_FACTS.FACT_044_FEM_FIF_MER;");
		System.out.println(queryStep5);
		Res = executeQuery(rms_mex, queryStep5);

		connection = false;		
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_06_ValidarTabla_BI_FACTS_FACT_044_FEM_FIF_MER";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara se carguen los datos de manera correcta a la tabla destino";
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
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

public class ATC_FT_07_ValidarActualizacion_BI_FACTS_CFG_000_CONTROL_CARGA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_07_ValidarActualizacion_BI_FACTS_CFG_000_CONTROL_CARGA_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT * FROM BI_FACTS.CFG_000_CONTROL_CARGA WHERE ID_PROYECTO='44' AND SUJETO_DESCRIPCION='GUIA RECTORA'";
		String queryStep3 = "SELECT * FROM BI_FACTS.CFG_000_CONTROL_CARGA";
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se verifica el estado de la tabla de control \n"
				+ " \n"
				+ "SELECT * FROM BI_FACTS.CFG_000_CONTROL_CARGA\n"
				+ "WHERE ID_PROYECTO='44' AND SUJETO_DESCRIPCION='GUIA RECTORA'");
		System.out.println(queryStep1);
		SQLResult Res = executeQuery(rms_mex, queryStep1);

		boolean connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informacion.");
		
		// Paso 2
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se valida la ejecucion del Job\n"
				+ " \n"
				+ "Job: BI_044_CONFIG_CTRLCARGA_EXPMER_020\n"
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
		
		// Paso 3
		addStep("Se verifica nuevamente el estado de la tabla de control \n"
				+ " \n"
				+ "SELECT * FROM BI_FACTS.CFG_000_CONTROL_CARGA");
		System.out.println(queryStep3);
		Res = executeQuery(rms_mex, queryStep3);

		connection = false;		
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informacion.");
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_07_ValidarActualizacion_BI_FACTS_CFG_000_CONTROL_CARGA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara la correcta actualizacion de la tabla de control";
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
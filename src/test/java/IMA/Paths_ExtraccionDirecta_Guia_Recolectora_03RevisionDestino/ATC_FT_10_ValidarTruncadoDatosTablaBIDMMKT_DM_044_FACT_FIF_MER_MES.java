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

public class ATC_FT_10_ValidarTruncadoDatosTablaBIDMMKT_DM_044_FACT_FIF_MER_MES extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_10_ValidarTruncadoDatosTablaBIDMMKT_DM_044_FACT_FIF_MER_MES_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT DISTINCT(MES_ID) FROM BIDMMKT.DM_044_FACT_FEM_FIF_MER_MES";
		String queryStep3 = "SELECT DISTINCT(MES_ID) FROM BIDMMKT.DM_044_FACT_FEM_FIF_MER_MES";
		
		// Paso 1
		addStep("Se obtiene el dato de los meses presentes en la tabla BIDMMKT.DM_044_FACT_FEM_FIF_MER_MES, mediante la siguiente consulta");
		System.out.println(queryStep1);
		SQLResult Res = executeQuery(fcdw4qa, queryStep1);

		boolean connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 2
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Una vez marcado el dato del mes utilizado, se ejecuta el siguiente Job. El cual realizara un truncado de los datos de la partici�n del mes seleccionado.\n"
				+ "Job: BI_044_SP_TRUNC_PART_DM_FAC_FEM_FIF_022\n"
				+ "Grupo: TQABI_MKT_EXPMER");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
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
		addStep("Una vez finalizado el Job, se realiza la siguiente consulta para verificar que no existen datos del mes seleccionado. SELECT DISTINCT(MES_ID) FROM BIDMMKT.DM_044_FACT_FEM_FIF_MER_MES");
		System.out.println(queryStep3);
		Res = executeQuery(fcdw4qa, queryStep3);

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
		return "ATC_FT_10_ValidarTruncadoDatosTablaBIDMMKT_DM_044_FACT_FIF_MER_MES";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Este Job realiza un truncado de la partici�n a insertar en la tabla destino";
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
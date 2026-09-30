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

public class ATC_FT_04_ValidarCargaTabla_RMS_TMP_FEM_FIF_STG_MER extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_ValidarCargaTabla_RMS_TMP_FEM_FIF_STG_MER_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT COUNT(*) FROM RMS.TMP_FEM_FIF_STG_MER";
		String queryStep2_1 = "SELECT * "
				+ "FORM "
				+ "RMS_XXFC.XXFC_ITEM_LOC_MERMA A, "
				+ "RMS.VM_FEM_FIF_STG_MER B "
				+ "WHERE "
				+ "A.LOC = B.STORE  AND A.ITEM = B.ITEM";
		String queryStep2_2 = "SELECT COUNT(*)  "
				+ "FORM "
				+ "RMS_XXFC.XXFC_ITEM_LOC_MERMA A, "
				+ "RMS.VM_FEM_FIF_STG_MER B "
				+ "WHERE "
				+ "A.LOC = B.STORE  AND A.ITEM = B.ITEM";
		
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se verifica el estado y los posibles datos presentes en la tabla destino");
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
		addStep("Se verifican los datos que ser�n insertados en la vista mediante las siguientes consultas.");
		System.out.println(queryStep2_1);
		Res = executeQuery(rms_mex, queryStep2_1);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		System.out.println(queryStep2_2);
		Res = executeQuery(rms_mex, queryStep2_2);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		
		// Paso 3
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se verifica en Control-M la correcta ejecuci�n del Job que realiza el refrescado de la tabla");
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
		
		// Paso 4
		addStep("Se verifican los datos de la VM actualizada, que deben corresponde con los citados en el paso 2");
		System.out.println(queryStep1);
		Res = executeQuery(rms_mex, queryStep1);

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
		return "ATC_FT_04_ValidarCargaTabla_RMS_TMP_FEM_FIF_STG_MER";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara la correcta carga de informaci�n a la tabla RMS.TMP_FEM_FIF_STG_MER";
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
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

public class ATC_FT_12_ValidarActualizacionTabla_RMS_TMP_FEM_FIF_STG_MER_ODS extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATFC_FT_12_ValidarActualizacionTabla_RMS_TMP_FEM_FIF_STG_MER_ODS_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT * FROM RMS.TMP_FEM_FIF_STG_MER";
		String queryStep2 = "SELECT COUNT(*) FROM RMS.VM_FEM_FIF_STG_INV_MERMA, RMS_XXFC.XXFC_ITEM_LOC_MERMA "
				+ "where (1=1) And (VM_FEM_FIF_STG_INV_MERMA.ITEM = XXFC_ITEM_LOC_MERMA.ITEM "
				+ "AND VM_FEM_FIF_STG_INV_MERMA.STORE = XXFC_ITEM_LOC_MERMA.LOC)";
		String queryStep4 = "SELECT COUNT(*) FROM RMS.TMP_FEM_FIF_STG_MER";
		
		// Paso 1
		addStep("Se valida que el estado de la tabla ODS. SELECT * FROM RMS.TMP_FEM_FIF_STG_MER");
		System.out.println(queryStep1);
		SQLResult Res = executeQuery(rms_mex, queryStep1);

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
		addStep("Se verifican los datos que se pasaran a la tabla destino RMS.TMP_FEM_FIF_STG_MER\n"
				+ "SELECT COUNT(*) FROM RMS.VM_FEM_FIF_STG_INV_MERMA, RMS_XXFC.XXFC_ITEM_LOC_MERMA \n"
				+ "where (1=1) And (VM_FEM_FIF_STG_INV_MERMA.ITEM = XXFC_ITEM_LOC_MERMA.ITEM \n"
				+ "AND VM_FEM_FIF_STG_INV_MERMA.STORE = XXFC_ITEM_LOC_MERMA.LOC)");
		System.out.println(queryStep2);
		Res = executeQuery(rms_mex, queryStep2);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 3
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se verifica la correcta ejecuci�n en Control-M del siguiente Job en ambiente de QA\n"
				+ "GRUPO: TQAODS_RMS_TRAN_DIA\n"
				+ "JOB: ODS_MAP_VM2TMP_FEM_FIF_STG_MER_040");
		
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
		
		// Paso 4
		addStep("Se valida que se haya hecho el pase de los datos a la tabla destino. SELECT COUNT(*) FROM RMS.TMP_FEM_FIF_STG_MER");
		System.out.println(queryStep4);
		Res = executeQuery(rms_mex, queryStep4);

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
		return "ATFC_FT_12_ValidarActualizacionTabla_RMS_TMP_FEM_FIF_STG_MER_ODS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la correcta actualizacion de la tabla RMS.TMP_FEM_FIF_STG_MER;  en el ambiente ODS";
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
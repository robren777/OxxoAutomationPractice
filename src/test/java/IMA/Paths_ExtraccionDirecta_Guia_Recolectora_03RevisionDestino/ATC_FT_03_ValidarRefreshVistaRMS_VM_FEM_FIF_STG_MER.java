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

public class ATC_FT_03_ValidarRefreshVistaRMS_VM_FEM_FIF_STG_MER extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_03_ValidarRefreshVistaRMS_VM_FEM_FIF_STG_MER_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT COUNT(*) FROM RMS.VM_FEM_FIF_STG_MER";
		String queryStep2_1 = "SELECT "
				+ "A.ITEM, A.ITEM_DESC, A.DIVISION,A.GROUPS,A.DEPT,A.CLASS,A.SUBCLASS,A.STORE,A.WH,A.TRANSACTION_ID,A.TRAN_DATE,A.TRAN_CODE, A.TOTAL_COST,A.TOTAL_RETAIL, "
				+ "A.UNIT_COST, A.UNIT_RETAIL,A.UNITS,A.REASON_CODE,A.SALES_TYPE,A.VAT_RATE,A.VAT_CODE,A.VAT_CODE_DESC,A.FROM_LOC,A.FROM_LOC_TYPE,A.TO_LOC,A.TO_LOC_TYPE, "
				+ "A.EXCHANGE_RATE,A.CURRENCY_CODE,A.CUSTOMER_ID,A.REFERENCE_1,A.REFERENCE_2,A.REFERENCE_3,A.REFERENCE_4,A.REFERENCE_5,A.REFERENCE_6,A.REFERENCE_7, "
				+ "A.REFERENCE_8,A.REFERENCE_9,A.REFERENCE_10,A.REFERENCE_11,A.INV_ADJ_USER_ID,A.LAST_UPDATE_USER_ID,A.LAST_UPDATE_DATETIME,A.SUPPLIER_TYPE,A.VAT_RATE_REGION, "
				+ "A.CR_PLAZA,A.VAT_CODE_COST,A.VAT_CODE_DESC_COST,A.VAT_RATE_COST,A.ID,A.MERCH_TYPE, SYSDATE "
				+ "FROM RMS.FEM_FIF_STG A , RMS.CFG_000_CONTROL_CARGA B "
				+ "WHERE B.ID_PROYECTO = 44 AND B.SUJETO_DESCRIPCION='GUIA RECTORA' AND A.TRAN_CODE = 22 AND A.REASON_CODE IN(514, 855) "
				+ "AND A.TRAN_DATE BETWEEN TRUNC(B.FECHAINICIO) AND TRUNC(B.FECHAFIN)";
		String queryStep2_2 = "SELECT "
				+ "COUNT(*) "
				+ "FROM RMS.FEM_FIF_STG A , RMS.CFG_000_CONTROL_CARGA B "
				+ "WHERE B.ID_PROYECTO = 44 AND B.SUJETO_DESCRIPCION='GUIA RECTORA' AND A.TRAN_CODE = 22 AND A.REASON_CODE IN(514, 855) "
				+ "AND A.TRAN_DATE BETWEEN TRUNC(B.FECHAINICIO) AND TRUNC(B.FECHAFIN)";
		
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se verifica el estado de la vista antes de la ejecuci�n del Job, as� como los datos que pudieran estar contenidos en la misma");
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
		addStep("Se verifican los datos que ser�n insertados en la vista mediante las siguientes consultas");
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

		addStep("Se verifica en Control-M la correcta ejecuci�n del Job que realiza el refrescado de la tabla. Job: ODS_REFRESH_VM_FEM_FIF_STG_MER_020, Grupo: TQAODS_RMS_TRANS_DIA");
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
		return "ATC_FT_03_ValidarRefreshVistaRMS_VM_FEM_FIF_STG_MER";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se verificara que el proceso relacionado realiza el correcto refresh de la vista";
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
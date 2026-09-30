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

public class ATC_FT_01_ValidarCargaTablaRMS_XXFC_XXFC_ITEM_LOC_MERMA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_ValidarCargaTablaRMS_XXFC_XXFC_ITEM_LOC_MERMA_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT COUNT(*) FROM XXFC.XXFC_ITEM_LOC_MERMA";
		String queryStep2 = "SELECT * FROM RMS_XXFC.XXFC_ITEM_LOC_MERMA";
		String queryStep4 = "SELECT COUNT(*) FROM RMS_XXFC.XXFC_ITEM_LOC_MERMA";
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se valida que la tabla origen contenga datos para ser replicados al ambiente de ODS");
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
		addStep("Se verifica el estado de la tabla destino en FCODSQA mediante la consulta");
		System.out.println(queryStep2);
		Res = executeQuery(fcodsqa, queryStep2);

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

		addStep("Se verifica la correcta ejecuci�n en Control-M del siguiente Job en ambiente de QA. Job: ODS_MAP_RMS2XXFC_ITEM_LOC_MER_CDC_020, Grupo: TQAODS_RMS_TRANS_DIA");
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
		addStep("Se verifica nuevamente la tabla destino para revisar que se han realizado la replica de manera correcta");
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
		return "ATC_FT_01_ValidarCargaTablaRMS_XXFC_XXFC_ITEM_LOC_MERMA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara la correcta carga de la tabla destino RMS_XXFC.XXFC_ITEM_LOC_MERMA en ambiente de ODS";
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
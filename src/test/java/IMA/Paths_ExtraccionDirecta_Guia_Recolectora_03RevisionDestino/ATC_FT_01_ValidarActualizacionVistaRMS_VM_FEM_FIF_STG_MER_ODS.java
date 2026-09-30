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

public class ATC_FT_01_ValidarActualizacionVistaRMS_VM_FEM_FIF_STG_MER_ODS extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_ValidarActualizacionVistaRMS_VM_FEM_FIF_STG_MER_ODS_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT * FROM RMS.CFG_000_CONTROL_CARGA WHERE ID_PROYECTO = 44 AND SUJETO_DESCRIPCION='GUIA RECTORA'";
		String queryStep2 = "UPDATE TABLE RMS.CFG_000_CONTROL_CARGA SET FECHAINICIO='', FECHAFIN='' WHERE ID_PROYECTO = 44 AND SUJETO_DESCRIPCION='GUIA RECTORA'";
		String queryStep3 = "SELECT * FROM RMS.CFG_000_CONTROL_CARGA WHERE ID_PROYECTO = 44 AND SUJETO_DESCRIPCION='GUIA RECTORA'";
		String queryStep4 = "SELECT COUNT(*) FROM RMS.VM_FEM_FIF_STG_MER";
		String queryStep5_1 = "SELECT * FROM RMS.FEM_FIF_STG_INVENTARIOS";
		String queryStep5_2 = "SELECT * FROM RMS.CFG_000_CONTROL_CARGA";
		String queryStep7 = "SELECT COUNT(*) FROM RMS.VM_FEM_FIF_STG_MER";	
		
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se validan la fechas en la tabla RMS.CFG_000_CONTROL_CARGA que correspondan al proyecto");
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
		addStep("Actualizar en la tabla los campos FECHA_INICIO y FECHA_FIN en la tabla RMS.CFG_000_CONTROL_CARGA");
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
		addStep("Se validan la fechas en la tabla RMS.CFG_000_CONTROL_CARGA que correspondan al proyecto despues de la actualizacion de las fechas inicio y fin");
		System.out.println(queryStep3);
		Res = executeQuery(rms_mex, queryStep3);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 4
		addStep("Se valida que la tabla destino exista en ODS");
		System.out.println(queryStep4);
		Res = executeQuery(fcodsqa, queryStep4);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 5
		addStep("Se valida que las tablas origen existan y tengan datos");
		System.out.println(queryStep5_1);
		Res = executeQuery(rms_mex, queryStep5_1);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		System.out.println(queryStep5_2);
		Res = executeQuery(rms_mex, queryStep5_2);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 6
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se verifica la correcta ejecucion en Control-M del siguiente Job en ambiente de QA. GRUPO: TQAODS_RMS_TRANS_BAJODEMANDA, JOB: ODS_REFRESH_VM_FEM_FIF_STG_MERMA_050");
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
		
		
		// Paso 7
		addStep("Se verifica que se haya hecho la actualizacion de la tabla RMS.VM_FEM_FIF_STG_MER");
		System.out.println(queryStep7);
		Res = executeQuery(rms_mex, queryStep7);

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
		return "ATC_FT_01_ValidarActualizacionVistaRMS_VM_FEM_FIF_STG_MER_ODS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la correcta actualizacion de la vista RMS.VM_FEM_FIF_STG_MER en el ambiente ODS";
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
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

public class ATC_FT_02_ValidarActualizacion_RMS_CFG_000_CONTROL_CARGA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_02_ValidarActualizacion_RMS_CFG_000_CONTROL_CARGA_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT * FROM RMS.CFG_000_CONTROL_CARGA WHERE ID_PROYECTO='44'";
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		
		// Paso 1
		addStep("Se verifica la tabla antes de la ejecuci�n del proceso");
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
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se verifica en Control-M la correcta ejecucion del Job. Job: ODS_CONFIG_CTRLCARGA_EXPMER_RMS_010, Grupo: TQAODS_RMS_TRANS_DIA");
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
		addStep("Una vez terminada la ejecucion del Job, se verifica nuevamente la tabla de control y se observa la actualizaci�n del registro para los campos FECHAINICIO y FECHAFIN");
		System.out.println(queryStep1);
		Res = executeQuery(rms_mex, queryStep1);

		connection = false;		
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		else {
			String fecha_ini = Res.getData(0, "FECHAINICIO");
			String fecha_fin = Res.getData(0, "FECHAFIN");
			String fechas = "FECHAINICIO: " + fecha_ini + " FECHAFIN: " + fecha_fin;
			testCase.addTextEvidenceCurrentStep(queryStep1);
		}
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_02_ValidarActualizacion_RMS_CFG_000_CONTROL_CARGA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se verificara la correcta actualizacion de la tabla de control RMS.CFG_000_CONTROL_CARGA para el indicador de Guia Rectora";
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
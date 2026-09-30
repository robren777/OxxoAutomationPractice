package IMA.Paths_ExtraccionDirecta_InventarioOptimoEnBodega;

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

public class ATC_FT_07_ValidarEjecucionMapaMAP_FCT_RO extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_07_ValidarEjecucionMapaMAP_FCT_RO_test(HashMap<String, String> data) throws Exception {
		
		String step2 = "select * from bidmmkt.fact_res_operativo_diario where tiempo_key>00000000";
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BIMTTO, GlobalVariables.DB_USER_BIMTTO, GlobalVariables.DB_PASSWORD_BIMTTO);
		
		// Paso 1
		JSONObject obj = new JSONObject(data.get("job"));


		addStep("Se valida la correcta ejecución del Job BI_MCD_RESUMEN_OPERATIVO_065.sh del grupo TQABI_MCD_RESUMEN_OPERATIVO");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		Control_mInicio CM = new Control_mInicio(u, data.get("ctrl_user"),data.get("passwd"));
		testCase.addScreenShotCurrentStep(u, "CTRL M");

		u.get(data.get("ctrl_url"));
		u.hardWait(20);
		u.waitForLoadPage();
		CM.logOn();

		ControlM control = new ControlM(u, testCase, obj);
		control.searchJob();
		String resultado = control.executeJob();
		
		testCase.addScreenShotCurrentStep(u, "Finalizado");


		System.out.println("Resultado de la ejecución: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		u.close();
		
		// Paso 2

		addStep("Se valida la correcta inserción realizada por el mapeo a la tabla  bidmmkt.fact_res_operativo_diario");
		System.out.println(step2);		
		SQLResult connectionResult = executeQuery(dbBi, step2);
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;

		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_07_ValidarEjecucionMapaMAP_FCT_RO_test";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la correcta ejecucion del Job BI_MCD_RESUMEN_OPERATIVO_065.sh del grupo TQABI_MCD_RESUMEN_OPERATIVO";
	}

	@Override
	public String setTestDesigner() {
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

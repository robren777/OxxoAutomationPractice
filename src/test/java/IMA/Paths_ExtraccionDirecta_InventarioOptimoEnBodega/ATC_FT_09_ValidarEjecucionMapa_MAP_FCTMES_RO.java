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

public class ATC_FT_09_ValidarEjecucionMapa_MAP_FCTMES_RO extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_09_ValidarEjecucionMapa_MAP_FCTMES_RO_test(HashMap<String, String> data) throws Exception {
		
		String step1 = "(SELECT * "
				+ "FROM "
				+ "    \"BIDMMKT\".\"FACT_RES_OPERATIVO_DIARIO\"  \"FACT_RES_OPERATIVO_DIARIO_1\"   "
				+ " JOIN   \"BIODSMKT\".\"HIST_RES_OP_CTRL_MES\"  \"HIST_RES_OP_CTRL_MES\" ON ( (( TO_DATE ( TO_CHAR ( \"FACT_RES_OPERATIVO_DIARIO_1\".\"TIEMPO_KEY\" ) , 'YYYYMMDD' ) = \"HIST_RES_OP_CTRL_MES\".\"ULTIMA_FECHA_RO\" )) AND (( \"FACT_RES_OPERATIVO_DIARIO_1\".\"TIENDA_KEY\" = \"HIST_RES_OP_CTRL_MES\".\"ID_TIENDA\" )) ) "
				+ " JOIN   \"BIODSMKT\".\"CTRL_CARGA_PERIODOS\"  \"CTRL_CARGA_PERIODOS\" ON ( 1=1 ) "
				+ "  WHERE "
				+ "  ( \"CTRL_CARGA_PERIODOS\".\"ID_SUJETO\" = 12 )  AND "
				+ "  ( \"HIST_RES_OP_CTRL_MES\".\"TIPO_PERIODO\" = 'M' ) "
				+ "        )";
		String step3 = "select * from  biodsmkt.temp_mov_ro_mes_hoy";
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BIMTTO, GlobalVariables.DB_USER_BIMTTO, GlobalVariables.DB_PASSWORD_BIMTTO);
		
		// Paso 1
		addStep("Se validan los datos a ser insertados en la tabla biodsmkt.temp_mov_ro_mes_hoy "+step1);
		System.out.println(step1);		
		SQLResult connectionResult = executeQuery(dbBi, step1);
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		// Paso 2
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se valida la correcta ejecución del Job BI_MCD_RESUMEN_OPERATIVO_085.sh del grupo TQABI_MCD_RESUMEN_OPERATIVO");
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
		
		// Paso 3
		addStep("Se validan los datos insertados por el mapeo a la tabla, mediante el siguiente query "+step3);
		System.out.println(step3);		
		connectionResult = executeQuery(dbBi, step3);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_09_ValidarEjecucionMapa_MAP_FCTMES_RO_test";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la correcta ejecucion del Job BI_MCD_RESUMEN_OPERATIVO_085.sh del grupo TQABI_MCD_RESUMEN_OPERATIVO";
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

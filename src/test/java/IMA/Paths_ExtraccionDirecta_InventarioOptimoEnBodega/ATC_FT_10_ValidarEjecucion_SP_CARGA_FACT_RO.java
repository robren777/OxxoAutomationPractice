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

public class ATC_FT_10_ValidarEjecucion_SP_CARGA_FACT_RO extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_10_ValidarEjecucion_SP_CARGA_FACT_RO_test(HashMap<String, String> data) throws Exception {
		String step1 = "SELECT * "
				+ "FROM (SELECT TIENDA_KEY, MAX (LOADDATE) FECHA_CARGA "
				+ "      FROM \"BIODSMKT\".\"TEMP_HOY_RO_SEM\" "
				+ "      GROUP BY TIENDA_KEY) B, "
				+ "      \"BIODSMKT\".\"TEMP_HOY_RO_SEM\" A, "
				+ "      BIODSMKT.CTRL_CARGA_PERIODOS CP "
				+ "WHERE A.TIENDA_KEY = B.TIENDA_KEY AND A.LOADDATE = B.FECHA_CARGA "
				+ "      AND A.SEMANA_ID BETWEEN CP.ID_PERIODO_INICIO "
				+ "      AND CP.ID_PERIODO_FIN AND ID_SUJETO = 13";
		String step2 = "SELECT * "
				+ "FROM (  SELECT TIENDA_KEY, MAX (LOADDATE) FECHA_CARGA "
				+ "        FROM \"BIODSMKT\".\"TEMP_MOV_RO_MES_HOY\" "
				+ "        GROUP BY TIENDA_KEY) B, "
				+ "        \"BIODSMKT\".\"TEMP_MOV_RO_MES_HOY\" A, "
				+ "        BIODSMKT.CTRL_CARGA_PERIODOS CP "
				+ "WHERE   A.TIENDA_KEY = B.TIENDA_KEY "
				+ "        AND A.LOADDATE = B.FECHA_CARGA "
				+ "        AND A.MES_ID BETWEEN CP.ID_PERIODO_INICIO AND CP.ID_PERIODO_FIN "
				+ "        AND ID_SUJETO = 12";
		String step4 = "select * from BIDMMKT.FACT_RES_OPERATIVO_SEMANA";
		String step5 = "select * from BIDMMKT.FACT_RES_OPERATIVO_MES";
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BIMTTO, GlobalVariables.DB_USER_BIMTTO, GlobalVariables.DB_PASSWORD_BIMTTO);
		
		// Paso 1
		addStep("Se verifican los datos a ser insertados en la tabla destino BIDMMKT.FACT_RES_OPERATIVO_SEMANA, mediante el Query "+step1);
		System.out.println(step1);		
		SQLResult connectionResult = executeQuery(dbBi, step1);
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		// Paso 2
		addStep("Se verifican los datos a ser insertados en la tabla destino BIDMMKT.FACT_RES_OPERATIVO_MES, mediante el Query "+step2);
		System.out.println(step2);		
		connectionResult = executeQuery(dbBi, step2);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		// Paso 3
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se validara la ejecución correcta del SP BIETL.CARGA_FACT_RO . "
				+ "Por medio del Job BI_MCD_RESUMEN_OPERATIVO_095.sh del grupo BI_MCD_RESUMEN_OPERATIVO");
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
		
		// Paso 4
		addStep("Se verifican la inserción de la información a nivel semana en la tabla, mediante el siguiente query "+step4);
		System.out.println(step4);		
		connectionResult = executeQuery(dbBi, step4);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		// Paso 5
		addStep("Se verifican la inserción de la información a nivel semana en la tabla "+step5);
		System.out.println(step5);		
		connectionResult = executeQuery(dbBi, step5);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene información.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_10_ValidarEjecucion_SP_CARGA_FACT_RO_test";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la correcta ejecucion del Job BI_MCD_RESUMEN_OPERATIVO_095.sh del grupo  BI_MCD_RESUMEN_OPERATIVO, que ejecuta el SP CARGA_FACT_RO";
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

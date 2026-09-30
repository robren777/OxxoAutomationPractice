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

public class ATC_FT_08_ValidarEjecucionMapa_MAP_FCTSEM_RO_ extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_08_ValidarEjecucionMapa_MAP_FCTSEM_RO_Test(HashMap<String, String> data) throws Exception {
		
		String step1 = "SELECT * FROM "
				+ "    \"BIODSMKT\".\"HIST_RES_OP_CTRL_SEM\"  \"HIST_RES_OP_CTRL_SEM\" "
				+ " JOIN   \"BIODSMKT\".\"CTRL_CARGA_PERIODOS\"  \"CTRL_CARGA_PERIODOS\" ON ( ( \"HIST_RES_OP_CTRL_SEM\".\"ID_TIPO_PERIODO\" BETWEEN \"CTRL_CARGA_PERIODOS\".\"ID_PERIODO_INICIO\" AND \"CTRL_CARGA_PERIODOS\".\"ID_PERIODO_FIN\" ) ) "
				+ " JOIN   \"BIDMMKT\".\"FACT_RES_OPERATIVO_DIARIO\"  \"FACT_RES_OPERATIVO_DIARIO\" ON ( (( \"FACT_RES_OPERATIVO_DIARIO\".\"TIENDA_KEY\" = \"HIST_RES_OP_CTRL_SEM\".\"ID_TIENDA\" )) AND (( TO_DATE ( TO_CHAR ( \"FACT_RES_OPERATIVO_DIARIO\".\"TIEMPO_KEY\" ) , 'YYYYMMDD' ) = \"HIST_RES_OP_CTRL_SEM\".\"ULTIMA_FECHA_RO\" )) ) "
				+ "  WHERE "
				+ "  ( \"CTRL_CARGA_PERIODOS\".\"ID_SUJETO\" = 13 )  AND "
				+ "  ( \"HIST_RES_OP_CTRL_SEM\".\"TIPO_PERIODO\" = 'S' )";
		String step3 = "SELECT * FORM BIODSMKT.TEMP_HOY_RO_SEM WHERE TIENDA_KEY = '%s'";
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BIMTTO, GlobalVariables.DB_USER_BIMTTO, GlobalVariables.DB_PASSWORD_BIMTTO);
		
		// Paso 1
		addStep("Se verifican los datos a ser insertados a la tabla destino  biodsmkt.temp_hoy_ro_sem mediante el siguiente Query "+step1);
		System.out.println(step1);		
		SQLResult connectionResult = executeQuery(dbBi, step1);
		String tienda_key = connectionResult.getData(0, "TIENDA_KEY");
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		// Paso 2
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se valida la correcta ejecuci�n del Job BI_MCD_RESUMEN_OPERATIVO_075.sh del grupo TQABI_MCD_RESUMEN_OPERATIVO");
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

		System.out.println("Resultado de la ejecuci�n: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		u.close();
		
		// Paso 3
		step3 = String.format(step3, tienda_key);		
		addStep("Se validan los datos insertados por el mapeo a la tabla, mediante el siguiente query "+step3);
		System.out.println(step3);		
		connectionResult = executeQuery(dbBi, step3);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_08_ValidarEjecucionMapa_MAP_FCTSEM_RO";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la correcta ejecuci�n del Job BI_MCD_RESUMEN_OPERATIVO_075.sh del grupo TQABI_MCD_RESUMEN_OPERATIVO";
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
package IMA.ods01;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class MTC_FT_012_MapeoTablaFact extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_MapeoTablaFact_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		
		IMATools ima = new IMATools(u,testCase);	
		String SumarizadoDD = "SELECT * FROM BIDMMKTCOL.FACT_109_REC_DEV_CPRAS_DD";
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Ingresar a OWB");
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar la existencia del mapeo ");
		
		/* PASO 3 *********************************************************************/	

		addStep("Ejecutar la siguiente secuencia de jobs en Control-M para el mapeo de la tabla de sumarizado diario");

		JSONObject obj = new JSONObject(data.get("query"));
		Control_mInicio CM = new Control_mInicio(u, data.get("ctrlM_user"), data.get("ctrlM_passwd"));
	
		addStep("Login");
		u.get(data.get("ctrlM_url"));
		u.hardWait(20);
		u.waitForLoadPage();
		CM.logOn();
	
		addStep("Inicio de job");
		ControlM control = new ControlM(u, testCase, obj);
	
		control.searchJob();	
		String resultado = control.executeJob();
	
		System.out.println("Resultado de la ejecuci�n: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		
		/* PASO 4 *********************************************************************/	

		addStep("Validar la inserci�n de registros en la tabla de sumarizado por d�a");
		
		System.out.println(SumarizadoDD);		
		SQLResult connectionResult = executeQuery(dbBi, SumarizadoDD);

		boolean connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;

		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		/* PASO 5 *********************************************************************/	

		addStep("Validar en los registros insertados que se aplique correctamente la suma de los montos de recepciones y devoluciones por d�a");

		/* PASO 6 *********************************************************************/	

		addStep("Validar que los registros insertados correspondan al rango de fechas que existen en las tablas de ODS");
		
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QA Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
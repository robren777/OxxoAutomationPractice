package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_365_Confirmar_cambios_del_JOB_IMPORT_DIM_TIENDA extends BaseExecution{
SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_365_Confirmar_cambios_del_JOB_IMPORT_DIM_TIENDA_test(HashMap<String, String> data) throws SQLException, FindFailed {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil FCDW4QA = new SQLUtil("10.184.80.26:1535/FCDW4QA.FEMCOM.NET", "GUIARECTORA_WEB", "GUIARECTORA_WEB");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String query = "select ID_SEGMENTO_MAESTRO, ID_POTENCIAL_HOGAR, "
				+ "ID_POTENCIAL_COMIDA, "
				+ "ID_POTENCIAL_DESAYUNO, ID_OVERLAY_HOGAR, "
				+ "ID_OVERLAY_COMIDA, ID_OVERLAY_DESAYUNO, "
				+ "ID_CONCEPTO_DIFERENCIADOR from DWADMIN.DIM_TIENDA WHERE rownum <= 10";
		
		// Paso 1
		addStep("Usuario accede donde se ejecuta el JOB IMPORT DIM_TIENDA");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		/*
		Control_mInicio CM = new Control_mInicio(u, data.get("ctrl_user"),data.get("passwd"));
		testCase.addScreenShotCurrentStep(u, "CTRL M");
		
		// Paso 2
		addStep("Usuario ejecuta el JOB IMPORT DIM_TIENDA");

		for(int i = 0; i < 9; i++ )
		{
			String job = "job" + Integer.toString(i+1);
			JSONObject obj = new JSONObject(data.get(job));
			u.get(data.get("ctrl_url"));
			u.hardWait(10);
			u.waitForLoadPage();
			CM.logOn();
	
			ControlM control = new ControlM(u, testCase, obj);
			control.searchJob();
			String resultado = control.executeJob();
			
			testCase.addScreenShotCurrentStep(u, "Finalizado");
	
			System.out.println("Resultado de la ejecuci�n: "+resultado);
			System.out.println("Valor de output : "+control.getOutput());
		}*/
		
		// Paso 2		
		//SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        Control_mInicio CM = new Control_mInicio(u, data.get("ctrl_user"),data.get("passwd"));        

        //PASO 2 Ingresar a Control-M --> y ejecutar el job "XXPE_CE_KYRIBA_IN_SH_PERU"
        addStep("Ingresar a Control-M --> y ejecutar la cadena de jobs");
        //testCase.addTextEvidenceCurrentStep(obj.getString("job"));
        u.get("http://10.184.56.194:18080/ControlM/");
        u.hardWait(30);
        u.waitForLoadPage();
        CM.logOn();        
        
        testCase.addScreenShotCurrentStep(u, "control m inicio");
        
        for(int i=0;i<10;i++)
        {
        	String tmp = "job" + Integer.toString(i+1);
	        JSONObject obj = new JSONObject(data.get(tmp));
	        ControlM controlM = new ControlM(u, testCase, obj);
	        boolean flag = controlM.searchJob();
	        assertFalse(!flag, "No se encontro el job: " + tmp);
	
	        System.out.println("Job Status " + controlM.getCurrentStatus());
	        //Ejecucion
	        String resultadoDelJon = controlM.executeJob();
	        testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJon);
	        System.out.println("Resultado de la ejecuci�n " + resultadoDelJon);
	        
	        //Valor del output
	        testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
	        System.out.println("Output " + controlM.getOutput());
	
	        //Validacion del caso
	        boolean testResult = true;
	        if (resultadoDelJon.equals("Failure")) {
	            testResult = false;
	            testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
	            System.out.println("Fallo la ejecuci�n del job ");
	        }
	
	        assertTrue(testResult, "La ejecucion del job fue exitosa");
	        controlM.closeViewpoint();
        }
		
		// Paso 3
		addStep("Sistema extraer de CNT los campos que componen el Segmento de tienda los cuales son Segmento Maestro, Potencial Hogar, Potencial Comida, Potencial Desayuno, Overlay Hogar, Overlay Comida, Overlay Desayuno y el concepto diferenciador");
		System.out.println(query);
		SQLResult ExecuteQuery = executeQuery(FCDW4QA, query);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		// Paso 4
		addStep("Usuario confirma que se muestre en el JOB las columnas correspondientes a segmento de tienda y concepto diferenciador dentro de DIM_TIENDA");
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
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_365_Confirmar_cambios_del_JOB_IMPORT_DIM_TIENDA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}
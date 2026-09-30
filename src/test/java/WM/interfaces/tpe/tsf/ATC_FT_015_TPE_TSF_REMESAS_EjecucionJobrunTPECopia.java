package WM.interfaces.tpe.tsf;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_015_TPE_TSF_REMESAS_EjecucionJobrunTPECopia extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_TPE_TSF_REMESAS_EjecucionJobrunTPECopia_test(HashMap<String, String> data) throws Exception {
		
		/**
		 * Informacion de Remesas en IMA
		 * CP: Ejecucion del job para CP :MTC-FT-083-Revision Concatenaci�n BD Remesas
		 * 
		 */
		

/**
* Variables
* ******************************************************************************************
* 
*/

		
		testCase.setTest_Description(data.get("id") + data.get("descripcion"));
		testCase.setProject_Name("Limite Dispersion Remesas");
		testCase.setPrerequisites("prerequisitos");


/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
*
* 
*/
//*************************************Ejecucion de job***********************************************	
		
  addStep("Ejecutar job runTPECopia en Ctrl-M ");
		
   SeleniumUtil u = new SeleniumUtil(new ChromeTest());
	
	JSONObject obj = new JSONObject(data.get("job"));

	testCase.addBoldTextEvidenceCurrentStep("Ejecucion  Control M ");
	Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
	//testCase.addPaso("Paso con addPaso");

	u.get(data.get("server"));
	u.hardWait(50);
	u.waitForLoadPage();
	CM.logOn(); 

	//Buscar del job
	
	ControlM control = new ControlM(u, testCase, obj);
	boolean flag = control.searchJob();
	assertTrue(flag);
	
	//Ejecucion
	String resultado = control.executeJob();
	System.out.println("Resultado de la ejecucion -> " + resultado);

	u.hardWait(40);
	
	//Valor del output 
	

	String Res2 = control.getNewStatus();
	

	System.out.println ("Valor de output getNewStatus:" +Res2);
	
	
	String output = control.getOutput();
	System.out.println ("Valor de output control:" +output);
	
	testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+Res2);
	testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
	//Validacion del caso
	Boolean casoPasado = false;
	
	if(Res2.equals("Wait Condition")) {
	casoPasado = true;
	}		
	
	control.closeViewpoint();
	u.hardWait(40);
	
	u.close();
	assertTrue(casoPasado);
	


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo automatizacion";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}

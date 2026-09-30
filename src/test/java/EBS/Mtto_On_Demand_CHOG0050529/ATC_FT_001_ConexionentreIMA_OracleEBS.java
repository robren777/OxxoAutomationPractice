package EBS.Mtto_On_Demand_CHOG0050529;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ConexionentreIMA_OracleEBS extends BaseExecution{

	@Test(dataProvider = "data-provider")
	
	/**
	 * DESCRIPCION: Conexión entre IMA
	 * 
	 * PREREQUISITOS:
	 * Acceso a Control M
	 *
	 * INSUMOS:
	 * - IDSolicitud 
	 *
	 * @author Michelle Jesús Vásquez Vivar
	 * @date 01/Marzo/2025
	 */
	
	public void ATC_FT_001_ConexionentreIMA_OracleEBS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Mtto_On_Demand");
	  /*
	   * VARIABLES
	   */
	  String controlMUser = data.get("controlMUser");
	  String controlMPass = PasswordUtil.decryptPassword(data.get("controlMPass"));
	  String controlMServer = data.get("controlMServer");
	  
	  /*
	   * UTILERIAS
	   */	  	  
	  SeleniumUtil u = new SeleniumUtil(new ChromeTest());

	    addStep("1.	Ingresar a la primera interfaz de Interfaz_EBS2Snow desde la interface de Data Stage desde Control M");
	    System.out.println("PASO 1");
	     
	    JSONObject objSnowEBS = new JSONObject(data.get("BIDS_MAIN_Interfaz_Snow2EBS")); 
	    Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPass);
	    u.get(controlMServer);
	    u.waitForLoadPage();
	    testCase.addScreenShotCurrentStep("Login");
	    CM.logOn(); // loguea
	    u.hardWait(15);
	    
	    addStep("2.	Ejecutar el job MAIN_Interfaz_EBS2Snow");
	    ControlM jSnowEBS = new ControlM(u, testCase, objSnowEBS); // Clase instanciada para job Interfaz_Snow2EBS
	    jSnowEBS.searchJob(); // busca el job Interfaz_Snow2EBS
	    addStep("Validar estatus");
	    String resultadoEjecucionSnow2EBS = jSnowEBS.executeJob(); // ejecuta el job BIDS_MAIN_Interfaz_Snow2EBS
	    System.out.println("resultadoEjecucionSnow2EBS");
	    addStep("3.	Carga de información por parte del job y finalizara");
	    assertTrue(resultadoEjecucionSnow2EBS.equals("Ended OK"), "El BIDS_MAIN_Interfaz_Snow2EBS no se ejecuto correctamente");	    
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a control M";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Conexión entre IMA";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "michelle.vasquez@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ConexionentreIMA_OracleEBS_teste";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

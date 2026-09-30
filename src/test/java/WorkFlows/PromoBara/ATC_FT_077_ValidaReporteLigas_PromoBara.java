package WorkFlows.PromoBara;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PromoBara.crearPromocion;
import WorkFlows.util.PromoBara.editPromocion;
import WorkFlows.util.PromoBara.loginUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_077_ValidaReporteLigas_PromoBara extends BaseExecution{
	

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_077_ValidaReporteLigas_PromoBara_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PromoBara");
	

	/**
	 * Variables
	 * 
	 */
	String userUltimus= data.get("userUltimus");
	
	/**
	 * 
	 * Utiler�as
	 */
	u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url_ultimus"));
	


	loginUltimus logUltimus = new loginUltimus(u, testCase, userUltimus);
	crearPromocion creaPromo = new crearPromocion(u, testCase, userUltimus);
	editPromocion editPromo = new editPromocion(u, testCase);


	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Administrador");
		logUltimus.loginAdmin(userUltimus);

		
		u.hardWait(2);
		
		addStep("Ingresar a la pantalla Buscar");
		editPromo.reports("ligas");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);

		
		u.getDriver().quit();
		

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
		return "Terminado. CP077 - Pantalla Buscar - Validar el reporte de ligas (Plaza) (Administrador)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_077_ValidaReporteLigas_PromoBara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

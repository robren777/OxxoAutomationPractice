package WorkFlows.PromoBara;


import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PromoBara.crearPromocion;
import WorkFlows.util.PromoBara.loginUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class CP078_PantallaBuscarValidaCrearPromocionUserCat extends BaseExecution{
	
	 
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_078_PantallaBuscarValidaCrearPromocionUserCat_Test(HashMap<String, String> data) throws Exception {
		
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

	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Categoria");
		logUltimus.login_Ultimus(userUltimus);
		
		u.hardWait(2);
		
		addStep("Ingresar a la pantalla Buscar para crear una nueva Promoción");
		creaPromo.validaPromocionNoExiste();
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
		return "Terminado. Validar el acceso directo a la creaci�n de promociones cuando el periodo actual se encuentra fuera del rango de captura (fecha mayor a n-34) ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_078_PantallaBuscarValidaCrearPromocionUserCat_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

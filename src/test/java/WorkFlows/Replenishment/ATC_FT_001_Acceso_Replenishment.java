package WorkFlows.Replenishment;

//Libreria para obtener la información de un excel
import java.util.HashMap;

//Libreria de anotaciones para ejecutar pruebas
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WorkFlows.util.Replenishment.replenishmentUtils;
//Nos da funciones para incluir prerequisitos, nombre de la prueba, autor, etc.
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Acceso_Replenishment extends BaseExecution{
	
	//Utilerias
	private SeleniumUtil u;
	private replenishmentUtils util;
	
	//Creamos el test y obtenemos los datos del dataProvider
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Acceso_Replenishment_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Framework WorkFlows --> Replenishment");
		
		//Instancias para manipular el navegador Edge
		u = new SeleniumUtil(new MEdgeTest(), true);
		util = new replenishmentUtils(u,testCase);
		
		//Paso 1
		addStep("Ingresar a la url del portal de aplicaciones en EDGE");
		util.applyAccesaPortalReplenishment();
	
		//Paso 2
		addStep("Ingresar el usuario y contraseña");
		util.applyCredentialsReple();

		//Paso 3
		addStep("Click en Ingresar");
		util.applyValidationLogin();
		
		//Paso 4
		addStep("Seleccionar el LINK Replenishment");
		util.applySelectLink();
		
		//Paso 5
		addStep("Seleccionar la opción Validación Replenishment");
		util.applySelectValidationReple();
	}
	
	@AfterTest
	public void afterTest() {
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
		return "Terminado. Validar el correcto acceso al portal de validación Replenishment";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Ian Alexander Ruiz González";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "FT_WF_Replenishment_01_Acceso";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

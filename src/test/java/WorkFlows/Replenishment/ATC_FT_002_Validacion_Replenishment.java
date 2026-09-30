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

public class ATC_FT_002_Validacion_Replenishment extends BaseExecution {
	
	//Utilerias
	private SeleniumUtil u;
	private replenishmentUtils util;
	
	//Creamos el test y obtenemos los datos del dataProvider
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_Validacion_Replenishment_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Framework WorkFlows --> Replenishment");
		
		//Instancias para manipular el navegador Edge
		u = new SeleniumUtil(new MEdgeTest(), true);
		util = new replenishmentUtils(u,testCase);
		
		//Paso 1 
		addStep("Ingresar a la url del portal de aplicaciones en EDGE");
		util.applyAccesaPortalReplenishment();
	
		//Paso 2
		addStep("Ingresar el usuario y contrasena");
		util.applyCredentialsReple();

		//Paso 3
		addStep("Click en Ingresar");
		util.applyValidationLogin();
		
		//Paso 4
		addStep("Seleccionar el LINK Replenishment");
		util.applySelectLink();
		
		//Paso 5
		addStep("Seleccionar la opcion Validacion Replenishment");
		util.applySelectValidationReple();
		
		//Paso 6
		addStep("Seleccionar un CEDIS");
		util.applySelectCEDIS();
		
		//Paso 7
		addStep("Seleccionar una plaza");
		util.applySelectSquare();
		
		//Paso 8
		addStep("Click al campo buscar");
		util.applyClickSearchButton();
		
		//Paso 9
		addStep("Seleccionar un registro en el Grid");
		util.applySelectRecordGrid();
		
		//Paso 10
		addStep("Click al boton Cambio");
		util.applyClickChangeButton();
		
		//Paso 11
		addStep("Capturar info de max stock y demo stock");
		util.applyMaxDemoStock();
		
		//Paso 12
		addStep("Click en modificar");
		util.applyClickModify();
		
		//Paso 13
		addStep("Click en boton terminar validacion");
		util.applyClickFinish();
		
		//Paso 14
		addStep("Validar la informacion de actualizacion en la tabla");
		util.applyValidateInformation();
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
		return "Realizar un cambio de Stock y Constantes en la pantalla de Reple y enviar las actualizaciones a las tablas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Ian Alexander Ruiz González";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "FT_WF_Replenishment_02_Validacion_Replenishment";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

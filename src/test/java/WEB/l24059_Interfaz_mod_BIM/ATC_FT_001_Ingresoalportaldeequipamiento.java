package WEB.l24059_Interfaz_mod_BIM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.PortalEquipamiento.PomPortalEquipamiento;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


/**
* DESCRIPCION: Verificar que el usuario pueda ingresar al portal de equipamiento.
* @author elberth.ruiz@grupotimex.onmicrosoft.com
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* - MTC-FT-001-Ingreso al portal de equipamiento
* 
* PREREQUISITOS: 
* - En el DataProvider ingresar en las distintas columnas el valor a consultar
* 
* @date 24/Marzo/2025
* 
* INSUMOS
* N/A
* 
*/

@AzureTestInfo(
		areaPath = "I24059-interfaz mod BIM con listado de equip y matriz uni",
		iterationPath = "I24059-interfaz mod BIM con listado de equip y matriz uni\\Sprint 2",
		tags = "I24059_Interfaz_mod_BIM;AutomationMPV1",
		userStoryId = 139289
		
)
public class ATC_FT_001_Ingresoalportaldeequipamiento extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_001_Ingresoalportaldeequipamiento_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("FrameworkWeb --> Portal de equipamiento V2");
		
		//*******************************************************************************************
		String Password = PasswordUtil.decryptPassword(data.get("ps"));
		
		u = new SeleniumUtil(new MEdgeTest(),true);
		
		
		PomPortalEquipamiento pomPortalEquipamiento = new PomPortalEquipamiento(u, testCase);
		u.get(pomPortalEquipamiento.getUrl());
		//*******************************************************************************************
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/	
		addStep("Ingresar al aplicativo Portal de equipamiento v2");
		pomPortalEquipamiento.login(data.get("user"), Password);
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Damos clic en el aplicativo Expansion >> \"Portal de equipamiento V2\"");
		pomPortalEquipamiento.appPorEquipamientoV2();
		
		boolean validacion=pomPortalEquipamiento.appPorEquipamientoV2();
		assertTrue(validacion,"Fallo el script");
			
		
	}
		
	@AfterMethod
	public void afterMethod() {
	u.getDriver().quit();
	
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "En el DataProvider ingresar en las distintas columnas el valor a consultar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que el usuario pueda ingresar al portal de equipamiento.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Ingresoalportaldeequipamiento_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

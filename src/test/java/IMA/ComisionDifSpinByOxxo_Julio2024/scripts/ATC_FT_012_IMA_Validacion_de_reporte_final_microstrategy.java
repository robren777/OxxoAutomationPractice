package IMA.ComisionDifSpinByOxxo_Julio2024.scripts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.ComisionDifSpinByOxxo_Julio2024.om.IMA_Page;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;




/*
 *Se valida el reporte final de la plataforma microstrategy
 *ATC_FT_012_IMA_Validacion_de_reporte_final_microstrategy
 *pre requisitos 1.-contar con acceso al portal ; 2.-obtener numero de empleado para ingreso al portal 
@author Alan Perez
@date 23/01/25
*/

public class ATC_FT_012_IMA_Validacion_de_reporte_final_microstrategy extends BaseExecution{

	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_IMA_Validacion_de_reporte_final_microstrategy_test (HashMap<String, String> data) throws Exception{
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMA_Page ima = new IMA_Page(u, testCase);
		
		
		//data 
		String usuarioW = data.get("WindowsU");
		String contrasenaW = data.get("PasswordU");
		// paso 1
		//Ingreso pagina
		addStep("Ingreso a la pagina web");
		
		assertTrue(ima.GotoPage(usuarioW, contrasenaW),"No se ingresa a la pagina MicroStrategy");
		testCase.addScreenShotCurrentStep("Ingreso pagina principal");
		
		//Se realiza el login con el usuario
		addStep("Login user");
	
		assertTrue(ima.Login(),"No realiza el login");
		testCase.addScreenShotCurrentStep("Login");
		
		//Se ingresa al menu correspondiente para el reporte de comision diferenciada
		addStep("Se realiza el ingreso a las carpetas dentro del portal microstrategy");
		
		assertTrue(ima.goToRouteSubFolder("Informacion Información Comercial QA "));
		testCase.addScreenShotCurrentStep("Ruta de ejecución reporte");
	
		
		addStep("Buscar filtrado año/mes");
		
		assertTrue(ima.filtroAnio(),"No filtra por año");
		testCase.addScreenShotCurrentStep("Se realiza el filtrado por mes");
		
		
		addStep("Ejecución de reporte");
		
		assertTrue(ima.EjecucionReporte(),"No se ejecuta el reporte");
		testCase.addScreenShotCurrentStep("Se ejecuta el reporte correspondiente");
		
		
	}
	

@Override
public void beforeTest() {
	// TODO Auto-generated method stub

}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Validar reporte final IMA";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Alan Perez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_012_IMA_Validacion_de_reporte_final_microstrategy_test";
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

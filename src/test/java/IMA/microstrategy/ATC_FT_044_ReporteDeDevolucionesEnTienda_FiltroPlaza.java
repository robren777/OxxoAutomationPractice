package IMA.microstrategy;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_044_ReporteDeDevolucionesEnTienda_FiltroPlaza extends BaseExecution{
	@Test(dataProvider = "data-provider")
  public void ATC_FT_044_ReporteDeDevolucionesEnTienda_FiltroPlaza_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);		
		
		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
						
		// Paso 2
		addStep("Seleccionar la carpeta de compras y devoluciones");
		ima.Select_FolderComprasyDevoluciones();
		
		//Paso 3
		addStep("Seleccion de reporte");
		ima.clickOnReporte("Devoluciones en Tienda por Fuente de abasto Mensual");
				
		//Paso 4
		addStep("Validacion de seleccion Plaza. Aplicar filtros(prompts)");
		ima.validarSeleccionPromptPlaza();
		u.hardWait(2);
		//ima.generarReporteDevolucionesEnTienda();
		
		//Paso 5
		addStep("Aplicar opcion navegacion filtro Plaza");
		
	
		//Paso 6
		//la tabla mencionada para la comparacion de registros no existe 
		
		
	}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_044_ReporteDeDevolucionesEnTienda_FiltroPlaza_test";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "MTC_FT_044 - Reporte de Devoluciones en Tienda por Fuente de abasto Mensual_Filtro Plaza";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "EQUIPO AUTOMATIZACION";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
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


}


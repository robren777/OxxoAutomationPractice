package IMA.microstrategy;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_024_ReporteDeRecepciones_Categoria_Filtro_Tienda extends BaseExecution{
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_024_ReporteDeRecepciones_Categoria_Filtro_Tienda_test(HashMap<String, String> data) throws Exception {
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
		ima.clickOnReporte("Recepciones en tienda por fuente de abasto (Selecci�n Categor�a)");
				
		//Paso 4
		addStep("Aplicar filtros(prompts) y generar reporte");
		ima.generarReporte();
		
		//Paso 5
		addStep("Aplicar opcion navegacion filtro Tienda");
		ima.NavegacionFiltroTienda();
		
		//Paso 6
		//No existe la tabla
		
		
	}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_024_ReporteDeRecepciones_Categoria_Filtro_Tienda_test";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "MTC_FT_024 - Reporte de Recepciones en tienda por fuente de abasto � Categor�a_Filtro Tienda";
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

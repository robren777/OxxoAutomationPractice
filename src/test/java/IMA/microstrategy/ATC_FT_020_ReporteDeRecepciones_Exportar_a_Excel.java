package IMA.microstrategy;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_020_ReporteDeRecepciones_Exportar_a_Excel extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_ReporteDeRecepciones_Exportar_a_Excel_test(HashMap<String, String> data) throws Exception {
		
		//Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		String report = data.get("Report");
		
		IMATools ima = new IMATools(u, testCase);
		
		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
		
		// Paso 2 
		addStep("Ir a carpeta de Compras y Devoluciones");
		ima.Select_FolderComprasyDevoluciones();
		
		// Paso 3 
		addStep("Seleccionar el reporte de recepciones en tienda por fuente de abasto y ejecutar informe");
		ima.EjecutarInformeReporteSeleccionado(report);
		
		// Paso 4
		addStep("Exportar");
		ima.exportarAExcel();	
		
	    u.hardWait(3);
		u.close();
	}
  
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Exportar Reporte a Excel";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_ReporteDeRecepciones_Exportar_a_Excel_test";
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

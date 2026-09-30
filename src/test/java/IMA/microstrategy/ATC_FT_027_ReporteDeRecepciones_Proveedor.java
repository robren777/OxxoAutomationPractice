package IMA.microstrategy;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_027_ReporteDeRecepciones_Proveedor extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_027_ReporteDeRecepciones_Proveedor_test(HashMap<String, String> data) throws Exception {
		
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
		addStep("Validar columnas");
		String[] ColumnasSuperiores_a_validar = {"Tienda", "Categoria"};
	    String result1 = ima.validarColumnasSuperiores(ColumnasSuperiores_a_validar);
	    if(!result1.equals("")) {
	        assertFalse(true, "No existe la columna "+result1);
	    }
		
		String[] Columnas_a_validar = {"Producto","Indicadores","Uds Recibidas Directos",
				"Uds Recibidas Directos","$ Cmp Directos"};
	    String result = ima.validarColumnas(Columnas_a_validar);
	    if(!result.equals("")) {
	        assertFalse(true, "No existe la columna "+result);
	    }

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
		return "Validar columnas en reporte";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_027_ReporteDeRecepciones_Proveedor_test";
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

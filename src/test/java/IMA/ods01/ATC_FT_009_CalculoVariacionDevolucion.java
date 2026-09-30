package IMA.ods01;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_009_CalculoVariacionDevolucion extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_CalculoVariacionDevolucion_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		//String user = data.get("user");
		//String password = data.get("password");
		IMATools ima = new IMATools(u,testCase);	
		
		String ConsultaTabla = "SELECT DEVOLUCIONES_MA, DEVOLUCIONES_MMAA FROM BIDMMKTCOL.FACT_109_REC_DEV_CPRAS_DD"; // No esta la tabla y no estan las columnas
		
		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
		
		
		// Paso 2 
		addStep("Seleccionar la carpeta de compras y devoluciones");
		ima.Select_FolderComprasyDevoluciones();
		
		//Paso 3 y 4
		addStep("Seleccionar el reporte, capturar los prompts y presionar Ok");
		ima.generarReporte("CIGARRILLOS");
		
		// Paso 5 
		addStep("Obtener el valor de la columna variaci�n de devoluciones (%)");
		List<String> valores = ima.ObtenerVariacionDevoluciones();
		
		// Paso 6
		addStep("Validar el c�lculo de la columna Variaci�n de devoluciones (%)");
		// Imagino que hace referencia a una base de datos
		
		String[] Columnas_a_validar = {"$ Cmp Directos MA","Uds Recibidas Directos MA","Uds Recibidas Directos MMAA"};
		String result = ima.validarColumnas(Columnas_a_validar);
		if(!result.equals("")) {
			assertFalse(true, "No existe la columna "+result);
		}
		
		
		
		
		
		
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_CalculoVariacionDevolucion_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
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

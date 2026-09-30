package IMA.Paths_ExtraccionDirecta_InventarioOptimoEnBodega;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_12_ValidarReporteResumenOperativoPlazaSemana extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_12_ValidarReporteResumenOperativoPlazaSemana_Test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);	
		
		// paso 1
		addStep("Se ingresa a la herramienta MicroStrategy a la siguiente ruta: Informaci�n Comercial  [QA]>Informes compartidos>Operaciones>Resumen Operativo>Reportes Semanal");
		ima.Login();	
		ima.gotoRoute("Informaci�n Comercial QA");
		ima.showAllSharedInf();
		ima.gotoRoute("Operaciones/Resumen Operativo/Reportes Semanal");		
		
		// Paso 2
		addStep("Se selecciona el reporte  Resumen Operativo Plaza Semana");
		ima.gotoRoute("Resumen Operativo Plaza Semana");	
		
		// Paso 3
		addStep("Se selecciona el filtro \"Ejecuci�n Inventario Optimo en Bodega\" de la secci�n 4 \"Choose from a list of objects de Ejecuci�n\"");
		ima.Seleccionar("Choose from a list of objects de Ejecucion");
		
		// Paso 4
		addStep("Seleccionamos el filtro obligatorio \"Seleccione de 1 a 4 Semanas\"");
		ima.Seleccionar("Seleccione de 1 a 4 Semanas");
		ima.Seleccionar("2022:2022");
		u.click(By.xpath("//*[@id=\"id_mstr299\"]/img"));		
		
		// Paso 5
		addStep("Se presiona el bot�n: \"Ejecutar informe\"");
		ima.ejecutarInforme();
		
		// Paso 6
		
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "";
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

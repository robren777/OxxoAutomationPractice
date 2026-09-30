package IMA.microstrategy;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
public class ATC_FT_032_ReporteDeRecepciones_Categoria_Filtro_Tiempo extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_032_ReporteDeRecepciones_Categoria_Filtro_Tiempo_test(HashMap<String, String> data) throws Exception {
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);		
		
		// paso 1
		addStep("Abrir navegador y loguearse en la página de MicroStrategy");
		ima.Login();
								
		// Paso 2
		addStep("Seleccionar la carpeta de compras y devoluciones");
		ima.SelectFolder_PurchaseReportsAndReturns();
		
		// Paso 3
		addStep("Seleccionar el reporte Recepciones por categoria");
		u.highLight(By.linkText("Recepciones por categoria"));
		u.click(By.linkText("Recepciones por producto Categoria"));
		testCase.addScreenShotCurrentStep(u, "Seleccionar el reporte Recepciones por producto por categoria" );
				
		// Paso 4
		addStep("Capturar los prompts y presionar Ok");
		ima.Generate_ReportReceptionsByProductPilotStoresCEDISMonthly();
		
		// Paso 5
		addStep("Navegar al menú Datos, y seleccionar la opción Navegación, Aplicar un filtro para el campo Ejercicio (Año y Mes)");
		ima.NavegacioMenuDatosOpcionNavegacion();
		ima.ValidarFiltroMuneuDatosNavegacion(data.get("tipoFiltro"));
		
		//Paso 6 Falta base de datos
}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_032_ReporteDeRecepciones_Categoria_Filtro_Tiempo_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Reporte de recepciones por categoria por filtro de tiempo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "IMA";
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

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}
}

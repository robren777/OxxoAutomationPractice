package IMA.microstrategy;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_031_ReporteDeRecepciones_Categoria extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_031_ReporteDeRecepciones_Categoria_test(HashMap<String, String> data) throws Exception {
		
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
		addStep("Validar que el reporte despliegue las siguientes columnas:" + 
						"-SKU" + 
						"-Descripción de articulo" + 
						"-Unidades recibidas Netas (Mes actual)" + 
						"-Unidades recibidas Netas (Mismo mes, año anterior)" + 
						"-$ Recepciones Netas (Mes actual)" + 
						"-$ Recepciones Netas (Mismo mes, año anterior)" + 
						"-$ Variación" + 
						"-% Variación");
		ima.validacionDeReportReceptionsByProductPilotStoresCEDISMonthly();
				
				
		// Paso 6
		addStep("\"Validar el formato de las columnas del reporte" + 
						
						"Columnas Mismo mes año anterior (MMAA):" + 
						"Formato Monto: -$9,999.99, ejemplo $195,124.92" + 
						"Formato Número: 9,999, ejemplo 28,822" + 
				
						"Columnas Mes Anterior (MA):" + 
						"Formato Monto: -$9,999.99, ejemplo $195,124.92" + 
						"Formato Número: 9,999, ejemplo 28,822" + 
						
						"Columna Variación $:" + 
						"Formato; -$9,999.99, ejemplo -$30.0" + 
						 
						"Columna Variación %:\r\n" + 
						"Formato; -999.9%, ejemplo -3.0%\"");
		ima.ValidarFormatoColumnasReporte();
		
		//Paso 7 falta base de datos
	}
  
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Reporte de recepciones por categoria";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_031_ReporteDeRecepciones_Categoria_test";
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

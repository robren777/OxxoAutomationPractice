package IMA.microstrategy;



import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_040_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_Exportar_a_Excel extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_040_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_Exportar_a_Excel_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);		
		
		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
						
		// Paso 2
		addStep("Seleccionar la carpeta de compras y devoluciones");
		ima.SelectFolder_PurchaseReportsAndReturns();
		
		
		// Paso 3
		addStep("Seleccionar el reporte Recepciones por producto tiendas piloto CEDIS Mensual");
		u.highLight(By.linkText("Recepciones por producto tiendas piloto CEDIS Mensual"));
		u.click(By.linkText("Recepciones por producto tiendas piloto CEDIS Mensual"));
		testCase.addScreenShotCurrentStep(u, "Seleccionar el reporte Recepciones por producto tiendas piloto CEDIS Mensua" );
		
		// Paso 4
		addStep("Capturar los prompts y presionar Ok");
		ima.Generate_ReportReceptionsByProductPilotStoresCEDISMonthly();
		
		// Paso 5
		addStep("Presionar el bot�n Exportar En las opciones para exportar seleccionar las opciones:" + 
				"-Informe completo" + 
				"-Excel con Formato");
		ima.NavegacioMenuInicioDeInformes_Exportar_ExcelConFormato();
		
		
		
		// Paso 6
		addStep("Guardar y abrir el archivo del reporte en Excel");
		
		

		  
	}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_040_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_Exportar_a_Excel_test";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "ATC_FT_040_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_Exportar_a_Excel_test";
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


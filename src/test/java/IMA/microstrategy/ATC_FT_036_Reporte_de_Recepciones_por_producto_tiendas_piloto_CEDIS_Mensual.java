package IMA.microstrategy;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_036_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_036_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_test(HashMap<String, String> data) throws Exception {
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
		addStep("Validar que el reporte despliegue las siguientes columnas:" + 
				"-SKU" + 
				"-Descripcion de articulo" + 
				"-Unidades recibidas Netas (Mes actual)" + 
				"-Unidades recibidas Netas (Mismo mes, anio anterior)" + 
				"-$ Recepciones Netas (Mes actual)" + 
				"-$ Recepciones Netas (Mismo mes, anio anterior)" + 
				"-$ Variacion" + 
				"-% Variacion");
		ima.validacionDeReportReceptionsByProductPilotStoresCEDISMonthly();
		
		
		// Paso 6
		addStep("\"Validar el formato de las columnas del reporte" + 
				
				"Columnas Mismo mes anio anterior (MMAA):" + 
				"Formato Monto: -$9,999.99, ejemplo $195,124.92" + 
				"Formato N�mero: 9,999, ejemplo 28,822" + 
		
				"Columnas Mes Anterior (MA):" + 
				"Formato Monto: -$9,999.99, ejemplo $195,124.92" + 
				"Formato N�mero: 9,999, ejemplo 28,822" + 
				
				"Columna Variacion $:" + 
				"Formato; -$9,999.99, ejemplo -$30.0" + 
				 
				"Columna Variacion %:\r\n" + 
				"Formato; -999.9%, ejemplo -3.0%\"");
		ima.ValidarFormatoColumnasReporte();
		
		
		
	}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_036_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_test";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "ATC_FT_036_Reporte_de_Recepciones_por_producto_tiendas_piloto_CEDIS_Mensual_test";
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


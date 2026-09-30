package IMA.Extraccion_ConexionDirecta.DepuracionAutomaticaDIM_TIEMPO_RETEK_51984895;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_Validar_reporte extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Validar_reporte_test(HashMap<String, String> data) throws Exception {
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);		
		
		/*************************************** PASO 1 ***************************************/
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
						
		/*************************************** PASO 2 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial");
		ima.Select_FolderAnalisisPromociones();
				
		addStep("Seleccion de reporte Promociones OXXO por categor�a");
		ima.clickOnReporte("Promociones Oxxo Por Categor�a");
		
		/*************************************** PASO 3 ***************************************/
		addStep("Aplicar filtros(prompts) con un periodo mayor a 2015");
		ima.generarReporteProm(); 
		
		/*************************************** PASO 4 ***************************************/
		addStep("Validar que se muestre el reporte con registros");
		ima.validarRepPromociones();
		
		/*************************************** PASO 5 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial");
		ima.Select_FolderAnalisisPromociones();
				
		addStep("Seleccion de reporte Promociones OXXO por categor�a");
		ima.clickOnReporte("Promociones Oxxo Por Categor�a");
		
		/*************************************** PASO 6 ***************************************/
		addStep("Aplicar filtros(prompts) con un periodo menor a 2015");
		ima.generarReportePromN(); 
		
		/*************************************** PASO 7 ***************************************/
		addStep("Verificar que se no se muestre el reporte");
		ima.validarRepPromocionesN();
	}
	
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_Validar_reporte_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC_FT-001_CP006 - Validar reporte";
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

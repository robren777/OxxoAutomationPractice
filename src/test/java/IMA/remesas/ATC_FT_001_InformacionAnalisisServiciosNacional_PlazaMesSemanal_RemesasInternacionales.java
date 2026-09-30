package IMA.remesas;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_InformacionAnalisisServiciosNacional_PlazaMesSemanal_RemesasInternacionales extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_InformacionAnalisisServiciosNacional_PlazaMesSemanal_RemesasInternacionales_test(HashMap<String, String> data) throws Exception {
		/*************************************** UTILERIAS ***************************************/	
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u, testCase);
		
		/*************************************** PASO 1 ***************************************/
		addStep("Abrir navegador y loguearse en la pagina de MicroStrategy");
		ima.Login();
						
		/*************************************** PASO 2 ***************************************/
		addStep("Seleccionar la carpeta de Informacion comercial");
		ima.Select_FolderTelefoniaServicios();
		
		/*************************************** PASO 3 ***************************************/
		addStep("Seleccion de reporte Analisis Servicios Nacional (Mes) - Remesas Internacionales");
		ima.clickOnReporte("Análisis Servicios Nacional (Mes) - Remesas Internacionales");
		ima.validarExistaFiltro(By.xpath("//*[@id=\"id_mstr31_summaryButton\"]"));
		
		/*************************************** PASO 4 ***************************************/
		addStep("Seleccionar la opcion \"Remesas\" de la ruta reflejada en la parte superior de la pantalla ");
		ima.Select_ReturnRemesas(); 
		
		/*************************************** PASO 5 ***************************************/
		addStep("Seleccionar Reporte Analisis Servicios Nacional (Semana) - Remesas Internacionales");
		ima.clickOnReporte("Análisis Servicios Nacional (Semana) - Remesas Internacionales");
		ima.validarExistaFiltro(By.xpath("//*[@id=\"id_mstr28_summaryButton\"]"));

		u.close();
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

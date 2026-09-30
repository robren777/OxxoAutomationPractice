package IMA.Paths_ExtraccionDirecta_BI_Servicios_en_BI_Comercial;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_15_ResultadosCategoriasIC_Acum_NoFiltroTienda extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_15_ResultadosCategoriasIC_Acum_NoFiltroTienda_Test(HashMap<String, String> data) throws Exception {

		/** VARIABLES *********************************************************************/	

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);	

		/* PASO 1 *********************************************************************/	
				
		addStep("Se ingresa a la siguiente Ruta en MicroStrategy: Informaci�n Comercial  [QA]>Informes compartidos>Informaci�n Comercial>Resultados del Negocio>Nacional>Resultados Categor�as");
		ima.Login();
		ima.gotoRoute("Informaci�n Comercial QA/Informaci�n Comercial/Resultados del Negocio/Nacional/Resultados Categor�as/");
		
		/* PASO 2 *********************************************************************/	
		
		addStep("Se selecciona el reporte: Resultados Categor�as + IC (Acum) v1");
		ima.gotoRoute("Resultados Categor�as + IC (Acum) v1");
		
		/* PASO 3 *********************************************************************/	
		
		addStep("Se selecciona el mes a consultar del filtro especifico");
		testCase.addScreenShotCurrentStep(u, "El dato es seleccionado de manera correcta.");
		String[] dataInput = {"2022:2022"};
		ima.capturaPrompt(dataInput);
		
		/* PASO 4 *********************************************************************/	
		
		addStep("No se selecciona valor alguno del filtro TIENDA");
		testCase.addScreenShotCurrentStep(u, "El filtro queda sin valor seleccionado.");
		
		/* PASO 5 *********************************************************************/
		addStep("Se pulsa el bot�n para generar el reporte");		
		ima.ejecutarInforme();
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_15_ResultadosCategoriasIC_Acum_NoFiltroTienda";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida la correcta generaci�n del reporte \"Resultados Categor�as + IC (Acum) v1\" sin seleccionar el filtro de tienda";
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
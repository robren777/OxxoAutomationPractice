package IMA.Paths_ExtraccionDirecta_BI_Servicios_en_BI_Comercial;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_14_ResultadosCategoriasIC_Acum extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_14_ResultadosCategoriasIC_Acum_Test(HashMap<String, String> data) throws Exception {

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
		String[] dataInput = {"2022:2022","Tiendas Maduras Comparativas Base 2021"};
		ima.capturaPrompt(dataInput);
		
		/* PASO 4 *********************************************************************/	
		
		addStep("Se selecciona un valor del Filtro TIENDA");
		testCase.addScreenShotCurrentStep(u, "El dato queda seleccionado de manera correcta.");
		
		/* PASO 5 *********************************************************************/
		addStep("Se pulsa el bot�n para generar el reporte");		
		ima.ejecutarInforme();
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_14_ResultadosCategoriasIC_Acum";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se genera de manera correcta el reporte Resultados Categor�as + IC (Acum) v1";
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
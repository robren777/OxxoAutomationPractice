package IMA.microstrategy;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_021_ReporteDeRecepciones_Categoria extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ReporteDeRecepciones_Categoria_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);		
		
		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
				
		// Paso 2
		addStep("Seleccionar la carpeta de compras y devoluciones");
		ima.Select_FolderComprasyDevoluciones();
			
		//Paso 3
		addStep("Seleccion de reporte");
		ima.clickOnReporte("Recepciones en tienda por fuente de abasto (Selecci�n Categor�a)");
		
		//Paso 4
		addStep("Aplicar filtros(prompts) y generar reporte");
		ima.generarReporte();
		
		//Paso 5
		addStep("Validacion de campos");
		ima.validacionDeCamposReporte21();
		
		//Paso 6
		addStep("Validacion de formato campos");
		ima.validarFormatoColumnas();
		
		
		//Paso 7
		//No existe la tabla
		
		}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar columnas en reporte";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ReporteCifrasDeCotrol_TiendaFronteriza_test";
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

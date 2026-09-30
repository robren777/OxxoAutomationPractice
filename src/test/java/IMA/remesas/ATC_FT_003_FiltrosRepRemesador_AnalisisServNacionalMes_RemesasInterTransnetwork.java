package IMA.remesas;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_FiltrosRepRemesador_AnalisisServNacionalMes_RemesasInterTransnetwork extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_FiltrosRepRemesador_AnalisisServNacionalMes_RemesasInterTransnetwork_test(HashMap<String, String> data) throws Exception {
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
		
		/*************************************** PASO 4 ***************************************/
		addStep("Aplicar filtros(prompts) para Tipo de Tienda y Año-mes");
		ima.generarReporteRemesasInterMes(); 
		
		/*************************************** PASO 5 ***************************************/
		addStep("Validar que se muestre el reporte con registros");
		ima.validarRepRemesas();
		
		/*************************************** PASO 6 ***************************************/
		addStep("Seleccionar Filtro del reporte Proveedor Remesas con la opcion Transnetwork");
		ima.ProveedorRemesas("2:PYIO22:Transnetwork");
		ima.validarColumnaRep();
		
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

package IMA.remesas;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_FiltrosRepRemesador_AnalisisServNacionalSemanalRemesasInterWesternUnion extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_FiltrosRepRemesaAnalisisServNacSemRemesasInternWesternUnion_test(HashMap<String, String> data) throws Exception {
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
		addStep("Seleccion de reporte Analisis Servicios Nacional (Semana) - Remesas Internacionales");
		ima.clickOnReporte("Análisis Servicios Nacional (Semana) - Remesas Internacionales");
		
		/*************************************** PASO 4 ***************************************/
		addStep("Aplicar filtros(prompts) para Tipo de Tienda y Semana");
		ima.generarReporteRemesasInterSemana(data.get("semana")); 
		
		/*************************************** PASO 5 ***************************************/
		addStep("Validar que se muestre el reporte con registros");
		ima.validarRepRemesas();
		
		/*************************************** PASO 6 ***************************************/
		addStep("Seleccionar Filtro del reporte Proveedor Remesas con la opcion Western Union");
		ima.ProveedorRemesas("1:PYIO18:Western Union");
		ima.validarColumnaRep();
		
		u.close();
	}


	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
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
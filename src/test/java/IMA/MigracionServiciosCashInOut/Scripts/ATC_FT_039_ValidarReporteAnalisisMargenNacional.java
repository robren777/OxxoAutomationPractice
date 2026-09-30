package IMA.MigracionServiciosCashInOut.Scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.MigracionServiciosCashInOut.om.MicroStrategyUtil;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**
* DESCRIPCIÓN: Realiza la ejecucion del reporte analisis de margen Nacional.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-039- Validar la generacion del reporte Analisis de Margen Nacional
* 
* PREREQUISITOS:
* 	- Acceso a MicroStrategy
* 
* INSUMOS:
* 
* @author Michelle Ceja
* @date 25/Noviembre/2024
*/
public class ATC_FT_039_ValidarReporteAnalisisMargenNacional extends BaseExecution{
	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_039_ValidarReporteAnalisisMargenNacional_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I24053 - Migración servicios Cash-in y Cash-out");


		/*************************************** Utilerias***************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase); 
		MicroStrategyUtil microStrategy = new MicroStrategyUtil(u,testCase);
		
		String semana = data.get("semana");
		String tienda = data.get("tienda");
		String reporte = data.get("informe");
		String subCat = data.get("subCategoria");
		String segmento = data.get("segmento");
		String subSegmento = data.get("subSegmento");
		
		

		
		/*************************************** PASO 1 ***************************************/	
		
		addStep("Ingresar a MicroStrategy");
		ima.Login();
			  
	 
		/*************************************** PASO 2 ***************************************/	
	
		addStep("Seleccionar Información Comercial QA");
		
		assertTrue(microStrategy.clickInfoComercialQa(), "Error al seleccionar informacion comercial");
				
		/*************************************** PASO 3 **************************************/
				  
		addStep("Seleccionar Informes Compartidos");
		assertTrue(microStrategy.clickInformeCompartido(),"Error al seleccionar Informes Compartidos*");
		/*************************************** PASO 4 **************************************/
		  
		addStep("Seleccionar A Reportes por Liberar");
		assertTrue(microStrategy.clickReportLiberar(),"Error al seleccionar Reportes a Liberar");

		
		/*************************************** PASO 5 **************************************/
		  
		addStep("Seleccionar Informacion Comercial*");
		
		assertTrue(microStrategy.clickInfoComercial(),"Error al seleccionar Informacion Comercial");

		/*************************************** PASO 6 **************************************/
		  
		addStep("Seleccionar Por Tema");
		
		assertTrue(microStrategy.clickPorTema(),"Error al seleccionar Por Tema");

		/*************************************** PASO 7 **************************************/
		  
		addStep("Seleccionar Analisis de Servicios");
		assertTrue(microStrategy.analisisServicio(),"Error al seleccionar Analisis de Servicios");

		
		/*************************************** PASO 8 **************************************/
		  
		addStep("Seleccionar Nacional");
		assertTrue(microStrategy.clickNacional(),"Error al seleccionar el reporte Nacional");

		
		/*************************************** PASO 9 **************************************/
		  
		addStep("Seleccionar Analisis de Servicios Semana o Mes");
		assertTrue(microStrategy.clickReportSemana(),"Error al seleccionar Analisis de Servicios Semana o Mes");

		
		/*************************************** PASO 10 *************************************/
		  
		addStep("Seleccionar los filtros correspondientes a la semana de la fecha de ejecucion ");
		assertTrue(microStrategy.selectSemana(semana),"Error al seleccionar semana");
		assertTrue(microStrategy.selectTienda(tienda),"Error al seleccionar tienda");


		/*************************************** PASO 11 *************************************/
		  
		addStep("Dar click en ejecutar informe");
		assertTrue(microStrategy.ejecutaInforme(reporte),"Error al ejecutar informe");

		;
		/*************************************** PASO 12 *************************************/
		  
		addStep("Validar que contenga informacion");
		assertTrue(microStrategy.selectOp(subCat, "SubCategoria",""),"Error al seleccionar SubCategoria");
		u.hardWait(2);
		assertTrue(microStrategy.selectOp(segmento, "Segmento",""),"Error al seleccionar Segmento");
		u.hardWait(2);
		assertTrue(microStrategy.selectOp(subSegmento, "SubSegmento",""),"Error al seleccionar SubSegmento");
		testCase.addScreenShotCurrentStep("");
		
		u.hardWait(2);
		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ejecutar el CP 038. Contar con credenciales para MicroStrategy";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la información procesada por los jobs se refleje en el reporte de analisis de margen";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_039_ValidarReporteAnalisisMargenNacional_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

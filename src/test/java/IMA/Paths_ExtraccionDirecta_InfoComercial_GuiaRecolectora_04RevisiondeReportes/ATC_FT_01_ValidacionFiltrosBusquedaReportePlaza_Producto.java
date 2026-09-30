package IMA.Paths_ExtraccionDirecta_InfoComercial_GuiaRecolectora_04RevisiondeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_01_ValidacionFiltrosBusquedaReportePlaza_Producto extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_ValidacionFiltrosBusquedaReportePlaza_Producto_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);		
		
		// Paso 1
		addStep("Ingresar a la herramienta MicroStrategy con la siguiente ruta: Informacion Comercial [QA]\\informes compartidos\\Informaci�n Comercial\\Gesti�n de Mercadeo\\Expulsi�n Por Merma");		
		ima.Login();
		ima.gotoRoute("Informacion Comercial QA");
		u.click(By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"));		
		ima.gotoRoute("Gesti�n de Mercadeo/Expulsi�n Por Merma/Inventario Plaza - Producto");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 2
		addStep("Validar que el aplicativo muestre los filtros de Busqueda:\n"
				+ "- Mes\n"
				+ "- Plaza");

		String[] lista = {data.get("mes"),data.get("plaza")};
		ima.capturaPrompt(lista);
		
		// Paso 3
		addStep("Informar el filtro de b�squeda:                                           \n"
				+ "- Mes                                                                                              \n"
				+ "- A�o                                                                                                                                                                               \n"
				+ "Pulsar el bot�n \"Generar Reporte\"");
		
		// Paso 4
		addStep("Seleccionar filtro de Visualizaci�n: \"Plaza\".                    \n"
				+ "Seleccionar 1 o m�s registros.");

		ima.ejecutarInforme();
		
		// Paso 5 
		addStep("Validar el encabezado de las columnas mostradas en el Grid");
		u.highLight(By.xpath("//*[@id=\"UniqueReportID_1_1_1\"]"));
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 6
		addStep("Seleccionar filtro de Visualizaci�n: \"Categor�a\".                    \n"
				+ "Seleccionar 1 o mas registros.");
		u.click(By.id("elemKey"));
		u.click(By.xpath("//*[contains(text(),'" + data.get("filtrar") + "')]"));
		
		// Paso 7
		addStep("Validar el encabezado de las columnas mostradas en el Grid");
		try {
			Thread.sleep(6000);
		}catch(Exception ex) {
			
		}
		u.highLight(By.xpath("//*[@id=\"UniqueReportID_1_1_1\"]"));
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		
		/*
		// Paso 3 
		addStep("Ingresar en la ruta: Mantenimiento\\Public Objects\\Reports\\Control de Gastos\\Formas OS\\Reportes\\Costos Acumulados");
		ima.gotoRoute("Mantenimiento/Control de Gastos/Formas OS/Reportes/Costos Acumulados");
		
		// Paso 4
		addStep("Validar los elementos elementos de creacion del reporte   Costos Acumulados");
		testCase.addScreenShotCurrentStep(u, "Se valida el acceso para capturar los datos necesarios.");
		
		// Paso 5
		addStep("Seleccionar los filtros deseadas del reporte. Hacer clic en \"Ejecutar Reporte\"");
		try {
			ima.clickonAllList(By.className("mstrBGIcon_treeNodeClosedOrphan"));
		}catch(Exception ex) {
			
		}
		String[] lista = {data.get("plaza"),data.get("anio"),data.get("mes")};
		ima.capturaPrompt(lista);
		ima.ejecutarInforme();
		
		// Paso 6
		addStep("Se actualiz� MicroStrategy de la Tabla DIM_TIENDA");
		String[] ab = {"Tienda"};
		ima.validarColumnas(ab);
		
		// Paso 7 
		addStep("Hacer clic en bot�n Exportar. Seleccionar formato Excel. Clic en bot�n \"Exportar\"");
		ima.exportar("Excel");
		
		// Paso 8
		addStep("Hacer clic en bot�n Exportar. Seleccionar formato PDF. Clic en bot�n \"Exportar\"");
		ima.exportar("PDF");
		
		ima.abrirReporteExcel("Costos Acumulados.xls");
		*/
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_01_ValidacionFiltrosBusquedaReportePlaza_Producto";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara que el reporte Plaza y Producto sea generado de manera correcta al informar los filtros presentes.";
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
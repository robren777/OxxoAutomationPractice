package IMA.Paths_ExtraccionDirecta_InfoComercial_GuiaRecolectora_04RevisiondeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_04_ValidarMetricasReportePlazaProducto extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_ValidarMetricasReportePlazaProducto_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);		
		
		// Paso 1
		addStep("Ingresar a la herramienta MicroStrategy con la siguiente ruta: Informaci�n Comercial [QA]\\informes compartidos\\Informaci�n Comercial\\Gesti�n de Mercadeo\\Expulsi�n Por Merma");		
		ima.Login();
		ima.gotoRoute("Informaci�n Comercial [QA]/informes compartidos/Informaci�n Comercial/Gesti�n de Mercadeo/Expulsi�n Por Merma");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		
		
		
		// Paso 1
		u.get("http://10.185.176.16/MicroStrategy/");
		addStep("Ingresar a MicroStrategy");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 2
		
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
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_04_ValidarMetricasReportePlazaProducto";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que las 20 m�tricas del Reporte Plaza - Producto sean mapeadas correctamente.";
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
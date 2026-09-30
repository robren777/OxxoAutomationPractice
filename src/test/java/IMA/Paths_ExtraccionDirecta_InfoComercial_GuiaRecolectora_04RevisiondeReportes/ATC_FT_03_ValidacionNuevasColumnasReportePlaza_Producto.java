package IMA.Paths_ExtraccionDirecta_InfoComercial_GuiaRecolectora_04RevisiondeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_03_ValidacionNuevasColumnasReportePlaza_Producto extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_03_ValidacionNuevasColumnasReportePlaza_Producto_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);		
		
		// Paso 1
		addStep("Ingresar a la herramienta MicroStrategy con la siguiente ruta: Informaci�n Comercial [QA]\\informes compartidos\\Informaci�n Comercial\\Gesti�n de Mercadeo\\Expulsi�n Por Merma");		
		ima.Login();
		ima.gotoRoute("Informaci�n Comercial QA");
		u.click(By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"));		
		ima.gotoRoute("Gesti�n de Mercadeo/Expulsi�n Por Merma/Inventario Plaza - Producto");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 2
		addStep("Validar que el aplicativo muestre los filtros de B�squeda:\n"
				+ "- Mes\n"
				+ "- Plaza");

		String[] lista = {data.get("mes"),data.get("plaza")};
		ima.capturaPrompt(lista);
		
		// Paso 3
		addStep("Informar el filtro de b�squeda: \n- Mes\n- A�o\n Pulsar el bot�n \"Generar Reporte\"");
		
		// Paso 4
		addStep("Seleccionar filtro de Visualizaci�n: \"Plaza\" Seleccionar 1 o m�s registros.");

		ima.ejecutarInforme();
		
		// Paso 5 
		addStep("Validar el encabezado de las columnas mostradas en el Grid");
		u.highLight(By.xpath("//*[@id=\"UniqueReportID_1_1_1\"]"));
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 6
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Unidades Inventario Inicial");
		ima.resaltaByType("td","Unidades Inventario Inicial");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 7
		addStep("Validar que el reporte muestre el siguiente atributo como columna: $ Monto Inventario Inicial");
		ima.resaltaByType("a","$Monto Inventario Inicial");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 8
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Costo Neto");
		//ima.resaltaByType("td","Costo Neto");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 9
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Unidades Vendidas De Expulsion");
		ima.resaltaByType("a","Unidades Vendidas de Expulsion");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 10
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Unidades Devueltas Num");
		//ima.resaltaByType("td","Unidades Devueltas Num");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 11
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Unidades vendidas de expulsi�n");
		ima.resaltaByType("a","Unidades Vendidas de Expulsion");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 12
		addStep("Validar que el reporte muestre el siguiente atributo como columna: $ Monto vendido de expulsi�n");
		ima.resaltaByType("a","$Monto Vendido de Expulsion");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 13
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Precio Venta");
		//ima.resaltaByType("a","Precio Venta");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 14
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Unidades de expulsi�n mermadas");
		ima.resaltaByType("td","Unidades de Expulsion Mermadas");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 15
		addStep("Validar que el reporte muestre el siguiente atributo como columna: $ Monto de expulsi�n Mermado");
		ima.resaltaByType("a","$Monto de Expulsion Mermado");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 16
		addStep("Validar que el reporte muestre el siguiente atributo como columna: M�todo de Expulsi�n");
		ima.resaltaByType("td","M�todo de expulsi�n");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 17
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Fecha de inicio expulsi�n");
		ima.resaltaByType("td","Fecha Inicio Expulsion");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 18
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Fecha fin expulsi�n");
		ima.resaltaByType("td","Fecha Fin Expulsion");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_03_ValidacionNuevasColumnasReportePlaza_Producto";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que las nuevas columnas del Reporte Plaza - Producto sean generadas correctamente.";
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
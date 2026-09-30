package IMA.Paths_ExtraccionDirecta_InfoComercial_GuiaRecolectora_04RevisiondeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_02_ValidacionColumnasReportePlaza_Producto extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_02_ValidacionColumnasReportePlaza_Producto_Test(HashMap<String, String> data) throws Exception {
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
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Producto");
		ima.resaltaByType("td","Producto");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 7
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Num Tiendas");
		ima.resaltaByType("td","Num Tiendas");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 8
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Unidades Inventario Actual");
		ima.resaltaByType("td","Unidades Inventario Actual");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 9
		addStep("Validar que el reporte muestre el siguiente atributo como columna: $ Monto Inventario Actual");
		ima.resaltaByType("td","$ Monto Inventario Actual");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 10
		addStep("Validar que el reporte muestre el siguiente atributo como columna: $ Monto Inv Prom");
		ima.resaltaByType("td","$ Monto Inv Prom");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 11
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Dias de Expulsion");
		ima.resaltaByType("a","Dias de Expulsion");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 12
		addStep("Validar que el reporte muestre el siguiente atributo como columna: Dias Inventario");
		ima.resaltaByType("a","Dias Inventario");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_02_ValidacionColumnasReportePlaza_Producto";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que las columnas del Reporte Plaza - Producto sean informadas correctamente.";
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
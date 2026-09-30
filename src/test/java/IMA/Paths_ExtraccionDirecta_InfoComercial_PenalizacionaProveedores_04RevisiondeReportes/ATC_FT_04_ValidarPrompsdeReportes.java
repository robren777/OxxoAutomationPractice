package IMA.Paths_ExtraccionDirecta_InfoComercial_PenalizacionaProveedores_04RevisiondeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_04_ValidarPrompsdeReportes extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_ValidarPrompsdeReportes_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);		
		
		// Paso 1
		addStep("Ingresar a la herramienta MicroStrategy con la siguiente ruta: Informaci�n Comercial  [QA]>Informes compartidos>Penalizaci�n de Proveedores");		
		ima.Login();
		ima.gotoRoute("Informaci�n Comercial QA/");
		u.click(By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"));		
		ima.gotoRoute("Penalizacion de Proveedores");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 2
		addStep("Seleccionamos el Reporte Penalizaci�n Proveedores");
		ima.gotoRoute("Reporte Penalizaci�n de Proveedores");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 3
		addStep("Se valida que los PROMP (Filtros) se muestren de la siguiente manera:\n"
				+ " \n"
				+ "Proveedor: Dato seleccionable de una lista\n"
				+ "Mes: Desplegable \n"
				+ "Orden de Compra: Dato seleccionable de una lista\n"
				+ "CEDIS: Dato seleccionable de una lista");
		ima.ScreenShotPrompt();		
		
		// Paso 4
		addStep("Seleccionamos el Reporte Penalizaci�n de Proveedores-Mes-CEDIS-Articulo");
		u.click(By.linkText("Penalizacion de Proveedores"));
		ima.gotoRoute("Proveedores Mes CEDIS Articulo");
		
		// Paso 5
		addStep("Se valida que los PROMP (Filtros) se muestren de la siguiente manera:\n"
				+ " \n"
				+ "Proveedor: Dato seleccionable de una lista\n"
				+ "Mes: Desplegable \n"
				+ "CEDIS: Dato seleccionable de una lista");		
		ima.ScreenShotPrompt();	
		
		// Paso 6
		addStep("Seleccionamos el Reporte Penalizaci�n de Proveedores-Mes-CEDIS");
		u.click(By.linkText("Penalizacion de Proveedores"));
		u.click(By.linkText("Proveedores Mes CEDIS"));
		//ima.gotoRoute("Proveedores Mes CEDIS");
		
		// Paso 7
		addStep("Se valida que los PROMP (Filtros) se muestren de la siguiente manera:\n"
				+ " \n"
				+ "Proveedor: Dato seleccionable de una lista\n"
				+ "Mes: Desplegable \n"
				+ "CEDIS: Dato seleccionable de una lista");		
		ima.ScreenShotPrompt();	
		
		// Paso 8
		addStep("Seleccionamos el Reporte Penalizaci�n de Proveedores-Mes-Articulo");
		u.click(By.linkText("Penalizacion de Proveedores"));
		ima.gotoRoute("Proveedores Mes CEDIS Articulo");
		
		// Paso 9
		addStep("Se valida que los PROMP (Filtros) se muestren de la siguiente manera:\n"
				+ " \n"
				+ "Proveedor: Dato seleccionable de una lista\n"
				+ "Mes: Desplegable");		
		ima.ScreenShotPrompt();	
				
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_04_ValidarPrompsdeReportes";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara los PROMP de los filtros para cada reporte est�n configurados de la manera correcta.";
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
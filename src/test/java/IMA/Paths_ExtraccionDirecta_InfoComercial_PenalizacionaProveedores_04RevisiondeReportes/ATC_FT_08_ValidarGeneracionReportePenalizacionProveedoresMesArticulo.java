package IMA.Paths_ExtraccionDirecta_InfoComercial_PenalizacionaProveedores_04RevisiondeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_08_ValidarGeneracionReportePenalizacionProveedoresMesArticulo extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_08_ValidarGeneracionReportePenalizacionProveedoresMesArticulo_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);		
		
		// Paso 1
		addStep("Se sigue la siguiente ruta en MicroStrategy: Informaci�n Comercial  [QA]>Informes compartidos>Penalizaci�n de Proveedores");		
		ima.Login();
		ima.gotoRoute("Informaci�n Comercial QA/");
		u.click(By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"));		
		ima.gotoRoute("Penalizacion de Proveedores/Proveedores Mes Articulo");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 2
		addStep("Se seleccionan los siguientes filtros para generar el reporte\n"
				+ " \n"
				+ "Proveedor\n"
				+ "Mes");
		String[] lista = {data.get("Proveedor"),data.get("Mes")};
		ima.capturaPrompt(lista);		
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 3
		addStep("Se pulsa sobre el bot�n \"Ejecutar\"");
		ima.ejecutarInforme();
		
				
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_08_ValidarGeneracionReportePenalizacionProveedoresMesArticulo";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara la correcta generaci�n del Reporte Penalizaci�n de Proveedores-Mes-Articulo.";
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
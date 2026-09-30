package IMA.remesas;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_008_FiltrosReporteRemesadorAnalisisServiciosNacionalSemanalRemesasInternacionalesMoneygram extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_FiltrosReporteRemesadorAnalisisServiciosNacionalSemanalRemesasInternacionalesMoneygram_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		//utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);		
		
		// Paso 1
		addStep("Ingresa a Microstrategy con la siguiente URL:  http://fcobiwqa01/microstrategy/asp/Main.aspx");		
		u.get(GlobalVariables.URL_MICROSTRATEGY);
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 2
		addStep("Ingresar usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		ima.Login();
		
		// Paso 3
		addStep("Ingresar a la opci�n \"Informaci�n Comercial QA\"");
		ima.gotoRoute("Informaci�n Comercial QA");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 4
		addStep("Seleccionar la opci�n \"Informes Compartidos\"");
		u.click(By.linkText("Informes compartidos"));
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 5
		addStep("Seleccionar la opci�n \"Informaci�n Comercial\" ");
		u.click(By.linkText("Informaci�n Comercial"));
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 6
		addStep("Seleccionar la opci�n \"Telefon�a y Servicios\"");
		ima.gotoRoute("Telefon�a y Servicios");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 7
		addStep("Seleccionar la opci�n \"Resultados Servicios\"");
		ima.gotoRoute("Resultados Servicios");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 8
		addStep("Seleccionar la opci�n \"Nacional\"");
		ima.gotoRoute("Nacional");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 9
		addStep("Seleccionar la opci�n \"Remesas\"");
		ima.gotoRoute("Remesas");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 10
		addStep("Seleccionar Reporte \"An�lisis Servicios Nacional (Semanal) - Remesas Internacionales\"");
		ima.gotoRoute("An�lisis Servicios Nacional (Semana) - Remesas Internacionales");
		testCase.addScreenShotCurrentStep(u, "Pagina");
		
		// Paso 11
		addStep("Seleccionar opci�n para :\n"
				+ "Tipo de tienda \n"
				+ "Selecci�n de semana \n"
				+ "Seleccionar \"Ejecutar Informe\"\n"
				+ "{Al encontrar informaci�n muestra en pantalla el informe ejecutado con los datos siguientes:\n"
				+ " Detalles de Informe\n"
				+ " Detalles de Selecci�n din�mica \n"
				+ " Paginaci�n seg�n: Proveedor Remesas (Filtro de selecci�n) }");
		
		String[] lista = {data.get("tienda"),data.get("semanas")};
		ima.capturaPrompt(lista,0);		
		testCase.addScreenShotCurrentStep(u, "Pagina");
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_FiltrosReporteRemesadorAnalisisServiciosNacionalSemanalRemesasInternacionalesMoneygram";
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
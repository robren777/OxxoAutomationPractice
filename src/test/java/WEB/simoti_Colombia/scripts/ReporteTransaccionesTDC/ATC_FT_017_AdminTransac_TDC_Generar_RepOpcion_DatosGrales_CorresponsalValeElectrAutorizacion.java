package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.Login;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_017_AdminTransac_TDC_Generar_RepOpcion_DatosGrales_CorresponsalValeElectrAutorizacion extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_AdminTransac_TDC_Generar_RepOpcion_DatosGrales_CorresponsalValeElectrAutorizacion_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = data.get("Password");
		String URL = data.get("Url");
		String INE = data.get("INE");
		
		// Paso 1
		addStep("El usuario ingresa a la aplicacion Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(URL);
		Login pa = new Login(u, testCase);		
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el boton Iniciar Sesion.");
		pa.logginPlatform(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opcion de SIMOTI.");
		pa.click_on_simoti();
		
		// Paso 4
		addStep("El usuario despliega el menu: Transacciones Electronicas y selecciona el reporte: Transacciones TDC");
		pa.transaccionesTDC();
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_017_AdminTransac_TDC_Generar_RepOpcion_DatosGrales_CorresponsalValeElectrAutorizacion_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}
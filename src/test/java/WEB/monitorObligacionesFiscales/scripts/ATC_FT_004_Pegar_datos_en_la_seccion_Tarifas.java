package WEB.monitorObligacionesFiscales.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.monitorObligacionesFiscales.om.MenuPrincipal_MonitorObligaciones;
import WEB.monitorObligacionesFiscales.om.Tablas;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_Pegar_datos_en_la_seccion_Tarifas extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Pegar_datos_en_la_seccion_Tarifas_test(HashMap<String, String> data) {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String URL = data.get("Url");
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_MonitorObligaciones mo = new MenuPrincipal_MonitorObligaciones(u,testCase);
		Tablas moT = new Tablas(u,testCase);
		// Paso 1
		addStep("Ingresar al portal de aplicaciones");

		u.get(URL);
		u.hardWait(3);
		pa.proceedToPortalIEsecureSite();

		// Paso 2
		addStep("El usuario ingresa usuario y contraseña y da clic en el botón <Iniciar Sesión>.");
		pa.cicloLogin(user, password);
		// Paso 3
		addStep("El usuario da clic en opci�n de Monitor de Obligaciones Fiscales.");
		pa.clickPortalMonitorObligacionesFiscales(1);
		//Paso 4
		mo.clickTablas("ISR");
		//Paso 5
		moT.selectAnio("2015");
		moT.selectMes("Abril");
		moT.buscar();
		assertTrue(moT.validarHeadersTabla(),"Los headers no coinciden");
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
		return null;
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


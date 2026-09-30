package WEB.reporteConciliacionRemesas.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_000_Validar_acceso_repConciliacion extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_000_Validar_acceso_repConciliacion_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
//		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		//		                            PASO 2
		addStep("Seleccionar Inventario de Espacios");
		pa.clickReporteConciliacionRemesas();
//		pa.clickPortalInventarioEspacios();

	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_000_Validar_acceso_repConciliacion_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_000_Validar_acceso_repConciliacion_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}

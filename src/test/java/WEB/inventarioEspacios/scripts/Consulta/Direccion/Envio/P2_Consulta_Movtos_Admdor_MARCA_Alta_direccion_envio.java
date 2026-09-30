package WEB.inventarioEspacios.scripts.Consulta.Direccion.Envio;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class P2_Consulta_Movtos_Admdor_MARCA_Alta_direccion_envio extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_P2_Consulta_Movtos_Admdor_MARCA_Alta_direccion_envio(HashMap<String, String> data) throws Exception {
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
		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		
		
		//		                            PASO 2
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		//                                  PASO 3
		addStep(" Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		mp.verificarInformacion(user, negocio, perfil);
		//                                 PASO 4
		addStep(" Validar que visualice los MENUS:");    //  PENDIENTE
		                      
		//                                 PASO 5
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
	}
 
	
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido.  Validar que se muestre  la alerta de \"Usuario y/o contrase�a invalidos\" y no permite el ingreso al usuario";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_P2_Consulta_Movtos_Admdor_MARCA_Alta_direccion_envio";
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

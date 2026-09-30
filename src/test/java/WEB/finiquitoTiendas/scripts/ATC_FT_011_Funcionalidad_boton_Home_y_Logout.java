package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.FuncionalidadBotonHomeLogout;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validacion de modificacion en pantalla.
* MTC_FT_011. Funcionalidad boton Home y Logout.
* @author Manuel Cuen
* @date Diciembre 2022
*/
public class ATC_FT_011_Funcionalidad_boton_Home_y_Logout extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_011_Funcionalidad_boton_Home_y_Logout_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");



		//									PASO 1
													  //https://10.182.92.88:8443/AccessControl/pages/login_form.jsf
		addStep(" Ingresar al portal de aplicaciones <http://10.182.92.88:8443/AccessControl/pages/login_form.jsf> "); 
     	u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge

		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		FuncionalidadBotonHomeLogout hl = new FuncionalidadBotonHomeLogout(u, testCase);
		hl.clickAgregarImagen();
		
		//		                            PASO 2
		
		addStep(" Capturar un usuario y contrase�a validos	 ");
		pa.cicloLogin(user, password);

		//                                  PASO 3
		addStep(" Seleccionar en el listado de aplicaciones Finiquito en Tienda ");
		pa.clickFiniquitoTiendas();

		//                                  PASO 4
		addStep(" Dar clic en el bot�n de Home.	");
		hl.clickHome();
		Boolean validacion = false;
		validacion = hl.validaEtiquetaPortalApli();
		if(validacion) {
			testCase.addTextEvidenceCurrentStep(" ��Validado: Etiqueta Existente Home!! ");
		}
		assertTrue(validacion, "No redirecciona al inicio del portal de aplicaciones");
		
		//                                  PASO 5
		addStep(" Ingresar nuevamente al portal Finiquito en Tienda. ");
		pa.cicloLogin(user, password);
		pa.clickFiniquitoTiendas();
		                      
		//                                  PASO 6
		addStep(" Dar clic al bot�n logout. ");
		hl.clickLogout();
		validacion = false;
		validacion = hl.validaEtiquetaPortalApli();
		if(validacion) {
			testCase.addTextEvidenceCurrentStep(" ��Validado: Etiqueta Existente Logout!! ");
		}
		assertTrue(validacion, "No redirecciona al inicio del portal de aplicaciones");
		
	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_011_Funcionalidad_boton_Home_y_Logout_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_Funcionalidad_boton_Home_y_Logout_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
//		u.getDriver().quit();
	}


}

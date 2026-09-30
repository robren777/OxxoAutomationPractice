package WEB.finiquitoTiendas.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_FiniquitoOptimo;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validacion de no afectacion.
* MTC_FT_004. Finiquito optimo
* @author Yair Lizarraga
* @date Diciembre 2022
*/
public class ATC_FT_004_Finiquito_Optimo extends BaseExecution { 
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Finiquito_Optimo_test (HashMap<String, String> data) throws Exception {

		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = GlobalVariables.URL_PORTAL_APPS_SAR;
		String Plaza = data.get("Plaza");
		String Distrito = data.get("Distrito");
		String Comisionista = data.get("Comisionista");
		String Tienda = data.get("Tienda");
		String Ayudante = data.get("Ayudante");
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pantalla_FiniquitoOptimo pfo = new Pantalla_FiniquitoOptimo (u,testCase);
		
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones "+url+" "); 
		
	    PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Capturar un usuario y contrase�a validos	"); 
		
		pa.proceedToPortal(); 
		pa.cicloLogin(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar en el listado de aplicaciones Finiquito en Tienda"); 
		pfo.clickContainer();
	
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("En el menu superior seleccionar Finiquito y posteriormente se da clic al boton de Finiquito Optimo");
		pfo.MostrarFiniquitoOptimo();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Dar clic en siguiente en el bot�n siguiente	");
		pfo.clicBtnFiniquitoOptimo();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Dar clic en siguiente en la pantalla de C�mo funciona el finiquito �ptimo.	");
		pfo.clicBtnInstruccionesFiniquitoOptimo();
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Se ingresa informaci�n en los campos Plaza, Distrito, Comisionista y Tienda");
		pfo.capturaCamposFiniquitoOptimo(Plaza, Distrito, Comisionista, Tienda);
		
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Llenar los campos y dar clic en bot�n siguiente");
		pfo.clicBtnSiguienteFO();
		
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Se llenan los campos si se tienen gastos a deducir y dar clic al bot�n siguiente.");
		pfo.campoAyudanteFiniquito(Ayudante);
		pfo.clicBtnSiguienteDeducir();
		pfo.clicBtnSiguienteGAD();
        	
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
		return null;
	}

	@Override
	public String setTestDesigner() {
		return"AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Finiquito_Optimo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_AdministracionFiniquitos;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validacion de modificacion en pantalla.
* MTC_FT_010. Sueldo de Equipo.
* @author David Valdez
* @date Diciembre 2022
*/
public class ATC_FT_010_Sueldo_de_Equipo extends BaseExecution { 
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_Sueldo_de_Equipo_test (HashMap<String, String> data) throws Exception {

		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = data.get("Password");
		String url = GlobalVariables.URL_PORTAL_APPS_SAR;
		String AyudanteA = data.get("AyudanteA");
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pantalla_AdministracionFiniquitos af = new Pantalla_AdministracionFiniquitos(u,testCase);
		MetodosExtra me = new MetodosExtra(u,testCase);
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
		af.clickContainer();
	
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("En el menu superior seleccionar Administraci�n y posteriormente se da clic al boton de Sueldo de Equipo.");
		pa.finiquitoSelectAdministracion("Sueldo de Equipo");
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Dar clic en el bot�n de Modificar.");
		pa.modificarSueldoEquipo();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Modificar alg�n campo y dar clic al bot�n Guardar.");
		pa.modificarSueldoEquipoyGuardar(AyudanteA);
		
		boolean validacion = me.comprobarMensajeAlerta("La modificaci�n se realiz� exitosamente.");
		assertTrue(validacion, "No se muestra el mensaje: La modificaci�n se realiz� exitosamente.");
		
        	
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
		return "ATC_FT_010_Sueldo_de_Equipo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

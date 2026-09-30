package WorkFlows.Planchado;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Planchado.pomPlanchado;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_045_Validacion_Historiales_Tienda extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_045_Validacion_Historiales_Tienda_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> PLANCHADO");
	
	/**
	 * Variables
	 * 
	 */
		String plaza  = data.get("FiltroPlaza");
		String tienda = data.get("FiltroTienda");
		String nombre  = data.get("FiltroNombre");
		String descripcion = data.get("FiltroDescripcion");
		String rutaExcel = data.get("ExcelPath");
	/**
	 * 
	 * Utilerias
	 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		String windowUltimus = u.getDriver().getWindowHandle();
		pomPlanchado util = new pomPlanchado(u,testCase);
		pomUltimus ultimus = new pomUltimus (u);
	
		
		
	// paso 1
			addStep("Login Planchado");
			ultimus.loginUltimus(GlobalVariables.userPlanchado, GlobalVariables.passPlanchado);
			ultimus.seleccionarVista("Iniciar");
			ultimus.openPlanchado();
			testCase.addScreenShotCurrentStep("Login Planchado");
			
	// paso 2
			addStep("Hacer clic en el men Planchado > Historiales del menu de la izquierda de la pantalla.");
			util.selectMenuHistoriales();
			testCase.addScreenShotCurrentStep("Menu Historiales");		
				
			
	// paso 3
			addStep("Informar los siguientes:" + 
					"Plaza (Plazas disponibles para consultar)." + 
					"Tienda (Tiendas disponibles para consultar, en base a la plaza seleccionada)." + 
					"Pulsar Boton");
			util.buscarSeleccionarHistoriales(plaza, tienda, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
			
	// paso 4
			addStep("Pulsar la imagenControl de la columna Tienda.");
				//Poso No Aplica
	// paso 5
			addStep("Validar que contenga los siguientes elementos:" + 
					
					"Titulo: Detalle" + 
					"Tienda: Columna que contiene el CR y descripcion de la tienda" + 
					"Activar: 1 FA: Columna que contiene el numero de articulos a los que se activa una FA." + 
					"Activar: +1 FA: Columna que contiene el numero de articulos a los que se activa mas de una FA." + 
					"Activar: +1 FA, no estandar: Columna que contiene el numero de articulos a los que se activa mas de una FA no estandar." + 
					"Activar: no estandar: Columna que contiene el numero de articulos a los que se realiza una activacion no estandar." + 
					"Ligar: 1 FA: Columna que contiene el numero de articulos a los que se ligara una FA." + 
					"Ligar: +1 FA: Columna que contiene el numero de articulos a los que se ligara mas de una FA." + 
					"Ligar: +1 FA, no estandar: Columna que contiene el numero de articulos a los que se ligara mas de una FA no estandar." + 
					"Ligar: no estandar: Columna que contiene el numero de articulos a los que se realiza una liga no estandar." + 
					"Inactivar: activo RMS: Columna que contiene el numero de articulos activos en RMS" + 
					"Inactivar: no existe en lego: Columna que contiene el numero de articulos a inactivar porque no existen en lego.");
			//Poso No Aplica
	
	}
	
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Tener acceso al portal de Engine mediante ultimus" + 
				"2.- Usuario con perfil de Planchado." + 
				"3.- Planchados Registrados.";
	}


	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "El objetivo de la prueba consiste en el correcto funcionamiento del pop-up de la columna Tienda..";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_045_Validacion_Historiales_Tienda_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

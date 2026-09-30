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

public class ATC_FT_044_Validacion_Historiales_Parametro extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_044_Validacion_Historiales_Parametro_Test(HashMap<String, String> data) throws Exception {
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
			addStep("Buscar por Plaza y Tienda" + 
					"Seleccionar del campo Plaza con un valor valido." + 
					"Seleccionar del campo Tienda con un valor valido..");
			util.buscarSeleccionarHistoriales(plaza, tienda, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
			
	// paso 4
			addStep("Seleccionar un registro del grid. Hacer clic en icono ImageControl (hoja blanca) que se encuentra en la columna â€œParametrosâ€�.");
			util.ValidaElementosPantallaParametrosHitorial();
	// paso 5
			addStep("Validar que la pantalla muestre los siguientes elementos:" + 
					"Etiqueta:Titulo Etiqueta con la leyenda Parametros (nombre del planchado)." + 
					"Etiqueta:Tiendas Etiqueta con la leyenda Tiendas." + 
					"Lista:Tiendas Contiene la lista de tiendas pertenecientes al planchado configurado." + 
					"Etiqueta:Legos Etiqueta con la leyenda Legos." + 
					"Lista:Legos Contiene la lista de legos pertenecientes al planchado configurado." + 
					"Etiqueta:Paquetes Etiqueta con la leyenda Paquetes." + 
					"Lista:Paquetes Contiene la lista de paquetes pertenecientes al planchado configurado." + 
					"CheckBox:Inactivacion Indica si se va a inactivar un articulo en caso de que no este sembrado y exista en Retek (activo), muestra la leyenda: Â¿Inactivar si el articulo no esta sembrado en la tienda y existe en Retek la liga en la tienda con status activo?" + 
					"Boton:Cerrar Cierra la pop-up.");
			
	// paso 6
			addStep("Validar el estado inicial de los elementos de la pantalla:" + 
					"Etiqueta Titulo Habilitado con valor Parametros (nombre del planchado)." + 
					"Etiqueta Tiendas Habilitado con valor Tiendas." + 
					"Lista Tiendas Habilitado Contiene la lista de tiendas configuradas para el planchado.." + 
					"Etiqueta Legos Habilitado con valor Legos." + 
					"Lista Legos Habilitado Contiene la lista de legos configurados para el planchado.." + 
					"Etiqueta Paquetes Habilitado con valor Paquetes." + 
					"Lista Paquetes Habilitado Contiene la lista de paquetes configuradas para el planchado.." + 
					"CheckBox Inactivacion Deshabilitado Indica si se va a inactivar un articulo en caso de que no este sembrado y exista en Retek (activo), muestra la leyenda: Â¿Inactivar si el articulo no esta sembrado en la tienda y existe en Retek la liga en la tienda con status activo?" + 
					"Boton Cerrar Habilitado con nombre Cerrar.");
			
	// paso 7
			addStep("Hacer clic en el boton Cerrar");
			util.ValidaElementosPantallaParametrosHitorial();
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_044_Validacion_Historiales_Parametro_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

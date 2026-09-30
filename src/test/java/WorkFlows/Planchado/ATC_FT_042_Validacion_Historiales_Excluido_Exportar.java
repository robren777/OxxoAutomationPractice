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

public class ATC_FT_042_Validacion_Historiales_Excluido_Exportar extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_042_Validacion_Historiales_Excluido_Exportar_Test(HashMap<String, String> data) throws Exception {
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
			addStep("Hacer clic en el menu Planchado Historiales del menu de la izquierda de la pantalla.");
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
			addStep("Pulsar la imagenControl de la columna Excluidos.");
			
			
	// paso 5
			addStep("Verificar la informacion de la pantalla de Excluidos.");
	// paso 6
			addStep("Oprimir el Boton <<<Boton>>>");
	// paso 7
			addStep("Oprimir el Boton Abrir.");		
			
	// paso 8
			addStep("Validar que contenga los siguientes campos en el Encabezado:" + 
					"Etiqueta: Femsa Comercio" + 
					"Etiqueta: Historial del Planchado Nombre del planchado");		
	// paso 9
			addStep("Validar que contenga los siguientes campos en el Cuerpo:" + 
					"# Plaza" + 
					"Plaza" + 
					"Location" + 
					"# Tienda" + 
					"Tienda " + 
					"# Articulo" + 
					"Articulo" + 
					"Supergrupo" + 
					"# FA" + 
					"FA" + 
					"Tipo FA" + 
					"Estatus Actual" + 
					"Estatus al Planchar" + 
					"Lego" + 
					"Posibles Legos" + 
					"Blindado" + 
					"Vigencia" + 
					"Acciones a Realizar");
			
			
			util.ValidaHistorialesParametrosPantalla();
	// paso 10
			addStep("Validar que contenga los siguientes campos en el Pie de pagina:" + 
					"Nombre del usuario Fecha y hora de generacion del reporte");
	// paso 11
			addStep("Pulsar el Boton <<<Boton>>>");
	// paso 12
			addStep("Oprimir el Boton Guardar");
	// paso 13
			addStep("Oprimir el Boton Guardar.");
			
	// paso 14
			addStep("Oprimir el Boton Abrir.");
	// paso 15
			addStep("Validar que contenga los siguientes campos en el Encabezado:" + 
					"Etiqueta: Femsa Comercio" + 
					"Etiqueta: Historial del Planchado Nombre del planchado");
	// paso 16
			addStep("Validar que contenga los siguientes campos en el Cuerpo:" + 
					"# Plaza" + 
					"Plaza" + 
					"Location" + 
					"# Tienda" + 
					"Tienda" + 
					"# Articulo" + 
					"Articulo" + 
					"Supergrupo" + 
					"# FA" + 
					"FA" + 
					"Tipo FA" + 
					"Estatus Actual" + 
					"Estatus al Planchar" + 
					"Lego" + 
					"Posibles Legos" + 
					"Blindado" + 
					"Vigencia" + 
					"Acciones a Realizar");
	// paso 17
			addStep("Validar que contenga los siguientes campos en el Pie de pagina:" + 
					"Nombre del usuario" + 
					"Fecha y hora de generacion del reporte");
			
					util.AbrirExcelExistente(rutaExcel);
					u.hardWait(10);
					testCase.addScreenShotCurrentStep("Reporte Excel");
					util.leerCeldasExcel(rutaExcel,18);
	// paso 18
			addStep("Pulsar el Boton <<<Boton>>>");
	// paso 19
			addStep("Oprimir el Boton Cancelar.");
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
		return "ATC_FT_042_Validacion_Historiales_Excluido_Exportar_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

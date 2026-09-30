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

public class ATC_FT_043_Validacion_Historiales_Filtros extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_043_Validacion_Historiales_Filtros_Test(HashMap<String, String> data) throws Exception {
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
			addStep("Hacer clic en el menu Planchado" + 
					"> Historiales del menu de la" + 
					"izquierda de la pantalla.");
			util.selectMenuHistoriales();
			testCase.addScreenShotCurrentStep("Menu Historiales");		
				
			
	// paso 3
			addStep("Validar que la pantalla muestre los siguientes elementos:" + 
					"Etiqueta:''Titulo'' Etiqueta con la leyenda ''Historial del planchado'" + 
					"Etiqueta:''Plaza'' Etiqueta con la leyenda ''Plaza''\r\n" + 
					"Campo ComboBox: Obligatorio -''Plaza'' Lista de plazas obtenida de la BD. Por default debera" + 
					"mostrar: Todos \r\n" + 
					"Etiqueta:''Nombre'' Etiqueta con la leyenda ''Nombre''\r\n" + 
					"Campo de Texto:''Nombre '' Campo para ingresar el nombre completo o parcial de un registro de" + 
					"planchado." + 
					"Etiqueta:''Descripcion" + 
					" '' Etiqueta con la leyenda ''Descripcion'" + 
					"Campo de Texto:''Descripcion" + 
					" '' Campo para ingresar la descripcion completa o parcial de un registro de planchado" + 
					"Etiqueta:''Tienda'' Etiqueta con la leyenda ''Tienda'" + 
					"Campo ComboBox:''Tienda'' Lista de tiendas obtenida de la BD en base a la plaza seleccionada. Por" + 
					"default debera mostrar: --- Todos ---, Si no se ha seleccionado ninguna plaza debera mostrar" + 
					"Seleccione una plaza" + 
					"Etiqueta:''Fecha'' Etiqueta con la leyenda ''Fecha'" + 
					"Campo de Texto:''Fecha Inicio'' Contiene la fecha seleccionada con el DateTimePicker" + 
					"Campo de Texto:''Fecha Fin'' Contiene la fecha seleccionada con el DateTimePicker " + 
					"Boton:''Buscar" + 
					"'' Obtiene lista de historiales de planchado de acuerdo a los filtros" + 
					"ingresados." + 
					"Boton:''Excel" + 
					" '' Genera el reporte de historiales de planchado en formato Excel" + 
					"Boton:''Limpiar" + 
					" '' Devuelve los valores por default de la pantalla." + 
					"Grid:''Historiales'' Gridview que contiene la lista de historiales de planchado de acuerdo a los filtros " + 
					"ingresados.'");
			
	// paso 4
			addStep("Validar el estado inicial de los elementos de la pantalla:" + 
					"Etiqueta ''Titulo'' Habilitado con valor ''Historial del planchado'" + 
					"Etiqueta ''Plaza'' Habilitado con valor ''Plaza'" + 
					"Campo ComboBox ''Plaza'' Habilitado con valor 'Todos'" + 
					"Etiqueta ''Nombre'' Habilitado con valor ''Nombre'" + 
					"Campo de Texto ''Nombre '' Habilitado Vacio." + 
					"Etiqueta ''Descripcion" + 
					" '' Habilitado con valor ''Descripcion'" + 
					"Campo de Texto ''Descripcion Habilitado Vacio." + 
					"Etiqueta ''Tienda'' Habilitado con valor ''Tienda'" + 
					"Campo ComboBox ''Tienda'' Habilitado con valor ''Seleccione una plaza'" + 
					"Etiqueta ''Fecha'' Habilitado con valor ''Fecha'" + 
					"Campo de Texto ''Fecha Inicio'' Habilitado Vacio." + 
					"Imagen Calendario ''Fecha Inicio'' Habilitado." + 
					"Campo de Texto ''Fecha Fin'' Habilitado Vacio." + 
					"Imagen Calendario ''Fecha Fin'' Habilitado." + 
					"Boton ''Buscar" + 
					" '' Habilitado con nombre ''Buscar'" + 
					"Boton ''Excel" + 
					" '' Habilitado con nombre ''Excel''" + 
					"Boton ''Limpiar" + 
					" Habilitado con nombre 'Limpiar." + 
					"Grid ''Historiales'' Habilitado Vacio");
					
	// paso 5
			addStep("Validar el grid muestre los siguientes elementos:" + 
					"Columna:Excluidos Columna del grid que muestra un imageControl para realizar la consulta de articulos excluidos.Columna:\"\"Parametros" + 
					"Columna del grid que muestra un imageControl para realizar la consulta de parametros configurados." + 
					"Columna:\"\"Plaza\"\" Columna del grid que muestra el CR de la plaza." + 
					"Columna:\"\"Planchado\"\" Columna del grid que muestra el nombre del planchado." + 
					"Columna:\"\"Fecha\"\" Columna del grid que contiene la fecha y hora de la ultima vez que se ejecuto el proceso de planchado exitosamente" + 
					"Columna:\"\"Tienda\"\" Columna del grid que muestra el CR y nombre de la plaza, ademas de un imageControl para realizar la consulta del detalle del planchado de la tienda." + 
					"Columna:\"\"Ligar: 1 FA\"\" Columna que contiene el numero de articulos a los que se ligara una FA" + 
					"Columna:\"\"Ligar: +1 FA\"\" Columna que contiene el numero de articulos a los que se ligara mas de una FA" + 
					"Columna:\"\"Ligar: +1 FA, no estandar\"\" Columna que contiene el numero de articulos a los que se ligara mas de una FA no estandar" + 
					"Columna:\"\"Ligar: no estandar\"\" Columna que contiene el numero de articulos a los que se realiza una liga no estandar" + 
					"Columna:\"\"Activar: 1 FA\"\" Columna del grid que contiene el numero de articulos a los que se activa una FA" + 
					"Columna:\"\"Activar: +1 FA\"\" Columna del grid que contiene el numero de articulos a los que se activa mas de una FA" + 
					"Columna:\"\"Activar: +1 FA, no estandar\"\" Columna del grid que contiene el numero de articulos a los que se activa mas de una FA no estandar" + 
					"Columna:\"\"Activar: no estandar\"\" Columna del grid que contiene el numero de articulos a los que se realiza una activacion no estandar" + 
					"Columna:\"\"Inactivar: activo RMS\"\" Columna del grid que contiene el numero de articulos activos en RMS" + 
					"Columna:\"\"Inactivar: no existe en lego\"\" Columna del grid que contiene el numero de articulos a inactivar porque no existen en lego." + 
					"Columna:\"\"Mensaje\"\" Columna del grid que contiene el estatus de la ejecucion del proceso de preview o planchado. Si fue exitoso o si se cancelo, y el detalle del error");
			
				
	// paso 6
			addStep("Hacer clic en el boton Buscar");
	// paso 7
			addStep("Validar los elementos desplegables del campo 'Plaza' con la" + 
					"siguiente consulta (SELECT PLAZA_CR PLAZA_DESC" + 
					"FROM XXVA_ENG_PLAZAS_MV ORDER BY PLAZA_CR; )");		
			
	// paso 8
			addStep("Buscar por Plaza Seleccionar del campo Plaza con un valor valido");		
	// paso 9
			addStep("Hacer clic en el boton Buscar");
	// paso 10
			addStep("Buscar por Plaza y Tienda." + 
					"Seleccionar del campo Plaza con un valor valido." + 
					"Seleccionar del campo Tienda con un valor valido." + 
					"Hacer clic en el boton Buscar");
	// paso 11
			addStep("Buscar por Plaza y Nombre." + 
					"Seleccionar del campo Plaza con un valor valido." + 
					"Capturar en el campo Nombre con un valor valido." +  
					"Hacer clic en el boton Buscar");
	// paso 12
			addStep("Buscar por Plaza y Descripcion" + 
					"Seleccionar del campo Plaza con un valor valido" + 
					"Capturar en el campo Descripcion" + 
					"con un valor valido." + 
					"Hacer clic en el boton Buscar");
	// paso 13
			addStep("Buscar por Plaza y Fecha Inicio, Fecha Fin." + 
					"Seleccionar del campo Plaza con un valor valido." + 
					"Hacer clic en el icono Fecha Inicio y Fecha Fin y seleccionar fecha, donde inicio < Fin." + 
					"Hacer clic en el boton Buscar");
			
	// paso 14
			addStep("Buscar por Nombre y Fecha Inicio,Fecha Fin." + 
					"Capturar en el campo Nombre con un valor valido." + 
					"Hacer clic en el icono Fecha Inicio y Fecha Fin y seleccionar fecha, donde inicio < Fin." + 
 
					"Hacer clic en el boton Buscar");
	// paso 15
			addStep("Hacer clic en el Boton Limpiar");
	// paso 16
			addStep("Buscar por Plaza y Tienda." + 
					"Seleccionar del campo Plaza con un valor valido." + 
					"Seleccionar del campo Tienda con un valor valido." + 

					"Hacer clic en el boton Buscar");
			util.buscarSeleccionarHistoriales(plaza, tienda, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
			

			
	// paso 17
			addStep("Hacer clic en el boton Excel");
			util.ValidaParametrosPantallaHistoriales();
			
			
	// paso 18
			addStep("Hacer clic en el boton Cancelar");
	// paso 19
			addStep("Hacer clic en el boton Texto");
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

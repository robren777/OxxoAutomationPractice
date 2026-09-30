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

public class ATC_FT_013_Validacion_Planchado_Programacion_Pantalla extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_013_Validacion_Planchado_Programacion_Pantalla_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> PLANCHADO");
	
	/**
	 * Variables
	 * 
	 */
		String plaza  = data.get("FiltroPlaza");
		String nombre = data.get("Nombre");
		String descripcion = data.get("Descripcion");

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
			addStep("Hacer clic en el menu Planchado Planchados del menu de la izquierda de la pantalla.");
			util.selectMenuPlanchados();
			testCase.addScreenShotCurrentStep("Menu Planchado");		
			
			
	// paso 3
			addStep("Validar que se muestra el mensaje 'Seleccione una opcion'.");
			
	// paso 4
			addStep("Buscar por Plaza 0 Nombre o Descripcion" + 
					"Seleccionar del campo ComboBox ''Plaza'' con '10MON'." + 
					"Capturar en el campo TextBox 'Nombre' con un nombre valido." + 
					"Capturar en el campo TextBox 'Descripcion'con un descripcion valido." + 
					"Hacer clic en el boton 'Buscar");
			util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
	// paso 5
			addStep("Hacer clic en el boton ImageControl 'Programacion' de la columna Programacion");
	// paso 6
			addStep("Validar que la pantalla muestra los siguientes elementos:" + 
					"Etiqueta 'Titulo' Etiqueta con la leyenda 'Programacion (+)'." + 
					"Boton 'Nuevo' Abre una pop-up para realizar la programacion del planchado." + 
					"Boton 'Cerrar' Cierra la pop-up." + 
					"Grid 'Programacion' Gridview que contiene la lista de programacion realizada para el planchado.");
			
	// paso 7
			addStep("Validar que el grid muestra los siguientes elementos:" + 
					"Columna ''Fecha'' Columna del grid que muestra la fecha de ejecucion configurada para el planchado." + 
					"Columna ''Tipo'' Columna del grid que muestra el tipo de programacion del planchado (programado o directo)." + 
					"Columna ''Accion'' Columna del grid que muestra el tipo de accion del planchado configurada (Preview o Planchar)." + 
					"Columna ''Inicio'' Columna del grid que muestra el dia y hora de inicio del proceso de planchado. Considerar el formato:." + 
					"Columna ''Fin'' Columna del grid que muestra el dia y hora de culminacion del proceso de planchado. Considerar el formato:." + 
					"Columna ''Mensaje'' Columna del grid que muestra el mensaje del resultado de ejecucion del proceso de planchado. en caso de error debera considerar:." + 
					"Columna ''*Editar'' Columna del grid que muestra un imageControl para realizar la edicion del registro. Icono ''EditaProgramacion'' Abre una pop-up para realizar la edicion de la programacion del planchado." + 
					"Columna ''*Eliminar'' Columna del grid que muestra un imageControl para realizar la eliminacion del registro. Icono ''EliminaProgramacion'' Muestra un mensaje para confirmar la eliminacion. �Desea eliminar " + 
					"la programacion: (+) – (x) – (y)?' ,(+) fecha de programacion, (x) tipo de programacion, (y) tipo de accion." + 
					"Columna ''Parametros (P.)'' Columna del grid que muestra un imageControl para la consulta de parametros configurados. Al pasar el puntero sobre el imageControl se muestra un tooltip. 'Parametros'." + 
					" Icono ''Detalle Parametros'' Abre una pop-up para la consulta de parametros configurados." + 
					"Columna ''Detalle (D.)'' Columna del grid que muestra un imageControl para la consulta del resultado de la ejecucion del proceso de planchado. Al pasar el puntero sobre el imageControl se muestra un tooltip." + 
					"'Detalle'. Icono ''DetallePlanchado'' Abre una pop-up para la consulta del detalle de ejecucion del proceso de planchado." + 
					"Columna ''Excluidos (E.)'' Columna del grid que muestra un imageControl para la consulta de articulos excluidos del proceso de planchado. Al pasar el puntero sobre el imageControl se muestra un tooltip." + 
					"'Excluidos'. Icono ''DetalleExcluidos'' Abre una pop-up para la consulta de articulos excluidos del proceso de planchado.");		
					util.ValidaPlanchadoProgramacionPantalla();
					
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
		return "El objetivo de este caso de prueba es validar los elementos de la pantalla 'Programacion' de Planchados ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_Validacion_Planchado_Programacion_Pantalla_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

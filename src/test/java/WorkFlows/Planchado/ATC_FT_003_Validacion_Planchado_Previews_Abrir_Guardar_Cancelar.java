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

public class ATC_FT_003_Validacion_Planchado_Previews_Abrir_Guardar_Cancelar extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_003_Validacion_Planchado_Previews_Abrir_Guardar_Cancelar_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> PLANCHADO");
	
	/**
	 * Variables
	 * 
	 */
		String plaza  = data.get("FiltroPlaza");
		String nombre = data.get("Nombre");
		String descripcion = data.get("Descripcion");
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
			addStep("Hacer clic en el menú Planchado Planchados del menu de la izquierda de la pantalla.");
			util.selectMenuPlanchados();
			testCase.addScreenShotCurrentStep("Menú Planchado");
			
	// paso 3
			addStep("Validar que se muestra el mensaje 'Seleccione una opcion'.");
			
	// paso 4
			addStep("Buscar por Plaza o Nombre o Descripción" + 
					"Seleccionar del campo ComboBox Plaza  con 10MON"   + 
					"Capturar en el campo TextBox Nombre con un nombre valido" + 
					"Capturar en el campo TextBox Descripción con un descripción valido" + 
					"Hacer clic en el botón 'Buscar'");
			util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripcióno");
			
	// paso 5
			addStep("Hacer clic en el botón ImageControl 'Previews' hoja en blanco que se encuentra en la columna Preview.");
			util.seleccionarPreviewsHojaBlanco();
			
	// paso 6
			addStep("Hacer clic en el botón 'Abrir'.");		
	// paso 7
			addStep("Validar el encabezado del reporte con los siguientes elementos:" + 
					"Etiqueta: Femsa Comercio, Leyenda fija del reporte, con formato Texto." + 
					"Etiqueta: Planchado de Articulos Nombre del planchado, Leyenda fija como título del reporte, con formato Texto.");
	// paso 8
			addStep("Validar el layout del reporte con los siguientes elementos:" + 
					"Etiqueta # Plaza: Contiene el CR de la Plaza, Formato: Texto" + 
					"Etiqueta Plaza: Contiene la descripción de la Plaza, Formato: Texto" + 
					"Etiqueta Location: Contiene un identificador de la tienda, Formato: Texto" + 
					"Etiqueta # Tienda: Contiene el CR de la Tienda, Formato: Texto" + 
					"Etiqueta Tienda: Contiene la descripción de la tienda, Formato: Texto" + 
					"Etiqueta # Articulo: Contiene el Código de barras del artículo, Formato: Texto" + 
					"Etiqueta Articulo: Contiene la descripción del artículo, Formato: Texto" + 
					"Etiqueta Supergrupo: Contiene el supergrupo al que pertenece el artículo, Formato: Texto" + 
					"Etiqueta # FA: Contiene el identificador de la fuente de abasto, Formato: Texto" + 
					"Etiqueta FA: Contiene la descripción o nombre de la fuente de abasto, Formato: Texto" + 
					"Etiqueta Tipo FA: Contiene el tipo de fuente de abasto del artículo, Formato: Texto" + 
					"Etiqueta Estatus Actual: Contiene el estatus actual del artículo, Formato: Texto" + 
					"Etiqueta Estatus al Planchar: Contiene el estatus del artículo al realizar el planchado, Formato: Texto" + 
					"Etiqueta Lego: Contiene el nombre del lego al que pertenece el artículo., Formato: Texto" + 
					"Etiqueta Posibles Legos: Contiene el nombre del (los) lego(s) al(os) que pertenecerá el artículo., Formato: Texto" + 
					"Etiqueta Blindado: Contiene una breve descripción del blindaje del que es objeto, Formato: Texto" + 
					"Etiqueta Vigencia: Contiene la fecha de vigencia del paquete de planchado, Formato: Texto" + 
					"Etiqueta Acciones a Realizar: ---, Formato: Texto");
	// paso 9
			addStep("Validar el pie del reporte con los siguientes elementos:" + 
					"Nombre de usuario: Nombre del usuario que ejecutó el preview del planchado . Formato: Texto" + 
					"Fecha y hora de ejecución del planchado: " + 
					"Fecha y hora de ejecución del preview del planchado con resultado exitoso." + 
					"Formato: Texto, considerando mostrar la fecha de la siguiente manera: dd MM yyyy HH:mm");
	// paso 10
			addStep("Buscar por Plaza, Nombre o Descripcion" + 
					"Seleccionar del campo ComboBox Plaza con  10MON " + 
					"Capturar en el campo TextBox Nombre con un nombre valido." + 
					"Capturar en el campo TextBox Descripcion" + 
					"con un descripción valido." + 
					"Hacer clic en el botón Buscar");	
			
	// paso 11
			addStep("Hacer clic en el botón ImageControl 'Previews' hoja en blanco que se encuentra en la columna Preview.");	
	// paso 12
			addStep("Hacer clic en el botón Guardar" + 
					"La aplicación abre el explorador con un directorio por default para indicar el nombre con el que se quiere guardar");
	// paso 13
			addStep("Abrir el directorio donde se guardo el reporte.");
	// paso 14
			addStep("Buscar por Plaza, Nombre o Descripcion" + 
					"Seleccionar del campo ComboBox Plaza con 10MON." + 
					"Capturar en el campo TextBox Nombre con un nombre valido" + 
					"Capturar en el campo TextBox Descripcion" + 
					"con un descripción valido" + 
					"Hacer clic en el boton Buscar");	
	// paso 15
			addStep("Hacer clic en el botón ImageControl Previews hoja en blanco que se encuentra en la columna Preview");

	// paso 16
			addStep("Hacer clic en el botón Cancelar.");
			util.AbrirExcelExistente(rutaExcel);
			u.hardWait(10);
			testCase.addScreenShotCurrentStep("Reporte Excel");
			util.leerCeldasExcel_fila_Columna(rutaExcel,6,18);
				
			
	
	
	
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
		return "El objetivo de este caso de prueba es validar las acciones abrir, guardar y cancelar reporte de la pantalla 'Previews' para planchado.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_Validacion_Planchado_Previews_Abrir_Guardar_Cancelar_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

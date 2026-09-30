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

public class ATC_FT_002_Validacion_Planchado_Preview_Pantalla extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_002_Validacion_Planchado_Preview_Pantalla_Test(HashMap<String, String> data) throws Exception {
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
	 
	 * Utilerias
	 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		String windowUltimus = u.getDriver().getWindowHandle();
		pomPlanchado util = new pomPlanchado(u,testCase);
		pomUltimus ultimus = new pomUltimus (u);
	
		
// *** Es importante cambiar la ruta del archivo descargado que se leera durante la ejecucion en el Campo "ExcelPath" del Data DataProvider
			
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
			addStep("Buscar por Plaza o Nombre o Descripcion" + 
					"Seleccionar del campo ComboBox Plaza  con 10MON"   + 
					"Capturar en el campo TextBox Nombre con un nombre valido" + 
					"Capturar en el campo TextBox Descripcion con un descripcion valido" + 
					"Hacer clic en el boton 'Buscar'");
			
				util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
				testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
	// paso 5
			addStep("Seleccionar un registro del grid.");
			
			
			
			
	// paso 6
			addStep("Hacer clic en el boton ImageControl 'Run Preview' flecha verde que se encuentra en la columna Preview.");	
			util.seleccionarRegistroGrid();
			
	// paso 7
			addStep("Hacer clic en el boton 'Aceptar'.");
	// paso 8
			addStep("Hacer clic en el boton 'Cerrar'.");
			
			util.clickBotonConfirmar();
			
	// paso 9
			addStep("Hacer clic en el boton ImageControl 'Run Preview' flecha verde que se encuentra en la columna Preview.");
			util.seleccionarRegistroGrid();
	// paso 10
			addStep("Hacer clic en el boton 'Aceptar'.");
	// paso 11
			addStep("Hacer clic en el boton 'Cerrar'.");
			util.clickBotonConfirmar();
	// paso 12
			addStep("Hacer clic en el boton ImageControl 'Run Preview' flecha verde que se encuentra en la columna Preview.");
			util.seleccionarRegistroGrid();
	// paso 13
			addStep("Hacer clic en el boton 'Aceptar'.");
	// paso 14
			addStep("Hacer clic en el boton 'Cerrar'.");
			testCase.addScreenShotCurrentStep("Hacer clic en el boton 'Cerrar");
			util.clickBotonConfirmar();
	// paso 15
			addStep("Hacer clic en el boton ImageControl 'Previews' hoja en blanco que se encuentra en la columna Preview.");
			util.seleccionarPreviewsHojaBlanco();
	// paso 16
			addStep("Hacer clic en el boton 'Abrir'");
	// paso 17
			addStep("Validar el encabezado del reporte con los siguientes elementos:" + 
					"Etiqueta: Femsa Comercio, Leyenda fija del reporte, con formato Texto." + 
					"Etiqueta: Planchado de Articulos [x]" + 
					"Leyenda fija como titulo del reporte [x]: CR de la plaza, con formato Texto.");
			
	// paso 18
			addStep("Validar el layout del reporte con los siguientes elementos:" + 
					"Etiqueta # Plaza: Contiene el CR de la Plaza, Formato: Texto" + 
					"Etiqueta Plaza: Contiene la descripcion de la Plaza, Formato: Texto" + 
					"Etiqueta Location: Contiene un identificador de la tienda, Formato: Texto" + 
					"Etiqueta # Tienda: Contiene el CR de la Tienda, Formato: Texto" + 
					"Etiqueta Tienda: Contiene la descripcion de la tienda, Formato: Texto" + 
					"Etiqueta # Articulo: Contiene el Codigo de barras del articulo, Formato: Texto" + 
					"Etiqueta Articulo: Contiene la descripcion del articulo, Formato: Texto" + 
					"Etiqueta Supergrupo: Contiene el supergrupo al que pertenece el articulo, Formato: Texto" + 
					"Etiqueta # FA: Contiene el identificador de la fuente de abasto, Formato: Texto" + 
					"Etiqueta FA: Contiene la descripcion o nombre de la fuente de abasto, Formato: Texto" + 
					"Etiqueta Tipo FA: Contiene el tipo de fuente de abasto del articulo, Formato: Texto" + 
					"Etiqueta Estatus Actual: Contiene el estatus actual del articulo, Formato: Texto" + 
					"Etiqueta Estatus al Planchar: Contiene el estatus del articulo al realizar el planchado, Formato: Texto" + 
					"Etiqueta Lego: Contiene el nombre del lego al que pertenece el articulo., Formato: Texto" + 
					"Etiqueta Posibles Legos: Contiene el nombre del (los) lego(s) al(os) que pertenecera el articulo., Formato: Texto" + 
					"Etiqueta Blindado: Contiene una breve descripcion del blindaje del que es objeto, Formato: Texto" + 
					"Etiqueta Vigencia: Contiene la fecha de vigencia del paquete de planchado, Formato: Texto" + 
					"Etiqueta Acciones a Realizar: ---, Formato: Texto");
	// paso 19
			addStep("Validar el pie del reporte con los siguientes elementos:" + 
					"Nombre de usuario: Nombre del usuario que ejecuto el preview del planchado . Formato: Texto" + 
					"Fecha y hora de ejecucion del planchado: " + 
					"Fecha y hora de ejecucion del preview del planchado con resultado exitoso." + 
					"Formato: Texto, considerando mostrar la fecha de la siguiente manera: dd MM yyyy HH:mm");
	// paso 20
			addStep("Hacer clic en el boton ImageControl 'Previews' hoja en blanco que se encuentra en la columna Preview.");					
	// paso 21
			addStep("Hacer clic en el boton 'Guardar'");	
	// paso 22
			addStep("Hacer clic en el boton 'Guardar'");
	// paso 23
			addStep("Abrir el directorio donde se guardo el reporte.");
			
	// paso 24
			addStep("Hacer clic en el boton ImageControl 'Previews' hoja en blanco que se encuentra en la columna Preview.");	
	// paso 25
			addStep("Hacer clic en el boton Cancelar");
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
		return "El objetivo de este caso de prueba es validar los elementos de la pantalla Previews de Planchados ";
	}
	

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_Validacion_Planchado_Preview_Pantalla_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

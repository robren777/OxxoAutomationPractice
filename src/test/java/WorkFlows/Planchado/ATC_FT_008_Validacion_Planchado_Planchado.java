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

public class ATC_FT_008_Validacion_Planchado_Planchado extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_008_Validacion_Planchado_Planchado_Test(HashMap<String, String> data) throws Exception {
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
			addStep("Buscar por Plaza o Nombre o Descripcion" + 
					"Seleccionar del campo ComboBox 'Plaza' con '10MON'" + 
					"Capturar en el campo TextBox 'Nombre' con un nombre valido." + 
					"Capturar en el campo TextBox 'Descripcion'" + 
					"con un descripcion valido." + 
					"Hacer clic en el boton 'Buscar'");
			
			util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripcion");
			
	// paso 5
			addStep("Seleccionar un registro del grid.");
	// paso 6
			addStep("Hacer clic en el boton ImageControl Planchar (flecha verde) que se encuentra en la columna Planchar.");
			util.seleccionarRegistroGrid();
	// paso 7
			addStep("Hacer clic en el boton Aceptar.");		
	
	// paso 8
			addStep("Hacer clic en el boton Cerrar");
			testCase.addScreenShotCurrentStep("Hacer clic en el boton Cerrar");
			util.clickBotonConfirmar();
	// paso 9
			addStep("Validar que la tienda se encuentre en estatus 'LA TIENDA SE MODIFICO Y NO SE HA ENVIADO'." + 
					"Hacer clic en el boton ImageControl 'Planchar' flecha verde que se encuentra en la columna Planchar.");
			util.seleccionarRegistroGrid();
	// paso 10
			addStep("Hacer clic en el boton Aceptar");	
			
	// paso 11
			addStep("Hacer clic en el boton Cerrar");	
			testCase.addScreenShotCurrentStep("Hacer clic en el boton Cerrar");
			util.clickBotonConfirmar();
	
	// paso 12
			addStep("Validar que la tienda se encuentre en estatus 'LA TIENDA NO TIENE SUS SECCIONES REQUERIDAS SEMBRADAS'" + 
					"Hacer clic en el boton Parametros." + 
					"Hacer clic en el boton ImageControl 'Planchar' flecha verde que se encuentra en la columna Planchar.'");
			util.seleccionarRegistroGrid();
	// paso 13
			addStep("Hacer clic en el boton Aceptar");	
	// paso 14
			addStep("Hacer clic en el boton Cancelar");	
			testCase.addScreenShotCurrentStep("Hacer clic en el boton Cerrar");
			util.clickBotonConfirmar();
	
	
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
		return "El objetivo de este caso de prueba es validar el comportamiento del boton Planchar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_Validacion_Planchado_Planchado_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

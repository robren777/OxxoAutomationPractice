package WorkFlows.Planchado;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Planchado.pomPlanchado;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_Validacion_Planchado_Estatus extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_005_Validacion_Planchado_Estatus_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> PLANCHADO");
	
	/**
	 * Variables
	 * 
	 */
		
		String plaza  = data.get("FiltroPlaza");
		String nombre = data.get("Nombre");
		String nombre2 = data.get("Nombre2");
		String descripcion = data.get("Descripcion");
		String activo = data.get("Activo");
		String activo2 = data.get("Activo2");

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
			addStep("Hacer clic en el boton 'Nuevo' que se encuentra al final del grid.");
			
			
			
	// paso 5
			addStep("Capturar en el campo TextBox 'Nombre' con un valor valido." + 
					"Capturar en el campo TextBox 'Descripcion'con un valor valido." + 
					"Seleccionar del campo ComboBox 'Plaza' con '10MON'." + 
					"Seleccionar del campo ComboBox 'Activo' con 'NO'.");
	// paso 6
			addStep("Hacer clic en el boton 'Insertar'");	
			nombre = nombre + "_" + util.cadenaAleatoria(3);
			util.insertarNuevoPlanchado(plaza, nombre, descripcion, activo);
			testCase.addScreenShotCurrentStep("Hacer clic en el boton 'Insertar");
			util.clickBotonConfirmar();
			
	// paso 7
			addStep("Validar en la columna estatus que el estatus del planchado sea 'Inactivo'");
			
			util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
	// paso 8
			addStep("Hacer clic en el boton 'Nuevo' que se encuentra al final del grid.");
	// paso 9
			addStep("Capturar en el campo TextBox 'Nombre' con un valor valido." + 
					"Capturar en el campo TextBox 'Descripcion'con un valor valido." + 
					"Seleccionar del campo ComboBox 'Plaza' con '10MON'" + 
					"Seleccionar del campo ComboBox 'Activo' con 'SI'.");
	// paso 10
			addStep("Hacer clic en el boton 'Insertar'");	
			nombre2 = nombre2 + "_" + util.cadenaAleatoria(3);
			util.insertarNuevoPlanchado(plaza, nombre2, descripcion, activo2);
			testCase.addScreenShotCurrentStep("Hacer clic en el boton 'Insertar");
			util.clickBotonConfirmar();
	// paso 11
			addStep("Validar en la columna estatus que el estatus del planchado sea 'Activo'.");	
			u.hardWait(10);
			u.click(By.xpath("//input[@class='btn botonAzul'and @value='Limpiar']"));
			util.buscarSeleccionarPlanchado(plaza, nombre2, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripciono");
				
	
	
	
	
	
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
		return "El objetivo de este caso de prueba es validar el estatus del registro nuevo de planchado 'Activo' e 'Inactivo'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_Validacion_Planchado_Estatus_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

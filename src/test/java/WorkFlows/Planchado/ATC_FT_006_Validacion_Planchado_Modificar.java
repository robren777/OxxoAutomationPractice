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

public class ATC_FT_006_Validacion_Planchado_Modificar extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_006_Validacion_Planchado_Modificar_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> PLANCHADO");
	
	/**
	 * Variables
	 * 
	 */
		
		
		String plaza  = data.get("FiltroPlaza");
		String nombre = data.get("Nombre");
		String descripcion = data.get("Descripcion");
		String activo = data.get("Activo");
		String nombreModi = data.get("NombreModificado");
		String descripcionModi = data.get("DescripcionModificado");
	

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
					"Seleccionar del campo ComboBox 'Plaza' con '10MON'." + 
					"Capturar en el campo TextBox 'Nombre' con un nombre valido." + 
					"Capturar en el campo TextBox 'Descripcion'" + 
					"con un descripcion valido." + 
					"Hacer clic en el boton 'Buscar'.");
			util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripcion");
	// paso 5
			addStep("Seleccionar un registro del grid.");
			testCase.addScreenShotCurrentStep(u, "registros");
			
	// paso 6
			addStep("Hacer clic en el boton ImageControl Editar que se encuentra en la columna del grid Editar.");
			
	// paso 7
			addStep("Validar que la aplicacion muestre los siguientes elementos:" + 
					"Etiqueta ''Nombre'' Etiqueta con la leyenda 'Nombre'." + 
					"Campo de Texto ''Nombre '' Contiene el nombre de planchado configurado.Obligatorio Si." + 
					"Etiqueta ''Descripcion'' Etiqueta con la leyenda 'Descripcion'." + 
					"Campo de Texto ''Descripcion'' Contiene la descripcion de planchado configurada.Obligatorio No." + 
					"Etiqueta ''Plaza'' Etiqueta con la leyenda 'Plaza'." + 
					"Campo ComboBox ''Plaza'' Contiene la plaza configurada para el planchado, Debe aparecer Inhabilitada." + 
					"Etiqueta ''Activo'' Etiqueta con la leyenda 'Activo'." + 
					"Campo ComboBox ''Activo'' Indica si el planchado esta activo o no.Obligatorio Si." + 
					"Boton ''Actualizar'' Guarda " + 
					"en la BD la informacion del planchado modificada." + 
					"Boton ''Cancelar'' Cierra la " + 
					"pop-up.'");
	
	// paso 8
			addStep("Capturar en el campo TextBox 'Nombre' con un valor valido" + 
					"Capturar en el campo TextBox 'Descripcion'" + 
					"con un valor valido." + 
					"Seleccionar del campo ComboBox 'Activo' con 'SI'");
				util.EditarRegistroPlanchado(plaza, nombreModi, descripcionModi, activo);
				testCase.addScreenShotCurrentStep("Editar Registro Planchado y Cancelar");
	// paso 9
			addStep("Hacer clic en el boton 'Cancelar'.");
			u.click(By.xpath("//input[@ng-click='modal.Planchado.visible = false' and @value='Cancelar']"));
			testCase.addScreenShotCurrentStep(u, "Cancelar");
	// paso 10
			addStep("Hacer clic en el boton ImageControl 'Editar' que se encuentra en la columna del grid Editar.");	
			
	// paso 11
			addStep("Capturar en el campo TextBox 'Nombre' con Capturar en el campo TextBox 'Descripcion' con Seleccionar del campo ComboBox 'Activo' con 'SI'");	
			util.EditarRegistroPlanchado(plaza, nombreModi, descripcionModi, activo);
			testCase.addScreenShotCurrentStep("Editar Registro Planchado ");
	// paso 12
			addStep("Hacer clic en el boton 'Actualizar'");
			u.click(By.id("btnAceptarPlanchadoModal"));
			testCase.addScreenShotCurrentStep("Registro Planchado Actualizado");
			util.clickBotonConfirmar();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("Registro Actualizado");	
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
		return "El objetivo de este caso de prueba es validar la modificacion de un planchado";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_Validacion_Planchado_Modificar_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

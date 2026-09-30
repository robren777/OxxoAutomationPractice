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

public class ATC_FT_011_Validacion_Planchado_Parametros_Pantalla extends BaseExecution{
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_011_Validacion_Planchado_Parametros_Pantalla_Test(HashMap<String, String> data) throws Exception {
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
		//utils.sql.SQLUtil dbWF = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_WF, GlobalVariables.DB_USER_WF,GlobalVariables.DB_PASSWORD_WF);
		
		
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
			addStep("Hacer clic en el boton ImageControl Parametros de la columna Parametros.");
	// paso 6
			addStep("Validar que la pantalla muestra los siguientes elementos:" + 
					"Etiqueta ''Titulo'' Etiqueta con la leyenda Parametros(+), (+) Nombre del planchado." + 
					"Etiqueta ''Tiendas'' Etiqueta con la leyenda Tiendas." + 
					"Lista ''Tiendas'' Obtiene la lista de tiendas pertenecientes al planchado configurado." + 
					"Lista ''TiendasSeleccionadas'' Contiene la lista de tiendas seleccionadas de lstTiendas.Obligatorio Si�." + 
					"Boton ''AgregaTienda (>)'' " + 
					"Agrega en lstTiendasSeleccionadas la tienda seleccionada en lstTiendas." + 
					"Boton ''AgregaTiendas (>>)'' " + 
					"Agrega en lstTiendasSeleccionadas todas las tiendas de lstTiendas. " + 
					"Boton ''EliminaTienda (<)'' " + 
					"Elimina de lstTiendasSeleccionadas la tienda seleccionada y la agrega en lstTiendas." + 
					"Boton ''EliminaTiendas (<<)'' " + 
					"Elimina de lstTiendasSeleccionadas todas las tiendas y las agrega en lstTiendas. " + 
					"Etiqueta ''Legos'' Etiqueta con la leyenda Legos." + 
					"Lista ''Legos'' Obtiene la lista de legos pertenecientes a las tiendas mostradas." + 
					"Lista ''LegosSeleccionados'' Contiene la lista de legos seleccionadas de lstLegos.Obligatorio Si�." + 
					"Boton ''AgregaLego (>)'" + 
					"Agrega en lstLegosSeleccionados el lego seleccionado en lstLegos." + 
					"Boton ''AgregaLegos (>>)'" + 
					"Agrega en lstLegosSeleccionados todos los legos de lstLegos. " + 
					"Boton ''EliminaLego (<)'" + 
					"Elimina de lstLegosSeleccionados el lego seleccionado y lo agrega en lstLegos." + 
					"Boton ''EliminaLegos (<<)'" + 
					"Elimina de lstLegosSeleccionados todos los legos y los agrega en lstLegos." + 
					"Etiqueta ''Paquetes'' Etiqueta con la leyenda Paquetes." + 
					"Lista ''Paquetes'' Obtiene la lista de paquetes configurados pertenecientes a la plaza. " + 
					"Lista ''PaquetesSeleccionados'' Contiene la lista de paquetes seleccionadas de lstPaquetes. Obligatorio Si�." + 
					"Boton ''AgregaPaquete (>)'" + 
					"Agrega en lstPaquetesSeleccionados el paquete seleccionado en lstPaquetes. " + 
					"Boton ''AgregaPaquetes (>>)'" + 
					"Agrega en lstPaquetesSeleccionados todos los paquetes de lstPaquetes. " + 
					"Boton ''EliminaPaquete (<)'" + 
					"Elimina de lstPaquetesSeleccionados el paquete seleccionado y lo agrega en lstPaquetes." + 
					"Boton ''EliminaPaquetes (<<)'" + 
					"Elimina de lstPaquetesSeleccionados todos los paquetes y los agrega en lstPaquetes. " + 
					"Boton ''InsertarParametros'" + 
					"Guarda en la BD la informacion de los parámetros para el planchado capturada." + 
					"Boton ''Cancelar'' Cierra la " + 
					"pop-up.'");
	// paso 7
			addStep("Validar el estado inicial de los elementos de la pantalla:" + 
					"Etiqueta ''Titulo'' Habilitado con valor Parametros (+), (+) nombre del planchado." + 
					"Etiqueta ''Tiendas'' Habilitado con valor Tiendas" + 
					"Lista ''Tiendas'' Habilitado con Lista de tiendas pertenecientes a la plaza configurada." + 
					"Lista ''Tiendas Seleccionadas'' Habilitado con Vacio." + 
					"Boton ''Agrega Tienda (>)'" + 
					"Habilitado con nombre (>)'" + 
					"Boton ''Agrega Tiendas (>>)'" + 
					"Habilitado con nombre >>." + 
					"Boton ''Elimina Tienda (<)'' " + 
					"Habilitado con nombre (<)'" + 
					"Boton ''Elimina Tiendas (<<)'' " + 
					"Habilitado con nombre (<<)" + 
					"Etiqueta ''Legos'' Habilitado con valor Legos" + 
					"Lista ''Legos'' Habilitado con Lista de legos pertenecientes a las tiendas seleccionadas en " + 
					"lstTiendas..\r\n" + 
					"Lista ''Legos Seleccionados'' Habilitado con Vacio." + 
					"Boton ''Agrega Lego (>)'' " + 
					"Habilitado con nombre (>)" + 
					"Boton ''Agrega Legos (>>)'' " + 
					"Habilitado con nombre (>>)" + 
					"Boton ''Elimina Lego (<)'" + 
					"Habilitado con nombre (<)" + 
					"Boton ''Elimina Legos (<<)'" + 
					"Habilitado con nombre (<<)" + 
					"Etiqueta ''Paquetes'' Habilitado " + 
					"con valor Paquetes." + 
					"Lista ''Paquetes'' Habilitado" + 
					"con Lista de paquetes pertenecientes a la plaza configurada." + 
					"Lista ''PaquetesSeleccionados'' Habilitado con Vacio." + 
					"Boton ''Agrega Paquete (>)''" + 
					"Habilitado con nombre (>)" + 
					"Boton ''Agrega Paquetes (>>)'' " + 
					"Habilitado con nombre (>>)" + 
					"Boton ''Elimina Paquete (<)'" + 
					"Habilitado con nombre (<)" + 
					"Boton ''Elimina Paquetes (<<)'" + 
					"Habilitado con nombre (<<)" + 
					"Boton ''Insertar Parametros'" + 
					"Habilitado con nombre Insertar " + 
					"Boton ''Cancelar'' Habilitado " + 
					"con nombre Cancelar.'");	
				util.ValidaPlanchadoParametrosPantalla();
				/*
				 * // paso 8
				 * addStep("Validar los datos de la lista de Tienda con la consulta (SELECT TIENDA_CR,TIENDA_DESC"
				 * + "FROM XXVA_ENG_TIENDAS_MV" + "WHERE PLAZA_CR = 'PLAZA SELECCIONADA'" +
				 * "ORDER BY TIENDA_CR)");
				 * 
				 * 
				 * // paso 9
				 * addStep("Validar los datos de la lista de Lego con la consulta (SELECT DESCRIPCION_LEGO"
				 * + "FROM XXVA_SEM_LEGOS WHERE PLAZA_ID = 'Dato de la plaza seleccionada' ;)");
				 * 
				 * // paso 10
				 * addStep("Validar los datos de la lista de paquetes con la consulta (SELECT NAME"
				 * + "FROM XXVA_ENG_PLANCHADO_PACKAGES_V" + "WHERE OPERATOR = 'INCLUDE'" +
				 * " AND ENABLED = 'Si'" + " AND PLAZA_ID = 'Dato de la plaza" + "seleccionada'"
				 * + "ORDER BY NAME ;)");
				 */	

	
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
		return "El objetivo de este caso de prueba es validar los elementos de la pantalla Parámetros de Planchados ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_011_Validacion_Planchado_Parametros_Pantalla_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

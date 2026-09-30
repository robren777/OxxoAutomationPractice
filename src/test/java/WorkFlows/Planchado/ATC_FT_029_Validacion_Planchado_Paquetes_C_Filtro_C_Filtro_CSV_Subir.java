package WorkFlows.Planchado;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Planchado.pomPlanchado;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_029_Validacion_Planchado_Paquetes_C_Filtro_C_Filtro_CSV_Subir extends BaseExecution{
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_029_Validacion_Planchado_Paquetes_C_Filtro_C_Filtro_CSV_Subir_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> PLANCHADO");
	
	/**
	 * Variables
	 * 
	 */
		String plaza  = data.get("FiltroPlaza");
		String nombre = data.get("Nombre");
		String descripcion = data.get("Descripcion");
		String nomArchivo = data.get("NomArchivo");

	/**
	 * 
	 * Utilerias
	 */
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
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
			addStep("Hacer clic en el menu Planchado  Paquetes  del menú de la izquierda de la pantalla.");
			util.selectMenuPaquetes();
			testCase.addScreenShotCurrentStep("Menú Paquetes");	
			
	// paso 3
			addStep("Buscar por Plaza, Nombre o Descripcion:" + 
					"Seleccionar del campo 'Plaza' con un valor valido." + 
					"Capturar en el campo 'Nombre' con un valor valido." + 
					"Capturar en el campo 'Descripción' con un valor valido.");
		
			
	// paso 4
			addStep("Hacer clic en el Botón 'Buscar'");
			util.buscarSeleccionarPlanchado(plaza, nombre, descripcion);
			testCase.addScreenShotCurrentStep("Buscar por Plaza o Nombre o Descripcióno");
			
	// paso 5
			addStep("Hacer clic en el botón Subir ImageControl Flecha Verde que se encuentra en la columna Filtros");
			util.seleccionarRegistroGrid();
			
	// paso 6
			addStep("Validar que se muestren los elementos siguientes en la pantalla:" + 
					"Etiqueta ''Titulo'' Etiqueta con la leyenda Subir CSV (Nombre del paquete)." + 
					"Etiqueta ''Archivo'' Etiqueta con la leyenda Archivo" + 
					"Botón ''Seleccionar Archivo'' Permite seleccionar un archivo del equipo (PC)." + 
					"Etiqueta ''Archivo Seleccionado'' Mostrará el nombre del archivo seleccionado. Por default, y si no se ha seleccionado ningún archivo, deberá mostrar el mensaje: No se eligió archivo" + 
					" Etiqueta ''Aviso Extensión'' Etiqueta con la leyenda CSV (delimitado por comas) (*.csv)" + 
					"Botón ''Subir'' Abre una pop-up con el preview de la información que se está cargando en el archivo." + 
					"Botón ''Cancelar'' Cierra la pop-up.");
			util.validarElementosPantallaSubirCSV();
			testCase.addScreenShotCurrentStep("Validar que se muestren los elementos");
	// paso 7
			addStep("\"Crear un archivo con extensión .csv con las siguientes columnas:" + 
					"ITEM Código de barras del artículo." + 
					"PLAZA_CR ID de la plaza." + 
					"TIENDA_CR ID de la tienda." + 
					" ORIGEN Sistema origen.");		
			
	// paso 8
			addStep("Capturar un registro con los siguientes valores:" + 
					"ITEM, PLAZA_CR, TIENDA_CR = consulta" + 
					"ORIGEN = PLA" + 
					"Donde consulta (SELECT I.ITEM, T.PLAZA_CR, T.TIENDA_CR FROM ITEM_LOC@FCRMSPRD I, XXVA_ENG_TIENDAS_MV T" + 
					"WHERE I.LOC = T.LOC" + 
					"AND T.PLAZA_CR = 'Dato de la plaza'" + 
					"AND T.TIENDA_CR = 'Dato de la tienda'" + 
					"AND STATUS = 'A' ;)");		
	// paso 9
			addStep("Hacer clic en el botón Seleccionar Archivo");
	// paso 10
			addStep("Buscar y seleccionar el archivo creado con extensión .csv");
	// paso 11
			addStep("Hacer clic en el botón Abrir.");
	// paso 12
			addStep("Hacer clic en el botón Subir");
			util.SubirArchivo(nomArchivo);
	// paso 13
			addStep("Validar que los datos mostrados en el grid coincidan con la" + 
					"consulta (SELECT I.ITEM,I.LOCAL_ITEM_DESC, T.PLAZA_CR, " + 
					"T.TIENDA_CR" + 
					"FROM ITEM_LOC@FCRMSPRD I, XXVA_ENG_TIENDAS_MV T" + 
					"WHERE I.LOC = T.LOC" + 
					"AND T.PLAZA_CR = 'Dato de la " + 
					"Plaza'" + 
					"AND T.TIENDA_CR = 'Dato de la Tienda'" + 
					"AND STATUS = 'A'" + 
					"AND I.ITEM = 'Articulo cargado en el archivo'; )");
			
	// paso 14
			addStep("Hacer clic en el botón Cerrar.");
			
	// paso 15
			addStep("Hacer clic en el botón Filtros ImageControl Hoja blanca que se encuentra en la columna Filtros.");
	// paso 16
			addStep("Validar que el artículo se encuentre en el grid.");
	
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
		return "El objetivo de este caso de prueba es validar al subir el archivo .csv";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_029_Validacion_Planchado_Paquetes_C_Filtro_C_Filtro_CSV_Subir_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

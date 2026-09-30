package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentPlantilla;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: 
 * - Validar alta de un Usuario nuevo
 * - Validación de no dejar vacio los campos
 * - Validar aparezca un mensaje que el campo solo permite caracteres alfabeticos en el campo Nombre
 * - Validar aparezca un mensaje que el campo solo permite caracteres numericos en el campo Usuario, 
 * - Validar se calculen de manera correcta las semanas
 * - Validar que no permita agregar un usuario con una antiguedad mayor a 1 año.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-084-Validar el alta de un nuevo usuario CEDIS
 * 
 * PREREQUISITOS:
 * 		- Contar con acceso a la VPN
 * 		- Agregar en la columna CEDIS del Data Provider un CEDIS que exista 
 * 		- Agregar en la columna Usuario del Data Provider un Usuario con solo números.
 * 		- Agregar en la columna Antiguedad del Data Provider una antiguedad entre la fecha actual y menos de 1 años de antiguedad.
 * 		- Agregar en la columna Nombre del Data Provider un nombre con solo letras.
 * 		- Agregar en la columna Turno del Data Provider un turno del 1 al 3.
 * 		- Agregar en la columna Tipocolaborador del Data Provider un tipo de colaborador que exista.
 * 		- Agregar en la columna Tipoincentivo del Data Provider un tipo de incentivo que exista.
 * 		- Agregar en la columna Posicion del Data Provider una posicion que exista.
 * 		- Agregar en la columna Nombreerroneo del Data Provider un nombre con números.
 * 		- Agregar en la columna Usuarioerroneo del Data Provider un no empleado con numeros y letras.
 * 		- Agregar en la columna Antiguedaderronea del Data Provider una fecha mayor a 1 año de antiguedad o una fecha mayor a la actual.
 * 
 * @author Carlos Martínez
 * @date 24-09-25
 */
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;AutomationMVP2;I24025_Labor_Management",
		userStoryId = 147309
		)
public class ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String CEDIS = data.get("CEDIS");
		String Usuario = data.get("Usuario");	
		String Antiguedad = data.get("Antiguedad");
		String Nombre = data.get("Nombre");
		String Turno = data.get("Turno");
		String Tipocolaborador = data.get("Tipodecolaborador");
		String Tipoincentivo = data.get("Tipodeincentivo");
		String Posicion = data.get("Posicion");
		String Nombreerroneo = data.get("Nombrerroneo");
		String Usuarioerroneo = data.get ("Usuarioerroneo");
		String Antiguedaderronea = data.get("Antiguedaderronea");
		
		/**
		 * Utilerias
		 ******************************************************************************************/
		//utils.sql.SQLUtil dbFCTPEQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW, GlobalVariables.DB_USER_FCTPEQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTPEQA_QAVIEW);
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);
		LaborManagment la = new LaborManagment(u, testCase); 
		LaborManagmentPlantilla lap = new LaborManagmentPlantilla(u, testCase);
		
		/**
		 * Pasos de evidencia
		 ******************************************************************************************/
		addStep("Inicializamos el navegador y accesamos a access control");		
		la.navegarURLsConManejoDeError(url,url2);
		
		addStep("Login LaborManagment");
		la.loginLaborManagment(user, password);
	
	    addStep("Ingresar al portal");
		assertTrue(la.clicPortalIncentivos(),"No se encontro el Portal de Incentivos");
		
		addStep("Seleccionar del menú la opción Plantilla");
		assertTrue(lap.opcionmenuplantilla(),"No cargo la pantalla de Plantilla");
		
		addStep("Seleccionar el botón de Alta");
		assertTrue(lap.seleccionarnuevousuario(),"No se muestra la ventana emergente de alta plantilla");
		
		addStep("Validar se muestren todo los campos");
		assertTrue(lap.validarCamposAlta(),"No se muestra la ventana emergente de alta plantilla");
		
		addStep("Dar clic en guardar con los campos vacios y validar que aparezca un mensaje \"Favor de completar todos los campos vacíos\"");
		assertTrue(lap.Validarmensajecompletarloscampos(),"No se mostro el mensaje esperado");
		
		addStep("Validar que si se agrega algun carácter diferente a letras en el campo nombre, se muestre el mensaje \"El campo solo permite datos alfabeticos\"");
		assertTrue(lap.validarsolocaracteresalfanumericos(CEDIS, Usuario, Antiguedad, Nombreerroneo, Turno, Tipocolaborador, Tipoincentivo, Posicion),"Permitio agregar valores alfabeticos");
		
		addStep("Validar que si se agrega algun carácter diferente a números en el campo No.empleado, se muestre el mensaje \"El campo solo permite datos númericos\"");
	    assertTrue(lap.validarsolocaracteresnumericos(Nombre, Usuarioerroneo),"Permitio agregar valores alfabeticos");
	    
	    addStep("Valdiar que al llenar el campo Antigüedad (Fecha Ingreso) el campo semana se calcule de manera correcta colocando el número de semanas del dia de hoy hasta el día de antigüedad.");
	    assertTrue(lap.validarcalculosemana(),"Permitio agregar valores alfanumericos");
	    
	    addStep("Validar que al llenar el campo Antigüedad (Fecha Ingreso) con una fecha mayor al día actual o menor a 1 año atrás se muestre el mensaje \"La fecha de ingreso no puede ser mayor al día actual y menor a un año atrás\".");
	    assertTrue(lap.validarfechamayoraunano(Usuario, Antiguedaderronea),"Permitio agregar una fecha mayor a 1 año");
	    
	    addStep("Llenar los campos Nombre, Turno, Antigüedad (fecha ingreso), Tipo de colaborador, Tipo de incentivo, Posición y Posición clave, con información valida y validar que al dar clic en guardar se guarde el registro y se muestre el mensaje \"El usuario se agregó de manera correcta\"");
	    assertTrue(lap.validaragregarunusuario(Antiguedad),"El usuario usuario no se agrego");
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
		return "- Contar con acceso a la VPN\r\n"
				+ "- Agregar en la columna CEDIS del Data Provider un CEDIS que exista \r\n"
				+ "- Agregar en la columna Usuario del Data Provider un Usuario con solo números.\r\n"
				+ "- Agregar en la columna Antiguedad del Data Provider una antiguedad entre la fecha actual y menos de 1 años de antiguedad.\r\n"
				+ "- Agregar en la columna Nombre del Data Provider un nombre con solo letras.\r\n"
				+ "- Agregar en la columna Turno del Data Provider un turno del 1 al 3.\r\n"
				+ "- Agregar en la columna Tipocolaborador del Data Provider un tipo de colaborador que exista.\r\n"
				+ "- Agregar en la columna Tipoincentivo del Data Provider un tipo de incentivo que exista.\r\n"
				+ "- Agregar en la columna Posicion del Data Provider una posicion que exista.\r\n"
				+ "- Agregar en la columna Nombreerroneo del Data Provider un nombre con números.\r\n"
				+ "- Agregar en la columna Usuarioerroneo del Data Provider un no empleado con numeros y letras.\r\n"
				+ "- Agregar en la columna Antiguedaderronea del Data Provider una fecha mayor a 1 año de antiguedad o una fecha mayor a la actual.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar funcione el botón de cargar archivo en diferentes navegadores.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Carlos Martinez Ortega";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_ValidarelaltadeunnuevousuarioCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
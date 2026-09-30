package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_259_Confirmar_que_solicite_la_captura_de_los_nuevos_campos extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_259_Confirmar_que_solicite_la_captura_de_los_nuevos_campos_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String diferenciador= data.get("Diferenciador");
		
		// Paso 1
		addStep("Ingresar a la <LigaCNT> del portal de aplicaciones.");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(URL);
		testCase.addScreenShotCurrentStep("Imagen de login");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		
		// Paso 2
		addStep("Ingresar el usuario y contrase�a para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		
		// Paso 3
		addStep("Selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus Nueva que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		// Paso 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		// Paso 6
		addStep("Usuario captura los campos requeridos por la pantalla a excepci�n del \"Concepto Diferenciador\" y selecciona Actualizar");
		fO.Diferenciador(" ");
		fO.clickBotonActualizarEntorno();
		boolean appearsmsg1 = fO.comprobarMensaje_onAlert("El Concepto Diferenciador es requerido.");
		assertTrue(appearsmsg1, "No se encontro el mensaje de que el campo de Concepto Diferenciador es requerido.");
		
		fO.aceptar_onAlert();
		
		// Paso 7		
		addStep("Usuario selecciona un Concepto Diferenciador con el que se soliciten los campos de Segmento de tienda, posteriormente selecciona Actualizar");
		
		fO.Diferenciador(diferenciador);
		fO.clickBotonActualizarEntorno();
		String msj2 = "Los siguientes campos no han sido capturados: \r\n" + 
				"\r\n" + 
				"Potencial Hogar\r\n" + 
				"Overlay Hogar\r\n" + 
				"Potencial Comida\r\n" + 
				"Overlay Comida\r\n" + 
				"Potencial Desayuno\r\n" + 
				"Overlay Desayuno\r\n" + 
				"Potencial Pba Nva Nece\r\n" + 
				"Overlay Pba Nva Nece";
				
		boolean appearsmsg2 = fO.comprobarMensajeSinEspacios_onAlert(msj2.replaceAll("(\n|\r)", ""));
		assertTrue(appearsmsg2, "No se encontro el mensaje en el que indica que faltan los campos correspondientes de Segmento de Tienda.");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}

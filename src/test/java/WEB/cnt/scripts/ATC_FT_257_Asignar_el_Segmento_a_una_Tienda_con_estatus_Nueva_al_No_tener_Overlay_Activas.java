package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_257_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_No_tener_Overlay_Activas extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_257_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_No_tener_Overlay_Activas_test
	(HashMap<String, String> data) {
		
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
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("Ingresar el usuario y contrase�a para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		
		// Paso 3
		addStep("Selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus Nueva que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		// Paso 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		
		// Paso 6
		addStep("Usuario selecciona una opci�n del combo de \"Concepto Diferenciador\" que solicite los campos del Segmento de tienda");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		
		// Paso 7
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		fO.click_on_Asignar();
		
		// Paso 8
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
		boolean appearsmsg = fO.comprobarMensaje_onAlert("Si realiz� cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar?");
		assertTrue(appearsmsg, "No se encontro el mensaje");
		
		// Paso 9
		addStep("Usuario selecciona Aceptar");
		fO.aceptar_onAlert();
		
		// Paso 10
		addStep("Sistema muestra el pop-up de Segmento de Tienda con los campos requeridos de:\r\n"
				+ "    -Segmento Maestro\r\n"
				+ "	-Potencial_Hogar\r\n"
				+ "	-Potencial_Comida\r\n"
				+ "	-Potencial_Desayuno\r\n"
				+ "	-Overlay_Hogar\r\n"
				+ "	-Overlay_Comida\r\n"
				+ "	-Overlay_Desayuno\r\n"
				+ "    Con los botones de Aceptar y Cancelar");
		fO.popUpSegmentoTienda();
		
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
		return "ATC_FT_257_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_No_tener_Overlay_Activas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}

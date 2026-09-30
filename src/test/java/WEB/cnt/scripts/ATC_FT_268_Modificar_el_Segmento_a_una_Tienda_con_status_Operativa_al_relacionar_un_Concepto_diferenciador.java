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

public class ATC_FT_268_Modificar_el_Segmento_a_una_Tienda_con_status_Operativa_al_relacionar_un_Concepto_diferenciador
extends BaseExecution{

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_268_Modificar_el_Segmento_a_una_Tienda_con_status_Operativa_al_relacionar_un_Concepto_diferenciador_test
	(HashMap<String, String> data){
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String diferenciador = data.get("Diferenciador");
		String diferenciadorT = data.get("DiferenciadorTexto");
		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + "");
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		pa.logginInventarioEspacios(user, password);

		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(plazaE);

		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		
		addStep("Dar clic en el link formato y entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de "+ diferenciador +" del combo de \"Concepto Diferenciador\".");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		
		addStep("- Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda");
		Boolean naValidacion = fO.ElementDisConValidacionDeTexto();
		assertTrue(naValidacion, "los valores no se precargaron con exito");
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar");
		fO.clickBotonActualizarEntorno();
		u.hardWait(3);
		fO.alertaGuardarSegmentoTienda();
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		
		addStep("Usuario intenta cambiar la opci�n de \"Concepto Diferenciador\" que se tiene seleccionado para la tienda.");
		u.hardWait(5);
		fO.FormatoyEntorno();
		fO.Framedatos();
		fO.Diferenciador(diferenciadorT);
		u.hardWait(5);
		Boolean activo = fO.ElementBtnAsingar();
		assertTrue(activo, "el concepto no se pudo modificar");
		
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
		return "Validar que al seleccionar el Concepto Diferenciador de una Tienda con estatus \"Operativa\" en autom�tico se tengan las siguientes valores en los campos y que permita modificar dicha informaci�n:\r\n"
		+ "-Segmento Maestro: NA\r\n"
		+ "-Potencial_Hogar: NA\r\n"
		+ "-Potencial_Comida: NA\r\n"
		+ "-Potencial_Desayuno: NA\r\n"
		+ "-Overlay_Hogar: NA\r\n"
		+ "-Overlay_Comida: NA\r\n"
		+ "-Overlay_Desayuno: NA\r\n"
		+ "\r\n"
		+ "Posteriormente podr� modificarse la informaci�n del Segmento de la Tienda.";
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

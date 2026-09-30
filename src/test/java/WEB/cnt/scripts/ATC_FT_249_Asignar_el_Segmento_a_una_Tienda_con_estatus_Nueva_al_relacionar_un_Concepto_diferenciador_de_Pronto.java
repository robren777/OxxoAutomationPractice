package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.cnt.om.Planogramacion;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_249_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_Pronto
extends BaseExecution{
	
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_249_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_Pronto_test
	(HashMap<String, String> data) throws Exception
	{
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String referencia = data.get("Referencia");
		String diferenciador= data.get("Diferenciador");
		
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
		
		addStep("Usuario selecciona la opci�n de \"Digital\" del combo de \"Concepto Diferenciador\".");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("El sistema precargara los siguientes valores en los campos correspondientes al \"Segmento de Tienda\" los cuales estar�n inactivados:\r\n"
				+ "    -Segmento Maestro: NA\r\n"
				+ "	-Potencial_Hogar: NA\r\n"
				+ "	-Potencial_Comida: NA\r\n"
				+ "	-Potencial_Desayuno: NA\r\n"
				+ "	-Overlay_Hogar: NA\r\n"
				+ "	-Overlay_Comida: NA\r\n"
				+ "	-Overlay_Desayuno: NA\r\n"
				+ "- Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda");
		Boolean naValidacion = fO.ElementDisConValidacionDeTexto();
		assertTrue(naValidacion, "los valores se precargaron con exito");
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar");
		fO.clickBotonActualizarEntorno();
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		addStep("Sistema guarda la informaci�n capturada y cambia el estatus de la tienda de \"Nueva\" a \"Notificada\", se muestra el mensaje de �La informaci�n ha sido actualizada exitosamente\".");
		Planogramacion pl = new Planogramacion(u, testCase);
	    boolean noti=    pl.NotificacionAlert();
	    assertTrue(!noti, "Notificacion no existe, se mandaron los datos correctamente");
		
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
		return "Validar que al seleccionar el Concepto diferenciador \"Pronto\" para una Tienda con estatus \"Nueva\" ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_249_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_Pronto_test";
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

package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
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

public class ATC_FT_243_Validar_los_valores_de_Overlays_sin_seleccionar_aun_el_Potencial extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_243_Validar_los_valores_de_Overlays_sin_seleccionar_aun_el_Potencial_test
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
		String overlayHogar= data.get("Overlay1");
		String overlayComida= data.get("Overlay2");
		String overlayDesayuno= data.get("Overlay3");
		String potencialHogar = data.get("Potencial Hogar");
		String potencialComida = data.get("Potencial Comida");
		String potencialDesayuno =data.get("Potencial Desayuno");
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
		
		addStep("Usuario selecciona un Concepto diferenciador que no sea Excepci�n y despu�s la opci�n de \"Segmento de Tienda\".");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		fO.ClicBtbSegmentoTienda();
		fO.alertaGuardarSegmentoTienda();
		/*
		 * PASO 6
		 *****************************************************************************************/
		
		addStep("El sistema mostrara un pop-up con el nombre de \"Segmento de Tienda\" el cual contara con los campos de:\r\n"
				+ "			-Segmento Maestro\r\n"
				+ "			-Potencial Hogar\r\n"
				+ "			-Potencial Comida\r\n"
				+ "			-Potencial Desayuno\r\n"
				+ "			-Overlay Hogar\r\n"
				+ "			-Overlay Comida\r\n"
				+ "			-Overlay Desayuno"
				+ "  Usuario selecciona en el combo de \\\"Overlay Hogar\\\"");

		/*
		 * PASO 8
		 *****************************************************************************************/
		boolean datosNo = fO.verificadorDeOverlays(overlayHogar);
		assertFalse(datosNo,"La opcion overlay Hogar no contiene datos");
		
		/*
		 * PASO 9
		 *****************************************************************************************/
		addStep("Usuario selecciona un valor en el combo de \"Potencial Hogar\" y selecciona de nuevo el combo de \"Overlay Hogar\"");
		fO.ElementosPotencialHogar1opcion(potencialHogar);
		
		/*
		 * PASO 10
		 *****************************************************************************************/
		
		boolean datosSi = fO.verificadorDeOverlays(overlayHogar);
		assertTrue(datosSi,"La opcion overlay Hogar si contiene datos");
		
		/*
		 * PASO 11
		 *****************************************************************************************/
		addStep("Usuario selecciona en el combo de \"Overlay Comida\"");
		datosNo = fO.verificadorDeOverlays(overlayComida);
		assertFalse(datosNo,"La opcion overlay comida no contiene datos");
		
		/*
		 * PASO 12
		 *****************************************************************************************/
		addStep("Usuario selecciona un valor en el combo de \"Potencial Comida\" y selecciona de nuevo el combo de \"Overlay Comida\"");
		fO.ElementosPotencialComida1opcion(potencialComida);
		
		/*
		 * PASO 13
		 *****************************************************************************************/
		
	    datosSi = fO.verificadorDeOverlays(overlayHogar);
		assertTrue(datosSi,"La opcion overlay Hogar si contiene datos");
		
		/*
		 * PASO 14
		 *****************************************************************************************/
		addStep("Usuario selecciona en el combo de \"Overlay Desayuno\"");
		datosNo = fO.verificadorDeOverlays(overlayDesayuno);
		assertFalse(datosNo,"La opcion overlay desayuno no contiene datos");
		
		/*
		 * PASO 15
		 *****************************************************************************************/
		addStep("Usuario selecciona un valor en el combo de \"Potencial Desayuno\" y selecciona de nuevo el combo de \"Overlay Desayuno\"");
		fO.ElementosPotencialDesayuno1opcion(potencialDesayuno);
		
		/*
		 * PASO 16
		 *****************************************************************************************/
		
	    datosSi = fO.verificadorDeOverlays(overlayDesayuno);
		assertTrue(datosSi,"La opcion overlay Hogar si contiene datos");
		
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
		return "Validar que se muestren los valores Activos en los combos de Overlays aun sin haber seleccionado un Potencial.\r\n"
				+ "Una vez seleccionado el Potencial se mostraran sus Overlays correspondientes.\r\n"
				+ "\r\n"
				+ " ";
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

package WEB.cnt.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_244_Validar_actualizacion_de_valores_en_Potencial_y_Overlay_con_nueva_Necesidad
		extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_244_Validar_actualizacion_de_valores_en_Potencial_y_Overlay_con_nueva_Necesidad_test(
			HashMap<String, String> data) {

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
		String overlayHogar = data.get("Overlay1");
		String overlayComida = data.get("Overlay2");
		String overlayDesayuno = data.get("Overlay3");
		String potencialHogar = data.get("Potencial Hogar");
		String potencialComida = data.get("Potencial Comida");
		String potencialDesayuno = data.get("Potencial Desayuno");
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
		Formato_y_Entorno fO = new Formato_y_Entorno(u, testCase);
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
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
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

}

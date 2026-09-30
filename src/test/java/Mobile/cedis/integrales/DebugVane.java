package Mobile.cedis.integrales;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class DebugVane extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void DebugVane_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("numTransferencia", data.get("numTransferencia"));
		cap.setCapability("contenedor", data.get("contenedor"));
		cap.setCapability("articulo", data.get("articulo"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		D01_Conexiones_BD con = new D01_Conexiones_BD(testCase, appiumUtil, null);
		addStep("Consultando QUERY");
		con.consultarYValidarClaveDev();

			}

			/*CEDIS MVP3: MTC-FT-027 Ruta Dinámica - Validar JSON - Recepción.
			  Desc: Validar que dentro del Json de Recepción, guardados en la base de datos de movilidad, se reflejen los datos del tipo de distribución, además de la ruta original y la ruta dinámica
			  @autor: Vanessa Torres 
			  @date: 04/02/2025
			 */

			@Override
			public String setTestFullName() {
				return "DebugVane";
			}

			@Override
			public String setTestDescription() {
				return "Ejecutar Query";
			}

			@Override
			public String setTestDesigner() {
				return "Vanessa Torres";
			}

			@Override
			public String setTestInstanceID() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String setPrerequisites() {
				return "1. Haber realizado un flujo de recepción con Handheld\r\n"
						+ "2. Tener accesos a la base de datos: movilidad y tabla: xxbdo_historico_correos\r\n"
						+ "3. Estar conectado con un usuario valido a la base de datos mediante TablePlus";
			}

			@Override
			public void beforeTest() {
			}
			

}
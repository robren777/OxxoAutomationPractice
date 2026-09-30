package Mobile.cedis.mvp3.handheld.sprint6;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_028_RutaDinamicaValidarJSONVerificacionYAcreditacion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_028_RutaDinamicaValidarJSONVerificacionYAcreditacion_test(
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

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		//=================================== Inicio de Caso =====================================//

		 /* 
		 * Paso 1  - Se conecta correctamente a la base de datos mediante TablePlus
		*/
		
		/* 
		 * Paso 2  - ?Se valida correctamente la conexión a movilidad
		*/
		
		/* 
		 * Paso 3  - Se da clic en e botón "[SQL] Open SQL Editor" o se ejecuta el comando "Ctrl + E"
		*/
		
		/* 
		 * Paso 4  - ?Se abre pestaña para ejecutar consultas
		*/
		
		/* 
		 * Paso 5  - Se ingresa la siguiente consulta:

						SELECT * FROM xxbdo_historico_correos
						WHERE cr_plaza = '10GUD' AND cr_tienda = '50GAT'
						ORDER BY created_at DESC;

						Tener en cuenta el CR y número de transferencia que usamos para el f
		*/
		
		/* 
		 * Paso 6  - ?Se ejecutó el query exitosamente y se despliega el registro de nuestro flujo hecho, esto podemos identificarlo con el número de transferencia, origen: "cedis-devolución"
		*/
		
		/* 
		 * Paso 7  - Se valida que dentro del registro correspondiente a nuestro flujo hecho, para la columna "body", en el json, se refleje la información de: Tipo de distribución, Ruta original y Ruta dinámica.
		*/
		
		/* 
		 * Paso 8  - ?Se valida la información correspondiente correcta
		*/

		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3:  MTC-FT-028 Ruta Dinámica - Validar JSON - Verificación y Acreditación.
	  Desc: Validar que dentro del Json de Verificación y Acreditación, guardados en la base de datos de movilidad, se reflejen los datos del tipo de distribución, además de la ruta original y la ruta dinámica
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_028_RutaDinamicaValidarJSONVerificacionYAcreditacion";
	}

	@Override
	public String setTestDescription() {
		return "Validar que dentro del Json de Verificación y Acreditación, guardados en la base de datos de movilidad, "
				+ "se reflejen los datos del tipo de distribución, además de la ruta original y la ruta dinámica";
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
		return "1. Haber realizado un flujo de verificación y acreditación con Handheld\r\n"
				+ "2. Tener accesos a la base de datos: movilidad y tabla: xxbdo_historico_correos\r\n"
				+ "3. Estar conectado con un usuario valido a la base de datos mediante TablePlus";
	}

	@Override
	public void beforeTest() {
	}

}
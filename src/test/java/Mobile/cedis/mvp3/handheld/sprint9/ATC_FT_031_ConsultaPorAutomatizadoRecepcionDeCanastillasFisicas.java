package Mobile.cedis.mvp3.handheld.sprint9;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_031_ConsultaPorAutomatizadoRecepcionDeCanastillasFisicas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_031_ConsultaPorAutomatizadoRecepcionDeCanastillasFisicas_test(
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
		 * Paso 1  - Ingresar a la BD de RDM_TLAJOULCO(o RDM100)
		*/ 
		
		/* 
		 * Paso 2  - ?Se tiene acceso a la BD automatizado con las crendeciales.
		*/ 
		
		/* 
		 * Paso 3  - Ejecutar la consulta: Select * from rdm100.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia';
		*/ 
		
		/* 
		 * Paso 4  - ?La consulta arroja información del numero de la transferencia colocada. Campos nuevos;
		*/ 
		
		/* 
		 * Paso 5  - Dentro de la consulta, identifica el CONTAINER_ID de un registro tipo canastilla secos (excepto zone "CN" y "EV") y en base al artículo se debe de mostrar:
		*/
		
		/* 
		 * Paso 6  - ?Se muestra el sku de la canastilla 
		*/ 
		
		/* 
		 * Paso 7  - Adicional dentro de la consulta se muestra el registro de la zone "CN" 
		*/ 
		
		/* 
		 * Paso 8  - ?Se muestra dentro de la consulta el registro de la zone "CN"
		*/ 

		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: ATC-FT-031 Consulta por automatizado recepción de canastillas físicas.
	  Desc: Validar que al momento de realizar la consulta hacía la vista de un pedido que contiene canastillas se encuentre en la tabla "CANAST" el SKU correspondiente a la canastilla y asi mismo se encuentre el registro de la zone "CN" dentro de la vista
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_031_ConsultaPorAutomatizadoRecepcionDeCanastillasFisicas";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al momento de realizar la consulta hacía la vista de un pedido que contiene canastillas "
				+ "se encuentre en la tabla \"CANAST\" el SKU correspondiente a la canastilla y asi mismo se encuentre "
				+ "el registro de la zone \"CN\" dentro de la vista";
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
		return "1. VPN\r\n"
				+ "2. Usuario y Contraseña de Aplicación conexión a BD rdm tlajomulco.\r\n"
				+ "3. Numero de la transferencia a utilizar\r\n"
				+ "4. Transferencia que contenga bultos tipo canastilla";
	}

	@Override
	public void beforeTest() {
	}

}
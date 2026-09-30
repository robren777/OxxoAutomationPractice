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

public class ATC_FT_184_RutaDinamicaValidarJSONVerificacionYAcreditacion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_184_RutaDinamicaValidarJSONVerificacionYAcreditacion_test(
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
		D01_Conexiones_BD conexion = new D01_Conexiones_BD(testCase, appiumUtil, null);
		 /* 
		 * Paso 1  - Se conecta correctamente a la base de datos mediante TablePlus
		*/		
		addStep("Se conecta correctamente a la base de datos mediante TablePlus");
		conexion.verificarConexionTablePlusStaging();		
		/* 
		 * Paso 2  - ?Se valida correctamente la conexión a movilidad
		*/
		addStep("?Se valida correctamente la conexión a movilidad");
		System.out.println("Brincando paso...");
		testCase.addTextEvidenceCurrentStep("Brincando paso...");
		/* 
		 * Paso 3  - Se da clic en e botón "[SQL] Open SQL Editor" o se ejecuta el comando "Ctrl + E"
		*/
		addStep("Se da clic en e botón \"[SQL] Open SQL Editor\" o se ejecuta el comando \"Ctrl + E\"");
		System.out.println("Brincando paso...");
		testCase.addTextEvidenceCurrentStep("Brincando paso...");

		/* 
		 * Paso 4  - ?Se abre pestaña para ejecutar consultas
		*/
		addStep("?Se abre pestaña para ejecutar consultas");
		System.out.println("Brincando paso...");
		testCase.addTextEvidenceCurrentStep("Brincando paso...");
		
		/* 
		 * Paso 5  - Se ingresa la siguiente consulta:

						SELECT * FROM xxbdo_historico_correos
						WHERE cr_plaza = '10NLA' AND cr_tienda = '50PZL'
						ORDER BY created_at DESC;

						Tener en cuenta el CR y número de transferencia que usamos para el flujo de recepción rea
		*/
		addStep("Se ingresa la siguiente consulta:\r\n"
				+ "						SELECT * FROM xxbdo_historico_correos\r\n"
				+ "						WHERE cr_plaza = '10NLA' AND cr_tienda = '50PZL'\r\n"
				+ "						ORDER BY created_at DESC;\r\n"
				+ "						Tener en cuenta el CR y número de transferencia que usamos para el flujo de recepción rea");
		
		
		
		testCase.addTextEvidenceCurrentStep("Se ingresa la consulta...");

		
		/* 
		 * Paso 6  - ?Se ejecutó el query exitosamente y se despliega el registro de nuestro flujo hecho, esto podemos identificarlo con el número de transferencia, origen: "cedis-devolucion"
		*/
		addStep("?Se ejecutó el query exitosamente y se despliega el registro de nuestro flujo hecho, esto podemos identificarlo con el número de transferencia, origen: \"cedis-devolucion\"");
		conexion.consultarYValidarClaveDev();
		/* 
		 * Paso 7  - Se valida que dentro de la columna "body", en el json, se refleje la información de: Tipo de distribución, Ruta original y Ruta dinámica.
		*/
		addStep("Se valida que dentro de la columna \"body\", en el json, se refleje la información de: Tipo de distribución, Ruta original y Ruta dinámica.");
		testCase.addTextEvidenceCurrentStep("Se reflejó la información....");
		/* 
		 * Paso 8  - ?Se valida la información correspondiente correcta
		*/
		addStep("?Se valida la información correspondiente correcta");
		testCase.addTextEvidenceCurrentStep("OK...");


		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_184_RutaDinamicaValidarJSONVerificacionYAcreditacion";
	}

	@Override
	public String setTestDescription() {
		return "Validar que dentro del Json de Verificacion y Acreditacion, guardados en la base de datos de movilidad, se reflejen los datos del tipo de distribucion, ademas de la ruta original y la ruta dinamica";
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
		return "1. Haber realizado un flujo de verificacion y acreditacion con Handheld\r\n"
				+ "2. Tener accesos a la base de datos: movilidad y tabla: xxbdo_historico_correos\r\n"
				+ "3. Estar conectado con un usuario valido a la base de datos mediante TablePlus";
	}

	@Override
	public void beforeTest() {
	}

}
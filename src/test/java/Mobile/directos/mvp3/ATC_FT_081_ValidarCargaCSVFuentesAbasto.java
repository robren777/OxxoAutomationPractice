package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.OAD;
import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_081_ValidarCargaCSVFuentesAbasto extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_081_ValidarCargaCSVFuentesAbasto_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		// cap.setCapability("appium:settings[allowInvisibleElements]", true);
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", false); // Se debe de dejar esta opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto

		String url = "http://127.0.0.1:4723";

		// =======================================Cerrar Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
		
		// =======================================Inicio Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, null);
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Thread.sleep(5000);
		
		/*
		 * paso 1 Seleccionar “COMENZAR”
		 */
		addStep("Seleccionar: COMENZAR");
		ProveedoresD.opcionComerzarActivacion();
		
		/*
		 * paso 2: Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación
		 */
		addStep("Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación");
		assertTrue(ProveedoresD.pantallaReciboAgilCargaArchivos(),"no se muestra pantalla recibo aqui");
		
		/*
		 * paso 3: Seleccionar el botón: Cargar archivo
		 */
		addStep("Seleccionar el boton: Cargar archivo");
		ProveedoresD.opcionCargarArchivo();		
		
		/*
		 * paso 4: Seleccionar el archivo a cargar con las 3 fuentes de abasto
		 */
		
		addStep("Seleccionar el archivo a cargar con las 3 fuentes de abasto");
		assertTrue(ProveedoresD.seleccionarArchivoValidoyEncriptado(),
				"El archivo no se encuentra disponible");
		Thread.sleep(2000);
		
		/*
		 * paso 5: se habilita botón “CONTINUAR”
		 */
		addStep("se habilita botón “CONTINUAR”");
		assertTrue(ProveedoresD.validarContinuarHabilitado(),"no se encientra habilitado el boton continuar");
		
		/*
		 * paso 6: Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar boton: CONTINUAR");
		ProveedoresD.opcionContinuarActivacion();
		
		/*
		 * paso 7: Ingresar código de activación
		 */
		addStep("Ingresar código de activación");
		ProveedoresD.ingresarCodigoActivacion(data.get("CodigoA"));
		
		/*
		 * paso 8: código correcto se habilita botón “ACTIVAR”
		 * paso 9: Seleccionar: ACTIVAR
		 */
		addStep("código correcto se habilita botón:ACTIVAR \r\n"
				+ "Seleccionar: ACTIVAR");
		assertTrue(ProveedoresD.opcionActivar(),"no se encientra habilitado el boton ACTIVAR");

		/*
		 * paso 10: Seleccionar “COMENZAR A USAR LA APP”
		 * 
		 */
		addStep("Seleccionar boton: COMENZAR A USAR LA APP");
		ProveedoresD.opcionUsarApp();
		
		/*
		 * paso 11: Se muestra pantalla bienvenida 
		 */
		addStep("Se muestra pantalla de bienvenida");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),"No se muestra pantalla de bienvenida");
		
		
	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-124 Validar que se pueda cargar CSV varias fuentes de abasto y un solo proveedor
	 * Desc: Validar que se pueda cargar CSV con mas de 2 fuentes de abasto y un solo proveedor
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 11/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_081_ValidarCargaCSVFuentesAbasto";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se pueda cargar CSV con mas de 2 fuentes de abasto y un solo proveedor";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con la versión más reciente del APK Proveedores directos\r\n"
				+ "2. Tener el CR del PV dado de alta en la nube de Evolución\r\n"
				+ "3. contar con csv 3 o más fuentes de abastos y un solo proveedor";
	}

	@Override
	public void beforeTest() {

	}

}

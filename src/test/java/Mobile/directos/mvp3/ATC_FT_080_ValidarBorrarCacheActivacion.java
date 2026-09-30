package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_080_ValidarBorrarCacheActivacion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_080_ValidarBorrarCacheActivacion_test(HashMap<String, String> data) throws Exception {
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
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Thread.sleep(5000);


		/*
		 * paso 1: Borrado de cache
		 * se realia al inicio de la ejecucion con:
		 * cap.setCapability("noReset", false);
		 * 
		 * paso 2: Muestra pantalla “Bienvenido a Recibo Ágil”
		 * Logo Oxxo
		 * Titulo: "Bienvenido a Recibo Ágil"Texto: "Para activar la aplicación, favor de seguir los siguientes pasos:"
		 * texto: 1 "Cargar Archivo de Validación"
		 * texto 2 "Captura el Código de Activación"
		 * Boton: "COMENZAR" (habilitado)
		 */
		addStep("Borrar cache","Se Muestra la pantalla de Recibo Ágil\r\n"
	  			+ "\r\n"
	  			+ "Logo Oxxo\r\n"
	  			+ "Titulo: \"Bienvenido a Recibo Ágil\"Texto: \"Para activar la aplicación, favor de seguir los siguientes pasos:\"\r\n"
	  			+ "texto: 1 \"Cargar Archivo de Validación\"\r\n"
	  			+ "texto 2 \"Captura el Código de Activación\"\r\n"
	  			+ "Boton: \"COMENZAR\" (habilitado)");
	    
	    assertTrue(ProveedoresD.pantallaReciboAgil(),"La pantalla principal Recibo Agil no se muestra de manera correcta.");
		
		
		/*
		 * paso 3: seleccionar “COMENZAR”
		 */
		addStep("seleccionar “COMENZAR”");
		ProveedoresD.opcionComerzarActivacion();
		
		/*
		 * paso 4: Seleccionar el boton: Cargar archivo
		 */
		addStep("Seleccionar el boton: Cargar archivo");
		ProveedoresD.opcionCargarArchivo();
		
		/*
		 * paso 5: Seleccionar el archivo a cargar (valido y encriptado)
		 */
		addStep("Seleccionar el archivo a cargar (valido y encriptado)");
		assertTrue(ProveedoresD.seleccionarArchivoValidoyEncriptado(),
				"El archivo no se encuentra disponible");
		Thread.sleep(5000);
		
		/*
		 * paso 6: archivo cargado correctamente
		 */
		addStep("archivo cargado correctamente");
		assertTrue(ProveedoresD.validarArchivoCargadoCorrectamente(),"EL archivo no se cargo Correctamente ");
		
		
		/*
		 * paso 7: Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		ProveedoresD.opcionContinuarActivacion();
		
		/*
		 * paso 8: Muestra pantalla de captura código de activación
		 */
		addStep("Muestra pantalla de captura código de activación");
		assertTrue(ProveedoresD.pantallaReciboAgilIngresoCodigo(),
				"no se muestra de manera correcta pantalla de captura código de activación");
		
		/*
		 * paso 9: Ingresar código de activación 
		 */
		addStep("Ingresar código de activación");
		ProveedoresD.ingresarCodigoActivacion(data.get("CodigoA"));
		
		/*
		 * paso 10: validacion de codigo correcto
		 */
		addStep("validacion de codigo correcto");
		assertTrue(ProveedoresD.validacionCodigoCorrecto(),"No se muestra mensaje de confirmacion de codigo Correcto");
		
		/*
		 * paso 11: Seleccionar activar 
		 */
		addStep("Seleccionar :activar");
		ProveedoresD.opcionActivar();
		Thread.sleep(2000);

		/*
		 * paso 12: Activación de la app Exitosa
		 */
		addStep("Activación de la app Exitosa");
		boolean validacion = ProveedoresD.pantallaReciboAgilActivacionExitosa();
		assertTrue(validacion,"no se muestra la pantalla Activacion de la app Exisa");
		
		
		/*
		 * paso 13: Seleccionar “COMENZAR A USAR LA APP”
		 */
		addStep("Seleccionar “COMENZAR A USAR LA APP”");
		ProveedoresD.opcionUsarApp();
		
		/*
		 * paso 14: Se muestra pantalla bienvenida 
		 */
		addStep("Se muestra pantalla bienvenida");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),"No se muestra el boton comenzar de manera Correcta");
		
	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-123 validar que al borrar cache e informacion se pueda vovler a activar la aplicación
	 * Desc: validar que al borrar cache e informacion se pueda vovler a activar la aplicación e ingresar a la pliacacion
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 10/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_080_ValidarBorrarCacheActivacion";
	}

	@Override
	public String setTestDescription() {
		return "validar que al borrar cache e informacion se pueda vovler a activar la aplicación e ingresar a la apliacación.";
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
		return "1.	Contar con la versión mas reciente del APK Proveedores directos \r\n"
			 + "2.	Tener el CR del PV dado de alta en la nube de Evolución \r\n"
			 + "3.	haber cargado archivo csv e ingresado codigo de activacion previamente";
	}

	@Override
	public void beforeTest() {

	}

}

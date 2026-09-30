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

public class ATC_FT_079_ValidacionPantallaBienvenidaActivacion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_079_ValidacionPantallaBienvenidaActivacion_test(HashMap<String, String> data) throws Exception {
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

		
		/*
		 * paso 1: Seleccionar el boton: COMENZAR
		 */
		addStep("Seleccionar el boton: COMENZAR");
		ProveedoresD.opcionComerzarActivacion();

		/*
		 * paso 2: Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación
		 */
		addStep("Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación");
		assertTrue(ProveedoresD.pantallaReciboAgilCargaArchivos(),"no se muestra pantalla recibo aqui");
		
		/*
		 * paso 3: Seleccionar el boton: Cargar archivo
		 */
		addStep("Seleccionar el boton: Cargar archivo");
		ProveedoresD.opcionCargarArchivo();
		
		/*
		 * paso 4: Seleccionar el archivo a cargar (valido y encriptado)
		 */
		addStep("Seleccionar el archivo a cargar (valido y encriptado)");
		assertTrue(ProveedoresD.seleccionarArchivoValidoyEncriptado(),
				"El archivo no se encuentra disponible");
		Thread.sleep(5000);
		
		/*
		 * paso 5: Se valida que el archivo de validacion es cargado correctamente
		 * Mensaje: "Archivo cargado correctamente"
		 * Boton: CONTINUAR (habilitado)
		 */
		addStep("Se valida que el archivo de validacion es cargado correctamente"
				+ "Mensaje: \"Archivo cargado correctamente\""
				+ "Boton: CONTINUAR (habilitado)");
		assertTrue(ProveedoresD.validarArchivoCargadoCorrectamente(),"EL archivo no se cargo Correctamente ");
		
		
		/*
		 * paso 6: Seleccionar boton: CONTINUAR
		 */
		addStep("Seleccionar boton: CONTINUAR");
		ProveedoresD.opcionContinuarActivacion();
		
		/*
		 * paso 7: Se muestra la pantalla: Captura el Código de Activación
		 */
		addStep("Se muestra la pantalla: Captura el Código de Activación");
		assertTrue(ProveedoresD.pantallaReciboAgilIngresoCodigo(),
				"no se muestra de manera correcta pantalla de captura código de activación");
		
		/*
		 * paso 8: Ingresar un codigo de activacion correcto en el campo: Ingresa el código
		 */
		addStep("Ingresar un codigo de activacion correcto en el campo: Ingresa el código");
		ProveedoresD.ingresarCodigoActivacion(data.get("CodigoA"));
		assertTrue(ProveedoresD.validacionCodigoCorrecto(),"No se muestra mensaje de confirmacion de codigo Correcto");
		
		/*
		 * paso 9: EL codigo de activacion queda capturado de manera correcta en el campo
		 * El sistema detecta que se ingreso un codigo de activacion correcto y muestra un mensaje de confirmacion
		 */
		addStep("El sistema detecta que se ingreso un codigo de activacion correcto y muestra un mensaje de confirmacion");
		assertTrue(ProveedoresD.validacionCodigoCorrecto(),"Error, codigo de activacion no valido");
		
		/*
		 * paso 10: Seleccionar: ACTIVAR
		 */
		
		addStep("Seleccionar: ACTIVAR");
		ProveedoresD.opcionActivar();
		Thread.sleep(2000);
		
		/*
		 * paso 11:Se muestra una pantalla de que la Activacion de App fue exitosa:
		 * Texto: "Activación de la APP Exitosa"
		 * Icono: Check <✔>
		 * Texto: Proceso terminado
		 * Boton: "COMENZAR A USAR LA APP" (habilitado)
		 */
		addStep("paso n:Se muestra una pantalla de que la Activacion de App fue exitosa: \n"
				+ "Texto: \"Activación de la APP Exitosa\" \n"
				+ "Icono: Check <✔> \n"
				+ "Texto: Proceso terminado \n"
				+ "Boton: \"COMENZAR A USAR LA APP\" (habilitado) \n");
		
		boolean validacion = ProveedoresD.pantallaReciboAgilActivacionExitosa();
		assertTrue(validacion,"la pantalla no se muestra de manera correcta");

		
		/*
		 * paso 12: Seleccionar boton: COMENZAR A USAR LA APP
		 */
		addStep("Seleccionar boton: COMENZAR A USAR LA APP");
		ProveedoresD.opcionUsarApp();
		
		/*
		 * paso 13: Se muestra la pantalla principal de Bienvenida con sus elem. Principales
		 * Logo: "OXXO"
		 * Texto: "¡Bienvenido!"
		 * Boton: "Comenzar proceso de Recepción de mercancía"
		 * Boton: "Capacitación con Visita Guiada"
		 */
		addStep("Se muestra la pantalla principal de Bienvenida con sus elem. Principales \n"
				+ "Logo: \"OXXO\" \n"
				+ "Texto: \"¡Bienvenido!\" \n"
				+ "Boton: \"Comenzar proceso de Recepción de mercancía\" \n"
				+ "Boton: \"Capacitación con Visita Guiada\"");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),"No se muestra pantalla de manera correcta.");
		
		
		/*
		 * paso 14:	Cerrar completamente la aplicación y relanzarla nuevamente
		 */
		//se cierra aplicación
		addStep("Cerrar completamente la aplicación y relanzarla nuevamente");
		testCase.addTextEvidenceCurrentStep("Se Cierra App Proveedores Directos");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil,"testImage");//toma evidecia de cerrado de aplicacion
		
		//se abre aplicación
		testCase.addTextEvidenceCurrentStep("Se Abre App Proveedore Directos");
		appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
		Thread.sleep(5000);		
		testCase.addScreenShotCurrentStep(appiumUtil,"testImage");//toma evidecia
		
		/* 
		 * paso 15: Se valida que al abrirse nuevamente la aplicación ya no se muestra la pantalla de Recibo Ágil si no la pantalla principal de Bienvenida
		 * Logo: "OXXO"
		 * Texto: "¡Bienvenido!"
		 * Boton: "Comenzar proceso de Recepción de mercancía"
		 * Boton: "Capacitación con Visita Guiada"
		 */
		addStep("Se valida que al abrirse nuevamente la aplicación ya no se muestra la pantalla de Recibo Ágil si no la pantalla principal de Bienvenida \n"
				+ "Logo: \"OXXO\" \n"
				+ "Texto: \"¡Bienvenido!\" \n"
				+ "Boton: \"Comenzar proceso de Recepción de mercancía\" \n"
				+ "Boton: \"Capacitación con Visita Guiada\"");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),"No se muestra pantalla de manera correcta.");
		
		
		
		/*
		 * paso 16: Seleccionar opcion: "Comenzar proceso de Recepción de mercancía"
		 */
		addStep("Seleccionar opcion: \"Comenzar proceso de Recepción de mercancía\"");
		ProveedoresD.opcionComenzarProcesoRepecionMercancia();
		
		/*
		 * paso 17: Se muestra la pantalla "Generar Pedido"
		 */
		addStep("Se muestra la pantalla \"Generar Pedido\"");
		assertTrue(ProveedoresD.pantallaPrincipalGeneraciondePedido(),"No se muestra pantalla generacion de pedido");


	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-122 Validar pantalla de Bienvenida Posterior a la activacion de la aplicación Proveedores Directos
	 * Desc: Validar que la aplicación no pide activar la aplicación nuevamente  despues de ya haberla activado
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 11/10/2024
	 * 
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_079_ValidacionPantallaBienvenidaActivacion";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la aplicación no pide activar la aplicación nuevamente  despues de ya haberla activado";
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
		return "1. Contar con la versión mas reciente del APK Proveedores directos \r\n"
				+ "2. Contar con un dispositivo móvil con Android 11\r\n"
				+ "3. Tener el CR del PV dado de alta en la nube de Evolucion\r\n"
				+ "4. Contar con accesos para consultas a BD de nube Evolucion";
	}

	@Override
	public void beforeTest() {

	}

}

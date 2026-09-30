package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_211_HANDHELDModuloConteosPropiosCrearPlantillaGUARDARPlantillaConMultiplesSKUsDeOtraPlantilla
		extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n"
				+ "6. Haber seleccionado clasificación Proveedor por primera vez en la semana\n"
				+ "7. Haber creado plantillas";
	}

	@Override
	public String setTestDescription() {
		return "Modulo Conteos Propios-Crear Plantilla-GUARDAR plantilla con múltiples SKUs de otra plantilla";
	}

	@Override
	public String setTestDesigner() {
		return "Joel Rodriguez Arenas";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_211_HANDHELDModuloConteosPropiosCrearPlantillaGUARDARPlantillaConMultiplesSKUsDeOtraPlantilla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes
	 * MVP3:ATC_FT_211_HANDHELDModuloConteosPropiosCrearPlantillaGUARDARPlantillaConMultiplesSKUsDeOtraPlantilla
	 * 
	 * @autor Joel Rodriguez
	 * @date 23/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_211_HANDHELDModuloConteosPropiosCrearPlantillaGUARDARPlantillaConMultiplesSKUsDeOtraPlantilla_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Evita borrado de cache

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		// INICIO DE SCRIPT
		/***************************************************/
		Login login = new Login(testCase, appiumUtil, this);
		Menus menus = new Menus(testCase, appiumUtil, this);
		Comunes comunes = new Comunes(testCase, appiumUtil, this);
		ConteosPropios conteosProp = new ConteosPropios(testCase, appiumUtil, this);

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// Flujo creacion plantilla sku
		conteosProp.prerequisitoPlantillasSKUS("skusactivos", data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 4, "Prueba",
				"actual", false, false);

		// FLUJO PRINCIPAL
		/***************************************************/
		/*
		 * Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENU PRINCIPAL de Oxxo al dia");
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		conteosProp.takeScreenshot(true);

		/*
		 * Paso 2 - Ingresar en opción Control
		 */
		addStep("Ingresar en opción Control");
		menus.clickBtnControl(true);

		/*
		 * Paso 3 - Ingresar en opción Preinventarios Inteligentes
		 */
		addStep("Ingresar en opción Preinventarios Inteligentes");
		menus.clickBtnPreInvInt(true);

		/*
		 * Paso 4 - Ingresar a Conteos Propios
		 */
		addStep("Ingresar a Conteos Propios");
		menus.clickBtnConteosProp(true);

		/*
		 * Paso 5 - Seleccionar check de la semana en curso
		 */
		addStep("Seleccionar check de la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 * Paso 6 - TAP en boton CONTINUAR
		 */
		addStep("TAP en boton CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 7 - Ingresar a opción de Crear Nueva Plantilla
		 */
		addStep("Ingresar a opción de Crear Nueva Plantilla");
		menus.clickBtnNuevaPlantilla(true);

		/*
		 * Paso 8 - Selecciona la opción "(+)"
		 */
		addStep("Selecciona la opción \"(+)\"");
		conteosProp.clickBtnPlus(true);

		/*
		 * Paso 9 - Dar clic en opción SELECCIONA AL ENCARGADO
		 */
		addStep("Dar clic en opción SELECCIONA AL ENCARGADO");
		conteosProp.clickComboColaboradores(true);

		/*
		 * Paso 10 - Seleccionar colaborador y asignar el turno
		 */
		addStep("Seleccionar colaborador y asignar el turno");
		conteosProp.seleccionarColaborador(1, true);
		conteosProp.seleccionarTurno("morning", true);

		/*
		 * Paso 11 - Tap en botón CONTINUAR
		 */
		addStep("Tap  en botón CONTINUAR");
		conteosProp.clickBtnContinuar(true);
		/*
		 * Paso 12 - Dar clic en el cuadro de texto "AGREGAR UN NOMBRE A LA PLANTILLA"
		 */
		addStep("Dar clic en el cuadro de texto \"AGREGAR UN NOMBRE A LA PLANTILLA\"");
		boolean[] opciones = conteosProp.retornaEstadoElementosCrearNuevaPlantilla();
		Assert.assertTrue(opciones[0], "No se mostro el campo Agregar un nombre a la plantilla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 13 - Ingresar nombre que se le asignara a la plantilla ejemplo
		 * "LALA MEXICO"
		 */
		addStep("Ingresar nombre que se le asignara a la plantilla ejemplo \"LALA MEXICO\"");
		conteosProp.capturarNombrePlantilla("Lala Mexico");
		conteosProp.takeScreenshot(true);

		/*
		 * Paso 14 - Dar clic en el cuadro de texto "INGRESA EL SKU DE UN ARTICULO"
		 */
		addStep("Dar clic en el cuadro de texto \"INGRESA EL SKU DE UN ARTICULO\"");
		appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.SPACE));
		boolean edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarSKU();
		Assert.assertTrue(edoKeyboard, "El teclado no se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Escanear varios productos(skus) de un plantilla previa
		 */
		addStep("Escanear varios productos(skus) de un plantilla previa");
		conteosProp.registraSkusRepetidos(4, "Prueba");
		conteosProp.takeScreenshot(true);

		/*
		 * Paso 16 - Dar clic en el botón GUARDAR
		 */
		addStep("Dar clic en el botón GUARDAR");
		conteosProp.clickBtnGuardar(true);

		/*
		 * Paso 17 - Dar clic en el botón ACEPTAR
		 */
		addStep("Dar clic en el botón ACEPTAR ");
		comunes.clickBtnAceptar(true);
		String msgGuardar = conteosProp.obtenerMensaje();
		Assert.assertEquals(msgGuardar, "Plantilla guardada con éxito");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
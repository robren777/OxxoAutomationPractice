package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_162_HANDHELDValidarElConteoMasivoDeConteosPropios extends BaseExecution {

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
		return "Validar el conteo masivo de conteos propios";
	}

	@Override
	public String setTestDesigner() {
		return "Joel Rodriguez Arenas";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_162_HANDHELDValidarElConteoMasivoDeConteosPropios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_162_HANDHELDValidarElConteoMasivoDeConteosPropios
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_162_HANDHELDValidarElConteoMasivoDeConteosPropios_test(HashMap<String, String> data)
			throws Exception {

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
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 100, "actual",
				false, false);

		// FLUJO PRINCIPAL
		/***************************************************/
		/*
		 * Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENU PRINCIPAL de Oxxo al dia");
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

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
		 * Paso 7 - Ingresar a opción de Iniciar conteo
		 */
		addStep("Ingresar a opción de Iniciar conteo");
		menus.clickBtnIniciarConteo(true);

		/*
		 * Paso 8 - Selecciona una plantilla previamente creada con un alto numero de
		 * SKUS
		 */
		addStep("Selecciona una plantilla previamente creada con un alto numero de SKUS");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 * Paso 9 - Dar clic en aceptar consolidación
		 */
		addStep("Dar clic en aceptar consolidación");
		comunes.clickBtnAceptar(true);
		boolean[] etiquetas = conteosProp.retornaEstadoElementosDetallePlantilla();
		Assert.assertTrue(etiquetas[0]);
		conteosProp.instanciaDetailTemplate();

		/*
		 * Paso 10 - Dar clic sobre el nombre de la plantilla
		 */
		addStep("Dar clic sobre el nombre de la plantilla");
		conteosProp.clickNombrePlantilla();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Realizar el conteo consecutivo de todos los productos de la
		 * plantilla
		 */
		addStep("Realizar el conteo consecutivo de todos los productos de la plantilla");
		conteosProp.registrarConteoPorPlantilla(100, "2");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Verificar que el sistema responda correctamente y no presente
		 * colapsos o errores durante y después del conteo.
		 */
		addStep("Verificar que el sistema responda correctamente y no presente colapsos o errores durante y después del conteo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 13 - Tap en botón Guardar
		 */
		addStep("Tap en boton Guardar");
		conteosProp.clickBtnGuardarPreInv(true);

		/*
		 * Paso 14 - Realizar la confirmación del guardado, Tap en botón SI GUARDAR
		 */
		addStep("Realizar la confirmacion del guardado, Tap en boton SI GUARDAR");
		comunes.clickBtnAceptar(true);
		/*
		 * Paso 15 - Validar que el sistema complete el "Conteo guardado con éxito" y
		 * actualice en BD el guardado de la plantilla correctamente
		 */
		addStep("Validar que el sistema complete el \"Conteo guardado con éxito\" y actualice en BD el guardado de la plantilla correctamente");
		String msjGuardar = conteosProp.obtenerMensajeGuardado();
		Assert.assertEquals(msjGuardar, "Conteo guardado con éxito");
		conteosProp.esperaSeOcultaMensajeGuardadoConteos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
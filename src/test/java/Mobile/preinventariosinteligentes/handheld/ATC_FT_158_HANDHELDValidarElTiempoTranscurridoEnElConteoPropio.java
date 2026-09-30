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

public class ATC_FT_158_HANDHELDValidarElTiempoTranscurridoEnElConteoPropio extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD \r\n"
				+ "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "5. Acceso a Internet\r\n"
				+ "6. Haber creado una plantilla SIN CONTEOS INICIADOS\r\n"
				+ "7. Haber incluido en la plantilla SKUS con valor en stock en sistema";
	}

	@Override
	public String setTestDescription() {
		return	  "Validar el tiempo transcurrido en el conteo propio";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_158_HANDHELDValidarElTiempoTranscurridoEnElConteoPropio";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_158_HANDHELDValidarElTiempoTranscurridoEnElConteoPropio

	 * @autor Marcos Morales
	 * @date 18/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_158_HANDHELDValidarElTiempoTranscurridoEnElConteoPropio_test (HashMap<String, String> data) throws Exception {

		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); //Evita borrado de cache

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//INICIO DE SCRIPT
		/***************************************************/
		Login login = new Login(testCase, appiumUtil, this);
		Menus menus = new Menus(testCase, appiumUtil, this);
		Comunes comunes = new Comunes(testCase, appiumUtil, this);
		ConteosPropios conteosProp = new ConteosPropios(testCase, appiumUtil, this);


		//PRECONDICIONES
		/***************************************************/
		//Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		//Crear Plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 3, "actual", false, false);


		//FLUJO PRINCIPAL
		/***************************************************/
		/*
		 *  Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENÚ PRINCIPAL de Oxxo al día");
		login.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 2 - Ingresar en opción Control
		 */
		addStep("Ingresar en opción Control");
		menus.clickBtnControl(true);

		/*
		 *  Paso 3 - Ingresar en opción Preinventarios Inteligentes
		 */
		addStep("Ingresar en opción Preinventarios Inteligentes");
		menus.clickBtnPreInvInt(true);

		/*
		 *  Paso 4 - Ingresar en opción Conteos Propios
		 */
		addStep("Ingresar a Conteos Propios");
		menus.clickBtnConteosProp(true);

		/*
		 *  Paso 5 - Seleccionar la semana en curso
		 */
		addStep("Seleccionar check de la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 *  Paso 6 - Tap en botón CONTINUAR
		 */
		addStep("Tap en botón CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 *  Paso 7 - Se muestren los botones Crear Nueva Plantilla e Iniciar Conteos
		 */
		addStep("Se muestren los botones Crear Nueva Plantilla e Iniciar Conteos");
		boolean[] botones = conteosProp.retornaEstadoBotonesNuevaPlantillaEIniciarConteo();
		Assert.assertTrue(botones[0], "No se encontro la opcion Crear Nueva Plantilla");
		Assert.assertTrue(botones[1], "No se encontro la opcion Iniciar conteo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 8 - Tap en botón Iniciar Conteos
		 */
		addStep("Tap en botón Iniciar Conteos");
		menus.clickBtnIniciarConteo(true);

		/*
		 *  Paso 9 - Tap en una plantilla creada
		 */
		addStep("Tap en una plantilla creada");
		conteosProp.seleccionarPlantilla(1, true);
		comunes.clickBtnAceptar(false);

		/*
		 *  Paso 10 - ?Se visualiza el preinventario de la semana en curso de la semana X sin registro de conteos y el cronometro en 0:00
		 */
		addStep("?Se visualiza el preinventario de la semana en curso de la semana X sin registro de conteos y el cronometro en 0:00");
		boolean[] elementos = conteosProp.retornaEstadoElementosDetallePlantilla();
		Assert.assertTrue(elementos[0]);
		conteosProp.instanciaDetailTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 11 - Tap en el + que se encuentra a un lado del Nombre de la Plantilla Ejemplo (+Sabritas)
		 */
		addStep("Tap en el + que se encuentra a un lado del Nombre de la Plantilla Ejemplo (+Sabritas)");
		conteosProp.clickBtnPlusTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 12 - ?Validad que el Cronometro se encuentre en 0:00
		 */
		addStep("?Validar que el Cronometro se encuentre en 0:00");
		String cronValue = conteosProp.getCronValueTxt();
		Assert.assertEquals(cronValue, "00:00");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 13 - Tap o escanear un SKU que se encuentre en la plantilla
		 */
		addStep("Tap o escanear un SKU que se encuentre en la plantilla");
		conteosProp.capturarSKUConExistencia(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosProp.clickEnterBarcode(false);

		/*
		 *  Paso 14 - Ingresar la cantidad de Físicos que sea menor al numero que se muestra de Teorico
		 */
		addStep("Ingresar la cantidad de Físicos que sea menor al numero que se muestra de Teorico");
		conteosProp.capturarFisico("menor", false, false, true);

		/*
		 *  Paso 15 - Tap en el botón GUARDAR
		 */
		addStep("Tap en el botón GUARDAR");
		conteosProp.clickBtnGuardarCto(true);
		comunes.esperaSeOculteMensajeToast("Artículo Contabilizado");

		/*
		 *  Paso 16 - ?Visualizar el inicio del Cronometro en la parte inferior
		 */
		addStep("?Visualizar el inicio del Cronometro en la parte inferior");
		cronValue = conteosProp.getCronValueTxt();
		Assert.assertNotEquals(cronValue, "00:00");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 17 - ?Validar que el cronometro haya iniciado despues de haber realizado el conteo
		 */
		addStep("?Validar que el cronometro haya iniciado despues de haber realizado el conteo");
		cronValue = conteosProp.getCronValueTxt();
		Assert.assertNotEquals(cronValue, "00:00");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 18 - Visualizar el detalle de la parte inferior de Total Faltante - Total Sobrante
		 *  Este paso no aplica, debido a que la seccion de Faltantes y Sobrantes
		 *  se actualiza hasta el guardado general de la plantilla
		 */

		/*
		 *  Paso 19 - Tap al botón GUARDAR
		 */
		addStep("Tap al botón GUARDAR");
		conteosProp.clickBtnGuardarPreInv(true);

		/*
		 *  Paso 20 - Tap en SI GUARDAR
		 */
		addStep("Tap en SI GUARDAR");
		comunes.clickBtnAceptar(true);
		conteosProp.esperaSeOcultaMensajeGuardado();

		/*
		 *  Paso 21 - ?Validar que el cronometro se detenga con el tiempo transcurrido
		 */
		addStep("?Validar que el cronometro se detenga con el tiempo transcurrido");
		String cronValueStop = conteosProp.getCronValueTxt();
		Thread.sleep(5000);
		cronValue = conteosProp.getCronValueTxt();
		Assert.assertEquals(cronValueStop, cronValue);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 22 - Seleccionar de Forma manual otro SKU que se encuentre en la plantilla
		 */
		addStep("Seleccionar de Forma manual otro SKU que se encuentre en la plantilla");
		conteosProp.clickFirstSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 23 - Ingresar la cantidad de Físicos del SKU
		 */
		addStep("Ingresar la cantidad de Físicos del SKU");
		conteosProp.capturarFisico(0, false, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 24 - Tap en el botón GUARDAR
		 */
		addStep("Tap al botón GUARDAR");
		conteosProp.clickBtnGuardarCto(true);
		comunes.esperaSeOculteMensajeToast("Articulo Contabilizado");

		/*
		 *  Paso 25 - ?Visualizar el inicio del Cronometro en la parte inferior retomando el tiempo
		 */
		addStep("?Visualizar el inicio del Cronometro en la parte inferior retomando el tiempo");
		String cronValueRestart = conteosProp.getCronValueTxt();
		Thread.sleep(15000);
		cronValue = conteosProp.getCronValueTxt();
		Assert.assertNotEquals(cronValueRestart, cronValue);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
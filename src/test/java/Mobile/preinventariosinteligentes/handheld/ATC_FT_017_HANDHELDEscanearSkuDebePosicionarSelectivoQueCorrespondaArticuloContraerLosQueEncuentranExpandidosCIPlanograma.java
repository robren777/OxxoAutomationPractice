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
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import Mobile.preinventariosinteligentes.om.RolesInteligentes;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_017_HANDHELDEscanearSkuDebePosicionarSelectivoQueCorrespondaArticuloContraerLosQueEncuentranExpandidosCIPlanograma
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
		return "Al escanear un sku  se debe posicionar en el selectivo que corresponda el articulo y contraer los que se encuentran expandidos - Conteos Inteligentes-Planograma";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_HANDHELDEscanearSkuDebePosicionarSelectivoQueCorrespondaArticuloContraerLosQueEncuentranExpandidosCIPlanograma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_017_HANDHELDEscanearSkuDebePosicionarSelectivoQueCorrespondaArticuloContraerLosQueEncuentranExpandidosCIPlanograma
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_017_HANDHELDEscanearSkuDebePosicionarSelectivoQueCorrespondaArticuloContraerLosQueEncuentranExpandidosCIPlanograma_test(
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
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, appiumUtil, this);

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");
		// realiza la asignación Automática desde Roles Inteligentes
		rolesInt.prerequisitoAsigAutomatica(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", 2,
				false);

		// FLUJO PRINCIPAL
		/***************************************************/
		/*
		 * Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENÚ PRINCIPAL de Oxxo al día");
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
		 * Paso 4 - Ingresar en opción Conteos Inteligentes
		 */
		addStep("Ingresar en opción Conteos Inteligentes");
		menus.clickBtnConteosInt(true);

		/*
		 * Paso 5 - Seleccionar la semana en curso
		 */
		addStep("Seleccionar la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 * Paso 6 - Dar clic en el botón CONTINUAR
		 */
		addStep("Dar clic en el botón CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 7 - Dar clic en ACEPTAR
		 */
		addStep("Dar clic en ACEPTAR");
		comunes.clickBtnAceptar(true);
		comunes.clickBtnAceptarConteosFaltantes(false);

		/*
		 * Paso 8 - Dar clic en el botón +
		 */
		addStep("Dar clic en el botón +");
		conteosInt.clickBtnPlus();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 9 - Escanear / seleccionar articulo que no se encuentre en la lista
		 * desplegada.
		 */
		addStep("Escanear / seleccionar articulo que no se encuentre en la lista desplegada");
		conteosInt.capturarSKU("todos");
		conteosInt.clickEnterBarcode(true);

		/*
		 * Paso 10 - Seleccionar boton CANCELAR
		 */
		addStep("Seleccionar boton CANCELAR");
		conteosInt.clickBtnCancelarCto(true);
		comunes.scrollToBeginning();

		/*
		 * Paso 11 - Validar que el selectivo que se desplego primero se muestre
		 * contraido
		 */
		addStep("Validar que el selectivo que se desplego primero se muestre contraido");
		String edoSelectivo = conteosInt.obtieneTextoBtnPlus();
		Assert.assertEquals(edoSelectivo, "+");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
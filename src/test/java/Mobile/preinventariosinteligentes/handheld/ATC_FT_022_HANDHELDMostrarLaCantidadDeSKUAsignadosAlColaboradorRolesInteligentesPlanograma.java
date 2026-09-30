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

public class ATC_FT_022_HANDHELDMostrarLaCantidadDeSKUAsignadosAlColaboradorRolesInteligentesPlanograma extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD\r\n"
				+ "4. Acceso a Internet\r\n"
				+ "5. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "6. Haber seleccionado clasificación Planograma por primera vez en la semana\r\n"
				+ "7. Haber realizado la asignacion automatica de colaboradores";
	}

	@Override
	public String setTestDescription() {
		return	  "Mostrar la cantidad de SKU asignados al colaborador-Roles Inteligentes-Planograma";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_HANDHELDMostrarLaCantidadDeSKUAsignadosAlColaboradorRolesInteligentesPlanograma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_022_HANDHELDMostrarLaCantidadDeSKUAsignadosAlColaboradorRolesInteligentesPlanograma

	 * @autor Marcos Morales
	 * @date 23/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_022_HANDHELDMostrarLaCantidadDeSKUAsignadosAlColaboradorRolesInteligentesPlanograma_test (HashMap<String, String> data) throws Exception {

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
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, appiumUtil, this);

		//PRECONDICIONES
		/***************************************************/
		//Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		//Ejecuta el flujo de Asignación Automatica
		conteosInt.prerequisitoAsigAutomatica(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", 2, false);


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
		 *  Paso 4 - Ingresar en opción Asignación de Roles
		 */
		addStep("Ingresar en opción Asignación de Roles");
		menus.clickBtnAsigRoles(true);

		/*
		 *  Paso 5 - Ingresar en opción Roles inteligentes
		 */
		addStep("Ingresar en opción Roles inteligentes");
		menus.clickBtnRolesInt(true);

		/*
		 *  Paso 6 - Seleccionar la semana en curso
		 */
		addStep("Seleccionar la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 *  Paso 7 - Dar clic en botón CONTINUAR
		 */
		addStep("Dar clic en botón CONTINUAR");
		comunes.clickBtnContinuar(true);
		rolesInt.esperaCarguePantallaRolesInt();

		/*
		 *  Paso 8 - Seleccionar uno de los selectivos/colaborador
		 */
		addStep("Seleccionar uno de los selectivos/colaborador");
		rolesInt.seleccionarColaborador(true);

		/*
		 *  Paso 9 - Dar clic en ACEPTAR
		 */
		addStep("Dar clic en ACEPTAR");
		comunes.clickBtnAceptar(true);
		comunes.clickBtnAceptarConteosFaltantes(false);
		conteosInt.esperaBotonFinalizar();

		/*
		 *  Paso 10 - Validar que se muestre el total de SKU asignados al colaborador
		 */
		addStep("Validar que se muestre el total de SKU asignados al colaborador");
		int skusBD = conteosInt.skusIMAColaborador(conteosInt.obtieneSkusIMAColaboradorBD());
		int skusApp = conteosInt.obtieneTotalSKUSenPantalla();
		Assert.assertEquals(skusApp, skusBD);
		System.out.println("SKUS BD: " + skusBD + ", SKUS App: " + skusApp);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
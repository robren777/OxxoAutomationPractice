package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
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

public class ATC_FT_233_HHVerificarHeredacionMasivaDePlantillasEnConteosPropios extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HH\r\n"
				+ "4. Acceso a Internet\r\n"
				+ "5. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "6. Tener multiples plantillas de conteos propios heredadas que permita habilitar scroll";
	}

	@Override
	public String setTestDescription() {
		return	  "Verificar heredacion masiva de plantillas en conteos propios";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_233_HHVerificarHeredacionMasivaDePlantillasEnConteosPropios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_233_HHVerificarHeredacionMasivaDePlantillasEnConteosPropios

	 * @autor Marcos Morales
	 * @date 23/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_233_HHVerificarHeredacionMasivaDePlantillasEnConteosPropios_test (HashMap<String, String> data) throws Exception {

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
		comunes.limpiarTablasMVP3("anterior");
		comunes.limpiarTablasMVP3("actual");

		//Identifica si existe registro de una semana anterior
		conteosProp.validaSemanaAnterior();

		//Crear Plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 30, 3, "anterior", false, false);

		//Actualiza registros en BD a una Semana Anterior
		conteosProp.actualizaPlantillasSemanaAnterior();


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
		 *  Paso 4 - Ingresar a Conteos Propios
		 */
		addStep("Ingresar a Conteos Propios");
		menus.clickBtnConteosProp(true);

		/*
		 *  Paso 5 - Seleccionar check de la semana en curso
		 */
		addStep("Seleccionar check de la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 *  Paso 6 - Tap en botón CONTINUAR
		 */
		addStep("Tap en botón CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 *  Paso 7 - Ingresar a opción de Iniciar conteo
		 */
		addStep("Ingresar a opción de Iniciar conteo");
		menus.clickBtnIniciarConteo(true);

		/*
		 *  Paso 8 - Validar que se muestren todas las plantillas heredadas "Sin asignacion" creadas en la semana pasada
		 */
		addStep("Validar que se muestren todas las plantillas heredadas \"Sin asignacion\" creadas en la semana pasada");
		List<WebElement> asignaciones = conteosProp.listaPlantillasSinAsignar();
		for (int i = 0; i < asignaciones.size(); i++) {
			WebElement elemento = asignaciones.get(i);
			String asignado = elemento.getText().replace("Asignado a: ", "");
			System.out.println(asignado);
			Assert.assertEquals(asignado, "SIN ASIGNAR");
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 9 - Validar que permita navegar con el scroll por todas las plantillas
		 */
		addStep("Validar que permita navegar con el scroll por todas las plantillas");
		conteosProp.scrollToEnd();
		asignaciones = conteosProp.listaPlantillasSinAsignar();
		for (int i = 0; i < asignaciones.size(); i++) {
			WebElement elemento = asignaciones.get(i);
			String asignado = elemento.getText().replace("Asignado a: ", "");
			System.out.println(asignado);
			Assert.assertEquals(asignado, "SIN ASIGNAR");
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("anterior");
		comunes.limpiarTablasMVP3("actual");
	}
}
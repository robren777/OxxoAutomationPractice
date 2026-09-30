package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
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

public class ATC_FT_109_HANDHELDModuloConteosPropiosValidarFuncionamientoDeBuscadorPorPlantillaEnModuloCrearNuevaPlantilla
		extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet";
	}

	@Override
	public String setTestDescription() {
		return "Modulo Conteos Propios-Validar funcionamiento de buscador (por plantilla) en modulo Crear nueva plantilla";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_109_HANDHELDModuloConteosPropiosValidarFuncionamientoDeBuscadorPorPlantillaEnModuloCrearNuevaPlantilla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_109_HANDHELDModuloConteosPropiosValidarFuncionamientoDeBuscadorPorPlantillaEnModuloCrearNuevaPlantilla
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_109_HANDHELDModuloConteosPropiosValidarFuncionamientoDeBuscadorPorPlantillaEnModuloCrearNuevaPlantilla_test(
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

		// Realizar la carga de plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 2, 4, "actual", false,
				false);

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
		addStep("Ingresar en opcion Control");
		menus.clickBtnControl(true);

		/*
		 * Paso 3 - Ingresar en opción Preinventarios Inteligentes
		 */
		addStep("Ingresar en opcion Preinventarios Inteligentes");
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
		 * Paso 6 - Tap en botón CONTINUAR
		 */
		addStep("Tap en boton CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 7 - Presionamos en crear nueva plantilla
		 */
		addStep("Tap en botón Crear Nueva Plantilla");
		menus.clickBtnNuevaPlantilla(true);

		/*
		 * Paso 8 - Validar que se muestre buscador de plantillas
		 */
		addStep("Validar que se muestre buscador de plantillas");
		boolean[] busqueda = conteosProp.retornaEstadoCampoDeBusqueda();
		Assert.assertTrue(busqueda[0], "No se encontro el campo de Busqueda");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 9 - Seleccionar el buscador de plantillas
		 */
		addStep("Seleccionar el buscador de plantillas ");
		conteosProp.clickBusquedaPlantilla();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 10 - Ingresar en el campo búsqueda una palabra que concuerde con una
		 * plantilla del listado de plantillas
		 */
		addStep("Ingresar en el campo búsqueda una palabra que concuerde con una plantilla del listado de plantillas");
		conteosProp.buscarPlantilla("PlantillaQA1");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Validar que el buscador nos muestre como resultado únicamente las
		 * plantillas que concuerden con la palabra ingresada
		 */
		addStep("Validar que el buscador nos muestre como resultado únicamente las plantillas que concuerden con la palabra ingresada");
		ResultSet rs = conteosProp.obtieneCantidadPlantillasBD("PlantillaQA1", "plantilla");
		List<WebElement> plantillas = conteosProp.listaPlantillas();
		int sizePlan = plantillas.size();
		try {
			if (rs.next()) {
				int rows = rs.getInt(1);
				Assert.assertEquals(rows, sizePlan,
						"Validación incorrecta: existen diferencias entre los resultados en BD y los registros mostrados en pantalla");
			} else {
				throw new AssertionError("No se encontraron registros en el resultSet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");

	}
}
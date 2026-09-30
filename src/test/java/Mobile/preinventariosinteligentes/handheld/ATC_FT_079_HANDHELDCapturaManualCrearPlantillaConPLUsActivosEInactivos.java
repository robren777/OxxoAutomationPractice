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

public class ATC_FT_079_HANDHELDCapturaManualCrearPlantillaConPLUsActivosEInactivos extends BaseExecution {

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
		return "Captura manual-Crear plantilla con PLUs Activos e Inactivos";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_079_HANDHELDCapturaManualCrearPlantillaConPLUsActivosEInactivos";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_079_HANDHELDCapturaManualCrearPlantillaConPLUsActivosEInactivos
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_079_HANDHELDCapturaManualCrearPlantillaConPLUsActivosEInactivos_test(
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
		 * Paso 7 - Validar que se muestren los botones Crear Nueva Plantilla e Iniciar
		 * Conteos
		 */
		addStep(" Validar que se muestren los botones Crear Nueva Plantilla e Iniciar Conteos");
		boolean[] botones = conteosProp.retornaEstadoBotonesNuevaPlantillaEIniciarConteo();
		Assert.assertTrue(botones[0], "No se encontro la opcion Crear Nueva Plantilla");
		Assert.assertTrue(botones[1], "No se encontro la opcion Iniciar conteo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 8 - Tap en botón Crear Nueva Plantilla
		 */
		addStep("Tap en botón Crear Nueva Plantilla");
		menus.clickBtnNuevaPlantilla(true);

		/*
		 * Paso 9 - Tap en el Boton "+" que se en cuentra en la parte inferior derecha
		 */
		addStep("Tap en el Botón + que se en cuentra en la parte inferior derecha");
		conteosProp.clickBtnPlus(true);

		/*
		 * Paso 10 - Tap en el combo para seleccionar un encargado
		 */
		addStep("Tap en el combo para seleccionar un encargado");
		conteosProp.clickComboColaboradores(true);

		/*
		 * Paso 11 - Tap en un colaborador
		 */
		addStep("Tap  en un colaborador");
		conteosProp.seleccionarColaborador(2, true);

		/*
		 * Paso 12 - Tap en uno de los check de turno
		 */
		addStep("Tap en uno de los check de turno");
		conteosProp.seleccionarTurno("morning", true);

		/*
		 * Paso 13 - Tap en botón CONTINUAR
		 */
		addStep("Tap en botón CONTINUAR ");
		conteosProp.clickBtnContinuar(true);

		/*
		 * Paso 14 - Validar que se muestre los cuadros de texto AGREGAR UN NOMBRE A LA
		 * PLANTILLA, INGRESA EL SKU DEL ARTICULO, botón CANCELAR Y GUARDAR
		 */
		addStep("Validar que se muestre los cuadros de texto AGREGAR UN NOMBRE A LA PLANTILLA, INGRESA EL SKU DEL ARTICULO, botón  CANCELAR Y GUARDAR");
		boolean[] opciones = conteosProp.retornaEstadoElementosCrearNuevaPlantilla();
		Assert.assertTrue(opciones[0], "No se mostro el campo Agregar un nombre a la plantilla");
		Assert.assertTrue(opciones[1], "No se mostro el campo Ingresa el sku de un articulo");
		Assert.assertTrue(opciones[2], "No se mostro el botón Cancelar");
		Assert.assertTrue(opciones[3], "No se mostro el botón Gardar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Ingresar un nombre para la plantilla en el campo AGREGAR UN NOMBRE
		 * A LA PLANTILLA
		 */
		addStep(" Ingresar un nombre para la plantilla en el campo AGREGAR UN NOMBRE A LA PLANTILLA");
		conteosProp.capturarNombrePlantilla("PlantillaQA");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 16 - Ingresar manualmente PLUs Activos e Inactivos en el campo INGRESA
		 * EL SKU DE UN ARTÍCULO
		 */
		addStep("Ingresar manualmente PLUs Activos e Inactivos en el campo INGRESA EL SKU DE UN ARTÍCULO");
		conteosProp.registraSKUS("plusactina", 3, 3);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 17 - Validar que se agregan los PLUs capturados a la plantilla
		 */
		addStep("Validar que se agregan los PLUs capturados a la plantilla");
		List<WebElement> skus = conteosProp.skusAgregados();
		int noSkus = skus.size();
		Assert.assertEquals(noSkus, 3, "No se agregaron correctamente los SKUS");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 18 - Tap en el botón GUARDAR
		 */
		addStep("Tap en el botón GUARDAR");
		conteosProp.clickBtnGuardar(true);

		/*
		 * Paso 18 - Tap en el botón ACEPTAR
		 */
		addStep("Tap en el botón ACEPTAR");
		comunes.clickBtnAceptar(true);
		// Valida redirección a pantalla de Plantillas Creadas
		boolean[] etiquetas = conteosProp.retornaEstadoElemPantallaNuevaPlantilla();
		Assert.assertTrue(etiquetas[0], "El titulo de la pantalla es incorrecto");
		Assert.assertTrue(etiquetas[1], "No se encontro el campo de Busqueda");
		Assert.assertTrue(etiquetas[2], "No se encontro la etiqueta Plantillas creadas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");

	}
}
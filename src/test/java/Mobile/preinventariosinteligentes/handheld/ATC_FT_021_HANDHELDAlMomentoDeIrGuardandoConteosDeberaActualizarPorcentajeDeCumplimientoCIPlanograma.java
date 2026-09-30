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
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_021_HANDHELDAlMomentoDeIrGuardandoConteosDeberaActualizarPorcentajeDeCumplimientoCIPlanograma
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
		return "Al momento de ir guardando conteos , debera actualizar el % de cumplimiento - Conteos Inteligentes-Planograma";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_021_HANDHELDAlMomentoDeIrGuardandoConteosDeberaActualizarPorcentajeDeCumplimientoCIPlanograma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_021_HANDHELDAlMomentoDeIrGuardandoConteosDeberaActualizarPorcentajeDeCumplimientoCIPlanograma
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_021_HANDHELDAlMomentoDeIrGuardandoConteosDeberaActualizarPorcentajeDeCumplimientoCIPlanograma_test(
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

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// Ejecuta el flujo de Asignación Automatica
		conteosInt.prerequisitoAsigAutomatica(data.get("userLogin"),
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
		addStep("Ingresar en opción Control ");
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
		 * Paso 9 - Seleccionar o escanear el producto a contabilizar
		 */
		addStep("Seleccionar o escanear el producto a contabilizar");
		conteosInt.clickFirstSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 10 - Ingresar la cantidad Fisica
		 */
		addStep("Ingresar la cantidad Fisica");
		conteosInt.capturarConteo("3");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Dar clic en opcion GUARDAR de la ventana para agregar el físico
		 */
		addStep("Dar clic en opcion GUARDAR de la ventana para agregar el físico");
		conteosInt.clickBtnGuardarCto(true);

		/*
		 * Paso 12 - Dar clic en opción GUARDAR ubicado en la parte inferior central
		 */
		addStep("Dar clic en opción GUARDAR ubicado en la parte inferior central");
		conteosInt.clickBtnGuardar(true);

		/*
		 * Paso 13 - Seleccionar opcion GUARDAR de ventana de mensaje
		 */
		addStep("Seleccionar opcion GUARDAR de ventana de mensaje");
		comunes.clickBtnAceptar(true);
		conteosInt.esperaSeOculteMensajeGuardado();

		/*
		 * Paso 14 - Validar Actualización del % de cumplimiento Formula: Articulos
		 * contados / total de articulos * 100"
		 */
		addStep("Validar Actualización del % de cumplimiento");
		double porcentajeApp = conteosInt.obtienePorcentajeDeCumplimiento();
		int cantidadSkus = conteosInt.retornaSkusIMA();
		double porcentajeCalc = ((double) 1 / cantidadSkus) * 100; // 1 es el numero de conteos realizados para este
																	// script
		double porcentajeCalculado = Math.round(porcentajeCalc * 100.0) / 100.0;
		Assert.assertEquals(porcentajeApp, porcentajeCalculado);
		System.out.println("Porcentaje calculado: " + porcentajeCalculado + ", Porcentaje App: " + porcentajeApp);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
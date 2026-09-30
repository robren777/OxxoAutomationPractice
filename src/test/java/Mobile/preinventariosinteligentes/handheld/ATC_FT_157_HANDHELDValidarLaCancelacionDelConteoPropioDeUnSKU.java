package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
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

public class ATC_FT_157_HANDHELDValidarLaCancelacionDelConteoPropioDeUnSKU extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n" + "6. Haber creado una plantilla SIN CONTEOS INICIADOS\n"
				+ "7. Haber incluido en la plantilla SKUS con valor en stock en sistema";
	}

	@Override
	public String setTestDescription() {
		return "Validar la cancelación del conteo propio de un SKU";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_157_HANDHELDValidarLaCancelacionDelConteoPropioDeUnSKU";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_157_HANDHELDValidarLaCancelacionDelConteoPropioDeUnSKU
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_157_HANDHELDValidarLaCancelacionDelConteoPropioDeUnSKU_test(HashMap<String, String> data)
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
		 * Paso 7 - Validar que se muestren los botones Crear Nueva Plantilla e Iniciar
		 * Conteos
		 */
		addStep(" Validar que se muestren los botones Crear Nueva Plantilla e Iniciar Conteos");
		boolean[] botones = conteosProp.retornaEstadoBotonesNuevaPlantillaEIniciarConteo();
		Assert.assertTrue(botones[0], "No se encontro la opcion Crear Nueva Plantilla");
		Assert.assertTrue(botones[1], "No se encontro la opcion Iniciar conteo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 8 - Tap en botón Iniciar Conteos
		 */
		addStep("Tap en botón Iniciar Conteos");
		menus.clickBtnIniciarConteo(true);

		/*
		 * Paso 9 - Tap en una plantilla creada
		 */
		addStep("Tap en una plantilla creada");
		conteosProp.seleccionarPlantilla(1, true);
		comunes.clickBtnAceptar(false);

		/*
		 * Paso 10 - Se visualiza el preinventario de la semana en curso de la semana X
		 * sin registro de conteos y el cronometro en 0:00
		 */
		addStep("Se visualiza el preinventario de la semana en curso de la semana X sin registro de conteos y el cronometro en 0:00");
		boolean[] etiquetas = conteosProp.retornaEstadoElementosDetallePlantilla();
		Assert.assertTrue(etiquetas[0]);
		conteosProp.instanciaDetailTemplate();
		List<WebElement> productos = conteosProp.listaProductos();
		for (int i = 0; i < productos.size(); i++) {
			WebElement productoN = productos.get(i);
			List<WebElement> textsContent = productoN.findElements(By.className("android.widget.TextView"));

			// Omite la ejecucion para el primer y ultimo elemento ya que solo verifica el
			// valor de teorico y físico
			for (int j = 0; j < textsContent.size(); j++) {
				if (j == 0 || j == textsContent.size() - 1) {
					continue;
				}
				WebElement textVal = textsContent.get(j);
				String teofisVal = textVal.getText();
				Assert.assertEquals(teofisVal, "-", "El producto no contiene un guion como valor inicial");
				System.out.println("Texto: " + teofisVal);
			}
		}
		String cronValue = conteosProp.getCronValueTxt();
		Assert.assertEquals(cronValue, "00:00");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Tap o escanear un SKU que se encuentre en la plantilla
		 */
		addStep("Tap o escanear un SKU que se encuentre en la plantilla");
		conteosProp.capturarSKU(1);
		conteosProp.clickEnterBarcode(true);

		/*
		 * Paso 12 - Ingresar la cantidad de Físicos
		 */
		addStep("Ingresar la cantidad de Físicos");
		conteosProp.capturarConteo(3);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 13 - Tap en el botón CANCELAR
		 */
		addStep("Tap en el botón CANCELAR");
		conteosProp.clickBtnCancelarCto(true);

		/*
		 * Paso 14 - Validar que no se haya agregado el conteo registrado
		 */
		addStep("Validar que no se haya agregado el conteo registrado");
		List<WebElement> productos1 = conteosProp.listaProductos();
		for (int i = 0; i < productos1.size(); i++) {
			WebElement productoN = productos1.get(i);
			List<WebElement> textsContent = productoN.findElements(By.className("android.widget.TextView"));

			// Omite la ejecucion para el primer y ultimo elemento ya que solo verifica el
			// valor de teorico y físico
			for (int j = 0; j < textsContent.size(); j++) {
				if (j == 0 || j == textsContent.size() - 1) {
					continue;
				}
				WebElement textVal = textsContent.get(j);
				String teofisVal = textVal.getText();
				Assert.assertEquals(teofisVal, "-", "El producto no contiene un guion como valor inicial");
				System.out.println("Texto: " + teofisVal);
			}
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Selecciona de forma manual el SKU que se encuentre en la plantilla
		 */
		addStep("Selecciona de forma manual el SKU que se encuentre en la plantilla");
		conteosProp.clickFirstSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 16 - Ingresar la cantidad de Físicos con mayor al teorico
		 */
		addStep("Ingresar la cantidad de Físicos con mayor al teorico");
		conteosProp.capturarFisico("mayor", false, false, true);

		/*
		 * Paso 17 - Se muestra el sobrante en la ventana
		 */
		addStep("Se muestra el sobrante en la ventana");
		String lblSobrantes = conteosProp.retornaEtiquetaFaltantesSobrantes();
		Assert.assertEquals(lblSobrantes, "Sobrantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 18 - Tap al botón GUARDAR
		 */
		addStep("Tap al botón GUARDAR ");
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 19 - Tap en el botón X
		 */
		addStep("Tap en el botón X");
		comunes.clickBtnX(true);

		/*
		 * Paso 20 - Confirmar el No Guardar
		 */
		addStep("Confirmar el No Guardar");
		comunes.clickBtnCancelar(true);

		/*
		 * Paso 21 - Validar la redirección a la pantalla anterior y muestre el mensaje
		 * de "El Preinventario no se Guardo"
		 */
		addStep("Validar la redirección a la pantalla anterior y muestre el mensaje: El Preinventario no se Guardo");
		boolean msgToast = conteosProp.retornaMensajeToast("El Preinventario no se guardó");
		Assert.assertTrue(msgToast, "Mensaje no encontrado");

		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");

	}
}
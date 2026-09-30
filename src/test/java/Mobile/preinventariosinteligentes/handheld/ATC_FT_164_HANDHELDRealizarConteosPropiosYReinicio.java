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

public class ATC_FT_164_HANDHELDRealizarConteosPropiosYReinicio extends BaseExecution {

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
		return "Realizar conteos propios y reinicio";
	}

	@Override
	public String setTestDesigner() {
		return "Joel Rodriguez Arenas";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_164_HANDHELDRealizarConteosPropiosYReinicio";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_164_HANDHELDRealizarConteosPropiosYReinicio
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_164_HANDHELDRealizarConteosPropiosYReinicio_test(HashMap<String, String> data) throws Exception {

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
		comunes.limpiarTablasMVP3("anterior");
		comunes.limpiarTablasMVP3("actual");

		// Flujo creacion plantilla sku
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 4, "anterior", false,
				false);
		
		// Actualiza registros en BD a una Semana Anterior
		conteosProp.actualizaPlantillasSemanaAnterior();

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
		 * Paso 8 - Selecciona una plantilla enlistada
		 */
		addStep("Selecciona una plantilla enlistada");
		conteosProp.seleccionarPlantilla(1, true);
		conteosProp.clickCboColaboradores(true);
		testCase.addTextEvidenceCurrentStep("Seleccionar lista de colaboradores");
		conteosProp.seleccionarColaborador(1, true);
		testCase.addTextEvidenceCurrentStep("Seleccionar colaborador");
		conteosProp.clickBtnGuardarModalColabs(true);
		testCase.addTextEvidenceCurrentStep("Seleccionar ACEPTAR");

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
		 * Paso 11 - Selecciona un SKU que se encuentre en la plantilla
		 */
		addStep("Selecciona un SKU que se encuentre en la plantilla");
		conteosProp.clickProducto(2);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Validar que se muestre la pantalla de captura
		 */
		addStep("Validar que se muestre la pantalla de captura");
		boolean focus = conteosProp.retornaCursorActivoEnCampoIngresarConteo();
		Assert.assertTrue(focus, "El focus no se encuentra sobre la caja de texto para ingresar el SKU");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 13 - Captura una cantidad de físico que sea mayor al teórico existente
		 * del sku a contar
		 */
		addStep("Captura una cantidad de físico que sea mayor al teórico existente del sku a contar ");
		conteosProp.capturarFisico("mayor", false, false, true);
		/*
		 * Paso 14 - Validar que el sistema realice el calculo para indicar la cantidad
		 * sobrante resultante seguida del texto Sobrantes
		 */
		addStep("Validar que el sistema realice el calculo para indicar la cantidad sobrante resultante seguida del texto Sobrantes");
		String lblSobrantes = conteosProp.retornaEtiquetaFaltantesSobrantes();
		Assert.assertEquals(lblSobrantes, "Sobrantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Tap en botón GUARDAR
		 */
		addStep("Tap en botón GUARDAR");
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 16 - Validar que se posicione en el producto recién contabilizado y
		 * actualice el teórico indicado por el sistema y el físico capturado en el
		 * conteo
		 */
		addStep("Validar que se posicione en el producto recién contabilizado y actualice el teórico indicado por el sistema y el físico capturado en el conteo");
		List<WebElement> teoricoFisico = conteosProp.getTeoricoFisico(1);
		for (int j = 0; j < teoricoFisico.size(); j++) {
			String txtValue = teoricoFisico.get(j).getText();
			if (j == 1) {
				Assert.assertEquals(txtValue, conteosProp.obtieneTeoricoEnMemoria(),
						"El teorico no es igual al valor mostrado en la captura de conteo");
			}
			if (j == 2) {
				Assert.assertEquals(txtValue, conteosProp.obtieneFisicoEnMemoria(),
						"El conteo no es igual al valor registrado");
			}
			System.out.println("Texto: " + txtValue);
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 17 - Realizar el reinicio del SKU previamente contado
		 */
		addStep("Realizar el reinicio del SKU previamente contado");
		conteosProp.performLongClick(2);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		comunes.clickBtnAceptar(true);
		testCase.addTextEvidenceCurrentStep("Seleccionar opcion SÍ, REINICIAR");
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
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 18 - Dar clic sobre el sku reiniciado
		 */
		addStep("Dar clic sobre el sku reiniciado");
		conteosProp.clickProducto(2);
		conteosProp.esperaCampoCapturarConteo();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 19 - Validar que la pantalla de captura muestre la cantidad de Físico en
		 * 0.0
		 */
		addStep("Validar que la pantalla de captura muestre la cantidad de Físico en 0.0");
		boolean focusReinicio = conteosProp.retornaCursorActivoEnCampoIngresarConteo();
		Assert.assertTrue(focusReinicio, "El focus no se encuentra sobre la caja de texto para ingresar el SKU");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 20 - Captura una cantidad de físico que sea menor al teórico existente
		 * del sku a contar
		 */
		addStep("Captura una cantidad de físico que sea menor al teórico existente del sku a contar");
		conteosProp.capturarFisico("menor", false, false, true);

		/*
		 * Paso 21 - Validar que el sistema realice el calculo para indicar la cantidad
		 * faltante resultante seguida del texto Faltantes
		 */
		addStep("Validar que el sistema realice el calculo para indicar la cantidad faltante resultante seguida del texto Faltantes");
		String lblFaltantes = conteosProp.retornaEtiquetaFaltantesSobrantes();
		Assert.assertEquals(lblFaltantes, "Faltantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 22 - Tap en botón GUARDAR
		 */
		addStep("Tap en botón  GUARDAR");
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 23 - Validar que se posicione en el producto recién contabilizado y
		 * actualice el teórico indicado por el sistema y el físico capturado en el
		 * conteo
		 */
		addStep("Validar que se posicione en el producto recién contabilizado y actualice el teórico indicado por el sistema y el físico capturado en el conteo");
		List<WebElement> teoricoFisico2 = conteosProp.getTeoricoFisico(1);
		for (int j = 0; j < teoricoFisico2.size(); j++) {
			String txtValue = teoricoFisico2.get(j).getText();
			if (j == 1) {
				Assert.assertEquals(txtValue, conteosProp.obtieneTeoricoEnMemoria(),
						"El teorico no es igual al valor mostrado en la captura de conteo");
			}
			if (j == 2) {
				Assert.assertEquals(txtValue, conteosProp.obtieneFisicoEnMemoria(),
						"El conteo no es igual al valor registrado");
			}
			System.out.println("Texto: " + txtValue);
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("anterior");
		comunes.limpiarTablasMVP3("actual");
	}
}
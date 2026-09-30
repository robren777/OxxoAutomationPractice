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

public class ATC_FT_155_HANDHELDValidarElConteoManualDeSKUsEnConteosPropios extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\r\n" + "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD \r\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "5. Acceso a Internet\r\n" + "6. Haber creado una plantilla SIN CONTEOS INICIADOS\r\n"
				+ "7. Haber incluido en la plantilla SKUS con valor en stock en sistema";
	}

	@Override
	public String setTestDescription() {
		return "Validar el conteo manual de SKUs en Conteos Propio";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_155_HANDHELDValidarElConteoManualDeSKUsEnConteosPropios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_155_HANDHELDValidarElConteoManualDeSKUsEnConteosPropios
	 * 
	 * @autor Marcos Morales
	 * @date 18/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_155_HANDHELDValidarElConteoManualDeSKUsEnConteosPropios_test(HashMap<String, String> data)
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

		// Crear Plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 3, "actual", false,
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
		addStep("Tap en botón CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 7 - Tap en botón Iniciar Conteos
		 */
		addStep("Tap en botón Iniciar Conteos");
		menus.clickBtnIniciarConteo(true);

		/*
		 * Paso 8 - Selecciona una plantilla de conteos Propios
		 */
		addStep("Selecciona una plantilla de conteos Propios");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 * Paso 9 - Tap en botón ACEPTAR consolidación
		 */
		addStep("Tap en botón ACEPTAR consolidación");
		comunes.clickBtnAceptar(true);

		/*
		 * Paso 10 - ? Validar que se muestre el detalle de la plantilla indicando en el
		 * titulo SEMANA X (número de semana).XXXX(año en curso)
		 */
		addStep("? Validar que se muestre el detalle de la plantilla indicando en el titulo SEMANA X (número de semana).XXXX(año en curso)");
		boolean[] etiquetas = conteosProp.retornaEstadoElementosDetallePlantilla();
		Assert.assertTrue(etiquetas[0]);
		conteosProp.instanciaDetailTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Capturar un SKU que se encuentre en la plantilla dentro del cuadro
		 * de texto (INGRESA EL SKU DE UN ARTÍCULO)
		 */
		addStep("Capturar un SKU que se encuentre en la plantilla dentro del cuadro de texto (INGRESA EL SKU DE UN ARTÍCULO)");
		//conteosProp.capturarSKUConExistencia(1);
		conteosProp.capturarSKU(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Dar clic en el botón -->l para aceptar el ingreso de sku
		 */
		addStep("Dar clic en el botón -->l para aceptar el ingreso de sku ");
		conteosProp.clickEnterBarcode(true);

		/*
		 * Paso 13 - ? Validar que se muestre la pantalla de captura con los datos
		 */
		addStep("? Validar que se muestre la pantalla de captura con los datos:");
		boolean[] etqConteos = conteosProp.retornaEstadoElementosCapturaConteo();
		Assert.assertTrue(etqConteos[0]);
		Assert.assertTrue(etqConteos[1]);
		Assert.assertTrue(etqConteos[2]);
		Assert.assertTrue(etqConteos[3]);
		Assert.assertTrue(etqConteos[4]);
		Assert.assertTrue(etqConteos[5]);
		Assert.assertTrue(etqConteos[6]);
		Assert.assertTrue(etqConteos[7]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 14 - Captura una cantidad de físico que sea menor al teórico existente
		 * del sku a contar
		 */
		addStep("Captura una cantidad de fisico que sea menor al teorico existente del sku a contar");
		conteosProp.capturarFisico("menor", false, false, true);

		/*
		 * Paso 15 - ? Validar que el sistema realice el calculo para indicar la
		 * cantidad faltante resultante seguida del texto Faltantes
		 */
		addStep("? Validar que el sistema realice el calculo para indicar la cantidad faltante resultante seguida del texto Faltantes");
		String lblFaltantes = conteosProp.retornaEtiquetaFaltantesSobrantes();
		Assert.assertEquals(lblFaltantes, "Faltantes");
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 16 - Captura el mismo SKU del paso 11 dentro del cuadro de texto
		 * (INGRESA EL SKU DE UN ARTÍCULO)
		 */
		addStep("Captura el mismo SKU del paso 11 dentro del cuadro de texto (INGRESA EL SKU DE UN ARTÍCULO)");
		//conteosProp.capturarSKUConExistencia(1);
		conteosProp.capturarSKU(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 17 - Dar clic en el botón -->l para aceptar el ingreso de sku
		 */
		addStep("Dar clic en el botón -->l para aceptar el ingreso de sku ");
		conteosProp.clickEnterBarcode(true);

		/*
		 * Paso 18 - ? Validar que se muestre la pantalla de captura con los datos
		 */
		addStep("? Validar que se muestre la pantalla de captura con los datos:");
		conteosProp.esperaCampoCapturarConteo();
		Assert.assertTrue(etqConteos[0]);
		Assert.assertTrue(etqConteos[1]);
		Assert.assertTrue(etqConteos[2]);
		Assert.assertTrue(etqConteos[3]);
		Assert.assertTrue(etqConteos[4]);
		Assert.assertTrue(etqConteos[5]);
		Assert.assertTrue(etqConteos[6]);
		Assert.assertTrue(etqConteos[7]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 19 - Captura una cantidad de físico que permita igualar al teórico
		 * existente del sku a contar
		 */
		addStep("Captura una cantidad de fisico que permita igualar al teorico existente del sku a contar");
		conteosProp.capturarFisico(1, true, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 20 - ? Validar que el sistema no muestre faltantes
		 */
		addStep("? Validar que el sistema no muestre faltantes");
		boolean edoEtiqueta = conteosProp.etqFaltanteSobrantesNOVisible();
		Assert.assertTrue(edoEtiqueta);
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 21 - ?Validar existencia de botón IMPRIMIR y GUARDAR
		 */
		addStep("?Validar existencia de boton IMPRIMIR y GUARDAR");
		boolean[] edoBtnsImprimirGuardar = conteosProp.retornaEstadoBtnsImprimirGuardar();
		Assert.assertTrue(edoBtnsImprimirGuardar[0]);
		Assert.assertTrue(edoBtnsImprimirGuardar[1]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 22 - Tap en botón GUARDAR
		 */
		addStep("Tap en botón  GUARDAR");
		conteosProp.clickBtnGuardarPreInv(true);
		comunes.clickBtnAceptar(false);
		conteosProp.esperaSeOcultaMensajeGuardado();

		/*
		 * Paso 23 - ? Validar que se posicione en el producto recién contabilizado y
		 * actualice el teórico indicado por el sistema y el físico capturado en el
		 * conteo.
		 */
		addStep("? Validar que se posicione en el producto recien contabilizado y actualice el teorico indicado por el sistema y el fisico capturado en el conteo");
		List<WebElement> teoricoFisico = conteosProp.getTeoricoFisico(0);
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

		
		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
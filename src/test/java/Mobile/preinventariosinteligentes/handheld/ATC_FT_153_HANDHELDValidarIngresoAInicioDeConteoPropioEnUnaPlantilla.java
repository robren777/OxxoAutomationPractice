package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ATC_FT_153_HANDHELDValidarIngresoAInicioDeConteoPropioEnUnaPlantilla extends BaseExecution {

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
		return "Validar Ingreso a Inicio de Conteo Propio en una Plantilla";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_153_HANDHELDValidarIngresoAInicioDeConteoPropioEnUnaPlantilla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_153_HANDHELDValidarIngresoAInicioDeConteoPropioEnUnaPlantilla
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_153_HANDHELDValidarIngresoAInicioDeConteoPropioEnUnaPlantilla_test(HashMap<String, String> data)
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
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 4, "actual", false,
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
		 * Paso 10 - Validar que se muestre el detalle de la plantilla indicando en el
		 * titulo SEMANA X (número de semana).XXXX(año en curso)
		 */
		addStep("Validar que se muestre el detalle de la plantilla indicando en el titulo SEMANA X (número de semana).XXXX(año en curso)");
		boolean[] etiquetas = conteosProp.retornaEstadoElementosDetallePlantilla();
		Assert.assertTrue(etiquetas[0]);
		conteosProp.instanciaDetailTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Se muestra la plantilla colapsada identificada con el + y debajo el
		 * colaborador asignado a la plantilla
		 */
		addStep("Se muestra la plantilla colapsada identificada con el + y debajo el colaborador asignado a la plantilla ");
		String edoSelectivo = conteosProp.obtieneTextoBtnPlus();
		Assert.assertEquals(edoSelectivo, "+");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Tap en el nombre de la plantilla
		 */
		addStep("Tap en el nombre de la plantilla ");
		conteosProp.clickNombrePlantilla();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 13 - ? Validar que se muestren todos los productos pertenecientes a la
		 * plantilla y que en las columnas de Teórico y Físico se muestre el signo "-"
		 */
		addStep("? Validar que se muestren todos los productos pertenecientes a la plantilla y que en las columnas de Teórico y Físico se muestre el signo - ");
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
		 * Paso 14 - Validar que se muestre el apartado de Totales(Faltantes, Sobrantes)
		 * y Resultado iniciado en 0
		 */
		addStep("Validar que se muestre el apartado de Totales(Faltantes, Sobrantes) y Resultado iniciado en 0  ");
		Map<String, String> footerValues = conteosProp.obtieneFaltantesSobrantesApp();
		Assert.assertEquals("Total Faltantes : ", footerValues.get("valor00"));
		Assert.assertEquals("0.00", footerValues.get("valor01"));
		Assert.assertEquals("$0.00", footerValues.get("valor02"));
		Assert.assertEquals("Total Sobrantes : ", footerValues.get("valor10"));
		Assert.assertEquals("0.00", footerValues.get("valor11"));
		Assert.assertEquals("$0.00", footerValues.get("valor12"));
		Assert.assertEquals("Resultado", footerValues.get("valor20"));
		Assert.assertEquals("$0.00", footerValues.get("valor21"));
		Assert.assertEquals("Conteos actuales:", footerValues.get("valor30"));
		Assert.assertEquals("Tiempo transcurrido:", footerValues.get("valor31"));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Validar que el cronometro se encentre en (00:00) y el dato de
		 * conteos actuales indique 0 de x (cantidad de productos de la plantilla)
		 */
		addStep("Validar que el cronometro se encentre en (00:00) y el dato de conteos actuales indique 0 de x cantidad de productos de la plantilla");
		String cronValue = conteosProp.getCronValueTxt();
		Assert.assertEquals(cronValue, "00:00");
		int totalSkusBD = conteosProp.obtieneTotalSKUSenBDPlantilla();
		String totalSkusAppStr = conteosProp.obtienetotalSkusApp();
		Assert.assertEquals(totalSkusAppStr, "0 de " + totalSkusBD);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 16 - Validar existencia de botón IMPRIMIR y GUARDAR
		 */
		addStep("Validar existencia de botón IMPRIMIR y GUARDAR");
		boolean[] edoBtnsImprimirGuardar = conteosProp.retornaEstadoBtnsImprimirGuardar();
		Assert.assertTrue(edoBtnsImprimirGuardar[0]);
		Assert.assertTrue(edoBtnsImprimirGuardar[1]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");

	}
}
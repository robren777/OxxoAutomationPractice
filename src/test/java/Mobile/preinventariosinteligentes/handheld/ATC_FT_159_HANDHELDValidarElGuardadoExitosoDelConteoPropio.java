package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.SessionNotCreatedException;
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

public class ATC_FT_159_HANDHELDValidarElGuardadoExitosoDelConteoPropio extends BaseExecution {

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
		return "Validar el guardado exitoso del conteo propio";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_159_HANDHELDValidarElGuardadoExitosoDelConteoPropio";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_159_HANDHELDValidarElGuardadoExitosoDelConteoPropio
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_159_HANDHELDValidarElGuardadoExitosoDelConteoPropio_test(HashMap<String, String> data)
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
		conteosProp.prerequisitoPlantillasSKUS("skusactivos", data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 6, "PlantillaQA1");

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
		 * Paso 8 - Tap en una plantilla creada
		 */
		addStep("Tap en una plantilla creada");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 * Paso 9 - Dar clic en aceptar consolidación
		 */
		addStep("Dar clic en aceptar consolidación ");
		comunes.clickBtnAceptar(true);
		boolean[] etiquetas = conteosProp.retornaEstadoElementosDetallePlantilla();
		Assert.assertTrue(etiquetas[0]);
		conteosProp.instanciaDetailTemplate();

		/*
		 * Paso 10 - Se muestra la plantilla colapsada identificada con el +
		 */
		addStep("Se muestra la plantilla colapsada identificada con el +");
		String edoSelectivo = conteosProp.obtieneTextoBtnPlus();
		Assert.assertEquals(edoSelectivo, "+");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Dar clic sobre el nombre de la plantilla
		 */
		addStep("Dar clic sobre el nombre de la plantilla");
		conteosProp.clickNombrePlantilla();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Realizar el conteo con faltantes de 3 productos listados en la
		 * plantilla
		 */
		addStep("Realizar el conteo con faltantes de 3 productos listados en la plantilla");
		conteosProp.registrarConteoMenorPorPlantilla(3, 1, false, false, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Tap en botón Guardar
		 */
		addStep("Tap en botón Guardar");
		conteosProp.clickBtnGuardarPreInv(true);

		/*
		 * Paso 13 - Realizar la confirmación del guardado, Tap en botón GUARDAR
		 */
		addStep("Realizar la confirmación del guardado, Tap en botón GUARDAR");
		comunes.clickBtnAceptar(true);

		/*
		 * Paso 14 - Validar que el sistema complete el "Conteo guardado con éxito" y
		 * actualice en BD el guardado de la plantilla correctamente
		 */
		addStep("Validar que el sistema complete el Conteo guardado con éxito y actualice en BD el guardado de la plantilla correctamente");
		String mensaje = conteosProp.obtenerMensajeGuardado();
		Assert.assertEquals(mensaje, "Conteo guardado con éxito");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Validar que se muestre la plantilla expandida a como previamente se
		 * mostraba antes de aplicar el guardado
		 */
		addStep("Validar que se muestre la plantilla expandida a como previamente se mostraba antes de aplicar el guardado");
		String edoSelectivo2 = conteosProp.obtieneTextoBtnPlus();
		Assert.assertEquals(edoSelectivo2, "-");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 17 - Validar que se actualice correctamente la información de faltantes
		 * de acuerdo a lo más actual de inventario teorico del sistema
		 */
		addStep("Validar que se actualice correctamente la información de faltantes de acuerdo a lo más actual de inventario teorico del sistema");
		Map<String, String> footerValues = conteosProp.obtieneFaltantesSobrantesApp();
		String faltantes = "";
		String faltantesM = "";
		String sobrantes = "";
		String sobrantesM = "";

		ResultSet rs = conteosProp.obtieneFaltantesSobrantesBD("P");

		try {
			if (rs.next()) {
				faltantes = rs.getString(1);
				faltantesM = rs.getString(2);
				sobrantes = rs.getString(3);
				sobrantesM = rs.getString(4);
			} else {
				throw new AssertionError(
						"No se encontraron registros al ejecutar la consulta del método: obtieneFaltantesSobrantesBD");
			}
			Assert.assertEquals("Total Faltantes : ", footerValues.get("valor00"));
			Assert.assertEquals(footerValues.get("valor01"), faltantes);
			Assert.assertEquals(footerValues.get("valor02"), faltantesM);
			Assert.assertEquals("Total Sobrantes : ", footerValues.get("valor10"));
			Assert.assertEquals(footerValues.get("valor11"), sobrantes);
			Assert.assertEquals(footerValues.get("valor12"), sobrantesM);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");

	}
}
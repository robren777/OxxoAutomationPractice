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
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_161_HANDHELDValidarElAutoguardadoExitosoEnConteosPropios extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD \r\n"
				+ "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "5. Acceso a Internet\r\n"
				+ "6. Haber creado una plantilla SIN CONTEOS INICIADOS\r\n"
				+ "7. Haber incluido en la plantilla SKUS con valor en stock en sistema";
	}

	@Override
	public String setTestDescription() {
		return	  "Validar el autoguardado exitoso en conteos propios";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_161_HANDHELDValidarElAutoguardadoExitosoEnConteosPropios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_161_HANDHELDValidarElAutoguardadoExitosoEnConteosPropios

	 * @autor Marcos Morales
	 * @date 19/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_161_HANDHELDValidarElAutoguardadoExitosoEnConteosPropios_test (HashMap<String, String> data) throws Exception {

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
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);


		//PRECONDICIONES
		/***************************************************/
		//Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		//Crear Plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 3, "actual", false, false);


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
		 *  Paso 8 - Selecciona una plantilla de conteos propios
		 */
		addStep("Selecciona una plantilla de conteos propios");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 *  Paso 9 - Dar clic en aceptar consolidación
		 */
		addStep("Dar clic en aceptar consolidación");
		comunes.clickBtnAceptar(true);

		/*
		 *  Paso 10 - Dar clic sobre el nombre de la plantilla
		 */
		addStep("Dar clic sobre el nombre de la plantilla");
		conteosProp.clickBtnPlusTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosProp.instanciaDetailTemplate();

		/*
		 *  Paso 11 - Realizar el conteo con sobrantes de 3 productos listados en la plantilla
		 */
		addStep("Realizar el conteo con sobrantes de 3 productos listados en la plantilla");
		conteosProp.registrarConteoMayorPorPlantilla(3, false, false, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 12 - Validar que el sistema realice el autoguardo una vez que se cumpla el tiempo configurado
		 */
		addStep("Validar que el sistema realice el autoguardo una vez que se cumpla el tiempo configurado");
		boolean autoguardado = conteosInt.retornaEstadoAutoGuardado(Duration.ofMinutes(5));
		Assert.assertTrue(autoguardado, "No se ejecutó el Autoguardado después de 5 minutos.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosInt.esperaSeOculteMensajeGuardado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 13 - Validar que se muestre la plantilla expandida a como previamente se mostraba antes de aplicar el autoguardado
		 */
		addStep("Validar que se muestre la plantilla expandida a como previamente se mostraba antes de aplicar el autoguardado");
		String edoSelectivo = conteosProp.obtieneTextoBtnPlus();
		Assert.assertEquals(edoSelectivo, "-");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 14 - Validar que se actualice correctamente la información de sobrantes de acuerdo a lo más actual de inventario teorico del sistema
		 */
		addStep("Validar que se actualice correctamente la información de sobrantes de acuerdo a lo más actual de inventario teorico del sistema");
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
				throw new AssertionError("No se encontraron registros al ejecutar la consulta del método: obtieneFaltantesSobrantesBD" );
			}
			Assert.assertEquals("Total Faltantes : ", footerValues.get("valor00"));
			Assert.assertEquals(footerValues.get("valor01"), faltantes);
			Assert.assertEquals(footerValues.get("valor02"), faltantesM);
			Assert.assertEquals("Total Sobrantes : ", footerValues.get("valor10"));
			Assert.assertEquals(footerValues.get("valor11"), sobrantes);
			Assert.assertEquals(footerValues.get("valor12"), sobrantesM);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
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
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_228_HHConteosInteligentesGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloSeleccionado extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD\r\n"
				+ "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "5. Acceso a Internet";
	}

	@Override
	public String setTestDescription() {
		return	  "Captura manual-Crear plantilla con SKUs Activos e Inactivos";
	}

	@Override
	public String setTestDesigner() {
		return "Alvaro Chavez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_228_HHConteosInteligentesGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloSeleccionado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_228_HHConteosInteligentesGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloSeleccionado
	   
	 * @autor Alvaro Chavez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)

	public void ATC_FT_228_HHConteosInteligentesGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloSeleccionado_test (HashMap<String, String> data) throws Exception {
		
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
			assertTrue(false, "Hubo un problema al acceder a la aplicaci n. \n"
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
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);
		ConteosPropios conteosProp = new ConteosPropios(testCase, appiumUtil, this);
		
		
		//PRECONDICIONES
        /***************************************************/
		//Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");
		
		//Ejecuta el flujo de Asignación Automatica
		conteosInt.prerequisitoAsigAutomatica(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", 2, false);
		
		
		//FLUJO PRINCIPAL
        /***************************************************/
		/*
		 *  Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENU PRINCIPAL de Oxxo al dia");
		login.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 2 - Ingresar en opción Control
		 */
		addStep("Ingresar en opcion Control");
	    menus.clickBtnControl(true);
		  
		/*
		 *  Paso 3 - Ingresar en opción Preinventarios Inteligentes
		 */
	    addStep("Ingresar en opcion Preinventarios Inteligentes");
	    menus.clickBtnPreInvInt(true);
	      
	    /*
		 *  Paso 4 - Ingresar a Conteos inteligentes
		 */
	    addStep("Ingresar a Conteos inteligentes");
		menus.clickBtnConteosInt(true);
		
		/*
		 *  Paso 5 - Seleccionar check de la semana en curso
		 */
		addStep("Seleccionar check de la semana en curso");
		comunes.seleccionarSemana(true);
		
		/*
		 *  Paso 6 - Tap en botón CONTINUAR
		 */
		addStep("Tap en boton CONTINUAR");
		comunes.clickBtnContinuar(true);
		
		/*
		 *  Paso 7 - Tap en botón ACEPTAR
		 */
		addStep("Tap en botón ACEPTAR");
		comunes.clickBtnAceptar(true);
		comunes.clickBtnAceptarConteosFaltantes(false);
		
		/*
		 *  Paso 8 - Validar se encuentre posicionado el cursor en campo INGRESA EL SKU
		 */
		addStep("Validar se encuentre posicionado el cursor en cuadro de texto INGRESA EL SKU");
		boolean focus = conteosInt.retornaCursorActivoEnCampoIngresarSKU();
		Assert.assertTrue(focus, "El focus no se encuentra sobre la caja de texto para ingresar el SKU");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosInt.clickBtnPlus();
		
		/*
		 *  Paso 9 - Seleccionar articulo de la plantilla
		 */
		addStep("Seleccionar articulo de la plantilla");
		conteosInt.clickFirstSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 10 - Validar cursor en campo Fisico
		 */
		addStep("Validar cursor en campo Fisico");
		boolean edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarConteo();
		Assert.assertTrue(edoKeyboard, "El teclado no se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 11 - Ingresar cantidad
		 */
		addStep("Ingresar cantidad");
		conteosInt.capturarConteo("10");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 12 - Tap en boton GUARDAR
		 */
		addStep("Tap en boton GUARDAR");
		conteosProp.clickBtnGuardarCto(true);
		edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarSKU();
		Assert.assertFalse(edoKeyboard, "El teclado se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//CIERRE
        /***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
    	}
}
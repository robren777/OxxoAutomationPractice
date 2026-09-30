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
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_217_HHConteosPropiosIniciarConteoGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloCapturado extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\n"
				+ "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n"
				+ "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n"
				+ "6. Haber seleccionado clasificación Proveedor por primera vez en la semana\n"
				+ "7. Haber creado plantillas";
	}

	@Override
	public String setTestDescription() {
		return	  "Validar que el teclado se oculte al guardar el conteo del articulo capturado";
	}

	@Override
	public String setTestDesigner() {
		return "Alvaro Chavez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_217_HHConteosPropiosIniciarConteoGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloCapturado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_217_HHConteosPropiosIniciarConteoGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloCapturado
	   
	 * @autor Alvaro Chavez
	 * @date 16/12/2024
	 */
	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_217_HHConteosPropiosIniciarConteoGUARDARValidarTecladoSeOculteAlGuardarConteoDeArticuloCapturado_test (HashMap<String, String> data) throws Exception {
		
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
		 *  Paso 4 - Ingresar en opción Conteos Propios
		 */
	    addStep("Ingresar en opcion Conteos Propios");
		menus.clickBtnConteosProp(true);
		
		/*
		 *  Paso 5 - Seleccionar la semana en curso
		 */
		addStep("Seleccionar la semana en curso");
		comunes.seleccionarSemana(true);
		
		/*
		 *  Paso 6 - Tap en botón CONTINUAR
		 */
		addStep("Tap en boton CONTINUAR");
		comunes.clickBtnContinuar(true);
		
		/*
		 *  Paso 7 - Dar clic en Opción Iniciar Conteos
		 */
		addStep("Dar clic en opcion Iniciar Conteos");
		menus.clickBtnIniciarConteo(true);
		
		/*
		 *  Paso 8 - Validar que se muestre el listado de plantillas creadas
		 */
		addStep("Validar que se muestre el listado de plantillas creadas");
		int cantPlantillasBD = conteosProp.retornaCantidadPlantillasBD();
		List<WebElement> plantillas = conteosProp.listaPlantillas();
		int cantPlantillasApp = plantillas.size();
		Assert.assertEquals(cantPlantillasApp, cantPlantillasBD, "Validación incorrecta: existen diferencias entre los resultados en BD y los registros mostrados en pantalla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 9 - Seleccionar una plantilla creada
		 */
		addStep("Seleccionar una plantilla creada");
		conteosProp.seleccionarPlantilla(1, true);
		
		/*
		 *  Paso 10 - Tap en botón ACEPTAR
		 */
		addStep("Tap en botón ACEPTAR");
		comunes.clickBtnAceptar(true);
		conteosProp.esperaCampoIngresarSKU();
		conteosProp.instanciaDetailTemplate();

		/*
		 *  Paso 11 - Validar se encuentre posicionado el cursor en campo INGRESA EL SKU
		 */
		addStep("Validar se encuentre posicionado el cursor en cuadro de texto INGRESA EL SKU");
		boolean focus = conteosProp.retornaCursorActivoEnCampoIngresarSKU();
		Assert.assertTrue(focus, "El focus no se encuentra sobre la caja de texto para ingresar el SKU");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 12 - Hacer tap en cuadro de texto INGRESA EL SKU
		 */
		addStep("Hacer tap en cuadro de texto INGRESA EL SKU");
		conteosInt.clickTxtBarcode(false);
		boolean edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarSKU();
		Assert.assertTrue(edoKeyboard, "El teclado no se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 13 - Ingresar SKU de articulo
		 */
		addStep("Ingresar SKU de articulo");
		conteosProp.capturarSKU(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 14 - Tap en boton Enter(Flecha azul)
		 */
		addStep("Tap en boton Enter(Flecha azul)");
		conteosProp.clickEnterBarcode(true);
		
		/*
		 *  Paso 15 - Validar muestre informacion de articulo
		 */
		addStep("Validar muestre informacion de articulo");
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
		 *  Paso 16 - Validar cursor en campo Fisico
		 */
		addStep("Validar cursor en campo Fisico");
		edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarConteo();
		Assert.assertTrue(edoKeyboard, "El teclado no se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 17 - Ingresar cantidad
		 */
		addStep("Ingresar cantidad");
		conteosProp.capturarConteo("10");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 18 - Tap en boton GUARDAR
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

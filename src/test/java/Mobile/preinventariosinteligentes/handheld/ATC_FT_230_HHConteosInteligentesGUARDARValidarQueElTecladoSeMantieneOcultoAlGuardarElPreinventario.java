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

public class ATC_FT_230_HHConteosInteligentesGUARDARValidarQueElTecladoSeMantieneOcultoAlGuardarElPreinventario extends BaseExecution{

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
		return	  "Descripcion de mi caso de prueba";
	}

	@Override
	public String setTestDesigner() {
		return "Brandon Santos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_230_HHConteosInteligentesGUARDARValidarQueElTecladoSeMantieneOcultoAlGuardarElPreinventario";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_230_HHConteosInteligentesGUARDARValidarQueElTecladoSeMantieneOcultoAlGuardarElPreinventario
	   
	 * @autor Brandon Santos
	 * @date 23/12/2024
	 */
	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_230_HHConteosInteligentesGUARDARValidarQueElTecladoSeMantieneOcultoAlGuardarElPreinventario_test (HashMap<String, String> data) throws Exception {
		
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
		
		/*
		 *  Paso 9 - Hacer tap en cuadro de texto INGRESA EL SKU
		 */
		addStep("Hacer tap en cuadro de texto INGRESA EL SKU");
		conteosInt.clickTxtBarcode(true);
		
		/*
		 *  Paso 10 - Ingresar SKU de articulo
		 */
		addStep("Ingresar SKU de articulo");
		conteosInt.capturarSKU(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 11 - Tap en boton Enter(Flecha azul)
		 */
		addStep("Tap en boton Enter(Flecha azul)");
		conteosInt.clickEnterBarcode(true);
		
		/*
		 *  Paso 12 - Validar muestre informacion de articulo
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
		 *  Paso 13 - Validar cursor en campo Fisico
		 */
		addStep("Validar cursor en campo Fisico");
		boolean edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarConteo();
		Assert.assertTrue(edoKeyboard, "El teclado no se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 14 - Ingresar cantidad
		 */
		addStep("Ingresar cantidad");
		conteosInt.capturarConteo("10");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 15 - Tap en boton GUARDAR
		 */
		addStep("Tap en boton GUARDAR");
		conteosProp.clickBtnGuardarCto(true);
		edoKeyboard = conteosProp.retornaTecladoVisibleEnCampoIngresarSKU();
		Assert.assertFalse(edoKeyboard, "El teclado se encuentra visible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 *  Paso 16 - Dar clic en opción GUARDAR ubicado en la parte inferior central
		 */
		addStep("Dar clic en opción GUARDAR ubicado en la parte inferior central");
		conteosInt.clickBtnGuardar(true);
		
		/*
		 *  Paso 17 - Seleccionar opcion GUARDAR
		 */
		addStep("Señeccionar opcion GUARDAR");
		//conteosProp.clickBtnGuardarPreInv(true);
		comunes.clickBtnAceptar(true);
		conteosProp.esperaSeOcultaMensajeGuardado();
		
		/*
		 *  Paso 18 - Validar que el cursor se mantiene activo en el campo para capturar SKUs y el teclado no es visible
		 */
		addStep("Validar que el cursor se mantiene activo en el campo para capturar SKUs y el teclado no es visible");
		focus = conteosProp.retornaCursorActivoEnCampoIngresarSKU();
		Assert.assertTrue(focus, "El focus no se encuentra sobre la caja de texto para ingresar el SKU");
		conteosInt.clickBtnPlus();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//CIERRE
        /***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
    	}
}
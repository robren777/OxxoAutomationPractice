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
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_172_HANDHELDModuloCPPermitaLaImpresiónDeTicketConTodosConteosRealizadosYReinicieLosConteosPlantillaSeleccionada extends BaseExecution{

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
				+ "6. Haber creado una plantilla CON TODOS LOS CONTEOS REALIZADOS";

	}

	@Override
	public String setTestDescription() {
		return	  "Modulo Conteos Propios-Permita la impresión de ticket con todos los conteos realizados y a su vez reinicie los conteos de  la plantilla seleccionada";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_172_HANDHELDModuloCPPermitaLaImpresiónDeTicketConTodosConteosRealizadosYReinicieLosConteosPlantillaSeleccionada";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_172_HANDHELDModuloCPPermitaLaImpresiónDeTicketConTodosConteosRealizadosYReinicieLosConteosPlantillaSeleccionada

	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_172_HANDHELDModuloCPPermitaLaImpresiónDeTicketConTodosConteosRealizadosYReinicieLosConteosPlantillaSeleccionada_test (HashMap<String, String> data) throws Exception {

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

		//PRECONDICIONES
		/***************************************************/
		comunes.limpiarTablasMVP3("actual");

		//Realizar la carga de plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS("skusactivos", data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 4, "PlantillaQA_CP172");
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		conteosProp.prerequisitoCapturaConteosPlantillas(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "PlantillaQA_CP172", 4, "3");

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
		addStep("Tap en boton CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 *  Paso 7 - Tap en botón Iniciar Conteos
		 */
		addStep("Tap en botón Iniciar Conteos");
		menus.clickBtnIniciarConteo(true);

		/*
		 *  Paso 8 - Seleccionar Plantilla
		 */
		addStep("Seleccionar Plantilla");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 *  Paso 9 - Seleccionar Aceptar la consolidación 
		 */
		addStep("Seleccionar Aceptar la consolidación ");
		comunes.clickBtnAceptar(true);

		/*
		 *  Paso 10 - Seleccionar Imprimir 
		 */
		addStep("Seleccionar Imprimir");
		conteosProp.clickBtnImprimir(true);
		comunes.clickBtnAceptar(false);
		conteosProp.esperaCampoIngresarSKU();

		/*
		 *  Paso 11 - Validar se realice el guardado y reinicio de los conteos en BD para la plantilla correspondiente
		 */
		addStep("Validar se realice el guardado y reinicio de los conteos en BD para la plantilla correspondiente");
		int reseteo = conteosProp.retornaPlantillaReseteadaBD("PlantillaQA_CP172");
		Assert.assertNotEquals(0, reseteo, "No se aplico correctamente el reseteo de la plantilla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		String resultadoBD = conteosProp.generaStringPlantillaReseteadaBD("PlantillaQA_CP172");
		testCase.addTextEvidenceCurrentStep(resultadoBD);

		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");		
	}
}
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

public class ATC_FT_203_HHConteosPropiosIniciarConteoPlantillaTODOSLosConteosBotonAceptarEnOpcionGuardarConteoYReiniciarPlantilla extends BaseExecution{

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
				+ "5. Acceso a Internet\r\n"
				+ "6. Haber creado una plantilla CON TODOS LOS CONTEOS REALIZADOS";
	}

	@Override
	public String setTestDescription() {
		return	  "Modulo Conteos Propios-Iniciar Conteo-Plantilla TODOS los conteos - Boton Aceptar en opcion Guardar conteo y reiniciar plantilla";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_203_HHConteosPropiosIniciarConteoPlantillaTODOSLosConteosBotonAceptarEnOpcionGuardarConteoYReiniciarPlantilla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_203_HHConteosPropiosIniciarConteoPlantillaTODOSLosConteosBotonAceptarEnOpcionGuardarConteoYReiniciarPlantilla

	 * @autor Marcos Morales
	 * @date 20/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_203_HHConteosPropiosIniciarConteoPlantillaTODOSLosConteosBotonAceptarEnOpcionGuardarConteoYReiniciarPlantilla_test (HashMap<String, String> data) throws Exception {

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
		//Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		//Crear Plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS("skusactivos", data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 7, "PlantillaCP203");

		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		conteosProp.prerequisitoCapturaConteosPlantillas(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "PlantillaCP203", 7, "8");


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
		 *  Paso 8 - Validar que se muestre el listado de plantillas creadas
		 */
		addStep("Validar que se muestre el listado de plantillas creadas");
		int cantPlantillasBD = conteosProp.retornaCantidadPlantillasBD();
		List<WebElement> plantillas = conteosProp.listaPlantillas();
		int cantPlantillasApp = plantillas.size();
		Assert.assertEquals(cantPlantillasApp, cantPlantillasBD, "Validación incorrecta: existen diferencias entre los resultados en BD y los registros mostrados en pantalla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 9 - Realizar SWIPE sobre la plantilla con todo el conteo
		 */
		addStep("Realizar SWIPE sobre la plantilla con todo el conteo");
		conteosProp.resetearPlantillas(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 10 - Tap en boton RESETEAR
		 */
		addStep("Tap en boton RESETEAR");
		conteosProp.clickOpcionResetear(580, 538);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 11 - Seleccionar opcion "Guardar conteo y reiniciar plantilla"
		 */
		addStep("Seleccionar opcion \"Guardar conteo y reiniciar plantilla\"");
		conteosProp.clickOpcionGuardarReiniciar(true);

		/*
		 *  Paso 12 - Tap en boton ACEPTAR
		 */
		addStep("Tap en boton ACEPTAR");
		conteosProp.clickBtnReinicioAceptar(true);

		/*
		 *  Paso 13 - Tap en boton ACEPTAR
		 */
		addStep("Tap en boton ACEPTAR");
		comunes.clickBtnAceptar(true);
		String mensaje = conteosProp.obtenerMensaje();
		Assert.assertEquals(mensaje, "Reseteo de la plantilla exitoso.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		//Espera de 5 segundos para que el backend concluya su proceso
		Thread.sleep(5000);

		/*
		 *  Paso 14 - Seleccionar plantilla reseteada
		 */
		addStep("Seleccionar plantilla reseteada");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 *  Paso 15 - Validar se haya reseteado plantilla
		 */
		addStep("Validar se haya reseteado plantilla");
		comunes.clickBtnAceptar(false);
		conteosProp.clickBtnPlusTemplate();
		List<WebElement> productos = conteosProp.listaProductos();
		for (int i = 0; i < productos.size(); i++) {
			WebElement productoN = productos.get(i);
			List<WebElement> textsContent = productoN.findElements(By.className("android.widget.TextView"));

			//Omite la ejecucion para el primer y ultimo elemento ya que solo verifica el valor de teorico y físico
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
		 *  Paso 16 - Validar se realice el guardado y reinicio de los conteos en BD para la plantilla correspondiente
		 */
		addStep("Validar se realice el guardado y reinicio de los conteos en BD para la plantilla correspondiente");
		int reseteo = conteosProp.retornaPlantillaReseteadaBD("PlantillaCP203");
		Assert.assertNotEquals(0, reseteo, "No se aplico correctamente el reseteo de la plantilla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		String resultadoDB = conteosProp.generaStringPlantillaReseteadaBD("PlantillaCP203");
		testCase.addTextEvidenceCurrentStep(resultadoDB);


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
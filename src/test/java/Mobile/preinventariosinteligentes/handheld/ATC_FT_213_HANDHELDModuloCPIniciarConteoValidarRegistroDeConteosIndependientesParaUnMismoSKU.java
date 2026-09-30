package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
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

public class ATC_FT_213_HANDHELDModuloCPIniciarConteoValidarRegistroDeConteosIndependientesParaUnMismoSKU extends BaseExecution{

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
		return	  "Modulo Conteos Propios-Iniciar conteo-Validar registro de conteos independientes para un mismo SKU";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_213_HANDHELDModuloCPIniciarConteoValidarRegistroDeConteosIndependientesParaUnMismoSKU";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:ATC_FT_213_HANDHELDModuloCPIniciarConteoValidarRegistroDeConteosIndependientesParaUnMismoSKU

	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_213_HANDHELDModuloCPIniciarConteoValidarRegistroDeConteosIndependientesParaUnMismoSKU_test (HashMap<String, String> data) throws Exception {

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
		conteosProp.prerequisitoPlantillasSKUS("skusactivos", data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 7, "PlantillaCP213");

		//Se crea una nueva plantilla con los skus de la plantilla previa
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 7, "PlantillaCP213 MISMOS SKUS", "PlantillaCP213");


		//FLUJO PRINCIPAL
		/***************************************************/
		/*
		 *  Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENÚ PRINCIPAL de Oxxo al día ");
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
		//conteosProp.validaPantallaIniciarConteo();
		conteosProp.esperaCampoDeBusqueda();
		int cantPlantillasBD = conteosProp.retornaCantidadPlantillasBD();
		List<WebElement> plantillas = conteosProp.listaPlantillas();
		int cantPlantillasApp = plantillas.size();
		Assert.assertEquals(cantPlantillasApp, cantPlantillasBD, "Validación incorrecta: existen diferencias entre los resultados en BD y los registros mostrados en pantalla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 9 - Seleccionar la primer Plantilla
		 */
		addStep("Seleccionar la primer Plantilla");
		//conteosProp.validaPantallaIniciarConteo();
		conteosProp.esperaCampoDeBusqueda();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		conteosProp.buscarPlantilla("PlantillaCP213");
		conteosProp.seleccionarPlantilla(1, false);

		/*
		 *  Paso 10 - Dar clic en opción ACEPTAR
		 */
		addStep("Dar clic en opción ACEPTAR");
		comunes.clickBtnAceptar(true);
		//conteosProp.validaPantallaDetallePlantilla();
		conteosProp.esperaCampoIngresarSKU();
		conteosProp.instanciaDetailTemplate();

		/*
		 *  Paso 11 - Dar clic en el botón "+"
		 */
		addStep("Dar clic en el botón \"+\"");
		conteosProp.clickBtnPlusTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 12 - Realizar el conteo de los SKUs de la plantilla
		 */
		addStep("Realizar el conteo de los SKUs de la plantilla");
		conteosProp.registrarConteoPorPlantilla("10");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 13 - Tap en botón Guardar
		 */
		addStep("Tap en botón Guardar");
		conteosProp.clickBtnGuardarPreInv(true);

		/*
		 *  Paso 14 - Confirmar el guardado, Tap en botón GUARDAR
		 */
		addStep("Confirmar el guardado, Tap en botón GUARDAR");
		comunes.clickBtnAceptar(true);

		/*
		 *  Paso 15 - Validar se muestre el mensaje "Conteo guardado con éxito"
		 */
		addStep("Validar se muestre el mensaje \"Conteo guardado con éxito\"");
		//conteosProp.validarMessage("Conteo guardado con éxito");
		String mensaje = conteosProp.obtenerMensajeGuardado();
		Assert.assertEquals(mensaje, "Conteo guardado con éxito");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 16 - Presionar el boton X de la parte superior izquierda
		 */
		addStep("Presionar el boton X de la parte superior izquierda");
		comunes.clickBtnX(true);
		conteosProp.clickBtnBorrarBusqueda(false);
		appiumUtil.driver.hideKeyboard();

		/*
		 *  Paso 17 - Ingresar a la segunda Plantilla
		 */
		addStep("Ingresar a la segunda Plantilla");
		//conteosProp.validaPantallaIniciarConteo();
		conteosProp.esperaCampoDeBusqueda();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		conteosProp.buscarPlantilla("PlantillaCP213 MISMOS SKUS");
		conteosProp.seleccionarPlantilla(1, false);

		/*
		 *  Paso 18 - Dar clic en opción ACEPTAR
		 */
		addStep("Dar clic en opción ACEPTAR");
		comunes.clickBtnAceptar(true);
		//conteosProp.validaPantallaDetallePlantilla();
		conteosProp.esperaCampoIngresarSKU();

		/*
		 *  Paso 19 - Dar clic en el botón "+"
		 */
		addStep("Dar clic en el botón \"+\"");
		conteosProp.clickBtnPlusTemplate();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 20 - Realizar el conteo de los SKUs de la plantilla
		 */
		addStep("Realizar el conteo de los SKUs de la plantilla");
		conteosProp.registrarConteoPorPlantilla("5");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 21 - Tap en botón Guardar
		 */
		addStep("Tap en botón Guardar");
		conteosProp.clickBtnGuardarPreInv(true);

		/*
		 *  Paso 22 - Confirmar el guardado, Tap en botón GUARDAR
		 */
		addStep("Confirmar el guardado, Tap en botón GUARDAR");
		comunes.clickBtnAceptar(true);

		/*
		 *  Paso 23 - Validar se muestre el mensaje "Conteo guardado con éxito"
		 */
		addStep("Validar se muestre el mensaje \"Conteo guardado con éxito\"");
		//conteosProp.validarMessage("Conteo guardado con éxito");
		mensaje = conteosProp.obtenerMensajeGuardado();
		Assert.assertEquals(mensaje, "Conteo guardado con éxito");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 24 - Presionar el boton X de la parte superior izquierda
		 */
		addStep("Presionar el boton X de la parte superior izquierda");
		comunes.clickBtnX(true);
		conteosProp.clickBtnBorrarBusqueda(false);
		appiumUtil.driver.hideKeyboard();

		/*
		 *  Paso 25 - Validar que la primer plantilla NO contiene los conteos realizados de la segunda plantilla
		 */
		addStep("Validar que la primer plantilla NO contiene los conteos realizados de la segunda plantilla");
		//conteosProp.validarConteosUnicosEnPlantillas("PlantillaCP213", "PlantillaCP213 MISMOS SKUS");
		ResultSet rs1 = conteosProp.obtieneConteosPorPlantillaBD("PlantillaCP213");
		ResultSet rs2 = conteosProp.obtieneConteosPorPlantillaBD("PlantillaCP213 MISMOS SKUS");
		boolean sonDiferentes = Comunes.areResultSetsDifferent(rs1, rs2);
		Assert.assertTrue(sonDiferentes, "Los ResultSets contienen los mismos valores");
		String resultadoDB1 = conteosProp.generaStringRegistrosDeConteosPorPlantilla("PlantillaCP213");
		testCase.addTextEvidenceCurrentStep(resultadoDB1);
		String resultadoDB2 = conteosProp.generaStringRegistrosDeConteosPorPlantilla("PlantillaCP213 MISMOS SKUS");
		testCase.addTextEvidenceCurrentStep(resultadoDB2);


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
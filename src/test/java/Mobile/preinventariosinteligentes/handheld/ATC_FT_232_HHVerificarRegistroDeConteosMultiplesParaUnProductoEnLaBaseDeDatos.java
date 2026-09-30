package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_232_HHVerificarRegistroDeConteosMultiplesParaUnProductoEnLaBaseDeDatos extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD\r\n"
				+ "4. Acceso a Internet\r\n"
				+ "5. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "6. Haber seleccionado clasificación Planograma por primera vez en la semana";
	}

	@Override
	public String setTestDescription() {
		return	  "Verificar registro de conteos múltiples para un producto en la base de datos";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_232_HHVerificarRegistroDeConteosMultiplesParaUnProductoEnLaBaseDeDatos";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_232_HHVerificarRegistroDeConteosMultiplesParaUnProductoEnLaBaseDeDatos

	 * @autor Marcos Morales
	 * @date 20/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_232_HHVerificarRegistroDeConteosMultiplesParaUnProductoEnLaBaseDeDatos_test (HashMap<String, String> data) throws Exception {

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
		addStep("Tap en boton ACEPTAR");
		comunes.clickBtnAceptar(true);

		//Articulos por contar y espera a ingresar a detalle de preinventario
		comunes.clickBtnAceptarConteosFaltantes(false);

		/*
		 *  Paso 8 - Se muestra el detalle del preinventario de la semana en curso 
		 */
		addStep("Se muestra el detalle del preinventario de la semana en curso ");
		boolean[] estatus = conteosInt.retornaEstadoTituloBarcode();
		Assert.assertTrue(estatus[0]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 9 - Tap en el nombre del primer selectivo para desplegar el detalle
		 */
		addStep("Tap en el nombre del primer selectivo para desplegar el detalle");
		conteosInt.clickBtnPlus();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		/*
		 *  Paso 10 - Tap sobre el primer producto del selectivo
		 */
		addStep("Tap sobre el primer producto del selectivo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosInt.clickFirstSKU();

		/*
		 *  Paso 11 - Identificar el teórico en la pantalla de captura e ingresar el dato de físico de 10 unidades
		 */
		addStep("Identificar el teórico en la pantalla de captura e ingresar el dato de físico de 10 unidades");
		conteosInt.capturarConteo("entero", "10", false, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 12 - Tap en botón GUARDAR
		 */
		addStep("Tap en botón GUARDAR");
		conteosInt.clickBtnGuardarCto(true);

		/*
		 *  Paso 13 - Tap en botón GUARDAR y confirmar el guardado
		 */
		addStep("Tap en botón GUARDAR y confirmar el guardado");
		conteosInt.clickBtnGuardar(true);
		comunes.clickBtnAceptar(true);
		conteosInt.esperaSeOculteMensajeGuardado();

		/*
		 *  Paso 14 - Validar que se muestre correctamente los datos de teórico y físico del producto contabilizado en el detalle del preinventario
		 */
		addStep("Validar que se muestre correctamente los datos de teórico y físico del producto contabilizado en el detalle del preinventario");
		List<WebElement> teoricoFisico = conteosInt.getTeoricoFisico(0);
		for (int j = 0; j < teoricoFisico.size(); j++) {
			String txtValue = teoricoFisico.get(j).getText();
			if (j == 1) {
				System.out.println("TeoricoFront: " + txtValue);
				System.out.println("TeoricoModal: " + conteosInt.obtieneTeoricoEnMemoria());
				Assert.assertEquals(txtValue, conteosInt.obtieneTeoricoEnMemoria());
			}
			if (j == 2) {
				System.out.println("FisicoFront: " + txtValue);
				System.out.println("FisicoModal: " + conteosInt.obtieneFisicoEnMemoria());
				Assert.assertEquals(txtValue, conteosInt.obtieneFisicoEnMemoria());
			}
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 15 - Validar en Base de Datos
		 */
		addStep("Validar en Base de Datos la captura del conteo");
		String resultadoDB = conteosInt.generaStringRegistrosConteosBD(10);
		testCase.addTextEvidenceCurrentStep(resultadoDB);

		/*
		 *  Paso 16 - Ingrese un segundo conteo del mismo producto en el sistema (por ejemplo, 20 unidades) y guardar el preinventario
		 */
		addStep("Ingrese un segundo conteo del mismo producto en el sistema (por ejemplo, 20 unidades) y guardar el preinventario");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosInt.clickFirstSKU();
		conteosInt.capturarConteo("entero", "20", true, false);
		conteosInt.clickBtnGuardarCto(true);
		conteosInt.clickBtnGuardar(true);
		comunes.clickBtnAceptar(true);
		conteosInt.esperaSeOculteMensajeGuardado();

		/*
		 *  Paso 17 - Validar en Base de Datos
		 */
		addStep("Validar en Base de Datos la captura del conteo");
		resultadoDB = conteosInt.generaStringRegistrosConteosBD(30);
		testCase.addTextEvidenceCurrentStep(resultadoDB);

		/*
		 *  Paso 18 - Ingrese un tercer conteo del mismo producto en el sistema (por ejemplo, 30 unidades) y guardar el preinventario
		 */
		addStep("Ingrese un tercer conteo del mismo producto en el sistema (por ejemplo, 30 unidades) y guardar el preinventario");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosInt.clickFirstSKU();
		conteosInt.capturarConteo("entero", "30", true, false);
		conteosInt.clickBtnGuardarCto(true);
		conteosInt.clickBtnGuardar(true);
		comunes.clickBtnAceptar(true);
		conteosInt.esperaSeOculteMensajeGuardado();
		Thread.sleep(2000); //Para darle tiempo al back

		/*
		 *  Paso 19 - Validar en Base de Datos
		 */
		addStep("Validar en Base de Datos la captura del conteo");
		resultadoDB = conteosInt.generaStringRegistrosConteosBD(60);
		testCase.addTextEvidenceCurrentStep(resultadoDB);

		/*
		 *  Paso 20 - Validar en Base de Datos TODOS los conteos
		 */
		addStep("Validar en Base de Datos TODOS los conteos");
		resultadoDB = conteosInt.generaStringRegistrosConteosBD("10,30,60");
		testCase.addTextEvidenceCurrentStep(resultadoDB);


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
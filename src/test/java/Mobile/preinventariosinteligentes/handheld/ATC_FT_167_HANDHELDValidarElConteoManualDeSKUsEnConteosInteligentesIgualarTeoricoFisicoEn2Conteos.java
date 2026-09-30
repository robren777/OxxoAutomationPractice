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

public class ATC_FT_167_HANDHELDValidarElConteoManualDeSKUsEnConteosInteligentesIgualarTeoricoFisicoEn2Conteos
		extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n"
				+ "6. Haber seleccionado clasificación Proveedor por primera vez en la semana\n"
				+ "7. Haber creado plantillas";
	}

	@Override
	public String setTestDescription() {
		return "Conteos Propios-Iniciar Conteo-Validar teclado se muestre al ingresar cantidad de SKU en articulo capturado";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_167_HANDHELDValidarElConteoManualDeSKUsEnConteosInteligentesIgualarTeoricoFisicoEn2Conteos";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes
	 * MVP3:ATC_FT_167_HANDHELDValidarElConteoManualDeSKUsEnConteosInteligentesIgualarTeoricoFisicoEn2Conteos
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_167_HANDHELDValidarElConteoManualDeSKUsEnConteosInteligentesIgualarTeoricoFisicoEn2Conteos_test(
			HashMap<String, String> data) throws Exception {

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
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// Ejecuta el flujo de Asignación Automatica
		conteosInt.prerequisitoAsigAutomatica(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", 2,
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
		 * Paso 4 - Ingresar a Conteos inteligentes
		 */
		addStep("Ingresar a Conteos inteligentes");
		menus.clickBtnConteosInt(true);

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
		 * Paso 7 - Tap en botón ACEPTAR
		 */
		addStep("Tap en boton ACEPTAR");
		comunes.clickBtnAceptar(true);

		// Articulos por contar y espera a ingresar a detalle de preinventario
		comunes.clickBtnAceptarConteosFaltantes(false);

		/*
		 * Paso 8 - ? Validar que se muestre el detalle del preinventario indicando en
		 * el titulo SEMANA X (número de semana).XXXX(año en curso)
		 */
		addStep("? Validar que se muestre el detalle del preinventario indicando en el titulo SEMANA X (número de semana).XXXX(año en curso)");
		boolean[] estatus = conteosInt.retornaEstadoTituloBarcode();
		Assert.assertTrue(estatus[0]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 9 - Capturar un SKU valido dentro del cuadro de texto (INGRESA EL SKU DE
		 * UN ARTÍCULO)
		 */
		addStep("Capturar un SKU valido dentro del cuadro de texto (INGRESA EL SKU DE UN ARTÍCULO)");
		conteosInt.capturarSKU(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 10 - Dar clic en el botón -->l para aceptar el ingreso de sku
		 */
		addStep("Dar clic en el botón -->l para aceptar el ingreso de sku");
		conteosInt.clickEnterBarcode(true);

		/*
		 * Paso 11 - ? Validar que se muestre la pantalla de captura
		 */
		addStep("? Validar que se muestre la pantalla de captura");
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
		 * Paso 12 - Captura una cantidad de físico que sea menor al teórico existente
		 * del sku a contar
		 */
		addStep("Captura una cantidad de físico que sea menor al teórico existente del sku a contar");
		conteosProp.capturarFisico("menor", false, false, true);

		/*
		 * Paso 13 - ? Validar que el sistema realice el calculo para indicar la
		 * cantidad faltante resultante seguida del texto Faltantes
		 */
		addStep("? Validar que el sistema realice el calculo para indicar la cantidad faltante resultante seguida del texto Faltantes");
		String lblFaltantes = conteosProp.retornaEtiquetaFaltantesSobrantes();
		Assert.assertEquals(lblFaltantes, "Faltantes");
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 14 - Captura el mismo SKU del paso 11 dentro del cuadro de texto
		 * (INGRESA EL SKU DE UN ARTÍCULO)
		 */
		addStep("Captura el mismo SKU del paso 11 dentro del cuadro de texto (INGRESA EL SKU DE UN ARTÍCULO)");
		conteosInt.capturarSKU(1);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Dar clic en el botón --> para aceptar el ingreso de sku
		 */
		addStep("Dar clic en el botón -->l para aceptar el ingreso de sku");
		conteosInt.clickEnterBarcode(true);

		/*
		 * Paso 16 - ? Validar que se muestre la pantalla de captura
		 */
		addStep("? Validar que se muestre la pantalla de captura");
		boolean[] etqConteos1 = conteosProp.retornaEstadoElementosCapturaConteo();
		Assert.assertTrue(etqConteos1[0]);
		Assert.assertTrue(etqConteos1[1]);
		Assert.assertTrue(etqConteos1[2]);
		Assert.assertTrue(etqConteos1[3]);
		Assert.assertTrue(etqConteos1[4]);
		Assert.assertTrue(etqConteos1[5]);
		Assert.assertTrue(etqConteos1[6]);
		Assert.assertTrue(etqConteos1[7]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/*
		 * Paso 17 - Captura una cantidad de físico que permita igualar al teórico
		 * existente del sku a contar
		 */
		addStep("Captura una cantidad de fisico que permita igualar al teorico existente del sku a contar");
		conteosProp.capturarFisico(1, true, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 18 - ? Validar que el sistema no muestre campo faltantes ni sobrantes
		 */
		addStep("? Validar que el sistema no muestre campo faltantes ni sobrantes");
		boolean edoEtiqueta = conteosProp.etqFaltanteSobrantesNOVisible();
		Assert.assertTrue(edoEtiqueta);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 19 - Tap en botón GUARDAR
		 */
		addStep("Tap en botón  GUARDAR");
		conteosProp.clickBtnGuardarCto(true);

		/*
		 * Paso 20 - ? Validar que se posicione en el producto recién contabilizado y
		 * actualice el teórico y físico iguales indicado por el sistema
		 */
		addStep("? Validar que se posicione en el producto recién contabilizado y actualice el teórico y físico iguales indicado por el sistema");
		List<WebElement> teoricoFisico = conteosProp.getTeoricoFisico(0);
		for (int j = 0; j < teoricoFisico.size(); j++) {
			String txtValue = teoricoFisico.get(j).getText();
			if (j == 1) {
				Assert.assertEquals(txtValue, conteosProp.obtieneTeoricoEnMemoria());
			}
			if (j == 2) {
				Assert.assertEquals(txtValue, conteosProp.obtieneFisicoEnMemoria());
			}
			System.out.println("Texto: " + txtValue);
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
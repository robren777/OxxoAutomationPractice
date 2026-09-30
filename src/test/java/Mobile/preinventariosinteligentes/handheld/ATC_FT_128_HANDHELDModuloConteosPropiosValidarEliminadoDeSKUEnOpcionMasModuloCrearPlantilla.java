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

public class ATC_FT_128_HANDHELDModuloConteosPropiosValidarEliminadoDeSKUEnOpcionMasModuloCrearPlantilla
		extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n";

	}

	@Override
	public String setTestDescription() {
		return "Modulo Conteos Propios-Validar eliminado de SKU en opción + en módulo Crear plantilla";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_128_HANDHELDModuloConteosPropiosValidarEliminadoDeSKUEnOpcionMasModuloCrearPlantilla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_128_HANDHELDModuloConteosPropiosValidarEliminadoDeSKUEnOpcionMasModuloCrearPlantilla
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_128_HANDHELDModuloConteosPropiosValidarEliminadoDeSKUEnOpcionMasModuloCrearPlantilla_test(
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

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// FLUJO PRINCIPAL
		/***************************************************/
		/*
		 * Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENÚ PRINCIPAL de Oxxo al día ");
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 2 - Ingresar a la opción "Control"
		 */
		addStep("Ingresar a la opción \"Control\"");
		menus.clickBtnControl(true);

		/*
		 * Paso 3 - Ingresar a la opción "Preinventarios Inteligentes"
		 */
		addStep("Ingresar  a la opción \"Preinventarios Inteligentes\"");
		menus.clickBtnPreInvInt(true);

		/*
		 * Paso 4 - Ingresar a opción "Conteos propios"
		 */
		addStep("Ingresar a opción \"Conteos propios\"");
		menus.clickBtnConteosProp(true);

		/*
		 * Paso 5 - Seleccionamos la semana en curso
		 */
		addStep("Seleccionamos la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 * Paso 6 - Presionamos en "CONTINUAR"
		 */
		addStep("Presionamos en \"CONTINUAR\"");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 7 - Presionamos en crear nueva plantilla
		 */
		addStep("Presionamos en crear nueva plantilla");
		menus.clickBtnNuevaPlantilla(true);

		/*
		 * Paso 8 - Validamos que se muestre botón "+" en la parte inferior derecha de
		 * la pantalla
		 */
		addStep("Validamos que se muestre botón \"+\" en la parte inferior derecha de la pantalla");
		boolean[] btnPlusEnabled = conteosProp.retornaEstadoBotonPlus();
		Assert.assertTrue(btnPlusEnabled[0], "El botón + no se encuentra habilitado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 9 - Presionamos botón "+"
		 */
		addStep("Presionamos botón \"+\"");
		conteosProp.clickBtnPlus(true);

		/*
		 * Paso 10 - Validar que se muestre pantalla de inicio "Asignar un encargado"
		 * para creación de plantilla
		 */
		addStep("Validar que se muestre pantalla de inicio \"Asignar un encargado\" para creación de plantilla");
		String[] etiquetas = conteosProp.retornaEtiquetasPantallaAsignarEncargado();
		Assert.assertEquals(etiquetas[0], "Asignar un encargado",
				"Titulo incorrecto en la pantalla Asignar un encargado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Validar que se muestre etiqueta "Asignar un colaborador"
		 */
		addStep("Validar que se muestre etiqueta \"Asignar un colaborador\"");
		Assert.assertEquals(etiquetas[1], "Asigna un encargado al preinventario",
				"Etiqueta incorrecta en la pantalla Asignar un encargado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12 - Presionamos debajo de etiqueta para seleccionar colaborador
		 */
		addStep("Presionamos debajo de etiqueta para seleccionar colaborador");
		conteosProp.clickComboColaboradores(true);

		/*
		 * Paso 13 - Validar que se muestre un listado de colaboradores activos
		 */
		addStep("Validar que se muestre un listado de colaboradores activos");
		ResultSet rs = conteosProp.colaboradoresActivosBD();
		List<WebElement> colaboradores = conteosProp.listaColaboradores();
		colaboradores.remove(0);
		int idx = 0;
		try {
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					String colabApp = colaboradores.get(idx).getText();
					String colabBD = rs.getString(1);
					Assert.assertEquals(colabApp, colabBD);
					idx++;
				}
			} else {
				throw new AssertionError("No se encontraron registros en el resultSet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 14 - Seleccionamos colaborador del listado y validamos que el nombre del
		 * colaborador se muestre en el campo
		 */
		addStep("Seleccionamos colaborador del listado y validamos que el nombre del colaborador se muestre en el campo");
		conteosProp.seleccionarColaborador(0, true);

		/*
		 * Paso 15 - Validar que se muestre opciones de turno "Mañana", "Tarde", "Noche"
		 * + etiqueta "Define el turno en que se deberá hacer"
		 */
		addStep("Validar que se muestre opciones de turno \"Mañana\", \"Tarde\", \"Noche\" + etiqueta \"Define el turno en que se deberá hacer\"");
		String[] turnos = conteosProp.retornaEtiquetasTurnos();
		Assert.assertEquals(turnos[0], "Mañana", "Etiqueta incorrecta");
		Assert.assertEquals(turnos[1], "Tarde", "Etiqueta incorrecta");
		Assert.assertEquals(turnos[2], "Noche", "Etiqueta incorrecta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 16 - Seleccionar turno
		 */
		addStep("Seleccionar turno");
		conteosProp.seleccionarTurno("morning", true);

		/*
		 * Paso 17 - Validar que se ponga check en color amarillo sobre el turno
		 * seleccionado
		 */
		addStep("Validar que se ponga check en color amarillo sobre el turno seleccionado");
		boolean[] turnoCheck = conteosProp.retornaTurnoSeleccionado("morning");
		Assert.assertTrue(turnoCheck[0], "El turno no se encuentra seleccionado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 18 - Validar que el botón continuar se habilite
		 */
		addStep("Validar que el botón continuar se habilite");
		boolean[] btnContinuarEnabled = conteosProp.retornaBtnContinuarHabilitado();
		Assert.assertTrue(btnContinuarEnabled[0], "El boton Continuar no se encuentra habilitado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 19 - Presionar botón continuar
		 */
		addStep("Presionar botón continuar");
		conteosProp.clickBtnContinuar(true);

		/*
		 * Paso 20 - Validar que se muestre campo "AGREGAR UN NOMBRE A LA PLANTILLA"
		 */
		addStep("Validar que se muestre campo \"AGREGAR UN NOMBRE A LA PLANTILLA\"");
		boolean[] opciones = conteosProp.retornaEstadoElementosCrearNuevaPlantilla();
		Assert.assertTrue(opciones[0], "No se mostro el campo Agregar un nombre a la plantilla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 21 - Capturamos nombre de la plantilla ej "LALA "
		 */
		addStep("Capturamos nombre de la plantilla ej \"LALA\"");
		conteosProp.capturarNombrePlantilla("PlantillaATC128");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 22 - Validar que se muestre campo "INGRESA EL SKU DE UN ARTICULO"
		 */
		addStep("Validar que se muestre campo \"INGRESA EL SKU DE UN ARTICULO\"");
		Assert.assertTrue(opciones[1], "No se mostro el campo Ingresa el sku de un articulo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 23 - Capturamos un SKU ya sea escaneado o manualmente
		 */
		addStep("Capturamos un SKU ya sea escaneado o manualmente");
		conteosProp.capturarSKU(1, 0);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosProp.clickEnterBarcode(false);

		/*
		 * Paso 24 - Damos tab y validamos que se capture el artículo correctamente en
		 * el listado
		 */
		addStep("Damos tab y validamos que se capture el artículo correctamente en el listado");
		idx = 0;
		rs = conteosProp.obtieneSkusParaCaptura(1, 0);
		List<WebElement> skus = conteosProp.skusAgregados();
		try {
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					String skuBD = rs.getString(3);
					String skuApp = skus.get(idx).getText().substring(5);
					Assert.assertEquals(skuBD, skuApp);
					System.out.println("SKU BD: " + skuBD + ", SKU App: " + skuApp);
					idx++;
				}
			} else {
				throw new AssertionError("No se encontraron registros en el resultSet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		conteosProp.registraSKUS(3, 1, false, false);

		/*
		 * Paso 25 - Presionamos "x" en un artículo del listado
		 */
		addStep("Presionamos \"x\" en un artículo del listado");
		conteosProp.eliminaSKUS(3);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 26 - Presionar en la opción CANCELAR
		 */
		addStep("Presionar en la opción CANCELAR");
		comunes.clickBtnCancelar(true);

		/*
		 * Paso 27 - Presionamos "x" en un artículo del listado
		 */
		addStep("Presionamos \"x\" en un artículo del listado");
		String skuEliminado = conteosProp.obtieneSkuAEliminar(3); // --- Indice comienza en 1
		conteosProp.eliminaSKUS(2); // --- Indice comienza en 0
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 28 - Presionar en la opción ELIMINAR
		 */
		addStep("Presionar en la opción ELIMINAR");
		comunes.clickBtnAceptar(true);

		/*
		 * Paso 29 - Validar que se elimine el sku del listado
		 */
		addStep("Validar que se elimine el sku del listado");
		boolean resultado = conteosProp.retornaSKUEliminado(skuEliminado);
		Assert.assertTrue(resultado, "El SKU aun está presente en la pantalla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
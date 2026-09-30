package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ATC_FT_131_HANDHELDModuloConteosPropiosValidarOpcionGuardarAceptarEnModuloCrearPlantilla extends BaseExecution{

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
				+ "5. Haber iniciado sesión con usuario genérico: Asesor/Líder";
	}

	@Override
	public String setTestDescription() {
		return	  "Modulo Conteos Propios-Validar opción GUARDAR/ACEPTAR en opción \"+\" en módulo \"Crear plantilla\"";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_131_HANDHELDModuloConteosPropiosValidarOpcionGuardarAceptarEnModuloCrearPlantilla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_131_HANDHELDModuloConteosPropiosValidarOpcionGuardarAceptarEnModuloCrearPlantilla

	 * @autor Marcos Morales
	 * @date 18/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_131_HANDHELDModuloConteosPropiosValidarOpcionGuardarAceptarEnModuloCrearPlantilla_test (HashMap<String, String> data) throws Exception {

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
		 *  Paso 2 - Ingresar a la opción "Control"
		 */
		addStep("Ingresar a la opción \"Control\"");
		menus.clickBtnControl(true);

		/*
		 *  Paso 3 - Ingresar  a la opción "Preinventarios Inteligentes"
		 */
		addStep("Ingresar a la opción \"Preinventarios Inteligentes\"");
		menus.clickBtnPreInvInt(true);

		/*
		 *  Paso 4 - Ingresar a opción "Conteos propios"
		 */
		addStep("Ingresar a opción \"Conteos propios\"");
		menus.clickBtnConteosProp(true);

		/*
		 *  Paso 5 - Seleccionamos la semana en curso
		 */
		addStep("Seleccionamos la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 *  Paso 6 - Presionamos en "CONTINUAR"
		 */
		addStep("Presionamos en \"CONTINUAR\"");
		comunes.clickBtnContinuar(true);

		/*
		 *  Paso 7 - Presionamos en crear nueva plantilla
		 */
		addStep("Presionamos en crear nueva plantilla");
		menus.clickBtnNuevaPlantilla(true);

		/*
		 *  Paso 8 - Validamos que se muestre botón "+" en la parte inferior derecha de la pantalla
		 */
		addStep("Validamos que se muestre botón \"+\" en la parte inferior derecha de la pantalla");
		boolean[] btnPlusEnabled = conteosProp.retornaEstadoBotonPlus();
		Assert.assertTrue(btnPlusEnabled[0], "El botón + no se encuentra habilitado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 9 - Presionamos botón "+"
		 */
		addStep("Presionamos botón \"+\"");
		conteosProp.clickBtnPlus(true);

		/*
		 *  Paso 10 - Validar que se muestre pantalla de inicio "Asignar un encargado" para creación de plantilla
		 */
		addStep("Validar que se muestre pantalla de inicio \"Asignar un encargado\" para creación de plantilla");
		String[] etiquetas = conteosProp.retornaEtiquetasPantallaAsignarEncargado();
		Assert.assertEquals(etiquetas[0], "Asignar un encargado", "Titulo incorrecto en la pantalla Asignar un encargado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 11 - Validar que se muestre etiqueta "Asignar un colaborador"
		 */
		addStep("Validar que se muestre etiqueta \"Asignar un colaborador\"");
		Assert.assertEquals(etiquetas[1], "Asigna un encargado al preinventario", "Etiqueta incorrecta en la pantalla Asignar un encargado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 12 - Presionamos debajo de etiqueta para seleccionar colaborador
		 */
		addStep("Presionamos debajo de etiqueta para seleccionar colaborador");
		conteosProp.clickComboColaboradores(true);

		/*
		 *  Paso 13 - Validar que se muestre un listado de colaboradores activos
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 14 - Seleccionamos colaborador del listado y validamos que el nombre del colaborador se muestre en el campo
		 */
		addStep("Seleccionamos colaborador del listado y validamos que el nombre del colaborador se muestre en el campo");
		conteosProp.seleccionarColaborador(0, true);

		/*
		 *  Paso 15 - Validar que se muestre opciones de turno "Mañana", "Tarde", "Noche" + etiqueta "Define el turno en que se deberá hacer"
		 */
		addStep("Validar que se muestre opciones de turno \"Mañana\", \"Tarde\", \"Noche\" + etiqueta \"Define el turno en que se deberá hacer\"");
		String[] turnos = conteosProp.retornaEtiquetasTurnos();
		Assert.assertEquals(turnos[0], "Mañana", "Etiqueta incorrecta");
		Assert.assertEquals(turnos[1], "Tarde", "Etiqueta incorrecta");
		Assert.assertEquals(turnos[2], "Noche", "Etiqueta incorrecta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 16 - Seleccionar turno
		 */
		addStep("Seleccionar turno");
		conteosProp.seleccionarTurno("morning", true);

		/*
		 *  Paso 17 - Validar que se ponga check en color amarillo sobre el turno seleccionado
		 */
		addStep("Validar que se ponga check en color amarillo sobre el turno seleccionado");
		boolean[] turnoCheck = conteosProp.retornaTurnoSeleccionado("morning");
		Assert.assertTrue(turnoCheck[0], "El turno no se encuentra seleccionado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 18 - Validar que el botón continuar se habilite
		 */
		addStep("Validar que el botón CONTINUAR se habilite");
		boolean[] btnContinuarEnabled = conteosProp.retornaBtnContinuarHabilitado();
		Assert.assertTrue(btnContinuarEnabled[0], "El boton Continuar no se encuentra habilitado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 19 - Presionar botón continuar
		 */
		addStep("Presionar botón CONTINUAR");
		conteosProp.clickBtnContinuar(true);

		/*
		 *  Paso 20 - Validar que se muestre campo "AGREGAR UN NOMBRE A LA PLANTILLA"
		 */
		addStep("Validar que se muestre campo \"AGREGAR UN NOMBRE A LA PLANTILLA\"");
		boolean[] opciones = conteosProp.retornaEstadoElementosCrearNuevaPlantilla();
		Assert.assertTrue(opciones[0], "No se mostro el campo Agregar un nombre a la plantilla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 21 - Capturamos nombre de la plantilla ej "LALA"
		 */
		addStep("Capturamos nombre de la plantilla ej \"LALA\"");
		conteosProp.capturarNombrePlantilla("PlantillaQA-CP131");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 22 - Validar que se muestre campo "INGRESA EL SKU DE UN ARTICULO"
		 */
		addStep("Validar que se muestre campo \"INGRESA EL SKU DE UN ARTICULO\"");
		Assert.assertTrue(opciones[1], "No se mostro el campo Ingresa el sku de un articulo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 23 - Capturamos un SKU
		 */
		addStep("Capturamos un SKU");
		conteosProp.capturarSKU(1, 0);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosProp.clickEnterBarcode(false);

		/*
		 *  Paso 24 - Damos tab y validamos que se capture el artículo correctamente en el listado
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 25 - Validar que se habilite opción GUARDAR
		 */
		addStep("Validar que se habilite opción GUARDAR");
		boolean[] btnGuardarEnabled = conteosProp.retornaEstadoBotonGuardar();
		Assert.assertTrue(btnGuardarEnabled[0], "El boton Guardar no se encuentra habilitado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 26 - Presionamos el botón GUARDAR y validamos que se muestre mensaje
		 */
		addStep("Presionamos el botón GUARDAR y validamos que se muestre mensaje");
		conteosProp.clickBtnGuardar(true);

		/*
		 *  Paso 27 - Presionar en la opción ACEPTAR
		 */
		addStep("Presionar en la opción ACEPTAR");
		comunes.clickBtnAceptar(true);
		conteosProp.esperaCampoDeBusqueda();
		conteosProp.esperaSeOcultaMensajeGuardado();

		/*
		 *  Paso 28 - Validar que la plantilla creada se muestre en el listado de plantillas con el nombre ingresado, colaborador asignado, y en la sección del turno que pertenece
		 */
		addStep("Validar que la plantilla creada se muestre en el listado de plantillas con el nombre ingresado, colaborador asignado, y en la sección del turno que pertenece");
		List<Map<String, Object>> plantillasTurnosDB = conteosProp.obtienePlantillasPorTurnoBD();
		List<WebElement> plantillasTurnos = conteosProp.listaPlantillasPorTurno();
		String nomTurno = "";
		String nomPlantilla = "";
		int cont = 0;

		for (int i = 0; i < plantillasTurnos.size(); i++) {
			boolean existe = conteosProp.isElementPresent(plantillasTurnos, i, conteosProp.getNombreTurno());

			if (existe) {
				nomTurno = plantillasTurnos.get(i).findElement(conteosProp.getNombreTurno()).getText();
			} else {
				Map<String, Object> datoDB = plantillasTurnosDB.get(cont);
				String turnoBD = (String) datoDB.get("turno");
				String plantillaBD = (String) datoDB.get("plantilla");
				System.out.println("BD DATA: " + turnoBD + ", " + plantillaBD);

				nomPlantilla = plantillasTurnos.get(i).findElement(conteosProp.getNombrePlantilla()).getText();
				Assert.assertEquals(nomTurno, turnoBD, "Los registros no coinciden");
				Assert.assertEquals(nomPlantilla, plantillaBD, "Los registros no coinciden");
				System.out.println("APP DATA: " + nomTurno + ", " + nomPlantilla);
				cont++;
			}
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 29 - Validar en base de datos que la plantilla se haya guardado correctamente
		 */
		addStep("Validar en base de datos que la plantilla se haya guardado correctamente");
		int plantillaCreada = conteosProp.retornaCreacionDePlantillaBD("PlantillaQA-CP131");
		Assert.assertNotEquals(0, plantillaCreada, "No se guardo la plantilla en BD");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		String resultadoDB = conteosProp.generaStringCreacionPlantillaBD("PlantillaQA-CP131");
		testCase.addTextEvidenceCurrentStep(resultadoDB);


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import Mobile.preinventariosinteligentes.om.PlantillaTienda;
import Mobile.preinventariosinteligentes.om.RolesInteligentes;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_001_HANDHELDConteosInteligentesSinPlantillaDeTiendaCreadaPlanograma extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n"
				+ "6. Haber seleccionado clasificación Proveedor por primera vez en la semana\n";
	}

	@Override
	public String setTestDescription() {
		return "Conteos Inteligentes sin plantilla de tienda creada-Planograma";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_HANDHELDConteosInteligentesSinPlantillaDeTiendaCreadaPlanograma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_001_HANDHELDConteosInteligentesSinPlantillaDeTiendaCreadaPlanograma
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_HANDHELDConteosInteligentesSinPlantillaDeTiendaCreadaPlanograma_test(
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
		PlantillaTienda plantillaTda = new PlantillaTienda(testCase, appiumUtil, this);
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, appiumUtil, this);
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// Seleccionar Clasificacion
		conteosInt.prerequisitoClasificacion(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", false);

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
		 * Paso 7 - ? Validar que se muestre el mensaje Paso 1 de 2 No hay una plantilla
		 * de tienda creada todavía
		 */
		addStep("Validar que se muestre el mensaje Paso 1 de 2 No hay una plantilla de tienda creada todavia");
		String[] msjPasos = rolesInt.retornaMensajePasos(true);
		Assert.assertEquals(msjPasos[0], "Paso 1 de 2\n" + "No hay una plantilla de tienda creada todavía.");
		Assert.assertEquals(msjPasos[1],
				"Se creará en automático la asignación de turnos a esta clasificación seleccionada.");

		/*
		 * Paso 8 - Tap en botón CREAR PLANTILLA
		 */
		addStep("Tap en boton CREAR PLANTILLA");
		comunes.clickBtnAceptar(true);

		/*
		 * Paso 9 - Tap en AÑADIR COLABORADOR
		 */
		addStep("Tap en AÑADIR COLABORADOR");
		plantillaTda.agregarColaborador(true);

		/*
		 * Paso 10 - Seleccionar 3 colaboradores y asignarles turnos a cada uno
		 */
		addStep("Seleccionar 3 colaboradores y asignarles turnos a cada uno");
		plantillaTda.seleccionarColaborador(3, false);
		plantillaTda.seleccionarDiaTurno(true);

		/*
		 * Paso 11 - Tap en botón Guardar
		 */
		addStep("Tap  en boton Guardar");
		plantillaTda.clickBtnGuardarPlantilla(true);

		/*
		 * Paso 12 - ? Validar que se muestre el mensaje Paso 2 de 2 Asignación de Roles
		 * Se creará la asignación automática de roles a esta plantilla seleccionada
		 */
		addStep("Validar que se muestre el mensaje Paso 2 de 2 Asignacion de Roles Se creara la asignacion automatica de roles a esta plantilla seleccionada");
		msjPasos = rolesInt.retornaMensajePasos(true);
		Assert.assertEquals(msjPasos[0], "Paso 2 de 2\n" + "Asignación de roles");
		Assert.assertEquals(msjPasos[1], "Se creará la asignación automática de roles a esta plantilla seleccionada.");

		/*
		 * Paso 13 - Tap en botón CREAR ASIGNACIONES
		 */
		addStep("Tap en boton CREAR ASIGNACIONES");
		comunes.clickBtnAceptar(true);

		/*
		 * Paso 14 - ? Validar que se muestre la vista preliminar de las asignaciones
		 * generadas automáticamente
		 */
		addStep("Validar que se muestre la vista preliminar de las asignaciones generadas automaticamente");
		comunes.esperaSeOculteLoading();
		ResultSet resultSet = rolesInt.obtieneRolesAsignadosBD();
		try {
			while (resultSet.next()) {
				String colaborador = resultSet.getString(2);
				String dia = resultSet.getString(3);
				String turno = resultSet.getString(5);

				System.out.println("Colaborador: " + colaborador + ", Dia: " + dia + ", Turno: " + turno);
				boolean existeDia = rolesInt.validaDia(dia);

				if (existeDia) {
					boolean existeColab = rolesInt.validaColaborador(colaborador);
					Assert.assertTrue(existeColab);
					System.out.println("Se valido al colaborador: " + colaborador);
				} else {
					rolesInt.scrollToDiaEnElCalendario(dia);
					boolean existeColab = rolesInt.validaColaborador(colaborador);
					Assert.assertTrue(existeColab);
					System.out.println("Se valido al colaborador: " + colaborador);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 15 - Tap en Botón CONTINUAR
		 */
		addStep("Tap en Boton CONTINUAR");
		rolesInt.clickBtnContinuar(true);

		/*
		 * Paso 16 - Tap en ACEPTAR
		 */
		addStep("Tap en ACEPTAR");
		comunes.clickBtnAceptar(true);
		comunes.clickBtnAceptarConteosFaltantes(true); // Articulos por contar

		/*
		 * Paso 17 - Validar que cada grupo cuente con su asignación de rol
		 */
		addStep("Validar que cada grupo cuente con su asignacion de rol");
		boolean[] estatus = conteosInt.retornaEstadoTituloBarcode();
		Assert.assertTrue(estatus[0]);
		int skusBD = conteosInt.totalSKUSAsignados(conteosInt.obtieneGruposAsignadosBD());
		int skusApp = conteosInt.obtieneTotalSKUSenPantalla();
		Assert.assertEquals(skusApp, skusBD);
		System.out.println("SKUS BD: " + skusBD + ", SKUS App: " + skusApp);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}

}
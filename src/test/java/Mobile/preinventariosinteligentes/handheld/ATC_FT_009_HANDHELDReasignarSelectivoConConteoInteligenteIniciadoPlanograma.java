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
import Mobile.preinventariosinteligentes.om.RolesInteligentes;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_009_HANDHELDReasignarSelectivoConConteoInteligenteIniciadoPlanograma extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Ambientación de equipo en Reléase 2.302.0.0\n" + "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n" + "4. Acceso a Internet\n"
				+ "5. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "6. Haber seleccionado clasificación Planograma por primera vez en la semana\n"
				+ "7. Haber realizado la asignacion automatica desde Roles Inteligentes";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Descripcion del Caso de Prueba";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alejandro Rodriguez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_HANDHELDReasignarSelectivoConConteoInteligenteIniciadoPlanograma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_009_HANDHELDReasignarSelectivoConConteoInteligenteIniciadoPlanograma
	 * 
	 * @autor Alejandro Rodriguez
	 * @date 17/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_009_HANDHELDReasignarSelectivoConConteoInteligenteIniciadoPlanograma_test(
			HashMap<String, String> data) throws Exception {

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
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, appiumUtil, this);
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// Asignación Automática desde Roles Inteligentes
		rolesInt.prerequisitoAsigAutomatica(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", 2,
				false);

		// Iniciar el conteo del selectivo a reasignar
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		conteosInt.prerequisitoConteoSelectivo(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "2", "5", false);

		// FLUJO PRINCIPAL
		/***************************************************/
		/*
		 * Paso 1 - Ingresar a MENÚ PRINCIPAL de Oxxo al día
		 */
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		addStep("Ingresar a MENÚ PRINCIPAL de Oxxo al día");
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 2 - Ingresar en opción Control
		 */
		addStep("Ingresar en opción Control");
		menus.clickBtnControl(true);

		/*
		 * Paso 3 - Ingresar en opción Preinventarios Inteligentes
		 */
		addStep("Ingresar en opción Preinventarios Inteligentes");
		menus.clickBtnPreInvInt(true);

		/*
		 * Paso 4 - Ingresa en opcion Asignacion de roles
		 */
		addStep("Ingresar en opción Asignación de Roles");
		menus.clickBtnAsigRoles(true);

		/*
		 * Paso 5 - Ingresar en Roles Inteligentes
		 * 
		 */
		addStep("Ingresar en Roles Inteligentes");
		menus.clickBtnRolesInt(true);

		/*
		 * Paso 6 - Seleccionar la semana en curso
		 */
		addStep("Seleccionar la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 * Paso 7 - Dar clic en el botón CONTINUAR
		 */
		addStep("Dar clic en el botón CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 8 - Realizar Long-Press en un recuadro de un colaborador-selectivo
		 */
		addStep("Realizar Long-Press en un recuadro de un colaborador-selectivo");
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
		rolesInt.clickLargoColaborador(true);

		/*
		 * Paso 9 - ?Validar que se muestre la opción ASIGNA UN ROL
		 */
		addStep("?Validar que se muestre la opción ASIGNA UN ROL");
		boolean reasignar = rolesInt.retornaEstadoOpcionReasignar();
		Assert.assertTrue(reasignar);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 10 - Tap en opción SELECCIONA AL ENCARGADO
		 */
		addStep("Tap en opción SELECCIONA AL ENCARGADO");
		rolesInt.clickCbRoles(true);

		/*
		 * Paso 11 - Seleccionar al encargado que se va reasignar el selectivo
		 */
		addStep("Seleccionar al encargado que se va reasignar el selectivo ");
		rolesInt.seleccionarRol(true);

		/*
		 * Paso 12 - Seleccionar la opción "SELECCIONA EL PLANOGRAMA"
		 */
		addStep("Seleccionar la opción \"SELECCIONA EL PLANOGRAMA");
		rolesInt.clickCbSelectivos(true);

		/*
		 * Paso 13 - Selecciona el Planograma a reasignar
		 */
		addStep("Selecciona el Planograma a reasignar ");
		rolesInt.seleccionarSelectivo(true);

		/*
		 * Paso 14 - Tap en botón CONTINUAR
		 */
		addStep("Tap en botón CONTINUAR");
		rolesInt.clickBtnContinuarCambioSelectivo(true);

		/*
		 * Paso 15 - Validar el switcheo de los selectivos de colaborador a colaborador
		 */
		addStep("Validar el switcheo de los selectivos de colaborador a colaborador");
		comunes.esperaSeOculteMensajeToast("Re-asignación aplicada con éxito");
		String selectivoOrg = rolesInt.selectivoOriginal(rolesInt.obtieneSelectivoOriginalBD());
		String selectivoNvo = rolesInt.selectivoNuevo(rolesInt.obtieneNuevoSelectivoBD());
		Assert.assertNotEquals(selectivoOrg, selectivoNvo, "No se aplico el cambio de selectivos");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
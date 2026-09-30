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

public class ATC_FT_002_HHConteosInteligentesConPlantillaDeTiendaCreadaPreviamenteOpcionSIPlanograma extends BaseExecution{

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
				+ "6. Haber seleccionado clasificación Planograma por primera vez en la semana\r\n"
				+ "7. Haber creado plantillas";
	}

	@Override
	public String setTestDescription() {
		return	  "Conteos inteligentes con plantilla de tienda creada previamente opción SI-Planograma";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_HHConteosInteligentesConPlantillaDeTiendaCreadaPreviamenteOpcionSIPlanograma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_002_HHConteosInteligentesConPlantillaDeTiendaCreadaPreviamenteOpcionSIPlanograma

	 * @autor Marcos Morales
	 * @date 17/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_002_HHConteosInteligentesConPlantillaDeTiendaCreadaPreviamenteOpcionSIPlanograma_test (HashMap<String, String> data) throws Exception {

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
		PlantillaTienda plantillaTda = new PlantillaTienda(testCase, appiumUtil, this);
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, appiumUtil, this);
		ConteosInteligentes conteosInt = new ConteosInteligentes(testCase, appiumUtil, this);


		//PRECONDICIONES
		/***************************************************/
		//Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		//Seleccionar Clasificacion
		conteosInt.prerequisitoClasificacion(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", false);

		//Crear Plantillas de Colaboradores
		comunes.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		plantillaTda.prerequisitoPlantillasColaboradores(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 3, false);


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
		 *  Paso 4 - Ingresar a Conteos inteligentes
		 */
		addStep("Ingresar a Conteos Inteligentes");
		menus.clickBtnConteosInt(true);

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
		 *  Paso 7 - ? Validar que se muestre el mensaje ¿Deseas continuar con la plantilla realizada previamente?
		 */
		addStep("? Validar que se muestre el mensaje ¿Deseas continuar con la plantilla realizada previamente?");
		String mensaje = rolesInt.obtenerMensaje();
		Assert.assertEquals(mensaje, "¿Deseas continuar con la plantilla realizada previamente?");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 8 - Tap en opción SI
		 */
		addStep("Tap en opción SI");
		comunes.clickBtnAceptar(true);

		/*
		 *  Paso 9 - ? Validar que se muestre la vista preliminar de las asignaciones generadas automáticamente
		 */
		addStep("? Validar que se muestre la vista preliminar de las asignaciones generadas automáticamente");
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 10 - Tap en Botón CONTINUAR 
		 */
		addStep("Tap en Botón CONTINUAR");
		rolesInt.clickBtnContinuar(true);

		/*
		 *  Paso 11 - Tap en ACEPTAR
		 */
		addStep("Tap en ACEPTAR");
		comunes.clickBtnAceptar(true);
		comunes.clickBtnAceptarConteosFaltantes(true);	//Articulos por contar

		/*
		 *  Paso 12 - ? Validar que cada grupo cuente con su asignación de rol
		 */
		addStep("? Validar que cada grupo cuente con su asignación de rol");
		conteosInt.esperaBotonFinalizar();
		int skusBD = conteosInt.totalSKUSAsignados(conteosInt.obtieneGruposAsignadosBD());
		int skusApp = conteosInt.obtieneTotalSKUSenPantalla();
		Assert.assertEquals(skusApp, skusBD);
		System.out.println("SKUS BD: " + skusBD + ", SKUS App: " + skusApp);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
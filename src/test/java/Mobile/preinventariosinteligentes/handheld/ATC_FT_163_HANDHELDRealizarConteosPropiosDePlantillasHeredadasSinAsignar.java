package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ATC_FT_163_HANDHELDRealizarConteosPropiosDePlantillasHeredadasSinAsignar extends BaseExecution{

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\r\n"
				+ "2. Versión Android 11\r\n"
				+ "3. Dispositivo HANDHELD \r\n"
				+ "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "5. Acceso a Internet\r\n"
				+ "6. Contar con plantillas heredadas de la semana pasada";
	}

	@Override
	public String setTestDescription() {
		return	  "Realizar conteos propios de plantillas heredadas(sin asignar)";
	}

	@Override
	public String setTestDesigner() {
		return "Marcos Morales";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_163_HANDHELDRealizarConteosPropiosDePlantillasHeredadasSinAsignar";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3: ATC_FT_163_HANDHELDRealizarConteosPropiosDePlantillasHeredadasSinAsignar

	 * @autor Marcos Morales
	 * @date 19/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_163_HANDHELDRealizarConteosPropiosDePlantillasHeredadasSinAsignar_test (HashMap<String, String> data) throws Exception {

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
		comunes.limpiarTablasMVP3("anterior");
		comunes.limpiarTablasMVP3("actual");

		//Identifica si existe registro de una semana anterior
		conteosProp.validaSemanaAnterior();

		//Crear Plantillas de SKUS
		conteosProp.prerequisitoPlantillasSKUS(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), 1, 8, "anterior", false, false);

		//Actualiza registros en BD a una Semana Anterior
		Thread.sleep(3000);
		conteosProp.actualizaPlantillasSemanaAnterior();


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
		 *  Paso 8 - Selecciona una plantilla precargada de la semana anterior (heredada)
		 */
		addStep("Selecciona una plantilla precargada de la semana anterior (heredada)");
		conteosProp.seleccionarPlantilla(1, true);

		/*
		 *  Paso 9 - ? Validar que se muestre la ventana para realizar la asignación
		 */
		addStep("? Validar que se muestre la ventana para realizar la asignación");
		String[] etiquetas = conteosProp.retornaEtiquetasModalAsignarEncargado();
		Assert.assertEquals(etiquetas[0], "Asigna colaborador para realizar conteo", "Titulo incorrecto en el modal Asignar un encargado");
		Assert.assertEquals(etiquetas[1], "SELECCIONA AL ENCARGADO", "Etiqueta incorrecta en el modal Asignar un encargado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 10 - Realizar la asignación de la plantilla a un colaborador en el turno de su elección
		 */
		addStep("Realizar la asignación de la plantilla a un colaborador en el turno de su elección");
		conteosProp.seleccionarEncargadoTurno(1, 1, true);

		/*
		 *  Paso 11 - Tap en botón CONTINUAR
		 */
		addStep("Tap en botón CONTINUAR");
		comunes.clickBtnAceptar(true);

		/*
		 *  Paso 12 - ? Validar que se muestre el detalle de la plantilla con la nueva asignación realizada previamente
		 */
		addStep("? Validar que se muestre el detalle de la plantilla con la nueva asignación realizada previamente");
		conteosProp.esperaCampoIngresarSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		conteosProp.clickNombrePlantilla();

		/*
		 *  Paso 13 - ? Validar que la plantilla no cuente con registros de conteos realizados o heredados de la semana anterior
		 */
		addStep("? Validar que la plantilla no cuente con registros de conteos realizados o heredados de la semana anterior");
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
		 *  Paso 14 - Realizar el conteo físico de 3 skus de la plantilla donde el físico sea menor al teórico existente en sistema
		 */
		addStep("Realizar el conteo físico de 3 skus de la plantilla donde el físico sea menor al teórico existente en sistema");
		conteosProp.registrarConteoMenorPorPlantilla(3, 0, false, false, false);
		comunes.esperaSeOculteMensajeToast("Artículo contabilizado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 15 - Realizar el conteo físico de 5 skus de la plantilla donde el físico sea mayor al teórico existente en sistema
		 */
		addStep("Realizar el conteo físico de 5 skus de la plantilla donde el físico sea mayor al teórico existente en sistema");
		conteosProp.registrarConteoMayorPorPlantilla(5, 3, false, false, false);
		comunes.esperaSeOculteMensajeToast("Artículo contabilizado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 16 - Tap en botón  GUARDAR
		 */
		addStep("Tap en botón  GUARDAR");
		conteosProp.clickBtnGuardarPreInv(true);

		/*
		 *  Paso 17 - Realizar la confinación del guardado, Tap en botón GUARDAR
		 */
		addStep("Realizar la confirmación del guardado, Tap en botón GUARDAR");
		comunes.clickBtnAceptar(true);;

		/*
		 *  Paso 18 - Validar que el sistema complete el "Conteo guardado con éxito" y actualice en BD el guardado de la plantilla correctamente
		 */
		addStep("Validar que el sistema complete el \"Conteo guardado con éxito\" y actualice en BD el guardado de la plantilla correctamente");
		String mensaje = conteosProp.obtenerMensajeGuardado();
		Assert.assertEquals(mensaje, "Conteo guardado con éxito");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 *  Paso 19 - ? Validar que la aplicación muestre correctamente el calculo de los Totales (Faltantes, Sobrantes) con sus respectivos montos y el resultado
		 */
		addStep("? Validar que la aplicación muestre correctamente el calculo de los Totales (Faltantes, Sobrantes) con sus respectivos montos y el resultado");
		Map<String, String> footerValues = conteosProp.obtieneFaltantesSobrantesApp();
		String faltantes = "";
		String faltantesM = "";
		String sobrantes = "";
		String sobrantesM = "";

		ResultSet rs = conteosProp.obtieneFaltantesSobrantesBD("P");
		
		try {
			if (rs.next()) {
				faltantes = rs.getString(1);
				faltantesM = rs.getString(2);
				sobrantes = rs.getString(3);
				sobrantesM = rs.getString(4);
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta del método: obtieneFaltantesSobrantesBD" );
			}
			Assert.assertEquals("Total Faltantes : ", footerValues.get("valor00"));
			Assert.assertEquals(footerValues.get("valor01"), faltantes);
			Assert.assertEquals(footerValues.get("valor02"), faltantesM);
			Assert.assertEquals("Total Sobrantes : ", footerValues.get("valor10"));
			Assert.assertEquals(footerValues.get("valor11"), sobrantes);
			Assert.assertEquals(footerValues.get("valor12"), sobrantesM);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		//CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("anterior");
		comunes.limpiarTablasMVP3("actual");
	}
}
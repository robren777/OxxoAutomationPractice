package Mobile.preinventariosinteligentes.handheld;

import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosFront;
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import Mobile.preinventariosinteligentes.om.ModalConteos;
import Mobile.preinventariosinteligentes.om.RolesInteligentes;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_169_HANDHELDValidarTotalesDeFaltanteYSobranteEnConteoInteligente extends BaseExecution {

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "Prerequisitos:\r\n" + "1. Ambientación de equipo en Reléase 2.302.0.0\r\n" + "2. Versión Android 11\r\n"
				+ "3. Dispositivo HH \r\n" + "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\r\n"
				+ "5. Acceso a Internet\r\n" + "6. Contar con una plantilla de tienda creada";
	}

	@Override
	public String setTestDescription() {
		return "Validar totales de faltante y sobrante en conteo inteligente";
	}

	@Override
	public String setTestDesigner() {
		return "Lizbeth Hernandez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_169_HANDHELDValidarTotalesDeFaltanteYSobranteEnConteoInteligente";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Preinventarios Inteligentes MVP3:
	 * ATC_FT_169_HANDHELDValidarTotalesDeFaltanteYSobranteEnConteoInteligente
	 * 
	 * @autor Lizbeth Hernandez
	 * @date 16/12/2024
	 */

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_169_HANDHELDValidarTotalesDeFaltanteYSobranteEnConteoInteligente_test(
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
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, appiumUtil, this);

		// PRECONDICIONES
		/***************************************************/
		// Limpieza de tablas
		comunes.limpiarTablasMVP3("actual");

		// Asignación Automática desde Roles Inteligentes
		rolesInt.prerequisitoAsigAutomatica(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("appPackage"), "planograma", 2,
				false);

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
		 * Paso 4 - Ingresar a Conteos inteligentes
		 */
		addStep("Ingresar a Conteos Inteligentes");
		menus.clickBtnConteosInt(true);

		/*
		 * Paso 5 - Seleccionar check de la semana en curso
		 */
		addStep("Seleccionar check de la semana en curso");
		comunes.seleccionarSemana(true);

		/*
		 * Paso 6 - Tap en botón CONTINUAR
		 */
		addStep("Tap en botón CONTINUAR");
		comunes.clickBtnContinuar(true);

		/*
		 * Paso 7 - Tap en botón ACEPTAR
		 */
		addStep("Tap en botón ACEPTAR");
		comunes.clickBtnAceptar(true);

		// Articulos por contar
		comunes.clickBtnAceptarConteosFaltantes(false);

		/*
		 * Paso 8 - Validar que se muestre el detalle del preinventario indicando en el
		 * titulo SEMANA X (número de semana).XXXX(año en curso)
		 */
		addStep("Validar que se muestre el detalle del preinventario indicando en el titulo SEMANA X (número de semana).XXXX(año en curso)");
		boolean[] estatus = conteosInt.retornaEstadoTituloBarcode();
		Assert.assertTrue(estatus[0]);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 9 - Dar clic sobre el selectivo a contar
		 */
		addStep("Dar clic sobre el selectivo a contar");
		conteosInt.clickBtnPlus();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 10 - Realizar el conteo con faltantes de 3 productos listados en el
		 * preinventario
		 */
		addStep("Realizar el conteo con faltantes de 3 productos listados en el preinventario");
		conteosInt.registrarConteoMenor(3, 0, "entero", false, true, false);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 11 - Realizar el conteo con sobrantes de 3 productos listados en el
		 * preinventario (diferentes a los del paso 11)
		 */
		addStep("Realizar el conteo con sobrantes de 3 productos listados en el preinventario (diferentes a los del paso 11)");
		conteosInt.registrarConteoMayor(3, 3, "entero", false, true, false);
		comunes.esperaSeOculteMensajeToast("Artículo Contabilizado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 12- Tap en botón Guardar
		 */
		addStep("Tap en botón Guardar");
		conteosInt.clickBtnGuardar(false);
		comunes.clickBtnAceptar(true); // Confirmacion
		conteosInt.esperaSeOculteMensajeGuardado();

		/*
		 * Paso 13 - Validar que se actualice la cantidad en los campos físico y teorico
		 * de los skus contabilizados
		 */
		addStep("Validar que se actualice la cantidad en los campos físico y teorico de los skus contabilizados");
		List<Map<String, Object>> conteosModal = ModalConteos.getConteos();
		List<Map<String, Object>> conteosFront = ConteosFront.getConteos();
		for (int i = 0; i < conteosModal.size(); i++) {
			Map<String, Object> modal = conteosModal.get(i);
			Map<String, Object> front = conteosFront.get(i);
			String skuModal = (String) modal.get("sku");
			String teoricoModal = (String) modal.get("teorico");
			String fisicoModal = (String) modal.get("fisico");
			String skuFront = (String) front.get("sku");
			String teoricoFront = (String) front.get("teorico");
			String fisicoFront = (String) front.get("fisico");
			Assert.assertEquals(skuModal, skuFront);
			Assert.assertEquals(teoricoModal, teoricoFront);
			Assert.assertEquals(fisicoModal, fisicoFront);
			System.out.println("SKU Modal: " + skuModal + ", Teorico Modal: " + teoricoModal + ", Fisico Modal: " + fisicoModal);
			System.out.println("SKU Front: " + skuFront + ", Teorico Front: " + teoricoFront + ", Fisico Front: " + fisicoFront);
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 14 - Validar que se haga la actualizacion haciendose la sumatoria en los
		 * campos de totales de sobrante y faltante (Cantidad de articulos y monto
		 * total)
		 */
		addStep("Validar que se haga la actualizacion haciendose la sumatoria en los campos de totales de sobrante y faltante (Cantidad de articulos y monto total)");
		Map<String, String> footerValues = conteosProp.obtieneFaltantesSobrantesApp();
		String faltantes = "";
		String faltantesM = "";
		String sobrantes = "";
		String sobrantesM = "";

		ResultSet rs = conteosProp.obtieneFaltantesSobrantesBD("I");

		try {
			if (rs.next()) {
				faltantes = rs.getString(1);
				faltantesM = rs.getString(2);
				sobrantes = rs.getString(3);
				sobrantesM = rs.getString(4);
			} else {
				throw new AssertionError(
						"No se encontraron registros al ejecutar la consulta del método: obtieneFaltantesSobrantesBD");
			}
			Assert.assertEquals("Total Faltantes : ", footerValues.get("valor00"));
			Assert.assertEquals(footerValues.get("valor01"), faltantes);
			Assert.assertEquals(footerValues.get("valor02"), faltantesM);
			Assert.assertEquals("Total Sobrantes : ", footerValues.get("valor10"));
			Assert.assertEquals(footerValues.get("valor11"), sobrantes);
			Assert.assertEquals(footerValues.get("valor12"), sobrantesM);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// CIERRE
		/***************************************************/
		comunes.finalizarApp(data.get("appPackage"));
		comunes.limpiarTablasMVP3("actual");
	}
}
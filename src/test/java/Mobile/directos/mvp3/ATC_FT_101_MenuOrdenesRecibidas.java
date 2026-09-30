package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_101_MenuOrdenesRecibidas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_101_CasoPrueba101_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", true); // Se debe de dejar esta opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto
		String url = "http://127.0.0.1:4723";

		// =======================================Cerrar Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		// =======================================Inicio Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		Login login = new Login(testCase, appiumUtil, this);
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
		Thread.sleep(5000);

		// =========================================Inicio Ejecucion=========================================================//
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		/*
		 * paso 1: Ingresar a la app "Oxxo al día" en el Menú Excelencia Operativa/Herramienta de compra/Cometa
		 */
		addStep("Ingresar a la app \"Oxxo al día\" en el Menú Excelencia Operativa/Herramienta de compra/Cometa");
		OxxoAlDia.ingresarModuloHerramientas();
		
		
		/*
		 * paso 2: Seleccionar la opción “Verificación de Mercancía Pedidos Proveedor Directo”
		 */
		addStep("Seleccionar la opción “Verificación de Mercancía Pedidos Proveedor Directo”");
		OxxoAlDia.opcionVerificacionMercancia();
		
		/*
		 * paso 3: Validar que nos muestre la pantalla “PROVEEDORES DIRECTOS” con las opciones 
		 * Búsqueda pedidos Precargados
		 * Leer QR para Preventa o Pedido
		 * Generación de herramienta de compra
		 * Órdenes de compra recibidas
		 */
		addStep("Validar que nos muestre la pantalla “PROVEEDORES DIRECTOS” con las opciones \r\n"
				+ "Búsqueda pedidos Precargados \r\n"
				+ "Leer QR para Preventa o Pedido \r\n"
				+ "Generación de herramienta de compra \r\n"
				+ "Órdenes de compra recibidas ");
		boolean validacion = OxxoAlDia.pantallaProveedoresDirectos();
		assertTrue(validacion,"Error, la pantalla de proveedores directos no se mestra correctamente");
		
		/*
		 * paso 4: Validar que se encuentre la opción “Órdenes de compra recibidas”
		 * paso 5: Verificar que este habilito el botón 
		 */
		addStep("Validar que se encuentre la opción “Órdenes de compra recibidas” "
				+ "Verificar que este habilito el botón ");
		validacion = OxxoAlDia.validarOpcionOrdenesCompraRecibidas();
		assertTrue(validacion, "Error, no se encutra el boton [Órdenes de compra recibidas]");
		
	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-177-Menú “Órdenes de compra recibidas”
	 * Desc: Validar que esté disponible la opción para visualizar la tira de entrega de las recepciones realizadas en el menú “Órdenes de compra recibidas”
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 28/10/2024
	 */


	@Override
	public String setTestFullName() {
		return "ATC_FT_101_MenuOrdenesRecibidas";
	}

	@Override
	public String setTestDescription() {
		return "Validar que esté disponible la opción para visualizar la tira de "
				+ "entrega de las recepciones realizadas en el menú “Órdenes de compra recibidas”";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.-Contar con la aplicación más reciente de Oxxo al día";
	}

	@Override
	public void beforeTest() {

	}

}

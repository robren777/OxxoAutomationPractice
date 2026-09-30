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

public class ATC_FT_102_PantallaOrdenesRecibidas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_102_PantallaOrdenesRecibidas_test(HashMap<String, String> data) throws Exception {
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
		 * paso 1: Ingresar a la app "Oxxo al día" en el Menú Excelencia Operativa/Herramienta de compra/Cometa/Verificación de Mercancía Pedidos Proveedores Directos
		 */
		addStep("Ingresar a la app \"Oxxo al día\" en el Menú Excelencia Operativa/Herramienta de compra/Cometa/Verificación de Mercancía Pedidos Proveedores Directos");
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		
		/*
		 * paso 2: Validar que nos muestre la pantalla “PROVEEDORES DIRECTOS” con las opciones 
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
		 * paso 3: Seleccionar la opción “Órdenes de compra recibidas”
		 */
		addStep("Seleccionar la opción [Órdenes de compra recibidas]");
		OxxoAlDia.opcionOrdenesCompraRecibidas();
		
		/*
		 * paso 4: Validar que se muestre la pantalla “ÓRDENES DE COMPRA RECIBIDAS” 
		 * icon ⬅️(Regresar)
		 * título ÓRDENES DE COMPRA RECIBIDAS
		 * icon  i (Tutoriales)
		 * BÚSQUEDA POR:
		 * Radio button Número de Pedido
		 * Radio button Proveedor
		 */
		addStep("Validar que se muestre la pantalla [ÓRDENES DE COMPRA RECIBIDAS] \r\n"
				+ "icon ⬅️(Regresar) \r\n"
				+ "título ÓRDENES DE COMPRA RECIBIDAS \r\n"
				+ "icon  i (Tutoriales) \r\n"
				+ "BÚSQUEDA POR: \r\n"
				+ "Radio button Número de Pedido \r\n"
				+ "Radio button Proveedor ");
		validacion = OxxoAlDia.pantallaOrdenesCompraRecibidas();
		assertTrue(validacion, "error, la pantalla no se muestra de manera correcta");
		
		/*
		 * paso 5: Presionar el botón Tutoriales representado con el icono “i”
		 */
		addStep("Presionar el botón Tutoriales representado con el icono [i]");
		OxxoAlDia.opcionTutoriales();
		
		
		/*
		 * paso 6: Validar que se muestre el mensaje informativo:
		 *  En este módulo podrás visualizar las órdenes de compra recibidas de cada proveedor. 
		 *  Las podrás consultar por número de proveedor o por número de orden de compra. 
		 *  [ACEPTAR] 
		 */
		addStep("Validar que se muestre el mensaje informativo: "
				+ "En este módulo podrás visualizar las órdenes de compra recibidas de cada proveedor. \r\n"
				+ "Las podrás consultar por número de proveedor o por número de orden de compra. \r\n"
				+ "[ACEPTAR] ");
		validacion = OxxoAlDia.mensajeOrdenesCompraRecibidas();
		assertTrue(validacion, "error, no se muestra de manera correcta el mensaje");	
		
	}
	
	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-178-Pantalla “Órdenes de compra recibidas”
	 * Desc: Validar que los elementos de la pantalla “Órdenes de compra recibidas” se encuentren correctamente 
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 28/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_102_PantallaOrdenesRecibidas";
	}

	@Override
	public String setTestDescription() {
		return "Validar que los elementos de la pantalla “Órdenes de compra recibidas” se encuentren correctamente";
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
		return "1.-Contar con la aplicación más reciente de Oxxo al día ";
	}

	@Override
	public void beforeTest() {

	}

}

package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

/**
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_220_ValidarQueSeVisualiceElResumenDeVerificacionDeUnaTransferenciaDeLaCualLaFechaDeVerificacionSeaMenorA3MesDeAntiguedad extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_220_ValidarQueSeVisualiceElResumenDeVerificacionDeUnaTransferenciaDeLaCualLaFechaDeVerificacionSeaMenorA3MesDeAntiguedad_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("numTransferencia", data.get("numTransferencia"));
		cap.setCapability("contenedor", data.get("contenedor"));
		cap.setCapability("articulo", data.get("articulo"));
		cap.setCapability("fecha", data.get("fecha"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

        		// Abre la aplicación
				Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
				// Ejecuta el logeo hacia el punto de venta
				Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
				login.loginPV(data.get("ip"));

				// Ejecuta el logeo del lider
				Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		
				// Menu principal hasta Herramientas de COMETA
				A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);

				// En el menu de herramientas doy clic en la verificacion de cedis
				A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);

				// En el menu de herramientas doy clic en la verificacion de cedis
				A05_LoginAreaDistribucion LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
				
				Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoverificacion = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);

				
		/*Paso 1 Ingresar al menu principal de Oxxo al dia
		 * Resultado esperado: Se ingresa correctamente al menu principal
		 */		
		addStep("Ingresar al menu principal de Oxxo al dia"
				+ "Resultado esperado: Se ingresa correctamente al menu principal");		
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		System.out.println("Se ingresa al menu principal correctamente");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
									
		/*Paso 2 Seleccionar el icono de Excelencia Operativa
		 * Resultado esperado: Se despliega la pantalla de Excelencia Operativa
		 */		
		addStep("Seleccionar el icono de Excelencia Operativa"
				+ "Resultado esperado: Se despliega la pantalla de Excelencia Operativa");		
		MenuOaD.SeleccionarObjeto("Excelencia");
		Thread.sleep(4000);		
		System.out.println("Se despliega la pantalla de excelencia operativa");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		/*Paso 3 Seleccionar el icono de Herramienta de Compra
		 * Resultado esperado: Resultado esperado: Se despliega el submenu de la pantalla de HERRAMIENTA DE COMPRA
		 */		
		addStep("Seleccionar el icono de Herramienta de Compra"
				+ "Resultado esperado: Se despliega el submenu de la pantalla de HERRAMIENTA DE COMPRA");		
		MenuOaD.SeleccionarObjeto("Herramienta");
		Thread.sleep(4000);		
		System.out.println("Se despliega el submenu de la pantalla de HERRAMIENTA DE COMPRA");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		/*Paso 4 Seleccionar el icono de Cometa
		 * Resultado esperado:Resultado esperado: Se despliega la pantalla de Herramientas
		 */		
		addStep("Seleccionar el icono de Cometa"
				+ "Resultado esperado: Se despliega la pantalla de Herramientas");		
		MenuOaD.SeleccionarObjeto("Cometa");
		Thread.sleep(4000);		
		System.out.println("Se despliega la pantalla de Herramientas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		/*Paso 5 Seleccionar la opcion que lleva por nombre \"Verificacion de mercancia pedidos cedis
		 * Resultado esperado: Se ingresa a la pantalla \"Verificacion de mercancia de pedidos cedis
		 */		
		addStep("Seleccionar la opcion que lleva por nombre \"Verificacion de mercancia pedidos cedis\"	."
				+ "Resultado esperado: Se ingresa a la pantalla \"Verificacion de mercancia de pedidos cedis\"");		
		ModulosOaD.clickBtnVerificacionMercanciaCEDIS();
		Thread.sleep(2000);
		System.out.println("Se ingresa al modulo verificacion de mercancia de pedidos cedis");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		/*Paso 6 Se muestra el listado de transferencias en el listado de paginacion con fechas anterior a 3 mes atras.	
		 * Resultado esperado: Se muestra correctamente el listado de transferencias en el cual cumple con la validacion de 3 mes de antiguedad.
		 */		
		addStep("Se muestra el listado de transferencias en el listado de paginacion con fechas anterior a 3 mes atras."
				+ "Se muestra correctamente el listado de transferencias en el cual cumple con la validacion de 3 mes de antiguedad.");		
		//listadoverificacion.FechaMenos90Dias();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
		/*Paso 7 Seleccionar una transferencia que se muestran en el listado de paginacion de la cual se encuentra con una fecha en un rango de 3 mes atras.
		 * Resultado esperado:Se muestra el  resumen de verificacion de la transferencia seleccionada la cual es menor a 3 mes de antiguedad  y se visualiza el bol.
		 */		
		addStep("Seleccionar una transferencia que se muestran en el listado de paginacion de la cual se encuentra con una fecha en un rango de 3 mes atras."
				+ "Se muestra el  resumen de verificacion de la transferencia seleccionada la cual es menor a 3 mes de antiguedad  y se visualiza el bol.");		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_220_ValidarQueSeVisualiceElResumenDeVerificacionDeUnaTransferenciaDeLaCualLaFechaDeVerificacionSeaMenorA3MesDeAntiguedad";
	}

	@Override
	public String setTestDescription() {
		return "Al seleccionar la transferencia se mostrara el resumen de verificacion y su bol, donde la fecha de verificacion sea menor a 3 mes de antiguedad para poder visualizar su informacion.";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hdz Rios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"1. App Oxxo al dia instalada en Handheld.\r\n"
				+ "2. Usuario y Contraseña de Aplicacion Oxxo al dia.\r\n"
				+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contraseña: 2\r\n"
				+ "6.- Contar con dispositivo HH\r\n"
				+ "7. Tomar en cuenta la fecha de 2 meses anteriores para que sean visibles las transferencias, *tomar en fecha a partir del dia en que se encuentra realizando el CP.\"";
	}

	@Override
	public void beforeTest() {
	}

}
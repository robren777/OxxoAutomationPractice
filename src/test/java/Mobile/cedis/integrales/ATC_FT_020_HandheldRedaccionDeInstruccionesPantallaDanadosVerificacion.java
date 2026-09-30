package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:
 * Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas 
   de acuerdo al cambio de redacción.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_020_HandHeldRedacciónDeInstruccionesPantallaDañadosVerificación
 *
 * PRERREQUISITOS:
 * 1. App Oxxo al día instalada en Handheld.
 * 2. Usuario y Contraseña de Aplicación Oxxo al día.
 * 3. Comunicación entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexión entre ambos dispositivos).
 * 4. P.V con acceso a la Herramienta de compra.
 * 5. Realizar flujo de verificación hasta llegar a la pantalla de dañados
 *
 * 
 */


public class ATC_FT_020_HandheldRedaccionDeInstruccionesPantallaDanadosVerificacion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_020_HandheldRedaccionDeInstruccionesPantallaDanadosVerificacion_test(
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
				// Obtiene valor del DataProvaider
				loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

				// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
				Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
				herramienta.Cargando();
				herramienta.logeoInicioHerramientas();

				Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
				menuCedis.clickBtnVerificacionMercanciaCEDIS();// Selecciona la opcion de Verificacion

				Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoverificacion = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(
						testCase, appiumUtil, this);
				listadoverificacion.busquedaNumTransferencia(data.get("numTransferencia"));// Realiza la busqueda de una
																							// verificación
				Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos verificacion = new Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
				verificacion.necesitaPermisos();
				verificacion.barraProceso();				
				verificacion.darClickBotonBultos();
				verificacion.hacerClickEnBultoDelPadre(1);				
				verificacion.tomarFoto();
				verificacion.barraProceso();
				verificacion.verificarArticulo();				
				verificacion.darClickBotonBultos();	
				verificacion.hacerClickEnBultoDelPadre(2);				
				verificacion.tomarFoto();
				verificacion.barraProceso();
				verificacion.verificarArticulo();				
				verificacion.continuarDanadosHH();
				

				Mobile.cedis.om.C03_Acreditacion_de_Danados danados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil,this);

				/*
				 * Paso 1 Posicionarse en la pantalla de Acreditación, para capturar el listado de articulos dañados.
				 * Resultado:? Se muestra pantalla para capturar el listado de articulos
				 * dañados.
				 */
				danados.pantallaDanados();
				addStep("Posicionarse en la pantalla de Acreditación, para capturar el listado de articulos dañados."
						+ "Resultado:? Se muestra pantalla para capturar el listado de articulos dañados.");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

				/*
				 * Paso 2 Ir al apartado instrucciones y validar que se muestre correctamente el
				 * orden de las instrucciones en la pantalla de dañados en acreditación.
				 * Resultado:? Se muestra correctamente homologado el orden de la siguiente
				 * manera:
				 * 1. Escanea o da clic al botón “Ingresar código SKU” para registrar cada uno
				 * de los artículos dañados.
				 * 2. Coloca la cantidad de productos que recibiste.
				 * 3. Toma fotografía de cada uno de los artículos dañados.
				 */
				danados.validacionInstruccionesDanados();
				addStep("Ir al apartado instrucciones y validar que se muestre correctamente el orden de las instrucciones en la pantalla de dañados en acreditación."
						+ "Resultado:? Se muestra correctamente homologado el orden de la siguiente manera:"
						+ "1.	Escanea o da clic al botón “Ingresar código SKU” para registrar cada uno de los artículos dañados.\r\n"
						+ "2.	Coloca la cantidad de productos que recibiste.\r\n"
						+ "3.	Toma fotografía de cada uno de los artículos dañados.\r\n"
						+ "");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				
				

				// cerrar Apps
				Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

			}

			/*CEDIS MVP3: MTC-FT-030/TABLET ACREDITACIÓN "Modificar Dañados y Caducos" - Agregar y quitar artículos dañados y caducos
			  Desc:Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas de acuerdo al cambio de redacción.
			  @autor: Juan Ibarra
			  @date: 08/12/2024
			 */

			@Override
			public String setTestFullName() {
				return "ATC-FT-020-HandHeld Redacción de instrucciones pantalla dañados verificación";
			}

			@Override
			public String setTestDescription() {
				return "Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas de acuerdo al cambio de redacción";
			}

			@Override
			public String setTestDesigner() {
				return "Juan Ibarra";
			}

			@Override
			public String setTestInstanceID() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String setPrerequisites() {
				return "1. App Oxxo al día instalada en Handheld.\r\n"
						+ "2. Usuario y Contraseña de Aplicación Oxxo al día.\r\n"
						+ "3. Comunicación entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexión entre ambos dispositivos).\r\n"
						+ "4. P.V con acceso a la Herramienta de compra.\r\n"
						+ "5. Realizar flujo de verificación hasta llegar a la pantalla de dañados";
			}

			@Override
			public void beforeTest() {
			}

}
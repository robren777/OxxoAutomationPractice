package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_159_MensajeDeAlertaRecepcionSinBULTOSEscaneadosTABLET extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_159_MensajeDeAlertaRecepcionSinBULTOSEscaneadosTABLET_test(
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

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n" + e.getMessage());
			e.printStackTrace();
		}
		
		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		// ---------------------------------------- INICIO ------------------------------------------------ //
				// Apuntado de Punto de Venta
				A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
				System.out.println("\nLogeando Punto de Venta\n");
				Thread.sleep(2000);
				LoginPDVOaD.loginPV(data.get("ip"));

				// Login usuario Lider
				A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
				System.out.println("Logeando Lider\n");
				Thread.sleep(2000);
				LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

				// Menu principal hasta Herramientas de COMETA
				A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
				System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
				Thread.sleep(2000);
				MenuOaD.logeoInicioHerramientas();

				// En el menu de herramientas doy clic en la verificacion de cedis
				A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
				System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
				Thread.sleep(2000);
				ModulosOaD.clickBtnRecepcionPedidosCEDIS();
				

				// En el menu de herramientas doy clic en la verificacion de cedis
				A05_LoginAreaDistribucion LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
				System.out.println("Logeando Area de Distribucion CEDIS\n");
				Thread.sleep(2000);
				LoginAreaDistribucion.LoginAreaDistribucion();

				B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(
				testCase, appiumUtil, this);
				System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
				Thread.sleep(2000);
				POM_ListadoDeRecepcion.SeleccionarPrimeraTransferencia(true);
				

				B10_DeseaComenzar DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
				Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
				Thread.sleep(2000);
				DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
				Thread.sleep(5000);

				
				B02_RecepcionPedidosCEDIS pantallatransferencia = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
				Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// Se desliza hacia arriba de la pantalla 
				
				
				//Paso 1 Se ubica en la pantalla: "Estar en la pantalla de Recepcion de Pedidos CEDIS con un dispositivo TABLET
				//Resultado esperado: Se muestra la pantalla de Recepcion
				System.out.println("\nPaso #1: Estar en la pantalla: 'Estar en la pantalla de Recepcion de Pedidos CEDIS con un dispositivo TABLET'...");
				Thread.sleep(2000); 
				addStep("Estar en la pantalla: 'Estar en la pantalla de Recepcion de Pedidos CEDIS con un dispositivo TABLET'",
				        "Resultado esperado: Se muestra la pantalla de Recepcion.");
				pantallatransferencia.ValidacionEncabezado();		
				Thread.sleep(2000);
				//Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				/*Paso 2 Se ubica en la pantalla: "Pulsar el icono de la camara y tomar las 3 fotografias que se pide en la aplicacion.
				 * Resultado esperado: Se toman las 3 evidencias y se muestran de forma enlistada.
				 */		
				System.out.println("\nPaso #2: Pulsar el icono de la camara y tomar las 3 fotografias que se pide en la aplicacion...");
				addStep("Acción realizada \r\n"
						+ "Resultado esperado: Se muestra ");		
				pantallatransferencia.Tomar3Fotos();
				Thread.sleep(2000);
				//Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				//Paso 3: Se ubica en la pantalla: 'Dar clic en el boton de CONTINUAR sin haber escaneado algun bulto de Cajas, canastillas Secos y Canastillas Refrigerados'.
				 //Resultado esperado: Se muestra un mensaje de alerta con el mensaje: "No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido."
				 // Este mensaje mostrara el boton de ACEPTAR.
				System.out.println("\nPaso #3: Dar clic en el boton de CONTINUAR sin haber escaneado algun bulto de Cajas, canastillas Secos y Canastillas Refrigerados...");
				addStep("Acción realizada \r\n"
						+ "Resultado esperado: Se muestra ");		
				pantallatransferencia.SeleccionarObjeto("Terminar");
				Thread.sleep(1500);
				pantallatransferencia.ValidarMensaje();
				//Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
			
				
				/* Paso 4: Se ubica en la pantalla: 'Dar clic en el boton de ACEPTAR'.
				 * Resultado esperado: Se regresa a la pantalla de Recepcion de Pedidos CEDIS cerrando el mensaje.
				 */
				System.out.println("\nPaso #4: Dar clic en el boton de ACEPTAR...");
				addStep("Acción realizada \r\n"
						+ "Resultado esperado: Se muestra ");		
				Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
				pantallatransferencia.SeleccionarObjeto("Aceptar");
				Thread.sleep(1500);
				pantallatransferencia.ValidacionEncabezado();
				//Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
												
				// cerrar Apps
				Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
			}



	@Override		
	public String setTestFullName() {
		return "ATC_FT_159_MensajeDeAlertaRecepcionSinBULTOSEscaneadosTABLET";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se muestre el mensaje correspondiente en la pantalla de Recepcion de Mercancia al continuar sin haber "
				+ "escaneado algun bulto utilizando un dispositivo TABLET";
	}

	@Override
	public String setTestDesigner() {
		return "Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con dispositivo TABLET\r\n"
				+ "2. Version APK actual\r\n"
				+ "3. Punto de Venta \r\n"
				+ "4. Ingresar a la pantalla de Recepcion por Ruta\r\n"
				+ "5. Seleccionar la opcion de Recepcion sin escaner\r\n"
				+ "6. Seleccionar una transferencia a recepcionar que cuente con Cajas, canastillas Secos y Canastillas Refrigerados";
	}

	@Override
	public void beforeTest() {
	}

}
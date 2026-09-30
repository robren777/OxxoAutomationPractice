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

public class ATC_FT_186_TabletNuevaPantallaCOMODESEASCOMENZARTABLET extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_186_TabletNuevaPantallaCOMODESEASCOMENZARTABLET_test(
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
		herramienta.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
					
		/*Paso 1.-Se ubica en la pantalla: "Recepción de pedido Cedis"
		Resultado Esperado: Se despliega correctamente la pantalla: "Recepción de pedido Cedis", donde Se visualiza el listado de transferencias.			
		 */
		addStep("Se ubica en la pantalla: \"Recepción de pedido Cedis\"\r\n"
				+ "		Resultado Esperado: Se despliega correctamente la pantalla: \"Recepción de pedido Cedis\", donde Se visualiza el listado de transferencias.");		
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo		
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 2.-Se da clic en una de estas transferencias
		Resultado Esperado: Se despliega correctamente la nueva pantalla de recepción con el titulo que lleva por nombre ¿COMO DESEAS COMENZAR?
		*/
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));		
		addStep("Se da clic en una de estas transferencias\r\n"
				+ "		Resultado Esperado: Se despliega correctamente la nueva pantalla de recepción con el titulo que lleva por nombre ¿COMO DESEAS COMENZAR?");				
		Thread.sleep(1500);
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3.-Se valida, al principio de la pantalla, que exista la cabecera informativa que incluye la información de la tienda.
		Resultado Esperado: "Se despliega correctamente la nueva pantalla de recepción y contiene los elementos de cabecera informativa:
		*Fecha.
		*Ruta
		*Ruta Dinámica
		*CR Tienda
		*Nombre Tienda
		*Líder
		*Transferencia"
		*/
		addStep("Se valida, al principio de la pantalla, que exista la cabecera informativa que incluye la información de la tienda.\r\n"
				+ "		Resultado Esperado: Se despliega correctamente la nueva pantalla de recepción y contiene los elementos de cabecera informativa:\r\n"
				+ "		*Fecha.\r\n"
				+ "		*Ruta\r\n"
				+ "		*Ruta Dinámica\r\n"
				+ "		*CR Tienda\r\n"
				+ "		*Nombre Tienda\r\n"
				+ "		*Líder\r\n"
				+ "		*Transferencia");	
		DeseaComenzar.ValidacionEncabezado();	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4.-Consiguiente de la información de la tienda, se valida que se visualicen las instrucciones.
		Resultado Esperado: Se despliega correctamente la nueva pantalla de recepción y contiene las instrucciones con el siguiente texto: Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.
		*/
		addStep("Consiguiente de la información de la tienda, se valida que se visualicen las instrucciones.\r\n"
				+ "		Resultado Esperado: Se despliega correctamente la nueva pantalla de recepción y contiene las instrucciones con el siguiente texto: Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.");		
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");// Se desliza un poco hacía abajo
		DeseaComenzar.ValidarInstrucciones();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 5.-Se presiona el botón "RECEPCIÓN DE PEDIDO"
		Resultado Esperado: Nos envía correctamente a la pantalla de recepción de la transferencia seleccionada, donde Se inicia el proceso de recepción(fotografías y escaneo de bultos).
		*/
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.ValidacionEncabezado();
		addStep("Se presiona el botón \"RECEPCIÓN DE PEDIDO\"\r\n"
				+ "		Resultado Esperado: Nos envía correctamente a la pantalla de recepción de la transferencia seleccionada, donde Se inicia el proceso de recepción(fotografías y escaneo de bultos)");		
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6.-Se regresa a la nueva pantalla y se presiona el botón "DEVOLUCIONES A CEDIS"
		Resultado Esperado: Nos envía correctamente a la pantalla de DEVOLUCIONES A CEDIS(Devolución de mercancía, Devolución de contenedores, Canastillas con devolución)
		*/
		
		addStep("Se regresa a la nueva pantalla y se presiona el botón \"DEVOLUCIONES A CEDIS\"\r\n"
				+ "		Resultado Esperado: Nos envía correctamente a la pantalla de DEVOLUCIONES A CEDIS(Devolución de mercancía, Devolución de contenedores, Canastillas con devolución)");		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		pantallatransferencia.SeleccionarObjeto("Desea Salir");
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		Thread.sleep(500);	
		devolucion.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");							
										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC-FT-186-Tablet Nueva Pantalla ¿COMO DESEAS COMENZAR? TABLET";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se haya agregado una nueva pantalla en el apartado recepción de pedido cedis la cual funciona para decidir por cual flujo iniciar la recepción de la transferencia seleccionada.";
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
		return "*Contar con la versión de la apk más actual\r\n"
				+ "*Haber cargado transferencias en el P.V\r\n"
				+ "*Contar con dispositivo Tablet\r\n"
				+ "*Tener asignado un P.V para tener conexión entre dispositivo HandHeld y P.V\r\n"
				+ "*Tener activados las credenciales del login de ruta\r\n"
				+ "-Usuario 123\r\n"
				+ "-Contraseña 2\r\n"
				+ "*Ingresar a la aplicación OXXO al día hasta la parte donde se muestra el listado de las transferencias.";
	}

	@Override
	public void beforeTest() {
	}

}
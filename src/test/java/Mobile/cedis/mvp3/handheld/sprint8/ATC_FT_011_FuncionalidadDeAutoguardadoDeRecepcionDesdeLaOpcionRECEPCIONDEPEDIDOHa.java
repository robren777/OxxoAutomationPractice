package Mobile.cedis.mvp3.handheld.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_011_FuncionalidadDeAutoguardadoDeRecepcionDesdeLaOpcionRECEPCIONDEPEDIDOHa extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_011_FuncionalidadDeAutoguardadoDeRecepcionDesdeLaOpcionRECEPCIONDEPEDIDOHa_test(
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
		//Mobile.cedis.om.Tablet_A03_Menus_y_submenus herramienta = new Mobile.cedis.om.Tablet_A03_Menus_y_submenus(testCase, appiumUtil, this);
		//herramienta.logeoInicioHerramientas();		
		

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia		

		/*Paso 1.- Se ubica en la pantalla: "Recepción de Pedido Cedis".
		 * Resultado esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar
		 */		
		addStep("Se ubica en la pantalla: \"Recepción de Pedido Cedis\".\r\n"
				+ "Resultado esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 2.- Se da clic en una de las transferencias nuevas.
		 * Resultado esperado: Se muestra de forma correcta la nueva pantalla de Recepción donde se tiene la leyenda ¿CÓMO DESEAS COMENZAR? Y dos opciones "RECEPCIÓN DE PEDIDO" y "DEVOLUCIONES A CEDIS"
		 */		
		addStep("Se da clic en una de las transferencias nuevas.\r\n"
				+ "Resultado esperado: Se muestra de forma correcta la nueva pantalla de Recepción donde se tiene la leyenda ¿CÓMO DESEAS COMENZAR? Y dos opciones \"RECEPCIÓN DE PEDIDO\" y \"DEVOLUCIONES A CEDIS\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3.- Se da clic en la opción de "RECEPCIÓN DE PEDIDO".
		 * Resultado esperado: Se visualiza la pantalla del proceso de RECEPCIÓN DE PEDIDO A CEDIS 
		 */		
		addStep("Se da clic en la opción de \"RECEPCIÓN DE PEDIDO\".\r\n"
				+ "Resultado esperado: Se visualiza la pantalla del proceso de RECEPCIÓN DE PEDIDO A CEDIS ");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 4.- Tomar las 3 fotografías que pide la aplicación.
		 * Resultado esperado: Se guardan de manera correcta las evidencias 
		 */		
		addStep("Tomar las 3 fotografías que pide la aplicación.\r\n"
				+ "Resultado esperado: Se guardan de manera correcta las evidencias ");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 5.- Se escanean algunos bultos que se tenga en el pedido sin dejar ninguna sección vacía (en caso de que alguna sección contenga una cantidad total de pedido o que no esté en cero.).
		 * Resultado esperado: Se contabilizan de manera correcta los bultos que se han escaneado
		 */		
		addStep("Se escanean algunos bultos que se tenga en el pedido sin dejar ninguna sección vacía (en caso de que alguna sección contenga una cantidad total de pedido o que no esté en cero.).\r\n"
				+ "Resultado esperado: Se contabilizan de manera correcta los bultos que se han escaneado");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6.- Dar clic para atrás la pantalla (primer ícono ubicado abajo de la pantalla a la izquierda).
		 * Resultado esperado: Se muestra un mensaje con el texto: “¿Estás seguro que deseas salir? Se guardaran solo las fotografías de entrega y los bultos escaneados hasta el momento.” Con las opciones de CANCELAR y SALIR.
		 */		
		addStep("Dar clic para atrás la pantalla (primer ícono ubicado abajo de la pantalla a la izquierda).\r\n"
				+ "Resultado esperado: Se muestra un mensaje con el texto: “¿Estás seguro que deseas salir? Se guardaran solo las fotografías de entrega y los bultos escaneados hasta el momento.” Con las opciones de CANCELAR y SALIR.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7.- Se da clic en SALIR.
		 * Resultado esperado: Se muestra la nueva pantalla de Recepción donde se tiene la leyenda ¿CÓMO DESEAS COMENZAR? Y dos opciones "RECEPCIÓN DE PEDIDO" y "DEVOLUCIONES A CEDIS"
		 */		
		addStep("Se da clic en SALIR..\r\n"
				+ "Resultado esperado: Se muestra la nueva pantalla de Recepción donde se tiene la leyenda ¿CÓMO DESEAS COMENZAR? Y dos opciones \"RECEPCIÓN DE PEDIDO\" y \"DEVOLUCIONES A CEDIS\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 8.- Se da clic nuevamente para atrás la pantalla (primer ícono ubicado abajo de la pantalla a la izquierda).
		 * Resultado esperado: Se manda a la pantalla de LOGIN de RUTA de Recepción 
		 */		
		addStep("Se da clic nuevamente para atrás la pantalla (primer ícono ubicado abajo de la pantalla a la izquierda).\r\n"
				+ "Resultado esperado: Se manda a la pantalla de LOGIN de RUTA de Recepción ");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 9.- Dar clic en ENTRAR teniendo el Login de RUTA.
		 * Resultado esperado: Se visualiza la pantalla de Recepción de Pedido CEDIS con la transferencia con el símbolo de reloj 
		 */		
		addStep("Dar clic en ENTRAR teniendo el Login de RUTA..\r\n"
				+ "Resultado esperado: Se visualiza la pantalla de Recepción de Pedido CEDIS con la transferencia con el símbolo de reloj ");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 10.- Ingresar a la transferencia que ya se avanzó (con el símbolo de reloj).
		 * Resultado esperado: Se muestra la nueva pantalla de Recepción donde se tiene la leyenda ¿CÓMO DESEAS COMENZAR? Y dos opciones "RECEPCIÓN DE PEDIDO" y "DEVOLUCIONES A CEDIS"
		 */		
		addStep("Ingresar a la transferencia que ya se avanzó (con el símbolo de reloj).\r\n"
				+ "Resultado esperado: Se muestra la nueva pantalla de Recepción donde se tiene la leyenda ¿CÓMO DESEAS COMENZAR? Y dos opciones \"RECEPCIÓN DE PEDIDO\" y \"DEVOLUCIONES A CEDIS\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 11.- Elegir nuevamente la opción de RECEPCIÓN DE PEDIDO.
		 * Resultado esperado: Se valida que la información ingresada anteriormente (fotografías tomadas y bultos escaneados) se mantenga de manera correcta.
		 */		
		addStep("Elegir nuevamente la opción de RECEPCIÓN DE PEDIDO.\r\n"
				+ "Resultado esperado: Se valida que la información ingresada anteriormente (fotografías tomadas y bultos escaneados) se mantenga de manera correcta.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_011_FuncionalidadDeAutoguardadoDeRecepcionDesdeLaOpcionRECEPCIONDEPEDIDOHa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en la opción \"RECEPCIÓN DE PEDIDO\" y al salir del proceso con información registrada, desempeñe de manera correcta la funcionalidad del autoguardado de Recepción.";
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
		return "1.-Contar con la versión de la apk más actual\r\n"
				+ "2.-Haber cargado transferencias en el P.V\r\n"
				+ "3.-Contar con dispositivo HandHeld\r\n"
				+ "4.-Tener asignado un P.V para tener conexión entre dispositivo HandHeld y P.V\r\n"
				+ "5.-Tener activados las credenciales del login de ruta\r\n"
				+ "-Usuario 123\r\n"
				+ "-Contraseña 2\r\n"
				+ "6.-Ingresar a la aplicación OXXO al día hasta la parte donde se muestra el listado de las transferencias.";
	}

	@Override
	public void beforeTest() {
	}

}
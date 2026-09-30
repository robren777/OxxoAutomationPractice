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

public class ATC_FT_150_HandheldSalirDeLaPantallaResumenDeEntrega extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_150_HandheldSalirDeLaPantallaResumenDeEntrega_test(
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
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		//transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));//Linea agregada por Juan para realizar la prueba donde se manda un numero de transferencia especifica
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//Linea agregada por Juan donde se agrega el OM de la pantalla de Como Desea Comenzar
		DeseaComenzar.Cargando();//Linea agregada por Juan donde se agrega metodo de carga de la pantalla antes de realizar la siguiente acción
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");//Linea agregada por Juan donde se selecciona la opción de Recepcion Pedido 
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();		
		pantallatransferencia.Tomar3Fotos();
		
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));
		pantallatransferencia.MoverSeccion("Terminar");
		pantallatransferencia.SeleccionarObjeto("Terminar");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		pantallatransferencia.SeleccionarObjeto("No");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		pantallatransferencia.MoverSeccion("Sobrantes");
		pantallatransferencia.CapturaContenedorSobrantes("654654");
		Thread.sleep(1000);
		pantallatransferencia.SeleccionarObjeto("Continuar Dañados");
		//Runtime.getRuntime().exec("adb shell input keyevent 4");//comentarizado hace un momento 
		pantallatransferencia.SeleccionarArticulo(data.get("articulo"), "", "");
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");
		//Thread.sleep(1000);
		pantallatransferencia.MoverSeccion("Dañados");
		//Thread.sleep(500);
		pantallatransferencia.SeleccionarObjeto("Terminar Dañados");
		//Thread.sleep(500);
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS ResumenPedido = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		
		/*Paso 1 Posicionarse en la pantalla Recepcion de pedido cedis apartado resumen de entrega de pedido
		 * Resultado esperado: Se muestra la pantalla correctamente
		 */		
		addStep("Posicionarse en la pantalla Recepcion de pedido cedis apartado resumen de entrega de pedido\r\n"
				+ "Resultado esperado: Se muestra la pantalla correctamente");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		
		/*Paso 2 Las pantallas que componen al resumen de entrega 
		 * Resumen entrega de pedido, entrada de cajas sin etiquetas/etiquetas dañadas, detalle faltantes,  sobrantes, dañados muestran registro de informacion capturada"
		 * Resultado esperado: Se muestra la pantalla y las secciones mencionadas con informacion capturada
		 */	
		
		addStep("Las pantallas que componen al resumen de entrega\r\n"
				+ "Resumen entrega de pedido, entrada de cajas sin etiquetas/etiquetas dañadas, detalle faltantes,  sobrantes, dañados muestran registro de informacion capturada\"\r\n"
				+ "Resultado esperado: Se muestra la pantalla y las secciones mencionadas con informacion capturada");
		ResumenPedido.SeleccionarObjeto("Resumen Total Pedido");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/**ResumenPedido.SeleccionarObjeto("Leyenda Faltante Refrigerado");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		*/
		ResumenPedido.SeleccionarObjeto("Total Dañados");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
		/*Paso 3 Presionar el boton  ‘Home’: es el icono redondo que se encuentra ubicado justo en la mitad. Este boton nos lleva a la pantalla principal de nuestro dispositivo
		 * Resultado esperado: Nos dirige a la pantalla principal de nuestro dispositivo  
		 */		
		addStep("Presionar el boton  ‘Home’: es el icono redondo que se encuentra ubicado justo en la mitad. Este boton nos lleva a la pantalla principal de nuestro dispositivo\r\n"
				+ "Resultado esperado: Nos dirige a la pantalla principal de nuestro dispositivo");
		Runtime.getRuntime().exec("adb shell input keyevent 3");// realiza la acción de Home en la handheld
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		
		/*Paso 4 Seleccionar el icono de la aplicacion oxxo al dia
		 * Resultado esperado: Nos dirige a la pantalla recepcion de pedido cedis apartado resumen de entrega de pedido y se verifica que los datos permanezcan guardados correctamente.
		 */		
		addStep("Seleccionar el icono de la aplicacion oxxo al dia\r\n"
				+ "Resultado esperado: Nos dirige a la pantalla recepcion de pedido cedis apartado resumen de entrega de pedido y se verifica que los datos permanezcan guardados correctamente.");		
		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		Thread.sleep(2000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");						
								
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override
	public String setTestFullName() {
		return "ATC-FT-150-HandHeld Salir de la pantalla resumen de entrega";
	}

	@Override
	public String setTestDescription() {
		return "Validar que si el operador distribucion se sale de la aplicacion, y vuelve a ingresar la pantalla de resumen de entrega debera mantener la informacion guardada previamente.";
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
		return "1. App Oxxo al dia instalada en Handheld.\r\n"
				+ "2. Usuario y Contraseña de Aplicacion Oxxo al dia.\r\n"
				+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Realizar flujo de verificacion hasta llegar a la pantalla de caducos\r\n"
				+ "6. Dispositivo HandHeld\r\n"
				+ "7. Salirse de la pantalla resumen de entrega";
	}

	@Override
	public void beforeTest() {
	}

}
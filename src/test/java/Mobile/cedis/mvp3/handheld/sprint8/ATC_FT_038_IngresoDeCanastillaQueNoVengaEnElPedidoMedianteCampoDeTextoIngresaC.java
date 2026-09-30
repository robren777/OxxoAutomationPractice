package Mobile.cedis.mvp3.handheld.sprint8;

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
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_038_IngresoDeCanastillaQueNoVengaEnElPedidoMedianteCampoDeTextoIngresaC extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_038_IngresoDeCanastillaQueNoVengaEnElPedidoMedianteCampoDeTextoIngresaC_test(
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
		cap.setCapability("Canastilla", data.get("Canastilla"));
		
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
		//POM_ListadoDeRecepcion.SeleccionarPrimeraTransferencia(true);//Se comentarizo la linea por parte de Juan para realizar la prueba				 
		POM_ListadoDeRecepcion.BuscarTransferencia(true,data.get("numTransferencia"));//Linea agregada por Juan para realizar la prueba donde se manda un numero de transferencia especifica
		
		B10_DeseaComenzar POM_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		POM_DeseaComenzar.Cargando();//linea agregada por Juan
		POM_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");//linea agregada por Juan
		
		B02_RecepcionPedidosCEDIS POM_PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		System.out.println("Tomando las 3 evidencias obligatorias del pedido\n");
		Thread.sleep(2000);
		POM_PedidoRecepcion.DeslizarArriba();
		POM_PedidoRecepcion.Tomar3Fotos();
		
		
		/*Paso 1 Se ubica en la pantalla: "Recepción de pedido CEDIS", en la subsección: "Contenedor".
		/*Resultado: Se despliega correctamente el campo de texto: "Ingresa código de contenedor". En la pantalla: "Recepción de pedido CEDIS", en la subsección: "Contenedor""		
		*/
		addStep("1 Se ubica en la pantalla: Recepción de pedido CEDIS , en la subsección: Contenedor \n\""
				+  "Resultado: Se despliega correctamente el campo de texto: \"Ingresa código de contenedor\". En la pantalla: \"Recepción de pedido CEDIS\", en la subsección: \"Contenedor\"\"		");
		POM_PedidoRecepcion.MoverSeccion("Contenedor");
		Runtime.getRuntime().exec("adb shell input swipe 100 300 100 500 100");
		Thread.sleep(2000);
		System.out.println("Se despliega correctamente el campo de texto: \"Ingresa código de contenedor\". En la pantalla: \"Recepción de pedido CEDIS\", en la subsección: \"Contenedor\"\"		");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
		/*Paso 2 Se da clic en el campo de texto: "Ingresa código de contenedor".	
		/*Resultado: Se despliega correctamente el teclado y únicamente nos permite ingresar números.		
		*/
		addStep("2 Se da clic en el campo de texto: \"Ingresa código de contenedor\" \n\""
				+  "Resultado: Se despliega correctamente el teclado y únicamente nos permite ingresar números.");
		POM_PedidoRecepcion.SeleccionarObjeto("Ingresar Contenedor");
		System.out.println("Se despliega correctamente el teclado y únicamente nos permite ingresar números.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3 Se escribe el código de una canastilla del tipo: "Refrigerados". Que no este dentro del pedido para agregarla en la recepción.	
		/*Resultado: Se ingresa correctamente el código de la canastilla del tipo: "Refrigerados"	
		*/
		addStep("3 Se escribe el código de una canastilla del tipo: \"Refrigerados\". Que no este dentro del pedido para agregarla en la recepción.	\n\""
				+  "Resultado: Se ingresa correctamente el código de la canastilla del tipo: \"Refrigerados\"	");
		Runtime.getRuntime().exec("adb shell input text " + data.get("Canastilla") );
		Thread.sleep(3000);
		System.out.println("Se ingresa correctamente el código de la canastilla del tipo: \"Refrigerados\"	");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4 Se da clic en el botón de: "Enter".
		/*Resultado: Se despliega correctamente el mensaje: "El código que ha ingresado no se encuentra en el pedido" [Confirmar]		
		*/
		addStep("4 Se da clic en el botón de: \"Enter\"  \n\""
				+  "Resultado:  Se despliega correctamente el mensaje: \"La canastilla que ha escaneado no se encuentra dentro del pedido\" [Confirmar]		");	
		Runtime.getRuntime().exec("adb shell input keyevent 66");
		Thread.sleep(2000);
		POM_PedidoRecepcion.ValidacionMsjCanastillaNoPertenecePedido();
		System.out.println("Se despliega correctamente el mensaje: \"La canastilla que ha escaneado no se encuentra dentro del pedido\" [Confirmar]	");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: ATC_FT_038_IngresoDeCanastillaQueNoVengaEnElPedidoMedianteCampoDeTextoIngresaC 
	  Desc: Validar que, se haya implementado el mensaje de error correcto cuando intentamos ingresar códigos de canastillas que no son validos o no vienen en el pedido. Por medio del campo de texto: Ingresa código de contenedor\". Mediante teclado "
	  @date: 30/01/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_038_IngresoDeCanastillaQueNoVengaEnElPedidoMedianteCampoDeTextoIngresaC";
	}

	@Override
	public String setTestDescription() {
		return "Validar que, se haya implementado el mensaje de error correcto cuando intentamos ingresar códigos de canastillas que no son validos o no vienen en el pedido. Por medio del campo de texto: "
				+ "\"Ingresa código de contenedor\". Mediante teclado ";
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
		return "1.*Tener pedido para recepcionar, el cual cuente con cajas, canastillas tipo secos y de tipo refrigerados\r\n"
				+ "2. *Haber tomado las tres fotos de evidencia del pedido";
	}

	@Override
	public void beforeTest() {
	}

}
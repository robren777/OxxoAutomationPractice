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

public class ATC_FT_158_MensajeDeAlertaRecepcionSinBULTOSEscaneados extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_158_MensajeDeAlertaRecepcionSinBULTOSEscaneados_test(
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
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		
		//---------------------------------------- PRE INICIO ------------------------------------------------//

		//En el login de punto de venta ingreso la ip y el puerto
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("Logeando Punto de Venta");
		Thread.sleep(2000);
		LoginPDVOaD.loginPV("10.75.6.126");

		//En el login de lider ingreso el usuario y contraseña
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider");
		Thread.sleep(2000);
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		//En la pantalla principal doy clic en el boton de excelencia operativa, luego en la hc y al final e cometa
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
		Thread.sleep(2000);
		MenuOaD.logeoInicioHerramientas();
		
		//En el menu de herramientas doy clic en la Recepción de Pedidos CEDIS
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Recepción de Pedidos CEDIS");
		Thread.sleep(2000);
		ModulosOaD.clickBtnRecepcionPedidosCEDIS();
		
		//Logeo en Recepción de Pedidos CEDIS
		A05_LoginAreaDistribucion LoginRec = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		System.out.println("Logeando Recepción de Pedido CEDIS");
		Thread.sleep(2000);
		LoginRec.LoginAreaDistribucion();
		
		//Seleccionamos transferencia del listado
		B01_RecepcionPedidosSeleccionarTransferencia Transfer = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		System.out.println("Seleccionando la primera trasnferencia");
		Thread.sleep(2000);
		Transfer.BuscarTransferencia(true,data.get("numTransferencia"));
		
		//Seleccion de recepción por Recepción de pedido
		B10_DeseaComenzar DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
		System.out.println("Seleccionando comenzar por Recepción de Pedido");
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		

		//=================================== Inicio de Caso =====================================//
		
		B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, null);

		 /* 
		 * Paso 1  - Estar en la pantalla de Recepcion de Pedidos CEDIS con un dispositivo Handheld
		*/ 
		addStep("Estar en la pantalla de Recepcion de Pedidos CEDIS con un dispositivo Handheld");
		PedidoRecepcion.DeslizarArriba();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Se muestra la pantalla de recepcion pedido cedis");
		PedidoRecepcion.MoverSeccion("Contenedor");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		PedidoRecepcion.MoverSeccion("Terminar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /*
		 * Paso 2  - ?Se muestra la pantalla de Recepcion
		*/ 
		addStep("?Se muestra la pantalla de Recepcion");
		PedidoRecepcion.DeslizarArriba();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 3  - Pulsar el icono de la camara y tomar las 3 fotografias que se pide en la aplicacion.
		*/ 
		addStep("Pulsar el icono de la camara y tomar las 3 fotografias que se pide en la aplicacion.");
		PedidoRecepcion.Tomar3Fotos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 4  - ?Se toman las 3 evidencias y se muestran de forma enlistada. 
		*/ 
		addStep("?Se toman las 3 evidencias y se muestran de forma enlistada.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 5  - Dar clic en el boton de TERMINAR sin haber escaneado algun bulto de Cajas, canastillas Secos y Canastillas Refrigerados
		*/ 
		addStep("Dar clic en el boton de TERMINAR sin haber escaneado algun bulto de Cajas, canastillas Secos y Canastillas Refrigerados");
		PedidoRecepcion.SeleccionarObjeto("Terminar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 6  - ?Se muestra un mensaje de alerta con el mensaje: "No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido."  
					 Este mensaje mostrara el boton de ACEPTAR
		*/ 
		addStep("?Se muestra un mensaje de alerta con el mensaje: \"No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido.\"  \r\n"
				+ "Este mensaje mostrara el boton de ACEPTAR");
		PedidoRecepcion.ValidarMensaje();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 7  - Dar clic en el boton de ACEPTAR.
		*/ 
		addStep("Dar clic en el boton de ACEPTAR.");
		PedidoRecepcion.SeleccionarObjeto("Aceptar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 8  - ?Se regresa a la pantalla de Recepcion de Pedidos CEDIS cerrando el mensaje.
		*/ 
		addStep("?Se regresa a la pantalla de Recepcion de Pedidos CEDIS cerrando el mensaje.");
		PedidoRecepcion.MoverSeccion("Contenedor");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-158 Mensaje de Alerta - Recepcion sin BULTOS escaneados
	  Desc:Validar que se muestre el mensaje correspondiente en la pantalla de Recepcion de Mercancia al continuar sin haber escaneado algun bulto utilizando un dispositivo Handheld.
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_158_MensajeDeAlertaRecepcionSinBULTOSEscaneados";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se muestre el mensaje correspondiente en la pantalla de Recepcion de Mercancia al continuar "
				+ "sin haber escaneado algun bulto utilizando un dispositivo Handheld";
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
		return "1. Contar con dispositivo Handheld\r\n"
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
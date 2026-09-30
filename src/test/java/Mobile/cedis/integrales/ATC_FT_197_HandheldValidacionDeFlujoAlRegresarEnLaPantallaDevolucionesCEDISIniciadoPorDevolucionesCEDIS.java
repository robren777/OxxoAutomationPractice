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
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_197_HandheldValidacionDeFlujoAlRegresarEnLaPantallaDevolucionesCEDISIniciadoPorDevolucionesCEDIS extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_197_HandheldValidacionDeFlujoAlRegresarEnLaPantallaDevolucionesCEDISIniciadoPorDevolucionesCEDIS_test(
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

				//Seleccionamos transferencia del listado
		B01_RecepcionPedidosSeleccionarTransferencia Transfer = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		
		B05_DevolucionMercancia Devo = new B05_DevolucionMercancia(testCase, appiumUtil, null);
		B10_DeseaComenzar comenzar = new B10_DeseaComenzar(testCase, appiumUtil, null);

		//=================================== Inicio de Caso =====================================//
		 /* 
		 * Paso 1  - Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar
		*/ 
			addStep("Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar");
			LoginRec.LoginAreaDistribucion();
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 				
		/* 
		 * Paso 2  - ?Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		*/ 
			addStep("?Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
			Transfer.MoverHacia("Transferencia");
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/* 
		 * Paso 3  - Se selecciona una transferencia que no ha sido atendida por el usuario.
		*/ 
			addStep("Se selecciona una transferencia que no ha sido atendida por el usuario.");
			Transfer.BuscarTransferencia(true,data.get("numTransferencia"));
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/* 
		 * Paso 4  - ?Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
						-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.

						Mostrara el Título de :"¿Como deseas comenzar?"
						Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada."

						Muestra los botones de:
						-"Recepción de pedido"
						-"Devoluciones a CEDIS"
		*/ 
			addStep("Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
					+ "					-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
					+ "					Mostrara el Título de :\"¿Como deseas comenzar?\"\r\n"
					+ "					Mostrara las Instrucciones de: \"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, \r\n"
					+ "					registrando las devoluciones del pedido. Selecciona la opción deseada.\"\r\n"
					+ "					Muestra los botones de:\r\n"
					+ "					-\"Recepción de pedido\"\r\n"
					+ "					-\"Devoluciones a CEDIS\"");
			comenzar.Cargando();
			comenzar.validarBotones();
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/* 
		 * Paso 5  - Se selecciona la opción de Devoluciones a CEDIS
		*/ 
			addStep("Se selecciona la opción de Devoluciones a CEDIS");
			comenzar.SeleccionarObjeto("Devolucion CEDIS");
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/* 
		 * Paso 6  - ?Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
		*/ 
			addStep("?Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
			Devo.ValidacionEncabezado();
			Devo.SeleccionarBOLSinCheck("", "");
			Devo.AumentarCanastilla(1, "Azul");
			Devo.AumentarCanastilla(2, "Amarilla");
			Devo.AumentarCanastilla(3, "Verde");
			Devo.AumentarCanastilla(4, "Naranja");
			Devo.AumentarCanastilla(5, "Otro");
			Devo.AumentarContenedores(6, "Tapas");
			Devo.AumentarContenedores(7, "Envases");
			Devo.TomarFotoDevolucion();
			Devo.IngresarCanastilla("874235");
			Devo.TomarFotoCanastilla();
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/* 
		 * Paso 7  - Se selecciona la opcion de Regresar en la pantalla de Devoluciones CEDIS una vez llenada la informacion correspondiente en la pantalla.
		*/ 
			addStep("Se selecciona la opcion de Regresar en la pantalla de Devoluciones CEDIS una vez llenada la informacion correspondiente en la pantalla.");
			Devo.sendKeysUsingKeyEvents(appiumUtil, "B");
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 8  - ?Se descarta la información capturada y se regresa al usuario a la pantalla donde se selecciona el flujo de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
					-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.

					Mostrara el Título de :"¿Como deseas comenzar?"
					Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada."

					Muestra los botones de:
					-"Recepción de pedido"
					-"Devoluciones a CEDIS"
		*/ 
			addStep("Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
					+ "					-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
					+ "					Mostrara el Título de :\"¿Como deseas comenzar?\"\r\n"
					+ "					Mostrara las Instrucciones de: \"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, \r\n"
					+ "					registrando las devoluciones del pedido. Selecciona la opción deseada.\"\r\n"
					+ "					Muestra los botones de:\r\n"
					+ "					-\"Recepción de pedido\"\r\n"
					+ "					-\"Devoluciones a CEDIS\"");
			comenzar.validarBotones();
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-197 Handheld Validación de flujo al regresar en la pantalla Devoluciones CEDIS iniciado por Devoluciones CEDIS
	  Desc: Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de Regresar en la pantalla de Devoluciones CEDIS para una transferencia iniciada por Devoluciones CEDIS
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_197_HandheldValidacionDeFlujoAlRegresarEnLaPantallaDevolucionesCEDISIniciado";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de Regresar "
				+ "en la pantalla de Devoluciones CEDIS para una transferencia iniciada por Devoluciones CEDIS";
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
		return "1. Contar con Transferencias por atender en la handheld\r\n"
				+ "2. Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS";
	}

	@Override
	public void beforeTest() {
	}

}
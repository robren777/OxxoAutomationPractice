package Mobile.cedis.mvp3.tablet.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_017_TabletValidacionDeInicioDeFlujoDeTransferenciaDesdeDevolucionesACEDIS extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_017_TabletValidacionDeInicioDeFlujoDeTransferenciaDesdeDevolucionesACEDIS_test(
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

		/*Paso 1.- Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar
		 * Resultado esperado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		 */		
		addStep("Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar\r\n"
				+ "Resultado esperado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 2.- Se selecciona una transferencia que no ha sido atendida por el usuario.
		 * Resultado esperado: Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
		 * -Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.
		 * Mostrara el Título de :"¿Como deseas comenzar?"
		 * Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada."
		 * Muestra los botones de:
		 * -"Recepción de pedido"
		 * -"Devoluciones a CEDIS"
		 */		
		addStep("Se selecciona una transferencia que no ha sido atendida por el usuario.\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
				+ "-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
				+ "Mostrara el Título de :\"¿Como deseas comenzar?\"\r\n"
				+ "Mostrara las Instrucciones de: \"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"\r\n"
				+ "Muestra los botones de:\r\n"
				+ "-\"Recepción de pedido\"\r\n"
				+ "-\"Devoluciones a CEDIS\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3.- Se selecciona la opción de Devoluciones a CEDIS
		 * Resultado esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
		 */		
		addStep("Se selecciona la opción de Devoluciones a CEDIS\r\n"
				+ "Resultado esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4.- Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla
		 * Resultado esperado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		 */		
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 5.- Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución
		 * Resultado esperado: Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR DEVOLUCIÓN".
		 */		
		addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a \"TERMINAR DEVOLUCIÓN\".");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6.- Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN
		 * Resultado esperado: Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas
		 */		
		addStep("Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7.- Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar
		 * Resultado esperado: Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada
		 */		
		addStep("Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar\r\n"
				+ "Resultado esperado: Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 8.- Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar
		 * Resultado esperado: Se muestra la sección de Sobrantes / Dañados
		 */		
		addStep("Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar \r\n"
				+ "Resultado esperado: Se muestra la sección de Sobrantes / Dañados");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 9.- Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar
		 * Resultado esperado: Se muestra la pantalla de Resumen Recepción con la información correspondiente
		 */		
		addStep("Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Resumen Recepción con la información correspondiente");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 10.- Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo
		 * Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de "cumplimiento de proceso de entrega" al final de la pantalla, arriba del botón "TERMINAR RECEPCIÓN".
		 */		
		addStep("Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de \"cumplimiento de proceso de entrega\" al final de la pantalla, arriba del botón \"TERMINAR RECEPCIÓN\".");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 11.- Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN
		 * Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.
		 * Se muestran los botones de
		 * -"MODIFICAR RECEPCIÓN"
		 * -"MODIFICAR DEVOLUCIONES"
		 * -"TERMINAR"
		 */		
		addStep("Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.\r\n"
				+ "Se muestran los botones de \r\n"
				+ "-\"MODIFICAR RECEPCIÓN\"\r\n"
				+ "-\"MODIFICAR DEVOLUCIONES\" \r\n"
				+ "-\"TERMINAR\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_017_TabletValidacionDeInicioDeFlujoDeTransferenciaDesdeDevolucionesACEDIS";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que inicia el flujo de la transferencia desde la opción de Devoluciones a CEDIS llegando hasta la pantalla de FUE";
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
		return "1.-Contar con Transferencias por atender en la tablet";
	}

	@Override
	public void beforeTest() {
	}

}
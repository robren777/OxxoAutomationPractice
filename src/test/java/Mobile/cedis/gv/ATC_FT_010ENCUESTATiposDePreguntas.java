package Mobile.cedis.gv;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_010ENCUESTATiposDePreguntas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_010ENCUESTATiposDePreguntas_test(
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

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		//transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
				
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		pantallatransferencia.TerminarHastaResumen();
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		resumen.FirmaAcuerdoSinEvidencia();
		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		acuerdo.TerminarRecepcion("SinEvidencia", null);
				
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		devolucion.SeleccionarObjeto("Continuar");
		devolucion.SeleccionarObjeto("Confirmar Seleccionar Bol");		
		
		Mobile.cedis.om.B07_ResumenDevolucion ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase, appiumUtil, this);		
		ResumenDevolucion.ClickFirmaAcuerdo("", "");
		
		Mobile.cedis.om.B08_AcuerdoDevolucion AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		AcuerdoDevolucion.FirmaPersonalTienda("", "");
		AcuerdoDevolucion.TerminarRecepcion("", "");
		
		Mobile.cedis.om.B09_FormatoUnicoEntrega FUE = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		FUE.ValidacionEncabezado();
		
		/*Paso 1 Posicionarse en la pantalla Encuesta de satisfacción.
		 * Resultado esperado: Se muestra correctamente la pantalla encuesta de satisfacción
		 */		
		addStep("Posicionarse en la pantalla Encuesta de satisfacción.\r\n"
				+ "Resultado esperado: Se muestra correctamente la pantalla encuesta de satisfacción");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					

		/*Paso 2 Se muestra el botón comenzar y se presiona
		 * Resultado esperado: El botón es funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción 
		 */		
		addStep("Se muestra el botón comenzar y se presiona\r\n"
				+ "Resultado esperado: El botón es funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción ");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3 Se muestra una pregunta de opción única  dentro de la sección 
		 * Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción única. (Solo se puede seleccionar una respuesta)
		 */		
		addStep("Se muestra una pregunta de opción única  dentro de la sección \r\n"
				+ "Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción única. (Solo se puede seleccionar una respuesta)");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					

		/*Paso 4 Selecciona la respuesta de la pregunta
		 * Resultado esperado: Se agrega correctamente la respuesta mediante opción única
		 */		
		addStep("Selecciona la respuesta de la pregunta\r\n"
				+ "Resultado esperado: Se agrega correctamente la respuesta mediante opción única");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 5 Se muestra una pregunta slider o control deslizante dentro de la sección 
		 * Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción única. (Slider, control deslizante)
		 */		
		addStep("Se muestra una pregunta slider o control deslizante dentro de la sección \r\n"
				+ "Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción única. (Slider, control deslizante)");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					

		/*Paso 6 Desplazar mediante el slider la respuesta de la pregunta
		 * Resultado esperado: Se agrega correctamente la respuesta mediante el slider
		 */		
		addStep("Desplazar mediante el slider la respuesta de la pregunta\r\n"
				+ "Resultado esperado: Se agrega correctamente la respuesta mediante el slider");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7 Se muestra una pregunta de opción numérica dentro de la sección 
		 * Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción numérica ( Donde los indicadores vayan desde el numero uno como muy malo hasta llegar al número cinco donde es excelente)
		 */		
		addStep("Se muestra una pregunta de opción numérica dentro de la sección \r\n"
				+ "Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción numérica ( Donde los indicadores vayan desde el numero uno como muy malo hasta llegar al número cinco donde es excelente)");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					

		/*Paso 8 Colocar un valor numérico en la respuesta
		 * Resultado esperado: Se coloca correctamente el valor numérico seleccionado en la respuesta de la pregunta
		 */		
		addStep("Colocar un valor numérico en la respuesta\r\n"
				+ "Resultado esperado: Se coloca correctamente el valor numérico seleccionado en la respuesta de la pregunta");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 9 Se muestra una pregunta de texto abierto dentro de la sección 
		 * Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción de texto abierto.
		 */		
		addStep("Se muestra una pregunta de texto abierto dentro de la sección \r\n"
				+ "Resultado esperado: La pregunta que se muestra permite que la respuesta sea mediante opción de texto abierto.");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					

		/*Paso 10 Colocar un comentario en la respuesta de la pregunta
		 * Resultado esperado: Se agrega correctamente la respuesta en el campo de texto abierto
		 */		
		addStep("Colocar un comentario en la respuesta de la pregunta\r\n"
				+ "Resultado esperado: Se agrega correctamente la respuesta en el campo de texto abierto");
		Thread.sleep(1000);	
		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
								
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC-FT-010- ENCUESTA - Tipos de preguntas";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en la primera pantalla de las preguntas de la encuestas se visualicen todas las opciones de respuesta.";
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
				+ "5. Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contraseña: 2\r\n"
				+ "6. Contar con dispositivo HH.\r\n"
				+ "7. Haber finalizado un flujo de recepción para que en cuanto lo finalices te arroje la encuesta.\r\n"
				+ "8.Haber cargado ASN´S a tu P.V";
	}

	@Override
	public void beforeTest() {
	}

}
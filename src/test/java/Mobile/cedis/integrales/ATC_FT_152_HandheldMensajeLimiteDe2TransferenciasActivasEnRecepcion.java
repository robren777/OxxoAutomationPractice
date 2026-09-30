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

public class ATC_FT_152_HandheldMensajeLimiteDe2TransferenciasActivasEnRecepcion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_152_HandheldMensajeLimiteDe2TransferenciasActivasEnRecepcion_test(
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
		herramienta.Cargando();	
		herramienta.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.MoverHacia("Transferencia");
		transferencia.ValidarCantidadTransferencias("3");
		
		transferencia.SeleccionarPrimeraTransferencia(true);
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");

		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();		
		pantallatransferencia.Tomar3Fotos();
		Thread.sleep(500);		
		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		pantallatransferencia.ValidarMensajeSalida();
		pantallatransferencia.SeleccionarObjeto("Desea Salir");		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		Thread.sleep(500);	
		loginRuta.SeleccionarObjeto("Entrar");
		transferencia.SeleccionarSegundaTransferencia(true);
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		pantallatransferencia.DeslizarArriba();		
		pantallatransferencia.Tomar3Fotos();
		Thread.sleep(500);		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		pantallatransferencia.ValidarMensajeSalida();
		pantallatransferencia.SeleccionarObjeto("Desea Salir");		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		Thread.sleep(500);	
		loginRuta.SeleccionarObjeto("Entrar");
			
				
		/*Paso 1 Estar en la pantalla "Recepcion de pedido Cedis" donde se muestran el listado de las transferencias a recepcionar
		 * Resultado esperado: Se muestra pantalla del listado de transferencias
		 */		
		addStep("Estar en la pantalla \"Recepcion de pedido Cedis\" donde se muestran el listado de las transferencias a recepcionar\r\n"
				+ "Resultado esperado: Se muestra pantalla del listado de transferencias");		
		Thread.sleep(500);				
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		
		/*Paso 2 En el listado existen 2 transferencias que estan pendientes de recepcionar pero que tuvieron un avance en el proceso de recepcion, son identificadas con un icono de reloj en señal que se encuentran en proceso
		 * Resultado esperado: Se muestran las transferencias avanzadas pendientes de recepcionar
		 */	
		addStep("En el listado existen 2 transferencias que estan pendientes de recepcionar pero que tuvieron un avance en el proceso de recepcion, son identificadas con un icono de reloj en señal que se encuentran en proceso\r\n"
				+ "Resultado esperado: Se muestran las transferencias avanzadas pendientes de recepcionar");
		transferencia.MoverHacia("Transferencia");
		transferencia.ValidacionIconoSDeDosTransferenciasEnProceso();
		Thread.sleep(500);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				
		/*Paso 3 Dentro del listado de transferencias selecciona la tercer transferencia a recepcionar
		 * Resultado esperado: Se muestra mensaje del sistema "Se alcanzó el límite de transferencias iniciadas para recepción (máx. 2). Favor de finalizar las transferencias anteriores para continuar." "ACEPTAR"
		 */		 
		addStep("Dentro del listado de transferencias selecciona la tercer transferencia a recepcionar\r\n"
				+ "Resultado esperado: Se muestra mensaje del sistema Se alcanzó el límite de transferencias iniciadas para recepción (máx. 2). Favor de finalizar las transferencias anteriores para continuar.\" \"ACEPTAR\"");
		transferencia.SeleccionarTerceraTransferencia(true);
		transferencia.ValidarMensajeLimite();		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		
		
		/*Paso 4 Selecciona ACEPTAR
		 * Resultado esperado: El sistema te mantiene en la pantalla de listado transferencias
		 */		
		addStep("Selecciona ACEPTAR\r\n"
				+ "Resultado esperado: El sistema te mantiene en la pantalla de listado transferencias");		
		transferencia.SeleccionarObjeto("Aceptar");
		transferencia.MoverHacia("Transferencia");		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
								
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override
	public String setTestFullName() {
		return "ATC-FT-152-Handheld Mensaje limite de 2 transferencias activas en recepcion";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al momento de querer ingresar a una tercera transferencia para su recepcion el sistema mande un mensaje";
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
		return "1.Tener 2 transferencias activas en recepcion\r\n"
				+ "2.Tener una transferencia a recepcionar ";
	}

	@Override
	public void beforeTest() {
	}

}
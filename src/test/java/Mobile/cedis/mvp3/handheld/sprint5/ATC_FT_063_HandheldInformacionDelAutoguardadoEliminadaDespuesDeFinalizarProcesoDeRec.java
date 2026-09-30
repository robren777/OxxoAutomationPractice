package Mobile.cedis.mvp3.handheld.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_063_HandheldInformacionDelAutoguardadoEliminadaDespuesDeFinalizarProcesoDeRec extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_063_HandheldInformacionDelAutoguardadoEliminadaDespuesDeFinalizarProcesoDeRec_test(
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
		transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");//linea agregada por Juan
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();		
		pantallatransferencia.Tomar3Fotos();
		Thread.sleep(500);
		String ValorTransferencia = pantallatransferencia.ValorTransferencia();
		
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));
		pantallatransferencia.MoverSeccion("Terminar");
		pantallatransferencia.SeleccionarObjeto("Terminar");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		pantallatransferencia.SeleccionarObjeto("No");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		pantallatransferencia.MoverSeccion("Sobrantes");
		pantallatransferencia.CapturaContenedorSobrantes("654654");
		pantallatransferencia.SeleccionarObjeto("Continuar Dañados");
		//Runtime.getRuntime().exec("adb shell input keyevent 4");
		pantallatransferencia.SeleccionarArticulo("491", "", "");
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");
		pantallatransferencia.MoverSeccion("Dañados");		
		pantallatransferencia.SeleccionarObjeto("Terminar Dañados");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS ResumenPedido = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		ResumenPedido.FirmaAcuerdoSinEvidencia();
		
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
		
		/*Paso 1 Estar en pantalla de "Formato Unico de Entrega"
		 * Resultado esperado: Se muestra pantalla FUE
		 */		
		addStep("Estar en pantalla de \"Formato Unico de Entrega\"\r\n"
				+ "Resultado esperado: Se muestra pantalla FUE");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		
		/*Paso 2 Dentro de la pantalla selecciona el boton "TERMINAR"
		 * Resultado esperado: Se realiza el proceso de envio de correo y si se finaliza con exito muestra mensaje "Recepcion de Pedido Existoso" "CONFIRMAR"
		 */	
		FUE.Terminar("tomaEvidencia", "Dentro de la pantalla selecciona el boton \"TERMINAR\"\r\n"
				+ "Resultado esperado: Se realiza el proceso de envio de correo y si se finaliza con exito muestra mensaje \"Recepcion de Pedido Existoso\" \"CONFIRMAR\"");
		
				
		/*Paso 3 Selecciona "CONFIRMAR"
		 * Resultado esperado: Se muestra Menu de Herramientas
		 */		
		addStep("Selecciona \"CONFIRMAR\"\r\n"
				+ "Resultado esperado: Se muestra Menu de Herramientas");
		Runtime.getRuntime().exec("adb shell input keyevent 3");// realiza la acción de Home en la handheld
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		
		/*Paso 4 Ingresa nuevamente a "Recepcion de Pedidos CEDIS"
		 * Resultado esperado: Se muestra el Login de recepcion 
		 */		
		addStep("Ingresa nuevamente a \"Recepcion de Pedidos CEDIS\"\r\n"
				+ "Resultado esperado: Se muestra el Login de recepcion");		
		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		Thread.sleep(2000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 5 Ingresa los datos en login correctamente y presiona "ENTRAR"
		 * Resultado esperado: Se muestra la primer pantalla de recepcion con el listado de transferencias 
		 */		
		addStep("Ingresa los datos en login correctamente y presiona \"ENTRAR\"\r\n"
				+ "Resultado esperado: Se muestra la primer pantalla de recepcion con el listado de transferencias ");
		Runtime.getRuntime().exec("adb shell input keyevent 3");// realiza la acción de Home en la handheld
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		
		/*Paso 6 Selecciona una transferencia disponible a recepcionar
		 * Resultado esperado: Ingresa al proceso para recepcionar el pedido sin problema, con los datos correctos de la transferencia
		 */		
		addStep("Selecciona una transferencia disponible a recepcionar\r\n"
				+ "Resultado esperado: Ingresa al proceso para recepcionar el pedido sin problema, con los datos correctos de la transferencia");		
		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		Thread.sleep(2000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
	
								
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override
	public String setTestFullName() {
		return "ATC_FT_063_HandheldInformacionDelAutoguardadoEliminadaDespuesDeFinalizarProcesoDeRec";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al finalizar la recepcion del pedido la informacion del autoguardado se elimine correctamente";
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
		return "1.-Tener una transferencia a recepcionar\r\n"
				+ "2.-Realizar el proceso de recepcion hasta llegar a Formato Unico de Entrega (Resumen)";
	}

	@Override
	public void beforeTest() {
	}

}
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

public class ATC_FT_190_FuncionalidadDeAutoguardadoDeRecepcionIngresandoPrimeramentePorLaOpcionDEVOLUCIONESACEDISEIngresandoPorSegundaVezConRECEPCIONDEPEDIDOHandheld extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_190_FuncionalidadDeAutoguardadoDeRecepcionIngresandoPrimeramentePorLaOpcionDEVOLUCIONESACEDISEIngresandoPorSegundaVezConRECEPCIONDEPEDIDOHandheld_test(
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
		
				
		/*1.-Se ubica en la pantalla: "Recepción de Pedido Cedis"
		Resultado Esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar
		 */		
		addStep("Se ubica en la pantalla: \"Recepción de Pedido Cedis\"\r\n"
				+ "		Resultado Esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar");		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		

		/*2.-Se da clic en una de las transferencias nuevas
		Resultado Esperado: Se muestra de forma correcta la nueva pantalla de Recepción donde se tiene la leyenda ¿COMO DESEAS COMENZAR? Y dos opciones "RECEPCIÓN DE PEDIDO" y "DEVOLUCIONES A CEDIS"
		 */		
		addStep("Se da clic en una de las transferencias nuevas\r\n"
				+ "		Resultado Esperado: Se muestra de forma correcta la nueva pantalla de Recepción donde se tiene la leyenda ¿COMO DESEAS COMENZAR? Y dos opciones \"RECEPCIÓN DE PEDIDO\" y \"DEVOLUCIONES A CEDIS\"");
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
		Thread.sleep(1500);
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				

		/*3.-Se da clic en la opción de "DEVOLUCIONES A CEDIS"
		Resultado Esperado: Se visualiza la pantalla del proceso de DEVOLUCIONES
		 */		
		addStep("Se da clic en la opción de \"DEVOLUCIONES A CEDIS\"\r\n"
				+ "		Resultado Esperado: Se visualiza la pantalla del proceso de DEVOLUCIONES");		
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		Thread.sleep(500);
		devolucion.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*4.-Se realiza el proceso de Devolución:
		* Seleccionar BOL
		* Registrar la cantidad de Devolución de Contenedores en:
		   Canastillas (Azul, Amarilla, Verde, Naranja, Otros)
		   Contenedores (Tapas de contenedores, Envases)
		* Toma de fotografía (evidencia)
		* Registrar Canastillas Con Devolución (escanear o ingresar manualmente SKU)"
		Resultado Esperado: Se realiza el proceso de Devolución de manera correcta.
		 */		
		addStep("Se realiza el proceso de Devolución:\r\n"
				+ "		* Seleccionar BOL\r\n"
				+ "		* Registrar la cantidad de Devolución de Contenedores en:\r\n"
				+ "		   Canastillas (Azul, Amarilla, Verde, Naranja, Otros)\r\n"
				+ "		   Contenedores (Tapas de contenedores, Envases)\r\n"
				+ "		* Toma de fotografía (evidencia)\r\n"
				+ "		* Registrar Canastillas Con Devolución (escanear o ingresar manualmente SKU)\r\n"
				+ "		Resultado Esperado: Se realiza el proceso de Devolución de manera correcta.");		
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		devolucion.AumentarCanastilla(2, "Azul");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		devolucion.MoverSeccion("Contenedores");
		devolucion.AumentarContenedores(3, "Tapas");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		devolucion.TomarFotoDevolucion();
		Thread.sleep(500);
		devolucion.IngresarCanastilla("654654");
		devolucion.TomarFotoCanastilla();
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*5.-Se da clic en el botón de CONTINUAR
		Resultado Esperado: Se dirige a la pantalla de RESUMEN DE DEVOLUCIÓN
		 */		
		addStep("Se da clic en el botón de CONTINUAR\r\n"
				+ "		Resultado Esperado: Se dirige a la pantalla de RESUMEN DE DEVOLUCIÓN");		
		devolucion.SeleccionarObjeto("Continuar");		
		Thread.sleep(500);
		Mobile.cedis.om.B07_ResumenDevolucion ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase, appiumUtil, this);
		ResumenDevolucion.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.BuscarSeccion("Sección Devolución");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.ClickFirmaAcuerdo("SinEvidencia", "");		

		/*6.-Se da clic en el botón de FIRMA DE ACUERDO al final de la pantalla cuando se haya verificado que la información del Resumen sea correcta.
		Resultado Esperado: Se manda a la pantalla de FIRMA DE ACUERDO
		 */		
		addStep("Se da clic en el botón de FIRMA DE ACUERDO al final de la pantalla cuando se haya verificado que la información del Resumen sea correcta.\r\n"
				+ "		Resultado Esperado: Se manda a la pantalla de FIRMA DE ACUERDO");		
		Mobile.cedis.om.B08_AcuerdoDevolucion AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		Thread.sleep(500);
		AcuerdoDevolucion.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		

		/*7.-Se realizan las dos firmas que pide la aplicación:
		* Firma de área Distribución
		* Firma de Personal Tienda"
		Resultado Esperado: Se muestran da las dos firmas de forma correcta.
		 */		
		addStep("Se realizan las dos firmas que pide la aplicación:\r\n"
				+ "		* Firma de área Distribución\r\n"
				+ "		* Firma de Personal Tienda\"\r\n"
				+ "		Resultado Esperado: Se muestran da las dos firmas de forma correcta.");		
		AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		AcuerdoDevolucion.FirmaPersonalTienda("", "");		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*8.-Se da clic en el botón de TERMINAR DEVOLUCIÓN ,al igual dar clic en el botón de CONFIRMAR en el mensaje "¿Esta seguro que ha terminado con el registro de devoluciones de mercancía y canastillas?"
		Resultado Esperado: Se dirige a la pantalla de RECEPCIÓN DE PEDIDO A CEDIS
		 */
		addStep("Se da clic en el botón de TERMINAR DEVOLUCIÓN ,al igual dar clic en el botón de CONFIRMAR en el mensaje \"¿Esta seguro que ha terminado con el registro de devoluciones de mercancía y canastillas?\"\r\n"
				+ "		Resultado Esperado: Se dirige a la pantalla de RECEPCIÓN DE PEDIDO A CEDIS");		
		AcuerdoDevolucion.SeleccionarObjeto("Terminar Devolución");
		AcuerdoDevolucion.ValidarMensajeTerminadoRegistro();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		AcuerdoDevolucion.SeleccionarObjeto("Confirmar Terminar Registro");
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		Thread.sleep(500);
		pantallatransferencia.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				

		/*9.-Se toman las 3 fotografías/evidencias, al igual se escanean algunos bultos que se tenga en el pedido sin dejar ninguna sección vacía (en caso de que alguna sección contenga una cantidad total de pedido o que no este en cero.)
		Resultado Esperado: Se muestran las tres fotografias se contabilizan de manera correcta los bultos que se han escaneado
		 */		
		addStep("Se toman las 3 fotografías/evidencias, al igual se escanean algunos bultos que se tenga en el pedido sin dejar ninguna sección vacía (en caso de que alguna sección contenga una cantidad total de pedido o que no este en cero.)\r\n"
				+ "		Resultado Esperado: Se muestran las tres fotografias se contabilizan de manera correcta los bultos que se han escaneado");		
		pantallatransferencia.Tomar3Fotos();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		pantallatransferencia.MoverSeccion("Terminar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				

		/*10.-Dar clic para atrás la pantalla (primer icono ubicado abajo de la pantalla a la izquierda)
		Resultado Esperado: Se muestra un mensaje con el texto: “¿Estas seguro que deseas salir. Se guardaran solo las fotografías de entrega y los bultos escaneados hasta el momento.” Con las opciones de CANCELAR y SALIR.
		 */		
		addStep("Dar clic para atrás la pantalla (primer icono ubicado abajo de la pantalla a la izquierda)\r\n"
				+ "		Resultado Esperado: Se muestra un mensaje con el texto: “¿Estas seguro que deseas salir. Se guardaran solo las fotografías de entrega y los bultos escaneados hasta el momento.” Con las opciones de CANCELAR y SALIR.");		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		pantallatransferencia.ValidarMensajeSalida();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		

		/*11.-Se da clic en SALIR.
		Resultado Esperado: Se manda a la pantalla de RESUMEN DE Devolución
		 */		
		addStep("Se da clic en SALIR.\r\n"
				+ "		Resultado Esperado: Se manda a la pantalla de RESUMEN DE Devolución");		
		pantallatransferencia.SeleccionarObjeto("Desea Salir");		
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		

		/*12.-Se dan varios clics para atrás la pantalla (primer icono ubicado abajo de la pantalla a la izquierda) hasta llegar a la pantalla de LOGIN DE RUTA
		Resultado Esperado: Se visualiza la pantalla de LOGIN de RUTA
		 */		
		addStep("Se dan varios clics para atrás la pantalla (primer icono ubicado abajo de la pantalla a la izquierda) hasta llegar a la pantalla de LOGIN DE RUTA\r\n"
				+ "		Resultado Esperado: Se visualiza la pantalla de LOGIN de RUTA");				
		Runtime.getRuntime().exec("adb shell input keyevent 4");// Regresar a la pantalla de Como Desea Comenzar
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// Regresar a la pantalla de Listado de transferencia
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		Runtime.getRuntime().exec("adb shell input keyevent 4");// Regresar a la pantalla de Login de Ruta
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*13.-Dar clic en ENTRAR teniendo el Login de RUTA.
		Resultado Esperado: Se visualiza la pantalla de Recepción de Pedido CEDIS con la transferencia con el símbolo de reloj 
		 */		
		addStep("Dar clic en ENTRAR teniendo el Login de RUTA.\r\n"
				+ "		Resultado Esperado: Se visualiza la pantalla de Recepción de Pedido CEDIS con la transferencia con el símbolo de reloj");		
		loginRuta.SeleccionarObjeto("Entrar");
		transferencia.MoverHacia("Reloj");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*14.-Ingresar a la transferencia que ya se avanzo (con el símbolo de reloj)
		Resultado Esperado: Se muestra la nueva pantalla de Recepción donde se tiene la leyenda ¿COMO DESEAS COMENZAR? Y dos opciones "RECEPCIÓN DE PEDIDO" y "DEVOLUCIONES A CEDIS"
		 */
		addStep("Ingresar a la transferencia que ya se avanzo (con el símbolo de reloj)\r\n"
				+ "		Resultado Esperado: Se muestra la nueva pantalla de Recepción donde se tiene la leyenda ¿COMO DESEAS COMENZAR? Y dos opciones \"RECEPCIÓN DE PEDIDO\" y \"DEVOLUCIONES A CEDIS\"");		
		transferencia.SeleccionarObjeto("Reloj");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		

		/*15.-Estando en esta pantalla ahora se da clic en la opción de "RECEPCIÓN DE PEDIDO"
		Resultado Esperado: Se muestra la pantalla de RECEPCIÓN DE PEDIDOS Se valida que la información ingresada anteriormente (fotografías tomadas y bultos escaneados se mantenga de manera correcta.
		 */
		addStep("Estando en esta pantalla ahora se da clic en la opción de \"RECEPCIÓN DE PEDIDO\"\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de RECEPCIÓN DE PEDIDOS Se valida que la información ingresada anteriormente (fotografías tomadas y bultos escaneados se mantenga de manera correcta.");		
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.DeslizarArriba();
		Thread.sleep(500);
		pantallatransferencia.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
							
										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC-FT-190-Funcionalidad de Autoguardado de Recepcion ingresando primeramente por la opcion \"DEVOLUCIONES A CEDIS\" e ingresando por segunda vez con \"RECEPCION DE PEDIDO\" (Handheld)";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al comenzar el flujo con la opción \"DEVOLUCIONES A CEDIS\",  salir del proceso con información registrada e ingresando nuevamente al mismo pedido pero con la opción de \"RECEPCIÓN DE PEDIDO\" desempeñe de manera correcta la funcionalidad del autoguardado de Recepción.";
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
		return "*Contar con la versión de la apk mas actual\r\n"
				+ "*Haber cargado transferencias en el P.V\r\n"
				+ "*Contar con dispositivo HandHeld\r\n"
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
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

public class ATC_FT_191_HappypathProcesoDeRecepcionYDevolucionDesdeLaOpcionRECEPCIONDEPEDIDOHandheld extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_191_HappypathProcesoDeRecepcionYDevolucionDesdeLaOpcionRECEPCIONDEPEDIDOHandheld_test(
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
		herramienta.Cargando();	
		herramienta.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
				
		/*1.-Se ubica en la pantalla: "Recepción de Pedido Cedis" en el listado de Transferencias
		Resultado Esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar
		 */		
		addStep("Se ubica en la pantalla: \"Recepción de Pedido Cedis\" en el listado de Transferencias\r\n"
				+ "		Resultado Esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar");		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	

		/*2.-Se da clic en una de las transferencias
		Resultado Esperado: Se muestra de forma correcta la nueva pantalla de Recepción donde se tiene la leyenda ¿COMO DESEAS COMENZAR? Y dos opciones "RECEPCIÓN DE PEDIDO" y "DEVOLUCIONES A CEDIS"
		 */		
		addStep("Se da clic en una de las transferencias\r\n"
				+ "		Resultado Esperado: Se muestra de forma correcta la nueva pantalla de Recepción donde se tiene la leyenda ¿COMO DESEAS COMENZAR? Y dos opciones \"RECEPCIÓN DE PEDIDO\" y \"DEVOLUCIONES A CEDIS\"");		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
		Thread.sleep(1500);
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*3.-Se da clic en la opción de "RECEPCIÓN DE PEDIDO"
		Resultado Esperado: Se visualiza la pantalla del proceso de RECEPCIÓN DE PEDIDO A CEDIS con la información de la tienda, icono de la cámara.
		 */		
		addStep("Se da clic en la opción de \"RECEPCIÓN DE PEDIDO\"\r\n"
				+ "		Resultado Esperado: Se visualiza la pantalla del proceso de RECEPCIÓN DE PEDIDO A CEDIS con la información de la tienda, icono de la cámara.");		
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		Thread.sleep(500);
		pantallatransferencia.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*4.-Se realiza el proceso de la pantalla de Recepción:
		* Toma de 3 fotografías (Evidencias)
		* Escanear bultos sin dejar ninguna sección vacía (en caso de que alguna sección contenga una cantidad total de pedido o que no este en cero.)
		* Opcional: Registrar Cajas Sin Etiqueta / Etiqueta Dañadas en caso de dejar algunas cajas sin escanear
		* Registrar bultos Sobrantes (escanear o ingresar manualmente SKU)
		* Registrar bultos Dañados (escanear o ingresar manualmente SKU)"
		Resultado Esperado: Se realiza el proceso de Recepción de manera correcta.
		 */		
		addStep("Se realiza el proceso de la pantalla de Recepción:\r\n"
				+ "		* Toma de 3 fotografías (Evidencias)\r\n"
				+ "		* Escanear bultos sin dejar ninguna sección vacía (en caso de que alguna sección contenga una cantidad total de pedido o que no este en cero.)\r\n"
				+ "		* Opcional: Registrar Cajas Sin Etiqueta / Etiqueta Dañadas en caso de dejar algunas cajas sin escanear\r\n"
				+ "		* Registrar bultos Sobrantes (escanear o ingresar manualmente SKU)\r\n"
				+ "		* Registrar bultos Dañados (escanear o ingresar manualmente SKU)\"\r\n"
				+ "		Resultado Esperado: Se realiza el proceso de Recepción de manera correcta.");		
		pantallatransferencia.Tomar3Fotos();		
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.MoverSeccion("Terminar");
		pantallatransferencia.SeleccionarObjeto("Terminar");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		pantallatransferencia.MoverSeccion("Sobrantes");		
		pantallatransferencia.CapturaContenedorSobrantes("654654");
		pantallatransferencia.SeleccionarObjeto("Continuar Dañados");
		pantallatransferencia.SeleccionarArticulo(data.get("articulo"), "", "");	
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");
		pantallatransferencia.MoverSeccion("Dañados");		
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Terminar Dañados");
		

		/*5.-Se da clic en el botón de TERMINAR una vez completado el proceso de Recepción, al igual dar clic en el botón de CONFIRMAR en el mensaje "¿Esta seguro que ha terminado de capturar los Sobrantes y Dañados del pedido?"
		Resultado Esperado: Se despliega la pantalla de RESUMEN ENTREGA DE PEDIDO
		 */		
		addStep("Se da clic en el botón de TERMINAR una vez completado el proceso de Recepción, al igual dar clic en el botón de CONFIRMAR en el mensaje \"¿Esta seguro que ha terminado de capturar los Sobrantes y Dañados del pedido?\"\r\n"
				+ "		Resultado Esperado: Se despliega la pantalla de RESUMEN ENTREGA DE PEDIDO");		
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Confirmar");		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*6.-Se da clic en el botón de FIRMA DE ACUERDO al final de la pantalla cuando se haya verificado que la información del Resumen sea correcta.
		Resultado Esperado: Se manda a la pantalla de FIRMA DE ACUERDO
		 */		
		addStep("Se da clic en el botón de FIRMA DE ACUERDO al final de la pantalla cuando se haya verificado que la información del Resumen sea correcta.\r\n"
				+ "		Resultado Esperado: Se manda a la pantalla de FIRMA DE ACUERDO");
		Thread.sleep(1000);
		Mobile.cedis.om.B03_ResumenPedidosCEDIS ResumenPedido = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);			
		ResumenPedido.SeleccionarObjeto("Leyenda Faltante Refrigerado");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenPedido.SeleccionarObjeto("Total Dañados");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		ResumenPedido.FirmaAcuerdoSinEvidencia();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*7.-Se realizan las dos firmas que pide la aplicación:
		* Firma de área Distribución
		* Firma de Personal Tienda
		(En dado caso de que en el proceso de Recepción queden bultos faltantes por escanear y al dar clic en la firma Personal de Tienda aparezca el mensaje: Hay ""N"" canastillas y ""N"" cajas faltantes, ¿desea continuar a la firma?, dar clic en CONTINUAR)"
		Resultado Esperado: Se muestran de forma correcta las dos firmas
		 */		
		addStep("Se realizan las dos firmas que pide la aplicación:\r\n"
				+ "		* Firma de área Distribución\r\n"
				+ "		* Firma de Personal Tienda\r\n"
				+ "		(En dado caso de que en el proceso de Recepción queden bultos faltantes por escanear y al dar clic en la firma Personal de Tienda aparezca el mensaje: Hay \"\"N\"\" canastillas y \"\"N\"\" cajas faltantes, ¿desea continuar a la firma?, dar clic en CONTINUAR)\"\r\n"
				+ "		Resultado Esperado: Se muestran de forma correcta las dos firmas");		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*8.-Se da clic en el botón de TERMINAR RECEPCIÓN ,al igual dar clic en el botón de CONFIRMAR en el mensaje "¿Esta seguro que ha terminado de realizar la recepción de los bultos del pedido?"
		Resultado Esperado: Se dirige a la pantalla de DEVOLUCIONES A CEDIS 
		 */		
		addStep("Se da clic en el botón de TERMINAR RECEPCIÓN ,al igual dar clic en el botón de CONFIRMAR en el mensaje \"¿Esta seguro que ha terminado de realizar la recepción de los bultos del pedido?\"\r\n"
				+ "		Resultado Esperado: Se dirige a la pantalla de DEVOLUCIONES A CEDIS ");		
		acuerdo.SeleccionarObjeto("Terminar Recepcion");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		acuerdo.SeleccionarObjeto("Confirmar");
		Thread.sleep(1500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*9.-Se realiza el proceso de Devolución:
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
				+ "		* Registrar Canastillas Con Devolución (escanear o ingresar manualmente SKU)\"\r\n"
				+ "		Resultado Esperado: Se realiza el proceso de Devolución de manera correcta.");		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
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
		Thread.sleep(4000);
		devolucion.IngresarCanastilla("654654");
		devolucion.TomarFotoCanastilla();
		Thread.sleep(4000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*10.-Se da clic en el botón de CONTINUAR
		Resultado Esperado: Se dirige a la pantalla de RESUMEN DE DEVOLUCIÓN
		 */		
		addStep("Se da clic en el botón de CONTINUAR\r\n"
				+ "		Resultado Esperado: Se dirige a la pantalla de RESUMEN DE DEVOLUCIÓN");		
		Thread.sleep(1000);	
		devolucion.SeleccionarObjeto("Continuar");		
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*11.-Se da clic en el botón de FIRMA DE ACUERDO al final de la pantalla cuando se haya verificado que la información del Resumen sea correcta.
		Resultado Esperado: Se manda a la pantalla de FIRMA DE ACUERDO
		 */		
		addStep("Se da clic en el botón de FIRMA DE ACUERDO al final de la pantalla cuando se haya verificado que la información del Resumen sea correcta.\r\n"
				+ "		Resultado Esperado: Se manda a la pantalla de FIRMA DE ACUERDO");		
		Mobile.cedis.om.B07_ResumenDevolucion ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase, appiumUtil, this);
		ResumenDevolucion.BuscarSeccion("Sección Canastillas");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.BuscarSeccion("Sección Bols");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.BuscarSeccion("Sección Devolución");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.ClickFirmaAcuerdo("SinEvidencia", "");		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*12.-Se realizan las dos firmas que pide la aplicación:
		* Firma de área Distribución
		* Firma de Personal Tienda
		También se da clic en la casilla de ""Se cumplió con el proceso de entrega"" "
		Resultado Esperado: Se muestran da las dos firmas y  la casilla del check de forma correcta.
		 */		
		addStep("Se realizan las dos firmas que pide la aplicación:\r\n"
				+ "		* Firma de área Distribución\r\n"
				+ "		* Firma de Personal Tienda\r\n"
				+ "		También se da clic en la casilla de \"\"Se cumplió con el proceso de entrega\"\" \"\r\n"
				+ "		Resultado Esperado: Se muestran da las dos firmas y  la casilla del check de forma correcta.");		
		Mobile.cedis.om.B08_AcuerdoDevolucion AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		AcuerdoDevolucion.FirmaPersonalTienda("", "");
		AcuerdoDevolucion.SeleccionarObjeto("Cumplio Proceso");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		
		/*13.-Se da clic en el botón de TERMINAR DEVOLUCIÓN ,al igual dar clic en el botón de CONFIRMAR en el mensaje "¿Esta seguro que ha terminado con el registro de las devoluciones de mercancía y canastillas?"
		Resultado Esperado: Se dirige a la pantalla del FORMATO ÚNICO DE ENTREGA (FUE)
		 */
		addStep("Se da clic en el botón de TERMINAR DEVOLUCIÓN ,al igual dar clic en el botón de CONFIRMAR en el mensaje \"¿Esta seguro que ha terminado con el registro de las devoluciones de mercancía y canastillas?\"\r\n"
				+ "		Resultado Esperado: Se dirige a la pantalla del FORMATO ÚNICO DE ENTREGA (FUE)");		
		AcuerdoDevolucion.SeleccionarObjeto("Terminar Devolución");
		AcuerdoDevolucion.ValidarMensajeTerminadoRegistro();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		AcuerdoDevolucion.SeleccionarObjeto("Confirmar Terminar Registro");
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*14.-Se da clic en el botón de TERMINAR al final de la pantalla cuando se haya verificado que la información del FUE sea correcta.
		Resultado Esperado: Se termina el proceso de Recepción de manera satisfactoria con el mensaje "Recepción de Pedido Exitoso."
		 */		
		addStep("Se da clic en el botón de TERMINAR al final de la pantalla cuando se haya verificado que la información del FUE sea correcta.\r\n"
				+ "		Resultado Esperado: Se termina el proceso de Recepción de manera satisfactoria con el mensaje \"Recepción de Pedido Exitoso.\"");
		Mobile.cedis.om.B09_FormatoUnicoEntrega FUE = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		FUE.ValidacionEncabezado();		
		FUE.BuscarSeccion("Entregados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Sin etiqueta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Faltantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		FUE.BuscarSeccion("Sobrantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Dañados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Firma Recepción");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Devolución Canastillas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Canastillas con Devolución");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Devolución Contenedores");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Firma Devolución");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Movimiento Tienda");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Botones");		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		FUE.RecepcionExitosa();					
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.SeleccionarObjeto("Confirmar Pedido");
										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC-FT-191-HappyPath-Proceso de Recepcion y Devolucion desde la opcion \"RECEPCION DE PEDIDO\" (Handheld)";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en la opción \"RECEPCIÓN DE PEDIDO\" desde la nueva pantalla se pueda realizar de manera correcta el proceso de recepción de acuerdo al flujo (pantallas) original.";
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
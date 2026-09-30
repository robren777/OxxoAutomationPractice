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

public class ATC_FT_192_HandheldValidacionDeInicioDeFlujoDeTransferenciaDesdeDevolucionesACEDIS extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_192_HandheldValidacionDeInicioDeFlujoDeTransferenciaDesdeDevolucionesACEDIS_test(
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
		
		/*1.-Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar
		Resultado Esperado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		 */		
		addStep("Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");		
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				

		/*2.-Se selecciona una transferencia que no ha sido atendida por el usuario.
		Resultado Esperado: "Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
		*Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.
		*Mostrara el Titulo de :""¿Como deseas comenzar?""
		*Mostrara las Instrucciones de: ""Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.""
		*Muestra los botones de:
		- Recepción de Pedido
		-Devoluciones a CEDIS"
		 */		
		addStep("Se selecciona una transferencia que no ha sido atendida por el usuario.\r\n"
				+ "		Resultado Esperado: \"Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
				+ "		*Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
				+ "		*Mostrara el Titulo de :\"\"¿Como deseas comenzar?\"\"\r\n"
				+ "		*Mostrara las Instrucciones de: \"\"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"\"\r\n"
				+ "		*Muestra los botones de:\r\n"
				+ "		- Recepción de Pedido\r\n"
				+ "		-Devoluciones a CEDIS");
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);		
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		DeseaComenzar.ValidacionEncabezado();
		Thread.sleep(500);
		DeseaComenzar.ValidarInstrucciones();
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				

		/*3.-Se selecciona la opción de Devoluciones a CEDIS
		Resultado Esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
		 */		
		addStep("Se selecciona la opción de Devoluciones a CEDIS\r\n"
				+ "		Resultado Esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");		
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");		
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				

		/*4.-Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.
		Resultado Esperado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		 */		
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes");		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		devolucion.ValidacionEncabezado();
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
		devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
				

		/*5.-Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución 
		Resultado Esperado: Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones Se renombrara a "TERMINAR DEVOLUCIÓN".
		 */		
		addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución \r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones Se renombrara a \"TERMINAR DEVOLUCIÓN\".");		
		Mobile.cedis.om.B07_ResumenDevolucion ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase, appiumUtil, this);		
		ResumenDevolucion.BuscarSeccion("Sección Contenedores");
		Thread.sleep(500);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.BuscarSeccion("Sección Devolución");
		Thread.sleep(500);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenDevolucion.ClickFirmaAcuerdo("", "");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				

		/*6.-Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN
		Resultado Esperado: Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas
		 */		
		addStep("Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas");		
		Mobile.cedis.om.B08_AcuerdoDevolucion AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		AcuerdoDevolucion.FirmaPersonalTienda("", "");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		AcuerdoDevolucion.SeleccionarObjeto("Terminar Devolución");
		Thread.sleep(500);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		AcuerdoDevolucion.SeleccionarObjeto("Confirmar");			
		Thread.sleep(5500);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				

		/*7.-Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar
		Resultado Esperado: Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada
		 */		
		addStep("Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar\r\n"
				+ "		Resultado Esperado: Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada");		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		pantallatransferencia.MoverSeccion("Terminar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Terminar");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Si");
		
		
		/*8.-Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar 
		Resultado Esperado: Se muestra la sección de Sobrantes / Dañados
		 */		
		addStep("Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar \r\n"
				+ "		Resultado Esperado: Se muestra la sección de Sobrantes / Dañados");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.RegistrarSinEtiqueta();		
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		pantallatransferencia.SeleccionarObjeto("Confirmar");		
		pantallatransferencia.MoverSeccion("Sobrantes");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
	
		/*9.-Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar
		Resultado Esperado: Se muestra la pantalla de Resumen Recepción con la información correspondiente
		 */		
		addStep("Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Resumen Recepción con la información correspondiente");
		pantallatransferencia.CapturaContenedorSobrantes("654654");
		pantallatransferencia.SeleccionarObjeto("Continuar Dañados");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarArticulo(data.get("articulo"), "", "");	
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");
		pantallatransferencia.MoverSeccion("Dañados");		
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Terminar Dañados");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
			

		/*10.-Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo
		Resultado Esperado: Se muestra la pantalla de Acuerdo de Entrega en la Se muestra la información correspondiente y donde se mostrara el checkbox de "cumplimiento de proceso de entrega" al final de la pantalla, arriba del botón "TERMINAR RECEPCIÓN".
		 */		
		addStep("Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Acuerdo de Entrega en la Se muestra la información correspondiente y donde se mostrara el checkbox de \"cumplimiento de proceso de entrega\" al final de la pantalla, arriba del botón \"TERMINAR RECEPCIÓN\".");		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		Thread.sleep(500);	
		resumen.ValidacionEncabezado();
		resumen.SeleccionarObjeto("Total Cajas Sin Etiqueta");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		resumen.SeleccionarObjeto("Sobrantes Total Sobrantes");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		resumen.SeleccionarObjeto("Total Dañados");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		resumen.FirmaAcuerdoSinEvidencia();
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				

		/*11.-Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN
		Resultado Esperado: "Se muestra la pantalla de Formato único de Entrega con sus campos correspondiente. Se muestran los botones de:
		*MODIFICAR RECEPCIÓN
		*MODIFICAR DEVOLUCIONES
		*TERMINAR"
		 */		
		addStep("Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN\r\n"
				+ "		Resultado Esperado: \"Se muestra la pantalla de Formato único de Entrega con sus campos correspondiente. Se muestran los botones de:\r\n"
				+ "		*MODIFICAR RECEPCIÓN\r\n"
				+ "		*MODIFICAR DEVOLUCIONES\r\n"
				+ "		*TERMINAR");		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		acuerdo.SeleccionarObjeto("Terminar Recepcion");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		acuerdo.SeleccionarObjeto("Confirmar");
				
		Mobile.cedis.om.B09_FormatoUnicoEntrega FUE = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		Thread.sleep(500);
		FUE.ValidacionEncabezado();		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Botones");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC-FT-192-Handheld Validacion de inicio de flujo de transferencia desde Devoluciones a CEDIS";
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
		return "*Contar con Transferencias por atender en la handheld";
	}

	@Override
	public void beforeTest() {
	}

}
package Mobile.cedis.integrales;

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
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B04_AcuerdoEntrega;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B09_FormatoUnicoEntrega;
import Mobile.cedis.om.B10_DeseaComenzar;
import Mobile.cedis.om.B11_EncuestaSatisfaccion;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_228_EncuestaOpcionContestarEnOtroMomento extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_228_EncuestaOpcionContestarenOtroMomento_test(
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
		

		//Abre la aplicaci�n 
    	Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
    	
    	// Ejecuta el logeo hacia el punto de venta
    			System.out.println("\nLogeandose en el Punto de Venta Oxxo al dia...");
    			Thread.sleep(3000);
    			Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
    			login.loginPV(data.get("ip"));
    			Thread.sleep(3000);

    			// Ingresar al menu principal de Oxxo al dia
    			System.out.println("\nIngresando al menu principal de Oxxo al dia...");
    			Thread.sleep(3000);
    			Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
    			loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
    			Thread.sleep(3000);

    			// Seleccionar el icono de Excelencia Operativa
    			Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
    			System.out.println("\nDando clic en Excelencia Operativa...");
    			Thread.sleep(2000);
    			herramienta.SeleccionarObjeto("Excelencia");
    			Thread.sleep(3000);

    			// Seleccionar el icono de Herramienta de Compra
    			System.out.println("\nDando clic en Herramienta de Compra...");
    			Thread.sleep(2000);
    			herramienta.SeleccionarObjeto("Herramienta");
    			Thread.sleep(3000);

    			// Seleccionar el icono de Cometa.
    			System.out.println("\nDando clic en Cometa...");
    			Thread.sleep(3000);
    			herramienta.SeleccionarObjeto("Cometa");
    			Thread.sleep(3000);

    			// Clic en Recepcion de Pedidos CEDIS
    			System.out.println("\nDando clic en Recepcion de Pedidos CEDIS...");
    			Thread.sleep(3000);
    			Mobile.cedis.om.A04_MenuHerramientas C04MenuHerramienta = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil,
    					this);
    			C04MenuHerramienta.clickBtnRecepcionPedidosCEDIS();
    			Thread.sleep(3000);

    			// Login de Area de Distribucion
    			System.out.println("\nLogeando Area de Distribucion...");
    			Thread.sleep(3000);
    			Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
    					appiumUtil, this);
    			C05LoginAreaDistribucion.LoginAreaDistribucion();
    			Thread.sleep(3000);

    			// Dando clic en Recepcion Sin Escaner
    			Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia B01_RecepcionPedidosSeleccionarTransferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(
    					testCase, appiumUtil, this);
    			System.out.println("\nDando clic en Switch Recepcion Sin Escaner y Entrando en la Primer Transferencia...");
    			Thread.sleep(3000);
    			B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);
    			Thread.sleep(3000);
		
    			
		B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		B03_ResumenPedidosCEDIS ResumenCedis = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		B04_AcuerdoEntrega Acuerdo = new B04_AcuerdoEntrega(testCase, appiumUtil, this);		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		B07_ResumenDevolucion Resumen = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion AcuerdoDev = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B09_FormatoUnicoEntrega FueRec = new B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		B10_DeseaComenzar POM_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
		B11_EncuestaSatisfaccion Encuesta = new B11_EncuestaSatisfaccion(testCase, appiumUtil, this);

		POM_ListadoDeRecepcion.ValidacionEncabezado();
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(3000);
		System.out.println("Se muestra la pantalla Recepci�n de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
		Thread.sleep(2000);
		POM_DeseaComenzar.Cargando();
		POM_DeseaComenzar.ValidacionEncabezado();
		Thread.sleep(6000);
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(3000);
		System.out.println("Se muestra correctamente la pantalla de Recepci�n de Pedidos CEDIS  �Como deseas comenzar?");
		POM_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(3000);
		System.out.println("Se inicia el flujo de la transferencia mostrando la pantalla de recepcion de pedido cedis");
		PedidoRecepcion.DeslizarArriba();
		Thread.sleep(2000);
		System.out.println("Se muestra la pantalla de recepcion pedido cedis");
		PedidoRecepcion.Tomar3Fotos();
		System.out.println("Ingresando contenedores\n");
		Thread.sleep(2000);
		PedidoRecepcion.CapturaContenedor(data.get("contenedor"));
		System.out.println("Terminando Recepcion\n");
		Thread.sleep(2000);
		PedidoRecepcion.MoverSeccion("Terminar");
		Thread.sleep(2000);
		PedidoRecepcion.SeleccionarObjeto("Terminar");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		PedidoRecepcion.SeleccionarObjeto("Si");
		Thread.sleep(2000);
		PedidoRecepcion.MoverSeccion("Cajas sin etiqueta");
		System.out.println("Se muestra el proceso de cajas sin etiqueta, la cual incluye envases");
		PedidoRecepcion.RegistrarSinEtiqueta();
		System.out.println("Registrando datos de caja sin etiqueta\n");
		Runtime.getRuntime().exec("adb shell input swipe 21 1160 23 900 40");// Se desliza hacía abajo
		Thread.sleep(5000);
		PedidoRecepcion.SeleccionarObjeto("Continuar a Faltantes");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		
		PedidoRecepcion.SeleccionarObjeto("Continuar Dañados");		
		PedidoRecepcion.SeleccionarObjeto("Terminar Dañados");
		PedidoRecepcion.SeleccionarObjeto("Confirmar Dañados");
		ResumenCedis.SeleccionarObjeto("Firma Acuerdo");
		Acuerdo.FirmaAreaDistribucion("", "");
		Acuerdo.FirmaPersonalTienda("", "");
		System.out.println("Se ingresan firmas\n");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Acuerdo.SeleccionarObjeto("Terminar Recepcion");
		System.out.println("Se finaliza el proceso de firmas de acuerdo de entrega, se selecciona boton terminar recepcion");
		Acuerdo.SeleccionarObjeto("Confirmar");
		POM_Devolucion.MoverSeccion("Continuar");
		POM_Devolucion.SeleccionarObjeto("Continuar");
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
		AcuerdoDev.FirmaAreaDistribucion("", "");
		Thread.sleep(2000);
		System.out.println("Firma Personal de Tienda\n");
		Thread.sleep(1000);
		AcuerdoDev.FirmaPersonalTienda("", "");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(2000);
		System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
		Thread.sleep(1000);
		AcuerdoDev.TerminarRecepcion("", "CEDIS_ConfirmarSeguroTerminarAcuerdo");
		FueRec.ValidacionEncabezado();
		Thread.sleep(3000);
		System.out.println("Se ingresa correctamente a la pantalla formato unico de entrega\n");
		FueRec.FinalizarFlujoFUE();
		Thread.sleep(1000);
		FueRec.esperaDinamicaLoaderFinalizarRecepcion();
		Thread.sleep(500);
		FueRec.esperaDinamicaValidarDespliegueMensajeRecepcionExitosa();
		Thread.sleep(5000);
		FueRec.SeleccionarObjeto("Confirmar Pedido");

		/*Paso 1 Posicionarse en la pantalla Encuesta de satisfacción.	
		 * Resultado esperado: Se muestra correctamente la pantalla encuesta de satisfacci�n
		 */		
		addStep("Posicionarse en la pantalla Encuesta de satisfacción. \r\n"
				+ "Resultado esperado: Se muestra correctamente la pantalla encuesta de satisfacción");		
		Encuesta.ValidacionPantallaEncuestaDeSatisfaccion();
		Thread.sleep(6000);
		System.out.println("Se muestra correctamente la pantalla encuesta de satisfaccion");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		
		/*Paso 2 Se muestra el botón comenzar y se presiona	
		 * Resultado esperado: El botónes funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción
		 */		
		addStep("Se muestra el botón comenzar y se presiona \r\n"
				+ "Resultado esperado: El botón es funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción");		
		Encuesta.SeleccionarObjeto("Boton Comenzar");
		Thread.sleep(9000);
		System.out.println("Se muestra la primera seccion de preguntas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		
		/*Paso 3 Al final de la pantalla encuesta de satisfacción en la seccion que te encuentres se visualiza el apartado contestar en otro momento.
		 * Resultado esperado: Se muestra correctamente el apartado contestar en otro momento.
		 */		
		addStep("Al final de la pantalla encuesta de satisfacción en la seccion que te encuentres se visualiza el apartado contestar en otro momento.\r\n"
				+ "Resultado esperado: Se muestra correctamente el apartado contestar en otro momento.");		
		Encuesta.ValidacionPantallaPrimeraSeccion();
		Thread.sleep(9000);
		System.out.println("Se muestra el apartado contestar en otro momento");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		
		/*Paso 4 Presionar el texto contestar en otro momento	
		 * Resultado esperado: Se despliega correctamente el mensaje: ¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.", con las opciones "CONFIRMAR y "CANCELAR".
		 */		
		addStep("Presionar el texto contestar en otro momento \r\n"
				+ "Resultado esperado: Se despliega correctamente el mensaje: ¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.\", con las opciones \"CONFIRMAR y \"CANCELAR\".");		
		Encuesta.SeleccionarObjeto("Boton Contestar En Otro Momento");
		Encuesta.ValidacionMensajeContestarEnOtroMomento();
		Thread.sleep(9000);
		System.out.println("Se muestra el mensaje correctamente: ¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonar� en otra visita del CEDIS.\", con las opciones \"CONFIRMAR y \"CANCELAR");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		/*Paso 5 Presionar CONFIRMAR	 
		 * Resultado esperado: Nos dirige a la pantalla donde se muestra el menu de herramientas.
		 */		
		addStep("Presionar CONFIRMAR \r\n"
				+ "Resultado esperado: Nos dirige a la pantalla donde se muestra el menu de herramientas.");		
		Encuesta.SeleccionarObjeto("Boton Confirmar");
		Thread.sleep(6000);
		Mobile.cedis.om.A04_MenuHerramientas A04_MenuHerramientas = new Mobile.cedis.om.A04_MenuHerramientas(testCase,
				appiumUtil, this);
		A04_MenuHerramientas.ValidacionPantallaHerramientas();
		Thread.sleep(7000);
		System.out.println("Nos dirige a la pantalla donde se muestra el menu de herramientas.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		 // Cerrar Apps
		  System.out.println("Terminando caso y cerrando OaD...");
		  Thread.sleep(1500);
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_228_EncuestaOpcionContestarEnOtroMomento";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en la primera pantalla de las preguntas de la encuestas se visualicen todas las opciones de respuesta.";
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
		return "1. App Oxxo al d�a instalada en Handheld.\r\n"
				+ "2. Usuario y Contrase�a de Aplicaci�n Oxxo al d�a.\r\n"
				+ "3. Comunicaci�n entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexi�n entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contrase�a: 2\r\n"
				+ "6. Contar con dispositivo HH.\r\n"
				+ "7. Haber finalizado un flujo de Recepci�n para que en cuanto lo finalices te arroje la encuesta.\r\n"
				+ "8.Haber cargado ASN�S a tu P.V\"";
	}

	@Override
	public void beforeTest() {
	}

}
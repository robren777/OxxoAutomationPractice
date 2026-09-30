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

public class ATC_FT_229_EncuestaOpcionCANCELAREnMensajeContestarEnOtroMomento extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_229_EncuestaOpcionCANCELAREnMensajeContestarEnOtroMomento_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Ejecuta el logeo hacia el punto de venta
		System.out.println("\nLogeandose en el Punto de Venta Oxxo al dia...");
		Thread.sleep(2000);
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		login.loginPV(data.get("ip"));

		// Ingresar al menu principal de Oxxo al dia
		System.out.println("\nIngresando al menu principal de Oxxo al dia...");
		Thread.sleep(5000);
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		Thread.sleep(5000);

		// Seleccionar el icono de Excelencia Operativa
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("\nDando clic en Excelencia Operativa...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Excelencia");

		// Seleccionar el icono de Herramienta de Compra
		System.out.println("\nDando clic en Herramienta de Compra...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Herramienta");

		// Seleccionar el icono de Cometa.
		System.out.println("\nDando clic en Cometa...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Cometa");

		// Clic en Recepcion de Pedidos CEDIS
		System.out.println("\nDando clic en Recepcion de Pedidos CEDIS...");
		Thread.sleep(2000);
		Mobile.cedis.om.A04_MenuHerramientas C04MenuHerramienta = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil,
				this);
		C04MenuHerramienta.clickBtnRecepcionPedidosCEDIS();

		// Login de Area de Distribucion
		System.out.println("\nLogeando Area de Distribucion...");
		Thread.sleep(2000);
		Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
				appiumUtil, this);
		C05LoginAreaDistribucion.LoginAreaDistribucion();

		// Dando clic en Recepcion Sin Escaner
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia B01_RecepcionPedidosSeleccionarTransferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(
				testCase, appiumUtil, this);
		System.out.println("\nDando clic en Switch Recepcion Sin Escaner y Entrando en la Primer Transferencia...");
		Thread.sleep(2000);
		
		// Paso #1.- Se ubica en la pantalla del listado de transferencias por recibir de Cedis, en la cual se encuentra una sola transferencia
		// Resultado esperado: Se despliega correctamente la pantalla del listado de transferencias por recibir de Cedis, con una sola transferencia
		System.out.println("\nPaso #1: Se ubica en la pantalla del listado de transferencias por recibir de Cedis, en la cual se encuentra una sola transferencia");
		addStep("Se ubica en la pantalla del listado de transferencias por recibir de Cedis, en la cual se encuentra una sola transferencia",
				"Resultado esperado: Se despliega correctamente la pantalla del listado de transferencias por recibir de Cedis, con una sola transferencia");
		B01_RecepcionPedidosSeleccionarTransferencia.ValidacionEncabezado();
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);

		// Dando clic en Recepcion de Pedido
		Mobile.cedis.om.B10_DeseaComenzar B10_DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		System.out.println("\nDando clic en Recepcion de Pedido...");
		Thread.sleep(2000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		
		// Ingresando las Imagenes de evidencia de Recepcion
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS B02_RecepcionPedidosCEDIS = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase,
				appiumUtil, this);
		System.out.println("\nIngresando las 3 Imagenes de evidencia de Recepcion...");
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.Tomar3Fotos();

		// Ingresando bultos
		System.out.println("\nIngresando bultos completos del pedido...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-3");
		
		B02_RecepcionPedidosCEDIS.CapturaContenedor("5667209");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("667445");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756377");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756407");
		
		B02_RecepcionPedidosCEDIS.CapturaContenedor("7986595");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986601");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986618");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986625");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756384");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756391");
		
		// Dando clic en Terminar
		System.out.println("\nFinalizando flujo completo de Recepcion (Recepcion y Devolucion)...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Continuar Sobrantes");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar Dañados");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		Mobile.cedis.om.B03_ResumenPedidosCEDIS B03_ResumenPedidosCEDIS = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase,appiumUtil, this);
		B03_ResumenPedidosCEDIS.SeleccionarObjeto("Firma Acuerdo");
		Thread.sleep(2000);
		Mobile.cedis.om.B04_AcuerdoEntrega B04_AcuerdoEntrega = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase,
				appiumUtil, this);
		B04_AcuerdoEntrega.FirmaAreaDistribucion("", "");
		B04_AcuerdoEntrega.FirmaPersonalTienda("", "");
		B04_AcuerdoEntrega.TerminarRecepcion("", "");
		Thread.sleep(2000);
		Mobile.cedis.om.B05_DevolucionMercancia B05_DevolucionMercancia = new Mobile.cedis.om.B05_DevolucionMercancia(testCase,
				appiumUtil, this);
		B05_DevolucionMercancia.SeleccionarObjeto("Continuar");
		B05_DevolucionMercancia.SeleccionarObjeto("Confirmar Seleccionar Bol");
		Thread.sleep(2000);
		Mobile.cedis.om.B07_ResumenDevolucion B07_ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase,
				appiumUtil, this);
		B07_ResumenDevolucion.ClickFirmaAcuerdo("", "");
		Thread.sleep(2000);
		Mobile.cedis.om.B08_AcuerdoDevolucion B08_AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase,
				appiumUtil, this);
		B08_AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		B08_AcuerdoDevolucion.FirmaPersonalTienda("", "");
		B08_AcuerdoDevolucion.TerminarRecepcion("", "");
		Thread.sleep(2000);
		Mobile.cedis.om.B09_FormatoUnicoEntrega B09_FormatoUnicoEntrega = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase,
				appiumUtil, this);
		B09_FormatoUnicoEntrega.FinalizarFlujoFUE();
		Thread.sleep(1000);
		B09_FormatoUnicoEntrega.esperaDinamicaLoaderFinalizarRecepcion();
		Thread.sleep(500);
		B09_FormatoUnicoEntrega.esperaDinamicaValidarDespliegueMensajeRecepcionExitosa();
		B09_FormatoUnicoEntrega.SeleccionarObjeto("Confirmar Pedido");
		Thread.sleep(5000);
		Mobile.cedis.om.B11_EncuestaSatisfaccion B11_EncuestaSatisfaccion = new Mobile.cedis.om.B11_EncuestaSatisfaccion(testCase,
				appiumUtil, this);
		
		/*Paso 1 Posicionarse en la pantalla Encuesta de satisfacción.	
		 * Resultado esperado: Se muestra correctamente la pantalla encuesta de satisfacción
		 */		
		addStep("Posicionarse en la pantalla Encuesta de satisfacción.", "Resultado esperado: Se muestra correctamente la pantalla encuesta de satisfacción");		
		B11_EncuestaSatisfaccion.ValidacionPantallaEncuestaDeSatisfaccion();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 2 Se muestra el botón comenzar y se presiona	
		 * Resultado esperado: El botón es funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción 
		 */		
		addStep("Se muestra el botón comenzar y se presiona", "Resultado esperado: El botón es funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción");		
		B11_EncuestaSatisfaccion.SeleccionarObjeto("Boton Comenzar");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3 Al final de la pantalla encuesta de satisfacción en la seccion que te encuentres se visualiza el apartado contestar en otro momento.
		 * Resultado esperado: Se muestra correctamente el apartado contestar en otro momento.
		 */		
		addStep("Al final de la pantalla encuesta de satisfacción en la seccion que te encuentres se visualiza el apartado contestar en otro momento.", "Resultaado esperado: Se muestra correctamente el apartado contestar en otro momento.");		
		B11_EncuestaSatisfaccion.ValidacionPantallaPrimeraSeccion();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		
		/*Paso 4 Presionar el texto contestar en otro momento	
		 * Resultado esperado: Se despliega correctamente el mensaje:¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.", con las opciones "SCONFIRMAR y "CANCELAR".
		 */		
		addStep("Presionar el texto contestar en otro momento", "Resultado esperado: Se despliega correctamente el mensaje:¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS. Cnn las opciones CONFIRMAR y CANCELAR");		
		B11_EncuestaSatisfaccion.SeleccionarObjeto("Boton Contestar En Otro Momento");
		Thread.sleep(3000);
		B11_EncuestaSatisfaccion.ValidacionMensajeContestarEnOtroMomento();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
		/*Paso 5 Presionar Cancelar	 
		 * Resultado esperado: Nos dirige a la pantalla donde se muestra el menu de herramientas.
		 */		
		addStep("Presionar Cancelar", "Resultado esperado: Nos dirige a la pantalla donde se muestra el menu de herramientas.");		
		B11_EncuestaSatisfaccion.SeleccionarObjeto("Boton Cancelar");
		Thread.sleep(3000);
		B11_EncuestaSatisfaccion.ValidacionPantallaPrimeraSeccion();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-229 Encuesta, opción CANCELAR en mensaje contestar en otro momento. ";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al momento de presionar la opción contestar en otro momento se despliegue el mensaje \"¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.\", con las opciones \"CONFIRMAR\" y \"CANCELAR\". Seleccionar CANCELAR y permanezca en la pantalla de encuesta de satisfacción";
	}

	@Override
	public String setTestDesigner() {
		return " Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "* App Oxxo al día instalada en Handheld.\n"
				+ "* Usuario y Contraseña de Aplicación Oxxo al día.\n"
				+ "* Comunicación entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexión entre ambos dispositivos).\n"
				+ "* P.V con acceso a la Herramienta de compra.\n"
				+ "* Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contraseña: 2\n"
				+ "* Contar con dispositivo HH.\n"
				+ "* Haber finalizado un flujo de recepción para que en cuanto lo finalices te arroje la encuesta.\n"
				+ "* Haber cargado ASN´S a tu P.V";
	}

	@Override
	public void beforeTest() {
	}

}
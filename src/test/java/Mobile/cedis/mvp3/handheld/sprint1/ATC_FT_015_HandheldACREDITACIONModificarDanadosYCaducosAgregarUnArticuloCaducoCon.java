package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import Mobile.cedis.om.C03_Acreditacion_de_Danados;
import Mobile.cedis.om.C04_Acreditacion_de_Caducos;
import Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


public class ATC_FT_015_HandheldACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoCon extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_015_HandheldACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoCon_test(HashMap<String, String> data) throws Exception {

		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(1000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(1000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//En el login de punto de venta ingreso la ip y el puerto
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("Logeando Punto de Venta");
		Thread.sleep(2000);
		LoginPDVOaD.loginPV("10.75.6.168");

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

		//En el menu de herramientas doy clic en la verificacion de cedis
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Verificacion de CEDIS");
		Thread.sleep(2000);
		ModulosOaD.clickBtnVerificacionMercanciaCEDIS();

		//Entro al listado de pedidos por verificar y busco la transferencia que quiero a traves del buscador para entrar en esa
		C01_Listado_de_transferencias_a_verificar ListadoVerificacion = new C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		System.out.println("Dando clic en Transferencia a Verificar");
		Thread.sleep(2000);
		ListadoVerificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();

		//Entro a verificacion, espero la barra, doy clic en bultos, clic en el primero, tomo evidencia y verifico todos los articulos cuadrando las cantidades,
		//Lo mismo para el segundo bulto y doy clic en continuar, para pasar directo a Caducos
		C02_Verificacion_De_Pedido_Y_Permisos verificacion = new C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		System.out.println("Entrando a Verificacion");
		verificacion.esperaDinamicaLoaderDeVerificacion(90000);
		verificacion.darClickBotonBultos();
		Thread.sleep(2000);
		verificacion.hacerClickEnElPrimerBultoDelPadre();
		Thread.sleep(2000);
		verificacion.tomarFoto();
		Thread.sleep(2000);
		verificacion.verificarArticulo();
		Thread.sleep(2000);
		verificacion.darClickBotonBultos();
		Thread.sleep(2000);
		verificacion.hacerClickEnElSegundoBultoDelPadre();
		Thread.sleep(2000);
		verificacion.tomarFoto();
		Thread.sleep(2000);
		verificacion.verificarArticulo();
		Thread.sleep(2000);
		verificacion.continuarDanadosHH();
		Thread.sleep(2000);

		//Entro a Caducos para ingresar el burrito que tiene de cantidad máxima 8 por eso el for
		C03_Acreditacion_de_Danados danados = new C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		Thread.sleep(1000);
		danados.presionarbotonIngresarCodigoSKU();
		Thread.sleep(300);
		danados.sendTextUsingKeyEventsT("JAM");
		Thread.sleep(300);
		danados.clicPrimerArticuloListado();
		Thread.sleep(300);
		danados.presionarbotonSiDescripcionArticulo();
		Thread.sleep(300);
		for (int i = 1; i < 8; i++) {
			danados.aumentarcantidadskuDanados();
			Thread.sleep(500);
		}
		danados.tomarfotografiaDanados();
		Thread.sleep(1000);
		danados.finalizarDanados();		

		//Entro a Resumen y aqui empieza mi caso de prueba
		// 1.- Descripción del paso: Se ubica en la pantalla "Resumen", hasta abajo del flujo de Acreditación.
		// 2.- Descripción del resultado esperado: Se despliega correctamente el botón: "Modificar Dañados y Caducos".
		// 3.- Descripción del paso: Se da clic en el botón: "Modificar Dañados y Caducos".
		// 4.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "¿Desea modificar los artículos Dañados y Caducos?" [NO] [SI].
		C05_Resumen_de_verificacion_acreditacion resumen = new C05_Resumen_de_verificacion_acreditacion(testCase, appiumUtil, this);
		resumen.validarBotonesvalidarMensajeEnResumen("Si", true, true, "Se ubica en la pantalla \"Resumen\", hasta abajo del flujo de Acreditación", "?Se despliega correctamente el botón: \"Modificar Dañados y Caducos\"", "Se da clic en el botón: \"Modificar Dañados y Caducos\"", "?Se despliega el siguiente mensaje: \"¿Desea modificar los artículos Dañados y Caducos?\" [NO] [SI]");

		// 5.- Descripción del paso: Se da clic en el botón : "Si".
		// 6.- Descripción del resultado esperado: Se despliega correctamente el apartado de "Artículos Dañados".
		addStep("Se da clic en el botón : \"Si\"", "?Se despliega correctamente el apartado de \"Articulos Dañados\"");
		danados.validarPantallaDanados();

		// 7.- Descripción del paso: Se da clic en el botón: "Continuar".
		// 8.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "¿Ha terminado la Devolución de Artículos Dañados?" [NO] [SI].
		danados.clickBotonContinuarDanados();
		addStep("Se da clic en el botón: \"Continuar\"", "Se despliega el siguiente mensaje: \"¿Ha terminado la Devolución de Artículos Dañados?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// 9.- Descripción del paso: Se da clic en el botón: "Si".
		// 10.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "¿Hay artículos Caducos en este Pedido?" [NO] [SI].
		danados.clickBotonSiConfirmarFinalizacionDeDanados();
		addStep("Se da clic en el botón: \"Si\"", "Se despliega el siguiente mensaje: \"¿Hay artículos Caducos en este Pedido?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// 11.- Descripción del paso: Se da clic en el botón: "Si".
		// 12.- Descripción del resultado esperado: Se despliega correctamente el apartado de "Artículos Caducos".
		danados.clickBotonSiPasarACaducosDesdeDanados();
		C04_Acreditacion_de_Caducos caducos = new C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		addStep("Descripción del paso: Se da clic en el botón: \"Si\"", "Se despliega correctamente el apartado de \"Artículos Caducos\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// 13.- Descripción del paso: Se da clic en el botón: "Ingresar código SKU".
		// 14.- Descripción del resultado esperado: Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del artículo".
		caducos.presionarbotonIngresarCodigoSKU();
		Thread.sleep(1000);
		addStep("Se da clic en el botón: \"Ingresar código SKU\"", "Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del artículo\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// 15.- Descripción del paso: Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o SKU del artículo ingresado con su máximo verificado en el apartado de Dañados.
		// 16.- Descripción del resultado esperado: Se despliega un listado de los artículos relacionados a los caracteres ingresados.
		caducos.sendTextUsingKeyEventsT("JAM");
		Thread.sleep(1000);
		addStep("Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o SKU del artículo ingresado con su máximo verificado en el apartado de Dañados", "Se despliega un listado de los artículos relacionados a los caracteres ingresados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(300);

		// 17.- Descripción del paso: Se da clic sobre el artículo que ingresamos su máximo verificado en el apartado de Dañados.
		// 18.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "Ingresaste el siguiente artículo: [SKU][NOMBRE DEL ARTÍCULO] ¿Deseas continuar?" [NO] [SI].
		caducos.clicprimerarticulodespegable();
		Thread.sleep(1000);
		addStep("Se da clic sobre el artículo que ingresamos su máximo verificado en el apartado de Dañados", "Se despliega el siguiente mensaje: \"Ingresaste el siguiente artículo: [SKU][NOMBRE DEL ARTÍCULO] ¿Deseas continuar?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(300);

		// 19.- Descripción del paso: Se da clic en el botón: "Si".
		// 20.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "El artículo no puede contener una cantidad mayor al número de piezas recibidas" [CONFIRMAR].
		Thread.sleep(1000);
		caducos.presionarbotonSiDescripcionArticulo();
		Thread.sleep(1000);
		addStep("Se da clic en el botón: \"Si\"", "Se despliega el siguiente mensaje: \"El artículo no puede contener una cantidad mayor al número de piezas recibidas\" [CONFIRMAR]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
		//Thread.sleep(1500);
		//Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	}

		/**
		 * CEDIS MVP #3: ATC_FT_014_HandHeld_ACREDITACION_Modificar_Danados_y_Caducos_Agregar_un_articulo_danado_con_el_maximo_ingresado_en_Articulos_Caducos.
		 * Desc:
		 * 1 - Validar que no permita avanzar a la verificación si no encuentra la Fuente de abasto en XPOS.
		 2 - Validar que permita leer el mismo QR por segunda ocasión, ya que no se genera un a OC
		 * @autor Jesús Marcelo Gutiérrez Bravo - QA GTIM
		 * @date 20/08/2024
		 */

		@BeforeTest
		public void beforeTest () {
		}

		@Override
		public String setPrerequisites () {
			return "*Haber recepcionado un pedido con cajas/canastillas\n" +
					"*Haber verificado ese mismo pedido\n" +
					"*Haber ingresado el máximo verificado de un artículo como caduco/s para que no nos dejé ingresarlo como artículo dañado";
		}

		@Override
		public String setTestDescription () {
			return "Validar que al dar clic en: \"Modificar Dañados y Caducos\", no nos permita agregar como dañado un artículo al cual previamente se ingreso su cantidad máxima verificada como caducos";
		}

		@Override
		public String setTestDesigner () {
			return "Jesús Marcelo Gutiérrez Bravo - QA GTIM";
		}

		@Override
		public String setTestFullName () {
			return "ATC_FT_015_HandheldACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoCon";
		}

		@Override
		public String setTestInstanceID () {
			return null;
		}

}


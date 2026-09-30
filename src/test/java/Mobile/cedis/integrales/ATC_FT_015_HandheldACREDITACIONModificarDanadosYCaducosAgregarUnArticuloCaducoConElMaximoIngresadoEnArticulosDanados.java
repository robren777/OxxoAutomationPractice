package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
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

/**
 * DESCRIPCIÓN:
 * Validar que al dar clic en: "Modificar Dañados y Caducos", no nos permita agregar como caduco un artículo al cual previamente se ingreso su cantidad 
   máxima verificada como dañados
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_015_HandHeldModificarDañadosYCaducosAgregarUnArtículoCaducoConElMáximoIngresadoEnArtículosDañados
 *
 * PRERREQUISITOS:
 * 1. Haber recepcionado un pedido con cajas/canastillas
 * 2. Haber verificado ese mismo pedido
 * 3. Haber ingresado el máximo verificado de un artículo como caduco/s para que no nos dejé ingresarlo como artículo dañado
 *
 * 
 * 
 */



public class ATC_FT_015_HandheldACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoConElMaximoIngresadoEnArticulosDanados extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_015_HandheldACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoConElMaximoIngresadoEnArticulosDanados_test(
			HashMap<String, String> data) throws Exception {

		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
		
		//En el login de punto de venta ingreso la ip y el puerto
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("Logeando Punto de Venta");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		LoginPDVOaD.loginPV(data.get("ip"));

		//En el login de lider ingreso el usuario y contraseña
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		//En la pantalla principal doy clic en el boton de excelencia operativa, luego en la hc y al final e cometa
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		MenuOaD.logeoInicioHerramientas();

		//En el menu de herramientas doy clic en la verificacion de cedis
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Verificacion de CEDIS");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		ModulosOaD.clickBtnVerificacionMercanciaCEDIS();

		//Entro al listado de pedidos por verificar y busco la transferencia que quiero a traves del buscador para entrar en esa
		C01_Listado_de_transferencias_a_verificar ListadoVerificacion = new C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		System.out.println("Dando clic en Transferencia a Verificar");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		ListadoVerificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();

		//Entro a verificacion, espero la barra, doy clic en bultos, clic en el primero, tomo evidencia y verifico todos los articulos cuadrando las cantidades,
		//Lo mismo para el segundo bulto y doy clic en continuar, para pasar directo a Caducos
		C02_Verificacion_De_Pedido_Y_Permisos verificacion = new C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		System.out.println("Entrando a Verificacion");
		verificacion.esperaDinamicaLoaderDeVerificacion(90000);
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnBultoDelPadre(1);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnBultoDelPadre(2);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnBultoDelPadre(3);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnBultoDelPadre(4);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.continuarDanadosHH();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		//Entro a Caducos para ingresar el burrito que tiene de cantidad máxima 8 por eso el for
		C03_Acreditacion_de_Danados danados = new C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		danados.presionarbotonIngresarCodigoSKU();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		danados.sendTextUsingKeyEventsT("JAM");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		danados.clicPrimerArticuloListado();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		danados.presionarbotonSiDescripcionArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		for (int i = 1; i < 8; i++) {
			danados.aumentarcantidadskuDanados();
			appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		}
		
		danados.tomarfotografiaDanados();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		danados.finalizarDanados();	
		C05_Resumen_de_verificacion_acreditacion resumen = new C05_Resumen_de_verificacion_acreditacion(testCase, appiumUtil, this);

		//Entro a Resumen y aqui empieza mi caso de prueba
		// 1.- Descripción del paso: Se ubica en la pantalla "Resumen", hasta abajo del flujo de Acreditación.
		// 2.- Descripción del resultado esperado: Se despliega correctamente el botón: "Modificar Dañados y Caducos".
		addStep("Se ubica en la pantalla Resumen, hasta abajo del flujo de Acreditación", "Resultado esperado: Se despliega correctamente el botón: Modificar Dañados y Caducos");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		resumen.ValidacionBotonModDanadosYCaducos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// 3.- Descripción del paso: Se da clic en el botón: "Modificar Dañados y Caducos".
		// 4.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "¿Desea modificar los artículos Dañados y Caducos?" [NO] [SI].
		addStep("Se da clic en el botón: Modificar Dañados y Caducos", "Resultado esperado: Se despliega el siguiente mensaje: ¿Desea modificar los artículos Dañados y Caducos? [NO] [SI]");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		resumen.SeleccionarObjeto("Boton Modificar Danados Y Caducos");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		resumen.ValidacionMensajeModificarDanadosYCaducos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// 5.- Descripción del paso: Se da clic en el botón : "Si".
		// 6.- Descripción del resultado esperado: Se despliega correctamente el apartado de "Artículos Dañados".
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		resumen.SeleccionarObjeto("Boton Si");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		addStep("Se da clic en el botón : \"Si\"", "Resultado esperado: Se despliega correctamente el apartado de \"Articulos Dañados\"");
		danados.validarPantallaDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// 7.- Descripción del paso: Se da clic en el botón: "Continuar".
		// 8.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "¿Ha terminado la Devolución de Artículos Dañados?" [NO] [SI].
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		danados.clickBotonContinuarDanados();
		addStep("Se da clic en el botón: \"Continuar\"", "Resultado esperado: Se despliega el siguiente mensaje: \"¿Ha terminado la Devolución de Artículos Dañados?\" [NO] [SI]");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		danados.ValidarMensajeAlClickBotonContinuar();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// 9.- Descripción del paso: Se da clic en el botón: "Si".
		// 10.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "¿Hay artículos Caducos en este Pedido?" [NO] [SI].
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		danados.clickBotonSiConfirmarFinalizacionDeDanados();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		addStep("Se da clic en el botón: \"Si\"", "Resultado esperado: Se despliega el siguiente mensaje: \"¿Hay artículos Caducos en este Pedido?\" [NO] [SI]");
		danados.ValidarMensajeAlClickBotonConfirmarPasarACaducos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// 11.- Descripción del paso: Se da clic en el botón: "Si".
		// 12.- Descripción del resultado esperado: Se despliega correctamente el apartado de "Artículos Caducos".
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		danados.clickBotonSiPasarACaducosDesdeDanados();
		C04_Acreditacion_de_Caducos caducos = new C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		addStep("Descripción del paso: Se da clic en el botón: \"Si\"", "Resultado esperado: Se despliega correctamente el apartado de \"Artículos Caducos\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.validarPantallaCaducos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

		// 13.- Descripción del paso: Se da clic en el botón: "Ingresar código SKU".
		// 14.- Descripción del resultado esperado: Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del artículo".
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.presionarbotonIngresarCodigoSKU();
		addStep("Se da clic en el botón: \"Ingresar código SKU\"", "Resultado esperado: Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del artículo\"");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.validarMensajeAlClickBotonIngresarCodigoSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

		// 15.- Descripción del paso: Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o SKU del artículo ingresado con su máximo verificado en el apartado de Dañados.
		// 16.- Descripción del resultado esperado: Se despliega un listado de los artículos relacionados a los caracteres ingresados.
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.sendTextUsingKeyEventsT("JAM");
		addStep("Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o SKU del artículo ingresado con su máximo verificado en el apartado de Dañados", "Resultado esperado: Se despliega un listado de los artículos relacionados a los caracteres ingresados");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.validardescripcionarticulo();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

		// 17.- Descripción del paso: Se da clic sobre el artículo que ingresamos su máximo verificado en el apartado de Dañados.
		// 18.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "Ingresaste el siguiente artículo: [SKU][NOMBRE DEL ARTÍCULO] ¿Deseas continuar?" [NO] [SI].
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.clicprimerarticulodespegable();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		addStep("Se da clic sobre el artículo que ingresamos su máximo verificado en el apartado de Dañados", "Resultado esperado: Se despliega el siguiente mensaje: \"Ingresaste el siguiente artículo: [SKU][NOMBRE DEL ARTÍCULO] ¿Deseas continuar?\" [NO] [SI]");
		caducos.validarmensajearticuloagregadoV();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

		// 19.- Descripción del paso: Se da clic en el botón: "Si".
		// 20.- Descripción del resultado esperado: Se despliega el siguiente mensaje: "El artículo no puede contener una cantidad mayor al número de piezas recibidas" [CONFIRMAR].
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		caducos.presionarbotonSiDescripcionArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		addStep("Se da clic en el botón: \"Si\"", "Resultado esperado: Se despliega el siguiente mensaje: \"El artículo no puede contener una cantidad mayor al número de piezas recibidas\" [CONFIRMAR]");
		caducos.presionarbotonSiDescripcionArticuloConMaximo();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-015 HandHeld Modificar Dañados y Caducos - Agregar un artículo caduco con el máximo ingresado en Artículos Dañados";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al dar clic en: Modificar Dañados y Caducos, no nos permita agregar como dañado un artículo al cual previamente se ingreso su cantidad máxima verificada como caducos";
	}

	@Override
	public String setTestDesigner() {
		return " Jesús Marcelo Gutiérrez Bravo - GTIM QA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Haber recepcionado un pedido con cajas/canastillas\n"
				+ "*Haber verificado ese mismo pedido\n"
				+ "*Haber ingresado el máximo verificado de un artículo como dañados para que no nos deje ingresarlo como artículo caduco";
	}

	@Override
	public void beforeTest() {
	}

}
package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
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
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:
 * Validar que al dar clic en: "Modificar Dañados y Caducos", podamos agregar y quitar artículos dañados y caducos
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_017_TABLETACREDITACIÓNModificarDañadosYCaducosAgregarYQuitarArtículosDañadosYCaducos
 *
 * PRERREQUISITOS:
 * 1. Haber recepcionado un pedido con cajas/canastillas
 * 2. Haber verificado ese mismo pedido
 * 3. Haber ingresado artículos dañados y caducos en sus apartados
 *
 * 
 * 
 */


public class ATC_FT_017_TABLETACREDITACIONModificarDanadosYCaducosAgregarYQuitarArticulosDanadosYCaducos extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_017_TABLETACREDITACIONModificarDanadosYCaducosAgregarYQuitarArticulosDanadosYCaducos_test(
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
		cap.setCapability("udid", data.get("udId"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
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
		verificacion.esperaDinamicaLoaderDeVerificacion(180000);
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

		Mobile.cedis.om.C03_Acreditacion_de_Danados C03_Acreditacion_de_Danados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		Mobile.cedis.om.C04_Acreditacion_de_Caducos C04_Acreditacion_de_Caducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion C05_Resumen_de_verificacion_acreditacion= new Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion(testCase, appiumUtil, this);
		
		C03_Acreditacion_de_Danados.presionarbotonIngresarCodigoSKU();
		C03_Acreditacion_de_Danados.ingresar3primerasletrasV();
		C03_Acreditacion_de_Danados.clicPrimerArticuloListado();
		C03_Acreditacion_de_Danados.presionarbotonSiDescripcionArticulo();
		C03_Acreditacion_de_Danados.aumentarcantidadskuDanados();
		C03_Acreditacion_de_Danados.tomarfotografiaDanadosactualizado();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		C03_Acreditacion_de_Danados.continuarACaducos();
		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		C04_Acreditacion_de_Caducos.presionarbotonIngresarCodigoSKU();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C04_Acreditacion_de_Caducos.ingresar3primerasletras();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		C04_Acreditacion_de_Caducos.clicprimerarticulodespegable();
		C04_Acreditacion_de_Caducos.presionarbotonSiDescripcionArticulo();
		C04_Acreditacion_de_Caducos.aumentarcantidadskucaduco();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		C04_Acreditacion_de_Caducos.tomarfotografiacaduco();
		C04_Acreditacion_de_Caducos.continuararesumen();
		
/*Paso 1 Se ubica en la pantalla "Resumen", hasta abajo del flujo de Acreditación".
  Resultado: ?Se despliega correctamente el botón: "Modificar Dañados y Caducos". 
   Paso 2 Se da clic en el botón: "Modificar Dañados y Caducos"".
  Resultado ? Se despliega el mensaje ¿Desea modificar los articulos Dañados y Caducos? NO SI: 

  */
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	 C05_Resumen_de_verificacion_acreditacion.validarBotonesvalidarMensajeEnResumen("Si", true, true, "Se ubica en la pantalla \"Resumen\", hasta abajo del flujo de Acreditación", "?Se despliega correctamente el botón: \"Modificar Dañados y Caducos\"", "Se da clic en el botón: \"Modificar Dañados y Caducos\"", "?Se despliega el siguiente mensaje: \"¿Desea modificar los artículos Dañados y Caducos?\" [NO] [SI]");
  
  /*Paso 3 Seleccionar Si".
  Resultado: ?Se despliega correctamente el apartado de "Articulos Dañados".
  */
	  addStep("Se despliega correctamente el apartado de \"Articulos Dañados");
	  C03_Acreditacion_de_Danados.validarPantallaDanados();
	  System.out.println("Se despliega correctamente el apartado de Articulos Dañados");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

  /*Paso 4 Se da clic en el botón "-" para disminuir la cantidad de un artículo dañado previamente ingresado".
  Resultado: ?Se borra la evidencia del artículo afectado.
  */
	  addStep("Se borra la evidencia del artículo afectado");
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  C03_Acreditacion_de_Danados.disminuircantidadskuDanados();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  System.out.println("Se borra la evidencia del artículo afectado");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  /*Paso 5 Se toma denuevo la evidencia del artículo afectado".
  Resultado: ?Se guarda la evidencia correctamente.
  */
	  addStep("Se guarda la evidencia correctamente");
	  C03_Acreditacion_de_Danados.tomarfotografiaDanadosactualizado();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  System.out.println("Se guarda la evidencia correctamente");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
 
	  /*Paso 6 Se da clic en el boton: "Ingresar codigo SKU".
  Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
  */	
	  addStep("Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
	  C03_Acreditacion_de_Danados.presionarbotonIngresarCodigoSkuV();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  System.out.println("Se despliega un campo flotante con el placeholder: Escribe el SKU o nombre del articulo");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

	  /*Paso 7 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo".
  Resultado: ?Se despliega un listado de los articulos relacionados a los caracteres ingresados.
  */
	  addStep("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
	  C03_Acreditacion_de_Danados.ingresar3primerasletrasS();
	  System.out.println("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

	  /*Paso 8 Se da clic sobre el articulo que queramos ingresar".
  Resultado: ?Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
  */
	  addStep("Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
	  C03_Acreditacion_de_Danados.clicPrimerArticuloListado();
	  System.out.println("Se despliega el mensaje del articulo que seleccionaste para ingresarlo como dañado");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
	  /*Paso 9 Se da clic en el botón : "Si".
  Resultado: ?Se despliega el artículo correctamente.
  */
	  addStep("Se despliega el artículo correctamente");
	  C03_Acreditacion_de_Danados.presionarbotonSiDescripcionArticulo();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  System.out.println("Se despliega el artículo correctamente");
      testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
 
      /*Paso 10 Se toma la evidencia del artículo afectado".
  Resultado: ?Se guarda la evidencia correctamente.
  */
	  addStep("Se guarda la evidencia correctamente");
	  C03_Acreditacion_de_Danados.tomarfotografiaDanadossegundoarticulo();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  System.out.println("Se guarda la evidencia correctamente");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
	  /*Paso 11 Se da clic en el botón : "Continuar"".
  Resultado: ?Se despliega el siguiente mensaje: "¿Ha terminado la Devolucion de Articulos Dañados?" [NO] [SI].
  */
	  addStep("Se despliega el siguiente mensaje: \"¿Ha terminado la Devolucion de Articulos Dañados?\" [NO] [SI]");
	  C03_Acreditacion_de_Danados.clickBotonContinuarDanados();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se despliega el siguiente mensaje: ¿Ha terminado la Devolucion de Articulos Dañados? NO SI");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
 
	  /*Paso 12 Se da clic en el botón : "Si".
  Resultado: ?Se despliega el siguiente mensaje: "¿Hay articulos Caducos en este Pedido?" [NO] [SI].
  */
	  addStep("Se despliega el siguiente mensaje: \"¿Hay articulos Caducos en este Pedido?\" [NO] [SI]");
	  C03_Acreditacion_de_Danados.clickBotonSiConfirmarFinalizacionDeDanados();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se despliega el siguiente mensaje: ¿Hay articulos Caducos en este Pedido? NO SI");
      testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
 
      /*Paso 13 "Se da clic en el botón : "Si".
  Resultado: ?Se despliega correctamente el apartado de "Articulos Caducos", con un artículo ingresado.
  */
	  addStep("Se despliega correctamente el apartado de \"Articulos Caducos\", con un artículo ingresado");
	  C03_Acreditacion_de_Danados.clickBotonSiConfirmarFinalizacionDeDanados();
	  C04_Acreditacion_de_Caducos.pantallaCaducos();
	  System.out.println("Se despliega correctamente el apartado de Articulos Caducos, con un artículo ingresado");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

	  /*Paso 14 Se da clic en el botón "-" para disminuir la cantidad de un artículo caduco previamente ingresado".
   Resultado: ?Se borra la evidencia del artículo afectado.
   */
	  addStep("Se borra la evidencia del artículo afectado");
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  C04_Acreditacion_de_Caducos.disminuircantidadskucaduco();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se borra la evidencia del artículo afectado");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
	  /*Paso 15 Se toma denuevo la evidencia del artículo afectado".
   Resultado: ?Se guarda la evidencia correctamente.
    */
	  addStep("Se guarda la evidencia correctamente");
	  C04_Acreditacion_de_Caducos.tomarfotografiacaduco();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se guarda la evidencia correctamente");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
 
	  /*Paso 16 Se da clic en el boton: "Ingresar codigo SKU"".
   Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
    */
	  addStep("Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
	  C04_Acreditacion_de_Caducos.presionarbotonIngresarCodigoSKU();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se despliega un campo flotante con el placeholder: Escribe el SKU o nombre del articulo");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
	  /*Paso 17 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo".
	   Resultado: ?Se despliega un listado de los articulos relacionados a los caracteres ingresados.
	    */	
	  addStep("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
	  C04_Acreditacion_de_Caducos.ingresar3primerasletrasSAN();
	  System.out.println("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  
	  /*Paso 18 Se da clic sobre el articulo que queramos ingresar".
	   Resultado: ?Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
	    */	
	  addStep("Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
	  C04_Acreditacion_de_Caducos.clicprimerarticulodespegable();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se despliega el mensaje correctamente");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  
	  /*Paso 19 Se da clic en el botón : "Si"".
	   Resultado: ?Se despliega el artículo correctamente.
	    */	
	  addStep("Se despliega el artículo correctamente");
	  C04_Acreditacion_de_Caducos.presionarbotonSiDescripcionArticulo();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	  Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
	  Thread.sleep(3000);
	  System.out.println("Se despliega el artículo correctamente");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  
	  /*Paso 20 Se toma la evidencia del artículo afectado".
	   Resultado: ?Se guarda la evidencia correctamente.				   
	    */	
	  addStep("Se guarda la evidencia correctamente");
	  C04_Acreditacion_de_Caducos.tomarfotografiaDanadossegundoarticulo();
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  System.out.println("Se guarda la evidencia correctamente");
	  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
	}

	@Override
	public String setTestFullName() {
		return " ATC-FT-017-TABLET ACREDITACIÓN \"Modificar Dañados y Caducos\"-Agregar y quitar artículos dañados y caducos";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al dar clic en: \"Modificar Dañados y Caducos\", podamos agregar y quitar artículos dañados y caducos";
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
		return "* Haber recepcionado un pedido con cajas/canastillas\n"
				+ "* Haber verificado ese mismo pedido\n"
				+ "* Haber ingresado artículos dañados y caducos en sus apartados";
	}

	@Override
	public void beforeTest() {
	}

}
package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:
 * Validar que al dar clic en: "Modificar Dañados y Caducos", podamos agregar y quitar artículos dañados y caducos
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_013_HandheldACREDITACIONModificarDañadosYCaducosAgregarYQuitarArticulosDanañadosYCaducos
 *
 * PRERREQUISITOS:
 * 1. Haber recepcionado un pedido con cajas/canastillas
 * 2. Haber verificado ese mismo pedido
 * 3. Haber ingresado artículos dañados y caducos en sus apartados
 *
 * @author Ricardo Hdz
 * @date 13 agosto 2024
 */


public class ATC_FT_013_HandheldACREDITACIONModificarDanadosYCaducosAgregarYQuitarArticulosDanadosYCaducos extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_013_HandheldACREDITACIONModificarDanadosYCaducosAgregarYQuitarArticulosDanadosYCaducos_test(
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

		//Abre la aplicación 
    	Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
    	//Ejecuta el logeo hacia el punto de venta
    	Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this); 
    	System.out.println("Logeando Punto de Venta");
    	login.loginPV(data.get("ip"));
    			
    	//Ejecuta el logeo del lider
    	Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this); 
    	System.out.println("Logeando Lider");
    	//Obtiene valor del DataProvaider
    	loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
    			
    	//Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
    	Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
    	System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
    	herramienta.logeoInicioHerramientas();
    			
    	Mobile.cedis.om.A04_MenuHerramientas menuCedis =  new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
    	System.out.println("Dando clic en Verificacion de CEDIS");
    	menuCedis.clickBtnVerificacionMercanciaCEDIS();
    			
    	Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoverificacion = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
    	System.out.println("Dando clic en Transferencia a Verificar");
    	listadoverificacion.busquedaNumTransferencia(data.get("numTransferencia"));//Realiza la busqueda de una verificación
 
		Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos verificacion = new Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		verificacion.esperaDinamicaLoaderDeVerificacion(100000);
		System.out.println("Entrando a Verificacion");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnElPrimerBultoDelPadre();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnElSegundoBultoDelPadre();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
		verificacion.VerificarArticulos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.continuarDanadosHH();
		Mobile.cedis.om.C03_Acreditacion_de_Danados pantallaDanados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		pantallaDanados.presionarbotonIngresarCodigoSKU();
		pantallaDanados.ingresar3primerasletrasV();
		pantallaDanados.clicPrimerArticuloListado();
		pantallaDanados.presionarbotonSiDescripcionArticulo();
		pantallaDanados.aumentarcantidadskuDanados();
		pantallaDanados.tomarfotografiaDanadosactualizado();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		pantallaDanados.continuarACaducos();
		
		Mobile.cedis.om.C04_Acreditacion_de_Caducos pantallaCaducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		pantallaCaducos.presionarbotonIngresarCodigoSKU();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		pantallaCaducos.ingresar3primerasletras();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		pantallaCaducos.clicprimerarticulodespegable();
		pantallaCaducos.presionarbotonSiDescripcionArticulo();
		pantallaCaducos.aumentarcantidadskucaduco();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		pantallaCaducos.tomarfotografiacaduco();
		pantallaCaducos.continuararesumen();
		
		//OM A utilizar
		Mobile.cedis.om.C03_Acreditacion_de_Danados danados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		Mobile.cedis.om.C04_Acreditacion_de_Caducos Caducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion pantallaResumen= new Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion(testCase, appiumUtil, this);

      /*Paso 1 Se ubica en la pantalla "Resumen", hasta abajo del flujo de Acreditación".
	  Resultado: ?Se despliega correctamente el botón: "Modificar Dañados y Caducos". 
	   Paso 2 Se da clic en el botón: "Modificar Dañados y Caducos"".
      Resultado ? Se despliega el mensaje ¿Desea modificar los articulos Dañados y Caducos? NO SI: 
	
	  */
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		  pantallaResumen.validarBotonesvalidarMensajeEnResumen("Si", true, true, "Se ubica en la pantalla \"Resumen\", hasta abajo del flujo de Acreditación", "?Se despliega correctamente el botón: \"Modificar Dañados y Caducos\"", "Se da clic en el botón: \"Modificar Dañados y Caducos\"", "?Se despliega el siguiente mensaje: \"¿Desea modificar los artículos Dañados y Caducos?\" [NO] [SI]");
	  
	  /*Paso 3 Seleccionar Si".
	  Resultado: ?Se despliega correctamente el apartado de "Articulos Dañados".
      */
		  addStep("Se despliega correctamente el apartado de \"Articulos Dañados");
		  danados.validarPantallaDanados();
		  System.out.println("Se despliega correctamente el apartado de Articulos Dañados");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
      /*Paso 4 Se da clic en el botón "-" para disminuir la cantidad de un artículo dañado previamente ingresado".
	  Resultado: ?Se borra la evidencia del artículo afectado.
	  */
		  addStep("Se borra la evidencia del artículo afectado");
		  danados.disminuircantidadskuDanados();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		  System.out.println("Se borra la evidencia del artículo afectado");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 5 Se toma denuevo la evidencia del artículo afectado".
	  Resultado: ?Se guarda la evidencia correctamente.
	  */
		  addStep("Se guarda la evidencia correctamente");
		  danados.tomarfotografiaDanadosactualizado();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		  System.out.println("Se guarda la evidencia correctamente");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	 
		  /*Paso 6 Se da clic en el boton: "Ingresar codigo SKU".
	  Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
	  */	
		  addStep("Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
		  danados.presionarbotonIngresarCodigoSkuV();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		  System.out.println("Se despliega un campo flotante con el placeholder: Escribe el SKU o nombre del articulo");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
		  /*Paso 7 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo".
	  Resultado: ?Se despliega un listado de los articulos relacionados a los caracteres ingresados.
	  */
		  addStep("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		  danados.ingresar3primerasletrasS();
		  System.out.println("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
		  /*Paso 8 Se da clic sobre el articulo que queramos ingresar".
	  Resultado: ?Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
	  */
		  addStep("Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
		  danados.clicPrimerArticuloListado();
		  System.out.println("Se despliega el mensaje del articulo que seleccionaste para ingresarlo como dañado");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  
		  /*Paso 9 Se da clic en el botón : "Si".
	  Resultado: ?Se despliega el artículo correctamente.
	  */
		  addStep("Se despliega el artículo correctamente");
          danados.presionarbotonSiDescripcionArticulo();
		  System.out.println("Se despliega el artículo correctamente");
          testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	 
          /*Paso 10 Se toma la evidencia del artículo afectado".
	  Resultado: ?Se guarda la evidencia correctamente.
	  */
		  addStep("Se guarda la evidencia correctamente");
		  danados.tomarfotografiaDanadossegundoarticulo();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		  System.out.println("Se guarda la evidencia correctamente");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  
		  /*Paso 11 Se da clic en el botón : "Continuar"".
	  Resultado: ?Se despliega el siguiente mensaje: "¿Ha terminado la Devolucion de Articulos Dañados?" [NO] [SI].
      */
		  addStep("Se despliega el siguiente mensaje: \"¿Ha terminado la Devolucion de Articulos Dañados?\" [NO] [SI]");
		  danados.clickBotonContinuarDanados();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se despliega el siguiente mensaje: ¿Ha terminado la Devolucion de Articulos Dañados? NO SI");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	 
		  /*Paso 12 Se da clic en el botón : "Si".
	  Resultado: ?Se despliega el siguiente mensaje: "¿Hay articulos Caducos en este Pedido?" [NO] [SI].
	  */
		  addStep("Se despliega el siguiente mensaje: \"¿Hay articulos Caducos en este Pedido?\" [NO] [SI]");
	      danados.clickBotonSiConfirmarFinalizacionDeDanados();
	      appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se despliega el siguiente mensaje: ¿Hay articulos Caducos en este Pedido? NO SI");
	      testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	 
	      /*Paso 13 "Se da clic en el botón : "Si".
      Resultado: ?Se despliega correctamente el apartado de "Articulos Caducos", con un artículo ingresado.
	  */
		  addStep("Se despliega correctamente el apartado de \"Articulos Caducos\", con un artículo ingresado");
		  danados.clickBotonSiConfirmarFinalizacionDeDanados();
		  Caducos.pantallaCaducos();
		  System.out.println("Se despliega correctamente el apartado de Articulos Caducos, con un artículo ingresado");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		  /*Paso 14 Se da clic en el botón "-" para disminuir la cantidad de un artículo caduco previamente ingresado".
	   Resultado: ?Se borra la evidencia del artículo afectado.
	   */
		  addStep("Se borra la evidencia del artículo afectado");
		  Caducos.disminuircantidadskucaduco();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se borra la evidencia del artículo afectado");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  
		  /*Paso 15 Se toma denuevo la evidencia del artículo afectado".
	   Resultado: ?Se guarda la evidencia correctamente.
	    */
		  addStep("Se guarda la evidencia correctamente");
		  Caducos.tomarfotografiacaduco();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se guarda la evidencia correctamente");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	 
		  /*Paso 16 Se da clic en el boton: "Ingresar codigo SKU"".
	   Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
	    */
		  addStep("Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
		  Caducos.presionarbotonIngresarCodigoSKU();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se despliega un campo flotante con el placeholder: Escribe el SKU o nombre del articulo");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		  /*Paso 17 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo".
		   Resultado: ?Se despliega un listado de los articulos relacionados a los caracteres ingresados.
		    */	
		  addStep("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		  Caducos.ingresar3primerasletrasSAN();
		  System.out.println("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  
		  /*Paso 18 Se da clic sobre el articulo que queramos ingresar".
		   Resultado: ?Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
		    */	
		  addStep("Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
		  Caducos.clicprimerarticulodespegable();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se despliega el mensaje correctamente");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  
		  /*Paso 19 Se da clic en el botón : "Si"".
		   Resultado: ?Se despliega el artículo correctamente.
		    */	
		  addStep("Se despliega el artículo correctamente");
		  Caducos.presionarbotonSiDescripcionArticulo();
		  System.out.println("Se despliega el artículo correctamente");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  
		  /*Paso 20 Se toma la evidencia del artículo afectado".
		   Resultado: ?Se guarda la evidencia correctamente.				   
		    */	
		  addStep("Se guarda la evidencia correctamente");
		  Caducos.tomarfotografiaDanadossegundoarticulo();
		  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		  System.out.println("Se guarda la evidencia correctamente");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  				  
	  // Cerrar Apps
	  System.out.println("Terminando caso y cerrando OaD...");
	  appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	  
	}
	
	


	@BeforeTest
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. App Oxxo al día instalada en Handheld.\r\n"
				+ "2. Usuario y Contraseña de Aplicación Oxxo al día.\r\n"
				+ "3. Comunicación entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexión entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Haber recepcionado un pedido con cajas/canastillas"
				+ "6. Que el SKU que se meterá como caduco no sea faltante \r\n"
				+ "7. Haber verificado ese mismo pedido\r\n"
				+ "8. Haber ingresado artículos dañados y caducos en sus apartados";
	
	
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en: \"Modificar Dañados y Caducos\", podamos agregar y quitar artículos dañados y caducos";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hdz";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_HandheldACREDITACIONModificarDanadosYCaducosAgregarYQuitarArticulosDan_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
 }			

}
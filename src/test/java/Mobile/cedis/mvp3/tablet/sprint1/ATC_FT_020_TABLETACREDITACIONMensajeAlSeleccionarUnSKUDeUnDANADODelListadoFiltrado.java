package Mobile.cedis.mvp3.tablet.sprint1;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
 
public class ATC_FT_020_TABLETACREDITACIONMensajeAlSeleccionarUnSKUDeUnDANADODelListadoFiltrado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_020_TABLETACREDITACIONMensajeAlSeleccionarUnSKUDeUnDANADODelListadoFiltrado_Test(HashMap<String, String> data) throws Exception {
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("appium:udid", data.get("udid"));
		//cap.setCapability("noReset", true); // Se debe de dejar esto opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";
 
		// Cerrar Apps
		Thread.sleep(1000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));
				
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
    	//Abre la aplicación 
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		Thread.sleep(2000);
		
		//Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this); 
		login.loginPV("10.75.6.168");
		
		//Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this); 
		//Obtiene valor del DataProvaider
		Thread.sleep(2000);
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		//Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.Tablet_A03_Menus_y_submenus herramienta = new Mobile.cedis.om.Tablet_A03_Menus_y_submenus(testCase, appiumUtil, this);
		Thread.sleep(2000);
		herramienta.logeoInicioHerramientasTT();
		
		Mobile.cedis.om.A04_MenuHerramientas menuCedis =  new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		Thread.sleep(2000);
		menuCedis.clickBtnVerificacionMercanciaCEDIS();
		
		Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoverificacion = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		Thread.sleep(2000);
		listadoverificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();//Realiza la busqueda de una verificación
		
		Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos verificacionHH = new Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);

		verificacionHH.esperaDinamicaLoaderDeVerificacion(90000);
		verificacionHH.darClickBotonBultos();
		Thread.sleep(2000);
		verificacionHH.hacerClickEnElPrimerBultoDelPadre();
		Thread.sleep(2000);
		verificacionHH.tomarFoto();
		Thread.sleep(2000);
		verificacionHH.verificarArticulo();
		Thread.sleep(2000);
		verificacionHH.darClickBotonBultos();
		Thread.sleep(2000);
		verificacionHH.hacerClickEnElSegundoBultoDelPadre();
		Thread.sleep(2000);
		verificacionHH.tomarFoto();
		Thread.sleep(2000);
		verificacionHH.verificarArticulo();
		Thread.sleep(2000);
		verificacionHH.continuarDanadosHH();
		Thread.sleep(2000);
	    
	    Mobile.cedis.om.C03_Acreditacion_de_Danados pantallaDanados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
	    pantallaDanados.presionarbotonIngresarCodigoSKU();
		Thread.sleep(1000);
	    pantallaDanados.sendTextUsingKeyEventsT("JAM");
		Thread.sleep(1000);
		pantallaDanados.clicPrimerArticuloListado();
		Thread.sleep(1000);
		pantallaDanados.presionarbotonSiDescripcionArticulo();
		Thread.sleep(1000);
		pantallaDanados.aumentarcantidadskuDanados();
		Thread.sleep(1000);
		pantallaDanados.tomarfotografiaDanadosactualizado();
		Thread.sleep(1000);
		pantallaDanados.clickBotonContinuarDanados();
		System.out.println("Iniciando clic en continuar");
		Thread.sleep(1000);
		pantallaDanados.clickBotonSiConfirmarFinalizacionDeDanados();
		System.out.println("Iniciando clic en confirmar terminar danados");
		Thread.sleep(1000);
		pantallaDanados.clickBotonSiPasarACaducosDesdeDanados();
		System.out.println("Iniciando clic en pasar a caducos");
		Thread.sleep(1000);
		
		Mobile.cedis.om.C04_Acreditacion_de_Caducos pantallaCaducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		Thread.sleep(1000);
		pantallaCaducos.presionarbotonIngresarCodigoSKU();
		Thread.sleep(1000);
		pantallaCaducos.sendTextUsingKeyEventsT("JAM");
		Thread.sleep(1000);
		pantallaCaducos.clicprimerarticulodespegable();
		Thread.sleep(1000);
		pantallaCaducos.presionarbotonSiDescripcionArticulo();
		Thread.sleep(1000);
		pantallaCaducos.aumentarcantidadskucaduco();
		Thread.sleep(1000);
		pantallaCaducos.tomarfotografiacaducoTT();
		Thread.sleep(1000);
		pantallaCaducos.continuararesumen();
		Thread.sleep(1000);
		
		Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion pantallaResumen= new Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion(testCase, appiumUtil, this);
		 Thread.sleep(3000);
		  pantallaResumen.validarBotonesvalidarMensajeEnResumen("Si", true, true, "Se ubica en la pantalla \"Resumen\", hasta abajo del flujo de Acreditación", "?Se despliega correctamente el botón: \"Modificar Dañados y Caducos\"", "Se da clic en el botón: \"Modificar Dañados y Caducos\"", "?Se despliega el siguiente mensaje: \"¿Desea modificar los artículos Dañados y Caducos?\" [NO] [SI]");
	  
	  /*Paso 3 Seleccionar Si".
	  Resultado: ?Se despliega correctamente el apartado de "Articulos Dañados".
     */
		  addStep("Se despliega correctamente el apartado de \"Articulos Dañados");
		  Mobile.cedis.om.C03_Acreditacion_de_Danados danados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		  danados.validarPantallaDanados();
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 4 Se da clic en el botón "-" para disminuir la cantidad de un artículo dañado previamente ingresado".
	  Resultado: ?Se borra la evidencia del artículo afectado.
	  */
		  addStep("Se borra la evidencia del artículo afectado");
		  danados.disminuircantidadskuDanados();
		  Thread.sleep(3000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 5 Se toma denuevo la evidencia del artículo afectado".
	  Resultado: ?Se guarda la evidencia correctamente.
	  */
		  addStep("Se guarda la evidencia correctamente");
		  danados.tomarfotografiaDanadosactualizado();
		  Thread.sleep(3000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 6 Se da clic en el boton: "Ingresar codigo SKU".
	  Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
	  */	
		  addStep("?Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
		  danados.presionarbotonIngresarCodigoSkuV();
		  Thread.sleep(3000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 7 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo".
	  Resultado: ?Se despliega un listado de los articulos relacionados a los caracteres ingresados.
	  */
		  addStep("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		  danados.sendTextUsingKeyEventsT("TRI");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 8 Se da clic sobre el articulo que queramos ingresar".
	  Resultado: ?Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
	  */
		  addStep("Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
		  danados.clicPrimerArticuloListado();
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 9 Se da clic en el botón : "Si".
	  Resultado: ?Se despliega el artículo correctamente.
	  */
		  addStep("Se despliega el artículo correctamente");
         danados.presionarbotonSiDescripcionArticulo();
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 10 Se toma la evidencia del artículo afectado".
	  Resultado: ?Se guarda la evidencia correctamente.
	  */
		  addStep("Se guarda la evidencia correctamente");
		  danados.tomarfotografiaDanadossegundoarticulo();
		  Thread.sleep(3000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 11 Se da clic en el botón : "Continuar"".
	  Resultado: ?Se despliega el siguiente mensaje: "¿Ha terminado la Devolucion de Articulos Dañados?" [NO] [SI].
     */
		  addStep("Se despliega el siguiente mensaje: \"¿Ha terminado la Devolucion de Articulos Dañados?\" [NO] [SI]");
		  danados.clickBotonContinuarDanados();
		  Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 12 Se da clic en el botón : "Si".
	  Resultado: ?Se despliega el siguiente mensaje: "¿Hay articulos Caducos en este Pedido?" [NO] [SI].
	  */
		  addStep("Se despliega el siguiente mensaje: \"¿Hay articulos Caducos en este Pedido?\" [NO] [SI]");
	      danados.clickBotonSiConfirmarFinalizacionDeDanados();
	      Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 13 "Se da clic en el botón : "Si".
     Resultado: ?Se despliega correctamente el apartado de "Articulos Caducos", con un artículo ingresado.
	  */
		  addStep("Se despliega correctamente el apartado de \"Articulos Caducos\", con un artículo ingresado");
		  danados.clickBotonSiConfirmarFinalizacionDeDanados();
		  Mobile.cedis.om.C04_Acreditacion_de_Caducos Caducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		  Caducos.pantallaCaducos();
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

	  /*Paso 14 Se da clic en el botón "-" para disminuir la cantidad de un artículo caduco previamente ingresado".
	   Resultado: ?Se borra la evidencia del artículo afectado.
	   */
		  addStep("Se borra la evidencia del artículo afectado");
		  Caducos.disminuircantidadskucaduco();
		  Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 15 Se toma denuevo la evidencia del artículo afectado".
	   Resultado: ?Se guarda la evidencia correctamente.
	    */
		  addStep("Se guarda la evidencia correctamente");
		  Caducos.tomarfotografiacaduco();
		  Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	  /*Paso 16 Se da clic en el boton: "Ingresar codigo SKU"".
	   Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
	    */
		  addStep("Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
		  Caducos.presionarbotonIngresarCodigoSKU();
		  Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		  /*Paso 17 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo".
		   Resultado: ?Se despliega un listado de los articulos relacionados a los caracteres ingresados.
		    */	
		  addStep("Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		  Caducos.sendTextUsingKeyEventsT("TRI");
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  /*Paso 18 Se da clic sobre el articulo que queramos ingresar".
		   Resultado: ?Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
		    */	
		  addStep("Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
		  Caducos.clicprimerarticulodespegable();
		  Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  /*Paso 19 Se da clic en el botón : "Si"".
		   Resultado: ?Se despliega el artículo correctamente.
		    */	
		  addStep("Se despliega el artículo correctamente");
		  Caducos.presionarbotonSiDescripcionArticulo();
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  /*Paso 20 Se toma la evidencia del artículo afectado".
		   Resultado: ?Se guarda la evidencia correctamente.				   
		    */	
		  addStep("Se guarda la evidencia correctamente");
		  Caducos.tomarfotografiaDanadossegundoarticulo();
		  Thread.sleep(2000);
		  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  				  
	  // Cerrar Apps
	  System.out.println("Terminando caso y cerrando OaD...");
					
	}
	
	
	
	/*
	  CEDIS MVP3: ATC_FT_011_Handheld_Acreditacion_Modificar_Dañados_Agregar_Y_Quitar_Un_Articulo_Dañado
	  Desc: "Validar que al dar clic en: "Modificar Dañados", podamos agregar y quitar artículos dañados"
	  @autor: Ricardo Hdz
	  @date: 16/08/2024
	 */
	
	@BeforeTest
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*Haber recepcionado un pedido con cajas/canastillas\r\n"
				+ "*Haber verificado ese mismo pedido\r\n"
				+ "*Haber ingresado artículos dañados y caducos en sus apartados";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en: \"Modificar Dañados y Caducos\", podamos agregar y quitar artículos dañados y caducos";
	}

	@Override
	public String setTestDesigner() {
		return "Marcelo Gutiérrez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_TABLETACREDITACIONMensajeAlSeleccionarUnSKUDeUnDANADODelListadoFiltrado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}			
}


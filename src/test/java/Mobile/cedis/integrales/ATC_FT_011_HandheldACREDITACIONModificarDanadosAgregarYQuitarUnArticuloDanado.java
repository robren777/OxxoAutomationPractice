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
 * Validar que al dar clic en: "Modificar Dañados", podamos agregar y quitar artículos dañados
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_011_HandheldACREDITACIONModificarDanadosAgregarYQuitarUnArticuloDanado
 *
 * PRERREQUISITOS:
 * 1. Haber recepcionado un pedido con cajas/canastillas
 * 2. Haber verificado ese mismo pedido
 * 3. Haber ingresado algún artículo como dañado para poder reducir su cantidad
 *
 * @author Ricardo Hdz Rios
 * @date 16 Enero 2024
 */


public class ATC_FT_011_HandheldACREDITACIONModificarDanadosAgregarYQuitarUnArticuloDanado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_011_HandheldACREDITACIONModificarDanadosAgregarYQuitarUnArticuloDanado_test(
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
		//verificacion.necesitaPermisos();
		verificacion.esperaDinamicaLoaderDeVerificacion(85000);
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
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	    
	    Mobile.cedis.om.C03_Acreditacion_de_Danados pantallaDanados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
	    pantallaDanados.ingresar3primerasletras("JAM");
	    pantallaDanados.clicPrimerArticuloListado();
	    pantallaDanados.presionarbotonSiDescripcionArticulo();
	    pantallaDanados.aumentarcantidadskuDanados();
	    pantallaDanados.tomarfotografiaDanadosactualizado();
	    pantallaDanados.continuarACaducos();
	    
	    //OM a utilizar
		Mobile.cedis.om.C04_Acreditacion_de_Caducos verificacionCaducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);

	    
		/*Paso 1 Se ubica en la pantalla "Artículos Caducos" del flujo de Acreditación".
		 Resultado: ?Se despliega correctamente el apartado de "Articulos Caducos"".
		*/
	    verificacionCaducos.validarPantallaCaducos("Se despliega correctamente el apartado de Articulos Caducos");
		System.out.println("Se despliega correctamente el apartado de Articulos Caducos");	    
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
	    
	    /*Paso 2 Se da clic en el botón: "Modificar Dañados".
		 Resultado: ?Se despliega correctamente el apartado de "Articulos Dañados".
		*/
		addStep("Se da clic en modificar dañados", "Se despliega correctamente el apartado de dañados");
		System.out.println("Se despliega correctamente el apartado de dañados");	    		
		pantallaDanados.validarPantallaDanados();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	    
	    /*Paso 3 Se da clic en el botón "-" para disminuir la cantidad de un artículo dañado previamente ingresado.
		 Resultado: ? Se borra la evidencia del artículo afectado".
		 */
		addStep("Se elimina la imagen capturada al momento de disminuir la cantidad del articulo dañado");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		pantallaDanados.disminuircantidadskuDanados();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		System.out.println("Se elimina la imagen capturada al momento de disminuir la cantidad del articulo dañado");	    				
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		    
		/*Paso 4 Se toma denuevo la evidencia del artículo afectado.
	      Resultado:  ? Se guarda la evidencia correctamente".
	     */
		    addStep("Se guarda correctamente la evidencia capturada");
		    pantallaDanados.tomarfotografiaDanadosactualizado();
		    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			System.out.println("Se guarda correctamente la evidencia capturada");	    				
		    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
		/*Paso 5 Se da clic en el boton: "Ingresar codigo SKU".
		  Resultado: ? Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"".
		 */
		    addStep("Se muestra el campo Escribe el SKU o nombre del articulo");
		    pantallaDanados.presionarbotonIngresarCodigoSKU();
		    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			System.out.println("Se muestra el campo Escribe el SKU o nombre del articulo");	    							
		    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
			    
		  /*Paso 6 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo.
			 Resultado: ? Se despliega un listado de los articulos relacionados a los caracteres ingresados".
			*/
			addStep("Se despliega el listado de articulos relacionados a los caracteres ingresados");
		    pantallaDanados.ingresar3primerasletrasV();
		    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			System.out.println("Se despliega el listado de articulos relacionados a los caracteres ingresados\"");	    							
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
		  /*Paso 7 Se da clic sobre el articulo que queramos ingresar.
			 Resultado: ? Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
			*/
			addStep("Se despliega el mensaje correctamente");
			pantallaDanados.clicPrimerArticuloListado();
			appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			System.out.println("Se despliega el mensaje correctamente");	    							
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		  /*Paso 8 Se da clic en el botón : "Si".
			 Resultado: ? Se despliega el artículo correctamente.
			*/
			addStep("El articulo se captura correctamente");
		    pantallaDanados.presionarbotonSiDescripcionArticulo();
		    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			System.out.println("El articulo se captura correctamente");	    							
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 
			/*Paso 9 Se toma la evidencia del artículo agregado.
			 Resultado: ?Se guarda la evidencia correctamente.
			*/
			addStep("La evidencia se captura correctamente");
			//pantallaDanados.tomarfotografiasegundoarticuloDanados();
			pantallaDanados.tomarfotografiaDanadossegundoarticulo();
			appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			System.out.println("La evidencia se captura correctamente");	    							
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
				+ "5. Haber recepcionado un pedido con cajas/canastillas\r\n"
				+ "6. Que el SKU que se meterá como caduco no sea faltante \r\n"
				+ "7. Haber verificado ese mismo pedido\r\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en: Modificar Dañados, podamos agregar y quitar artículos dañados";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hdz";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_HandheldACREDITACIONModificarDanadosAgregarYQuitarUnArticuloDanado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}			
}
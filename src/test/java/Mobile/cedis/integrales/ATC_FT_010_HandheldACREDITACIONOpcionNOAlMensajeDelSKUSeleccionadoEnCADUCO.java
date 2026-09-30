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
 *DESCRIPCIÓN:
 *Validar que al seleccionar "NO" al mensaje de confirmación el sistema permita al operador ingresar en 
  el campo de texto otro número/descripción
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *MTC_FT_010_HandHeldACREDITACIÓNOpcionNOAlMensajeDelSKUSeleccionadoEnCADUCO
 *
 *PRERREQUISITOS:
 *1.Haber recepcionado un pedido con cajas/canastillas
 *2.Haber verificado ese mismo pedido
 *
 *@author Ricardo Hdz Rios
 *@date   16/08/2024
 */


public class ATC_FT_010_HandheldACREDITACIONOpcionNOAlMensajeDelSKUSeleccionadoEnCADUCO extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_010_HandheldACREDITACIONOpcionNOAlMensajeDelSKUSeleccionadoEnCADUCO_test(
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
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnElSegundoBultoDelPadre();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
		verificacion.VerificarArticulos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		verificacion.continuarCaducosDirecto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//OM a utilizar
        Mobile.cedis.om.C04_Acreditacion_de_Caducos verificacionCaducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);

		
		/*Paso 1 Dentro de la pantalla CADUCOS se da clic en el boton: "Ingresar codigo SKU".
		 Resultado: ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo".
		*/
	        addStep("Se despliega correctamente el campo flotante con el el placeholder: \"Escribe el SKU o nombre del articulo");
	        verificacionCaducos.presionarbotonIngresarCodigoSKU();
	        appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			System.out.println("Se presiona el boton ingresar codigo SKU");
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
	    
	    /*Paso 2 Filtra el sku o nombre un artículo que se logró recepcionar.
		 Resultado: ?Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a las letras colocadas.
		*/
			 addStep("Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a las letras colocadas");
		     verificacionCaducos.ingresar3primerasletras();
			 System.out.println("Se ingresan las tres letras del articulo a seleccionar");
		     testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
	    
	    /*Paso 3 Selecciona el resultado del filtro que está relaccionado al artículo que quieres ingresar como caduco.
		 Resultado: ?Se muestra mensaje "Ingresaste el siguiente artículo: #SKU + DESCRIPCIÓN DE ARTÍCULO  ¿Deseas continuar? NO/SI ".
		*/
		    addStep("Se selecciona el primer articulo que se quiere ingresar como caduco y se despliega el mensaje correctamente");
		    verificacionCaducos.clicprimerarticulodespegable();
			System.out.println("Se selecciona el primer articulo desplegado de la lista");
			appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		   
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		    
		    /*Paso 4 Selecciona NO.
			 Resultado: ?El  mensaje desaparece y el sistema permite que busques otro artículo ".
			*/
		    addStep("Se selecciona NO, y no se enlista el articulo seleccionado y nos devuelve a la pantalla para ingresar un nuevo articulo caduco");
		    verificacionCaducos.presionarbotonNoDescripcionArticulo(); 
		    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			System.out.println("Se selecciona NO al mensaje mostrado, y nos devuelve a la pantalla de caducos");
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
				+ "7. Haber verificado ese mismo pedido\r\n";
	}


	@Override
	public String setTestDescription() {
		return "Validar que al seleccionar \"NO\" al mensaje de confirmación el sistema permita al operador ingresar en el campo de texto otro número/descripción ";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hdz";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_HandheldACREDITACIONOpcionNOAlMensajeDelSKUSeleccionadoEnCADUCO";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}	
}
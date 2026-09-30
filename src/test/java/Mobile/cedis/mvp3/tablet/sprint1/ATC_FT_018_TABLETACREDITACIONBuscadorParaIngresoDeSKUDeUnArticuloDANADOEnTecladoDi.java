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
 
public class ATC_FT_018_TABLETACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloDANADOEnTecladoDi extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_018_TABLETACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloDANADOEnTecladoDi_Test(HashMap<String, String> data) throws Exception {
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
		listadoverificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();;//Realiza la busqueda de una verificación
		
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
	    Thread.sleep(1000);
	    pantallaDanados.presionarbotonIngresarCodigoSKU();
		Thread.sleep(300);
	    pantallaDanados.sendTextUsingKeyEventsT("JAM");
	    Thread.sleep(1000);
	    pantallaDanados.clicPrimerArticuloListado();
	    Thread.sleep(1000);
	    pantallaDanados.presionarbotonSiDescripcionArticulo();
	    Thread.sleep(1000);
	    pantallaDanados.aumentarcantidadskuDanados();
	    Thread.sleep(1000);
	    pantallaDanados.tomarfotografiaDanados();
	    Thread.sleep(1000);
	    pantallaDanados.continuarACaducos();
	    Thread.sleep(1000);
	    
		/*Paso 1 Se ubica en la pantalla "Artículos Caducos" del flujo de Acreditación".
		 Resultado: ?Se despliega correctamente el apartado de "Articulos Caducos"".
		*/
		Mobile.cedis.om.C04_Acreditacion_de_Caducos verificacionCaducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
	    verificacionCaducos.validarPantallaCaducos("Se despliega correctamente el apartado de Articulos Caducos");
	    Thread.sleep(1000);
	    
	    /*Paso 2 Se da clic en el botón: "Modificar Dañados".
		 Resultado: ?Se despliega correctamente el apartado de "Articulos Dañados".
		*/
		addStep("Se da clic en modificar dañados", "Se despliega correctamente el apartado de dañados");
		pantallaDanados.validarPantallaDanados();
		
	    
	    /*Paso 3 Se da clic en el botón "-" para disminuir la cantidad de un artículo dañado previamente ingresado.
		 Resultado: ? Se borra la evidencia del artículo afectado".
		 */
		addStep("Se elimina la imagen capturada al momento de disminuir la cantidad del articulo dañado");
		pantallaDanados.disminuircantidadskuDanados();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		    
		/*Paso 4 Se toma denuevo la evidencia del artículo afectado.
	      Resultado:  ? Se guarda la evidencia correctamente".
	     */
		    addStep("Se guarda correctamente la evidencia capturada");
		    pantallaDanados.tomarfotografiaDanados();
		    Thread.sleep(3000);
		    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
		/*Paso 5 Se da clic en el boton: "Ingresar codigo SKU".
		  Resultado: ? Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"".
		 */
		    addStep("Se muestra el campo Escribe el SKU o nombre del articulo");
		    pantallaDanados.presionarbotonIngresarCodigoSKU();
		    Thread.sleep(1000);
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
			    
		  /*Paso 6 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo.
			 Resultado: ? Se despliega un listado de los articulos relacionados a los caracteres ingresados".
			*/
			addStep("Se despliega el listado de articulos relacionados a los caracteres ingresados");
		    pantallaDanados.sendTextUsingKeyEventsT("TRI");
			Thread.sleep(1000);
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
		  /*Paso 7 Se da clic sobre el articulo que queramos ingresar.
			 Resultado: ? Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
			*/
			addStep("Se despliega el mensaje correctamente");
			pantallaDanados.clicPrimerArticuloListado();
			Thread.sleep(1000);
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		  /*Paso 8 Se da clic en el botón : "Si".
			 Resultado: ? Se despliega el artículo correctamente.
			*/
			addStep("El articulo se captura correctamente");
		    pantallaDanados.presionarbotonSiDescripcionArticulo();
			Thread.sleep(1000);
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		  /*Paso 9 Se toma la evidencia del artículo agregado.
			 Resultado: ?Se guarda la evidencia correctamente.
			*/
			addStep("La evidencia se captura correctamente");
			pantallaDanados.tomarfotografiasegundoarticuloDanadosTT();
			Thread.sleep(3000);
			testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
					
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
				+ "*Haber ingresado algún artículo como dañado para poder reducir su cantidad";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en: \"Modificar Dañados\", podamos agregar y quitar artículos dañados";
	}

	@Override
	public String setTestDesigner() {
		return "Marcelo Gutiérrez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_TABLET_ACREDITACION_Modificar_Dañados_Agregar_Y_Quitar_Un_Articulo_Dañado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}			
}


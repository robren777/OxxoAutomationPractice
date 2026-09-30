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
 * Validar que al dar clic en: "Modificar Dañados", podamos agregar y quitar artículos dañados
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT_016_TABLETACREDITACIÓNModificarDañadosAgregarYQuitarUnArtículoDañado
 *
 * PRERREQUISITOS:
 * 1. Haber recepcionado un pedido con cajas/canastillas
 * 2. Haber verificado ese mismo pedido
 * 3. Haber ingresado algún artículo como dañado para poder reducir su cantidad
 *
 * 
 * 
 */

public class ATC_FT_016_TABLETACREDITACIONModificarDanadosAgregarYQuitarUnArticuloDanado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_016_TABLETACREDITACIONModificarDanadosAgregarYQuitarUnArticuloDanado_test(
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
		//cap.setCapability("udid", data.get("udId"));

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
		
		C03_Acreditacion_de_Danados.ingresar3primerasletras("JAM");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.clicPrimerArticuloListado();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.presionarbotonSiDescripcionArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.aumentarcantidadskuDanados();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.tomarfotografiaDanadosactualizado();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.continuarACaducos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		/*Paso 1 Se ubica en la pantalla "Artículos Caducos" del flujo de Acreditación".
		 Resultado: ?Se despliega correctamente el apartado de "Articulos Caducos"".
		*/
		C04_Acreditacion_de_Caducos.validarPantallaCaducos("Se despliega correctamente el apartado de Articulos Caducos");
		System.out.println("Se despliega correctamente el apartado de Articulos Caducos");	    
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
	    
	    /*Paso 2 Se da clic en el botón: "Modificar Dañados".
		 Resultado: ?Se despliega correctamente el apartado de "Articulos Dañados".
		*/
		addStep("Se da clic en modificar dañados", "Se despliega correctamente el apartado de dañados");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.validarPantallaDanados();
		System.out.println("Se despliega correctamente el apartado de dañados");
		
		
		/*Paso 3 Se da clic en el botón "-" para disminuir la cantidad de un artículo dañado previamente ingresado.
		 Resultado: ? Se borra la evidencia del artículo afectado".
		 */
		addStep("Se elimina la imagen capturada al momento de disminuir la cantidad del articulo dañado");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		C03_Acreditacion_de_Danados.disminuircantidadskuDanados();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		System.out.println("Se elimina la imagen capturada al momento de disminuir la cantidad del articulo dañado");	    				
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		/*Paso 4 Se toma denuevo la evidencia del artículo afectado.
	      Resultado:  ? Se guarda la evidencia correctamente".
	     */
	    addStep("Se guarda correctamente la evidencia capturada");
	    C03_Acreditacion_de_Danados.tomarfotografiaDanadosactualizado();
	    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		System.out.println("Se guarda correctamente la evidencia capturada");	    				
	    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	    
	    /*Paso 5 Se da clic en el boton: "Ingresar codigo SKU".
		  Resultado: ? Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"".
		 */
	    addStep("Se muestra el campo Escribe el SKU o nombre del articulo");
	    C03_Acreditacion_de_Danados.presionarbotonIngresarCodigoSKU();
	    appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		System.out.println("Se muestra el campo Escribe el SKU o nombre del articulo");	    							
	    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	    
	    /*Paso 6 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku de un articulo.
		 Resultado: ? Se despliega un listado de los articulos relacionados a los caracteres ingresados".
		*/
		addStep("Se despliega el listado de articulos relacionados a los caracteres ingresados");
		C03_Acreditacion_de_Danados.ingresar3primerasletrasV();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		System.out.println("Se despliega el listado de articulos relacionados a los caracteres ingresados\"");	    							
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7 Se da clic sobre el articulo que queramos ingresar.
		 Resultado: ? Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI].
		*/
		addStep("Se despliega el mensaje correctamente");
		C03_Acreditacion_de_Danados.clicPrimerArticuloListado();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		System.out.println("Se despliega el mensaje correctamente");	    							
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	    
		/*Paso 8 Se da clic en el botón : "Si".
		 Resultado: ? Se despliega el artículo correctamente.
		*/
		addStep("El articulo se captura correctamente");
		C03_Acreditacion_de_Danados.presionarbotonSiDescripcionArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		System.out.println("El articulo se captura correctamente");	    							
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 9 Se toma la evidencia del artículo agregado.
		 Resultado: ?Se guarda la evidencia correctamente.
		*/
		addStep("La evidencia se captura correctamente");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		//pantallaDanados.tomarfotografiasegundoarticuloDanados();
		C03_Acreditacion_de_Danados.tomarfotografiaDanadossegundoarticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		System.out.println("La evidencia se captura correctamente");	    							
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	}


	@Override		
	public String setTestFullName() {
		return " ATC-FT-016-TABLET ACREDITACIÓN \"Modificar Dañados\"-Agregar y quitar un artículo dañado";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al dar clic en: \"Modificar Dañados\", podamos agregar y quitar artículos dañados";
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
				+ "* Haber ingresado algún artículo como dañado para poder reducir su cantidad";
	}

	@Override
	public void beforeTest() {
	}

}
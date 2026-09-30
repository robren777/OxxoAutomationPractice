package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertTrue;

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
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;



public class ATC_FT_002_HandheldACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnDANADOS extends BaseExecution { 
	@Test(dataProvider = "data-provider", enabled = true) 
	public void ATC_FT_002_HandheldACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnDANADOS_test(HashMap<String, String> data) throws Exception {	
		

	/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities caps = new DesiredCapabilities();
		
		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));
		//caps.setCapability("appium:appPackage", data.get("appPackageP"));
		//caps.setCapability("appium:appActivity", data.get("appActivityP"));
		caps.setCapability("ip", data.get("ip"));
		caps.setCapability("userLogin", data.get("userLogin"));
		caps.setCapability("passwordLogin", data.get("passwordLogin"));
		caps.setCapability("numTransferencia", data.get("numTransferencia"));
		//caps.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";
		
		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
	//================================ Abrir aplicativo =====================================//
	AppiumUtil appiumUtil = null;
	try {
		appiumUtil = new AppiumUtil(url, caps);
	} catch (SessionNotCreatedException e) {
		assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
				+ e.getMessage());
		e.printStackTrace();
	}
	
	
	//---------------------------------------- INICIO ------------------------------------------------

			//En el login de punto de venta ingreso la ip y el puerto
			A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
			System.out.println("Logeando Punto de Venta");
			Thread.sleep(2000);
			LoginPDVOaD.loginPV("10.217.24.107");

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
			Thread.sleep(10000);
			verificacion.verificarArticulo();
			Thread.sleep(2000);
			verificacion.darClickBotonBultos();
			Thread.sleep(2000);
			verificacion.hacerClickEnElSegundoBultoDelPadre();
			Thread.sleep(2000);
			verificacion.tomarFoto();
			Thread.sleep(10000);
			verificacion.VerificarArticulos();
			Thread.sleep(2000);
			verificacion.continuarDanadosHH();
			Thread.sleep(5000);
			
//			------------------------------------- //FALTA VERIFICAR PEDIDO EL METODO ---------------------------------------------------
	
	
	
	C03_Acreditacion_de_Danados danados = new C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
	
	//=================================== Inicio de Caso =====================================//
	/*
	 *  Paso 1 - Estar en la pantalla de "Dañados" 
		Nota: Previo hay que realizar la verificación del pedido 
	 */
	addStep("Estar en la pantalla de Dañados");
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	/*
	 *  Paso 2 - ?Se muestra pantalla de dañados correctamente 
	 */
	addStep("?Se muestra pantalla de dañados correctamente");
	danados.pantallaDanados();
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	/*
	 *  Paso 3 - Dentro de la pantalla se da clic en el boton: "Ingresar codigo SKU" 
	 */
	addStep("Dentro de la pantalla se da clic en el boton: Ingresar codigo SKU");
	danados.presionarbotonIngresarCodigoSKU();
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	/*
	 *  Paso 4 - ?Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo"
	 */
	addStep("?Se despliega un campo flotante con el placeholder: Escribe el SKU o nombre del articulo");
	danados.siexisteTXT();
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	/*
	 *  Paso 5 - Ingresa las primeras 3 letras del nombre del artículo
	 */
	addStep("Ingresa las primeras 3 letras del nombre del artículo");
	danados.sendTextUsingKeyEvents("JAM");
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	/*
	 *  Paso 6 - ?Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a las letras colocadas
	 */
	addStep("?Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a las letras colocadas");
	danados.validardescripcionarticuloV();
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	/*
	 *  Paso 7 - Borra e ingresa los primeros 3 números del SKU correspondiente al artículo
	 */
	addStep("Borra e ingresa los primeros 3 números del SKU correspondiente al artículo");
	danados.ingresar3primerosnumerosV();
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
	/*
	 *  Paso 8 - ?Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a los numeros colocados
	 */
	addStep("?Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a los numeros colocados");
	danados.validardescripcionarticuloV();
	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	
}
	/*
	 * Cedis MVP3 Sprint 1: ATC_FT_002_HandheldACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnDANADOS
	 * Descripción:Validar que en el campo de texto al ingresar 3 caracteres alfanumericos 
	 * 				se aplique un filtro y muestre el listado correspondiente
	 *
	 * @autor Vanessa Torres
	 * @date 16/08/2024
	 */ 
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_002_HandheldACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnDANADOS";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar que en el campo de texto al ingresar 3 caracteres alfanumericos se aplique un filtro y muestre el listado correspondiente. ";
	}
	
	@Override
	public String setTestDesigner() {
		return "Vanessa Torres";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override 
	public String setPrerequisites() {
		return "Haber recepcionado un pedido con cajas/canastillas"
				+ "Haber verificado ese mismo pedido"
				+ "Haber seleccionado el botón \"Ingresar codigo SKU\"";
	}
	
	@Override
	public void beforeTest() {
	}	
	
}
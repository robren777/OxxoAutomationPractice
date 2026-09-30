package Mobile.cedis.mvp3.tablet.sprint1;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_024_TABLETACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnCADUCOS extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_024_TABLETACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnCADUCOS_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
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
		//cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";

		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));
;				
    	@SuppressWarnings("null")
		@Nonnull
		AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
    	//Abre la aplicación 
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
							
		//Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this); 
		login.loginPV(data.get("ip"));
		
		//Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this); 
		//Obtiene valor del DataProvaider
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		//Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.Tablet_A03_Menus_y_submenus herramienta = new Mobile.cedis.om.Tablet_A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.logeoInicioHerramientas();
		
		Mobile.cedis.om.A04_MenuHerramientas menuCedis =  new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnVerificacionMercanciaCEDIS();//Selecciona la opcion de Verificacion
								
		Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoverificacion = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		listadoverificacion.busquedaNumTransferencia(data.get("numTransferencia"));//Realiza la busqueda de una verificación 
		Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos verificacion = new Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		Thread.sleep(3000);
		//verificacion.necesitaPermisos();
		verificacion.barraProceso();
		verificacion.darClickBotonBultos();
		verificacion.darClickPrimerBulto();
		verificacion.tomarFotoTablet();
		Thread.sleep(2000);
		verificacion.verificarArticuloTablet();
		Thread.sleep(2000);
		verificacion.darClickBotonBultos();
		verificacion.darClickSegundoBulto();
		verificacion.tomarFotoTablet();
		Thread.sleep(2000);
		verificacion.verificarArticuloTablet();
		Thread.sleep(2000);
		verificacion.continuarCaducosDirecto();
		
		//SCRIPT DEL CASO DE PRUEBA//
		
		Mobile.cedis.om.C04_Acreditacion_de_Caducos caducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		//Paso #1: Estar en la pantalla de "Caducos""
		addStep("Estar en la pantalla de \"Caducos\" ");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #2: ?Se muestra pantalla de caducos correctamente "
		addStep("Resultado Esperado: ?Se muestra pantalla de caducos correctamente");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #3: Dentro de la pantalla se da clic en el boton: "Ingresar codigo SKU""
		addStep("Dentro de la pantalla se da clic en el boton: \"Ingresar codigo SKU\"");		
		Thread.sleep(1000);
		caducos.presionarbotonIngresarCodigoSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #4: ?Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\""
		addStep("Resultado Esperado: ?Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
		Thread.sleep(1000);
		caducos.cuadroTextoIngresarCodigoSKUVerificado();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #5: Ingresa las primeras 3 letras del nombre del artículo."
		addStep("Ingresa las primeras 3 letras del nombre del artículo.");	
		caducos.ingresar3primerasletras();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #6: ?Se despliega el flitro correctamente y muestra el sku + nombre del artículo completo relaccionado a las letras colocadas"
		addStep("Resultado Esperado: ?Se despliega el flitro correctamente y muestra el sku + nombre del artículo completo relaccionado a las letras colocadas");
		Thread.sleep(1000);
		caducos.validardescripcionarticulo();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #7: Ingresa las primeras 3 letras del nombre del artículo."
		addStep("Borra e ingresa los primeros 3 números del SKU correspondiente al artículo.");	
		Thread.sleep(1000);
		caducos.ingresar3primerosnumeros();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Paso #8: ??Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a los numeros colocados"
		addStep("Resultado Esperado: ?Se despliega el flitro correctamente y muestra el sku+nombre del artículo completo relaccionado a los numeros colocados");
		Thread.sleep(1000);
		caducos.validardescripcionarticulo();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		// cerrar Apps
		Thread.sleep(5000);
		//Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	}



	@BeforeTest
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*Contar con dispositivo Tablet\r\n"
				+ "*Haber recepcionado un pedido con cajas/canastillas\r\n"
				+ "*Haber verificado ese mismo pedido";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en el campo de texto al ingresar 3 caracteres alfanumericos se aplique un filtro y muestre el listado correspondiente";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Lugo";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_024_TABLETACREDITACIONFiltroAPartirDe3CaracteresAlfanumericosEnCADUCOS";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
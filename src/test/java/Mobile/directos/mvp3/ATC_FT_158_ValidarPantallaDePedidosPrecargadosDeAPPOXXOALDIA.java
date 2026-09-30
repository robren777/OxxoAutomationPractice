package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


public class ATC_FT_158_ValidarPantallaDePedidosPrecargadosDeAPPOXXOALDIA
extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_158_ValidarPantallaDePedidosPrecargadosDeAPPOXXOALDIA_test(HashMap<String, String> data) throws Exception {
	DesiredCapabilities cap = new DesiredCapabilities();
	 
	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformVersion", data.get("platformVersion"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	cap.setCapability("appium:settings[enableMultiWindows]", true);
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";
	
	//======= Cerrar Aplicaciones =========
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	  
	//======================================================================================================
	AppiumUtil appiumUtil = null;
	try {
		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}			
 //=======================================================================================================//		
	//Llamado al OM de Login.
	Login LoginDirectos = new Login(testCase, appiumUtil, this);
	//Llamado al OM de Oxxo Al Dia.
	OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
	 //=======================================================================================================//
	OxxoAlDia.validaCrEnNubeEvolucion(data.get("IpPV"));
	LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
	/*
	 * Paso 1 - Ingresar a la app 'Oxxo al dia' en el Menu Excelencia Operativa>>Herramienta de compra>>Cometa>>Verificacion de Mercancia Pedidos Proveedor Directo
	 */
	addStep("Ingresar a la app 'Oxxo al dia' en el Menu Excelencia Operativa>>Herramienta de compra>>Cometa>>Verificacion de Mercancia Pedidos Proveedor Directo");
	OxxoAlDia.llegarAPantallaVerfiMercancia();
	
	/*
	 * Paso 2 - Verificar que se muestre la pantalla 'PROVEEDORES DIRECTOS'
	 */
	addStep("Verificar que se muestre la pantalla 'PROVEEDORES DIRECTOS'");
	boolean validacion = OxxoAlDia.validaExistePantallaProveedoresDirectos();
	assertTrue(validacion,"Error, No se encontro la pantalla: PROVEEDORES DIRECTOS");
	
	/*
	 * Paso 3 - Seleccionar la opcion “Busqueda pedidos Precargados”
	 */
	addStep("Seleccionar la opcion 'Busqueda pedidos Precargados'");
	OxxoAlDia.tapBtnBusquedaPedPrecargados();
	
	/*
	 * Paso 4 - Verificar que se muestre la pantalla “BUSQUEDA DE PEDIDO”
	 */
	addStep("Verificar que se muestre la pantalla “BUSQUEDA DE PEDIDO”");
	validacion = OxxoAlDia.validaExistePantallaBusquedaDePedido_PP();
	assertTrue(validacion,"Error, No se encontro la pantalla: BUSQUEDA DE PEDIDO");
	
	/*
	 * Paso 5 - Validar que muestre el encabezado 'PEDIDOS PRECARGADOS'
	 */
	addStep("Validar que muestre el encabezado 'PEDIDOS PRECARGADOS'");
	validacion = OxxoAlDia.validaEncabezadoPedidosPrecargados_PP();
	assertTrue(validacion,"Error, No se encontro el encabezado 'PEDIDOS PRECARGADOS'");
	
	/*
	 * Paso 6 - Validar que muestre el sub encabezado 'BÚSQUEDA POR:'
	 */
	addStep("Validar que muestre el sub encabezado 'BÚSQUEDA POR:'");
	validacion = OxxoAlDia.validaEncabezadoBusquedaPor_PP();
	assertTrue(validacion,"Error, No se encontro el encabezado 'BUSQUEDA POR:'");
	
	/*
	 * Paso 7 - Validar que se muestre el sub encabezado 'PEDIDOS' debajo del botón 'BUSCAR'
	 */
	addStep("Validar que se muestre el sub encabezado 'PEDIDOS' debajo del botón 'BUSCAR'");
	validacion = OxxoAlDia.validaEncabezadoPedidos_PP();
	assertTrue(validacion,"Error, No se encontro el encabezado 'PEDIDOS'");
	//=======================================Cerrar Aplicaciones========================================================//
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	//==================================================================================================================//
    
    
  }
  
  /*
   * Proveedores Directos MVP3: MTC-FT-158 Validar pantalla de Pedidos Precargados de APP OXXO AL DIA
   * Desc: Verificar que muestre el encabezado “PEDIDOS PRECARGADOS” “BÚSQUEDA POR:” Y “PEDIDOS” 
   * 
   * @autor Alex Aquino
   * @date 24/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_158_ValidarPantallaDePedidosPrecargadosDeAPPOXXOALDIA";
  }
  
  @Override
  public String setTestDescription() {
  	return "Verificar que muestre el encabezado “PEDIDOS PRECARGADOS” “BÚSQUEDA POR:” Y “PEDIDOS”";
  }  

  @Override
  public String setTestDesigner() {
  	return "Alex Aquino";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1 Contar con el APK más reciente de “Proveedores Directos”\r\n"
  			+ "2 Contar con una fuente de abastos valida\r\n"
  			+ "3 Contar con artículos en vigencia 1 ";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}

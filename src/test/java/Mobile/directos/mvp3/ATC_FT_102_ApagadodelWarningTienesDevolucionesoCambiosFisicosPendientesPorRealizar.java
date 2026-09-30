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


public class ATC_FT_102_ApagadodelWarningTienesDevolucionesoCambiosFisicosPendientesPorRealizar extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_102_ApagadodelWarningTienesDevolucionesoCambiosFisicosPendientesPorRealizar_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
		String url = "http://127.0.0.1:4723";

		//=======================================Cerrar Aplicaciones========================================================//
		  Thread.sleep(5000);
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		//==================================================================================================================//
	
		  addStep("Ingresar a la app \"Oxxo al día\" en el Menú Excelencia Operativa/Herramienta de compra/cometa/"
		  		+ "Verificación de Mercancia Pedidos Proveedor Directo/Leer QR para Preventa o Pedido ");
		  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500);
			
//=======================================================================================================//		
  /*
   * Llamado al OM de Login.
   */
		
  Login LoginDirectos = new Login(testCase, appiumUtil, this);
  
  /*
   * Llamado al OM de Oxxo Al Dia.
   */
  OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
  
//=======================================================================================================//
  
  /*
   * Paso 1 -  Ingresar a la app "Oxxo al día" en el Menú Excelencia Operativa/Herramienta de compra
   * /cometa/Verificación de Mercancia Pedidos Proveedor Directo/Leer QR para Preventa o Pedido 
   */
  
  LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
  
  OxxoAlDia.ingresarModuloHerramientasOff();
  OxxoAlDia.selec_VerificacionMercancia();
  OxxoAlDia.selec_LeerQrPreventaoPedido();
  
  
  /*
   * Paso 2 - Validar que no se muestre el siguiente mensaje warning: ¿Tienes Devoluciones o cambios físicos pendientes por realizar?
				Botón <NO> y Botón <SI>
   */
  
  addStep("Validar que no se muestre el siguiente mensaje warning: ¿Tienes Devoluciones o cambios físicos pendientes por realizar? "
  		+ "Botón <NO> y Botón <SI>");
  
  boolean validacion = OxxoAlDia.validarMensajePendientesOff();
  assertTrue(validacion,"Se sigue visualizando el mensaje.");
  
  /*
   * Paso 3 - Validar que se abra apartado para realizar el escaneo de QR.
   */
  
  addStep("Validar que se abra apartado para realizar el escaneo de QR.");
  
  validacion = OxxoAlDia.pantalla_Qr();
  assertTrue(validacion,"No se muestra pantalla de manera corecta.");
  
  /*
   * Paso 4 - Presionar el botón “BACK” nativo de Android .
   */
  
  addStep("Presionar el botón “BACK” nativo de Android.");
  
  validacion = OxxoAlDia.botonBACK();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 5 - Validar que nos regrese a la pantalla “PROVEEDORES DIRECTOS”
   */
  
  addStep("Validar que nos regrese a la pantalla “PROVEEDORES DIRECTOS”");
  
  validacion = OxxoAlDia.validarPantallaVerificacionMercancia();
  assertTrue(validacion,"La pantalla no se muestra de manera correcta");
  
  /*
   * Paso 6 -  Seleccionar el botón Generación de herramienta de compra.
   */
  
  addStep("Seleccionar el botón Generación de herramienta de compra.");
  
  OxxoAlDia.selec_GenHerramientaCompra();
  
  /*
   * Paso 7 - Validar que no se muestre el siguiente mensaje warning: ¿Tienes Devoluciones o cambios físicos pendientes por realizar?
				Botón <NO> y Botón <SI>
   */
  
  addStep("Validar que no se muestre el siguiente mensaje warning: ¿Tienes Devoluciones o cambios físicos pendientes por realizar? "
  		+ "Botón <NO> y Botón <SI>");
  
  validacion = OxxoAlDia.validarMensajePendientesOff();
  assertTrue(validacion,"Se sigue visualizando el mensaje.");
  
  /*
   * Paso 8 - Validar que se muestre la pantalla “GENERACION DE HERRAMIENTA DE COMPRA”
   */
  
  addStep("Validar que se muestre la pantalla “GENERACION DE HERRAMIENTA DE COMPRA”");
  
  validacion = OxxoAlDia.PantallaGeneracionDeHerramientaCompra();
  assertTrue(validacion,"La pantalla no se muestra de manera correcta");
  
  
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-102-Apagado del Warning “¿Tienes devoluciones o cambios físicos pendientes por realizar?”
   * 
   * Desc: Validar que no se visualice el mensaje “¿Tienes devoluciones o cambios físicos pendientes por realizar?” al ser apagado en central.
   * 
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Validar con el equipo de Desarrollo que la opción esta deshabilitada en las BD de Movilidad
   * 
   * @autor Alejandro Valdez
   * @date 24/10/2024
   */
  
  @Override
  public String setTestFullName() {
  	return "ATC_FT_100_CasoPrueba100";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que no se visualice el mensaje “¿Tienes devoluciones o cambios físicos pendientes por realizar?” al ser apagado en central.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Alejandro Valdez";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1.-Contar con la aplicación más reciente de Oxxo al día \r\n"
  			+ "2.- Validar con el equipo de Desarrollo que la opción esta deshabilitada en las BD de Movilidad";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}

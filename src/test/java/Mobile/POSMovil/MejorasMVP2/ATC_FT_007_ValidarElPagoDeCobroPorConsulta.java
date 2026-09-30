package Mobile.POSMovil.MejorasMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.POSMovil.om.LoginPM;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-007-Validar el pago de cobro por consulta.
* DESCRIPCIÓN: Al cancelar el servicio, se deberá de realizar el pago del cobro por consulta del servicio exitosamente
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Contar con los folios de los servicios no financieros
* Contar con .TRA de comisión por consulta / por servicios.
* 
* INSUMOS:
* Folio de servicio
* 
* @autor Cesar Guel
* @date 17/12/2025
*/
//========================================================================//

public class ATC_FT_007_ValidarElPagoDeCobroPorConsulta extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_007_ValidarElPagoDeCobroPorConsulta_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
//		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
//		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
//		cap.setCapability("platformName", data.get("platformName"));
//		cap.setCapability("appPackage", data.get("appPackage"));
//		cap.setCapability("appActivity", data.get("appActivity"));
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
//				
//		String url = "http://127.0.0.1:4723";
		
		/************************ Paso 1 - Ingresar a la App ***************************/
		addStep("Iniciar Aplicación POS Móvil");

  	AppiumUtil appiumUtil = null;
  	try {
//  		appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(500);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
  /*
   * Llamado al OM de Login de PosMovil.
   */
  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
  /*
   * Llamado al OM de PosMovil.
   */
  PM POSMOVIL = new PM(testCase, appiumUtil, this);
  
//================================================================================================================//
  /************************ Paso 2 - Inicio de sesión ***************************/
  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
  LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresarJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresarJournal,"Journal no encontrado");
  
  /************************ Paso 4 - Ingresar al apartado de servicios ***************************/
  addStep("Se ingresa al apartado de servicios");
  POSMOVIL.ingresarModServ();
  
  /************************ Paso 5 - Seleccionar servicio Sky en línea ***************************/
  addStep("Se selecciona el tipo de servicio Sky en línea");
  POSMOVIL.seleccionarServicioSky(data.get("servicio"));
  
  /************************ Paso 6 - Ingresar la información del cliente ***************************/
  addStep("Se ingresa la información del cliente");
  POSMOVIL.ingresarInfCliente(PasswordUtil.decryptPassword(data.get("infCliente")));
  
  /************************ Paso 7 - Confirmar información ***************************/
  addStep("Se confirma la información");
  POSMOVIL.cancelarPagServ();
  
  /************************ Paso 8 - Confirmar que se cancela el servicio ***************************/
  addStep("Se confirma que se va a cancelar el pago del servicio");
  POSMOVIL.siCancelarServ();
  
  /************************ Paso 9 - Validar que se muestre el cobro de la comisión por consulta ***************************/
  addStep("Se valida que se muestre el cobro por consulta");
  boolean valCobroXConsulta = POSMOVIL.validarCobroComConsServ();
  assertTrue(valCobroXConsulta,"No se encontro el elemento esperado");
  
  /************************ Paso 10 - Totalizar venta ***********************************/
  addStep("Se totaliza la venta en el journal");
  POSMOVIL.totalizarArticulo();
  
  /************************ Paso 11 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 12 - Confirmar pago ***************************/
  addStep("Se confirma el pago");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 13 - Validar que la venta sea exitosa***************************/
  addStep("Se valida la venta exitosa del servicio");
 boolean validarVtaExi =  POSMOVIL.validarVentaExitosa();
 assertTrue(validarVtaExi,"La venta no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================/
  
  }

  @Override
  public String setTestFullName() {
  	return "MTC-FT-007-Validar el pago de cobro por consulta.";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al cancelar el servicio, se deberá de realizar el pago del cobro por consulta del servicio exitosamente";
  }  

  @Override
  public String setTestDesigner() {
  	return "Cesar Guel";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1.- Versión mas actualizada de APK\r\n"
  			+ "2.- Tener conexión a la bd de la nube\r\n"
  			+ "3 - Contar con Impresoras Epson y Zebra\r\n"
  			+ "4-Contar con los folios de los servicios no financieros\r\n"
  			+ "5- Contar con .TRA de comisión por consulta / por servicios.";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}

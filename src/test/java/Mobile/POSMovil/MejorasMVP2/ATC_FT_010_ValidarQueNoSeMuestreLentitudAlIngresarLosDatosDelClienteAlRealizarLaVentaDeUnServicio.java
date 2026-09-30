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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-010-Validar que no se muestre lentitud al ingresar los datos del cliente 
* al realizar la venta de un servicio 
* DESCRIPCIÓN: Al realizar la venta de un servicio no financiero, no se deberá de mostrar lentitud al realizar la 
* consulta con los datos del cliente y al confirmarlos
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
* @date 18/12/2025
*/
//========================================================================//

public class ATC_FT_010_ValidarQueNoSeMuestreLentitudAlIngresarLosDatosDelClienteAlRealizarLaVentaDeUnServicio extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_010_ValidarQueNoSeMuestreLentitudAlIngresarLosDatosDelClienteAlRealizarLaVentaDeUnServicio_test(HashMap<String, String> data) throws Exception {
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
  
  /************************ Paso 6 - Validar leyenda de comisión de servicio**********************************/
  addStep("Se valida la leyenda de la comsión");
  boolean valLeyCom = POSMOVIL.validarLeyComServ();
  assertTrue(valLeyCom,"No se encontro la leyenda");
  
  /************************ Paso 7 - Ingresar la información del cliente ***************************/
  addStep("Se ingresa la información del cliente");
  POSMOVIL.ingresarInfCliente(PasswordUtil.decryptPassword(data.get("infCliente")));
  
  /************************ Paso 8 - Validar que al confirmar no se muestre lentitud********************/
  addStep("Se valida que no se muestre la lentitud");
  boolean valTiempo = POSMOVIL.validarTiemEspServ();
  assertTrue(valTiempo,"No se pudo validar la lentitud del modulo");
  
  /************************ Paso 9 - Validar que se muestren los datos correspodientes a la consulta*******************/
  addStep("Se valida que se muestren los datos correspondiente a la consulta");
  boolean valElemServ = POSMOVIL.validarElementosServ();
  assertTrue(valElemServ,"No se encontraron los elementos");
  
  /************************ Paso 10 - Confirmar información ***************************/
  addStep("Se confirma la información");
  POSMOVIL.confirmarInfCliente();
  POSMOVIL.siContinuarServ();
  
  /************************ Paso 11 - Validar que se muestre el cobro de la comisión ***************************/
  addStep("Se valida que se muestre el cobro de la comisión del servicio");
  boolean valCobroComision = POSMOVIL.validarCobroComServ();
  assertTrue(valCobroComision,"No se encontro el elemento esperado");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "TC-FT-010-Validar que no se muestre lentitud al ingresar los datos del cliente al realizar la venta de un servicio";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la venta de un servicio no financiero, no se deberá de mostrar lentitud al realizar la consulta con los datos del cliente y al confirmarlos";
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

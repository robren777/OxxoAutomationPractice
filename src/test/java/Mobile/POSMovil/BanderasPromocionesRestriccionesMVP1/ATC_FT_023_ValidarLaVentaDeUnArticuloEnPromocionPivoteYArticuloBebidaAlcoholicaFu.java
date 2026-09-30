package Mobile.POSMovil.BanderasPromocionesRestriccionesMVP1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


//========================================================================//
/**
* PROYECTO: POS Móvil MVP1
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-023-Validar la venta de un articulo En promoción Pivote y articulo bebida alcoholica Fuera de horario - Efectivo
* DESCRIPCIÓN: Dado que un usuario desea realizar la compra de un articlo en promoción Pivote y una bebida alcohólica, pero se encuentra fuera del horario permitido, deberá mostrarse un mensaje donde indique que no se puede realizar la venta del articulo correspondiente.
* 
* PRE-REQUISITOS
*1.- Versión mas actualizada de APK
2.- Tener conexión a la bd de la nube
3 - Contar con Impresoras Epson y Zebra
4 - Encender bandera - RESTRICTIONS_FLG
5 - Cargar promoción - Pivote
6 - Realizar la prueba fuera del horario permitido
* 
* INSUMOS:
* SKU de articulos
* 
* @autor Juan Ibarra 
* @date 08/04/2026
*/
//========================================================================//

public class ATC_FT_023_ValidarLaVentaDeUnArticuloEnPromocionPivoteYArticuloBebidaAlcoholicaFu extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;
	private String crTienda;
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_023_ValidarLaVentaDeUnArticuloEnPromocionPivoteYArticuloBebidaAlcoholicaFu_test(HashMap<String, String> data) throws Exception {
	  DesiredCapabilities cap = new DesiredCapabilities();
	  
	  this.userLogin = data.get("userLogin");
	  this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
	  this.appPackage = data.get("appPackage");
	  this.crTienda = data.get("crTienda");	
	  

	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";

  	AppiumUtil appiumUtil = null;

	//=============================================================================================//
  	System.out.println("Cerrando aplicacion si está abierta");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	Thread.sleep(2000);
	//=============================================================================================//


  	try {  		
  		appiumUtil = new AppiumUtil(url, cap);
//  		appiumUtil = PMCapabilities.initializeAppium("login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(500); 
		
	
    //Realiza la valiación de la hora del dispositvo en caso de que se ejecute dentro del horario realiza la actualización del dispotivo
  	//	Llamado al OM de Login de PosMovil.
  	LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
  	LoginPosMovil.validarArchivoVPN();
  	LoginPosMovil.existePsExe();
  	
  	// Llamado al OM de PosMovil.
  	PM POSMOVIL = new PM(testCase, appiumUtil, this);
  	
	//  Realiza la valiación de la hora del dispositvo en caso de que se ejecute dentro del horario de venta de alcohol 
	//  actualiza la hora del dispotivo y del punto de venta
//	  LoginPosMovil.cambiarHoraPV(data.get("crTienda"), "fueraHorario");
//	  LoginPosMovil.actualizarHoraFueraHorario(data.get("crTienda"));
	  LoginPosMovil.cambiarHoraPV(crTienda, "fueraHorario");
	  LoginPosMovil.actualizarHoraFueraHorario(crTienda); 
	  


//================================================================================================================//
    /************************ Paso 0 - Inicio de sesión ***************************/
	  //revisar por que sin este paso no avanza de manera correcta el caso, se tiene que tomar la captura para entre las demas validaciones
	  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
//    LoginPosMovil.verificarLoginTiendaUserCrV2(data.get("crTienda"),data.get("salInic"));
    LoginPosMovil.verificarLoginTiendaUserCrV2(crTienda,data.get("salInic"));
    
    /************************ Paso 1 - Se ingresa al Joural ***************************/
    addStep("Se ingresa al Journal");
    boolean ingresoJournal = POSMOVIL.ingresarJournalV2();
    assertTrue(ingresoJournal,"No se pudo encontrar Journal");
    
    /************************ Paso 2 - Ingresar el SKU o nombre del articulo en promocion a en el buscador ***************************/    
    addStep("Ingresar el SKU o nombre del articulo en promocion a en el buscador");   
//    POSMOVIL.ingresarArticuloPrmPivote(POSMOVIL.obtenerCRTienda(data.get("crTienda")));
    POSMOVIL.ingresarArticuloPrmPivote(POSMOVIL.obtenerCRTienda(crTienda));
//    boolean artPrm = POSMOVIL.ingresarArticuloPrmPivote(POSMOVIL.obtenerCRTienda(data.get("crTienda")));   
//    Thread.sleep(2000);
//    assertTrue(artPrm,"No se pudo agregar el articulo en promocion al journal");
    int journal = POSMOVIL.retornaCantidadProductosJournal();
    
    
    /************************ Paso 3 - Ingresar el SKU o nombre de la bebida alcohólica en el buscador ***************************/    
    addStep("Ingresar el SKU o nombre de la bebida alcohólica en el buscador");
//    POSMOVIL.ingresarSkuArtBAJournalMensaje(LoginPosMovil.obtenerIPTienda(data.get("crTienda")));
    POSMOVIL.ingresarSkuArtBAJournalMensaje(LoginPosMovil.obtenerIPTienda(crTienda));
    
//    /************************ Paso 4 Se muestra resultados de búsqueda.***************************/
//    No aplica el paso en la validación dek caso
//    addStep("Se muestra resultados de búsqueda.");

//    /************************ Paso 5 Seleccionar articulo a comprar.***************************/
//    No aplica el paso en la validación dek caso
//    addStep("Seleccionar articulo a comprar.");

    /************************ Paso 6 Validar se muestra mensaje fuera de horario permitido.***************************/    
    addStep("Validar se muestra mensaje fuera de horario permitido.");
    boolean mensaje = POSMOVIL.validarMensajeToast("Venta fuera de horario.", 30);
	assertTrue(mensaje, "No se pudo encontrar el mensaje");
    
    /************************ Paso 7 Validar que no se agregue el articulo bebida alcoholica al journal.***********************/    
    addStep("Validar que no se agregue el articulo bebida alcoholica al journal.");
    POSMOVIL.clickBotonLimpiarBusqueda(); 
    Thread.sleep(500); 
    int journal2 = POSMOVIL.retornaCantidadProductosJournal();
	Assert.assertEquals(journal, journal2);	
	
  //=======================================Cerrar Aplicaciones========================================================//
//     Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  //==================================================================================================================//

   //Realiza la actualización de la hora del punto de venta y del dispositivo a la hora real 
//     System.out.println("Realiza la actualización de la hora del punto de venta y del dispositivo a la hora real");
//     LoginPosMovil.cambiarHoraPV(data.get("crTienda"), "actualizarHorario");
//     LoginPosMovil.actualizarHoraIgualalPV(data.get("crTienda"));
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-023-Validar la venta de un articulo En promoción Pivote y articulo bebida alcoholica Fuera de horario - Efectivo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que un usuario desea realizar la compra de un articlo en promoción Pivote y una bebida alcohólica, pero se encuentra fuera del horario permitido, deberá mostrarse un mensaje donde indique que no se puede realizar la venta del articulo correspondiente.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Juan Ibarra";
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
  			+ "4 - Encender bandera - RESTRICTIONS_FLG\r\n"
  			+ "5 - Cargar promoción - Pivote\r\n"
  			+ "6 - Realizar la prueba fuera del horario permitido";
  }
  
  @Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	
	@AfterTest
	public void afterTest() throws Exception {
//	System.out.println("Realiza la acción del AfterTest");
//	  AppiumUtil appiumUtil = null;
//	  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);	  
//	  LoginPosMovil.cerrarVPN();
	  }	
	
	@AfterMethod (alwaysRun = true)
	public void afterMethod() {
		System.out.println("Entra a @AfterMethod");
		AppiumUtil appiumUtil = null;	
		LoginPM afterMethodLoginPosMovil = new LoginPM(testCase, appiumUtil, this);		
				
		try {		
			System.out.println("Realiza la actualización de la hora del punto de venta");
			afterMethodLoginPosMovil.cambiarHoraPV(crTienda, "actualizarHorario");			
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		try {		
			System.out.println("Realiza la actualización de la hora del dispositivo a la hora real");
			afterMethodLoginPosMovil.actualizarHoraIgualalPV(crTienda);
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		 
		try {
			System.out.println("Cierra la aplicación");
			Runtime.getRuntime().exec("adb shell am force-stop \"" + appPackage + "\"");			
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		try {
			System.out.println("Cierra la VPN");
			afterMethodLoginPosMovil.cerrarVPN();				
		} catch (Exception e) {
	        e.printStackTrace();
	    }		
	}

}
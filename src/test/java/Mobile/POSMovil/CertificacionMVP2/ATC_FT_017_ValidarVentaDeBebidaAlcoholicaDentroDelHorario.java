package Mobile.POSMovil.CertificacionMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.util.GlobalVariables;
import Mobile.POSMovil.om.LoginPM;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-017-Validar venta de bebida alcohólica - dentro del horario permitido
*  - Efectivo
* DESCRIPCIÓN: Dado que se va a realizar la venta de una bebida alcohólica y se encuentra dentro del horario 
* permitido,se deberá realizar la venta con método de pago en efectivo.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* SKU de articulo bebida alcoholica
* 
* @autor Cesar Guel
* @date 10/09/2024
*/
//========================================================================//

public class ATC_FT_017_ValidarVentaDeBebidaAlcoholicaDentroDelHorario extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_017_ValidarVentaDeBebidaAlcoholicaDentroDelHorario_test(HashMap<String, String> data) throws Exception {
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
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);

	/************************ Ingresar a la App ***************************/
	addStep("Iniciar Aplicación");
  
  	AppiumUtil appiumUtil = null;
  	try {
//  		appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500); //Tiempo de Espera
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
    
	//  Realiza la valiación de la hora del dispositvo en caso de que se ejecute dentro del horario de venta de alcohol 
	//  actualiza la hora del dispotivo y del punto de venta
  	LoginPosMovil.validarArchivoVPN();
  	LoginPosMovil.existePsExe();
  	System.out.println("linea de LoginPosMovil.actualizarHoraFueraHorario(data.get(\"crTienda\"));");
  	LoginPosMovil.actualizarHoraDentroHorarioD();  
  	System.out.println("linea de LoginPosMovil.cambiarHoraPV(data.get(\"crTienda\"), \"dentroHorario\");");
  	LoginPosMovil.cambiarHoraPVD("dentroHorario");
    
  //================================================================================================================//
    
    /************************ Inicio de sesión ***************************/
    addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
    LoginPosMovil.verificarLoginTiendaUserCrV2D(data.get("salInic"));
    
    /************************ Paso 1 - Ingresar al Journal ***************************/
    addStep("Se ingresa al Journal");
    boolean ingresarJournal = POSMOVIL.ingresarJournal();
    assertTrue(ingresarJournal,"Journal no encontrado");
    
    /************************ Paso 2 - Ingresar Sku del articulo ***************************/
    addStep("Se ingresa el articulo bebida alcoholica correspondiente al Journal");
    POSMOVIL.ingresarSkuArticuloBAJournalD();
    
    /************************ Paso 3 - Realizar la busqueda del articulo ***************************/
    addStep("Se realiza la busqueda del articulo");
    POSMOVIL.buscarArticulo();
    
    /************************ Paso 4 - Seleccionar el articulo ***************************/
    addStep("Se selecciona el articulo");
    POSMOVIL.seleccionarArticulo();
    
    /************************ Paso 5 - Seleccionar la cantidad del articulo ***************************/
    addStep("Se selecciona la cantidad articulo");
    POSMOVIL.seleccionarCantidadArticulo();
    
    /************************ Paso 6 - Presionar el boton calcular ***************************/
    addStep("Se presiona el boton calcular");
    POSMOVIL.seleccionarBotonCalcular();
    
    /************************ Paso 7 - Presionar el boton Total a Pagar ***************************/
    addStep("Se presiona el boton total a pagar");
    POSMOVIL.seleccionarBotonTotalPagar();
    
    /************************ Paso 8 - Seleccionar el metodo de pago en efectivo ***************************/
    addStep("Se selecciona el metodo de pago efectivo");
    POSMOVIL.seleccionarMetodoDePagoEfectivo();
    
    /************************ Paso 9 - Terminar la venta con metodo de pago efectivo ***************************/
    addStep("Se termina el pago en efectivo");
    POSMOVIL.confirmarMetodoDePagoEfectivo();
    
    /************************ Paso 10 - Validar que la venta sea exitosa ***************************/
    addStep("Se valida que la venta sea exitosa");
    boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
    assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
    
  //=======================================Cerrar Aplicaciones========================================================//
    Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  //==================================================================================================================//
    
  }

   @Override
   public String setTestFullName() {
     return "ATC-FT-017-Validar venta de bebida alcohólica - dentro del horario permitido - Efectivo";
   }
   
   @Override
   public String setTestDescription() {
     return "Dado que se va a realizar la venta de una bebida alcohólica y se encuentra dentro del horario permitido, se deberá realizar la venta con método de pago en efectivo.";
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
     return "1-Versión mas actualizada de APK"
     		+ "2-Tener conexión a la bd de la nube"
     		+ "3-Contar con Impresoras Epson y Zebra";
   }
   
   @Override
   public void beforeTest() {
     
   }  
   /*
   @AfterTest
 	public void afterTest() throws Exception {
 	System.out.println("Realiza la acción del AfterTest");
 	  AppiumUtil appiumUtil = null;
 	  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);	  
 	  LoginPosMovil.cerrarVPN();
 	  }	 */
 
 }
 
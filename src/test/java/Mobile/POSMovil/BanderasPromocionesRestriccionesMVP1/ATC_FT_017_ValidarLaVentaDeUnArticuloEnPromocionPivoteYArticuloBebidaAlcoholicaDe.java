package Mobile.POSMovil.BanderasPromocionesRestriccionesMVP1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-016-Validar la venta de un articulo En promoción MBG y articulo bebida
*  alcoholica dentro de horario - Efectivo
* DESCRIPCIÓN: Dado que se va a realizar la venta de un articulo en promoción MBG junto con una bebida 
* alcohólica y se encuentra dentro del horario permitido, se deberá realizar la venta con método de pago EFECTIVO
* 
* PRE-REQUISITOS
* 1.- Versión mas actualizada de APK
* 2.- Tener conexión a la bd de la nube
* 3 - Tener artiulos con prmocion pivote
* 
* INSUMOS:
* N/A
* 
* @autor Brandon Sanchez
* @date 08/04/2026
*/
//========================================================================//

public class ATC_FT_017_ValidarLaVentaDeUnArticuloEnPromocionPivoteYArticuloBebidaAlcoholicaDe extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_017_ValidarLaVentaDeUnArticuloEnPromocionPivoteYArticuloBebidaAlcoholicaDe_test(HashMap<String, String> data) throws Exception {
	  DesiredCapabilities cap = new DesiredCapabilities();
	    
	  this.userLogin = data.get("userLogin");
	  this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
	  this.appPackage = data.get("appPackage");
	    
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
//		String url = "http://127.0.0.1:4723";
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		String crTienda = GlobalVariables.crTiendaD;
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		GlobalVariables.nomTiendaD = PM.getNomTiendaD(data.get("nomTienda"));
		System.out.println("Nombre Tienda: " + GlobalVariables.nomTiendaD);
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);
		
		/************************ Ingresar a la App ***************************/

				addStep("Iniciar Aplicación POS Móvil");

	  	AppiumUtil appiumUtil = null;
	  	try {
//	  		appiumUtil = new AppiumUtil(url, cap);
	  		appiumUtil = PMCapabilities.initializeAppium("login");
			} catch (SessionNotCreatedException e) {
				assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
						+ e.getMessage());
				e.printStackTrace();
			}
	  	
	  	Thread.sleep(500);
//	  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
	    /*
	     * Llamado al OM de Login de PosMovil.
	     */
	    LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
	    //Realiza la valiación de la hora del dispositvo en caso de que se ejecute dentro del horario realiza la actualización del dispotivo
	   // LoginPosMovil.actualizarHoraFueraHorario(data.get("crTienda"));
	    
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
	    /************************ Paso 0 - Inicio de sesión ***************************/
	    addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
	    LoginPosMovil.verificarLoginTiendaUserCr(crTienda,PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
	    
	    /************************ Paso 1 - Se ingresa al Joural ***************************/
	    addStep("Se ingresa al Journal");
	    boolean ingresoJournal = POSMOVIL.ingresarJournal();
	    assertTrue(ingresoJournal,"No se pudo encontrar Journal");
	    
	    /************************ Paso 2 - Ingresar el SKU del articulo en el buscador ***************************/
	    addStep("Ingresar el SKU del articulo en el buscador");
	    POSMOVIL.ingresarArticuloPrmPivoteD();
	    /************************ Paso 3 - Ingresar el SKU del articulo en el buscador ***************************/
	    addStep("Se ingresa Sku de articulo bebida alcoholica en el buscador ");
	    POSMOVIL.ingresarSkuArtBAJournalD();
	    
	    /************************ Paso 5 - Se muestra mensaje no se permite venta de alcohol a menores ***************************/
	    addStep("Se valida mensaje bebida alcoholica");
	    boolean validarMensajeBA = POSMOVIL.busquedaConPresenciaBADE();
	    assertTrue(validarMensajeBA,"No se encontro el mensaje esperado");
	    
	    /************************ Paso 6 - Ingresar la cantidad de articulos a comprar ***************************/
	    addStep("Se presiona el boton calcular");
	    POSMOVIL.seleccionarBotonCalcular();
	    
	    /************************ Paso 7 - Presionar el boton Total a Pagar ***************************/
	    addStep("Se presiona el boton total a pagar");
	    POSMOVIL.seleccionarBotonTotalPagar();
	    
	    /************************ Paso 8 - Seleccionar el metodo de pago en efectivo ***************************/
	    addStep("Se selecciona el metodo de pago efectivo");
	    POSMOVIL.seleccionarMetodoDePagoEfectivo();
	    
	    /************************ Paso 9 - Finalizar la venta ***************************/
	    addStep("Se termina el pago en efectivo");
	    POSMOVIL.confirmarMetodoDePagoEfectivo();
	    
	    /************************ Paso 10 - Validar que se realice la venta exitosamente ***************************/
	    addStep("Se valida que la venta sea exitosa");
	    boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
	    assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
	    
	  //=======================================Cerrar Aplicaciones========================================================//
	     Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	  //==================================================================================================================//
	  	
	    }

	    @Override
	    public String setTestFullName() {
	    	return "ATC-FT-017-Validar la venta de un articulo En promoción Pivote y articulo bebida alcoholica dentro de horario - Efectivo";
	    }
	    
	    @Override
	    public String setTestDescription() {
	    	return "Dado que se va a realizar la venta de un articulo en promoción Pivote junto con una bebida alcohólica y se encuentra "
	    			+ "dentro del horario permitido, se deberá realizar la venta con método de pago EFECTIVO";
	    }  

	    @Override
	    public String setTestDesigner() {
	    	return "Brandon Sanchez";
	    }  

	    @Override
	    public String setTestInstanceID() {
	    	return null;  
	    }
	    
	    @Override
	    public String setPrerequisites() {
	    	return "1.- Versión mas actualizada de APK"
	    			+ "2.- Tener conexión a la bd de la nube"
	    			+ "3 - Encender bandera - PROMOTIONS_FLG"
	    			+ "4 - Cargar promoción Pivote"
	    			+ "5 - Realizar la prueba dentro del horario permitido";
	    }
	    
	    @Override
	    public void beforeTest() {
	    	
	    }  
	    
		@AfterTest
		public void afterTest() {
//		  AppiumUtil appiumUtil = null;
//		  LoginPM POSMOVIL = new LoginPM(testCase, appiumUtil, this);
//		  POSMOVIL.cerrarVPN();
		  }
		
		@AfterMethod (alwaysRun = true)
		public void afterMethod() {
			System.out.println("Entra a @AfterMethod");
			AppiumUtil appiumUtil = null;	
			
			try {
				System.out.println("Cierra la aplicación");
				Runtime.getRuntime().exec("adb shell am force-stop \"" + appPackage + "\"");			
			} catch (Exception e) {
		        e.printStackTrace();
		    } 
			
			try {
				LoginPM afterMethodLoginPosMovil = new LoginPM(testCase, appiumUtil, this);
				System.out.println("Cierra la VPN");
				afterMethodLoginPosMovil.cerrarVPN();
					
			} catch (Exception e) {
		        e.printStackTrace();
		    } 	
		}
		

	  }

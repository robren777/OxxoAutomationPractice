package Mobile.POSMovil.ModulosMVP1;

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
import Mobile.POSMovil.om.SQLCMDFormatter;
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
//import io.restassured.response.Response;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP1
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-004-Validar encendido de modulo FastFood
* DESCRIPCIÓN: Dado que al realizar el encendido del módulo de FastFood deberá de mostrarse en el menú del POS Móvil y realizar una venta de producto FastFood exitosa.
* 
* PRE-REQUISITOS
* 1.	Versión más actualizada de APK
* 2.	Contar con la colección completa para apagado/encendido de módulos

* 
* INSUMOS:
* SKU de articulos
* 
* @autor Juan Ibarra 
* @date 21/04/2026
*
* Adecuaión comentarios de equipo BAU
* 1.- El Paso 1 no tiene evidencia alguna accion realizada
* 2.- En el Paso 7 para el agregar algun tipo de Alimento al Journal debe ser dinamico debido a que no en todos los CR existe lo mismo que traen fijo en el script 
*
* @date 11/06/2026
* @autor Juan Ibarra
*
*/
//========================================================================//

public class ATC_FT_004_ValidarEncendidoDeModuloFastFood extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;


@Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_004_ValidarEncendidoDeModuloFastFood_test(HashMap<String, String> data) throws Exception {
	DesiredCapabilities cap = new DesiredCapabilities();
	
	this.userLogin = data.get("userLogin");
    this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
    this.appPackage = data.get("appPackage");

	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";
	GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
	String crTienda = GlobalVariables.crTiendaD;
	System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
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
		
	
  	//	Llamado a los OM's necesarios para la prueba.
  	LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
  	PM POSMOVIL = new PM(testCase, appiumUtil, this);  	
  	LoginPosMovil.validarArchivoVPN();
//  	LoginPosMovil.existePsExe();
  	
  	//================================================================================================================//
    /************************ Paso 1. Ingresar a POSTMAN  ***************************/	  
	  addStep("Ingresar a POSTMAN ");
	  testCase.addCodeEvidenceCurrentStep("Se ingresa a POSTMAN" );
	  
	  /************************ Paso 2. Ingresar los datos de la caja en la colección del Endpoint***************************/
	  addStep("Se realiza el encendido del modulo TAE");
		String consultamodulo = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNubeD());
		System.out.println("Consulta Permisos en la NUBE\n" + consultamodulo);
		testCase.addTextEvidenceCurrentStep("Consulta en la NUBE del modulo:\n"+consultamodulo);		
		POSMOVIL.deshabilitarHabilitarPermisoD(data.get("userLogin"),
		PasswordUtil.decryptPassword(data.get("passwordLogin")), 4, "Venta y Retiros", 10,
		"Fast food", 1);
			

	  /************************ Paso 4. ?Validar mensaje 200 al realizar encendido***************************/
	  addStep("Validar mensaje 200 al realizar encendido");
		boolean valStatus = POSMOVIL.validarRespuesta200();
		assertTrue(valStatus,"Hubo algun problema al validar el status");
	  	
	  /************************
	  * Documenta los servicios para el CR utilizado
	  ***************************/
	  addStep("Se obtienePermisos Habilitados en la NUBE");
	  String permisos = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNubeD());
	  System.out.println("Permisos en la NUBE\n" + permisos);
	  testCase.addTextEvidenceCurrentStep(permisos);


	  /************************ Paso 5. Ingresar al aplicativo POS Móvil***************************/
	  addStep("Ingresar al aplicativo POS Móvil");
	  LoginPosMovil.verificarLoginTiendaUserCrV2(crTienda,data.get("salInic"));	  
	  boolean ingresoJournal = POSMOVIL.ingresarJournalV2();
	  assertTrue(ingresoJournal,"No se pudo encontrar Journal");
	  
	  /************************ Paso 6. ?Validar que al ingresar al POS Móvil se muestre el módulo FastFood***************************/
	  addStep("Validar que al ingresar al POS Móvil se muestre el módulo FastFood");
	  POSMOVIL.seleccionarBotonFastFood();
	  boolean ingresaropcionesFF = POSMOVIL.OpcionFastFood();
	  assertTrue(ingresaropcionesFF,"No se muestra opciones de Fast Food");
	  
	  /************************ Paso 7. Ingresar articulo FastFood al journal***************************/
	  addStep("Ingresar articulo FastFood al journal");
	  POSMOVIL.seleccionarBotonAlimentos();
	  POSMOVIL.seleccionarComidaCaliente();
	  POSMOVIL.agregartopalimento();
	  boolean articulotopBebida = POSMOVIL.articuloJournal();
	  assertTrue(articulotopBebida,"No se encuentra articulo en Journal");
	  
	  /************************ Paso 8. Totalizar venta***************************/
	  addStep("Totalizar venta");
	  POSMOVIL.seleccionarBotonCalcular();
	  POSMOVIL.seleccionarBotonTotalPagar();
	  
	  /************************ Paso 9. Seleccionar método de pago efectivo***************************/
	  addStep("Seleccionar método de pago efectivo");
	  POSMOVIL.seleccionarMetodoDePagoEfectivo();
	  
	  /************************ Paso 10. Confirmar método de pago efectivo***************************/
	  addStep("Confirmar método de pago efectivo");
	  POSMOVIL.confirmarMetodoDePagoEfectivo();
	  
	  /************************ Paso 11. ?Validar que la venta sea exitosa***************************/
	  addStep("Validar que la venta sea exitosa");	  
	  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
	  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
	  

  //=======================================Cerrar Aplicaciones========================================================//
//     Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  //==================================================================================================================//

  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-004-Validar encendido de modulo FastFood";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que al realizar el encendido del módulo de FastFood deberá de mostrarse en el menú del POS Móvil y realizar una venta de producto FastFood exitosa.";
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
  	return "1.	Versión más actualizada de APK\r\n"
  			+ "2.	Contar con la colección completa para apagado/encendido de módulos";
  }
  
  @Override
	public void beforeTest() {
	  
	}
	

	@AfterMethod (alwaysRun = true)
	public void afterMethod() {
		System.out.println("Entra a afterMethod");
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
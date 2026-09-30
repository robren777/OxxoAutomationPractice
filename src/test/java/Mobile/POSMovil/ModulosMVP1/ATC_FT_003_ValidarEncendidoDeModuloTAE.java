package Mobile.POSMovil.ModulosMVP1;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.SQLCMDFormatter;
import Mobile.util.GlobalVariables;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import org.testng.annotations.AfterMethod;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-003-Validar encendido de modulo TAE
* DESCRIPCIÓN: Dado que al realizar el encendido del modulo de TAE debera de mostrarse en el menu del POS
*  Móvil y realizar una venta de recarga tiempo aire exitosa
* 
* PRE-REQUISITOS
* 1.- Versión mas actualizada de APK
* 2.- Contar con la colección completa para apagado/encendido de modulos
* 
* INSUMOS:
* SKU de articulos
* 
* @autor Cesar Guel
* @date 21/04/2026
*
* Adecuaión comentarios de equipo BAU
* 1.- En el Paso dos Falta agregar evidencia de lo realizado para Encender el modulo por el API y la descripcion esta Incorrecta ya que dice de Apagar el modulo de FastFood
* 2.- En el Paso 7 nos Dice que valida que el modulo de Fast Food este Habilitado pero en la evidencia dice lo contrario
* 
* @date 11/06/2026
* @autor Juan Ibarra
*
*/
//========================================================================//

public class ATC_FT_003_ValidarEncendidoDeModuloTAE extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_003_ValidarEncendidoDeModuloTAE_test(
			HashMap<String, String> data) throws Exception {
//		DesiredCapabilities cap = new DesiredCapabilities();
		this.userLogin = data.get("userLogin");
	    this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
	    this.appPackage = data.get("appPackage");
		
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
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true
		// para que no se borre el cache y asi lograr evitar la vista de ingresar la IP
		// y puerto

//		String url = "http://127.0.0.1:4723";
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		String crTienda = GlobalVariables.crTiendaD;
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		/************************ Ingresar a la App ***************************/
		addStep("Iniciar Aplicación POS Móvil");

		/************************
		 * Paso 1 - Ingresar a la App
		 ***************************/

		AppiumUtil appiumUtil = null;
		try {
			//appiumUtil = new AppiumUtil(url, cap);
	  		appiumUtil = PMCapabilities.initializeAppium("Login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
		/*
		 * Llamado al OM de Login de PosMovil.
		 */
		LoginPM loginPM = new LoginPM(testCase, appiumUtil, this);
		loginPM.validarArchivoVPN();
		/*
		 * Llamado al OM de PosMovil.
		 */
		PM posmovil = new PM(testCase, appiumUtil, this);

//================================================================================================================//

		/************************
		 * Paso 1 - Realizar encendido de modulo
		 ***************************/
		addStep("Se realiza el encendido del modulo TAE");
		String consultamodulo = SQLCMDFormatter.formatResultSet(posmovil.obtienePermisosBDNubeD());
		System.out.println("Consulta Permisos en la NUBE\n" + consultamodulo);
		testCase.addTextEvidenceCurrentStep("Consulta en la NUBE del modulo:\n"+consultamodulo);		
		posmovil.deshabilitarHabilitarPermisoD(data.get("userLogin"),
		PasswordUtil.decryptPassword(data.get("passwordLogin")), 4, "Venta y Retiros", 8,
		"TAE", 1);
		
		/************************
		 * Paso 2 - Validar mensaje 200
		 ***************************/
		addStep("Se valida el mensaje 200 del estatus");
		boolean valStatus = posmovil.validarRespuesta200();
		assertTrue(valStatus,"Hubo algun problema al validar el status");

		/************************
		 * Documenta los servicios para el CR utilizado
		 ***************************/
		addStep("Se obtienePermisos Habilitados en la NUBE");
		String permisos = SQLCMDFormatter.formatResultSet(posmovil.obtienePermisosBDNubeD());
		System.out.println("Permisos en la NUBE\n" + permisos);
		testCase.addTextEvidenceCurrentStep(permisos);

		/************************
		 * Inicio de sesion 
		 ***************************/
		 addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
		 loginPM.verificarLoginTiendaUserCr(crTienda,PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
		
		 /************************
			 * Reiniciar APK
			 ***************************/
		System.out.println("Reiniciando aplicacion si está abierta");
		loginPM.reiniciarAPK(data.get("appPackage"));

		/************************
		 * Paso 3 - Ingresar al Journal
		 ***************************/
		addStep("Ingresar al Journal");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 4 - Se valida que se muestre apartado habilitado
		 ***************************/
		addStep("Se valida que se muestre apartado habilitado");
		boolean valModTaeHab = posmovil.validaModTAEDesh();
		assertTrue(!valModTaeHab,"Hubo algun detalle al validar el apartado habilitado");
		
		/************************
		 * Paso 5 - Se Ingresa al apartado de TAE
		 ***************************/
		  addStep("Se Ingresa al apartado de TAE");
		  posmovil.ingresarApTAE();
		  
		  /************************
			 * Paso 6 - Se selecciona el tipo de servicio TAE
			 ***************************/
		  addStep("Se selecciona el tipo de servicio TAE");
		  posmovil.seleccionarComTAE();
		  
		  /************************
			 * Paso 7 - Se totaliza la vanta
			 ***************************/
		  addStep("Se totaliza la vanta");
		  posmovil.totalizarArticulo();
		  
		  /************************
			 * Paso 8 - Se selecciona el metodo de pago efectivo
			 ***************************/
		  addStep("Se selecciona el metodo de pago efectivo");
		  posmovil.seleccionarMetodoDePagoEfectivo();
		  
		  /************************
			 * Paso 9 - Se confirma el pago con efectivo
			 ***************************/
		  addStep("Se confirma el pago con efectivo");
		  posmovil.confirmarMetodoDePagoEfectivo();
		  
		  /************************
			 * Paso 10 - Se ingresa el numero del cliente
			 ***************************/
		  addStep("Se ingresa el numero del cliente");
		  posmovil.ingresarNumCliente(data.get("numCliente"));
		  
		  /************************
			 * Paso 11 - Se confirma el numero del cliente
			 ***************************/
		  addStep("Se confirma el numero del cliente");
		  posmovil.confirmarNumCliente();
		  
		  /************************
			 * Paso 12 - Se valida que la recarga sea exitosa
			 ***************************/
		  addStep("Se valida que la recarga sea exitosa");
		  boolean validarTaeExi = posmovil.validarServRecExitosa();
		  assertTrue(validarTaeExi,"La recarga no fue exitosa");
		
		/************************
		 *Se habilita nuevamente el Permiso
		 ***************************/
//		posmovil.deshabilitarHabilitarPermisoD(data.get("userLogin"),
//				PasswordUtil.decryptPassword(data.get("passwordLogin")), 4, "Venta y Retiros", 8,
//				"TAE", 1);
//=======================================Cerrar Aplicaciones========================================================//
//		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-003-Validar encendido de modulo TAE";
	}

	@Override
	public String setTestDescription() {
		return "Dado que al realizar el encendido del modulo de TAE debera de mostrarse en el menu del POS Móvil y"
				+ " realizar una venta de recarga tiempo aire exitosa";
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
		return "1.- Versión mas actualizada de APK"
				+ "2.- Contar con la colección completa para apagado/encendido de modulos";
	}

	@Override
	public void beforeTest() {

	}

	@AfterMethod (alwaysRun = true)
	public void afterMethod() {
		System.out.println("Entra a afterMethod");
		AppiumUtil appiumUtil = null;		
		try {
			System.out.println("Restaurando permiso TAE de afterMethod");
			PM posmovilAfterMethod = new PM(testCase, appiumUtil, this);
			if(userLogin != null && passwordLogin != null) {			
			posmovilAfterMethod.deshabilitarHabilitarPermisoD(userLogin, passwordLogin, 4, "Venta y Retiros", 8,"TAE", 1);
			}
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
			LoginPM afterMethodLoginPosMovil = new LoginPM(testCase, appiumUtil, this);
			System.out.println("Cierra la VPN");
			afterMethodLoginPosMovil.cerrarVPN();
				
		} catch (Exception e) {
	        e.printStackTrace();
	    } 	
	}

}
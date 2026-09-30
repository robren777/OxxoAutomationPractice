package Mobile.POSMovil.ModulosMVP1;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

//import org.junit.After;
import org.openqa.selenium.SessionNotCreatedException;
//import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.SQLCMDFormatter;
import Mobile.util.GlobalVariables;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
//import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.AfterClass;



//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-001-Validar apagado de modulo TAE 
* DESCRIPCIÓN: Dado que al realizar el apagado del modulo de TAE no debera de mostrarse en el menu del POS Móvil
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
* Adecuación comentarios de equipo BAU
* 1.- En el Paso dos Falta agregar evidencia de lo realizado para apagar el modulo por el API.
* 2.- EL metodo para volver a Habilitar el modulo de TAE debe de realizarse en un aftermethod para que en caso de fallar en algun paso lo aplique y no dejar Deshabilitado el modulo
* 
* @date 11/06/2026
* @autor Juan Ibarra
*/
//========================================================================//

public class ATC_FT_001_ValidarApagadoDeModuloTAE extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_ValidarApagadoDeModuloTAE_test(HashMap<String, String> data) throws Exception {
		this.userLogin = data.get("userLogin");
	    this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
	    this.appPackage = data.get("appPackage");
	    
//		DesiredCapabilities cap = new DesiredCapabilities();
		
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

		/************************Paso 1 - Realizar el apagado del modulo ***************************/
		addStep("Se realiza el apagado del modulo TAE");
//		posmovil.deshabilitarHabilitarPermisoD(data.get("userLogin"),
//				PasswordUtil.decryptPassword(data.get("passwordLogin")), 4, "Venta y Retiros", 8,
//				"TAE", 0);
		String consultamodulo = SQLCMDFormatter.formatResultSet(posmovil.obtienePermisosBDNubeD());
		System.out.println("Consulta Permisos en la NUBE\n" + consultamodulo);
		testCase.addTextEvidenceCurrentStep("Consulta antes del apagado del modulo:\n"+consultamodulo);		
		boolean valDeshabilitar = posmovil.actualizarPermisoD(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")), 4, "Venta y Retiros", 8,"TAE", 0);
		assertTrue(valDeshabilitar,"Hubo algun problema al deshabilitar y validar el status del módulo TAE");
				
		
		/************************Paso 2 - Validar mensaje 200 ***************************/
		addStep("Se valida el mensaje 200 del estatus");
		boolean valStatus = posmovil.validarRespuesta200();
		assertTrue(valStatus,"Hubo algun problema al validar el status");

		/************************
		 * Documenta los servicios para el CR utilizado
		 ***************************/
		addStep("Se obtiene Permisos Habilitados en la NUBE");
		String permisos = SQLCMDFormatter.formatResultSet(posmovil.obtienePermisosBDNubeD());
		System.out.println("Permisos en la NUBE\n" + permisos);
		testCase.addTextEvidenceCurrentStep("Consulta despues del apagado del modulo:\n"+permisos);

		/************************ Inicio de sesión ***************************/
		 addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
		 loginPM.verificarLoginTiendaUserCr(crTienda,PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
		
		/****************************Reiniciar APK**************************/
		System.out.println("Reiniciando aplicacion si está abierta");
		loginPM.reiniciarAPK(data.get("appPackage"));

		/************************
		 * Paso 3 - Ingresar al Journal
		 ***************************/
		addStep("Ingresar al Journal");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 4 - Validar modulo apagado
		 ***************************/
		addStep("Se valida que se muestre apartado deshabilitado");
		boolean valModTaeDes = posmovil.validaModTAEDesh();
		assertTrue(valModTaeDes,"Hubo algun detalle al validar el apartado deshabilitado");
		
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
		return "MTC-FT-001-Validar apagado de modulo TAE";
	}

	@Override
	public String setTestDescription() {
		return "Dado que al realizar el apagado del modulo de TAE no debera de mostrarse en el menu del POS Móvil";
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
//		 System.out.println("Realiza la acción del beforeTest");
//		  AppiumUtil appiumUtil = null;
//		  LoginPM beforePosMovil = new LoginPM(testCase, appiumUtil, this);
//		  beforePosMovil.validarArchivoVPN();
//		  System.out.println("termina beforePosMovil.validarArchivoVPN()");
//		  appiumUtil.wait(12);
//		  System.out.println("tiempo appiumUtil.wait(12);");
	}
	
//	@BeforeMethod(alwaysRun = true)
//	public void beforeMethod() {
//		System.out.println("Realiza la acción del @BeforeMethod");
//		  AppiumUtil appiumUtil = null;
//		  LoginPM beforePosMovil = new LoginPM(testCase, appiumUtil, this);
//		  beforePosMovil.validarArchivoVPN();
//		  System.out.println("termina beforePosMovil.validarArchivoVPN()");
//		
//	}

	@AfterMethod (alwaysRun = true)
	public void afterMethod() {//throws IOException, InterruptedException {
		
//		AppiumUtil appiumUtil = null;
//		PM posmovilAfterMethod = new PM(testCase, appiumUtil, this);		
//		posmovilAfterMethod.deshabilitarHabilitarPermisoD(userLogin, passwordLogin, 4, "Venta y Retiros", 8, "TAE", 1);		
//		System.out.println("Cierra la aplicación");
//		Runtime.getRuntime().exec("adb shell am force-stop \"" + appPackage + "\"");
//		System.out.println("Cierra la VPN");
//		LoginPM afterMethodLoginPosMovil = new LoginPM(testCase, appiumUtil, this);	  
//		afterMethodLoginPosMovil.cerrarVPN();
//		Thread.sleep(2000);
		
		System.out.println("Entra a afterMethod");
		AppiumUtil appiumUtil = null;	
		
		try {
			System.out.println("Restaurando permiso TAE de afterMethod");
			PM posmovilAfterMethod = new PM(testCase, appiumUtil, this);
			if(userLogin != null && passwordLogin != null) {
			posmovilAfterMethod.deshabilitarHabilitarPermisoD(userLogin, passwordLogin, 4, "Venta y Retiros", 8, "TAE", 1);
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
package Mobile.POSMovil.BanderasPromocionesRestriccionesMVP1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-026-Validar PROMOTIONS_FLG=FALSE y RESTRICTIONS_FLG=FALSE con instalacion de parches FeatureFlag
* DESCRIPCIÓN: Se valida el apagado de las banderas PROMOTIONS_FLG=FALSE  y RESTRICTIONS_FLG=FALSE eb BD posterior a ejecutar los parches FeatureFlag
* 
* PRE-REQUISITOS
* Tener conexión a la bd de la nube
* Tener ejecutados los parches feature Flag con parametro False
* 
* INSUMOS:
* parche FeatureFlagPatch2.0.14.1
* parche FeatureFlagPatch2.0.13.2
* 
* @autor Brandon Sanchez
* @date 08/04/2026
*/
//========================================================================//

public class ATC_FT_026_ValidarBanderaPROMOTIONSFLGFALSEYRESTRICTIONSFLG extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_026_ValidarBanderaPROMOTIONSFLGFALSEYRESTRICTIONSFLG_test(HashMap<String, String> data) throws Exception {
	//	DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		//System.out.println("Cerrando aplicacion si está abierta");
		//Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		//Thread.sleep(2000);
		//=============================================================================================//
		
//		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
//		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
//		cap.setCapability("platformName", data.get("platformName"));
//		cap.setCapability("appPackage", data.get("appPackage"));
//		cap.setCapability("appActivity", data.get("appActivity"));
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
//		String url = "http://127.0.0.1:4723";
		
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		String ipPdv =GlobalVariables.ipPvD;
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);
		

	  //=================================================================================================================//		

	  /*
	   * Llamado al OM de Login de PosMovil.
	   */
	  LoginPM LoginPosMovil = new LoginPM(testCase, null, this);
	  
	  /*
	   * Llamado al OM de PosMovil.
	   */
	  PM POSMOVIL = new PM(testCase, null, this);
	  
		/*****PRERREQUISITO******/
		LoginPosMovil.ejecucionParches(ipPdv,"FeatureFlagPatch2.0.14.1","C:\\InstallFiles\\patch","FALSE"); //Ejecucion parche FeatureFlagPatch2.0.14.1
		LoginPosMovil.ejecucionParches(ipPdv,"FeatureFlagPatch2.0.13.2","C:\\InstallFiles\\patch","FALSE"); //Ejecucion parche FeatureFlagPatch2.0.13.2
		
	  
	//=================================================================================================================//		



		/************************ Paso 1 - "Ejecutar en SSMS
		
USE XposStore;

BEGIN TRY
    SELECT 
        SCAT.CODE, 
        SCAT.NAME, 
        SVAL.VALUE 
    FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK)
    INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK)
        ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID
    WHERE SCAT.CODE IN ('PROMOTIONS_FLG', 'RESTRICTIONS_FLG');
END TRY
BEGIN CATCH
    SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_MESSAGE() AS ErrorMessage;
END CATCH;"
***************************/
		addStep("\"Ejecutar en SSMS:\r\n"
				+ "USE XposStore;\r\n"
				+ "\r\n"
				+ "BEGIN TRY\r\n"
				+ "    SELECT \r\n"
				+ "        SCAT.CODE, \r\n"
				+ "        SCAT.NAME, \r\n"
				+ "        SVAL.VALUE \r\n"
				+ "    FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK)\r\n"
				+ "    INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK)\r\n"
				+ "        ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID\r\n"
				+ "    WHERE SCAT.CODE IN ('PROMOTIONS_FLG', 'RESTRICTIONS_FLG');\r\n"
				+ "END TRY\r\n"
				+ "BEGIN CATCH\r\n"
				+ "    SELECT \r\n"
				+ "        ERROR_NUMBER() AS ErrorNumber,\r\n"
				+ "        ERROR_MESSAGE() AS ErrorMessage;\r\n"
				+ "END CATCH;\"\r\n"
				+ "");
//		 List <String> Valores = LoginPosMovil.consultaPromotionAndRestrictionsFlg("50DHA");
		 
			/************************ Paso 2 - "Validar Bandera PROMOTIONS_FLG=FALSE, RESTRICTIONS_FLG = FALSE" ***************************/
		addStep("Validar Bandera PROMOTIONS_FLG=FALSE, RESTRICTIONS_FLG = FALSE\r\n");
//		assertTrue(LoginPosMovil.validaPromotionAndRestrictionsFlg(Valores),"No se encontro el mensaje esperado");
		boolean banderasApagadas = POSMOVIL.validarBanderasApagadasD();
		assertTrue(banderasApagadas,"El resultado de la validación de las banderas no es el esperado");
		 
//=======================================Cerrar Aplicaciones========================================================//
  //Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-026Validar bandera PROMOTIONS_FLG=FALSE  y RESTRICTIONS_FLG=FALSE posterior a instalacion de parches FeatureFlag";
  }
  
  @Override
  public String setTestDescription() {
  	return "Se valida el apagado de las banderas PROMOTIONS_FLG=FALSE  y RESTRICTIONS_FLG=FALSE eb BD posterior a ejecutar los parches FeatureFlag";
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
  	return   
  			  "1.- Tener conexión a la bd de la nube\r\n"
  			+ "2 - Tener ejecutados los parches feature Flag con parametro False";
  }
  
  @Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	
	/*@AfterTest
	public void afterTest() {
	  AppiumUtil appiumUtil = null;
	  LoginPM POSMOVIL = new LoginPM(testCase, appiumUtil, this);
	  POSMOVIL.cerrarVPN();
	  }*/

}

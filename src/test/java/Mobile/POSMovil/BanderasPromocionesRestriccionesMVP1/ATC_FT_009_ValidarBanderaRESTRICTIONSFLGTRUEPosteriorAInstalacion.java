package Mobile.POSMovil.BanderasPromocionesRestriccionesMVP1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-009-Validar bandera RESTRICTIONS_FLG = TRUE posterior a instalacion 
* de parche FeatureFlagPatch(RESTRICCIONS)
* DESCRIPCIÓN: Validar que posterior a aplica el parche FeatureFlagPatch(RESTRICCIONES) la Bandera de
*  Restricciones se encuentren en TRUE
* 
* PRE-REQUISITOS
* Tener conexión a la BD del PV
* Tener instalada la herramienta SQL Server Management Studio (SSMS)
* Haber ejecutado el parche FeatureFlagPatch correspondiente a Restricciones
* 
* INSUMOS:
* N/A
* 
* @autor Cesar Guel
* @date 03/04/2026
*/
//========================================================================//

public class ATC_FT_009_ValidarBanderaRESTRICTIONSFLGTRUEPosteriorAInstalacion extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_009_ValidarBanderaRESTRICTIONSFLGTRUEPosteriorAInstalacion_test(HashMap<String, String> data) throws Exception {

		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);
//================================================================================================================//
  /*
   * Llamado al OM de PosMovil.
   */
  PM POSMOVIL = new PM(testCase, null, this);
  
//================================================================================================================//

  /************************ Paso 1 - Se valida bandera de restricciones encendidas ***************************/
  addStep("Se valida bandera de restricciones encendida");
  boolean valBanderaRest = POSMOVIL.validarBanderaRestriccionesEncendidaD();
  assertTrue(valBanderaRest,"No se pudo encontrar bandera encendida");
  
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-009-Validar bandera RESTRICTIONS_FLG = TRUE posterior a instalacion de parche FeatureFlagPatch(RESTRICCIONS)";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que posterior a aplica el parche FeatureFlagPatch(RESTRICCIONES) la Bandera de Restricciones se encuentren en TRUE";
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
  	return "1.- Tener conexión a la BD del PV"
  			+ "2.- Tener instalada la herramienta SQL Server Management Studio (SSMS)"
  			+ "3.- Haber ejecutado el parche FeatureFlagPatch correspondiente a Restricciones";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}

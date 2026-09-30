package Mobile.POSMovil.BanderasPromocionesRestriccionesMVP1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import Mobile.POSMovil.om.PM;
import Mobile.util.GlobalVariables;
import modelo.BaseExecution;


//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-005-Validar bandera PROMOTIONS_FLG = TRUE posterior a instalacion
*  de parche FeatureFlagPatch(PROMOCIONES)
* DESCRIPCIÓN: Validar que posterior a aplica el parcheFeatureFlagPatch(PROMOCIONES) la Bandera de 
* Promociones se encuentren en TRUE
* 
* PRE-REQUISITOS
* Tener conexión a la BD del PV
* Tener instalada la herramienta SQL Server Management Studio (SSMS)
* Haber ejecutado el parche FeatureFlagPatch correspondiente a Promociones
* 
* INSUMOS:
* N/A
* 
* @autor Cesar Guel
* @date 03/04/2026
*/
//========================================================================//

public class ATC_FT_005_ValidarBanderaPROMOTIONSFLGTRUEPosteriorAInstalacion extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_005_ValidarBanderaPROMOTIONSFLGTRUEPosteriorAInstalacion_test(HashMap<String, String> data) throws Exception {
		
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);
//================================================================================================================//
/*
   * Llamado al OM de PosMovil.
   */
  PM POSMOVIL = new PM(testCase, null, this);
  
//================================================================================================================//
  
  /************************ Paso 1 - Se valida bandera de promociones encendidas ***************************/
  addStep("Se valida bandera de promociones encendida");
  boolean valBanderaProm = POSMOVIL.validarBanderaPromocionesEncendidaD();
  assertTrue(valBanderaProm,"No se pudo encontrar bandera encendida");
  
//================================================================================================================//
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-005-Validar bandera PROMOTIONS_FLG = TRUE posterior a instalacion de parche FeatureFlagPatch(PROMOCIONES)";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que posterior a aplica el parcheFeatureFlagPatch(PROMOCIONES) la Bandera de Promociones se encuentren en TRUE";
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
  			+ "3.- Haber ejecutado el parche FeatureFlagPatch correspondiente a Promociones";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}

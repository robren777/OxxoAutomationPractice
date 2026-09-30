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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-002-Validar actualización de Stored Procedures posterior a instalacion 
* de parche CloudMiddPatch2.0.9.4
* DESCRIPCIÓN: Validar que posterior a aplica el parche CloudMiddPatch2.0.9.4 los SPs {Nombres de SPs} se actualicen
* 
* PRE-REQUISITOS
* 1.- Tener conexión a la BD del PV
* 2.- Tener instalada la herramienta SQL Server Management Studio (SSMS)
* 3.- Haber ejecutado el parche CloudMiddPatch2.0.9.4
* 
* INSUMOS:
* N/A
* 
* @autor Cesar Guel
* @date 08/04/2026
*/
//========================================================================//

public class ATC_FT_002_ValidarActualizacionDeStoredProceduresPosteriorAInstalacion extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_002_ValidarActualizacionDeStoredProceduresPosteriorAInstalacion_test(HashMap<String, String> data) throws Exception {

		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);
	  
//=================================================================================================================//		
  /*
   * Llamado al OM de PosMovil.
   */
  PM POSMOVIL = new PM(testCase, null, this);
  
//================================================================================================================//
  /************************ Paso 1 - Se valida bandera de promociones encendidas ***************************/
  addStep("Se valida la fecha de modificacion del SP");
  boolean valFecha = POSMOVIL.validarFechaModSPD();
  assertTrue(valFecha,"No se pudo validar fecha de modificacion");
  
//=======================================Cerrar Aplicaciones========================================================//

  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-002-Validar actualización de Stored Procedures posterior a instalacion de parche CloudMiddPatch2.0.9.4";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que posterior a aplica el parche CloudMiddPatch2.0.9.4 los SPs {Nombres de SPs} se actualicen";
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
  			+ "3.- Haber ejecutado el parche CloudMiddPatch2.0.9.4";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}

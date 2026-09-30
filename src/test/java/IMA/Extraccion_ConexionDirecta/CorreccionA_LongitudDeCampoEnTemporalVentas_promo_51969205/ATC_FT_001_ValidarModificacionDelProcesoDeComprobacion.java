package IMA.Extraccion_ConexionDirecta.CorreccionA_LongitudDeCampoEnTemporalVentas_promo_51969205;

import modelo.BaseExecution;
import IMA.util.GlobalVariables;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

import java.util.HashMap;

public class ATC_FT_001_ValidarModificacionDelProcesoDeComprobacion extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarModificacionDelProcesoDeComprobacion_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIQN = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "select object_name, object_id, object_type, status from user_objects where object_type='PROCEDURE' and object_name = 'QA_TEMP_VENTA_PROMO'";
		
		String Query2= "select text from all_source where name = 'QA_TEMP_VENTA_PROMO'";
		
		String Query3 = "SELECT * FROM (select item, store, tran_date, units, fechacarga from BIODSMKT.TEMP_FEM_FIF_STG_VENTAS_PROMO) WHERE ROWNUM <=25";
		
		/*************************************** PASO 1 ***************************************/	
		
		  addStep("Seleccionar el procedimiento BIETL.QA_TEMP_VENTA_PROMO");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN); System.out.println(Query);
		  
		  SQLResult ExecuteQuery = executeQuery(dbBIQN, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se encuentra el procedimiento BIETL.QA_TEMP_VENTA_PROMO."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  }
		  assertFalse(StatusQuery, "No se encuentra el procedimiento BIETL.QA_TEMP_VENTA_PROMO.");
		 
		/*************************************** PASO 2 ***************************************/	
		
		addStep("Verificar el procedimiento BIETL.QA_TEMP_VENTA_PROMO");

		System.out.println(GlobalVariables.DB_HOST_BIQN);
		System.out.println("\n" + Query2);
		
		SQLResult ExecuteQuery2 = executeQuery(dbBIQN, Query2);
		
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		
		if (!StatusQuery2) {
				System.out.println("Monitoreando Procedimiento");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				}else {
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}			
		    assertFalse(StatusQuery2,"No se muestra el codigo del procedimiento BIETL.QA_TEMP_VENTA_PROMO.");		
		    
		/*************************************** PASO 3 ***************************************/		
		    
		    addStep("Dirigirse a la consulta de la tabla BIODSMKT.TEMP_FEM_FIF_STG_VENTAS_PROMO.");
			  
			  System.out.println(GlobalVariables.DB_HOST_BIQN); System.out.println(Query3);
			  
			  SQLResult ExecuteQuery3 = executeQuery(dbBIQN, Query3);
			  
			  boolean StatusQuery3 = ExecuteQuery3.isEmpty();
			  System.out.println(StatusQuery3); 
			  if (!StatusQuery3) {
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
			  } else { 
				  testCase.addTextEvidenceCurrentStep("No se encuentran rergistros en la tabla BIODSMKT.TEMP_FEM_FIF_STG_VENTAS_PROMO."); 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
			  }
			  assertFalse(StatusQuery3, "No se encuentran rergistros en la tabla BIODSMKT.TEMP_FEM_FIF_STG_VENTAS_PROMO.");
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

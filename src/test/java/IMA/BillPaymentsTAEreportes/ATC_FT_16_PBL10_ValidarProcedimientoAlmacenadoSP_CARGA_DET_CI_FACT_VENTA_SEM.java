package IMA.BillPaymentsTAEreportes;

import static org.testng.Assert.assertFalse;

import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_16_PBL10_ValidarProcedimientoAlmacenadoSP_CARGA_DET_CI_FACT_VENTA_SEM extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);  
	@Test(dataProvider = "data-provider")
	public void ATC_FT_16_PBL10_ValidarProcedimientoAlmacenadoSP_CARGA_DET_CI_FACT_VENTA_SEM_test(HashMap<String, String> data) throws Exception {
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);
		 u.close();		
		/*************************************** VARIABLES ***************************************/	
		String Query1 = "{call SP_CARGA_DET_CI_FACT_VENTA_SEM}";
		String Query2= "select tienda_key,plaza_id,producto_key,unidadespedidas_num, unidadesvendidas_num from BIDMMKT.TEMP_DET_CI_FACT_VENTA_SEMANA";
		/*************************************** PASO 1 ***************************************/	
		
		 addStep("Ejecutar el procedimiento BIETL.SP_CARGA_DET_CI_FACT_VENTA_SEM almacenado en la BD FCDW4QA");
          
          System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query1);
          
          Statement stmt = db.getConn().createStatement();
          boolean execute= stmt.execute(Query1);
          System.out.println(execute);
          
         if(execute == false) {
                 testCase.addTextEvidenceCurrentStep("PL/SQL Procedimiento completado exitosamente.");
         }else {
             testCase.addTextEvidenceCurrentStep("PL/SQL Procedimiento no completado.");
         }
         assertFalse(execute,"No se ejecuto correctamente el procedimiento BIETL.SP_CARGA_DET_CI_FACT_VENTA_SEM");
		  
		/*************************************** PASO 2 ***************************************/	
			
		  addStep("Verificar que se cargue la tabla BIDMMKT.TEMP_DET_CI_FACT_VENTA_SEMANA");
		  
		  System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query2);
		  
		  SQLResult ExecuteQuery2 = executeQuery(db, Query2);
		  
		  boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		  System.out.println(StatusQuery2); 
		  if (!StatusQuery2) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.TEMP_DET_CI_FACT_VENTA_SEMANA."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
			}
		  assertFalse(StatusQuery2,"No se muestran registros en la tabla BIDMMKT.TEMP_DET_CI_FACT_VENTA_SEMANA.");
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
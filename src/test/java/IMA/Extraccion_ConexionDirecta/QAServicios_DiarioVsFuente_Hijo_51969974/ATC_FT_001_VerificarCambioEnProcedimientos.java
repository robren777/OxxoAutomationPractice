package IMA.Extraccion_ConexionDirecta.QAServicios_DiarioVsFuente_Hijo_51969974;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_VerificarCambioEnProcedimientos extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_VerificarCambioEnProcedimientos_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIQN = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "select object_name, object_id, object_type, status from user_objects where object_type='PROCEDURE' and object_name = 'SP_012_QA_SERVICIOS_SEM'";
		
		String Query2= "select text from all_source where name = 'SP_012_QA_SERVICIOS_SEM'";
		
		/*************************************** PASO 1 ***************************************/	
		
		  addStep("Seleccionar el procedimiento BIETL.SP_012_QA_SERVICIOS_SEM");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIQN); System.out.println(Query);
		  
		  SQLResult ExecuteQuery = executeQuery(dbBIQN, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se encuentra el procedimiento BIETL.SP_012_QA_SERVICIOS_SEM."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  }
		  assertFalse(StatusQuery, "No se encuentra el procedimiento BIETL.SP_012_QA_SERVICIOS_SEM.");
		 
		/*************************************** PASO 2 ***************************************/	
		
		addStep("Verificar que en el procedimiento exista la condicion: \n"
				+ " \"AND PRODUCTO_KEY NOT IN (SELECT ID_PRODUCTO FROM BIODSMKT.CNV_010_SERV_OTROS_INGRESOS)");

		System.out.println(GlobalVariables.DB_HOST_BIQN);
		System.out.println("\n" + Query2);
		
		SQLResult ExecuteQuery2 = executeQuery(dbBIQN, Query2);
		
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		
		if (!StatusQuery2) {
			String registro= "AND PRODUCTO_KEY NOT IN (SELECT ID_PRODUCTO FROM BIODSMKT.CNV_010_SERV_OTROS_INGRESOS)";
			
			String reg1= ExecuteQuery2.getData(76, "Text");
			System.out.println(registro);
			System.out.println(reg1);
			
			if(reg1.contains(registro)) {
				System.out.println("La condicion existe en el codigo del procedimiento");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				testCase.addTextEvidenceCurrentStep("Registro esperado en el procedimiento: "+registro);
				testCase.addTextEvidenceCurrentStep("Registro encontrado en el procedimiento: "+reg1);
			}else {
				System.out.println("La condici�n no existe en el c�digo del procedimiento");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				testCase.addTextEvidenceCurrentStep("Registro esperado en el procedimiento: "+registro);
				testCase.addTextEvidenceCurrentStep("Registro encontrado en el procedimiento: "+reg1);
				assertTrue(StatusQuery2,"La condici�n no existe en el c�digo del procedimiento");
				}
		}			
		    assertFalse(StatusQuery2,"No se muestra el codigo del procedimiento BIETL.SP_012_QA_SERVICIOS_SEM.");
			
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_001_VerificarCambioEnProcedimientos_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "CP001 - Verificar cambio en Procedimientos";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Team IMA";
	}

	@Override
	public String setTestInstanceID() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
// TODO Auto-generated method stub
	}

	@Override
	public void baseAfterTest() {
	}
}
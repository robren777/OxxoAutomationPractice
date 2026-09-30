package WM.interfaces.tpe.tsf;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_011_TPE_TSF_REMESAS_RevisionBDTotalRetiroDinero extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_TPE_TSF_REMESAS_RevisionBDTotalRetiroDinero_test(HashMap<String, String> data) throws Exception {
         
		/**
		 * Proyecto: Limite Dispersion Remesas
		 * CP: MTC-FT-019-Revision Base de Datos Total  Retiro de Dinero SQL Developer  
		 * 
		 */
		
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCTPEQA2 = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		/*
		 * Variables
		 *********************************************************************/
		
	/*	String transaccionRemesas = "select Application, entity, operation,  creation_date  \r\n"
				+ "FROM TPEUSER.TPE_FR_TRANSACTION\r\n"
				+ "WHERE ENTITY = 'wu' \r\n"
				+ "AND CREATION_DATE > =TO_DATE('"+ data.get("date")+"' ,'dd/mm/yyyy ')\r\n"
				+ "AND OPERATION = 'QRY02'\r\n"
				+ "AND ROWNUM <=100";*/
		
		String transaccionRemesas = "select Application, entity, operation,  creation_date\r\n"
				+ "FROM TPEUSER.TPE_FR_TRANSACTION\r\n"
				+ "WHERE ENTITY = '"+ data.get("entity")+"'\r\n" //WU
				+ "AND OPERATION = '"+ data.get("operation")+"'\r\n" //TRN03
				+ "AND CREATION_DATE > =TO_DATE('"+ data.get("date")+"' ,'dd/mm/yyyy')\r\n" //17/04/2023
				+ "AND rownum = 1\r\n"
				+ "order by CREATION_DATE desc";
		

		testCase.setProject_Name("Limite Dispersion Remesas");
		
		testCase.setTest_Description(data.get("id")+ data.get("name"));
		
		testCase.setPrerequisites(data.get("prerequicitos"));

		/**************************************************************************************************
		 * 
		 * 
		 *************************************************************************************************/
	
//Paso 1*******************************************************************************************************************
		
       addStep("Ingresar a SQL Developer y realizar conexion a FCTPEQA2");
		
		boolean conexionFCTPEQA = true;
		
		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA2 ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE_QRO);
		
		assertTrue(conexionFCTPEQA, "La conexion no fue exitosa");
		
//Paso 2********************************************************************************************************************	
		
		 addStep("Realizar  select: ");
			
	      
			 System.out.println(transaccionRemesas);
		        
		       SQLResult transaccionRemesas_Result = dbFCTPEQA2.executeQuery(transaccionRemesas);
		       
		      
		       boolean validaTransaccionRemesas = transaccionRemesas_Result.isEmpty(); //checa que el string contenga datos
		    		  
		       
		       if(!validaTransaccionRemesas) {
		    	   
		    	   testCase.addQueryEvidenceCurrentStep(transaccionRemesas_Result);  //Si no esta vacio, lo agrega a la evidencia
		       }
		      
		       System.out.println(validaTransaccionRemesas); 

	       
	   assertFalse("No existen registros de  transacciones ",validaTransaccionRemesas ); //Si esta vacio, imprime mensaje
	     
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo automatizacion";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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

}


package WM.interfaces.tpe.tsf;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_014_TPE_TSF_REMESAS_FormasPagoHabilitadosRemesador extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_TPE_TSF_REMESAS_FormasPagoHabilitadosRemesador_test(HashMap<String, String> data) throws Exception {
		
		/**   Proyecto Informacion de Remesas en IMA
		 * 
		 *     MTC-FT-087-Revision de configuracion de formas de pago habilitados para remesador y mensaje de respuesta WesternUnion
		 *     MTC-FT-088-Revision de configuracion de formas de pago habilitados para remesador y mensaje de respuesta TransNetwork
		 *     MTC-FT-089-Revision de configuracion de formas de pago habilitados para remesador y mensaje de respuesta MoneyGram
		 */

		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCTPEQA2 = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		/*
		 * Variables
		 *********************************************************************/
		
		String config = "Select Application, entity, operation, code, creation_date \r\n"
				+ "from TPEUSER.TPE_FR_CONFIG \r\n"
				+ "where Application='TSF' \r\n"
				+ "and Entity='"+ data.get("entity")+"' \r\n"
				+ "and Code IN('forma-pago', 'mensajes-mc')"
				+ "and rownum = 1";
		

		testCase.setProject_Name("Informacion de Remesas en IMA");
		
		testCase.setTest_Description(data.get("id")+ data.get("name"));
		
		testCase.setPrerequisites(data.get("prerequicitos"));

		/**************************************************************************************************
		 * 
		 * 
		 *************************************************************************************************/
	
//Paso 1*******************************************************************************************************************
		
       addStep("Ingresar a SQL Developer");
		
		boolean conexionFCTPEQA = true;
		
		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA2 ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE_QRO);
		
		assertTrue(conexionFCTPEQA, "La conexion no fue exitosa");
		
//Paso 2********************************************************************************************************************	
		
		 addStep("Realizar consulta de transacciones ");
			
	      
			 System.out.println(config);
		        
		       SQLResult config_Result = dbFCTPEQA2.executeQuery(config);
		       
		      
		       boolean validaConfig = config_Result.isEmpty(); //checa que el string contenga datos
		    		  
		       
		       if(!validaConfig) {
		    	   
		    	   testCase.addQueryEvidenceCurrentStep(config_Result);  //Si no esta vacio, lo agrega a la evidencia
		       }
		      
		       System.out.println(validaConfig); 

	       
	   assertFalse("No existen registros de configuracion de transacciones ",validaConfig ); //Si esta vacio, imprime mensaje
	     
		
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

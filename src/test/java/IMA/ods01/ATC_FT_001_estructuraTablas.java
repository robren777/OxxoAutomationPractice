package IMA.ods01;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_estructuraTablas extends BaseExecution{
		
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_estructuraTablas_test(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		String tdcQueryConnectionOrigen = "SELECT * FROM BIDMMKT.CONTROL_CEDIS_IMPULSO"; // Estas no son las tablas, en la matriz no esta ninguna tabla
		String tdcQueryConnectionDestino = "SELECT * FROM BIDMMKT.BIDMMKT_BITACORA_CARGA";	

		/* PASO 1 *********************************************************************/	
				
		addStep("Validar la estructura de las columnas de la tabla origen");
		
		System.out.println(tdcQueryConnectionOrigen);		
		SQLResult connectionResult = executeQuery(dbPos, tdcQueryConnectionOrigen);

		boolean connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 

		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar la estructura de las columnas de la tabla destino");
		
		System.out.println(tdcQueryConnectionDestino);		
		connectionResult = executeQuery(dbPos, tdcQueryConnectionDestino);

		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;

		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");			
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
		return "Validar la estructura de las tablas de origen y destino";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QA Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_estructuraTablas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
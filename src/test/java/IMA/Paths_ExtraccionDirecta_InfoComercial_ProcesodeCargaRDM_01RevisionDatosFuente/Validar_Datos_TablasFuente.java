package IMA.Paths_ExtraccionDirecta_InfoComercial_ProcesodeCargaRDM_01RevisionDatosFuente;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class Validar_Datos_TablasFuente extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void Validar_Datos_TablasFuente_Test(HashMap<String, String> data) throws Exception {
		
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		
		String queryStep1 = "SELECT COUNT(*) FROM BIDMMKT.TEMP_DESABASTO_DIARIO";
		String queryStep2 = "SELECT COUNT(*) FROM BIDMMKT.FACT_VENTA";
		String queryStep3 = "SELECT COUNT(*) FROM BIDMMKT.DM_FACT_VENTA_SEMANA";
		
		// Paso 1
		addStep("Se valida que la tabla BIDMMKT.TEMP_DESABASTO_DIARIO contenga datos");
		System.out.println(queryStep1);		
		SQLResult connectionResult = executeQuery(fcdw4qa, queryStep1);
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 2
		addStep("Se valida que la tabla BIDMMKT.FACT_VENTA contenga datos");
		System.out.println(queryStep2);		
		connectionResult = executeQuery(fcdw4qa, queryStep2);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 3
		addStep("Se valida que la tabla BIDMMKT.DM_FACT_VENTA_SEMANA contenga datos");
		System.out.println(queryStep3);		
		connectionResult = executeQuery(fcdw4qa, queryStep3);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		

	}
	
  
	@Override
	public String setTestFullName() {
		return "Validar_Datos_TablasFuente";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que las tablas fuente contengan datos, para la ejecuci�n del proceso y sean insertados a las tablas destino";
	}

	@Override
	public String setTestDesigner() {
		return "EQUIPO AUTOMATIZACION";
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
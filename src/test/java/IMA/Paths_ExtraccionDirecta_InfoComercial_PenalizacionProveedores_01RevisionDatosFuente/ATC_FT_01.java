package IMA.Paths_ExtraccionDirecta_InfoComercial_PenalizacionProveedores_01RevisionDatosFuente;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_01 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_Test(HashMap<String, String> data) throws Exception {
		
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		String queryStep1 = "SELECT * "
				+ "FROM RMS100.ITEM_SUPP_COUNTRY a "
				+ "WHERE TRUNC(a.CREATE_DATETIME) BETWEEN (SELECT FECHA_INI  FROM biquerymkt.control_cargaobsequios"
				+ "  WHERE INDICADOR=8) AND(SELECT FECHA_FIN  FROM biquerymkt.control_cargaobsequios"
				+ "  WHERE INDICADOR=8)"
				+ "  OR TRUNC(a.LAST_UPDATE_DATETIME) BETWEEN (SELECT FECHA_INI  FROM biquerymkt.control_cargaobsequios"
				+ "  WHERE INDICADOR=8) AND (SELECT FECHA_FIN  FROM biquerymkt.control_cargaobsequios"
				+ "  WHERE INDICADOR=8)";
		
		String queryStep2 = "SELECT * "
				+ "FROM RMS100.FEM_FUTURE_COST_NEW a "
				+ "WHERE trunc(a.PROCESS_DATE) BETWEEN (SELECT FECHA_INI  FROM biquerymkt.control_cargaobsequios"
				+ "  WHERE INDICADOR=8) AND(SELECT FECHA_FIN  FROM biquerymkt.control_cargaobsequios"
				+ "  WHERE INDICADOR=8)";
		
		// Paso 1
		addStep("Se verifican los datos que ser�n insertados a la tabla RMS.ITEM_SUPP_COUNTRY aplicando el siguiente Query en BD RMS");
		System.out.println(queryStep1);		
		SQLResult connectionResult = executeQuery(rms_mex, queryStep1);
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 2
		addStep("Se verifican los datos que ser�n insertados a la tabla RMS.FEM_FUTURE_COST_NEW aplicando el siguiente Query en BD RMS");
		System.out.println(queryStep2);		
		connectionResult = executeQuery(rms_mex, queryStep2);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		
		
		
		
		
		
		
		

	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_01";
	}

	@Override
	public String setTestDescription() {
		return "Se validara el dato que ser� insertado al ejecutarse los procesos";
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
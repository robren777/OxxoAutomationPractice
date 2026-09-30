package IMA.Paths_ExtraccionDirecta_InventarioOptimoEnBodega;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_06_ValidarIndicadorAgrege79 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_06_ValidarIndicadorAgrege79_Test(HashMap<String, String> data) throws Exception {
		
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		String verificaDesc = "SELECT * FROM BIDMMKT.DIM_INDICADORES_RO WHERE ID_INDICATOR=79";
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Se ejecuta el siguiente comando sobre la tabla BIDMMKT.DIM_INDICADORES_RO para verificar el nuevo indicador.");
		
		System.out.println(verificaDesc);		
		SQLResult Res = executeQuery(fcdw4qa, verificaDesc);

		boolean connection = false;
		if(Res.getRowCount()>0)
			connection = true;
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(Res);					
		}		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informacion.");	

	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_01_ValidarNuevaColumna_OPER_DIARIO";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
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
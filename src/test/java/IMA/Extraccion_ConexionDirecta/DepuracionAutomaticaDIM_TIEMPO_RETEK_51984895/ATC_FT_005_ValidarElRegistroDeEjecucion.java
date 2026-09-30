package IMA.Extraccion_ConexionDirecta.DepuracionAutomaticaDIM_TIEMPO_RETEK_51984895;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_005_ValidarElRegistroDeEjecucion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarElRegistroDeEjecucion_test(HashMap<String, String> data) throws Exception {

		/***************************************
		 * BASE DATOS
		 ***************************************/
		SQLUtil dbBI = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,
				GlobalVariables.DB_PASSWORD_BI);

		/***************************************
		 * VARIABLES
		 ***************************************/
		String Query = "SELECT ID_MKT,LOADDATE,GRUPO,INICIO_FIN,CONCEPTO_TABLA FROM BIDMMKT.BIT_BITACORA_CARGA";

		/***************************************
		 * PASO 1
		 ***************************************/
		addStep("Verificar que se muestran registros con el INICIO y FIN del CONCEPTO_TABLA");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + Query);

		SQLResult ExecuteQuery = executeQuery(dbBI, Query);

		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {
			testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIDMMKT.DIM_TIEMPORETEK.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}
		assertFalse(StatusQuery, "No se muestran los registros en la tabla BIDMMKT.DIM_TIEMPORETEK.");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidarElRegistroDeEjecucion_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC_FT-001_CP005 - Validar el registro de ejecuci�n";
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

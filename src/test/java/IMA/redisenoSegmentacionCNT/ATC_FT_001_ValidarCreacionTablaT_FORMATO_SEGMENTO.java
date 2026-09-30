package IMA.redisenoSegmentacionCNT;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidarCreacionTablaT_FORMATO_SEGMENTO extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarCreacionTablaT_FORMATO_SEGMENTO_test(HashMap<String, String> data) throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		//utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS2,GlobalVariables.DB_USER_FCIASQAS2, GlobalVariables.DB_PASSWORD_FCIASQAS2);
		//utils.sql.SQLUtil DB2 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_ODS, GlobalVariables.DB_USER_ODS, GlobalVariables.DB_PASSWORD_ODS);
		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA, GlobalVariables.DB_PASSWORD_FCDW4QA);

		// 1 DB
		
		query = "SELECT * FROM TIENDAS.T_FORMATO_SEGMENTO WHERE ROWNUM <= 5";

		addStep("Se valida la conexion a la base de datos");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		System.out.println("se valida conexion");

		addStep("Se valida que la consulta regrese datos");
		ima.validarContenidoTablas(connectionResult, query);

		testCase.addQueryEvidenceCurrentStep(connectionResult);

		
		/*
		// 2 DB
		query = "SELECT * FROM CNT.T_FORMATO_SEGMENTO WHERE ROWNUM <= 5";

		addStep("Se valida la conexion a la base de datos");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		System.out.println("se valida conexion");

		addStep("Se valida que la consulta regrese datos");
		ima.validarContenidoTablas(connectionResult, query);

		testCase.addQueryEvidenceCurrentStep(connectionResult);
*/
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar consulta en base de datos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarCreaciónTablaT_FORMATO_SEGMENTO_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}

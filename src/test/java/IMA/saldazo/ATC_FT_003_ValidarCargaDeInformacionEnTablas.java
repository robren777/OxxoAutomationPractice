package IMA.saldazo;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_003_ValidarCargaDeInformacionEnTablas extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_ValidarExistenciaDeDatosEnTablas_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);
		
		/**
		 * Los registros son limitados a 50 para optimizar la ejecucion del caso de prueba
		 */
		
		//Paso 1
		addStep("Se realiza consulta en la tabla BIDMMKT.FACT_017_TRNDET");
		query = "Select * from BIDMMKT.FACT_017_TRNDET where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoColumnas(connectionResult, "RETIRO_MAYOR");
		ima.validarContenidoColumnas(connectionResult, "RETIRO_MENOR");
		
		//Paso 2
		addStep("Se realiza consulta en la tabla BIDMMKT.FACT_017_TRN");
		query = "Select * from BIDMMKT.FACT_017_TRN where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoColumnas(connectionResult, "RETIRO_MAYOR");
		ima.validarContenidoColumnas(connectionResult, "RETIRO_MENOR");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar carga de informacion";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_ValidarExistenciaDeDatosEnTablas_test";
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

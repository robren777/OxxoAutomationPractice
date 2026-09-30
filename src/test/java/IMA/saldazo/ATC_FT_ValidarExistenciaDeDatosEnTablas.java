package IMA.saldazo;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_ValidarExistenciaDeDatosEnTablas extends BaseExecution {
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
		addStep("Se realiza consulta en la tabla BIDMMKT.DM_017_TRN_XTM");
		query = "Select * from BIDMMKT.DM_017_TRN_XTM where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 2
		addStep("Se realiza consulta en la tabla bidmmkt.controlcargamonedero");
		query = "Select * from DWADMIN.DIM_TIENDA where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar existencia de datos en tablas";
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
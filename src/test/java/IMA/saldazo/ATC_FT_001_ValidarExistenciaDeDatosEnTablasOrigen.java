package IMA.saldazo;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidarExistenciaDeDatosEnTablasOrigen extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarExistenciaDeDatosEnTablasOrigen_test(HashMap<String, String> data)
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
		addStep("Se realiza consulta en la tabla bidmmkt.fact_017_trndet");
		query = "Select * from bidmmkt.fact_017_trndet where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 2
		addStep("Se realiza consulta en la tabla bidmmkt.controlcargamonedero");
		query = "Select * from bidmmkt.controlcargamonedero";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 3
		addStep("Se realiza consulta en la tabla biodsmkt.temp_017_comision");
		query = "Select * from biodsmkt.temp_017_comision where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 4
		addStep("Se realiza consulta en la tabla biquerymkt.vm_temp_017_base_trn");
		query = "Select * from biquerymkt.vm_temp_017_base_trn where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 5
		addStep("Se realiza consulta en la tabla bidmmkt.dim_017_monedero");
		query = "Select * from bidmmkt.dim_017_monedero where rownum <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 6
		addStep("Se realiza consulta en la tabla bidmmkt.dim_017_tipotransaccion");
		query = "Select * from bidmmkt.dim_017_tipotransaccion";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		//Paso 7
		addStep("Se realiza consulta en la tabla bidmmkt.fact_017_trnventa");
		query = "Select * from bidmmkt.fact_017_trnventa where rownum <= 50";
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
		return "Validar datos para Job";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarExistenciaDeDatosEnTablasOrigen_test";
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

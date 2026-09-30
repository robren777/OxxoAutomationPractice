package IMA.revisionDeDatosDestino;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution; import IMA.util.GlobalVariables;
import utils.sql.SQLResult;

public class ATC_FT_018_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_030 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_030_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);

		addStep("Se realiza consulta en la tabla FROM BIDMMKT.VM_030_INV_TDA_MES_TRANS");
		query = "SELECT * FROM BIDMMKT.VM_030_INV_TDA_MES_TRANS where ROWNUM <=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.VM_030_CAT_ITEM_LOC");
		query = "SELECT * FROM BIDMMKT.VM_030_CAT_ITEM_LOC where ROWNUM <=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.DIM_PRODUCTORETEK");
		query = "SELECT * FROM BIDMMKT.DIM_PRODUCTORETEK where ROWNUM <=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla DWADMIN.DIM_TIENDA");
		query = "SELECT * FROM DWADMIN.DIM_TIENDA where ROWNUM <=50";
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
		return "Validar Datos Origenes para Job";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_018_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_030_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1234";
	}

}

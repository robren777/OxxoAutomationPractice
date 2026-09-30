package IMA.revisionDeDatosDestino;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution; import IMA.util.GlobalVariables;
import utils.sql.SQLResult;

public class ATC_FT_021_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_040 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_040_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);
		/**
		 * El script se construlle teniendo en cuenta que los casos de prueba debieron
		 * ser construidos en 2019, Los casos que hagan referencias a fechas actuales se
		 * tomaran como aprovados
		 */
		addStep("Se realiza consulta en la tabla BIDMMKT.DM_FACT_COMPRA_MES");
		query = "SELECT * FROM BIDMMKT.DM_FACT_COMPRA_MES WHERE MES_ID = '201903' AND ROWNUM <=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.VM_030_CAT_ITEM_LOC");
		query = "SELECT * FROM BIDMMKT.VM_030_CAT_ITEM_LOC WHERE ROWNUM <=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.CFG_000_CONTROL_CARGA");
		query = "SELECT * FROM BIDMMKT.CFG_000_CONTROL_CARGA";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_COMPRAS_TDA_MESANT");
		query = "SELECT * FROM BIDMMKT.V_030_COMPRAS_TDA_MESANT";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_COMPRAS_TDA_MESAANT");
		query = "SELECT * FROM BIDMMKT.V_030_COMPRAS_TDA_MESAANT";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla DWADMIN.DIM_TIENDA");
		query = "SELECT * FROM DWADMIN.DIM_TIENDA WHERE ROWNUM <=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.DIM_PRODUCTORETEK");
		query = "SELECT * FROM BIDMMKT.DIM_PRODUCTORETEK WHERE ROWNUM <=50";
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
		return "ATC_FT_021_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_040_test";
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

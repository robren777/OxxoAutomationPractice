package IMA.revisionDeDatosDestino;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution; import IMA.util.GlobalVariables;
import utils.sql.SQLResult;

public class ATC_FT_033_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_080 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_033_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_080_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		String queryComparar;
		SQLResult connectionResult;
		SQLResult connectionResultComparar;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);

		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_MERMA_CEDIS");
		query = "SELECT * FROM BIDMMKT.V_030_MERMA_CEDIS WHERE ROWNUM<=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se valida que exista informacion en la segunda tabla");
		queryComparar = "(SELECT CENTRODISTRIBUCION_KEY,PROVEEDOR_KEY,PRODUCTO_KEY,MES_ID, SUM(NUM_MERMA) NUM_MERMA,SUM(ATM_MERMA) ATM_MERMA "
				+ "FROM BI_FACTS.FACT_030_MERMA_CEDIS@DBL_000_FCEDW.BI_FACTS A, BIDMMKT.CFG_000_CONTROL_CARGA B, BIDMMKT.DIM_TIEMPORETEK C "
				+ "WHERE C.MES_ID BETWEEN B.MES_INICIO AND B.MES_FIN AND C.TIEMPO_KEY = A.TIEMPO_KEY AND B.ID_SUJETO = 2 GROUP BY CENTRODISTRIBUCION_KEY,PROVEEDOR_KEY,PRODUCTO_KEY,MES_ID)";
		testCase.addTextEvidenceCurrentStep(queryComparar);
		connectionResultComparar = executeQuery(DB, queryComparar);
		ima.validarContenidoTablas(connectionResultComparar, queryComparar);

		addStep("Se realiza consulta para comparar la consulta con los datos de la primera consulta");
		ima.compararContenidoTablas(connectionResult, connectionResultComparar);

		addStep("Se realiza consulta en la tabla BIDMMKT.CFG_000_CONTROL_CARGA");
		query = "SELECT * FROM BIDMMKT.CFG_000_CONTROL_CARGA";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.DIM_PRODUCTORETEK");
		query = "SELECT * FROM BIDMMKT.DIM_PRODUCTORETEK WHERE ROWNUM<=50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_MERMA_CEDIS_MESANT");
		query = "SELECT * FROM BIDMMKT.V_030_MERMA_CEDIS_MESANT";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_MERMA_CEDIS_MESAANT");
		query = "SELECT * FROM BIDMMKT.V_030_MERMA_CEDIS_MESAANT";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_PROVEEDOR_FUENTEABASTO");
		query = "SELECT * FROM BIDMMKT.V_030_PROVEEDOR_FUENTEABASTO WHERE ROWNUM <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		addStep("Se realiza consulta en la tabla BIDMMKT.DM_030_MERMA_CEDIS_MMPCC");
		query = "SELECT * SELECT * FROM BIDMMKT.DM_030_MERMA_CEDIS_MMPCC WHERE ROWNUM <= 50";
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
		return "ATC_FT_033_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_080_test";
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

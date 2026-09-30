package IMA.revisionDeDatosDestino;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONArray;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution; import IMA.util.GlobalVariables;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_040_ValidarEjecucionDeJob_BI_030_ADEPROVEEDORES_100 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_040_ValidarEjecucionDeJob_BI_030_ADEPROVEEDORES_100_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);

		addStep("Se realiza consulta en la tabla BIDMMKT.TEMP_030_ADEPROV_MMPCC");
		query = "SELECT * FROM BIDMMKT.TEMP_030_ADEPROV_MMPCC WHERE ROWNUM <= 50";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_PROM_IEPS_PROV");
		query = "SELECT * FROM BIDMMKT.V_030_PROM_IEPS_PROV";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		addStep("Se realiza consulta en la tabla BIDMMKT.V_030_PROM_IVA_PROV");
		query = "SELECT * FROM BIDMMKT.V_030_PROM_IVA_PROV";
		testCase.addTextEvidenceCurrentStep(query);
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		
		// Se obtiene la cadena de texto del data provider en la columna "jobs
		// Se asigna a un array para poder manejarlo

		JSONArray array = new JSONArray(data.get("cm_jobs"));
		// testCase.addTextEvidenceCurrentStep("Ejecuci�n Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));

		// testCase.addTextEvidenceCurrentStep("Login");

		addStep("Login");
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();

		// testCase.addTextEvidenceCurrentStep("Inicio de job");
		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();

		addStep("Abrir la herramienta de control M para validar que la ejecuci�n del job haya sido exitosa");
		// testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion: " +
		// resultadoEjecucion);
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");

		u.hardWait(3);
		u.close();
		
		addStep("Se realiza consulta en la tabla BIDMMKT.DM_030_ADEPROV_MMPCC");
		query = "SELECT * FROM BIDMMKT.DM_030_ADEPROV_MMPCC";
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
		return "Validar ejecucion de Job";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_040_ValidarEjecucionDeJob_BI_030_ADEPROVEEDORES_100_test";
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
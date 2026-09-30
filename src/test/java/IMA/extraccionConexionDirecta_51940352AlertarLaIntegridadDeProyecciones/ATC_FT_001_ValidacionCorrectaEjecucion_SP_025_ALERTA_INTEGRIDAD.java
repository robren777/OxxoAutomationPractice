package IMA.extraccionConexionDirecta_51940352AlertarLaIntegridadDeProyecciones;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.json.JSONArray;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidacionCorrectaEjecucion_SP_025_ALERTA_INTEGRIDAD extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidacionCorrectaEjecucion_SP_025_ALERTA_INTEGRIDAD_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;
		utils.sql.SQLUtil DB;
		
		/*
		 * Se limitan los registros a 50 para limitar los tiempos de carga en la ejecucion.
		 */

		
		DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);

		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla biodsmkt.qa_025_proyecciones");
		query = "Select * From biodsmkt.qa_025_proyecciones";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		query = "select count(*) from biodsmkt.qa_025_proyecciones";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);

		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla biodsmkt.qa_025_proyecciones_hist");
		query = "Select * from biodsmkt.qa_025_proyecciones_hist where rownum <=5";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		query = "select count(*) from biodsmkt.qa_025_proyecciones_hist";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		int count = connectionResult.getColumnCount();
		
		//////////////////////////////////////////////////////////////
		
		JSONArray array = new JSONArray(data.get("cm_jobs"));
		testCase.addTextEvidenceCurrentStep("Ejecuci�n Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.USER_CTRL_M_JH, GlobalVariables.PASSWORD_CTRL_M_JH);

		testCase.addTextEvidenceCurrentStep("Login");

		addStep("Login");
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();
		
		addStep("Abrir la herramienta de control M para validar que la ejecuci�n del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");

		u.hardWait(7);
		u.close();
		
		//////////////////////////////////////////////////////////////////////
		
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla biodsmkt.qa_025_proyecciones");
		query = "Select * From biodsmkt.qa_025_proyecciones";
		connectionResult = executeQuery(DB, query);
		ima.validarConexionFallada(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		query = "select count(*) from biodsmkt.qa_025_proyecciones";
		connectionResult = executeQuery(DB, query);
		ima.validarConexionFallada(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);

		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla biodsmkt.qa_025_proyecciones_hist");
		query = "Select * from biodsmkt.qa_025_proyecciones_hist where date='fecha' and rownum <= 5";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		query = "select count(*) from biodsmkt.qa_025_proyecciones_hist where date='fecha'";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		int count2 = connectionResult.getColumnCount();
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		System.out.println(count + " " + count2);
		Boolean success;
		if (count == count2) {
			success = true;
		} else {
			success = false;
		}
		assertFalse(success, "No se ha insertado informacion");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar columnas en reporte";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidacionCorrectaEjecucion_SP_025_ALERTA_INTEGRIDAD_test";
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
package IMA.extracccionConexionDirecta_InformacionComercial_ReporteDeCompras_02RevisionDeEjecucionDeMapeos_Jobs;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONArray;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarCorrectaEjecucionDeProcesoDeExtraccionYCargaDeDatos_Sprint3 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarCorrectaEjecucionDeProcesoDeExtraccionYCargaDeDatos_Sprint3_test(
			HashMap<String, String> data) throws Exception {

		// Se obtiene la cadena de texto del data provider en la columna "jobs
		// Se asigna a un array para poder manejarlo
		
		addStep("Entrar a Control-M");
		JSONArray array = new JSONArray(data.get("cm_jobs"));
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.USER_CTRL_M_JH, GlobalVariables.PASSWORD_CTRL_M_JH);

		testCase.addTextEvidenceCurrentStep("Login");

		addStep("Login");
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();
		
		u.hardWait(10);
		u.waitForLoadPage();
		addStep("ODS_RMS2ITEM_LOC_070");
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		
		u.hardWait(10);
		u.waitForLoadPage();
		/*
		 * ODS_RMS2REPL_ITEM_LOC_090
		 */
		addStep("ODS_RMS2REPL_ITEM_LOC_090");
		array = new JSONArray(data.get("cm_jobs2"));
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs2"));

		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		u.hardWait(10);
		u.waitForLoadPage();
		/*
		 * ODS_RMS2ITEM_SUPP_COUNTRY_DIM_130
		 */
		addStep("Se ejecuta el Job ODS_RMS2ITEM_SUPP_COUNTRY_DIM_130");
		array = new JSONArray(data.get("cm_jobs3"));
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs3"));

		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		u.hardWait(10);
		u.waitForLoadPage();
		/*
		 * ODS_RDF2XXFC_CONSTANTE_PROMO_010
		 */
		addStep("Se ejecuta el ODS_RDF2XXFC_CONSTANTE_PROMO_010");
		array = new JSONArray(data.get("cm_jobs4"));
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs4"));

		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		u.hardWait(10);
		u.waitForLoadPage();

		
		/*
		 * Se ejecuta el Job ODS_RMS2XXFC_MOTIVOS_DEVOLUCION_120
		 */
		addStep("Se ejecuta el Job ODS_RMS2XXFC_MOTIVOS_DEVOLUCION_120");
		array = new JSONArray(data.get("cm_jobs5"));
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs5"));

		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");

		u.hardWait(7);
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar correcta ejecución de proceso de extracción y carga de datos Sprint 3";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_ValidarCorrectaEjecucionDeProcesoDeExtraccionYCargaDeDatos_Sprint3";
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
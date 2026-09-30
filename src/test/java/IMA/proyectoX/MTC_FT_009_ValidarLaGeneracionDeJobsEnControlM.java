package IMA.proyectoX;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_009_ValidarLaGeneracionDeJobsEnControlM extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void MTC_FT_009_ValidarLaGeneracionDeJobsEnControlM_test(HashMap<String, String> data) throws Exception {

		// Se obtiene la cadena de texto del data provider en la columna "jobs
		// Se asigna a un array para poder manejarlo
		JSONObject array;
		addStep("Entrar a Control-M");
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.USER_CTRL_M_JH, GlobalVariables.PASSWORD_CTRL_M_JH);
		boolean flag;
		ControlM j;

		testCase.addTextEvidenceCurrentStep("Login");

		addStep("Login");
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();
		testCase.addScreenShotCurrentStep("Login");

		u.hardWait(10);
		u.waitForLoadPage();

		// Job1
		addStep("Se busca el Job ODS_MAP_CNT2ODS_T_FORMATO_SEGMENTO_140");
		array = new JSONObject(data.get("cm_jobs"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		System.out.println("Si existe el Job");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");

		// Job2
		addStep("Se busca el Job ODS_MAP_CNT2ODS_T_FORMATO_SEGMENTO_DET_150");
		array = new JSONObject(data.get("cm_jobs2"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		System.out.println("Si existe el Job");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");

		// Job3
		addStep("Se busca el Job BI_121_MKT_NUEVA_SEGM_SQL_010");
		array = new JSONObject(data.get("cm_jobs3"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		System.out.println("Si existe el Job");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");

		// Job4
		addStep("Se busca el Job BI_MAP_121_VIEW2TBL_CONCEPTO_DIF_020");
		array = new JSONObject(data.get("cm_jobs4"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		System.out.println("Si existe el Job");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");

		// Job5
		addStep("Se busca el BI_MAP_121_VIEW2TBL_MAESTRO_SEGM_030");
		array = new JSONObject(data.get("cm_jobs5"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job6
		addStep("Se busca el Job BI_MAP_121_VIEW2TBL_OVERLAY_040");
		array = new JSONObject(data.get("cm_jobs6"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job7
		addStep("Se busca el Job BI_MAP_121_VIEW2TBL_POTENCIAL_050");
		array = new JSONObject(data.get("cm_jobs7"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job8
		addStep("Se busca el Job BI_121_MKT_NUEVA_SEGM_SQL_060");
		array = new JSONObject(data.get("cm_jobs8"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job9
		addStep("Se busca el Job BI_MAP_121_VM2TEMP_SEGMENTO2_070");
		array = new JSONObject(data.get("cm_jobs9"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job10
		addStep("Se busca el Job BI_MAP_121_VM2TEMP_SEGMENTO3_080");
		array = new JSONObject(data.get("cm_jobs10"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job11
		addStep("Se busca el Job BI_MAP_121_TEMP2DIM_TIENDA_090");
		array = new JSONObject(data.get("cm_jobs11"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();

		// Job12
		addStep("Se busca el Job BI_121_MKT_NUEVA_SEGM_SQL_100");
		array = new JSONObject(data.get("cm_jobs12"));
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que exista el job");
		j = new ControlM(u, testCase, array);

		flag = j.searchJob();
		assertTrue(flag, "El job no existe");
		testCase.addTextEvidenceCurrentStep("Si existe el Job");
		u.waitForLoadPage();
		
		/*
		 * EN ESTA PARTE TERMINA EL AVANCE DEL CASO 9 DE LA MATRIZ PROYECTO X, ES EL PASO NUMERO 5.
		 */
		/*
		 * u.hardWait(10); u.close();
		 */
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar generacion de Jobs en Control-M";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "MTC_FT_009_ValidarLaGeneracionDeJobsEnControlM_test";
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
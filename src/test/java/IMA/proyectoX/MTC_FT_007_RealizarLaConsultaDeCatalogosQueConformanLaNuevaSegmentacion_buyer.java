package IMA.proyectoX;

import static org.testng.Assert.assertEquals;

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

public class MTC_FT_007_RealizarLaConsultaDeCatalogosQueConformanLaNuevaSegmentacion_buyer extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void MTC_FT_007_RealizarLaConsultaDeCatalogosQueConformanLaNuevaSegmentacion_buyer_test(
			HashMap<String, String> data) throws Exception {

		
		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS2, GlobalVariables.DB_USER_FCIASQAS2,
				GlobalVariables.DB_PASSWORD_FCIASQAS2);
		//Paso 1
		query = "SELECT * FROM XXNST.NST_MAESTRO_SEGMENTOS";
		addStep("Se valida la conexion a la base de datos");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		
		//Paso2
		addStep("Se valida que las consultas regresen datos");
		
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM XXNST.NST_NECESIDAD_DESARROLLO";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM XXNST.NST_NIVEL_OVERLAY";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM XXNST.NST_OVERLAY";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM XXNST.NST_POTENCIAL";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM XXNST.NST_REL_POTENCIAL_NEC_DES";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
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
		
		addStep("Se ejecuta el Job ODS_MAP_CNT2ODS_T_FORMATO_SEGMENTO_140");
		testCase.addTextEvidenceCurrentStep("Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa");
		testCase.addTextEvidenceCurrentStep("Inicio de job");
		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();

		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		
		u.hardWait(10);
		u.waitForLoadPage();
		/*
		 * Se ejecuta el Job ODS_RMS2DIVISION_030
		 */
		addStep("Se ejecuta el Job TQABI_NUEVA_SEGM_TDA");
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

		u.hardWait(7);
		u.close();
		
		
		DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_OVERLAY";
		addStep("Se valida la conexion a la base de datos");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		
		//Paso2
		addStep("Se valida que las consultas regresen datos");
		
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_POTENCIAL";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_MAESTRO_SEGMENTO";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_MAESTRO_SEGMENTO";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_OVERLAY_COMIDA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_OVERLAY_DESAYUNO";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_OVERLAY_HOGAR";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_POTENCIAL_COMIDA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_POTENCIAL_DESAYUNO";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM DWADMIN.VM_121_DIM_POTENCIAL_HOGAR";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_NECECIDAD_NA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_NECECIDAD_HOGAR";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_NECECIDAD_DESAYUNO";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		//Paso 1
		query = "SELECT * FROM BIQUERYMKT.V_121_NECECIDAD_COMIDA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar informacion de base de datos despue de la ejecucion de jobs";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "MTC_FT_007_RealizarLaConsultaDeCatalogosQueConformanLaNuevaSegmentacion_buyer_test";
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
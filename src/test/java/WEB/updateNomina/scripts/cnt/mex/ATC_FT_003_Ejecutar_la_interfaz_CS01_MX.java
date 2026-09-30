package WEB.updateNomina.scripts.cnt.mex;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WEB.updateNomina.om.UpdateNomina;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class ATC_FT_003_Ejecutar_la_interfaz_CS01_MX extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Ejecutar_la_interfaz_CS01_MX_test(HashMap<String, String> data) throws Exception {

		//VARIABLES

		//String password = PasswordUtil.decryptPassword(data.get("password"));
		String controlMUser = data.get("controlMUser");
		String controlMPwd = PasswordUtil.decryptPassword(data.get("controlMPwd"));
		System.out.println(controlMPwd);

		JSONObject obj = new JSONObject(data.get("job"));
		JSONObject obj2 = new JSONObject(data.get("job2"));

		String host = data.get("host");
		String user = data.get("user");
		String pass = PasswordUtil.decryptPassword(data.get("pass"));

		String crSuperior = data.get("crSuperior");
		String crTienda = data.get("crTienda");
		System.out.println(pass);

		//UTILERIAS WEB
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio controlMInicio = new Control_mInicio(u, controlMUser, controlMPwd);
		ControlM controlMJob = new ControlM(u, testCase, obj);
		ControlM controlMJob2 = new ControlM(u, testCase, obj2);
		UpdateNomina updNom = new UpdateNomina(u, testCase);

		//UTILERIAS SQL
		SQLUtil dbFCDW2QA = new SQLUtil(host, user, pass);

		System.out.println("Exitoso conexion a BD FCDW2QA para CNT Mexico");
		SQLUtil dbWMLog = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA,
				GlobalVariables.DB_PASSWORD_FCWMLQA);
		System.out.println("Exitoso conexion a BD WM Log para revision de status de ejecucion de los jobs");


		// PASO 1
		addStep("Validar que en CNT la tienda ya esté en estatus Operativa");
		//SELECT *FROM t_tiendas where STATUS in ('S_OPERATIVA'); //query para ver qué tiendas se encuentran en status S y Operativas
		//SELECT *FROM t_tiendas where CR_TIENDA in ('50NRG'); //query para buscar una tienda en especifico
		String queryBuscarCrStatusOperativa = "SELECT CR_SUPERIOR, CR_TIENDA, STATUS FROM t_tiendas WHERE STATUS = 'S_OPERATIVA' AND CR_SUPERIOR= '"+crSuperior+"' AND CR_TIENDA='"+crTienda+"'";
		SQLResult resultBuscarCrStatusOperativa = executeQuery(dbFCDW2QA, queryBuscarCrStatusOperativa);
		System.out.println("query exitoso");
		testCase.addQueryEvidenceCurrentStep(resultBuscarCrStatusOperativa);
		String Tienda = resultBuscarCrStatusOperativa.getData(0,"CR_TIENDA"); //este valor deberia de ser igual en tiendas.XX_NOMCOM_CNT_SAP columna ceco
		System.out.println(Tienda);

		// PASO 2
		addStep("Ingresar a Control -M con usuario y contrasena valido http://10.184.56.194:18080/ControlM/");
		// PASO 3
		addStep("\"Pasar informacion a NOMCOM\n" +
				"Grupo: TQA_BAJO_DEMANDA\n" +
				"Job: CntaSAP.sh\"\n");
		updNom.logInControlMyEjecutarJob(controlMInicio, controlMJob);
		
		// PASO 4
		addStep("\"Validar envio del ceco a la tabla temporal \n" +
				"select*from tiendas.XX_NOMCOM_CNT_SAP where ceco in  (''\n" +
						"Validar status en L, sociedad F099\"\n");
		//XXtiendas.XX_NOMCOM_CNT_SAP es una tabla temporal que solo se encuentra despues de ejecutar el job CntaSAP.sh que no puedo ejecutar hasta que sea la semana de ejecucion para CNT MEX
		String queryCntSap = "SELECT wm_run_id, wm_status, sociedad, division_fi, ceco, nombre, estado_fin " +
									"FROM tiendas.XX_NOMCOM_CNT_SAP WHERE ceco = '"+Tienda+"'";
		System.out.println(queryCntSap);
		SQLResult resultCntSap = executeQuery(dbFCDW2QA, queryCntSap);
		String statusCntSap =resultCntSap.getData(0, "WM_STATUS");
		System.out.println(statusCntSap);
		testCase.addTextEvidenceCurrentStep("Query utilizado : "+queryCntSap);
		testCase.addQueryEvidenceCurrentStep(resultCntSap);
		
		// PASO 5
		addStep("\"Ingresar a Control-M con usuario y contraseña valido:\n" +
				" http://10.184.56.194:18080/ControlM/\"\n");
		// PASO 6
		addStep("ejecutar interfaz Job: runCS01 Carpeta: TQA_TEST_WM99_INT");
		updNom.ejecutarJob(controlMJob2);
		
		// PASO 7
		addStep("\"Se valida que la ejecucion termino en \"\"S\"\" en el servidor redhat oxwwqbocq01\n" +
				"SELECT * FROM\n" +
				"WMLOG.WM_LOG_RUN \n" +
				"WHERE INTERFACE LIKE\n" +
				"'%S%'\n" +
				"ORDER BY START_DT\n" +
				"DESC;\"\n");

		String queryWMLog = "SELECT * FROM (select run_id, interface, start_dt,end_dt, status from WMLOG.WM_LOG_RUN WHERE INTERFACE='CS01'order by run_id desc) where rownum=1";
		SQLResult resultWMLog = executeQuery(dbWMLog, queryWMLog);
		/*
		boolean flag = resultWMLog.getData(0, "status").equals("E");
		if (!flag) {
			testCase.addTextEvidenceCurrentStep("Fallo la ejecución del job ");
			System.out.println("Fallo la ejecución del job ");
		}
		*/
        //assertTrue(flag, "Se ejecuto CS01 correctamente");
		testCase.addQueryEvidenceCurrentStep(resultWMLog);
		
		// PASO 8
		addStep("Validar cambio estatus en la tabla xx_nomcom_cnt_sap que indica que el ceco o cr_tienda se encuentra de lado del SAP FI");
		String queryCntSapStatusE = "SELECT wm_run_id, wm_status, sociedad, division_fi, ceco, nombre, estado_fin " +
				"FROM tiendas.XX_NOMCOM_CNT_SAP WHERE ceco = '"+Tienda+"'";
		System.out.println(queryCntSapStatusE);
		SQLResult resultCntSapStatusE = executeQuery(dbFCDW2QA, queryCntSap);
		
		testCase.addQueryEvidenceCurrentStep(resultCntSapStatusE);
		
		addStep("Validar envio de Cecos en SAP FI");
		testCase.addTextEvidenceCurrentStep("La validacion se relizara de forma manual");
		
		addStep("Se ejecutara interfaz ALE por parte de expertal para enviar la informacion hacia SAP HCM");
		testCase.addTextEvidenceCurrentStep("La validacion se relizara de forma manual");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Ejecutar_la_interfaz_CS01_MX";
	}

	@Override
	public String setTestDescription() {
		return "Ejecución de la interfaz CS01(M) envie la tienda operativa hacia el SAP financiero (SAP FI)";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Alberto De Leon Rodriguez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a Control M y acceso a BD de CNT Mexico";
	}

	@Override
	public void beforeTest() {

	}


}

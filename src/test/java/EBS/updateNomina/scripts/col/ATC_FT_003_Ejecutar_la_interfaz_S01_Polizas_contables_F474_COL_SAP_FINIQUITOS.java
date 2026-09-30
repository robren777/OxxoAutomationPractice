package EBS.updateNomina.scripts.col;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import EBS.updateNomina.om.Login;
import EBS.updateNomina.om.PolizasSOL;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;


public class ATC_FT_003_Ejecutar_la_interfaz_S01_Polizas_contables_F474_COL_SAP_FINIQUITOS extends BaseExecution {

	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_002_Ejecutar_la_interfaz_S01_Polizas_contables_F474_COL_SAP_NOMINAS_test(HashMap<String, String> data) throws Exception {



		 //VARIABLES
		 String serverFCWM6QA = data.get("serverFCWM6QA");
		 String usernameFCWM6QA = data.get("usernameFCWM6QA");
		 String passwordFCWM6QA = data.get("passwordFCWM6QA"); //TODO: C�mo encriptar contrase�as?

		 //String password = PasswordUtil.decryptPassword(data.get("password"));
		 String controlMUser = data.get("controlMUser");
		 String controlMPwd = data.get("controlMPwd");

		 String portalAppsUser = data.get("portalAppsUser");
		 String portalAppsPwd = data.get("portalAppsPwd");

		 //JSONObject obj = new JSONObject(data.get("job"));
		 JSONObject obj = null;
		 try {
			 obj = new JSONObject(data.get("job"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }

		 //QUERIES
		 String query0 = "SELECT * FROM SAPUSER.sap_inbound_docs docs JOIN SAPUSER.sap_pyr_header header ON docs.Id = header.SID_ID WHERE docs.STATUS = 'S' AND header.BUKRS = 'F474'";
		 String query3 = "SELECT * FROM apps.gl_interface WHERE group_id IN (6222408, 6222406)";

		 //UTILERIAS WEB
		 SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		 ControlM controlM = new ControlM(u, testCase, obj);
		 Login login = new Login(u, testCase);
		 PolizasSOL polizasSOL = new PolizasSOL(u, testCase);

		 //UTILERIAS SQL
		 utils.sql.SQLUtil bdWMLog = new utils.sql.SQLUtil(serverFCWM6QA, usernameFCWM6QA, passwordFCWM6QA);
		 utils.sql.SQLUtil ebsColombia = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_EBS_COL, GlobalVariables.DB_USER_EBS_COL, GlobalVariables.DB_PASSWORD_EBS_COL);
		 // PASO 1
		 addStep("Ingresar a la BD FCWM6QA de WMLOG" + "usuario: QAVIEW" + "host: oxzvmwmiracq01.femcom.net" + "puerto: 1521" + "nombre servicio: FCWM6QA.FEMCOM.NET");
		 System.out.println("Paso 1: Conexi�n con BD FCWM6QA exitosa -> "+ bdWMLog.toString());

		 // PASO 2
		 addStep("Validar estatus L para polizas con la sociedad F474 en la tabla sapuser.sap_inbound_docs ,SAP_PYR_HEADER");

		 SQLResult sap_inbound_docs = bdWMLog.executeQuery(query0);
		 System.out.println("Paso 2: consulta a SAPUSER.sap_inbound_docs SAPUSER.sap_pyr_header EXITOSO");
		 //Mostrar resultados query1
//		 List<String[]> resultado1 = sap_inbound_docs.getAllData();
//		 for (String[] row : resultado1) {
//			 System.out.println(Arrays.toString(row));
//			 testCase.addTextEvidenceCurrentStep(Arrays.toString(row)); //TODO: c�mo puedo evidenciar el resultado de los queries?
//		 }

//		 // PASO 3
//		 addStep("Ingresar a Control -M con usuario y contrase�a valido http://10.184.56.194:18080/ControlM/");
//		 u.get(GlobalVariables.CONTROL_M_SERVER);
//		 u.waitForLoadPage();
//		 CM.logOn();
//		 testCase.addScreenShotCurrentStep(u, "control m inicio");
//
//		 // PASO 4
//		 addStep("Ejecutar interfaz S01 Carpeta: TQA_TEST_WM99_INT");
//		 boolean flag = controlM.searchJob();
//		 assertFalse(!flag, "No se encontro el job");
//		 System.out.println("Job Status " + controlM.getCurrentStatus());
//		 //Ejecucion
//		 String resultadoDelJob = controlM.executeJob();
//		 testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob);
//		 System.out.println("Resultado de la ejecuci�n " + resultadoDelJob);
//
//		 //Valor del output
//		 testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
//		 System.out.println("Output " + controlM.getOutput());
//
//		 //Validacion del caso
//		 boolean testResult = true;
//		 if (resultadoDelJob.equals("Failure")) {
//			 testResult = false;
//			 testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
//			 System.out.println("Fallo la ejecuci�n del job ");
//		 }
//
//		 assertTrue(testResult, "La ejecucion del job fue exitosa");
//		 controlM.closeViewpoint();

		 // PASO 5
		 addStep("\"Ingresar a la BD FCWM6QA de WMLOG\n" + "usuario: QAVIEW\n" + "host: oxzvmwmiracq01.femcom.net\n" + "puerto: 1521\n" + "nombre servicio: FCWM6QA.FEMCOM.NET\"\n");

		 // PASO 6
		 addStep("Validar el cambio de status a �S� en la tabla sapuser.sap_inbound_docs");

		 SQLResult sap_inbound_docs2 = bdWMLog.executeQuery(query0);
		 System.out.println("Paso 6: consulta a SAPUSER.sap_inbound_docs SAPUSER.sap_pyr_header EXITOSO");
		 //Resultados segunda ejecuci�n query1
//		 List<String[]> resultado3 = sap_inbound_docs2.getAllData();
//		 for (String[] row : resultado3) {
//			 System.out.println(Arrays.toString(row));
//			 testCase.addTextEvidenceCurrentStep(Arrays.toString(row)); //TODO: c�mo puedo evidenciar el resultado de los queries?
//		 }

		 // PASO 7
		 addStep("\"Ingresar a la BD EBS Colombia\n" +
				 "usuario: APPSVIEW\n" +
				 "host: fcfin5.femcom.net\n" +
				 "puerto 1531\n" +
				 "servicio: FCEBSCO2\n" +
				 "esquema: APPS\"\n");
		 // PASO 8
		 addStep("validar el envio de polizas a la tabla de EBS select * from apps.gl_interface where group_id in  \n");
		 ebsColombia.executeQuery(query3);
		 System.out.println("Paso 7: Conexi�n EBS Colombia EXITOSO");
		 System.out.println("Paso 8: Ejecuci�n query 3 EXITOSO -> "+query3);

		 // PASO 9
		 addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contrase�a v�lidos.");
		 login.IngresarAAuthFemco(portalAppsUser, portalAppsPwd);

		 // PASO 10
		 addStep("Seleccionar la opci�n de Integraci�n P�lizas SOL");
		 login.SeleccionarSOL();

		 // PASO 11
		 addStep("Seleccionar el men� \"Reportes\"");
		 // PASO 12
		 addStep("Seleccionar el reporte \"Polizas Enviadas a Oracle GL\"");
		 polizasSOL.IngresarPolizasEnviadasAOracleGL();


		 // PASO 13
		 addStep("\"Seleccionar criterio de b�squeda de Polizas Enviadas a Oracle GL, los cuales con los siguientes:\n" +
				 "-Buscar por ID SAP\n" +
				 "-Buscar por Filtros: Periodo, Rango de Fechas\"\n");
		 polizasSOL.SeleccionarBusquedaPolizasEnviadasAOracleGL();

		 // PASO 14
		 addStep("Dar click en bot�n Buscar por Filtros o Buscar por ID SAP seg�n el criterio de b�squeda.\n");
	    }

	    @Override
	    public String setTestFullName() {
	        return "ATC_FT_009_Envio_de_pago de_nomina_y_generacion_de_polizas_a_traves_de_la_interface_OS1_MX_F097";
	    }

	    @Override
	    public String setTestDescription() {
	        return "Ejecuci�n de la interfaz   FEMSA_OS1_MX de avante para enviar informaci�n de p�lizas de SAP_INBOUND_DOCS hacia SAP_RPY_DETL y contabilizaci�n de p�lizas en GL_INTERFACE";
	    }

	    @Override
	    public String setTestDesigner() {
	        return "Mario Alberto De Le�n Rodr�guez";
	    }

	    @Override
	    public String setTestInstanceID() {
	        return null;
	    }

	    @Override
	    public String setPrerequisites() {
	        return "Acceso a EBS, CNT, Control M";
	    }

	    @Override
	    public void beforeTest() {

	    }


}

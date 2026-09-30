package EBS.updateNomina.scripts.mex;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.updateNomina.om.Login;
import EBS.updateNomina.om.PolizasSOL;
import modelo.BaseExecution;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_014_Ejecutar_la_interfaz_OS9_MX_Finiquitos extends BaseExecution {
	
	
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_014_Ejecutar_la_interfaz_OS9_MX_Finiquitos_test(HashMap<String, String> data) throws Exception{
		
		
		String serverFCWM6QA = data.get("serverFCWM6QA");
		 String usernameFCWM6QA = data.get("usernameFCWM6QA");
		 String passwordFCWM6QA = data.get("passwordFCWM6QA"); //TODO: C�mo encriptar contrase�as?
		 
		 String serverFCWMLQA = data.get("serverFCWMLQA");
		 String usernameFCWMLQA = data.get("usernameFCWMLQA");
		 String passwordFCWMLQA = data.get("passwordFCWMLQA");

		 //String password = PasswordUtil.decryptPassword(data.get("password"));
		 String controlMUser = data.get("controlMUser");
		 String controlMPwd = data.get("controlMPwd");

		 String portalAppsUser = data.get("portalAppsUser");
		 String portalAppsPwd = data.get("portalAppsPwd");
		
		 String idBD = null;
		 
		 
		 
		 /*JSONObject obj = null;
		 try {
			 obj = new JSONObject(data.get("job"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }
		*/
		 //QUERIES
		 String query0 = "SELECT docs.ID,docs.STATUS,header.BUKRS FROM SAPUSER.sap_inbound_docs docs JOIN SAPUSER.sap_pyr_header header ON docs.Id = header.SID_ID WHERE docs.STATUS = 'L' AND header.BUKRS = 'F099' and rownum < 2;";
		 String query3 = "SELECT * FROM apps.gl_interface WHERE group_id IN (6222408, 6222406)";

		 //UTILERIAS WEB
		 SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		 //ControlM controlM = new ControlM(u, testCase, obj);
		 Login login = new Login(u, testCase);
		 PolizasSOL polizasSOL = new PolizasSOL(u, testCase);

		 //UTILERIAS SQL
		 /*utils.sql.SQLUtil bdWMLog = new utils.sql.SQLUtil(serverFCWM6QA, usernameFCWM6QA, passwordFCWM6QA);
		 utils.sql.SQLUtil ebsMexico = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		 utils.sql.SQLUtil EBSMEX = new utils.sql.SQLUtil(GlobalVariables.DB_USER_EBSMex, GlobalVariables.DB_PASSWORD_EBSMex, GlobalVariables.DB_HOST_EBSMex);
		 utils.sql.SQLUtil BDWMLog = new utils.sql.SQLUtil(serverFCWMLQA, usernameFCWMLQA, passwordFCWMLQA);
		 utils.sql.SQLUtil BD_ORAFIN = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_Ebs, GlobalVariables.DB_USER_Ebs, GlobalVariables.DB_PASSWORD_Ebs);*/
		 
		 
		 // ***** PASO 1 *****
		 addStep("Ingresar a la BD EBS México usuario: APPSVIEW host: oxfdbfinq00.femcom.neT puerto: 1535nombre servicio: AVEBQA esquema: APPS ");
		 //System.out.println("Paso 1: Conexi�n con BD FCWM6QA exitosa -> "+ EBSMEX.toString());
		 
		// ***** PASO 2 *****
		 addStep("Validar la información de las pólizas en BD AVEBQA en la tabla GL_INTERFACE con estatus diferente de NEW");
		 /*String query4 = "SELECT STATUS, LEDGER_ID, USER_JE_CATEGORY_NAME, SEGMENT1, SEGMENT2, SEGMENT3, SEGMENT4, SEGMENT7, REFERENCE6, DATE_CREATED, GROUP_ID  FROM GL.GL_INTERFACE  WHERE UPPER( STATUS ) <> 'NEW' AND GROUP_ID = 1058" ;
		 SQLResult Polizas = EBSMEX.executeQuery(query4);
		 if (Polizas.getRowCount() > 0) {
			 System.out.println("correcto, la consulta obtiene datos ");
			 testCase.addQueryEvidenceCurrentStep(Polizas);
			 
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }*/
		 
		 // ***** PASO 3 *****
		 addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contraseña válidos.");
		 //login.IngresarAAuthFemco(portalAppsUser, portalAppsPwd);
		 
		 // ***** PASO 4 *****
		 addStep("Seleccionar la opci�n de Integraci�n P�lizas SOL");
		// login.SeleccionarSOL();
		 
		 // ***** PASO 5 ***** 
		// addStep("Seleccionar el menú Reportes");
		 
		 // ***** PASO 6 *****
		 addStep("Seleccionar el reporte Polizas Enviadas a Oracle GL");
		 //polizasSOL.IngresarPolizasEnviadasAOracleGL();
		 
		 // ***** PASO 7 *****
		 addStep("Seleccionar criterio de búsqueda de Polizas Enviadas a Oracle GL, los cuales con los siguientes:-Buscar por ID SAP Buscar por Filtros: Periodo, Rango de Fechas");
		// polizasSOL.SeleccionarBusquedaPolizasEnviadasAOracleGL();
		 
		// ***** PASO 8 *****
		 addStep("Dar click en botón Buscar por Filtros o Buscar por ID SAP según el criterio de búsqueda.");
		 // validar con dolores esta parte tambien afecta al caso 2, 12, 13, 14	 
		 
		 
		// ***** PASO 9 *****
		 
		 addStep("Realizar la conexión a la BD FCWM6QA Usuario: QAVIEW host:oxzvmwmiracq01.femcom.net puerto:1521 servicio: FCWM6QA.FEMCOM.NET");
		 //System.out.println("Paso 9: Conexi�n con BD FCWM6QA exitosa -> "+ bdWMLog.toString());
		
		
		// ***** PASO 10 *****
		
		addStep(" Validar que se actualiza correctamente la información de la tabla SAP_INBOUND_DOCS en BD FCWML6QA con estatus F");
		/* String query5 = "SELECT ID, TARGET_ID, DOCNUM, GSBER, RUNID, SEQNO, CREATE_DATE, ( SELECT DOCLIN FROM SAPUSER.SAP_PYR_DETL T3 WHERE T3.SID_ID = T1.ID AND ROWNUM < 2 ) AS DOCLIN FROM SAPUSER.SAP_INBOUND_DOCS T1, SAPUSER.SAP_PYR_HEADER T2 WHERE T1.ID = T2.SID_ID AND T2.TARGET_SYS = 'O' AND T1.STATUS = 'S' AND T2.BUKRS = 'F096'";
		 		 
			 SQLResult SAP_INBOUND_DOCS =  bdWMLog.executeQuery(query5); 
			 if (SAP_INBOUND_DOCS.getRowCount() > 0) {
				 System.out.println("correcto, la consulta trae datos");
				 testCase.addQueryEvidenceCurrentStep(SAP_INBOUND_DOCS);
				 String statusBD = SAP_INBOUND_DOCS.getData(0, "STATUS").toString();
				 assertEquals(statusBD,"F");
			 }else {
				 Assert.fail("Error, la consulta no trae datos");
			 }*/
			 
		// ***** PASO 11 *****
			 
		addStep("Ingresar a la BD EBS México usuario: APPSVIEW host: oxfdbfinq00.femcom.net puerto: 1535 nombre servicio: AVEBQA esquema: APPS ");
		//System.out.println("Paso 11: Conexi�n con BD FCWM6QA exitosa -> "+ EBSMEX.toString());
		
		
		// ***** PASO 12 *****
		addStep("Validar que exista información a procesar ");
		/*String query6 = "  select * from wmuser.xxfc_gl_sap_headers_sent;";
		SQLResult infoProcesar = EBSMEX.executeQuery(query6);
		if (infoProcesar.getRowCount() > 0) {
			 System.out.println("correcto, la consulta contiene información a procesar");
			 testCase.addQueryEvidenceCurrentStep(infoProcesar);
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }*/
		
		// ***** PASO 13 ***** 
		addStep("Realizar la conexión a la BD FCWM6QA: Usuario: QAVIEW host:oxzvmwmiracq01.femcom.net puerto:1521 servicio: FCWM6QA.FEMCOM.NET");
		//System.out.println("Paso 13: Conexi�n con BD FCWM6QA exitosa -> "+ bdWMLog.toString());
		
		// ***** PASO 14 ***** 
		addStep("Se valida que exista informacion a procesar");
		
		/*String query7 = "SELECT ID, TARGET_ID, DOCNUM, GSBER, RUNID, SEQNO, CREATE_DATE, ( SELECT DOCLIN FROM SAP_PYR_DETL T3 WHERE T3.SID_ID = T1.ID AND ROWNUM < 2 ) AS DOCLIN FROM SAP_INBOUND_DOCS T1, SAP_PYR_HEADER T2 WHERE T1.ID = T2.SID_ID AND T2.TARGET_SYS = 'O' AND T1.STATUS = 'S' AND T2.BUKRS = 'F096'";
		SQLResult VAl_infoProcesar = bdWMLog.executeQuery(query7);
		if (VAl_infoProcesar.getRowCount() > 0) {
			 System.out.println("correcto, la consulta contiene información a procesar");
			 testCase.addQueryEvidenceCurrentStep(VAl_infoProcesar);
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }*/
		
		// ***** PASO 15 *****	
		addStep("Ingresar a Control -M con usuario y contraseña valido http://10.184.56.194:18080/ControlM/");
		 /*u.get(GlobalVariables.CONTROL_M_SERVER);
		 u.waitForLoadPage();
		 CM.logOn();
		 testCase.addScreenShotCurrentStep(u, "control m inicio")*/
		 
		 
		// ***** PASO 16 *****
		  addStep("ejecutar la interfaz OS9_MX");
		/* boolean flag = controlM.searchJob();
		 assertFalse(!flag, "No se encontro el job");
		 System.out.println("Job Status " + controlM.getCurrentStatus());
		 //Ejecucion
		 String resultadoDelJob = controlM.executeJob();
		 testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob);
		 System.out.println("Resultado de la ejecuci�n " + resultadoDelJob);

		 //Valor del output
		 testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
		 System.out.println("Output " + controlM.getOutput());

		 //Validacion del caso
		 boolean testResult = true;
		 if (resultadoDelJob.equals("Failure")) {testResult = false;
		   testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
		   System.out.println("Fallo la ejecuci�n del job ");
		 }

			 assertTrue(testResult, "La ejecucion del job fue exitosa");
			 controlM.closeViewpoint();*/
			 
			 
		 // ***** PASO 17 *****
		 addStep("Realizar la conexión a la BD FCWMLQA Usuario: QAVIEW host:oxzvmwmiracq01.femcom.net puerto:1521 servicio: FCWMLQA.FEMCOM.NET");	 
		 //System.out.println("Paso 17: Conexi�n con BD FCWMLQA exitosa -> "+ BDWMLog.toString());
		 
		 // ***** PASO 18 *****
		 addStep("Validar que el término de la ejecución de la interface OS9 sea correcta en la tabla WM WM_LOG_RUN de WMLOG.");
		 
		 /*String query8 = "select * from WMLOG.wm_log_run where interface LIKE '%OS9%' ORDER BY RUN_ID DESC;" ;
		 SQLResult termino_ejecucion = bdWMLog.executeQuery(query7);
		 if (termino_ejecucion.getRowCount() > 0) {
			 System.out.println("correcto, la informacion se ingresa correctamente");
			 testCase.addQueryEvidenceCurrentStep(termino_ejecucion);
			 idBD = termino_ejecucion.getData(0, "S").toString();
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 */
		// ***** PASO 19 *****
		 addStep("Ingresar a la BD EBS México usuario: APPSVIEW host: oxfdbfinq00.femcom.net puerto: 1535 nombre servicio: AVEBQA esquema: APPS ");
		 //System.out.println("Paso 19: Conexi�n con BD AVEBQA exitosa -> "+ EBSMEX.toString());
		 
		// ***** PASO 20 *****
		 addStep("Validar que la información procesada quede registrada en la tabla de bitácora xxfc_gl_sap_headers_sent ");
		 /*String query9 = "select * from  wmuser.xxfc_gl_sap_headers_sent WHERE creation_date >= TRUNC(SYSDATE)";
		 SQLResult registrobitacora = EBSMEX.executeQuery(query9);
		 if (registrobitacora.getRowCount() > 0) {
			 System.out.println("correcto, La información se inserta correctamente en SAPUSER");
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		*/
		
	}
	
	@Override
	public String setTestFullName() {
	    return "ATC_FT_014_Ejecutar_la_interfaz_OS9_MX_Finiquitos";
	}
	
	@Override
	public String setTestDescription() {
	    return "Contar con el equipo de expertal para el envio de polizas (FINIQUITOS) desde sap HCM a ORACLE y  haber ejecutado las interfaces SW50, SO1, OS2";
	}
	
	@Override
	public String setTestDesigner() {
	    return "Mario de Jesus Salazar Rocha";
	}
	
	@Override
	public String setTestInstanceID() {
	    return null;
	}
	
	@Override
	public String setPrerequisites() {
	    return "Contar con el equipo de expertal para el envio de polizas Nominas y Finiquitos desde sap HCM";
	}
	
	@Override
	public void beforeTest() {
		
	}

}

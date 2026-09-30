package WM.interfaces.MARK_PRM;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.om.ENREDOUTV2;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_008_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenH extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_008_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenH_test(HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-008 Marcado de para todas las tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en H
		Desc:
		Prueba de componente nuevo, el equipo de desarrollo general el job nuevo MARK_PRM_CLOUD,
		que marca todas las tiendas con el campo MIGRATED_FLAG_OUT_CLOUD, en este caso tiene el valor: H (Hibrido)
		Si MIGRATED_FLAG_OUT_CLOUD=H el job MARK_PRM_CLOUD actualiza los campos WM_PRM_STATUS a L y CLOUD_PRM_STATUS a L
		@Valeria Fernandez
		@date   2024/10/31*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 mrk = new ENREDOUTV2(testCase);
		
		utils.sql.SQLUtil dbFCDASPFR = null;
		int maxAttempts = 5;
		int retryInterval = 1000; // 1 second
		
		//Reintenta conexion hasta 5 veces de dbFCDASPFR, porque es inestable su conexion
		 for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			 try {
				 dbFCDASPFR = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO,GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_DASPFR_MEX_MULTIFORMATO);
				 System.out.println("Conexion completada en el intento " + attempt); // Perform database operations here 
				 break; // Exit the loop if connection is successful 
				 } catch (Exception e) {
					 System.out.println("Conexion intento " + attempt + " fallado: " + e.getMessage()); 
					 if (attempt < maxAttempts) {
						 try {
							 Thread.sleep(retryInterval);
							 } 
						 catch (InterruptedException ie) {
							 Thread.currentThread().interrupt();
							 } 
						} else {
							System.out.println("Todos los intentos de conexion fallaron"); 
						} 
				 } 
			 }
		 
		utils.sql.SQLUtil dbAVEBQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA , GlobalVariables.DB_USER_AVEBQA ,GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLUtil dbFCWMESIT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit,GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
		utils.sql.SQLUtil dbFCWMLQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA02,GlobalVariables.DB_USER_FCWMLQA_WMLOG, GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG);
		
		//AVEBQA
		String queryRetekCR = "SELECT * FROM XXFC_MAESTRO_DE_CRS_V WHERE RETEK_CR='" + data.get("retek_cr") + "'";
		String queryRetek2CR = "SELECT * FROM XXFC_MAESTRO_DE_CRS_V WHERE RETEK_CR='" + data.get("retek_cr2") + "'";
		String queryRetek3CR = "SELECT * FROM XXFC_MAESTRO_DE_CRS_V WHERE RETEK_CR='" + data.get("retek_cr3") + "'";
		
		//FCWMESIT
		String queryFlag = "SELECT * FROM POSUSER.POS_RULES_DOC_CFG WHERE MIGRATED_FLAG_OUT_CLOUD = '" + data.get("flag_cloud") + "' AND DOC_TYPE='PRM' AND PV_CR_PLAZA = '" +data.get("plaza")+ "' AND PV_CR_TIENDA = '" +data.get("tienda")+ "'";
		String queryDeepDays = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG WHERE INTERFASE ='PR63_MX' AND OPERACION ='depthDays'";
		
		//FCDASPFR
		String queryStatusPRMHead_Antes = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_antes") + "' AND CLOUD_PRM_STATUS " + data.get("cloud_prm_status_antes") + " AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='" + data.get("batchId") + "'";
		String queryStatusPRMHead_Despues = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_despues") + "' AND CLOUD_PRM_STATUS ='" + data.get("cloud_prm_status_despues") + "' AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='" + data.get("batchId") + "'";
		String queryStatusPRMHead2_Antes = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_antes") + "' AND CLOUD_PRM_STATUS " + data.get("cloud_prm_status_antes") + " AND LOCATION='" + data.get("retek_cr2") + "' AND BATCH_ID='" + data.get("batchId2") + "'";
		String queryStatusPRMHead2_Despues = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_despues") + "' AND CLOUD_PRM_STATUS ='" + data.get("cloud_prm_status_despues") + "' AND LOCATION='" + data.get("retek_cr2") + "' AND BATCH_ID='" + data.get("batchId2") + "'";
		String queryStatusPRMHead3_Antes = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_antes") + "' AND CLOUD_PRM_STATUS " + data.get("cloud_prm_status_antes") + " AND LOCATION='" + data.get("retek_cr3") + "' AND BATCH_ID='" + data.get("batchId3") + "'";
		String queryStatusPRMHead3_Despues = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_despues") + "' AND CLOUD_PRM_STATUS ='" + data.get("cloud_prm_status_despues") + "' AND LOCATION='" + data.get("retek_cr3") + "' AND BATCH_ID='" + data.get("batchId3") + "'";
		
		//Valida estatus de distintos campos, segun sea usado
		boolean validaStatus = true;
		
		//***************************Paso 0*****************************************************************************
		 addStep("Ingresar a la BD AVEBQA");
		 
		 //Aca solo toma la evidencia, las conexiones las hace arriba en Utilerias
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_AVEBQA);
		 
		//Primera tienda
		 //Imprime la consulta que se ejecutara
		 System.out.println(queryRetekCR);
		 //Realiza la consulta con la conexion que se especifique (BD)
		 SQLResult queryRetekCR2 = dbAVEBQA.executeQuery(queryRetekCR);
		 //Extrae el valor del campo RETEK_CR de la consulta realizada
		 String retek = queryRetekCR2.getData(0, "RETEK_CR");
		 System.out.println("RETEK_CR: " + retek);
		 
		 //Si la consulta regresa vacio, marca error. En caso contrario captura la evidencia
		 boolean vacio = queryRetekCR2.isEmpty();
		 if (!vacio) {
				testCase.addQueryEvidenceCurrentStep(queryRetekCR2);
		 }
		 mrk.processQueryInChunks(dbAVEBQA, queryRetekCR);
		 
		assertFalse("No se muestra la informacion de la tienda.", vacio);
		
		//Segunda tienda
		 //Imprime la consulta que se ejecutara
		 System.out.println(queryRetek2CR);
		 //Realiza la consulta con la conexion que se especifique (BD)
		 SQLResult queryRetek2CR2 = dbAVEBQA.executeQuery(queryRetek2CR);
		 //Extrae el valor del campo RETEK_CR de la consulta realizada
		 String retek2 = queryRetek2CR2.getData(0, "RETEK_CR");
		 System.out.println("RETEK_CR: " + retek2);
		 
		 //Si la consulta regresa vacio, marca error. En caso contrario captura la evidencia
		 vacio = queryRetek2CR2.isEmpty();
		 if (!vacio) {
				testCase.addQueryEvidenceCurrentStep(queryRetek2CR2);
		 }
		 mrk.processQueryInChunks(dbAVEBQA, queryRetek2CR);
		 
		assertFalse("No se muestra la informacion de la tienda.", vacio);
		
		//Tercera tienda
		 //Imprime la consulta que se ejecutara
		 System.out.println(queryRetek3CR);
		 //Realiza la consulta con la conexion que se especifique (BD)
		 SQLResult queryRetek3CR2 = dbAVEBQA.executeQuery(queryRetek3CR);
		 //Extrae el valor del campo RETEK_CR de la consulta realizada
		 String retek3 = queryRetek3CR2.getData(0, "RETEK_CR");
		 System.out.println("RETEK_CR: " + retek3);
		 
		 //Si la consulta regresa vacio, marca error. En caso contrario captura la evidencia
		 vacio = queryRetek3CR2.isEmpty();
		 if (!vacio) {
				testCase.addQueryEvidenceCurrentStep(queryRetek3CR2);
		 }
		 mrk.processQueryInChunks(dbAVEBQA, queryRetek3CR);
		 
		assertFalse("No se muestra la informacion de la tienda.", vacio);
			
		//***************************Paso 1*****************************************************************************
		 addStep("Ingresar a la BD FCWMESIT");
	 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_fcwmesit);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_fcwmesit);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar configuracion tienda que se migraran a cloud con consulta: " + queryFlag);
		 System.out.println(queryFlag);
		 SQLResult queryFlag2 = dbFCWMESIT.executeQuery(queryFlag);
		 boolean paso2 = queryFlag2.isEmpty();
		 if (!paso2) {
				testCase.addQueryEvidenceCurrentStep(queryFlag2);
		 }
		 mrk.processQueryInChunks(dbFCWMESIT, queryFlag);
		 
		assertFalse("No se muestra la informacion de la tienda.", paso2);
		
		//***************************Paso 3*****************************************************************************
		 addStep("Validar dias de profundidad con la consulta: " + queryDeepDays);
		 System.out.println(queryDeepDays);
		 SQLResult queryDeepDays2 = dbFCWMESIT.executeQuery(queryDeepDays);
		 boolean paso3 = queryDeepDays2.isEmpty();
		 if (!paso3) {
				testCase.addQueryEvidenceCurrentStep(queryDeepDays2);
		 }
		 mrk.processQueryInChunks(dbFCWMESIT, queryDeepDays);
		 
		assertFalse("No se muestra la informacion de los dias de profundidad.", paso3);

		//***************************Paso 4*****************************************************************************
		addStep("Ingresar a la BD FCDASPFR");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		 
		//***************************Paso 5*****************************************************************************
		 addStep("Validar que la tienda tenga el valor L en la misma tienda que la tabla POS_PRM_HEAD: " + queryStatusPRMHead_Antes);
		 testCase.addBoldTextEvidenceCurrentStep(queryStatusPRMHead2_Antes);
		 testCase.addBoldTextEvidenceCurrentStep(queryStatusPRMHead3_Antes);
		 
		 //Para primera tienda
		 System.out.println(queryStatusPRMHead_Antes);
		 SQLResult queryStatusPRMHead_Antes2 = dbFCDASPFR.executeQuery(queryStatusPRMHead_Antes);
		 String wm_prm_status = queryStatusPRMHead_Antes2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 String cloud_prm_status = queryStatusPRMHead_Antes2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 boolean paso5 = queryStatusPRMHead_Antes2.isEmpty();
		 if (!paso5) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead_Antes2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead_Antes);
		 
		assertFalse("No se muestra el estatus esperado.", paso5);
		
		//Para segunda tienda
		System.out.println(queryStatusPRMHead2_Antes);
		 SQLResult queryStatusPRMHead2_Antes2 = dbFCDASPFR.executeQuery(queryStatusPRMHead2_Antes);
		 wm_prm_status = queryStatusPRMHead2_Antes2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 cloud_prm_status = queryStatusPRMHead2_Antes2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 paso5 = queryStatusPRMHead2_Antes2.isEmpty();
		 if (!paso5) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead2_Antes2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead2_Antes);
		 
		assertFalse("No se muestra el estatus esperado.", paso5);
		
		//Para tercera tienda
		System.out.println(queryStatusPRMHead3_Antes);
		 SQLResult queryStatusPRMHead3_Antes2 = dbFCDASPFR.executeQuery(queryStatusPRMHead3_Antes);
		 wm_prm_status = queryStatusPRMHead3_Antes2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 cloud_prm_status = queryStatusPRMHead3_Antes2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 paso5 = queryStatusPRMHead3_Antes2.isEmpty();
		 if (!paso5) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead3_Antes2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead3_Antes);
		 
		assertFalse("No se muestra el estatus esperado.", paso5);
			
		//***************************Paso 6*****************************************************************************
		addStep("Ingresar a Control M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		JSONObject obj = new JSONObject(data.get("job"));
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "Login");
		
		//***************************Paso 7*****************************************************************************
		addStep("Ejecutar job runMarkDocCloudPRM_MX");
		
		//Buscar el job
		ControlM control = new ControlM(u, testCase, obj);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");	
		u.hardWait(50);
		boolean flag = control.searchJob();
		u.hardWait(50);
		assertFalse(!flag, "No se encontro el job");
		
		//Ejecucion
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);
		
		//Obtener status 
		String statusEjecucion = control.getNewStatus();
		System.out.println ("Valor de output getNewStatus:" +statusEjecucion);
		
		//Obtener output
		String output = control.getOutput();
		System.out.println ("Valor de output control:" +output);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+statusEjecucion);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
		
		//Validacion de la ejecucion del job
		Boolean casoPasado = false;
		
		if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")) {
		casoPasado = true;
		}		
		
		control.closeViewpoint();
		assertTrue(casoPasado, "No termino correctamente");
		u.close();

		//***************************Paso 8*****************************************************************************
		addStep("Validar que las tiendas migradas se hayan actualizado con consulta: " + queryStatusPRMHead_Despues);
		testCase.addBoldTextEvidenceCurrentStep(queryStatusPRMHead2_Despues);
		testCase.addBoldTextEvidenceCurrentStep(queryStatusPRMHead3_Despues);
		
		//Para primera tienda
		System.out.println(queryStatusPRMHead_Despues);
		 SQLResult queryStatusPRMHead_Despues2 = dbFCDASPFR.executeQuery(queryStatusPRMHead_Despues);
		 wm_prm_status = queryStatusPRMHead_Despues2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 cloud_prm_status = queryStatusPRMHead_Despues2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 boolean paso8 = queryStatusPRMHead_Despues2.isEmpty();
		 if (!paso8) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead_Despues2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead_Despues);
		 
		assertFalse("No se muestra el estatus esperado.", paso8);
		if(!wm_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		
		if(!cloud_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
		//Para segunda tienda
		System.out.println(queryStatusPRMHead2_Despues);
		 SQLResult queryStatusPRMHead2_Despues2 = dbFCDASPFR.executeQuery(queryStatusPRMHead2_Despues);
		 wm_prm_status = queryStatusPRMHead2_Despues2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 cloud_prm_status = queryStatusPRMHead2_Despues2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 paso8 = queryStatusPRMHead2_Despues2.isEmpty();
		 if (!paso8) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead2_Despues2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead2_Despues);
		 
		assertFalse("No se muestra el estatus esperado.", paso8);
		if(!wm_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		
		if(!cloud_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
			
		//Para tercera tienda
		System.out.println(queryStatusPRMHead3_Despues);
		 SQLResult queryStatusPRMHead3_Despues2 = dbFCDASPFR.executeQuery(queryStatusPRMHead3_Despues);
		 wm_prm_status = queryStatusPRMHead3_Despues2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 cloud_prm_status = queryStatusPRMHead3_Despues2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 paso8 = queryStatusPRMHead3_Despues2.isEmpty();
		 if (!paso8) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead3_Despues2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead3_Despues);
		 
		assertFalse("No se muestra el estatus esperado.", paso8);
		if(!wm_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		
		if(!cloud_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
		//***************************Paso 9*****************************************************************************
		addStep("Ingresar a la BD FCWMLQA");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA02);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMLQA);
		
		//***************************Paso 10*****************************************************************************
		String queryLogRun = "SELECT * FROM WMLOG.WM_LOG_RUN WHERE INTERFACE='" +data.get("interface")+ "' AND START_DT>= SYSDATE - INTERVAL '" +data.get("minutos")+ "' MINUTE AND STATUS='" +data.get("statusInterface")+ "' ORDER BY START_DT DESC";
		addStep("Ejecutar la siguiente consulta en la base de datos FCWMLQA para validar que no se encuentren registros de error: " + queryLogRun);
		System.out.println(queryLogRun);
		SQLResult queryLogRun2 = dbFCWMLQA.executeQuery(queryLogRun);
		String statusEjecucionLog = queryLogRun2.getData(0, "STATUS");
		String runIdInteface = queryLogRun2.getData(0, "RUN_ID");
		System.out.println(runIdInteface);
		boolean paso10 = queryLogRun2.isEmpty();
		if (!paso10) {
				testCase.addQueryEvidenceCurrentStep(queryLogRun2);
		 }
		mrk.processQueryInChunks(dbFCWMLQA, queryLogRun);
		
		assertFalse("No se muestra la informacion de la ejecucion", paso10);
		if(!statusEjecucionLog.equals("S")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
		//***************************Paso 11*****************************************************************************
		String queryLogError = "SELECT * FROM WMLOG.WM_LOG_ERROR WHERE RUN_ID='"+ runIdInteface +"' AND ERROR_DATE>= SYSDATE - INTERVAL '" +data.get("minutos")+ "' MINUTE ORDER BY ERROR_DATE DESC";
		addStep("Ejecutar la siguiente consulta en la base de datos FCWMLQA para validar que no se encuentren registros de error: " + queryLogError);
		System.out.println(queryLogError);
		SQLResult queryLogError2 = dbFCWMLQA.executeQuery(queryLogError);
		 boolean paso11 = queryLogError2.isEmpty();
		 if (paso11) {
				testCase.addQueryEvidenceCurrentStep(queryLogError2);
		 }
		 mrk.processQueryInChunks(dbFCWMLQA, queryLogError);
		 
		 assertTrue(paso11, "Se encontro algun registro de error");
		 
		}

	@Override
	public String setTestFullName() {
		return "MTC-FT-008 Marcado de para todas las tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en H";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Marcado de todas las tiendas migradas y no migradas a la nube con el job runMarkDocCloudPRM_MX, el campo MIGRATED_FLAG_OUT_CLOUD debe tener el valor H";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Valeria Fernandez";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Acceso a BD FCWMESIT, FCDASPFR y FCWMLQA\r\n" +
				"2. Acceso control M\r\n" +
				"3. Registro existente en tabla POS_RULES_DOC_CFG con atributos:\r\n" +
				"-MIGRATED_FLAG_OUT_CLOUD = H\r\n" +
				"-PV_CR_PLAZA = * AND PV_CR_TIENDA = *\r\n" +
				"-DOC_TYPE=PRM\r\n" +
				"4. Tres registros existentes en tabla POS_PRM_HEAD con atributos: WM_PRM_STATUS = L y CLOUD_PRM_STATUS = NULL\r\n" +
				"Un registro con atributos:" +
				"-LOCATION=1070\r\n" +
				"Un registro con atributos:" +
				"-LOCATION=141\r\n" +
				"Un registro con atributos:" +
				"-LOCATION=453\r\n" +
				"5. Corroborar que cada registro su tienda y plaza sean la misma en la BD AVEBQA vista XXFC_MAESTRO_DE_CRS_V:\r\n" +
				"Un registro con atributos:" +
				"-RETEK_CR=1070\r\n" +
				"Un registro con atributos:" +
				"-RETEK_CR=141\r\n" +
				"Un registro con atributos:" +
				"-RETEK_CR=453\r\n" +
				"6. El job MARK_PRM_CLOUD debe estar ordenado (haberlo solicitado por correo):\r\n" +
				"Job Name: MARK_PRM\r\n" +
				"Command Line: runMarkPRMCloud_MX.sh\r\n" +
				"Run As: brmsdas\r\n" +
				"Host: tqa_clu_dasperf\r\n" +
				"Folder Name: PERF_PROM_16\r\n";

	}
}

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

public class ATC_014_MARKPRM_ValidarExistenciaJobCamposBD extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_014_MARKPRM_ValidarExistenciaJobCamposBD_test(HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-014 Validar existencia de job MARK_PRM_CLOUD, campos y paquetes nuevos en BD
		Desc:
		Prueba de componente nuevo, el equipo de desarrollo general el job nuevo MARK_PRM_CLOUD,
		que marca las tiendas con el campo MIGRATED_FLAG_OUT_CLOUD, en este caso debe validar que
		en Control M exista y este ordenado el job MARK_PRM_CLOUD.
		Tambien verifica los campos nuevos creados en las BD FCWMESIT y FCDASPFR
		@Valeria Fernandez
		@date   2024/10/31*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 mrk = new ENREDOUTV2(testCase);
		
		utils.sql.SQLUtil dbFCDASPFR = null;
		int maxAttempts = 5;
		int retryInterval = 1000; // 1 second
		
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
		utils.sql.SQLUtil dbFCWMESIT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit,GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
		
		//FCWMESIT
		String queryFlagCloud = "SELECT COUNT(*) FROM all_tab_columns WHERE table_name = 'POS_RULES_DOC_CFG' AND column_name = 'MIGRATED_FLAG_OUT_CLOUD'";
		String queryDeepDays = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG WHERE INTERFASE ='PR63_MX' AND OPERACION ='depthDays'";
		
		//FCDASPFR
		String queryStatusCloud = "SELECT COUNT(*) FROM all_tab_columns WHERE table_name = 'POS_PRM_HEAD' AND column_name = 'CLOUD_PRM_STATUS'";
		String queryPaqueteCloud = "SELECT owner, object_name, object_type FROM dba_objects WHERE object_type = 'PACKAGE' AND object_name = 'MARK_DOC_CLOUD'";
		
		//Valida estatus de distintos campos, segun sea usado
		boolean validaStatus = true;
		
		//***************************Paso 1*****************************************************************************
		 addStep("Ingresar a la BD FCWMESIT");
	 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Confirmar que existe el campo MIGRATED_FLAG_OUT_CLOUD con consulta: " + queryFlagCloud);
		 System.out.println(queryFlagCloud);
		 SQLResult queryFlagCloud2 = dbFCWMESIT.executeQuery(queryFlagCloud);
		 String flagCloud = queryFlagCloud2.getData(0, "COUNT(*)");
		 System.out.println(flagCloud);
		 boolean paso2 = queryFlagCloud2.isEmpty();
		 if (!paso2) {
				testCase.addQueryEvidenceCurrentStep(queryFlagCloud2);
		 }
		 mrk.processQueryInChunks(dbFCWMESIT, queryFlagCloud);
		 
		assertFalse("Campo en BD no existente", paso2);
		if(!flagCloud.equals("1")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("Los dias de profundidad no fueron respetados");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
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
		 addStep("Confirmar que existe el campo CLOUD_PRM_STATUS con consulta: " + queryStatusCloud);
		 System.out.println(queryStatusCloud);
		 SQLResult queryStatusCloud2 = dbFCDASPFR.executeQuery(queryStatusCloud);
		 String statusCloud = queryStatusCloud2.getData(0, "COUNT(*)");
		 System.out.println(statusCloud);
		 boolean paso5 = queryStatusCloud2.isEmpty();
		 if (!paso5) {
				testCase.addQueryEvidenceCurrentStep(queryStatusCloud2);
		 }
		 
		assertFalse("Campo en BD no existente", paso5);
		if(!statusCloud.equals("1")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("Los dias de profundidad no fueron respetados");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
			
		//***************************Paso 6*****************************************************************************
		
		addStep("Confirmar que en paquetes exista uno llamado MARK_PRM_CLOUD con el esquema WMUSER" + queryPaqueteCloud);
		 System.out.println(queryPaqueteCloud);
		 SQLResult queryPaqueteCloud2 = dbFCDASPFR.executeQuery(queryPaqueteCloud);
		 boolean paso6 = queryPaqueteCloud2.isEmpty();
		 if (!paso6) {
				testCase.addQueryEvidenceCurrentStep(queryPaqueteCloud2);
		 }
		 
		assertFalse("Paquete en BD no existente", paso6);
		
		//***************************Paso 7*****************************************************************************
		addStep("Ingresar a Control M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		JSONObject obj = new JSONObject(data.get("job"));
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "Login");
		
		//***************************Paso 8*****************************************************************************
		addStep("Confirmar que existe el job MARK_PRM_CLOUD");
		
		//Buscar el job
		ControlM control = new ControlM(u, testCase, obj);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");	
		u.hardWait(50);
		boolean flag = control.searchJob();
		u.hardWait(50);
		assertFalse(!flag, "No se encontro el job");
		
		//***************************Paso 9*****************************************************************************
		addStep("Ejecutar el job: runMarkDocCloudPRM_MX , con el comando: runMarkPRMCloud_MX.sh en el host: oxdasperf02");
		
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
		
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-014 Validar existencia de job MARK_PRM_CLOUD, campos y paquetes nuevos en BD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validacion de existencia de campos nuevos: \r\n"
				+ "BD FCWMESIT:\r\n"
				+ "MIGRATED_FLAG_OUT_CLOUD\r\n"
				+ "\r\n"
				+ "BD FCDASPFR:\r\n"
				+ "CLOUD_PRM_STATUS";
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
		"3. El job MARK_PRM_CLOUD debe estar ordenado (haberlo solicitado por correo)\r\n" +
		"Job Name: MARK_PRM\r\n" +
		"Command Line: runMarkPRMCloud_MX.sh\r\n" +
		"Run As: brmsdas\r\n" +
		"Host: oxvwqbosq03\r\n" +
		"Folder Name: PERF_PROM_16\r\n" +
		"4. Existencia de campos nuevos:\r\n" +
		"BD FCWMESIT:\r\n" +
		"MIGRATED_FLAG_OUT_CLOUD\r\n" +
		"BD FCDASPFR:\r\n" +
		"CLOUD_PRM_STATUS\r\n";

	}
}

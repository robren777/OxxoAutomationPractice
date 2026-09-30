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

public class ATC_013_MARKPRM_VerificarProfundidadDatos extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_013_MARKPRM_VerificarProfundidadDatos_test(HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-013 Verificar profundidad de datos para job MARK_PRM_CLOUD
		Desc:
		Prueba de componente nuevo, el equipo de desarrollo general el job nuevo MARK_PRM_CLOUD,
		que marca las tiendas con el campo MIGRATED_FLAG_OUT_CLOUD, en este caso se verifica la profundidad que determina
		la cantidad de dias que abarca el job (campo depthDays)
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
		
		//AVEBQA
		String queryRetekCR = "SELECT * FROM XXFC_MAESTRO_DE_CRS_V WHERE RETEK_CR='" + data.get("retek_cr") + "'";
		
		//FCWMESIT
		String queryFlag = "SELECT * FROM POSUSER.POS_RULES_DOC_CFG WHERE MIGRATED_FLAG_OUT_CLOUD = '" + data.get("flag_cloud") + "' AND DOC_TYPE='PRM' AND PV_CR_PLAZA = '" +data.get("plaza")+ "' AND PV_CR_TIENDA = '" +data.get("tienda")+ "'";
		String queryDeepDays = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG WHERE INTERFASE ='PR63_MX' AND OPERACION ='depthDays'";
		
		//FCDASPFR
		String queryStatusPRMHead = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status") + "' AND CLOUD_PRM_STATUS " + data.get("cloud_prm_status") + " AND LOCATION='" + data.get("retek_cr") + "' AND LOAD_DATE <=SYSDATE-30";
		
		//Valida estatus de distintos campos, segun sea usado
		boolean validaStatus = true;
		
		//***************************Paso 0*****************************************************************************
		 addStep("Ingresar a la BD AVEBQA");
		 
		 //Aca solo toma la evidencia, las conexiones las hace arriba en Utilerias
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_AVEBQA);
		 
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
		
		String deepDays = queryDeepDays2.getData(0, "VALOR1");
		System.out.println(deepDays);
		int deepDaysInt = Integer.parseInt(deepDays);
		if (deepDaysInt < 1 || deepDaysInt > 10) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("Los dias de profundidad no fueron respetados");
		}

		//***************************Paso 4*****************************************************************************
		addStep("Ingresar a la BD FCDASPFR");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		 
		//***************************Paso 5*****************************************************************************
		 addStep("Validar que la tienda tenga el valor L en la misma tienda que la tabla POS_PRM_HEAD: " + queryStatusPRMHead);
		 System.out.println(queryStatusPRMHead);
		 SQLResult queryStatusPRMHead2 = dbFCDASPFR.executeQuery(queryStatusPRMHead);
		 String wm_prm_status = queryStatusPRMHead2.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 String cloud_prm_status = queryStatusPRMHead2.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 String load_date = queryStatusPRMHead2.getData(0, "LOAD_DATE");
		 System.out.println("LOAD_DATE: " + load_date);
		 boolean paso5 = queryStatusPRMHead2.isEmpty();
		 if (!paso5) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead2);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead);
		 
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
		addStep("Ejecutar job MARK_PRM_CLOUD");
		
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
		addStep("Validar que las tiendas migradas se hayan actualizado con consulta: " + queryStatusPRMHead);
		System.out.println(queryStatusPRMHead);
		 SQLResult queryStatusPRMHead3 = dbFCDASPFR.executeQuery(queryStatusPRMHead);
		 wm_prm_status = queryStatusPRMHead3.getData(0, "WM_PRM_STATUS");
		 System.out.println("WM_PRM_STATUS: " + wm_prm_status);
		 cloud_prm_status = queryStatusPRMHead3.getData(0, "CLOUD_PRM_STATUS");
		 System.out.println("CLOUD_PRM_STATUS: " + cloud_prm_status);
		 boolean paso8 = queryStatusPRMHead3.isEmpty();
		 if (!paso8) {
				testCase.addQueryEvidenceCurrentStep(queryStatusPRMHead3);
		 }
		 mrk.processQueryInChunks(dbFCDASPFR, queryStatusPRMHead);
		 
		assertFalse("No se muestra el estatus esperado.", paso8);
		if(!wm_prm_status.equals("L")) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("Los dias de profundidad no fueron respetados");
		}
		
		if(cloud_prm_status != null) {
			validaStatus = false;
			testCase.addTextEvidenceCurrentStep("Los dias de profundidad no fueron respetados");
		}
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		 
		}

	@Override
	public String setTestFullName() {
		return "MTC-FT-013 Verificar profundidad de datos para job MARK_PRM_CLOUD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que la profundidad de datos sea de segun la configuracion de deepdays, al actualizar en tiendas migradas a cloud. Registros anteriores a esa profundidad no se veran afectados. ";
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
				"-MIGRATED_FLAG_OUT_CLOUD = C\r\n" +
				"-PV_CR_PLAZA = 10VCZ AND PV_CR_TIENDA = 50CTQ\r\n" +
				"-DOC_TYPE=PRM\r\n" +
				"4. Registro existente en tabla POS_PRM_HEAD con atributos:\r\n" +
				"-LOCATION=10042\r\n" +
				"-WM_PRM_STATUS = L\r\n" +
				"-CLOUD_PRM_STATUS = NULL\r\n" +
				"5. Corroborar que la tienda y plaza sean la misma en la BD AVEBQA vista XXFC_MAESTRO_DE_CRS_V con el atributo:\r\n" +
				"-RETEK_CR=10042\r\n" +
				"6. El job MARK_PRM_CLOUD debe estar ordenado (haberlo solicitado por correo):\r\n" +
				"Job Name: MARK_PRM\r\n" +
				"Command Line: runMarkPRMCloud_MX.sh\r\n" +
				"Run As: brmsdas\r\n" +
				"Host: tqa_clu_dasperf\r\n" +
				"Folder Name: PERF_PROM_16\r\n";

	}
}

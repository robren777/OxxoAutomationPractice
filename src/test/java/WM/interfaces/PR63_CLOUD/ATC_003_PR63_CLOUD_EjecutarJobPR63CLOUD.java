package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.HashMap;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.azure.core.credential.TokenCredential;
import com.azure.monitor.query.models.QueryTimeInterval;

import WM.interfaces.MARK_PRM.ATC_001_MARKPRM_MarcadoTiendasMIGRATEDFLAGOUTCLOUDenC;
import WM.om.PR63_CLOUD;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_003_PR63_CLOUD_EjecutarJobPR63CLOUD extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	public void ATC_003_PR63_CLOUD_EjecutarJobPR63CLOUD_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-003 Ejecutar Job PR63_CLOUD
		Desc:
		Prueba de componente para verificar que el job PR63_CLOUD funcione correctamente, en la tabla PRM_HEAD
		debe cambiar el campo wm_prm_status a E del registro
		@Valeria Fernandez
		@date   2024/12/13*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
        
        //MDB_ERDD_PromotionCloud
		String queryStatusDigitalY = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("plaza")+ "' AND STORE='" +data.get("tienda")+ "' AND DIGITAL='" +data.get("digital")+ "'";
        
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
		
		String queryPRMHead_PR63CLOUD = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_pr63") + "' AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='" + data.get("batchId") + "'";
		
		String API_StartCopy = data.get("API_StartCopy");
        String Path_StartCopy = data.get("Path_StartCopy");
        String fullUrl = API_StartCopy + Path_StartCopy;
        String MockAPI = data.get("MockAPI");
        int timeout_PR63_CLOUD = Integer.parseInt(data.get("timeout_PR63_CLOUD"));
        
		By btnViewpoints = By.xpath("//span[text()='Viewpoints']");
		boolean validaStatus = false;
		boolean resultado2;
		
        //***************************Paso 1*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea digital, con la consulta: " + queryStatusDigitalY);
		
		 validaStatus = tr.queryBDAzure(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 tr.getValueCampoQuery(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
		 
		//***************************Paso 3*****************************************************************************
		 addStep("Ejecutar de la matriz SIGA_Script de Pruebas_MARK_PRM el caso de prueba @MTC-FT-001 Marcado de tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en C\r\n"
		 + "Nota: La plaza y tienda del caso a ejecutar deben ser 10MAN y 507VH respectivamente");
			
		ATC_001_MARKPRM_MarcadoTiendasMIGRATEDFLAGOUTCLOUDenC atc001_mark_prm = new ATC_001_MARKPRM_MarcadoTiendasMIGRATEDFLAGOUTCLOUDenC();
		atc001_mark_prm.setTestCase(this.testCase);
		atc001_mark_prm.ATC_001_MARKPRM_MarcadoTiendasMIGRATEDFLAGOUTCLOUDenC_test(data);
		
		//***************************Paso 4*****************************************************************************
		addStep("Validar este ordenado el job runMarkDocCloudPRM_MX (debe estar antes de los jobs PR63)");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		JSONObject obj1 = new JSONObject(data.get("job"));
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "Login");
		
		//Buscar el job
		ControlM control = new ControlM(u, testCase, obj1);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		u.hardWait(10);
		boolean flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");
		
		//***************************Paso 5*****************************************************************************
		addStep("Ejecutar job PR63_CLOUD\r\n"
				+ "Nota: En paralelo esta ordenado el job PR63_MX");
		if (MockAPI.equals("Y")) {
			resultado2 = tr.runPR63_CLOUD(MockAPI, API_StartCopy, fullUrl);
			assertTrue("La API WF_PRM_StartCopy no respondió exitosamente", resultado2);
		}else {
			JSONObject obj2 = new JSONObject(data.get("job2"));
			//Buscar el job
			control = new ControlM(u, testCase, obj2);
			testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
			u.hardWait(10);
			u.click(btnViewpoints);
			flag = control.searchJob();
			u.hardWait(10);
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
			boolean casoPasado = false;
			
			if(statusEjecucion.equals("Ended OK") || statusEjecucion.equals("Wait for Event")) {
			casoPasado = true;
			}		
			
			assertTrue(casoPasado, "No termino correctamente");
		}
		
		Thread.sleep(timeout_PR63_CLOUD);
		
		u.close();
		
		OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime); 
		
		//***************************Paso 6*****************************************************************************
		addStep("Ingresar a la BD FCDASPFR");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		 
		//***************************Paso 7*****************************************************************************
		addStep("Verificar que se haya modificado el CLOUD_PRM_STATUS=E en la tabla POS_RPM_HEAD de la BD FCDASPFR con la consulta: " + queryPRMHead_PR63CLOUD);
		
		System.out.println(queryPRMHead_PR63CLOUD);
			
		validaStatus = tr.queryOracle(queryPRMHead_PR63CLOUD, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryPRMHead_PR63CLOUD);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String cloud_prm_status = tr.getValueCampoOracle(queryPRMHead_PR63CLOUD, dbFCDASPFR, "CLOUD_PRM_STATUS");
		validaStatus = false;
				
		if(cloud_prm_status.equals("C")) {
			validaStatus = true;
		 }
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
		//***************************Paso 6*****************************************************************************
		 addStep("Ingresar a Log de Errores de Azure");
			
		 String clientId = data.get("clientId");
		 String clientSecret = data.get("clientSecret");
		 String tenantId = data.get("tenantId");
		 String workspaceId = data.get("workspaceId");
		
		 TokenCredential tokenCredential = tr.loginLogsAzure(clientId, tenantId, clientSecret);

		//***************************Paso 7*****************************************************************************
		addStep("Validar que se registro la ejecucion en la tabla de log: ");

		validaStatus = tr.consultaLogsAzure(tokenCredential, workspaceId, timeInterval);
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-003 Ejecutar Job PR63_CLOUD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecutar job PR63_CLOUD para confirmar que este disponible y funcionando";
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
		return "1. Acceso a la BD: FCDASPFR\r\n"
				+ "2. Acceso a la BD de Azure: MDB_ERDD_PromotionCloud\r\n"
				+ "3. Acceso al aplicativo Control M\r\n"
				+ "4. Tener ordenados los jobs en Control M, el listado con sus datos esta en la pestania Cadena Jobs de esta matriz. (solicitado por correo)\r\n"
				+ "5. Tener acceso a las matrices de prueba de runMarkDocCloudPRM_MX y ENRED OUT V2 con sus correspondientes componentes de QA\r\n"
				+ "6. Contar con acceso a Logs de ejecucion de Azure\r\n"
				+ "7. Tener un registro de tienda digital\r\n"
				+ "8. Tener acceso y activar el recurso de azure OXZRGERDDQ01";
	
	}
}
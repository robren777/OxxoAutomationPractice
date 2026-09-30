package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.azure.core.credential.TokenCredential;
import com.azure.monitor.query.models.QueryTimeInterval;

import WM.interfaces.ENRED_OUT_V2.ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud;
import WM.interfaces.MARK_PRM.ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC;
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

public class ATC_016_PR63_CLOUD_EnvioPromocionComplejaTiendaDigitalAutomatico extends BaseExecution {
	@Test
	(dataProvider = "data-provider")

	public void ATC_016_PR63_CLOUD_EnvioPromocionComplejaTiendaDigitalAutomatico_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-016 Envio de promocion compleja a tienda digital cloud-automatico
		Desc:
		Prueba de flujo E2E del flujo de promociones con su envio y repecion de documentos,
		se desarrollaron los siguientes componentes: job MARK_PRM, job PR63_CLOUD y API ENRED_OUT_V2
		Dependiendo de la tienda (digital cloud, fisica cloud y onpremise) el archivo se va a FTP (Filezilla) o Azure Storage.
		Este flujo prueba los componentes job MARK_PRM, job PR63_MX y API ENRED_OUT_V2 y el envio de una promocion en RMS16,
		a modo de regresion de promociones a tiendas onpremises.
		@Valeria Fernandez
		@date   2024/12/26*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
		
		utils.sql.SQLUtil dbFCDASPFR = tr.reintentarConexion();
		utils.sql.SQLUtil dbFCWM6QA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		
		//MDB_ERDD_PromotionCloud
		String queryGetBatchId = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("retek_cr")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_STATUS IS NULL AND PV_DOC_TYPE='" +data.get("doctype")+ "' ORDER BY CREATED_DATE DESC";
		String queryGetBatchId2 = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("retek_cr2")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_STATUS IS NULL AND PV_DOC_TYPE='" +data.get("doctype")+ "' ORDER BY CREATED_DATE DESC";

        //Nota: El valor de nombrePromoN debe ser el mismo que el nombre de la promo en el archivo ods, este archivo esta en la ruta "file"
		String queryDASPromo_Head1 = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE LOCATION = '" +data.get("retek_cr")+ "' AND WM_PRM_STATUS = '" +data.get("wm_prm_status_antes")+ "' AND CLOUD_PRM_STATUS " +data.get("cloud_prm_status_antes")+ " ORDER BY LOAD_DATE DESC";
		String queryDASPromo_Head2 = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE LOCATION = '" +data.get("retek_cr2")+ "' AND WM_PRM_STATUS = '" +data.get("wm_prm_status_antes")+ "' AND CLOUD_PRM_STATUS " +data.get("cloud_prm_status_antes")+ " ORDER BY LOAD_DATE DESC";
		String queryStatusPRMHeadPr63Cloud2 = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS = '" + data.get("wm_prm_status_despues") + "' AND CLOUD_PRM_STATUS='" + data.get("cloud_prm_status_pr63") + "' AND LOCATION='" + data.get("retek_cr") + "' AND BATCH_ID='batchIdValor'";
		String queryStatusPRMHeadTemp2 = "select * from XXSC_DOCS.POS_PRM_HEAD_TMP WHERE BATCH_ID='batchIdValor'";
		
		//FCWMESIT
		String querySimotiOutDoc1 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND ID='" + data.get("id") + "'";
		String querySimotiOutDoc2 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId2")+ "' AND PV_CR_TIENDA = '" +data.get("storeId2")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND ID='" + data.get("id2") + "'";
  			
  		//Azure Storage
  		String azureConnectionString = data.get("azureConnectionString");
  	    String azureContainerName = data.get("azureContainerName");
  		
        int timeout_PR63_CLOUD = Integer.parseInt(data.get("timeout_PR63_CLOUD"));
  		boolean validaStatus = true;

		//***************************Paso 1*****************************************************************************
		addStep("Verificar que la informacion de la promocion tienda 50YDY plaza 10VCZ llego a la tabla POS_PRM_HEAD en DAS lista para que la tome la PR63_CLOUD: " + queryDASPromo_Head1);
		
		validaStatus = tr.queryOracle(queryDASPromo_Head1, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDASPromo_Head1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String batchIdQuery1 = tr.getValueCampoOracle(queryDASPromo_Head1, dbFCDASPFR, "BATCH_ID");

		//Obtiene batch id de BD azure cloud erdd
		validaStatus = tr.queryBDAzure(queryGetBatchId, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String idQuery1 = tr.getValueCampoQuery(queryGetBatchId, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "BATCH_ID");
				
		//***************************Paso 2*****************************************************************************
		addStep("Verificar que la informacion de la promocion tienda 50B87 plaza 10TLC llego a la tabla POS_PRM_HEAD en DAS lista para que la tome la PR63_CLOUD: " + queryDASPromo_Head2);

		validaStatus = tr.queryOracle(queryDASPromo_Head2, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryDASPromo_Head2);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String batchIdQuery2 = tr.getValueCampoOracle(queryDASPromo_Head2, dbFCDASPFR, "BATCH_ID");
		
		//***************************Paso 3*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_MARK_PRM el caso de prueba @MTC-FT-001 Marcado de tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en C\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");

		data.put("plaza", "*");
		data.put("tienda", "*");
		data.put("batchId", batchIdQuery1);
		data.put("batchId2", batchIdQuery2);
		data.put("batchId3", batchIdQuery1);
		
		ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC atc007_mark_prm = new ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC();
		atc007_mark_prm.setTestCase(this.testCase);
		atc007_mark_prm.ATC_007_MARKPRM_MarcadoTodasTiendasMIGRATEDFLAGOUTCLOUDenC_test(data);

		data.put("plaza", "10VCZ");
		data.put("tienda", "50YDY");
		data.put("location", "11524");
		data.put("id", idQuery1);
		
		//***************************Paso 4*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_MARK_PRM el caso de prueba @MTC-FT-001 Marcado de tiendas MARK_PRM_CLOUD campo MIGRATED_FLAG_OUT_CLOUD en C\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10TLC y 50B87 respectivamente");
		
		//Se ejecuta en conjunto al caso 007 del paso anterior
		testCase.addTextEvidenceCurrentStep("Se ejecuta en conjunto al caso 007 del paso anterior");
		
		//***************************Paso 5*****************************************************************************
		addStep("Esperar que se ejecuta automaticamente de acuerdo a como se encuentra configurado y programado");
		
		Thread.sleep(timeout_PR63_CLOUD);
		
		OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);
	    
	    //***************************Paso 6*****************************************************************************
	  	addStep("Esperar que se ejecuta automaticamente de acuerdo a como se encuentra configurado y programado");
	  	
	  	SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		JSONObject obj2 = new JSONObject(data.get("runPR63_CLOUD"));
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "Login");
		
		//Buscar el job
		ControlM control = new ControlM(u, testCase, obj2);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");	
		u.hardWait(50);
		boolean flag = control.searchJob();
		u.hardWait(10);
		assertFalse(!flag, "No se encontro el job");
		
		String resultado = control.getCurrentStatus();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+resultado);
		
		//Validacion de la ejecucion del job
		Boolean casoPasado = false;

		if(resultado.equals("Ended OK") || resultado.equals("Wait for Event")) {
		    casoPasado = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "Status de ejecucion");

		assertTrue(casoPasado, "No termino correctamente");
		
		u.close();

		//***************************Paso 7*****************************************************************************
		String queryStatusPRMHeadPr63Cloud=queryStatusPRMHeadPr63Cloud2.replace("batchIdValor", batchIdQuery1);
		String queryStatusPRMHeadTemp=queryStatusPRMHeadTemp2.replace("batchIdValor", batchIdQuery1);
		
		addStep("Verificar que se haya modificado el WM_PRM_STATUS=E en la tabla POS_PRM_HEAD de la BD FCDASPFR con la consulta: " + queryStatusPRMHeadPr63Cloud);
		
		validaStatus = tr.queryOracle(queryStatusPRMHeadPr63Cloud, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryStatusPRMHeadPr63Cloud);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		validaStatus = tr.queryOracle(queryStatusPRMHeadTemp, dbFCDASPFR);
		tr.processQueryInChunks(dbFCDASPFR, queryStatusPRMHeadTemp);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String cloud_prm_status = tr.getValueCampoOracle(queryStatusPRMHeadPr63Cloud, dbFCDASPFR, "CLOUD_PRM_STATUS");

		if(cloud_prm_status.equals("C")) {
			validaStatus = false;
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
		 
		//***************************Paso 8*****************************************************************************
		addStep("Ingresar a Log de Errores de Azure");
		
		 String clientId = data.get("clientId");
		 String clientSecret = data.get("clientSecret");
		 String tenantId = data.get("tenantId");
		 String workspaceId = data.get("workspaceId");
		
		 TokenCredential tokenCredential = tr.loginLogsAzure(clientId, tenantId, clientSecret);

		//***************************Paso 9*****************************************************************************
		addStep("Validar que se registro la ejecucion en la tabla de log: ");

		validaStatus = tr.consultaLogsAzure(tokenCredential, workspaceId, timeInterval);
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
		
       //***************************Paso 10*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_010 Validar Api V2 ACK Estatus P WmCode 101-Tienda digital cloud\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");
		
		ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud atc010_enredoutv2 = new ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud();
		atc010_enredoutv2.setTestCase(this.testCase);
		atc010_enredoutv2.ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud_test(data);
		
		// Obtener el NOMBRE del documento OUTBOUND
		String nameOutbound = atc010_enredoutv2.name;
		System.out.println("Nombre del documento 1 que regresa la API, flujo E2E: " + nameOutbound);
		
		//***************************Paso 11*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_010 Validar Api V2 ACK Estatus P WmCode 101-Tienda digital cloud\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10TLC y 50B87 respectivamente");
		
		validaStatus = tr.queryBDAzure(queryGetBatchId2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		String idQuery2 = tr.getValueCampoQuery(queryGetBatchId2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "BATCH_ID");
		
		//Cambiar a diferente tienda
		data.put("departmentId", "10TLC");
		data.put("storeId", "50B87");
		data.put("location", "41341");
		data.put("id", idQuery2);
				
		atc010_enredoutv2.ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud_test(data);
		String nameOutbound2 = atc010_enredoutv2.name;
		System.out.println("Nombre del documento 2 que regresa la API, flujo E2E: " + nameOutbound2);
		
		//***************************Paso 12*****************************************************************************
		addStep("Validar que el archivo generado de plaza 10VCZ y tienda 50YDY este en la ruta de Azure Storage:\r\n"
				+ "\r\n"
				+ "Ir a Resource Group -> Dar click a OXZRGERDDD02 -> Dar click en pestania Overview - Buscar y dar click a oxzsterdddap02\r\n"
				+ "Dar click en Data Storage -> Dar click a Container -> Dar click en el contenedor ct-json-mappedjsonstorepromotiondata -> Buscar archivo con nombre en tabla PRM_HEAD");
		
		boolean noExisteAzure = tr.archivoAzureNoExiste(azureConnectionString, azureContainerName, nameOutbound);
		
		if (noExisteAzure) {
	    	System.out.println("Archivo PRM V2 NO encontrado en Azure Storage: " + nameOutbound);
	        testCase.addTextEvidenceCurrentStep("Archivo PRM V2 NO encontrado en Azure Storage: " + nameOutbound);
	    }
	    
	    assertFalse("El archivo sigue existiendo en Blob Storage", !noExisteAzure);
		
		//***************************Paso 13*****************************************************************************
        addStep("Validar que el archivo generado de la plaza 10TLC y tienda 50B87 este en la ruta de Azure Storage:\r\n"
        		+ "\r\n"
        		+ "Ir a Resource Group -> Dar click a OXZRGERDDD02 -> Dar click en pestania Overview - Buscar y dar click a oxzsterdddap02\r\n"
        		+ "Dar click en Data Storage -> Dar click a Container -> Dar click en el contenedor ct-json-mappedjsonstorepromotiondata -> Buscar archivo con nombre en tabla PRM_HEAD");
		
        noExisteAzure = tr.archivoAzureNoExiste(azureConnectionString, azureContainerName, nameOutbound2);
    	
    	if (noExisteAzure) {
        	System.out.println("Archivo PRM V2 NO encontrado en Azure Storage: " + nameOutbound2);
            testCase.addTextEvidenceCurrentStep("Archivo PRM V2 NO encontrado en Azure Storage: " + nameOutbound2);
        }
        
        assertFalse("El archivo sigue existiendo en Blob Storage", !noExisteAzure);
		
		//***************************Paso 14 a 19*****************************************************************************
		addStep("No se pueden automatizar pasos 14 a 19 debido a doble factor de autenticacion");
		
		//***************************Paso 20*****************************************************************************
		addStep("Accesar BD FCWM6QA");
		testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWM6QA_NUEVA);
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		
		//***************************Paso 21*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc1);
		
		 //validaStatus = tr.queryOracle(querySimotiOutDoc1, dbFCWM6QA);
		 //tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc1);
		 //assertTrue("No se muestra el estatus esperado.", validaStatus);
		 testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
		
		//***************************Paso 22*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc2);
			
		 //validaStatus = tr.queryOracle(querySimotiOutDoc2, dbFCWM6QA);
		 //tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc2);
		 //assertTrue("No se muestra el estatus esperado.", validaStatus);
		 testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
				
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-016 Envio de promocion compleja a tienda digital cloud-automatico";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de promocion compleja a tienda digital cloud, archivo de respuesta almacenado en Azure Storage, interface PR63_CLOUD ejecucion programada (automatica).";
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
		return "1. Acceso a las BD's: RMSv16, FCDASPFR y FCWM6QA\r\n"
				+ "2. Acceso a la BD de Azure: MDB_ERDD_PromotionCloud\r\n"
				+ "3. Acceso al aplicativo RMS16, Control M y portal SIMOTI\r\n"
				+ "4. Contar con un SKU existente en la tienda 50YDY plaza 10VCZ (LOC=11524) en las BDs RMSv16 y FCDASPFR.\r\n"
				+ "Y con otro SKU existente en la tienda 50B87 plaza 10TLC (LOC=41341) en las mismas BDs.\r\n"
				+ "5. Tener ordenados los jobs en Control M, el listado con sus datos esta en la pestania Cadena Jobs de esta matriz. (solicitado por correo)\r\n"
				+ "6. Haber descargado el archivo .ods template de promocion simple de RMS16 (Tasks -> Buscar: Promotions -> Download Template Simple Promotion)\r\n"
				+ "7. Haber descargado el archivo .ods template cambio estatus promocion de RMS16 (Tasks -> Buscar: Filter Promotions -> Download Template)\r\n"
				+ "8. Tener acceso a las matrices de prueba de MARK_PRM y ENRED OUT V2 con sus correspondientes componentes de QA\r\n"
				+ "9. Contar con acceso a Azure Storage y Logs de ejecucion de Azure\r\n"
				+ "10. Tener configurado la ejecucion automatica del job PR63_CLOUD cada minuto\r\n"
				+ "11. Validar en la BD de RMSv16 en la tabla rms.PERIOD que el campo VDATE corresponda al dia actual, las promociones solo seran enviadas si tienen fecha de inicio del dia actual o 1 dia despues.";

	}
}

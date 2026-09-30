package WM.interfaces.ENRED_OUT_V2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.HashMap;

import org.apache.commons.net.ftp.FTPClient;
import org.testng.annotations.Test;

import com.azure.core.credential.TokenCredential;
import com.azure.monitor.query.models.QueryTimeInterval;

import WM.om.ENREDOUTV2;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_017_ENREDOUTV2_ValidarArchivosDescargadosOnpremiseDigital extends BaseExecution {
	public String[] regresaNames = new String[3];
	@Test
	(dataProvider = "data-provider")

	public void ATC_017_ENREDOUTV2_ValidarArchivosDescargadosOnpremiseDigital_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_017 Validar Archivos ITM (on-premise) y PRM (onpremise y digital cloud)-Hibrido
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que los servicios LISP, OUTBOUND y ACK de la API ENRED_OUT V1 funcione correctamente para que los archivos PRM e ITM (onpremise) se almacenen en Filezilla (FTP)
		y que los servicios LISP, OUTBOUND y ACK de la API ENRED_OUT V2 funcione correctamente para que archivos PRM (digital cloud) se almacenen en Azure Storage.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
		utils.sql.SQLUtil dbFCWM6QA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		utils.sql.SQLUtil dbFCWMLQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA02,GlobalVariables.DB_USER_FCWMLQA_WMLOG, GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG);
		
		//FCWM6QA
		String queryStatusAntesPrm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='" +data.get("doctype")+ "' AND STATUS='" +data.get("status")+ "' AND ID='" +data.get("idPrm")+ "'";
		String queryStatusLispPrm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='" +data.get("doctype")+ "' AND STATUS='" +data.get("statusLispRespuesta")+ "' AND ID='" +data.get("idPrm")+ "'";
		String queryStatusOutboundPrm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='" +data.get("doctype")+ "' AND STATUS='" +data.get("statusOutboundRespuesta")+ "' AND ID='" +data.get("idPrm")+ "'";
		String queryStatusAckPrm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='" +data.get("doctype")+ "' AND STATUS='" +data.get("statusAckRespuesta")+ "' AND ID='" +data.get("idPrm")+ "'";
		String queryStatusAntesItm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='ITM' AND STATUS='" +data.get("status")+ "' AND ID='" +data.get("idItm")+ "'";
		String queryStatusLispItm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='ITM' AND STATUS='" +data.get("statusLispRespuesta")+ "' AND ID='" +data.get("idItm")+ "'";
		String queryStatusOutboundItm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='ITM' AND STATUS='" +data.get("statusOutboundRespuesta")+ "' AND ID='" +data.get("idItm")+ "'";
		String queryStatusAckItm = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='ITM' AND STATUS='" +data.get("statusAckRespuesta")+ "' AND ID='" +data.get("idItm")+ "'";
		
		//FCWMLQA
		String queryLogError = "SELECT * FROM WMLOG.WM_OUT_DOC_LOG WHERE START_DATE >= SYSDATE - INTERVAL '" + data.get("minutes") + "' MINUTE AND WM_CODE != '" + data.get("getExpectedCode") + "' ORDER BY START_DATE DESC";
		
		//MDB_ERDD_PromotionCloud
        String queryStatusDigitalY = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId")+ "' AND STORE='" +data.get("storeId")+ "' AND DIGITAL='" +data.get("digital")+ "'";
		String queryStatusAntesCloud = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusLispRespuestaC")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("idAzure")+ "'";
		String queryStatusLispCloud = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusLispRespuestaC")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("idAzure")+ "'";
		String queryStatusOutboundCloud = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusOutboundRespuestaC")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("idAzure")+ "'";
		String queryStatusAckCloud = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusAckRespuestaC")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("idAzure")+ "'";
		
		String ftpHost="10.188.48.8";
		String ftpUser="posuser";
		String ftpPassword="posuser";
		
        String azureConnectionString = data.get("azureConnectionString");
        String azureContainerName = data.get("azureContainerName");
		
		FTPClient ftpClient = new FTPClient();
		
		// Variables para directorios y nombres de archivos (FTP y Azure Storage)
        String filePathFTP = data.get("ftpDir"); //Directorio remoto FTP de donde se descargan los archivos
        String filePathLocal = data.get("localDir"); //Directorio local donde vas a descargar los archivos
        boolean validaStatus = false;
		
		//***************************Paso 1*****************************************************************************
		 addStep("Ingresar a la BD FCWM6QA");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus L: " + queryStatusAntesItm);
		 
		 validaStatus = tr.queryOracle(queryStatusAntesItm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusAntesItm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String status = tr.getValueCampoOracle(queryStatusAntesItm, dbFCWM6QA, "STATUS");
			
		//***************************Paso 3*****************************************************************************
		addStep("Validar que el registro de la tienda este en estatus L: " + queryStatusAntesPrm);

		validaStatus = tr.queryOracle(queryStatusAntesPrm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusAntesPrm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusAntesPrm, dbFCWM6QA, "STATUS");
			
		//***************************Paso 4*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 5*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea digital, con la consulta: " + queryStatusDigitalY);
		 
		 validaStatus = tr.queryBDAzure(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String digital = tr.getValueCampoQuery(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
		
		//***************************Paso 6*****************************************************************************
		 addStep("Validar que el registro del documento este en estatus C: " + queryStatusAntesCloud);
		 
		 validaStatus = tr.queryBDAzure(queryStatusAntesCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String statusCloud = tr.getValueCampoQuery(queryStatusAntesCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 String pvStatus = tr.getValueCampoQuery(queryStatusAntesCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
			
		//***************************Paso 7*****************************************************************************
		addStep("Invocar la API LISP V2 para conocer la lista de documentos pendientes para la tienda");

		//Obtener datos del data provider
		String testcaseName = data.get("name ");
		String getPathToken = data.get("getPathToken");
		String baseURLToken = data.get("baseURLToken");
		String Auth = data.get("Auth");
		
		String access_token = tr.getTokenAPI(getPathToken, baseURLToken, Auth);
		
		//Definici�n de Variables 	
		String baseURILisp =  data.get("baseURILisp");
		String getPathLisp = data.get("getPathLisp");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String source = data.get("source");
		String doctype = data.get("doctype");
		String getExpectedCode = data.get("getExpectedCode");
		
		regresaNames = tr.lispAPI3ArchivosV2V1(baseURILisp, getPathLisp, departmentId, storeId, source, doctype, access_token, getExpectedCode, testcaseName);
		System.out.println("Doc de arreglo: " + regresaNames[0]);
		System.out.println("Doc de arreglo: " + regresaNames[1]);
		System.out.println("Doc de arreglo: " + regresaNames[2]);
		
		String name1 = regresaNames[0].substring(0, regresaNames[0].length() - 3);
		
		//***************************Paso 8*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus W: " + queryStatusLispItm);
		 
		 validaStatus = tr.queryOracle(queryStatusLispItm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusLispItm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusLispItm, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("W")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 9*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus W: " + queryStatusLispPrm);

		 validaStatus = tr.queryOracle(queryStatusLispPrm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusLispPrm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusLispPrm, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("W")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("La ejecucion no fue exitosa");
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 10*****************************************************************************
		 addStep("Validar que el registro del documento este en estatus C: " + queryStatusLispCloud);
		 
		 validaStatus = tr.queryBDAzure(queryStatusLispCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 statusCloud = tr.getValueCampoQuery(queryStatusLispCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 pvStatus = tr.getValueCampoQuery(queryStatusLispCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 validaStatus = false;
		 
		 if(!statusCloud.equals("C") && pvStatus != null) {
				validaStatus = true;
		 }
		 
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
		 
		//***************************Paso 11*****************************************************************************
		 addStep("Invocar la API Outbound V2 con JSON para el archivo PRM");
		 
		// Llamada al API OUTBOUND usando el token generado	
		String getPathOutbound = data.get("getPathOutbound");
		String getPathOutboundV1 = data.get("getPathOutboundv1");
		
		String eventDateOutbound1 = tr.outboundAPI(baseURILisp, getPathOutbound, regresaNames[0], departmentId, storeId, source, doctype, access_token, getExpectedCode, testcaseName);

		//***************************Paso 12*****************************************************************************
		 addStep("Invocar la API Outbound V1 con JSON para los archivos ITM y PRM");
		 
		 int getExpectedCode1 = Integer.parseInt(data.get("getExpectedCode"));
					
		String eventDateOutbound2 = tr.outboundAPIV1(baseURILisp, getPathOutboundV1, regresaNames[1], departmentId, storeId, source, doctype, access_token, getExpectedCode1);
		String eventDateOutbound3 = tr.outboundAPIV1(baseURILisp, getPathOutboundV1, regresaNames[2], departmentId, storeId, source, "ITM", access_token, getExpectedCode1);
		
		//***************************Paso 13*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus E: " + queryStatusOutboundItm);
		
		 validaStatus = tr.queryOracle(queryStatusOutboundItm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusOutboundItm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusOutboundItm, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("E")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("Campo status diferente al esperado: " + status);
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 14*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus E: " + queryStatusOutboundPrm);

		 validaStatus = tr.queryOracle(queryStatusOutboundPrm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusOutboundPrm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusOutboundPrm, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("E")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("Campo status diferente al esperado: " + status);
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 15*****************************************************************************
		 addStep("Validar que el registro del documento se actualizo a estatus C: " + queryStatusOutboundCloud);
		 
		 validaStatus = tr.queryBDAzure(queryStatusOutboundCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 statusCloud = tr.getValueCampoQuery(queryStatusOutboundCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 pvStatus = tr.getValueCampoQuery(queryStatusOutboundCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 validaStatus = false;
		 
		 if(!statusCloud.equals("C") && pvStatus != null) {
				validaStatus = true;
		 }
		 
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 16*****************************************************************************
	 	addStep("Descargar el archivo generado de la tienda este en la ruta de Azure Storage (Blob Storage):\r\n"
	 			+ "\r\n"
	 			+ "Ir a Resource Group -> Dar click a OXZRGERDDQ01 -> Dar click en pestania Overview - Buscar y dar click a oxzsterddqap01\r\n"
	 			+ "Dar click en Data Storage -> Dar click a Container -> Dar click en el contenedor ct-json-mappedjsonstorepromotiondata -> Buscar archivo con nombre en tabla PRM_HEAD");	
			
	 	 boolean existeAzure = tr.archivoAzureExiste(azureConnectionString, azureContainerName, name1);
	        
        if (existeAzure) {
        	System.out.println("Archivo PRM V2 encontrado en Azure Storage: " + name1);
            testCase.addTextEvidenceCurrentStep("Archivo PRM V2 encontrado en Azure Storage: " + name1);
        }
        assertTrue("No se muestra el estatus esperado: el archivo PRM V2 no fue encontrado en Azure Storage.", existeAzure);
	 	
	 	String azurePRM2 = tr.downloadFileFromAzure(azureConnectionString, azureContainerName, name1, filePathLocal);
	 	
	 	//***************************Paso 17*****************************************************************************
	 	addStep("Invocar la API ACK V2 para confirmar un documento PRM descargado");
	 	
	    // Llamada al API ACK usando el token generado
 		String getPathACK = data.get("getPathACK");
 		String getPathACKv1 = data.get("getPathACKv1");
 		String statusBody = data.get("statusBody");
 		String clientIdAPI = data.get("clientIdAPI");
 		String secretIdAPI = data.get("secretIdAPI");
 	
 		tr.ackAPI(baseURILisp, getPathACK, regresaNames[0], departmentId, storeId, eventDateOutbound1, doctype, getPathToken, getExpectedCode, testcaseName, baseURLToken, Auth, clientIdAPI, secretIdAPI, statusBody);
 		
 		OffsetDateTime endTime = OffsetDateTime.now();
 	    OffsetDateTime startTime = endTime.minusMinutes(5);
 	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);
		
		//***************************Paso 18*****************************************************************************
	 	addStep("Invocar la API ACK V1 para confirmar los documentos ITM y PRM descargados");	
			
	    // Llamada al API ACK usando el token generado
 		tr.ackAPI(baseURILisp, getPathACKv1, regresaNames[1], departmentId, storeId, eventDateOutbound2, doctype, getPathToken, getExpectedCode, testcaseName, baseURLToken, Auth, clientIdAPI, secretIdAPI, statusBody);
 		tr.ackAPI(baseURILisp, getPathACKv1, regresaNames[2], departmentId, storeId, eventDateOutbound3, "ITM", getPathToken, getExpectedCode, testcaseName, baseURLToken, Auth, clientIdAPI, secretIdAPI, statusBody);
	 	
		//***************************Paso 19*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus P: " + queryStatusAckItm);
		
		 validaStatus = tr.queryOracle(queryStatusAckItm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusAckItm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusAckItm, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("P")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("Campo status diferente al esperado: " + status);
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 20*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus P: " + queryStatusAckPrm);

		 validaStatus = tr.queryOracle(queryStatusAckPrm, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusAckPrm);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusAckPrm, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("P")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("Campo status diferente al esperado: " + status);
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);

		//***************************Paso 21*****************************************************************************
		 addStep("Validar que el registro del documento se actualizo a estatus A: " + queryStatusAckCloud);
		 
		 validaStatus = tr.queryBDAzure(queryStatusAckCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 statusCloud = tr.getValueCampoQuery(queryStatusAckCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 pvStatus = tr.getValueCampoQuery(queryStatusAckCloud, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 validaStatus = false;
		 
		 if(!statusCloud.equals("C") && !pvStatus.equals("P")) {
				validaStatus = true;
		 }
		 
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 22*****************************************************************************
		 addStep("Ingresar a Log de Errores de Azure");
			
		 String clientId = data.get("clientId");
		 String clientSecret = data.get("clientSecret");
		 String tenantId = data.get("tenantId");
		 String workspaceId = data.get("workspaceId");
		 
		 TokenCredential tokenCredential = tr.loginLogsAzure(clientId, tenantId, clientSecret);

		//***************************Paso 23*****************************************************************************
		addStep("Validar que se registro la ejecucion en la tabla de log: ");
		 
		validaStatus = tr.consultaLogsAzure(tokenCredential, workspaceId, timeInterval);
		 assertFalse("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 24*****************************************************************************
		addStep("Confirmar que el archivo de la tienda ya NO este en la ruta de Azure Storage (Blob Storage):\r\n"
				+ "\r\n"
				+ "Ir a Resource Group -> Dar click a OXZRGERDDQ01 -> Dar click en pestania Overview - Buscar y dar click a oxzsterddqap01\r\n"
				+ "Dar click en Data Storage -> Dar click a Container -> Dar click en el contenedor ct-json-mappedjsonstorepromotiondata -> Buscar archivo con nombre en tabla PRM_HEAD");
		
		boolean noExisteAzure = tr.archivoAzureNoExiste(azureConnectionString, azureContainerName, name1);
        
        if (noExisteAzure) {
        	System.out.println("Archivo PRM V2 NO encontrado en Azure Storage: " + name1);
            testCase.addTextEvidenceCurrentStep("Archivo PRM V2 NO encontrado en Azure Storage: " + name1);
        }
        
        assertFalse("El archivo sigue existiendo en Blob Storage", !noExisteAzure);
		
		//***************************Paso 25*****************************************************************************
		addStep("Ingresar a la BD FCWMLQA");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA02);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMLQA_WMLOG);
		 
		//***************************Paso 26*****************************************************************************
		addStep("Validar que se registro la ejecucion en la tabla de log: " + queryLogError);
		
		validaStatus = tr.queryOracleEmpty(queryLogError, dbFCWMLQA);
		tr.processQueryInChunks(dbFCWM6QA, queryLogError);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 27*****************************************************************************
        addStep("Se valida el movimiento del archivo ITM en Filezilla");
        
        String prmv1FilePath = filePathFTP + "/" + regresaNames[1];
        String itmv1FilePath = filePathFTP + "/" + regresaNames[2];
        String prmv1FilePathLocal = filePathLocal + "/" + regresaNames[1];
        
        ftpClient.connect(ftpHost);
        if (tr.isFilePresentInFTP(ftpHost, ftpUser, ftpPassword, itmv1FilePath)) {
            System.out.println("Archivo ITM V1 encontrado en FTP" + regresaNames[2]);
            testCase.addTextEvidenceCurrentStep("Archivo ITM V1 encontrado en FTP" + regresaNames[2]);
            validaStatus = false;
        } else {
            System.out.println("Archivo ITM V1 no encontrado en FTP" + regresaNames[2]);
            testCase.addTextEvidenceCurrentStep("Archivo ITM V1 no encontrado en FTP" + regresaNames[2]);
            validaStatus = true;
        }
        assertFalse("Se encontro algun registro de error", validaStatus);

      //***************************Paso 28*****************************************************************************
        addStep("Se valida el movimiento del archivo PRM en Filezilla");
        
        if (tr.isFilePresentInFTP(ftpHost, ftpUser, ftpPassword, prmv1FilePath)) {
            System.out.println("Archivo PRM V1 encontrado en FTP: " + regresaNames[1]);
            testCase.addTextEvidenceCurrentStep("Archivo PRM V1 encontrado en FTP: " + regresaNames[1]);
            validaStatus = false;
        } else {
            System.out.println("Archivo PRM V1 no encontrado en FTP: " + regresaNames[1]);
            testCase.addTextEvidenceCurrentStep("Archivo PRM V1 no encontrado en FTP: " + regresaNames[1]);
            validaStatus = true;
        }
        assertFalse("Archivo PRM V1 no encontrado en FTP", validaStatus);

      //***************************Paso 29*****************************************************************************
        addStep("Comparar archivos PRM para confirmar que tienen la misma estructura");
        
        String ftpPRM2 = tr.downloadFileFromFTP(ftpHost, ftpUser, ftpPassword, prmv1FilePath, prmv1FilePathLocal);
        
      //Descomprimir archivos
        String ftpPRMDesc = tr.decompressGzipFile(ftpPRM2);
        
        File ftpPRM = new File(ftpPRMDesc);
        File azurePRM = new File(azurePRM2);

        testCase.addTextEvidenceCurrentStep("Archivo PRM API V1 descargado en ruta local: " + ftpPRM);
        testCase.addTextEvidenceCurrentStep("Archivo PRM API V2 descargado en ruta local: " + azurePRM);

        if (tr.compareJSONFiles(ftpPRM, azurePRM)) {
            System.out.println("Los archivos PRM tienen la misma estructura");
            validaStatus = false;
        } else {
            System.out.println("Los archivos PRM NO tienen la misma estructura");
            validaStatus = true;
        }
        assertFalse("Los archivos PRM NO tienen la misma estructura", validaStatus);
        
	}   

	@Override
	public String setTestFullName() {
		return "ATC_017_Validar_Archivos_ITM_onpremise_PRM_onpremise_digial_descargados_Hibrido";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Valida que el archivo PRM de tienda digital cloud es descargado correctamente con la API Outbound V2\r\n"
				+ "Valida que el archivo PRM de tienda onpremise es descargado correctamente con la API Outbound V1\r\n"
				+ "Valida que el archivo ITM de tienda onpremise es descargado correctamente con la API Outbound V1";
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
		return "1. Contar con la mensajeria para invocar la API Otubound V2 y Otubound V1\r\n"
				+ "3. Esten instalado el cambio de la ENRED OUT V2\r\n"
				+ "4. Tener documentos PRM generados para tienda digital cloud y tienda onpremise\r\n"
				+ "5. Tener documentos ITM generados para tienda onpremise\r\n"
				+ "6. Respuesta con codigo: wmCode 101\r\n"
				+ "7. Verificar que exista la interface Enred out V2 en las configuraciones de Azure\r\n"
				+ "8. Haber ejecutado el proceso de envio de las tiendas para tener documentos pendientes (PR63_Cloud y PR63_MX)\r\n"
				+ "9. Acceso a BD MDB_ERDD_PromotionCloud (Azure) y Azure Storage\r\n"
				+ "10. Tener acceso al buzon de tienda: 10.188.48.7 Con el puerto 7787\r\n"
				+ "Usuario es plaza y tienda, ejemplo: 10VCZ50DVA, mismo caso para contrasenia.";

	}
}
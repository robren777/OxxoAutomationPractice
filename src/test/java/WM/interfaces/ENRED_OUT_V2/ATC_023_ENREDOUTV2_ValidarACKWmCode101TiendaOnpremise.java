package WM.interfaces.ENRED_OUT_V2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

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

public class ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise extends BaseExecution {
	public String eventDateOutbound = "";
	public String name = "";
	
	@Test
	(dataProvider = "data-provider")

	public void ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_023 Validar Api V1 ACK Estatus P WmCode 101-Tienda onpremise
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que el servicio ACK de la API ENRED_OUT V1 funcione correctamente y regrese WmCode en 101 con estatus P.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
		utils.sql.SQLUtil dbFCWM6QA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		utils.sql.SQLUtil dbFCWMLQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA02,GlobalVariables.DB_USER_FCWMLQA_WMLOG, GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG);
		
		//FCWM6QA
		String queryStatusAntes = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='" +data.get("doctype")+ "' AND STATUS='" +data.get("status")+ "' AND ID='" +data.get("id")+ "'";
		String queryStatusDespues = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='" +data.get("doctype")+ "' AND STATUS='" +data.get("statusAckRespuesta")+ "' AND ID='" +data.get("id")+ "'";
		String queryLogError = "SELECT * FROM WMLOG.WM_OUT_DOC_LOG WHERE START_DATE >= SYSDATE - INTERVAL '" + data.get("minutes") + "' MINUTE AND WM_CODE != '" + data.get("getExpectedCode") + "' ORDER BY START_DATE DESC";
		
		boolean validaStatus = false;
		
		//***************************Paso 1*****************************************************************************
		 addStep("Ingresar a la BD FCWM6QA");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que el registro de la tienda este en estatus E: " + queryStatusAntes);
		 
		 validaStatus = tr.queryOracle(queryStatusAntes, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusAntes);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String status = tr.getValueCampoOracle(queryStatusAntes, dbFCWM6QA, "STATUS");
			
		//***************************Paso 3*****************************************************************************
		addStep("Invocar la API ACK V1 para confirmar un documento descargado\n");

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
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		String getExpectedCode1 = data.get("getExpectedCode");
		String statusBody = data.get("statusBody");
		
		name = tr.lispAPIV1(baseURILisp, getPathLisp, departmentId, storeId, source, doctype, access_token, getExpectedCode);
		
		// Llamada al API OUTBOUND usando el token generado	
		String getPathOutbound = data.get("getPathOutbound");
				
		eventDateOutbound = tr.outboundAPIV1(baseURILisp, getPathOutbound, name, departmentId, storeId, source, doctype, access_token, getExpectedCode);
		
		// Llamada al API ACK usando el token generado	
		String getPathACK = data.get("getPathACK");
		String clientIdAPI = data.get("clientIdAPI");
		String secretIdAPI = data.get("secretIdAPI");
		
		tr.ackAPI(baseURILisp, getPathACK, name, departmentId, storeId, eventDateOutbound, doctype, getPathToken, getExpectedCode1, testcaseName, baseURLToken, Auth, clientIdAPI, secretIdAPI, statusBody);
		
		//***************************Paso 4*****************************************************************************
		 addStep("Validar que el registro de la tienda se actualizo a estatus P: " + queryStatusDespues);
		 
		 validaStatus = tr.queryOracle(queryStatusDespues, dbFCWM6QA);
		 tr.processQueryInChunks(dbFCWM6QA, queryStatusDespues);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoOracle(queryStatusDespues, dbFCWM6QA, "STATUS");
		 validaStatus = false;
		 
		if(!status.equals("P")) {
			validaStatus = true;
			testCase.addTextEvidenceCurrentStep("Campo status diferente al esperado: " + status);
		}
		assertFalse("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 5*****************************************************************************
		addStep("Ingresar a la BD FCWMLQA");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA02);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMLQA_WMLOG);
		 
		//***************************Paso 6*****************************************************************************
		addStep("Validar que no se registraron errores en la WMLOG: " + queryLogError);
		
		validaStatus = tr.queryOracleEmpty(queryLogError, dbFCWMLQA);
		tr.processQueryInChunks(dbFCWMLQA, queryLogError);
		assertTrue("No se muestra el estatus esperado.", validaStatus);
	}

	@Override
	public String setTestFullName() {
		return "ATC_023_ValidarApiV1_ACKEstatusP_WmCode101_Tienda_onpremise";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecuta servicio API ACK de la interface ENRED_OUT V1 de tienda onpremise, regresando un codigo 101 de operacion exitosa para los archivos con estatus P";
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
		return "1. Contar con la mensajeria para invocar la API ACK V1\r\n"
				+ "2. Esten instalado el cambio de la ENRED OUT V2\r\n"
				+ "3. Respuesta con codigo: wmCode 101\r\n"
				+ "4. Acceso a BD FCWM6QA y FCWMLQA\r\n"
				+ "5. Haber ejecutado el caso de prueba: MTC_FT_021 Validar Api Oubtbound V1 WmCode 101-Tienda onpremise\r\n"
				+ "6. Verificar que exista la interface Enred out V1 (lisp, outbound, ack) con al consulta: SELECT * FROM POSUSER.POS_INTERFASE_CONFIG WHERE INTERFASE = 'ENRED_OUT' ORDER BY ENTIDAD;\r\n"
				+ "7. Haber ejecutado el proceso de envio de las tiendas para tener documentos pendientes (PR63_MX)";

	}
}

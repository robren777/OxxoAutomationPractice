package WM.interfaces.ENRED_OUT_V2;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.HashMap;

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

public class ATC_013_ENREDOUTV2_ValidarACKEstatusXWmCode101TiendaDigitalCloud extends BaseExecution {
	public String eventDateOutbound = "";
	public String name = "";
	
	@Test
	(dataProvider = "data-provider")

	public void ATC_013_ENREDOUTV2_ValidarACKEstatusXWmCode101TiendaDigitalCloud_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_013 Validar Api V2 ACK Estatus X WmCode 101-Tienda digital cloud
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que el servicio ACK de la API ENRED_OUT_V2 funcione correctamente y regrese WmCode en 101 con estatus P.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
		//MDB_ERDD_PromotionCloud
		String queryStatusDigitalY = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId")+ "' AND STORE='" +data.get("storeId")+ "' AND DIGITAL='" +data.get("digital")+ "'";
        String queryStatusAntes = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusOutboundRespuesta")+  "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("id")+ "'";
		String queryStatuAck = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusAckRespuesta")+  "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("id")+ "'";
		
		boolean validaStatus = false;
		
		//***************************Paso 0*****************************************************************************
		 addStep("Ejecutar caso @MTC_FT_005 Validar Api V2 Oubtbound WmCode 101-Tienda digital cloud");
		 
		 ATC_005_ENREDOUTV2_ValidarOutboundWmCode101TiendaDigitalCloud atc005_enredoutv2 = new ATC_005_ENREDOUTV2_ValidarOutboundWmCode101TiendaDigitalCloud();
		 atc005_enredoutv2.setTestCase(this.testCase);
		 atc005_enredoutv2.ATC_005_ENREDOUTV2_ValidarOutboundWmCode101TiendaDigitalCloud_test(data);
		 
		 eventDateOutbound = atc005_enredoutv2.eventDateOutbound;
		 name = atc005_enredoutv2.name;
		 
		//***************************Paso 1*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea digital, con la consulta: " + queryStatusDigitalY);
		 
		 validaStatus = tr.queryBDAzure(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String digital = tr.getValueCampoQuery(queryStatusDigitalY, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
		 
		//***************************Paso 3*****************************************************************************
		 addStep("Validar que el registro del documento se actualizo a estatus C: " + queryStatusAntes);

		 validaStatus = tr.queryBDAzure(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String status = tr.getValueCampoQuery(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 String pvStatus = tr.getValueCampoQuery(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 
		//***************************Paso 4*****************************************************************************
		addStep("Invocar la API ACK V2 para confirmar un documento descargado");
		
		// Llamada al API ACK usando el token generado
		String testcaseName = data.get("name ");
		String baseURLToken = data.get("baseURLToken");
		String baseURIACK = data.get("baseURILisp");
		String getPathACK = data.get("getPathACK");
		String getPathToken = data.get("getPathToken");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String doctype = data.get("doctype");
		String statusBody = data.get("statusBody");
		String getExpectedCode = data.get("getExpectedCode");
		String Auth = data.get("Auth");
		String clientIdAPI = data.get("clientIdAPI");
		String secretIdAPI = data.get("secretIdAPI");
	
		tr.ackAPI(baseURIACK, getPathACK, name, departmentId, storeId, eventDateOutbound, doctype, getPathToken, getExpectedCode, testcaseName, baseURLToken, Auth, clientIdAPI, secretIdAPI, statusBody);
		
		OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);
		
		//***************************Paso 5*****************************************************************************
		 addStep("Validar que el registro del documento se actualizo a estatus X: " + queryStatuAck);
		 
		 tr.queryBDAzure(queryStatuAck, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 status = tr.getValueCampoQuery(queryStatuAck, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 pvStatus = tr.getValueCampoQuery(queryStatuAck, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 validaStatus = false;
		 
		 if(status.equals("C") || pvStatus.equals("X")) {
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
		assertTrue("No se muestra el estatus esperado.", validaStatus);
			
		//***************************Paso 8*****************************************************************************
		addStep("Confirmar que el servicio LISP NO se puede volver a consultar:\n");
		
		//Obtener datos del data provider
		String access_token = tr.getTokenAPI(getPathToken, baseURLToken, Auth);

		//Definici�n de Variables 	
		String baseURILisp =  data.get("baseURILisp");
		String getPathLisp = data.get("getPathLisp");
		String source = data.get("source");
		String getExpectedCode2 = data.get("getExpectedCode2");
		validaStatus = true;
		
		String name2 = tr.lispAPI(baseURILisp, getPathLisp, departmentId, storeId, source, doctype, access_token, getExpectedCode2, testcaseName);
		
		if (name.equals(name2)) {
			validaStatus = false;
		}
		
		assertTrue(validaStatus, "El archivo aun se puede consultar en el LISP, no deberia estar disponible");
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_013 Validar Api V2 ACK Estatus X WmCode 101-Tienda digital cloud";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecuta servicio API ACK de la interface ENRED_OUT V2 de tienda digital que va por cloud, regresando un codigo 101 de operacion exitosa para los archivos con estatus X";
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
		return "1. Contar con la mensajeria para invocar la API ACK V2 \r\n"
				+ "2. Esten instalado el cambio de la ENRED OUT V2 \r\n"
				+ "3. Haber ejecutado los casos de prueba: MTC_FT_005 Validar Api V2 Oubtbound WmCode 101-Tienda digital cloud \r\n"
				+ "4. Mandar estatus F en el body del ACK. Respuesta con codigo: wmCode 101 \r\n"
				+ "5. Verificar que exista la interface Enred out V2 en las configuraciones de Azure \r\n"
				+ "6. Haber ejecutado el proceso de envio de las tiendas para tener documentos pendientes (PR63_Cloud) \r\n"
				+ "7. Acceso a BD MDB_ERDD_PromotionCloud (Azure) y Azure Storage";

	}
}

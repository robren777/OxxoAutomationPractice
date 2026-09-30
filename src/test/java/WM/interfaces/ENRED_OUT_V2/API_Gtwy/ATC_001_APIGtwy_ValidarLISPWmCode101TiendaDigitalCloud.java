package WM.interfaces.ENRED_OUT_V2.API_Gtwy;

import static org.junit.Assert.assertFalse;
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

public class ATC_001_APIGtwy_ValidarLISPWmCode101TiendaDigitalCloud extends BaseExecution {
	@Test
	(dataProvider = "data-provider")

	public void ATC_001_APIGtwy_ValidarLISPWmCode101TiendaDigitalCloud_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_001 Validar API V2 LISP WmCode 101-Tienda digital cloud
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que el servicio LISP de la API ENRED_OUT_V2 funcione correctamente y regrese WmCode en 101.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
		//MDB_ERDD_PromotionCloud
        String queryStatusDigitalY = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId")+ "' AND STORE='" +data.get("storeId")+ "' AND DIGITAL='" +data.get("digital")+ "'";
		String queryStatusAntes = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "'AND BATCH_ID='" +data.get("id")+ "'";
		String queryStatusDespues = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("statusLispRespuesta")+  "' AND PV_DOC_TYPE='" +data.get("doctype")+ "' AND BATCH_ID='" +data.get("id")+ "'";
		
		boolean validaStatus = true;
		
		//***************************Paso 1*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea fisica, con la consulta: " + queryStatusDigitalY);
		 
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
		addStep("Invocar la API LISP V2 para conocer la lista de documentos pendientes para la tienda\n");
		
		//Obtener datos del data provider
		String testcaseName = data.get("name ");
		String getPathToken = data.get("getPathToken");
		String baseURLToken = data.get("baseURLToken");
		String Auth = data.get("Auth");
		
		String access_token = tr.getTokenAPI(getPathToken, baseURLToken, Auth);

		//Definici�n de Variables 	
		String baseURILisp = data.get("baseURILisp");
		String getPathLisp = data.get("getPathLisp");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String source = data.get("source");
		String doctype = data.get("doctype");
		String getExpectedCode = data.get("getExpectedCode");
		
		tr.lispAPI(baseURILisp, getPathLisp, departmentId, storeId, source, doctype, access_token, getExpectedCode, testcaseName);

	    OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);
		
	    //********************Paso 5*****************************************************************************
		 addStep("Validar que el registro del documento se actualizo a estatus C: " + queryStatusDespues);
		 
		 validaStatus = tr.queryBDAzure(queryStatusDespues, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 status = tr.getValueCampoQuery(queryStatusDespues, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 pvStatus = tr.getValueCampoQuery(queryStatusDespues, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
		 validaStatus = false;
		 
		 if(status.equals("C") || pvStatus == null) {
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
		return "MTC_FT_001 Validar API V2 LISP WmCode 101-Tienda digital cloud";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecuta servicio API LISP de la interface API Gtwy de tienda digital que va por cloud, regresando un codigo 101 de operacion exitosa";
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
		return "1. Contar con la mensajeria para invocar la API LISP V2 \r\n"
				+ "2. Esten instalado el cambio de la ENRED OUT V2 \r\n"
				+ "3. Respuesta con codigo: wmCode 101 \r\n"
				+ "4. Acceso a BD MDB_ERDD_PromotionCloud (Azure) y Azure Storage \r\n"
				+ "5. Verificar que exista la interface Enred out V2 en las configuraciones de Azure \r\n"
				+ "6. Haber ejecutado el proceso de envio de las tiendas para tener documentos pendientes (PR63_Cloud)";

	}
}

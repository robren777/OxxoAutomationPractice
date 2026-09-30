package WM.interfaces.ENRED_OUT_V2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class ATC_006_ENREDOUTV2_ValidarOutboundWmCode116TiendaFisicaCloud extends BaseExecution {
	public String eventDateOutbound = "";
	public String name = "";
	
	@Test
	(dataProvider = "data-provider")

	public void ATC_006_ENREDOUTV2_ValidarOutboundWmCode116TiendaFisicaCloud_test (HashMap<String, String> data) throws Exception {
		/*BackOffice Mexico: MTC_FT_006 Validar Api V2 Oubtbound-Tienda fisica cloud
		Desc:
		Prueba de componente actualizado a V2, el equipo de desarrollo genero la version 2 de la API ENRED_OUT,
		que envia documentos varios, actualmente solo se envia a buzon de tienda, pero en este desarrollo se pretende que
		envie documentos a buzon de tienda y a la nube segun la bandera que tenga el documento.
		Este caso de prueba revisa que el servicio OUTBOUND de la API ENRED_OUT_V2 funcione correctamente y regrese WmCode en 101.
		@Valeria Fernandez
		@date   2024/11/15*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		ENREDOUTV2 tr = new ENREDOUTV2(testCase);
		
        //MDB_ERDD_PromotionCloud
        String queryStatusDigitalN = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("departmentId")+ "' AND STORE='" +data.get("storeId")+ "' AND DIGITAL='" +data.get("digital")+ "'";
        String queryStatusAntes = "SELECT * FROM dbo.CO_PRM_DCM_STS WHERE LOCATION= '" +data.get("location")+ "' AND PROCESSING_STATUS='" +data.get("status")+ "' AND PV_DOC_TYPE='" +data.get("doctype")+ "'AND BATCH_ID='" +data.get("id")+ "'";
        String queryHEADP = "SELECT * FROM dbo.CO_PRM_HEADP WHERE ID_PRM_HDR_OX = '" +data.get("id_prm_hdr_ox")+"' ORDER BY DT_SCH DESC";
        String queryHEADD = "SELECT * FROM dbo.CO_PRM_HEADD WHERE FO_PRM = '" + data.get("promotionFolio") + "' AND ID_PRM_HDR_OX = '" +data.get("id_prm_hdr_ox")+"'";
        boolean validaStatus;
        
		//***************************Paso 0*****************************************************************************
        addStep("Haber ejecutado el caso de prueba: MTC_FT_002 Validar API V2 LISP WmCode 101-Tienda fisica cloud");
		
        ATC_002_ENREDOUTV2_ValidarLISPWmCode101TiendaFisicaCloud atc002_enredoutv2 = new ATC_002_ENREDOUTV2_ValidarLISPWmCode101TiendaFisicaCloud();
        atc002_enredoutv2.setTestCase(this.testCase);
        atc002_enredoutv2.ATC_002_ENREDOUTV2_ValidarLISPWmCode101TiendaFisicaCloud_test(data);
		
		//***************************Paso 1*****************************************************************************
		addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea fisica, con la consulta: " + queryStatusDigitalN);
		 
		 validaStatus = tr.queryBDAzure(queryStatusDigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String digital = tr.getValueCampoQuery(queryStatusDigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
		
		//***************************Paso 3*****************************************************************************
		 addStep("Validar la informacion de tienda fisica de la tabla CO_PRM_HEADP con la consulta:  " + queryHEADP);
		 
		 validaStatus = tr.queryBDAzure(queryHEADP, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 4*****************************************************************************
		 addStep("Validar la informacion de tienda fisica de la tabla CO_PRM_HEADD con la consulta:  " + queryHEADD);
		 
		 validaStatus = tr.queryBDAzure(queryHEADD, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		
		//***************************Paso 5*****************************************************************************
		 addStep("Validar que el registro del documento este en estatus C: " + queryStatusAntes);

		 validaStatus = tr.queryBDAzure(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String status = tr.getValueCampoQuery(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PROCESSING_STATUS");
		 String pvStatus = tr.getValueCampoQuery(queryStatusAntes, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "PV_STATUS");
				
		//***************************Paso 6*****************************************************************************
		addStep("Invocar la API Outbound con JSON");
		
		//Invocar la API Outbound con JSON
		//Obtener datos del data provider
		String testcaseName = data.get("name ");
		String getPathToken = data.get("getPathToken");
		String baseURLToken = data.get("baseURLToken");
		String Auth = data.get("Auth");
		
		String access_token = tr.getTokenAPI(getPathToken, baseURLToken, Auth);

		//Definici�n de Variables 	
		String baseURILisp =  data.get("baseURILisp");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String source = data.get("source");
		String doctype = data.get("doctype");
		String getExpectedCode = data.get("getExpectedCode");
		String getExpectedCodeOutbound = data.get("getExpectedCodeOutbound");
		
		name = atc002_enredoutv2.name;
		
		// Llamada al API OUTBOUND usando el token generado	
		String getPathOutbound = data.get("getPathOutbound");
		
		eventDateOutbound = tr.outboundAPI(baseURILisp, getPathOutbound, name, departmentId, storeId, source, doctype, access_token, getExpectedCodeOutbound, testcaseName);
		
		OffsetDateTime endTime = OffsetDateTime.now();
	    OffsetDateTime startTime = endTime.minusMinutes(5);
	    QueryTimeInterval timeInterval = new QueryTimeInterval(startTime, endTime);
	    
		//***************************Paso 7*****************************************************************************
		addStep("Ingresar a Log de Errores de Azure");
		
		 String clientId = data.get("clientId");
		 String clientSecret = data.get("clientSecret");
		 String tenantId = data.get("tenantId");
		 String workspaceId = data.get("workspaceId");
		 
		 TokenCredential tokenCredential = tr.loginLogsAzure(clientId, tenantId, clientSecret);

		//***************************Paso 8*****************************************************************************
		addStep("Validar que se registro la ejecucion en la tabla de log: ");

		validaStatus = tr.consultaLogsAzure(tokenCredential, workspaceId, timeInterval);
		assertFalse("No se muestra el estatus esperado.", validaStatus);
		
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_006 Validar Api V2 Oubtbound-Tienda fisica cloud";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecuta servicio API Outbound de la interface ENRED_OUT V2 de tienda fisica que va por cloud, la respuesta no tendra archivos de tiendas fisicas (No se generan archivo de tiendas fisicas, por lo que estas no pasan por la API V2).";
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
		return "1. Contar con la mensajeria para invocar la API Outbound V2 \r\n"
				+ "2. Esten instalado el cambio de la ENRED OUT V2 \r\n"
				+ "3. Haber ejecutado el caso de prueba: MTC_FT_002 Validar API V2 LISP WmCode 101-Tienda fisica cloud \r\n"
				+ "4. No se generan archivo de tiendas fisicas, por lo que estas no pasan por la API V2, por lo que la respuesta no tendra archivos de tiendas fisicas. \r\n"
				+ "5. Verificar que exista la interface Enred out V2 en las configuraciones de Azure \r\n"
				+ "6. Haber ejecutado el proceso de envio de las tiendas para tener documentos pendientes (PR63_Cloud) \r\n"
				+ "7. Acceso a BD MDB_ERDD_PromotionCloud (Azure) y Azure Storage";

	}
}

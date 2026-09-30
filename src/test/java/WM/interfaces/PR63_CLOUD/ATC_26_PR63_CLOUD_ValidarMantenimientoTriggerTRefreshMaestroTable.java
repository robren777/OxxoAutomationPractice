package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.azure.core.credential.TokenCredential;

import WM.om.PR63_CLOUD;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_26_PR63_CLOUD_ValidarMantenimientoTriggerTRefreshMaestroTable extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	public void ATC_26_PR63_CLOUD_ValidarMantenimientoTriggerTRefreshMaestroTable_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC_FT_26 Validar mantenimiento trigger TRefreshMaestroTable
		Desc:
		Verificar que el mantenimiento del trigger TRefreshMaestroTable (Data Factory) se haya ejecutado y la tabla de la BD Azure ERDD se actualice.
		@Valeria Fernandez
		@date   2025/05/27/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
		
		utils.sql.SQLUtil dbFCDASPFR = tr.reintentarConexion();
		
		//AVEBQA
		String countMCRS1 = "SELECT COUNT (*) AS MCRS_ONPR1 FROM XXFC.XXFC_MAESTRO_DE_CRS_V";
		
		//FCDASPFR
		String countMCRS2 = "SELECT COUNT (*) AS MCRS_ERDD_ST FROM dbo.CO_ERDD_MCRS_STAGING";
		
        //MDB_ERDD_PromotionCloud
		String countMCRS3 = "SELECT COUNT (*) AS MCRS_ERDD FROM dbo.CO_ERDD_MCRS";
        
		String clientId = data.get("clientId");
		String clientSecret = data.get("clientSecret");
		String tenantId = data.get("tenantId");
        String subscriptionId = data.get("subscriptionId");
        String resourceGroup = data.get("resourceGroup");
        String dataFactoryName = data.get("dataFactoryName");
        String triggerName = data.get("triggerName");
		int timeout_Trigger = Integer.parseInt(data.get("timeout_Trigger"));
        boolean validaStatus=false;
        
        //***************************Paso 1*****************************************************************************
         addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
		
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Realizar la siguiente consulta en la BD MDB_ERDD_PromotionCloud: " + countMCRS1);
			
		 validaStatus = tr.queryBDAzure(countMCRS3, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String count3 = tr.getValueCampoQuery(countMCRS3, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "MCRS_ERDD");
		 
		 Thread.sleep(timeout_Trigger);
		 
		//***************************Paso 3*****************************************************************************
		 addStep("Ingresar a Data Factory");
		 
        TokenCredential tokenCredential = tr.loginDataFactoryAzure(clientId, tenantId, clientSecret);
		 
		//***************************Paso 4*****************************************************************************
		 addStep("Validar que el trigger TRefreshMaestroTable se haya ejecutado en las ultimas 24 hrs y tenga status exitoso");
		 
		 boolean seEjecuto = tr.consultaTriggerDFAzure(tokenCredential, subscriptionId, tenantId, resourceGroup, dataFactoryName, triggerName);
		 assertTrue("El trigger no se ejecutó en las últimas 24 horas", seEjecuto);
		 
		//***************************Paso 5*****************************************************************************
		 addStep("Ingresar a la BD FCDASPFR");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_DASPFR_MEX_MULTIFORMATO);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_DASPFR_MEX_MULTIFORMATO);
		 
		//***************************Paso 6*****************************************************************************
		 addStep("Realizar la siguiente consulta en la BD FCDASPFR: " + countMCRS1);
		 
		 validaStatus = tr.queryOracle(countMCRS1, dbFCDASPFR);
		 tr.processQueryInChunks(dbFCDASPFR, countMCRS1);
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String count1 = tr.getValueCampoOracle(countMCRS1, dbFCDASPFR, "MCRS_ONPR1");
		 
		//***************************Paso 7*****************************************************************************
		 addStep("Realizar la siguiente consulta en la BD MDB_ERDD_PromotionCloud (Azure): " + countMCRS2);
			
		 validaStatus = tr.queryBDAzure(countMCRS2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 String count2 = tr.getValueCampoQuery(countMCRS2, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "MCRS_ERDD_ST");
		 
		//***************************Paso 8*****************************************************************************
		 addStep("Realizar la siguiente consulta en la BD MDB_ERDD_PromotionCloud (Azure): " + countMCRS3);
		 
		 validaStatus = tr.queryBDAzure(countMCRS3, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 count3 = tr.getValueCampoQuery(countMCRS3, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "MCRS_ERDD");
		 
		//***************************Paso 9*****************************************************************************
		 addStep("Verificar que los resultados de las consultas de los pasos 6 y 7 sean iguales");
		 
		 validaStatus = false;
		 if(!count1.equals(count2)) {
			 validaStatus =true;
		 }
		 
		 assertFalse("No se actualizo CO_ERDD_MCRS de la BD MDB_ERDD_PromotionCloud", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La cantidad de registros de XXFC_MAESTRO_DE_CRS_V: " + count1 + "y CO_ERDD_MCRS_STAGING: " + count2);
		 
		//***************************Paso 10*****************************************************************************
		 addStep("Verificar que los resultados de las consultas de los pasos 7 y 8 sean iguales");
		 testCase.addBoldTextEvidenceCurrentStep("Nota: Los registros de la tabla CO_ERDD_MCRS_STAGING no se deben duplicar");
		 
		 if(!count2.equals(count3)) {
			 validaStatus =true;
		 }
		 
		 assertFalse("No se actualizo CO_ERDD_MCRS_STAGING de la BD MDB_ERDD_PromotionCloud", validaStatus);
		 testCase.addTextEvidenceCurrentStep("La cantidad de registros de CO_ERDD_MCRS_STAGING: " + count2 + " y CO_ERDD_MCRS: " + count3);
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_26 Validar mantenimiento trigger TRefreshMaestroTable";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que el mantenimiento del trigger TRefreshMaestroTable (Data Factory) se haya ejecutado y la tabla de la BD Azure ERDD se actualice.";
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
				+ "3. Tener un registro de tienda digital\r\n"
				+ "4. Tener acceso y activar el recurso de azure OXZRGERDDQ01\r\n"
				+ "5. Acceso a Data Factory OXZADFERDDQAP01";
	
	}
}
package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

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

public class ATC_001_PR63_CLOUD_IdentificarTiendaFisica extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	public void ATC_001_PR63_CLOUD_IdentificarTiendaFisica_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-001 Identificar tienda fisica
		Desc:
		Prueba de componente para verificar bandera de tienda fisica o digital en la BD de Azure
		En este caso de prueba se verifica que el registro en BD de Azure el campo DIGITAL=N
		@Valeria Fernandez
		@date   2024/12/13*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
        
        //MDB_ERDD_PromotionCloud
        String queryStatusDigitalN = "SELECT * FROM dbo.CO_PRM_STR_DCM_CFG WHERE DEPARTMENT='" +data.get("plaza")+ "' AND STORE='" +data.get("tienda")+ "' AND DIGITAL='" +data.get("digital")+ "'";
        boolean validaStatus;
        
        //***************************Paso 1*****************************************************************************
		 addStep("Accesar BD MDB_ERDD_PromotionCloud (Azure)");
	
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud);
		 
		//***************************Paso 2*****************************************************************************
		 addStep("Validar que la tienda del documento a enviar sea fisica, con la consulta: " + queryStatusDigitalN);
			
		 validaStatus = tr.queryBDAzure(queryStatusDigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "No");
		 assertTrue("No se muestra el estatus esperado.", validaStatus);
		 tr.getValueCampoQuery(queryStatusDigitalN, GlobalVariables.DB_JDBC_AZURE_MDB_ERDD_PromotionCloud, "DIGITAL");
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-001 Identificar tienda fisica";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Identificacion de tienda fisica mediante el campo DIGITAL de la tabla CO_PRM_STR_DCM_CFG";
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
		return "1. Acceso a BD MDB_ERDD_PromotionCloud (Azure)\r\n"
				+ "2. Tener acceso y activar el recurso de azure OXZRGERDDQ01";
	
	}
}
package WEB.cnt.scripts;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_010_CNT_Agregar_Campos_Distrito_y_EntidadFederativa extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_010_Agregar_Campos_Distrito_y_Entidad_Federativa(HashMap<String, String> data) throws Exception {
		
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String Tienda = data.get("Tienda");
		String query = "SELECT entidad_federativa, distrito, wm_run_id, wm_status,wm_rec_dt, division_fi,ceco, nombre FROM TIENDAS.XX_NOMCOM_CNT_SAP WHERE ceco='"+Tienda+"'";

		
		/*
		 * 										PASO 1
		 *****************************************************************************************/
		
		addStep("Acceder a la base de datos FCIASQA al esquema TIENDAS.");
		u = new SeleniumUtil(new ChromeTest(), true); 
		
		
		/*
		 * 										PASO 2
		 *****************************************************************************************/
		
		addStep("Ejecutar el siguiente query: SELECT entidad_federativa, distrito wm_run_id, wm_status,wm_rec_dt, bitacora_id, sociedad, division_fi,ceco, nombre FROM XX_NOMCOM_CNT_SAP");
		
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla");
		}
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		/*
		 * 										PASO 3
		 *****************************************************************************************/
		
		addStep("Entrar a la descripcion de la tabla y validar que se encuentren los campos Entidad Federativa y Distrito.");
		
		query = "SELECT column_name, data_length, data_type FROM all_tab_columns WHERE table_name = 'XX_NOMCOM_CNT_SAP'";
		ExecuteQuery = executeQuery(dbFCIAS, query);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_010_Agregar_Campos_Distrito_y_Entidad_Federativa";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_Agregar_Campos_Distrito_y_Entidad_Federativa";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
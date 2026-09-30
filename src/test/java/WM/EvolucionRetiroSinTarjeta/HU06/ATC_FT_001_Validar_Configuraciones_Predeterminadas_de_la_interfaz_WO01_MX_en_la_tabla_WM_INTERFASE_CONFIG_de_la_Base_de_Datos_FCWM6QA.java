package WM.EvolucionRetiroSinTarjeta.HU06;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_Validar_Configuraciones_Predeterminadas_de_la_interfaz_WO01_MX_en_la_tabla_WM_INTERFASE_CONFIG_de_la_Base_de_Datos_FCWM6QA extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validar_Configuraciones_Predeterminadas_de_la_interfaz_WO01_MX_en_la_tabla_WM_INTERFASE_CONFIG_de_la_Base_de_Datos_FCWM6QA_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Consultas de BD de FCWM6QA
				 * Desc:
				 * Validar Configuraciones Predeterminadas de la interfaz WO01_MX en la tabla WM_INTERFASE_CONFIG de la Base de Datos FCWM6QA
				 * @author Mónica Soto
				 * @date 06/02/2024
				 **/
				/*
				 * Utilerias
				 *********************************************************************/
				//BD dbFCWM6QA
				SQLUtil dbFCWM6QA = new SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_QAVIEW, GlobalVariables.DB_USER_FCWM6QA_QAVIEW,
						GlobalVariables.DB_PASSWORD_FCWM6QA_QAVIEW);
				
				/*
				 * Variables
				 ******************************************************************************************/

				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("La tabla WM_INTERFASE_CONFIG debe estar creada en la BD FCWM6QA");
				
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				String consulta_FCWM6QA = "SELECT INTERFASE,ENTIDAD,OPERACION,CATEGORIA,VALOR1,DESCRIPCION FROM WMUSER.WM_INTERFASE_CONFIG WHERE INTERFASE='WO01_MX' AND ENTIDAD='WM' AND OPERACION IN ('ATRIBUTO04','CURRENCY_CODE','DEPTH_DAYS','ENTERED_DR','OPERATION','PERTENECE_A','SIZE_SPLIT','STATUS_FLAG','WAIT_TIME','WM_CODE','WM_STATUS_INITIAL','WM_STATUS_SUCCESS')AND CATEGORIA='CONFIG'";
				System.out.println("Consulta: " + consulta_FCWM6QA);
				
				SQLResult ExecuteQuery = executeQuery(dbFCWM6QA, consulta_FCWM6QA);

				boolean conexiondbFCWM6QA  = true;
				conexiondbFCWM6QA  = ExecuteQuery.isEmpty();

				addStep("Se realiza la conexión a la BD de FCWM6QA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: FCWM6QA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWM6QA_QAVIEW);
				
				addStep("Se Validan que los datos de los campos concuerdan correctamente a las configuraciones predeterminadas de la WO01_MX");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

				assertFalse(conexiondbFCWM6QA, "La conexion no fue exitosa");
			}


	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Configuraciones Predeterminadas de la interfaz WO01_MX en la tabla WM_INTERFASE_CONFIG de la Base de Datos FCWM6QA";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validar_Configuraciones_Predeterminadas_de_la_interfaz_WO01_MX_en_la_tabla_WM_INTERFASE_CONFIG_de_la_Base_de_Datos_FCWM6QA_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla WM_INTERFASE_CONFIG debe estar creada en la BD FCWM6QA\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

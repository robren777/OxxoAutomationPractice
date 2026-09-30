package WM.EvolucionRetiroSinTarjeta.HU23;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.password.PasswordUtil;

import java.util.HashMap;

import EBS.util.GlobalVariables;

import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validar_la_Existencia_de_la_tabla_TPE_FR_TRANSACTION_RST_en_la_BD_FCWMREQA extends BaseExecution{
 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_la_Existencia_de_la_tabla_TPE_FR_TRANSACTION_RST_en_la_BD_FCWMREQA_test (HashMap<String, String> data)
		throws Exception{

			/**
			 * Consultas de BD de FCWMREQA
			 * Desc:
			 * Validar la existencia de la tabla TPE_FR_TRANSACTION_RST en la BD FCWMREQA
			 * @author Mónica Soto
			 * @date 22/03/2024
			 **/

			/*
			 * Utilerias
			 *********************************************************************/
			SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
					GlobalVariables.DB_PASSWORD_FCWMREQA);
			
			/*
			 * Variables
			 ******************************************************************************************/
	
			testCase.setTest_Description(data.get("id") + data.get("description"));
			testCase.setPrerequisites("La tabla TPE_FR_TRANSACTION_RST debe estar creada en la BD FCWMREQA");
			
			System.out.println(
					"*****************************************PASO 1 y 2************************************************");
			String consulta_FCWMREQA = "SELECT OWNER,TABLE_NAME FROM ALL_TABLES WHERE OWNER = 'TPEREP' AND TABLE_NAME='TPE_FR_TRANSACTION_RST'";
			System.out.println("Consulta: " + consulta_FCWMREQA);
	
			SQLResult ExecuteQuery = executeQuery(dbFCWMREQA, consulta_FCWMREQA);
	
			boolean conexiondbFCWMREQA  = true;
			conexiondbFCWMREQA  = ExecuteQuery.isEmpty();
	
			addStep("Se realiza la conexión a la BD de FCWMREQA de manera exitosa.");
			testCase.addTextEvidenceCurrentStep("Conexión: FCWMREQA");
			testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
			
			addStep("Realizar la siguiente consulta para validar la existencia de la tabla TPE_FR_TRANSACTION_RST");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
	
			assertFalse(conexiondbFCWMREQA, "La conexion no fue exitosa");
			
		}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar la existencia de la tabla TPE_FR_TRANSACTION_RST en la BD FCWMREQA";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_la_Existencia_de_la_tabla_TPE_FR_TRANSACTION_RST_en_la_BD_FCWMREQA_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla TPE_FR_TRANSACTION_RST debe estar creada en la BD FCWMREQA\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

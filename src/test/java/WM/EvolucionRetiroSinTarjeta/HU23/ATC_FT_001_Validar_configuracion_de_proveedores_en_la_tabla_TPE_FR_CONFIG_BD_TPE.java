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

public class ATC_FT_001_Validar_configuracion_de_proveedores_en_la_tabla_TPE_FR_CONFIG_BD_TPE extends BaseExecution{
 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validar_configuracion_de_proveedores_en_la_tabla_TPE_FR_CONFIG_BD_TPE_test (HashMap<String, String> data)
		throws Exception{

			/**
			 * Consultas de BD de FCTPEQA_S1_MTY
			 * Desc:
			 * Validar configuración de proveedores en la tabla TPE_FR_CONFIG BD TPE
			 * @author Mónica Soto
			 * @date 22/03/2024
			 **/

			/*
			 * Utilerias
			 *********************************************************************/
			//BD FCTPEQA_S1_MTY
			SQLUtil dbFCTPEQA_S1_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
					GlobalVariables.DB_PASSWORD_FCTPE);
			
			/*
			 * Variables
			 ******************************************************************************************/
	
			testCase.setTest_Description(data.get("id") + data.get("description"));
			testCase.setPrerequisites("La tabla TPE_FR_CONFIG debe estar creada en la BD FCTPEQA_S1_MTY");
			
			System.out.println(
					"*****************************************PASO 1 y 2************************************************");
			String consulta_dbFCTPEQA_S1_MTY = "SELECT * FROM TPEUSER.TPE_FR_CONFIG WHERE operation = 'TPECOPIA' AND code = 'replica' AND value1 = 'A'";
			System.out.println("Consulta: " + consulta_dbFCTPEQA_S1_MTY);
	
			SQLResult ExecuteQuery = executeQuery(dbFCTPEQA_S1_MTY, consulta_dbFCTPEQA_S1_MTY);
	
			boolean conexiondbFCTPEQA_S1_MTY  = true;
			conexiondbFCTPEQA_S1_MTY  = ExecuteQuery.isEmpty();
	
			addStep("Se realiza la conexión a la BD de FCTPEQA_S1_MTY de manera exitosa.");
			testCase.addTextEvidenceCurrentStep("Conexión: FCTPEQA_S1_MTY");
			testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);
			
			addStep("Realizar la siguiente Consulta para validar la configuracion lde proveedores en la tabla TPE_FR_CONFIG");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
	
			assertFalse(conexiondbFCTPEQA_S1_MTY, "La conexion no fue exitosa");
			
		}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar configuración de proveedores en la tabla TPE_FR_CONFIG BD TPE.";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validar_configuracion_de_proveedores_en_la_tabla_TPE_FR_CONFIG_BD_TPE_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla TPE_FR_CONFIG debe estar creada en la BD FCTPEQA_S1_MTY\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

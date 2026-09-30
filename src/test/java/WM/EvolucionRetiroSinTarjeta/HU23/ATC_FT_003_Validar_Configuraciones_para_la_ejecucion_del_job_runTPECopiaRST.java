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

public class ATC_FT_003_Validar_Configuraciones_para_la_ejecucion_del_job_runTPECopiaRST extends BaseExecution{
 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Validar_Configuraciones_para_la_ejecucion_del_job_runTPECopiaRST_test (HashMap<String, String> data)
		throws Exception{

			/**
			 * Job runTPECopiaRST.sh Folder TQA_TRANSAC_ELEC
			 * Desc:
			 * Validar Configuraciones para la ejecución del job runTPECopiaRST.sh
			 * @author Mónica Soto
			 * @date 26/03/2024
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
			String consulta_FCWMREQA = "SELECT ID, TABLA, STATUS, TABLA_STATUS, TIPO, FECHA_COPIADA, OWNER, DBLINK, DIAS_ESPACIO_DEPURACION FROM TPEREP.TPE_TABLAS_COPIA WHERE status = 'P' and tabla_status = 'A' AND TABLA = 'TPE_FR_TRANSACTION'";
			System.out.println("Consulta: " + consulta_FCWMREQA);
	
			SQLResult ExecuteQuery = executeQuery(dbFCWMREQA, consulta_FCWMREQA);
	
			boolean conexiondbFCWMREQA  = true;
			conexiondbFCWMREQA  = ExecuteQuery.isEmpty();
	
			addStep("Se realiza la conexión a la BD de FCWMREQA de manera exitosa.");
			testCase.addTextEvidenceCurrentStep("Conexión: FCWMREQA");
			testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
			
			addStep("Realizar la siguiente consulta en la tabla TPE_TABLAS_COPIA para validar las configuraciones necesarias para la ejecucion del job runTPECopiaRST.sh con los siguientes datos en los campos ");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
	
			assertFalse(conexiondbFCWMREQA, "La conexion no fue exitosa");
			
		}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Configuraciones para la ejecución del job runTPECopiaRST.sh";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Validar_Configuraciones_para_la_ejecucion_del_job_runTPECopiaRST_test";
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

package WM.EvolucionRetiroSinTarjeta.HU06;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_Validar_Correos_Configurados_para_Alerta_de_Errores_de_la_WO01_MX_en_cada_uno_de_los_perfiles extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_Validar_Correos_Configurados_para_Alerta_de_Errores_de_la_WO01_MX_en_cada_uno_de_los_perfiles_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Job runTPECopiaRST.sh Folder TQA_TRANSAC_ELEC / job runFT_SINTARJ_CNKTA.sh, runFT_SINTARJ_FTECH.sh, runFT_SINTARJ_HPAY.sh folder TQA_BAJO_DEMANDA / job runWO01_MX.sh folder TQA_TRANSAC_ELEC
				 * Desc:
				 * Validar Correos Configurados para Alerta de Errores de la WO01_MX en cada uno de los perfiles
				 * @author Mónica Soto
				 * @date 08/02/2024
				 **/

				/*
				 * Utilerias
				 *********************************************************************/
				//BD dbFCWMLOG
				SQLUtil dbFCWMLOG = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2, GlobalVariables.DB_USER_FCWMLQA_WMLOG2,
						GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG2);
				
				/*
				 * Variables
				 ******************************************************************************************/

				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("La tabla WM_LOG_GROUP debe estar creada en la BD FCWMLOG");

				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				String consulta_FCWMLOG = "SELECT * FROM WMLOG.WM_LOG_GROUP WHERE INTERFACE_NAME='WO01_MX' ORDER BY GROUP_ID";
				System.out.println("Consulta: " + consulta_FCWMLOG);

				SQLResult ExecuteQuery = executeQuery(dbFCWMLOG, consulta_FCWMLOG);

				boolean conexiondbFCWMLOG  = true;
				conexiondbFCWMLOG  = ExecuteQuery.isEmpty();

				addStep("Se realiza la conexión a la BD de FCWMLOG de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: FCWMLOG");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2);
				
				addStep("Realizar la siguiente consulta para validar que se encuentren configurados los perfiles de Alerta de Errores \r\n"
						+ "				GROUP_ID, GROUP_NAME, GROUP_DESC, INTERFACE_NAME\r\n"
						+ "				???, WMREP, Administrador de Replica de TPREP, WO01_MX\r\n"
						+ "				???, WMADMIN, Administrador de webMethods, WO01_MX\r\n"
						+ "				???, ORAFIN, Administrador de Oracle Finanzas, WO01_MX\r\n"
						+ "				???, WMLOG, Administrador de webMethods, WO01_MX");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

				assertFalse(conexiondbFCWMLOG, "La conexion no fue exitosa");
				
				System.out.println(
						"*****************************************PASO 3************************************************");
				
				String consulta_groupID_FCWMLOG = "SELECT * FROM WMLOG.WM_LOG_USER_GROUP WHERE GROUP_ID IN (SELECT GROUP_ID FROM WMLOG.WM_LOG_GROUP WHERE INTERFACE_NAME='WO01_MX')";
				SQLResult Execute_GroupId_Query = executeQuery(dbFCWMLOG, consulta_groupID_FCWMLOG);

				boolean conexion_group_FCWMLOG  = true;
				conexion_group_FCWMLOG  = Execute_GroupId_Query.isEmpty();
				
				addStep("Realizar la siguiente consulta para validar que cada uno de los GROUP_ID obtenidos del paso anterior estén relacionados a un USER_ID en la tabla WM_LOG_USER_GROUP");
				testCase.addQueryEvidenceCurrentStep(Execute_GroupId_Query);

				assertFalse(conexion_group_FCWMLOG, "La conexion no fue exitosa");
				
				
				System.out.println(
						"*****************************************PASO 4************************************************");
	
				String consulta_userId_FCWMLOG = "SELECT * FROM WMLOG.WM_LOG_USER WHERE USER_ID IN (SELECT USER_ID FROM WMLOG.WM_LOG_USER_GROUP WHERE GROUP_ID IN (SELECT GROUP_ID FROM WMLOG.WM_LOG_GROUP WHERE INTERFACE_NAME='WO01_MX'))";
				SQLResult Execute_userId_Query = executeQuery(dbFCWMLOG, consulta_userId_FCWMLOG);

				boolean conexion_user_FCWMLOG  = true;
				conexion_user_FCWMLOG  = Execute_userId_Query.isEmpty();
				
				addStep("Realizar la siguiente Consulta para validar que en la tabla WM_LOG_USER cada USER_ID se encuentra registrado con un NAME y EMAIL que se usara para su notificación de Alerta de Errores.");
				testCase.addQueryEvidenceCurrentStep(Execute_userId_Query);

				assertFalse(conexion_user_FCWMLOG, "La conexion no fue exitosa");

			}


	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Correos Configurados para Alerta de Errores de la WO01_MX en cada uno de los perfiles";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_Validar_Correos_Configurados_para_Alerta_de_Errores_de_la_WO01_MX_en_cada_uno_de_los_perfiles_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla WM_LOG_GROUP debe estar creada en la BD FCWMLOG\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

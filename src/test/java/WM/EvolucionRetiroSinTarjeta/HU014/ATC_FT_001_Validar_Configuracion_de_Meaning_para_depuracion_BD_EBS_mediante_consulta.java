package WM.EvolucionRetiroSinTarjeta.HU014;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_Validar_Configuracion_de_Meaning_para_depuracion_BD_EBS_mediante_consulta extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validar_Configuracion_de_Meaning_para_depuracion_BD_EBS_mediante_consulta_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Consulta de BD de AVEBQA
				 * Desc:
				 * Validar Configuracion de MEANING para depuracion BD EBS mediante consulta
				 * @author Mónica Soto
				 * @date 21/02/2024
				 **/

				/*
				 * Utilerias
				 *********************************************************************/
				//BD AVEBQA
				SQLUtil AVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
											GlobalVariables.DB_PASSWORD_AVEBQA);
				/*
				 * Variables
				 ******************************************************************************************/
		
				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("Que exista la tabla FND_LOOKUP_VALUES_VL en la bd AVEBQA");
				
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				String consulta_AVEBQA = "SELECT LOOKUP_TYPE,LOOKUP_CODE,MEANING FROM APPS.FND_LOOKUP_VALUES_VL WHERE LOOKUP_TYPE = 'XXFC_GL_RST_CONFIG' AND LOOKUP_CODE = 'RESP_MESES'";
				System.out.println("Consulta: " + consulta_AVEBQA);
		
				SQLResult ExecuteQuery = executeQuery(AVEBQA, consulta_AVEBQA);
		
				boolean conexiondbAVEBQA  = true;
				conexiondbAVEBQA  = ExecuteQuery.isEmpty();
		
				addStep("Se realiza la conexión a la BD de AVEBQA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Realizar la siguiente consulta para validar las configuraciones del campo MEANING='30' para la depuracion de la Base de Datos de EBS");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
				assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
			}


	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Configuracion de MEANING para depuracion BD EBS mediante consulta";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validar_Configuracion_de_Meaning_para_depuracion_BD_EBS_mediante_consulta_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla FND_LOOKUP_VALUES_VL debe estar creada en la BD AVEBQA\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

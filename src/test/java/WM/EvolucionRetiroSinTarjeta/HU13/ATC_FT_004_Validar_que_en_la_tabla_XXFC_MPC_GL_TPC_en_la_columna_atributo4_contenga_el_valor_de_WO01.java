package WM.EvolucionRetiroSinTarjeta.HU13;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_Validar_que_en_la_tabla_XXFC_MPC_GL_TPC_en_la_columna_atributo4_contenga_el_valor_de_WO01 extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Validar_que_en_la_tabla_XXFC_MPC_GL_TPC_en_la_columna_atributo4_contenga_el_valor_de_WO01_test (HashMap<String, String> data)
			throws Exception{

				/**
				 * Consulta de BD de AVEBQA
				 * Desc:
				 * Validar que en la tabla  XXFC_MPC_GL_TPC en la columna atributo4 contenga el valor de WO01
				 * @author Mónica Soto
				 * @date 20/03/2024
				 **/

				/*
				 * Utilerias
				 *********************************************************************/
				
				//BD AVEBQA
				SQLUtil dbAVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
											GlobalVariables.DB_PASSWORD_AVEBQA);
				/*
				 * Variables
				 ******************************************************************************************/

				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("La tabla FND_LOOKUP_VALUES_VL debe estar creada en la BD AVEBQA");
					
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				
				String consulta_dbAVEBQA = "select distinct atributo4 from apps.XXFC_MPC_GL_TPC where atributo4 is not null";
				System.out.println("Consulta: " + consulta_dbAVEBQA);

				SQLResult Execute_dbAVEBQA_Query = executeQuery(dbAVEBQA, consulta_dbAVEBQA);
				
				boolean conexiondbAVEBQA  = true;
				conexiondbAVEBQA  = Execute_dbAVEBQA_Query.isEmpty();
				
				addStep("Ingresar a la BD de AVEBQA");
				testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Realizar la siguiente consulta para validar que tenga el valor WO01  de la nueva interfaz");
				testCase.addTextEvidenceCurrentStep("Si es WO01 se asume que son registros que vienen de la tabla réplica de la transaccional. Si no tiene de método de pago WO01, tomará los registros de la TPC..");

				testCase.addQueryEvidenceCurrentStep(Execute_dbAVEBQA_Query);
				
				assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
				
				
			}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar que en la tabla  XXFC_MPC_GL_TPC en la columna atributo4 contenga el valor de WO01";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Validar_que_en_la_tabla_XXFC_MPC_GL_TPC_en_la_columna_atributo4_contenga_el_valor_de_WO01_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla fnd_lookup_values_vl debe estar creada en la BD AVEBQA";
	}

	@AfterMethod
	public void afterMethod() {

	}
	
}

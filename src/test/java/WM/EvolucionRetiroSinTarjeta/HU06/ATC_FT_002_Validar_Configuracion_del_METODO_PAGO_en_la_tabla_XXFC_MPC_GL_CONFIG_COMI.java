package WM.EvolucionRetiroSinTarjeta.HU06;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validar_Configuracion_del_METODO_PAGO_en_la_tabla_XXFC_MPC_GL_CONFIG_COMI extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_Configuracion_del_METODO_PAGO_en_la_tabla_XXFC_MPC_GL_CONFIG_COMI_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Consultas de BD de AVEBQA
				 * Desc:
				 * Validar Configuracion del METODO_PAGO en la tabla XXFC_MPC_GL_CONFIG_COMI
				 * @author Mónica Soto
				 * @date 06/02/2024
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
				testCase.setPrerequisites("La tabla XXFC_MPC_GL_CONFIG_COMI debe estar creada en la BD AVEBQA");

				//PasswordUtil.generateEncryptPass();
				
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				String consulta_AVEBQA = "SELECT METODO_PAGO, FORMA_PAGO,PERTENECE_A FROM XXFC.XXFC_MPC_GL_CONFIG_COMI WHERE ESTADO='A' AND APLICA_IVA = 'y' AND TIPO_COMISION = 'V'";
				System.out.println("Consulta: " + consulta_AVEBQA);

				SQLResult ExecuteQuery = executeQuery(AVEBQA, consulta_AVEBQA);

				boolean conexiondbAVEBQA  = true;
				conexiondbAVEBQA  = ExecuteQuery.isEmpty();

				addStep("Se realiza la conexión a la BD de AVEBQA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Validar la configuracion del Campo Metodo_Pago en la tabla XXFC_MPC_GL_CONFIG_COMI se encuentre en NA");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
				
				assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
			}


	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Configuracion del METODO_PAGO en la tabla XXFC_MPC_GL_CONFIG_COMI";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_Configuracion_del_METODO_PAGO_en_la_tabla_XXFC_MPC_GL_CONFIG_COMI_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla XXFC_MPC_GL_CONFIG_COMI debe estar creada en la BD AVEBQA\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

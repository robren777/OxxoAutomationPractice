package EBS.pagoQrBazMVP3.US01;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

@AzureTestInfo(areaPath = "I23013 - Pago con QR en OXXO", iterationPath = "I23013 - Pago con QR en OXXO", tags = "ATC_FC_001;QR_BAZ", userStoryId = 73503)

/**
 * DESCRIPCION: Se valida el campo CODE, contenga el dato de WM_TPG en la BD FCTPEQA.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-004 Validar que IMA
 * este apuntando a servidores de QRO
 *
 * PREREQUISITOS: - Que este configurado el site a MTY o QRO - Tener permiso
 * para el acceso a la BD
 *
 * INSUMOS: - Usuario y Contraseña
 *
 * @author Antonio Martínez
 * @date 09/Abril/2025
 */
public class ATC_FT_004_ValidarQueImaEsteApuntandoAServidoresDeQro extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarQueImaEsteApuntandoAServidoresDeQro_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Pago con QR en OXXO");

		/* Utils **************************************************************/

		/* Data Provider **************************************************************/

		String code = data.get("CODE");

		/* Variables **************************************************************/
		
		/* Validate Xpos ready to start execution, do not move ****************/

		// Script Code Begin
		try {
			// Datos del acceso a la DB
			SQLUtil sqlUtil = new SQLUtil(GlobalVariables.DB_HOST_FCWMQA, GlobalVariables.DB_USER_FCWMQA,
					GlobalVariables.DB_PASSWORD_FCWMQA);

			addStep("Ejecutar la consulta SQL y verificar los datos para el ID " + code);
			String query = " SELECT * FROM (SELECT ID,ENTITY,OPERATION,CODE,CATEGORY,DESCRIPTION FROM WMUSER.WM_FT_TRN_CONFIG WHERE ID  = "
					+ "'" + code + "'" + ") WHERE ROWNUM <= 10";
			SQLResult result = sqlUtil.executeQuery(query);
			testCase.addQueryEvidenceCurrentStep(result);

			try {
				assertFalse(result.isEmpty(), "No se encontraron resultados para el ID: " + code);
				testCase.addTextEvidenceCurrentStep("Se mostraron correctamente los resultados de la consulta");

				addStep("Verificar el campo ID");

				String codigo = result.getData(0, "ID");
				testCase.addTextEvidenceCurrentStep("El valor del campo es : " + codigo);
				String queryValue = " SELECT * FROM (SELECT ID,ENTITY,OPERATION,CODE,CATEGORY,DESCRIPTION FROM WMUSER.WM_FT_TRN_CONFIG WHERE ID  = "
						+ "'" + code + "'" + ") WHERE ROWNUM <= 1";
				SQLResult resultValue = sqlUtil.executeQuery(queryValue);
				testCase.addQueryEvidenceCurrentStep(resultValue);

				assertEquals(codigo, "5", "El id BAZ no coincide");
				testCase.addTextEvidenceCurrentStep("El código es correcto: " + codigo);

			} catch (AssertionError e) {
				testCase.addTextEvidenceCurrentStep("No se encontraron resultados para el valor ID: " + code);
				throw e;
			}

		} catch (SQLException e) {

			e.printStackTrace();
			addStep("Error en la ejecución de la consulta SQL");
			throw new RuntimeException("Error al ejecutar la consulta SQL", e);
		}

		// Script Code End
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Tener el  punto de venta ambientado correctamente ," + " 2. Tener habilitada la forma de pago QR ,"
				+ " 3. Tener encendida la funcionalidad oauth";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarQueImaEsteApuntandoAServidoresDeQro";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la configuracion de IMA apunte a QRO";
	}
}

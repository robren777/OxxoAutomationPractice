package EBS.pagoQrBazMVP3.US01;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.File;
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
 * DESCRIPCION: Se valida el campo CODE, contenga el dato de WM_TPG en la BD
 * FCTPEQA.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-018 Validar puertos
 * HTTPS
 *
 * PREREQUISITOS: - Que este configurado el site a MTY o QRO - Tener permiso
 * para el acceso a la BD
 *
 * INSUMOS: - Usuario y Contraseña
 *
 * @author Antonio Martínez
 * @date 09/Abril/2025
 */
public class ATC_FT_018_ValidarPuertosHttps extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_ValidarPuertosHttps_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Pago con QR en OXXO");

		/* Utils **************************************************************/

		/* Data Provider **************************************************************/

		String application = data.get("application");
		String entity = data.get("entity");
		String operation = data.get("operation");
		String value1 = data.get("value1");

		/* Variables **************************************************************/
		String ticketAntes;
		String ticketDespues;
		File archivo;

		/* Validate Xpos ready to start execution, do not move ****************/

		// Script Code Begin
		try {
			// Datos del acceso a la DB
			SQLUtil sqlUtil = new SQLUtil(GlobalVariables.DB_HOST_FCTPEQA, GlobalVariables.DB_USER_FCTPEQA,
					GlobalVariables.DB_PASSWORD_FCTPEQA);

			addStep("Ejecutar la consulta SQL y verificar los datos para Validar puertos HTTPS " + application);
			String query = "SELECT APPLICATION, ENTITY, OPERATION, COMPANY, CODE, CATEGORY, VALUE1 FROM TPEUSER.TPE_MCP_CONFIG \r\n"
					+ "            WHERE APPLICATION = " + "'" + application + "'" + "AND ENTITY =" + "'" + entity + "'"
					+ "AND OPERATION =" + "'" + operation + "'";

			SQLResult result = sqlUtil.executeQuery(query);
			testCase.addQueryEvidenceCurrentStep(result);

			try {
				assertFalse(result.isEmpty(), "No se encontraron resultados para el campo APLICATION: " + application);
				testCase.addTextEvidenceCurrentStep("Se mostraron correctamente los resultados de la consulta");

				addStep("Verificar el campo VALUE1");

				String codigo = result.getData(7, "VALUE1");
				testCase.addTextEvidenceCurrentStep("El valor del campo es : " + codigo);
				String queryValue = "SELECT APPLICATION, ENTITY, OPERATION, CODE, VALUE1 FROM TPEUSER.TPE_MCP_CONFIG \r\n"
						+ "            WHERE APPLICATION = " + "'" + application + "'" + "AND ENTITY =" + "'" + entity
						+ "'" + "AND OPERATION =" + "'" + operation + "'" + "AND VALUE1 = " + "'" + value1 + "'";
				SQLResult resultValue = sqlUtil.executeQuery(queryValue);
				testCase.addQueryEvidenceCurrentStep(resultValue);

				assertEquals(codigo,
						"https://qa-prov-baz-se.femcom.net/banco-azteca/socios-comerciales/corresponsalia-bancaria/pagos-digitales/seguridad/v1/aplicaciones/llaves",
						"La url  no coincide");
				testCase.addTextEvidenceCurrentStep("La url correcta: es " + codigo);

			} catch (AssertionError e) {
				testCase.addTextEvidenceCurrentStep("No se encontraron resultados para URL: " + value1);
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
		return "1. Que este configurado el site a MTY o QRO," + " 2. Tener permiso para el acceso a la BD";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_018_ValidarPuertosHttps";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida el campo CODE, contenga el dato de WM_TPG en la BD FCTPEQA.";
	}
}

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
 * DESCRIPCION: Validar la regla de la fecha de transacción.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-016 Validar la regla
 * de la fecha de transacción.
 *
 * PREREQUISITOS: - Que este configurado el site a MTY o QRO - Tener permiso
 * para el acceso a la BD
 *
 * INSUMOS: - Usuario y Contraseña
 *
 * @author Antonio Martínez
 * @date 09/Abril/2025
 */
public class ATC_FT_016_ValidarLaReglaDeLaFechaDeTransaccion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ValidarLaReglaDeLaFechaDeTransaccion_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Pago con QR en OXXO");

		/* Utils **************************************************************/

		/* Data Provider **************************************************************/

		String application = data.get("application");
		String operation = data.get("operation");
		String reversed = data.get("reversed");
		String admin_date = data.get("admin_date");
		String admin_date_like = data.get("admin_date_like");

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

			addStep("Ejecutar la consulta SQL y verificar los datos para Validar la fecha administrativa "
					+ admin_date);
			String query = "SELECT * FROM (SELECT FOLIO,APPLICATION,ENTITY,OPERATION,VALUE,ADMIN_DATE,WM_DESCRIPTION FROM TPEUSER.TPG_TRANSACTIONS \r\n"
					+ "            WHERE APPLICATION = " + "'" + application + "'" + "AND OPERATION =" + "'" + operation
					+ "'" + "AND REVERSED IN" + "(" + reversed + ")" + "ORDER BY ADMIN_DATE DESC) WHERE ROWNUM <= 10";
			SQLResult result = sqlUtil.executeQuery(query);
			testCase.addQueryEvidenceCurrentStep(result);

			try {
				assertFalse(result.isEmpty(), "No se encontraron resultados para el campo APLICATION: " + application);
				testCase.addTextEvidenceCurrentStep("Se mostraron correctamente los resultados de la consulta");

				addStep("Verificar el campo ADMIN_DATE");

				String codigo = result.getData(0, "ADMIN_DATE");
				testCase.addTextEvidenceCurrentStep("El valor del campo es : " + admin_date);
				String queryValue = "SELECT * FROM (SELECT ADMIN_DATE FROM TPEUSER.TPG_TRANSACTIONS  \r\n"
						+ "         WHERE APPLICATION = " + "'" + application + "'" + "AND OPERATION =" + "'"
						+ operation + "'" + "AND REVERSED IN" + "(" + reversed + ")"
						+ "ORDER BY ADMIN_DATE DESC) WHERE ROWNUM <= 10";
				SQLResult resultValue = sqlUtil.executeQuery(queryValue);
				testCase.addQueryEvidenceCurrentStep(resultValue);

				assertEquals(codigo, "2025-06-05 00:00:00", "La fecha Administrativa  no coincide");
				testCase.addTextEvidenceCurrentStep("La fecha Administrativa es: " + codigo);

			} catch (AssertionError e) {
				testCase.addTextEvidenceCurrentStep(
						"No se encontraron resultados para la Fecha Administrativa: " + admin_date);
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
		return "1. 1-	Confirmar formato de fecha administrativa," + " 2. Tener permiso para el acceso a la BD";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarLaReglaDeLaFechaDeTransaccion";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se Valida la regla de la fecha de transacción..";
	}
}

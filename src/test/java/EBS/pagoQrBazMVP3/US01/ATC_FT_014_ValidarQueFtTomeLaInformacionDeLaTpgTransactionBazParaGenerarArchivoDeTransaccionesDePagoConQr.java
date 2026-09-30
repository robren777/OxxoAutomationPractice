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
 * DESCRIPCION: Validar que FT tome la información de la TPG_Transaction_BAZ
 * para generar archivo de transacciones de Pago con QR
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-014 Validar que FT
 * tome la información de la TPG_Transaction_BAZ para generar archivo de
 * transacciones de Pago con QR
 *
 * PREREQUISITOS: - Que este configurado el site a MTY o QRO - Tener permiso
 * para el acceso a la BD - Previamente la Ejecucion de jobs FT
 *
 * INSUMOS: - Usuario y Contraseña
 *
 * @author Antonio Martínez
 * @date 09/Abril/2025
 */
public class ATC_FT_014_ValidarQueFtTomeLaInformacionDeLaTpgTransactionBazParaGenerarArchivoDeTransaccionesDePagoConQr
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_ValidarQueFtTomeLaInformacionDeLaTpgTransactionBazParaGenerarArchivoDeTransaccionesDePagoConQr_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Pago con QR en OXXO");

		/* Utils **************************************************************/

		/* Data Provider **************************************************************/

		String forma_pago = data.get("FORMA_PAGO");
		String cr_plaza = data.get("CR_PLAZA");
		String cr_tienda = data.get("CR_TIENDA");
		String entered_cr = data.get("ENTERED_CR");

		/* Variables **************************************************************/
		String ticketAntes;
		String ticketDespues;
		File archivo;

		/* Validate Xpos ready to start execution, do not move ****************/

		// Script Code Begin
		try {
			// Datos del acceso a la DB
			SQLUtil sqlUtil = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
					GlobalVariables.DB_PASSWORD_AVEBQA);

			addStep("Ejecutar la consulta SQL y verificar los datos para el folio " + forma_pago);
			String query = "SELECT FORMA_PAGO,CR_PLAZA,CR_TIENDA,ACCOUNTING_DATE,ENTERED_CR,CREATION_DATE,FOLIO_WM FROM APPS.XXFC_MPC_GL_TPC WHERE FORMA_PAGO ="
					+ "'" + forma_pago + "'" + "AND CR_PLAZA =" + "'" + cr_plaza + "'" + "AND CR_TIENDA =" + "'"
					+ cr_tienda + "'" + "AND ENTERED_CR in" + " ('" + entered_cr + ")";
			SQLResult result = sqlUtil.executeQuery(query);
			testCase.addQueryEvidenceCurrentStep(result);

			try {
				assertFalse(result.isEmpty(), "No se encontraron resultados para el folio: " + forma_pago);
				testCase.addTextEvidenceCurrentStep("Se mostraron correctamente los resultados de la consulta");

				addStep("Verificar el campo Forma de Pago");

				String codigo = result.getData(0, "FORMA_PAGO");
				testCase.addTextEvidenceCurrentStep("El valor del campo es : " + codigo);
				String queryValue = "SELECT FORMA_PAGO, CR_PLAZA, CR_TIENDA, ACCOUNTING_DATE, ENTERED_CR, CREATION_DATE, FOLIO_WM FROM APPS.XXFC_MPC_GL_TPC WHERE FORMA_PAGO ="
						+ "'" + forma_pago + "'" + "AND CR_PLAZA =" + "'" + cr_plaza + "'" + "AND CR_TIENDA =" + "'"
						+ cr_tienda + "'" + "AND ENTERED_CR in" + " ('" + entered_cr + ")";
				SQLResult resultValue = sqlUtil.executeQuery(queryValue);
				testCase.addQueryEvidenceCurrentStep(resultValue);

				assertEquals(codigo, "TTYP1813", "El código Forma de pago no coincide");
				testCase.addTextEvidenceCurrentStep("El código es correcto: " + codigo);

			} catch (AssertionError e) {
				testCase.addTextEvidenceCurrentStep(
						"No se encontraron resultados para el valor Forma de Pago: " + forma_pago);
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
		return "1. Que este configurado el site a MTY o QRO," + " 2. Tener permiso para el acceso a la BD,"
				+ " 3. Ejecucion de jobs FT";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_014_ValidarQueFtTomeLaInformacionDeLaTpgTransactionBazParaGenerarArchivoDeTransaccionesDePagoConQr";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que FT tome la información de la TPG_Transaction_BAZ para generar archivo de transacciones de Pago con QR";
	}
}

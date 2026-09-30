package EBS.devolucionesOxxoPay;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_008_ValidarMovimientosRetiroTablasXXFC_GL_RST_ENCA_XXFC_GL_RST_LINEAS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_ValidarMovimientosRetiroTablasXXFC_GL_RST_ENCA_XXFC_GL_RST_LINEAS_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String query1 = "SELECT id_rst_encabezado, forma_pago, cr_plaza, accounting_date "
				+ "FROM XXFC.XXFC_GL_RST_ENCA where forma_pago = 'PYIO49'";
		String query2 = "SELECT id_rst_linea, forma_pago, cr_plaza, cr_tienda, accounting_date "
				+ "FROM XXFC.XXFC_GL_RST_LINEAS where forma_pago = 'PYIO49' AND id_rst_encabezado = ''";

		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Conectarse a la base de datos EBS 12.2.4");

		// PASO 2
		addStep("Ejecutar el query");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(query1);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(query1);
		String formaPago = "";

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			formaPago = ValidChangeExec.getData(0, "forma_pago");
			System.out.println("Forma de Pago: " + formaPago);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + query1);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}

		// PASO 3
		addStep("Ejecutar el query");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(query2);
		SQLResult ValidChangeExec2 = dbAvebqa.executeQuery(query2);
		String formaPago2 = "";

		boolean ValidChangeRes2 = ValidChangeExec2.isEmpty();
		if (!ValidChangeRes2) {
			formaPago2 = ValidChangeExec2.getData(0, "forma_pago");
			System.out.println("Forma de pago: " + formaPago2);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec2);
		} else if (ValidChangeRes2) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + query2);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec2);
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener acceso a BD, Ejecutar el proceso de Dispersion";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que lleguen a la tabla XXFC_GL_RST_ENCA, XXFC_GL_RST_LINEAS movimientos de tipo retiro."
				+ "Prerrequisitos: Tener acceso a BD, Ejecutar el proceso de Dispersion";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_ValidarMovimientosRetiroTablasXXFC_GL_RST_ENCA_XXFC_GL_RST_LINEAS_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

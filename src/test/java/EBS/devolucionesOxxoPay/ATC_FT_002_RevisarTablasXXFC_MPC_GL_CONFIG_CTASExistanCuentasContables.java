package EBS.devolucionesOxxoPay;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_002_RevisarTablasXXFC_MPC_GL_CONFIG_CTASExistanCuentasContables extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_RevisarTablasXXFC_MPC_GL_CONFIG_CTASExistanCuentasContables_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ValidCuentas = "SELECT NOMBRE_CUENTA, CUENTA, AUXILIAR, TIPO" + " FROM xxfc.XXFC_MPC_GL_CONFIG_CTAS"
				+ " WHERE cuenta in ('15TS0125','51OI3807','15TO0026','DEVOXPAY')";

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
		addStep("Ejecutar el query ");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(ValidCuentas);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(ValidCuentas);
		String cuenta = "";

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			cuenta = ValidChangeExec.getData(0, "cuenta");
			System.out.println("Tipo Persona: " + cuenta);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + ValidCuentas);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ejecutar el script mediante soporte de la creacion de cuentas";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la correcta actualizacion en la 'Plantilla de Proveedores'."
				+ "Prerequisitos: Ejecutar el script mediante soporte de la creacion de cuentas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_RevisarTablasXXFC_MPC_GL_CONFIG_CTASExistanCuentasContables_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

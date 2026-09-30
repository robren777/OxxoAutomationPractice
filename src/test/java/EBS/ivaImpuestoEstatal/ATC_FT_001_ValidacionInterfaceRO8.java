package EBS.ivaImpuestoEstatal;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidacionInterfaceRO8 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidacionInterfaceRO8_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String query = "SELECT * FROM apps.GL_INTERFACE FETCH FIRST 1 ROWS ONLY";

		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a la BD: FCQAM1.FEMCOM.NET");

		// PASO 2
		addStep("Ejecutar la siguiente consulta Select * From apps.GL_INTERFACE");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(query);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(query);

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			System.out.println(ValidChangeExec);
			testCase.addTextEvidenceCurrentStep("La consulta contiene informacion, validacion exitosa");
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + query);
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
		return "Acceso a la BD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Prerequisitos: Acceso a la BD";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidacionInterfaceRO8_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

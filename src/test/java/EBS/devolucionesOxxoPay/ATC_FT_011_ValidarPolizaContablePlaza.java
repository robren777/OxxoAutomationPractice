package EBS.devolucionesOxxoPay;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_011_ValidarPolizaContablePlaza extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_ValidarPolizaContablePlaza_Plaza_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String cartaFemsaQuery = "SELECT carta_femsa_id, pie, fecha_transaccion, origen, descripcion_corta FROM XXFC.xxfc_cartas_femsa WHERE CARTA_FEMSA_ID = 1412283";
		String cartaFemsaLinea = "SELECT * FROM XXFC.xxfc_cartas_femsa_lineas WHERE CARTA_FEMSA_ID = 1412283";

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
		System.out.println(cartaFemsaQuery);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(cartaFemsaQuery);
		String cartaFemsa = "";

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			cartaFemsa = ValidChangeExec.getData(0, "carta_femsa_id");
			System.out.println("Carta Femsa ID: " + cartaFemsa);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + cartaFemsaQuery);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}

		// PASO 3
		addStep("Ejecutar el query");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(cartaFemsaLinea);
		SQLResult ValidChangeExec2 = dbAvebqa.executeQuery(cartaFemsaLinea);
		String cartaFemsaL = "";

		boolean ValidChangeRes2 = ValidChangeExec2.isEmpty();
		if (!ValidChangeRes2) {
			cartaFemsaL = ValidChangeExec2.getData(0, "carta_femsa_linea_id");
			System.out.println("Carta Femsa Linea ID: " + cartaFemsaL);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec2);
		} else if (ValidChangeRes2) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + cartaFemsaLinea);
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
		return "Ejecutar CP Alta de Comisiones";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al efectuar el proceso de dispersion, se genere correctamente una Poliza Plaza."
				+ "Prerequisitos: Ejecutar CP Alta de Comisiones";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_ValidarPolizaContablePlaza_Plaza_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

package WorkFlows.SipreBara;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_041_GeneraCambioPrecioMasivo extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_041_GeneraCambioPrecioMasivo_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Workflows Library: Sipre Bara");

		/**
		 * 
		 * Utilerias
		 */
		;

		utils.sql.SQLUtil dbSipreBara = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREBARA,
				GlobalVariables.DB_USER_SIPREBARA, GlobalVariables.DB_PASSWORD_SIPREBARA);

		/**
		 * QUERYS
		 */

		String Query = "SELECT CREATE_DATE,ACTIVE_DATE,ZONE_ID,ITEM FROM RMS100.XXFC_API_PRECIOS_CP@FCRMSQA WHERE ROWNUM <=5";

		/*-----------------------------------------------------------------------------------------*/
		addStep("Despues de haber ejecutado los 3 casos de prueba dependientes, realizar la siguiente consulta");
		System.out.println("resultado consulta --->");

		SQLResult queryResult = executeQuery(dbSipreBara, Query);
		testCase.addQueryEvidenceCurrentStep(queryResult);

		System.out.println("resultado consulta --->" + queryResult.isEmpty());

		assertFalse(queryResult.isEmpty(), "La consulta no muestra resultados de carga del día de hoy");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Haber ejecutado el CP Previamente MTC-FT-SIPRE-BARA- CP57 Carga Input CAMBIOS MASIVOS 2. Haber ejecutado el cp Previamente MTC-FT-SIPRE-BARA- CP62 Performance JOB SIPRE-BARA2_API_CAMBIOS_MASIVOS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que se pueda realizar una carga masiva de precios y la info llegue a las intermedias de RMS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_041_GeneraCambioPrecioMasivo_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

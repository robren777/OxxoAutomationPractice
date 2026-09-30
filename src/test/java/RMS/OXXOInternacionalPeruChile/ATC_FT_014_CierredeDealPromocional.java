package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.Queries_Tiendas;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;
import RMS.util.JobUtil;

public class ATC_FT_014_CierredeDealPromocional extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_CierredeDealPromocional_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);
		
		boolean ejecucionCorrectaJob = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		JobUtil job = new JobUtil(su, testCase, data);
		SQLResult executeQuery;
		
		//Paso 1
		addStep("Realizar la consulta para revisar deals con fecha de cierre hoy o fechas anteriores al d�a de hoy");
		testCase.addScreenShotCurrentStep("Paso1");

		String QDeal = String.format(Queries_Tiendas.tbl_deal_head);
		System.out.println(QDeal);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, QDeal);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "Se encontraron datos en la base de datos");

		// Paso 2
		addStep("Ejecutar el Job dealcls en control-m.");
		String resultadoEjecucion = "";
		resultadoEjecucion = job.ejecutar("dealcls");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job dealcls no se ejecuto correctamente");
		
		
		//Paso 3
		addStep("Realizar la consulta para revisar que los deals con fecha de cierre hoy o fechas anteriores al d�a de hoy se encuentren en estatus C.");
		QDeal = String.format(Queries_Tiendas.tbl_deal_head2);
		System.out.println(QDeal);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, QDeal);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "Se encontraron datos en la base de datos");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_CierredeDealPromocional_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_014_CierredeDealPromocional";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public void beforeTest() {
	}

}

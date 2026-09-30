package RMS.promocionesComplejas;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.BuscadorPromo;
import RMS.util.Queries;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class ValidaPromociones extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_Valida_Promociones(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		JobUtil job = new JobUtil(u, testCase, data);
		BuscadorPromo bp = new BuscadorPromo(dbRms);

		System.out.println("DB HOST: " + dbRms.getHost());

		String statusInicial = data.get("status_inicial");
		String statusPreupd = data.get("status_preupd");
		String statusPrmext = data.get("status_prmext");

		// PASO 1
		addStep(data.get("paso1"));
		System.out.println("PASO 1");

		String opPromhead = data.get("promhead");
		String opPromstore = data.get("promstore");
		// selecciona promo
		String promotion = bp.seleccionarPromo(data.get("evento"), statusInicial, opPromhead, opPromstore);

		if (promotion != null) {
			System.out.println("Promocion seleccionada: " + promotion);
			testCase.addTextEvidenceCurrentStep(
					"Promocion " + promotion + " seleccionada. Promocion con STATUS = " + statusInicial + "");
		}

		assertNotNull(promotion,
				"No se encontraron promociones con STATUS = " + statusInicial + " " + "y PROMHEAD.END_DATE "
						+ opPromhead + " PROMEVENT.END_DATE, " + "PROMSTORE.END_DATE " + opPromstore
						+ " PROMEVENT.END_DATE");

		// PASO 2
		String statusPromstore = statusInicial;
		if (statusInicial.equals("A") || statusInicial.equals("S") || statusInicial.equals("M")) {
			statusPromstore = "null";
		}

		addStep("Revisar la promoci�n en las tablas de RMS PROMHEAD " + "(Status " + statusInicial
				+ ") y PROMSTORE(Status " + statusPromstore + ")");
		System.out.println("PASO 2");

		// valida tabla PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD_STATUS, promotion, statusInicial);
		System.out.println(promheadPromotion);

		SQLResult executeQuery = executeQuery(dbRms, promheadPromotion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y STATUS = "
				+ statusInicial + " en PROMHEAD");

		// PROMSTORE
		String promstorePromotion = String.format(Queries.PROMSTORE_STATUS, promotion, statusInicial);
		if (statusPromstore.equals("null")) {
			promstorePromotion = String.format(Queries.PROMSTORE_STATUS_NULL, promotion);
		}

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y STATUS = "
				+ statusPromstore + " en PROMSTORE");

		// PASO 3
		addStep("Ejecutar job prmpreupd");
		System.out.println("PASO 3");

		String resultadoEjecucion = job.ejecutar("prmpreupd");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job prmpreupd no se ejecuto correctamente");

		// PASO 4
		addStep("Validar en la tabla promstore status = " + statusPreupd + " y promhead = " + statusInicial);
		System.out.println("PASO 4");

		// valida tabla PROMHEAD
		promheadPromotion = String.format(Queries.PROMHEAD_STATUS, promotion, statusInicial); // cambia %s por valor de
																								// promotion
		System.out.println(promheadPromotion);

		executeQuery = executeQuery(dbRms, promheadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y STATUS = "
				+ statusInicial + " en PROMHEAD");

		// PROMSTORE
		promstorePromotion = String.format(Queries.PROMSTORE_STATUS, promotion, statusPreupd);

		if (statusPreupd.equals("null")) {
			promstorePromotion = String.format(Queries.PROMSTORE_STATUS_NULL, promotion);
		}

		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y STATUS = "
				+ statusPreupd + " en PROMSTORE");

		// PASO 5
		addStep("Ejecutar xxfc_pre_prmext en Control M");
		System.out.println("PASO 5");

		resultadoEjecucion = job.ejecutar("xxfc_pre_prmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job xxfc_pre_prmext no se ejecuto correctamente");

		// PASO 6
		addStep("Revisar en la tabla XXFC.XXFC_PROMO_PRMEXT_T las promociones que fueron validadas por el "
				+ "job xxfc_pre_prmext y que ser�n extraidas por el prmext");
		System.out.println("PASO 6");

		String prmextPromotion = String.format(Queries.XXFC_PROMO_PRMEXT_T, promotion);
		System.out.println(prmextPromotion);

		executeQuery = executeQuery(dbRms, prmextPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en la tabla XXFC.XXFC_PROMO_PRMEXT_T");

		// PASO 7
		addStep("Ejecutar prmext Control M");
		System.out.println("PASO 7");

		resultadoEjecucion = job.ejecutar("prmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job prmext no se ejecuto correctamente");

		// PASO 8
		addStep("Validar en tabla PROMSTORE");
		System.out.println("PASO 8");

		promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROMSTORE");

		// PASO 9
		addStep("Ejecutar postprmext en Control M");
		System.out.println("PASO 9");

		resultadoEjecucion = job.ejecutar("postprmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job postprmext no se ejecuto correctamente");

		// PASO 10
		addStep("Validar en tabla PROMHEAD cambia status " + statusPrmext);
		promheadPromotion = String.format(Queries.PROMHEAD_STATUS, promotion, statusPrmext);
		System.out.println(promheadPromotion);

		executeQuery = executeQuery(dbRms, promheadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y STATUS = "
				+ statusPrmext + " en PROMHEAD");

		// PASO 11
		addStep("Validar promoci�n en las Tablas Pos_Mods Tran type 32 y pos_prom_detail");
		System.out.println("PASO 11");

		// valida tabla XXFC_POS_MODS_PRM_TEMP
		String posModsPrmTempTranPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP_TRAN_32, promotion);
		System.out.println(posModsPrmTempTranPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion
				+ " y TRAN_TYPE = 32 en XXFC.XXFC_POS_MODS_PRM_TEMP");

		// valida tabla POS_PROM_DETAIL
		String posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, posPromDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");

		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_Valida_Promociones";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Valida promociones con un estatus inicial determinado "
				+ "y las fechas que tienen en comparacion con PROMEVENT. "
				+ "-JOBS: prmpreupd, xxfc_pre_prmext, prmext, postprmext. " + "-CASOS: "
				+ "MTC-FT-002 Validar promociones con status E cuya fecha fin de promhead y promstore no coincida con la del evento, "
				+ "MTC-FT-003 Validar promociones con status E cuya fecha fin de promhead coincida con la del evento y en promstore se tengan fechas menor, "
				+ "MTC-FT-004 Validar promociones con status E cuya fecha fin de promhead coincida con la del evento y en promstore se tengan fechas mayor, "
				+ "MTC-FT-005 Validar promociones con status E cuya fecha fin de promhead no coincida con la del evento y sea menor, "
				+ "MTC-FT-006 Validar promociones con status A cuya fecha fin de promhead coincida con la del evento, "
				+ "MTC-FT-007 Validar promociones con status S cuya fecha fin de promhead coincida con la del evento, "
				+ "MTC-FT-008 Validar promociones con status W cuya fecha fin de promhead coincida con la del evento, "
				+ "MTC-FT-009 Validar funcionalidad de promociones con status E y fecha fin dos d�as despu�s de la ejecuci�n del job prmpreupd, "
				+ "MTC-FT-010 Validar promociones con status E cuya fecha fin de promhead  no coincida con la del evento  y la fecha fin promstore coincida con la del evento, "
				+ "MTC-FT-011 Prueba de performance prmpreupd.";

	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
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
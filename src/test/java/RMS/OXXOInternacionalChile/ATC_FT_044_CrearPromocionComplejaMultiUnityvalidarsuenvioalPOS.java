package RMS.OXXOInternacionalChile;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.Queries;
import RMS.util.PageObject.PromocionesComplejas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_044_CrearPromocionComplejaMultiUnityvalidarsuenvioalPOS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_044_CrearPromocionComplejaMultiUnityvalidarsuenvíoalPOS_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Informacion para consultas en base de datos de CHILE
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDCHRMSCQA, GlobalVariables.DB_USER_BDCHRMSCQA,
				GlobalVariables.DB_PASSWORD_BDCHRMSCQA);

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		PromocionesComplejas p = new PromocionesComplejas(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns,
				Fol, pathImg, dbRms, data, testCase);
		JobUtil job = new JobUtil(u, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep(data.get("paso1"));
		System.out.println("PASO 1");

		p.loginRMS();
		p.menuPricingPromotions();

		String promotion = p.CreateNewPromo();

		p.confPromoCompleja();

		p.submitPromo();

		p.minimizar();

		// Ejecucion de los jobs para poder aprobar la promo
		// Job previo
		String resultadoEjecucion = job.ejecutar("rr_setup.sh_2");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job rr_setup.sh_2 no se ejecuto correctamente");

		// Job que permite aprobar la promo cambiando el estatus en la bd
		resultadoEjecucion = job.ejecutar("pcovrlpq");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job pcovrlpq no se ejecuto correctamente");

		p.loginRMS();

		p.viewPromo(promotion);
		p.aprobarPromo();

		su.close();

		testCase.addBoldTextEvidenceCurrentStep("Se creo la promocion: " + promotion);

		// PASO 2
		addStep("Revisar la promoci�n en las tablas de RMS PROMHEAD (Status A), PROMSTORE, "
				+ "PROM_THRESHOLD_HEAD, PROM_THRESHOLD_DETAIL y PROM_THRESHOLD_SKU");
		System.out.println("PASO 2");

		// valida tabla PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD_STATUS_A, promotion); // cambia %s por valor de
																						// promotion
		System.out.println(promheadPromotion);

		SQLResult executeQuery = executeQuery(dbRms, promheadPromotion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con promotion = " + promotion + " y status = 'A' en PROMHEAD");

		// valida tabla PROMSTORE
		String promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + "en PROMSTORE");

		// valida tabla PROM_THRESHOLD_HEAD
		String promThresholdHeadPromotion = String.format(Queries.PROM_THRESHOLD_HEAD, promotion);
		System.out.println(promThresholdHeadPromotion);

		executeQuery = executeQuery(dbRms, promThresholdHeadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_HEAD");

		// valida tabla PROM_THRESHOLD_DETAIL
		String promThresholdDetailPromotion = String.format(Queries.PROM_THRESHOLD_DETAIL, promotion);
		System.out.println(promThresholdDetailPromotion);

		executeQuery = executeQuery(dbRms, promThresholdDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_DETAIL");

		// valida tabla PROM_THRESHOLD_SKU
		String promThresholdSkuPromotion = String.format(Queries.PROM_THRESHOLD_SKU, promotion);
		System.out.println(promThresholdSkuPromotion);

		executeQuery = executeQuery(dbRms, promThresholdSkuPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_SKU");

		// PASO 3
		addStep("Ejecutar xxfc_pre_prmext en Control M");
		System.out.println("PASO 3");

		resultadoEjecucion = job.ejecutar("xxfc_pre_prmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job xxfc_pre_prmext no se ejecuto correctamente");

		// PASO 4
		addStep("Revisar en la tabla XXFC.XXFC_PROMO_PRMEXT_T las promociones que fueron validadas "
				+ "por el job xxfc_pre_prmext y que ser�n extraidas por el prmext");
		System.out.println("PASO 4");

		String prmextPromotion = String.format(Queries.XXFC_PROMO_PRMEXT_T, promotion);
		System.out.println(prmextPromotion);

		executeQuery = executeQuery(dbRms, prmextPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en la tabla XXFC.XXFC_PROMO_PRMEXT_T");

		// PASO 5
		addStep("Ejecutar xxfc_pmodstemp_prm en Control M");
		System.out.println("PASO 5");

		resultadoEjecucion = job.ejecutar("xxfc_pmodstemp_prm");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job xxfc_pmodstemp_prm no se ejecuto correctamente");

		// PASO 6
		addStep("Validar que no existan datos en la tabla xxfc.xxfc_pos_mods_prm_temp");
		System.out.println("PASO 6");

		String posModsPrmPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP, promotion);
		System.out.println(posModsPrmPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmPromotion);

		statusQuery = executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "Se encontraron registros en la tabla XXFC.XXFC_POS_MODS_PRM_TEMP");

		// PASO 7
		addStep("Ejecutar prmext Control M");
		System.out.println("PASO 7");

		resultadoEjecucion = job.ejecutar("prmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job prmext no se ejecuto correctamente");

		// PASO 8
		addStep("Validar en tabla PROMSTORE (Extract_Status E)");
		System.out.println("PASO 8");

		promstorePromotion = String.format(Queries.PROMSTORE_STATUS_E, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'E' en PROMSTORE");

		// PASO 9
		addStep("Ejecutar postprmext en Control M");
		System.out.println("PASO 9");

		resultadoEjecucion = job.ejecutar("postprmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job postprmext no se ejecuto correctamente");

		// PASO 10
		addStep("Validar en tabla PROMHEAD (status E)");
		System.out.println("PASO 10");

		promheadPromotion = String.format(Queries.PROMHEAD_STATUS_E, promotion);
		System.out.println(promheadPromotion);

		executeQuery = executeQuery(dbRms, promheadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " STATUS = 'E' en PROMHEAD");

		// PASO 11
		addStep("Validar promoci�n en las Tablas xxfc_pos_mods_prm_temp Tran type 31 y pos_prom_detail");
		System.out.println("PASO 11");

		// valida tabla XXFC_POS_MODS_PRM_TEMP
		String posModsPrmTempTranPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP_TRAN_31, promotion);
		System.out.println(posModsPrmTempTranPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion
				+ " y TRAN_TYPE = 31 en XXFC.XXFC_POS_MODS_PRM_TEMP");

		// valida tabla POS_PROM_DETAIL
		String posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, posPromDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");

		// PASO 12
		addStep("Se ejecuta el job fem_prmext_post.");
		System.out.println("PASO 12");

		resultadoEjecucion = job.ejecutar("fem_prmext_post");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job fem_prmext_post no se ejecuto correctamente");

		// PASO 13
		addStep("Se ejecutan los jobs xxfc_procesa_promdnld_prm y runPRM.");
		System.out.println("PASO 13");

		resultadoEjecucion = job.ejecutar("xxfc_procesa_promdnld_prm");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job xxfc_procesa_promdnld_prm no se ejecuto correctamente");

		resultadoEjecucion = job.ejecutar("runPRM");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job runPRM no se ejecuto correctamente");

		// PASO 14
		addStep("Validar en tabla wmuser.pos_prm_head");
		System.out.println("PASO 14");

		System.out.println(Queries.WM_POS_PRM_HEAD);

		executeQuery = executeQuery(dbRms, Queries.WM_POS_PRM_HEAD);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en WMUSER.POS_PRM_HEAD");

		// PASO 15
		addStep("Se valida que la promoci�n se encuentre en las tablas staging "
				+ "WMUSER.FEM_POS_PROM_DETAIL_STG y WMUSER.FEM_POS_MODS_PRM_STG.");
		System.out.println("PASO 15");

		// valida WMUSER.FEM_POS_MODS_PRM_STG
		String wmPosModsPromotion = String.format(Queries.WM_FEM_POS_MODS_PRM_STG, promotion);
		System.out.println(wmPosModsPromotion);

		executeQuery = executeQuery(dbRms, wmPosModsPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_MODS_PRM_STG");

		// valida WMUSER.FEM_POS_PROM_DETAIL_STG
		String wmPospromDetailStgPromotion = String.format(Queries.WM_FEM_POS_PROM_DETAIL_STG, promotion);
		System.out.println(wmPospromDetailStgPromotion);

		executeQuery = executeQuery(dbRms, wmPospromDetailStgPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_PROM_DETAIL_STG");

		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_044_CrearPromocionComplejaMultiUnityvalidarsuenvíoalPOS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_044_CrearPromocionComplejaMultiUnityvalidarsuenvíoalPOS";
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

package RMS.RegresionesPromociones;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class ATC_FT_036_Validar_funcionalidad_de_rutina_de_reenvio_de_una_promocion_promopack_incompleta_que_nunca_tendra_la_ligas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_036_Validar_funcionalidad_de_rutina_de_reenvio_de_una_promocion_promopack_incompleta_que_nunca_tendra_la_ligas_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

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
		DateTimeFormatter dateFormatDatabase = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
		DateTimeFormatter dateFormatProvider = DateTimeFormatter.ofPattern("ddMMyy Hmm");
		JobUtil job = new JobUtil(u, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep(data.get("paso1"));
		System.out.println("PASO 1");

		p.loginRMS();
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
		String[] tiendasCompletas = p.obtenerTiendas("completas");
		String[] tiendasIncompletas = p.obtenerTiendas("incompletas");

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

			boolean existeEnTabla = false;
			testCase.addBoldTextEvidenceCurrentStep("TIENDAS CON LIGAS COMPLETAS");
			for (int i = 0; i < tiendasCompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasCompletas[i], executeQuery);

				if (existeEnTabla) {
					testCase.addTextEvidenceCurrentStep(tiendasCompletas[i]);
				}

				assertTrue(existeEnTabla, "La tienda " + tiendasCompletas[i] + " tiene ligas completas"
						+ " y no existe en XXFC_POS_MODS_PRM_TEMP");
			}

			testCase.addBoldTextEvidenceCurrentStep("TIENDAS CON LIGAS INCOMPLETAS");
			for (int i = 0; i < tiendasIncompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasIncompletas[i], executeQuery);

				if (!existeEnTabla) {
					testCase.addTextEvidenceCurrentStep(tiendasIncompletas[i]);
				}

				assertFalse(existeEnTabla, "La tienda " + tiendasIncompletas[i] + " tiene ligas incompletas"
						+ " y existe en XXFC_POS_MODS_PRM_TEMP");

			}

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
		addStep("Se actualiza tabla promstore, el campo extract_status "
				+ "regresa a nulo para las tiendas que no tienen ligado el art�culo");
		System.out.println("PASO 13");

		if (tiendasIncompletas[0] != null) {
			promstorePromotion = String.format(Queries.PROMSTORE_STATUS_NULL, promotion);
			System.out.println(promstorePromotion);

			executeQuery = executeQuery(dbRms, promstorePromotion);

			statusQuery = executeQuery.isEmpty();
			if (!statusQuery) {
				testCase.addQueryEvidenceCurrentStep(executeQuery);

				boolean existeEnTabla = false;
				for (int i = 0; i < tiendasIncompletas.length; i++) {
					existeEnTabla = p.validarLigas(tiendasIncompletas[i], executeQuery);

					assertTrue(existeEnTabla, "La tienda " + tiendasIncompletas[i] + " tiene ligas incompletas"
							+ " y no cambio el EXTRACT_STATUS a NULL en PROMSTORE");
				}
			}

			assertFalse(statusQuery,
					"No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'E' en PROMSTORE");

		} else {
			testCase.addTextEvidenceCurrentStep(
					"La promocion = " + promotion + " no tiene tiendas" + " con ligas incompletas");
		}

		// PASO 14
		addStep("Se ejecutan los jobs xxfc_procesa_promdnld_prm y runPRM.");
		System.out.println("PASO 14");

		resultadoEjecucion = job.ejecutar("xxfc_procesa_promdnld_prm");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job xxfc_procesa_promdnld_prm no se ejecuto correctamente");

		resultadoEjecucion = job.ejecutar("runPRM");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job runPRM no se ejecuto correctamente");

		// PASO 15
		addStep("Validar en tabla wmuser.pos_prm_head");
		System.out.println("PASO 15");

		System.out.println(Queries.WM_POS_PRM_HEAD);
		executeQuery = executeQuery(dbRms, Queries.WM_POS_PRM_HEAD);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

			boolean existeEnTabla = false;
			for (int i = 0; i < tiendasCompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasCompletas[i], executeQuery);

				assertTrue(existeEnTabla, "La tienda " + tiendasCompletas[i] + " tiene ligas completas"
						+ " y no existe en WMUSER.POS_PRM_HEAD");
			}
		}

		assertFalse(statusQuery, "No se encontraron registros en WMUSER.POS_PRM_HEAD");

		// PASO 16
		addStep("Se valida que la promoci�n se encuentre en las tablas staging "
				+ "WMUSER.FEM_POS_PROM_DETAIL_STG y WMUSER.FEM_POS_MODS_PRM_STG.");
		System.out.println("PASO 16");

		// valida WMUSER.FEM_POS_MODS_PRM_STG
		String wmPosModsPromotion = String.format(Queries.WM_FEM_POS_MODS_PRM_STG, promotion);
		System.out.println(wmPosModsPromotion);

		executeQuery = executeQuery(dbRms, wmPosModsPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

			boolean existeEnTabla = false;
			for (int i = 0; i < tiendasCompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasCompletas[i], executeQuery);

				assertTrue(existeEnTabla, "La tienda " + tiendasCompletas[i] + " tiene ligas completas"
						+ " y no existe en WMUSER.FEM_POS_MODS_PRM_STG");
			}

		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_MODS_PRM_STG");

		// valida WMUSER.FEM_POS_PROM_DETAIL_STG
		LocalDateTime femPosPromStartTime = null;
		LocalDateTime femPosPromEndTime = null;

		String wmPospromDetailStgPromotion = String.format(Queries.WM_FEM_POS_PROM_DETAIL_STG, promotion);
		System.out.println(wmPospromDetailStgPromotion);

		executeQuery = executeQuery(dbRms, wmPospromDetailStgPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			femPosPromStartTime = LocalDateTime.parse(executeQuery.getData(0, "START_TIME"), dateFormatDatabase);
			femPosPromEndTime = LocalDateTime.parse(executeQuery.getData(0, "END_TIME"), dateFormatDatabase);

			boolean existeEnTabla = false;
			for (int i = 0; i < tiendasCompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasCompletas[i], executeQuery);

				assertTrue(existeEnTabla, "La tienda " + tiendasCompletas[i] + " tiene ligas completas"
						+ " y no existe en WM_FEM_POS_PROM_DETAIL_STG");
			}
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_PROM_DETAIL_STG");

		// PASO 17
		addStep("Validar que los d�as y horas configurados en la promoci�n se encuentren correctamente en las "
				+ "tablas wmuser.");
		System.out.println("PASO 17");
		String fechaInicio = p.obtenerFechaInicio();
		String fechaFin = p.obtenerFechaFin();

		LocalDateTime startTime = LocalDateTime.parse(fechaInicio, dateFormatProvider);
		LocalDateTime endTime = LocalDateTime.parse(fechaFin, dateFormatProvider);

		boolean fechaConfigCorrectamente = false;
		if (startTime.equals(femPosPromStartTime) && endTime.equals(femPosPromEndTime)) {
			String femPosModsTime = String.format(Queries.WM_FEM_POS_PROM_DETAIL_STG_TIME, promotion);
			executeQuery = executeQuery(dbRms, femPosModsTime);

			testCase.addTextEvidenceCurrentStep(
					"La fecha hora estan configuradas correctamente en WMUSER.FEM_POS_PROM_DETAIL_STG");
			testCase.addQueryEvidenceCurrentStep(executeQuery);

			fechaConfigCorrectamente = true;
		}

		assertTrue(fechaConfigCorrectamente,
				"La fecha no esta configurada correctamente en: WMUSER.FEM_POS_PROM_DETAIL_STG");

		u.close();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_036_Validar_funcionalidad_de_rutina_de_reenvio_de_una_promocion_promopack_incompleta_que_nunca_tendra_la_ligas_test";

	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_036_Validar_funcionalidad_de_rutina_de_reenvio_de_una_promocion_promopack_incompleta_que_nunca_tendra_la_ligas";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

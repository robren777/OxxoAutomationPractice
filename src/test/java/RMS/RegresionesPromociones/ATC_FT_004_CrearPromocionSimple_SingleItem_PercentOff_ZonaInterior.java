package RMS.RegresionesPromociones;

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

public class ATC_FT_004_CrearPromocionSimple_SingleItem_PercentOff_ZonaInterior extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_CrearPromocionSimple_SingleItem_PercentOff_ZonaInterior_test(HashMap<String, String> data)
			throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		// Queries		

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);	
		Robot robot = new Robot();		
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		PromocionesComplejas p = new PromocionesComplejas(sk, su, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, data, testCase);
		JobUtil job = new JobUtil(u, testCase, data);
		String resultadoEjecucion;
		boolean ejecucionCorrectaJob;
		SQLResult executeQuery;
		boolean statusQuery;
		String promstorePromotion;
		String promheadPromotion;
		String store = null;
		
		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Ingresar a RMS con la liga: http://10.184.16.184:8029/rmsQA.html");
		System.out.println("PASO 1");

		p.loginRMS();
		p.menuPricingPromotions();
		String promotion = p.CreateNewPromo();
		System.out.println(promotion);
		p.confPromoSimple("percentOff");
		p.submitPromo2();

		p.CloseBrowser();
		
		// Ejecucion de los jobs para poder aprobar la promo
		// Job previo
		
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB rr_setup_sh_2");
		resultadoEjecucion = job.ejecutar("rr_setup_sh_2");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB pcovrlpq");
		resultadoEjecucion = job.ejecutar("pcovrlpq");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		su = new SeleniumUtil(new IETest(), true);
		p = new PromocionesComplejas(sk, su, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, data, testCase);

		p.loginRMS();
		
		p.menuPricingPromotions();
		p.viewPromo(promotion);
		p.aprobarPromo();
		
		su.close();		
		
		// PASO 2
		addStep("Revisar la promoci�n en las tablas de RMS PROMHEAD (Status A), PROMSTORE, "
				+ "PROM_THRESHOLD_HEAD, PROM_THRESHOLD_DETAIL y PROM_THRESHOLD_SKU");
		System.out.println("PASO 2");
		
		// valida tabla PROMHEAD
		promheadPromotion = String.format(Queries.PROMHEAD_STATUS_A, promotion); // cambia %s por valor de promotion
		System.out.println(promheadPromotion);

		executeQuery = executeQuery(dbRms, promheadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con promotion = " + promotion + " y status = 'A' en PROMHEAD");

		// valida tabla PROMSTORE
		promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);
		store = null; // guarda la tienda para usarse como identificador en la tabla WMUSER.POS_PRM_HEAD
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			store = executeQuery.getData(0, "STORE");
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + "en PROMSTORE");
		
//		// valida tabla PROM_THRESHOLD_HEAD
//		promThresholdHeadPromotion = String.format(Queries.PROM_THRESHOLD_HEAD, promotion);
//		System.out.println(promThresholdHeadPromotion);
//
//		executeQuery = executeQuery(dbRms, promThresholdHeadPromotion);
//
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_HEAD");
		
		// valida tabla PROM_THRESHOLD_DETAIL
//		String promThresholdDetailPromotion = String.format(Queries.PROM_THRESHOLD_DETAIL, promotion);
//		System.out.println(promThresholdDetailPromotion);
//
//		executeQuery = executeQuery(dbRms, promThresholdDetailPromotion);
//
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_DETAIL");
		
		// valida tabla PROM_THRESHOLD_SKU
//		String promThresholdSkuPromotion = String.format(Queries.PROM_THRESHOLD_SKU, promotion);
//		System.out.println(promThresholdSkuPromotion);
//
//		executeQuery = executeQuery(dbRms, promThresholdSkuPromotion);
//
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_SKU");
				
		// PASO 3
		addStep("Ejecutar xxfc_pre_prmext en Control M");
		System.out.println("PASO 3");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB rr_setup_sh_9");
		resultadoEjecucion = job.ejecutar("rr_setup_sh_9");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxfc_pre_prmext");
		resultadoEjecucion = job.ejecutar("xxfc_pre_prmext");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
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

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion +" en la tabla XXFC.XXFC_PROMO_PRMEXT_T");

		
		// PASO 5
		addStep("Ejecutar xxfc_pmodstemp_prm en Control M");
		System.out.println("PASO 5");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxfc_pmodstemp_prm");
		resultadoEjecucion = job.ejecutar("xxfc_pmodstemp_prm");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		
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
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB prmext");
		resultadoEjecucion = job.ejecutarSetOk("prmext");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		
		
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

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'E' en PROMSTORE");
	
		promstorePromotion = String.format(Queries.XXFC_XXFC_POS_MODS_PRM_TEMP, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + "en XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		// PASO 9
		addStep("Ejecutar postprmext en Control M");
		System.out.println("PASO 9");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB postprmext");
		resultadoEjecucion = job.ejecutar("postprmext");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
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

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " STATUS = 'E' en PROMHEAD");
		
		
		// PASO 11
		addStep("Validar promoci�n en las Tablas xxfc_pos_mods_prm_temp Tran type 31 y pos_prom_detail");
		System.out.println("PASO 11");
		
		// valida tabla XXFC_POS_MODS_PRM_TEMP
		String posModsPrmTempTranPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP, promotion);
		System.out.println(posModsPrmTempTranPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y TRAN_TYPE = 31 en XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		// valida tabla POS_PROM_DETAIL
//		String posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
//		System.out.println(posPromDetailPromotion);
//
//		executeQuery = executeQuery(dbRms, posPromDetailPromotion);
//
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");
		
		
		// PASO 12
		addStep("Se ejecuta el job fem_prmext_post.");
		System.out.println("PASO 12");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB fem_prmext_post");
		resultadoEjecucion = job.ejecutar("fem_prmext_post");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		
		// PASO 13
		addStep("Se ejecutan los jobs xxfc_procesa_promdnld_prm y runPRM.");
		System.out.println("PASO 13");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxfc_procesa_promdnld_prm");
		resultadoEjecucion = job.ejecutar("xxfc_procesa_promdnld_prm");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB runprm");
		resultadoEjecucion = job.ejecutar("runprm");
		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");

		// PASO 14
		addStep("Validar en tabla wmuser.pos_prm_head");
		System.out.println("PASO 14");
		
		String wmPosPrmHeadStore = String.format(Queries.WM_POS_PRM_HEAD, store);
		System.out.println(wmPosPrmHeadStore);

		executeQuery = executeQuery(dbRms, wmPosPrmHeadStore);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en WMUSER.POS_PRM_HEAD");

		
		// PASO 15
		addStep("Se valida que la promoci�n se encuentre en las tablas staging "
				+ "WMUSER.FEM_POS_PROM_DETAIL_STG y WMUSER.FEM_POS_MODS_PRM_STG.");
		System.out.println("PASO 15");
		System.out.println(GlobalVariables.DB_HOST_FCRMSQA);
		
		// valida WMUSER.FEM_POS_MODS_PRM_STG
		String wmPosModsPromotion = String.format(Queries.WM_FEM_POS_MODS_PRM_STG, promotion);
		System.out.println(wmPosModsPromotion);

		executeQuery = executeQuery(dbRms, wmPosModsPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_MODS_PRM_STG");
		
		// valida WMUSER.FEM_POS_PROM_DETAIL_STG
//		String wmPospromDetailStgPromotion = String.format(Queries.WM_FEM_POS_PROM_DETAIL_STG, promotion);
//		System.out.println(wmPospromDetailStgPromotion);
//
//		executeQuery = executeQuery(dbRms, wmPospromDetailStgPromotion);
//
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_PROM_DETAIL_STG");
						
		u.close();			


	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_CrearPromocionSimple_SingleItem_PercentOff_ZonaInterior_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_004_CrearPromocionSimple_SingleItem_PercentOff_ZonaInterior";
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

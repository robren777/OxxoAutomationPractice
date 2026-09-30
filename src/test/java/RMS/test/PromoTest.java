package RMS.test;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.PromocionesComplejas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class PromoTest extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void Promo_Test(HashMap<String, String> data) throws Exception {
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
		JobUtil job = new JobUtil(u, testCase, data);

		System.out.println(dbRms.getHost());

		/*
		 * //PASO 1 addStep(data.get("paso1")); System.out.println("PASO 1");
		 * 
		 * p.loginRMS(); p.menuPricingPromotions();
		 * 
		 * String promotion = p.CreateNewPromo();
		 * ATC_FT_005_AltaItemMREGULyvalidarsuenvioalPOS.java
		 * 
		 * p.confPromoCompleja();
		 * 
		 * p.submitPromo();
		 * 
		 * p.minimizar();
		 */
		// Ejecucion de los jobs para poder aprobar la promo
		// Job previo
		String resultadoEjecucion = job.ejecutar("rr_setup.sh_2");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job rr_setup.sh_2 no se ejecuto correctamente");

		// Job que permite aprobar la promo cambiando el estatus en la bd
		resultadoEjecucion = job.ejecutar("pcovrlpq");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job pcovrlpq no se ejecuto correctamente");
		/*
		 * p.loginRMS();
		 * 
		 * p.viewPromo(promotion); p.aprobarPromo();
		 * 
		 * su.close();
		 * 
		 * testCase.addBoldTextEvidenceCurrentStep("Se creo la promocion: " +
		 * promotion);
		 * 
		 * 
		 * // PASO 2
		 * addStep("Revisar la promoci�n en las tablas de RMS PROMHEAD (Status A), PROMSTORE, "
		 * + "PROM_THRESHOLD_HEAD, PROM_THRESHOLD_DETAIL y PROM_THRESHOLD_SKU");
		 * System.out.println("PASO 2");
		 * 
		 * // valida tabla PROMHEAD String promheadPromotion =
		 * String.format(Queries.PROMHEAD_STATUS_A, promotion); // cambia %s por valor
		 * de promotion System.out.println(promheadPromotion);
		 * 
		 * SQLResult executeQuery = executeQuery(dbRms, promheadPromotion);
		 * 
		 * boolean statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con promotion = " +
		 * promotion + " y status = 'A' en PROMHEAD");
		 * 
		 * // valida tabla PROMSTORE String promstorePromotion =
		 * String.format(Queries.PROMSTORE, promotion);
		 * System.out.println(promstorePromotion);
		 * 
		 * executeQuery = executeQuery(dbRms, promstorePromotion);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " +
		 * promotion + "en PROMSTORE");
		 * 
		 * // valida tabla PROM_THRESHOLD_HEAD String promThresholdHeadPromotion =
		 * String.format(Queries.PROM_THRESHOLD_HEAD, promotion);
		 * System.out.println(promThresholdHeadPromotion);
		 * 
		 * executeQuery = executeQuery(dbRms, promThresholdHeadPromotion);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " +
		 * promotion + " en PROM_THRESHOLD_HEAD");
		 * 
		 * // valida tabla PROM_THRESHOLD_DETAIL String promThresholdDetailPromotion =
		 * String.format(Queries.PROM_THRESHOLD_DETAIL, promotion);
		 * System.out.println(promThresholdDetailPromotion);
		 * 
		 * executeQuery = executeQuery(dbRms, promThresholdDetailPromotion);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " +
		 * promotion + " en PROM_THRESHOLD_DETAIL");
		 * 
		 * // valida tabla PROM_THRESHOLD_SKU String promThresholdSkuPromotion =
		 * String.format(Queries.PROM_THRESHOLD_SKU, promotion);
		 * System.out.println(promThresholdSkuPromotion);
		 * 
		 * executeQuery = executeQuery(dbRms, promThresholdSkuPromotion);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " +
		 * promotion + " en PROM_THRESHOLD_SKU");
		 */
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
		return "Testing de promos";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "Promo_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

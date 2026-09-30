package RMS.CHO_52051610;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.BuscadorPromo;
import RMS.util.Queries;
import RMS.util.PageObject.PromocionesComplejas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class MTC_FT_007_PruebaPerformancePCOVRLPQ extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_PruebaPerformancePCOVRLPQ(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		String Users = data.get("rms_user");
		String PWDs = PasswordUtil.decryptPassword(data.get("rms_ps"));
		String URL = data.get("rms_url");
		String cb = null;
		Boolean st = null;

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
		BuscadorPromo bp = new BuscadorPromo(dbRms);

		System.out.println(dbRms.getHost());


		// PASO 1
		addStep("Se toman promociones  y se da submit por pantalla RMS (Promhead status I) Revisar las tablas "
				+ "Promhead, Price_overlap_queue");

		// TOMAR PROMOCIONES
		String statusInicial = data.get("status_inicial");
		String statusFinal = data.get("status_final");
		String promotion = bp.seleccionarPromo(statusInicial);

		if (promotion != null) {
			testCase.addTextEvidenceCurrentStep(promotion + " es una promocion compleja con status = '" + statusInicial + "'");
		}

		assertNotNull(promotion, "No se encontraron promociones con status = '" + statusInicial + "'");

		//SUBMIT PROMOCIONES
		p.viewPromo(promotion);
		p.submitPromo();


		//VALIDAR TABLAS
		//PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD, promotion);
		System.out.println(promheadPromotion);

		SQLResult executeQuery = dbRms.executeQuery(promheadPromotion);

		boolean statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion +  "'");

		//PRICE_OVERLAP_QUEUE
		String priceOverlap = String.format(Queries.PRICE_OVERLAP_QUEUE, promotion);
		System.out.println(priceOverlap);

		executeQuery = dbRms.executeQuery(promheadPromotion);

		statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PRICE_OVERLAP_QUEUE con PRICE_NO = '" 
				+ promotion +  "'");

		// PASO 2
		addStep("Ejecutar el job pcovrlpq");
		String resultadoEjecucion = job.ejecutar("pcovrlpq");
		System.out.println("Resultado Job pcovrlpq: " + resultadoEjecucion);

		//PASO 3
		addStep("Revisar en la tabla promhead que se modific� la promoci�n de status W a S ");
		promheadPromotion = String.format(Queries.PROMHEAD_STATUS_S, promotion);
		System.out.println(promheadPromotion);

		executeQuery = dbRms.executeQuery(promheadPromotion);

		statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion +  "'"
				+ " y STATUS = '" + statusFinal);

		// PASO 5
		addStep("Se da Approved (Promhead status I) Revisar las tablas Promhead, Price_overlap_queue");
		p.viewPromo(promotion);
		p.submitPromo();
		p.aprobarPromo();

		//VALIDAR TABLAS
		//PROMHEAD
		promheadPromotion = String.format(Queries.PROMHEAD, promotion);
		System.out.println(promheadPromotion);

		executeQuery = dbRms.executeQuery(promheadPromotion);

		statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion +  "'");

		//PRICE_OVERLAP_QUEUE
		priceOverlap = String.format(Queries.PRICE_OVERLAP_QUEUE, promotion);
		System.out.println(priceOverlap);

		executeQuery = dbRms.executeQuery(promheadPromotion);

		statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PRICE_OVERLAP_QUEUE con PRICE_NO = '" 
				+ promotion +  "'");

		
		// PASO 5
		addStep("Ejecutar el job pcovrlpq");
		resultadoEjecucion = job.ejecutar("pcovrlpq");
		System.out.println("Resultado Job pcovrlpq: " + resultadoEjecucion);

		
		// PASO 6
		addStep("Revisar en la tabla promhead que se modific� la promoci�n de status S a A");
		statusInicial = statusFinal;
		statusFinal = "A";
		
		promheadPromotion = String.format(Queries.PROMHEAD_STATUS_A, promotion);
		System.out.println(promheadPromotion);

		executeQuery = dbRms.executeQuery(promheadPromotion);

		statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion +  "'"
				+ " y STATUS = '" + statusFinal);
	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_PruebaPerformancePCOVRLPQ";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Prueba de performance para pcovrlpq.";
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

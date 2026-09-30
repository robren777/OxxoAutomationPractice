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

public class MTC_FT_001_ValidarCambioEstatusPromosComplejas_WS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarCambioPromosEstatusComplejas_WS(HashMap<String, String> data) throws Exception {

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
		addStep("Seleccionar una promoci�n compleja en status W");
		String status = data.get("status_inicial");
		String promotion = bp.seleccionarPromo(status);
		
		if (promotion != null) {
			testCase.addTextEvidenceCurrentStep(promotion + " es una promocion compleja con status = '" + status + "'");
		}
		
		assertNotNull(promotion, "No se encontraron promociones con status = '" + status + "'");
		
		
		// PASO 2
		addStep("Dar Submit por pantalla RMS");
		p.viewPromo(promotion);
		p.submitPromo();
		
		su.close();
		
		
		//PASO 3
		addStep("Revisar las tablas, Promhead, Price_overlap_queue");
		
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
		
		
		// PASO 4
		addStep("Ejecutar el job pcovrlpq");
		String resultadoJob = job.ejecutar("rr_setup.sh_2"); // Pre requisito para el job pcovrlpq
		boolean ejecucionCorrectaJob = false;
		
		if (resultadoJob.equals("Wait Condition") || resultadoJob.equals("Ended Ok")) {
			ejecucionCorrectaJob = true;
		}
		
		assertTrue(ejecucionCorrectaJob, "El job rr_setup.sh_2 no se ejecuto correctamente");
		
		
		ejecucionCorrectaJob = false;
		resultadoJob = job.ejecutar("pcovrlpq");
		
		if (resultadoJob.equals("Wait Condition") || resultadoJob.equals("Ended Ok")) {
			ejecucionCorrectaJob = true;
		}
		
		assertTrue(ejecucionCorrectaJob, "El job pcovrlpq no se ejecuto correctamente");
		
		
		// PASO 5
		addStep("Revisar en la tabla promhead que se modific� la promoci�n de status W a S");
		
		status = data.get("status_target");		
		String promheadPromotionStatusS = String.format(Queries.PROMHEAD_STATUS_S, promotion);
		System.out.println(promheadPromotionStatusS);
		
		executeQuery = dbRms.executeQuery(promheadPromotionStatusS);
		
		statusQuery = !executeQuery.isEmpty();
	
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion +  "'"
				+ " y STATUS = '" + status + "'");	
		
		su.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarCambioPromosEstatusComplejas_WS";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar el cambio de estatus de W a S en una promo compleja.";
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

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

public class MTC_FT_003_ValidarCambioEstatusPromosComplejas_SA extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarCambioEstatusPromosComplejas_SA(HashMap<String, String> data) throws Exception {

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
		PromocionesComplejas p = new PromocionesComplejas(sk, su, robot, pathimg, ListImage, ImgPatter, patterns, Fol,
				pathImg, dbRms, data, testCase);
		JobUtil job = new JobUtil(u, testCase, data);
		BuscadorPromo bp = new BuscadorPromo(dbRms);

		System.out.println(dbRms.getHost());

		/*
		// PASO 1
		addStep("Seleccionar una promoci�n compleja en status S");
		String status = data.get("status_inicial");
		String promotion = bp.seleccionarPromo(status);
		
		if (promotion != null) {
			testCase.addTextEvidenceCurrentStep(promotion + " es una promocion compleja con status = '" + status + "'");
		}
		
		assertNotNull(promotion, "No se encontraron promociones con status = '" + status + "'");
		
		
		// PASO 2
		addStep("Darle Approved por pantalla RMS");
		p.viewPromo(promotion);
		p.aprobarPromo();
		
		su.close();
		
		*/
		//PASO 3
		addStep("Revisar las tablas, Promhead, Price_overlap_queue");
		
		//PROMHEAD

		String statusInicial = data.get("status_inicial");
		String statusTarget = data.get("status_target");
		String accion = data.get("accion");

		// PASO 1
		addStep("Seleccionar una promocion compleja en status " + statusInicial);
		System.out.println("PASO 1");

		String promotion = bp.seleccionarPromo(statusInicial);

		if (promotion != null) {
			testCase.addTextEvidenceCurrentStep(
					promotion + " es una promocion compleja con status = '" + statusInicial + "'");
			System.out.println("Promocion seleccionada: " + promotion);
		}

		assertNotNull(promotion, "No se encontraron promociones con status = '" + statusInicial + "'");

		// PASO 2
		addStep("Dar " + accion + " por pantalla RMS");
		System.out.println("PASO 2");

		p.loginRMS();
		p.viewPromo(promotion);

		p.cambioStatus();

		su.close();

		// PASO 3
		addStep("Revisar las tablas, Promhead, Price_overlap_queue");
		System.out.println("PASO 3");

		// PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD, promotion);
		System.out.println(promheadPromotion);

		SQLResult executeQuery = dbRms.executeQuery(promheadPromotion);

		boolean statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion + "'");

		// PRICE_OVERLAP_QUEUE
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
		System.out.println("PASO 4");

		String resultadoEjecucion = job.ejecutar("rr_setup.sh_2"); // Pre requisito para el job pcovrlpq
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job rr_setup.sh_2 no se ejecuto correctamente");

		resultadoEjecucion = job.ejecutar("pcovrlpq");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job pcovrlpq no se ejecuto correctamente");

		// PASO 5
		addStep("Revisar en la tabla promhead que se modific� la promoci�n de status " + statusInicial + " a "
				+ statusTarget);
		System.out.println("PASO 5");

		String promheadPromotionStatus = String.format(Queries.PROMHEAD_STATUS, promotion, statusTarget);
		System.out.println(promheadPromotionStatus);

		executeQuery = dbRms.executeQuery(promheadPromotionStatus);

		statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		//assertTrue(statusQuery, "No se encontraron registros en PROMHEAD con PROMOTION = '" + promotion +  "'"
				//+ " y STATUS = '" + status + "'");
		
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarCambioEstatusPromosComplejas_SA";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Valida cambio de status en promociones. " + "-JOBS: rr_setup.sh_2, prmpreupd. " + "-CASOS: "
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

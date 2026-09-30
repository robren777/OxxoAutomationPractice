package RMS.promocionesComplejas;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.Queries;
import RMS.util.PageObject.PromocionesComplejas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class ValidarFuncionalidadRutinaReenvio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_Validar_Funcionalidad_Reenvio(HashMap<String, String> data) throws Exception {

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

		System.out.println("DB HOST: " + dbRms.getHost());

		String promotion = data.get("promotion");

		// PASO 1
		addStep(data.get("paso1"));
		System.out.println("PASO 1");

		System.out.println(data.get("paso1"));

		// PASO 2
		addStep("Se ejecutan los procesos previos al validador de promociones. "
				+ "a) Extraccion de promociones (prmext) " + "b) Prepost Actualiza estatus (PostPrmext) "
				+ "c) Proceso que traspasa la informacion de POS_MODS a XXFC.XXFC_POS_MODS_TEMP (xxfcpmodsstaging) ");
		System.out.println("PASO 2");

		// prmext
		String resultadoEjecucion = job.ejecutar("prmext");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job prmext no se ejecuto correctamente");

		// postprmext
		resultadoEjecucion = job.ejecutar("postprmext");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job postprmext no se ejecuto correctamente");

		// xxfcpmodsstaging
		resultadoEjecucion = job.ejecutar("xxfcpmodsstaging");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job xxfcpmodsstaging no se ejecuto correctamente");

		// PASO 3
		addStep("Previo a ejecutar el validador de promociones se revisan las tablas XXFC_POS_MODS_PRM_TEMP, "
				+ "RMS100.POS_PROM_DETAIL, RMS100.PROMSTORE y XXFC.XXFC_PROM_RESEND");
		System.out.println("PASO 3");

		// valida XXFC.XXFC_POS_MODS_PRM_TEMP
		String posModsPrmTempPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP_PROMOTION, promotion);
		System.out.println(posModsPrmTempPromotion);

		SQLResult executeQuery = executeQuery(dbRms, posModsPrmTempPromotion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en XXFC.XXFC_POS_MODS_PRM_TEMP");

		// Valida POS_PROM_DETAIL
		String posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, posPromDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");

		// Valida PROMSTORE
		String promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROMSTORE");

		// Valida XXFC.XXFC_PROM_RESEND
		String promResendPromotion = String.format(Queries.XXFC_PROM_RESEND, promotion);
		System.out.println(promResendPromotion);

		executeQuery = executeQuery(dbRms, promResendPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " en XXFC.XXFC_PROM_RESEND");

		// PASO 4
		addStep("Se ejecuta el validador de promociones (fem_prmext_post)");
		System.out.println("PASO 4");

		resultadoEjecucion = job.ejecutar("fem_prmext_post");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job fem_prmext_post no se ejecuto correctamente");

		// PASO 5
		addStep("Se revisa que en la tabla PROMSTORE se haya quedado en estatus E (Extracted) para la primera promocion en F (Fallado) para la segunda "
				+ "promocion para la tienda que no tiene ligas completas");
		System.out.println("PASO 5");

		String[] tiendasCompletas = p.obtenerTiendas("completas");
		String[] tiendasIncompletas = p.obtenerTiendas("incompletas");

		String promstorePromotionE = String.format(Queries.PROMSTORE_STATUS_E, promotion);
		System.out.println(promstorePromotionE);

		executeQuery = executeQuery(dbRms, promstorePromotionE);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

			boolean existeEnTabla = false;
			for (int i = 0; i < tiendasCompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasCompletas[i], executeQuery);

				assertFalse(existeEnTabla, "La tienda " + tiendasCompletas[i] + " tiene ligas completas"
						+ " y no cambio el EXTRACT_STATUS a E en PROMSTORE");
			}
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'E' en PROMSTORE");

		String promstorePromotionF = String.format(Queries.PROMSTORE_STATUS_F, promotion);
		System.out.println(promstorePromotionF);

		executeQuery = executeQuery(dbRms, promstorePromotionF);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);

			boolean existeEnTabla = false;
			for (int i = 0; i < tiendasIncompletas.length; i++) {
				existeEnTabla = p.validarLigas(tiendasIncompletas[i], executeQuery);

				assertFalse(existeEnTabla, "La tienda " + tiendasIncompletas[i] + " tiene ligas incompletas"
						+ " y no cambio el EXTRACT_STATUS a F en PROMSTORE");
			}
		}

		assertFalse(statusQuery,
				"No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'F' en PROMSTORE");

		// PASO 6
		addStep("Se revisa la tabla XXFC.XXFC_PROM_RESEND y se valida que se haya actualizado el campo "
				+ "send_date_end para la segunda promocion por el ultimo intento de reenvio");
		System.out.println("PASO 6");

		String promResendDatePromotion = String.format(Queries.XXFC_PROM_RESEND_DATE, promotion);
		System.out.println(promResendDatePromotion);

		executeQuery = executeQuery(dbRms, promResendDatePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion
				+ " en XXFC.XXFC_PROM_RESEND con fecha actualizada");

		// PASO 7
		addStep("Se revisa el log de la ejecucion el job fem_prmext_post");
		System.out.println("PASO 7");

		testCase.addTextEvidenceCurrentStep(resultadoEjecucion);

		u.close();

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Valida la funcionalidad de rutina de envios "
				+ "(Se debe pedir UPDATE para acctualizar el campo send_date_start de la tabla "
				+ "XXFC.XXFC_PROM_RESEND para que cumpla con el parametro de intentos de "
				+ "reenvio definido en la tabla RMS100.CODE_DETAIL)." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET. "
				+ "-JOBS: rr_setup.sh_2, pcovrlpq, xxfc_pre_prmext, xxfc_pmodstemp_prm, "
				+ "prmext, postprmext, fem_prmext_post, xxfc_procesa_promdnld_prm, runprm." + "-CASOS: "
				+ "MTC-FT-077 Validar funcionalidad de rutina de reenv�o de una promoci�n promopack incompleta que nunca tendr� la ligas.";
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
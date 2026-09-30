package RMS.CHO_52039366;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.Queries;
import RMS.util.PageObject.Promociones;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class MTC_FT_014_GenerarBajaPromocionSimple_TranType32 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_GenerarBajaPromocionSimple_TranType32(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		// Queries
		String promotion = data.get("promotion"); // id del registro que vamos a buscar en las tablas 
		String cb = null;
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SikuliUtil sk = new SikuliUtil();		
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);	
		Robot robot = new Robot();		
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Boolean st = false;
		Promociones p = new Promociones(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data);
		JobUtil job = new JobUtil(u, testCase, data);
		
		System.out.println(dbRms.getHost());
		
		//PASO 1
		addStep("Configurar baja de promoci�n simple para M�xico.");
		System.out.println("PASO 1");
		
		job.ejecutar("IT_setup.sh");
		job.ejecutar("dtesys");
		p.Login_En_RMS();
		p.Crear_Promocion();
		p.Configurar_Promocion_Simple_Item_List(); // falta programar las fechas para que ingrese en el RMS fecha inicio y fecha fin con la misma fecha de hoy.
		p.Submit_Promocion();
		job.ejecutar("rr_setup.sh_2");
		p.Validar_DB();
		p.Aprovar_Promocion();
		job.ejecutar("rr_setup.sh_2");
		
		String resultadoEjecucion = p.Validar_DB();
		assertEquals(resultadoEjecucion,"A");
		
		
		// PASO 2
		addStep("Revisar la promoci�n en las tablas de RMS PROMHEAD, PROMSTORE, PROMSKU (Status A)");
		System.out.println("PASO 2");

		// valida tabla PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD_STATUS_A, promotion); // cambia %s por valor de promotion
		System.out.println(promheadPromotion);

		SQLResult executeQuery = executeQuery(dbRms, promheadPromotion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		//assertFalse(statusQuery, "No se encontraron registros con promotion = " + promotion + " y status = 'A' en PROMHEAD");

		// valida tabla PROMSTORE
		String promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);
		String store = null; // guarda la tienda para usarse como identificador en la tabla WMUSER.POS_PRM_HEAD
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			store = executeQuery.getData(0, "STORE");
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + "en PROMSTORE");
		
		// valida tabla PROMSKU
		String promskuPromotion = String.format(Queries.PROMSKU, promotion);
		System.out.println(promskuPromotion);

		executeQuery = executeQuery(dbRms, promskuPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		//assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROMSKU");
		
		
		// PASO 3
		addStep("Ejecutar xxfc_pre_prmext en Control M");
		System.out.println("PASO 3");

		//resultadoEjecucion = job.ejecutar("xxfc_pre_prmext");
		//assertEquals(resultadoEjecucion, "Ended OK");
					
		
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

		//assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion +" en la tabla XXFC.XXFC_PROMO_PRMEXT_T");

		
		// PASO 5
		addStep("Ejecutar xxfc_pmodstemp_prm en Control M");
		System.out.println("PASO 5");
		
		//resultadoEjecucion = job.ejecutar("xxfc_pmodstemp_prm");
		//assertEquals(resultadoEjecucion, "Ended OK");
		
		
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
		
		//assertTrue(statusQuery, "Se encontraron registros en la tabla XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		
		// PASO 7
		addStep("Ejecutar prmext Control M");
		System.out.println("PASO 7");

		//resultadoEjecucion = job.ejecutar("prmext");
		//assertEquals(resultadoEjecucion, "Ended OK");

		
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

		//assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'E' en PROMSTORE");
	
		
		// PASO 9
		addStep("Ejecutar postprmext en Control M");
		System.out.println("PASO 9");

		//resultadoEjecucion = job.ejecutar("postprmext");
		//assertEquals(resultadoEjecucion, "Ended OK");

		
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
		addStep("Validar promoci�n simple en la Tabla xxfc.xxfc_pos_mods_prm_temp Tran type 31");
		System.out.println("PASO 11");

		String posModsPrmTempTranPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP, promotion);
		System.out.println(posModsPrmTempTranPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y TRAN_TYPE = 32 en XXFC.XXFC_POS_MODS_PRM_TEMP");

		
		// PASO 12
		addStep("Se ejecuta el job fem_prmext_post.");
		System.out.println("PASO 12");
		
		//resultadoEjecucion = job.ejecutar("fem_prmext_post");
		//assertEquals(resultadoEjecucion, "Ended OK");
		
		
		// PASO 13
		addStep("Se ejecutan los jobs xxfc_procesa_promdnld_prm y runPRM.");
		System.out.println("PASO 13");

		//resultadoEjecucion = job.ejecutar("xxfc_procesa_promdnld_prm");
		//assertEquals(resultadoEjecucion, "Ended OK");
		
		//resultadoEjecucion = job.ejecutar("runPRM");
		//assertEquals(resultadoEjecucion, "Ended OK");
		

		// PASO 14
		addStep("Validar en tabla wmuser.pos_prm_head");
		System.out.println("PASO 14");
		System.out.println(GlobalVariables.DB_HOST_FCRMSQA);
		
		String wmPosPrmHeadStore = String.format(Queries.WM_POS_PRM_HEAD, store);
		System.out.println(wmPosPrmHeadStore);

		executeQuery = executeQuery(dbRms, wmPosPrmHeadStore);
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		//assertFalse(statusQuery, "No se encontraron registros en WMUSER.POS_PRM_HEAD");

		
		// PASO 15
		addStep("Se valida que la promoci�n se encuentre en las tablas staging WMUSER.FEM_POS_MODS_PRM_STG.");
		System.out.println("PASO 15");
		
		String wmPosModsPromotion = String.format(Queries.WM_FEM_POS_MODS_PRM_STG, promotion);
		System.out.println(wmPosModsPromotion);

		executeQuery = executeQuery(dbRms, wmPosModsPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_MODS_PRM_STG");
		
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_GenerarBajaPromocionSimple_TranType32";
	}

	@Override
	public String setTestDescription() {
		return "Crear baja de promoci�n simple para que se genere el Tran_type 32";
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
	public void beforeTest() {}

}
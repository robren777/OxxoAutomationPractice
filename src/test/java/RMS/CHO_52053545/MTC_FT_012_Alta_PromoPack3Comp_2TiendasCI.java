package RMS.CHO_52053545;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

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
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class MTC_FT_012_Alta_PromoPack3Comp_2TiendasCI extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_Alta_PromoPack3Comp_2TiendasCI(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		// Queries
		String promotion = data.get("promotion"); // id del registro que vamos a buscar en las tablas 		
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
		
		System.out.println(dbRms.getHost());
		
		
		//PASO 1
		addStep("Configurar un Alta Promopack 3 componentes 2 "
				+ "tiendas con ligas completas y 2 con ligas incompletas");
		System.out.println("PASO 1");		
		
		su.get(URL);
		p.loguiRMS();
		p.CreateNewPromo();
		p.confPromoCompleja("percentOff");
		p.submitPromo();
		
		job.ejecutar("rr_setup.sh_2");
		p.RunDB();
		p.approvepromo();
		job.ejecutar("pcovrlpq");
		
		String resultadoEjecucion = p.RunDB();
		assertEquals(resultadoEjecucion,"A");
		
		p.closeRMS(); 
	    
		
// PASO 2
		addStep("Revisar la promoci�n en las tablas de RMS PROMHEAD (Status A), PROMSTORE, "
				+ "PROM_THRESHOLD_HEAD, PROM_THRESHOLD_DETAIL y PROM_THRESHOLD_SKU");
		System.out.println("PASO 2");

		// valida tabla PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD_STATUS_A, promotion); // cambia %s por valor de promotion
		System.out.println(promheadPromotion);

		SQLResult executeQuery = executeQuery(dbRms, promheadPromotion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con promotion = " + promotion + " y status = 'A' en PROMHEAD");

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
		
		// valida tabla PROM_THRESHOLD_HEAD
		String promThresholdHeadPromotion = String.format(Queries.PROM_THRESHOLD_HEAD, promotion);
		System.out.println(promThresholdHeadPromotion);

		executeQuery = executeQuery(dbRms, promThresholdHeadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_HEAD");
		
		// valida tabla PROM_THRESHOLD_DETAIL
		String promThresholdDetailPromotion = String.format(Queries.PROM_THRESHOLD_DETAIL, promotion);
		System.out.println(promThresholdDetailPromotion);

		executeQuery = executeQuery(dbRms, promThresholdDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROM_THRESHOLD_DETAIL");
		
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
		assertEquals(resultadoEjecucion, "Ended OK");
					
		
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
		
		resultadoEjecucion = job.ejecutar("xxfc_pmodstemp_prm");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		
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
		assertEquals(resultadoEjecucion, "Ended OK");

		
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
	
		
		// PASO 9
		addStep("Ejecutar prmext y postprmext en Control M  "
				+ "Nota: S�lo inserta la promoci�n para las tiendas que tienen ligas completas ");
		System.out.println("PASO 9");

		resultadoEjecucion = job.ejecutar("prmext");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		resultadoEjecucion = job.ejecutar("postprmext");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		
		// PASO 10
		addStep("Validar las promociones en las tablas PROMHEAD PROMSTORE "
				+ "Tabla XXFC.XXFC_POS_MODS_PRM_TEMPy Pos_Mods (Tran type 31)  pos_prom_detail");
		System.out.println("PASO 10");

		promheadPromotion = String.format(Queries.PROMHEAD, promotion);
		System.out.println(promheadPromotion);

		executeQuery = executeQuery(dbRms, promheadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROMHEAD");
		
		//PROMSTORE
		promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en PROMSTORE");
		
		// valida tabla XXFC_POS_MODS_PRM_TEMP
		String posModsPrmTempTranPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP_TRAN_31, promotion);
		System.out.println(posModsPrmTempTranPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y TRAN_TYPE = 31 en XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		// valida tabla POS_PROM_DETAIL
		String posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, posPromDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");
		
		// PASO 11
		addStep("Promoci�n-Tienda es eliminada de las tablas de "
				+ "env�o XXFC.XXFC_POS_MODS_PRM_TEMP y pos_prom_detail.");
		System.out.println("PASO 11");
		
		posModsPrmTempTranPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP_TRAN_31, promotion);
		System.out.println(posModsPrmTempTranPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempTranPromotion);

		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "Se encontraron registros con PROMOTION = " + promotion + " y TRAN_TYPE = 31 en XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		// valida tabla POS_PROM_DETAIL
		posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, posPromDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "Se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");
		
		
		// PASO 13
		addStep("Se actualiza tabla promstore, el campo extract_status "
				+ "regresa a nulo para las tiendas que no tienen ligado el art�culo.");
		System.out.println("PASO 13");

		promstorePromotion = String.format(Queries.PROMSTORE_STATUS_NULL, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " y EXTRACT_STATUS = NULL en PROMSTORE");		
		

		//PASO 14
		addStep("Se ejecutan los jobs xxfc_procesa_promdnld_prm");
		System.out.println("PASO 14");
		
		resultadoEjecucion = job.ejecutar("xxfc_procesa_promdnld_prm");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		
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

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_MODS_PRM_STG");
		
		// valida WMUSER.FEM_POS_PROM_DETAIL_STG
		String wmPospromDetailStgPromotion = String.format(Queries.WM_FEM_POS_PROM_DETAIL_STG, promotion);
		System.out.println(wmPospromDetailStgPromotion);

		executeQuery = executeQuery(dbRms, wmPospromDetailStgPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_PROM_DETAIL_STG");		
		
		// PASO 15
		addStep("Ejecutar runPRM");
		System.out.println("PASO 12");
		
		resultadoEjecucion = job.ejecutar("runPRM");
		assertEquals(resultadoEjecucion, "Ended OK");
		
				
		// PASO 16
		addStep("Validar la generaci�n de documento en tablas RMS");
		System.out.println("PASO 13");
		
		
		// PASO 17
		addStep("Validar la generaci�n de documento en tablas RMS");
		System.out.println("PASO 14");
		
		
		// PASO 18
		addStep("Validar log de ejecuci�n");
		System.out.println("PASO 15");
		
		
		// PASO 19
		addStep("Validar en el buzon de tienda el documento");
		System.out.println("PASO 16");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_Alta_PromoPack3Comp_2TiendasCI";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Alta Promopack 3 componentes 2 tiendas con ligas completas "
				+ "y 2 con ligas incompletas";
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
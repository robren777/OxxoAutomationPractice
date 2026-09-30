package RMS.CHO_52050085;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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

public class MTC_FT_017_ForzarFinalizacion_PromoCompleja_Plaza extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_ForzarFinalizacion_PromoCompleja_Plaza(HashMap<String, String> data) throws Exception {

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
		JobUtil job = new JobUtil(su, testCase, data);
		BuscadorPromo bp = new BuscadorPromo(dbRms);
		
		System.out.println("DB HOST: " + dbRms.getHost());
		
		//PASO 1
		addStep("Se toma una promoci�n a nivel plaza vigente para M�xico.");
		System.out.println("PASO 1");
		
		String promotion = bp.seleccionarPromoComplejaVigente();
		
		if (promotion != null) {
			testCase.addTextEvidenceCurrentStep(promotion + " es una promocion compleja nacional vigente.");
		}
		
		assertNotNull(promotion, "No se encontraron promociones nacionales vigentes");
		
		
		// PASO 2
		addStep("Se revisa validando que este configurada para 1 plaza. Promhead Promstore");
		System.out.println("PASO 2");
		
		
		// PASO 3
		addStep("Se realiza un update a la tabla promhead y promstore para actualizar la fecha "
				+ "fin de la promoci�n para que se procese su cancelaci�n "
				+ "(end_date = get_vdate)");
		System.out.println("PASO 3");
		
		System.out.println("Se debe pedir UPDATE a soporte. end_date = get_vdate WHERE PROMOTION = " + promotion);
				
		
		// PASO 3
		addStep("Se revisa validando que este configurada para 2 o m�s plazas.  Promhead Promstore");
		System.out.println("PASO 3");
		
		// valida tabla PROMHEAD
		String promheadPromotion = String.format(Queries.PROMHEAD, promotion); // cambia %s por valor de promotion
		System.out.println(promheadPromotion);

		SQLResult executeQuery = executeQuery(dbRms, promheadPromotion);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con promotion = " + promotion + " en PROMHEAD");

		// valida tabla PROMSTORE
		String promstorePromotion = String.format(Queries.PROMSTORE, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);
		String store = null; // guarda la tienda para usarse como identificador en la tabla WMUSER.POS_PRM_HEAD

		statusQuery = executeQuery.isEmpty();
		boolean dosOMasRegistros = false;
		
		if (!statusQuery) {
			store = executeQuery.getData(0, "STORE");
			
			if (executeQuery.getRowCount() >= 2) {
				testCase.addTextEvidenceCurrentStep("La promotion " + promotion + " tiene dos o mas registros en PROMSTORE");
				dosOMasRegistros = true;
			}
			
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + "en PROMSTORE");
		assertTrue(dosOMasRegistros, "La promotion " + promotion + " no tiene 2 o mas registros en PROMSTORE");
		
		
		// PASO 4
		addStep("Se ejecuta el job xxfc_pre_prmext");
		System.out.println("PASO 4");
		
		String resultadoEjecucion = job.ejecutar("xxfc_pre_prmext");
		assertEquals(resultadoEjecucion, "Ended Ok");
		
		
		// PASO 5
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

		
		// PASO 6
		addStep("Ejecutar xxfc_pmodstemp_prm en Control M");
		System.out.println("PASO 6");
		
		resultadoEjecucion = job.ejecutar("xxfc_pmodstemp_prm");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		
		// PASO 7
		addStep("Validar que no existan datos en la tabla xxfc.xxfc_pos_mods_prm_temp");
		System.out.println("PASO 7");
			
		String posModsPrmPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP, promotion);
		System.out.println(posModsPrmPromotion);
		
		executeQuery = executeQuery(dbRms, posModsPrmPromotion);
		
		statusQuery = executeQuery.isEmpty();
		
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(statusQuery, "Se encontraron registros en la tabla XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		
		// PASO 8
		addStep("Ejecutar prmext Control M");
		System.out.println("PASO 8");

		resultadoEjecucion = job.ejecutar("prmext");
		assertEquals(resultadoEjecucion, "Ended OK");

		
		// PASO 9
		addStep("Validar que haya sido extra�da la baja de la promoci�n por el prmext en XXFC.XXFC_POS_MODS_PRM_TEMP y pos_prom_detail");
		System.out.println("PASO 9");
		
		// valida XXFC.XXFC_POS_MODS_PRM_TEMP
		String posModsPrmTempPromotion = String.format(Queries.XXFC_POS_MODS_PRM_TEMP_PROMOTION, promotion);
		System.out.println(posModsPrmTempPromotion);

		executeQuery = executeQuery(dbRms, posModsPrmTempPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en XXFC.XXFC_POS_MODS_PRM_TEMP");
		
		// valida pos_prom_detail
		String posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " POS_PROM_DETAIL");
		
		
		// PASO 10
		addStep("Revisar que el prmext actualiza campo extract_status a M en la tabla promstore");
		System.out.println("PASO 10");
	
		promstorePromotion = String.format(Queries.PROMSTORE_STATUS_M, promotion);
		System.out.println(promstorePromotion);

		executeQuery = executeQuery(dbRms, promstorePromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " EXTRACT_STATUS = 'M' en PROMSTORE");
		
		
		// PASO 11	
		addStep(" Validar que el job Postprmext actualiza campo status a M en la tabla promhead.");
		System.out.println("PASO 11");
		
		//Ejecuta postprmext
		resultadoEjecucion = job.ejecutar("postprmext");
		assertEquals(resultadoEjecucion, "Ended OK");

		// Valida PROMHEAD con STATUS = M		
		promheadPromotion = String.format(Queries.PROMHEAD_STATUS_M, promotion);
		System.out.println(promheadPromotion);

		executeQuery = executeQuery(dbRms, promheadPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " STATUS = 'M' en PROMHEAD");
		
		
		// PASO 12
		addStep("Se ejecuta el job fem_prmext_post.");
		System.out.println("PASO 12");
		
		resultadoEjecucion = job.ejecutar("fem_prmext_post");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		
		// PASO 13
		addStep("Validar informacion en las tablas POS_PROM_DETAIL");
		System.out.println("PASO 13");
		
		posPromDetailPromotion = String.format(Queries.POS_PROM_DETAIL, promotion);
		System.out.println(posPromDetailPromotion);

		executeQuery = executeQuery(dbRms, posPromDetailPromotion);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en POS_PROM_DETAIL");
		
		
		// PASO 13
		addStep("Se ejecutan los jobs xxfc_procesa_promdnld_prm y runPRM.");
		System.out.println("PASO 13");

		resultadoEjecucion = job.ejecutar("xxfc_procesa_promdnld_prm");
		assertEquals(resultadoEjecucion, "Ended OK");
		
		resultadoEjecucion = job.ejecutar("runPRM");
		assertEquals(resultadoEjecucion, "Ended OK");
		

		// PASO 14
		addStep("Inserta en las tablas staging WMUSER.POS_PRM_HEAD");
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
				+ "WMUSER.FEM_POS_PROM_DETAIL_STG y WMUSER.FEM_POS_MODS_PRM_STG");
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
		String wmPosPromDetailStg = String.format(Queries.WM_FEM_POS_PROM_DETAIL_STG, promotion);
		System.out.println(wmPosPromDetailStg);

		executeQuery = executeQuery(dbRms, wmPosPromDetailStg);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " en WMUSER.FEM_POS_PROM_DETAIL_STG");
		
		u.close();
	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_ForzarFinalizacion_PromoCompleja_Plaza";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar el Forzar la finalizaci�n de una promoci�n nacional "
				+ "compleja vigente a nivel plaza";
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
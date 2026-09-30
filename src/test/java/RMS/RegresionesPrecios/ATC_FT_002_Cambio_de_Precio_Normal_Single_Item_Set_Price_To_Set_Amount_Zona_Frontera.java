package RMS.RegresionesPrecios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_Cambio_de_Precio_Normal_Single_Item_Set_Price_To_Set_Amount_Zona_Frontera extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Cambio_de_Precio_Normal_Single_Item_Set_Price_To_Set_Amount_Zona_Frontera_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		String cb = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);

		SQLResult executeQuery;
		boolean status = false;
		String costChange = null;

		// Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: 'http://10.184.16.184:8029/rmsQA.html' y Capturar los siguientes campos: \r\n"
				+ "<<<User>>>\r\n " + "<<<Password>>> " + "Dar clic en Logon \r\n");
		testCase.addScreenShotCurrentStep("");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Control, y dentro Setup y la opcion Price Zone Group y presiona new ");
		t.navPricingRetailCost();

		// Paso 3
		addStep("Seleccionar del campo Action la opci�n New.\r\n"
				+ "Seleccionar del campo Origin la opci�n Retail y llenar los siguientes campos:\r\n"
				+ "Retail Zone Group <<<>>>\r\n" + "Item Type <<< Single Item >>>\r\n" + "Item <<< #item >>>\r\n"
				+ "Presionar el boton OK");
		t.fillRetail();

		// Paso 4
		addStep("Se deben llenar los siguientes campos:\r\n" + "Price Change <<< Descripcion >>>\r\n"
				+ "Effective Date <<< Fecha >>>\r\n" + "Reason <<< 7 >>>\r\n" + "Presionar el boton Locations\r\n");
		costChange = t.fillRetailCostHeader();
		System.out.println(costChange);

		// Paso 5
		addStep("Llenar los siguientes campos:\r\n" + "Selection Type <<< >>>\r\n" + "Value <<< # >>>\r\n"
				+ "Comments <<< comentarios >>>\r\n" + "Dar click en Apply.\r\n"
				+ "Repetir en caso de agregar mas plazas.\r\n" + "Dar click en Ok.  \r\n");
		t.fillPriceChangeLocations();

		// Paso 6
		addStep("En la secci�n Event-Level Price Change Default llenar los siguientes campos:\r\n"
				+ "Type <<< Change Price By Amount >>>\r\n" + "Amount <<< >>>\r\n" + "UOM <<< mEA >>>\r\n"
				+ "Adjust <<< None >>>\r\n" + "Dar click en Apply\r\n" + " Y dar clic en el bot�n Item");
		t.fillEventLevelPrice();

		// Paso 7 - 8
		addStep("En la ventana Retail Price Change Item Maintenance se encuentra el n�mero de �tem y dar click en Item / Locs"
				+ "En el campo Retail se verifica el nuevo precio. Dar click en OK");
		t.verifyNewPrice();

		// Paso 9
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Submit.");
		t.submit();

		// Paso 10
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Approve.");
		t.approveRetailPrice1();

		t.close_RMS_RPCH2();

		t.CloseBrowser();

		String queryCost = String.format(
				"SELECT PRICE_CHANGE, ITEM, PC_SEQ_NO, ZONE_GROUP_ID FROM price_susp_detail where PRICE_CHANGE = '%s'",
				costChange);
		status = false;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.isEmpty();
		}

		//////////////////////////////////////////////////////////////////////

		assertFalse(executeQuery.isEmpty(), "No se encontraron registros");

		queryCost = String.format(
				"SELECT ZONE_ID, UNIT_RETAIL, SELLING_UOM MULTI_UNITS FROM price_susp_detail where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.isEmpty();
		}

		assertFalse(executeQuery.isEmpty(), "No se encontraron registros");

		/////////////////////////////////////////////////////////////////////////

		queryCost = String.format(
				"SELECT MULTI_UNIT_RETAIL, MULTI_SELLING_UOM, STATUS, PRICE_CHG_TYPE, DIFF_ID FROM price_susp_detail where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		String statusT = null;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusT = executeQuery.getData(0, "STATUS");
		}

		assertEquals(statusT, "A");

		/*
		 * Siguiente consulta en la tabla PRICE_SUSP_HEAD
		 */

		queryCost = String.format(
				"SELECT PRICE_CHANGE, PRICE_CHANGE_DESC, REASON, ACTIVE_DATE FROM price_susp_head where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.isEmpty();
		}

		assertFalse(executeQuery.isEmpty(), "No se encontraron registros");

		/////////////////////////////////////////////////////////////////

		queryCost = String.format(
				"SELECT STATUS, PC_ORIGIN, PC_TRAN_TYPE, CREATE_DATE, CREATE_ID FROM price_susp_head where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusT = executeQuery.getData(0, "STATUS");
		}

		assertEquals(statusT, "A");

		/////////////////////////////////////////////////////////////////

		queryCost = String.format(
				"SELECT APPROVAL_DATE, APPROVAL_ID, EXTRACT_DATE, IMPACT_QUANTITY FROM price_susp_head where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.isEmpty();
		}

		assertFalse(executeQuery.isEmpty(), "No se encontraron registros");

		/////////////////////////////////////////////////////////////////

		queryCost = String.format(
				"SELECT IMPACT_AMOUNT, IMPACT_DATE, VENDOR_FUNDED_IND, SUPPLIER FROM price_susp_head where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.isEmpty();
		}

		assertFalse(executeQuery.isEmpty(), "No se encontraron registros");

		/////////////////////////////////////////////////////////////////

		queryCost = String.format(
				"SELECT DEAL_ID, DEAL_DETAIL_ID, PARTNER_ID, PARTNER_TYPE, COMMENT_DESC FROM price_susp_head where PRICE_CHANGE = '%s'",
				costChange);
		status = true;
		System.out.println(queryCost);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryCost);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.isEmpty();
		}

		assertFalse(executeQuery.isEmpty(), "No se encontraron registros");

		/////////////////////////////////////////////////////////////////

		// Paso 11
		addStep("Ejecutar AIX_Precios en Control M Nota: Los JOBS a ejecutar son la pccext");

		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		JobUtil job = new JobUtil(u, testCase, data);
		u.get(data.get("ctrl_url"));
		u.waitWebElement(By.id("login-user-name"), 600, 2);
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.CtrlM_User,
				PasswordUtil.decryptPassword(GlobalVariables.CtrlM_Pss));
		cm.logOn();

		JSONObject obj1 = new JSONObject(data.get("job1"));
		ControlM job1 = new ControlM(u, testCase, obj1);
		u.hardWait(5);
		boolean flag1 = job1.searchFolder();
		assertTrue(flag1);

		job1.accionSinOutput();

		u.hardWait(3);
		job1.closeViewpoint();
		u.hardWait(15);

		JSONArray array = new JSONArray(data.get("jobs"));

		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		u.hardWait(3);

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB pctranex");
		String resultado = job.ejecutarSetOk("pctranex");
		assertEquals(resultado, "Ended OK");

		///////////////////////////////////////////////////////////////

		JSONObject obj2 = new JSONObject(data.get("job2"));
		ControlM job2 = new ControlM(u, testCase, obj2);
		u.hardWait(5);
		boolean flag2 = job1.searchFolder();
		assertTrue(flag2);

		job2.accionSinOutput();

		u.hardWait(3);
		job2.closeViewpoint();
		u.hardWait(10);

		System.out.println(costChange);

		/**
		 * PASO 12
		 ******************************************************************************************/
		addStep("Ejecutar PR26");

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB rr_setup_sh_9");
		resultado = job.ejecutar("rr_setup_sh_9");
		assertEquals(resultado, "Ended OK");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB rule_check_itm");
		resultado = job.ejecutar("rule_check_itm");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxfc_api_price");
		resultado = job.ejecutar("xxfc_api_price");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB pcdnld");
		resultado = job.ejecutar("pcdnld");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB fem_chk_lock_POS_MODS");
		resultado = job.ejecutar("fem_chk_lock_pos_mods");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxfcpmodsstaging");
		resultado = job.ejecutar("xxfcpmodsstaging");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB fem_posdnld_pre");
		resultado = job.ejecutar("fem_posdnld_pre");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxreo_fcp_pos_mods");
		resultado = job.ejecutar("xxreo_fcp_pos_mods");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB xxfc_procesa_posdnld_itm");
		resultado = job.ejecutar("xxfc_procesa_posdnld_itm");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB runITM");
		resultado = job.ejecutar("runitm");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB PR35");
		resultado = job.ejecutar("pr35");
		assertEquals(resultado, "Wait Condition");

		///////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB PR36");
		resultado = job.ejecutar("pr36");
		assertEquals(resultado, "Wait Condition");

		////////////////////////////////////////////////////////////////

		u.quit();

		// Paso 14
		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el art�culo-tienda.");
		String query;
		String store = data.get("store");
		;
		String item = data.get("Item");
		query = String.format(
				"select TRAN_TYPE, ITEM, ITEM_NUMBER_TYPE, FORMAT_ID from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select PREFIX, ITEM_LONG_DESC, ITEM_SHORT_DESC, REF_ITEM from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select REF_ITEM_NUMBER_TYPE, REF_FORMAT_ID, REF_PREFIX, DEPT from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select CLASS, SUBCLASS, STORE, NEW_PRICE from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select NEW_SELLING_UOM, OLD_PRICE, OLD_SELLING_UOM, START_DATE from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select NEW_MULTI_UNITS, OLD_MULTI_UNITS, NEW_MULTI_UNIT_RETAIL, NEW_MULTI_SELLING_UOM from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select OLD_MULTI_UNIT_RETAIL, OLD_MULTI_SELLING_UOM, STATUS, TAXABLE_IND from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select PROMOTION, MIX_MATCH_NO, MIX_MATCH_TYPE, THRESHOLD_NO from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select START_TIME, END_TIME, LAUNCH_DATE, QTY_KEY_OPTIONS from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select MANUAL_PRICE_ENTRY, DEPOSIT_CODE, FOOD_STAMP_IND, WIC_IND from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select PROPORTIONAL_TARE_PCT, FIXED_TARE_VALUE, FIXED_TARE_UOM, REWARD_ELIGIBLE_IND from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select ELECT_MTK_CLUBS, RETURN_POLICY, STOP_SALE_IND, RETURNABLE_IND from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select REFUNDABLE_IND, BACK_ORDER_IND, VAT_CODE, VAT_RATE from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");

		/////////////////////////////////////////////////////

		query = String.format(
				"select CLASS_VAT_IND, CREATE_DATETIME, LAST_UPDATE_DATETIME, LAST_UPDATE_ID from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)",
				item, store, "11");
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		assertFalse(executeQuery.isEmpty(), "No existen registros con el tran type 11");
		
		
		
		testCase.addBoldTextEvidenceCurrentStep("Validar que se genero el Batch_id");
		
		query = String.format("select BATCH_ID, LOCATION, ARCHIVO, LOAD_DATE from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc", store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select LOAD_WEEK, LOAD_BATCH_ID, WM_ITM_STATUS, WM_PRM_STATUS from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc", store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		String loadBatchID = executeQuery.getData(0, "LOAD_BATCH_ID");
		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select WM_TARGET_ITM, WM_TARGET_PRM from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc", store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		
		/***********************************************************************************************************
		 * ********************************************************************************************************************************
		 */
		
		testCase.addBoldTextEvidenceCurrentStep("Validar que se encuentre el registro del alta del articulo que se proceso dentro del Batch_id de la ejecucion. ");
		
		query = String.format("select STORE, UPDATE_TYPE, START_DATE, TIME from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select TRAN_TYPE, ITEM, ITEM_NUMBER_TYPE, FORMAT_ID from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select PREFIX, REF_ITEM, REF_ITEM_NUMBER_TYPE, REF_FORMAT_ID from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select REF_PREFIX, ITEM_SHORT_DESC, ITEM_LONG_DESC, DEPT from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select CLASS, SUBCLASS, NEW_PRICE, NEW_SELLING_UOM  from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select NEW_MULTI_UNITS, NEW_MULTI_UNIT_RETAIL, NEW_MULTI_SELLING_UOM, STATUS  from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select TAXABLE_IND, PROMOTION, MIX_MATCH_NO, MIX_MATCH_TYPE  from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select THRESHOLD_NO, LAUNCH_DATE, QTY_KEY_OPTIONS, MANUAL_PRICE_ENTRY from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select DEPOSIT_CODE, FOOD_STAMP_IND, WIC_IND, PROPORTIONAL_TARE_PCT from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select FIXED_TARE_VALUE, FIXED_TARE_UOM, REWARD_ELIGIBLE_IND, ELECT_MTK_CLUBS from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select RETURN_POLICY, STOP_SALE_IND, RETURNABLE_IND, REFUNDABLE_IND from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select BACK_ORDER_IND, VAT_CODE, VAT_RATE, CLASS_VAT_IND from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select LINE_ID, LOAD_TIMESTAMP, LOAD_WEEK, LOAD_BATCH_ID from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'", loadBatchID, store, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		
		/***********************************************************************************************************
		 * 
		 ************************************************************************************************************/
		
		
		testCase.addBoldTextEvidenceCurrentStep("Validar que se genero el archivo ITM");
		
		query = String.format("select BATCH_ID, LOCATION, ARCHIVO, LOAD_DATE from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc", store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select LOAD_WEEK, LOAD_BATCH_ID, WM_ITM_STATUS, WM_PRM_STATUS from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc", store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
		/////////////////////////////////////////////////////////
		
		query = String.format("select WM_TARGET_ITM, WM_TARGET_PRM from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc", store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(executeQuery.isEmpty(), "No se encontraron registros");
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Cambio_de_Precio_Normal_Single_Item_Set_Price_To_Set_Amount_Zona_Frontera_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_002_Cambio_de_Precio_Normal_Single_Item_Set_Price_To_Set_Amount_Zona_Frontera";
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

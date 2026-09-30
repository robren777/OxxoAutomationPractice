package RMS.RegresionesRecetas;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_AltadeRecetaMREGUL_PLU_IVA16_Interior extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AltadeRecetaMREGUL_PLU_IVA16_Interior_test(HashMap<String, String> data) throws Exception {

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
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		

		System.out.println(dbRms.getHost());
		String status;
		SQLResult executeQuery;
		
		String loc = data.get("groupValue");

		System.out.println("************************************PASO 1 - 2*******************************************");
		addStep("Ingresar a RMS a través de la siguiente liga: \r\n"
				+ "http://10.184.16.184:8029/rmsQA.html"
				+ "Capturar los siguientes campos: \r\n"
				+ "<<<User>>>\r\n"
				+ "<<<Password>>>\r\n"
				+ "Y dar clic en Logon\r\n"
				+ "");
		testCase.addScreenShotCurrentStep("");
		t.Login_En_RMS();

		System.out.println("************************************PASO 3*******************************************");// PASO 3
		addStep("Seleccionar el folder items, elegir la opcion items y dar clic en el botón open");
		t.navItems();

		System.out.println("************************************PASO 4*******************************************");
		addStep("Seleccionar en el campo Action la opción New item.\r\n"
				+ "Llenar los siguientes campos según corresponda:\r\n"
				+ "Subcategoria <<<subcategoria>>>\r\n"
				+ "Segmento <<<segmento>>>\r\n"
				+ "Subsegmento <<<subsegmento>>>\r\n"
				+ "Pack item <<<Yes>>>\r\n");
		t.NewItem_2();

		System.out.println("************************************PASO 5*******************************************");
		addStep("Capturar o seleccionar la opción correspondiente de los siguientes campos:\r\n"
				+ "item Type <<<PLU>>>\r\n"
				+ "item <<<número item>>>\r\n"
				+ "Description <<<Descripción Larga Item>>>\r\n"
				+ "Short Desc <<<Descripción corta Item>>>\r\n"
				+ "Tran Level <<<Level 1>>>\r\n"
				+ "Packs <<<Complex>>>\r\n"
				+ "Sellable <<<Check>>>\r\n"
				+ "Merchandise <<<Check>>>\r\n"
				+ "Catch Weight <<<Check>>>\r\n"
				+ "Constant Dimensions <<<Check>>>\r\n"
				+ "Retail Zone <<<1>>>\r\n"
				+ "Cost Zone <<<1>>>\r\n"
				+ "Selling Unit Retail <<<Precio Unitario de Venta>>>\r\n"
				+ "Suggested Retail <<<Precio Sugerido de Venta>>>\r\n"
				+ "Store Order Multiple <<<Eaches>>>\r\n"
				+ "Standard UOM <<<EA>>>\r\n"
				+ "Dar clic en Groc Attributes");
		
		String queryITEM = "Select * from item_master where ITEM_NUMBER_TYPE = 'PLU' and item LIKE '60%' and length(item) = '5' and rownum <=5 order by ITEM DESC";
		String newItem = "";
		boolean statusQueryITEM = false;
		System.out.println("Consulta: " + queryITEM);
		executeQuery = executeQuery(dbRms, queryITEM);
		if (!executeQuery.isEmpty()) {
			newItem = executeQuery.getData(0, "ITEM");
			
			statusQueryITEM = executeQuery.isEmpty();
		}
		
		assertFalse(statusQueryITEM, "No se encontraron registros");
		
		int item = t.fil_ItemVirtual2(newItem);

		System.out.println("************************************PASO 6*******************************************");
		addStep("Capturar los datos correspondientes en los siguientes campos:\r\n"
				+ "package RMS.Size <<<1>>>\r\n"
				+ "package RMS.UOM <<<EA>>>\r\n"
				+ "Dar clic en el botón OK.");
		t.FillItemMaster();
		
		System.out.println("************************************PASO 7*******************************************");
		addStep("Dar clic en el boton Pack Details");
		t.ClicPackDetails();
	
		System.out.println("************************************PASO 8*******************************************");
		addStep("Dar clic en el botón Add item, en el campo Item, seleccionar un item de la lista, capturar la cantidad en el campo Quantity y dar clic en el botón Apply. ");
		t.fillPackITemDetailMaintenance();
		
		System.out.println("************************************PASO 9*******************************************");
		addStep("Dar clic en la opción Retail by Zone despliega la zona seleccionada y se da clic en OK. ");
		t.clicRetailByZone();
		t.clicBtnOKRecetas();
		
		System.out.println("************************************PASO 10*******************************************");
		addStep("Dar clic en la opción Location.");
		t.clicLocation();
		
		System.out.println("************************************PASO 11*******************************************");
		addStep("En la ventana Item Location seleccionar los siguientes campos que se encuentran en la parte inferior izquierda de la pantalla: Group Type <<<Store>>>Group Value <<<#Tienda>>>\r\n"
				+ "Presionar el botón Apply Dar clic en el botón OK.");
		t.fillLocations();

		System.out.println("************************************PASO 12*******************************************");
		addStep("Dar clic en la opcion Submit.");
		t.submitReceta();
		
		System.out.println("************************************PASO 13*******************************************");
		addStep("Dar clic en la opción Approve y presionar el botón OK.");
		t.approveRI();
		status = t.validarStatus();
		assertEquals(status, "Approved");
		
		t.clicBtnOKRecetas();
		t.close_RMS_2();
		su.close();
		
		
		System.out.println("************************************PASO 14*******************************************");
		addStep("Validar que la información del item se vea reflejada en las tablas de la BD");
		System.out.println("*******************************************************************************");
		System.out.println("******************************ITEM_LOC*****************************************");
		System.out.println("*******************************************************************************");
		String query;
		boolean statusQuery;
		testCase.addBoldTextEvidenceCurrentStep("ITEM_LOC");
		
		statusQuery = false;
		query = "Select ITEM, LOC, ITEM_PARENT, ITEM_GRANDPARENT from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select LOC_TYPE, UNIT_RETAIL, SELLING_UNIT_RETAIL, SELLING_UOM from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select PROMO_RETAIL, PROMO_SELLING_RETAIL, PROMO_SELLING_UOM, CLEAR_IND from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select TAXABLE_IND, LOCAL_ITEM_DESC, LOCAL_SHORT_DESC, TI from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select HI, STORE_ORD_MULT, STATUS, STATUS_UPDATE_DATE from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select DAILY_WASTE_PCT, MEAS_OF_EACH, MEAS_OF_PRICE, UOM_OF_PRICE from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select PRIMARY_VARIANT, PRIMARY_COST_PACK, PRIMARY_SUPP, PRIMARY_CNTRY from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////

		statusQuery = false;
		query = "Select RECEIVE_AS_TYPE, CREATE_DATETIME, LAST_UPDATE_DATETIME, LAST_UPDATE_ID from item_loc where status = 'A' and loc = '%s' and item = '%s'";
		
		query = String.format(query, loc, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("*******************************************************************************");
		System.out.println("******************************ITEM_MASTER**************************************");
		System.out.println("*******************************************************************************");
		
		
		testCase.addBoldTextEvidenceCurrentStep("ITEM_MASTER");
		
		statusQuery = false;
		query = "Select ITEM, ITEM_NUMBER_TYPE, FORMAT_ID, PREFIX from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		
		statusQuery = false;
		query = "Select ITEM_PARENT, ITEM_GRANDPARENT, PACK_IND, ITEM_LEVEL from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select TRAN_LEVEL, ITEM_AGGREGATE_IND, DIFF_1, DIFF_1_AGGREGATE_IND from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select DIFF_2, DIFF_2_AGGREGATE_IND, DIFF_3, DIFF_3_AGGREGATE_IND  from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select DIFF_4, DIFF_4_AGGREGATE_IND, DEPT, CLASS from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		
		statusQuery = false;
		query = "Select SUBCLASS, STATUS, ITEM_DESC, SHORT_DESC from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select DESC_UP, PRIMARY_REF_ITEM_IND, RETAIL_ZONE_GROUP_ID, COST_ZONE_GROUP_ID from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select STANDARD_UOM, UOM_CONV_FACTOR, PACKAGE_SIZE, PACKAGE_UOM from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select MERCHANDISE_IND, STORE_ORD_MULT, FORECAST_IND, ORIGINAL_RETAIL from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select MFG_REC_RETAIL, RETAIL_LABEL_TYPE, RETAIL_LABEL_VALUE, HANDLING_TEMP from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select HANDLING_SENSITIVITY, CATCH_WEIGHT_IND, FIRST_RECEIVED, LAST_RECEIVED from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select QTY_RECEIVED, WASTE_TYPE, WASTE_PCT, DEFAULT_WASTE_PCT from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select CONST_DIMEN_IND, SIMPLE_PACK_IND, CONTAINS_INNER_IND, SELLABLE_IND from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select ORDERABLE_IND, PACK_TYPE, ORDER_AS_TYPE, COMMENTS from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select ITEM_SERVICE_LEVEL, GIFT_WRAP_IND, SHIP_ALONE_IND, CREATE_DATETIME from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select LAST_UPDATE_ID, LAST_UPDATE_DATETIME, CHECK_UDA_IND from item_master where item = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("*******************************************************************************");
		System.out.println("*******************************PACKITEM****************************************");
		System.out.println("*******************************************************************************");
		
		
		testCase.addBoldTextEvidenceCurrentStep("PACkITEM");
		
		statusQuery = false;
		query = "Select PACK_NO, SEQ_NO, ITEM, ITEM_PARENT from packitem where pack_no = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select PACK_TMPL_ID, PACK_QTY, CREATE_DATETIME, LAST_UPDATE_DATETIME from packitem where pack_no = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		statusQuery = false;
		query = "Select LAST_UPDATE_ID from packitem where pack_no = '%s'";
		
		query = String.format(query, item);
		System.out.println("Consulta: " + query);
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		
		assertFalse(statusQuery, "No se encontraron registros");
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		
		System.out.println("************************************PASO 15*******************************************");
		addStep("Validar que se haya insertado el alta del artículo receta en la tabla POS_MODS con tran type 1");
		String tranType = "1";
		
		query = String.format("select TRAN_TYPE, ITEM, ITEM_NUMBER_TYPE, FORMAT_ID from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select PREFIX, ITEM_LONG_DESC, ITEM_SHORT_DESC, REF_ITEM from POS_MODS where ITEM = '%s' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select REF_ITEM_NUMBER_TYPE, REF_FORMAT_ID, REF_PREFIX, DEPT from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select CLASS, SUBCLASS, STORE, NEW_PRICE from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select NEW_SELLING_UOM, OLD_PRICE, OLD_SELLING_UOM, START_DATE from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select NEW_MULTI_UNITS, OLD_MULTI_UNITS, NEW_MULTI_UNIT_RETAIL, NEW_MULTI_SELLING_UOM from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select OLD_MULTI_UNIT_RETAIL, OLD_MULTI_SELLING_UOM, STATUS, TAXABLE_IND from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select PROMOTION, MIX_MATCH_NO, MIX_MATCH_TYPE, THRESHOLD_NO from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select START_TIME, END_TIME, LAUNCH_DATE, QTY_KEY_OPTIONS from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);
		
		/////////////////////////////////////////////////////

		query = String.format(
				"select MANUAL_PRICE_ENTRY, DEPOSIT_CODE, FOOD_STAMP_IND, WIC_IND from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select PROPORTIONAL_TARE_PCT, FIXED_TARE_VALUE, FIXED_TARE_UOM, REWARD_ELIGIBLE_IND from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select ELECT_MTK_CLUBS, RETURN_POLICY, STOP_SALE_IND, RETURNABLE_IND from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);

		/////////////////////////////////////////////////////

		query = String.format(
				"select REFUNDABLE_IND, BACK_ORDER_IND, VAT_CODE, VAT_RATE from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);
		
		/////////////////////////////////////////////////////

		query = String.format(
				"select CLASS_VAT_IND, CREATE_DATETIME, LAST_UPDATE_DATETIME, LAST_UPDATE_ID from POS_MODS where ITEM = '%S' AND TRAN_TYPE = '%s'",
				item, tranType);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el tran type: " + tranType);
		
		
		System.out.println("************************************PASO 16*******************************************");
		addStep("Ejecutar la cadena de jobs del grupo AIX_PRECIOS_Y_PROMOCIONES hasta el job XXFC_PROCESA_POSDNLD_ITM en Control-M");
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		JobUtil job = new JobUtil(u, testCase, data);
		u.get(data.get("ctrl_url"));
		u.waitWebElement(By.id("login-user-name"), 600, 2);
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.CtrlM_User, PasswordUtil.decryptPassword(GlobalVariables.CtrlM_Pss));
		cm.logOn();
		String resultado;
		
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
		
		System.out.println("************************************PASO 17*******************************************");
		addStep("Validar que se haya generado el Batch_id de la ejecución en la tabla WMUSER.POS_ITM_PRM_HEAD.");
		
		String loadBatchID = "";
		
		query = String.format("select BATCH_ID, LOCATION, ARCHIVO, LOAD_DATE from WMUSER.POS_ITM_PRM_HEAD where location = '%s' and rownum <=10 order by load_date desc",
				loc);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		/////////////////////////////////////////////////////
		
		query = String.format("select LOAD_WEEK, LOAD_BATCH_ID, WM_ITM_STATUS, WM_PRM_STATUS from WMUSER.POS_ITM_PRM_HEAD where location = '%s' and rownum <=10 order by load_date desc",
				loc);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			loadBatchID = executeQuery.getData(0, "LOAD_BATCH_ID");
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		/////////////////////////////////////////////////////
		
		query = String.format("select WM_TARGET_ITM, WM_TARGET_PRM from WMUSER.POS_ITM_PRM_HEAD where location = '%s' and rownum <=10 order by load_date desc",
				loc);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		/////////////////////////////////////////////////////
		
		System.out.println("************************************PASO 18*******************************************");
		addStep("Validar en la tabla WMUSER.POS_MODS_STG que se encuentre el registro del alta del artículo  que se procesó dentro del Batch_id de la ejecución.");
		
		query = String.format("select STORE, START_DATE, TIME, TRAN_TYPE, ITEM from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
				loadBatchID);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);
		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select TRAN_TYPE, ITEM, ITEM_NUMBER_TYPE, FORMAT_ID from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		S
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PREFIX, REF_ITEM, REF_ITEM_NUMBER_TYPE, REF_FORMAT_ID from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select REF_PREFIX, ITEM_SHORT_DESC, ITEM_LONG_DESC, DEPT from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select CLASS, SUBCLASS, NEW_PRICE, NEW_SELLING_UOM from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select NEW_MULTI_UNITS, NEW_MULTI_UNIT_RETAIL, NEW_MULTI_SELLING_UOM, STATUS from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select TAXABLE_IND, PROMOTION, MIX_MATCH_NO, MIX_MATCH_TYPE from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select THRESHOLD_NO, LAUNCH_DATE, QTY_KEY_OPTIONS, MANUAL_PRICE_ENTRY from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select DEPOSIT_CODE, FOOD_STAMP_IND, WIC_IND, PROPORTIONAL_TARE_PCT from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select FIXED_TARE_VALUE, FIXED_TARE_UOM, REWARD_ELIGIBLE_IND, ELECT_MTK_CLUBS from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select RETURN_POLICY, STOP_SALE_IND, RETURNABLE_IND, REFUNDABLE_IND from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select BACK_ORDER_IND, VAT_CODE, VAT_RATE, CLASS_VAT_IND from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select LINE_ID, LOAD_TIMESTAMP, LOAD_WEEK, LOAD_BATCH_ID from WMUSER.FEM_POS_MODS_STG where load_batch_id = '%s' order by start_date desc",
//				loadBatchID);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = executeQuery.isEmpty();
//		}
//		assertFalse(statusQuery, "No existen registros con el location: " + loc);
//		
//		//////////////////////////////////////////////////////////////////////////////
		
		System.out.println("************************************PASO 19*******************************************");
		addStep("Ejecutar el Job runITM en Control-M");
		
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB runITM");
		resultado = job.ejecutar("runitm");
		assertEquals(resultado, "Wait Condition");
		
		System.out.println("************************************PASO 20*******************************************");
		addStep("Validar en la tabla WMUSER.POS_ITM_PRM_HEAD, dentro del Batch_id de la ejecución, que se haya generado el archivo ITM");
		
		String vmTargetItm = "";
		
		query = String.format("select BATCH_ID, LOCATION, ARCHIVO, LOAD_DATE from WMUSER.POS_ITM_PRM_HEAD where location = '%s' and rownum <=10 order by load_date desc",
				loc);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		/////////////////////////////////////////////////////
		
		query = String.format("select LOAD_WEEK, LOAD_BATCH_ID, WM_ITM_STATUS, WM_PRM_STATUS from WMUSER.POS_ITM_PRM_HEAD where location = '%s' and rownum <=10 order by load_date desc",
				loc);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		/////////////////////////////////////////////////////
		
		query = String.format("select WM_TARGET_ITM, WM_TARGET_PRM from WMUSER.POS_ITM_PRM_HEAD where location = '%s' and rownum <=10 order by load_date desc",
				loc);
		System.out.println(query);
		executeQuery = executeQuery(dbRms, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			vmTargetItm = executeQuery.getData(0, "WM_TARGET_ITM");
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		/////////////////////////////////////////////////////
		
		System.out.println("************************************PASO 21*******************************************");
		addStep("Validar que se encuentre el archivo ITM en la tabla POSUSER.POS_OUTBOUND_DOCS");
		
		SQLUtil dbPOS = new SQLUtil(GlobalVariables.DB_HOST_FCWM6QA, GlobalVariables.DB_USER_FCWM6QA,
				GlobalVariables.DB_PASSWORD_FCWM6QA);
		
		query = String.format("select ID, POE_ID, TARGET_TYPE, DOC_NAME from POSUSER.POS_OUTBOUND_DOCS where doc_name = '%s' and rownum <=10",
				vmTargetItm);
		System.out.println(query);
		executeQuery = executeQuery(dbPOS, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);
		
		//////////////////////////////////////////////////////////////////////////
		
		query = String.format("select DOC_TYPE, SENT_DATE, PV_CR_PLAZA, PV_CR_TIENDA, STATUS from POSUSER.POS_OUTBOUND_DOCS where doc_name = '%s' and rownum <=10",
				vmTargetItm);
		System.out.println(query);
		executeQuery = executeQuery(dbPOS, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);
		
		//////////////////////////////////////////////////////////////////////////
		
		query = String.format("select SOURCE_ID, DB_LOADED, DATE_CREATED, DATE_SENT from POSUSER.POS_OUTBOUND_DOCS where doc_name = '%s' and rownum <=10",
				vmTargetItm);
		System.out.println(query);
		executeQuery = executeQuery(dbPOS, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);
		
		//////////////////////////////////////////////////////////////////////////
		
		query = String.format("select DATE_CONFIRM, NB_SENT, SOURCEAPP, PARTITION_DATE from POSUSER.POS_OUTBOUND_DOCS where doc_name = '%s' and rownum <=10",
				vmTargetItm);
		System.out.println(query);
		executeQuery = executeQuery(dbPOS, query);

		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = executeQuery.isEmpty();
		}
		assertFalse(statusQuery, "No existen registros con el location: " + loc);
		
		//////////////////////////////////////////////////////////////////////////
		
		System.out.println("************************************PASO 22*******************************************");
		addStep("Ejecutar el Job PR35 en Control-M");
		assertFalse(statusQuery, "No existen registros con el location: " + loc);

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB PR35");
		resultado = job.ejecutar("pr35");
		assertEquals(resultado, "Wait Condition");
		
		///////////////////////////////////////////////////////////////

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB PR36");
		resultado = job.ejecutar("pr36");
		assertEquals(resultado, "Wait Condition");
		
		////////////////////////////////////////////////////////////////
		
		System.out.println("************************************PASO 23*******************************************");
		addStep("Validar en la tabla POSUSER.POS_OUTBOUND_DOCS que se haya generado el archivo PCC");
		
		
		
		
		
		
		
		
	
		

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
		return "ATC_FT_001_AltadeRecetaMREGUL_PLU_IVA16_Interior_test";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Modificacion_Receta";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

package RMS.RegresionTransferencias;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.RDM.TransferenciasRDM;
import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.Transferencias;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_CrearTransferenciaenRMSCedis_TienaconenviodeASN extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CrearTransferenciaenRMSCedis_TienaconenviodeASN_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		String cb = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();		
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);	
		Robot robot = new Robot();		
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Transferencias t = new Transferencias (sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		
		boolean ejecucionCorrectaJob = false;
		SQLResult executeQuery;
		String query;
		String transfer;
		String item;
		String store;
		String cedis;
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
//		t.loginRMS();	
//		
//		//Paso 2
//		addStep("Seleccionar el men� Invetory, elegir la opci�n Transfer y dar click en Open.");
//		t.navInventoryTransfer();
//		
//		//Paso 3
//		addStep("En el campo Action seleccionar la opci�n �New�  y en el campo Type seleccionar �Manual Requisition�. Dar click en OK.");
//		t.actionNewTransfer();
//
//		//Paso 4
//		addStep("En el campo Freight Type seleccionar la opci�n �Normal�, en el apartado From seleccionar la opci�n �Warehouse� y llenar el campo solicitado.\r\n" + 
//				"En el apartado To seleccionar la opci�n �Store� y llenar el campo solicitado. Dar click en Items.\r\n" + 
//				"");
//		t.fillNewTransfer();
//		
//		
//		//Paso 5
//		addStep("En la base de datos seleccionar el art�culo a transferir:\r\n"
//				+ "Revisar la tienda destino\r\n"
//				+ "");
//		item = data.get("item");
//		store = data.get("location_to");
//
//		query = String.format("select ITEM, ITEM_PARENT, ITEM_GRANDPARENT, LOC from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select LOC_TYPE, AV_COST, UNIT_COST, STOCK_ON_HAND from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select SOH_UPDATE_DATETIME, LAST_HIST_EXPORT_DATE, IN_TRANSIT_QTY, PACK_COMP_INTRAN from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PACK_COMP_SOH, TSF_RESERVED_QTY, PACK_COMP_RESV, TSF_EXPECTED_QTY from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PACK_COMP_EXP, RTV_QTY, NON_SELLABLE_QTY, CUSTOMER_RESV from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select CUSTOMER_BACKORDER, PACK_COMP_CUST_RESV, PACK_COMP_CUST_BACK, CREATE_DATETIME from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select LAST_UPDATE_DATETIME, LAST_UPDATE_ID, FIRST_RECEIVED, LAST_RECEIVED from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select QTY_RECEIVED, FIRST_SOLD, LAST_SOLD, QTY_SOLD from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PRIMARY_SUPP, PRIMARY_CNTRY from item_loc_soh where item = '%s' and LOC = '%s'", item, store);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/****************************************************************************************************
//		 ****************************************************************************************************
//		 ****************************************************************************************************/
//		
//		testCase.addBoldTextEvidenceCurrentStep("Revisar el cedis destino en la base de datos");
//		
//		cedis = data.get("location_from");
//
//		query = String.format("select ITEM, ITEM_PARENT, ITEM_GRANDPARENT, LOC from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select LOC_TYPE, AV_COST, UNIT_COST, STOCK_ON_HAND from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select SOH_UPDATE_DATETIME, LAST_HIST_EXPORT_DATE, IN_TRANSIT_QTY, PACK_COMP_INTRAN from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PACK_COMP_SOH, TSF_RESERVED_QTY, PACK_COMP_RESV, TSF_EXPECTED_QTY from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PACK_COMP_EXP, RTV_QTY, NON_SELLABLE_QTY, CUSTOMER_RESV from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select CUSTOMER_BACKORDER, PACK_COMP_CUST_RESV, PACK_COMP_CUST_BACK, CREATE_DATETIME from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select LAST_UPDATE_DATETIME, LAST_UPDATE_ID, FIRST_RECEIVED, LAST_RECEIVED from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select QTY_RECEIVED, FIRST_SOLD, LAST_SOLD, QTY_SOLD from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//		
//		query = String.format("select PRIMARY_SUPP, PRIMARY_CNTRY from item_loc_soh where item = '%s' and LOC = '%s'", item, cedis);
//		System.out.println(query);
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		/////////////////////////////////////////////////////////////////////////
//
//		
//		
//		//Paso 6 - 8
//		addStep("Seleccionar la opci�n �Item� del campo Item Type, ingresar el id del art�culo a transferir y dar click en la tecla enter. "
//				+ "Seleccionar la opci�n �Manual� del campo Qty Type."
//				+ "Ingresar la cantidad a transferir en el campo Trans Qty. Dar click en Apply.");
//		t.fillNewTransferItem();
//		
//		//Paso 9
//		addStep("Del men� Options seleccionar la opci�n �Submit� y dar click en OK.");
//		t.submit();
//		t.clickBtnOkU();
//		
//		//Paso 10
//		addStep("En el campo Action seleccionar la opci�n �Edit�, en el campo Type seleccionar �Manual Requisition� y en el campo Transfer capturar el id de la transferencia. Dar click en OK.");
//		t.actionEditTransfer();
//		
//		//Paso 11
//		addStep("Del men� Options seleccionar la opci�n �Approve� y dar click en OK.");
//		t.approve();
//		t.clickBtnOkU();
//		
//		t.close_RMS_2();
//		
//		t.CloseBrowser();
//		
//		
//		//Paso 12 - 13
//		addStep("Se ingresa a la base de datos de RMS QA"
//				+ "Se valida en la tabla TSF_HEAD que el status este en A");
//		cb = App.getClipboard();
//		System.out.println(cb);
//		transfer = cb;
//
//		query = String.format(QueriesRegresion.TSFHEAD, transfer);
//		System.out.println(query);
//		System.out.println("conexion = "+ dbRms.getConn().toString());
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertEquals(executeQuery.getData(0, "STATUS"), "A");
//		
//		//Paso 14
//		addStep("Se valida el detalle en la tabla TSFDETAIL.");
//		cb = App.getClipboard();
//		System.out.println(cb);
//		transfer = cb;
//
//		query = String.format(QueriesRegresion.TSFDETAIL, transfer);
//		System.out.println(query);
//		System.out.println("conexion = "+ dbRms.getConn().toString());
//		executeQuery = executeQuery(dbRms, query);
//		if (!executeQuery.isEmpty()) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
//		
//		su.close();
//		//Paso 15
//		addStep("Se valida el encabezado del pedido en RDM.");
		
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_USER_RDMVIEW, GlobalVariables.DB_USER_RDMVIEW,
				GlobalVariables.DB_PASSWORD_RDMVIEW);
		System.out.println("Entro");
		cb = App.getClipboard();
		transfer = cb;

		query = String.format("Select FACILITY_ID, DISTRO_NBR, PICK_NOT_BEFORE_DATE from  STOCK_ORDER where DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"No se encuentran registros con la consulta");
		
		
		//Paso 16
		addStep("Se valida el detalle del pedido en RDM.");
		
		cb = App.getClipboard();
		transfer = cb;
		item = data.get("item");
		query = String.format("Select FACILITY_ID, DISTRO_NBR, DEST_ID, ITEM_ID from  STOCK_ALLOCATION where DISTRO_NBR = '%s' and item_id = '%s'", transfer, item);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		
		// Paso 17
		addStep("Se ejecuta el Job XXFC_BASE_PROGRAMACION_MTY");
		
		String resultadoEjecucion;
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		JobUtil job = new JobUtil(su, testCase, data);
		
		resultadoEjecucion = job.ejecutar("xxfc_base_programacion_mty");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El xxfc_base_programacion_mty no se ejecuto correctamente");
		
		//Paso 18
		addStep("Validar que la informaci�n se encuentre en las siguientes tablas"
				+ "Xxfc_base_tipo_distro"
				+ "Xxfc_base_programacion"
				+ "Xxfc_base_programacion_zona");
		transfer = cb;
		String date = "";

		testCase.addBoldTextEvidenceCurrentStep("Se valida el registro en la tabla Xxfc_base_tipo_distro");
		query = String.format("SELECT CR_PLAZA, CREATE_DATE, CR_TIENDA, RUTA FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRdm, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			date = executeQuery.getData(0, "CREATE_DATE");
			date = date.substring(0, 10);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT DEST_ID, TSF_NO, GENERADO, TIPO_DISTRO FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PO, SUPPLY_LINE, STATUS, FECHA_INSERT FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT ESTATUS_PROC_RDM, PMO_ROUTE, PMO_ROUTE_OLD, DIA_GEN_PED FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_DIA_GEN_PED_OLD, PMO_GEN_PED_DATE, PMO_PROG_DATE, PMO_INI_PROD_DATE FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_FIN_PROD_DATE, PMO_INI_CARGA_DATE, PMO_FIN_CARGA_DATE, PMO_SALIDA_DATE FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_ROUTE_INI_ENT, PMO_ROUTE_FIN_ENT, PMO_PO_GEN_CUTOFF_TIME, PMO_PO_GENERATION_TIME FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_DISP_UNI_CEDIS, PMO_LOAD_SEQ, PMO_INI_VENT_ENT, PMO_FIN_VENT_ENT FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRdm.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_TIPO_PEDIDO, PMO_CROSSDOCK, PMO_LSP, PMO_LSSEP1 FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_LSSEP2, REG_AB_SEL, FECHA_AB_SEL, FECHA_LIBER FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT DOOR_ID, QA_IND, VERIFY_EXT, VERIFY_INT FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_INI_VER_DATE, PMO_FIN_VER_DATE, PMO_INI_CONS_DATE, PMO_FIN_CONS_DATE FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT TC_FLAG, PMO_FACT_DATE, PMO_ARRIBO_PT_DATE, PMO_SALIDA_PT_DATE FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_ARRIBO_TDA, PMO_INI_ENTREGA_TDA, PMO_FIN_ENTREGA_TDA, PMO_INI_DEV_TDA FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_FIN_DEV_TDA, PMO_FOLIO_TDA, PMO_SALIDA_TDA, PMO_RETORNO_CEDIS FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT PMO_INI_ENR_CED, PMO_FIN_ENR_CED, DISTRO_NBR, NEW_TSF_NO FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT VERFICACION_CAJAS, VERFICACION_CAJAS_FECHA, CONSOLIDACION, CONSOLIDACION_FECHA FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		//////////////////////////////////////////////////
		
		query = String.format("SELECT CARGA, CARGA_FECHA, LIBERACION FROM rdm100.xxfc_base_tipo_distro WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/****************************************************************************************************
		 ****************************************************************************************************
		 ****************************************************************************************************/
		

		testCase.addBoldTextEvidenceCurrentStep("Se valida el registro en la tabla Xxfc_base_programacion");
		query = String.format("SELECT CEDIS, CR_PLAZA, TSF_NO, ITEM_ID FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT DEPARTMENT, STATUS, GENERADO, CREATE_DATE FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT DEST_ID, ZONE, LOC_CLASS, UNID_PED FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT RUTA, TIPO_DISTRO, PO, SUPPLY_LINE FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT STD_CASEPACK, INNER_PACK_QTY, CAJ_PED, CAJ_PED_HIELO FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CAJTAR_PED, CAJTAR_PED_VAL, CAJTAR_PED_RYC, CM_CUBE_CAN_SLS FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_CAN_VAL, CM_CUBE_CAN_CHO, CM_CUBE_CAN_FYV, CM_CUBE_CAN_RYC FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_CAN_TT, CM_CUBE_CAN_FF, CM_CUBE_CAN_P, CM_CUBE_CAJ_H FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_CAJ, CM_CUBE_CAJ_VAL, CM_CUBE_CAJ_RYC, SLS FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT SVAL, SCF, SCHO, SFYV FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT SRYC, STT, SFF, SP FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT FECHA_INSERT, LIBERADO, FECHA_LIBER, USER_ID FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CROSSDOCK, ESTATUS_PROC_RDM, SSP, QA_IND FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_SSP, CALC_QTY, CALC_CUBE, CALC_CLASIF FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT DISTRO_NBR, NEW_TSF_NO FROM rdm100.xxfc_base_programacion WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/****************************************************************************************************
		 ****************************************************************************************************
		 ****************************************************************************************************/

		testCase.addBoldTextEvidenceCurrentStep("Se valida el registro en la tabla Xxfc_base_programacion_zona");

		query = String.format("SELECT CEDIS, CR_PLAZA, TSF_NO, STATUS FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT GENERADO, CREATE_DATE, DEST_ID, RUTA FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT ZONE, LOC_CLASS, TIPO_DISTRO, PO FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT SUPPLY_LINE, EST_PICKS, EST_CASES_TOT, EST_CASES_SECOS FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT EST_CASES_H, EST_CASES_VAL, EST_CASES_RYC, EST_TOTES_SLS FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT EST_TOTES_VAL, EST_TOTES_CHO, EST_TOTES_FYV, EST_TOTES_RYC FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT EST_TOTES_TT, EST_TOTES_FF, EST_TOTES_P, CM_CUBE_TOTES_SLS FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_TOTES_VAL, CM_CUBE_TOTES_CHO, CM_CUBE_TOTES_FYV, CM_CUBE_TOTES_RYC FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_TOTES_TT, CM_CUBE_TOTES_FF, CM_CUBE_TOTES_P, CM_CUBE_CAJ_H FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CM_CUBE_CAJ, CM_CUBE_CAJ_VAL, CM_CUBE_CAJ_RYC, SLS FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT SVAL, SCF, SCHO, SFYV FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT SRYC, STT, SFF, SP FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT FECHA_INSERT, LIBERADO, FECHA_LIBER, USER_ID FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT PESO, ESTATUS_PROC_RDM, SSP, CM_CUBE_SSP FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CALC_QTY, CALC_CUBE, CALC_CLASIF, DISTRO_NBR FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		////////////////////////////////////////////////////////////////////////////
		
		query = String.format("SELECT CALC_CM_CUBE_TOTES, CALC_EST_TOTES FROM rdm100.xxfc_base_programacion_zona WHERE DISTRO_NBR = '%s'", transfer);
		System.out.println(query);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"Se encuentran registros con la consulta");
		
		/****************************************************************************************************
		 ****************************************************************************************************
		 ****************************************************************************************************/
		
		su = new SeleniumUtil(new IETest(), true);
		TransferenciasRDM trdm = new TransferenciasRDM(su, testCase, data);
		
		
		//Paso 19
		addStep("Ingresar a la Aplicaci�n RDM. Para realizar la programaci�n por Ruta.");
		testCase.addScreenShotCurrentStep("");
		trdm.logIn();	
		
		//Paso 20
		addStep("Seleccionar el men� Distribution Planning -> XXFC � Programaci�n y balanceo ->  XXFC-Mod. Programaci�n p Ruta.");
		trdm.navXXFCModProgramacionPRuta();
		
		//String date = "23-May-2023";
		//Paso 21
		addStep("Se debe capturar el campo Distro y seleccionar ejecutar para que muestre el registro creado.");
		trdm.fillXXFCPRogramacionSurtidoRoute(date);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * En esta parte inicia el proceso de pasos de la plataforma de RDM
		 */
		

		
		
		
	}
	
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CrearTransferenciaenRMSCedis_TienaconenviodeASN_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_001_CrearTransferenciaenRMSCedis_TienaconenviodeASN";
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

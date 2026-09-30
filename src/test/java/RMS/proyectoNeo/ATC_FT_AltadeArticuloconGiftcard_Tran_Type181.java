package RMS.proyectoNeo;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_AltadeArticuloconGiftcard_Tran_Type181 extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_AltadeArticuloconGiftcard_Tran_Type181_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA, GlobalVariables.DB_USER_BDRMSCQA,
				GlobalVariables.DB_PASSWORD_BDRMSCQA);
		
		// Queries
//		String promotion = data.get("promotion"); // id del registro que vamos a buscar en las tablas 	
		String cb = null;
		boolean ejecucionCorrectaJob = false;
		//String ResultadoEjecucion = null;
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
		JobUtil job = new JobUtil(su, testCase, data);
		//JSONArray array = new JSONArray(data.get("jobs"));
		//JobManagement j = new JobManagement(su, testCase, array);
		
		SQLResult executeQuery;
		Boolean statusQuery = false;
		
		//Paso 1 - 2
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();	
		
		//Paso 3
		addStep("Seleccionar el folder items, elegir la opcion items y dar clic en el bot�n open");
		t.navItems();
		
		
		//Paso 4
		addStep("Seleccionar en el campo Action la opci�n New item.\r\n"
				+ "Llenar los siguientes campos seg�n corresponda:\r\n"
				+ " Subcategoria <<<subcategoria>>>\r\n"
				+ " Segmento <<<segmento>>>\r\n"
				+ " Subsegmento <<<subsegmento>>>\r\n"
				+ " Pack item <<<No>>>\r\n"
				+ " Presionar OK.");
		
		t.NewItem_2();
		
		//Paso 5
		addStep("Capturar o seleccionar la opci�n correspondiente de los siguientes campos:\r\n"
				+ "  item Type <<<EAN8>>>\r\n"
				+ "  item <<<n�mero item>>>\r\n"
				+ "  Description <<<Descripci�n Larga Item>>>\r\n"
				+ "  Short Desc <<<Descripci�n corta Item>>>\r\n"
				+ "  Tran Level <<<Level 1>>>\r\n"
				+ "  Merchandise <<<Check>>>\r\n"
				+ "  Forecastable <<<Check>>>\r\n"
				+ "  Catch Weight <<<Check>>>\r\n"
				+ "  Constant Dimensions <<<Check>>>\r\n"
				+ "  Retail Zone <<<1>>>\r\n"
				+ "  Cost Zone <<<1>>>\r\n"
				+ "  Selling Unit Retail <<<Precio Unitario de Venta>>>\r\n"
				+ "  Suggested Retail <<<Precio Sugerido de Venta>>>\r\n"
				+ "  Store Order Multiple <<<Eaches>>>\r\n"
				+ "  Standard UOM <<<EA>>>\r\n"
				+ "  Dar clic en Groc Attributes");
		t.fil_ItemVirtual_SinItem();
		
		//Paso 6
		addStep("Capturar los datos correspondientes en los siguientes campos:\r\n"
				+ "  package RMS.Size <<<1>>>\r\n"
				+ "  package RMS.UOM <<<KG>>>\r\n"
				+ "  Dar clic en el bot�n OK.");
		t.FillItemMaster();
		
		//Paso 7
		addStep("Dar clic en la opci�n Supplier");
		t.ClicBtnSupplier();
		
		
		//Paso 8
		addStep("Dar clic en el bot�n Add, en el campo Supplier, seleccionar un proveedor de la lista y dar clic en el bot�n Apply.");
		t.AddSupplier();
		
		
		//Paso 9
		addStep("Dar clic en la opci�n Item-Supplier-Origin-Country");
		t.Clicbtn_ISOC();			
		
		//Paso 10
		addStep("En el campo Origin Country, seleccionar el pa�s de origen del proveedor y dar clic en el bot�n Create y llenar los siguientes campos:\r\n"
				+ "  Inner Size <<<Tama�o interior>>>\r\n"
				+ "  Case Size <<<Tama�o de la caja>>>\r\n"
				+ "  Tier <<<Nivel>>>\r\n"
				+ "  Height <<<Altura>>>\r\n"
				+ "  Case Cost <<<Costo Caja>>>\r\n"
				+ "  Unit Cost <<<Costo Unitario>>>");
		t.SelectOriginCountry();	
		
		//Paso 11
		addStep("En la secci�n Dimensions, llenar los campos de cada package RMS.Type (Each, Case, Inner, Palet) dando click en OK + Repeat en cada caso.\r\n"
				+ "  Length <<<Largo>>>\r\n"
				+ "  Width <<<Ancho>>>\r\n"
				+ "  Height <<<Altura>>>\r\n"
				+ "  Dimension Unit Of Measure <<<CM>>>\r\n"
				+ "  Volume Unit Of Measure <<<CM3>>>\r\n"
				+ "  Gross Weight <<<Peso Bruto>>>\r\n"
				+ "  Net Weight <<<Peso Neto>>>\r\n"
				+ "  Weight Unit Of Measure <<<KG>>>\r\n"
				+ "  Liquid Volume <<<Volumen>>>\r\n"
				+ "  Liquid Unit Of Measure <<<CM3>>>\r\n"
				+ "  Al finalizar, presionar el bot�n OK.");
		t.FillPackageType();		
		
		//Paso 12
		addStep("Dar clic en la opci�n Retail by Zone.");
		t.clicRetailbyZone();	
		
		//Paso 13
		addStep("Despliega la zona seleccionada y se da clic en OK.");
		t.clic_btn_OK_skuretl();	
		
		//Paso 14
		addStep("Dar clic en la opci�n Location.");
		t.clicLocations();	
		
		//Paso 15
		addStep("En la ventana Item Location seleccionar los siguientes campos que se encuentran en la parte inferior izquierda de la pantalla:\r\n"
				+ "  Group Type <<<Store>>>\r\n"
				+ "  Group Value <<<#Tienda>>>\r\n"
				+ "  Presionar el bot�n Apply\r\n"
				+ "  Dar clic en el bot�n OK.");
		t.ItemLocation();	
		
		//Paso 16
		addStep("Dar clic en la opci�n Submit.");
		t.ClicSubmit();		
		
		//Paso 17
		addStep("Dar clic en la opci�n Approve y Presionar el bot�n OK.");
		t.ClicApprove();	
		
		t.close_RMS_2();
		t.CloseBrowser();
		
		
		//Paso 18
		addStep("Validar que la informaci�n del item se vea reflejada en las tablas item_master, item_loc, item_supplier, item_supp_country y item_supp_country_dim dentro de la Base de Datos.");
		cb = App.getClipboard();
		System.out.println(cb);
		String item = cb;
		String store = null;
		String queryItem = String.format(Queries_Tiendas.tbl_Item_Master,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_loc,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			store = executeQuery.getData(0, "LOC");
		}

		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_supplier,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_supp_country,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_supp_country_dim,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		//Paso 19
		addStep("Validar que se haya insertado el alta del art�culo receta en la tabla POS_MODS con tran type 1");
		
		queryItem = String.format(Queries_Tiendas.tbl_pos_mods,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el tran_type 1 fue encontrado");		
		
		////////////////////////Consultas extra//////////////////////
		queryItem = Queries_Tiendas.tbl_xxfc_configuracion_general;
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"Se realizo la consulta");
		
		store = data.get("GroupValue");
		queryItem = String.format(Queries_Tiendas.tbl_xxfc_tiendas_migradas_geocom,store);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"El registro cuenta con estatus 1");	
		
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		Control_mInicio CM = new Control_mInicio(su, GlobalVariables.CtrlM_User,PasswordUtil.decryptPassword(GlobalVariables.CtrlM_Pss));
//		su.get(GlobalVariables.CtrlM_URL);
//		su.hardWait(50);
//		su.waitForLoadPage();
//		CM.logOn();
//		String resultadoEjecucion;
//
//		//Paso 20
//		addStep("Ejecutar la cadena de jobs del grupo AIX_PRECIOS_Y_PROMOCIONES hasta el job XXFC_PROCESA_POSDNLD_ITM en Control-M");
//		
//		resultadoEjecucion = job.ejecutar("rr_setup.sh_9_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job rr_setup.sh_9_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("rule_check_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job rule_check_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("prmxpld_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job prmxpld_chile no se ejecuto correctamente");
//				
//		resultadoEjecucion = job.ejecutar("prmpreupd_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job prmpreupd_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("xxfc_pre_prmext_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job xxfc_pre_prmext_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("prmext_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job prmext_peru no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("postprmext_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job postprmext_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("xxfc_api_price_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job xxfc_api_price_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("pcdnld_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job pcdnld_peru no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("fem_chk_lock_pos_mods_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job fem_chk_lock_pos_mods_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("xxfcpmodsstaging_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job xxfcpmodsstaging_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("fem_posdnld_pre_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job fem_posdnld_pre_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("fem_prmext_post_peru");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job fem_prmext_post_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("xxreo_fcp_pos_mods_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job xxreo_fcp_pos_mods_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("xxfc_procesa_posdnld_itm_chile");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job xxfc_procesa_posdnld_itm_chile no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("pcovrlpq");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job pcovrlpq no se ejecuto correctamente");
//		
//		// PASO 21
//		addStep("Validar que se haya generado el Batch_id de la ejecuci�n en la tabla "
//				+ "WMUSER.POS_ITM_PRM_HEAD. ");
//		System.out.println("PASO 8");
//		
//		String posItem = String.format(QueriesItems.WM_POS_ITM_PRM_HEAD, store);
//		System.out.println(posItem);
//		executeQuery = executeQuery(dbRms, posItem);
//		String loadBatchId = "";
//		
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			loadBatchId = executeQuery.getData(0, "LOAD_BATCH_ID");
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con LOC = " + store
//				+ " en WMUSER.POS_ITM_PRM_HEAD");
//		
//		// PASO 22
//		addStep("Validar en la tabla WMUSER.FEM_POS_MODS_STG que se encuentre el registro del alta del "
//				+ "art�culo� que se proces� dentro del Batch_id de la ejecuci�n.");
//		
//		String modsStg = String.format(QueriesItems.WM_FEM_POS_MODS_STG, item, loadBatchId);
//		System.out.println(modsStg);
//		
//		executeQuery = executeQuery(dbRms, modsStg);
//		
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item
//				+ " y LOAD_BATCH_ID = " + loadBatchId + "en WMUSER.FEM_POS_MODS_STG");
//		
//		// PASO 23
//		addStep("Ejecutar el Job runITM en Control-M");
//		resultadoEjecucion = job.ejecutar("runITM");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		assertTrue(ejecucionCorrectaJob, "El job pcovrlpq no se ejecuto correctamente");
//		
//		// PASO 24
//		addStep("Validar en la tabla WMUSER.POS_ITM_PRM_HEAD, dentro del Batch_id de la ejecuci�n, "
//				+ "que se haya generado el archivo ITM");
//		System.out.println("PASO 11");
//		
//		posItem = String.format(QueriesItems.WM_POS_ITM_PRM_HEAD, store);
//		System.out.println(posItem);
//		
//		executeQuery = executeQuery(dbRms, posItem);
//		String itm = "";
//				
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			itm = executeQuery.getData(0, "WM_TARGET_ITM");
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con LOC = " + store
//				+ " en WMUSER.POS_ITM_PRM_HEAD");
//		
//		// PASO 25
//		addStep("Validar que se encuentre el archivo ITM en la tabla POSUSER.POS_OUTBOUND_DOCS");
//		System.out.println("PASO 12");
//		
//		String posUser = String.format(QueriesItems.POS_OUTBOUND_DOCS_ITM, itm);
//		System.out.println(posUser);
//		
//		executeQuery = executeQuery(dbRms, posUser);
//		
//		statusQuery = executeQuery.isEmpty();
//		if (!statusQuery) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//		}
//
//		assertFalse(statusQuery, "No se encontraron registros con DOC_NAME = " + itm
//				+ " en POSUSER.POS_OUTBOUND_DOCS");
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_018_AltadeArticuloconGiftcard_Tran_Type181_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_018_AltadeArticuloconGiftcard_Tran_Type181";
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

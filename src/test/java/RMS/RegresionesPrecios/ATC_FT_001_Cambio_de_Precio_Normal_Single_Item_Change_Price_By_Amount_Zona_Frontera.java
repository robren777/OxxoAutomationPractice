package RMS.RegresionesPrecios;

import static org.testng.Assert.assertEquals;
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

import RMS.util.ExecuteQuery;
import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_Cambio_de_Precio_Normal_Single_Item_Change_Price_By_Amount_Zona_Frontera extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Cambio_de_Precio_Normal_Single_Item_Change_Price_By_Amount_Zona_Frontera_test(
			HashMap<String, String> data) throws Exception {

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

		String costChange = null;
		boolean msg = false;

		ExecuteQuery eq = new ExecuteQuery(testCase);

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
		msg = t.verifyNewPrice();
		assertTrue(msg, "No se ha modificado el precio.");

		// Paso 9
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Submit.");
		msg = t.submit();

		assertTrue(msg, "No se cambio status a Submitted, se deben probar con otros datos de tienda");

		// Paso 10
		addStep("En la barra de navegaci�n, seleccionar del men� Options la opci�n Approve.");
		msg = t.approveRetailPrice1();

		assertTrue(msg, "No se cambio status a Approved");

		t.close_RMS_RPCH2();

		t.CloseBrowser();

		String queryCost = String.format("SELECT * FROM price_susp_detail where PRICE_CHANGE = '%s'", costChange);

		eq.executeQuerySeparatedColumns(dbRms, queryCost);

		String statusT = eq.getData(dbRms, queryCost, 0, "STATUS");

		assertEquals(statusT, "A");

		/*
		 * Siguiente consulta en la tabla PRICE_SUSP_HEAD
		 */

		queryCost = String.format("SELECT * FROM price_susp_head where PRICE_CHANGE = '%s'", costChange);

		eq.executeQuerySeparatedColumns(dbRms, queryCost);

		statusT = eq.getData(dbRms, queryCost, 0, "STATUS");

		assertEquals(statusT, "A");

		/////////////////////////////////////////////////////////////////

		// Paso 11
		addStep("Ejecutar AIX_Precios en Control M Nota: Los JOBS a ejecutar son la pccext");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
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
		u.hardWait(3);

		JSONArray array = new JSONArray(data.get("jobs"));

		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		u.hardWait(3);

		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB pctranex");
		String resultado = job.ejecutarSetOk("pctranex");
		assertEquals(resultado, "Ended OK");

		///////////////////////////////////////////////////////////////

		array = new JSONArray(data.get("jobs2"));

		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
		u.hardWait(3);

		System.out.println(costChange);

		/**
		 * PASO 12
		 ******************************************************************************************/
		addStep("Ejecutar PR26");
		boolean result = false;
		testCase.addBoldTextEvidenceCurrentStep("Ejecucion del JOB rr_setup_sh_9");
		resultado = job.ejecutar("rr_setup_sh_9");
		if (resultado.equals("Ended OK") || resultado.equals("Wait Condition")) {
			result = true;
		}
		assertTrue(result, "No se ha ejecutado el job exitosamente");

		////////////////////////////////////////////////////////////////
		
		
//		array = new JSONArray(data.get("jobsPP"));
//
//		j = new JobManagement(u, testCase, array);
//		resultadoEjecucion = j.jobRunner();
//		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);
//
//		assertTrue(job.fueEjecutadoCorrectamente(resultadoEjecucion), "No se ejecuto el JOB correctamente");
//		u.hardWait(3);
//
//		System.out.println(costChange);
		

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
		String item = data.get("Item");

		query = String.format("select * from POS_MODS where ITEM = '%S' AND STORE = '%S' AND TRAN_TYPE IN (%s)", item,
				store, "11");

		eq.executeQuerySeparatedColumns(dbRms, query);

		/*
		 * BATCH ID
		 */

		testCase.addBoldTextEvidenceCurrentStep("Validar que se genero el Batch_id");

		query = String.format("select * from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc",
				store);

		eq.executeQuerySeparatedColumns(dbRms, query);

		String loadBatchID = eq.getData(dbRms, query, 0, "LOAD_BATCH_ID");

		boolean b = false;

		if (loadBatchID.equals(null)) {
			b = false;
		} else {
			b = true;
		}
		assertTrue(b, "No se ha generado el LOAD BATCH ID");

		/***********************************************************************************************************
		 * ********************************************************************************************************************************
		 */

		testCase.addBoldTextEvidenceCurrentStep(
				"Validar que se encuentre el registro del alta del articulo que se proceso dentro del Batch_id de la ejecucion. ");

		query = String.format(
				"select * from wmuser.fem_pos_mods_stg where load_batch_id = '%s' and stire = '%s' and item '%s'",
				loadBatchID, store, item);
		eq.executeQuerySeparatedColumns(dbRms, query);

		/***********************************************************************************************************
		 * 
		 ************************************************************************************************************/

		testCase.addBoldTextEvidenceCurrentStep("Validar que se genero el archivo ITM");

		query = String.format("select * from wmuser.pos_itm_prm_head where location = '%s' order by load_date desc",
				store);
		eq.executeQuerySeparatedColumns(dbRms, query);

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Cambio_de_Precio_Normal_Single_Item_Change_Price_By_Amount_Zona_Frontera_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_001_Cambio_de_Precio_Normal_Single_Item_Change_Price_By_Amount_Zona_Frontera";
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

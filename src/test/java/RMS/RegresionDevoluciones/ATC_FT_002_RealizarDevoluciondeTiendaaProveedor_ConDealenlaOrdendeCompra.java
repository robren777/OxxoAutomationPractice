package RMS.RegresionDevoluciones;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.QueriesRegresion;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_RealizarDevoluciondeTiendaaProveedor_ConDealenlaOrdendeCompra extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_RealizarDevoluciondeTiendaaProveedor_ConDealenlaOrdendeCompra_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Aqui se necesita poner la informacion de las bases de datos de WB
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		boolean ejecucionCorrectaJob = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		JobUtil job = new JobUtil(su, testCase, data);
		String resultadoEjecucion;

		SQLResult executeQuery;
		Boolean statusQuery = true;
		String query;
		String pid_id;
		String item;
		String pv_doc_name;
		String cr_plaza = null;
		String reference_3 = null;
		String reference_9 = null;
		String rtv_order_no;
		String rtk_filename;
		String supplier;
		String store;
		String fem_remision;
		String order_no;
		String tran_code;

		// Paso 1
		addStep("Se genera venta de articulos en el punto de venta");
		
		// Paso 2
		addStep("Se ejecuta la DS50");
						
		resultadoEjecucion = job.ejecutar("DS50");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job DS50 no se ejecuto correctamente");
		
		// Paso 3
		addStep("Se revisa información de la venta en tablas de Webmethods");
		
		//POS_RTV
		pid_id = data.get("pid_id");
		query = String.format(QueriesRegresion.POS_RTV, pid_id);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//POS_RTV_DETL
		pid_id = data.get("pid_id");
		query = String.format(QueriesRegresion.POS_RTV_DETL, pid_id);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//POS_INBOUND_DOCS
		pv_doc_name = data.get("pv_doc_name");
		query = String.format(QueriesRegresion.POS_INBOUND_DOCS, pv_doc_name);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 4
		addStep("Se ejecuta la interfaz PR5 en Control-M");
		
		resultadoEjecucion = job.ejecutar("PR5");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job PR5 no se ejecuto correctamente");
		
		
		// Paso 5
		addStep("Validar que se genere el archivo .DAT en la tabla POS_INBOUND_DOCS y que el estatus cambie de I a E.");
		
		//POS_INBOUND_DOCS
		pv_doc_name = data.get("pv_doc_name");
		query = String.format(QueriesRegresion.POS_INBOUND_DOCS, pv_doc_name);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		assertEquals(executeQuery.getData(0, "STATUS"), "E");
		
		if (executeQuery.getData(0, "TARGET_ID") == "") {
			statusQuery = false;
		}
		else {
			rtk_filename = executeQuery.getData(0, "TARGET_ID");
		}
		assertFalse(statusQuery, "No se genero el archivo .DAT");
		
		
		// Paso 6
		addStep("Validar que el archivo .DAT de ventas se encuentre en la ruta /u04/retek/rmsqa/batch/wmworking/SAL en el servidor unix de RMS.");
		
//		testCase.addTextEvidenceCurrentStep("FTP_HOST: " + data.get("FTP_HOST") +
//		                        "\nFTP_PORT: " + data.get("FTP_PORT") +
//		                        "\nFTP_USER: " + data.get("FTP_USER"));
//
//		FTPUtil ftp = new FTPUtil(data.get("FTP_HOST"), Integer.parseInt(data.get("FTP_PORT")), data.get("FTP_USER"), data.get("FTP_PASSWORD"));
//
//		String path = "/u01/posuser/FEMSA_OXXO/POS/"+data.get("CR_PLAZA")+"/"+data.get("CR_TIENDA")+"/working" + "/" +docName;
//
//		if (ftp.fileExists(path)) {
//			System.out.println(path + " - Existe");
//			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: " + path);
//		} else {
//			System.out.println(path + " - No xiste");
//			testCase.addTextEvidenceCurrentStep("Error - no se encontr� archivo: " + path);
//		}
//
//		assertTrue(ftp.fileExists(path), "No se obtiene el archivo por FTP.");
		
		
		// Paso 7
		addStep("Se ejecuta el proceso FEMRTVUPLD");
		
		resultadoEjecucion = job.ejecutar("FEMRTVUPLD");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job FEMRTVUPLD no se ejecuto correctamente");
		
		
		// Paso 8
		addStep("Validar que se haya insertado información de la devolución en las tablas RTV_HEAD y RTV_DETAIL de RMS.");
		//RTV_HEAD,
		rtv_order_no = data.get("rtv_order_no");
		supplier = data.get("supplier");
		store = data.get("store");
		fem_remision = data.get("fem_remision");
		query = String.format(QueriesRegresion.RTV_HEAD, rtv_order_no, supplier, store, fem_remision);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//RTV_DETAIL
		rtv_order_no = data.get("rtv_order_no");
		item = data.get("item");
		query = String.format(QueriesRegresion.RTV_DETAIL, rtv_order_no,item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 9
		addStep("Se ejecuta el job FEMRVFIDNLD_DLY en Control-M.");
		
		resultadoEjecucion = job.ejecutar("FEMRVFIDNLD_DLY");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job FEMRVFIDNLD_DLY no se ejecuto correctamente");
		
		
		// Paso 10
		addStep("Validar que se refleje información de la devolución en las tablas FEM_RTVHEAD_TEMP y FEM_RTVDETAIL_TEMP.");
		
		//FEM_RTVHEAD_TEMP
		rtv_order_no = data.get("rtv_order_no");
		query = String.format(QueriesRegresion.FEM_RTVHEAD_TEMP, rtv_order_no);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//FEM_RTVDETAIL_TEMP
		rtv_order_no = data.get("rtv_order_no");
		item = data.get("item");
		query = String.format(QueriesRegresion.FEM_RTVDETAIL_TEMP, rtv_order_no);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 11
		addStep("Validar que se haya generado la orden de compra. Revisar las tablas de órdenes de compra.");
		
		//ORHEAD2
		order_no = data.get("order_no");
		query = String.format(QueriesRegresion.ORHEAD2, order_no);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		//ORDLOC
		order_no = data.get("order_no");
		item = data.get("item");
		query = String.format(QueriesRegresion.ORDLOC, order_no, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		//ORDLOC_DISCOUNT
		order_no = data.get("order_no");
		item = data.get("item");
		query = String.format(QueriesRegresion.ORDLOC_DISCOUNT, order_no, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		//ORDLOC_DISCOUNT
		order_no = data.get("order_no");
		item = data.get("item");
		query = String.format(QueriesRegresion.ORDSKU, order_no, item);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 12
		addStep("Validar que la información de la venta del packitem se encuentre en la tabla TRAN_DATA");
				
		//TRAN_DATA
		String tran_date = ZonedDateTime.now(ZoneId.of("America/Chicago")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		store = data.get("store");
		tran_code = data.get("tran_code");
				
		query = String.format(QueriesRegresion.TRAN_DATA_2, tran_code, store, item,tran_date);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 13
		addStep("Se ejecuta la interfaz STOCK_LEDGER  en Control-M");
		
		resultadoEjecucion = job.ejecutar("STOCK_LEDGER");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job STOCK_LEDGER no se ejecuto correctamente");
		
		
		// Paso 14
		addStep("Validar en la tabla FEM_FIF_STG que se haya generado el Tran Code 24");
				
		//FEM_FIF_STG
		tran_code = "24";
		query = String.format(QueriesRegresion.FEM_FIF_STG_2, tran_code, cr_plaza, store, item, tran_date, reference_3);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 15
		addStep("Se ejecuta la interfaz runRO8_MEX");
		
		resultadoEjecucion = job.ejecutar("runRO8_MEX");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job runRO8_MEX no se ejecuto correctamente");
		
		
		// Paso 16
		addStep("Validar que los campos reference_3 y reference_9 ya no se encuentre en null en la tabla FEM_FIF_STG para el Tran Code 24.");
		
		//FEM_FIF_STG
		query = String.format(QueriesRegresion.FEM_FIF_STG_reference_2, tran_code, cr_plaza, store, item, tran_date);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		if (executeQuery.getData(0, "REFERENCE_3") == "") {
			statusQuery = false;
		}
		else if(executeQuery.getData(0, "REFERENCE_9") == ""){
			statusQuery = false;
		}
		else {
			statusQuery = true;
		}
		assertFalse(statusQuery, "No se genero el archivo .DAT");
		
		
		// Paso 17
		addStep("Validar que la información de la Devolución se encuentre en la tabla GL_INTERFACE de EBS.");
		
		/*
		 * Poner aqui los datos de la base de datos de EBS
		 */
		dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		//GL_INTERFACE
		query = String.format(QueriesRegresion.GL_INTERFACE_2, reference_3, reference_9);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertFalse(executeQuery.isEmpty(), "No existen registros");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_RealizarDevoluciondeTiendaaProveedor_ConDealenlaOrdendeCompra_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_002_RealizarDevoluciondeTiendaaProveedor_ConDealenlaOrdendeCompra";
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

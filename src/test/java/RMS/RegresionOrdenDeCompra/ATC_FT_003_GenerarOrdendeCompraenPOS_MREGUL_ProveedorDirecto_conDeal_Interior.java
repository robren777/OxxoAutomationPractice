package RMS.RegresionOrdenDeCompra;

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

public class ATC_FT_003_GenerarOrdendeCompraenPOS_MREGUL_ProveedorDirecto_conDeal_Interior extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_GenerarOrdendeCompraenPOS_MREGUL_ProveedorDirecto_conDeal_Interior_test(HashMap<String, String> data)
			throws Exception {

		SQLUtil dbRms;

		boolean ejecucionCorrectaJob = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		JobUtil job = new JobUtil(su, testCase, data);
		String resultadoEjecucion;
		
		SQLResult executeQuery;
		Boolean statusQuery = true;
		String query;
		String cr_tienda;
		String rtk_filename = null;
		String store;
		String cr_plaza = null;
		String reference_3 = null;
		String tran_code;
		String orhead;
		String orhead2;


		// Paso 1
		addStep("Se genera orden de compra a Proveedor Directo desde punto de venta.");
		
		// Paso 2
		addStep("Se ejecuta la DS50");
		
		resultadoEjecucion = job.ejecutar("DS50");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job DS50 no se ejecuto correctamente");
		
		// Paso 3
		addStep("Se revisa la información de la compra en las tablas de Webmethods\r\n"
				+ "POS_INBOUND_DOCS\r\n"
				+ "POS_REC\r\n"
				+ "POS_REC_DTL");
		
		/*
		 * Se necesita poner en esta parte los datos para WEB METHODS
		 */
		dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
				
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");		
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");	
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");	
		
	
		
		// Paso 4
		addStep("Se ejecutan los jobs execPR28 y execPR23 en Control-M.");
		
		resultadoEjecucion = job.ejecutar("execPR28");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job execPR28 no se ejecuto correctamente");
		
		resultadoEjecucion = job.ejecutar("execPR23");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job execPR23 no se ejecuto correctamente");
		
		
		// Paso 5
		addStep("Validar que se actualice el estatus a E en la tabla POS_INBOUND_DOCS y que se generen los archivos RECUPD y APOUPD.");
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 6
		addStep("Validar que se refleje un registro REC en la tabla WMUSER.RTK_INBOUND_DOCS en RMS.");
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 7
		addStep("Validar que se refleje un registro REC en la tabla WMUSER.RTK_INBOUND_DOCS en RMS.");
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		

		// Paso 8
		addStep("Verificar que el archivo recupd se encuentre en la ruta /u04/retek/rmsqa/batch/wmworking/REC en el servidor de RMS.");
		
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
		
		
		// Paso 9
		addStep("Verificar que el archivo apoupd se encuentre en la ruta /u04/retek/rmsqa/batch/wmworking/APO en el servidor de RMS.");
		
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
		

		// Paso 10
		addStep("Ejecutar el proceso FEMPOUPLD en Control-M.");
		
		resultadoEjecucion = job.ejecutar("FEMPOUPLD");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job FEMPOUPLD no se ejecuto correctamente");
		
		
		// Paso 11
		addStep("Validar en la tabla WMUSER.RTK_INBOUND_DOCS de RMS que el archivo REC cambie a estatus E.");
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 12
		addStep("Validar en la tabla WMUSER.RTK_INBOUND_DOCS de RMS que el archivo APO cambie a estatus E");
		
		//WMUSER_RTK_INBOUND_DOCS
		cr_plaza = data.get("cr_plaza");
		cr_tienda = data.get("cr_tienda");
		rtk_filename = data.get("rtk_filename");
		query = String.format(QueriesRegresion.WMUSER_RTK_INBOUND_DOCS_2, cr_plaza, cr_tienda, rtk_filename);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 13
		addStep("Validar que el archivo recupd se haya depositado en la ruta /u04/retek/rmsqa/batch/femrecupd/processed en el servidor de RMS.");
		
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
		
		
		// Paso 14
		addStep("Validar que el archivo apoupd se haya depositado en la ruta /u04/retek/rmsqa/batch/femapoupd/processed en el servidor de RMS.");
		
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
		
		// Paso 15
		addStep("Validar que la información de la venta del packitem se encuentre en la tabla TRAN_DATA");
						
		//TRAN_DATA
		String tran_date = ZonedDateTime.now(ZoneId.of("America/Chicago")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		store = data.get("store");
		tran_code = data.get("tran_code");
				
		query = String.format(QueriesRegresion.TRAN_DATA_3, tran_code, tran_date, store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");	
		
		
		// Paso 16
		addStep("Validar que se refleje información del pedido para la tienda en las tablas de órdenes de compra en RMS.");
						
		//TRAN_DATA
		tran_date = ZonedDateTime.now(ZoneId.of("America/Chicago")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		store = data.get("store");
		tran_code = data.get("tran_code");
				
		query = String.format(QueriesRegresion.TRAN_DATA_3, tran_code, tran_date, store);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		// Paso 17
		addStep("Ejecutar el Job FEMRCFIDNLD_DLY en Control-M.");
		
		resultadoEjecucion = job.ejecutar("FEMRCFIDNLD_DLY");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job FEMRCFIDNLD_DLY no se ejecuto correctamente");
		
		
		// Paso 18
		addStep("Validar que se refleje información de la orden de compra en las tablas FEM_RECHEAD_TEMP y FEM_RECDETAIL_TEMP.");
				
		//FEM_RECHEAD_TEMP
		orhead = data.get("orhead_no");
		orhead2 = data.get("orhead_no2");
		
		query = String.format(QueriesRegresion.FEM_RECHEAD_TEMP, orhead, orhead2);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");

		
		//FEM_RECDETAIL_TEMP
		orhead = data.get("orhead_no");
		orhead2 = data.get("orhead_no2");
		
		query = String.format(QueriesRegresion.FEM_RECDETAIL_TEMP, orhead, orhead2);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 19
		addStep("Se ejecuta el ciclo STOCK_LEDGER en Control-M.");
		
		resultadoEjecucion = job.ejecutar("STOCK_LEDGER");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job STOCK_LEDGER no se ejecuto correctamente");
		
		
		// Paso 20
		addStep("Validar en la tabla FEM_FIF_STG que se haya generado información de la orden de compra.");
		
		//FEM_FIF_STG
		cr_plaza = data.get("cr_plaza");
		store = data.get("store");
		tran_code = data.get("tran_code");
		
		query = String.format(QueriesRegresion.FEM_FIF_STG_3, cr_plaza, tran_date, tran_code, reference_3);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(executeQuery.isEmpty(), "No existen registros");
		
		
		// Paso 21
		addStep("Se ejecuta la interfaz runRO8_MEX_CPAS en Control-M.");
		
		resultadoEjecucion = job.ejecutar("runRO8_MEX_CPAS");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job runRO8_MEX_CPAS no se ejecuto correctamente");
		
		
		// Paso 22
		addStep("Validar que los campos reference_3 y reference_9 ya no se encuentren en null en la tabla FEM_FIF_STG.");
		//FEM_FIF_STG
		cr_plaza = data.get("cr_plaza");
		store = data.get("store");
		tran_code = data.get("tran_code");
		
		query = String.format(QueriesRegresion.FEM_FIF_STG_reference_3, cr_plaza, tran_date, tran_code);
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
		
		
		// Paso 23
		addStep("Validar que la información de compras se encuentre en la tabla GL_INTERFACE de EBS para la póliza generada por la interfaz RO8.");
		
		/*
		 * Poner aqui los datos de la base de datos de EBS
		 */
		dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		//GL_INTERFACE
		query = String.format(QueriesRegresion.GL_INTERFACE_2, reference_3);
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
		return "ATC_FT_003_GenerarOrdendeCompraCentral_MREGUL_ProveedorDirecto_conDeal_Interior_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_003_GenerarOrdendeCompraCentral_MREGUL_ProveedorDirecto_conDeal_Interior";
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
